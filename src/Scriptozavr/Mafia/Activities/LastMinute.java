package Scriptozavr.Mafia.Activities;


import Scriptozavr.Mafia.Helpers.ArrayHelper;
import Scriptozavr.Mafia.Models.Player;
import Scriptozavr.Mafia.R;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class LastMinute extends MorningActions {
    private ArrayList<Integer> playersForLastMinute;
    private int index = 0;
    private Parcelable[] players;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.morning);

        playersForLastMinute = getIntent().getIntegerArrayListExtra("lastMin");
        currentCirlce = getIntent().getIntExtra("currentCircle", 0);

        timerTextView = (TextView) findViewById(R.id.ourTimer);

        final TextView[] playerLabels = {
                (TextView) findViewById(R.id.number1),
                (TextView) findViewById(R.id.number2),
                (TextView) findViewById(R.id.number3),
                (TextView) findViewById(R.id.number4),
                (TextView) findViewById(R.id.number5),
                (TextView) findViewById(R.id.number6),
                (TextView) findViewById(R.id.number7),
                (TextView) findViewById(R.id.number8),
                (TextView) findViewById(R.id.number9),
                (TextView) findViewById(R.id.number10)
        };

        playerLabelsForOnFinish = playerLabels;

        final Button[] voteBtns = {
                (Button) findViewById(R.id.vote_btn1),
                (Button) findViewById(R.id.vote_btn2),
                (Button) findViewById(R.id.vote_btn3),
                (Button) findViewById(R.id.vote_btn4),
                (Button) findViewById(R.id.vote_btn5),
                (Button) findViewById(R.id.vote_btn6),
                (Button) findViewById(R.id.vote_btn7),
                (Button) findViewById(R.id.vote_btn8),
                (Button) findViewById(R.id.vote_btn9),
                (Button) findViewById(R.id.vote_btn10),
        };

        final Button[] faultsBtns = {
                (Button) findViewById(R.id.fault_btn1),
                (Button) findViewById(R.id.fault_btn2),
                (Button) findViewById(R.id.fault_btn3),
                (Button) findViewById(R.id.fault_btn4),
                (Button) findViewById(R.id.fault_btn5),
                (Button) findViewById(R.id.fault_btn6),
                (Button) findViewById(R.id.fault_btn7),
                (Button) findViewById(R.id.fault_btn8),
                (Button) findViewById(R.id.fault_btn9),
                (Button) findViewById(R.id.fault_btn10),
        };

        for (Button b : voteBtns) {
            b.setVisibility(View.INVISIBLE);
        }
        timer = new ourCountDownTimer(60000, 100, faultsBtns);
        Circle = (TextView) findViewById(R.id.Circle);
        Circle.setText(getResources().getString(R.string.Circle) + ":" + currentCirlce);
        timerTextView = (TextView) findViewById(R.id.ourTimer);
        ((TextView) findViewById(R.id.OnVote)).setText(ArrayHelper.arrayToString(getResources().getString(R.string.LastMin), playersForLastMinute));
        startTimer(faultsBtns, timer);
        stopTimer(faultsBtns, timer);
        players = getIntent().getParcelableArrayExtra("players");

        currentPlayer = playersForLastMinute.get(index);
        previousPlayer = currentPlayer;
        playerLabelsForOnFinish[currentPlayer].setBackgroundColor(Color.GREEN);

        for (int i = 0; i < players.length; i++) {

            playerLabels[i].setText(players[i].toString());
            faultButtonToPlayerMap.put(faultsBtns[i], (Player) players[i]);
            voteButtonToPlayerMap.put(voteBtns[i], (Player) players[i]);

            final int buttonInd = i;
            onFaultButtonClick(playerLabels[buttonInd], faultsBtns, voteBtns[buttonInd], players[buttonInd], faultsBtns[i], buttonInd);
            if (!((Player) players[i]).getStatus().equals(getResources().getString(R.string.status_alive))) {
                //playerLabels[i].setBackgroundColor(Color.RED);
                faultsBtns[i].setClickable(false);
                voteBtns[i].setClickable(false);
            }
        }
    }

    @Override
    protected void currentPlayersSpeechCalculate(Button[] mButtons) {
        //super.currentPlayersSpeechCalculate(mButtons);
        index++;
        if (index > playersForLastMinute.size() - 1) {
            finishLastMin();
        } else {
            previousPlayer = currentPlayer;
            if (((Player) players[playersForLastMinute.get(index)]).getStatus().equals(getResources().getString(R.string.status_alive))) {
                currentPlayer = playersForLastMinute.get(index);
                playerLabelsForOnFinish[currentPlayer].setBackgroundColor(Color.GREEN);
                playerLabelsForOnFinish[previousPlayer].setBackgroundColor(Color.TRANSPARENT);
            } else {
                while (index < playersForLastMinute.size() && !((Player) players[playersForLastMinute.get(index)]).getStatus().equals(getResources().getString(R.string.status_alive))) {
                    index++;
                }
                if (index < playersForLastMinute.size()) {
                    //previousPlayer = currentPlayer;
                    currentPlayer = playersForLastMinute.get(index);
                } else {
                    finishLastMin();
                }
            }
        }
    }

    private void finishLastMin() {
        boolean ifKilled = getIntent().getBooleanExtra("ifKilled", false);
        if (ifKilled) {
            Intent MorningActions = new Intent(getApplicationContext(), MorningActions.class);
            MorningActions.putExtra("players", players);
            MorningActions.putExtra("currentCircle", currentCirlce);
            startActivity(MorningActions);
        } else {
            Intent NightActions = new Intent(getApplicationContext(), NightActions.class);
            NightActions.putExtra("players", players);
            NightActions.putExtra("currentCircle", currentCirlce);
            startActivity(NightActions);
        }
        finish();
    }
}
