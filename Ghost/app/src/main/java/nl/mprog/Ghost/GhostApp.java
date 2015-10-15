package nl.mprog.Ghost;

import android.app.Application;

import nl.mprog.Ghost.Models.Game;

/**
 * Cornelis Boon - 10561145
 * Application Class that keeps track of several variables of the game:
 * - The names of the players
 * - The game instance
 * - The language
 */
public class GhostApp extends Application {

    private Game game;

    public static final String LANGUAGE_EN = "en";
    public static final String LANGUAGE_NL = "nl";

    private String language = LANGUAGE_EN;
    private String player1;
    private String player2;

    /** Setter and Getter of the Game instance */
    public void setGame(Game game){
        this.game = game;
    }
    public Game getGame() { return this.game; }

    /** Setters & Getters of player names. */
    public void setPlayer1(String name){ this.player1 = name; }
    public String getPlayer1(){ return this.player1; }

    public void setPlayer2(String name){ this.player2 = name; }
    public String getPlayer2(){ return this.player2; }

    /** Setter and Getter of the current language*/
    public void setLanguage(String language){
        this.language = language;
    }
    public String getLanguage(){
        return language;
    }

}
