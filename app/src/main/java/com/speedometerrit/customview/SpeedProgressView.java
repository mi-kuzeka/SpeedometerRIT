package com.speedometerrit.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

import com.speedometerrit.helpers.ColorManager;
import com.speedometerrit.helpers.SpeedometerHelper;

public class SpeedProgressView extends SpeedView {
    private final SpeedometerHelper speedometerHelper;

    // Paint object for coloring and styling
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int scaleSize = 0; // Scale size
    private float scalePadding = 0; // Padding of scale
    // Current speed sector color
    private int speedColor;
    private boolean isSmallWidget = false;

    public SpeedProgressView(Context context) {
        super(context);
        setDefaultColor();
        this.isSmallWidget = false;
        speedometerHelper = new SpeedometerHelper();
    }

    @Override
    public void setSpeed(int speed) {
        speedometerHelper.setSpeed(speed);
        invalidate();
    }

    public SpeedProgressView(Context context, boolean isSmallWidget) {
        super(context);
        setDefaultColor();
        this.isSmallWidget = isSmallWidget;
        speedometerHelper = new SpeedometerHelper();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        scaleSize = Math.min(getMeasuredWidth(), getMeasuredHeight());
        setMeasuredDimension(scaleSize, scaleSize);

        speedometerHelper.setScaleSize(scaleSize, isSmallWidget);
        scalePadding = speedometerHelper.getScalePadding();
    }

    private void setDefaultColor() {
        this.speedColor = getColor(ColorManager.getMainColorId());
    }

    private int getColor(int colorId) {
        return getResources().getColor(colorId);
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
                speedometerHelper.getSpeedAngle(), false, paint);
    }
}
