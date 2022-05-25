package com.christopherrons.common.requests;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

public class HttpClient {
    private static final Logger LOGGER = Logger.getLogger(HttpClient.class.getName());

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
            LOGGER.warning(e.toString());
        } finally {
            connection.disconnect();
        }

        return "";
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
}
