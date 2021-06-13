package com.darktornado.nustyex;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.telephony.SmsManager;

public class NustyRequestListener extends NotificationListenerService {

    private Handler _handler;

    @Override
    public void onCreate() {
        _handler = new Handler();
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
        if (!sbn.getPackageName().equals(Nusty.HOST_PACKAGE_NAME)) return;
        Bundle data = sbn.getNotification().extras;
        int type = data.getInt(Nusty.REQUEST_TYPE, 0);
        onActionRequested(type, data);
//        sbn.getNotification().actions[0].actionIntent.send();
    }

    private void onActionRequested(int type, Bundle data) {
        try {
            WifiManager wm;
            switch (type) {
                case Nusty.REQUEST_TYPE_SEND_SMS:
                    if (!Nusty.rootLoad(this, "send_sms", false)) return;
                    String number = data.getString(Nusty.REQUEST_TYPE_SMS_NUMBER);
                    String msg = data.getString(Nusty.REQUEST_TYPE_SMS_MSG);
                    SmsManager.getDefault().sendTextMessage(number, null, msg, null, null);
                    toast("[NustyEx] 문자 전송 요청이 감지되어, 문자를 전송했어요.\n전화번호 : " + number + "\n내용 : " + msg);
                    break;
                case Nusty.REQUEST_TYPE_WIFI_ON:
                    if (!Nusty.rootLoad(this, "wifi", false)) return;
                    wm = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
                    wm.setWifiEnabled(true);
                    toast("[NustyEx] 와이파이 제어 요청이 감지되어 와이파이를 켰어요.");
                    break;
                case Nusty.REQUEST_TYPE_WIFI_OFF:
                    if (!Nusty.rootLoad(this, "wifi", false)) return;
                    wm = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
                    wm.setWifiEnabled(false);
                    toast("[NustyEx] 와이파이 제어 요청이 감지되어 와이파이를 껐어요.");
                    break;
            }
        } catch (Exception e) {
            toast(e.toString());
        }
    }

    public void runOnUIThread(Runnable runnable) {
        _handler.post(runnable);
    }

    private void toast(String msg) {
        Intent intent = new Intent(this, ToastService.class);
        intent.putExtra("msg", msg);
        startService(intent);
    }

}
