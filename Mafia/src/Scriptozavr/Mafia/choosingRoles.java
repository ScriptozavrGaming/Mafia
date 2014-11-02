package Scriptozavr.Mafia;

import android.app.Activity;
import android.content.Intent;
import  android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.widget.TextView;
import android.widget.Spinner;

import java.util.ArrayList;

public class choosingRoles extends Activity {
    String[] Roles = {"Мирный","Мафия","Дон","Комиссар"};
    Player[] players = new Player[10];
    int playerNumb = 1;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choosing);

        //adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Roles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //adapter
        //Find Spinners
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
        //govnokod need to fix
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

        spinners.get(spinners.size()-1).setAdapter(adapter);
        spinners.get(spinners.size()-1).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                players[spinners.size() -1] = new Player("Sasha", spinners.size()-1, parent.getItemAtPosition(position).toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        for(int i=0;i<spinners.size() - 1;i++)
        {
            final int nextIndex = i + 1;
            spinners.get(i).setAdapter(adapter);
            spinners.get(i).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    players[nextIndex - 1]=new Player("Sasha", nextIndex, parent.getItemAtPosition(position).toString());
                    //spinners.get(nextIndex).setVisibility(View.VISIBLE);

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
        spinners.get(1).setVisibility(View.VISIBLE);
    }
}
