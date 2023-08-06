package myApp.models;

public class Book {

    private int book_id;
    private int person_id;
    private String book_name;
    private String author;
    private int book_year;

    public Book() {
    }

    public Book(String book_name, String author, int book_year) {
        this.book_name = book_name;
        this.author = author;
        this.book_year = book_year;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getBook_year() {
        return book_year;
    }

    public void setBook_year(int book_year) {
        this.book_year = book_year;
    }
}
