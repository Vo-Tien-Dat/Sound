package com.music.sound.security.EncryptAndDecrypt;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.DESKeySpec;

public class DES {

    private Cipher cipher;
    private SecretKey desKey;

    public DES(String key) throws Exception {
        DESKeySpec dks = new DESKeySpec(key.getBytes());
        SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
        SecretKey desKey = skf.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        this.cipher = cipher;
        this.desKey = desKey;
    }

    public String encrypt(String message) throws Exception {

        this.cipher.init(Cipher.ENCRYPT_MODE, desKey);
        byte[] byteEncrypt = this.cipher.doFinal(message.getBytes());
        String encryptStr = Base64.getEncoder().encodeToString(byteEncrypt);
        return encryptStr;
    }

    public String decrypt(String message) throws Exception {
        byte[] byteMessage = Base64.getDecoder().decode(message);
        this.cipher.init(Cipher.DECRYPT_MODE, desKey);
        byte[] byteDecrypt = this.cipher.doFinal(byteMessage);
        String decryptStr = new String(byteDecrypt);
        return decryptStr;
    }
}