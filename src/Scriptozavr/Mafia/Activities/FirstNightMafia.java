package Scriptozavr.Mafia.Activities;

import Scriptozavr.Mafia.Helpers.CountDownTimer;
import Scriptozavr.Mafia.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class FirstNightMafia extends Activity{
    private Parcelable[] players;
    protected boolean pressed = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_night_timer);
        players = getIntent().getParcelableArrayExtra("players");
        TextView timerTextView = (TextView) findViewById(R.id.timerDisplay);

        final CountDownTimer timer = new CountDownTimer(60000, 100, timerTextView) {

            @Override
            public void onFinish() {
                Intent main = new Intent(getApplicationContext(), MorningActions.class);
                int currentCircle = 0;
                main.putExtra("players", players);
                main.putExtra("currentCircle", currentCircle);
                startActivity(main);
                finish();
            }
        };

        Button startStopBtn = (Button)findViewById(R.id.startTimer_Btn);
        startStopBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!pressed) {
                    timer.start();
                    pressed = true;
                }else{
                    timer.cancel();
                    timer.onFinish();
                }
            }
        });
    }
}
