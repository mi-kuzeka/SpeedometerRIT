package com.speedometerrit.helpers;

import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.speedometerrit.customview.SpeedometerView;
import com.speedometerrit.speedometerwidgets.MaxSpeedView;
import com.speedometerrit.speedometerwidgets.MiniSpeedometerView;

public class WidgetsSpeedManager {
    private final String LOG_TAG = WidgetsSpeedManager.class.getSimpleName();

    private final FrameLayout leftContainer;
    private final FrameLayout centralContainer;
    private final FrameLayout rightContainer;

    public WidgetsSpeedManager(FrameLayout leftContainer,
                               FrameLayout centralContainer,
                               FrameLayout rightContainer) {
        this.leftContainer = leftContainer;
        this.centralContainer = centralContainer;
        this.rightContainer = rightContainer;
    }

    public void setSpeed(int newSpeed) {
        if (newSpeed > SpeedometerHelper.getMaxSpeed()) {
            SpeedometerHelper.setMaxSpeed(newSpeed);
            setMaxSpeedToView(leftContainer);
            setMaxSpeedToView(rightContainer);
        }

        SpeedometerHelper.setSpeed(newSpeed);

        setSpeedToMiniSpeedometer(leftContainer);
        setSpeedToBigSpeedometer();
        setSpeedToMiniSpeedometer(rightContainer);
    }

    private void setSpeedToBigSpeedometer() {
        if (centralContainer.getTag() != null) {
            View view = centralContainer.getChildAt(0);
            try {
                SpeedometerView speedometerView = (SpeedometerView) view;
                speedometerView.setSpeed(SpeedometerHelper.getSpeed());
            } catch (ClassCastException e) {
                Log.e(LOG_TAG, "Can't cast View to SpeedometerView");
            }
        }
    }

    private void setSpeedToMiniSpeedometer(FrameLayout viewContainer) {
        if (WidgetNamesManager.isMiniSpeedometer(viewContainer)) {
            View view = viewContainer.getChildAt(0);
            try {
                MiniSpeedometerView speedometerView = (MiniSpeedometerView) view;
                speedometerView.setSpeed(SpeedometerHelper.getSpeed());
            } catch (ClassCastException e) {
                Log.e(LOG_TAG, "Can't cast View to MiniSpeedometerView");
            }
        }
    }

    public void updateMaxSpeed() {
        setMaxSpeedToView(leftContainer);
        setMaxSpeedToView(rightContainer);
    }

    private void setMaxSpeedToView(FrameLayout viewContainer) {
        if (WidgetNamesManager.isMaxSpeedView(viewContainer)) {
            View view = viewContainer.getChildAt(0);
            try {
                MaxSpeedView maxSpeedView = (MaxSpeedView) view;
                maxSpeedView.setMaxSpeed(SpeedometerHelper.getMaxSpeed());
            } catch (ClassCastException e) {
                Log.e(LOG_TAG, "Can't cast View to MaxSpeedView");
            }
        }
    }
}
