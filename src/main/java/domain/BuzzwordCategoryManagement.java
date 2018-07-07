package domain;

import data.Buzzword;
import data.BuzzwordCategory;
import exceptions.IDNotFoundException;

import java.util.LinkedList;

public class BuzzwordCategoryManagement {

    LinkedList<BuzzwordCategory> buzzwordCategories;


    public BuzzwordCategoryManagement() {
        this.buzzwordCategories = buzzwordCategories;
    }

    public BuzzwordCategory addBuzzwordCategory(String name){
        BuzzwordCategory newBuzzwordCategory = new BuzzwordCategory(name, new LinkedList<Buzzword>());

        buzzwordCategories.add(newBuzzwordCategory);

        boolean addedToPersistence = false;
        // TODO: Co-Routine sinnvoller? (ggf. SQL gerade nicht verfügbar)
        while(!addedToPersistence){
            writeSingleBuzzwordCategoryToPersistence(newBuzzwordCategory);
        }

        return newBuzzwordCategory;
    }

    public BuzzwordCategory getBuzzwordCategory(String categoryName) throws IDNotFoundException{
        for (BuzzwordCategory buzzwordCategory: buzzwordCategories)
        {
            if(buzzwordCategory.getName().equals(categoryName)) return buzzwordCategory;
        }

        throw new IDNotFoundException("Buzzword-Kategorie", categoryName);
    }

    public void addBuzzwordToCategory(String categoryName, Buzzword buzzword) throws IDNotFoundException {
        BuzzwordCategory buzzwordCategory = getBuzzwordCategory(categoryName);

        buzzwordCategory.addWord(buzzword);
    }

    private static LinkedList<BuzzwordCategory> loadCategories(){
        // TODO: Buzzwordkategorien laden
        return null;
    }

    private boolean writeSingleBuzzwordCategoryToPersistence(BuzzwordCategory buzzwordCategory){
        // TODO: Logik für Speicherung einer Buzzword-Kategorie in SQL
        return false;
    }
}
