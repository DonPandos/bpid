package com.study.course4.bpid.crypt;

import javafx.util.Pair;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

public class PermutationCrypt {

    public static Pair<String, String> encode(String str){
        String key;
        String encodeString;
        Integer arrWidth = (int)Math.ceil((double)str.length()/5);
        Character[][] arr = new Character[5][arrWidth]; // инициализируем массив высотой 5, длиной, которой хватит для вмещения символов
        int counter = 0;
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < arrWidth; j++){
                System.out.println(i + ", " + j);
                try {
                    arr[i][j] = str.charAt(counter) != ' ' ? str.charAt(counter) : '_';
                } catch (StringIndexOutOfBoundsException e){
                    arr[i][j] = '_';
                } finally {
                    counter++;
                }
            }
        } // заполнение массива строкой

        Integer iterationsCount = (int)Math.ceil((double)arrWidth / 10); // количество итераций


        //tests
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < arrWidth; j++){
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
        //
        //return new Pair<>(encodeString, key);
        return null;
    }

    public static void main(String[] args) {
        encode("Hello worldHello world world!HellHello world world!o world world!Hello world world!Hello world world! world!");

    }
}
