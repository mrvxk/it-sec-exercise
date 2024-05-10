package de.hft;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.SecureRandom;
import java.util.Base64;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.Random;

public class AESTask {

    public static SecretKey generateKey(int n) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(n); // 128 or 256 bits
        return keyGenerator.generateKey();
    }

    public static IvParameterSpec generateIv() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    public static String encrypt(String algorithm, String input, SecretKey key, IvParameterSpec iv) throws Exception {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        byte[] cipherText = cipher.doFinal(input.getBytes());
        return Base64.getEncoder().encodeToString(cipherText);
    }

    public static String decrypt(String algorithm, String cipherText, SecretKey key, IvParameterSpec iv) throws Exception {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(cipherText));
        return new String(plainText);
    }

    public static void captureScreen(String fileName) throws Exception {
        Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        BufferedImage capture = new Robot().createScreenCapture(screenRect);
        ImageIO.write(capture, "bmp", new File(fileName));
    }

    public static void main(String[] args) throws Exception {
        String input = randomString(1000);
        SecretKey key128 = generateKey(128);
        SecretKey key256 = generateKey(256);
        IvParameterSpec iv = generateIv();

        String algorithm = "AES/CBC/PKCS5Padding";
        String cipherText128 = encrypt(algorithm, input, key128, iv);
        String cipherText256 = encrypt(algorithm, input, key256, iv);
        String plainText128 = decrypt(algorithm, cipherText128, key128, iv);
        String plainText256 = decrypt(algorithm, cipherText256, key256, iv);

        System.out.println("AES-128 Cipher Text start: " + cipherText128.substring(0, 10) + "...");
        System.out.println("AES-128 Decrypted Text: " + plainText128.substring(0, 10) + "...");
        System.out.println("Texts are equal: " + plainText128.equals(input));
        System.out.println("AES-256 Cipher Text: " + cipherText256.substring(0, 10) + "...");
        System.out.println("AES-256 Decrypted Text: " + plainText256.substring(0, 10) + "...");
        System.out.println("Texts are equal: " + plainText256.equals(input));

        // Performance measurement example for AES-256 encryption
        long startTime = System.nanoTime();
        encrypt(algorithm, input, key128, iv);
        long endTime = System.nanoTime();
        System.out.println("Elapsed Time for AES-128 Encryption: " + (endTime - startTime) / 1000000f + " mikroseconds");

        // Performance measurement example for AES-256 encryption
        startTime = System.nanoTime();
        encrypt(algorithm, input, key256, iv);
        endTime = System.nanoTime();
        System.out.println("Elapsed Time for AES-256 Encryption: " + (endTime - startTime) / 1000000f + " mikroseconds");

        // Performance measurement example for AES-256 encryption
        startTime = System.nanoTime();
        decrypt(algorithm, cipherText128, key128, iv);
        endTime = System.nanoTime();
        System.out.println("Elapsed Time for AES-128 Decryption: " + (endTime - startTime) / 1000000f + " mikroseconds");

        // Performance measurement example for AES-256 encryption
        startTime = System.nanoTime();
        decrypt(algorithm, cipherText256, key256, iv);
        endTime = System.nanoTime();
        System.out.println("Elapsed Time for AES-256 Decryption: " + (endTime - startTime) / 1000000f + " mikroseconds");


        // Optionally, capture a screenshot of the results
        // captureScreen("screenshot.bmp");
    }

    private static String randomString(int i) {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder(i);
        Random random = new Random();
        for (int j = 0; j < i; j++) {
            int index = random.nextInt(alphabet.length());
            char randomChar = alphabet.charAt(index);
            sb.append(randomChar);
        }
        return sb.toString();
    }
}
