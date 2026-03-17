package com.trollcorp.cpuchecker;

import java.io.RandomAccessFile;
import java.io.IOException;

public class CpuScanner {
    // Part 4: Get Core Count
    public static int getCoreCount() {
        return Runtime.getRuntime().availableProcessors();
    }

    // Part 5: Read BogoMIPS or Clock Speed
    public static String getCpuInfo() {
        try {
            RandomAccessFile reader = new RandomAccessFile("/proc/cpuinfo", "r");
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            reader.close();
            return sb.toString();
        } catch (IOException e) {
            return "Check Failed: Root or Permission Issue";
        }
    }
}
