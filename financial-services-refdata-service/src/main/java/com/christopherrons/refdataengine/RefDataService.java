package com.christopherrons.refdataengine;

import com.christopherrons.common.api.marketdata.MarketDataOrder;
import com.christopherrons.common.api.marketdata.MarketDataTrade;
import com.christopherrons.common.api.refdata.Instrument;
import com.christopherrons.common.broadcasts.OrderEventBroadcast;
import com.christopherrons.common.broadcasts.TradeEventBroadcast;
import com.christopherrons.common.enums.refdata.InstrumentTypeEnum;
import com.christopherrons.common.model.refdata.HistoricalPriceCollection;
import com.christopherrons.common.model.refdata.Portfolio;
import com.christopherrons.common.model.refdata.YieldRefData;
import com.christopherrons.refdataengine.historicalprices.HistoricalPriceService;
import com.christopherrons.refdataengine.instrument.InstrumentRefDataService;
import com.christopherrons.refdataengine.participant.ParticipantRefDataService;
import com.christopherrons.refdataengine.portfolio.PortfolioRefDataService;
import com.christopherrons.refdataengine.statistics.StatisticsService;
import com.christopherrons.refdataengine.statistics.model.OrderbookStatistics;
import com.christopherrons.refdataengine.yield.YieldRefDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class RefDataService {

    private final InstrumentRefDataService instrumentRefDataService;
    private final ParticipantRefDataService participantRefDataService;
    private final PortfolioRefDataService portfolioRefDataService;
    private final YieldRefDataService yieldRefDataService;
    private final HistoricalPriceService historicalPriceService;
    private final StatisticsService statisticsService;

    @Autowired
    public RefDataService(InstrumentRefDataService instrumentRefDataService,
                          ParticipantRefDataService participantRefDataService,
                          PortfolioRefDataService portfolioRefDataService,
                          YieldRefDataService yieldRefDataService,
                          HistoricalPriceService historicalPriceService,
                          StatisticsService statisticsService) {
        this.instrumentRefDataService = instrumentRefDataService;
        this.participantRefDataService = participantRefDataService;
        this.portfolioRefDataService = portfolioRefDataService;
        this.yieldRefDataService = yieldRefDataService;
        this.historicalPriceService = historicalPriceService;
        this.statisticsService = statisticsService;
    }

    @EventListener
    public void onOrderEvent(OrderEventBroadcast event) {
        for (MarketDataOrder order : event.getOrders()) {
            instrumentRefDataService.addInstrument(order.getInstrument());
            participantRefDataService.addParticipant(order.getParticipant());
            statisticsService.updateStatistics(order);
        }
    }

    @EventListener
    public void onTradeEvent(TradeEventBroadcast tradeEventBroadcast) {
        for (MarketDataTrade trade : tradeEventBroadcast.getTrades()) {
            portfolioRefDataService.updatePortfolioFromTrade(trade);
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

    public YieldRefData getYieldRefData() throws IOException {
        return yieldRefDataService.getYieldRefData();
    }

    public HistoricalPriceCollection getHistoricalData() throws IOException {
        return historicalPriceService.getHistoricalData();
    }

    public List<Portfolio> getPortfolios() {
        return portfolioRefDataService.getPortfolios();
    }

    public OrderbookStatistics getOrderbookStatistics(final String orderbookId) {
        return statisticsService.getOrderbookStatistics(orderbookId);
    }
}
