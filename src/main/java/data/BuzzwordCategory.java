package data;

import java.util.LinkedList;

public class BuzzwordCategory {

    private String name;

    public BuzzwordCategory(String name, LinkedList<Buzzword> buzzwords) {
        this.name = name;
        this.buzzwords = buzzwords;
    }

    private LinkedList<Buzzword> buzzwords;

    public String getName() {
        return name;
    }

    public void addWord(Buzzword buzzword){
        buzzwords.add(buzzword);
    }

    public void removeWord(Buzzword buzzword){
        buzzwords.remove(buzzword);
    }

    public LinkedList<Buzzword> getBuzzwords() {
        return buzzwords;
    }

}
