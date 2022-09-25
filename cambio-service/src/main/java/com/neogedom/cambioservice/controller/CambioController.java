package com.neogedom.cambioservice.controller;

import java.math.BigDecimal;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neogedom.cambioservice.model.Cambio;

@RestController
@RequestMapping(value = "/cambio-service")
public class CambioController {
    
    final private Environment environment;

    public CambioController(Environment environment) {
        this.environment = environment;
    }

    @GetMapping(value = "/{amount}/{from}/{to}")
    public Cambio getCambio(@PathVariable("amount") BigDecimal amount, @PathVariable("from") String from, @PathVariable("to") String to) {
        var port = environment.getProperty("local.server.port");
        return new Cambio(1L, from, to, BigDecimal.ONE, BigDecimal.ONE, port);
    }


}
