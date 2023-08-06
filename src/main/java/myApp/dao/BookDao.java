package myApp.dao;

import myApp.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

@Component
public class BookDao {
    private final JdbcTemplate jdbcTemplateBook;

    @Autowired
    public BookDao(JdbcTemplate jdbcTemplateBook) {
        this.jdbcTemplateBook = jdbcTemplateBook;
    }

    public List<Book> test(){
        String SQL = "SELECT * FROM book";
        List <Book> books = jdbcTemplateBook.query(SQL, new BookMapper());
        return books;
    }

//    public List<Book> index() {
//        return jdbcTemplateBook.query("SELECT * FROM book", new BeanPropertyRowMapper<>(Book.class));
//    }
    public void save(Book book) throws SQLException {
        jdbcTemplateBook.update("INSERT INTO book(book_name, author, book_year) VALUES(?, ?, ?)",
                book.getBook_name(), book.getAuthor(), book.getBook_year());
    }
    public Book show(int id) {
        String sql = "SELECT * FROM book WHERE book_id=?";
        Book book;
        book=jdbcTemplateBook.query(sql, new Object[]{id}, new BookMapper())
                .stream().findAny().orElse(null);
        return book;
    }
    public void update(int id, Book updatedBook) {
        System.out.println(id+": "+updatedBook.getBook_name()+","+updatedBook.getAuthor()+", "+updatedBook.getBook_year());
        String updateQuery = "update book set book_name = ?, author = ?, book_year = ? where book_id = ?";
        jdbcTemplateBook.update(updateQuery, updatedBook.getBook_name(), updatedBook.getAuthor(), updatedBook.getBook_year(), id);
    }

    public void delete(int id) {
        jdbcTemplateBook.update("DELETE FROM book WHERE book_id=?", id);
    }

    public void selectReader(int idReader,Book selectedBook){
        jdbcTemplateBook.update("update book SET person_id=? where book_id=?", idReader, selectedBook.getBook_id());
    }

    public void cancelReader(Book selectedBook){
        jdbcTemplateBook.update("update book SET person_id=null where book_id=?", selectedBook.getBook_id());
    }

}
