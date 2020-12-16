package com.study.course4.bpid.crypt;

import lombok.SneakyThrows;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.util.Base64;

public class DESCrypt {
    Cipher ecipher;
    Cipher dcipher;
    SecretKey key;

    @SneakyThrows
    public DESCrypt(SecretKey key) {
        this.key = key;
        ecipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        dcipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        ecipher.init(Cipher.ENCRYPT_MODE, key);
        dcipher.init(Cipher.DECRYPT_MODE, key);
    }

    @SneakyThrows
    public String encrypt(String input) {
        return new String(Base64.getEncoder().encode(ecipher.doFinal(input.getBytes())), "UTF-8");
    }

    @SneakyThrows
    public String decrypt(String input) {
        byte[] inputBytes = Base64.getDecoder().decode(input.getBytes("UTF-8"));
        return new String(dcipher.doFinal(inputBytes));
    }

}
