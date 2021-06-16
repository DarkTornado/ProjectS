package com.darktornado.nustyex;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.darktornado.library.LineView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(1);
        int pad = dip2px(10);
        String[] menuS = {"SMS 발신", "SMS 수신", "와이파이 제어"};
        final String[] bools = {"send_sms", "receive_sms", "wifi"};
        Switch[] sws = new Switch[menuS.length];
        for(int n=0;n<menuS.length;n++){
            if(n>0){
                LineView line = new LineView(this);
                line.setColor(Color.LTGRAY);
                layout.addView(line);
            }
            sws[n] = new Switch(this);
            sws[n].setText(menuS[n]);
            sws[n].setTextSize(20);
            sws[n].setId(n);
            sws[n].setTextColor(Color.WHITE);
            sws[n].setChecked(Nusty.rootLoad(this, bools[n], false));
            sws[n].setPadding(pad, pad, pad, pad);
            sws[n].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton swit, boolean onoff) {
                    Nusty.rootSave(MainActivity.this, bools[swit.getId()], onoff);
                }
            });
            layout.addView(sws[n]);
        }
        String[] menus = {"알림 접근 허용", "앱 정보", "제작자 블로그"};
        TextView[] txts = new TextView[menus.length];
        for(int n=0;n<menus.length;n++){
            LineView line = new LineView(this);
            line.setColor(Color.LTGRAY);
            layout.addView(line);
            txts[n] = new TextView(this);
            txts[n].setText(menus[n]);
            txts[n].setTextSize(20);
            txts[n].setTextColor(Color.WHITE);
            txts[n].setId(n);
            txts[n].setPadding(pad, pad, pad, pad);
            txts[n].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uri;
                    switch (v.getId()) {
                        case 0:
                            startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
                            toast("알림 접근 허용 창으로 이동합니다.");
                            break;
                        case 1:
                            showDialog("앱 정보 / 도움말", "앱 이름 : Nusty Extender\n버전 : " + Nusty.getVersion() + "\n제작자 : Dark Tornado\n\n" +
                                    "  Google측의 정책에 따라 특정한 기능을 가진 앱은 Play 스토어에 올리거나, 올릴 수 있어도 기능이 작동하지 않기 때문에, 해당 기능들만 따로 문리시킨거에요.\n" +
                                    "  Nusty가 상단바에 요청을 담아 알림을 띄우면 Nusty Extender가 그 알림에 접근하여 요청한 작업을 수행하는 방식이에요");
                            break;
                        case 2:
                            uri = Uri.parse("https://github.com/DarkTornado/ProjectS");
                            startActivity(new Intent(Intent.ACTION_VIEW, uri));
                            break;
                        case 3:
                            uri = Uri.parse("https://blog.naver.com/dt3141592");
                            startActivity(new Intent(Intent.ACTION_VIEW, uri));
                            break;
                    }
                }
            });
            layout.addView(txts[n]);
        }
        int pad2 = dip2px(20);
        layout.setPadding(pad2, pad, pad2, pad);
        ScrollView scroll = new ScrollView(this);
        scroll.addView(layout);
        setContentView(scroll);
    }

    public void showDialog(String title, CharSequence msg) {
        try {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle(title);
            dialog.setMessage(msg);
            dialog.setNegativeButton("닫기", null);
            dialog.show();
        } catch (Exception e) {
            toast(e.toString());
        }
    }

    public int dip2px(int dips) {
        return (int) Math.ceil(dips * this.getResources().getDisplayMetrics().density);
    }

    public void toast(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG);
                toast.getView().setBackgroundColor(Color.argb(150, 0, 0, 0));
                int pad = dip2px(5);
                toast.getView().setPadding(pad, pad, pad, pad);
                toast.show();
            }
        });
    }

}
