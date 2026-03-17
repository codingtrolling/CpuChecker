package com.codingtrolling.cpuchecker;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        
        TextView title = findViewById(R.id.suite_title);
        if (title != null) title.setText("Cpu Checker");

        ImageView brandLogo = findViewById(R.id.menu_brand_logo);
        setBrandLogo(brandLogo);
    }

    private void setBrandLogo(ImageView view) {
        String man = Build.MANUFACTURER.toLowerCase();
        String hw = Build.HARDWARE.toLowerCase();
        String board = Build.BOARD.toLowerCase();

        // STRICT CHIPSET CHECK (Prioritize Snapdragon/Qualcomm)
        if (hw.contains("qcom") || hw.contains("snapdragon") || board.contains("qcom") || board.contains("msm")) {
            view.setImageResource(R.drawable.snapdragon);
        } else if (hw.contains("mt") || hw.contains("mediatek") || board.contains("mt")) {
            view.setImageResource(R.drawable.mediatek);
        } else if (hw.contains("exynos") || board.contains("exynos")) {
            view.setImageResource(R.drawable.exynos);
        } 
        // MANUFACTURER FALLBACK
        else if (man.contains("xiaomi") || man.contains("redmi") || man.contains("poco")) {
            view.setImageResource(R.drawable.logo_xiaomi);
        } else if (man.contains("samsung")) {
            view.setImageResource(R.drawable.logo_samsung);
        } else if (man.contains("google")) {
            view.setImageResource(R.drawable.logo_google);
        } else {
            view.setImageResource(R.drawable.logo_generic);
        }
    }

    public void launchCpu(View v) { startActivity(new Intent(this, CpuActivity.class)); }
    public void launchNetwork(View v) { startActivity(new Intent(this, NetworkActivity.class)); }
    public void launchBattery(View v) { startActivity(new Intent(this, BatteryActivity.class)); }
    public void launchSystem(View v) { startActivity(new Intent(this, SystemActivity.class)); }
    public void launchDisplay(View v) { startActivity(new Intent(this, DisplayActivity.class)); }
}
