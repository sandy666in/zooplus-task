package com.zooplus.assignment.service;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.springframework.web.reactive.function.client.WebClient;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class BaseTest {

    @Mock
    protected WebClient webClientMock;
    @Mock
    protected WebClient.RequestHeadersSpec requestHeadersMock;
    @Mock
    protected WebClient.RequestHeadersUriSpec requestHeadersUriMock;
    @Mock
    protected WebClient.ResponseSpec responseMock;

    @BeforeEach
    protected void setMocks() {

        when(webClientMock.get()).thenReturn(requestHeadersUriMock);
        when(requestHeadersUriMock.uri(anyString())).thenReturn(requestHeadersMock);
        when(requestHeadersMock.retrieve()).thenReturn(responseMock);
        when(responseMock.onStatus(any(), any())).thenReturn(responseMock);

    }
}
