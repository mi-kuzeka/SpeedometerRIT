package com.speedometerrit.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.speedometerrit.R;

public class DotsScaleView extends ScaleView {
    // Current speed
    private int speed;
    // Speed units (km/h, MPH)
    private byte speedUnits;
    // Max speed value on the scale
    private int maxScaleValue;
    // Count of sectors on the scale
    private int scaleSectorsCount;

    // Paint object for coloring and styling
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    // Scale border width in pixels
    private int borderWidth = 10;
    private int scaleSize = 0; // Scale size
    private int innerCircleSize = 0; // Inner circle size
    private int innerCirclePadding = 0; // Inner circle padding
    // Ratio of scale to inner circle
    private final double innerCircleRatio = 1.7;
    int innerCircleColor; // Inner circle color
    int scaleColor; // Scale color

    public DotsScaleView(Context context) {
        super(context);
        setDefaultColors();
    }

    @Override
    public void setSpeed(int speed, byte speedUnits) {
        super.setSpeed(speed, speedUnits);
        this.speed = getSpeed();
        this.speedUnits = getSpeedUnits();
        this.maxScaleValue = getMaxScaleValue();
        this.scaleSectorsCount = getScaleSectorsCount();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        scaleSize = Math.min(getMeasuredWidth(), getMeasuredHeight());
        setMeasuredDimension(scaleSize, scaleSize);

        scaleSize -= borderWidth;
        innerCircleSize = (int) Math.round(scaleSize / innerCircleRatio);
        innerCirclePadding = (scaleSize - innerCircleSize) / 2;
    }

    private void setDefaultColors() {
        this.innerCircleColor = getResources().getColor(R.color.gray);
        this.scaleColor = getResources().getColor(R.color.turquoise);
    }

    public void setInnerCircleColor(int color) {
        this.innerCircleColor = color;
        //TODO: changeScaleColor();
    }

    public void setScaleColor(int color) {
        this.scaleColor = color;
        //TODO: changeScaleColor();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawScale(canvas);
    }


    private void drawScale(Canvas canvas) {
        paint.setColor(scaleColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(borderWidth);


        RectF oval = new RectF(borderWidth, borderWidth, scaleSize, scaleSize);
        canvas.drawArc(oval, SCALE_BEGIN_ANGLE, SCALE_SWEEP_ANGLE, false, paint);

        paint.setColor(innerCircleColor);

        int innerCircleRightBottomPadding = innerCircleSize + innerCirclePadding;
        RectF innerOval = new RectF(innerCirclePadding, innerCirclePadding,
                innerCircleRightBottomPadding, innerCircleRightBottomPadding);
        canvas.drawArc(innerOval, SCALE_BEGIN_ANGLE, SCALE_SWEEP_ANGLE, false, paint);

//        float speedAngle = (float) (SCALE_SWEEP_ANGLE * speed) / maxScaleValue;
//        canvas.drawArc(oval, SCALE_BEGIN_ANGLE, speedAngle, false, paint);
    }
}
