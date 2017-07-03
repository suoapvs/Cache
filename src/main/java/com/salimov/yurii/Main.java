package com.salimov.yurii;

import com.salimov.yurii.cache.Cache;

import java.util.Map;

/**
 * @author Yuriy Salimov (yuriy.alex.salimov@gmail.com)
 */
public class Main {

    public static void main(String[] args) {
        final Cache cache = Cache.getTemporaryCache();
        for (int i = 0; i < 100; i++) {
            cache.put("Key #" + i, new Object());
        }
        for (Map.Entry<String, String> entry : cache.getEntriesToString().entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
    }
}
