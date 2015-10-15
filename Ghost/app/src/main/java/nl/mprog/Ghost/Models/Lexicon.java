package nl.mprog.Ghost.Models;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;



/**
 * Cornelis Boon - 10561145
 * Model Class of the Lexicon.
 * Contains two HashSets which are used to keep track of which words are still possible to play.
 * One HashSet (baseLex) is used to quickly reset the Lexicon,
 * the other one(filteredLex) is actively updated.
 */
public class Lexicon extends Object {

    private HashSet<String> baseLex;
    private HashSet<String> filteredLex;
    private String filter;
    private static final String TAG = "Lexicon:";


    /** Main Constructor */
    public Lexicon(Context context, String sourcePath) {
        try {
            Scanner scanner = new Scanner(context.getAssets().open(sourcePath));
            baseLex = new HashSet<>();
            while (scanner.hasNext()) {
                String line = scanner.next();
                baseLex.add(line);
            }
            scanner.close();
            Log.d(TAG, "BaseLexicon is done");
            filteredLex = new HashSet<>(baseLex);
            Log.d(TAG, "filteredLexicon is done too");
            filter = "";
        } catch(IOException e){
            Log.d(TAG, "Something went wrong", e);
            e.printStackTrace();
        }
    }

    /**
     * Filters the lexicon based on the filter.
     *
     * If the filter is longer than 3 character and matches one of the words in the lexicon,
     * returns the matched word. Else null.
     * */
    public String filter(String guess){
        filter = filter + guess;
        for (Iterator<String> it  = filteredLex.iterator(); it.hasNext();){
            String word = it.next();
            if((filter.length() > 3) && word.equals(filter)){
                return word;
            }
            if(! word.startsWith(filter)){
                it.remove();
            }
        }
        return null;
    }

    public int count(){
        return this.filteredLex.size();
    }

    public void reset(){
        filteredLex = new HashSet<>(baseLex);
        filter = "";
    }

    public String getFilter() {
        return filter;
    }
}
