package com.speedometerrit;

import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.speedometerrit.customview.SpeedometerView;
import com.speedometerrit.helpers.WidgetNamesGenerator;
import com.speedometerrit.helpers.ColorManager;
import com.speedometerrit.helpers.SpeedometerHelper;
import com.speedometerrit.speedometerwidgets.CurrentTimeView;
import com.speedometerrit.speedometerwidgets.DotsSpeedometerView;
import com.speedometerrit.speedometerwidgets.MaxSpeedView;
import com.speedometerrit.speedometerwidgets.MiniSpeedometerView;
import com.speedometerrit.speedometerwidgets.OneLineSpeedometerView;


public class MainActivity extends AppCompatActivity {
    private final Handler handler = new Handler();
    private Runnable runnable;
    private final int delay = 2000; // Update speed every 2 sec

    private final String LOG_TAG = MainActivity.class.getSimpleName();

    // Gesture detector for tracking touches
    private GestureDetector gestureDetector;

    // Ensures that all widgets have been loaded
    private boolean widgetsLoaded = false;
    // Animation time
    private int shortAnimationDuration;

    // Container for animation
    private ConstraintLayout transitionsContainer;

    // Widget containers
    private FrameLayout speedometerViewContainer;
    private FrameLayout leftViewContainer;
    private FrameLayout rightViewContainer;

    private int speed = 0;
    private int maxSpeed = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    @Override
    protected void onPause() {
        // Stop handler when activity isn't visible
        handler.removeCallbacks(runnable);
        super.onPause();
    }

    @Override
    protected void onResume() {
        // Refresh speed
        handler.postDelayed(runnable = () -> {
            handler.postDelayed(runnable, delay);
            if (widgetsLoaded) setSpeed(SpeedometerHelper.getRandomSpeed());
        }, delay);
        super.onResume();
    }

    private void init() {
        // Set container for animation
        transitionsContainer = findViewById(R.id.main_layout);
        // Retrieve and cache the system's default "short" animation time.
        shortAnimationDuration = getResources().getInteger(
                android.R.integer.config_shortAnimTime);

        // View containers
        speedometerViewContainer = findViewById(R.id.speedometer_view);
        leftViewContainer = findViewById(R.id.left_view);
        rightViewContainer = findViewById(R.id.right_view);

        // Init speed units radioGroup
        RadioGroup speedUnitsGroup = findViewById(R.id.speed_units_group);
        speedUnitsGroup.setOnCheckedChangeListener((radioGroup, checkedId) -> {
            if (checkedId == R.id.kmh_button) {
                setSpeedUnits(SpeedometerHelper.SPEED_UNITS_KMH);
            } else if (checkedId == R.id.mph_button) {
                setSpeedUnits(SpeedometerHelper.SPEED_UNITS_MPH);
            }
        });
        RadioButton kmhButton = findViewById(R.id.kmh_button);
        kmhButton.setChecked(true);

        // Show random views
        setRandomViews();

        // Create detector for tracking touches
        gestureDetector = new GestureDetector(this, new GestureListener());
        setSpeed(SpeedometerHelper.getRandomSpeed());
    }

    /**
     * Change speed units
     *
     * @param speedUnits is an integer value of units from {@link SpeedometerHelper}
     */
    private void setSpeedUnits(int speedUnits) {
        SpeedometerHelper.setSpeedUnits(speedUnits);
        initAnimation();
        reloadViews();
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        return gestureDetector.onTouchEvent(e);
    }

    private void setRandomViews() {
        widgetsLoaded = false;
        setRandomMiniWidget(leftViewContainer);
        setRandomCentralWidget();
        setRandomMiniWidget(rightViewContainer);
        widgetsLoaded = true;
    }

    private void setRandomCentralWidget() {
        // Generate random widget name for central view
        String widgetName = WidgetNamesGenerator.getCentralWidgetName();
        // There's no need to replace view with the same one
        if (speedometerViewContainer.getTag() == widgetName) return;

        // Replace widget with new one
        setCentralWidget(widgetName);
    }

    private void setRandomMiniWidget(FrameLayout viewContainer) {
        String widgetName = WidgetNamesGenerator.getMiniWidgetName();
        if (viewContainer.getTag() == widgetName) return;

        setMiniWidget(viewContainer, widgetName);
    }

    private void setCentralWidget(String widgetName) {
        speedometerViewContainer.setTag(widgetName);
        speedometerViewContainer.removeAllViews();

        SpeedometerView speedometerView;

        if (WidgetNamesGenerator.DOTS_SPEEDOMETER_VIEW.equals(widgetName)) {
            speedometerView = new DotsSpeedometerView(this);
        } else {
            speedometerView = new OneLineSpeedometerView(this);
        }
        speedometerView.setSpeed(this.speed);
        speedometerViewContainer.addView(speedometerView);
    }

    private void setMiniWidget(FrameLayout viewContainer, String widgetName) {
        viewContainer.setTag(widgetName);
        viewContainer.removeAllViews();

        switch (widgetName) {
            case WidgetNamesGenerator.MINI_SPEEDOMETER_VIEW:
                MiniSpeedometerView speedometerView = new MiniSpeedometerView(this);
                speedometerView.setSpeed(this.speed);
                viewContainer.addView(speedometerView);
                break;
            case WidgetNamesGenerator.MAX_SPEED_VIEW:
                MaxSpeedView maxSpeedView = new MaxSpeedView(this);
                //TODO set speeds
                maxSpeedView.setMaxSpeed(this.maxSpeed);
                viewContainer.addView(maxSpeedView);
                break;
            case WidgetNamesGenerator.CURRENT_TIME_VIEW:
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
            if (!widgetsLoaded) return true;
            initAnimation();
            setRandomViews();
            return true;
        }

        // Event when double tap occurs
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            if (!widgetsLoaded) return true;

            // Change speedometer color
            ColorManager.setRandomMainColor();
            reloadViews();

            return true;
        }
    }

    /**
     * Initialize crossfade animation
     */
    private void initAnimation() {
        AutoTransition autoTransition = new AutoTransition();
        autoTransition.setDuration(shortAnimationDuration);
        TransitionManager.beginDelayedTransition(transitionsContainer, autoTransition);
    }

    private void reloadViews() {
        Object centralWidgetName = speedometerViewContainer.getTag();
        if (centralWidgetName != null) {
            speedometerViewContainer.removeAllViews();
            setCentralWidget((String) centralWidgetName);
        }

        Object leftWidgetName = leftViewContainer.getTag();
        if (leftWidgetName != null) {
            leftViewContainer.removeAllViews();
            setMiniWidget(leftViewContainer, (String) leftWidgetName);
        }

        Object rightWidgetName = rightViewContainer.getTag();
        if (rightWidgetName != null) {
            rightViewContainer.removeAllViews();
            setMiniWidget(rightViewContainer, (String) rightWidgetName);
        }
    }

    private void setSpeed(int speed) {
        this.speed = speed;
        this.maxSpeed = Math.max(speed, maxSpeed);

        setSpeedToMiniSpeedometer(leftViewContainer);
        setSpeedToBigSpeedometer();
        setSpeedToMiniSpeedometer(rightViewContainer);
    }

    private void setSpeedToBigSpeedometer() {
        if (speedometerViewContainer.getTag() != null) {
            View view = speedometerViewContainer.getChildAt(0);
            try {
                SpeedometerView speedometerView = (SpeedometerView) view;
                speedometerView.setSpeed(speed);
            } catch (ClassCastException e) {
                Log.e(LOG_TAG, "Can't cast View to SpeedometerView");
            }
        }
    }

    private void setSpeedToMiniSpeedometer(FrameLayout viewContainer) {
        if (isMiniSpeedometer(viewContainer)) {
            View view = viewContainer.getChildAt(0);
            try {
                MiniSpeedometerView speedometerView = (MiniSpeedometerView) view;
                speedometerView.setSpeed(speed);
            } catch (ClassCastException e) {
                Log.e(LOG_TAG, "Can't cast View to MiniSpeedometerView");
            }
        }
    }

    private boolean isMiniSpeedometer(FrameLayout viewContainer) {
        Object widgetName = viewContainer.getTag();
        if (widgetName != null) {
            return widgetName.equals(WidgetNamesGenerator.MINI_SPEEDOMETER_VIEW);
        }
        return false;
    }
}