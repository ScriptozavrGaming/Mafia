package Scriptozavr.Mafia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

public class main extends Activity {
    /**
     * Called when the activity is first created.
     */
    Button new_game_Btn;
    Button set_nicknames_Btn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        new_game_Btn = (Button) findViewById(R.id.new_game_Btn);
        set_nicknames_Btn= (Button) findViewById(R.id.set_nicknames_Btn);
        View.OnClickListener OnClickNGBtn = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //actionsWhenButtonClicked
                switch (v.getId()){
                    case R.id.new_game_Btn:
                                  Intent chooseRoles = new Intent(getApplicationContext(),choosingRoles.class);
                                  startActivity(chooseRoles);
                                  break;
                    case R.id.set_nicknames_Btn:
                                   Intent setNicknames = new Intent(getApplicationContext(), setNicknames.class);
                                   startActivity(setNicknames);
                                   break;
                }
            }
        };
        new_game_Btn.setOnClickListener(OnClickNGBtn);
        set_nicknames_Btn.setOnClickListener(OnClickNGBtn);


    }


}
