package com.study.course4.bpid;

import com.study.course4.bpid.crypt.DESCrypt;
import com.study.course4.bpid.crypt.RSACrypt;
import lombok.SneakyThrows;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Scanner;

public class Lab4Main {

    static Scanner scn = new Scanner(System.in);

    public static void main(String[] args) {
        Security.addProvider(new BouncyCastleProvider());
        int key;
        do{
            System.out.println("Выберите один из предложенных вариантов: ");
            System.out.println("1 - Зашифровать файл");
            System.out.println("2 - Расшифровать файл");
            System.out.println("3 - Создать электронную подпись");
            System.out.println("4 - Проверить электронную подпись");
            System.out.println("0 - Выход");
            key = Integer.parseInt(scn.nextLine());
            switch (key) {
                case 1:
                    encodeMenu();
                    break;
                case 2:
                    decodeMenu();
                    break;
                case 3:
                    createSignMenu();
                    break;
                case 4:
                    checkSignMenu();
                    break;
                case 0:

                    break;
                default:
                    System.out.println("Данной опции в списке нет.");
                    break;
            }
        } while(key != 0);
    }

    @SneakyThrows
    public static void encodeMenu(){
        System.out.println("Введите путь к файлу, который хотите зашифровать: ");

        String filePath = "src/main/java/com/study/course4/bpid/lab4files/" + scn.nextLine(); // получаем путь к файлу
        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1, filePath.length());
        String content = new String(Files.readAllBytes(Paths.get(filePath))); // получаем содержиммое файла

         // генерируем ключ для DES
        DESCrypt desCrypt = new DESCrypt(key); // создаем объект DES шифрования

        String ecnrypted = desCrypt.encrypt(content); // шифруем содержимое файла

        createFileWithData("src/main/java/com/study/course4/bpid/lab4files/decfile/dec" + fileName, ecnrypted); // записываем в файл зашифрованные данные

        KeyPair keyPair = KeyPairGenerator.getInstance("RSA").generateKeyPair(); // генерируем pub и priv ключи для RSA

        createFileWithData("src/main/java/com/study/course4/bpid/lab4files/rsapub/pub" + fileName,
                Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded()));
        createFileWithData("src/main/java/com/study/course4/bpid/lab4files/rsapriv/priv" + fileName,
                Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded())); // cоздаем файлы pub и priv ключей

        String encryptedDesKey = RSACrypt.encrypt(Base64.getEncoder().encodeToString(keyPair.getPrivate()), keyPair.getPublic()); // шифруем DES ключ
        createFileWithData("src/main/java/com/study/course4/bpid/lab4files/deskey/des" + fileName, encryptedDesKey); // создаем файл с DES ключом
    }

    @SneakyThrows
    public static void decodeMenu(){
        System.out.println("Введите путь к файлу, который хотите расшифровать: ");
        String filePath = "src/main/java/com/study/course4/bpid/lab4files/decfile/" + scn.nextLine(); // получаем путь к файлу

        String fileName = filePath.substring(filePath.lastIndexOf("/") + 4, filePath.length());
        String content = new String(Files.readAllBytes(Paths.get(filePath)));

        String encryptedDesKey = new String(Files.readAllBytes(Paths.get("src/main/java/com/study/course4/bpid/lab4files/deskey/des" + fileName)));
        String privateRsaKey = new String(Files.readAllBytes(Paths.get("src/main/java/com/study/course4/bpid/lab4files/rsapriv/priv" + fileName)));

        KeyFactory rsaKf = KeyFactory.getInstance("RSA"); // or "EC" or whatever
        PrivateKey privateKey = rsaKf.generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateRsaKey)));

        String desKeyString = RSACrypt.decrypt(encryptedDesKey, privateKey);
        byte[] decodedDesKey = Base64.getDecoder().decode(desKeyString);

        SecretKey originalKey = new SecretKeySpec(decodedDesKey, 0, decodedDesKey.length, "DES");

        DESCrypt desCrypt = new DESCrypt(originalKey);

        String decryptedContent = desCrypt.decrypt(content);
        createFileWithData("src/main/java/com/study/course4/bpid/lab4files/encfile/enc" + fileName, decryptedContent);
    }

    @SneakyThrows
    public static void createSignMenu() {
        System.out.println("Введите путь к файлу, который хотите подписать: ");
        String filePath = "src/main/java/com/study/course4/bpid/lab4files/" + scn.nextLine(); // получаем путь к файлу

        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1, filePath.length());
        String content = new String(Files.readAllBytes(Paths.get(filePath))); // получаем содержиммое файла

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(content.getBytes(StandardCharsets.UTF_8));
        String sha256hex = new String(Hex.encode(hash));

        Signature signature = Signature.getInstance ("SHA256withDSA");

        SecureRandom secureRandom = new SecureRandom();

        KeyPair keyPair = KeyPairGenerator.getInstance("DSA").generateKeyPair();

        signature.initSign(keyPair.getPrivate(), secureRandom);

        byte[] data = sha256hex.getBytes(StandardCharsets.UTF_8);
        signature.update(data);

        String sign = Base64.getEncoder().encodeToString(signature.sign()); // эцп

        createFileWithData("src/main/java/com/study/course4/bpid/lab4files/sign/sign" + fileName, sign);
        createFileWithData("src/main/java/com/study/course4/bpid/lab4files/signpub/signpub" + fileName,
                Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded()));
    }

    @SneakyThrows
    public static void checkSignMenu() {
        System.out.println("Введите название файла, подпись которого хотите проверить: ");
        String filePath = "src/main/java/com/study/course4/bpid/lab4files/" + scn.nextLine(); // получаем путь к файлу

        System.out.println("Введите путь к файлу с ЭЦП: ");
        String signFilePath = "src/main/java/com/study/course4/bpid/lab4files/" + scn.nextLine();

        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1, filePath.length());
        String content = new String(Files.readAllBytes(Paths.get(filePath))); // получаем содержиммое файла

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(content.getBytes(StandardCharsets.UTF_8));
        String sha256hex = new String(Hex.encode(hash));

        Signature signature = Signature.getInstance("SHA256withDSA");

        String publicRsaKey = new String(Files.readAllBytes(
                Paths.get("src/main/java/com/study/course4/bpid/lab4files/signpub/signpub" + fileName)));
        KeyFactory rsaKf = KeyFactory.getInstance("DSA"); // or "EC" or whatever
        PublicKey publicKey = rsaKf.generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(publicRsaKey)));

        signature.initVerify(publicKey);

        byte[] data = sha256hex.getBytes(StandardCharsets.UTF_8);
        signature.update(data);
        
        String sign = new String(Files.readAllBytes(Paths.get(signFilePath)));

        boolean isTrue;
        try {
            isTrue = signature.verify(Base64.getDecoder().decode(sign));
        } catch (SignatureException e) {
            isTrue = false;
        }
        if(isTrue) System.out.println("ЭЦП валидна");
        else System.out.println("ЭЦП не валидна");
    }

    @SneakyThrows
    public static void createFileWithData(String filePath, String data){
        File file = new File(filePath);
        file.delete();
        file.createNewFile();
        Files.write(Paths.get(file.getAbsolutePath()), data.getBytes(), StandardOpenOption.WRITE);
    }
}
