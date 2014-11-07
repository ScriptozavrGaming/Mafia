package Scriptozavr.Mafia.Activities;

import Scriptozavr.Mafia.Models.Player;
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
                (TextView) findViewById(R.id.number1),
                (TextView) findViewById(R.id.number2),
                (TextView) findViewById(R.id.number3),
                (TextView) findViewById(R.id.number4),
                (TextView) findViewById(R.id.number5),
                (TextView) findViewById(R.id.number6),
                (TextView) findViewById(R.id.number7),
                (TextView) findViewById(R.id.number8),
                (TextView) findViewById(R.id.number9),
                (TextView) findViewById(R.id.number10)
        };
        Player[] players = ReadFile(FinalFilename);
        for (int i = 0; i < players.length; i++) {
            playerNickNames[i].setText(players[i].toString());
        }
    }

    private Player[] ReadFile(String FILENAME) {
        Player[] players = new Player[10];
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    openFileInput(FILENAME)));

            for(int i = 0; i < players.length;i++) {
                String[] tokens = br.readLine().split(";");
                players[i] = new Player();
                players[i].setPosition(i+1);
                players[i].setNick(tokens[0]);
                players[i].setRole(tokens[1]);
                players[i].setStatus(tokens[2]);
                players[i].setFaults(Integer.parseInt(tokens[3]));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return players;
    }
}
