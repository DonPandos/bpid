package com.study.course4.bpid.crypt;

import javafx.util.Pair;

import java.util.*;

public class PermutationCrypt {

    public static Pair<String, String> encode(String str){
        String key = "";
        String encodeString = "";
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
        Integer currIteration = iterationsCount;
        while (currIteration > 0){
            Integer columnCount;
            if(currIteration == 1){ // если последняя итерация, то берем остаток от деления
                columnCount = arrWidth % 10;
            } else {
                columnCount = 10;
            }
            List<Integer> randomList = new ArrayList<>();
            for(int i = 0; i < columnCount; i++){
                randomList.add(i);
            }
            Collections.shuffle(randomList); // генерация случайного ключа
            for(Integer num : randomList){
                int j = ((iterationsCount - currIteration) * 10) + num;
                System.out.println("j: " + j);
                for(int i = 0; i < 5; i++){
                    encodeString += arr[i][j];
                }
                key += num;
            }
            currIteration -= 1;
        }
        return new Pair<>(encodeString, key);
        //tests
    }

    public static void main(String[] args) {
        Pair<String, String> pair = encode("Hello worldHello world world!HellHello world world!o world world!Hello world world!Hello world world! world!");
        System.out.println(pair.getKey());
        System.out.println(pair.getValue());
    }
}
