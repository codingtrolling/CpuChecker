package com.codingtrolling.cpuchecker;

import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SystemActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView output = findViewById(R.id.cpu_terminal_output);

        StringBuilder sb = new StringBuilder();
        sb.append("--- SYSTEM ARCHIVE ---\n");
        sb.append(String.format("%-18s %s\n", "Manufacturer", Build.MANUFACTURER.toUpperCase()));
        sb.append(String.format("%-18s %s\n", "Model", Build.MODEL));
        sb.append(String.format("%-18s %s\n", "Android Ver", Build.VERSION.RELEASE));
        sb.append(String.format("%-18s %s\n", "Security Patch", Build.VERSION.SECURITY_PATCH));
        sb.append(String.format("%-18s %s\n", "Hardware", Build.HARDWARE));
        sb.append(String.format("%-18s %s\n", "Board", Build.BOARD));
        sb.append("----------------------------\n");
        sb.append(String.format("%-18s %s\n", "Kernel", System.getProperty("os.version")));

        output.setText(sb.toString());
    }
}
