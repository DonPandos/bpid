package com.study.course4.bpid.crypt;


import javafx.util.Pair;
import lombok.SneakyThrows;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FeistCrypt {

    @SneakyThrows
    public static Pair<String, List<String>> encode(StringBuilder stringToEncode, Integer roundsCount){
        //stringToEncode = new StringBuilder(new String(stringToEncode.toString().getBytes(), "UTF-16"));
        while(stringToEncode.length() % 16 != 0){
            stringToEncode.append('_');
        } // заполняем строку пробелами, пока длина не станет кратной 16 байтам(128 битам)
        //System.out.println("ss: " + stringToEncode);
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
                int rand = r.nextInt(26) +  65;
                key.append((char)rand);
            }
            keys.add(key.toString()); // добавляем ключ в список
        }

        //List<String> encodedBlocks = new ArrayList<>(); // список зашифрованных блоков
        String[] encodedBlocks = new String[blocks.size()];
        List<Thread> threads = new ArrayList<>();
        for(int i = 0; i < blocks.size(); i++){ // для каждого блока
            final int pos = i;
            threads.add(new Thread(() -> { // создаем поток, который шифрует блок
                String block = "";
                try {
                    block = new String(blocks.get(pos).getBytes("UTF-8")); // получаем блок
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                System.out.println(pos + ": " + block);
                String x1 = block.substring(0, 4);
                String x2 = block.substring(4, 8);
                String x3 = block.substring(8, 12);
                String x4 = block.substring(12, 16);
                for(String key : keys) { //для каждого ключа проводим раунд шифрования
                    String prevX1 = x1;
                    String x1XorK0 = Base64Crypt.encode(x1, key);
                    x1 =  Base64Crypt.encode(x2, x1XorK0);
                    x2 = x3;
                    x3 = x4;
                    x4 = prevX1;
                }
                encodedBlocks[pos] = (x1 + x2 + x3 + x4);
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
            resultString.append(encodedBlock + ",");
        }
        return new Pair<>(resultString.toString(), keys);
    }

    public static String decode(String encodedString, List<String> keys){
        String[] encodedBlocks = encodedString.split(","); // разбиваем строку на блоки
        String[] decodedBlocks = new String[encodedBlocks.length]; // создаем пустой массив для расшифрованных блоков
        List<Thread> threads = new ArrayList<>();

        for(int i = 0; i < encodedBlocks.length; i++){ // foreach block
            final int pos = i;
            threads.add(new Thread(() -> {
                String block = encodedBlocks[pos];
                System.out.println(pos + "(enc): " + block);
                String x1 = block.substring(0, block.length()/4);
                String x2 = block.substring(block.length()/4, block.length()/2);
                String x3 = block.substring(block.length()/2, (block.length() / 4) + (block.length() / 2));
                String x4 = block.substring((block.length() / 4) + (block.length() / 2), block.length());
                for(int j = keys.size() - 1; j >= 0; j--){
                    String prevX1 = x1;
                    x1 = x4;
                    x4 = x3;
                    x3 = x2;
                    String x1XorK0 = Base64Crypt.encode(x1, keys.get(j));
                    x2 = Base64Crypt.decode(prevX1, x1XorK0);
                }
                decodedBlocks[pos] = (x1 + x2 + x3 + x4);
            }));
            threads.get(pos).start();
        }
        for(Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        String result = "";
        for(String s : decodedBlocks) {
            result += s;
        }
        return result.replaceAll("_", " ");
    }

}
