/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author MinhDuc
 */
public class XMLPreProcessor implements Serializable {

    public static String wellForm(String raw) {
        System.out.println("well form");
        String result = closeTags(raw);
        System.out.println("close tags done");
        result = correctAmps(result);
        System.out.println("correct amps done");
        result = removeShitTags(result);
        System.out.println("remove done");
        return result;
    }

    private static String removeShitTags(String raw) {
//        System.out.println("raw: " + raw);
        String result = "";
        boolean skip = false;
        int i = 0;
        while (i < raw.length()) {
            if (raw.charAt(i) == '<') {
                int j = i + 1;
                String tagName = "";
                while (j < raw.length() && (Character.isLetterOrDigit(raw.charAt(j)) || raw.charAt(j) == '/')) {
                    tagName += raw.charAt(j);
                    j++;
                }
                if (tagName.equals("style") || tagName.equals("script") || tagName.equals("meta")) {
                    skip = true;
                } else if (tagName.equals("/style") || tagName.equals("/script") || tagName.equals("/meta")) {
                    skip = false;
                    while(raw.charAt(i)!='>') {
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
        }
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

    private static String closeTags(String raw) { // đóng thẻ
        Stack<String> stackTagName = new Stack<>();
        Stack<Integer> stackPos = new Stack<>();
        List<Integer> unclosedTagPos = new ArrayList<>();

        // Step 1: Find unclosed tags
        int i = 0;
        while (i < raw.length()) {
            if (raw.charAt(i) == '<') {// element detected
                // get Element name
                i++;
                String tagName = "";
                while (i < raw.length() && raw.charAt(i) != ' ' && raw.charAt(i) != '>') {
                    tagName += raw.charAt(i);
                    i++;
                }
                // process Element
                if (tagName.startsWith("/") == false) { // is Start Element                
                    stackTagName.push(tagName);
                    stackPos.push(new Integer(i));
                } else { // is End Element
                    tagName = tagName.substring(1);
                    while (stackTagName.isEmpty() == false && stackTagName.peek().equals(tagName) == false) {
                        int pos = stackPos.pop();
                        stackTagName.pop();
                        unclosedTagPos.add(new Integer(pos));
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

        // Step 2: Add "/>" 
        String result = "";
        int j = 0;
        boolean found = false;
        for (int k = 0; k < raw.length(); k++) {
            if (j < unclosedTagPos.size() && unclosedTagPos.get(j).equals(new Integer(k))) {
                found = true;
            }
            if (found && raw.charAt(k) == '>') {
                result += '/';
                found = false;
            }
            result += raw.charAt(k);
        }

        return result;
    }
    
    private static String correctAttrs(String raw) {
        return null;
    }
    
    private static String removeComments(String raw) {
        return null;
    }

}
