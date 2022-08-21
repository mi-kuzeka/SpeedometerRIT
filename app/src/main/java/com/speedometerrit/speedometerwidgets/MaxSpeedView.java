package com.speedometerrit.speedometerwidgets;

import android.content.Context;
import android.support.annotation.NonNull;

import com.speedometerrit.R;
import com.speedometerrit.customview.MiniWidget;
import com.speedometerrit.helpers.SpeedometerHelper;

/**
 * Small widget that displays maximum speed
 */
public class MaxSpeedView extends MiniWidget {
    private int maxSpeed = 0;

    public MaxSpeedView(@NonNull Context context) {
        super(context);
        init();
    }

    private void init() {
        super.setTopImage(R.drawable.ic_widgets_name_max_long);
        super.setText(String.valueOf(maxSpeed));
        setSpeedUnits(SpeedometerHelper.getSpeedUnits());
    }

    public void setMaxSpeed(int speed) {
        this.maxSpeed = speed;
        super.setText(String.valueOf(speed));
    }

    public void setSpeedUnits(int speedUnits) {
        if (speedUnits == SpeedometerHelper.SPEED_UNITS_KMH) {
            setUnitsKMH();
        } else {
            setUnitsMPH();
        }
    }

    private void setUnitsKMH() {
        super.setBottomImage(R.drawable.ic_widgets_desc_kmh);
    }

    private void setUnitsMPH() {
        super.setBottomImage(R.drawable.ic_widgets_desc_mph);
    }
}
