package com.speedometerrit.speedometerwidgets;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.speedometerrit.R;
import com.speedometerrit.customview.DotsScaleView;
import com.speedometerrit.customview.NeedleView;
import com.speedometerrit.customview.SpeedProgressView;
import com.speedometerrit.customview.SpeedView;
import com.speedometerrit.customview.SpeedometerView;

public class DotsSpeedometerView extends SpeedometerView {
    private NeedleView needleView;

    public DotsSpeedometerView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public DotsSpeedometerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        DotsScaleView scaleView = new DotsScaleView(context);
        super.addScaleView(scaleView);
        needleView = new NeedleView(context);
        super.addSpeedProgressView(needleView);
        super.setTextSize(getResources().getDimension(R.dimen.speedometer_text_size));
    }

    @Override
    public void setSpeed(int speed) {
        super.setSpeed(speed);
        needleView.setSpeed(speed);
    }
}
