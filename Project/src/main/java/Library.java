

import objectClasses.Book;

import java.util.ArrayList;
import java.util.List;

import readingFromXML.DOMParser;
import services.AuthorService;
import services.BookService;
import services.GenreService;

public class Library {


    BookService bookService = new BookService();
    AuthorService authorService = new AuthorService();
    GenreService genreService = new GenreService();

    public void addAllObjectsXMLToDB() {

        List<Book> book = DOMParser.readXML();

        for (Book book1 : book) {

            String author_name = book1.getAuthor().getName();
            String genre_name = book1.getGenre().getName();


            authorVerificationByID(book1, author_name);


            genreVerificationByID(book1, genre_name);

            bookService.addBookToDB(book1);
        }
    }


    public Book addBook(String title, String genre, String author, String ISBN) {
        for (Book book : getAllBooks()) {
            if (book.getTitle().equals(title)) {
                System.out.println("Книга с таким названием уже есть в библиотеке!");
                return bookService.fetchBookById(book.getId());
            }
        }
        Book book = buildBook(title, author, genre, ISBN);
        bookService.addBookToDB(book);
        return bookService.fetchBookById(bookService.getID(title));
    }

    private Book buildBook(String title, String genre, String author, String ISBN) {
        Book book = new Book();

        book.setTitle(title);
        book.setISBN(ISBN);

        genreVerificationByID(book, genre);
        authorVerificationByID(book, author);

        return book;
    }

    private void authorVerificationByID(Book book, String author_name) {
        long author_id = authorService.getID(author_name);

        if (author_id == 0) {
            authorService.addAuthorToDB(author_name);
        }
        book.setAuthor(authorService.fetchAuthorByName(author_name));
    }

    private void genreVerificationByID(Book book, String genre_name) {
        long genre_id = genreService.getID(genre_name);

        if (genre_id == 0) {
            genreService.addGenreToDB(genre_name);
        }
        book.setGenre(genreService.fetchGenreByName(genre_name));

    }

    public void deleteBook(long id) {
        bookService.deleteBookFromDB(id);
    }

    public Book editBook(long id, String title, String author_newName, String genre_newName) {
        Book book = bookService.fetchBookById(id);
        book.setTitle(title);

        genreVerificationByID(book, genre_newName);
        authorVerificationByID(book, author_newName);

        bookService.editBookFromDB(book);

        return book;

    }


    public List<Book> getAllBooks() {

        return new ArrayList<>(bookService.fetchAllBooks());
    }

    public Book getBook(long id) {
        return bookService.fetchBookById(id);
    }


}
