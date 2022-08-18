package com.speedometerrit.speedometerwidgets;

import android.content.Context;
import android.support.annotation.NonNull;

import com.speedometerrit.R;
import com.speedometerrit.customview.MiniWidget;
import com.speedometerrit.helpers.TimeHelper;

import java.util.Calendar;

public class CurrentTimeView extends MiniWidget {
    Calendar currentTime;
    String time;
    int amPm;

    public CurrentTimeView(@NonNull Context context) {
        super(context);
        init();
    }

    private void init() {
        super.setTopImage(R.drawable.ic_widgets_name_time);
        this.currentTime = Calendar.getInstance();
        setTime();
    }

    public void setCurrentTime(Calendar currentTime){
        this.currentTime = currentTime;
        setTime();
    }

    public void updateCurrentTime() {
        this.currentTime = Calendar.getInstance();
        setTime();
    }

    private void setTime() {
        TimeHelper timeHelper = new TimeHelper(this.currentTime);
        this.time = timeHelper.getTimeString();
        this.amPm = timeHelper.getAmPm();

        super.setText(this.time);
        setAmPmImage();
    }

    private void setAmPmImage() {
        if (this.amPm == TimeHelper.AM) {
            setAmImage();
        } else {
            setPmImage();
        }
    }

    private void setAmImage() {
        super.setBottomImage(R.drawable.ic_widgets_desc_am);
    }

    private void setPmImage() {
        super.setBottomImage(R.drawable.ic_widgets_desc_pm);
    }
}
