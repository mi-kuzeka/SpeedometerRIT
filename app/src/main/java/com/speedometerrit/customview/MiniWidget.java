package com.speedometerrit.customview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.speedometerrit.R;
import com.speedometerrit.helpers.ColorManager;

/**
 * Parent class for mini widgets
 */
public class MiniWidget extends ConstraintLayout {
    private TextView textView = null;
    private ImageView topImageView = null;
    private ImageView bottomImageView = null;

    public MiniWidget(@NonNull Context context) {
        super(context);
        inflateLayout();
    }

    private void inflateLayout() {
        String service = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(service);

        ConstraintLayout layout = (ConstraintLayout)
                li.inflate(R.layout.mini_widget, this, true);

        topImageView = layout.findViewById(R.id.top_image);
        textView = layout.findViewById(R.id.text_view);
        bottomImageView = layout.findViewById(R.id.bottom_image);

        int textColor = getResources().getColor(ColorManager.getTextColor());
        textView.setTextColor(textColor);
    }

    protected void setText(String text) {
        textView.setText(text);
    }

    protected void setTopImage(int imageId) {
        topImageView.setImageResource(imageId);
    }

    protected void setBottomImage(int imageId) {
        bottomImageView.setImageResource(imageId);
    }
}
