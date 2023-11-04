package org.example.services;

import org.example.models.Book;
import org.example.models.Person;
import org.example.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@Service
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findByTitle(String title) {
        return bookRepository.findByTitleStartingWith(title);
    }

    public Person getBookOwner(int id) {
        return bookRepository.findById(id).orElseThrow(null).getOwner();
    }

    public List<Book> findAll(boolean sortByYear) {
        if (sortByYear) {
            return bookRepository.findAll(Sort.by("year_of_production"));
        } else {
            return bookRepository.findAll();
        }
    }

    public List<Book> findWithPagination(Integer page, Integer books_per_page, boolean sortByYear) {
        if (sortByYear) {
            return bookRepository.findAll(PageRequest.of(page, books_per_page, Sort.by("year_of_production"))).getContent();
        } else {
            return bookRepository.findAll(PageRequest.of(page, books_per_page)).getContent();
        }
    }

    public Book findById(int id) {
        Optional<Book> book = bookRepository.findById(id);
        return book.orElse(null);
    }

    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public void delete(int id) {
        bookRepository.deleteById(id);
    }

    @Transactional
    public void updateBook(int id, Book updatedBook) {
        Book bookWhichWillBeUpdated = bookRepository.findById(id).get();
        updatedBook.setId(id);
        updatedBook.setOwner(bookWhichWillBeUpdated.getOwner());
        bookRepository.save(updatedBook);
    }

    @Transactional
    public void release(int id) {
        Book book = bookRepository.findById(id).get();
        book.setOwner(null);
        book.setTakenAt(null);
    }

    @Transactional
    public void assign(int id, Person selectedPerson) {
        Book book = bookRepository.findById(id).get();
        book.setOwner(selectedPerson);
        book.setTakenAt(new Date());
    }


}
