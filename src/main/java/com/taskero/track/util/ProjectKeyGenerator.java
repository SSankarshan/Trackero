package com.taskero.track.util;

public class ProjectKeyGenerator {

    /**
     * Generate a 3-letter key from project name by taking first letter of first 3 words or letters.
     * Example: "Milpitas" -> "MPT"
     */
    public static String generateKeyFromName(String name) {
        if (name == null || name.isEmpty()) return null;

        String[] words = name.trim().split("\\s+");
        StringBuilder key = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                key.append(Character.toUpperCase(word.charAt(0)));
                if (key.length() == 3) break;
            }
        }

        // If less than 3 letters, pad with letters from the first word
        if (key.length() < 3) {
            String firstWord = words[0];
            for (int i = 1; i < firstWord.length() && key.length() < 3; i++) {
                key.append(Character.toUpperCase(firstWord.charAt(i)));
            }
        }

        return key.toString();
    }
}
