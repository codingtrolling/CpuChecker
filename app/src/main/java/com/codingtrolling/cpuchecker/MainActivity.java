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
            String info = HardwareScanner.getCpuInfo().toLowerCase();
            int iconRes = 0; 

            if (info.contains("qualcomm") || info.contains("snapdragon")) {
                iconRes = R.drawable.snapdragon;
            } else if (info.contains("mediatek") || info.contains("mt6")) {
                iconRes = R.drawable.snapdragon;
            } else if (info.contains("exynos") || info.contains("samsung")) {
                iconRes = R.drawable.snapdragon;
            }

            final int finalIcon = iconRes;
            handler.post(() -> {
                if (finalIcon != 0) {
                    brandIcon.setImageResource(finalIcon);
                }
                output.setText(">> SCAN COMPLETE\n" + HardwareScanner.getCpuInfo());
            });
        }).start();
    }
}
