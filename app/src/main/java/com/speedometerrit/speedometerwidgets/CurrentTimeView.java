package com.speedometerrit.speedometerwidgets;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.speedometerrit.R;
import com.speedometerrit.customview.MiniWidget;
import com.speedometerrit.helpers.TimeHelper;

import java.util.Calendar;

public class CurrentTimeView extends MiniWidget  {
    private final Handler handler = new Handler();
    private Runnable runnable;
    private final int delay = 5000; // Check time every 5 sec

    private TimeHelper timeHelper;
    private Calendar currentTime;
    private String time;
    private int amPm;

    public CurrentTimeView(@NonNull Context context) {
        super(context);
        init();
    }

    private void init() {
        super.setTopImage(R.drawable.ic_widgets_name_time);
        this.currentTime = Calendar.getInstance();
        timeHelper = new TimeHelper(this.currentTime);
        setTime();

        // Refresh current time
        handler.postDelayed(runnable = () -> {
            handler.postDelayed(runnable, delay);
            timeHelper.refreshCurrentTime();
            if (!this.time.equals(timeHelper.getTimeString())) {
                updateCurrentTime();
            }
        }, delay);
    }

    public void updateCurrentTime() {
        timeHelper.refreshCurrentTime();
        this.currentTime = timeHelper.getCurrentTime();
        setTime();
    }

    private void setTime() {
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

    @Override
    protected void onDetachedFromWindow() {
        handler.removeCallbacks(runnable); // Stop handler when view removed
        super.onDetachedFromWindow();
    }
}
