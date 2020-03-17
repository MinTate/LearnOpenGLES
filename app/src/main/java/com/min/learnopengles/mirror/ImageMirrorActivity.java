package com.min.learnopengles.mirror;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.min.learnopengles.R;
import com.min.learnopengles.utils.Utils;

public class ImageMirrorActivity extends AppCompatActivity {

    private GLSurfaceView mGLSurfaceView;
    private Bitmap mBitmap;

    private ImageRender mImageRender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        mGLSurfaceView = findViewById(R.id.gl_surface);

        mImageRender = new ImageRender();
        mBitmap = Utils.getImageFromAssetsFile("images/scarlett.jpg");
        mImageRender.setBitmap(mBitmap);

        mGLSurfaceView.setEGLContextClientVersion(2);
        mGLSurfaceView.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        mGLSurfaceView.setRenderer(mImageRender);
        mGLSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGLSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGLSurfaceView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(mBitmap!=null && !mBitmap.isRecycled()){
            mBitmap.recycle();
        }
    }
}
