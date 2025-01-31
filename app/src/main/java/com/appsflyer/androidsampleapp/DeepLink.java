package com.appsflyer.androidsampleapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.appsflyer.AppsFlyerConversionListener;
import com.appsflyer.AppsFlyerLib;

import java.util.Map;

/**
 * Test this deep link with the link : https://androidsampleapp.onelink.me/Pvqj
 */

/**
 * run: $ adb shell am start -a android.intent.action.VIEW -d https://androidsampleapp.onelink.me/Pvqj
 */


public class DeepLink extends AppCompatActivity {


    final Handler handler = new Handler();

    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deep_link);


        /* Add this call to the tracker on each deep linked activity */

        //AppsFlyerLib.getInstance().sendDeepLinkData(this);
        AppsFlyerLib.getInstance().sendPushNotificationData(this);


        AppsFlyerLib.getInstance().registerConversionListener(this, new AppsFlyerConversionListener() {

            /* Returns the attribution data. Note - the same conversion data is returned every time per install */
            @Override
            public void onConversionDataSuccess(Map<String, Object> conversionData) {
                for (String attrName : conversionData.keySet()) {
                    Log.d("AppsFlyerLibCore.LOG_TAG", "attribute: " + attrName + " = " + conversionData.get(attrName));
                }
            }

            @Override
            public void onConversionDataFail(String errorMessage) {
                Log.d("AppsFlyerLibCore.LOG_TAG", "error onInstallConversionFailure : " + errorMessage);
            }


            /* Called only when a Deep Link is opened */
            @Override
            public void onAppOpenAttribution(Map<String, String> conversionData) {
                String attributionDataText = "Attribution Data: \n";
                for (String attrName : conversionData.keySet()) {
                    Log.d("AppsFlyerLibCore.LOG_TAG", "attribute: " + attrName + " = " +
                            conversionData.get(attrName));
                    attributionDataText += conversionData.get(attrName) + "\n";

                }
                setAttributionText(attributionDataText);
            }

            @Override
            public void onAttributionFailure(String errorMessage) {
                Log.d("AppsFlyerLibCore.LOG_TAG", "error onAttributionFailure : " + errorMessage);
            }
        });

    }


    /* Used to display the deep link data returned from onAppOpenAttribution */

    public void setAttributionText(final String data) {

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                TextView attributionTextView = findViewById(R.id.attributionText);
                attributionTextView.setText(data);
            }
        }, 2500);
    }

}
