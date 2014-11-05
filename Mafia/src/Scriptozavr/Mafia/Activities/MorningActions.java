package Scriptozavr.Mafia.Activities;

import Scriptozavr.Mafia.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;


public class MorningActions extends Activity {
    private final String FinalFilename = "FullPlayers.txt";
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
