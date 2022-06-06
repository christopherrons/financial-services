package com.christopherrons.common.model.refdata;

import java.util.ArrayList;
import java.util.List;

import static com.christopherrons.common.math.probability.ProbabilityCalculationUtils.calculateMean;
import static com.christopherrons.common.math.probability.ProbabilityCalculationUtils.calculateVariance;


public class HistoricalPrice {

    private final List<Double> closingPrices;
    private final List<Double> dailyRelativeReturns;
    private final double relativeReturnMean;
    private final double relativeReturnVariance;

    private final double relativeReturnStd;

    public HistoricalPrice(List<Double> closingPrices) {
        this.closingPrices = closingPrices;
        this.dailyRelativeReturns = createReturns(closingPrices);
        this.relativeReturnMean = calculateMean(dailyRelativeReturns);
        this.relativeReturnVariance = calculateVariance(dailyRelativeReturns, relativeReturnMean);
        this.relativeReturnStd = Math.sqrt(relativeReturnVariance);
    }

    public List<Double> getClosingPrices() {
        return closingPrices;
    }

    private List<Double> createReturns(final List<Double> closingPrices) {
        List<Double> returns = new ArrayList<>();
        for (int i = 0; i < closingPrices.size() - 1; i++) {
            double dailyReturnAbsolut = closingPrices.get(i + 1) - closingPrices.get(i);
            double dailyReturnRelative = dailyReturnAbsolut / closingPrices.get(i);
            returns.add(dailyReturnRelative);
        }
        return returns;
    }

    public List<Double> getDailyRelativeReturns() {
        return dailyRelativeReturns;
    }

    public double getRelativeReturnMean() {
        return relativeReturnMean;
    }

    public double getRelativeReturnVariance() {
        return relativeReturnVariance;
    }

    public double getRelativeReturnStd() {
        return relativeReturnStd;
    }
}
