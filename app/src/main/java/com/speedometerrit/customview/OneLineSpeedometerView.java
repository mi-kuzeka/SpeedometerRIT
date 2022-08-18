package com.speedometerrit.customview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.speedometerrit.R;

public class OneLineSpeedometerView extends SpeedometerView {
    private OneLineScaleView scaleView;

    public OneLineSpeedometerView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public OneLineSpeedometerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        scaleView = new OneLineScaleView(context);
        super.addScaleView(scaleView);
        super.setTextSize(getResources().getDimension(R.dimen.speedometer_text_size));
    }

    @Override
    public void setSpeed(int speed, byte speedUnits) {
        super.setSpeed(speed, speedUnits);
        scaleView.setSpeed(speed, speedUnits);
    }
}
