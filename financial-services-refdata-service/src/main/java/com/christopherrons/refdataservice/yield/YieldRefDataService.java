package com.christopherrons.refdataservice.yield;

import com.christopherrons.common.model.refdata.YieldRefData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

import static com.christopherrons.common.requests.HttpClient.requestGET;
import static com.christopherrons.refdataservice.yield.enums.NasdaqDataLinkMaturityDatesEnum.createMaturityDate;
import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class YieldRefDataService {
    private static final Logger LOGGER = Logger.getLogger(YieldRefDataService.class.getName());
    private static final String BASE_URL = "https://data.nasdaq.com/api/v3/datasets/USTREASURY/YIELD.json/";
    private static final String API_KEY = "Fm1uExMDkQBg8HKVwCDz";
    private static final int UPDATE_INTERVAL_IN_DAYS = 365;
    private static final ObjectMapper mapper = new ObjectMapper();
    private YieldRefData yieldRefData = null;
    private LocalDate lastUpdateTime = LocalDate.now();

    public YieldRefData getYieldRefData() {
        LocalDate currentTime = LocalDate.now();
        if (updateYield(currentTime)) {
            LOGGER.info("Updating Yield Ref Data: Previous updated on: " + lastUpdateTime.toString());
            lastUpdateTime = currentTime;
            try {
                yieldRefData = getYield(lastUpdateTime.toString());
            } catch (Exception e) {
                LOGGER.warning("Fetching yield data failed!");
            }
        }
        return yieldRefData;
    }

    private boolean updateYield(final LocalDate currentTime) {
        return yieldRefData == null || DAYS.between(lastUpdateTime, currentTime) >= UPDATE_INTERVAL_IN_DAYS;
    }

    private YieldRefData getYield(final String requestDate) throws IOException {
        URL url = new URL(String.format("%s?start_date=%s?end_date=%s?api_key=%s", BASE_URL, requestDate, requestDate, API_KEY));
        try {
            String jsonResponse = requestGET(url, new HashMap<>());
            return getYield(requestDate, jsonResponse);
        } catch (RuntimeException e) {
            LOGGER.warning("Fetching yield data failed. Using backup!");
            StringBuilder backupFile = new StringBuilder();
            Scanner in = new Scanner(new FileReader("/home/christopher/versioned/financial-services/financial-services-refdata-service/src/main/resources/backup_yield_data.json"));
            while (in.hasNext()) {
                backupFile.append(in.next());
            }
            in.close();
            return getYield("2022-02-04", backupFile.toString());
        }
    }

    private YieldRefData getYield(final String requestDate, final String jsonResponse) throws IOException {
        JsonNode yieldJsonObject = mapper.readValue(jsonResponse, JsonNode.class).get("dataset");
        String newestDate = yieldJsonObject.get("newest_available_date").asText();
        if (newestDate.equals(requestDate)) {
            return createYieldRefData(
                    requestDate,
                    yieldJsonObject.get("data").get(0).toString(),
                    yieldJsonObject.get("column_names").toString()
            );
        }

        return getYield(newestDate);
    }

    private YieldRefData createYieldRefData(final String startDate, final String yields, String maturities) throws JsonProcessingException {
        return new YieldRefData(
                LocalDate.parse(startDate),
                createMaturityDates(startDate, maturities),
                createYieldsAsDecimal(yields)
        );
    }

    private double[] createYieldsAsDecimal(final String yieldsJsonString) throws JsonProcessingException {
        List<String> yieldsAsStrings = mapper.readValue(yieldsJsonString, TypeFactory.defaultInstance().constructCollectionType(List.class, String.class));
        yieldsAsStrings.set(0, "0");
        return yieldsAsStrings.stream().mapToDouble(yieldString -> Double.parseDouble(yieldString) / 100).toArray();
    }

    private List<LocalDate> createMaturityDates(final String startDate, final String maturitiesJsonString) throws JsonProcessingException {
        List<String> maturitiesAsString = mapper.readValue(maturitiesJsonString, TypeFactory.defaultInstance().constructCollectionType(List.class, String.class));
        maturitiesAsString.set(0, startDate);
        return maturitiesAsString.stream()
                .map(maturityString -> createMaturityDate(LocalDate.parse(startDate), maturityString.replace(" ", "")))
                .toList();
    }
}
