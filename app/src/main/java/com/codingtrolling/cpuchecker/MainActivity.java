package com.codingtrolling.cpuchecker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu); // You'll need a menu layout with buttons
    }

    public void launchCpu(View v) {
        startActivity(new Intent(this, CpuActivity.class));
    }

    public void launchNetwork(View v) {
        startActivity(new Intent(this, NetworkActivity.class));
    }
}
