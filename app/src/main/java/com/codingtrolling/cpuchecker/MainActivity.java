package com.codingtrolling.cpuchecker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        // Logo logic removed as per design request
    }

    public void launchCpu(View v) { startActivity(new Intent(this, CpuActivity.class)); }
    public void launchNetwork(View v) { startActivity(new Intent(this, NetworkActivity.class)); }
    public void launchBattery(View v) { startActivity(new Intent(this, BatteryActivity.class)); }
    public void launchSystem(View v) { startActivity(new Intent(this, SystemActivity.class)); }
    public void launchDisplay(View v) { startActivity(new Intent(this, DisplayActivity.class)); }
}
