package services;

import objectClasses.Genre;
import repository.GenreRepository;

import java.sql.SQLException;


/**
 * Отсюда должны вызываться методы классов репозиториев
 */

public class GenreService {

    private final GenreRepository genreRepository = new GenreRepository();


    public Genre fetchGenreByName(String name) {
        try {
            return genreRepository.getGenreByName(name);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void addGenreToDB(String genre) {
        genreRepository.addGenreToBD(genre);
    }

    public long getID(String genre_name) {
        return genreRepository.getGenreId(genre_name);
    }
}
