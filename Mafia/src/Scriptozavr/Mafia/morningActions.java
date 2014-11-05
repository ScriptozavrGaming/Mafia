package Scriptozavr.Mafia;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;


public class morningActions extends Activity {
    private final String FinalFilename = "FullPlayers.txt";
    private final String[] inGameStatuses = {"Жив", "Убит","Изгнан"};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.morning);

        final TextView[] playerNickNames = {
                (TextView) findViewById(R.id.nickMorn1),
                (TextView) findViewById(R.id.nickMorn2),
                (TextView) findViewById(R.id.nickMorn3),
                (TextView) findViewById(R.id.nickMorn4),
                (TextView) findViewById(R.id.nickMorn5),
                (TextView) findViewById(R.id.nickMorn6),
                (TextView) findViewById(R.id.nickMorn7),
                (TextView) findViewById(R.id.nickMorn8),
                (TextView) findViewById(R.id.nickMorn9),
                (TextView) findViewById(R.id.nickMorn10)
        };
        final TextView[] playerRoles = {
                (TextView) findViewById(R.id.roleMorn1),
                (TextView) findViewById(R.id.roleMorn2),
                (TextView) findViewById(R.id.roleMorn3),
                (TextView) findViewById(R.id.roleMorn4),
                (TextView) findViewById(R.id.roleMorn5),
                (TextView) findViewById(R.id.roleMorn6),
                (TextView) findViewById(R.id.roleMorn7),
                (TextView) findViewById(R.id.roleMorn8),
                (TextView) findViewById(R.id.roleMorn9),
                (TextView) findViewById(R.id.roleMorn10)
        };
        String[] nicknames = ReadFile(FinalFilename);
        for(int i=0,k=0;i<nicknames.length;i+=2,k++){
            playerNickNames[k].setText(nicknames[i]);
            playerRoles[k].setText(nicknames[i+1]);
        }
        final TextView[] playerStatus = {
                (TextView) findViewById(R.id.status1),
                (TextView) findViewById(R.id.status2),
                (TextView) findViewById(R.id.status3),
                (TextView) findViewById(R.id.status4),
                (TextView) findViewById(R.id.status5),
                (TextView) findViewById(R.id.status6),
                (TextView) findViewById(R.id.status7),
                (TextView) findViewById(R.id.status8),
                (TextView) findViewById(R.id.status9),
                (TextView) findViewById(R.id.status10)
        };
        for(TextView tv:playerStatus){
            tv.setText(inGameStatuses[2]);
        }
        final TextView[] playerFaults = {
                (TextView) findViewById(R.id.faults1),
                (TextView) findViewById(R.id.faults2),
                (TextView) findViewById(R.id.faults3),
                (TextView) findViewById(R.id.faults4),
                (TextView) findViewById(R.id.faults5),
                (TextView) findViewById(R.id.faults6),
                (TextView) findViewById(R.id.faults7),
                (TextView) findViewById(R.id.faults8),
                (TextView) findViewById(R.id.faults9),
                (TextView) findViewById(R.id.faults10)
        };
        for(TextView tv:playerFaults){
            tv.setText("0");
        }
    }

    private String[] ReadFile(String FILENAME){
        String[] str = new String[20];
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    openFileInput(FILENAME)));

            int i=0;
            String tempStr = "";
            while ((tempStr=br.readLine()) != null) {
                str[i]=tempStr;
                i++;
            }
        } catch (FileNotFoundException e) {
            for(int i = 0; i < str.length;i++){
                str[i] = "Player " + (i+1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }
}
