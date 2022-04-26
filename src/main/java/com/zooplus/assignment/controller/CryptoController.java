package com.zooplus.assignment.controller;

import com.zooplus.assignment.model.Currency;
import com.zooplus.assignment.model.Location;
import com.zooplus.assignment.service.CryptoService;
import com.zooplus.assignment.service.LocationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
public class CryptoController {

    @Autowired
    private CryptoService cryptoService;

    @Autowired
    private LocationService locationService;

    @ModelAttribute("currencies")
    public List<Currency> allUsers() {
        return cryptoService.getCurrencies();
    }

    private static final String EURO_SYMBOL = "\u20ac";

    @GetMapping("/form")
    public String getForm(Model model) {
        model.addAttribute("lastSelected", "Select Cryptocurrency");
        model.addAttribute("symbolCourse", "");
        model.addAttribute("ipAddress", "");

        return "form";
    }

    @PostMapping("/form")
    public String getForm(@Valid String currency, @Valid String ipAddress, Model model) {

        if(currency.length() == 0) {
            return "form";
        }

        final String[] currPrice = {""};
        List<Currency> currencies = cryptoService.getCurrencies().stream()
                .filter(c -> c.getCode().equals(currency))
                .collect(Collectors.toList());

        Location location;
        try {
            location = locationService.getLocation(ipAddress).block();
        } catch (Exception ex) {
            log.error(ex.toString());
            location = new Location(ipAddress, new Currency("EUR", "Euro", EURO_SYMBOL));
        }
        cryptoService.getPrice(currencies.get(0).getCode() + "-" + location.getCurrency().getCode())
                .subscribe(crypto -> {
                    currPrice[0] = crypto.getAmount();
                });

        model.addAttribute("ipAddress", ipAddress);
        model.addAttribute("lastSelected", currencies.get(0).getName());
        model.addAttribute("symbolCourse", location.getCurrency().getSymbol() + currPrice[0]);

        return "form";
    }
}
