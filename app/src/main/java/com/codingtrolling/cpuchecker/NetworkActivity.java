package com.codingtrolling.cpuchecker;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class NetworkActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView output = findViewById(R.id.cpu_terminal_output);

        WifiManager wm = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wm.getConnectionInfo();

        String netData = "--- NETWORK ---\n" +
                        String.format("%-18s %s\n", "SSID", info.getSSID()) +
                        String.format("%-18s %d Mbps\n", "Link Speed", info.getLinkSpeed()) +
                        String.format("%-18s %d\n", "Network ID", info.getNetworkId());
        output.setText(netData);
    }
}
