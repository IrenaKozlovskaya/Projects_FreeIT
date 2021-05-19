package services;

import objectClasses.Author;
import repository.AuthorRepository;

import java.sql.SQLException;


public class AuthorService {

    AuthorRepository authorRepository = new AuthorRepository();

    public Author fetchAuthorByName(String name) {
        try {
            return authorRepository.getAuthorByName(name);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addAuthorToDB(String author) {
        authorRepository.addAuthorToBD(author);
    }

    public long getID(String author_name) {
        return authorRepository.getAuthorId(author_name);
    }

}
