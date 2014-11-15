package Scriptozavr.Mafia.Activities;

import Scriptozavr.Mafia.R;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DuelistsSpeech extends MorningActions {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.morning);

        final Button[] faultsBtns = {
                (Button) findViewById(R.id.fault_btn1),
                (Button) findViewById(R.id.fault_btn2),
                (Button) findViewById(R.id.fault_btn3),
                (Button) findViewById(R.id.fault_btn4),
                (Button) findViewById(R.id.fault_btn5),
                (Button) findViewById(R.id.fault_btn6),
                (Button) findViewById(R.id.fault_btn7),
                (Button) findViewById(R.id.fault_btn8),
                (Button) findViewById(R.id.fault_btn9),
                (Button) findViewById(R.id.fault_btn10),
        };

        for (Button b: faultsBtns){
            b.setVisibility(View.INVISIBLE);
        }
    }
}
