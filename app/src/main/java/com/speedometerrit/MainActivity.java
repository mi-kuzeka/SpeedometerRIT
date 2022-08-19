package com.speedometerrit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.speedometerrit.speedometerwidgets.CurrentTimeView;
import com.speedometerrit.speedometerwidgets.DotsSpeedometerView;
import com.speedometerrit.speedometerwidgets.MaxSpeedView;
import com.speedometerrit.helpers.SpeedometerHelper;
import com.speedometerrit.speedometerwidgets.MiniSpeedometerView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setViews();
    }

    private void setViews() {
        FrameLayout speedometerViewContainer = findViewById(R.id.speedometer_view);
        FrameLayout leftViewContainer = findViewById(R.id.left_view);
        FrameLayout rightViewContainer = findViewById(R.id.right_view);

//        DotsScaleView scaleView = new DotsScaleView(this);
//        scaleView.setSpeed(40, DrawingScaleUtil.SPEED_UNITS_KMH);
//        speedometerViewContainer.addView(scaleView);

//        OneLineScaleView scaleView = new OneLineScaleView(this);
//        scaleView.setSpeed(120, DrawingScaleUtil.SPEED_UNITS_KMH);
//        speedometerViewContainer.addView(scaleView);

//        OneLineSpeedometerView speedometerView = new OneLineSpeedometerView(this);
//        speedometerView.setSpeed(24, DrawingScaleUtil.SPEED_UNITS_KMH);
//        speedometerViewContainer.addView(speedometerView);

        DotsSpeedometerView speedometerView = new DotsSpeedometerView(this);
        speedometerView.setSpeed(60, SpeedometerHelper.SPEED_UNITS_KMH);
        speedometerViewContainer.addView(speedometerView);

//        MaxSpeedView maxSpeedView = new MaxSpeedView(this);
//        maxSpeedView.setMaxSpeed(134, SpeedometerHelper.SPEED_UNITS_MPH);
//        leftViewContainer.addView(maxSpeedView);
//
        MiniSpeedometerView scaleView1 = new MiniSpeedometerView(this);
        scaleView1.setSpeed(24, SpeedometerHelper.SPEED_UNITS_MPH);
        leftViewContainer.addView(scaleView1);

        CurrentTimeView currentTimeView = new CurrentTimeView(this);
        rightViewContainer.addView(currentTimeView);

//        OneLineScaleView scaleView2 = new OneLineScaleView(this);
//        scaleView2.setSpeed(120, SpeedometerHelper.SPEED_UNITS_KMH);
//        rightViewContainer.addView(scaleView2);
//        SpeedometerView speedometerView = new SpeedometerView(this);
//        speedometerView.setSpeed(24);
//        speedometerView.setTextSize(getResources().getDimension(R.dimen.speedometer_text_size));
//        speedometerViewContainer.addView(speedometerView);
    }
}