package com.speedometerrit.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.view.View;

import com.speedometerrit.R;

public class DotsScaleView extends View {
    private final DrawingScaleUtil drawingScaleUtil;
    // Paint object for coloring and styling
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private float scaleSize = 0; // Scale size
    private float innerCircleSize = 0; // Inner circle size
    private float scalePadding = 0; // Padding of scale

    private int innerCircleColor; // Inner circle color
    private int scaleColor; // Scale color
    private int needleColor; // Needle color
    private int centerCircleColor; // Center circle color

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
        setMeasuredDimension((int) scaleSize, (int) scaleSize);

        innerCircleSize = drawingScaleUtil.getInnerCircleSize(scaleSize);
        scalePadding = drawingScaleUtil.getScalePadding();
    }

    private void setDefaultColors() {
        this.innerCircleColor = getResources().getColor(R.color.gray);
        this.scaleColor = getResources().getColor(R.color.turquoise);
        this.needleColor = getResources().getColor(R.color.white);
        this.centerCircleColor = getResources().getColor(R.color.black);
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
        // Draw outer circle
        drawOuterCircle(canvas);

        // Draw inner circle
        float innerCirclePadding = drawingScaleUtil
                .getInnerCirclePadding(scaleSize, innerCircleSize);
        float innerCircleRightBottomPadding = drawingScaleUtil
                .getInnerCircleRightBottomPadding(innerCircleSize, innerCirclePadding);

        drawInnerCircle(canvas, innerCirclePadding, innerCircleRightBottomPadding);

        paint.setShader(null);

        // Draw dots
        float circleRadius = drawingScaleUtil.getDotCircleRadius((float) scaleSize);
        float dotOffset = drawingScaleUtil.getDotOffset(circleRadius);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(0);

        for (int dotNumber = 1;
             dotNumber <= drawingScaleUtil.getDotsCount();
             dotNumber++) {

            drawDots(canvas, dotNumber, circleRadius, dotOffset);
        }

        float center = (float) scaleSize / 2;

        // Draw the needle
        drawNeedle(canvas, center);

        // Draw center circle
        float centerCircleRadius =
                drawingScaleUtil.getCenterCircleRadius(center, innerCirclePadding);
        drawCenterCircle(canvas, center, centerCircleRadius);
    }

    private void drawOuterCircle(Canvas canvas) {
        // Set gradient
        paint.setShader(getScaleGradient(scaleSize - scalePadding, scaleColor));

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(DrawingScaleUtil.SCALE_THICKNESS);

        RectF oval = new RectF(scalePadding, scalePadding,
                scaleSize - scalePadding, scaleSize - scalePadding);
        canvas.drawArc(oval, DrawingScaleUtil.SCALE_BEGIN_ANGLE,
                DrawingScaleUtil.SCALE_SWEEP_ANGLE, false, paint);
    }

    private LinearGradient getScaleGradient(float circleRadius, int scaleColor) {
        // Create the gradient shader
        int endColor = getResources().getColor(R.color.scale_gradient);
        int[] colors = {scaleColor, scaleColor, endColor};
        float[] positions = {0, 0.75f, 1};
        float gradientBeginAngleOffset = 10f;
        float gradientY1 = DrawingScaleUtil.getDotY(
                DrawingScaleUtil.SCALE_BEGIN_ANGLE - gradientBeginAngleOffset,
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
        canvas.drawArc(innerOval, DrawingScaleUtil.SCALE_BEGIN_ANGLE,
                DrawingScaleUtil.SCALE_SWEEP_ANGLE, false, paint);
    }

    private void drawDots(Canvas canvas,
                          int dotNumber,
                          float circleRadius,
                          float dotOffset) {
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

    private void drawNeedle(Canvas canvas, float center) {
        paint.setColor(needleColor);
        paint.setStyle(Paint.Style.FILL);

        float needleOffset = drawingScaleUtil.getNeedleOffset();
        float needleLength = DrawingScaleUtil.getNeedleLength(scaleSize, needleOffset);

        float topLeftX = needleOffset;
        float topLeftY = center - DrawingScaleUtil.NEEDLE_END_WIDTH / 2;

        float topRightX = needleLength + topLeftX;
        float topRightY = center - DrawingScaleUtil.NEEDLE_BEGIN_WIDTH / 2;

        float bottomRightX = topRightX;
        float bottomRightY = center + DrawingScaleUtil.NEEDLE_BEGIN_WIDTH / 2;

        float bottomLeftX = topLeftX;
        float bottomLeftY = center + DrawingScaleUtil.NEEDLE_END_WIDTH / 2;

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
        matrix.postRotate(drawingScaleUtil.getNeedleAngle(), center, center);
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
