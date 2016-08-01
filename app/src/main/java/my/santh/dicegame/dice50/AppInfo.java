package my.santh.dicegame.dice50;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class AppInfo extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "com.example.santh.MESSAGE";
    private String[] titles = new String[]{
            "Application Name",
            "Developer",
            "Overview",
            "Features"

    };

    private String[] values = new String[]{
            "Dice",
            "Santhosh Raj Ravirala",
            " A Simple two player  dice game whose outcome depends on pure probability .<br> " +
                    " The players who finishes 50 points earlier wins!.  " +
                    " Rule1:if the player get 6 points , he gets bonus roll. <br>" +
                    " Rule2.if the player rolls same dice of his opponent, the opponent loses same point.<br>"+
                    " At the end, I hope you can enjoy this little game. Good luck! :)",
            "1.The dices give virtual rolling effects with sound.<br>"+
            "2.Dice can also be rolled by shaking the device "/*,
            "1.<a href=\"http://code.tutsplus.com/tutorials/using-the-accelerometer-on-android--mobile-22125\">Sensor Information to record device shake </a><br>"+
                    "2.<a href=\"https://design.google.com/Google design\">Google design -Wallpaper</a>"*/


    };
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LinearLayout parent = (LinearLayout) findViewById(R.id.scrollParent);
        for (int i = 0; i < titles.length; i++) {
            //In addition to creating layouts through an XML file you can also create and add views
            //programmatically
            TextView title = new TextView(this);
            title.setText(Html.fromHtml("<b>" + titles[i] + "</b>"));
            title.setTextSize(16);
            parent.addView(title);

            TextView content = new TextView(this);
            //Converts the text to HTML
            content.setText(Html.fromHtml(values[i] + "<br>"));
            //Allows the user to follow any links in the text
            content.setMovementMethod(LinkMovementMethod.getInstance());
            parent.addView(content);
        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }



    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "AppInfo Page", // TODO: Define a title for the content shown.
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
                "AppInfo Page", // TODO: Define a title for the content shown.
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

   @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // System.out.println("back button clicked");
                Intent intent = getIntent();
                String strdata = intent.getExtras().getString("Dice");
                if (strdata.equals("main")) {
                    Intent intentMain = new Intent(this, MainActivity.class);
                    startActivity(intentMain);

                } else {
                    String message = intent.getStringExtra(game.EXTRA_MESSAGE);
                    Intent intentMain = new Intent(this, game.class);

                    Bundle getData = intent.getExtras();

                    // System.out.println(getData.getString("score of player1"));
                    Bundle bundlefrominfo = new Bundle();
                    String p1 = getData.getString("currentplayer");


                    String p1Sc = getData.getString("score of player1");

                    String p2sc = getData.getString("score of player2");

                    String p1na = getData.getString("player1name");

                    String p2na = getData.getString("player2name");

                    String p1cs = getData.getString("current p1 score");
                    //diceRollVauep1.setText(p1cs);
                    String p2cs = getData.getString("current p2 score");
                    //diceRollvaluep2.setText(p2cs);
                    bundlefrominfo.putString("score of player1", p1Sc);
                    bundlefrominfo.putString("score of player2", p2sc);
                    bundlefrominfo.putString("current p1 score", p1cs);
                    bundlefrominfo.putString("current p2 score", p2cs);
                    bundlefrominfo.putString("player1name", p1na);
                    bundlefrominfo.putString("player2name", p2na);
                    bundlefrominfo.putString("currentplayer", p1);
                    intentMain.putExtras(getData);
                    intentMain.putExtra("Dice", "Appinfo");
                    startActivity(intentMain);
                }
                    return true;
                    default:
                       return super.onOptionsItemSelected(item);
                }

            }

    @Override
    public void onBackPressed() {

       // moveTaskToBack(true);
        super.onBackPressed();
    }




}
