package com.codingtrolling.cpuchecker;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        
        ImageView brandLogo = findViewById(R.id.menu_brand_logo);
        applyMotherboardLogo(brandLogo);
    }

    private void applyMotherboardLogo(ImageView view) {
        String man = Build.MANUFACTURER.toLowerCase();
        
        if (man.contains("xiaomi") || man.contains("redmi") || man.contains("poco")) {
            view.setImageResource(R.drawable.logo_xiaomi);
        } else if (man.contains("samsung")) {
            view.setImageResource(R.drawable.logo_samsung);
        } else if (man.contains("oppo") || man.contains("realme")) {
            view.setImageResource(R.drawable.logo_oppo);
        } else if (man.contains("google")) {
            view.setImageResource(R.drawable.logo_google);
        } else if (man.contains("motorola") || man.contains("lenovo")) {
            view.setImageResource(R.drawable.logo_motorola);
        } else if (man.contains("huawei") || man.contains("honor")) {
            view.setImageResource(R.drawable.logo_huawei);
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
