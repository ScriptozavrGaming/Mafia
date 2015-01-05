package Scriptozavr.Mafia.Activities;

import Scriptozavr.Mafia.Helpers.*;
import Scriptozavr.Mafia.Helpers.CountDownTimer;
import Scriptozavr.Mafia.Models.Player;
import Scriptozavr.Mafia.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.*;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.*;


public class MorningActions extends Activity {
    protected Map<Button, Player> faultButtonToPlayerMap = new HashMap<Button, Player>();
    protected Map<Button, Player> voteButtonToPlayerMap = new HashMap<Button, Player>();
    protected ArrayList<Integer> playersOnVote = new ArrayList<Integer>();
    protected ArrayList<Integer> playersForLastMinute = new ArrayList<Integer>();
    protected TextView timerTextView;
    protected TextView Circle;
    protected int currentCircle = 0;
    protected int currentPlayer = 0;
    private int firstAlivePlayer = 0;
    private int lastAlivePlayer = 0;
    private int currPlayerToNull = 0;
    protected int previousPlayer = 0;
    private long currentTime = 0;
    //protected ourCountDownTimer timer;
    protected CountDownTimer timer;
    protected boolean continuing = false;
    protected TextView[] playerLabelsForOnFinish = new TextView[10];
    private Parcelable[] players;

    protected class ourCountDownTimer extends Scriptozavr.Mafia.Helpers.CountDownTimer {

        private final Button[] mButtons;

        public ourCountDownTimer(long millisInFuture, long countDownInterval, Button[] buttons) {
            super(millisInFuture, countDownInterval, timerTextView);
            mButtons = buttons;
        }

        @Override
        public void onFinish() {
            currentPlayersSpeechCalculate(mButtons);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.morning);

        currentCircle = getIntent().getIntExtra("currentCircle", 0);
        if(currentCircle == 10) {
            currentCircle = 0;
        }

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


        playerLabelsForOnFinish = playerLabels;


        Circle = (TextView) findViewById(R.id.Circle);
        Circle.setText(String.format("%s:%d", getResources().getString(R.string.Circle), currentCircle));
        ((TextView) findViewById(R.id.OnVote)).setText(getResources().getString(R.string.Nobody_on_vote));
        timerTextView = (TextView) findViewById(R.id.ourTimer);
        timer = new ourCountDownTimer(60000, 100, faultsBtns);

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

        players = getIntent().getParcelableArrayExtra("players");

        fullCheckForWin();
        //
        try {
            firstAlivePlayer = FirstAlivePlayer();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            currPlayerToNull = FirstAliveFromTheEnd();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            lastAlivePlayer = LastAlivePlayer();
        } catch (Exception e) {
            e.printStackTrace();
        }
        currentPlayer = firstAlivePlayer;
        previousPlayer = firstAlivePlayer;
        playerLabelsForOnFinish[currentPlayer].setBackgroundColor(Color.GREEN);

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
                    playersOnVote.add(buttonInd);
                    ((TextView) findViewById(R.id.OnVote)).setText(ArrayHelper.arrayToString(getResources().getString(R.string.on_vote_label), playersOnVote));
                }
            });
            onFaultButtonClick(playerLabels[buttonInd], faultsBtns, voteBtns[buttonInd], players[buttonInd], faultsBtns[i], buttonInd);
            if (!((Player) players[i]).getStatus().equals(getResources().getString(R.string.status_alive))) {
                //playerLabels[i].setBackgroundColor(Color.RED);
                faultsBtns[i].setClickable(false);
                voteBtns[i].setClickable(false);
            }
        }
    }

    protected void onFaultButtonClick(final TextView playerLabel, final Button[] faultsBtns, final Button voteBtn, final Parcelable player, Button faultsBtn, final int buttonInd) {
        faultsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Player p = (Player) player;
                p.setFaults(p.getFaults() + 1);
                if (p.getFaults() > 3) {
                    faultsBtns[buttonInd].setClickable(false);
                    voteBtn.setClickable(false);
                    p.setStatus(getResources().getString(R.string.status_banished));
                    fullCheckForWin();
                }
                playerLabel.setText(p.toString());
            }
        });
    }




    protected void currentPlayersSpeechCalculate(Button[] mButtons) {
        currentPlayer++;
        try {
            lastAlivePlayer = LastAlivePlayer();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (currentPlayer < 10 && currentPlayer != lastAlivePlayer + 1) {
            if (((Player) players[currentPlayer]).getStatus().equals(getResources().getString(R.string.status_alive))) {
                playerLabelsForOnFinish[currentPlayer].setBackgroundColor(Color.GREEN);
                playerLabelsForOnFinish[previousPlayer].setBackgroundColor(Color.TRANSPARENT);
                previousPlayer = currentPlayer;
            } else {
                if (currentPlayer == currPlayerToNull + 1) {
                    currentPlayer = 0;
                }
                while (!((Player) players[currentPlayer]).getStatus().equals(getResources().getString(R.string.status_alive))) {
                    currentPlayer++;
                }
                playerLabelsForOnFinish[currentPlayer].setBackgroundColor(Color.GREEN);
                playerLabelsForOnFinish[previousPlayer].setBackgroundColor(Color.TRANSPARENT);
                previousPlayer = currentPlayer;
            }
        } else if (currentPlayer == 10 && lastAlivePlayer != 9) {
            previousPlayer = currentPlayer - 1;
            currentPlayer = 0;
            firstAlivePlayerFromPosition(currentPlayer, lastAlivePlayer);
            playerLabelsForOnFinish[currentPlayer].setBackgroundColor(Color.GREEN);
            playerLabelsForOnFinish[previousPlayer].setBackgroundColor(Color.TRANSPARENT);
            previousPlayer = currentPlayer;

        } else if (currentPlayer == lastAlivePlayer + 1) {
            if (playersOnVote.size() > 1) {
                Intent VotingForElimination = new Intent(getApplicationContext(), VoteForElimination.class);
                VotingForElimination.putIntegerArrayListExtra("votingList", playersOnVote);
                VotingForElimination.putExtra("players", players);
                VotingForElimination.putExtra("currentCircle", currentCircle);
                startActivity(VotingForElimination);
                finish();
            } else if (playersOnVote.size() == 1) {
                ((Player) players[playersOnVote.get(0)]).setStatus(getResources().getString(R.string.status_banished));
                playerLabelsForOnFinish[playersOnVote.get(0)].setText(players[playersOnVote.get(0)].toString());
                fullCheckForWin();
                //playerLabelsForOnFinish[playersOnVote.get(0)-1].setBackgroundColor(Color.RED);
                mButtons[playersOnVote.get(0)].setClickable(false);
                playersForLastMinute.add(playersOnVote.get(0));
                boolean ifKilled = false;
                Intent LastMinute = new Intent(getApplicationContext(), Scriptozavr.Mafia.Activities.LastMinute.class);
                LastMinute.putExtra("ifKilled", ifKilled);
                LastMinute.putExtra("players", players);
                LastMinute.putExtra("currentCircle", currentCircle);
                LastMinute.putIntegerArrayListExtra("lastMin", playersForLastMinute);
                startActivity(LastMinute);
                //NightActions.putExtra("players", players);
                //NightActions.putExtra("currentCircle", currentCircle);
                //startActivity(NightActions);
                finish();
            } else {
                Intent NightActions = new Intent(getApplicationContext(), Scriptozavr.Mafia.Activities.NightActions.class);
                NightActions.putExtra("players", players);
                NightActions.putExtra("currentCircle", currentCircle);
                startActivity(NightActions);
                finish();
            }
        }
    }

    protected void stopTimer(final Button[] buttons) {
        findViewById(R.id.stopTimer_Btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                timer = new ourCountDownTimer(60000, 100, buttons);
                timerTextView.setText(getResources().getString(R.string.StartTime));
                continuing = false;
                timer.onFinish();
            }
        });
    }

    protected void startTimer(final Button[] buttons) {
        findViewById(R.id.startTimer_Btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!continuing) {
                    timer.start();
                } else {
                    timer.cancel();
                    String[] time = timerTextView.getText().toString().split(":");
                    currentTime = Long.parseLong(time[1]);
                    timer = new ourCountDownTimer(currentTime * 1000, 100, buttons);
                }
                continuing = !continuing;
            }
        });
    }

    private void firstAlivePlayerFromPosition(int pos, int lastAlivePlayer) {
        currentPlayer = pos;
        while (!((Player) players[currentPlayer]).getStatus().equals(getResources().getString(R.string.status_alive)) && currentPlayer < lastAlivePlayer) {
            currentPlayer++;
        }
    }

    private int FirstAlivePlayer() throws Exception {
        int currPlayer = currentCircle;
        for (int i = currPlayer; i < players.length; i++) {
            if (((Player) players[i]).getStatus().equals(getResources().getString(R.string.status_alive))) {
                return i;
            }
        }
        for (int i = 0; i < currPlayer; i++) {
            if (((Player) players[i]).getStatus().equals(getResources().getString(R.string.status_alive))) {
                return i;
            }
        }
        throw new Exception("No Players Alive");
    }

    private int LastAlivePlayer() throws Exception {

        int currPlayer = currentCircle;

        for (int i = currPlayer; i < players.length; i++) {
            if (((Player) players[i]).getStatus().equals(getResources().getString(R.string.status_alive))) {
                currPlayer = i;
            }
        }
        for (int i = 0; i < currentCircle; i++) {
            if (((Player) players[i]).getStatus().equals(getResources().getString(R.string.status_alive))) {
                currPlayer = i;
            }
        }
        return currPlayer;

//        for (int i = currPlayer; i >= 0; i--) {
//            if (((Player) players[i]).getStatus().equals(getResources().getString(R.string.status_alive))) {
//                return i;
//            }
//        }
//        for (int i = players.length; i >= currPlayer; i--) {
//            if (((Player) players[i]).getStatus().equals(getResources().getString(R.string.status_alive))) {
//                return i;
//            }
//        }
//        throw new Exception("No Players Alive");
    }

    // Check, when we need currentPlayer = 0;
    private int FirstAliveFromTheEnd() throws Exception {
        for (int i = players.length - 1; i >= 0; i--) {
            if (((Player) players[i]).getStatus().equals(getResources().getString(R.string.status_alive))) {
                return i;
            }
        }
        throw new Exception("Error in count of players");
    }
    private enum winType{gameContinues, peasantsWin, mafiaWin};
    private winType CheckForWin() {
        int mafiaCount = 0, peasantsCount = 0;
        for (Parcelable player : players) {
            if ((((Player) player).getRole().equals(getResources().getString(R.string.mafia)) ||
                    ((Player) player).getRole().equals(getResources().getString(R.string.don))) &&
                    ((Player) player).getStatus().equals(getResources().getString(R.string.status_alive))) {
                mafiaCount++;
            } else if ((((Player) player).getRole().equals(getResources().getString(R.string.peasant)) ||
                    ((Player) player).getRole().equals(getResources().getString(R.string.comissar))) &&
                    ((Player) player).getStatus().equals(getResources().getString(R.string.status_alive))) {
                peasantsCount++;
            }
        }
        if (mafiaCount == peasantsCount) {
            return winType.mafiaWin;
        }
        if (mafiaCount == 0) {
            return winType.peasantsWin;
        }
        return winType.gameContinues;
    }

    private void fullCheckForWin() {
        winType checkForWin = CheckForWin();
        if (checkForWin != winType.gameContinues) {
            if (checkForWin == winType.mafiaWin) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MorningActions.this);
                builder.setTitle("В городе:")
                        .setMessage("Победа мафии!")
                        //.setIcon(R.drawable.ic_android_cat)
                        .setCancelable(false)
                        .setNegativeButton("Закончить игру",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                        finish();
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(MorningActions.this);
                builder.setTitle("В городе:")
                        .setMessage("Победа города!")
                                //.setIcon(R.drawable.ic_android_cat)
                        .setCancelable(false)
                        .setNegativeButton("Закончить игру",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                        finish();
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();
            }

        }
    }
}
