package myApp.dao;

import myApp.models.Book;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        Book book = new Book();

        book.setBook_id(rs.getInt("book_id"));
        book.setPerson_id(rs.getInt("person_id"));
        book.setBook_name(rs.getString("book_name"));
        book.setAuthor(rs.getString("author"));
        book.setBook_year(rs.getInt("book_year"));

        return book;
    }
}
