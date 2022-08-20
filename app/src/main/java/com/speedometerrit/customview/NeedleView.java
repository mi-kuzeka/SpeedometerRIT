package com.speedometerrit.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

import com.speedometerrit.helpers.ColorManager;
import com.speedometerrit.helpers.SpeedometerHelper;

public class NeedleView extends SpeedView {
    private final SpeedometerHelper speedometerHelper;
    // Paint object for coloring and styling
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private float scaleSize = 0; // Scale size
    private float innerCircleSize = 0; // Inner circle size

    private int needleColor; // Needle color
    private int centerCircleColor; // Center circle color

    public NeedleView(Context context) {
        super(context);
        setDefaultColors();
        speedometerHelper = new SpeedometerHelper();
    }

    @Override
    public void setSpeed(int speed) {
        speedometerHelper.setSpeed(speed);
        invalidate();
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
        this.needleColor = getColor(ColorManager.getNeedleColor());
        this.centerCircleColor = getColor(ColorManager.getCenterCircleColor());
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
        paint.setStrokeWidth(speedometerHelper.getScaleThickness());

        float innerCircleMargin = speedometerHelper
                .getInnerCircleTopLeftMargin(innerCircleSize);
        float center = scaleSize / 2;

        // Draw the needle
        drawNeedle(canvas, center);

        // Draw center circle
        float centerCircleRadius =
                speedometerHelper.getCenterCircleRadius(center, innerCircleMargin);
        drawCenterCircle(canvas, center, centerCircleRadius);
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
