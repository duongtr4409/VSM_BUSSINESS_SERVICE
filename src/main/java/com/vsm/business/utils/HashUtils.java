package com.vsm.business.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.spec.KeySpec;
import java.util.Base64;

public class HashUtils {
    private static final String SECRET_KEY = "8x/A?D(G+KbPeSgVkYp3s6v9y$B&E)H@McQfTjWmZq4t7w!z%C*F-JaNdRgUkXp2";
    private static final String SALT = "?D(G+KbPeShVmYq3t6v9y$B&E)H@McQfTjWnZr4u7x!A%C*F-JaNdRgUkXp2s5v8";

    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz0123456789";

    private static final int KEY_SHIFT = ALPHABET.length() / 2;

    public static String tmp_encrypt(String strToEncrypt) {
        try {
            byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            IvParameterSpec ivspec = new IvParameterSpec(iv);

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), SALT.getBytes(), 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
            return URLEncoder.encode(Base64.getEncoder()
                .encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8))), "UTF-8");
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    public static String tmp_decrypt(String strToDecrypt) {
        try {
            byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            IvParameterSpec ivspec = new IvParameterSpec(iv);

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), SALT.getBytes(), 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
            return new String(cipher.doFinal(Base64.getDecoder().decode(URLDecoder.decode(strToDecrypt, "UTF-8"))));
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }

    public static String encrypt(String inputStr) {
        // convert inputStr into lower case
        inputStr = inputStr.toLowerCase();

        // encryptStr to store encrypted data
        String encryptStr = "";

        // use for loop for traversing each character of the input string
        for (int i = 0; i < inputStr.length(); i++) {
            // get position of each character of inputStr in ALPHABET
            int pos = ALPHABET.indexOf(inputStr.charAt(i));

            // get encrypted char for each char of inputStr
            int encryptPos = (KEY_SHIFT + pos) % ALPHABET.length();
            char encryptChar = ALPHABET.charAt(encryptPos);

            // add encrypted char to encrypted string
            encryptStr += encryptChar;
        }

        // return encrypted string
        return encryptStr;
    }

    public static String decrypt(String inputStr) {
        // convert inputStr into lower case
        inputStr = inputStr.toLowerCase();

        // decryptStr to store decrypted data
        String decryptStr = "";

        // use for loop for traversing each character of the input string
        for (int i = 0; i < inputStr.length(); i++) {

            // get position of each character of inputStr in ALPHABET
            int pos = ALPHABET.indexOf(inputStr.charAt(i));

            // get decrypted char for each char of inputStr
            int decryptPos = (pos - KEY_SHIFT) % ALPHABET.length();

            // if decryptPos is negative
            if (decryptPos < 0) {
                decryptPos = ALPHABET.length() + decryptPos;
            }
            char decryptChar = ALPHABET.charAt(decryptPos);

            // add decrypted char to decrypted string
            decryptStr += decryptChar;
        }
        // return decrypted string
        return decryptStr;
    }

    public static void main(String[] args) {
        String seed = "980712";
        System.out.println(tmp_encrypt(seed));
        System.out.println(tmp_decrypt(tmp_encrypt(seed)));
    }
}
