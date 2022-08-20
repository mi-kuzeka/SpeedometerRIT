package com.speedometerrit.customview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.speedometerrit.R;
import com.speedometerrit.helpers.ColorManager;

public class MiniWidget extends ConstraintLayout {
    private ConstraintLayout layout = null;
    private TextView textView = null;
    private ImageView topImageView = null;
    private ImageView bottomImageView = null;
    private int textColor;
    private String text = "";

    public MiniWidget(@NonNull Context context) {
        super(context);
        inflateLayout();
    }

    private void inflateLayout() {
        String service = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(service);

        layout = (ConstraintLayout)
                li.inflate(R.layout.mini_widget, this, true);

        topImageView = layout.findViewById(R.id.top_image);
        textView = layout.findViewById(R.id.text_view);
        bottomImageView = layout.findViewById(R.id.bottom_image);

        textColor = getResources().getColor(ColorManager.getDefaultTextColor());
        textView.setTextColor(textColor);
    }

    protected void setText(String text) {
        this.text = text;
        textView.setText(text);
    }

    protected String getText() {
        return this.text;
    }

    protected void setTopImage(int imageId) {
        topImageView.setImageResource(imageId);
    }

    protected void setBottomImage(int imageId) {
        bottomImageView.setImageResource(imageId);
    }
}
