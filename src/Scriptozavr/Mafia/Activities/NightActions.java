package Scriptozavr.Mafia.Activities;

import Scriptozavr.Mafia.R;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;



public class NightActions extends Activity{
    private int currentCircle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.night);

        final Parcelable[] players = getIntent().getParcelableArrayExtra("players");
        currentCircle = getIntent().getIntExtra("currentCircle",0);
        findViewById(R.id.continueToMorning).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCircle++;
                Intent MorningActions = new Intent(getApplicationContext(), MorningActions.class);
                MorningActions.putExtra("currentCircle",currentCircle);
                MorningActions.putExtra("players",players);
                startActivity(MorningActions);
            }
        });
    }
}
