package my.santh.dicegame.dice50;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import android.os.*;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import android.hardware.SensorEventListener;
import android.hardware.Sensor;
import android.hardware.SensorEvent;

public class game extends AppCompatActivity implements SensorEventListener {
    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    public final static String EXTRA_MESSAGE = "com.example.santh.MESSAGE";
    ImageView dice_picture;        //reference to dice picture
    ImageView dice_picture2;
    String message;
    Random rng = new Random();    //generate random numbers
    SoundPool dice_sound = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
    SoundPool diceBonus=new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
    SoundPool diceLose=new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
    int sound_id;
    int sound_bonus;
     int sound_lose;//Used to control sound stream return by SoundPool
    Handler handler;    //Post message to start roll
    Timer timer = new Timer();    //Used to implement feedback to user
    boolean rolling = false;        //Is die rolling?
    Button diceRollVauep1;
    int sump1=0;
    int sump2=0;
    int x ;
    int y ;
    int previousx,previousy;
    private long lastUpdate = 0;
    private float last_x, last_y, last_z;
    private static final int SHAKE_THRESHOLD = 600;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    TextView Player1name ;
    Button diceRollvaluep2;
    Button p1Score;
    Button p2Score;

    String player1 ;
    String player2 ;
    MediaPlayer mdiceSound;
    MediaPlayer mdiceWin;
    MediaPlayer mdiceLose;
    Intent intentMain ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
         mdiceSound=MediaPlayer.create(this,R.raw.shake_dice);
         mdiceWin=MediaPlayer.create(this,R.raw.win);
         mdiceLose=MediaPlayer.create(this,R.raw.lose);
        p1Score=(Button)findViewById(R.id.button4);
        p2Score=(Button)findViewById(R.id.button5);
        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        Intent intent = getIntent();

        //load dice sound
       // sound_id = dice_sound.load(this, R.raw.shake_dice, 1);
        //sound_bonus=diceBonus.load(this,R.raw.youwinsoundeffect5,1);
        //sound_lose=diceBonus.load(this,R.raw.lose,1);
        //get reference to image widget
        dice_picture = (ImageView) findViewById(R.id.imageView);
        dice_picture2 = (ImageView) findViewById(R.id.imageView2);
        dice_picture2.setEnabled(false);
        diceRollVauep1 = (Button) findViewById(R.id.button2);
        diceRollvaluep2 = (Button) findViewById(R.id.button3);




        //link handler to callback
        handler = new Handler(callback);
        onreturn();
      /*  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor mySensor = event.sensor;

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            long curTime = System.currentTimeMillis();

            if ((curTime - lastUpdate) > 100) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;

                float speed = Math.abs(x + y + z - last_x - last_y - last_z)/ diffTime * 10000;

                if (speed > SHAKE_THRESHOLD && sump1<50 && sump2<50) {
                    if (!rolling) {
                        rolling = true;
                        //Show rolling image
                        if(dice_picture2.isEnabled())

                            dice_picture2.setImageResource(R.drawable.blue);
                        else
                            dice_picture.setImageResource(R.drawable.blue);

                        //Start rolling sound
                       // dice_sound.play(sound_id, 1.0f, 1.0f, 0, 0, 1.0f);
                        mdiceSound.start();
                        //Pause to allow image to update
                        timer.schedule(new Roll(), 400);
                    }
                }

                last_x = x;
                last_y = y;
                last_z = z;
            }
        }
    }

    public void onSensorChange(SensorEvent sensorEvent) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void onreturn()
    {
        /*Intent intentfrominfo=getIntent();
        String messagefrominfo=intentfrominfo.getStringExtra(AppInfo.EXTRA_MESSAGE);
        TextView textView = (TextView)findViewById(R.id.textView);*/

        Intent intent = getIntent();
        String strdata = intent.getExtras().getString("Dice");
        Button p1Name = (Button) findViewById(R.id.button0);
        Button p2Name = (Button) findViewById(R.id.button1);
        Player1name = (TextView) findViewById(R.id.name);
        if(strdata.equals("MainActivity")) {
            message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);


            if (message.equals("@") || message.isEmpty()) {
                player1 = "player1";
                player2 = "player2";
            } else {
                String message1[] = message.split("@");
                player1 = message1[0];
                player2 = message1[1];
            }


            //player1=intent.getStringExtra("Player1");
            //player2=intent.getStringExtra("Player2");
            //String fullmessage= intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
            //System.out.print(fullmessage);
            //Bundle extras = intent.getExtras();
            //Bundle bundle = this.getIntent().getExtras();

            //player1 = bundle.getString("Player1");
            // player2=bundle.getString("Player2");
            ///player1 = extras.getString();
            //player2 = extras.getString("EXTRA_PASSWORD");


            Player1name.setText(player1 + "to play");
            p1Name.setText("" + player1);
            p2Name.setText("" + player2);
        }
        else if (strdata.equals("Appinfo"))
        {
       Intent intentfromApp = getIntent();
// message = intent.getStringExtra(AppInfo.EXTRA_MESSAGE);
            Bundle getData = intentfromApp.getExtras();
            Bundle getDa=intent.getExtras();
            /*String message1[] = message.split("@");
            p1Score.setText( message1[0]);
            p2Score.setText( message1[1]);
            diceRollVauep1.setText(message1[2]);
            diceRollvaluep2.setText(message1[3]);
            p1Name.setText("" + message1[4]);
            p2Name.setText("" + message1[5]);*/
            String p1Sc=getData.getString("score of player1");
            p1Score.setText(""+p1Sc);
           try {
               sump1 = Integer.parseInt(p1Score.getText().toString());
           }
           catch(NumberFormatException e){sump1=0;}

            String p2sc=getData.getString("score of player2");
            p2Score.setText(""+p2sc);
            try {
                sump2 = Integer.parseInt(p2Score.getText().toString());
            }
            catch(NumberFormatException e){sump2=0;}
            String p1na=getData.getString("player1name");
            p1Name.setText(p1na);
            String p2na=getData.getString("player2name");
            p2Name.setText(p2na);
            String p1cs=getData.getString("current p1 score");
            diceRollVauep1.setText(p1cs);
            String p2cs=getData.getString("current p2 score");
            diceRollvaluep2.setText(p2cs);
            String playern=getData.getString("currentplayer");
            Player1name.setText(playern);
            player1=p1na;
            player2=p2na;
            previousx=Integer.parseInt(getData.getString("previousx"));
            previousy=Integer.parseInt(getData.getString("previousy"));
            int curvaluex=Integer.parseInt(getData.getString("curvaluex"));
            int curvaluey=Integer.parseInt(getData.getString("curvaluey"));
            String runnable=getData.getString("Dice1Running");
            if(runnable.equals(("p1")))
            {
                dice_picture.setEnabled(true);
                dice_picture2.setEnabled(false);
            }
            else
            {
                dice_picture.setEnabled(false);
                dice_picture2.setEnabled(true);
            }

            rolling = false;

            switch (curvaluex) {
                case 1:
                    dice_picture.setImageResource(R.drawable.dice1);
                    //dice_picture2.setImageResource(R.drawable.dice1);
                    //diceRollVaue.setText("1");
                    break;
                case 2:
                    dice_picture.setImageResource(R.drawable.dice2);
                    //diceRollVaue.setText("2");
                    break;
                case 3:
                    dice_picture.setImageResource(R.drawable.dice3);
                    // diceRollVaue.setText("3");
                    break;
                case 4:
                    dice_picture.setImageResource(R.drawable.dice4);
                    //diceRollVaue.setText("4");
                    break;
                case 5:
                    dice_picture.setImageResource(R.drawable.dice5);
                    //diceRollVaue.setText("5");
                    break;
                case 6:
                    dice_picture.setImageResource(R.drawable.dice6);
                    //diceRollVaue.setText("6");
                    break;
                default:
            }


            switch (curvaluey) {

                case 1:
                    dice_picture2.setImageResource(R.drawable.dice1);
                    //dice_picture2.setImageResource(R.drawable.dice1);
                    //diceRollVaue.setText("1");
                    break;
                case 2:
                    dice_picture2.setImageResource(R.drawable.dice2);
                    // diceRollVaue.setText("2");
                    break;
                case 3:
                    dice_picture2.setImageResource(R.drawable.dice3);
                    // diceRollVaue.setText("3");
                    break;
                case 4:
                    dice_picture2.setImageResource(R.drawable.dice4);
                    // diceRollVaue.setText("4");
                    break;
                case 5:
                    dice_picture2.setImageResource(R.drawable.dice5);
                    //diceRollVaue.setText("5");
                    break;
                case 6:
                    dice_picture2.setImageResource(R.drawable.dice6);
                    //diceRollVaue.setText("6");
                    break;
                default:
            }


        }
       // message = intent.getStringExtra(InitialActivity.EXTRA_MESSAGE);


    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "game Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://my.santh.dicegame.dice50/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "game Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://my.santh.dicegame.dice50/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
    public void HandleClick(View arg0) {

        if (!rolling &&  sump1<50 && sump2<50)

        {
            rolling = true;
            //Show rolling image
if(dice_picture2.isEnabled())

            dice_picture2.setImageResource(R.drawable.blue);
            else
    dice_picture.setImageResource(R.drawable.blue);

            //Start rolling sound
            //dice_sound.play(sound_id, 1.0f, 1.0f, 0, 0, 1.0f);
            mdiceSound.start();
            //Pause to allow image to update
            timer.schedule(new Roll(), 400);

        }

    }
    //When pause completed message sent to callback
    class Roll extends TimerTask {
        public void run() {
            handler.sendEmptyMessage(0);
        }
    }

    Handler.Callback callback = new Handler.Callback() {

        public boolean handleMessage(Message msg) {


            //Get roll result
            //Remember nextInt returns 0 to 5 for argument of 6
            //hence + 1


            if(dice_picture.isEnabled()) {

                x = rng.nextInt(6) + 1;

                if(x==previousy)
                {
                    sump2=sump2-x;
                    mdiceLose.start();
                    //diceLose.play(sound_lose, 1.0f, 1.0f, 0, 0, 1.0f);
                }

                switch (x) {
                    case 1:
                        dice_picture.setImageResource(R.drawable.dice1);
                        //dice_picture2.setImageResource(R.drawable.dice1);
                        //diceRollVaue.setText("1");
                        break;
                    case 2:
                        dice_picture.setImageResource(R.drawable.dice2);
                        //diceRollVaue.setText("2");
                        break;
                    case 3:
                        dice_picture.setImageResource(R.drawable.dice3);
                        // diceRollVaue.setText("3");
                        break;
                    case 4:
                        dice_picture.setImageResource(R.drawable.dice4);
                        //diceRollVaue.setText("4");
                        break;
                    case 5:
                        dice_picture.setImageResource(R.drawable.dice5);
                        //diceRollVaue.setText("5");
                        break;
                    case 6:
                        dice_picture.setImageResource(R.drawable.dice6);
                        //diceRollVaue.setText("6");
                        break;
                    default:
                }
                diceRollVauep1.setText("I got " + x);
                previousx=x;

                if(x==6)
                {
                    dice_picture2.setEnabled(false);
                    dice_picture.setEnabled(true);
                    //diceBonus.play(sound_bonus, 1.0f, 1.0f, 0, 0, 1.0f);
                    mdiceWin.start();
                    Player1name.setText("BONUS !"+player1+" to play");
                }
                else
                {
                    dice_picture2.setEnabled(true);
                    dice_picture.setEnabled(false);
                    Player1name.setText(player2 +" to play");
                }
                sump1=sump1+x;

                rolling = false;
            }
            else {

                y = rng.nextInt(6) + 1;
                if(y==previousx)
                {
                    sump1=sump1-y;
                    mdiceLose.start();
                    //diceLose.play(sound_lose, 1.0f, 1.0f, 0, 0, 1.0f);
                }

                switch (y) {

                    case 1:
                        dice_picture2.setImageResource(R.drawable.dice1);
                        //dice_picture2.setImageResource(R.drawable.dice1);
                        //diceRollVaue.setText("1");
                        break;
                    case 2:
                        dice_picture2.setImageResource(R.drawable.dice2);
                        // diceRollVaue.setText("2");
                        break;
                    case 3:
                        dice_picture2.setImageResource(R.drawable.dice3);
                        // diceRollVaue.setText("3");
                        break;
                    case 4:
                        dice_picture2.setImageResource(R.drawable.dice4);
                        // diceRollVaue.setText("4");
                        break;
                    case 5:
                        dice_picture2.setImageResource(R.drawable.dice5);
                        //diceRollVaue.setText("5");
                        break;
                    case 6:
                        dice_picture2.setImageResource(R.drawable.dice6);
                        //diceRollVaue.setText("6");
                        break;
                    default:
                }
                diceRollvaluep2.setText("I got " + y);
                previousy=y;
                if(y==6)
                {
                    dice_picture.setEnabled(false);
                    dice_picture2.setEnabled(true);
                    mdiceWin.start();
                            //diceBonus.play(sound_bonus,1.0f,1.0f,0,0,1.0f);




                        Player1name.setText("BONUS !"+player2+" to play");
                    }
                    else
                {
                    dice_picture.setEnabled(true);
                    dice_picture2.setEnabled(false);
                    Player1name.setText(player1 + " to play ");
                }
                sump2=sump2+y;


                // int z = x + y;
                //diceRollVaue.setText("" + z);
                rolling = false;


            }

            p1Score.setText(""+sump1);
            p2Score.setText(""+sump2);

            if(sump1>=50||sump2>=50)
            {
                final AlertDialog.Builder alert = new AlertDialog.Builder(game.this);
                LinearLayout layout = new LinearLayout(game.this);
                layout.setOrientation(LinearLayout.VERTICAL);
                String winner;
                if(sump1>sump2)
                    winner=player1;
                else
                winner=player2;
                alert.setTitle("Congratulations "+winner+" , You Won");

                final   TextView descriptionBox=new TextView(game.this);
               // final TextView descriptionBox = new Textview(game.this);
                descriptionBox.setTextSize(20);
                descriptionBox.setHint( player1+" Score ="+sump1+player2 +" Score ="+sump2);
                layout.addView(descriptionBox);

                alert.setView(layout);
mdiceWin.start();

                alert.setPositiveButton("Reset", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {

                        sump1=0;
                        sump2=0;
                        p1Score.setText("0");
                        p2Score.setText("0");
                        diceRollVauep1.setText("");
                        diceRollvaluep2.setText("");
                        dice_picture.setImageResource(R.drawable.blue);
                        dice_picture2.setImageResource(R.drawable.blue);
                    }

                });
                alert.setNegativeButton("Play Again", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //So sth here when "cancel" clicked.
                        intentMain = new Intent(game.this, MainActivity.class);
                        startActivity(intentMain);
                    }
                });
                alert.create().show();

            }


                return true;

        }

    };

    protected void onPause() {
        super.onPause();
        senSensorManager.unregisterListener(this);
        //dice_sound.pause(sound_id);
        //diceBonus.pause(sound_bonus);
        //diceLose.pause(sound_lose);

       //mdiceSound.pause();

        //mdiceLose.pause();
        //mdiceWin.pause();
        //System.out.println("On Pause");
        // onreturn();
    }

    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
    protected void onResume() {
        super.onResume();

        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        //System.out.println("On Resume");
        //Intent intent = getIntent();



        //String message = intent.getStringExtra(InitialActivity.EXTRA_MESSAGE);

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
                Bundle gamedata=new Bundle();

                gamedata.putString("score of player1",p1Score.getText().toString());
                gamedata.putString("score of player2",p2Score.getText().toString());
                gamedata.putString("current p1 score",diceRollVauep1.getText().toString());
                gamedata.putString("current p2 score",diceRollvaluep2.getText().toString());
                gamedata.putString("player1name",player1);
                gamedata.putString("player2name",player2);
                gamedata.putString("currentplayer",Player1name.getText().toString());
                gamedata.putString("previousx",String.valueOf(previousx));
                gamedata.putString("previousy",String.valueOf(previousy));
                if(x==0&&y==0){
                    x=previousx;y=previousy;}
                gamedata.putString("curvaluex",String.valueOf(x));
                gamedata.putString("curvaluey",String.valueOf(y));

                if(dice_picture.isEnabled())
                {
                    gamedata.putString("Dice1Running","p1");
                }
                else
                {
                    gamedata.putString("Dice1Running","p2");
                }
                String gameContents=p1Score.getText().toString()+"@"+p2Score.getText().toString()+"@"+diceRollVauep1.getText().toString()+"@"+diceRollvaluep2.getText().toString()
                        +"@"+player1.toString()+"@"+player2.toString();
                Intent infoIntent = new Intent(this, AppInfo.class);
                infoIntent.putExtras( gamedata);
                infoIntent.putExtra("Dice", "game");
                startActivity(infoIntent);

                break;
        }return super.onOptionsItemSelected(item);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public void onBackPressed() {

        // moveTaskToBack(true);
        super.onBackPressed();
    }


}
