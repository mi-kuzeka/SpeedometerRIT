package com.speedometerrit.customview;

public class DrawingScaleHelper {
    public static final byte SPEED_UNITS_KMH = 0;
    public static final byte SPEED_UNITS_MPH = 1;

    public static final int SCALE_SWEEP_ANGLE = 270;
    public static final int SCALE_BEGIN_ANGLE = 135;
    public static final int DEFAULT_MAJOR_TICKS = 20;
    public static final int DEFAULT_MINOR_TICKS = 1;
    // Ratio of scale to inner circle
    public static final double DEFAULT_INNER_CIRCLE_RATIO = 1.7;

    public static final int DEFAULT_MAX_SCALE_VALUE_KMH = 240;
    public static final int DEFAULT_MAX_SCALE_VALUE_MPH = 160;


    private int speed;
    private byte speedUnits;
    private int maxScaleValue;
    private int scaleSectorsCount;
    private float speedAngle;

    public DrawingScaleHelper(int speed, byte speedUnits) {
        setSpeed(speed, speedUnits);
    }

    public DrawingScaleHelper() {
        setSpeed(this.speed, this.speedUnits);
    }

    protected void setSpeed(int speed, byte speedUnits) {
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
        this.speedAngle = ((float) SCALE_SWEEP_ANGLE * (float) this.speed) / (float) maxScaleValue;
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

    protected float getSpeedAngle() {
        return this.speedAngle;
    }

    protected float getDotAngle(int dotNumber) {
        float dotAngle = SCALE_BEGIN_ANGLE +
                ((float) SCALE_SWEEP_ANGLE / (float) this.scaleSectorsCount) * dotNumber;
        if (dotAngle < 360) return dotAngle;
        return dotAngle - 360;
    }

    protected int getDotsCount() {
        return this.scaleSectorsCount - 1;
    }

    public static int getInnerCircleSize(int scaleSize) {
        return (int) Math.round(scaleSize / DEFAULT_INNER_CIRCLE_RATIO);
    }

    public static int getInnerCirclePadding(int scaleSize, int innerCircleSize) {
        return (scaleSize - innerCircleSize) / 2;
    }

    public static int getInnerCircleRightBottomPadding(
            int innerCircleSize, int innerCirclePadding) {

        return innerCircleSize + innerCirclePadding;
    }
}
