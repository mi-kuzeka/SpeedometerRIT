package com.speedometerrit.speedometerwidgets;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.speedometerrit.R;
import com.speedometerrit.customview.OneLineScaleView;
import com.speedometerrit.customview.SpeedLineView;
import com.speedometerrit.helpers.ColorManager;
import com.speedometerrit.helpers.SpeedManager;

/**
 * Small "one-line" speedometer widget (without needle)
 */
public class MiniSpeedometerView extends ConstraintLayout {
    private TextView speedTextView = null;
    private ImageView speedUnitsImageView = null;
    private SpeedLineView speedView;

    public MiniSpeedometerView(Context context) {
        super(context);
        inflateLayout(context);
    }

    private void inflateLayout(Context context) {
        String service = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(service);

        ConstraintLayout layout = (ConstraintLayout)
                li.inflate(R.layout.mini_speedometer, this, true);

        speedTextView = layout.findViewById(R.id.mini_speed_text_view);
        speedUnitsImageView = layout.findViewById(R.id.speed_units_image_view);

        int textColor = getResources().getColor(ColorManager.getTextColor());
        speedTextView.setTextColor(textColor);
        speedTextView.setText(String.valueOf(SpeedManager.getSpeed()));

        setSpeedUnits(SpeedManager.getSpeedUnits());

        FrameLayout scaleContainer = layout.findViewById(R.id.mini_scale_container);
        // Add fixed scale
        OneLineScaleView scaleView = new OneLineScaleView(context, true);
        scaleContainer.addView(scaleView);
        // Add view that draws speed progress on the scale
        speedView = new SpeedLineView(context, true);
        scaleContainer.addView(speedView);
    }

    public void setSpeed(int speed) {
        if (speed < 0) speed = 0;
        // Draw speed on the scale
        speedView.setSpeed(speed);
        speedTextView.setText(String.valueOf(speed));
    }

    public void setSpeedUnits(int speedUnits) {
        if (speedUnits == SpeedManager.SPEED_UNITS_KMH) {
            setUnitsKMH();
        } else {
            setUnitsMPH();
        }
    }

    private void setUnitsKMH() {
        setSpeedUnitsImage(R.drawable.ic_widgets_desc_kmh);
    }

    private void setUnitsMPH() {
        setSpeedUnitsImage(R.drawable.ic_widgets_desc_mph);
    }

    private void setSpeedUnitsImage(int imageId) {
        speedUnitsImageView.setImageResource(imageId);
    }
}
