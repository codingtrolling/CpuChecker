package com.codingtrolling.cpuchecker;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.RandomAccessFile;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private TextView output;
    private ImageView brandIcon;
    private Handler handler = new Handler(Looper.getMainLooper());
    private boolean isRunning = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        output = findViewById(R.id.cpu_terminal_output);
        brandIcon = findViewById(R.id.cpu_brand_icon);
        startMonitoring();
    }

    private void startMonitoring() {
        new Thread(() -> {
            while (isRunning) {
                String rawInfo = HardwareScanner.getCpuInfo();
                float usage = getCpuUsage();
                int cores = Runtime.getRuntime().availableProcessors();
                
                String model = "MediaTek Helio/Dimensity"; // Default for your chip
                for (String line : rawInfo.split("\n")) {
                    if (line.contains("Hardware") || line.contains("model name")) {
                        model = line.split(":")[1].trim();
                        break;
                    }
                }

                final String display = "SYSTEM ONLINE\n\n" +
                                     "CHIP: " + model + "\n" +
                                     "CORES: " + cores + " THREADS\n" +
                                     "LOAD: " + String.format("%.1f", usage) + "%";

                handler.post(() -> output.setText(display));
                
                try { Thread.sleep(1000); } catch (Exception e) {}
            }
        }).start();
    }

    private float getCpuUsage() {
        try {
            RandomAccessFile reader = new RandomAccessFile("/proc/stat", "r");
            String load = reader.readLine();
            String[] toks = load.split(" +");
            long idle1 = Long.parseLong(toks[4]);
            long cpu1 = Long.parseLong(toks[1]) + Long.parseLong(toks[2]) + Long.parseLong(toks[3]) + Long.parseLong(toks[4]) + Long.parseLong(toks[5]) + Long.parseLong(toks[6]) + Long.parseLong(toks[7]);
            try { Thread.sleep(360); } catch (Exception e) {}
            reader.seek(0);
            load = reader.readLine();
            reader.close();
            toks = load.split(" +");
            long idle2 = Long.parseLong(toks[4]);
            long cpu2 = Long.parseLong(toks[1]) + Long.parseLong(toks[2]) + Long.parseLong(toks[3]) + Long.parseLong(toks[4]) + Long.parseLong(toks[5]) + Long.parseLong(toks[6]) + Long.parseLong(toks[7]);
            return (float)(cpu2 - cpu1 - (idle2 - idle1)) * 100 / (cpu2 - cpu1);
        } catch (IOException ex) { return 0; }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isRunning = false;
    }
}
