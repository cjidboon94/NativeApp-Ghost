package nl.mprog.Ghost.Models;

/**
 * Cornelis Boon - 10561145
 * Model Class of the User.
 * This model is more of a struct than a class.
 * It is used to keep data from the database about the players in one object.
 */
public class User {
    public final int id;
    public final String name;
    public int score;
    public int games;

    /** Constructor in case that user does not already exist in the database. */
    public User(String name){
        this.id = -1;
        this.name = name;
        this.score = 0;
        this.games = 0;
    }

    /** Main constructor */
    public User(int id, String name, int score, int games){
        this.id = id;
        this.name = name;
        this.score = score;
        this.games = games;
    }
}
