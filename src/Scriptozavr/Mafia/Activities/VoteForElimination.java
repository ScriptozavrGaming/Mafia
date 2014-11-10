package Scriptozavr.Mafia.Activities;

import Scriptozavr.Mafia.Helpers.ArrayHelper;
import Scriptozavr.Mafia.Models.Player;
import Scriptozavr.Mafia.R;
import android.app.Activity;
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
    private Parcelable[] players;
    //people who were pushed to vote
    private ArrayList<Integer> votingList;
    private TextView votedPlayerTextView;
    private NumberPicker numberPicker;
    private TextView[] votesTextView;
    private int[] votesCount;
    private int currentVoteIndex = 0;
    private int alivePlayers;
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
        for (int i : votingList) {
            i--;
        }

        final LinearLayout playersLabelsList = (LinearLayout) findViewById(R.id.votedPlayersList);
        for (int i = 0; i < votesTextView.length; i++) {
            votesTextView[i] = new TextView(this);
            votesTextView[i].setId((int) Math.random());
            votesTextView[i].setText(String.format("%s%d:", getResources().getText(R.string.number), votingList.get(i) + 1));
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
                if (currentVoteIndex < votesCount.length - 1 && currentVotes < alivePlayers) {
                    updateNumberPicker(currentVotes);
                    updateVotingInfoLabel();
                } else {
                    votesCount[votesCount.length - 1] = alivePlayers - currentVotes;
                    votesTextView[votesCount.length - 1].setText((String) votesTextView[votesCount.length - 1].getText() +
                            votesCount[votesCount.length - 1]);
                    int maxVotesIndex = ArrayHelper.getMaxIndex(votesCount);
                    int countMaxElements = ArrayHelper.countElements(votesCount[maxVotesIndex], votesCount);

                    if (countMaxElements > 1) {
                        ArrayList<Integer> duelants = new ArrayList<Integer>();
                        int maxVotes = votesCount[maxVotesIndex];
                        for (int i = 0; i < votesCount.length; i++) {
                            if (votesCount[i] == maxVotes) {
                                duelants.add(votingList.get(i));
                            }
                        }
                        votedPlayerTextView.setText("DUEL, players are: " + Arrays.toString(duelants.toArray()));
                    } else {
                        votedPlayerTextView.setText("ELIMINATED: " + (votingList.get(maxVotesIndex) + 1));
                    }
                }
            }
        });
        updateVotingInfoLabel();
        updateNumberPicker(0);
    }

    private void updateVotingInfoLabel() {
        votedPlayerTextView.setText(String.format("%s %s", getResources().getString(R.string.voting_for_lbl), votingList.get(currentVoteIndex)));
    }

    private void updateNumberPicker(int curVotes) {
        numberPicker.setMaxValue(alivePlayers - curVotes);
    }

    private int getAlivePlayersCount(Parcelable[] arr) {
        int alivePlayers = 0;
        for (Parcelable p : arr) {
            if (((Player) p).getStatus().equals(getResources().getString(R.string.status_alive))) alivePlayers++;
        }
        return alivePlayers;
    }
/*
    private View.OnClickListener getOkButtonListener(final Button[] voteButtons) {
        return new View.OnClickListener() {
            TextView label = (TextView)findViewById(R.id.votingForLabel);
            @Override
            public void onClick(View v) {
                countVotes();
                //do this until the pre-last item in voting list
                if (! (currentVoteIndex < votingList.size() - 1)) {
                    lastVotingProcess();
                }
            }

            private void lastVotingProcess() {
                int sum = ArrayHelper.getSum(votesCount);
                votesCount[votesCount.length - 1] = votedMap.size()- sum;
                int maxVotesIndex = ArrayHelper.getMaxIndex(votesCount);

                if (ArrayHelper.countElements(votesCount[maxVotesIndex], votesCount) > 1){
                    label.setText("DUEL!");
                    return;
                }

                int eliminatedPlayer = votingList.get(maxVotesIndex);

                ((Player)players[eliminatedPlayer]).setStatus(getResources().getString(R.string.status_banished));
                label.setText("ELIMINATED: " + (eliminatedPlayer + 1));
            }

            private void countVotes() {
                votesCount[currentVoteIndex] = currentVotes;
                currentVotes = 0;
                currentVoteIndex++;
                for (Map.Entry<Player, Boolean> entry : thisTurnVoted.entrySet()) {
                    if (entry.getValue()) {
                        votedMap.put(entry.getKey(), true);
                        Button voteButton = voteButtons[entry.getKey().getPosition() - 1];
                        voteButton.setClickable(false);
                    }
                }
                Player votedPlayer = (Player) players[votingList.get(currentVoteIndex)];
                label.setText(String.format("%s%d - %s",getResources().getString(R.string.voting_for_lbl),
                        votedPlayer.getPosition(), votedPlayer.getNick()));
            }
        };
    }

    private View.OnClickListener getVoteButtonListener(final Player player, final Button btn) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!votedMap.get(player)) {
                    boolean current = thisTurnVoted.get(player);
                    thisTurnVoted.put(player, !current);
                    if (current) {
                        btn.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC);
                        currentVotes--;
                    } else {
                        btn.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC);
                        currentVotes++;
                    }
                }

            }
        };
    }

    private Map<Player,Boolean> getAlivePlayers(Parcelable[] players) {
        HashMap <Player,Boolean> res = new HashMap<Player, Boolean>();
        for (Parcelable p: players){
            if (((Player)p).getStatus().equals(getResources().getString(R.string.status_alive))){
                res.put((Player)p, false);
            }
        }
        return res;
    }
    */
}
