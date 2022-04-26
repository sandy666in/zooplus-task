package com.zooplus.assignment.service;

import com.zooplus.assignment.model.Price;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CryptoServiceTest extends BaseTest {

    private CryptoService cryptoService;

    @BeforeEach
    void setUp() {
        cryptoService = new CryptoService(webClientMock);
    }

    @Test
    public void getPriceForCurrency() {

        //Case to check the currency rate in local/locale currency.
        Price mockPrice = new Price("","BTC","USD","3200");

        when(responseMock.bodyToMono(Price.class)).thenReturn(Mono.just(mockPrice));

        Mono<Price> priceMono = cryptoService.getPrice("BTC","USD");

        StepVerifier.create(priceMono)
                .expectNextMatches(price -> price.getRate().equals("3200"))
                .verifyComplete();
    }
}
