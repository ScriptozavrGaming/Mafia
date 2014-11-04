package Scriptozavr.Mafia;

import android.app.Activity;
import android.content.Intent;
import  android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.widget.TextView;
import android.widget.Spinner;

import java.io.*;

import java.util.ArrayList;

public class choosingRoles extends Activity {
    String[] Roles = {"Мирный","Мафия","Дон","Комиссар"};
    Player[] players = new Player[10];
    //int playerNumb = 1;
    int nextIndex =1;
    private final String FILENAME = "players.txt";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choosing);

        //adapter
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Roles);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Roles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //adapter
        //Find Spinners withlove)
        Spinner p1 = (Spinner) findViewById(R.id.spinner0);
        Spinner p2 = (Spinner) findViewById(R.id.spinner1);
        Spinner p3 = (Spinner) findViewById(R.id.spinner2);
        Spinner p4 = (Spinner) findViewById(R.id.spinner3);
        Spinner p5 = (Spinner) findViewById(R.id.spinner4);
        Spinner p6 = (Spinner) findViewById(R.id.spinner5);
        Spinner p7 = (Spinner) findViewById(R.id.spinner6);
        Spinner p8 = (Spinner) findViewById(R.id.spinner7);
        Spinner p9 = (Spinner) findViewById(R.id.spinner8);
        Spinner p10 = (Spinner) findViewById(R.id.spinner9);
        //Find Spinners
        //govnokod need to fix with love)))
        final ArrayList<Spinner> spinners = new ArrayList<Spinner>();
        spinners.add(p1);
        spinners.add(p2);
        spinners.add(p3);
        spinners.add(p4);
        spinners.add(p5);
        spinners.add(p6);
        spinners.add(p7);
        spinners.add(p8);
        spinners.add(p9);
        spinners.add(p10);
        //need to fix
        String[] nicknames = ReadFile(FILENAME);
        //One more with love)))))))))
        TextView poNick = (TextView) findViewById(R.id.p0Nick);
        TextView p1Nick = (TextView) findViewById(R.id.p1Nick);
        TextView p2Nick = (TextView) findViewById(R.id.p2Nick);
        TextView p3Nick = (TextView) findViewById(R.id.p3Nick);
        TextView p4Nick = (TextView) findViewById(R.id.p4Nick);
        TextView p5Nick = (TextView) findViewById(R.id.p5Nick);
        TextView p6Nick = (TextView) findViewById(R.id.p6Nick);
        TextView p7Nick = (TextView) findViewById(R.id.p7Nick);
        TextView p8Nick = (TextView) findViewById(R.id.p8Nick);
        TextView p9Nick = (TextView) findViewById(R.id.p9Nick);

        poNick.setText(nicknames[0]);
        p1Nick.setText(nicknames[1]);
        p2Nick.setText(nicknames[2]);
        p3Nick.setText(nicknames[3]);
        p4Nick.setText(nicknames[4]);
        p5Nick.setText(nicknames[5]);
        p6Nick.setText(nicknames[6]);
        p7Nick.setText(nicknames[7]);
        p8Nick.setText(nicknames[8]);
        p9Nick.setText(nicknames[9]);
        //---------------
        spinners.get(spinners.size()-1).setAdapter(adapter);
        spinners.get(spinners.size()-1).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //players[spinners.size() -1] = new Player("Sasha", spinners.size()-1, parent.getItemAtPosition(position).toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //public int i=0;
        for(int i=0;i<spinners.size() - 1;i++)
        //while(i<spinners.size()-1)
        {
            //int nextIndex = 0;
            spinners.get(i).setAdapter(adapter);
            spinners.get(i).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    //players[nextIndex - 1]=new Player("Sasha", nextIndex, parent.getItemAtPosition(position).toString());
                    spinners.get(nextIndex).setVisibility(View.VISIBLE);
                    nextIndex++;
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

        for (Spinner s: spinners){
            s.setVisibility(View.INVISIBLE);
        }
        spinners.get(0).setVisibility(View.VISIBLE);
        //spinners.get(1).setVisibility(View.VISIBLE);
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
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
       return str;
    }
}
