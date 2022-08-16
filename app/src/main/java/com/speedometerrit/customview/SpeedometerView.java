package com.speedometerrit.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.speedometerrit.R;

public class SpeedometerView extends ConstraintLayout {
    public static final String TAG = SpeedometerView.class.getSimpleName();

    ConstraintLayout layout = null;
    TextView speedTextView = null;
    FrameLayout scaleContainer = null;
    int speed = 0;

    public SpeedometerView(@NonNull Context context) {
        super(context);
        inflateLayout();
    }

    public SpeedometerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray attributes = context.obtainStyledAttributes(attrs,
                R.styleable.SpeedometerView);
        speed = attributes.getInt(R.styleable.SpeedometerView_speed,
                0);

        inflateLayout();

        attributes.recycle();
    }

    private void inflateLayout() {
        String service = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(service);

        layout = (ConstraintLayout)
                li.inflate(R.layout.speedometer_view, this, true);

        speedTextView = layout.findViewById(R.id.speed_text_view);
        scaleContainer = layout.findViewById(R.id.scale_container);

        speedTextView.setText(String.valueOf(speed));
    }

    protected void setSpeed(int speed) {
        if (speed < 0) this.speed = 0;
        speedTextView.setText(String.valueOf(speed));
    }

    protected int getSpeed() {
        return this.speed;
    }

    protected void addScaleView(View scaleView) {
        scaleContainer.addView(scaleView);
    }

    protected void setTextSize(float textSize) {
        speedTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
    }

}
