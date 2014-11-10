package Scriptozavr.Mafia.Activities;

import Scriptozavr.Mafia.Models.Player;
import Scriptozavr.Mafia.R;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Parcelable;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import java.util.*;
import java.util.concurrent.TimeUnit;


public class MorningActions extends Activity {
    private Map<Button,Player> faultButtonToPlayerMap = new HashMap<Button, Player>();
    private Map<Button,Player> voteButtonToPlayerMap = new HashMap<Button, Player>();
    private ArrayList<Integer> playersOnVote = new ArrayList<Integer>();
    private TextView timerTextView;
    private TextView Circle;
    private int currentCirlce = 0;
    private int currentPlayer = 0;
    private int previousPlayer = 0;
    private int cnt=0;
    long currentTime = 0;
    private ourCountDownTimer timer;
    private boolean continuing = false;
    private TextView[] playerLabelsForOnFinish = new TextView[10];
    private Parcelable[] p = new Parcelable[10];


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.morning);

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


        playerLabelsForOnFinish = playerLabels;//cycleShiftLeft(playerLabels,currentCirlce);

        timer = new ourCountDownTimer(60000,100,faultsBtns);
        Circle = (TextView) findViewById(R.id.Circle);
        Circle.setText(getResources().getString(R.string.Circle) + ":" + currentCirlce );
        ((TextView)findViewById(R.id.OnVote)).setText(getResources().getString(R.string.Nobody_on_vote));
        timerTextView = (TextView)findViewById(R.id.ourTimer);

        //playerLabels[currentPlayer].getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC);
        playerLabelsForOnFinish[currentPlayer].setBackgroundColor(Color.GREEN);
        startTimer(faultsBtns);
        stopTimer(faultsBtns);


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
        final Parcelable[] players = getIntent().getParcelableArrayExtra("players");
        for(int i =0 ;i<players.length;i++) {
            p[i]=players[i];
        }
        ((Player)p[0]).setStatus(getResources().getString(R.string.status_killed));
        playerLabels[0].setText(p[0].toString());
        //final Player[] players = ReadFile(FinalFilename);
        for (int i = 0; i < players.length; i++) {

            playerLabels[i].setText(players[i].toString());
            faultButtonToPlayerMap.put(faultsBtns[i], (Player) players[i]);
            voteButtonToPlayerMap.put(voteBtns[i], (Player) players[i]);

            final int buttonInd = i;
            voteBtns[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    voteBtns[buttonInd].setClickable(false);
                    playersOnVote.add(buttonInd + 1);
                    ((TextView) findViewById(R.id.OnVote)).setText(getResources().getString(R.string.on_vote_label)
                            + Arrays.toString(playersOnVote.toArray()));
                }
            });
            faultsBtns[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Player p = (Player)players[buttonInd];
                    p.setFaults(p.getFaults() + 1);
                    if (p.getFaults() > 3) {
                        faultsBtns[buttonInd].setClickable(false);
                        voteBtns[buttonInd].setClickable(false);
                        p.setStatus(getResources().getString(R.string.status_banished));
                        //playerLabels[p.getPosition()-1].setBackgroundColor(Color.RED);
                    }
                    playerLabels[buttonInd].setText(p.toString());
                }
            });
            if(!((Player)players[i]).getStatus().equals(getResources().getString(R.string.status_alive))){
                //playerLabels[i].setBackgroundColor(Color.RED);
                faultsBtns[i].setClickable(false);
                voteBtns[i].setClickable(false);
            }
        }
    }

    private void stopTimer(final Button[] buttons) {
        ((Button) findViewById(R.id.stopTimer_Btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                timer = new ourCountDownTimer(60000,100,buttons);
                timerTextView.setText(getResources().getString(R.string.StartTime));
                continuing = false;
                timer.onFinish();
            }
        });
    }

    private void startTimer(final Button[] buttons) {
        ((Button) findViewById(R.id.startTimer_Btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!continuing) {
                    timer.start();
                }
                else{
                    timer.cancel();
                    String [] time = timerTextView.getText().toString().split(":");
                    currentTime=Long.parseLong(time[1]);
                    timer = new ourCountDownTimer(currentTime*1000,100,buttons);
                }
                continuing = !continuing;
            }
        });
    }

    private class ourCountDownTimer extends CountDownTimer{

        private final long mMillisInFuture;
        private final long mCountdownInterval;
        private final Button[] mButtons;

        public ourCountDownTimer(long millisInFuture, long countDownInterval, Button[] buttons) {
            super(millisInFuture,countDownInterval);
            mMillisInFuture = millisInFuture;
            mCountdownInterval = countDownInterval;
            mButtons = buttons;
        }



        @Override
        public void onTick(long millisUntilFinished) {
            String ms = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));
            timerTextView.setText(ms);
        }

        @Override
        public void onFinish() {
            currentPlayer++;
            if(currentPlayer<10) {
                if(((Player)p[currentPlayer]).getStatus().equals(getResources().getString(R.string.status_alive))) {
                    playerLabelsForOnFinish[currentPlayer].setBackgroundColor(Color.GREEN);
                    playerLabelsForOnFinish[previousPlayer].setBackgroundColor(Color.TRANSPARENT);
                    previousPlayer = currentPlayer;
                }
                else{
                    while(!((Player)p[currentPlayer]).getStatus().equals(getResources().getString(R.string.status_alive))) {
                        currentPlayer++;
                    }
                    playerLabelsForOnFinish[currentPlayer].setBackgroundColor(Color.GREEN);
                    playerLabelsForOnFinish[previousPlayer].setBackgroundColor(Color.TRANSPARENT);
                    previousPlayer = currentPlayer;
                }
            }
            else{
                if(playersOnVote.size()>1) {
                    Intent VotingForElimination = new Intent(getApplicationContext(), VoteForElimination.class);
                    VotingForElimination.putIntegerArrayListExtra("votingList", playersOnVote);
                    VotingForElimination.putExtra("players", p);
                    startActivity(VotingForElimination);
                }
                else if(playersOnVote.size()==1){
                    ((Player)p[playersOnVote.get(0)-1]).setStatus(getResources().getString(R.string.status_banished));
                    playerLabelsForOnFinish[playersOnVote.get(0)-1].setText(p[playersOnVote.get(0)-1].toString());
                    //playerLabelsForOnFinish[playersOnVote.get(0)-1].setBackgroundColor(Color.RED);
                    mButtons[playersOnVote.get(0)-1].setClickable(false);
                }
                Intent NightActions = new Intent(getApplicationContext(), Scriptozavr.Mafia.Activities.NightActions.class);
                NightActions.putExtra("players",p);

                startActivity(NightActions);
            }
        }
    }



}
