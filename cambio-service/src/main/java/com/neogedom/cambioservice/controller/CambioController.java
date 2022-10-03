package com.neogedom.cambioservice.controller;

import com.neogedom.cambioservice.model.Cambio;
import com.neogedom.cambioservice.repository.CambioRepository;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;

@RestController
@RequestMapping(value = "/cambio-service")
public class CambioController {
    
    final private Environment environment;
    final private CambioRepository repository;

    public CambioController(Environment environment, CambioRepository repository) {
        this.environment = environment;
        this.repository = repository;
    }

    @GetMapping(value = "/{amount}/{from}/{to}")
    public Cambio getCambio(
            @PathVariable("amount") BigDecimal amount,
            @PathVariable("from") String from,
            @PathVariable("to") String to) {

        var cambio = repository.findByFromAndTo(from, to);
        if (cambio == null) throw new RuntimeException("Currency unsupported");
        var port = environment.getProperty("local.server.port");
        BigDecimal conversionFactor = cambio.getConversionFactor();
        BigDecimal convertedValue = cambio.getConvertedValue().multiply(amount);
        cambio.setConvertedValue(convertedValue.setScale(2, RoundingMode.CEILING));
        cambio.setEnvironment(port);
        return cambio;
    }


}
