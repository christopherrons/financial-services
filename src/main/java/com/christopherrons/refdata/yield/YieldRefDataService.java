package com.christopherrons.refdata.yield;

import com.christopherrons.refdata.yield.model.YieldRefData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.christopherrons.common.requests.HttpClient.getRequest;
import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class YieldRefDataService {
    private static final String BASE_URL = "https://data.nasdaq.com/api/v3/datasets/USTREASURY/YIELD.json/";
    private static final String API_KEY = "bG3_NQF6ohqTN2PVTFgy";
    private static final int UPDATE_INTERVAL_IN_DAYS = 1;
    private static final ObjectMapper mapper = new ObjectMapper();
    private YieldRefData yieldRefData = null;
    private LocalDate lastUpdateTime;

    public YieldRefData getYieldRefData() throws IOException {
        LocalDate currentTime = LocalDate.now();
        if (updateYield(currentTime)) {
            lastUpdateTime = currentTime;
            return getYield(lastUpdateTime.toString());
        }
        return yieldRefData;
    }

    private boolean updateYield(final LocalDate currentTime) {
        return yieldRefData == null || DAYS.between(lastUpdateTime, currentTime) >= UPDATE_INTERVAL_IN_DAYS;
    }

    private YieldRefData getYield(final String requestDate) throws IOException {
        URL url = new URL(String.format("%s?start_date=%s?end_date=%s?api_key=%s", BASE_URL, requestDate, requestDate, API_KEY));
        String jsonResponse = getRequest(url);

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
                .map(maturityString -> createMaturityDate(LocalDate.parse(startDate), maturityString))
                .collect(Collectors.toList());
    }

    public LocalDate createMaturityDate(final LocalDate startDate, final String maturityString) {
        if (maturityString.equals("1 MO")) {
            return startDate.plusMonths(1);
        }
        if (maturityString.equals("2 MO")) {
            return startDate.plusMonths(2);
        }
        if (maturityString.equals("3 MO")) {
            return startDate.plusMonths(3);
        }
        if (maturityString.equals("6 MO")) {
            return startDate.plusMonths(6);
        }
        if (maturityString.equals("1 YR")) {
            return startDate.plusYears(1);
        }
        if (maturityString.equals("2 YR")) {
            return startDate.plusYears(2);
        }
        if (maturityString.equals("3 YR")) {
            return startDate.plusYears(3);
        }
        if (maturityString.equals("5 YR")) {
            return startDate.plusYears(5);
        }
        if (maturityString.equals("7 YR")) {
            return startDate.plusYears(7);
        }
        if (maturityString.equals("10 YR")) {
            return startDate.plusYears(10);
        }
        if (maturityString.equals("20 YR")) {
            return startDate.plusYears(20);
        }
        if (maturityString.equals("30 YR")) {
            return startDate.plusYears(30);
        }
        return startDate;
    }
}
