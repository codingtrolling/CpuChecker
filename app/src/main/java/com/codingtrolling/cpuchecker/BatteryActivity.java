package com.codingtrolling.cpuchecker;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class BatteryActivity extends AppCompatActivity {
    private TextView output;
    private Handler handler = new Handler(Looper.getMainLooper());
    private boolean isRunning = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Reusing the terminal layout
        output = findViewById(R.id.cpu_terminal_output);
        startBatteryMonitor();
    }

    private void startBatteryMonitor() {
        new Thread(() -> {
            while (isRunning) {
                IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
                Intent batteryStatus = registerReceiver(null, ifilter);

                int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                int voltage = batteryStatus.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0);
                float temp = ((float) batteryStatus.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0)) / 10;
                int health = batteryStatus.getIntExtra(BatteryManager.EXTRA_HEALTH, 0);

                StringBuilder sb = new StringBuilder();
                sb.append("--- POWER DIAGNOSTIC ---\n");
                sb.append(String.format("%-18s %d%%\n", "Level", level));
                sb.append(String.format("%-18s %.1f°C\n", "Temperature", temp));
                sb.append(String.format("%-18s %d mV\n", "Voltage", voltage));
                sb.append(String.format("%-18s %s\n", "Health", getHealthString(health)));

                final String res = sb.toString();
                handler.post(() -> output.setText(res));
                try { Thread.sleep(2000); } catch (Exception e) {}
            }
        }).start();
    }

    private String getHealthString(int health) {
        switch (health) {
            case BatteryManager.BATTERY_HEALTH_GOOD: return "GOOD";
            case BatteryManager.BATTERY_HEALTH_OVERHEAT: return "OVERHEAT";
            case BatteryManager.BATTERY_HEALTH_DEAD: return "DEAD";
            default: return "UNKNOWN";
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isRunning = false;
    }
}
