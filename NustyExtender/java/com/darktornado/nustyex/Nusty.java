package com.darktornado.nustyex;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class Nusty {

    public static final String HOST_PACKAGE_NAME = "com.darktornado.nusty";
    public static final String HOST_RECEIVER_NAME = "NustyEx";
    public static final String REQUEST_TYPE = "nusty.extender.type";
    public static final int REQUEST_TYPE_SEND_SMS = 0;
    public static final int REQUEST_TYPE_WIFI_ON = 1;
    public static final int REQUEST_TYPE_WIFI_OFF = 2;
    public static final String REQUEST_TYPE_SMS_NUMBER = "nusty.send_sms.phone_number";
    public static final String REQUEST_TYPE_SMS_MSG = "nusty.send_sms.sms_body";

    public static String getVersion(){
        return BuildConfig.VERSION_NAME;
    }

    public static boolean saveFile(String path, String value) {
        try {
            File file = new File(path);
            FileOutputStream fos = new java.io.FileOutputStream(file);
            fos.write(value.getBytes());
            fos.close();
            return true;
        } catch (Exception e) {
            //toast(e.toString());
        }
        return false;
    }

    public static String readFile(String path) {
        try {
            File file = new File(path);
            if (!file.exists()) return null;
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String str = br.readLine();
            String line = "";
            while ((line = br.readLine()) != null) {
                str += "\n" + line;
            }
            fis.close();
            isr.close();
            br.close();
            return str;
        } catch (Exception e) {
            //toast(e.toString());
        }
        return null;
    }

    public static boolean rootSave(Context ctx, String name, String value) {
        String root = ctx.getFilesDir().getPath();
        return saveFile(root + "/" + name, value);
    }

    public static boolean rootSave(Context ctx, String name, boolean settings) {
        String root = ctx.getFilesDir().getPath();
        return saveFile(root + "/" + name + ".txt", String.valueOf(settings));
    }

    public static String rootRead(Context ctx, String name) {
        String root = ctx.getFilesDir().getPath();
        return readFile(root + "/" + name);
    }

    public static boolean rootLoad(Context ctx, String name, boolean defaultSettings) {
        String data = rootRead(ctx, name + ".txt");
        if (data == null) return defaultSettings;
        return data.equals("true");
    }
}
