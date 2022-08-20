package com.speedometerrit.speedometerwidgets;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.speedometerrit.R;
import com.speedometerrit.customview.OneLineScaleView;
import com.speedometerrit.customview.SpeedProgressView;
import com.speedometerrit.customview.SpeedView;
import com.speedometerrit.helpers.ColorManager;
import com.speedometerrit.helpers.SpeedometerHelper;

public class MiniSpeedometerView extends ConstraintLayout {
    private TextView speedTextView = null;
    private ImageView amPmImageView = null;
    private SpeedView speedView;

    private int speed = 0;

    public MiniSpeedometerView(Context context) {
        super(context);
        inflateLayout(context);
    }

    private void inflateLayout(Context context) {
        String service = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(service);

        ConstraintLayout layout = (ConstraintLayout)
                li.inflate(R.layout.mini_speedometer, this, true);

        FrameLayout scaleContainer = layout.findViewById(R.id.mini_scale_container);
        speedTextView = layout.findViewById(R.id.mini_speed_text_view);
        amPmImageView = layout.findViewById(R.id.am_pm_image_view);

        int textColor = getResources().getColor(ColorManager.getDefaultTextColor());
        speedTextView.setTextColor(textColor);
        speedTextView.setText(String.valueOf(speed));

        setSpeedUnits(SpeedometerHelper.getSpeedUnits());

        OneLineScaleView scaleView = new OneLineScaleView(context, true);
        scaleContainer.addView(scaleView);
        speedView = new SpeedProgressView(context, true);
        scaleContainer.addView(speedView);
    }

    public void setSpeed(int speed) {
        this.speed = Math.max(speed, 0);
        speedTextView.setText(String.valueOf(this.speed));
        speedView.setSpeed(speed);
    }

    public void setSpeedUnits(int speedUnits) {
        if (speedUnits == SpeedometerHelper.SPEED_UNITS_KMH) {
            setUnitsKMH();
        } else {
            setUnitsMPH();
        }
    }

    private void setUnitsKMH() {
        setAmPmImage(R.drawable.ic_widgets_desc_kmh);
    }

    private void setUnitsMPH() {
        setAmPmImage(R.drawable.ic_widgets_desc_mph);
    }

    private void setAmPmImage(int imageId) {
        amPmImageView.setImageResource(imageId);
    }
}
