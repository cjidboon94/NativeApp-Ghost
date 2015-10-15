package nl.mprog.Ghost;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import nl.mprog.Ghost.Database.DBHelper;
import nl.mprog.Ghost.Models.Game;
import nl.mprog.Ghost.Models.User;
/**
 * Cornelis Boon - 10561145
 *
 * Controller class controlling the Game Activity
 * Implements the main loop of the Game amongst other things.
 * */

public class GameActivity extends Activity {

    private Game game;
    private GhostApp app;
    private DBHelper db;

    TextView word, player1, player2;
    EditText input;

    /**
     * Gets all references and sets up the values as needed */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        app = (GhostApp)this.getApplication();
        game = app.getGame();
        db = new DBHelper(this);

        word = (TextView) findViewById(R.id.wordView);
        if(!game.isStarted()) {
            game.started();
        }
        word.setText(game.getFilter());

        player1 = (TextView) findViewById(R.id.player1);
        player2 = (TextView) findViewById(R.id.player2);
        setTextTurn(game.getTurn());

        input = (EditText)findViewById(R.id.gameInput);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
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

    public void play(View view){
        String guess = input.getText().toString();
        String madeWord = game.guess(guess);

        word.setText(word.getText().toString() + guess);

        if(madeWord != null || game.ended()){
            endGame(madeWord);
        } else {
            input.setText("");
            setTextTurn(game.turn());
        }
    }

    private void setTextTurn(boolean turn) {
        if (turn) {
            player1.setText(app.getPlayer1() + " <-");
            player2.setText(app.getPlayer2());
        } else {
            player1.setText(app.getPlayer1());
            player2.setText("-> " + app.getPlayer2());
        }
    }

    /**
     * Shows a toast saying who has won and why and stops the game */
    private void endGame(String madeWord){
        String winner;
        String loser;
        String message;

        if(game.getTurn()){
            loser = app.getPlayer1();
            winner = app.getPlayer2();
        } else {
            loser = app.getPlayer2();
            winner = app.getPlayer1();
        }
        if(madeWord != null) {
            message = madeWord + " is an actual word, " + loser + ". " + winner + " wins!";
        } else {
            message = "No words are left, " + loser + ". " + winner + " wins!";
        }
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        stopGame(winner, loser);
    }

    /**
     * Disables the confirm button and input field from being used and updates the scores of the users*/
    private void stopGame(String winner, String loser){
        game.finished();
        input.setInputType(InputType.TYPE_NULL);

        Button confirm = (Button)findViewById(R.id.confirm);
        confirm.setClickable(false);

        upsertAtEndOfGame(winner, true);
        upsertAtEndOfGame(loser, false);

        Toast.makeText(this, "Scores saved", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "You can now return to the Main Menu or play another game", Toast.LENGTH_LONG).show();
    }

    /**
     * Updates or inserts (upserts) the result per player */
    public void upsertAtEndOfGame(String name, boolean won){
        User player = db.exists(name);
        if(player == null) {
            player = new User(name);
        }
        if(won) {
            player.score++;
        }
        player.games++;
        if(player.id == -1){
            db.insert(player);
        } else {
            db.update(player);
        }
    }

    /**
     * Saves the current state of the game and returns to MainActivity */
    public void onClickHome(View view){
        app.setGame(this.game);
        finish();
    }

    /**
     * Resets the game */
    public void onClickReset(View view){
        game.reset();
        word.setText(game.getFilter());
        onCreate(null);
    }

    public void onClickOptions(View view){
        Intent intent = new Intent(this, OptionsActivity.class);
        startActivity(intent);
    }

}
