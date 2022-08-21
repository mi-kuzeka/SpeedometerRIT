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

public class SpeedProgressView extends SpeedView {
    private SpeedometerHelper speedometerHelper;

    // Paint object for coloring and styling
    private Paint paint;
    // Current speed sector color
    private int speedColor;
    private int scaleSize = 0; // Scale size
    private float scalePadding = 0; // Padding of scale
    private final boolean isSmallWidget;

    private int currentSpeed = 0;
    // Duration of animation
    private int animationDuration;
    // Holder of animation values
    private final String SPEED_VALUE_HOLDER = "speed";

    public SpeedProgressView(Context context) {
        super(context);
        this.isSmallWidget = false;
        init();
    }

    public SpeedProgressView(Context context, boolean isSmallWidget) {
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

        scaleSize = Math.min(getMeasuredWidth(), getMeasuredHeight());
        setMeasuredDimension(scaleSize, scaleSize);

        speedometerHelper.setScaleSize(scaleSize, isSmallWidget);
        scalePadding = speedometerHelper.getScalePadding();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawSpeedProgress(canvas);
    }

    protected void drawSpeedProgress(Canvas canvas) {
        paint.setColor(speedColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(speedometerHelper.getScaleThickness());

        // Draw speed progress
        RectF oval = new RectF(scalePadding, scalePadding,
                scaleSize - scalePadding, scaleSize - scalePadding);

        canvas.drawArc(oval, SpeedometerHelper.SCALE_BEGIN_ANGLE,
                speedometerHelper.getSpeedAngle(currentSpeed), false, paint);
    }
}
