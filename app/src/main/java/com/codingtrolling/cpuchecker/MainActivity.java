package com.codingtrolling.cpuchecker;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.RandomAccessFile;

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
                int cores = Runtime.getRuntime().availableProcessors();
                
                sb.append(String.format("%-15s %s\n", "Model", "MT6769H"));
                sb.append(String.format("%-15s %s\n", "Cores", cores));
                sb.append(String.format("%-15s %s\n", "Process", "12 nm"));
                sb.append("-------------------------------\n");

                for (int i = 0; i < cores; i++) {
                    String freq = getCoreFreq(i);
                    sb.append(String.format("%-15s %s MHz\n", "CPU " + i, freq));
                }

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
            int khz = Integer.parseInt(line.trim());
            return String.valueOf(khz / 1000); // Convert KHz to MHz
        } catch (Exception e) { return "0"; }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isRunning = false;
    }
}
