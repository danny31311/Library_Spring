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
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public List<Book> index(){
        return jdbcTemplate.query("SELECT * FROM BOOK", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book show(int id){
        return jdbcTemplate.query("SELECT * FROM BOOK WHERE id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class))
                .stream().findAny().orElse(null);
    }

    public void save(Book book){
        jdbcTemplate.update("INSERT INTO BOOK (book_name , author, year) VALUES(?,?,?)", book.getTitle(),
                book.getAuthor(), book.getYear());
    }
    public void delete(int id){
        jdbcTemplate.update("DELETE FROM BOOK WHERE id =?", id);
    }



    public void update(int id, Book updatedBook){
        jdbcTemplate.update("UPDATE BOOK SET book_name=?, author = ?, year = ? WHERE id =?",
               updatedBook.getTitle(), updatedBook.getAuthor(), updatedBook.getYear(),
                id);
    }
    public void assign(int id, Person selectedPerson){
        jdbcTemplate.update("UPDATE BOOK SET person_id=? WHERE id=?", selectedPerson.getId(), id);
    }
    public void release(int id){
        jdbcTemplate.update("UPDATE BOOK SET person_id=NULL WHERE id=?", id);
    }
    public Optional<Person> getBookOwner(int id){
        return jdbcTemplate.query("SELECT PERSON.* FROM BOOK JOIN PERSON ON BOOK.person_id=PERSON.id "+
                "WHERE BOOK.id=?",new Object[]{id},new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

}
