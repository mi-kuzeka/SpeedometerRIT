package com.speedometerrit.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.speedometerrit.helpers.ColorManager;
import com.speedometerrit.helpers.SpeedometerHelper;

public class OneLineScaleView extends ScaleView {
    private SpeedometerHelper speedometerHelper;

    // Paint object for coloring and styling
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int scaleSize = 0; // Scale size
    private float scalePadding = 0; // Padding of scale
    int scaleColor; // Scale color
    // Current speed sector color
    int speedColor;

    public OneLineScaleView(Context context) {
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
        setMeasuredDimension(scaleSize, scaleSize);

        speedometerHelper.setScaleSize(scaleSize);
        scalePadding = speedometerHelper.getScalePadding();
    }

    private void setDefaultColors() {
        this.scaleColor = getColor(ColorManager.getDefaultOneLineScaleColor());
        this.speedColor = getColor(ColorManager.getMainColorId());
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
        paint.setColor(scaleColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(speedometerHelper.getScaleThickness());

        // Draw scale
        RectF oval = new RectF(scalePadding, scalePadding,
                scaleSize - scalePadding, scaleSize - scalePadding);
        canvas.drawArc(oval, SpeedometerHelper.SCALE_BEGIN_ANGLE,
                SpeedometerHelper.SCALE_SWEEP_ANGLE, false, paint);

        // Draw speed progress
        paint.setColor(speedColor);

        canvas.drawArc(oval, SpeedometerHelper.SCALE_BEGIN_ANGLE,
                speedometerHelper.getSpeedAngle(), false, paint);
    }
}
