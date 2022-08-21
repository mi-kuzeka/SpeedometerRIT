package com.speedometerrit.helpers;

import com.speedometerrit.R;

import java.util.Random;

public class ColorManager {
    private static int colorId = getDefaultMainColor();

    private ColorManager() {
    }

    public static int getMainColorId() {
        return colorId;
    }

    public static void setRandomMainColor() {
        int oldColorId = colorId;
        while (oldColorId == colorId) {
            setMainColor(getRandomColor());
        }
    }

    public static void setMainColor(int colorId) {
        ColorManager.colorId = colorId;
    }

    public static int getDefaultMainColor() {
        return R.color.turquoise;
    }

    public static int getScaleGradientColor() {
        return R.color.scale_gradient;
    }

    public static int getRandomColor() {
        int[] colors = {
                R.color.turquoise,
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

    public static int getTextColor() {
        return R.color.white;
    }

    public static int getOneLineScaleColor() {
        return R.color.dark_grey;
    }

    public static int getInnerCircleColor() {
        return R.color.gray;
    }

    public static int getCentralCircleColor() {
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
