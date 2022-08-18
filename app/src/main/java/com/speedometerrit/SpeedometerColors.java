package com.speedometerrit;

import java.util.Random;

public class SpeedometerColors {
    private SpeedometerColors() {
    }

    public static int getDefaultDotsScaleColor() {
        return R.color.turquoise;
    }

    public static int getScaleGradientColor() {
        return R.color.scale_gradient;
    }

    public static int getRandomColor() {
        int[] colors = {R.color.turquoise,
                R.color.green,
                R.color.azure,
                R.color.purple,
                R.color.violet,
                R.color.blue,
                R.color.white,
                R.color.lemon};
        Random random = new Random();
        int randomIndex = random.nextInt(colors.length);
        return colors[randomIndex];
    }

    public static int getDefaultTextColor() {
        return R.color.white;
    }

    public static int getDefaultOneLineScaleColor() {
        return R.color.dark_grey;
    }

    public static int getDefaultSpeedProgressColor() {
        return R.color.turquoise;
    }

    public static int getInnerCircleColor() {
        return R.color.gray;
    }

    public static int getCenterCircleColor() {
        return R.color.black;
    }

    public static int getNeedleColor() {
        return R.color.white;
    }

    public static int getReachedPointColor() {
        return R.color.white;
    }

    public static int getPointColor() {
        return R.color.gray;
    }
}
