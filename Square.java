package com.example.user.bumpmap1;

/**
 * Created by user on 9/11/2015.
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

/**
 * Created by user on 9/6/2015.
 */
public class Square {

    private FloatBuffer mFVertexBuffer;
    private ByteBuffer mColorBuffer;
    private ByteBuffer mIndexBuffer;

    public  FloatBuffer mTextureBuffer;


    float texincrease=0.3f;



    public int[] mTexture0 = new int[1];
    public int[] mTexture1= new int[1];

    static float lightAngle=0.0f;

    public void muktiTextureBumpMap(GL10 gl,int mainTexture,int normalTexture)
    {

        float x,y,z;
        lightAngle+= 0.3f;

        if(lightAngle > 180)
            lightAngle=0;

        //set up the light vector.
        x=(float)Math.sin(lightAngle*(3.14159/180.0f));
        y=0.0f;
        z=(float)Math.cos(lightAngle*(3.141559/180.0f));

        //half shifting
        x= x*0.5f + 0.5f;
        y= y*0.5f + 0.5f;
        z= z*0.5f + 0.5f;

        gl.glColor4f(x,y,z,1.0f);

        //the color and normal map are combined.

        gl.glActiveTexture(GL10.GL_TEXTURE0);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, mainTexture);

        gl.glTexEnvf(GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE, GL11.GL_COMBINE);
        gl.glTexEnvf(GL10.GL_TEXTURE_ENV,GL11.GL_COMBINE_RGB, GL11.GL_DOT3_RGB);
        gl.glTexEnvf(GL10.GL_TEXTURE_ENV,GL11.GL_SRC0_RGB, GL11.GL_TEXTURE);
        gl.glTexEnvf(GL10.GL_TEXTURE_ENV,GL11.GL_SRC1_RGB, GL11.GL_PREVIOUS);

        //set up the second texture and combine it with the result of thr DOT3 combination.

        gl.glActiveTexture(GL10.GL_TEXTURE1);
        gl.glBindTexture(GL10.GL_TEXTURE_2D,normalTexture);

        gl.glTexEnvf(GL10.GL_TEXTURE_ENV,GL10.GL_TEXTURE_ENV_MODE,GL10.GL_MODULATE);

    }




    public void draw(GL10 gl) {


        gl.glEnable(GL10.GL_TEXTURE_2D);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, mTexture0[0]);
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

        gl.glFrontFace(GL11.GL_CW);
        gl.glVertexPointer(2, GL11.GL_FLOAT, 0, mFVertexBuffer);
        gl.glColorPointer(4, GL11.GL_FLOAT, 0, mColorBuffer);

        gl.glClientActiveTexture(GL10.GL_TEXTURE0);
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);

        gl.glClientActiveTexture(GL10.GL_TEXTURE1);
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);

     //   multiTexture(gl, mTexture0[0], mTexture1[0]);

        muktiTextureBumpMap(gl,mTexture0[0],mTexture1[0]);
        gl.glDrawElements(GL11.GL_TRIANGLES,6,GL11.GL_UNSIGNED_BYTE,mIndexBuffer);
        gl.glFrontFace(GL11.GL_CCW);
    }



    public Square()
    {
        float vertices[]=
                {
                        -1.0f,-1.0f,
                        1.0f,-1.0f,
                        -1.0f,1.0f,
                        1.0f,1.0f

                };
        byte maxColor =(byte)255;

        byte color[]=
                {
                        maxColor,maxColor,0,maxColor,
                        0,maxColor,maxColor,maxColor,
                        0,0,0,maxColor,
                        maxColor,0,maxColor,maxColor
                };
        byte indices[]=
                {
                        0,3,1,
                        0,2,3
                };

        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());
        mFVertexBuffer= vbb.asFloatBuffer();
        mFVertexBuffer.put(vertices);
        mFVertexBuffer.position(0);

        mColorBuffer = ByteBuffer.allocateDirect(color.length);
        mColorBuffer.put(color);
        mColorBuffer.position(0);


        mIndexBuffer=ByteBuffer.allocateDirect(indices.length);
        mIndexBuffer.put(indices);
        mIndexBuffer.position(0);


        float [] textureCoords =
                {
                        0.0f,0.0f,
                        1.0f,0.0f,
                        0.0f,1.0f,
                        1.0f,1.0f
                };

        ByteBuffer tbb = ByteBuffer.allocateDirect(textureCoords.length*4);
        tbb.order(ByteOrder.nativeOrder());
        mTextureBuffer = tbb.asFloatBuffer();
        mTextureBuffer.put(textureCoords);
        mTextureBuffer.position(0);

    }




    public int createTexture(GL10 gl,Context contextRegf,int resource1 , int resource2)
    {
        Bitmap image1 = BitmapFactory.decodeResource(contextRegf.getResources(), resource1);
        gl.glGenTextures(1, mTexture0,0);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, mTexture0[0]);

        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, image1, 0);

        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);

        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);

        image1.recycle();




        Bitmap image2 = BitmapFactory.decodeResource(contextRegf.getResources(), resource2);
        gl.glGenTextures(1,mTexture1,0);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, mTexture1[0]);

        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, image2, 0);

        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);

        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);

        image2.recycle();






        return  resource1;
    }
}
