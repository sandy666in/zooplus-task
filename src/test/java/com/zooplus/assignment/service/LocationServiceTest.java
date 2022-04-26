package com.zooplus.assignment.service;

import com.zooplus.assignment.model.Currency;
import com.zooplus.assignment.model.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LocationServiceTest extends BaseTest {

    private LocationService locationService;


    @BeforeEach
    void setUp() {
        locationService = new LocationService(webClientMock);
    }

    @Test
    public void getLocationWithIP() {

        //Case when IP address is passed from the UI
        String ipAddress = "49.37.180.254";
        Location mockLocation = new Location(ipAddress, new Currency("USD", "US Dollar", "Dollar"));

        when(responseMock.bodyToMono(Location.class)).thenReturn(Mono.just(mockLocation));

        Mono<Location> locationMono = locationService.getLocation(ipAddress);

        StepVerifier.create(locationMono)
                .expectNextMatches(loc -> loc.getCurrency().getCode().equals("USD"))
                .verifyComplete();
    }

    @Test
    public void getLocationWithoutIP() {

        //Case when No IP address is passed from the UI
        String ipAddress = "";
        Location mockLocation = new Location(ipAddress, new Currency("INR", "Indian Ruupee", "Rs"));

        when(responseMock.bodyToMono(Location.class)).thenReturn(Mono.just(mockLocation));

        Mono<Location> locationMono = locationService.getLocation(ipAddress);

        StepVerifier.create(locationMono)
                .expectNextMatches(loc -> loc.getCurrency().getCode().equals("INR"))
                .verifyComplete();
    }

}
