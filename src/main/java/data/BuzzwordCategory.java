package data;

import java.util.LinkedList;

public class BuzzwordCategory {

    private String name;
    private LinkedList<Buzzword> buzzwords;

    public String getName() {
        return name;
    }

    public void addWord(String word){
        buzzwords.add(new Buzzword(word));
    }

    public void removeWord(Buzzword buzzword){
        buzzwords.remove(buzzword);
    }

    public LinkedList<Buzzword> getBuzzwords() {
        return buzzwords;
    }

}
