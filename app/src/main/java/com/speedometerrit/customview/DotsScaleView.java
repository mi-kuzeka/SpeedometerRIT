package com.speedometerrit.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.view.View;

import com.speedometerrit.helpers.ColorManager;
import com.speedometerrit.helpers.SpeedometerDrawingHelper;

/**
 * This class is draws scale for DotsSpeedometer -
 * only unchanged shapes
 */
public class DotsScaleView extends View {
    private final SpeedometerDrawingHelper speedometerDrawingHelper;
    // Paint object for coloring and styling
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    // Size of parent view (available canvas for drawing)
    private float viewSize = 0;
    // Size of inner scale circle
    private float innerCircleSize = 0;

    // Color for main scale
    private int scaleColor;
    // Color for inner scale circle
    private int innerCircleColor;

    public DotsScaleView(Context context) {
        super(context);
        setDefaultColors();
        speedometerDrawingHelper = new SpeedometerDrawingHelper();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        viewSize = Math.min(getMeasuredWidth(), getMeasuredHeight());
        setMeasuredDimension((int) viewSize, (int) viewSize);

        speedometerDrawingHelper.setScaleSize(viewSize, false);
        innerCircleSize = speedometerDrawingHelper.getInnerCircleSize();
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
        drawOuterScale(canvas);

        // Draw inner circle
        drawInnerScale(canvas);

        // Clear gradient
        paint.setShader(null);

        // Draw dots
        drawDots(canvas);
    }

    private void drawOuterScale(Canvas canvas) {
        float scalePadding = speedometerDrawingHelper.getScalePadding();
        // Set gradient
        paint.setShader(getScaleGradient(viewSize - scalePadding, scaleColor));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(speedometerDrawingHelper.getScaleThickness());

        // Create scale bounds
        RectF oval = new RectF(scalePadding, scalePadding,
                viewSize - scalePadding, viewSize - scalePadding);
        // Draw scale
        canvas.drawArc(oval, SpeedometerDrawingHelper.SCALE_BEGIN_ANGLE,
                SpeedometerDrawingHelper.SCALE_SWEEP_ANGLE, false, paint);
    }

    /**
     * Get linear gradient for circle
     */
    private LinearGradient getScaleGradient(float circleRadius, int scaleColor) {
        // Create the gradient shader
        int endColor = getColor(ColorManager.getScaleGradientColor());
        // Colors for gradient
        int[] colors = {scaleColor, scaleColor, endColor};
        // Positions of gradient colors
        float[] positions = {0, 0.75f, 1};
        // Offset of circle gradient for smooth bounds at the bottom
        float gradientBeginAngleOffset = 10f;
        // Bottom Y coordinate for creating gradient
        float gradientY1 = SpeedometerDrawingHelper.getDotY(
                SpeedometerDrawingHelper.SCALE_BEGIN_ANGLE - gradientBeginAngleOffset,
                circleRadius,
                0);

        return new LinearGradient(
                viewSize / 2,
                0,
                viewSize / 2,
                gradientY1,
                colors,
                positions,
                Shader.TileMode.CLAMP);
    }

    private void drawInnerScale(Canvas canvas) {
        float topLeftMargin = speedometerDrawingHelper
                .getInnerCircleTopLeftMargin(innerCircleSize);
        float rightBottomMargin = SpeedometerDrawingHelper
                .getInnerCircleRightBottomCoordinate(innerCircleSize, topLeftMargin);

        // Set gradient
        paint.setShader(getScaleGradient(viewSize - topLeftMargin,
                innerCircleColor));
        // Create circle bounds
        RectF innerOval = new RectF(topLeftMargin, topLeftMargin,
                rightBottomMargin, rightBottomMargin);
        // Draw scale
        canvas.drawArc(innerOval, SpeedometerDrawingHelper.SCALE_BEGIN_ANGLE,
                SpeedometerDrawingHelper.SCALE_SWEEP_ANGLE, false, paint);
    }

    /**
     * Draw scale dots
     */
    private void drawDots(Canvas canvas) {
        // Radius of circle for drawing dots on it
        float circleRadius = speedometerDrawingHelper.getDotCircleRadius();
        // Offset of dot relative to left/top bounds
        float dotOffset = speedometerDrawingHelper.getDotOffset(circleRadius);

        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(0);
        paint.setColor(getColor(ColorManager.getPointColor()));

        // Draw dots on the circle
        for (int dotNumber = 1;
             dotNumber <= SpeedometerDrawingHelper.getDotsCount();
             dotNumber++) {

            // Get dot location angle on the circle
            float angle = SpeedometerDrawingHelper.getDotAngle(dotNumber);
            // Calculate coordinates for drawing current dot
            float x = SpeedometerDrawingHelper.getDotX(angle, circleRadius, dotOffset);
            float y = SpeedometerDrawingHelper.getDotY(angle, circleRadius, dotOffset);
            // Draw dot
            canvas.drawCircle(x, y, speedometerDrawingHelper.getDotRadius(), paint);
        }
    }
}
