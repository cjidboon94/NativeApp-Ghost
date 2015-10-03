package nl.mprog.Ghost;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by tafgh on 10/3/15.
 */
public class DBHelper extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "test_db";

    public static final String TABLE_NAME = "users";
    public static final String KEY_ID = "_id";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_SCORE = "score";
    public static final String KEY_NO_OF_GAMES = "# of games";

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

    public int insert (){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues vals = new ContentValues();
        //vals.put(key, val)

        long errorCode = db.insert(TABLE_NAME, null, vals);
        db.close();
        return (int) errorCode;
    }

    public void update (){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues vals = new ContentValues();
        //vals.put(key, val);

        //db.update(TABLE_NAME, vals, KEY_ID + "=?", new String[] {ID})
        db.close();
    }

    public void delete (){
        SQLiteDatabase db = getWritableDatabase();
        //db.delete(TABLE_NAME, KEY_ID + "=?", new String[] {ID})
        db.close();
    }

    public void clear(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(("DROP TABLE IF EXISTS " + TABLE_NAME));
        onCreate(db);
    }
}
