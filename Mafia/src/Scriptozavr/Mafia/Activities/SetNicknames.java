package Scriptozavr.Mafia.Activities;

import Scriptozavr.Mafia.Models.Player;
import Scriptozavr.Mafia.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.*;


public class SetNicknames extends Activity {


    private final String FILENAME = "players.txt";
    private Button save_Btn;
    private Button reset_Btn;

    EditText[] nicknames;
    public Player[] players = new Player[10];
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_nicknames);
        save_Btn = (Button) findViewById(R.id.save_Btn);
        reset_Btn = (Button) findViewById(R.id.reset_Btn);
        //EditTexts
        nicknames = new EditText[]{
                (EditText) findViewById(R.id.nick1),
                (EditText) findViewById(R.id.nick2),
                (EditText) findViewById(R.id.nick3),
                (EditText) findViewById(R.id.nick4),
                (EditText) findViewById(R.id.nick5),
                (EditText) findViewById(R.id.nick6),
                (EditText) findViewById(R.id.nick7),
                (EditText) findViewById(R.id.nick8),
                (EditText) findViewById(R.id.nick9),
                (EditText) findViewById(R.id.nick10)
        };

        View.OnClickListener OnClkButton = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {

                    case R.id.save_Btn:

                        writeFile(FILENAME);
                        Intent main = new Intent(getApplicationContext(), Main.class);
                        startActivity(main);
                        finish();
                        break;
                    case R.id.reset_Btn:
                        //with love from me))*
                        resetNickNames();
                        //---------
                        break;
                }

            }
        };
        save_Btn.setOnClickListener(OnClkButton);
        reset_Btn.setOnClickListener(OnClkButton);

        loadNicknamesFromFile();
    }

    private void resetNickNames() {
        for(int i = 0; i < nicknames.length; i++){
            nicknames[i].setText("Player " + (i+1));
        }
    }

    private void loadNicknamesFromFile() {
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput(FILENAME)));


            for (EditText n:nicknames){
                n.setText(br.readLine());
            }
        }catch(FileNotFoundException ex){
            resetNickNames();
        }catch (IOException ex){

        }
    }

    private void writeFile(String FILENAME){
        try{
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(openFileOutput(FILENAME,MODE_PRIVATE)));

            for(EditText n:nicknames){
                bw.write(n.getText() + "\n");
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
