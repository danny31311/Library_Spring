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

}
