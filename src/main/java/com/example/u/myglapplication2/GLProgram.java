package com.example.u.myglapplication2;

import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;
import android.widget.Toast;

public class GLProgram
{
    public final int program;
    public final int positionHandle;
    public final int texturePositionHandle;
    public final int textureHandle;
    public final int vpMatrixHandle;
    public final int mMatrixHandle;

    public static final String vertexShaderCode =
            "uniform mat4 uVPMatrix;" +
            "uniform mat4 uMMatrix;" +
            "attribute vec4 a_position;" +
            "attribute vec2 a_texture;" +
            "varying   vec2 v_texture;" +
            "void main() {" +
            "  v_texture  = a_texture;" +
            "  gl_Position = uVPMatrix * uMMatrix * a_position;" +
            "}";

    public static final String fragmentShaderCode =
            "precision mediump float;" +
            "uniform sampler2D u_texture;" +
            "varying   vec2 v_texture;" +
            "void main() {" +
            //"  gl_FragColor = vec4(0.5, 0.5, 0.5, 0.5);" +
            "  gl_FragColor = texture2D(u_texture, vec2(v_texture.s, v_texture.t));" +
            "}";

    public GLProgram()
    {
        int vshader = compileShader(GLES20.GL_VERTEX_SHADER, GLProgram.vertexShaderCode);
        int fshader = compileShader(GLES20.GL_FRAGMENT_SHADER, GLProgram.fragmentShaderCode);

        int program = GLES20.glCreateProgram();
        GLES20.glAttachShader(program, vshader);
        GLES20.glAttachShader(program, fshader);
        GLES20.glLinkProgram(program);
        int [] is = new int[1];
        GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, is, 0);
        if (is[0] != 0) {
            Log.e("GLProgram", "failed to link program.");
        }

        this.program        = program;
        this.positionHandle = GLES20.glGetAttribLocation(program, "a_position");
        this.texturePositionHandle = GLES20.glGetAttribLocation(program, "a_texture");
        this.textureHandle  = GLES20.glGetUniformLocation(program, "u_texture");
        this.vpMatrixHandle = GLES20.glGetUniformLocation(program, "uVPMatrix");
        this.mMatrixHandle  = GLES20.glGetUniformLocation(program, "uMMatrix");
    }

    private static int compileShader(int type, String code)
    {
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, code);
        GLES20.glCompileShader(shader);

        return shader;
    }
}
