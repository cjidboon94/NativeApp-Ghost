package nl.mprog.Ghost.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import nl.mprog.Ghost.Models.User;

/**
 * Cornelis Boon - 10561145
 *
 * Class that is used to store into and extract from a database about the users,
 * their scores and how many games they have played.
 */
public class DBHelper extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "test_db";

    public static final String TABLE_NAME = "users";
    public static final String KEY_ID = "_id";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_SCORE = "score";
    public static final String KEY_NO_OF_GAMES = "number_of_games";

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE " + TABLE_NAME + " ( " +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_USERNAME + " TEXT, " +
                KEY_SCORE + " INTEGER, " +
                KEY_NO_OF_GAMES + " INTEGER TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /** If a User exists in the DB, returns the User. Else null */
    public User exists(String name){
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_USERNAME + "=?";
        Cursor cursor = db.rawQuery(query, new String[] { name });
        User existingUser = null;
        if(cursor.moveToFirst()){
            existingUser = new User(cursor.getInt(cursor.getColumnIndex(KEY_ID)),
                    cursor.getString(cursor.getColumnIndex(KEY_USERNAME)),
                    cursor.getInt(cursor.getColumnIndex(KEY_SCORE)),
                    cursor.getInt(cursor.getColumnIndex(KEY_NO_OF_GAMES)));
        }
        cursor.close();
        db.close();
        return existingUser;
    }

    /** Inserts a new User into the database */
    public int insert (User user){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues vals = new ContentValues();
        vals.put(KEY_USERNAME, user.name);
        vals.put(KEY_NO_OF_GAMES, user.games);
        vals.put(KEY_SCORE, user.score);
        long errorCode = db.insert(TABLE_NAME, null, vals);
        db.close();
        return (int) errorCode;
    }

    /** Extracts all users from the db and returns an ArrayList of strings containing Names and Scores */
    public ArrayList<String> readAll(){
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT " + KEY_USERNAME + ", " + KEY_SCORE  +" FROM " + TABLE_NAME + " ORDER BY " + KEY_SCORE + " DESC;";
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<String> users = new ArrayList<>();
        if(cursor.moveToFirst()){
            do {
                String entry = cursor.getString(cursor.getColumnIndex(KEY_USERNAME)) + "\t|\t" +
                        cursor.getInt(cursor.getColumnIndex(KEY_SCORE));
                users.add(entry);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return users;
    }

    /** Updates an already existing user's score and games total */
    public void update (User user){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues vals = new ContentValues();
        vals.put(KEY_USERNAME, user.name);
        vals.put(KEY_SCORE, user.score);
        vals.put(KEY_NO_OF_GAMES, user.games);

        db.update(TABLE_NAME, vals, KEY_ID + "=?", new String[]{String.valueOf(user.id)});
        db.close();
    }

    public void delete (User user){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + "=?", new String[] {String.valueOf(user.id)});
        db.close();
    }

    public void clear(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(("DROP TABLE IF EXISTS " + TABLE_NAME));
        onCreate(db);
    }
}
