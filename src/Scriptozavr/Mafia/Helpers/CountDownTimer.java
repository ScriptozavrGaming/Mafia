package Scriptozavr.Mafia.Helpers;

import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public abstract class CountDownTimer extends android.os.CountDownTimer {

    private TextView timerTextView;

    public CountDownTimer(long millisInFuture, long countDownInterval, TextView timerTextView) {
        super(millisInFuture, countDownInterval);
        this.timerTextView = timerTextView;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        String ms = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));
        timerTextView.setText(ms);
    }
}
