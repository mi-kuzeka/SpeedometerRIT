package com.speedometerrit.customview;

import android.content.Context;
import android.support.annotation.NonNull;

import com.speedometerrit.R;

public class MaxSpeedView extends MiniWidget {
    private int maxSpeed = 0;
    private int speedUnits = SpeedometerHelper.SPEED_UNITS_KMH;

    public MaxSpeedView(@NonNull Context context) {
        super(context);
        init();
    }

    private void init() {
        super.setTopImage(R.drawable.ic_widgets_name_max_long);
        super.setText(String.valueOf(maxSpeed));
        setUnitsKMH();
    }

    public void setMaxSpeed(int speed, int speedUnits) {
        setMaxSpeed(speed);
        setSpeedUnits(speedUnits);
    }

    public void setMaxSpeed(int speed) {
        this.maxSpeed = speed;
        super.setText(String.valueOf(speed));
    }

    public void setSpeedUnits(int speedUnits) {
        this.speedUnits = speedUnits;
        if (this.speedUnits == SpeedometerHelper.SPEED_UNITS_KMH) {
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
