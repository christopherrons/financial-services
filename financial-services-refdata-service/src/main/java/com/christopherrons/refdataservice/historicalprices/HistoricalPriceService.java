package com.christopherrons.refdataservice.historicalprices;


import com.christopherrons.common.model.refdata.HistoricalPrice;
import com.christopherrons.common.model.refdata.HistoricalPriceCollection;
import com.christopherrons.refdataservice.historicalprices.enums.YahooApiSymbolEnum;
import com.christopherrons.refdataservice.historicalprices.model.HistoricalPriceResponse;
import com.christopherrons.refdataservice.historicalprices.model.YahooHistoricalPrices;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import static com.christopherrons.common.requests.HttpClient.getRequestToEntity;
import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class HistoricalPriceService {
    private static final Logger LOGGER = Logger.getLogger(HistoricalPriceService.class.getName());
    private static final String BASE_URL = "https://yfapi.net/v8/finance/spark";
    private static final HttpHeaders HEADERS = new HttpHeaders();
    private static final int UPDATE_INTERVAL_IN_DAYS = 365;
    private static final ObjectMapper mapper = new ObjectMapper();
    private HistoricalPriceCollection historicalPriceCollection = null;
    private LocalDate lastUpdateTime = LocalDate.now();

    public HistoricalPriceService() {
        HEADERS.set("X-API-KEY", "zAXILAd1bX7UzfaqN3gpda4Fe0iOSwBGakmvZDfd");
    }

    public HistoricalPriceCollection getHistoricalData() throws IOException {
        LocalDate currentTime = LocalDate.now();
        if (updateHistoricalData(currentTime)) {
            LOGGER.info("Updating Historical Ref Data: Previous updated on: " + lastUpdateTime.toString());
            lastUpdateTime = currentTime;
            try {
                historicalPriceCollection = requestHistoricalData();
            } catch (Exception e) {
                LOGGER.warning("Fetching historical data failed. Using backup!" + e);
                var backupFile = new File("/home/christopher/versioned/financial-services/financial-services-refdata-service/src/main/resources/backup_historical_prices.json");
                var backupResponse = mapper.readValue(backupFile, YahooHistoricalPrices.class);
                historicalPriceCollection = createHistoricalPriceCollection(backupResponse);
            }
        }
        return historicalPriceCollection;
    }

    private boolean updateHistoricalData(final LocalDate currentTime) {
        return historicalPriceCollection == null || DAYS.between(lastUpdateTime, currentTime) >= UPDATE_INTERVAL_IN_DAYS;
    }

    private HistoricalPriceCollection requestHistoricalData() throws URISyntaxException {
        URI uri = new URI(String.format("%s?interval=%s&range=%s&symbols=%s", BASE_URL, "1d", "5y", YahooApiSymbolEnum.API_SYMBOLS));
        YahooHistoricalPrices responses = getRequestToEntity(uri, HEADERS, YahooHistoricalPrices.class);
        return createHistoricalPriceCollection(responses);
    }

    private HistoricalPriceCollection createHistoricalPriceCollection(final YahooHistoricalPrices responses) {
        Map<String, HistoricalPrice> instrumentIdToHistoricalDataItem = new ConcurrentHashMap<>();
        for (HistoricalPriceResponse response : responses.getResponses()) {
            if (response != null && !response.isEmpty()) {
                HistoricalPrice historicalPrice = createHistoricalDataItem(response.getClose());
                instrumentIdToHistoricalDataItem.put(YahooApiSymbolEnum.apiSymbolToInstrumendId(response.getSymbol()), historicalPrice);
            }
        }
        return new HistoricalPriceCollection(instrumentIdToHistoricalDataItem);
    }

    private HistoricalPrice createHistoricalDataItem(final Double[] closingPrices) {
        return new HistoricalPrice(Arrays.asList(closingPrices));
    }

}