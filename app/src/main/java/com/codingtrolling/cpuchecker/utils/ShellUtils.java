package com.codingtrolling.cpuchecker.utils;

import java.io.InputStream;
import java.util.Scanner;

public class ShellUtils {
    public static String getProp(String propName) {
        try {
            Process process = Runtime.getRuntime().exec("getprop " + propName);
            InputStream in = process.getInputStream();
            // Fixed: use \A for the scanner delimiter
            Scanner s = new Scanner(in).useDelimiter("\\A");
            return s.hasNext() ? s.next().trim() : "";
        } catch (Exception e) {
            return "Unknown";
        }
    }
}
