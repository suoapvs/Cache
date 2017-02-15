package com.salimov.yurii;

import com.salimov.yurii.cache.Cache;

import java.util.Map;

/**
 * @author Yuriy Salimov (yuriy.alex.salimov@gmail.com)
 * @version 1.0
 */
public class Main {

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            Cache.put("Key #" + i, new Object());
        }
        for (Map.Entry<String, String> entry : Cache.getEntriesToString().entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
    }
}