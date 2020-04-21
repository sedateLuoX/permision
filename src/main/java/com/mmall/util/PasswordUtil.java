package com.mmall.util;

import java.util.Date;
import java.util.Random;

public class PasswordUtil {

    public final static String[] word = {
            "a", "b", "c", "d", "e", "f", "g",
            "h", "j", "k", "m", "n", "p", "q",
            "r", "s", "t", "u", "v", "w", "x", "y", "z"
    };

    public final static String[] num = {
            "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "0"
    };

    public static String randomPassword() {

        StringBuffer sb = new StringBuffer();
        Random random = new Random(new Date().getTime());
        boolean flag = false;
        int length = random.nextInt(3) + 8;

        for (int i = 0; i < length; i++) {
            if (flag) {
                sb.append(num[random.nextInt(num.length)]);
            } else {
                sb.append(word[random.nextInt(word.length)]);
            }
            flag = !flag;

        }

        return sb.toString();

    }


    public static void main(String[] args) {

        String s = randomPassword();

        System.out.println(s);
    }
}
