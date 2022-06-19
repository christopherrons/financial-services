package com.christopherrons.refdataservice.historicalprices;

import com.christopherrons.common.model.refdata.HistoricalPrice;
import com.christopherrons.common.model.refdata.HistoricalPriceCollection;
import com.christopherrons.refdataservice.historicalprices.enums.YahooApiSymbolEnum;
import com.christopherrons.refdataservice.historicalprices.model.HistoricalPriceResponse;
import com.christopherrons.refdataservice.historicalprices.model.YahooHistoricalPrices;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

import static com.christopherrons.common.math.probability.ProbabilityCalculationUtils.createNormalDistribution;
import static com.christopherrons.common.requests.HttpClient.getRequestToEntity;
import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class HistoricalPriceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(HistoricalPriceService.class);
    private static final String BASE_URL = "https://yfapi.net/v8/finance/spark";
    private static final HttpHeaders HEADERS = new HttpHeaders();
    private static final int UPDATE_INTERVAL_IN_DAYS = 365;
    private final int nrOfYearsOfHistoricalData = 5;
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
                LOGGER.warn("Fetching historical data failed. Using backup!" + e);
                StringBuilder backupFile = new StringBuilder();
                Resource resource = new ClassPathResource("backup_historical_prices.json");
                Scanner in = new Scanner(resource.getInputStream());
                while (in.hasNext()) {
                    backupFile.append(in.next());
                }
                in.close();
                var backupResponse = mapper.readValue(backupFile.toString(), YahooHistoricalPrices.class);
                historicalPriceCollection = createHistoricalPriceCollection(backupResponse);
            }
        }
        return historicalPriceCollection;
    }

    private boolean updateHistoricalData(final LocalDate currentTime) {
        return historicalPriceCollection == null || DAYS.between(lastUpdateTime, currentTime) >= UPDATE_INTERVAL_IN_DAYS;
    }

    private HistoricalPriceCollection requestHistoricalData() throws URISyntaxException {
        URI uri = new URI(String.format("%s?interval=%s&range=%s&symbols=%s", BASE_URL, "1d", nrOfYearsOfHistoricalData + "y", YahooApiSymbolEnum.API_SYMBOLS));
        YahooHistoricalPrices responses = getRequestToEntity(uri, HEADERS, YahooHistoricalPrices.class);
        return createHistoricalPriceCollection(responses);
    }

    private HistoricalPriceCollection createHistoricalPriceCollection(final YahooHistoricalPrices responses) {
        Map<String, HistoricalPrice> instrumentIdToHistoricalDataItem = new ConcurrentHashMap<>();
        for (HistoricalPriceResponse response : responses.getResponses()) {
            if (response != null && !response.isEmpty()) {
                HistoricalPrice historicalPrice = createHistoricalDataItem(response.getClose(), response.createReturns(), response.getTimestamp());
                instrumentIdToHistoricalDataItem.put(YahooApiSymbolEnum.apiSymbolToInstrumendId(response.getSymbol()), historicalPrice);
            }
        }
        return new HistoricalPriceCollection(instrumentIdToHistoricalDataItem);
    }

    private HistoricalPrice createHistoricalDataItem(final List<Double> closingPrices,
                                                     final List<Double> returns,
                                                     final List<Long> timeStamp) {
        addMissingDates(closingPrices, returns, timeStamp);
        return new HistoricalPrice(closingPrices);
    }

    private void addMissingDates(final List<Double> closingPrices,
                                 final List<Double> returns,
                                 final List<Long> timeStamp) {
        var returnsNormalDistribution = createNormalDistribution(returns);
        var lastUpdateDate = lastUpdateTime.atStartOfDay();
        var startDate = lastUpdateDate.minusYears(nrOfYearsOfHistoricalData);
        long daysBetween = DAYS.between(startDate, lastUpdateDate);
        for (int days = 0; days < daysBetween; days++) {
            long currentDate = startDate.plusDays(days).toEpochSecond(ZoneOffset.UTC);
            if (currentDate != timeStamp.get(days)) {
                timeStamp.add(days, currentDate);
                closingPrices.add(days, (1 + returnsNormalDistribution.sample()) * closingPrices.get(days));
            }
        }
    }

    public static void main(String[] args) throws IOException {
        var t = new HistoricalPriceService();
        t.getHistoricalData();
    }
}