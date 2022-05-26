package com.christopherrons.refdata.historicalprices;

import com.christopherrons.marketdata.common.enums.subscription.TradingPairEnum;
import com.christopherrons.refdata.historicalprices.model.HistoricalPrice;
import com.christopherrons.refdata.historicalprices.model.HistoricalPriceCollection;
import com.christopherrons.refdata.instrument.api.Instrument;
import com.christopherrons.refdata.instrument.enums.InstrumentTypeEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import static com.christopherrons.common.requests.HttpClient.requestGET;
import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class HistoricalPriceService {

    private static final Logger LOGGER = Logger.getLogger(HistoricalPriceService.class.getName());
    private static final String BASE_URL = "https://yfapi.net/v8/finance/spark";
    private static final Map<String, String> headerToValue = Map.of("X-API-KEY", "WTYBCiVjhh6zZlqjlhuBx3RTpCwZkv7W99FCYyNo");
    private static final int UPDATE_INTERVAL_IN_DAYS = 1;
    private static final ObjectMapper mapper = new ObjectMapper();
    private HistoricalPriceCollection historicalPriceCollection = null;
    private LocalDate lastUpdateTime = LocalDate.now();

    private enum ApiSymbolEnum {
        BTC_USD("BTC-USD", Instrument.createInstrument(InstrumentTypeEnum.STOCK, TradingPairEnum.BTC_USD).getInstrumentId()),
        XRP_USD("XRP-USD", Instrument.createInstrument(InstrumentTypeEnum.STOCK, TradingPairEnum.XRP_USD).getInstrumentId());

        private final String apiSymbolName;
        private final String instrumentId;

        ApiSymbolEnum(String apiSymbolName, String instrumentId) {
            this.apiSymbolName = apiSymbolName;
            this.instrumentId = instrumentId;
        }

    }

    public HistoricalPriceCollection getHistoricalData() throws IOException {
        LocalDate currentTime = LocalDate.now();
        if (updateHistoricalData(currentTime)) {
            LOGGER.info("Updating Historical Ref Data: Previous updated on: " + lastUpdateTime.toString());
            lastUpdateTime = currentTime;
            historicalPriceCollection = requestHistoricalData();
        }
        return historicalPriceCollection;
    }

    private boolean updateHistoricalData(final LocalDate currentTime) {
        return historicalPriceCollection == null || DAYS.between(lastUpdateTime, currentTime) >= UPDATE_INTERVAL_IN_DAYS;
    }

    private HistoricalPriceCollection requestHistoricalData() throws IOException {
        Map<String, HistoricalPrice> instrumentIdToHistoricalDataItem = new ConcurrentHashMap<>();
        for (ApiSymbolEnum apiSymbolEnum : ApiSymbolEnum.values()) {
            URL url = new URL(String.format("%s?interval=%s&range=%s&symbols=%s", BASE_URL, "1d", "5y", apiSymbolEnum.apiSymbolName));
            String jsonResponse = requestGET(url, headerToValue);
            HistoricalPrice historicalPrice = createHistoricalDataItem(jsonResponse, apiSymbolEnum.apiSymbolName);
            instrumentIdToHistoricalDataItem.put(apiSymbolEnum.instrumentId, historicalPrice);
        }

        return new HistoricalPriceCollection(instrumentIdToHistoricalDataItem);
    }

    private HistoricalPrice createHistoricalDataItem(String jsonResponse, String symbol) throws JsonProcessingException {
        String closingPricesString = mapper.readValue(jsonResponse, JsonNode.class).get(symbol).get("close").toString();
        List<Double> closingPrices = Arrays.stream(closingPricesString.replace("[", "").replace("]", "").split(","))
                .map(Double::valueOf)
                .toList();
        return new HistoricalPrice(closingPrices); //TODO: Check order of closing prices ascending/descending
    }
    
}
