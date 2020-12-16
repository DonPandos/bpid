package com.study.course4.bpid.crypt;

import lombok.SneakyThrows;

import javax.crypto.Cipher;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

public class RSACrypt {

    @SneakyThrows
    public static String encrypt(String plainText, PublicKey publicKey) {
        Cipher ecipher = Cipher.getInstance("RSA");
        ecipher.init(Cipher.ENCRYPT_MODE, publicKey);

        byte[] encryptedText = ecipher.doFinal(plainText.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(encryptedText);
    }

    @SneakyThrows
    public static String decrypt(String encryptedText, PrivateKey privateKey) {
        byte[] bytes = Base64.getDecoder().decode(encryptedText);

        Cipher decriptCipher = Cipher.getInstance("RSA");
        decriptCipher.init(Cipher.DECRYPT_MODE, privateKey);

        return new String(decriptCipher.doFinal(bytes), "UTF-8");
    }
}
