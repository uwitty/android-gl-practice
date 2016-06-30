package com.example.u.myglapplication2;

import android.opengl.GLES20;
import android.opengl.Matrix;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Triangle {
    final static int NUM_VERTICIES = 3;
    public final int vertexCount = NUM_VERTICIES;
    public final int vertexStride = (3+2) * 4;
    public final float[] triangleCoords;
    private final FloatBuffer vertexBuffer;
    public int texture;

    private final float DEFAULT_COORDS[] = {
             0.0f,  (float)Math.sqrt(3) / 3.0f, 0.0f, 0.5f, 1.0f,   // top
            -0.5f, -(float)Math.sqrt(3) / 6.0f, 0.0f, 0.0f, 0.0f,   // bottom left
             0.5f, -(float)Math.sqrt(3) / 6.0f, 0.0f, 1.0f, 0.0f    // bottom right
    };

    public Triangle(int texture)
    {
        this.vertexBuffer = (FloatBuffer) ByteBuffer.allocateDirect(Float.SIZE * DEFAULT_COORDS.length).
                order(ByteOrder.nativeOrder()).
                asFloatBuffer().put(DEFAULT_COORDS).position(0);
        this.triangleCoords = DEFAULT_COORDS.clone();

        this.texture = texture;
    }

    float angle;
    public void draw(GLProgram program, float deltaTime)
    {
        float[] rotationMatrix = new float[16];
        angle += 45.0f * deltaTime;
        Matrix.setRotateM(rotationMatrix, 0, angle, 0.0f, 1.0f, 0.0f);
//        Matrix.setRotateM(rotationMatrix, 0, angle, 0.0f, 0.0f, 1.0f);

        GLES20.glEnableVertexAttribArray(program.positionHandle);
        GLES20.glEnableVertexAttribArray(program.texturePositionHandle);

        vertexBuffer.position(0);
        GLES20.glVertexAttribPointer(
                program.positionHandle, Triangle.NUM_VERTICIES,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer);

        vertexBuffer.position(3);
        GLES20.glVertexAttribPointer(
                program.texturePositionHandle, 2,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer);

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glUniform1i(program.textureHandle, 0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture);

        GLES20.glUniformMatrix4fv(program.mMatrixHandle, 1, false, rotationMatrix, 0);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);

        GLES20.glDisableVertexAttribArray(program.texturePositionHandle);
        GLES20.glDisableVertexAttribArray(program.positionHandle);
    }
}
