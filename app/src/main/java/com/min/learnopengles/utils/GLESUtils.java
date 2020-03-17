package com.min.learnopengles.utils;

import android.graphics.Bitmap;
import android.opengl.GLUtils;

import static android.opengl.GLES20.*;

public class GLESUtils {

    // 根据类型编译着色器
    public static int compileShader(int type, String shaderCode) {
        // 根据不同的类型创建着色器 ID
        final int shaderObjectId = glCreateShader(type);
        if (shaderObjectId == 0) {
            return 0;
        }
        // 将着色器 ID 和着色器程序内容连接
        glShaderSource(shaderObjectId, shaderCode);
        // 编译着色器
        glCompileShader(shaderObjectId);
        // 以下为验证编译结果是否失败
        final int[] compileStatus = new int[1];
        glGetShaderiv(shaderObjectId, GL_COMPILE_STATUS, compileStatus, 0);
        if ((compileStatus[0] == 0)) {
            // 失败则删除
            glDeleteShader(shaderObjectId);
            return 0;
        }
        return shaderObjectId;
    }

    // 链接OpenGL 程序
    public static int linkProgram(int vertexShaderId, int fragmentShaderId) {
        // 创建 OpenGL 程序 ID
        final int programObjectId = glCreateProgram();
        if (programObjectId == 0) {
            return 0;
        }
        // 链接上 顶点着色器
        glAttachShader(programObjectId, vertexShaderId);
        // 链接上 片段着色器
        glAttachShader(programObjectId, fragmentShaderId);
        // 链接着色器之后，链接 OpenGL 程序
        glLinkProgram(programObjectId);
        final int[] linkStatus = new int[1];
        // 验证链接结果是否失败
        glGetProgramiv(programObjectId, GL_LINK_STATUS, linkStatus, 0);
        if (linkStatus[0] == 0) {
            // 失败则删除 OpenGL 程序
            glDeleteProgram(programObjectId);
            return 0;
        }
        return programObjectId;
    }

    // 创建 OpenGL 程序过程
    public static int buildProgram(String vertexShaderSource, String fragmentShaderSource) {

        int vertexShader = compileShader(GL_VERTEX_SHADER, vertexShaderSource);
        int fragmentShader = compileShader(GL_FRAGMENT_SHADER, fragmentShaderSource);

        int program = linkProgram(vertexShader, fragmentShader);

        if (validateProgram(program)) {
            return program;
        }
        return 0;
    }

    public static boolean validateProgram(int programObjectId) {
        glValidateProgram(programObjectId);
        final int[] validateStatus = new int[1];
        glGetProgramiv(programObjectId, GL_VALIDATE_STATUS, validateStatus, 0);
        return validateStatus[0] != 0;

    }

    /**
     * 创建纹理
     *
     * @param bitmap 需要传入纹理的bitmap
     */
    public static int createTexture(Bitmap bitmap) {
        int[] texture = new int[1];
        if (bitmap != null && !bitmap.isRecycled()) {
            // 生成纹理
            glGenTextures(1, texture, 0);
            // 绑定纹理
            glBindTexture(GL_TEXTURE_2D, texture[0]);
            // 设置缩小过滤为使用纹理中坐标最接近的一个像素的颜色作为需要绘制的像素颜色
            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
            // 设置放大过滤为使用纹理中坐标最接近的若干个颜色，通过加权平均算法得到需要绘制的像素颜色
            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
            // 设置环绕方向S，截取纹理坐标到[1/2n,1-1/2n]。将导致永远不会与border融合
            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
            // 设置环绕方向T，截取纹理坐标到[1/2n,1-1/2n]。将导致永远不会与border融合
            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
            // 根据以上指定的参数，生成一个2D纹理
            GLUtils.texImage2D(GL_TEXTURE_2D, 0, bitmap, 0);
            glBindTexture(GL_TEXTURE_2D, 0);
            return texture[0];
        }
        return 0;
    }
}
