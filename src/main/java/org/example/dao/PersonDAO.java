package org.example.dao;

import org.example.models.Book;
import org.example.models.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@Transactional(readOnly = true)
public class PersonDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public PersonDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;

    }

    public List<Person> index() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Person", Person.class).getResultList();
    }

    public Person show(String name) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Person.class, name);
    }

    public Person show(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Person.class, id);
    }

    @Transactional
    public void update(int id, Person updatedPerson) {
        Session session = sessionFactory.getCurrentSession();
        Person personWhichWillBeUpdated = session.get(Person.class, id);
        personWhichWillBeUpdated.setName(updatedPerson.getName());
        personWhichWillBeUpdated.setAge(updatedPerson.getAge());
        personWhichWillBeUpdated.setBooks(updatedPerson.getBooks());

    }

    @Transactional
    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(session.get(Person.class, id));

    }

    @Transactional
    public void save(Person person) {
        Session session = sessionFactory.getCurrentSession();
        session.save(person);
    }

    //Валидация уникальности ФИО
    public Optional<Person> getPersonByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Person where name = name", Person.class).stream().findAny();
    }

    public List<Book> getBooksByPersonId(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT b FROM Book b where b.owner.id=:id", Book.class).setParameter("id", id).getResultList();

    }
}
