package com.speedometerrit.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;

import com.speedometerrit.helpers.ColorManager;
import com.speedometerrit.helpers.SpeedometerHelper;

public class DotsScaleView extends ScaleView {
    private final SpeedometerHelper speedometerHelper;
    // Paint object for coloring and styling
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private float scaleSize = 0; // Scale size
    private float innerCircleSize = 0; // Inner circle size

    private int innerCircleColor; // Inner circle color
    private int scaleColor; // Scale color
    private int needleColor; // Needle color
    private int centerCircleColor; // Center circle color

    public DotsScaleView(Context context) {
        super(context);
        setDefaultColors();
        speedometerHelper = new SpeedometerHelper();
    }

    @Override
    public void setSpeed(int speed) {
        speedometerHelper.setSpeed(speed);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        scaleSize = Math.min(getMeasuredWidth(), getMeasuredHeight());
        setMeasuredDimension((int) scaleSize, (int) scaleSize);

        speedometerHelper.setScaleSize(scaleSize);
        innerCircleSize = speedometerHelper.getInnerCircleSize();
    }

    private void setDefaultColors() {
        this.innerCircleColor = getColor(ColorManager.getInnerCircleColor());
        this.scaleColor = getColor(ColorManager.getMainColorId());
        this.needleColor = getColor(ColorManager.getNeedleColor());
        this.centerCircleColor = getColor(ColorManager.getCenterCircleColor());
    }

    private int getColor(int colorId) {
        return getResources().getColor(colorId);
    }

    public void setInnerCircleColor(int colorId) {
        this.innerCircleColor = getColor(colorId);
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

        float center = scaleSize / 2;

        // Draw the needle
        drawNeedle(canvas, center);

        // Draw center circle
        float centerCircleRadius =
                speedometerHelper.getCenterCircleRadius(center, innerCircleMargin);
        drawCenterCircle(canvas, center, centerCircleRadius);
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
        if (speedometerHelper.pointReached(dotNumber)) {
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

    private void drawNeedle(Canvas canvas, float center) {
        paint.setColor(needleColor);
        paint.setStyle(Paint.Style.FILL);

        float needleOffset = speedometerHelper.getNeedleOffset();
        float needleLength = speedometerHelper.getNeedleLength(needleOffset);
        float needleBeginWidth = speedometerHelper.getNeedleBeginWidth();
        float needleEndWidth = speedometerHelper.getNeedleEndWidth();

        float topLeftX = needleOffset;
        float topLeftY = center - needleEndWidth / 2;

        float topRightX = needleLength + topLeftX;
        float topRightY = center - needleBeginWidth / 2;

        float bottomRightX = topRightX;
        float bottomRightY = center + needleBeginWidth / 2;

        float bottomLeftX = topLeftX;
        float bottomLeftY = center + needleEndWidth / 2;

        Path needle = new Path();
        needle.setFillType(Path.FillType.EVEN_ODD);

        needle.moveTo(topLeftX, topLeftY);
        needle.lineTo(topRightX, topRightY);
        needle.lineTo(bottomRightX, bottomRightY);
        needle.lineTo(bottomLeftX, bottomLeftY);
        needle.lineTo(topLeftX, topLeftY);
        needle.close();

        Matrix matrix = new Matrix();
        RectF bounds = new RectF();
        needle.computeBounds(bounds, true);
        matrix.postRotate(speedometerHelper.getNeedleAngle(), center, center);
        needle.transform(matrix);

        canvas.drawPath(needle, paint);
    }

    private void drawCenterCircle(Canvas canvas,
                                  float center,
                                  float radius) {
        paint.setColor(centerCircleColor);
        paint.setStyle(Paint.Style.FILL);

        canvas.drawCircle(center, center, radius, paint);
    }
}
