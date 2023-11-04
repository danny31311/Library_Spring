package org.example.dao;

import org.example.models.Book;
import org.example.models.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Component
@EnableTransactionManagement
public class BookDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public BookDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Book> index() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Book", Book.class).getResultList();
    }

    public Book show(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Book.class, id);

    }

    public void save(Book book) {
        Session session = sessionFactory.getCurrentSession();
        session.save(book);

    }

    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(session.get(Book.class, id));

    }


    public void update(int id, Book updatedBook) {
        Session session = sessionFactory.getCurrentSession();
        Book bookWhichWillBeUpdated = session.get(Book.class, id);
        bookWhichWillBeUpdated.setTitle(updatedBook.getTitle());
        bookWhichWillBeUpdated.setYear(updatedBook.getYear());
        bookWhichWillBeUpdated.setAuthor(updatedBook.getAuthor());
        bookWhichWillBeUpdated.setOwner(updatedBook.getOwner());
    }

    public void assign(int id, Person selectedPerson) {
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, id);
        book.setOwner(selectedPerson);
    }

    public void release(int id) {
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, id);
        book.setOwner(null);
    }

    public Optional<Person> getBookOwner(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT p FROM Person as p JOIN p.books b WHERE b.id= :id", Person.class)
                .setParameter("id", id).stream().findAny();

    }

}
