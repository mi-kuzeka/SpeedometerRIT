package com.speedometerrit.customview;

import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.animation.LinearInterpolator;

import com.speedometerrit.helpers.ColorManager;
import com.speedometerrit.helpers.SpeedometerHelper;

/**
 * This class is draws speed line for OneLineSpeedometer
 * relative to current speed
 */
public class SpeedLineView extends SpeedView {
    private SpeedometerHelper speedometerHelper;

    // Paint object for coloring and styling
    private Paint paint;
    // Current speed sector color
    private int speedColor;
    // Parent view size
    private int viewSize = 0;
    private float scalePadding = 0;
    // The flag indicates that the view is called from mini widget
    private final boolean isSmallWidget;

    // Current speed - used to display speed animation
    private int currentSpeed = 0;
    // Duration of animation
    private int animationDuration;
    // Holder of animation values
    private final String SPEED_VALUE_HOLDER = "speed";

    public SpeedLineView(Context context) {
        super(context);
        this.isSmallWidget = false;
        init();
    }

    public SpeedLineView(Context context, boolean isSmallWidget) {
        super(context);
        this.isSmallWidget = isSmallWidget;
        init();
    }

    private void init() {
        speedometerHelper = new SpeedometerHelper();
        currentSpeed = SpeedometerHelper.getSpeed();

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        setDefaultColor();

        // Retrieve and cache the system's default "short" animation time.
        animationDuration = getResources().getInteger(
                android.R.integer.config_shortAnimTime);
    }

    private void setDefaultColor() {
        this.speedColor = getColor(ColorManager.getMainColorId());
    }

    private int getColor(int colorId) {
        return getResources().getColor(colorId);
    }

    @Override
    public void setSpeed(int newSpeed) {
        // Create animation for speed changing
        PropertyValuesHolder valuesHolder =
                PropertyValuesHolder.ofInt(SPEED_VALUE_HOLDER, currentSpeed, newSpeed);

        ValueAnimator animator = ValueAnimator.ofPropertyValuesHolder(valuesHolder);
        animator.setInterpolator(new LinearInterpolator());
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
        setMeasuredDimension(viewSize, viewSize);

        speedometerHelper.setScaleSize(viewSize, isSmallWidget);
        scalePadding = speedometerHelper.getScalePadding();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw speed line on the scale
        drawSpeedProgress(canvas);
    }

    protected void drawSpeedProgress(Canvas canvas) {
        paint.setColor(speedColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(speedometerHelper.getScaleThickness());

        // Create scale bounds
        RectF oval = new RectF(scalePadding, scalePadding,
                viewSize - scalePadding, viewSize - scalePadding);

        // Draw speed progress
        canvas.drawArc(oval, SpeedometerHelper.SCALE_BEGIN_ANGLE,
                SpeedometerHelper.getSpeedAngle(currentSpeed), false, paint);
    }
}
