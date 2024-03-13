package com.tsypk.urlshortener;

import org.springframework.stereotype.Service;

@Service
public class BaseConversion {

    private static final String allowedString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private char[] allowedCharacters = allowedString.toCharArray();
    private int base = allowedCharacters.length;

    public String encode(long input){
        var encodedString = new StringBuilder();

        if(input == 0) {
            return String.valueOf(allowedCharacters[0]);
        }

        while (input > 0) {
            encodedString.append(allowedCharacters[(int) (input % base)]);
            input = input / base;
        }

        return encodedString.reverse().toString();
    }

    public long decode(String input) {
        char[] chars = input.toCharArray();
        int length = chars.length;
        long decoded = 0;

        for (int i = 0; i < length; i++) {
            decoded += (long) (allowedString.indexOf(chars[i]) * Math.pow(base, length - i - 1));
        }

        return decoded;
    }
}
