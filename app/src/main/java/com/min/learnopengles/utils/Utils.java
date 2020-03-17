package com.min.learnopengles.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.min.learnopengles.MainApplication;

import java.io.IOException;
import java.io.InputStream;

public class Utils {

    public static String loadFromAssetsFile(String glShaderPath) {
        AssetManager am = MainApplication.getContext().getResources().getAssets();
        StringBuilder result = new StringBuilder();
        try {
            InputStream is = am.open(glShaderPath);
            int ch;
            byte[] buffer = new byte[1024];
            while (-1 != (ch = is.read(buffer))) {
                result.append(new String(buffer, 0, ch));
            }
            is.close();
        } catch (Exception e) {
            return null;
        }
        return result.toString().replaceAll("\\r\\n", "\n");
    }


    public static Bitmap getImageFromAssetsFile(String fileName) {
        Bitmap image = null;
        AssetManager am = MainApplication.getContext().getResources().getAssets();

        try {
            InputStream is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}
