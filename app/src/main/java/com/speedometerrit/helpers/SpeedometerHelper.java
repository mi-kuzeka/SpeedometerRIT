package com.speedometerrit.helpers;

import com.speedometerrit.R;

import java.util.Random;

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

    // Current speed units
    private static int speedUnits = SPEED_UNITS_KMH;
    // Max value on the scale for current speed units
    private static int maxScaleValue = DEFAULT_MAX_SCALE_VALUE_KMH;
    // Count of scale sectors
    private static int scaleSectorsCount = maxScaleValue / MAJOR_TICKS;

    // Current speed
    private static int speed;
    // Maximum speed
    private static int maxSpeed;
    // Angle of speed point on the scale
    private static float speedAngle;

    // Speedometer view size in pixels
    private float scaleSize = 660f;
    // Scale border width in pixels
    private float scaleThickness = 10f;
    // Distance from scale to dots
    private float dotsMargin = 30f;

    // Width of speedometer needle at the end
    private float needleEndWidth = 20f;
    // Width of speedometer needle at the begin
    private float needleBeginWidth = 40f;

    public SpeedometerHelper() {
    }

    /**
     * Set size of the speedometer view
     */
    public void setScaleSize(float scaleSize, boolean isSmallWidget) {
        this.scaleSize = scaleSize;
        setScaleThickness(scaleSize, isSmallWidget);
        setDotsMargin();
        setNeedleWidth();
    }

    /**
     * Get size of the speedometer view
     */
    public float getScaleSize() {
        return this.scaleSize;
    }

    /**
     * Set scale border width in pixels
     *
     * @param scaleSize is size of the speedometer view
     */
    public void setScaleThickness(float scaleSize, boolean isSmallWidget) {
        if (isSmallWidget) {
            this.scaleThickness = scaleSize / 35f;
        } else {
            this.scaleThickness = scaleSize / 65f;
        }
    }

    /**
     * Get scale border width in pixels
     */
    public float getScaleThickness() {
        return this.scaleThickness;
    }

    /**
     * Get padding of the scale in pixels
     */
    public float getScalePadding() {
        return this.scaleThickness / 2;
    }

    /**
     * Get radius of dot for DotsSpeedometer
     */
    public float getDotRadius() {
        return this.scaleSize / 130f;
    }

    private void setDotsMargin() {
        this.dotsMargin = this.scaleThickness * 3;
    }

    public float getDotsMargin() {
        return this.dotsMargin;
    }

    /**
     * Get radius of circle (in px) where the points will be located
     */
    public float getDotCircleRadius() {
        return this.scaleSize / 2 - this.scaleThickness - dotsMargin;
    }

    /**
     * Get offset of points relative to a circle
     *
     * @param circleRadius is radius of points circle
     */
    public float getDotOffset(float circleRadius) {
        return circleRadius + getScalePadding() + dotsMargin + getDotRadius();
    }

    private void setNeedleWidth() {
        this.needleEndWidth = this.scaleSize / 32.5f;
        this.needleBeginWidth = this.needleEndWidth * 2;
    }

    /**
     * Get width of speedometer needle at the begin
     */
    public float getNeedleBeginWidth() {
        return this.needleBeginWidth;
    }

    /**
     * Get width of speedometer needle at the end
     */
    public float getNeedleEndWidth() {
        return this.needleEndWidth;
    }

    /**
     * Get offset of the speedometer needle relative to its left side
     */
    public float getNeedleOffset() {
        return this.scaleThickness + (dotsMargin / 2);
    }

    /**
     * Get length of the speedometer needle in pixels
     *
     * @param needleOffset is offset of the needle relative to its left side
     */
    public float getNeedleLength(float needleOffset) {
        return (this.scaleSize / 2) - needleOffset;
    }

    /**
     * Get size of inner circle for DotsSpeedometer
     */
    public float getInnerCircleSize() {
        return this.scaleSize / INNER_CIRCLE_RATIO;
    }

    /**
     * Get top and left margin of inner circle for DotsSpeedometer
     *
     * @param innerCircleSize is the size of inner circle
     */
    public float getInnerCircleTopLeftMargin(float innerCircleSize) {
        return (this.scaleSize - innerCircleSize) / 2;
    }

    /**
     * Get the radius of the black circle in the center of the speedometer
     *
     * @param center            is the center of speedometer
     * @param innerCircleMargin is margin of gray inner circle
     */
    public float getCenterCircleRadius(float center, float innerCircleMargin) {
        return center - innerCircleMargin - getScalePadding();
    }


    /* STATIC METHODS */


    /**
     * Set current speed
     */
    public static void setSpeed(int newSpeed) {
        if (newSpeed < 0) {
            speed = 0;
        } else
            speed = Math.min(newSpeed, maxScaleValue);

        speedAngle = ((float) SCALE_SWEEP_ANGLE * (float) speed)
                / (float) maxScaleValue;
    }

    /**
     * Get current speed
     */
    public static int getSpeed() {
        return speed;
    }

    /**
     * Get maximum speed
     */
    public static int getMaxSpeed() {
        return maxSpeed;
    }

    /**
     * Get maximum speed
     */
    public static void setMaxSpeed(int newMaxSpeed) {
        maxSpeed = newMaxSpeed;
    }

    /**
     * Get angle for current speed
     */
    public float getSpeedAngle() {
        return speedAngle;
    }

    /**
     * Get angle for current speed
     */
    public static float getSpeedAngle(int currentSpeed) {
        return ((float) SCALE_SWEEP_ANGLE * (float) currentSpeed)
                / (float) maxScaleValue;
    }

    /**
     * Generate random speed
     */
    public static int getRandomSpeed() {
        Random random = new Random();
        return random.nextInt(maxScaleValue);
    }

    /**
     * Generate random maximum speed
     */
    public static int getRandomMaxSpeed() {
        Random random = new Random();
        int bound = maxScaleValue / 10 - 1;
        // Get only multiples of ten speed values (can't be zero)
        return random.nextInt(bound) * 10 + 10;
    }

    /**
     * Get the maximum scale value in current speed units
     */
    public static int getDefaultMaxScaleValue(int speedUnits) {
        if (speedUnits == SPEED_UNITS_KMH) return DEFAULT_MAX_SCALE_VALUE_KMH;
        return DEFAULT_MAX_SCALE_VALUE_MPH;
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
     * Get converted speed from km/h to mph
     *
     * @param kmh is speed in km/h
     * @return speed in mph
     */
    public static int convertSpeedToMph(int kmh) {
        return Math.round(kmh / 1.609344f);
    }

    /**
     * Get converted speed from mph to km/h
     *
     * @param mph is speed in mph
     * @return speed in km/h
     */
    public static int convertSpeedToKmh(int mph) {
        return Math.round(mph * 1.609344f);
    }

    /**
     * Change speed units
     */
    public static void changeSpeedUnits(int newSpeedUnits) {
        if (speedUnits != newSpeedUnits) {
            if (newSpeedUnits == SpeedometerHelper.SPEED_UNITS_KMH) {
                speed = SpeedometerHelper.convertSpeedToKmh(speed);
                maxSpeed = SpeedometerHelper.convertSpeedToKmh(maxSpeed);
            } else {
                speed = SpeedometerHelper.convertSpeedToMph(speed);
                maxSpeed = SpeedometerHelper.convertSpeedToMph(maxSpeed);
            }
            // Round max speed
            maxSpeed = roundToTen(maxSpeed);
            setSpeedUnits(newSpeedUnits);
        }
    }

    /**
     * Round speed to integer which ends with 0
     */
    private static int roundToTen(int speed) {
        // Smaller multiple
        int smaller = (speed / 10) * 10;
        // Larger multiple
        int larger = smaller + 10;

        if (smaller == 0) return larger;
        // Return closest of two
        return (speed - smaller > larger - speed) ? larger : smaller;
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
     * Check if the speed has reached this point
     *
     * @param dotNumber is number of current point
     */
    public static boolean pointReached(int dotNumber) {
        return speed >= dotNumber * MAJOR_TICKS;
    }

    /**
     * Check if the speed has reached this point
     *
     * @param dotNumber    is number of current point
     * @param currentSpeed is current speed
     */
    public static boolean pointReached(int dotNumber, int currentSpeed) {
        return currentSpeed >= dotNumber * MAJOR_TICKS;
    }

    /**
     * Get rotation needle angle for current speed
     */
    public static float getNeedleAngle() {
        return speedAngle - (180 - SCALE_BEGIN_ANGLE);
    }

    /**
     * Get rotation needle angle for current speed
     */
    public static float getNeedleAngle(int currentSpeed) {
        return getSpeedAngle(currentSpeed) - (180 - SCALE_BEGIN_ANGLE);
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
}
