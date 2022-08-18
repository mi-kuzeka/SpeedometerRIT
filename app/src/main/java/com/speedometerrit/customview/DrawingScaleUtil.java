package com.speedometerrit.customview;

public class DrawingScaleUtil {
    public static final byte SPEED_UNITS_KMH = 0;
    public static final byte SPEED_UNITS_MPH = 1;

    public static final int SCALE_SWEEP_ANGLE = 270;
    public static final int SCALE_BEGIN_ANGLE = 135;
    public static final int MAJOR_TICKS = 20;

    // Ratio of scale to inner circle
    public static final float INNER_CIRCLE_RATIO = 1.7f;

    public static final int DEFAULT_MAX_SCALE_VALUE_KMH = 240;
    public static final int DEFAULT_MAX_SCALE_VALUE_MPH = 160;

    // Scale border width in pixels
    public static final float SCALE_THICKNESS = 10f;
    // Radius of dots
    public static final float DOT_RADIUS = 5f;
    // Distance from scale to dots
    public static final float DOTS_MARGIN = 30f;

    public static final float NEEDLE_END_WIDTH = 20f;
    public static final float NEEDLE_BEGIN_WIDTH = 40f;

    private int speed;
    private byte speedUnits;
    private int maxScaleValue;
    private int scaleSectorsCount;
    private float speedAngle;

    private float scalePadding = SCALE_THICKNESS / 2;

    public DrawingScaleUtil(int speed, byte speedUnits) {
        setSpeed(speed, speedUnits);
    }

    public DrawingScaleUtil() {
        setSpeed(this.speed, this.speedUnits);
    }

    protected void setSpeed(int speed, byte speedUnits) {
        this.speedUnits = speedUnits;
        if (speedUnits == SPEED_UNITS_KMH) {
            this.maxScaleValue = DEFAULT_MAX_SCALE_VALUE_KMH;
        } else {
            this.maxScaleValue = DEFAULT_MAX_SCALE_VALUE_MPH;
        }
        this.scaleSectorsCount = this.maxScaleValue / MAJOR_TICKS;

        if (speed < 0) {
            this.speed = 0;
        } else
            this.speed = Math.min(speed, maxScaleValue);

        this.speedAngle = ((float) SCALE_SWEEP_ANGLE
                * (float) this.speed) / (float) maxScaleValue;
    }

    protected float getScalePadding() {
        return this.scalePadding;
    }

    protected int getMaxScaleValue() {
        return this.maxScaleValue;
    }

    /**
     * Get count of sectors on the scale
     */
    protected int getScaleSectorsCount() {
        return this.scaleSectorsCount;
    }

    protected int getSpeed() {
        return this.speed;
    }

    protected byte getSpeedUnits() {
        return this.speedUnits;
    }

    /**
     * Get angle for current speed
     */
    protected float getSpeedAngle() {
        return this.speedAngle;
    }

    /**
     * Get angle for current dot
     */
    protected float getDotAngle(int dotNumber) {
        float dotAngle = SCALE_BEGIN_ANGLE +
                ((float) SCALE_SWEEP_ANGLE / (float) this.scaleSectorsCount) * dotNumber;
        if (dotAngle < 360) return dotAngle;
        return dotAngle - 360;
    }

    protected int getDotsCount() {
        return this.scaleSectorsCount - 1;
    }

    protected boolean pointReached(int dotNumber) {
        return this.speed >= dotNumber * MAJOR_TICKS;
    }

    protected float getDotCircleRadius(float scaleSize) {
        return scaleSize / 2 - SCALE_THICKNESS - DOTS_MARGIN;
    }

    protected float getDotOffset(float circleRadius) {
        return circleRadius + scalePadding + DOTS_MARGIN + DOT_RADIUS;
    }

    /**
     * Get X coordinate for drawing dot
     */
    public static float getDotX(float angle, float circleRadius, float dotOffset) {
        return (float) ((Math.cos(Math.toRadians(angle)) * circleRadius) + dotOffset);
    }

    /**
     * Get Y coordinate for drawing dot
     */
    public static float getDotY(float angle, float circleRadius, float dotOffset) {
        return (float) ((Math.sin(Math.toRadians(angle)) * circleRadius) + dotOffset);
    }

    protected float getInnerCircleSize(float scaleSize) {
        return scaleSize / INNER_CIRCLE_RATIO;
    }

    protected float getInnerCirclePadding(float scaleSize, float innerCircleSize) {
        return (scaleSize - innerCircleSize) / 2;
    }

    protected float getInnerCircleRightBottomPadding(
            float innerCircleSize, float innerCirclePadding) {

        return innerCircleSize + innerCirclePadding;
    }

    protected float getNeedleOffset() {
        return SCALE_THICKNESS + (DOTS_MARGIN / 2);
    }

    public static float getNeedleLength(float viewSize, float needleOffset) {
        return (viewSize / 2) - needleOffset;
    }

    protected float getNeedleAngle() {
        return this.speedAngle - (180 - SCALE_BEGIN_ANGLE);
    }

    protected float getCenterCircleRadius(float center, float innerCirclePadding) {
        return center - innerCirclePadding - scalePadding;
    }
}