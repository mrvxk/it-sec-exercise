package de.hft;

import java.math.BigInteger;

public class DHKETask {
    public static void main(String[] args) {
        // Define the prime number and base
        BigInteger n = new BigInteger("17");
        BigInteger g = new BigInteger("12");

        // Secrets chosen by user1 and user2
        BigInteger secretUser1 = new BigInteger("88");
        BigInteger secretUser2 = new BigInteger("104");

        // Calculate public keys for user1 and user2
        BigInteger publicKeyUser1 = g.modPow(secretUser1, n);
        BigInteger publicKeyUser2 = g.modPow(secretUser2, n);

        // Exchange and calculate the shared secret
        BigInteger sharedSecretUser1 = publicKeyUser2.modPow(secretUser1, n);
        BigInteger sharedSecretUser2 = publicKeyUser1.modPow(secretUser2, n);

        // Print the results
        System.out.println("Public key of user1: " + publicKeyUser1);
        System.out.println("Public key of user2: " + publicKeyUser2);
        System.out.println("Shared secret of user1: " + sharedSecretUser1);
        System.out.println("Shared secret of user2: " + sharedSecretUser2);
    }

}

