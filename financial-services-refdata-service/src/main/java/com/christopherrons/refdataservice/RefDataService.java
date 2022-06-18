package com.christopherrons.refdataservice;

import com.christopherrons.common.api.marketdata.MarketDataOrder;
import com.christopherrons.common.api.marketdata.MarketDataTrade;
import com.christopherrons.common.api.refdata.Instrument;
import com.christopherrons.common.broadcasts.OrderEventBroadcast;
import com.christopherrons.common.broadcasts.TradeEventBroadcast;
import com.christopherrons.common.enums.refdata.InstrumentTypeEnum;
import com.christopherrons.common.model.refdata.HistoricalPriceCollection;
import com.christopherrons.common.model.refdata.Portfolio;
import com.christopherrons.common.model.refdata.YieldRefData;
import com.christopherrons.refdataservice.historicalprices.HistoricalPriceService;
import com.christopherrons.refdataservice.instrument.InstrumentRefDataService;
import com.christopherrons.refdataservice.participant.ParticipantRefDataService;
import com.christopherrons.refdataservice.statistics.StatisticsService;
import com.christopherrons.refdataservice.statistics.model.OrderbookStatistics;
import com.christopherrons.refdataservice.yield.YieldRefDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Service
public class RefDataService {

    private final InstrumentRefDataService instrumentRefDataService;
    private final ParticipantRefDataService participantRefDataService;
    private final YieldRefDataService yieldRefDataService;
    private final HistoricalPriceService historicalPriceService;
    private final StatisticsService statisticsService;

    @Autowired
    public RefDataService(InstrumentRefDataService instrumentRefDataService,
                          ParticipantRefDataService participantRefDataService,
                          YieldRefDataService yieldRefDataService,
                          HistoricalPriceService historicalPriceService,
                          StatisticsService statisticsService) {
        this.instrumentRefDataService = instrumentRefDataService;
        this.participantRefDataService = participantRefDataService;
        this.yieldRefDataService = yieldRefDataService;
        this.historicalPriceService = historicalPriceService;
        this.statisticsService = statisticsService;
    }

    @EventListener
    public void onOrderEvent(OrderEventBroadcast event) {
        for (MarketDataOrder order : event.getOrders()) {
            instrumentRefDataService.addInstrument(order.getInstrument());
            participantRefDataService.handleOrder(order);
            statisticsService.updateStatistics(order);
        }
    }

    @EventListener
    public void onTradeEvent(TradeEventBroadcast tradeEventBroadcast) {
        for (MarketDataTrade trade : tradeEventBroadcast.getTrades()) {
            participantRefDataService.handleTrade(trade);
            statisticsService.updateStatistics(trade);
        }
    }

    public List<Instrument> getInstruments() {
        return instrumentRefDataService.getInstruments();
    }

    public List<String> getInstrumentsByType(final InstrumentTypeEnum instrumentTypeEnum) {
        return instrumentRefDataService.getInstrumentsByType(instrumentTypeEnum);
    }

    public List<InstrumentTypeEnum> getInstrumentTypes() {
        return instrumentRefDataService.getInstrumentTypes();
    }

    public YieldRefData getYieldRefData() {
        return yieldRefDataService.getYieldRefData();
    }

    public HistoricalPriceCollection getHistoricalData() throws IOException {
        return historicalPriceService.getHistoricalData();
    }

    public List<Portfolio> getPortfolios() {
        return participantRefDataService.getPortfolios();
    }

    public OrderbookStatistics getOrderbookStatistics(final String orderbookId) {
        return statisticsService.getOrderbookStatistics(orderbookId);
    }

}
