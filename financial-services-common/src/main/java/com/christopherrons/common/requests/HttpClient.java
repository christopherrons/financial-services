package com.christopherrons.common.requests;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

public class HttpClient {
    private static final Logger LOGGER = Logger.getLogger(HttpClient.class.getName());
    private static final RestTemplate REST_TEMPLATE = new RestTemplate();

    private HttpClient() {
        throw new IllegalStateException("Utility class");
    }

    public static String requestGET(final URL url, Map<String, String> headerToValue) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("accept", "application/json");
        headerToValue.forEach(connection::setRequestProperty);

        try {
            return getResponse(connection);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } finally {
            connection.disconnect();
        }
    }

    private static String getResponse(final HttpURLConnection connection) throws IOException {
        checkConnection(connection.getResponseCode());
        StringBuilder responsePayload = new StringBuilder();
        Scanner scanner = new Scanner(connection.getInputStream());

        while (scanner.hasNext()) {
            responsePayload.append(scanner.nextLine());
        }
        scanner.close();

        return responsePayload.toString();
    }

    private static void checkConnection(final int responseCode) {
        if (responseCode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responseCode);
        }
    }

    public static <T> T getRequestToEntity(final URI uri, final HttpHeaders headers, final Class<T> clazz) {
        return REST_TEMPLATE.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), clazz).getBody();
    }
}
