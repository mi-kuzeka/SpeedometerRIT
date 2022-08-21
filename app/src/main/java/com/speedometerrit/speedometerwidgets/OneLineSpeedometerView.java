package com.speedometerrit.speedometerwidgets;

import android.content.Context;
import android.support.annotation.NonNull;

import com.speedometerrit.R;
import com.speedometerrit.customview.OneLineScaleView;
import com.speedometerrit.customview.SpeedLineView;
import com.speedometerrit.customview.CentralSpeedometerView;

/**
 * Central "one-line" speedometer widget (without needle)
 */
public class OneLineSpeedometerView extends CentralSpeedometerView {

    public OneLineSpeedometerView(@NonNull Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        // Add fixed scale
        super.addScaleView(new OneLineScaleView(context, false));
        // Add view that draws speed progress on the scale
        super.addSpeedProgressView(new SpeedLineView(context));
        // Set size of speed text
        super.setTextSize(getResources().getDimension(R.dimen.speedometer_text_size));
    }

    @Override
    public void setSpeed(int speed) {
        super.setSpeed(speed);
    }
}
