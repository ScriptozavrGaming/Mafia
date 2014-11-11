package Scriptozavr.Mafia.Activities;

import Scriptozavr.Mafia.R;
import android.os.Bundle;

public class DuelVoting extends VoteForElimination {
    @Override
    public void onCreate(Bundle savedAppState) {
        super.onCreate(savedAppState);
        setContentView(R.layout.voting);

        players = getIntent().getParcelableArrayExtra("players");

    }
}
