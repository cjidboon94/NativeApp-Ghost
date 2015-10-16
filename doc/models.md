# Models

Contains an overview of the fields and the methods of the models

## Game
```
Fields:

private int turn
private Lexicon lexicon
private boolean playing
```
```
Methods:
public String guess(String guess)
public void reset()
public boolean turn()
public boolean ended()

public void started()
public boolean isStarted()
public void finished()

public getTurn()
public String getFilter()
```

## Lexicon
```
Fields: 

private HashSet<String> baseLex
private Hashset<String> filteredLex 
private String filter
```
```
Methods:

public String filter(String guess)
public int count()
public void reset()
public String getFilter()
```
## User
```
Fields:

public final int id
public final String name
public int score
public int game
```