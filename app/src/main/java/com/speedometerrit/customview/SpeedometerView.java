package com.speedometerrit.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.speedometerrit.R;
import com.speedometerrit.helpers.ColorManager;
import com.speedometerrit.helpers.SpeedometerHelper;

public class SpeedometerView extends ConstraintLayout {
    ConstraintLayout layout = null;
    TextView speedTextView = null;
    FrameLayout scaleContainer = null;
    ScaleView scaleView;

    int speed = 0;
    int speedUnits;
    int maxScaleValue;
    int textColor;

    public SpeedometerView(@NonNull Context context) {
        super(context);
        textColor = getResources().getColor(ColorManager.getDefaultTextColor());
        inflateLayout();
    }

    public SpeedometerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray attributes = context.obtainStyledAttributes(attrs,
                R.styleable.SpeedometerView);
        textColor = attributes.getColor(R.styleable.SpeedometerView_textColor,
                ColorManager.getDefaultTextColor());
        speed = attributes.getInt(R.styleable.SpeedometerView_speed,
                0);
        maxScaleValue = attributes.getInt(R.styleable.SpeedometerView_maxScaleValue,
                SpeedometerHelper.getDefaultMaxScaleValue(speedUnits));

        inflateLayout();

        attributes.recycle();
    }

    private void inflateLayout() {
        String service = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(service);

        layout = (ConstraintLayout)
                li.inflate(R.layout.speedometer_view, this, true);

        speedUnits = SpeedometerHelper.getSpeedUnits();

        speedTextView = layout.findViewById(R.id.speed_text_view);
        scaleContainer = layout.findViewById(R.id.scale_container);

        speedTextView.setText(String.valueOf(speed));
        speedTextView.setTextColor(textColor);
    }

    public void setSpeed(int speed) {
        if (speed < 0) this.speed = 0;
        speedTextView.setText(String.valueOf(speed));
    }

    protected int getSpeed() {
        return this.speed;
    }

    protected void addScaleView(ScaleView scaleView) {
        scaleContainer.removeAllViews();
        this.scaleView = scaleView;
        scaleContainer.addView(scaleView);
    }

    protected void setTextSize(float textSize) {
        speedTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
    }
}
