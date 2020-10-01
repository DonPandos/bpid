package com.study.course4.bpid.crypt;


import javafx.util.Pair;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FeistCrypt {

    public static Pair<String, List<String>> encode(StringBuilder stringToEncode, Integer roundsCount){
        while(stringToEncode.length() % 16 != 0){
            stringToEncode.append(' ');
        } // заполняем строку пробелами, пока длина не станет кратной 16 байтам(128 битам)
        List<String> blocks = new ArrayList<>(); // создаем список блоков
        for(int i = 0; i < stringToEncode.length(); i+=16){
            blocks.add(stringToEncode.substring(i, i+16));
        } // заполняем блоки данными

        //генерация 16 байтовых (128 битовых) ключей для каждого раунда
        Random r = new Random();
        List<String> keys = new ArrayList<>(); // создаем пустой список ключей
        for(int i = 0; i < roundsCount; i++){ // для каждого раунда
            StringBuilder key = new StringBuilder(); // создаем пустой ключ
            for(int j = 0; j < 16; j++) { // генерируем 16 рандомных символов ключа
                int rand = r.nextInt(127) + 1;
                key.append((char)rand);
            }
            keys.add(key.toString()); // добавляем ключ в список
        }

        List<String> encodedBlocks = new ArrayList<>(); // список зашифрованных блоков
        List<Thread> threads = new ArrayList<>();
        for(String block : blocks) encodedBlocks.add("");
        for(int i = 0; i < blocks.size(); i++){ // для каждого блока
            final int pos = i;
            threads.add(new Thread(() -> { // создаем поток, который шифрует блок
                String block = blocks.get(pos); // получаем блок
                String L0 = block.substring(0, 8); // делим на правую и левую часть
                String R0 = block.substring(8, 16);
                for(String key : keys) { //для каждого ключа проводим раунд шифрования
                    String prevL0 = L0;
                    String L0xorK0 = Base64Crypt.encode(L0, key);
                    L0 = Base64Crypt.encode(L0xorK0, R0);
                    R0 = prevL0;
                }
                encodedBlocks.add(pos, L0);
            }));
            threads.get(pos).start(); // запускаем поток
        }

        for(Thread t : threads) { // ждем, пока все потоки закончат работу
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // генерируем зашифрованную строку
        StringBuilder resultString = new StringBuilder();
        for(String encodedBlock : encodedBlocks){
            resultString.append(encodedBlock + "\n");
        }
        System.out.println(resultString);
        return null;
    }

    public static void main(String[] args) {
        encode(new StringBuilder("Большая строка должна быть тут, но ее к сожалению нет. очень жаль, что так произошло, но жизнь такая, что поделать. Люблю фрипсы кстати, мой друг тоже их любит"), 8);
    }
}
