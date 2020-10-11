package com.study.course4.bpid;

import com.study.course4.bpid.crypt.Base64Crypt;
import com.study.course4.bpid.crypt.FeistCrypt;
import javafx.util.Pair;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class Tests {
    public static void main(String[] args) throws UnsupportedEncodingException {
        String toEncode = "12. Шаньгин";
        Pair<String, List<String>> pair = FeistCrypt.encode(new StringBuilder(toEncode), 8);
        String decodedString = FeistCrypt.decode(pair.getKey(), pair.getValue());
        System.out.println(decodedString.getBytes("UTF-8"));

//        String encoded = Base64Crypt.encode("QWERTYUIOP{}ASDFGHJKL:|ZXCVBNM<>?qwertyuiop[]asdfghjkl;zxcvbnm,./йцукенгшщзхъфывапролджэячсмитьбю.ЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮ,", "ASFSDF");
//        System.out.println(Base64Crypt.decode(encoded, "ASFSDF"));
    }
}
