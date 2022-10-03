package com.neogedom.bookservice.controller;

import com.neogedom.bookservice.model.Book;
import com.neogedom.bookservice.proxy.CambioProxy;
import com.neogedom.bookservice.repository.BookRepository;
import com.neogedom.bookservice.response.Cambio;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("book-service")
public class BookController {

    final private Environment environment;
    final private BookRepository repository;
    final private CambioProxy proxy;

    public BookController(Environment environment, BookRepository repository, CambioProxy proxy) {
        this.environment = environment;
        this.repository = repository;
        this.proxy = proxy;
    }
    @GetMapping("{id}/{currency}")
    public Book findBook(@PathVariable("id") Long id, @PathVariable("currency") String currency){
        var book = repository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));

        var cambio = proxy.getCambio(book.getPrice(), "USD", currency);
        var port = environment.getProperty("local.server.port");
        book.setEnvironment(port + " FEIGN");
        book.setPrice(cambio.getConvertedValue());
        return book;
    }

//    @GetMapping("{id}/{currency}")
//    public Book findBook(@PathVariable("id") Long id, @PathVariable("currency") String currency){
//        var book = repository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
//
//        Map<String, String> params = new HashMap<>();
//        params.put("amount", book.getPrice().toString());
//        params.put("from", "USD");
//        params.put("to", currency);
//
//        var response = new RestTemplate().getForEntity("http://localhost:8000/cambio-service/{amount}/{from}/{to}", Cambio.class, params);
//
//        var port = environment.getProperty("local.server.port");
//        book.setEnvironment(port);
//        book.setPrice(response.getBody().getConvertedValue());
//        return book;
//    }
}
