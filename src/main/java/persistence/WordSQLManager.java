package persistence;

import data.Buzzword;
import data.BuzzwordCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class WordSQLManager {

    private Connection connection;
    private PreparedStatement distinctCategoriesPreStatement;
    private ResultSet distinctCategoriesResultSet;
    private String table = "wort";


    public WordSQLManager() throws Exception {
        ConnectionManager connectionManager = new ConnectionManager();
        connection = connectionManager.getConnection();
    }

    public List<BuzzwordCategory> readAllCategories() throws SQLException {
        List<BuzzwordCategory> buzzwordCategories = new ArrayList<>();
        distinctCategoriesPreStatement = connection.prepareStatement("SELECT DISTINCT Kategorie FROM " + table + ";");
        distinctCategoriesResultSet = distinctCategoriesPreStatement.executeQuery();

        while (distinctCategoriesResultSet.next()) {
            //Gucken ob Buzzwordkategorie schon existiert, ansonsten erstellen
            boolean categoryExists = false;

            try{
                String categoryName = distinctCategoriesResultSet.getString(1);

                for (BuzzwordCategory bc : buzzwordCategories) {
                    if (categoryName.equals(bc.getName()))
                        categoryExists = true;
                }
                if (!categoryExists) {
                    buzzwordCategories.add(new BuzzwordCategory(categoryName, new LinkedList<Buzzword>()));
                }
            } catch (SQLException e){
                e.getNextException();
            }
        }

        for(BuzzwordCategory buzzwordCategory : buzzwordCategories){
            PreparedStatement singleCategoryPreStatement = connection.prepareStatement("SELECT Name FROM " + table + " WHERE Kategorie=\'" + buzzwordCategory.getName() + "\';");
            ResultSet singleCategoryResultSet = singleCategoryPreStatement.executeQuery();
            while (singleCategoryResultSet.next()) {
                buzzwordCategory.addWord(new Buzzword(singleCategoryResultSet.getString(1)));
            }


        }

        return buzzwordCategories;
    }

    public BuzzwordCategory readBuzzwordCategory(String name) throws SQLException {
        BuzzwordCategory bc = null;
        distinctCategoriesPreStatement = connection.prepareStatement("SELECT * FROM " + table + ";");
        distinctCategoriesResultSet = distinctCategoriesPreStatement.executeQuery();
        while (distinctCategoriesResultSet.next()) {
            bc = new BuzzwordCategory(distinctCategoriesResultSet.getString(1), new LinkedList<Buzzword>());
            if (!Objects.equals(name, bc.getName())) {
                bc.addWord(new Buzzword(distinctCategoriesResultSet.getString(0)));
            }
        }
        return  bc;
    }

    public void createBuzzword(Buzzword buzzword, BuzzwordCategory buzzwordCategory) throws SQLException {
        distinctCategoriesPreStatement = connection.prepareStatement("INSERT INTO " + table + "(Name,Kategorie) VALUES(\'" + buzzword.getBuzzword() + "\',\'" + buzzwordCategory.getName() + "\');");
        distinctCategoriesPreStatement.executeUpdate();
    }

    public void deleteBuzzword(Buzzword buzzword, BuzzwordCategory buzzwordCategory) throws Exception {
        distinctCategoriesPreStatement = connection.prepareStatement("DELETE FROM " + table + " WHERE Name=\"" + buzzword.getBuzzword() +"\";");
        distinctCategoriesPreStatement.executeUpdate();
    }
}
