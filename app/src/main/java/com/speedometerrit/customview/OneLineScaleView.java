package com.speedometerrit.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

import com.speedometerrit.helpers.ColorManager;
import com.speedometerrit.helpers.SpeedometerDrawingHelper;

/**
 * This class is draws scale for OneLineSpeedometer -
 * only unchanged shapes
 */
public class OneLineScaleView extends View {
    private final SpeedometerDrawingHelper speedometerDrawingHelper;

    // Paint object for coloring and styling
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    // Parent view size
    private int viewSize = 0;
    private float scalePadding = 0;
    private int scaleColor;
    // The flag indicates that the view is called from mini widget
    private final boolean isSmallWidget;

    public OneLineScaleView(Context context) {
        super(context);
        setDefaultColor();
        this.isSmallWidget = false;
        speedometerDrawingHelper = new SpeedometerDrawingHelper();
    }

    public OneLineScaleView(Context context, boolean isSmallWidget) {
        super(context);
        setDefaultColor();
        this.isSmallWidget = isSmallWidget;
        speedometerDrawingHelper = new SpeedometerDrawingHelper();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // Get view size in pixels
        viewSize = Math.min(getMeasuredWidth(), getMeasuredHeight());
        setMeasuredDimension(viewSize, viewSize);

        speedometerDrawingHelper.setScaleSize(viewSize, isSmallWidget);
        scalePadding = speedometerDrawingHelper.getScalePadding();
    }

    private void setDefaultColor() {
        this.scaleColor = getColor(ColorManager.getOneLineScaleColor());
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
        paint.setStrokeWidth(speedometerDrawingHelper.getScaleThickness());

        // Get the scale bounds
        RectF oval = new RectF(scalePadding, scalePadding,
                viewSize - scalePadding, viewSize - scalePadding);
        // Draw the scale
        canvas.drawArc(oval, SpeedometerDrawingHelper.SCALE_BEGIN_ANGLE,
                SpeedometerDrawingHelper.SCALE_SWEEP_ANGLE, false, paint);
    }
}
