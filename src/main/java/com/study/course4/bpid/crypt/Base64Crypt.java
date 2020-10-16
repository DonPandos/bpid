package com.study.course4.bpid.crypt;

import lombok.SneakyThrows;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class Base64Crypt {

    public static String encode(String s, String key) {
       // return base64Encode(xorEncode(s, key));
        return base64Encode(xorWithKey(s.getBytes(), key.getBytes()));
    }

    public static String decode(String s, String key) {
        return new String(xorWithKey(base64Decode(s), key.getBytes()));
       // return new String(xorDecode(base64Decode(s), key));
    }

    public static byte[] xorWithKey(byte[] a, byte[] key) {
        byte[] out = new byte[a.length];
        for (int i = 0; i < a.length; i++) {
            out[i] = (byte) (a[i] ^ key[i % key.length]);
        }
        return out;

//        StringBuilder sb = new StringBuilder();
//        String s = String.valueOf(a);
//        String key = String.valueOf(secret);
//        for(int i = 0; i < s.length(); i++)
//            sb.append((char)(s.charAt(i) ^ key.charAt(i % key.length())));
//        String result = sb.toString();
//        return result.getBytes();
    }

    public static byte[] base64Decode(String s) {
        try {
            BASE64Decoder d = new BASE64Decoder();
            return d.decodeBuffer(s);
        } catch (IOException e) {throw new RuntimeException(e);}
       // return Base64.getDecoder().decode(s);
    }

    public static String base64Encode(byte[] bytes) {
        BASE64Encoder enc = new BASE64Encoder();
        return enc.encode(bytes).replaceAll("\\s", "");
//        return Base64.getEncoder().withoutPadding().encodeToString(bytes).replaceAll("\\s", "");
    }

    @SneakyThrows
    public static byte[] xorEncode(String pText, String pKey) {
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
    public static String xorDecode(byte[] pText, String pKey) {
        byte[] res = new byte[pText.length];
        byte[] key = pKey.getBytes();

        for (int i = 0; i < pText.length; i++) {
            res[i] = (byte) (pText[i] ^ key[i % key.length]);
        }

        return new String(res);
    }

//    public static String encode(String s, String key) {
//        try {
//            return base64Encode(xorWithKey(s.getBytes("UTF-8"), key.getBytes("UTF-8")));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public static String decode(String s, String key) {
//        return new String(xorWithKey(base64Decode(s), key.getBytes("UTF-8")));
//    }
//
//    public static byte[] xorWithKey(byte[] a, byte[] key) {
//        byte[] out = new byte[a.length];
//        for (int i = 0; i < a.length; i++) {
//            out[i] = (byte) (a[i] ^ key[i%key.length]);
//        }
//        return out;
//    }
//
//    public static byte[] base64Decode(String s) {
//        try {
//            BASE64Decoder d = new BASE64Decoder();
//            return d.decodeBuffer(s);
//        } catch (IOException e) {throw new RuntimeException(e);}
//    }
//
//    public static String base64Encode(byte[] bytes) {
//        BASE64Encoder enc = new BASE64Encoder();
//        return enc.encode(bytes).replaceAll("\\s", "");
//
//    }
}
