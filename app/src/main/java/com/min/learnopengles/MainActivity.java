package com.min.learnopengles;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.min.learnopengles.image.ImageActivity;
import com.min.learnopengles.mirror.ImageMirrorActivity;
import com.min.learnopengles.simple.SimpleActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private Button mHandleSimpleBtn;
    private Button mHandleImageBtn;
    private Button mHandleImageMorrirBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHandleSimpleBtn = findViewById(R.id.handle_simple);
        mHandleSimpleBtn.setOnClickListener(this);

        mHandleImageBtn = findViewById(R.id.handle_image);
        mHandleImageBtn.setOnClickListener(this);

        mHandleImageMorrirBtn = findViewById(R.id.handle_mirror);
        mHandleImageMorrirBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (mHandleSimpleBtn == view) {
            Intent intent = new Intent(this, SimpleActivity.class);
            startActivity(intent);
        }

        if (mHandleImageBtn == view) {
            Intent intent = new Intent(this, ImageActivity.class);
            startActivity(intent);
        }

        if (mHandleImageMorrirBtn == view) {
            Intent intent = new Intent(this, ImageMirrorActivity.class);
            startActivity(intent);
        }
    }
}
