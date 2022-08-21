package com.speedometerrit.customview;

import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.speedometerrit.helpers.ColorManager;
import com.speedometerrit.helpers.SpeedManager;
import com.speedometerrit.helpers.SpeedometerDrawingHelper;

/**
 * This class is draws needle and dots for DotsSpeedometer
 * relative to current speed
 */
public class NeedleSpeedView extends SpeedView {
    private final SpeedometerDrawingHelper speedometerDrawingHelper;
    // Paint object for coloring and styling
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    // Parent view size
    private float viewSize = 0;
    private float innerCircleSize = 0;

    private int needleColor;
    private int centralCircleColor;

    // Current speed - used to display speed animation
    private int currentSpeed;
    // Duration of animation
    private final int animationDuration;
    // Holder of animation values
    private final String SPEED_VALUE_HOLDER = "needle_speed";

    public NeedleSpeedView(Context context) {
        super(context);
        setDefaultColors();
        speedometerDrawingHelper = new SpeedometerDrawingHelper();
        currentSpeed = SpeedManager.getSpeed();

        // Retrieve and cache the system's default "short" animation time.
        animationDuration = getResources().getInteger(
                android.R.integer.config_shortAnimTime);
    }

    @Override
    public void setSpeed(int newSpeed) {
        // Create animation for speed changing
        PropertyValuesHolder valuesHolder =
                PropertyValuesHolder.ofInt(SPEED_VALUE_HOLDER, currentSpeed, newSpeed);

        ValueAnimator animator = ValueAnimator.ofPropertyValuesHolder(valuesHolder);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(animationDuration);
        animator.addUpdateListener(valueAnimator -> {
            currentSpeed = (int) valueAnimator.getAnimatedValue(SPEED_VALUE_HOLDER);
            invalidate();
        });
        animator.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // Get view size in pixels
        viewSize = Math.min(getMeasuredWidth(), getMeasuredHeight());
        setMeasuredDimension((int) viewSize, (int) viewSize);

        speedometerDrawingHelper.setScaleSize(viewSize, false);
        innerCircleSize = speedometerDrawingHelper.getInnerCircleSize();
    }

    private void setDefaultColors() {
        this.needleColor = getColor(ColorManager.getNeedleColor());
        this.centralCircleColor = getColor(ColorManager.getCentralCircleColor());
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
        // Draw dots
        drawDots(canvas);

        // Canvas center
        float center = viewSize / 2;

        // Draw the needle
        drawNeedle(canvas, center);

        // Draw central circle
        float innerCircleMargin = speedometerDrawingHelper
                .getInnerCircleTopLeftMargin(innerCircleSize);
        float centralCircleRadius =
                speedometerDrawingHelper.getCentralCircleRadius(center, innerCircleMargin);
        drawCentralCircle(canvas, center, centralCircleRadius);
    }

    /**
     * Draw dots on the scale (only ones reached by needle)
     */
    private void drawDots(Canvas canvas) {
        // Radius of circle for drawing dots on it
        float circleRadius = speedometerDrawingHelper.getDotCircleRadius();
        // Offset of dot relative to left/top bounds
        float dotOffset = speedometerDrawingHelper.getDotOffset(circleRadius);

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(getColor(ColorManager.getReachedPointColor()));

        // Draw dots on the circle
        for (int dotNumber = 1;
             dotNumber <= SpeedometerDrawingHelper.getDotsCount();
             dotNumber++) {

            // Draw only reached points
            if (!SpeedometerDrawingHelper.pointReached(dotNumber, currentSpeed)) break;

            // Get dot location angle on the circle
            float angle = SpeedometerDrawingHelper.getDotAngle(dotNumber);
            // Calculate coordinates for drawing current dot
            float x = SpeedometerDrawingHelper.getDotX(angle, circleRadius, dotOffset);
            float y = SpeedometerDrawingHelper.getDotY(angle, circleRadius, dotOffset);
            // Draw dot
            canvas.drawCircle(x, y, speedometerDrawingHelper.getDotRadius(), paint);
        }
    }

    /**
     * Draw speedometer needle
     */
    private void drawNeedle(Canvas canvas, float center) {
        paint.setColor(needleColor);
        paint.setStyle(Paint.Style.FILL);

        // Offset of the needle relative to left bound of canvas
        float needleOffset = speedometerDrawingHelper.getNeedleOffset();
        float needleLength = speedometerDrawingHelper.getNeedleLength(needleOffset);
        float needleBeginWidth = speedometerDrawingHelper.getNeedleBeginWidth();
        float needleEndWidth = speedometerDrawingHelper.getNeedleEndWidth();

        float topLeftX = needleOffset;
        float topLeftY = center - needleEndWidth / 2;

        float topRightX = needleLength + topLeftX;
        float topRightY = center - needleBeginWidth / 2;

        float bottomRightX = topRightX;
        float bottomRightY = center + needleBeginWidth / 2;

        float bottomLeftX = topLeftX;
        float bottomLeftY = center + needleEndWidth / 2;

        // Draw the needle path
        Path needle = new Path();
        needle.setFillType(Path.FillType.EVEN_ODD);

        needle.moveTo(topLeftX, topLeftY);
        needle.lineTo(topRightX, topRightY);
        needle.lineTo(bottomRightX, bottomRightY);
        needle.lineTo(bottomLeftX, bottomLeftY);
        needle.lineTo(topLeftX, topLeftY);
        needle.close();

        // Rotate the needle to current speed angle
        Matrix matrix = new Matrix();
        RectF bounds = new RectF();
        needle.computeBounds(bounds, true);
        matrix.postRotate(SpeedometerDrawingHelper.getNeedleAngle(currentSpeed),
                center, center);
        needle.transform(matrix);

        // Draw rotated needle
        canvas.drawPath(needle, paint);
    }

    /**
     * Draw central black circle (covers part of the needle)
     */
    private void drawCentralCircle(Canvas canvas,
                                   float center,
                                   float radius) {
        paint.setColor(centralCircleColor);
        paint.setStyle(Paint.Style.FILL);

        canvas.drawCircle(center, center, radius, paint);
    }
}
