package services;

import repository.BookRepository;

import objectClasses.Book;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookService {

    private final BookRepository bookRepository = new BookRepository();

    public List<Book> fetchAllBooks() {

        List<Book> booksList = new ArrayList<>();

        try {
            List<Book> list = bookRepository.getBooks();
            booksList.addAll(list);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return booksList;
    }

    public Book fetchBookById(long id) {
        try {
            return bookRepository.getBookByID(id);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteBookFromDB(long id) {
        try {
            bookRepository.deleteBookByID(id);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void addBookToDB(Book book) {
        bookRepository.addBookToBD(book);
    }

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
