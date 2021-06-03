package services;

import objectClasses.Book;

import java.util.List;

public interface BookService {

    List<Book> fetchAllBooks();

    Book fetchBookById(long id);

    void deleteBookFromDB(long id);

    void addBookToDB(Book book);

    void editBookFromDB(Book book);


}
