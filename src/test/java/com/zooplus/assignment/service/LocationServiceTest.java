package com.zooplus.assignment.service;

import com.zooplus.assignment.model.Currency;
import com.zooplus.assignment.model.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LocationServiceTest {

    private LocationService locationService;
    @Mock
    private WebClient webClientMock;
    @Mock
    private WebClient.RequestHeadersSpec requestHeadersMock;
    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriMock;
    @Mock
    private WebClient.ResponseSpec responseMock;

    @BeforeEach
    void setUp() {
        locationService = new LocationService(webClientMock);
    }

    @Test
    public void getPriceTest() {

        String ipAddress = "49.37.180.254";
        Location mockLocation = new Location(ipAddress, new Currency("INR", "Indian Ruupee", "Rs"));
        when(webClientMock.get()).thenReturn(requestHeadersUriMock);
        when(requestHeadersUriMock.uri(LocationService.PATH_PARAM + ipAddress)).thenReturn(requestHeadersMock);
        when(requestHeadersMock.retrieve()).thenReturn(responseMock);
        when(responseMock.onStatus(any(), any())).thenReturn(responseMock);
        when(responseMock.bodyToMono(Location.class)).thenReturn(Mono.just(mockLocation));

        Mono<Location> locationMono = locationService.getLocation(ipAddress);

        StepVerifier.create(locationMono)
                .expectNextMatches(loc -> loc.getCurrency().getCode().equals("INR"))
                .verifyComplete();
    }
}
