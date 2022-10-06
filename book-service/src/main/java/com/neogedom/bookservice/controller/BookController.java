package com.neogedom.bookservice.controller;

import com.neogedom.bookservice.model.Book;
import com.neogedom.bookservice.repository.BookRepository;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/book-service")
public class BookController {

    final private Environment environment;
    final private BookRepository repository;

    public BookController(Environment environment, BookRepository repository) {
        this.environment = environment;
        this.repository = repository;
    }
    @GetMapping("/{id}/{currency}")
    public Book findBook(@PathVariable("id") Long id, @PathVariable("currency") String currency ) {
        var book = repository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
        var port = environment.getProperty("local.server.port");
        book.setCurrency(currency);
        book.setEnvironment(port);
        return book;
    }
}
