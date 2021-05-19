package repository;

import config.DataSourceUtil;
import objectClasses.Genre;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenreRepository {

    DataSourceUtil dataSourceUtil = new DataSourceUtil();

    public List<Genre> getGenres() throws SQLException {

        ResultSet resultSet = null;

        String sql = "SELECT * FROM  genres";
        try {
            PreparedStatement statement = dataSourceUtil.getConnection().prepareStatement(sql);
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assert resultSet != null;
        return createGenres(resultSet);
    }

    public Genre getGenreByName(String name) throws SQLException {

        ResultSet resultSet = null;

        String sql = "SELECT id, genre FROM genres WHERE genre = ?";
        try {
            PreparedStatement statement = dataSourceUtil.getConnection().prepareStatement(sql);
            statement.setString(1, name);
            resultSet = statement.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        assert resultSet != null;
        return createGenres(resultSet).get(0);
    }

    private List<Genre> createGenres(ResultSet resultSet) throws SQLException {
        List<Genre> genreList = new ArrayList<>();

        while (resultSet.next()) {
            Genre genre = new Genre();
            genre.setId(resultSet.getLong(1));
            genre.setName(resultSet.getString(2));

            genreList.add(genre);
        }

        dataSourceUtil.disconnect();

        return genreList;

    }

    public void addGenreToBD(String genre) {

        String sql = "INSERT INTO genres(genre) VALUES (?)";
        try {
            PreparedStatement statement = dataSourceUtil.getConnection().prepareStatement(sql);

            statement.setString(1, genre);


            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public long getGenreId(String genre_name) {

        List<Genre> genres = null;
        try {
            genres = getGenres();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        long genre_id = 0;

        assert genres != null;
        for (Genre i : genres) {
            if (i.getName().equals(genre_name)) {
                genre_id = i.getId();
                break;
            }
        }
        return genre_id;
    }
}
