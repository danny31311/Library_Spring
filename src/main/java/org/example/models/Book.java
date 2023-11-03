package org.example.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public class Book {
    private int id;
    @NotEmpty(message = "Book name can not be empty")
    private String book_name;
    @NotEmpty(message = "String author can not be empty")
    private String author;
    @Min(value = 0, message = "I doubt you want add book which was written B.C. ")
    private int year;

    public Book() {
    }

    public Book(int id, String book_name, String author, int year) {
        this.id = id;
        this.book_name = book_name;
        this.author = author;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }


}
