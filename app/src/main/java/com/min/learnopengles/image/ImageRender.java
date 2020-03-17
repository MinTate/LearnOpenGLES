package com.min.learnopengles.image;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

import com.min.learnopengles.utils.GLESUtils;
import com.min.learnopengles.utils.Utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class ImageRender implements GLSurfaceView.Renderer {

    private static final String TAG = "ImageRender";
    private static final String mVertexShaderPath = "shaders/color_vertex.glsl";
    private static final String mFragmentShaderPath = "shaders/color_fragment.glsl";

    private static final float[] mVertex = {
            -1.0f, 1.0f,
            -1.0f, -1.0f,
            1.0f, 1.0f,
            1.0f, -1.0f
    };

    private static final float[] mTextureCoord = {
            0.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 0.0f,
            1.0f, 1.0f,
    };

    private FloatBuffer mVertexBuffer;
    private FloatBuffer mTextureCoordBuffer;

    private int mTextureId;

    private int mProgram;
    private int mPositionHandle;
    private int mTextureCoordHandle;
    private int mTextureHandle;
    private int mMatrixHandle;

    private float[] mViewMatrix = new float[16];
    private float[] mProjectMatrix = new float[16];
    private float[] mMVPMatrix = new float[16];

    private Bitmap mBitmap;

    public ImageRender() {
        mVertexBuffer = ByteBuffer.allocateDirect(mVertex.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(mVertex);
        mVertexBuffer.position(0);

        mTextureCoordBuffer = ByteBuffer.allocateDirect(mTextureCoord.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(mTextureCoord);
        mTextureCoordBuffer.position(0);
    }

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GLES20.glEnable(GLES20.GL_TEXTURE_2D);

        String vertexShaderSource = Utils.loadFromAssetsFile(mVertexShaderPath);
        String fragmentShaderSource = Utils.loadFromAssetsFile(mFragmentShaderPath);
        mProgram = GLESUtils.buildProgram(vertexShaderSource, fragmentShaderSource);
        GLES20.glUseProgram(mProgram);

        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        mTextureCoordHandle = GLES20.glGetAttribLocation(mProgram, "vCoordinate");
        mTextureHandle = GLES20.glGetUniformLocation(mProgram, "vTexture");
        mMatrixHandle = GLES20.glGetUniformLocation(mProgram, "vMatrix");

        GLES20.glEnableVertexAttribArray(mPositionHandle);
        GLES20.glVertexAttribPointer(mPositionHandle, 2, GLES20.GL_FLOAT, false,
                8, mVertexBuffer);

        GLES20.glEnableVertexAttribArray(mTextureCoordHandle);
        GLES20.glVertexAttribPointer(mTextureCoordHandle, 2, GLES20.GL_FLOAT, false,
                8, mTextureCoordBuffer);

        mTextureId = GLESUtils.createTexture(mBitmap);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0 + 0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureId);
        GLES20.glUniform1i(mTextureHandle, 0);
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        int w = mBitmap.getWidth();
        int h = mBitmap.getHeight();
        float sWH = w / (float) h;
        float sWidthHeight = width / (float) height;
        if (width > height) {
            if (sWH > sWidthHeight) {
                Matrix.orthoM(mProjectMatrix, 0, -sWidthHeight * sWH, sWidthHeight * sWH, -1, 1, 0, 10);
            } else {
                Matrix.orthoM(mProjectMatrix, 0, -sWidthHeight / sWH, sWidthHeight / sWH, -1, 1, 0, 10);
            }
        } else {
            if (sWH > sWidthHeight) {
                Matrix.orthoM(mProjectMatrix, 0, -1, 1, -1 / sWidthHeight * sWH, 1 / sWidthHeight * sWH, 0, 10);
            } else {
                Matrix.orthoM(mProjectMatrix, 0, -1, 1, -sWH / sWidthHeight, sWH / sWidthHeight, 0, 10);
            }
        }
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, 5.0f, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectMatrix, 0, mViewMatrix, 0);

        for (int i = 0; i < mMVPMatrix.length; i++) {
            Log.d(TAG, "mMVPMatrix " + i + ": " + mMVPMatrix[i]);
        }
        GLES20.glUniformMatrix4fv(mMatrixHandle, 1, false, mMVPMatrix, 0);
    }

    @Override
    public void onDrawFrame(GL10 unused) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
    }

    public void setBitmap(Bitmap bitmap) {
        this.mBitmap = bitmap;
    }
}
