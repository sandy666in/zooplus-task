package com.zooplus.assignment.service;

import com.zooplus.assignment.model.Location;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.xml.rpc.ServiceException;


@Slf4j
@Service
public class LocationService {

    private WebClient webClient;
    private final static String BASE_URL = "https://api.ipgeolocation.io/";
    public final static String PATH_PARAM  = "/ipgeo?apiKey=2d0ccf4e1f844155852b2ab3041ab40c&fields=currency&ip=";

    public LocationService(WebClient webClient) {
        this.webClient = webClient;
    }

    public LocationService() {
        this.webClient = WebClient.create(BASE_URL);;
    }

    public Mono<Location> getLocation(String ipAddress) {

        return webClient.get()
                .uri(getUri(ipAddress))
                .retrieve()
                .onStatus(status -> status.isError(),
                        response -> Mono.error(
                                new ServiceException("Exception: Please check the URL. " + response.statusCode().value())))
                .bodyToMono(Location.class);
    }

    private String getUri(String ipAddress) {
        return PATH_PARAM + ipAddress;
    }
}
