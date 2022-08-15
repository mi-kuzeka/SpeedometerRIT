package com.speedometerrit.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.speedometerrit.R;

public class CustomView extends ConstraintLayout {
    public static final String TAG = CustomView.class.getSimpleName();
    ConstraintLayout layout = null;
    TextView textView = null;

    public CustomView(Context context) {
        super(context);

        inflateLayout("");
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.CustomView);
        String text = attributes.getString(R.styleable.CustomView_text);

        text = text == null ? "" : text;

        inflateLayout(text);

        attributes.recycle();
    }

    private void inflateLayout(String text) {
        String service = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(service);

        layout = (ConstraintLayout) li.inflate(R.layout.custom_view, this, true);

        textView = (TextView) layout.findViewById(R.id.custom_text_view);

        textView.setText(text);
    }

    protected void setText(String text) {
        try {
            textView.setText(text);
        } catch (Exception e) {
            Log.e(TAG, "Can't set text " + text);
        }
    }

    public String getText() {
        return textView.getText().toString();
    }
}
