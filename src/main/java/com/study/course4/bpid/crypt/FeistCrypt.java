package com.study.course4.bpid.crypt;


public class FeistCrypt {

    public static void encode(StringBuilder stringToEncode){
        while(stringToEncode.length() % 16 != 0){
            stringToEncode.append('_');
        }
        System.out.println(stringToEncode);
    }

    public static void main(String[] args) {
        encode(new StringBuilder(""));
    }
}
