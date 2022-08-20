package com.speedometerrit.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.view.View;

import com.speedometerrit.helpers.ColorManager;
import com.speedometerrit.helpers.SpeedometerHelper;

public class DotsScaleView extends View {
    private final SpeedometerHelper speedometerHelper;
    // Paint object for coloring and styling
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private float scaleSize = 0; // Scale size
    private float innerCircleSize = 0; // Inner circle size

    private int innerCircleColor; // Inner circle color
    private int scaleColor; // Scale color

    public DotsScaleView(Context context) {
        super(context);
        setDefaultColors();
        speedometerHelper = new SpeedometerHelper();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        scaleSize = Math.min(getMeasuredWidth(), getMeasuredHeight());
        setMeasuredDimension((int) scaleSize, (int) scaleSize);

        speedometerHelper.setScaleSize(scaleSize, false);
        innerCircleSize = speedometerHelper.getInnerCircleSize();
    }

    private void setDefaultColors() {
        this.innerCircleColor = getColor(ColorManager.getInnerCircleColor());
        this.scaleColor = getColor(ColorManager.getMainColorId());
    }

    private int getColor(int colorId) {
        return getResources().getColor(colorId);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawScale(canvas);
    }

    protected void drawScale(Canvas canvas) {
        // Draw outer circle
        drawOuterCircle(canvas);

        // Draw inner circle
        float innerCircleMargin = speedometerHelper
                .getInnerCircleTopLeftMargin(innerCircleSize);
        float innerCircleRightBottomCoordinate = SpeedometerHelper
                .getInnerCircleRightBottomCoordinate(innerCircleSize, innerCircleMargin);

        drawInnerCircle(canvas, innerCircleMargin, innerCircleRightBottomCoordinate);

        // Clear gradient
        paint.setShader(null);

        // Draw dots
        float circleRadius = speedometerHelper.getDotCircleRadius();
        float dotOffset = speedometerHelper.getDotOffset(circleRadius);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(0);

        for (int dotNumber = 1;
             dotNumber <= SpeedometerHelper.getDotsCount();
             dotNumber++) {

            drawDots(canvas, dotNumber, circleRadius, dotOffset);
        }
    }

    private void drawOuterCircle(Canvas canvas) {
        float scalePadding = speedometerHelper.getScalePadding();
        // Set gradient
        paint.setShader(getScaleGradient(scaleSize - scalePadding, scaleColor));

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(speedometerHelper.getScaleThickness());

        RectF oval = new RectF(scalePadding, scalePadding,
                scaleSize - scalePadding, scaleSize - scalePadding);
        canvas.drawArc(oval, SpeedometerHelper.SCALE_BEGIN_ANGLE,
                SpeedometerHelper.SCALE_SWEEP_ANGLE, false, paint);
    }

    private LinearGradient getScaleGradient(float circleRadius, int scaleColor) {
        // Create the gradient shader
        int endColor = getColor(ColorManager.getScaleGradientColor());
        int[] colors = {scaleColor, scaleColor, endColor};
        float[] positions = {0, 0.75f, 1};
        float gradientBeginAngleOffset = 10f;
        float gradientY1 = SpeedometerHelper.getDotY(
                SpeedometerHelper.SCALE_BEGIN_ANGLE - gradientBeginAngleOffset,
                circleRadius,
                0);

        return new LinearGradient(scaleSize / 2,
                0,
                scaleSize / 2,
                gradientY1,
                colors,
                positions,
                Shader.TileMode.CLAMP);
    }

    private void drawInnerCircle(Canvas canvas,
                                 float padding,
                                 float rightBottomPadding) {
        // Set gradient
        paint.setShader(getScaleGradient(scaleSize - padding,
                innerCircleColor));
        RectF innerOval = new RectF(padding, padding,
                rightBottomPadding, rightBottomPadding);
        canvas.drawArc(innerOval, SpeedometerHelper.SCALE_BEGIN_ANGLE,
                SpeedometerHelper.SCALE_SWEEP_ANGLE, false, paint);
    }

    private void drawDots(Canvas canvas,
                          int dotNumber,
                          float circleRadius,
                          float dotOffset) {
        int pointColorId;
        if (SpeedometerHelper.pointReached(dotNumber)) {
            pointColorId = ColorManager.getReachedPointColor();
        } else {
            pointColorId = ColorManager.getPointColor();
        }
        paint.setColor(getColor(pointColorId));

        float angle = SpeedometerHelper.getDotAngle(dotNumber);
        float x = SpeedometerHelper.getDotX(angle, circleRadius, dotOffset);
        float y = SpeedometerHelper.getDotY(angle, circleRadius, dotOffset);
        canvas.drawCircle(x, y, speedometerHelper.getDotRadius(), paint);
    }
}
