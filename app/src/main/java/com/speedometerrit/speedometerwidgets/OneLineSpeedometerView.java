package com.speedometerrit.speedometerwidgets;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.speedometerrit.R;
import com.speedometerrit.customview.OneLineScaleView;
import com.speedometerrit.customview.SpeedProgressView;
import com.speedometerrit.customview.SpeedometerView;

public class OneLineSpeedometerView extends SpeedometerView {
    private SpeedProgressView speedProgressView;

    public OneLineSpeedometerView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public OneLineSpeedometerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        OneLineScaleView scaleView = new OneLineScaleView(context, false);
        super.addScaleView(scaleView);
        speedProgressView = new SpeedProgressView(context);
        super.addSpeedProgressView(speedProgressView);
        super.setTextSize(getResources().getDimension(R.dimen.speedometer_text_size));
    }

    @Override
    public void setSpeed(int speed) {
        super.setSpeed(speed);
        speedProgressView.setSpeed(speed);
    }
}
