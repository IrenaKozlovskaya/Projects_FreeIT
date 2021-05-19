package repository;

import config.DataSourceUtil;
import objectClasses.Genre;
import objectClasses.Author;
import objectClasses.Book;
import objectClasses.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class BookRepository {

    private final String SQL = "SELECT b.id, b.title, b.ISBN, a.author, g.genre FROM books b LEFT JOIN authors a ON b.author_id = a.id LEFT JOIN genres g ON b.genre_id = g.id";
    DataSourceUtil dataSourceUtil = new DataSourceUtil();

    public void addBookToBD(Book book) {

        String sql = "INSERT INTO books(id, title, ISBN, author_id,genre_id) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement statement = dataSourceUtil.getConnection().prepareStatement(sql);

            statement.setLong(1, book.getId());
            statement.setString(2, book.getTitle());
            statement.setString(3, book.getISBN());
            statement.setLong(4, book.getAuthor().getId());
            statement.setLong(5, book.getGenre().getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<Book> getBooks() throws SQLException {

        PreparedStatement statement = dataSourceUtil.getConnection().prepareStatement(SQL);
        ResultSet resultSet = statement.executeQuery();

        return createBooks(resultSet);
    }

    public Book getBookByID(long id) throws SQLException {

        ResultSet resultSet = null;

        String sql = SQL + " WHERE b.id = ?";
        try {

            PreparedStatement statement = dataSourceUtil.getConnection().prepareStatement(sql);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        assert resultSet != null;
        return createBooks(resultSet).get(0);
    }

    public void deleteBookByID(long id) throws SQLException {


        String sql = "DELETE FROM books WHERE id = ?";
        try {
            PreparedStatement statement = dataSourceUtil.getConnection().prepareStatement(sql);
            statement.setLong(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editBookByID(Book book) throws SQLException {


        String sql = "UPDATE books SET title=?, author_id=?, genre_id=? WHERE id=?";
        try {
            PreparedStatement statement = dataSourceUtil.getConnection().prepareStatement(sql);

            statement.setString(1, book.getTitle());
            statement.setLong(2, book.getAuthor().getId());
            statement.setLong(3, book.getGenre().getId());
            statement.setLong(4, book.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private List<Book> createBooks(ResultSet resultSet) throws SQLException {
        List<Book> bookList = new ArrayList<>();

        while (resultSet.next()) {
            Book book = new Book();
            book.setId(resultSet.getLong(1));
            book.setTitle(resultSet.getString(2));
            book.setISBN(resultSet.getString(3));
            Author author = new Author();
            author.setName(resultSet.getString(4));
            book.setAuthor(author);
            Genre genre = new Genre();
            genre.setName(resultSet.getString(5));
            book.setGenre(genre);
            bookList.add(book);
        }

        dataSourceUtil.disconnect();

        return bookList;

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
