package com.study.course4.bpid.crypt;

import javafx.util.Pair;

import java.util.*;

public class PermutationCrypt {

    public static Pair<String, String> encode(String str){
        String key = "";
        String encodeString = "";
        Integer arrWidth = (int)Math.ceil((double)str.length()/5);
        Character[][] arr = new Character[arrWidth][5]; // инициализируем массив высотой 5, длиной, которой хватит для вмещения символов
        int counter = 0;
        for(int j = 0; j < 5; j++){
          for(int i = 0; i < arrWidth; i++){
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

        System.out.println(Arrays.deepToString(arr));

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
                int i = ((iterationsCount - currIteration) * 10) + num;
                System.out.println("i: " + i);
                for(int j = 0; j < 5; j++){
                    encodeString += arr[i][j];
                }
                key += num;
            }
            currIteration -= 1;
        }
        return new Pair<>(key, encodeString);
        //tests
    }

    public static String decode(Pair<String, String> keyValue){
        String result = "";
        String key = keyValue.getKey(); // ключ
        String encodeString = keyValue.getValue(); // зашифрованная фраза
        Character[][] arr = new Character[key.length()][5]; // массив для расшифровки
        Integer iterationsCount = (int) Math.ceil((double)key.length() / 10); // количество итерации ключа
        System.out.println(iterationsCount);
        Integer currentIteration = 0; // текущая итерация ключа

        while(currentIteration < iterationsCount){ // пока текущая итерация не последняя
            Integer currentIterationKeyLength = key.length() > 9 ? 10 : key.length(); // количество столбцов в текущей итерации ключа(всегда 10, кроме последней итерации)
            for(int i = 0; i < currentIterationKeyLength; i++){
                Integer currentKey = Integer.parseInt(String.valueOf(key.charAt(0))); // текущий ключ в данной итерации
                Integer currentColumn = (currentIteration * 10) + currentKey; // номер столбца
                key = key.substring(1, key.length());
                for(int j = 0; j < 5; j++){
                    arr[currentColumn][j] = encodeString.charAt(0);
                    encodeString = encodeString.substring(1, encodeString.length());
                }
            }
            currentIteration++;
        }
        for(int j = 0; j < 5; j++){
            for(int i = 0; i < arr.length; i++){
                result += String.valueOf(arr[i][j]).equals("_") ? " " : String.valueOf(arr[i][j]);
            }
        }
        return result;
    }

}
