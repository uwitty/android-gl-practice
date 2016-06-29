package com.example.u.myglapplication2;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by u1 on 2016/06/12.
 */
public class RotateImageRenderer implements GLSurfaceView.Renderer {
    private long lastTime = 0;
    private GLProgram glProgram;
    private Triangle triangle;

    private static class PVMatrix
    {
        private final float[] projectionMatrix = new float[16];
        private final float[] viewMatrix = new float[16];
    }
    private final PVMatrix pvMatrix = new PVMatrix();

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        initGL();
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        //GLES20.glClearColor(0.125f, 0.25f, 0.5f, 0.5f);
        GLES20.glClearColor(0.8f, 0.8f, 0.7f, 1.0f);

        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / height;

        Matrix.frustumM(pvMatrix.projectionMatrix, 0, -ratio, ratio, -1, 1, 1, 100);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        long currentTime = System.currentTimeMillis();
        long deltaTime = (lastTime != 0)? currentTime - lastTime : 0;
        lastTime = currentTime;

        // Set the camera position (View matrix)
        Matrix.setLookAtM(pvMatrix.viewMatrix, 0,
                0, 0, -2,
                0f, 0f, 0f,
                0f, 1.0f, 0.0f);

        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT |  GLES20.GL_DEPTH_BUFFER_BIT);

        GLES20.glUseProgram(glProgram.program);

        float[] vpMatrix = new float[16];
        Matrix.multiplyMM(vpMatrix, 0, pvMatrix.projectionMatrix, 0, pvMatrix.viewMatrix, 0);
        GLES20.glUniformMatrix4fv(glProgram.vpMatrixHandle, 1, false, vpMatrix, 0);

        triangle.draw(glProgram, deltaTime / 1000.0f);
    }

    private void initGL()
    {
        this.glProgram = new GLProgram();
        GLES20.glPixelStorei(GLES20.GL_UNPACK_ALIGNMENT, 1);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        GLES20.glEnable(GLES20.GL_BLEND);

        //byte[] pixels = {85, (byte)128, (byte)192, (byte)0xff};
        //byte[] pixels = {(byte)0xc0, (byte)0x80, (byte)0x40, (byte)0xff};
        byte[] pixels = { (byte)0xc0, (byte)0x80, (byte)0x40, (byte)0xff
                        , (byte)0x40, (byte)0x80, (byte)0xc0, (byte)0xff};
        int texture = GLUtil.create_texture(1, 2, pixels);
        triangle = new Triangle(texture);
    }
}
