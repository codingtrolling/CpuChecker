package com.codingtrolling.cpuchecker;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.RandomAccessFile;

public class CpuActivity extends AppCompatActivity {
    private TextView output;
    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // We can reuse the layout for now
        output = findViewById(R.id.cpu_terminal_output);
        startCpuMonitor();
    }

    private void startCpuMonitor() {
        new Thread(() -> {
            while (true) {
                StringBuilder sb = new StringBuilder();
                sb.append("--- PROCESSOR ---\n");
                sb.append(String.format("%-18s %s\n", "SoC", Build.HARDWARE.toUpperCase()));
                sb.append(String.format("%-18s %d\n", "Cores", Runtime.getRuntime().availableProcessors()));
                sb.append("----------------------------\n");
                for (int i = 0; i < 4; i++) { // Monitoring first 4 cores
                    sb.append(String.format("%-18s %s MHz\n", "Core " + i, getFreq(i)));
                }
                final String res = sb.toString();
                handler.post(() -> output.setText(res));
                try { Thread.sleep(1000); } catch (Exception e) {}
            }
        }).start();
    }

    private String getFreq(int core) {
        try {
            RandomAccessFile r = new RandomAccessFile("/sys/devices/system/cpu/cpu" + core + "/cpufreq/scaling_cur_freq", "r");
            String line = r.readLine(); r.close();
            return String.valueOf(Integer.parseInt(line.trim()) / 1000);
        } catch (Exception e) { return "0"; }
    }
}
