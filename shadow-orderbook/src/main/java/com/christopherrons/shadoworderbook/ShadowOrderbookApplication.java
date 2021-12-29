package com.christopherrons.shadoworderbook;

import com.christopherrons.shadoworderbook.exchange.bitstamp.client.BitstampWebsocketClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import javax.websocket.DeploymentException;
import java.io.IOException;
import java.net.URISyntaxException;

@SpringBootApplication
public class ShadowOrderbookApplication {

    public static void main(String[] args) throws InterruptedException, DeploymentException, IOException, URISyntaxException {
        ApplicationContext applicationContext = SpringApplication.run(ShadowOrderbookApplication.class, args);
        BitstampWebsocketClient bitstampWebsocketClient = applicationContext.getBean(BitstampWebsocketClient.class);
		bitstampWebsocketClient.subscribeToLiveOrders();

        while (true) {
            Thread.sleep(10000);
        }
    }

}
