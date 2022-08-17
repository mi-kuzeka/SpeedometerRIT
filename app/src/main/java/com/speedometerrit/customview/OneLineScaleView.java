package com.speedometerrit.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

import com.speedometerrit.R;

public class OneLineScaleView extends View {
    private final DrawingScaleUtil drawingScaleUtil;

    // Paint object for coloring and styling
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    // Scale border width in pixels
    private int borderWidth = 10;
    private int scaleSize = 0; // Scale size
    int scaleColor; // Scale color
    // Current speed sector color
    int speedColor;

    public OneLineScaleView(Context context) {
        super(context);
        setDefaultColors();
        drawingScaleUtil = new DrawingScaleUtil();
    }

    public void setSpeed(int speed, byte speedUnits) {
        drawingScaleUtil.setSpeed(speed, speedUnits);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        scaleSize = Math.min(getMeasuredWidth(), getMeasuredHeight());
        setMeasuredDimension(scaleSize, scaleSize);

        scaleSize -= borderWidth;
    }

    private void setDefaultColors() {
        this.scaleColor = getResources().getColor(R.color.dark_grey);
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

        // Draw scale
        RectF oval = new RectF(borderWidth, borderWidth, scaleSize, scaleSize);
        canvas.drawArc(oval, DrawingScaleUtil.SCALE_BEGIN_ANGLE,
                DrawingScaleUtil.SCALE_SWEEP_ANGLE, false, paint);

        // Draw speed progress
        paint.setColor(speedColor);

        canvas.drawArc(oval, DrawingScaleUtil.SCALE_BEGIN_ANGLE,
                drawingScaleUtil.getSpeedAngle(), false, paint);
    }
}
