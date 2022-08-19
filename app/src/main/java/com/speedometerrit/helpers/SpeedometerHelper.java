package com.speedometerrit.helpers;

public class SpeedometerHelper {
    public static final int SPEED_UNITS_KMH = 0;
    public static final int SPEED_UNITS_MPH = 1;

    // Angle of full scale
    public static final int SCALE_SWEEP_ANGLE = 270;
    // Begin angle for scale
    public static final int SCALE_BEGIN_ANGLE = 135;
    // Distance between dots in speed units
    public static final int MAJOR_TICKS = 20;

    // Ratio of scale to inner circle
    public static final float INNER_CIRCLE_RATIO = 1.7f;

    // Maximum scale value for KM/H
    public static final int DEFAULT_MAX_SCALE_VALUE_KMH = 240;
    // Maximum scale value for MPH
    public static final int DEFAULT_MAX_SCALE_VALUE_MPH = 160;

    // Scale border width in pixels
    public static final float SCALE_THICKNESS = 10f;
    // Speedometer padding
    private static final float scalePadding = SCALE_THICKNESS / 2;
    // Radius of dots
    public static final float DOT_RADIUS = 5f;
    // Distance from scale to dots
    public static final float DOTS_MARGIN = 30f;

    // Width of speedometer needle at the end
    public static final float NEEDLE_END_WIDTH = 20f;
    // Width of speedometer needle at the begin
    public static final float NEEDLE_BEGIN_WIDTH = 40f;

    // Current speed units
    private static int speedUnits = SPEED_UNITS_KMH;
    // Max value on the scale for current speed units
    private static int maxScaleValue = DEFAULT_MAX_SCALE_VALUE_KMH;
    // Count of scale sectors
    private static int scaleSectorsCount = maxScaleValue / MAJOR_TICKS;

    // Current speed
    private int speed;
    // Angle of speed point on the scale
    private float speedAngle;


    public SpeedometerHelper(int speed) {
        setSpeed(speed);
    }

    public SpeedometerHelper() {
        setSpeed(this.speed);
    }

    /**
     * Set current speed
     */
    public void setSpeed(int speed) {
        if (speed < 0) {
            this.speed = 0;
        } else
            this.speed = Math.min(speed, maxScaleValue);

        this.speedAngle = ((float) SCALE_SWEEP_ANGLE
                * (float) this.speed) / (float) maxScaleValue;
    }

    /**
     * Get current speed
     */
    public int getSpeed() {
        return this.speed;
    }

    /**
     * Get angle for current speed
     */
    public float getSpeedAngle() {
        return this.speedAngle;
    }

    /**
     * Check if the speed has reached this point
     *
     * @param dotNumber is number of current point
     */
    public boolean pointReached(int dotNumber) {
        return this.speed >= dotNumber * MAJOR_TICKS;
    }

    /**
     * Get rotation needle angle for current speed
     */
    public float getNeedleAngle() {
        return this.speedAngle - (180 - SCALE_BEGIN_ANGLE);
    }


    /* STATIC METHODS */

    /**
     * Get the maximum scale value in current speed units
     */
    public static int getDefaultMaxScaleValue(int speedUnits) {
        if (speedUnits == SPEED_UNITS_KMH) return DEFAULT_MAX_SCALE_VALUE_KMH;
        return DEFAULT_MAX_SCALE_VALUE_MPH;
    }

    /**
     * Get padding of the scale in pixels
     */
    public static float getScalePadding() {
        return scalePadding;
    }

    /**
     * Get count of sectors on the scale
     */
    public static int getScaleSectorsCount() {
        return SpeedometerHelper.scaleSectorsCount;
    }

    public static void setSpeedUnits(int speedUnits) {
        SpeedometerHelper.speedUnits = speedUnits;
        if (speedUnits == SPEED_UNITS_KMH) {
            maxScaleValue = DEFAULT_MAX_SCALE_VALUE_KMH;
        } else {
            maxScaleValue = DEFAULT_MAX_SCALE_VALUE_MPH;
        }
        scaleSectorsCount = maxScaleValue / MAJOR_TICKS;
    }

    /**
     * Get current speed units
     */
    public static int getSpeedUnits() {
        return SpeedometerHelper.speedUnits;
    }

    /**
     * Get angle for current dot
     */
    public static float getDotAngle(int dotNumber) {
        float dotAngle = SCALE_BEGIN_ANGLE +
                ((float) SCALE_SWEEP_ANGLE / (float) scaleSectorsCount) * dotNumber;
        if (dotAngle < 360) return dotAngle;
        return dotAngle - 360;
    }

    /**
     * Get count of dots on the scale
     */
    public static int getDotsCount() {
        return scaleSectorsCount - 1;
    }

    /**
     * Get radius of circle (in px) where the points will be located
     *
     * @param scaleSize is size of speedometer view
     */
    public static float getDotCircleRadius(float scaleSize) {
        return scaleSize / 2 - SCALE_THICKNESS - DOTS_MARGIN;
    }

    /**
     * Get offset of points relative to a circle
     *
     * @param circleRadius is radius of points circle
     */
    public static float getDotOffset(float circleRadius) {
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

    /**
     * Get size of inner circle for DotsSpeedometer
     *
     * @param scaleSize is speedometer size
     */
    public static float getInnerCircleSize(float scaleSize) {
        return scaleSize / INNER_CIRCLE_RATIO;
    }

    /**
     * Get top and left margin of inner circle for DotsSpeedometer
     *
     * @param scaleSize       is speedometer size
     * @param innerCircleSize is the size of inner circle
     */
    public static float getInnerCircleTopLeftMargin(float scaleSize, float innerCircleSize) {
        return (scaleSize - innerCircleSize) / 2;
    }

    /**
     * Get coordinate of right and bottom side of inner circle for DotsSpeedometer
     *
     * @param innerCircleSize    is the size of inner circle
     * @param innerCirclePadding is top and left inner circle padding
     */
    public static float getInnerCircleRightBottomCoordinate(
            float innerCircleSize, float innerCirclePadding) {

        return innerCircleSize + innerCirclePadding;
    }

    /**
     * Get offset of the speedometer needle relative to its left side
     */
    public static float getNeedleOffset() {
        return SCALE_THICKNESS + (DOTS_MARGIN / 2);
    }

    /**
     * Get length of the speedometer needle in pixels
     *
     * @param viewSize     is the size of speedometer
     * @param needleOffset is offset of the needle relative to its left side
     */
    public static float getNeedleLength(float viewSize, float needleOffset) {
        return (viewSize / 2) - needleOffset;
    }

    /**
     * Get the radius of the black circle in the center of the speedometer
     *
     * @param center            is the center of speedometer
     * @param innerCircleMargin is margin of gray inner circle
     */
    public static float getCenterCircleRadius(float center, float innerCircleMargin) {
        return center - innerCircleMargin - scalePadding;
    }
}
