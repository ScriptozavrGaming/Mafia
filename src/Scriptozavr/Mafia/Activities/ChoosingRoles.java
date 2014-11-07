package Scriptozavr.Mafia.Activities;

import Scriptozavr.Mafia.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChoosingRoles extends Activity {
    private final String nickNameFile = "players.txt";
    private final String playerCharacteristics = "FullPlayers.txt";
    //Player[] players = new Player[10];
    //int[] RolesCount = {6,2,1,1};
    private final Map<String, Integer> RolesCount = new HashMap<String, Integer>();
    private final String[] forWritingFile = new String[10];

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choosing);

        //with love)
        RolesCount.put(getResources().getString(R.string.peasant), 6);
        RolesCount.put(getResources().getString(R.string.mafia), 2);
        RolesCount.put(getResources().getString(R.string.don), 1);
        RolesCount.put(getResources().getString(R.string.comissar), 1);

        String[] nicknames = ReadFile(nickNameFile);

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
        for(int i = 0; i < playerNickNames.length;i++){
            spinners[i] = (Spinner)findViewById(playerNickNames[i].getLabelFor());
        }
        //adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                RolesCount.keySet().toArray(new String[RolesCount.size()]));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        for (int i = 0; i < playerNickNames.length; i++) {
            playerNickNames[i].setText(nicknames[i]);
        }
        //initialize first and last spinners
        spinners[0].setAdapter(adapter);
        spinners[spinners.length - 1].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                findViewById(R.id.continue_btn).setVisibility(View.VISIBLE);
                forWritingFile[spinners.length - 1] = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        for (int i = 0; i < spinners.length - 1; i++)
        {
            final int nextIndex = i + 1;
            final Activity t = this;
            spinners[i].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    spinners[nextIndex].setVisibility(View.VISIBLE);
                    spinners[nextIndex - 1].setClickable(false);
                    List<String> availableRoles = new ArrayList<String>();
                    String choice = (String) parent.getItemAtPosition(position);
                    forWritingFile[nextIndex - 1] = choice;
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
            });
        }

        for (Spinner s : spinners) {
            s.setVisibility(View.INVISIBLE);
        }
        spinners[0].setVisibility(View.VISIBLE);

        findViewById(R.id.continue_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main = new Intent(getApplicationContext(), MorningActions.class);
                startActivity(main);
                writeFile(playerNickNames, forWritingFile);
                finish();
            }
        });

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

    private void writeFile(TextView[] pn, String[] roles) {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(openFileOutput(playerCharacteristics, MODE_PRIVATE)));
            for (int i = 0; i < roles.length; i++) {
                String writeString = String.format("%s;%s;%s;%d\n", pn[i].getText(), roles[i],
                        getResources().getString(R.string.status_alive), 0);

                bw.write(writeString);
            }
            bw.flush();
            bw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
