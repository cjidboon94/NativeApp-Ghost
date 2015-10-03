package nl.mprog.Ghost;

/**
 * Created by tafgh on 10/3/15.
 */
public class Game {

    private Lexicon lexicon;
    boolean turn;

    public Game(Lexicon lexicon){
        this.lexicon = lexicon;
    }

    public void guess(String guess){
        lexicon.filter(guess);
    }

    public boolean wordMade(){
        return lexicon.result().equals(lexicon.getFilter());
    }

    public void reset() { lexicon.reset(); }
    public boolean turn() { turn = !turn; return turn; }
    public boolean ended(){ return (lexicon.count() == 1); }
    public boolean winner(){
        return wordMade() ?  !turn : turn;
    }
}
