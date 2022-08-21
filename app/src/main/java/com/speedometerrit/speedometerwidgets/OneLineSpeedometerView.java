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

    public OneLineSpeedometerView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public OneLineSpeedometerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        super.addScaleView(new OneLineScaleView(context, false));
        super.addSpeedProgressView(new SpeedProgressView(context));
        super.setTextSize(getResources().getDimension(R.dimen.speedometer_text_size));
    }

    @Override
    public void setSpeed(int speed) {
        super.setSpeed(speed);
    }
}
