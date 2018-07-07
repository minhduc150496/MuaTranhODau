/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 *
 * @author MinhDuc
 */
public class HTMLPreprocessor implements Serializable {

    private static final String EMPTY_ELEMENTS = "<area> <base> <br> <col> <hr> <img> <input> <link> <meta> <param> <command> <keygen> <source>"; // HTML 5 

    public static String getHTMLFromURL(String sUrl) throws MalformedURLException, IOException {
        URL url = new URL(sUrl);
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        String result = null;
        try {
            is = url.openStream();
            isr = new InputStreamReader(is, "UTF-8"); // XIN ĐỪNG QUÊN!!!
            br = new BufferedReader(isr);
            result = "";
            String line;
            while ((line = br.readLine()) != null) {
                result += line;
            }
        } finally {
            if (br != null) {
                br.close();
            }
            if (isr != null) {
                isr.close();
            }
            if (is != null) {
                is.close();
            }
        }
        return result;
    }

    public static String wellForm(String raw) {
//        System.out.println("WELL-FORMING BEGIN...");

        String result = raw;

        result = removeShitTagsAndComments(result);
//        System.out.println("remove shit tags done");

        result = closeTags(result);
//        System.out.println("close tags done");

        result = addMissingTags(result);
//        System.out.println("add missing tags done");

        result = addSpaceBetweenAttrs(result);
//        System.out.println("add space between attrs done");

        result = addQuoteToAttrs(result);
//        System.out.println("add quote to attrs done");

        result = removeDuplicatedAttrKey(result);
//        System.out.println("remove duplicated attr key");

        result = correctAmps(result);
//        System.out.println("correct &amps; done");

//        System.out.println("WELL-FORMING DONE.");
        return result;
    }

    private static String convertLinkedListToString(LinkedList<Character> linkedList) {
        char[] arr = new char[linkedList.size()];
        int i = 0;
        for (Character ch : linkedList) {
            arr[i] = ch;
            i++;
        }
        String s = new String(arr);
        return s;
    }

    private static void addToLinkedList(LinkedList<Character> linkedList, String s) {
        for (int i = 0; i < s.length(); i++) {
            linkedList.add(s.charAt(i));
        }
    }

    // <body ---> <body>
    private static String closeTags(String raw) {
        int i = 0;
        boolean foundTag = false;
        LinkedList<Character> tmp = new LinkedList<>();
        while (i < raw.length()) {
            char ch = raw.charAt(i);
            if (ch == '<') {
                if (foundTag) {
                    tmp.add('>');
                } else {
                    foundTag = true;
                }
            }
            if (ch == '>') {
                foundTag = false;
            }
            tmp.add(ch);
            i++;
        }
        String result = convertLinkedListToString(tmp);
        return result;
    }

    private static String removeShitTagsAndComments(String raw) {
        LinkedList<Character> tmp = new LinkedList<>();
        boolean skip = false;
        boolean foundComment = false;
        int i = 0;
        while (i < raw.length()) {
            char ch = raw.charAt(i);
            if (ch == '<') {
                String tagName = "";
                int j = i + 1;
                while (j < raw.length() && raw.charAt(j) != ' ' && raw.charAt(j) != '>') {
                    tagName += raw.charAt(j);
                    j++;
                }
                if (tagName.startsWith("!--")) {
                    while (i - 2 < 0 || raw.substring(i - 2, i + 1).equals("-->") == false) {
                        i++;
                    }
                    i++;
                    continue;
                } else if (tagName.equals("style") || tagName.equals("script") || tagName.equals("meta")) {
                    skip = true;
                } else if (tagName.equals("/style") || tagName.equals("/script") || tagName.equals("/meta")) {
                    skip = false;
                    while (raw.charAt(i) != '>') {
                        i++;
                    }
                    i++;
                    continue;
                }
            }
            if (skip == false) {
                tmp.add(raw.charAt(i));
            }
            i++;
        } // end while

        String result = convertLinkedListToString(tmp);
        return result;
    }

    private static String correctAmps(String raw) { // correct "&"
        boolean found = false;
        List<Integer> incorrectPos = new ArrayList<>();
        // found incorrect "&"
        for (int i = 0; i < raw.length(); i++) {
            char ch = raw.charAt(i);
            if (ch == '&') {
                found = true;
                incorrectPos.add(new Integer(i));
            } else if (found && Character.isLetterOrDigit(ch) == false) {
                found = false;
                if (ch == ';') {
                    int size = incorrectPos.size();
                    incorrectPos.remove(size - 1);
                }
            }
        }

        // convert & to &amp;
        LinkedList<Character> tmp = new LinkedList<>();
        int k = 0;
        for (int i = 0; i < raw.length(); i++) {
            tmp.add(raw.charAt(i));
            if (k < incorrectPos.size()) {
                int pos = incorrectPos.get(k);
                if (i == pos) {
                    tmp.add('a');
                    tmp.add('m');
                    tmp.add('p');
                    tmp.add(';');
                    k++;
                }
            }
        }
        String result = convertLinkedListToString(tmp);
        return result;
    }

    // <img> --> <img/>     
    // <html><body></html> --> <html><body></body></html>
    // <html></body></html> --> <html></html>
    private static String addMissingTags(String raw) {
        Stack<String> stack = new Stack<>(); // stack of tag names
        Map<String, Integer> map = new HashMap<>(); // TagName - Number of occurance
        LinkedList<Character> tmp = new LinkedList<>();
        int i = 0;
        while (i < raw.length()) {
            char ch = raw.charAt(i);
            if (ch == '<') { // if meet <
                int j = i + 1;
                // get tagName
                String tagName = "";
                while (j < raw.length() && raw.charAt(j) != ' ' && raw.charAt(j) != '>') {
                    tagName += raw.charAt(j);
                    j++;
                }
                // end: get tagName

                class MyMethodUtils {

                    private void getOutOfMap(Map<String, Integer> map, String tagName) {
                        if (map.get(tagName) == null) {
                            return;
                        }
                        int count = map.get(tagName);
                        count--;
                        if (count == 0) {
                            map.remove(tagName);
                        } else {
                            map.put(tagName, count);
                        }
                    }
                }
                // PROCESS in each CASES
                if (EMPTY_ELEMENTS.contains("<" + tagName + ">")) { // case 1: is Empty Tag
                    // đảm bảo có "/>"
                    while (i < raw.length() && raw.charAt(i) != '>') {
                        tmp.add(raw.charAt(i));
                        i++;
                    }
                    if (raw.charAt(i - 1) != '/') {
                        tmp.add('/');
                    }
                    tmp.add(raw.charAt(i));
                    i++;
                } else if (tagName.startsWith("/") == false) { // case 2: is Opening Tag                    
                    stack.push(tagName);
                    // put into map
                    if (map.get(tagName) == null) {
                        map.put(tagName, 1);
                    } else {
                        int count = map.get(tagName);
                        count++;
                        map.put(tagName, count);
                    }
                    tmp.add(ch);
                    i++;
                } else { // case 3: is Ending Tag. </someTag>
                    tagName = tagName.substring(1);
                    if (stack.isEmpty() == false && stack.peek().equals(tagName)) {
                        // case 3.1: Ending Tag matches an Opening Tag
                        String t = stack.pop();
                        (new MyMethodUtils()).getOutOfMap(map, t);
                        tmp.add(ch);
                        i++;
                    } else if (stack.isEmpty() == false && map.get(tagName) != null) {
                        // case 3.2: mở mà chưa đóng --> đóng giùm
                        while (stack.isEmpty() == false && stack.peek().equals(tagName) == false) { // đóng giùm những thẻ chưa đóng, pop khỏi stack
                            String t = stack.pop();
                            (new MyMethodUtils()).getOutOfMap(map, t);
                            addToLinkedList(tmp, "</" + t + ">");
                        }
                        String t = stack.pop();
                        (new MyMethodUtils()).getOutOfMap(map, t);
                        tmp.add(ch);
                        i++;
                    } else {
                        // case 3.3: chưa mở mà đóng --> bỏ qua ending tag đó
                        while (i < raw.length() && raw.charAt(i - 1) != '>') {
                            i++;
                        }
                    }
                }
                // end: PROCESS in each CASES
            } // end if meet < 
            else {
                tmp.add(ch);
                i++;
            }
        }
        String result = convertLinkedListToString(tmp);
        return result;
    }

    // <img src="a.jpg"alt="good"/> ---> <img src="a.jpg" alt="good"/>
    private static String addSpaceBetweenAttrs(String raw) {
        boolean foundStartElement = false;
        boolean foundQuote = false;
        char quoteMark = ' ';
        LinkedList<Character> tmp = new LinkedList<>();
        for (int i = 0; i < raw.length(); i++) {
            char ch = raw.charAt(i);
            tmp.add(ch);
            if (ch == '<') {
                foundStartElement = true;
            } else if (foundStartElement && ch == '>') {
                foundStartElement = false;
            } else if (foundStartElement && foundQuote == false && (ch == '\"' || ch == '\'')) {
                foundQuote = true;
                quoteMark = ch;
            } else if (foundQuote && ch == quoteMark) { // found UNQUOTE
                if (i + 1 < raw.length() && raw.charAt(i + 1) != ' ') {
                    tmp.add(' ');
                }
                foundQuote = false;
            }
        }
        String result = convertLinkedListToString(tmp);
        return result;
    }

    // <img src=a.jpg /> ---> <img src="a.jpg" />
    private static String addQuoteToAttrs(String raw) {
        boolean foundStartElement = false;
        boolean inAttrValue = false;
        boolean rememberToUnquote = false;
        LinkedList<Character> tmp = new LinkedList<>();
        for (int i = 0; i < raw.length(); i++) {
            char ch = raw.charAt(i);
            if (rememberToUnquote && ch == '\'') {
                tmp.add('"');
                rememberToUnquote = false;
                inAttrValue = false;
                continue;
            } else if (rememberToUnquote && (ch == ' ' || ch == '/' || ch == '>')) {
                tmp.add('"');
                rememberToUnquote = false;
                inAttrValue = false;
            }
            tmp.add(ch);
            if (ch == '<') {
                foundStartElement = true;
            } else if (foundStartElement && ch == '>') {
                foundStartElement = false;
            } else if (foundStartElement && inAttrValue == false && ch == '=') { // found an attribute value
                if (i + 1 < raw.length() && raw.charAt(i + 1) != '\"' && raw.charAt(i + 1) != '\'') {
                    System.out.println(i);
                    tmp.add('"');
                    rememberToUnquote = true;
                }
                inAttrValue = true;
            } else if (rememberToUnquote && ch == '"') {
                rememberToUnquote = false;
                inAttrValue = false;
            }
        }
        String result = convertLinkedListToString(tmp);
        return result;
    }

    // <img width="120" width="120" /> ---> <img width="120" />
    private static String removeDuplicatedAttrKey(String raw) {
        boolean inStartElement = false;
        boolean inAttrValue = false;
        char quoteMark = ' ';
        LinkedList<Character> tmp = new LinkedList<>();
        Set<String> keySet = null;
        int i = 0;
        while (i<raw.length()) {
            char ch = raw.charAt(i);
            if (inStartElement && inAttrValue == false && (ch == '"' || ch == '\'')) {
                inAttrValue = true;
                quoteMark = ch;
            } else if (inAttrValue && ch == quoteMark) {
                inAttrValue = false;
            } else if (inStartElement && inAttrValue == false
                    && Character.isLetter(ch) && raw.charAt(i - 1) == ' ') {
                // found attr key
                String attrKey = "";
                int j = i;
                while (j < raw.length() && raw.charAt(j)!='=') {
                    attrKey += raw.charAt(j);
                    j++;
                }
                if (keySet.contains(attrKey)) {
                    // skip
                    char quote = raw.charAt(j+1);
                    i=j+2;
                    while(i<raw.length()&& raw.charAt(i)!=quote) {
                        i++;
                    }
                    i++;
                    continue;
                } else {
                    keySet.add(attrKey);
                }
            } else if (ch == '<') {
                inStartElement = true;
                keySet = new HashSet<>();
            } else if (ch == '>') {
                inStartElement = false;
                keySet = null;
            }
            tmp.add(ch);
            i++;
        }//end while
        String result = convertLinkedListToString(tmp);
        return result;
    }

}
