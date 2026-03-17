package com.codingtrolling.cpuchecker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class HardwareScanner {

    // Part 38: Read the standard cpuinfo file
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

    // Part 39: Get Maximum Frequency for Core 0
    public static String getMaxFreq() {
        try (BufferedReader br = new BufferedReader(new FileReader("/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq"))) {
            return br.readLine() + " Hz";
        } catch (IOException e) {
            return "N/A";
        }
    }
}

    // Part 60: Get CPU Scaling Governor
    public static String getGovernor() {
        try (BufferedReader br = new BufferedReader(new FileReader("/sys/devices/system/cpu/cpu0/cpufreq/scaling_governor"))) {
            return br.readLine();
        } catch (IOException e) {
            return "unknown";
        }
    }

    // Part 61: Extract BogoMIPS from /proc/cpuinfo
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

    // Part 67: Check CPU Thermal Zone (Temperature)
    public static String getCpuTemp() {
        try (BufferedReader br = new BufferedReader(new FileReader("/sys/class/thermal/thermal_zone0/temp"))) {
            float temp = Float.parseFloat(br.readLine()) / 1000;
            return temp + "°C";
        } catch (Exception e) {
            return "N/A";
        }
    }

    // Part 68: Get Hardware Board Name
    public static String getBoard() {
        return android.os.Build.BOARD;
    }

    // Part 69: Get Hardware Manufacturer
    public static String getManufacturer() {
        return android.os.Build.MANUFACTURER;
    }
