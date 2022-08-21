package com.speedometerrit.customview;

import android.content.Context;
import android.view.View;

/**
 * Parent abstract class for speed progress views:
 * {@link NeedleSpeedView} and {@link SpeedLineView}
 */
public abstract class SpeedView extends View {
    public SpeedView(Context context) {
        super(context);
    }

    public abstract void setSpeed(int speed);
}
