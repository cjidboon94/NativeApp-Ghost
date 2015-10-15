package nl.mprog.Ghost;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import nl.mprog.Ghost.Database.DBHelper;


/**
 * Cornelis Boon - 10561145
 * Controller class for the score activity.
 * Displays the highscores of players sorted on their scores
 * */
public class ScoreActivity extends Activity {

    /**
     * Retrieves the scores and names of players and shows them in the listview */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        DBHelper db = new DBHelper(this);
        ArrayList<String> list = db.readAll();
        list.add(0, "Name\t|\tScore");
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                                            R.layout.score_entry, R.id.scoreLine, list);
        ListView listview = (ListView)findViewById(R.id.scores);
        listview.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_score, menu);
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

    public void returnToHome(View view){
        finish();
    }
}
