package Scriptozavr.Mafia.Activities;

import Scriptozavr.Mafia.Models.Player;
import Scriptozavr.Mafia.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class Main extends Activity {
    /**
     * Called when the activity is first created.
     */

    private final boolean DEBUG = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button new_game_Btn = (Button) findViewById(R.id.new_game_Btn);
        Button set_nicknames_Btn = (Button) findViewById(R.id.set_nicknames_Btn);

        View.OnClickListener OnClickNGBtn = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //actionsWhenButtonClicked
                switch (v.getId()) {
                    case R.id.new_game_Btn:
                        Intent chooseRoles = new Intent(getApplicationContext(), ChoosingRoles.class);
                        startActivity(chooseRoles);
                        break;
                    case R.id.set_nicknames_Btn:
                        Intent setNicknames = new Intent(getApplicationContext(), SetNicknames.class);
                        startActivity(setNicknames);
                        break;
                }
            }
        };
        new_game_Btn.setOnClickListener(OnClickNGBtn);
        set_nicknames_Btn.setOnClickListener(OnClickNGBtn);

        if (DEBUG) {
            findViewById(R.id.voting_test_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent votingForElimination = new Intent(getApplicationContext(), VoteForElimination.class);
                    Player[] p = new Player[10];
                    for(int i = 0; i < p.length; i++){
                        p[i] = new Player();
                        p[i].setFaults(0);
                        p[i].setRole(getResources().getString(R.string.peasant));
                        p[i].setStatus(getResources().getString(R.string.status_alive));
                        p[i].setNick("Player " + (i + 1));
                        p[i].setPosition(i + 1);
                    }
                    p[6].setRole(getResources().getString(R.string.comissar));
                    p[7].setRole(getResources().getString(R.string.don));
                    p[8].setRole(getResources().getString(R.string.mafia));
                    p[9].setRole(getResources().getString(R.string.mafia));
                    votingForElimination.putExtra("players",p);

                    ArrayList<Integer> votingList = new ArrayList<Integer>();
                    votingList.add(0);
                    votingList.add(1);
                    votingList.add(2);
//                    votingList.add(3);
//                    votingList.add(4);
//                    votingList.add(5);
//                    votingList.add(6);
//                    votingList.add(7);
//                    votingList.add(8);
//                    votingList.add(9);

                    votingForElimination.putIntegerArrayListExtra("votingList", votingList);
                    startActivity(votingForElimination);
                }
            });
            findViewById(R.id.toMorning_Btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent morning = new Intent(getApplicationContext(), MorningActions.class);
                    Player[] p = new Player[10];
                    for(int i = 0; i < p.length; i++){
                        p[i] = new Player();
                        p[i].setFaults(0);
                        p[i].setRole(getResources().getString(R.string.peasant));
                        p[i].setStatus(getResources().getString(R.string.status_alive));
                        p[i].setNick("Player " + (i + 1));
                        p[i].setPosition(i + 1);
                    }
                    p[6].setRole(getResources().getString(R.string.comissar));
                    p[7].setRole(getResources().getString(R.string.don));
                    p[8].setRole(getResources().getString(R.string.mafia));
                    p[9].setRole(getResources().getString(R.string.mafia));
                    morning.putExtra("players",p);

                    startActivity(morning);

                }
            });
        }

    }
}
