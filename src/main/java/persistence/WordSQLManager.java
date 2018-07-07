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
    private String tableWord = "wort";
    private String tableCategory = "wortkategorie";
    private String tableConnection = "wort_wortkategorie";


    public WordSQLManager() throws Exception {
        ConnectionManager connectionManager = new ConnectionManager();
        connection = connectionManager.getConnection();
    }

    public List<BuzzwordCategory> readAllCategorys() throws SQLException {
        List<BuzzwordCategory> buzzwordCategories = new ArrayList<>();
        preStatement = connection.prepareStatement("SELECT * FROM " + tableCategory +";");
        resultSet = preStatement.executeQuery();
        while (resultSet.next()) {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM " + tableWord +";");
            ResultSet rs = ps.executeQuery();
            LinkedList<Buzzword> buzzwords = new LinkedList<Buzzword>();
            while (rs.next()) {
                Buzzword buzzword = new Buzzword(rs.getString(0));
                //TODO: hier noch Abfrage ob zu Kategorie gehört
                buzzwords.add(buzzword);
            }

            BuzzwordCategory p = new BuzzwordCategory(
                    resultSet.getString(0),
                    buzzwords
            );

        }
        return buzzwordCategories;
    }

    public BuzzwordCategory readBuzzwordCategory(int name) throws SQLException {
        return null;
    }

    public void createBuzzword(Buzzword buzzword, BuzzwordCategory buzzwordCategory) throws SQLException {

    }

    public Boolean deleteBuzzword(Buzzword buzzword, BuzzwordCategory buzzwordCategory) throws Exception {
        return null;
    }
}
