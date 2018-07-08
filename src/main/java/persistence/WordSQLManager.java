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

public class WordSQLManager {

    private Connection connection;
    private PreparedStatement preStatement;
    private ResultSet resultSet;
    private String table = "wort";


    public WordSQLManager() throws Exception {
        ConnectionManager connectionManager = new ConnectionManager();
        connection = connectionManager.getConnection();
    }

    public List<BuzzwordCategory> readAllCategories() throws SQLException {
        List<BuzzwordCategory> buzzwordCategories = new ArrayList<>();
        preStatement = connection.prepareStatement("SELECT * FROM " + table + ";");
        resultSet = preStatement.executeQuery();
        while (resultSet.next()) {
            //Gucken ob Buzzwordkategorie schon existiert, ansonsten erstellen
            int counter = 0;
            for (BuzzwordCategory bc : buzzwordCategories) {
                if (resultSet.getString(1) != bc.getName())
                    counter++;
                if (counter == buzzwordCategories.size()) {
                    buzzwordCategories.add(new BuzzwordCategory(resultSet.getString(1), new LinkedList<Buzzword>()));
                }
            }
//            Buzzword in die Kategorie laden
            for (BuzzwordCategory bc : buzzwordCategories) {
                if (resultSet.getString(1) != bc.getName()){
                    bc.addWord(new Buzzword(resultSet.getString(0)));
                }
            }
        }
        return buzzwordCategories;
    }

    public BuzzwordCategory readBuzzwordCategory(int name) throws SQLException {
        BuzzwordCategory bc = null;
        preStatement = connection.prepareStatement("SELECT * FROM " + table + ";");
        resultSet = preStatement.executeQuery();
        while (resultSet.next()) {
            bc = new BuzzwordCategory(resultSet.getString(1), new LinkedList<Buzzword>());
            if (resultSet.getString(1) != bc.getName()) {
                bc.addWord(new Buzzword(resultSet.getString(0)));
            }
        }
        return  bc;
    }

    public void createBuzzword(Buzzword buzzword, BuzzwordCategory buzzwordCategory) throws SQLException {
        preStatement = connection.prepareStatement("INSERT INTO " + table + "(Name,Kategorie) VALUES(\'" + buzzword.getBuzzword() + "\',\'" + buzzwordCategory.getName() + "\');");
        preStatement.executeUpdate();
    }

    public void deleteBuzzword(Buzzword buzzword, BuzzwordCategory buzzwordCategory) throws Exception {
        preStatement = connection.prepareStatement("DELETE FROM " + table + " WHERE Name=\"" + buzzword.getBuzzword() +"\";");
        preStatement.executeUpdate();
    }
}
