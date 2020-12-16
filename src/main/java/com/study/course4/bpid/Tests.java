package com.study.course4.bpid;

import lombok.SneakyThrows;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.*;


public class Tests {
    @SneakyThrows
    public static void main(String[] args) throws UnsupportedEncodingException {
        Security.addProvider(new BouncyCastleProvider());
//        SecretKey key = KeyGenerator.getInstance("DES").generateKey();
//        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
//        cipher.init(Cipher.ENCRYPT_MODE, key);
//        String strToEnc = "Эта строка зашифруетсяTut eshe english symbols1234567890-=!@#$%^&*()_+{}|:{}?><L:?";
//        byte[] utf8 = strToEnc.getBytes("UTF-8");
//        byte[] enc = cipher.doFinal(utf8);
//        System.out.println(new String(enc));
//        Cipher cipher1 = Cipher.getInstance("DES");
//        cipher1.init(Cipher.DECRYPT_MODE, key);
//        byte[] dec = cipher1.doFinal(enc);
//        System.out.println(new String(dec));
//        System.out.println(Base64.getEncoder().encodeToString(key.getEncoded()));
//
//        String toEnc = "This string will be encodedЧто по русским буквам то?!@#$%^&*()_{}|!№;%:?*)_ХЭЮ,,ЗХЪЙЦУЗJAIDSOAIVQOWR \n2342wrewfw\nqwrqerq";
//        SecretKey key = KeyGenerator.getInstance("DES").generateKey(); // генерируем ключ
//        DESCrypt desCrypt = new DESCrypt(key); // создаем объект шифра
//        String enc = desCrypt.encrypt(toEnc); //шифруем массив байтов Base64 и преобразуем в строку
//        System.out.println("Encrypted string: " + enc);
//        String decoded = desCrypt.decrypt(enc);
//        System.out.println("Decrypted string: " + new String(decoded));
//
//        KeyPair keyPair = KeyPairGenerator.getInstance("RSA").generateKeyPair();
//        String toEnc = "This is string которую будем шифровать";
//        String ecnrypted = RSACrypt.encrypt(toEnc, keyPair.getPublic());
//        System.out.println("Ecnrypted: " + ecnrypted);
//        String decrypted = RSACrypt.decrypt(ecnrypted, keyPair.getPrivate());
//        System.out.println("Decrypted: " + decrypted);
//        System.out.println(Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded()));

        Signature signature = Signature.getInstance("SHA256withDSA");

        SecureRandom secureRandom = new SecureRandom();
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DSA");
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        signature.initSign(keyPair.getPrivate(), secureRandom);

        byte[] data = "helloworld".getBytes("UTF-8");
        signature.update(data);

        System.out.println(new String(data));

        byte[] signData = signature.sign();

        System.out.println(new String(signData));

        signature.initVerify(keyPair.getPublic());

        signature.update(data);
        System.out.println(signature.verify(signData));

        //

        String originalString = "Эта строка с ЭЦП";
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(
                originalString.getBytes(StandardCharsets.UTF_8));
        String sha256hex = new String(Hex.encode(hash));

        System.out.println(sha256hex);


//        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
//        keyPairGenerator.initialize(1024, SecureRandom.getInstance("SHA1PRNG", "SUN"));
//        KeyPair keyPair = keyPairGenerator.generateKeyPair();
//        System.out.println(Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded()));
    }
}
