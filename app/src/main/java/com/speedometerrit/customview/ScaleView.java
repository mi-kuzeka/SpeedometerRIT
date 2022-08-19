package com.speedometerrit.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

public abstract class ScaleView extends View {
    public ScaleView(Context context) {
        super(context);
    }

    public abstract void setSpeed(int speed, int speedUnits);
    protected abstract void drawScale(Canvas canvas);
}
