package Scriptozavr.Mafia.Activities;

import Scriptozavr.Mafia.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.widget.TextView;
import android.widget.Spinner;
import java.io.*;
import java.util.*;

public class ChoosingRoles extends Activity {
    String[] Roles = {"Мирный", "Мафия", "Дон", "Комиссар"};
    //int[] RolesCount = {6,2,1,1};
    Map<String, Integer> RolesCount = new HashMap<String, Integer>();
    String[] forWritingFile = new String[10];
    //Player[] players = new Player[10];


    private final String FILENAME = "players.txt";
    private final String FinalFilename = "FullPlayers.txt";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choosing);

        //adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Roles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //with love)
        RolesCount.put("Мирный", 6);
        RolesCount.put("Мафия", 2);
        RolesCount.put("Дон", 1);
        RolesCount.put("Комиссар", 1);

        final Spinner[] spinners = {
                (Spinner) findViewById(R.id.spinner0),
                (Spinner) findViewById(R.id.spinner1),
                (Spinner) findViewById(R.id.spinner2),
                (Spinner) findViewById(R.id.spinner3),
                (Spinner) findViewById(R.id.spinner4),
                (Spinner) findViewById(R.id.spinner5),
                (Spinner) findViewById(R.id.spinner6),
                (Spinner) findViewById(R.id.spinner7),
                (Spinner) findViewById(R.id.spinner8),
                (Spinner) findViewById(R.id.spinner9)
        };
        String[] nicknames = ReadFile(FILENAME);

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
        for (int i = 0; i < playerNickNames.length; i++) {
            playerNickNames[i].setText(nicknames[i]);
        }
        //---------------
        spinners[spinners.length - 1].setAdapter(adapter);
        spinners[spinners.length - 1].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                findViewById(R.id.continue_btn).setVisibility(View.VISIBLE);
                forWritingFile[spinners.length-1]=(String)parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //public int i=0;
        spinners[0].setAdapter(adapter);

        for (int i = 0; i < spinners.length - 1; i++)
        //while(i<spinners.size()-1)
        {
            //int nextIndex = 0;
            //spinners[i].setAdapter(adapter);
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
        //spinners.get(1).setVisibility(View.VISIBLE);
        View.OnClickListener OnClkButton = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main = new Intent(getApplicationContext(), MorningActions.class);
                startActivity(main);
                writeFile(playerNickNames,forWritingFile);
            }
        };
        findViewById(R.id.continue_btn).setOnClickListener(OnClkButton);

    }

    private String[] ReadFile(String FILENAME){
        String[] str = new String[10];
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

    private void writeFile(TextView[] pn, String[] roles){
        try{
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(openFileOutput(FinalFilename,MODE_PRIVATE)));
            for(int i=0;i<roles.length;i++){
                bw.write(pn[i].getText()+"\n");
                bw.write(roles[i]+"\n");
            }
            bw.flush();
            bw.close();

            //-----------------------------------
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

    }
}
