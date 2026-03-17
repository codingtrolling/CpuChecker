package com.codingtrolling.cpuchecker;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.RandomAccessFile;
import java.io.File;

public class MainActivity extends AppCompatActivity {
    private TextView output;
    private Handler handler = new Handler(Looper.getMainLooper());
    private boolean isRunning = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        output = findViewById(R.id.cpu_terminal_output);
        startLiveUpdate();
    }

    private void startLiveUpdate() {
        new Thread(() -> {
            while (isRunning) {
                StringBuilder sb = new StringBuilder();
                
                // --- DYNAMIC SOC DETECTION ---
                // Reading from the actual System Build props
                String model = Build.HARDWARE; 
                String board = Build.BOARD;
                int cores = Runtime.getRuntime().availableProcessors();
                
                sb.append(String.format("%-18s %s\n", "Hardware", model.toUpperCase()));
                sb.append(String.format("%-18s %s\n", "Board", board));
                sb.append(String.format("%-18s %s\n", "Cores", cores));
                sb.append("------------------------------------\n");

                // --- LIVE CORE FREQUENCIES ---
                for (int i = 0; i < cores; i++) {
                    sb.append(String.format("%-18s %s MHz\n", "CPU " + i, getCoreFreq(i)));
                }

                sb.append("------------------------------------\n");

                // --- BATTERY & OS ---
                Intent b = registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
                int level = b.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                int temp = b.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0);
                
                sb.append(String.format("%-18s %d%%\n", "Battery", level));
                sb.append(String.format("%-18s %.1f°C\n", "Temp", (temp / 10.0)));
                sb.append(String.format("%-18s Android %s\n", "OS Version", Build.VERSION.RELEASE));
                
                // Detecting if the device is rooted (Common for CodingTrolling users)
                boolean isRooted = new File("/system/app/Superuser.apk").exists() || new File("/system/xbin/su").exists();
                sb.append(String.format("%-18s %s\n", "Root Access", isRooted ? "YES" : "NO"));

                final String result = sb.toString();
                handler.post(() -> output.setText(result));
                try { Thread.sleep(1000); } catch (Exception e) {}
            }
        }).start();
    }

    private String getCoreFreq(int coreIndex) {
        try {
            RandomAccessFile reader = new RandomAccessFile("/sys/devices/system/cpu/cpu" + coreIndex + "/cpufreq/scaling_cur_freq", "r");
            String line = reader.readLine();
            reader.close();
            return String.valueOf(Integer.parseInt(line.trim()) / 1000);
        } catch (Exception e) { return "0"; }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isRunning = false;
    }
}
