package com.networkpro.app;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.provider.Settings;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.widget.*;
import android.view.Gravity;
import android.graphics.Color;

public class MainActivity extends Activity {

    TextView info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(24, 30, 24, 24);
        layout.setBackgroundColor(Color.rgb(15, 23, 42));

        TextView title = new TextView(this);
        title.setText("NETWORK PRO");
        title.setTextSize(28);
        title.setTextColor(Color.WHITE);
        title.setGravity(Gravity.CENTER);
        title.setPadding(0, 20, 0, 25);
        layout.addView(title);

        TextView subtitle = new TextView(this);
        subtitle.setText("أداة معلومات وإعدادات الشبكة");
        subtitle.setTextSize(18);
        subtitle.setTextColor(Color.CYAN);
        subtitle.setGravity(Gravity.CENTER);
        layout.addView(subtitle);

        info = new TextView(this);
        info.setTextColor(Color.WHITE);
        info.setTextSize(16);
        info.setPadding(10, 30, 10, 30);
        layout.addView(info);

        Button refresh = new Button(this);
        refresh.setText("تحديث معلومات الشبكة");
        layout.addView(refresh);

        refresh.setOnClickListener(v -> showNetworkInfo());

        Button settings = new Button(this);
        settings.setText("فتح إعدادات الشبكة");
        layout.addView(settings);

        settings.setOnClickListener(v -> {
            try {
                startActivity(new Intent(
                    Settings.ACTION_WIRELESS_SETTINGS
                ));
            } catch (Exception e) {
                startActivity(new Intent(
                    Settings.ACTION_SETTINGS
                ));
            }
        });

        Button advanced = new Button(this);
        advanced.setText("إعدادات شبكة الهاتف");
        layout.addView(advanced);

        advanced.setOnClickListener(v -> {
            try {
                startActivity(new Intent(
                    "android.settings.NETWORK_OPERATOR_SETTINGS"
                ));
            } catch (Exception e) {
                startActivity(new Intent(
                    Settings.ACTION_SETTINGS
                ));
            }
        });

        setContentView(layout);
        showNetworkInfo();
    }

    private void showNetworkInfo() {
        try {
            TelephonyManager tm =
                (TelephonyManager) getSystemService(
                    Context.TELEPHONY_SERVICE
                );

            String network = tm.getNetworkOperatorName();
            String type = "غير معروف";

            switch (tm.getDataNetworkType()) {
                case TelephonyManager.NETWORK_TYPE_GPRS:
                case TelephonyManager.NETWORK_TYPE_EDGE:
                case TelephonyManager.NETWORK_TYPE_CDMA:
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                    type = "2G";
                    break;

                case TelephonyManager.NETWORK_TYPE_UMTS:
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                case TelephonyManager.NETWORK_TYPE_HSPA:
                    type = "3G";
                    break;

                case TelephonyManager.NETWORK_TYPE_LTE:
                    type = "4G LTE";
                    break;

                case TelephonyManager.NETWORK_TYPE_NR:
                    type = "5G";
                    break;
            }

            info.setText(
                "معلومات الشبكة\n\n" +
                "اسم المشغل: " + network + "\n\n" +
                "نوع اتصال البيانات: " + type
            );

        } catch (Exception e) {
            info.setText(
                "تعذر قراءة معلومات الشبكة.\n" +
                "قد يتطلب ذلك أذونات إضافية."
            );
        }
    }
}