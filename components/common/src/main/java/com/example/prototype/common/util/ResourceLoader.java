package com.example.prototype.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ResourceLoader {

    public static String loadResource(String resourceName) throws IOException {
        InputStream is = ResourceLoader.class.getResourceAsStream(resourceName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuffer sb = new StringBuffer();
        while (reader.ready()) {
            String line = reader.readLine();
            sb.append(line);
        }
        return sb.toString();
    }
}
