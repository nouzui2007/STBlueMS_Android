/*
 * Copyright (c) 2017  STMicroelectronics – All rights reserved
 * The STMicroelectronics corporate logo is a trademark of STMicroelectronics
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * - Redistributions of source code must retain the above copyright notice, this list of conditions
 *   and the following disclaimer.
 *
 * - Redistributions in binary form must reproduce the above copyright notice, this list of
 *   conditions and the following disclaimer in the documentation and/or other materials provided
 *   with the distribution.
 *
 * - Neither the name nor trademarks of STMicroelectronics International N.V. nor any other
 *   STMicroelectronics company nor the names of its contributors may be used to endorse or
 *   promote products derived from this software without specific prior written permission.
 *
 * - All of the icons, pictures, logos and other images that are provided with the source code
 *   in a directory whose title begins with st_images may only be used for internal purposes and
 *   shall not be redistributed to any third party or modified in any way.
 *
 * - Any redistributions in binary form shall not include the capability to display any of the
 *   icons, pictures, logos and other images that are provided with the source code in a directory
 *   whose title begins with st_images.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY
 * AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER
 * OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY
 * OF SUCH DAMAGE.
 */

package com.st.BlueMS;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.st.BlueSTSDK.gui.AboutActivity;
import com.st.BlueSTSDK.gui.thirdPartyLibLicense.LibLicense;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.concurrent.Exchanger;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * Entry point activity, it show a full screen image for 2s and than the button for start the
 * scan or go in the privacy_policy/help activity
 */
public class MainActivity extends com.st.BlueSTSDK.gui.MainActivity {

    private static final String TAG = MainActivity.class.getName();

    private static final ArrayList<LibLicense> LIB_LICENSES = new ArrayList<>();
    static {
        LIB_LICENSES.add(new LibLicense("Android Design Support Library",R.raw.lic_android_support));
        LIB_LICENSES.add(new LibLicense("Android Support Library v13",R.raw.lic_android_support));
        LIB_LICENSES.add(new LibLicense("Android Annotations Support Library",R.raw.lic_android_support));
        LIB_LICENSES.add(new LibLicense("Android Support Card View Library",R.raw.lic_android_support));
        LIB_LICENSES.add(new LibLicense("Android Support Constraint Layout Library",R.raw.lic_android_support));
        LIB_LICENSES.add(new LibLicense("Android Support AppCompat Library",R.raw.lic_android_support));
        LIB_LICENSES.add(new LibLicense("Android Support GridLayout Library",R.raw.lic_android_support));
        LIB_LICENSES.add(new LibLicense("Android Support RecyclerView Library",R.raw.lic_android_support));
        LIB_LICENSES.add(new LibLicense("BlueSTSDK",R.raw.lic_bluestsdk));
        LIB_LICENSES.add(new LibLicense("BlueSTSDK Gui",R.raw.lic_bluestsdk_gui));
        LIB_LICENSES.add(new LibLicense("Paho",R.raw.lic_paho));
        LIB_LICENSES.add(new LibLicense("Play Services Base",R.raw.lic_android_support));
    }

    private static final String ABOUT_PAGE_URL = "file:///android_asset/about.html";

    private Socket mSocket;

    @Override
    public void startScanBleActivity(View view) {
        startActivity(new Intent(this, NodeListActivity.class));
    }

    @Override
    public void startAboutActivity(View view) {
        URL privacyPage = getPrivacyPolicyUrl();
        AboutActivity.startActivityWithAboutPage(this,ABOUT_PAGE_URL,privacyPage,LIB_LICENSES);
    }

    @Override
    public URL getPrivacyPolicyUrl(){
        return null;
    }


    @Override
    protected void onStart() {
        super.onStart();

        try {

            DemoApplication app = (DemoApplication)getApplication();
            mSocket = app.getSocket();
            mSocket.on(Socket.EVENT_CONNECT,onConnect);
            mSocket.on("published", onPublished);
            mSocket.connect();

            mSocket.emit("connected", "nouzui2007");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this,
                            "コネクト", Toast.LENGTH_LONG).show();
                }
            });
        }
    };

    private Emitter.Listener onPublished = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this,
                            "Published", Toast.LENGTH_LONG).show();
                }
            });
        }
    };


}
