package com.codingtrolling.cpuchecker;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.codingtrolling.cpuchecker.utils.AbiDetector;

public class MainActivity extends AppCompatActivity {
    private TextView output;
    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        output = findViewById(R.id.cpu_terminal_output);
        runDiagnostic();
    }

    private void runDiagnostic() {
        new Thread(() -> {
            String abis = AbiDetector.getSupportedAbis();
            String gov = HardwareScanner.getGovernor();
            String temp = HardwareScanner.getCpuTemp();
            String board = HardwareScanner.getBoard();
            String brand = HardwareScanner.getManufacturer();
            String info = HardwareScanner.getCpuInfo();

            handler.post(() -> {
                StringBuilder sb = new StringBuilder();
                sb.append(">> CORE_DIAGNOSTIC_COMPLETE\n");
                sb.append("MANUFACTURER: ").append(brand).append("\n");
                sb.append("BOARD: ").append(board).append("\n");
                sb.append("ABI: ").append(abis).append("\n");
                sb.append("GOVERNOR: ").append(gov).append("\n");
                sb.append("TEMP: ").append(temp).append("\n");
                sb.append("---------------------------\n");
                sb.append(info);
                output.setText(sb.toString());
            });
        }).start();
    }
}
