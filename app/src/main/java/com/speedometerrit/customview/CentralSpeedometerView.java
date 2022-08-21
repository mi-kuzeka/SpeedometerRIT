package com.speedometerrit.customview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.speedometerrit.R;
import com.speedometerrit.helpers.ColorManager;

/**
 * Parent class for central widgets (speedometers)
 */
public class CentralSpeedometerView extends ConstraintLayout {
    // TextView that displays current speed
    private TextView speedTextView = null;
    // Container for scale (without speed progress)
    private FrameLayout scaleContainer = null;
    // View that draws current speed on the scale
    private SpeedView speedView;

    private final int textColor;

    public CentralSpeedometerView(@NonNull Context context) {
        super(context);
        textColor = getResources().getColor(ColorManager.getTextColor());
        inflateLayout();
    }

    private void inflateLayout() {
        String service = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(service);

        ConstraintLayout layout = (ConstraintLayout)
                li.inflate(R.layout.speedometer, this, true);

        speedTextView = layout.findViewById(R.id.speed_text_view);
        scaleContainer = layout.findViewById(R.id.scale_container);

        // Set default speed
        speedTextView.setText(String.valueOf(0));
        speedTextView.setTextColor(textColor);
    }

    public void setSpeed(int speed) {
        // Speed can't be negative
        if (speed < 0) speed = 0;
        speedTextView.setText(String.valueOf(speed));
        // Set speed for drawing it on the scale
        if (this.speedView != null) this.speedView.setSpeed(speed);
    }

    protected void addScaleView(View scaleView) {
        scaleContainer.removeAllViews();
        scaleContainer.addView(scaleView);
    }

    protected void addSpeedProgressView(SpeedView speedProgressView) {
        this.speedView = speedProgressView;
        scaleContainer.addView(this.speedView);
    }

    protected void setTextSize(float textSize) {
        speedTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
    }
}
