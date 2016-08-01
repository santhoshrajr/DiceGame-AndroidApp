package my.santh.dicegame.dice50;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "com.example.santh.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }





    public void onPlay(View v)
    {

        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        alert.setTitle("Players Name please");
        final TextView rowTextView = new TextView(this);

        // set some properties of rowTextView or something
        rowTextView.setText("Player 1 Name ");
        layout.addView(rowTextView);


        final EditText titleBox = new EditText(this);
        titleBox.setHint("Player 1 ");
        layout.addView(titleBox);

        final TextView row2TextView = new TextView(this);

        // set some properties of rowTextView or something
        row2TextView.setText("Player 2 ");
        layout.addView(row2TextView);

        final EditText descriptionBox = new EditText(this);
        descriptionBox.setHint("Player 2 ");
        layout.addView(descriptionBox);

        alert.setView(layout);
        final String Name=titleBox.getText().toString();
        final String word=descriptionBox.getText().toString();

        final Intent intent = new Intent(this,game.class);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int whichButton) {

                    String Player1name, Player2name;
                    // SHOULD NOW WORK
                    Player1name = titleBox.getText().toString();
                    Player2name = descriptionBox.getText().toString();
                    String message1, message2;
                    message1 = Player1name;
                    message2 = Player2name;
                    String playersname = message1 + "@" + message2;
                    Bundle extras = new Bundle();
                    extras.putString(message1, "Player1");
                    extras.putString(message2, "Player2");
                    intent.putExtra(EXTRA_MESSAGE, playersname);
                    intent.putExtra("Dice", "MainActivity");
                    startActivity(intent);


            }

        });
        alert.setNegativeButton("CANCEL", null);
        alert.create().show();

        //AlertDialog.Builder b = new AlertDialog.Builder(this);




    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
            case R.id.info:
                //Intents are used to start new activities, in this case it starts my InformationActivity class
                //which contains my info page
                Intent intent = getIntent();
                //String message = intent.getStringExtra(InitialActivity.EXTRA_MESSAGE);

                Intent infoIntent = new Intent(this, AppInfo.class);

                infoIntent.putExtra("Dice", "main");
                startActivity(infoIntent);

                break;
        }return super.onOptionsItemSelected(item);

    }
    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }


}
