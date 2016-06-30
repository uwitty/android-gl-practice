package com.example.u.myglapplication2;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class GLUtil {
    public static int create_texture(int width, int height, Buffer pixels) {
        int[] textures = new int[1];
        GLES20.glGenTextures(1, textures, 0);
        int texture = textures[0];
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture);

        int format = GLES20.GL_RGBA;
        GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, format , width, height, 0, format, GLES20.GL_UNSIGNED_BYTE, pixels);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
        return texture;
    }

    public static int create_texture(int width, int height, byte[] pixels) {
        return create_texture(width, height, createBuffer(pixels));
    }

    public static int create_texture(Bitmap bitmap) {
        int[] textures = new int[1];
        GLES20.glGenTextures(1, textures, 0);
        int texture = textures[0];
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture);

        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);

        //GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
        return texture;
    }
    public static Buffer createBuffer(byte[] bytes) {
        return ByteBuffer.allocateDirect(bytes.length).order(ByteOrder.nativeOrder()).put(bytes).position(0);
    }
}
