package com.zooplus.assignment.service;

import com.zooplus.assignment.model.Price;
import com.zooplus.assignment.model.Currency;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CryptoService {

    public Mono<Price> getPrice(String currencyPair) {

        return WebClient.create()
                .get()
                .uri(getUri(currencyPair))
                .retrieve()
                .bodyToMono(Price.class);
    }

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

    private String getUri(String currencyPair) {
        log.info( "https://api.coinbase.com/v2/prices/" + currencyPair + "/spot");

        return "https://api.coinbase.com/v2/prices/" + currencyPair + "/spot";
    }
}
