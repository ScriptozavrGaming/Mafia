package Scriptozavr.Mafia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class main extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

       Button NGBtn = (Button) findViewById(R.id.NGBtn);
        View.OnClickListener OnClickNGBtn = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //actionsWhenButtonClicked
                Intent chooseRoles = new Intent(getApplicationContext(),choosingRoles.class);
                startActivity(chooseRoles);
            }
        };
        NGBtn.setOnClickListener(OnClickNGBtn);
    }


}
