package objectClasses;

public class Book {
    private long id;
    private String title;
    private String ISBN;
    private Genre genre = new Genre();
    private Author author = new Author();


    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Genre getGenre() {
        return this.genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }


    public String toString() {
        return "id = " + this.id + ", название = \"" + this.title + "\", ISBN = " + this.ISBN + ", жанр = " + this.genre.getName() + ", автор = " + this.author.getName();
    }

}
