package com.speedometerrit.customview;

import android.content.Context;
import android.view.View;

public class ScaleView extends View {
    public static final byte SPEED_UNITS_KMH = 0;
    public static final byte SPEED_UNITS_MPH = 1;

    protected static final int SCALE_SWEEP_ANGLE = 270;
    protected static final int SCALE_BEGIN_ANGLE = 135;
    protected static final int DEFAULT_MAJOR_TICKS = 20;
    protected static final int DEFAULT_MINOR_TICKS = 1;

    public static final int DEFAULT_MAX_SCALE_VALUE_KMH = 240;
    public static final int DEFAULT_MAX_SCALE_VALUE_MPH = 160;

    private int speed = 0;
    private byte speedUnits = SPEED_UNITS_KMH;
    private int maxScaleValue = DEFAULT_MAX_SCALE_VALUE_KMH;
    private int scaleSectorsCount = maxScaleValue / DEFAULT_MAJOR_TICKS;

    public ScaleView(Context context) {
        super(context);
    }

    public void setSpeed(int speed, byte speedUnits) {
        this.speedUnits = speedUnits;
        if (speedUnits == SPEED_UNITS_KMH) {
            this.maxScaleValue = DEFAULT_MAX_SCALE_VALUE_KMH;
        } else {
            this.maxScaleValue = DEFAULT_MAX_SCALE_VALUE_MPH;
        }
        this.scaleSectorsCount = this.maxScaleValue / DEFAULT_MAJOR_TICKS;

        if (speed < 0) {
            this.speed = 0;
        } else this.speed = Math.min(speed, maxScaleValue);
    }

    protected int getMaxScaleValue() {
        return this.maxScaleValue;
    }

    protected int getScaleSectorsCount() {
        return this.scaleSectorsCount;
    }

    protected int getSpeed() {
        return this.speed;
    }

    protected byte getSpeedUnits() {
        return this.speedUnits;
    }
}
