package com.speedometerrit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.speedometerrit.customview.SpeedometerView;

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
        SpeedometerView speedometerView = new SpeedometerView(this);
        speedometerView.setSpeed("24");
        speedometerViewContainer.addView(speedometerView);
    }
}