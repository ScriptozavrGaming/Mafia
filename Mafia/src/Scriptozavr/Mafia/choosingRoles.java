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
    ArrayList<Player> players = new ArrayList<Player>();
    int playerNumb = 1;
    int i = 0;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choosing);

        //adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Roles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //adapter
        //Find Spinners
        Spinner p1 = (Spinner) findViewById(R.id.p1);
        Spinner p2 = (Spinner) findViewById(R.id.p2);
        Spinner p3 = (Spinner) findViewById(R.id.p3);
        Spinner p4 = (Spinner) findViewById(R.id.p4);
        Spinner p5 = (Spinner) findViewById(R.id.p5);
        Spinner p6 = (Spinner) findViewById(R.id.p6);
        Spinner p7 = (Spinner) findViewById(R.id.p7);
        Spinner p8 = (Spinner) findViewById(R.id.p8);
        Spinner p9 = (Spinner) findViewById(R.id.p9);
        Spinner p10 = (Spinner) findViewById(R.id.p10);
        //Find Spinners
        //govnokod need to fix
        ArrayList<Spinner> spinners = new ArrayList<Spinner>();
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

        //for(int i=0;i<spinners.size();i++) {
        while(i<10){
            (spinners.get(i)).setVisibility(View.VISIBLE);

            spinners.get(i).setAdapter(adapter);
            spinners.get(i).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    players.add(new Player("Sasha", playerNumb, parent.getItemAtPosition(position).toString()));
                    i++;
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            playerNumb++;
        }
        double b;
        //p = new Player("Sasha",1,Role);
        //players.add(p);
        //TextView TV = (TextView) findViewById(R.id.player1);
        //TV.setText(players.get(0).role);
    }
}
