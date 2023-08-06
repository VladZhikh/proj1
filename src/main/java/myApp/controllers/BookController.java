package myApp.controllers;

import myApp.dao.BookDao;
import myApp.dao.PersonDao;
import myApp.models.Book;
import myApp.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@Controller
public class BookController {

    private final BookDao bookDao;
    private final PersonDao personDao;

    @Autowired
    public BookController(BookDao bookDao, PersonDao personDao) {
        this.bookDao = bookDao;
        this.personDao = personDao;
    }

    @GetMapping("/book")
    public String index(Model model) {
        model.addAttribute("book", bookDao.test());
        return "book/index";
    }

    @GetMapping("book/new")
    public String newBook(@ModelAttribute("book") Book book) {
        System.out.println(book.getBook_name() + " ," + book.getAuthor());
        return "book/new";
    }
    @PostMapping("book/new")
    public String create(@ModelAttribute("book")  Book book) throws SQLException {
        System.out.println(book.getBook_name() + " ," + book.getAuthor());
        bookDao.save(book);
        return "redirect:/book";
    }

    @GetMapping("book/{id}")
    public String show(@PathVariable("id") int id, Model model){
        Book book= bookDao.show(id);
        Person person=personDao.show(book.getPerson_id());
        if (person==null) person = new Person("",0);
        //System.out.println(person.getBirthYear());
        model.addAttribute("book",book);
        model.addAttribute("person",person);
        model.addAttribute("people", personDao.index());
        return "book/show";
    }

    @GetMapping("book/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookDao.show(id));
        return "book/edit";
    }

    @PatchMapping("book/{id}")
    public String update(@PathVariable("id") int id, @ModelAttribute("book") Book book) {
        bookDao.update(id, book);
        return "redirect:/book";
    }

    @DeleteMapping("book/{id}")
    public String delete(@PathVariable("id") int id) {
        bookDao.delete(id);
        return "redirect:/book";
    }

    @PatchMapping("book/select/{id}")
    public String selectPerson(@ModelAttribute("person") Person person, @PathVariable int id){
        Book book = bookDao.show(id);
        bookDao.selectReader(person.getPerson_id(), book);
        return "redirect:/book";
    }

    @PatchMapping("book/cancel/{id}")
    public String cancel(@PathVariable int id){
        Book book = bookDao.show(id);
        bookDao.cancelReader(book);
        return "redirect:/book";
    }
}
