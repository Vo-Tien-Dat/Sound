package com.music.sound.security.EncryptAndDecrypt;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class DESAlgorithm {
    private static String KEY = "hello";

    public static String encrypt(String value) throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        try {
            String originalText = value;
            SecretKeySpec skeySpec = new SecretKeySpec(KEY.getBytes(), "DES");
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            byte[] byteEncrypted = cipher.doFinal(originalText.getBytes());
            String encrypted = Base64.getEncoder().encodeToString(byteEncrypted);
            return encrypted;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String value) throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        try {
            String encryptText = value;
            SecretKeySpec skeySpec = new SecretKeySpec(KEY.getBytes(), "DES");
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(encryptText));
            return (new String(decrypted));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
