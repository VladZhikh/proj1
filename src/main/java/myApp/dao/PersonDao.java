package myApp.dao;

import myApp.models.Book;
import myApp.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.sql.SQLException;
import java.util.List;

@Component
public class PersonDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public void save(Person person) throws SQLException {
        jdbcTemplate.update("INSERT INTO Person(fullName, birthYear) VALUES(?, ?)",
                person.getFullName(), person.getBirthYear());
    }


    public List<Book> findBooks(int id) {
        String SQL_book="SELECT * FROM book WHERE person_id=?";
        List<Book> books = jdbcTemplate.query(SQL_book,new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class));
       return books;
    }

    public Person show(int id) {
        Person person;
        person=jdbcTemplate.query("SELECT * FROM person WHERE person_id=?",
                        new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
        return person;
    }

    public void update(int id, Person updatedPerson) {
        String updateQuery = "update person set fullname = ?, birthyear = ? where person_id = ?";
        jdbcTemplate.update(updateQuery, updatedPerson.getFullName(), updatedPerson.getBirthYear(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Person WHERE person_id=?", id);
    }
}
