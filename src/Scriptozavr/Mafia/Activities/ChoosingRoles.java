package Scriptozavr.Mafia.Activities;

import Scriptozavr.Mafia.Models.Player;
import Scriptozavr.Mafia.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.io.*;
import java.util.*;

public class ChoosingRoles extends Activity {
    private final String nickNameFile = "players.txt";
    private final Player[] players = new Player[10];
    private final Map<String, Integer> RolesCount = new HashMap<String, Integer>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choosing);

        RolesCount.put(getResources().getString(R.string.peasant), 6);
        RolesCount.put(getResources().getString(R.string.mafia), 2);
        RolesCount.put(getResources().getString(R.string.don), 1);
        RolesCount.put(getResources().getString(R.string.comissar), 1);


        final TextView[] playerNickNames = {
                (TextView) findViewById(R.id.p0Nick),
                (TextView) findViewById(R.id.p1Nick),
                (TextView) findViewById(R.id.p2Nick),
                (TextView) findViewById(R.id.p3Nick),
                (TextView) findViewById(R.id.p4Nick),
                (TextView) findViewById(R.id.p5Nick),
                (TextView) findViewById(R.id.p6Nick),
                (TextView) findViewById(R.id.p7Nick),
                (TextView) findViewById(R.id.p8Nick),
                (TextView) findViewById(R.id.p9Nick)
        };
        final Spinner[] spinners = new Spinner[playerNickNames.length];

        initPlayersSpinners(playerNickNames, spinners);
        initFirstAndLastSpinnerAdapters(spinners);

        for (int i = 0; i < spinners.length - 1; i++)
        {
            spinners[i].setOnItemSelectedListener(getListener(spinners, i + 1));
        }

        findViewById(R.id.continue_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main = new Intent(getApplicationContext(), MorningActions.class);
                main.putExtra("players", players);
                startActivity(main);
                finish();
            }
        });
    }

    private AdapterView.OnItemSelectedListener getListener(final Spinner[] spinners, final int nextIndex) {
        final Activity t = this;
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinners[nextIndex].setVisibility(View.VISIBLE);
                spinners[nextIndex - 1].setClickable(false);
                List<String> availableRoles = new ArrayList<String>();
                String choice = (String) parent.getItemAtPosition(position);
                players[nextIndex - 1].setRole(choice);
                RolesCount.put(choice, RolesCount.get(choice) - 1);

                for (int i = 0; i < parent.getCount(); i++) {
                    if (RolesCount.get(parent.getItemAtPosition(i)) > 0) {
                        availableRoles.add((String) parent.getItemAtPosition(i));
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(t, android.R.layout.simple_spinner_item, availableRoles);//Roles);

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinners[nextIndex].setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
    }

    private void initFirstAndLastSpinnerAdapters(final Spinner[] spinners) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                RolesCount.keySet().toArray(new String[RolesCount.size()]));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinners[0].setAdapter(adapter);
        spinners[spinners.length - 1].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                findViewById(R.id.continue_btn).setVisibility(View.VISIBLE);
                players[spinners.length - 1].setRole((String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initPlayersSpinners(TextView[] playerNickNames, Spinner[] spinners) {
        String[] nicknames = ReadFile(nickNameFile);

        for (int i = 0; i < players.length; i++) {
            players[i] = new Player(nicknames[i], i+1, null,
                    getResources().getString(R.string.status_alive), 0);

            spinners[i] = (Spinner)findViewById(playerNickNames[i].getLabelFor());
            spinners[i].setVisibility(View.INVISIBLE);
            playerNickNames[i].setText(nicknames[i]);
        }
        spinners[0].setVisibility(View.VISIBLE);
    }

    private String[] ReadFile(String FILENAME) {
        String[] str = new String[10];
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    openFileInput(FILENAME)));

            int i = 0;
            String tempStr;
            while ((tempStr = br.readLine()) != null) {
                str[i] = tempStr;
                i++;
            }
        } catch (FileNotFoundException e) {
            for (int i = 0; i < str.length; i++) {
                str[i] = "Player " + (i + 1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return str;
    }
}
