package Scriptozavr.Mafia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.*;


public class setNicknames extends Activity {


    private final String FILENAME = "players.txt";
    private Button save_Btn;
    private Button reset_Btn;
    EditText nick1;
    EditText nick2;
    EditText nick3;
    EditText nick4;
    EditText nick5;
    EditText nick6;
    EditText nick7;
    EditText nick8;
    EditText nick9;
    EditText nick10;
    public Player[] players = new Player[10];
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_nicknames);
        save_Btn = (Button) findViewById(R.id.save_Btn);
        reset_Btn = (Button) findViewById(R.id.reset_Btn);
        //EditTexts
        nick1 = (EditText) findViewById(R.id.nick1);
        nick2 = (EditText) findViewById(R.id.nick2);
        nick3 = (EditText) findViewById(R.id.nick3);
        nick4 = (EditText) findViewById(R.id.nick4);
        nick5 = (EditText) findViewById(R.id.nick5);
        nick6 = (EditText) findViewById(R.id.nick6);
        nick7 = (EditText) findViewById(R.id.nick7);
        nick8 = (EditText) findViewById(R.id.nick8);
        nick9 = (EditText) findViewById(R.id.nick9);
        nick10 = (EditText) findViewById(R.id.nick10);
        //------

        View.OnClickListener OnClkButton = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {

                    case R.id.save_Btn:

                        writeFile(FILENAME);
                        Intent main = new Intent(getApplicationContext(), main.class);
                        startActivity(main);
                        break;
                    case R.id.reset_Btn:
                        //with love from me))*
                        nick1.setText("Player 1");
                        nick2.setText("Player 2");
                        nick3.setText("Player 3");
                        nick4.setText("Player 4");
                        nick5.setText("Player 5");
                        nick6.setText("Player 6");
                        nick7.setText("Player 7");
                        nick8.setText("Player 8");
                        nick9.setText("Player 9");
                        nick10.setText("Player 10");
                        //---------
                        break;
                }

            }
        };
        save_Btn.setOnClickListener(OnClkButton);
        reset_Btn.setOnClickListener(OnClkButton);
    }

    private void writeFile(String FILENAME){
        try{
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(openFileOutput(FILENAME,MODE_PRIVATE)));
            //FileOutputStream os = openFileOutput(FILENAME,MODE_PRIVATE);
            //OutputStreamWriter bw = new OutputStreamWriter(os);
            //WITH LOVE FROM ME :****
            bw.write(nick1.getText().toString());
            bw.newLine();
            bw.write(nick2.getText().toString());
            bw.newLine();
            bw.write(nick3.getText().toString());
            bw.newLine();
            bw.write(nick4.getText().toString());
            bw.newLine();
            bw.write(nick5.getText().toString());
            bw.newLine();
            bw.write(nick6.getText().toString());
            bw.newLine();
            bw.write(nick7.getText().toString());
            bw.newLine();
            bw.write(nick8.getText().toString());
            bw.newLine();
            bw.write(nick9.getText().toString());
            bw.newLine();
            bw.write(nick10.getText().toString());
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
