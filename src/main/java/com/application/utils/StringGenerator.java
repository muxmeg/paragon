package com.application.utils;

import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

@Service
public final class StringGenerator {

    private StringGenerator() {
    }

//    public String generateString(int length, String characters) {
//        char[] chars = characters.toCharArray();
//        char[] result = new char[length];
//        IntStream.range(0, length).forEach(i -> {
//            char character;
//            do {
//                character = chars[ThreadLocalRandom.current().nextInt(chars.length)];
//            } while (Arrays.con);
//            result[i] = ;
//        });
//        return new String(result);
//    }

    public String generateStringFromUniqueSymbols(String characters, int limit) {
        char[] chars = characters.toCharArray();
        int count = chars.length;
        for (int i = count; i > 1; i--) {
            swap(chars, i - 1, ThreadLocalRandom.current().nextInt(i));
        }
        return new String(chars).substring(0, limit);
    }

    public String generateStringFromUniqueSymbols(String characters) {
        char[] chars = characters.toCharArray();
        int count = chars.length;
        for (int i = count; i > 1; i--) {
            swap(chars, i - 1, ThreadLocalRandom.current().nextInt(i));
        }
        return new String(chars);
    }

    private static void swap(char[] array, int i, int j) {
        char temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}