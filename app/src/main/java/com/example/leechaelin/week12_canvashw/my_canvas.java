package com.example.leechaelin.week12_canvashw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by leechaelin on 2017. 5. 18..
 */

public class my_canvas extends View {
    Bitmap mBitmap;
    Canvas mCanvas;
    Paint mpaint = new Paint();
    String operationtype = "";

    Boolean Coloring_flag = false,red_flag= false,blue_flag = false;
    Boolean Stamp_flag=false,Blurring_flag=false,Bigwidth_flag=false;

    float[] matrixarray={2f,0f,0f,0f,-25f,
            0f,2f,0f,0f,-25f,
            0f,0f,2f,0f,-25f,
            0f,0f,0f,1f,0f};

    public my_canvas(Context context) {
        super(context);
        mpaint.setColor(Color.BLACK);
        this.setLayerType(LAYER_TYPE_SOFTWARE,null);
    }

    public my_canvas(Context context, AttributeSet attrs) {
        super(context, attrs);
        mpaint.setColor(Color.BLACK);
        this.setLayerType(LAYER_TYPE_SOFTWARE,null);
    }

    public void setOperationtype(String operationtype){
        this.operationtype= operationtype;
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

        super.onSizeChanged(w, h, oldw, oldh);
        mBitmap = Bitmap.createBitmap(w,h, Bitmap.Config.ARGB_8888);
        //그림판? 판대기 자체
        mCanvas = new Canvas();
        mCanvas.setBitmap(mBitmap);
        mCanvas.drawColor(Color.YELLOW);
    }

    public void drawStamp(int x,int y){
        mCanvas.save();
        Bitmap img = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);

        if(Coloring_flag){
            ColorMatrix matrix = new ColorMatrix(matrixarray);
            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
            mpaint.setColorFilter(filter);
        }else{
            mpaint = new Paint();
        }
        if(Blurring_flag){
            BlurMaskFilter blurring = new BlurMaskFilter(100, BlurMaskFilter.Blur.INNER);
            mpaint.setMaskFilter(blurring);
        }else{
            mpaint = new Paint();
        }

        if(operationtype.equals("rotate")){
            mCanvas.rotate(30,x,y);
            mCanvas.drawBitmap(img,x,y,mpaint);
            mCanvas.restore();
            operationtype="";
            return;
        }
        if(operationtype.equals("move")){
            mCanvas.translate(100,100);
            mCanvas.drawBitmap(img,x,y,mpaint);
            mCanvas.restore();
           operationtype="";
            return;
        }
        if(operationtype.equals("scale")){
            mCanvas.scale(1.5f,1.5f);
            float scaleX = img.getWidth()*1.5f;
            float scaleY = img.getHeight()*1.5f;

            Bitmap largeimg = Bitmap.createScaledBitmap(img,(int)scaleX,(int)scaleY,false);
            x -= largeimg.getWidth()/2;
            y -= largeimg.getHeight()/2;
            mCanvas.drawBitmap(largeimg,x,y,mpaint);
            mCanvas.restore();
            operationtype="";
            return;
        }
        if(operationtype.equals("skew")){
            mCanvas.skew(0.2f,0.0f);
            mCanvas.drawBitmap(img,x,y,mpaint);
            mCanvas.restore();
            operationtype="";
            return;
        }

        x -= img.getWidth()/2;
        y -= img.getHeight()/2;

        mCanvas.drawBitmap(img,x,y,mpaint);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mBitmap!=null)
            canvas.drawBitmap(mBitmap,0,0,mpaint);

    }

    public void clear(){
        mBitmap.eraseColor(Color.WHITE);
        invalidate();
    }

    public Bitmap getmBitmap() {
        return mBitmap;
    }

    int oldX = -1,oldY = -1;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int X = (int)event.getX();
        int Y = (int)event.getY();
        if(Stamp_flag){
            if(event.getAction()==MotionEvent.ACTION_DOWN){
                drawStamp(X,Y);
                invalidate();
            }
        }else{
            if(Bigwidth_flag){
                mpaint.setStrokeWidth(5);
            }else{
                mpaint.setStrokeWidth(3);
            }
            if(red_flag && blue_flag!=true) {
                Log.d("red_flag",String.valueOf(red_flag));
                Log.d("blue_flag",String.valueOf(blue_flag));
                mpaint.setColor(Color.RED);
            }
            if(blue_flag && red_flag!=true) {
                Log.d("red_flag",String.valueOf(red_flag));
                Log.d("blue_flag",String.valueOf(blue_flag));
                mpaint.setColor(Color.BLUE);
            }
            if(event.getAction()==MotionEvent.ACTION_DOWN){
                oldX = X;
                oldY = Y;

            }else if(event.getAction()==MotionEvent.ACTION_MOVE){
                if(oldX != -1){
                    mCanvas.drawLine(oldX,oldY,X,Y,mpaint);
                    invalidate();
                    oldX = X;
                    oldY = Y;
                }


            }else if(event.getAction()==MotionEvent.ACTION_UP){
                if(oldX != -1){
                    mCanvas.drawLine(oldX,oldY,X,Y,mpaint);
                    invalidate();
                }

                oldX = -1;
                oldY = -1;
            }

        }

        return true;
    }
}
