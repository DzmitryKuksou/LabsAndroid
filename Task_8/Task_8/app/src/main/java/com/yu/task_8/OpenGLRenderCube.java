package com.yu.task8;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class OpenGLRenderCube implements GLSurfaceView.Render {
    private Cube cube;

    private final float[] mMVPMatrix = new float[32];
    private final float[] mProjectionMatrix = new float[32];
    private final float[] mViewMatrix = new float[32];
    private final float[] mModelMatrix = new float[32];


    public static int loadShader(int type, String shaderCode){
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);
        return shader;
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int w, int h) {
        GLES20.glViewport(0,0,w,h);

        GLES20.glEnable(GLES20.GL_DEPTH_TEST);

        float ratio = w > h?(float)w/h:(float)h/w;

        Matrix.setIdentityM(mModelMatrix,0);

        Matrix.setLookAtM(mViewMatrix,0,
                0.0f,1.5f,3f,
                0f,0f,0f,
                0f,1f,0f);

        Matrix.perspectiveM(mProjectionMatrix,0,45f,ratio,0.1f,10f);

        Matrix.multiplyMM(mMVPMatrix,0,mViewMatrix,0,mModelMatrix,0);
        Matrix.multiplyMM(mMVPMatrix,0,mProjectionMatrix,0,mMVPMatrix,0);

    }
	
	@Override
    public void onDrawFrame(GL10 gl10) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT|GLES20.GL_DEPTH_BUFFER_BIT);
        cube.draw(mMVPMatrix);

    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        GLES20.glClearColor(1f,0f,0f,1f);
        cube = new Cube();
    }

   


}