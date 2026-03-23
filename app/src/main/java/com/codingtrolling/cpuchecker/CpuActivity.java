package com.codingtrolling.cpuchecker;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.RandomAccessFile;

public class CpuActivity extends AppCompatActivity {
    private TextView output;
    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        output = findViewById(R.id.cpu_terminal_output);
        ImageView logo = findViewById(R.id.activity_logo);
        TextView title = findViewById(R.id.activity_title_label);
        
        title.setText("CPU CORE DATA");
        applyChipsetLogo(logo);
        startCpuMonitor();
    }

    private void applyChipsetLogo(ImageView view) {
        String hw = Build.HARDWARE.toLowerCase();
        if (hw.contains("mt") || hw.contains("mediatek")) view.setImageResource(R.drawable.mediatek);
        else if (hw.contains("qcom") || hw.contains("snapdragon")) view.setImageResource(R.drawable.snapdragon);
        else view.setImageResource(R.drawable.logo_generic);
    }

    private void startCpuMonitor() {
        new Thread(() -> {
            while (true) {
                StringBuilder sb = new StringBuilder();
                sb.append("HARDWARE: ").append(Build.HARDWARE.toUpperCase()).append("\n\n");
                for (int i = 0; i < 4; i++) {
                    sb.append("CORE ").append(i).append(": ").append(getFreq(i)).append(" MHz\n");
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
