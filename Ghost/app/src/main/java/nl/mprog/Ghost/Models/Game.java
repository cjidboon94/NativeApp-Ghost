package nl.mprog.Ghost.Models;

/**
 * Cornelis Boon - 10561145
 *
 * Model Class for the Game. Provides basic functionality for the Ghost game such as:
 * - Checking if the game is still being played.
 * - An interface with the lexicon to check if a word hasn't been made.
 * - Switching of turns
 * - Resetting the game.
 */
public class Game extends Object {

    private Lexicon lexicon;
    private boolean turn;
    private boolean playing;

    /**
     * Main constructor
     */
    public Game(Lexicon lexicon){
        this.lexicon = lexicon;
        this.playing = false;
        this.turn = true;
    }

    /**
     * Filters the lexicon based on the previous filter + the new character.
     * Returns null if the current filter does not match a word, else returns the word it matched.
     */
    public String guess(String guess){
        return lexicon.filter(guess);
    }

    public void reset() { lexicon.reset(); started(); }
    public boolean turn() { this.turn = !this.turn; return this.turn; }
    public boolean ended(){ return (lexicon.count() <= 1); }


    public boolean getTurn(){ return this.turn; }

    //Setters & Getters to .
    public void started(){
        this.playing = true;
    }
    public boolean isStarted(){
        return this.playing;
    }

    public void finished(){
        this.playing = false;
    }

    public String getFilter(){
        return lexicon.getFilter();
    }
}
