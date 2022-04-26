package com.zooplus.assignment.service;

import com.zooplus.assignment.model.Price;
import com.zooplus.assignment.model.Currency;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.xml.rpc.ServiceException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CryptoService {

    private WebClient webClient;
    private final static String BASE_URL = "https://rest.coinapi.io/v1/exchangerate/";
    public final static String API_KEY = "?apikey=7CD817DB-CA24-4318-942C-B6FC7232B2E6";

    public CryptoService(WebClient webClient) {
        this.webClient = webClient;
    }

    public CryptoService() {
        this.webClient = WebClient.create(BASE_URL);
    }

    /**
     * Returns the price detail of the base cryptocurrency from Coin API.
     *
     * @param base - Base cryptocurrency code.
     * @param currency - - Location bases currency code.
     * @return - price details for the associated cryptocurrency.
     */
    public Mono<Price> getPrice(String base, String currency) {

        return webClient.get()
                .uri(getUri(base, currency))
                .retrieve()
                .onStatus(status -> status.isError(),
                        response -> Mono.error(
                                new ServiceException("Exception: Please check the URL. " + response.statusCode().value())))
                .bodyToMono(Price.class);
    }

    /**
     * Returns list of  cryptocurrencies.
     * @return List of cryptocurrency
     */
    public List<Currency> getCurrencies() {

        List<Currency> currencies = new ArrayList<>();
        currencies.add(new Currency("BTC", "BitCoin", ""));
        currencies.add(new Currency("ETH", "Ethereum", ""));
        currencies.add(new Currency("LTC", "Litecoin", ""));
        currencies.add(new Currency("ADA", "Cardano", ""));
        currencies.add(new Currency("DOT", "Polkadot", ""));
        currencies.add(new Currency("DOGE", "Dogecoin", ""));
        currencies.add(new Currency("XLM", "Stellar", ""));
        currencies.add(new Currency("USDT", "Tether", ""));
        currencies.add(new Currency("XMR", "Monero", ""));
        currencies.add(new Currency("BNB", "Binance", ""));

        return currencies;
    }

    private String getUri(String base, String currency) {
        return base +"/"+currency+API_KEY;
    }
}
