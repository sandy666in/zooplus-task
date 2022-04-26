package com.zooplus.assignment.controller;

import com.zooplus.assignment.model.Currency;
import com.zooplus.assignment.model.Location;
import com.zooplus.assignment.model.Price;
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
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;
import java.text.DecimalFormat;

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

    /**
     * Gets fired on UI load, Sets defaults.
     *
     * @param model - The UI model
     * @return
     */
    @GetMapping("/form")
    public String getForm(Model model) {

        setModelAttributes(model, "Select Cryptocurrency", "", "");
        return "form";
    }

    /**
     * Get the current market price of the selected cryptocurrency and loads it to the UI.
     * Gets fired on post from the UI.
     *
     * @param currency - The selected cryptocurrency
     * @param ipAddress - IP address to get the locale
     * @param model - The UI model
     *
     */

    @PostMapping("/form")
    public String getForm(@Valid String currency, @Valid String ipAddress, Model model) {

        if (currency.length() == 0) {
            return "form";
        }
        double marketRate = 0;
        Location location;

        List<Currency> currencies = cryptoService.getCurrencies().stream().filter(c -> c.getCode().equals(currency))
                .collect(Collectors.toList());

        try {
            location = locationService.getLocation(ipAddress).block();
        } catch (Exception ex) {
            log.error("Exception while fetching location API", ex);
            // Default to Euro location.
            location = new Location(ipAddress, new Currency("EUR", "Euro", EURO_SYMBOL));
        }

        try {
            Price price = cryptoService.getPrice(currencies.get(0).getCode(), location.getCurrency().getCode()).block();
            marketRate = Double.parseDouble(price.getRate());
        } catch (Exception ex) {
            log.error("Exception while fetching crypto API", ex);
        }

        setModelAttributes(model, currencies.get(0).getName(), getFormattedPrice(location.getCurrency()
                .getSymbol(), marketRate), ipAddress);

        return "form";
    }

    private String getFormattedPrice(String symbol, Double price) {

        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.UP);
        return "Current unit price: " + symbol + " " + df.format(price);
    }

    private void setModelAttributes(Model model, String lastSelected, String priceDetails, String ipAddress) {

        model.addAttribute("lastSelected", lastSelected);
        model.addAttribute("priceDetails", priceDetails);
        model.addAttribute("ipAddress", ipAddress);
    }
}
