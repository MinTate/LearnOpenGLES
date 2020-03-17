package com.min.learnopengles;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

/**
 * 应用基类，负责权限申请管理等等；
 */
public class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity";

    private static final String[] mPermissionList = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET,
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO};

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (missPermission(mPermissionList)) {
            ActivityCompat.requestPermissions(this, mPermissionList, 0);
        }
    }

    public boolean missPermission(String... permissions) {
        for (String permission : permissions) {
            if ((ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "requestCode= " + requestCode);
        for (int i = 0; i < permissions.length; i++) {
            Log.d(TAG, "i  " + i + " permission= " + permissions[i]);
        }
        switch (requestCode) {
            case 0:
                if (permissions[0].equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    if (grantResults.length <= 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                        finish();
                    }
                }
                if (permissions[1].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    if (grantResults.length <= 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                        finish();
                    }
                }
                if (permissions[2].equals(Manifest.permission.INTERNET)) {
                    if (grantResults.length <= 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                        finish();
                    }
                }
                if (permissions[3].equals(Manifest.permission.CAMERA)) {
                    if (grantResults.length <= 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                        finish();
                    }
                }
                if (permissions[4].equals(Manifest.permission.RECORD_AUDIO)) {
                    if (grantResults.length <= 0 || grantResults[1] != PackageManager.PERMISSION_GRANTED) {
                        finish();
                    }
                }
                break;
            default:
        }
    }
}
