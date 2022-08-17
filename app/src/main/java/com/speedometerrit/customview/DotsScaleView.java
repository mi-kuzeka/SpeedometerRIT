package com.speedometerrit.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

import com.speedometerrit.R;

public class DotsScaleView extends View {
    private final DrawingScaleUtil drawingScaleUtil;
    // Paint object for coloring and styling
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private int scaleSize = 0; // Scale size
    private int innerCircleSize = 0; // Inner circle size

    int innerCircleColor; // Inner circle color
    int scaleColor; // Scale color

    public DotsScaleView(Context context) {
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

        scaleSize -= DrawingScaleUtil.SCALE_THICKNESS;
        innerCircleSize = DrawingScaleUtil.getInnerCircleSize(scaleSize);
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
        paint.setStrokeWidth(DrawingScaleUtil.SCALE_THICKNESS);

        float ovalPadding = DrawingScaleUtil.SCALE_THICKNESS;
        // Draw scale
        RectF oval = new RectF(ovalPadding, ovalPadding, scaleSize, scaleSize);
        canvas.drawArc(oval, DrawingScaleUtil.SCALE_BEGIN_ANGLE,
                DrawingScaleUtil.SCALE_SWEEP_ANGLE, false, paint);

        // Draw inner circle
        paint.setColor(innerCircleColor);

        int padding = DrawingScaleUtil
                .getInnerCirclePadding(scaleSize, innerCircleSize);
        int rightBottomPadding = DrawingScaleUtil
                .getInnerCircleRightBottomPadding(innerCircleSize, padding);

        RectF innerOval = new RectF(padding, padding,
                rightBottomPadding, rightBottomPadding);
        canvas.drawArc(innerOval, DrawingScaleUtil.SCALE_BEGIN_ANGLE,
                DrawingScaleUtil.SCALE_SWEEP_ANGLE, false, paint);

        // Draw dots
        float circleRadius = DrawingScaleUtil.getDotCircleRadius((float) scaleSize);
        float dotOffset = DrawingScaleUtil.getDotOffset(circleRadius);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(0);

        for (int dotNumber = 1;
             dotNumber <= drawingScaleUtil.getDotsCount();
             dotNumber++) {

            drawDots(dotNumber, circleRadius, dotOffset, canvas);
        }
    }

    private void drawDots(int dotNumber,
                          float circleRadius,
                          float dotOffset,
                          Canvas canvas) {
        if (drawingScaleUtil.pointReached(dotNumber)) {
            paint.setColor(getResources().getColor(R.color.white));
        } else {
            paint.setColor(getResources().getColor(R.color.gray));
        }

        float angle = drawingScaleUtil.getDotAngle(dotNumber);
        float x = DrawingScaleUtil.getDotX(angle, circleRadius, dotOffset);
        float y = DrawingScaleUtil.getDotY(angle, circleRadius, dotOffset);
        canvas.drawCircle(x, y, DrawingScaleUtil.DOT_RADIUS, paint);
    }
}
