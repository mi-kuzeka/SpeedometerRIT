package com.speedometerrit.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

import com.speedometerrit.helpers.ColorManager;
import com.speedometerrit.helpers.SpeedometerHelper;

public class OneLineScaleView extends View {
    private SpeedometerHelper speedometerHelper;

    // Paint object for coloring and styling
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int scaleSize = 0; // Scale size
    private float scalePadding = 0; // Padding of scale
    private int scaleColor; // Scale color
    private boolean isSmallWidget = false;

    public OneLineScaleView(Context context) {
        super(context);
        setDefaultColor();
        this.isSmallWidget = false;
        speedometerHelper = new SpeedometerHelper();
    }

    public OneLineScaleView(Context context, boolean isSmallWidget) {
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
        this.scaleColor = getColor(ColorManager.getDefaultOneLineScaleColor());
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
    }
}
