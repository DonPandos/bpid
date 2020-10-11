package com.study.course4.bpid;

import com.study.course4.bpid.crypt.Base64Crypt;
import com.study.course4.bpid.crypt.FeistCrypt;
import javafx.util.Pair;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class Tests {
    public static void main(String[] args) throws UnsupportedEncodingException {
        String toEncode = new String("Русский".getBytes("UTF-8"));
        System.out.println(toEncode.length());
        Pair<String, List<String>> pair = FeistCrypt.encode(new StringBuilder(toEncode), 8);
        System.out.println("encoded: " + pair.getKey());
        String decodedString = FeistCrypt.decode(pair.getKey(), pair.getValue());
        System.out.println(decodedString);
//        String toEncode = "StringTOsdasdasdEncode";
//        String encodedString = Base64Crypt.base64Encode(toEncode.getBytes());
//        System.out.println("encodedStirng : " + encodedString);
//        String decodedString = new String(Base64Crypt.base64Decode(encodedString));
//        System.out.println("decodedString : " + decodedString);
//

//        String encoded = Base64Crypt.encode("QWERTYUIOP{}ASDFGHJKL:|ZXCVBNM<>?qwertyuiop[]asdfghjkl;zxcvbnm,./йцукенгшщзхъфывапролджэячсмитьбю.ЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮ,", "ASFSDF");
//        System.out.println(Base64Crypt.decode(encoded, "ASFSDF"));
    }
}
