package com.trollcorp.cpuchecker;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        TextView dataView = findViewById(R.id.cpu_data);
        String report = "Cores: " + CpuScanner.getCoreCount() + "\n\n" + CpuScanner.getCpuInfo();
        dataView.setText(report);
    }
}
