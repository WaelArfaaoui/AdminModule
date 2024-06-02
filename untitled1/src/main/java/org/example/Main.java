package org.example;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Main {
    public static void main(String[] args) {
        // Create an instance of BCryptPasswordEncoder
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // Call the encode method on the instance
        String encodedPassword = passwordEncoder.encode("123");

        // Print the encoded password
        System.out.println(encodedPassword);
    }
}
