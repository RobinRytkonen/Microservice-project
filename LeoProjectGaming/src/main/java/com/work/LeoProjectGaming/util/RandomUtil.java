package com.work.LeoProjectGaming.util;

import java.util.Random;

public class RandomUtil {
    private RandomUtil() {}

    public static boolean getRandomBoolean() {
        return new Random().nextBoolean();
    }

    public static int getRandomInteger() {
        return new Random().nextInt(100);
    }
}
