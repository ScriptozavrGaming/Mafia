package Scriptozavr.Mafia.Activities;

import android.os.Bundle;

import java.util.ArrayList;

public class DuelVoting extends VoteForElimination {
    @Override
    public void onCreate(Bundle savedAppState) {
        super.onCreate(savedAppState);
    }

    @Override
    protected void voteDuel(ArrayList<Integer> duelists) {
        super.voteDuel(duelists);
    }

    @Override
    protected void voteSuccessful(int maxVotesIndex) {
        super.voteSuccessful(maxVotesIndex);
    }
}
