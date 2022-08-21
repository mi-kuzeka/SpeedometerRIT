package com.speedometerrit.helpers;

import java.util.Random;

public class SpeedManager {
    private SpeedManager() {}

    public static final int SPEED_UNITS_KMH = 0;
    public static final int SPEED_UNITS_MPH = 1;

    // Current speed units
    private static int speedUnits = SPEED_UNITS_KMH;
    // Current speed
    private static int speed;
    // Maximum speed
    private static int maxSpeed;

    /**
     * Set current speed
     */
    public static void setSpeed(int newSpeed) {
        if (newSpeed < 0) {
            speed = 0;
        } else
            speed = Math.min(newSpeed, SpeedometerDrawingHelper.getMaxScaleValue());

        SpeedometerDrawingHelper.setSpeedAngle();
    }

    /**
     * Get current speed
     */
    public static int getSpeed() {
        return speed;
    }

    /**
     * Set maximum speed
     */
    public static void setMaxSpeed(int newMaxSpeed) {
        maxSpeed = newMaxSpeed;
    }

    /**
     * Get maximum speed
     */
    public static int getMaxSpeed() {
        return maxSpeed;
    }

    /**
     * Generate random speed
     */
    public static int getRandomSpeed() {
        Random random = new Random();
        return random.nextInt(SpeedometerDrawingHelper.getMaxScaleValue());
    }

    /**
     * Generate random maximum speed
     */
    public static int getRandomMaxSpeed() {
        Random random = new Random();
        int bound = SpeedometerDrawingHelper.getMaxScaleValue() / 10 - 1;
        // Get only multiples of ten speed values (can't be zero)
        return random.nextInt(bound) * 10 + 10;
    }

    /**
     * Set speed units
     */
    public static void setSpeedUnits(int newSpeedUnits) {
        SpeedManager.speedUnits = newSpeedUnits;
        SpeedometerDrawingHelper.setMaxScaleValue();
    }

    /**
     * Get current speed units
     */
    public static int getSpeedUnits() {
        return speedUnits;
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
            if (newSpeedUnits == SPEED_UNITS_KMH) {
                speed = convertSpeedToKmh(speed);
                maxSpeed = convertSpeedToKmh(maxSpeed);
            } else {
                speed = convertSpeedToMph(speed);
                maxSpeed = convertSpeedToMph(maxSpeed);
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
}
