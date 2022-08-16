package com.speedometerrit.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.speedometerrit.R;

public class OneLineScaleView extends ScaleView {
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
    private Float borderWidth = 10.0f;
    private int size = 0; // View size
    int scaleColor; // Scale color
    // Current speed sector color
    int speedColor;

    public OneLineScaleView(Context context) {
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

        size = Math.min(getMeasuredWidth(), getMeasuredHeight());

        setMeasuredDimension(size, size);
    }

    private void setDefaultColors() {
        this.scaleColor = getResources().getColor(R.color.gray);
        this.speedColor = getResources().getColor(R.color.turquoise);
    }

    public void setScaleColor(int color) {
        this.scaleColor = color;
        //TODO: changeScaleColor();
    }

    public void setSpeedColor(int color) {
        this.speedColor = color;
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

        int scaleSize = Math.round(size - borderWidth);
        int ovalPadding = Math.round(borderWidth);

        RectF oval = new RectF(ovalPadding, ovalPadding, scaleSize, scaleSize);
        canvas.drawArc(oval, SCALE_BEGIN_ANGLE, SCALE_SWEEP_ANGLE, false, paint);

        paint.setColor(speedColor);

        float speedAngle = (float) (SCALE_SWEEP_ANGLE * speed) / maxScaleValue;
        canvas.drawArc(oval, SCALE_BEGIN_ANGLE, speedAngle, false, paint);
    }
}
