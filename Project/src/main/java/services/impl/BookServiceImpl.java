package services.impl;


import objectClasses.Book;
import repository.impl.BookRepositoryImpl;
import services.BookService;

import java.sql.SQLException;
import java.util.List;

public class BookServiceImpl implements BookService {

    private final BookRepositoryImpl bookRepository = new BookRepositoryImpl();

    @Override
    public List<Book> fetchAllBooks() {

        try {
            return bookRepository.getBooks();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Book fetchBookById(long id) {
        try {
            return bookRepository.getBookByID(id);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteBookFromDB(long id) {
        try {
            bookRepository.deleteBookByID(id);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addBookToDB(Book book) {
        try {
            bookRepository.addBookToBD(book);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void editBookFromDB(Book book) {
        try {
            bookRepository.editBookByID(book);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public long getID(String title) {
        return bookRepository.getBookId(title);
    }
}
