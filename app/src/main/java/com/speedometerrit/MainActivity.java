package com.speedometerrit;

import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.speedometerrit.customview.CentralSpeedometerView;
import com.speedometerrit.helpers.SpeedManager;
import com.speedometerrit.helpers.WidgetNamesManager;
import com.speedometerrit.helpers.ColorManager;
import com.speedometerrit.helpers.WidgetsSpeedManager;
import com.speedometerrit.speedometerwidgets.CurrentTimeView;
import com.speedometerrit.speedometerwidgets.DotsSpeedometerView;
import com.speedometerrit.speedometerwidgets.MaxSpeedView;
import com.speedometerrit.speedometerwidgets.MiniSpeedometerView;
import com.speedometerrit.speedometerwidgets.OneLineSpeedometerView;


public class MainActivity extends AppCompatActivity {
    // Handler for generation random speed
    private final Handler handler = new Handler();
    private Runnable runnable;
    private final int delay = 2000; // Update speed every 2 sec

    // Gesture detector for tracking touches
    private GestureDetector gestureDetector;
    // Manager for updating speed in widgets
    private WidgetsSpeedManager widgetsSpeedManager;

    // Ensures that all widgets have been loaded
    private boolean widgetsLoaded = false;
    // Animation time
    private int shortAnimationDuration;

    // Container for animation
    private ConstraintLayout transitionsContainer;

    // Widget containers
    private FrameLayout centralViewContainer;
    private FrameLayout leftViewContainer;
    private FrameLayout rightViewContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    @Override
    protected void onResume() {
        // Refresh speed
        handler.postDelayed(runnable = () -> {
            handler.postDelayed(runnable, delay);
            if (widgetsLoaded) {
                widgetsSpeedManager.setSpeed(SpeedManager.getRandomSpeed());
                widgetsSpeedManager.setMaxSpeed(SpeedManager.getRandomMaxSpeed());
            }
        }, delay);
        super.onResume();
    }

    @Override
    protected void onPause() {
        // Stop handler when activity isn't visible
        handler.removeCallbacks(runnable);
        super.onPause();
    }

    private void init() {
        // Set container for animation
        transitionsContainer = findViewById(R.id.main_layout);
        // Retrieve and cache the system's default "short" animation time.
        shortAnimationDuration = getResources().getInteger(
                android.R.integer.config_shortAnimTime);

        // View containers
        centralViewContainer = findViewById(R.id.central_view);
        leftViewContainer = findViewById(R.id.left_view);
        rightViewContainer = findViewById(R.id.right_view);

        widgetsSpeedManager = new WidgetsSpeedManager(leftViewContainer,
                centralViewContainer, rightViewContainer);

        // Init speed units radioGroup
        RadioGroup speedUnitsGroup = findViewById(R.id.speed_units_group);
        speedUnitsGroup.setOnCheckedChangeListener((radioGroup, checkedId) -> {
            if (checkedId == R.id.kmh_button) {
                setSpeedUnits(SpeedManager.SPEED_UNITS_KMH);
            } else if (checkedId == R.id.mph_button) {
                setSpeedUnits(SpeedManager.SPEED_UNITS_MPH);
            }
        });
        // Set defaults speed units - km/h
        RadioButton kmhButton = findViewById(R.id.kmh_button);
        kmhButton.setChecked(true);

        // Show random views
        setRandomViews();

        // Create detector for tracking touches
        gestureDetector = new GestureDetector(this, new GestureListener());
        widgetsSpeedManager.setSpeed(SpeedManager.getRandomSpeed());
        widgetsSpeedManager.setMaxSpeed(SpeedManager.getRandomMaxSpeed());
    }

    /**
     * Change speed units
     *
     * @param speedUnits is an integer value of units from {@link SpeedManager}
     */
    private void setSpeedUnits(int speedUnits) {
        SpeedManager.changeSpeedUnits(speedUnits);
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
        String widgetName = WidgetNamesManager.getCentralWidgetName();
        // There's no need to replace view with the same one
        if (centralViewContainer.getTag() == widgetName) return;

        // Replace widget with new one
        setCentralWidget(widgetName);
    }

    private void setRandomMiniWidget(FrameLayout viewContainer) {
        // Generate random widget name for side view
        String widgetName = WidgetNamesManager.getMiniWidgetName();
        // There's no need to replace view with the same one
        if (viewContainer.getTag() == widgetName) return;

        // Replace widget with new one
        setMiniWidget(viewContainer, widgetName);
    }

    /**
     * Set certain widget to central view container
     *
     * @param widgetName is the name of widget from {@link WidgetNamesManager}
     */
    private void setCentralWidget(String widgetName) {
        // Save widget name to container tag
        centralViewContainer.setTag(widgetName);
        // Remove previous widget
        centralViewContainer.removeAllViews();

        CentralSpeedometerView centralSpeedometerView;
        // Create required widget
        if (WidgetNamesManager.DOTS_SPEEDOMETER_VIEW.equals(widgetName)) {
            centralSpeedometerView = new DotsSpeedometerView(this);
        } else {
            centralSpeedometerView = new OneLineSpeedometerView(this);
        }
        // Set current speed to widget
        centralSpeedometerView.setSpeed(SpeedManager.getSpeed());
        // Add widget to container
        centralViewContainer.addView(centralSpeedometerView);
    }

    /**
     * Set certain widget to side view container
     *
     * @param viewContainer is container for widget
     * @param widgetName    is the name of widget from {@link WidgetNamesManager}
     */
    private void setMiniWidget(FrameLayout viewContainer, String widgetName) {
        // Save widget name to container tag
        viewContainer.setTag(widgetName);
        // Remove previous widget
        viewContainer.removeAllViews();

        // Find required widget name and add it to view container
        switch (widgetName) {
            case WidgetNamesManager.MINI_SPEEDOMETER_VIEW:
                MiniSpeedometerView speedometerView = new MiniSpeedometerView(this);
                // Set current speed to widget
                speedometerView.setSpeed(SpeedManager.getSpeed());
                viewContainer.addView(speedometerView);
                break;
            case WidgetNamesManager.MAX_SPEED_VIEW:
                MaxSpeedView maxSpeedView = new MaxSpeedView(this);
                // Set max speed to widget
                maxSpeedView.setMaxSpeed(SpeedManager.getMaxSpeed());
                viewContainer.addView(maxSpeedView);
                break;
            case WidgetNamesManager.CURRENT_TIME_VIEW:
                CurrentTimeView currentTimeView = new CurrentTimeView(this);
                viewContainer.addView(currentTimeView);
                break;
            default:
                break;
        }
    }

    /**
     * Gesture listener for tracking touches
      */
    private class GestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            if (!widgetsLoaded) return true;
            initAnimation();
            // Replace widgets with new random widgets
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

    /**
     * Repaint all widgets
     */
    private void reloadViews() {
        Object centralWidgetName = centralViewContainer.getTag();
        if (centralWidgetName != null) {
            centralViewContainer.removeAllViews();
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
}