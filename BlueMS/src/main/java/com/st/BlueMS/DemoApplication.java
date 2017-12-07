package com.st.BlueMS;

import android.app.Application;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URISyntaxException;
import java.util.Enumeration;

import io.socket.client.IO;
import io.socket.client.Socket;

/**
 * Created by apple on 2017/12/06.
 */

public class DemoApplication extends Application {

    private static final String TAG = MainActivity.class.getName();

    private Socket mSocket;

    public Socket getSocket() {
        if (mSocket == null) {
            try {
                String ip = getWifiIPAddress(this);
                mSocket = IO.socket("http://" + ip + ":8080/");
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
        return mSocket;
    }

    // モバイルネットワークインターフェースのIPアドレスを取得する。
    // 注意：インターフェース2つとか、もろもろの異常系は考慮していない
    private static final String LOCAL_LOOPBACK_ADDR = "127.0.0.1";
    private static final String INVALID_ADDR = "0.0.0.0";

    private static String getMobileIPAddress() {
        try {
            NetworkInterface ni = NetworkInterface.getByName("hso0"); // インターフェース名
            if (ni == null) {
                Log.d(TAG, "Failed to get mobile interface.");
                return null;
            }

            Enumeration<InetAddress> addresses = ni.getInetAddresses();
            while (addresses.hasMoreElements()) {
                String address = addresses.nextElement().getHostAddress();
                if (!LOCAL_LOOPBACK_ADDR.equals(address) && !INVALID_ADDR.equals(address)) {
                    // Found valid ip address.
                    return address;
                }
            }
            return null;
        } catch (Exception e) {
            Log.d(TAG, "Exception occured. e=" + e.getMessage());
            return null;
        }
    }

    // Wi-FiインターフェースのIPアドレスを取得する。
    // 注意：インターフェース2つとか、もろもろの異常系は考慮していない
    private static String getWifiIPAddress(Context context) {
        WifiManager manager = (WifiManager) context.getSystemService(WIFI_SERVICE);
        WifiInfo info = manager.getConnectionInfo();
        int ipAddr = info.getIpAddress();
        String ipString = String.format("%02d.%02d.%02d.%02d",
                (ipAddr >> 0) & 0xff, (ipAddr >> 8) & 0xff, (ipAddr >> 16) & 0xff, (ipAddr >> 24) & 0xff);
        return ipString;
    }
}
