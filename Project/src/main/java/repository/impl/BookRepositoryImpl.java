package repository.impl;

import config.DataSourceUtil;
import objectClasses.Book;
import objectClasses.Genre;
import repository.BookRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class BookRepositoryImpl implements BookRepository {

    private final String sql = "SELECT b.id, b.title, b.ISBN, g.genre FROM books b LEFT JOIN genres g ON b.genre_id = g.id";
    DataSourceUtil dataSourceUtil = new DataSourceUtil();
    AuthorRepositoryImpl authorRepository = new AuthorRepositoryImpl();

    @Override
    public List<Book> getBooks() throws SQLException {

        PreparedStatement statement = dataSourceUtil.getConnection().prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        return createBooks(resultSet);
    }

    @Override
    public Book getBookByID(long id) throws SQLException {

        String request = sql + " WHERE b.id = ?";
        PreparedStatement statement = dataSourceUtil.getConnection().prepareStatement(request);
        statement.setLong(1, id);
        ResultSet resultSet = statement.executeQuery();

        return createBooks(resultSet).get(0);
    }

    private List<Book> createBooks(ResultSet resultSet) throws SQLException {

        List<Book> bookList = new ArrayList<>();
        while (resultSet.next()) {
            Book book = new Book();
            book.setId(resultSet.getLong(1));
            book.setTitle(resultSet.getString(2));
            book.setISBN(resultSet.getString(3));
            Genre genre = new Genre();
            genre.setName(resultSet.getString(4));
            book.setGenre(genre);
            book.setAuthors(authorRepository.getAuthorsByBookID(resultSet.getLong(1)));
            bookList.add(book);
        }

        dataSourceUtil.disconnect();

        return bookList;
    }

    @Override
    public void addBookToBD(Book book) throws SQLException {

        String request = "INSERT INTO books(title, ISBN, genre_id) VALUES (?,?,?)";
        PreparedStatement statement = dataSourceUtil.getConnection().prepareStatement(request);
        statement.setString(1, book.getTitle());
        statement.setString(2, book.getISBN());
        statement.setLong(3, book.getGenre().getId());
        statement.executeUpdate();

        book.setId(getBookId(book.getTitle()));

        authorRepository.setUpAuthorsInBook(book);

        dataSourceUtil.disconnect();
    }

    @Override
    public void deleteBookByID(long id) throws SQLException {

        String request = "DELETE FROM books WHERE id = ?";
        PreparedStatement statement = dataSourceUtil.getConnection().prepareStatement(request);
        statement.setLong(1, id);
        statement.executeUpdate();

        dataSourceUtil.disconnect();
    }

    @Override
    public void editBookByID(Book book) throws SQLException {

        String request = "UPDATE books SET title=?, genre_id=? WHERE id=?";
        PreparedStatement statement = dataSourceUtil.getConnection().prepareStatement(request);
        statement.setString(1, book.getTitle());
        statement.setLong(2, book.getGenre().getId());
        statement.setLong(3, book.getId());
        statement.executeUpdate();

        authorRepository.updateAuthorsInBook(book);

        dataSourceUtil.disconnect();
    }

    public long getBookId(String title) {

        List<Book> books = null;
        try {
            books = getBooks();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        long book_id = 0;
        assert books != null;
        for (Book book : books) {
            if (book.getTitle().equals(title)) {
                book_id = book.getId();
                break;
            }
        }
        return book_id;
    }
}
