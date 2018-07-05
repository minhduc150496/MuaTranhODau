/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 *
 * @author MinhDuc
 */
public class HTMLPreprocessor implements Serializable {

    private static final String EMPTY_ELEMENTS = "<area> <base> <br> <col> <hr> <img> <input> <link> <meta> <param> <command> <keygen> <source>"; // HTML 5 

    public static String wellForm(String raw) {
        System.out.println("WELL-FORMING BEGIN...");

        String result = raw;

        result = removeShitTagsAndComments(result);
        System.out.println("remove shit tags done");

        result = closeTags(result);
        System.out.println("close tags done");

        result = addMissingTags(result);
        System.out.println("add missing tags done");

        result = addSpaceBetweenAttrs(result);
        System.out.println("add space between attrs done");

        result = addQuoteToAttrs(result);
        System.out.println("add quote to attrs done");

        result = correctAmps(result);
        System.out.println("correct &amps; done");

        System.out.println("WELL-FORMING DONE.");
        return result;
    }

    // <body ---> <body>
    private static String closeTags(String raw) {
        int i = 0;
        boolean foundTag = false;
        String result = "";
        while (i < raw.length()) {
            char ch = raw.charAt(i);
            if (ch == '<') {
                if (foundTag) {
                    result += ">";
                } else {
                    foundTag = true;
                }
            }
            if (ch == '>') {
                foundTag = false;
            }
            result += ch;
            i++;
        }
        return result;
    }

    private static String removeShitTagsAndComments(String raw) {
        String result = "";
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
                result += raw.charAt(i);
            }
            i++;
        } // end while
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
        String result = "";
        int k = 0;
        for (int i = 0; i < raw.length(); i++) {
            result += raw.charAt(i);
            if (k < incorrectPos.size()) {
                int pos = incorrectPos.get(k);
                if (i == pos) {
                    result += "amp;";
                    k++;
                }
            }
        }
        return result;
    }

    // <img> ---> <img />
    private static String addMissingTags_Old(String raw) {
        Stack<String> stackTagName = new Stack<>();
        Stack<Integer> stackPos = new Stack<>();
        List<Integer> unclosedTagPos = new ArrayList<>(); // position of > of that tag

        // Step 1: Find unclosed tags
        int i = 0;
        while (i < raw.length()) {
            if (raw.charAt(i) == '<') {// element detected
                int tagPos = i;
                // get Element name
                i++;
                String tagName = "";
                // get tagName
                while (i < raw.length() && raw.charAt(i) != ' ' && raw.charAt(i) != '>') {
                    tagName += raw.charAt(i);
                    i++;
                }
                i++;
                // process Element
                if (Character.isLetter(tagName.charAt(0))) { // is Start Element                
                    stackTagName.push(tagName);
                    stackPos.push(new Integer(tagPos));
                } else if (tagName.startsWith("/")) { // is End Element

                    tagName = tagName.substring(1);
                    while (stackTagName.isEmpty() == false && stackTagName.peek().equals(tagName) == false) {
                        int pos = stackPos.pop();
                        String tag = stackTagName.pop();
                        unclosedTagPos.add(new Integer(pos));
                    }
                    if (stackTagName.isEmpty() == false) {
                        stackPos.pop();
                        stackTagName.pop();
                    }
                }
            } else {
                i++;
            }// end if charAt(i) == '<'
        } // end while i
        while (stackTagName.isEmpty() == false) {
            int pos = stackPos.pop();
            stackTagName.pop();
            unclosedTagPos.add(new Integer(pos));
        }

        unclosedTagPos.sort((o1, o2) -> {
            return (Integer) o1 - (Integer) o2; //To change body of generated lambdas, choose Tools | Templates.
        });
//        for (int j = 0; j < unclosedTagPos.size(); j++) {
//            int p = unclosedTagPos.get(j);
//            System.out.println("unclo: " + p + " - " + raw.substring(p,p+40));
//        }

        // Step 2: Add "/>" 
        String result = "";
        boolean found = false;
        int iPos = 0;
        for (i = 0; i < raw.length(); i++) {
            if (iPos < unclosedTagPos.size() && unclosedTagPos.get(iPos).equals(new Integer(i))) {
                iPos++;
                found = true;
            }
            if (found && raw.charAt(i) == '>') {
                if (raw.charAt(i - 1) != '/') {
                    result += '/';
                }
                found = false;
            }
            result += raw.charAt(i);
        }

        return result;
    }

    // <img> --> <img/>     
    // <html><body></html> --> <html><body></body></html>
    // <html></body></html> --> <html></html>
    private static String addMissingTags(String raw) {
        Stack<String> stack = new Stack<>(); // stack of tag names
        Map<String, Integer> map = new HashMap<>(); // TagName - Number of occurance
        String result = "";
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
                        if (map.get(tagName)==null) {
                            return;
                        }
                        int count = map.get(tagName);
                        count--;
                        if (count==0) {
                            map.remove(tagName);
                        } else {
                            map.put(tagName, count);
                        }
                    }
                }
                // PROCESS in each CASES
                if (EMPTY_ELEMENTS.contains("<" + tagName + ">")) { // case 1: is Empty Tag
                    // đảm bảo có "/>"
                    boolean foundSlash = false; // slash is /
                    while (i < raw.length() && raw.charAt(i) != '>') {
                        if (raw.charAt(i) == '/') {
                            foundSlash = true;
                        }
                        result += raw.charAt(i);
                        i++;
                    }
                    if (foundSlash == false) {
                        result += '/';
                    }
                    result += raw.charAt(i);
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
                    result += ch;
                    i++;
                } else { // case 3: is Ending Tag. </someTag>
                    tagName = tagName.substring(1);
                    if (stack.isEmpty() == false && stack.peek().equals(tagName)) {
                        // case 3.1: Ending Tag matches an Opening Tag
                        String t = stack.pop();
                        (new MyMethodUtils()).getOutOfMap(map, t);
                        result += ch;
                        i++;
                    } else if (stack.isEmpty() == false && map.get(tagName) != null) {
                        // case 3.2: mở mà chưa đóng --> đóng giùm
                        while (stack.isEmpty() == false && stack.peek().equals(tagName) == false) { // đóng giùm những thẻ chưa đóng, pop khỏi stack
                            String t = stack.pop();
                            (new MyMethodUtils()).getOutOfMap(map, t);
                            result += "</" + t + ">";
                        }
                        String t = stack.pop();
                        (new MyMethodUtils()).getOutOfMap(map, t);
                        result += ch;
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
                result += ch;
                i++;
            }
        }
        return result;
    }

    // <img src="a.jpg"alt="good"/> ---> <img src="a.jpg" alt="good"/>
    private static String addSpaceBetweenAttrs(String raw) {
        boolean foundStartElement = false;
        boolean foundQuote = false;
        char quoteMark = ' ';
        String result = "";
        for (int i = 0; i < raw.length(); i++) {
            char ch = raw.charAt(i);
            result += ch;
            if (ch == '<') {
                foundStartElement = true;
            } else if (foundStartElement && ch == '>') {
                foundStartElement = false;
            } else if (foundStartElement && foundQuote == false && (ch == '\"' || ch == '\'')) {
                foundQuote = true;
                quoteMark = ch;
            } else if (foundQuote && ch == quoteMark) { // found UNQUOTE
                if (i + 1 < raw.length() && raw.charAt(i + 1) != ' ') {
                    result += " ";
                }
                foundQuote = false;
            }
        }
        return result;
    }

    // <img src=a.jpg /> ---> <img src="a.jpg" />
    private static String addQuoteToAttrs(String raw) {
        boolean foundStartElement = false;
        boolean foundAttrValue = false;
        boolean rememberToUnquote = false;
        String result = "";
        for (int i = 0; i < raw.length(); i++) {
            char ch = raw.charAt(i);
            if (rememberToUnquote && ch == '\'') {
                result += '"';
                rememberToUnquote = false;
                foundAttrValue = false;
                continue;
            } else if (rememberToUnquote && (ch == ' ' || ch == '/' || ch == '>')) {
                result += '"';
                rememberToUnquote = false;
                foundAttrValue = false;
            }
            result += ch;
            if (ch == '<') {
                foundStartElement = true;
            } else if (foundStartElement && ch == '>') {
                foundStartElement = false;
            } else if (foundStartElement && foundAttrValue == false && ch == '=') { // found an attribute value
                if (i + 1 < raw.length() && raw.charAt(i + 1) != '\"' && raw.charAt(i + 1) != '\'') {
                    System.out.println(i);
                    result += '"';
                    rememberToUnquote = true;
                }
                foundAttrValue = true;
            } else if (rememberToUnquote && ch == '"') {
                rememberToUnquote = false;
                foundAttrValue = false;
            }
        }
        return result;
    }

    private static String removeComments(String raw) {
        return null;
    }

}
