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
        for(int i = 0; i < arrWidth; i++){
            for(int j = 0; j < 5; j++){
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
        String key = keyValue.getKey();
        String value = keyValue.getValue();
        Character[][] arr = new Character[key.length()][5];
        int iterator = 0;
        int keyIterations = (int)Math.ceil((double)key.length() / 10);
        System.out.println("keyIt: " + keyIterations);
        while(iterator < keyIterations){
            Integer currKey = Integer.parseInt(String.valueOf(key.charAt(0)));
            for(int j = 0; j < 5; j++){
                System.out.println("val: " + (iterator * 50) + (currKey * 5) + j);
                arr[currKey][j] = value.charAt((iterator * 50) + (currKey + 5) + j);
            }
            iterator++;
        }
//        System.out.println(Arrays.deepToString(arr));
        String result = "";
        for(int j = 0; j < key.length(); j++){
            for(int i = 0; i < 5; i++){
//                System.out.println(String.valueOf(arr[i][j]));
                result += String.valueOf(arr[i][j]);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Pair<String, String> pair = encode("Фантастический американский сериал, снятый по мотивам комиксов Marvel. Агенты организации Щ.И.Т. снова объединяются, спасая Землю от глобальных угроз с помощью невероятных способностей. " +
                "Действие в новом сериале происходит после событий в блокбастере «Мстители». Выживший агент Фил Колсон собирает группу специалистов для действия в рамках засекреченной организации Щ.И.Т., специализирующейся только на самых сложных, необъяснимых и загадочных происшествиях.");
        System.out.println(pair.getKey());
        System.out.println(pair.getValue());
        System.out.println(decode(new Pair<>("12786305942071645983410369852797815236048217594630380421769568923041754173568290974365812001", "стический__снятый_пориал,америФантаий_се_мотикансков_Maвам_кации_омиксганиз_Агенты_ор._сноЩ.И.Тrvel.сая_Зъединва_об,_спаот_глгроз_ных_уемлю_яютсяобальв_нов_Действие_ощью_собноневероятныстей.с_помх_споокбас_проириале_в_блле_сотере_т_посбытийсходиом_сеившийбирае«Мсти_аген._Выжтели»он_со_Колст_грут_Филв_рамасекреченнлистов_дляппу_с_дейспециаках_зтвия_.,_спганизющейсЩ.И.Тециализируя_толации_ько_ной_ороисшеадочнбъясн,_неои_загимых_ых_прых_сложныха_самствиях.___")));
    }
}
