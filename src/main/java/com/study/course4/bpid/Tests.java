package com.study.course4.bpid;

import com.study.course4.bpid.crypt.Base64Crypt;
import com.study.course4.bpid.crypt.FeistCrypt;
import javafx.util.Pair;
import lombok.SneakyThrows;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class Tests {
    public static void main(String[] args) throws UnsupportedEncodingException {

      String toEncode = "Русский текст тут Here so many enРусский текст тут Here so many english text Here so many english textHere so many english textHere so many english textHere so many english textHere so many english textHere so many english textHere so many english textHere so many e124564656786780!@#$%^&*()_+[]nglish textHere so many english textHere so many english textHere so many english textHere so many english text";
        Pair<String, List<String>> pair = FeistCrypt.encode(new StringBuilder(toEncode), 8);
        //System.out.println("encoded: " + pair.getKey());
        String decodedString = FeistCrypt.decode(pair.getKey(), pair.getValue());
        System.out.println(decodedString);
////        String toEncode = "Love your penis";
////        String encodedString = Base64Crypt.base64Encode(toEncode.getBytes());
////        System.out.println("encodedStirng : " + encodedString);
////        String key = "hello";
////        String encodedTwice = stringXor(encodedString.getBytes(), key.getBytes());
////        System.out.println("encodedTwice: " + encodedTwice);
////        String decodedFromTwice = stringXor(encodedTwice.getBytes(), key.getBytes());
////        System.out.println(decodedFromTwice);
////        String decodedString = new String(Base64Crypt.base64Decode(decodedFromTwice.getBytes()));
////        System.out.println("decodedString : " + decodedString);
//        String toEncode = "Строка для кодирdfgdfgd фваыва ыва ыва овани";
//        byte[] encodedString = encode(toEncode, "ключ");
//        String decodedString = decode(encodedString, "ключ");
//        System.out.println(decodedString);
//        String text = "hello man привет как дела";
//        String textbase = Base64.getEncoder().encodeToString(text.getBytes("UTF-8"));
//        System.out.println(textbase.length());
//        System.out.println(text.getBytes().length);
//        String koi = new String(text.getBytes("UTF-8"), ""); // string in koi8-
//        System.out.println(koi.getBytes().length);
//        System.out.println(koi);
//        String key = new String("русские".getBytes("UTF-8"), "KOI8-R");
//        System.out.println(key.length());
//        System.out.println(key);
//        String encoded = new String(encode(text, key), "KOI8-R");
//        System.out.println(encoded);
//        String decoded = new String(decode(encoded.getBytes(), key).getBytes("KOI8-R"));
//        System.out.println(decoded);


        String encoded = Base64Crypt.encode("QWERTYUIOP{}ASDFGHJKL:|ZXCVBNM<>?qwertyuiop[]asdfghjkl;zxcvbnm,./йцукенгшщзхъфывапролджэячсмитьбю.ЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮ,", "ASFSDF");
       // System.out.println(Base64Crypt.decode(encoded, "ASFSDF"));
    }

    public static String stringXor(byte[] str, byte[] key){
        byte[] out = new byte[str.length];
        for(int i = 0; i < str.length; i++){
            out[i] = (byte) (str[i] ^ key[i % key.length]);
        }
        return String.valueOf(out);
    }

    @SneakyThrows
    public static byte[] encode(String pText, String pKey) {
        byte[] txt = pText.getBytes();
        byte[] key = pKey.getBytes();
        byte[] res = new byte[txt.length];
        System.out.println(pText.length());
        System.out.println(txt.length);

        for (int i = 0; i < txt.length; i++) {
            res[i] = (byte) (txt[i] ^ key[i % key.length]);
        }

        return res;
    }

    @SneakyThrows
    public static String decode(byte[] pText, String pKey) {
        byte[] res = new byte[pText.length];
        byte[] key = pKey.getBytes();

        for (int i = 0; i < pText.length; i++) {
            res[i] = (byte) (pText[i] ^ key[i % key.length]);
        }

        return new String(res);
    }
}
