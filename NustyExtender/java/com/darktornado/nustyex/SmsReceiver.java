package com.darktornado.nustyex;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SmsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context ctx, Intent intent) {
        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            Bundle bundle = intent.getExtras();
            if (bundle == null) return;
            Object[] pdus = (Object[]) bundle.get("pdus");
            SmsMessage[] msgs = new SmsMessage[pdus.length];
            for (int n = 0; n < msgs.length; n++) {
                msgs[n] = SmsMessage.createFromPdu((byte[]) pdus[n]);
                String number = msgs[n].getOriginatingAddress();
                String msg = msgs[n].getMessageBody();
//                Toast.makeText(ctx, "[NustyEx]\nSender : " + number + "\nMessage : " + msg, Toast.LENGTH_SHORT).show();
                if (Nusty.rootLoad(ctx, "receive_sms", false)) sendDataToNusty(ctx, number, msg);
            }
        }
    }

    private void sendDataToNusty(Context ctx, String sender, String msg) {
        try {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName(Nusty.HOST_PACKAGE_NAME, Nusty.HOST_PACKAGE_NAME + "." + Nusty.HOST_RECEIVER_NAME));
            intent.putExtra(Nusty.REQUEST_TYPE_SMS_NUMBER, sender);
            intent.putExtra(Nusty.REQUEST_TYPE_SMS_MSG, msg);
            ctx.startService(intent);
        } catch (Exception e) {
            Toast.makeText(ctx, "Nusty가 설치되어있지 않은 것 같습니다.\n" + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
