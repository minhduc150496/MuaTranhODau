/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.utils;

import java.io.Serializable;
import java.text.Normalizer;
import java.util.regex.Pattern;

/**
 *
 * @author MinhDuc
 */
public class StringUtils implements Serializable {

    public static String toRawString(String content) {
        if (content == null) {
            return null;
        }

        String result = content.toLowerCase();
                
        result = result.replace("á", "a");
        result = result.replace("à", "a");
        result = result.replace("ả", "a");
        result = result.replace("ã", "a");
        result = result.replace("ạ", "a");
        
        result = result.replace("ă", "a");
        result = result.replace("ắ", "a");
        result = result.replace("ằ", "a");
        result = result.replace("ẳ", "a");
        result = result.replace("ẵ", "a");
        result = result.replace("ặ", "a");
        
        result = result.replace("â", "a");
        result = result.replace("ấ", "a");
        result = result.replace("ầ", "a");
        result = result.replace("ẩ", "a");
        result = result.replace("ẫ", "a");
        result = result.replace("ậ", "a");
        
        result = result.replace("đ", "d");
        
        result = result.replace("é", "e");
        result = result.replace("è", "e");
        result = result.replace("ẻ", "e");
        result = result.replace("ẽ", "e");
        result = result.replace("ẹ", "e");
                
        result = result.replace("ê", "e");
        result = result.replace("ế", "e");
        result = result.replace("ề", "e");
        result = result.replace("ể", "e");
        result = result.replace("ễ", "e");
        result = result.replace("ệ", "e");
                
        result = result.replace("í", "i");
        result = result.replace("ì", "i");
        result = result.replace("ỉ", "i");
        result = result.replace("ĩ", "i");
        result = result.replace("ị", "i");
        
        result = result.replace("ó", "o");
        result = result.replace("ò", "o");
        result = result.replace("ỏ", "o");
        result = result.replace("õ", "o");
        result = result.replace("ọ", "o");
        
        result = result.replace("ô", "o");
        result = result.replace("ố", "o");
        result = result.replace("ồ", "o");
        result = result.replace("ổ", "o");
        result = result.replace("ỗ", "o");
        result = result.replace("ộ", "o");
        
        result = result.replace("ơ", "o");
        result = result.replace("ớ", "o");
        result = result.replace("ờ", "o");
        result = result.replace("ở", "o");
        result = result.replace("ỡ", "o");
        result = result.replace("ợ", "o");
        
        result = result.replace("ú", "u");
        result = result.replace("ù", "u");
        result = result.replace("ủ", "u");
        result = result.replace("ũ", "u");
        result = result.replace("ụ", "u");
        
        result = result.replace("ư", "u");
        result = result.replace("ứ", "u");
        result = result.replace("ừ", "u");
        result = result.replace("ử", "u");
        result = result.replace("ữ", "u");
        result = result.replace("ự", "u");
                
        result = result.replace("ý", "y");
        result = result.replace("ỳ", "y");
        result = result.replace("ỷ", "y");
        result = result.replace("ỹ", "y");
        result = result.replace("ỵ", "y");
        
        return result;
    }
}
