package com.speedometerrit.speedometerwidgets;

import android.content.Context;
import android.support.annotation.NonNull;

import com.speedometerrit.R;
import com.speedometerrit.customview.DotsScaleView;
import com.speedometerrit.customview.NeedleSpeedView;
import com.speedometerrit.customview.CentralSpeedometerView;

/**
 * Central speedometer widget with needle and dots on the scale
 */
public class DotsSpeedometerView extends CentralSpeedometerView {
    private NeedleSpeedView needleSpeedView;

    public DotsSpeedometerView(@NonNull Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        // Add fixed scale
        super.addScaleView(new DotsScaleView(context));
        // Add view that draws speed progress on the scale
        needleSpeedView = new NeedleSpeedView(context);
        super.addSpeedProgressView(needleSpeedView);
        // Set size of speed text
        super.setTextSize(getResources().getDimension(R.dimen.speedometer_text_size));
    }

    @Override
    public void setSpeed(int speed) {
        super.setSpeed(speed);
    }
}
