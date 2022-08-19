package com.speedometerrit;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import com.speedometerrit.customview.SpeedometerView;
import com.speedometerrit.helpers.RandomWidgetsGenerator;
import com.speedometerrit.helpers.ColorManager;
import com.speedometerrit.helpers.SpeedometerHelper;
import com.speedometerrit.speedometerwidgets.CurrentTimeView;
import com.speedometerrit.speedometerwidgets.DotsSpeedometerView;
import com.speedometerrit.speedometerwidgets.MaxSpeedView;
import com.speedometerrit.speedometerwidgets.MiniSpeedometerView;
import com.speedometerrit.speedometerwidgets.OneLineSpeedometerView;


public class MainActivity extends AppCompatActivity {
    GestureDetector gestureDetector;
    private boolean widgetsAreLoaded = false;

    private ConstraintLayout transitionsContainer;
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
        transitionsContainer = findViewById(R.id.main_layout);

        speedometerViewContainer = findViewById(R.id.speedometer_view);
        leftViewContainer = findViewById(R.id.left_view);
        rightViewContainer = findViewById(R.id.right_view);

        setRandomViews();

        gestureDetector = new GestureDetector(this, new GestureListener());
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        return gestureDetector.onTouchEvent(e);
    }

    private void setRandomViews() {
        widgetsAreLoaded = false;
        setRandomMiniWidget(leftViewContainer);
        setRandomCentralWidget();
        setRandomMiniWidget(rightViewContainer);
        widgetsAreLoaded = true;
    }

    private void setRandomCentralWidget() {
        String widgetName = RandomWidgetsGenerator.getCentralWidgetName();
        if (speedometerViewContainer.getTag() == widgetName) return;

        setCentralWidget(widgetName);
    }

    private void setRandomMiniWidget(FrameLayout viewContainer) {
        String widgetName = RandomWidgetsGenerator.getMiniWidgetName();
        if (viewContainer.getTag() == widgetName) return;

        setMiniWidget(viewContainer, widgetName);
    }

    private void setCentralWidget(String widgetName) {
        speedometerViewContainer.setTag(widgetName);
        speedometerViewContainer.removeAllViews();

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

    private void setMiniWidget(FrameLayout viewContainer, String widgetName) {
        viewContainer.setTag(widgetName);
        viewContainer.removeAllViews();

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

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            if (!widgetsAreLoaded) return true;
            TransitionManager.beginDelayedTransition(transitionsContainer);
            setRandomViews();
            return true;
        }

        // Event when double tap occurs
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            if (!widgetsAreLoaded) return true;

            ColorManager.setRandomMainColor();

            // Reload views
            String centralWidgetName = (String) speedometerViewContainer.getTag();
            speedometerViewContainer.removeAllViews();
            setCentralWidget(centralWidgetName);

            String leftWidgetName = (String) leftViewContainer.getTag();
            leftViewContainer.removeAllViews();
            setMiniWidget(leftViewContainer, leftWidgetName);

            String rightWidgetName = (String) rightViewContainer.getTag();
            rightViewContainer.removeAllViews();
            setMiniWidget(rightViewContainer, rightWidgetName);

            return true;
        }
    }
}