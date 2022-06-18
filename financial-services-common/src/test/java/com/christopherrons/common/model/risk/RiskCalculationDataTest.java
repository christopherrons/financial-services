package com.christopherrons.common.model.risk;

import com.christopherrons.common.api.refdata.Instrument;
import com.christopherrons.common.enums.marketdata.MarketDataFeedEnum;
import com.christopherrons.common.enums.marketdata.TradingPairEnum;
import com.christopherrons.common.enums.pricing.MarginPriceMethodEnum;
import com.christopherrons.common.enums.refdata.InstrumentTypeEnum;
import com.christopherrons.common.model.pricing.MarginPrice;
import com.christopherrons.common.model.pricing.PriceCollectionItem;
import com.christopherrons.common.model.refdata.*;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static testutils.TestAssertionUtils.*;

class RiskCalculationDataTest {

    private static final MarketDataFeedEnum MARKET_DATA_FEED_ENUM = MarketDataFeedEnum.BITSTAMP;
    private static final double DELTA = 0.000001;
    private static final Participant PARTICIPANT_1 = new Participant(new Member("MEMBER"), new User("ONE", "LAST"));
    private static final Participant PARTICIPANT_2 = new Participant(new Member("MEMBER"), new User("TWO", "LAST"));
    private static final Instrument INSTRUMENT_1 = Instrument.createInstrument(InstrumentTypeEnum.STOCK, TradingPairEnum.XRP_USD);
    private static final Instrument INSTRUMENT_2 = Instrument.createInstrument(InstrumentTypeEnum.STOCK, TradingPairEnum.BTC_USD);
    private static final MarginPrice MARGIN_PRICE_1 = new MarginPrice(INSTRUMENT_1.getInstrumentId(), 10, MarginPriceMethodEnum.LAST_TRADED);
    private static final MarginPrice MARGIN_PRICE_2 = new MarginPrice(INSTRUMENT_2.getInstrumentId(), 20, MarginPriceMethodEnum.LAST_TRADED);
    private static final HistoricalPrice PRICE_HISTORICAL_1 = new HistoricalPrice(List.of(10.0, 11.0, 12.0, 11.0));
    private static final HistoricalPrice PRICE_HISTORICAL_2 = new HistoricalPrice(List.of(20.0, 21.0, 22.0, 21.0));
    private Portfolio portfolio = new Portfolio(PARTICIPANT_1);
    private Map<String, PriceCollectionItem> instrumentIdToPriceCollectionItem = new HashMap<>();
    private RiskCalculationData riskCalculationData;


    @BeforeEach
    void init() {
        portfolio.updatePortfolio(INSTRUMENT_1.getInstrumentId(), 10, true);
        portfolio.updatePortfolio(INSTRUMENT_2.getInstrumentId(), 10, true);

        instrumentIdToPriceCollectionItem.put(INSTRUMENT_1.getInstrumentId(),
                new PriceCollectionItem(INSTRUMENT_1.getInstrumentId(), MARGIN_PRICE_1, PRICE_HISTORICAL_1));
        instrumentIdToPriceCollectionItem.put(INSTRUMENT_2.getInstrumentId(),
                new PriceCollectionItem(INSTRUMENT_2.getInstrumentId(), MARGIN_PRICE_2, PRICE_HISTORICAL_2));

        riskCalculationData = new RiskCalculationData(portfolio, instrumentIdToPriceCollectionItem);

    }

    @Test
    void testNumberOfPositions() {
        assertEquals(2, riskCalculationData.getNumberOfPositions());
    }

    @Test
    void testPortfolioValue() {
        assertDouble(300.0, riskCalculationData.getPortfolioValue(), DELTA);
    }

    @Test
    void testPositionWeights() {
        assertEquals(new ArrayRealVector().append(2 / 3.0).append(1 / 3.0), riskCalculationData.getPositionWeights());
    }

    @Test
    void testRelativeReturns() {
        var matrix = new Array2DRowRealMatrix(3, 2);
        matrix.addToEntry(0, 1, (11 - 10) / 10.0);
        matrix.addToEntry(1, 1, (12 - 11) / 11.0);
        matrix.addToEntry(2, 1, (11 - 12) / 12.0);

        matrix.addToEntry(0, 0, (21 - 20) / 20.0);
        matrix.addToEntry(1, 0, (22 - 21) / 21.0);
        matrix.addToEntry(2, 0, (21 - 22) / 22.0);

        assertDoubleMatrix(matrix,
                riskCalculationData.getRelativeReturnsMatrix(),
                DELTA);
    }

    @Test
    void testPositionReturnMean() {
        assertDoubleVector(new ArrayRealVector().append(0.017388167388167387).append(0.03585858585858586),
                riskCalculationData.getPositionReturnMeans(),
                DELTA);
    }

    @Test
    void testPositionReturnVariance() {
        assertDoubleVector(new ArrayRealVector().append(0.0029633222).append(0.0106756964),
                riskCalculationData.getPositionReturnVariances(),
                DELTA);
    }

    @Test
    void testPositionReturnCovariance() {
        var matrix = new Array2DRowRealMatrix(2, 2);
        matrix.addToEntry(0, 0, 0.00296332215163384);
        matrix.addToEntry(1, 0, 0.005623168918623463);

        matrix.addToEntry(0, 1, 0.005623168918623463);
        matrix.addToEntry(1, 1, 0.010675696357514537);
        assertDouble(riskCalculationData.getPositionReturnVariance(INSTRUMENT_2.getInstrumentId()),
                riskCalculationData.getPositionReturnCovarianceMatrix().getEntry(0, 0),
                DELTA);
        assertDouble(riskCalculationData.getPositionReturnVariance(INSTRUMENT_1.getInstrumentId()),
                riskCalculationData.getPositionReturnCovarianceMatrix().getEntry(1, 1),
                DELTA);
        assertDouble(riskCalculationData.getPositionReturnVariances().getEntry(1),
                riskCalculationData.getPositionReturnCovarianceMatrix().getEntry(1, 1),
                DELTA);
        assertDouble(riskCalculationData.getPositionReturnVariances().getEntry(0),
                riskCalculationData.getPositionReturnCovarianceMatrix().getEntry(0, 0),
                DELTA);
        assertDouble(riskCalculationData.getPositionReturnVariances().getEntry(1),
                riskCalculationData.getPositionReturnCovarianceMatrix().getEntry(1, 1),
                DELTA);
        assertDoubleMatrix(matrix,
                riskCalculationData.getPositionReturnCovarianceMatrix(),
                DELTA);
    }

    @Test
    void testPositionReturnCorrelation() {
        var matrix = new Array2DRowRealMatrix(2, 2);
        matrix.addToEntry(0, 0, 1);
        matrix.addToEntry(1, 0, 0.9999999999999999);

        matrix.addToEntry(0, 1, 0.9999999999999999);
        matrix.addToEntry(1, 1, 1);
        assertDoubleMatrix(matrix, riskCalculationData.getPositionReturnCorrelationMatrix(), DELTA);

    }

}