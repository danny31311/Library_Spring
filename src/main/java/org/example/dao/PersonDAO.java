package org.example.dao;

import org.example.models.Book;
import org.example.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public List<Person> index(){
        return jdbcTemplate.query("SELECT * FROM PERSON", new BeanPropertyRowMapper<>(Person.class));
    }
    public Optional<Person> show(String name){
        return jdbcTemplate.query("SELECT * FROM PERSON WHERE name = ?", new Object[]{name}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny();
    }
    public Person show(int id){
        return jdbcTemplate.query("SELECT * FROM PERSON WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }
    public void update(int id, Person updatedPerson) {
        jdbcTemplate.update("UPDATE PERSON SET name=?, age=? WHERE id=?", updatedPerson.getName(),
                updatedPerson.getAge(),id);
    }
    public void delete(int id){
        jdbcTemplate.update("DELETE FROM PERSON WHERE id = ?", id);
    }
    public void save(Person person){
        jdbcTemplate.update("INSERT INTO PERSON(name,age) VALUES (?,?)",person.getName(),person.getAge());
    }
    //Валидация уникальности ФИО
    public Optional<Person> getPersonByName(String name){
        return jdbcTemplate.query("SELECT * FROM PERSON WHERE name=?", new Object[]{name},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }
    public List<Book> getBooksByPersonId(int id){
        return jdbcTemplate.query("SELECT * FROM BOOK WHERE person_id = ?", new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class));
    }
}
