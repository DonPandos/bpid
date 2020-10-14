package com.study.course4.bpid;

import com.study.course4.bpid.crypt.Base64Crypt;
import com.study.course4.bpid.crypt.FeistCrypt;
import javafx.util.Pair;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class Tests {
    public static void main(String[] args) throws UnsupportedEncodingException {
//        String toEncode = "Hello, how are u?";
//        System.out.println(toEncode.length());
//        Pair<String, List<String>> pair = FeistCrypt.encode(new StringBuilder(toEncode), 8);
//        System.out.println("encoded: " + pair.getKey());
//        String decodedString = FeistCrypt.decode(pair.getKey(), pair.getValue());
//        System.out.println(decodedString);
        String toEncode = "Love your penis";
        String encodedString = Base64Crypt.base64Encode(toEncode.getBytes());
        System.out.println("encodedStirng : " + encodedString);
        String key = "hello";
        String encodedTwice = stringXor(encodedString.getBytes(), key.getBytes());
        System.out.println("encodedTwice: " + encodedTwice);
        String decodedFromTwice = stringXor(encodedTwice.getBytes(), key.getBytes());
        System.out.println(decodedFromTwice);
        String decodedString = new String(Base64Crypt.base64Decode(decodedFromTwice.getBytes()));
        System.out.println("decodedString : " + decodedString);


//        String encoded = Base64Crypt.encode("QWERTYUIOP{}ASDFGHJKL:|ZXCVBNM<>?qwertyuiop[]asdfghjkl;zxcvbnm,./йцукенгшщзхъфывапролджэячсмитьбю.ЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮ,", "ASFSDF");
//        System.out.println(Base64Crypt.decode(encoded, "ASFSDF"));
    }

    public static String stringXor(byte[] str, byte[] key){
        byte[] out = new byte[str.length];
        for(int i = 0; i < str.length; i++){
            out[i] = (byte) (str[i] ^ key[i % key.length]);
        }
        return String.valueOf(out);
    }
}
