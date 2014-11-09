package Scriptozavr.Mafia.Activities;

import Scriptozavr.Mafia.Helpers.ArrayHelper;
import Scriptozavr.Mafia.Models.Player;
import Scriptozavr.Mafia.R;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VoteForElimination extends Activity {
    //status of all players
    private Parcelable[] players;
    //people who were pushed to vote
    private ArrayList<Integer> votingList;
    //flags for people who voted
    private Map<Player,Boolean> thisTurnVoted;
    private Map<Player,Boolean> votedMap;
    //count of votes for each victim
    private int[] votesCount;
    private int currentVoteIndex = 0;
    private int currentVotes;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voting);

        initMembers();

        final Button[] voteButtons = {
                (Button) findViewById(R.id.button1),
                (Button) findViewById(R.id.button2),
                (Button) findViewById(R.id.button3),
                (Button) findViewById(R.id.button4),
                (Button) findViewById(R.id.button5),
                (Button) findViewById(R.id.button6),
                (Button) findViewById(R.id.button7),
                (Button) findViewById(R.id.button8),
                (Button) findViewById(R.id.button9),
                (Button) findViewById(R.id.button10)
        };

        for(int i = 0; i < players.length; i++){
            if (!((Player)players[i]).getStatus().equals(getResources().getString(R.string.status_alive))){
                voteButtons[i].getBackground().setColorFilter(Color.GRAY,PorterDuff.Mode.SRC);
                voteButtons[i].setClickable(false);
            }else{
                final Player player = (Player) players[i];
                final Button btn = voteButtons[i];
                btn.getBackground().setColorFilter(Color.GREEN,PorterDuff.Mode.SRC);
                btn.setOnClickListener(getVoteButtonListener(player, btn));
            }
        }
        findViewById(R.id.buttonOk).setOnClickListener(getOkButtonListener(voteButtons));
        ((TextView)findViewById(R.id.votingForLabel)).setText(String.format("%s%d - %s",
                getResources().getString(R.string.voting_for_lbl), votingList.get(0) + 1,
                ((Player) players[votingList.get(0)]).getNick()));
    }

    private void initMembers() {
        players = getIntent().getParcelableArrayExtra("players");
        votingList = getIntent().getIntegerArrayListExtra("votingList");
        for (int i: votingList) i--;
        if (votingList.size() == 1){
            ((Player)players[votingList.get(0)]).setStatus(getResources().getString(R.string.status_banished));
        }
        votedMap = getAlivePlayers(players);
        thisTurnVoted = getAlivePlayers(players);
        votesCount = new int[votingList.size()];
        currentVotes = 0;

    }

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
}
