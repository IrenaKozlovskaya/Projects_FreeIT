package repository;

import objectClasses.Author;
import config.DataSourceUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorRepository {

    DataSourceUtil dataSourceUtil = new DataSourceUtil();

    public List<Author> getAuthors() throws SQLException {

        ResultSet resultSet = null;

        String sql = "SELECT * FROM  authors";
        try {
            PreparedStatement statement = dataSourceUtil.getConnection().prepareStatement(sql);
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assert resultSet != null;
        return createAuthors(resultSet);
    }

    public Author getAuthorByName(String name) throws SQLException {

        ResultSet resultSet = null;

        String sql = "SELECT id, author FROM authors WHERE author = ?";
        try {
            PreparedStatement statement = dataSourceUtil.getConnection().prepareStatement(sql);
            statement.setString(1, name);
            resultSet = statement.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        assert resultSet != null;
        return createAuthors(resultSet).get(0);
    }

    private List<Author> createAuthors(ResultSet resultSet) throws SQLException {
        List<Author> authorList = new ArrayList<>();

        while (resultSet.next()) {
            Author author = new Author();
            author.setId(resultSet.getLong(1));
            author.setName(resultSet.getString(2));
            authorList.add(author);
        }

        dataSourceUtil.disconnect();

        return authorList;

    }

    public void addAuthorToBD(String author) {

        String sql = "INSERT INTO authors( author) VALUES (?)";
        try {
            PreparedStatement statement = dataSourceUtil.getConnection().prepareStatement(sql);

            statement.setString(1, author);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public long getAuthorId(String author_name) {

        List<Author> authors = null;
        try {
            authors = getAuthors();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        long author_id = 0;
        assert authors != null;
        for (Author author : authors) {
            if (author.getName().equals(author_name)) {
                author_id = author.getId();
                break;
            }
        }
        return author_id;
    }


}
