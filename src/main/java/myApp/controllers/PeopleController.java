package myApp.controllers;

import myApp.models.Book;
import myApp.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import myApp.dao.PersonDao;
import java.sql.SQLException;
import java.util.List;

@Controller

public class PeopleController {

    private final PersonDao personDao;


    @Autowired
    public PeopleController(PersonDao personDao) {
        this.personDao = personDao;
    }

    @GetMapping("/people")
    public String index(Model model) {
        model.addAttribute("people", personDao.index());
        return "people/index";
    }

    @GetMapping("people/new")
    public String newPerson(String name, String year) throws SQLException {
        int birthYear;
        try {
            birthYear = Integer.parseInt(year);
        } catch (Exception e) {
            birthYear = 0;
        }
        Person person = new Person(name, birthYear);
        if (person.getFullName() == null || person.getFullName().isEmpty())
            return "people/new";
        else {
            //System.out.println(person.getFullName() + " ," + person.getBirthYear());
            personDao.save(person);
            return "success";
        }
    }

    @GetMapping("people/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", personDao.show(id));
        return "people/edit";
    }
    //"people/{id}"
    @PatchMapping("people/{id}")
    public String update(@PathVariable("id") int id, @ModelAttribute("person") Person person)
    {
        System.out.println(id+", "+person.getFullName()+", "+person.getBirthYear());
        personDao.update(id, person);
        return "success";
        //return "people/edit";
    }

    @DeleteMapping("people/{id}")
    public String delete(@PathVariable("id") int id) {
        personDao.delete(id);
        return "redirect:/people";
    }

    @GetMapping("people/{id}/find")
    public String findBooks(@PathVariable("id") int id,Model model){
        List<Book> bookList=personDao.findBooks(id);
        model.addAttribute("book", bookList);
        Person person = personDao.show(id);
        model.addAttribute("person",person);
        return "people/find";
    }
}
