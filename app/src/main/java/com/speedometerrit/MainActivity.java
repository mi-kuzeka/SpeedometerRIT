package com.speedometerrit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.speedometerrit.customview.DotsScaleView;
import com.speedometerrit.customview.DrawingScaleHelper;
import com.speedometerrit.customview.OneLineScaleView;

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

        DotsScaleView scaleView = new DotsScaleView(this);
        scaleView.setSpeed(120, DrawingScaleHelper.SPEED_UNITS_KMH);
        speedometerViewContainer.addView(scaleView);

        OneLineScaleView scaleView1 = new OneLineScaleView(this);
        scaleView1.setSpeed(120, DrawingScaleHelper.SPEED_UNITS_MPH);
        leftViewContainer.addView(scaleView1);
        OneLineScaleView scaleView2 = new OneLineScaleView(this);
        scaleView2.setSpeed(120, DrawingScaleHelper.SPEED_UNITS_KMH);
        rightViewContainer.addView(scaleView2);
//        SpeedometerView speedometerView = new SpeedometerView(this);
//        speedometerView.setSpeed(24);
//        speedometerView.setTextSize(getResources().getDimension(R.dimen.speedometer_text_size));
//        speedometerViewContainer.addView(speedometerView);
    }
}