package nl.mprog.Ghost;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;



/**
 * Created by tafgh on 10/3/15.
 */
public class Lexicon extends HashSet {

    private HashSet<String> baseLex;
    public HashSet<String> filteredLex;
    private String filter;

    public Lexicon(String sourcePath) {
        Scanner scanner = new Scanner(sourcePath);
        baseLex = new HashSet<String>();
        while (scanner.hasNext()){
            String line = scanner.next();
            baseLex.add(line);
        }
        scanner.close();
        filteredLex = new HashSet<String>(baseLex);
        filter = "";
    }

    public void filter(String guess){
        filter = filter + guess;
        for (String word : filteredLex){
            if(! word.startsWith(guess)){
                filteredLex.remove(word);
            }
        }
    }

    public int count(){
        return this.size();
    }

    public String getFilter(){
        return filter;
    }

    public String result(){
        if(count() == 1){
            Iterator<String> words = filteredLex.iterator();
            return words.next();
        }
        return null;
    }

    public void reset(){
        filteredLex = new HashSet<String>(baseLex);
        filter = "";
    }
}
