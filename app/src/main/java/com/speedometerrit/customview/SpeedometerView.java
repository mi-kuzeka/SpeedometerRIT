package com.speedometerrit.customview;

import android.content.Context;
import android.util.AttributeSet;

public class SpeedometerView extends CustomView {
    public SpeedometerView(Context context) {
        super(context);
    }

    public SpeedometerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setSpeed(String speed) {
        setText(speed);
    }
}
