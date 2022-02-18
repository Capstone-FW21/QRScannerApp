package com.example.qrscanner;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

public class drawRoom extends View {
    Paint paint = new Paint();
    int xcor = 0;
    int ycor = 0;

    public drawRoom(Context context, int x, int y) {
        super(context);
        xcor = x;
        ycor = y;
    }


    @Override
    public void onDraw(Canvas canvas) {
        paint.setColor(Color.BLACK);

        int canvasW = getWidth();
        int canvasH = getHeight();
        Point centerOfCanvas = new Point(canvasW / 2, canvasH / 2);
        int rectW = xcor*6;
        int rectH = ycor*6;
        int left = centerOfCanvas.x - (rectW / 2);
        int top = centerOfCanvas.y - (rectH / 2);
        int right = centerOfCanvas.x + (rectW / 2);
        int bottom = centerOfCanvas.y + (rectH /2);

        Rect rect = new Rect(left-30, top-30, right+30, bottom+30);
        paint.setColor(Color.BLACK);
        canvas.drawRect(rect, paint);

        Rect rect2 = new Rect(left, top, right, bottom);
        paint.setColor(Color.WHITE);
        canvas.drawRect(rect2, paint);

        //paint.setColor(Color.RED);
        //canvas.drawCircle(left,top,50,paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        

        return super.onTouchEvent(event);
    }

    public void setXcor(int xcor) {
        this.xcor = xcor;
    }

    public void setYcor(int ycor) {
        this.ycor = ycor;
    }

    public int getXcor() {
        return xcor;
    }

    public int getYcor() {
        return ycor;
    }
}