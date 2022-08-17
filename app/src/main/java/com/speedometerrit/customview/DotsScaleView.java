package com.speedometerrit.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

import com.speedometerrit.R;

public class DotsScaleView extends View {
    private final DrawingScaleHelper drawingScaleHelper;
    // Paint object for coloring and styling
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    // Scale border width in pixels
    private final int borderWidth = 10;
    // Distance from scale to dots
    private final int dotsMargin = 30;
    private int scaleSize = 0; // Scale size
    private int innerCircleSize = 0; // Inner circle size
    private int innerCirclePadding = 0; // Inner circle padding
    private int innerCircleRightBottomPadding = 0;
    int innerCircleColor; // Inner circle color
    int scaleColor; // Scale color

    public DotsScaleView(Context context) {
        super(context);
        setDefaultColors();
        drawingScaleHelper = new DrawingScaleHelper();
    }

    public void setSpeed(int speed, byte speedUnits) {
        drawingScaleHelper.setSpeed(speed, speedUnits);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        scaleSize = Math.min(getMeasuredWidth(), getMeasuredHeight());
        setMeasuredDimension(scaleSize, scaleSize);

        scaleSize -= borderWidth;
        innerCircleSize = DrawingScaleHelper.getInnerCircleSize(scaleSize);
        innerCirclePadding = DrawingScaleHelper
                .getInnerCirclePadding(scaleSize, innerCircleSize);
        innerCircleRightBottomPadding = DrawingScaleHelper
                .getInnerCircleRightBottomPadding(innerCircleSize, innerCirclePadding);
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

        // Draw scale
        RectF oval = new RectF(borderWidth, borderWidth, scaleSize, scaleSize);
        canvas.drawArc(oval, DrawingScaleHelper.SCALE_BEGIN_ANGLE,
                DrawingScaleHelper.SCALE_SWEEP_ANGLE, false, paint);

        // Draw inner circle
        paint.setColor(innerCircleColor);

        RectF innerOval = new RectF(innerCirclePadding, innerCirclePadding,
                innerCircleRightBottomPadding, innerCircleRightBottomPadding);
        canvas.drawArc(innerOval, DrawingScaleHelper.SCALE_BEGIN_ANGLE,
                DrawingScaleHelper.SCALE_SWEEP_ANGLE, false, paint);

        // Draw dots
        for (int dotNumber = 1;
             dotNumber <= drawingScaleHelper.getDotsCount(); dotNumber++)
            drawDots(dotNumber, canvas);

//        float speedAngle = (float) (SCALE_SWEEP_ANGLE * speed) / maxScaleValue;
//        canvas.drawArc(oval, SCALE_BEGIN_ANGLE, speedAngle, false, paint);
    }

    private void drawDots(int dotNumber, Canvas canvas) {
        float angle = drawingScaleHelper.getDotAngle(dotNumber);
        int dotRadius = borderWidth / 4;
        int circleRadius = scaleSize / 2 - borderWidth - dotsMargin;
        int dotOffset = circleRadius + borderWidth + dotsMargin + dotRadius * 2;
        float x = (float) ((Math.cos(Math.toRadians(angle)) * (float) circleRadius)
                + dotOffset);
        float y = (float) ((Math.sin(Math.toRadians(angle)) * (float) circleRadius)
                + dotOffset);
        paint.setColor(getResources().getColor(R.color.white));
        canvas.drawCircle(x, y, dotRadius, paint);
    }
}
