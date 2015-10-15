package nl.mprog.Ghost;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import nl.mprog.Ghost.Models.Game;
import nl.mprog.Ghost.Models.Lexicon;

/**
 * Cornelis Boon - 10561145
 * Controller Class for the Options Activity
 *
 * User can set the settings such as language and their names in this activity.
 * This class implements the functionality and responsiveness of the buttons of this activity.
 * */

public class OptionsActivity extends Activity {

    GhostApp app;
    EditText player1, player2;
    int checked;
    RadioGroup radio;
    boolean fromMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            fromMain = extras.getBoolean("fromMain", false);
        } else {
            fromMain = false;
        }

        radio = (RadioGroup)findViewById(R.id.radioGroupOpt);

        app = (GhostApp) getApplication();
        player1 = (EditText)findViewById(R.id.editPlayer1Opt);
        player2 = (EditText)findViewById(R.id.editPlayer2Opt);
        if(app.getLanguage().equals(app.LANGUAGE_EN)){
            checked = R.id.radioEnglish;
        } else {
            checked = R.id.radioDutch;
        }
        radio.check(checked);

        radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (!fromMain && checkedId != checked) {
                    Toast.makeText(OptionsActivity.this, "If you change the language, a new game will start.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        player1.setText(app.getPlayer1());
        player2.setText(app.getPlayer2());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void onClickCancel(View view){ finish(); }

    /**
     * Saves the (changed) values in the options into the application instance
     * and finishes.
     *
     * If this activity was accessed from MainActivity, then it starts GameActivity */
    public void onClickConfirm(View view) {
        if(radio.getCheckedRadioButtonId() != -1 ){
            String name1 = player1.getText().toString();
            String name2 = player2.getText().toString();
            if (app.getPlayer1() == null || !app.getPlayer1().equals(name1)) {
                app.setPlayer1(name1);
            }
            if (app.getPlayer2() == null || !app.getPlayer2().equals(name2)) {
                app.setPlayer2(name2);
            }

            if (fromMain || checked != radio.getCheckedRadioButtonId()) {
                Lexicon lexicon;
                if (radio.getCheckedRadioButtonId() == R.id.radioDutch) {
                    app.setLanguage(app.LANGUAGE_NL);
                    lexicon = new Lexicon(this, "dutch.txt");
                } else {
                    app.setLanguage(app.LANGUAGE_EN);
                    lexicon = new Lexicon(this, "english.txt");
                }
                app.setGame(new Game(lexicon));
            }
            if(fromMain) {
                Intent intent = new Intent(this, GameActivity.class);
                startActivity(intent);
            }
            finish();
        } else {
            Toast.makeText(this, "You need to choose a language.",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
