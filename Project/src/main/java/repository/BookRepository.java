package repository;

import objectClasses.Book;

import java.sql.SQLException;
import java.util.List;

public interface BookRepository {

    List<Book> getBooks()throws SQLException;

    Book getBookByID(long id)throws SQLException;

    void addBookToBD(Book book)throws SQLException;

    void deleteBookByID(long id)throws SQLException;

    void editBookByID(Book book)throws SQLException;



}
