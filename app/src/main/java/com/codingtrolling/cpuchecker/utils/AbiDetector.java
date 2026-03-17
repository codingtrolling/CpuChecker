package com.codingtrolling.cpuchecker.utils;

import android.os.Build;

public class AbiDetector {
    // Part 52: Get Supported ABIs
    public static String getSupportedAbis() {
        StringBuilder abis = new StringBuilder();
        for (String abi : Build.SUPPORTED_ABIS) {
            abis.append(abi).append(" ");
        }
        return abis.toString().trim();
    }

    // Part 53: Detect 64-bit support
    public static boolean is64Bit() {
        return android.os.Process.is64Bit();
    }
}
