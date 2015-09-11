package com.example.user.bumpmap1;

/**
 * Created by user on 9/11/2015.
 */
import android.content.Context;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by user on 9/6/2015.
 */
public class SquareRenderer implements GLSurfaceView.Renderer {

    public SquareRenderer(boolean useTransclucentBackground,Context context)
    {
        mTranslucentBackground=useTransclucentBackground;
        mSquare=new Square();
        this.mContext = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {



        int resid1 = R.drawable.earthnormal;
        int resID2 = R.drawable.earth;
        mSquare.createTexture(gl,this.mContext,resid1,resID2);

        gl.glDisable(GL10.GL_DITHER);

        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
        if(mTranslucentBackground)
        {
            gl.glClearColor(0,0,0,0);
        }
        else
        {
            gl.glClearColor(1,1,1,1);
        }
        gl.glDisable(GL10.GL_CULL_FACE);
        gl.glShadeModel(GL10.GL_SMOOTH);
        // gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glDisable(GL10.GL_DEPTH_TEST);

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {


        gl.glViewport(0,0,width,height);

        float ratio = (float)width/(float)height;

        gl.glMatrixMode(GL10.GL_PROJECTION);

        gl.glLoadIdentity();

        gl.glFrustumf(-ratio,ratio,-1,1,1,10);

    }

    @Override
    public void onDrawFrame(GL10 gl) {

        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

        gl.glMatrixMode(GL10.GL_MODELVIEW);


        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);



        gl.glDisable(GL10.GL_CULL_FACE);
        gl.glDisable(GL10.GL_DEPTH_TEST);


        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);









        /// sqaure 2

        /*

        gl.glLoadIdentity();
        gl.glTranslatef((float) (Math.sin(mTransY) / 2.0f), 0.0f, -2.90f);
        gl.glColor4f(1.0f, 0.0f, 0.0f, 0.5f);
        mSquare.draw(gl);

        */

        ////sqaure 1;
        gl.glLoadIdentity();
        gl.glTranslatef(0.0f, mTransY, -3.0f);
        gl.glColor4f(0.0f, 0.0f, 1.0f, 0.5f);
        mSquare.draw(gl);




        //  gl.glEnableClientState(GL10.GL_COLOR_ARRAY);



       // mTransY+=0.075;

    }



    private boolean mTranslucentBackground;
    private Square mSquare;
    private float mTransY;
    private float mAngle;

    private Context mContext;
}
