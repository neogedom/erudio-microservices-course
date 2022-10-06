package com.neogedom.bookservice.controller;

import com.neogedom.bookservice.model.Book;
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

    public BookController(Environment environment) {
        this.environment = environment;
    }
    @GetMapping("/{id}/{currency}")
    public Book findBook(@PathVariable("id") Long id, @PathVariable("currency") String currency ) {
        var port = environment.getProperty("local.server.port");
        return new Book(1L, "Nigel Poulton", "Docker Deep Dive", new Date(), Double.valueOf(13.7), currency, port);
    }
}
