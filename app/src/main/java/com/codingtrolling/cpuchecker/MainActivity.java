package com.codingtrolling.cpuchecker;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.RandomAccessFile;

public class MainActivity extends AppCompatActivity {
    private TextView output;
    private ImageView brandIcon;
    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        output = findViewById(R.id.cpu_terminal_output);
        brandIcon = findViewById(R.id.cpu_brand_icon);
        startDiagnostics();
    }

    private void startDiagnostics() {
        new Thread(() -> {
            while (true) {
                String manufacturer = Build.MANUFACTURER.toLowerCase();
                int logoRes = R.drawable.logo_generic;

                // --- GLOBAL BRAND MAPPING ---
                if (manufacturer.contains("xiaomi") || manufacturer.contains("poco") || manufacturer.contains("redmi")) {
                    logoRes = R.drawable.logo_xiaomi;
                } else if (manufacturer.contains("samsung")) {
                    logoRes = R.drawable.logo_samsung;
                } else if (manufacturer.contains("oppo") || manufacturer.contains("realme") || manufacturer.contains("oneplus")) {
                    logoRes = R.drawable.logo_oppo; // BBK Electronics group
                } else if (manufacturer.contains("google")) {
                    logoRes = R.drawable.logo_google;
                } else if (manufacturer.contains("motorola") || manufacturer.contains("lenovo")) {
                    logoRes = R.drawable.logo_motorola;
                } else if (manufacturer.contains("vivo") || manufacturer.contains("iqoo")) {
                    logoRes = R.drawable.logo_vivo;
                } else if (manufacturer.contains("infinix") || manufacturer.contains("tecno") || manufacturer.contains("itel")) {
                    logoRes = R.drawable.logo_transsion; // Transsion Holdings
                } else if (manufacturer.contains("huawei") || manufacturer.contains("honor")) {
                    logoRes = R.drawable.logo_huawei;
                }

                final int finalLogo = logoRes;
                final String stats = "MANUFACTURER: " + Build.MANUFACTURER.toUpperCase() + "\n" +
                                     "HARDWARE:     " + Build.HARDWARE.toUpperCase() + "\n" +
                                     "MODEL:        " + Build.MODEL + "\n" +
                                     "CORES:        " + Runtime.getRuntime().availableProcessors() + "\n" +
                                     "----------------------------\n" +
                                     "CORE 0:       " + getFreq(0) + " MHz\n" +
                                     "CORE 1:       " + getFreq(1) + " MHz";

                handler.post(() -> {
                    brandIcon.setImageResource(finalLogo);
                    output.setText(stats);
                });

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
