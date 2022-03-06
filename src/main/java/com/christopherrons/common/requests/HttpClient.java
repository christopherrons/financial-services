package com.christopherrons.common.requests;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.logging.Logger;

public class HttpClient {
    private static final Logger LOGGER = Logger.getLogger(HttpClient.class.getName());

    private HttpClient() {
        throw new IllegalStateException("Utility class");
    }

    public static String getRequest(final URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setDoOutput(true);

        try {
            return getResponse(connection);
        } catch (RuntimeException e) {
            LOGGER.warning(e.toString());
        }

        return "";
    }

    private static String getResponse(final HttpURLConnection connection) throws IOException {
        checkConnection(connection.getResponseCode());
        return getResponse(connection.getURL());
    }

    private static void checkConnection(final int responseCode) {
        if (responseCode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responseCode);
        }
    }

    private static String getResponse(final URL url) throws IOException {
        StringBuilder responsePayload = new StringBuilder();
        Scanner scanner = new Scanner(url.openStream());

        while (scanner.hasNext()) {
            responsePayload.append(scanner.nextLine());
        }
        scanner.close();

        return responsePayload.toString();
    }
}
