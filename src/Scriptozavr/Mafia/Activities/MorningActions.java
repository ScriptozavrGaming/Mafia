package Scriptozavr.Mafia.Activities;

import Scriptozavr.Mafia.Models.Player;
import Scriptozavr.Mafia.R;
import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


public class MorningActions extends Activity {
    private final String FinalFilename = "FullPlayers.txt";
    private Map<Button,Player> forTakeFault = new HashMap<Button, Player>();
    private Map<Button,Player> forVote = new HashMap<Button, Player>();
    private String forOnVoteTextView = new String();
    private ArrayList<Integer> playersOnVote = new ArrayList<Integer>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.morning);


        final TextView[] playerLabels = {
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
        final Button[] voteBtns = {
                (Button) findViewById(R.id.vote_btn1),
                (Button) findViewById(R.id.vote_btn2),
                (Button) findViewById(R.id.vote_btn3),
                (Button) findViewById(R.id.vote_btn4),
                (Button) findViewById(R.id.vote_btn5),
                (Button) findViewById(R.id.vote_btn6),
                (Button) findViewById(R.id.vote_btn7),
                (Button) findViewById(R.id.vote_btn8),
                (Button) findViewById(R.id.vote_btn9),
                (Button) findViewById(R.id.vote_btn10),
        };
        final Parcelable[] players = getIntent().getParcelableArrayExtra("players");
        //final Player[] players = ReadFile(FinalFilename);
        for (int i = 0; i < players.length; i++) {
            playerLabels[i].setText(players[i].toString());
            forTakeFault.put(faultsBtns[i],(Player)players[i]);
            forVote.put(voteBtns[i],(Player)players[i]);

            final int buttonInd = i;
            voteBtns[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    voteBtns[buttonInd].setClickable(false);
                    playersOnVote.add(buttonInd + 1);
                    ((TextView) findViewById(R.id.OnVote)).setText(getResources().getString(R.string.on_vote_label)
                            + Arrays.toString(playersOnVote.toArray()));
                }
            });
            faultsBtns[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Player p = (Player)players[buttonInd];
                    p.setFaults(p.getFaults() + 1);
                    if (p.getFaults() > 3) {
                        faultsBtns[buttonInd].setClickable(false);
                        p.setStatus(getResources().getString(R.string.status_banished));
                    }
                    playerLabels[buttonInd].setText(p.toString());
                }
            });
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
