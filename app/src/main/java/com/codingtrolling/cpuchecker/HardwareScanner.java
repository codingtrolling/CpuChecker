package com.codingtrolling.cpuchecker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class HardwareScanner {

    public static String getCpuInfo() {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader("/proc/cpuinfo"))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            return "Error reading /proc/cpuinfo";
        }
        return sb.toString();
    }

    public static String getMaxFreq() {
        try (BufferedReader br = new BufferedReader(new FileReader("/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq"))) {
            return br.readLine() + " Hz";
        } catch (IOException e) {
            return "N/A";
        }
    }

    public static String getGovernor() {
        try (BufferedReader br = new BufferedReader(new FileReader("/sys/devices/system/cpu/cpu0/cpufreq/scaling_governor"))) {
            return br.readLine();
        } catch (IOException e) {
            return "unknown";
        }
    }

    public static String getBogoMips() {
        try (BufferedReader br = new BufferedReader(new FileReader("/proc/cpuinfo"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.toLowerCase().contains("bogomips")) {
                    return line.split(":")[1].trim();
                }
            }
        } catch (IOException e) {
            return "N/A";
        }
        return "Not Found";
    }

    public static String getCpuTemp() {
        try (BufferedReader br = new BufferedReader(new FileReader("/sys/class/thermal/thermal_zone0/temp"))) {
            float temp = Float.parseFloat(br.readLine()) / 1000;
            return temp + "°C";
        } catch (Exception e) {
            return "N/A";
        }
    }

    public static String getBoard() {
        return android.os.Build.BOARD;
    }

    public static String getManufacturer() {
        return android.os.Build.MANUFACTURER;
    }
}
