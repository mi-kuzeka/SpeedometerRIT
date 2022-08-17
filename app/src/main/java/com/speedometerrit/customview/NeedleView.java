package com.speedometerrit.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

import com.speedometerrit.R;

public class NeedleView extends View {
    private final DrawingScaleUtil drawingScaleUtil;
    // Paint object for coloring and styling
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private int scaleSize = 0; // Scale size
    private int innerCircleSize = 0; // Inner circle size

    public NeedleView(Context context) {
        super(context);
        drawingScaleUtil = new DrawingScaleUtil();
    }

    public void setSpeed(int speed, byte speedUnits) {
        drawingScaleUtil.setSpeed(speed, speedUnits);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        scaleSize = Math.min(getMeasuredWidth(), getMeasuredHeight());
        setMeasuredDimension(scaleSize, scaleSize);

        scaleSize -= DrawingScaleUtil.SCALE_THICKNESS;
        innerCircleSize = DrawingScaleUtil.getInnerCircleSize(scaleSize);
        ;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawNeedle(canvas);
    }

    private void drawNeedle(Canvas canvas) {
        paint.setColor(getResources().getColor(R.color.white));
        paint.setStyle(Paint.Style.FILL);

        float needleOffset = DrawingScaleUtil.getNeedleOffset();
        float needleLength = DrawingScaleUtil.getNeedleLength(scaleSize, needleOffset);
        float center = (float) scaleSize / 2;

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

        canvas.drawPath(needle, paint);
    }

}
