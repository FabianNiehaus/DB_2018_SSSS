package domain;

import data.Buzzword;
import data.BuzzwordCategory;
import exceptions.IDNotFoundException;
import persistence.WordSQLManager;

import java.util.LinkedList;

public class BuzzwordCategoryManagement {

    WordSQLManager wordSQLManager;

    public LinkedList<String> getBuzzwordCategorieNames() {
        LinkedList<String> categoryNames = new LinkedList<>();
        for(BuzzwordCategory buzzwordCategory : buzzwordCategories){
            categoryNames.add(buzzwordCategory.getName());
        }
        return  categoryNames;
    }

    private LinkedList<BuzzwordCategory> buzzwordCategories = new LinkedList<>();

    public BuzzwordCategoryManagement() {

        /*LinkedList<Buzzword> buzzwords = new LinkedList<>();
        for(int i = 1; i < 25; i++){
            buzzwords.add(new Buzzword("TEST BUZZWORD " + String.valueOf(i)));
        }

        buzzwordCategories.add(new BuzzwordCategory("Test", buzzwords));*/

        try {
            wordSQLManager = new WordSQLManager();
            buzzwordCategories = new LinkedList<>(wordSQLManager.readAllCategories());

            for(BuzzwordCategory buzzwordCategory : buzzwordCategories){
                System.out.println(buzzwordCategory.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
