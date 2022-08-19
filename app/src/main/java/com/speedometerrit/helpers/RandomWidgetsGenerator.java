package com.speedometerrit.helpers;

import java.util.Random;

public class RandomWidgetsGenerator {
    public static final String ONE_LINE_SPEEDOMETER_VIEW = "OneLineSpeedometer";
    public static final String DOTS_SPEEDOMETER_VIEW = "DotsSpeedometer";

    public static final String MINI_SPEEDOMETER_VIEW = "MiniSpeedometer";
    public static final String MAX_SPEED_VIEW = "MaxSpeed";
    public static final String CURRENT_TIME_VIEW = "CurrentTime";

    private RandomWidgetsGenerator() {
    }

    public static String getCentralWidgetName() {
        String[] widgets = {ONE_LINE_SPEEDOMETER_VIEW, DOTS_SPEEDOMETER_VIEW};
        return getRandomWidgetName(widgets);
    }

    public static String getMiniWidgetName() {
        String[] widgets = {
                MINI_SPEEDOMETER_VIEW,
                MAX_SPEED_VIEW,
                CURRENT_TIME_VIEW};
        return getRandomWidgetName(widgets);
    }

    private static String getRandomWidgetName(String[] widgets) {
        Random random = new Random();
        int randomIndex = random.nextInt(widgets.length);
        return widgets[randomIndex];
    }
}
