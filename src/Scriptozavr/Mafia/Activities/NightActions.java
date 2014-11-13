package Scriptozavr.Mafia.Activities;

import Scriptozavr.Mafia.Models.Player;
import Scriptozavr.Mafia.R;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;

import java.util.HashMap;
import java.util.Map;


public class NightActions extends Activity{
    private int currentCircle;
    private Map<Button,Player> killButtonToPlayerMap = new HashMap<Button, Player>();
    private Map<Button,Integer> pressedButtonToIntegerMap = new HashMap<Button, Integer>();
    private Button choosedKillButton;
    private int choosedButton = -1;
    private boolean keyPressed = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fullnight);

        final Button [] killButtons = {
                (Button)findViewById(R.id.killButton1),
                (Button)findViewById(R.id.killButton2),
                (Button)findViewById(R.id.killButton3),
                (Button)findViewById(R.id.killButton4),
                (Button)findViewById(R.id.killButton5),
                (Button)findViewById(R.id.killButton6),
                (Button)findViewById(R.id.killButton7),
                (Button)findViewById(R.id.killButton8),
                (Button)findViewById(R.id.killButton9),
                (Button)findViewById(R.id.killButton10),
        };
        final Parcelable[] players = getIntent().getParcelableArrayExtra("players");

        for(int i=0; i<players.length; i++) {
            if(!((Player)players[i]).getStatus().equals(getResources().getString(R.string.status_alive))){
                killButtons[i].setClickable(false);
                killButtons[i].setBackgroundColor(Color.GRAY);
            }
            else{
                killButtons[i].setBackgroundColor(Color.GREEN);
            }
            killButtonToPlayerMap.put(killButtons[i],(Player)players[i]);
            pressedButtonToIntegerMap.put(killButtons[i],i);
        }
        currentCircle = getIntent().getIntExtra("currentCircle",0);
        for(int i=0; i<killButtons.length;i++){
            killButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!keyPressed) {
                        choosedKillButton = (Button) findViewById(v.getId());
                        choosedKillButton.setBackgroundColor(Color.RED);
                        choosedButton = pressedButtonToIntegerMap.get(choosedKillButton);
                        for(int i=0;i<killButtons.length;i++){
                            if(i!=choosedButton) {
                                killButtons[i].setClickable(false);
                            }
                        }
                        keyPressed=!keyPressed;
                    }
                    else{
                        choosedKillButton.setBackgroundColor(Color.GREEN);
                        for(int i=0;i<killButtons.length;i++){
                            if(((Player)players[i]).getStatus().equals(getResources().getString(R.string.status_alive))) {
                                killButtons[i].setClickable(true);
                            }
                        }
                        choosedButton = -1;
                        keyPressed=!keyPressed;
                    }
                }
            });
        }
        ((Button)findViewById(R.id.buttonOk)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(choosedButton!=-1){
                    ((Player)players[choosedButton]).setStatus(getResources().getString(R.string.status_killed));
                }
                currentCircle++;
                Intent MorningActions = new Intent(getApplicationContext(), MorningActions.class);
                MorningActions.putExtra("currentCircle",currentCircle);
                MorningActions.putExtra("players",players);
                startActivity(MorningActions);
            }
        });

    }
}
