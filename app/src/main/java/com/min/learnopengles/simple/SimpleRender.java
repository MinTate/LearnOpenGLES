package com.min.learnopengles.simple;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.min.learnopengles.utils.GLESUtils;
import com.min.learnopengles.utils.Utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class SimpleRender implements GLSurfaceView.Renderer {

    private static final int FLOAT_SIZE_BYTES = 4;

    private static final String VERTEX_SHADER =
            "attribute vec4 vPosition;\n"
                    + "void main() {\n"
                    + "  gl_Position = vPosition;\n"
                    + "}";
    private static final String FRAGMENT_SHADER =
            "precision mediump float;\n"
                    + "void main() {\n"
                    + "  gl_FragColor = vec4(1, 0, 0, 1);\n"
                    + "}";
    private static final float[] VERTEX = {   // in counterclockwise order:
            -1, 0.5f, 0,
            -1, -0.5f, 0,
            1, -0.5f, 0,
            1, 0.5f, 0,
    };

    private final FloatBuffer mVertexBuffer;

    private int mProgram;
    private int mPositionHandle;

    public SimpleRender() {
        mVertexBuffer = ByteBuffer.allocateDirect(VERTEX.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(VERTEX);
        mVertexBuffer.position(0);
    }

    static int loadShader(int type, String shaderCode) {
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);
        return shader;
    }

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        mProgram = GLESUtils.buildProgram(VERTEX_SHADER, FRAGMENT_SHADER);
        GLES20.glUseProgram(mProgram);

        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

        GLES20.glEnableVertexAttribArray(mPositionHandle);
        GLES20.glVertexAttribPointer(mPositionHandle, 3, GLES20.GL_FLOAT, false,
                3 * FLOAT_SIZE_BYTES, mVertexBuffer);
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 unused) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 4);
    }
}
