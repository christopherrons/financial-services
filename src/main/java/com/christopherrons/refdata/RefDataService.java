package com.christopherrons.refdata;

import com.christopherrons.common.broadcasts.MatchingEngineBroadcast;
import com.christopherrons.common.broadcasts.OrderEventBroadcast;
import com.christopherrons.marketdata.api.MarketDataOrder;
import com.christopherrons.marketdata.api.MarketDataTrade;
import com.christopherrons.refdata.historicalprices.HistoricalPriceService;
import com.christopherrons.refdata.historicalprices.model.HistoricalPriceCollection;
import com.christopherrons.refdata.instrument.InstrumentRefDataService;
import com.christopherrons.refdata.instrument.api.Instrument;
import com.christopherrons.refdata.instrument.enums.InstrumentTypeEnum;
import com.christopherrons.refdata.participant.ParticipantRefDataService;
import com.christopherrons.refdata.portfolio.PortfolioRefDataService;
import com.christopherrons.refdata.portfolio.model.Portfolio;
import com.christopherrons.refdata.yield.YieldRefDataService;
import com.christopherrons.refdata.yield.model.YieldRefData;
import com.christopherrons.tradingengine.matchingengine.model.MatchingEngineResult;
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

    @Autowired
    public RefDataService(InstrumentRefDataService instrumentRefDataService,
                          ParticipantRefDataService participantRefDataService,
                          PortfolioRefDataService portfolioRefDataService,
                          YieldRefDataService yieldRefDataService,
                          HistoricalPriceService historicalPriceService) {
        this.instrumentRefDataService = instrumentRefDataService;
        this.participantRefDataService = participantRefDataService;
        this.portfolioRefDataService = portfolioRefDataService;
        this.yieldRefDataService = yieldRefDataService;
        this.historicalPriceService = historicalPriceService;
    }

    @EventListener
    public void onOrderEvent(OrderEventBroadcast event) {
        for (MarketDataOrder order : event.getOrders()) {
            instrumentRefDataService.addInstrument(order.getInstrument());
            participantRefDataService.addParticipant(order.getParticipant());
        }
    }

    @EventListener
    public void onMatchingEngineEvent(MatchingEngineBroadcast matchingEngineBroadcast) {
        List<MatchingEngineResult> matchingEngineResult = matchingEngineBroadcast.getMatchingEngineResult();
        for (MatchingEngineResult result : matchingEngineResult) {
            for (MarketDataTrade trade : result.getTrades()) {
                portfolioRefDataService.updatePortfolioFromTrade(trade);
            }
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
}
