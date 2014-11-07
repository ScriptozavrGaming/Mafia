package Scriptozavr.Mafia.Activities;

import Scriptozavr.Mafia.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main extends Activity {
    /**
     * Called when the activity is first created.
     */
    private Button new_game_Btn;
    private Button set_nicknames_Btn;

    private final boolean DEBUG = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        new_game_Btn = (Button) findViewById(R.id.new_game_Btn);
        set_nicknames_Btn = (Button) findViewById(R.id.set_nicknames_Btn);
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
            Button vote_Btn = (Button) findViewById(R.id.voting_test_btn);
            vote_Btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent votingForElimination = new Intent(getApplicationContext(), VoteForElimination.class);
                    startActivity(votingForElimination);
                }
            });
        }

    }


}
