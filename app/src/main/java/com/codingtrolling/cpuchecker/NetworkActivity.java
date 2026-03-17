package com.codingtrolling.cpuchecker;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

public class NetworkActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BrandUtils.applyLogo((ImageView) findViewById(R.id.activity_logo));
        TextView output = findViewById(R.id.cpu_terminal_output);

        StringBuilder sb = new StringBuilder();
        sb.append("--- NETWORK ANALYZER ---\n");

        try {
            WifiManager wm = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wm.getConnectionInfo();
            
            sb.append(String.format("%-18s %s\n", "SSID", info.getSSID()));
            sb.append(String.format("%-18s %d Mbps\n", "Link Speed", info.getLinkSpeed()));
            sb.append(String.format("%-18s %s\n", "Local IP", getIPAddress()));
        } catch (Exception e) {
            sb.append("Error: Check Permissions (Location/WiFi)\n");
        }

        output.setText(sb.toString());
    }

    private String getIPAddress() {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress();
                        if (sAddr.indexOf(':') < 0) return sAddr;
                    }
                }
            }
        } catch (Exception ignored) { }
        return "Unavailable";
    }
}
