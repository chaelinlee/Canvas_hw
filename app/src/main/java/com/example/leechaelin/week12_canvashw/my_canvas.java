package com.example.leechaelin.week12_canvashw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
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

    Boolean Checkbox_flag= false,Scale_flag=false,Skew_flag=false;
    Boolean  Erase_flag= false,Open_flag=false,Save_flag=false,Rotate_flag=false,Move_flag=false;

    public my_canvas(Context context) {
        super(context);
        mpaint.setColor(Color.YELLOW);
    }

    public my_canvas(Context context, AttributeSet attrs) {
        super(context, attrs);
        mpaint.setColor(Color.YELLOW);
    }

    public void setOperationtype(String operationtype){
        this.operationtype= operationtype;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

        super.onSizeChanged(w, h, oldw, oldh);
        mBitmap = Bitmap.createBitmap(w,h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas();
        mCanvas.setBitmap(mBitmap);

        drawStamp(1,1);
    }

    public void drawStamp(int x,int y){
        Bitmap img = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
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
    public void rotate(){
        mCanvas.rotate(45,this.getWidth()/2,this.getHeight()/2);
    }
    public void scale(){
        mCanvas.scale(1.2f,1.2f);
    }
    public void skew(){
        mCanvas.skew(0.3f,0.3f);
    }

    public void move(){
        mCanvas.translate(10,10);
    }


    int oldX = -1,oldY = -1;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int X = (int)event.getX();
        int Y = (int)event.getY();

        if(event.getAction()==MotionEvent.ACTION_DOWN){
            if(Checkbox_flag)
                drawStamp(X,Y);
            invalidate();

            oldX = X;
            oldY = Y;
        }else if(event.getAction()==MotionEvent.ACTION_MOVE){
            if(oldX != -1){
                if(Checkbox_flag)
                    mCanvas.drawLine(oldX,oldY,X,Y,mpaint);
                invalidate();
                oldX = X;
                oldY = Y;
            }


        }else if(event.getAction()==MotionEvent.ACTION_UP){
            if(oldX != -1){
                if(Checkbox_flag)
                    mCanvas.drawLine(oldX,oldY,X,Y,mpaint);
                invalidate();
            }
            oldX = -1;
            oldY = -1;
        }

        return true;
    }
}
