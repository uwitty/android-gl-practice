package com.example.u.myglapplication2;

import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private GLSurfaceView glView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        glView = (GLSurfaceView) findViewById(R.id.glView);
        glView.setEGLContextClientVersion(2);
        glView.setRenderer(new RotateImageRenderer());
        glView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        glView.onResume();
    }

    @Override
    protected void onPause()
    {
        super.onPause();

        glView.onPause();
    }
}
