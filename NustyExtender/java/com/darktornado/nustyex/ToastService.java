package com.darktornado.nustyex;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.IBinder;
import android.widget.Toast;

public class ToastService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String msg = intent.getStringExtra("msg");
        if (msg != null) {
            Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG);
            toast.getView().setBackgroundColor(Color.argb(150, 0, 0, 0));
            int pad = dip2px(5);
            toast.getView().setPadding(pad, pad, pad, pad);
            toast.show();
        }
        stopSelf();
        return START_NOT_STICKY;
    }

    public int dip2px(int dips) {
        return (int) Math.ceil(dips * this.getResources().getDisplayMetrics().density);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
