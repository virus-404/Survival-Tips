package com.example.survivaltips.health;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

public class DrawView  extends View {

    private boolean fillFlag = false;

    public DrawView(Context context){
        super(context);
    }

    public DrawView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public DrawView(Context context, AttributeSet attributeSet, int defStyleAttr) {
        super(context,attributeSet,defStyleAttr, defStyleAttr);
    }

    public boolean getFillFlag() {
        return fillFlag;
    }

    public void setFillFlag(boolean fillFlag) {
        this.fillFlag = fillFlag;
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        Paint paint = new Paint();

        paint.setColor(Color.RED);

        if (fillFlag) paint.setStyle(Paint.Style.FILL_AND_STROKE);
        else paint.setStyle(Paint.Style.STROKE);

        paint.setStrokeWidth(10f);

        Point point1_draw = new Point(width/2, 0);
        Point point2_draw = new Point(0, height);
        Point point3_draw = new Point(width, height);

        Path path = new Path();
        path.moveTo(point1_draw.x, point1_draw.y);
        path.lineTo(point2_draw.x, point2_draw.y);
        path.lineTo(point3_draw.x, point3_draw.y);
        path.lineTo(point1_draw.x, point1_draw.y);
        path.close();
        canvas.drawPath(path,paint);
    }
}
