package com.speedometerrit.customview;

import android.content.Context;
import android.view.View;

public abstract class SpeedView extends View {
    public SpeedView(Context context) {
        super(context);
    }

    public abstract void setSpeed(int speed);
}
