package com.vish.timetextview;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

@SuppressLint("AppCompatCustomView")
public class TimeTextView extends TextView {

    private static final int DEFAULT_INTERVAL = 1000;
    private static final String DEFAULT_TIME_FORMAT = "hh:mm aaa";
    private Timer timer = new Timer();
    private long interval = DEFAULT_INTERVAL;
    private boolean isCanceled = false;
    private String date_format=DEFAULT_TIME_FORMAT;

    public TimeTextView(Context context) {
        super(context);
    }

    public TimeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TimeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TimeTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopTimer();
    }

    @Override protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (VISIBLE == visibility) {
            startTimer();
        } else {
            stopTimer();
        }
    }

    public void setInterval(long interval) {
        if (interval >= 0) {
            this.interval = interval;
            stopTimer();
            startTimer();
        }
    }

    private void startTimer() {
        if (isCanceled) {
            timer = new Timer();
            isCanceled = false;
        }
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override public void run() {
                if (null == getHandler()) {
                    return;
                }
                getHandler().post(new Runnable() {
                    @Override public void run() {
                        setText(getCurrentDateTime());
                    }
                });
            }
        }, 0, interval);
    }

    private void stopTimer() {
        timer.cancel();
        isCanceled = true;
    }

    private String getCurrentDateTime() {
        DateFormat df = new SimpleDateFormat(date_format);
        return df.format(Calendar.getInstance().getTime());
    }
}
