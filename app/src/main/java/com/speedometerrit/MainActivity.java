package com.speedometerrit;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.speedometerrit.customview.SpeedometerView;
import com.speedometerrit.helpers.RandomWidgetsGenerator;
import com.speedometerrit.speedometerwidgets.CurrentTimeView;
import com.speedometerrit.speedometerwidgets.DotsSpeedometerView;
import com.speedometerrit.helpers.SpeedometerHelper;
import com.speedometerrit.speedometerwidgets.MaxSpeedView;
import com.speedometerrit.speedometerwidgets.MiniSpeedometerView;
import com.speedometerrit.speedometerwidgets.OneLineSpeedometerView;


public class MainActivity extends AppCompatActivity {
    private ConstraintLayout mainLayout;
    private FrameLayout speedometerViewContainer;
    private FrameLayout leftViewContainer;
    private FrameLayout rightViewContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        speedometerViewContainer = findViewById(R.id.speedometer_view);
        leftViewContainer = findViewById(R.id.left_view);
        rightViewContainer = findViewById(R.id.right_view);

        setRandomViews();

        mainLayout = findViewById(R.id.main_layout);
        mainLayout.setOnClickListener(v -> setRandomViews());
    }

    private void setRandomViews() {
        setRandomCentralWidget();
        setRandomMiniWidget(leftViewContainer);
        setRandomMiniWidget(rightViewContainer);
    }

    private void setRandomCentralWidget() {
        String widgetName = RandomWidgetsGenerator.getCentralWidgetName();

        if (speedometerViewContainer.getTag() == widgetName) return;

        speedometerViewContainer.removeAllViews();
        speedometerViewContainer.setTag(widgetName);
        SpeedometerView speedometerView;

        if (RandomWidgetsGenerator.DOTS_SPEEDOMETER_VIEW.equals(widgetName)) {
            speedometerView = new DotsSpeedometerView(this);
        } else {
            speedometerView = new OneLineSpeedometerView(this);
        }
        //TODO set speed and units
        speedometerView.setSpeed(24, SpeedometerHelper.SPEED_UNITS_MPH);
        speedometerViewContainer.addView(speedometerView);
    }

    private void setRandomMiniWidget(FrameLayout viewContainer) {
        String widgetName = RandomWidgetsGenerator.getMiniWidgetName();

        if (viewContainer.getTag() == widgetName) return;

        viewContainer.removeAllViews();
        viewContainer.setTag(widgetName);

        switch (widgetName) {
            case RandomWidgetsGenerator.MINI_SPEEDOMETER_VIEW:
                MiniSpeedometerView speedometerView = new MiniSpeedometerView(this);
                //TODO set speed and units
                speedometerView.setSpeed(35, SpeedometerHelper.SPEED_UNITS_MPH);
                viewContainer.addView(speedometerView);
                break;
            case RandomWidgetsGenerator.MAX_SPEED_VIEW:
                MaxSpeedView maxSpeedView = new MaxSpeedView(this);
                //TODO set speed and units
                maxSpeedView.setMaxSpeed(135, SpeedometerHelper.SPEED_UNITS_MPH);
                viewContainer.addView(maxSpeedView);
                break;
            case RandomWidgetsGenerator.CURRENT_TIME_VIEW:
                CurrentTimeView currentTimeView = new CurrentTimeView(this);
                viewContainer.addView(currentTimeView);
                break;
            default:
                break;
        }
    }
}