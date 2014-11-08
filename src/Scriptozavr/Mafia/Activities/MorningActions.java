package Scriptozavr.Mafia.Activities;

import Scriptozavr.Mafia.Models.Player;
import Scriptozavr.Mafia.R;
import android.app.Activity;
import android.os.Bundle;
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
    private int sizeofArr = 1;
    private int [] playersOnVote = new int[sizeofArr];

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
        Player[] players = ReadFile(FinalFilename);
        for (int i = 0; i < players.length; i++) {
            playerNickNames[i].setText(players[i].toString());
        }
        for (int i=0;i<faultsBtns.length;i++){
            forTakeFault.put(faultsBtns[i],players[i]);
            forVote.put(voteBtns[i],players[i]);
        }
        View.OnClickListener OnClckBtn = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(forTakeFault.containsKey(findViewById(v.getId()))) {
                    int fault = (forTakeFault.get(findViewById(v.getId()))).getFaults();
                    int posFault = (forTakeFault.get(findViewById(v.getId()))).getPosition() - 1;
                    fault++;
                    if (fault > 3) {
                        faultsBtns[posFault].setClickable(false);
                        (forTakeFault.get(findViewById(v.getId()))).setStatus("Изгнан");
                    }
                    (forTakeFault.get(findViewById(v.getId()))).setFaults(fault);
                    playerNickNames[posFault].setText((forTakeFault.get(findViewById(v.getId()))).toString());
                }
                if(forVote.containsKey(findViewById(v.getId()))) {
                    int posOnVote = (forVote.get(findViewById(v.getId()))).getPosition();

                    forOnVoteTextView += Integer.toString(posOnVote) + " ";
                    voteBtns[posOnVote-1].setClickable(false);
                    if(!forOnVoteTextView.isEmpty()){
                        if(sizeofArr == 1){
                            playersOnVote[sizeofArr-1] = posOnVote;
                        }
                        else {
                            sizeofArr++;
                            playersOnVote = resizeArr(playersOnVote, sizeofArr);
                            forOnVoteTextView += Integer.toString(posOnVote) + " ";
                        }
                        ((TextView) findViewById(R.id.OnVote)).setText("Выставлены: " + forOnVoteTextView);
                    }
                }
            }

        };
        for(Button b: faultsBtns){
            b.setOnClickListener(OnClckBtn);
        }
        for (Button b: voteBtns){
            b.setOnClickListener(OnClckBtn);
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

    private int[] resizeArr(int[] toResize,int newSize){
        int[] newArr=new int[newSize];
        for (int i=0;i<newSize;i++)
            newArr[i] = toResize[i];
        return newArr;
    }
}
