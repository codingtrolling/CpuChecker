package com.codingtrolling.cpuchecker;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

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
        runDiagnostic();
    }

    private void runDiagnostic() {
        new Thread(() -> {
            String rawInfo = HardwareScanner.getCpuInfo();
            String lowerInfo = rawInfo.toLowerCase();
            
            // Extract only the "Hardware" or "Model" line
            String displayName = "Unknown Processor";
            for (String line : rawInfo.split("\n")) {
                if (line.contains("Hardware") || line.contains("model name")) {
                    displayName = line.split(":")[1].trim();
                    break;
                }
            }

            int iconRes = 0;
            // Better detection logic
            if (lowerInfo.contains("mt") || lowerInfo.contains("mediatek") || lowerInfo.contains("helio") || lowerInfo.contains("dimensity")) {
                iconRes = R.drawable.mediatek;
            } else if (lowerInfo.contains("qcom") || lowerInfo.contains("snapdragon")) {
                iconRes = R.drawable.snapdragon;
            } else if (lowerInfo.contains("exynos") || lowerInfo.contains("samsung")) {
                iconRes = R.drawable.exynos;
            }

            final int finalIcon = iconRes;
            final String finalDisplay = displayName;
            
            handler.post(() -> {
                if (finalIcon != 0) brandIcon.setImageResource(finalIcon);
                output.setTextSize(24);
                output.setText("CHIP DETECTED:\n" + finalDisplay);
            });
        }).start();
    }
}
