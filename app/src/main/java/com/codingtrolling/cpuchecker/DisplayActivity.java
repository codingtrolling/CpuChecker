package com.codingtrolling.cpuchecker;

import android.os.Bundle;
import android.widget.ImageView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DisplayActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BrandUtils.applyLogo((ImageView) findViewById(R.id.activity_logo));
        TextView output = findViewById(R.id.cpu_terminal_output);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        Display display = getWindowManager().getDefaultDisplay();
        
        float refreshRate = display.getRefreshRate();

        StringBuilder sb = new StringBuilder();
        sb.append("--- VISUAL ENGINE ---\n");
        sb.append(String.format("%-18s %dx%d\n", "Resolution", dm.widthPixels, dm.heightPixels));
        sb.append(String.format("%-18s %.2f Hz\n", "Refresh Rate", refreshRate));
        sb.append(String.format("%-18s %d dpi\n", "Density", dm.densityDpi));
        sb.append(String.format("%-18s %.1f inches\n", "Physical Size", getScreenSize(dm)));
        sb.append("----------------------------\n");
        sb.append(String.format("%-18s %s\n", "GPU Renderer", "Mali-G52 MC2")); // Dynamic GPU code coming in next patch!

        output.setText(sb.toString());
    }

    private double getScreenSize(DisplayMetrics dm) {
        double x = Math.pow(dm.widthPixels / dm.xdpi, 2);
        double y = Math.pow(dm.heightPixels / dm.ydpi, 2);
        return Math.sqrt(x + y);
    }
}
