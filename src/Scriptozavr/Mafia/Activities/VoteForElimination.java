package Scriptozavr.Mafia.Activities;

import Scriptozavr.Mafia.Helpers.ArrayHelper;
import Scriptozavr.Mafia.Models.Player;
import Scriptozavr.Mafia.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class VoteForElimination extends Activity {
    //status of all players
    protected Parcelable[] players;
    //people who were pushed to vote
    protected ArrayList<Integer> votingList;
    protected TextView votedPlayerTextView;
    protected NumberPicker numberPicker;
    protected TextView[] votesTextView;
    protected int[] votesCount;
    protected int currentVoteIndex = 0;
    protected int alivePlayers;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voting);

        players = getIntent().getParcelableArrayExtra("players");
        votingList = getIntent().getIntegerArrayListExtra("votingList");
        votedPlayerTextView = (TextView) findViewById(R.id.textView);
        numberPicker = (NumberPicker) findViewById(R.id.numberPicker);
        Button okButton = (Button) findViewById(R.id.button);
        votesTextView = new TextView[votingList.size()];
        votesCount = new int[votingList.size()];
        alivePlayers = getAlivePlayersCount(players);

        final LinearLayout playersLabelsList = (LinearLayout) findViewById(R.id.votedPlayersList);
        for (int i = 0; i < votesTextView.length; i++) {
            votesTextView[i] = new TextView(this);
            votesTextView[i].setText(String.format("%s%d:", getResources().getText(R.string.number), votingList.get(i) ));
            votesTextView[i].setTextSize(30);
            playersLabelsList.addView(votesTextView[i]);
        }

        okButton.setOnClickListener(new View.OnClickListener() {
            private int currentVotes = 0;

            @Override
            public void onClick(View v) {
                votesCount[currentVoteIndex] = numberPicker.getValue();
                votesTextView[currentVoteIndex].setText((String) votesTextView[currentVoteIndex].getText() +
                        votesCount[currentVoteIndex]);
                currentVoteIndex++;
                currentVotes += numberPicker.getValue();
                if (currentVoteIndex < votesCount.length - 1 && currentVotes < alivePlayers &&
                        currentVotes <= alivePlayers / 2) {
                    updateNumberPicker(currentVotes);
                    updateVotingInfoLabel();
                } else {
                    votesCount[votesCount.length - 1] = alivePlayers - currentVotes;
                    votesTextView[votesCount.length - 1].setText((String) votesTextView[votesCount.length - 1].getText() +
                            votesCount[votesCount.length - 1]);
                    int maxVotesIndex = ArrayHelper.getMaxIndex(votesCount);
                    int countMaxElements = ArrayHelper.countElements(votesCount[maxVotesIndex], votesCount);

                    if (countMaxElements > 1) {
                        ArrayList<Integer> duelists = new ArrayList<Integer>();
                        int maxVotes = votesCount[maxVotesIndex];
                        for (int i = 0; i < votesCount.length; i++) {
                            if (votesCount[i] == maxVotes) {
                                duelists.add(votingList.get(i));
                            }
                        }
                        voteDuel(duelists);
                    } else {
                        voteSuccessful(maxVotesIndex);
                    }
                }
            }
        });
        updateVotingInfoLabel();
        updateNumberPicker(0);
    }

    protected void voteDuel(ArrayList<Integer> duelists) {
        votedPlayerTextView.setText("DUEL, players are: " + Arrays.toString(duelists.toArray()));
        //если <6 игроков, нельзя выгонять больше одного
        //
        Intent i = new Intent(getApplicationContext(), DuelistsSpeech.class);
        i.putIntegerArrayListExtra("duelists", duelists);
        i.putExtra("players", players);
        int currentCircle = getIntent().getIntExtra("currentCircle", 0);
        i.putExtra("currentCircle", currentCircle);
        startActivity(i);
    }

    protected void voteSuccessful(int maxVotesIndex) {
        votedPlayerTextView.setText("ELIMINATED: " + (votingList.get(maxVotesIndex) + 1));
        ((Player) players[votingList.get(maxVotesIndex + 1)]).setStatus(getResources().getString(R.string.status_banished));
        Intent i = new Intent(getApplicationContext(), NightActions.class);
        i.putExtra("players", players);
        int currentCircle = getIntent().getIntExtra("currentCircle", 0);
        i.putExtra("currentCircle", currentCircle);
        startActivity(i);
    }

    protected void updateVotingInfoLabel() {
        votedPlayerTextView.setText(String.format("%s %s", getResources().getString(R.string.voting_for_lbl), votingList.get(currentVoteIndex)));
    }

    protected void updateNumberPicker(int curVotes) {
        numberPicker.setMaxValue(alivePlayers - curVotes);
    }

    protected int getAlivePlayersCount(Parcelable[] arr) {
        int alivePlayers = 0;
        for (Parcelable p : arr) {
            if (((Player) p).getStatus().equals(getResources().getString(R.string.status_alive))) alivePlayers++;
        }
        return alivePlayers;
    }
}
