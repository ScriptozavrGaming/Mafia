package Scriptozavr.Mafia.Activities;

import Scriptozavr.Mafia.Models.Player;
import android.app.Activity;
import android.os.Bundle;

public class VoteForElimination extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        Player p = getIntent().getParcelableExtra("test_val");
        p.setFaults(15);
    }
}
