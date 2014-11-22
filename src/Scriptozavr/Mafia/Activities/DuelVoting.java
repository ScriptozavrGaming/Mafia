package Scriptozavr.Mafia.Activities;

import Scriptozavr.Mafia.Helpers.ArrayHelper;
import Scriptozavr.Mafia.Models.Player;
import Scriptozavr.Mafia.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class DuelVoting extends VoteForElimination {
    @Override
    public void onCreate(Bundle savedAppState) {
        super.onCreate(savedAppState);
    }

    @Override
    protected void voteDuel(final ArrayList<Integer> duelists) {
        votedPlayerTextView.setText(ArrayHelper.arrayToString("DUEL, players are: ", duelists) + "what to do?");
        //если <6 игроков, нельзя выгонять больше одного
        //если <??? игроков, нельзя выгонять больше двух
        Button killButton = (Button)findViewById(R.id.killDuelantsBtn);
        if (players.length >= 6 && duelists.size() > 1 ||
                players.length >= 8 && duelists.size() > 2) {

            killButton.setVisibility(View.VISIBLE);
        }

        Button releaseButton = (Button)findViewById(R.id.releaseDuelantsBtn);
        releaseButton.setVisibility(View.VISIBLE);
        killButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i: duelists){
                    ((Player)players[votingList.get(i)]).setStatus(getResources().getString(R.string.status_banished));
                }

                Intent i = new Intent(getApplicationContext(),LastMinute.class);
                i.putExtra("players", players);
                i.putIntegerArrayListExtra("lastMin", duelists);
                i.putExtra("ifKilled", false);
                int currentCircle = getIntent().getIntExtra("currentCircle", 0);
                i.putExtra("currentCircle", currentCircle);
                startActivity(i);
                finish();
            }
        });

        releaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), NightActions.class);
                i.putExtra("players", players);
                int currentCircle = getIntent().getIntExtra("currentCircle", 0);
                i.putExtra("currentCircle", currentCircle);
                startActivity(i);
                finish();
            }
        });

    }
}
