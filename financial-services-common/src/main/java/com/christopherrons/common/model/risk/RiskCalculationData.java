package com.christopherrons.common.model.risk;

import com.christopherrons.common.model.pricing.PriceCollectionItem;
import com.christopherrons.common.model.refdata.Participant;
import com.christopherrons.common.model.refdata.Portfolio;
import com.christopherrons.common.model.refdata.Position;
import org.apache.commons.math3.distribution.MultivariateNormalDistribution;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.christopherrons.common.math.probability.ProbabilityCalculationUtils.*;

public class RiskCalculationData {

    private final Participant participant;
    private final double portfolioValue;
    private final Map<String, Position> instrumentIdToPosition;
    private final Map<String, PriceCollectionItem> instrumentIdToPriceCollectionItem;

    private final RealMatrix relativeReturnsMatrix;
    private final RealVector positionWeights;
    private final int numberOfPositions;
    private final RealVector positionReturnMeans;
    private final RealVector positionReturnVariance;

    private final RealMatrix positionReturnCovarianceMatrix;
    private final RealMatrix positionReturnCorrelationMatrix;

    private final MultivariateNormalDistribution returnDistribution;


    public RiskCalculationData(Portfolio portfolio, Map<String, PriceCollectionItem> instrumentIdToPriceCollectionItem) {
        this.participant = portfolio.getParticipant();
        this.instrumentIdToPosition = portfolio.getInstrumentIdToPosition();
        this.instrumentIdToPriceCollectionItem = instrumentIdToPriceCollectionItem;
        this.numberOfPositions = instrumentIdToPosition.values().size();
        this.portfolioValue = calculatePortfolioValue();
        this.positionWeights = buildPositionWeightVector();
        this.positionReturnMeans = buildPositionMeanVector();
        this.positionReturnVariance = buildPositionVarianceVector();
        this.relativeReturnsMatrix = buildRelativeReturnMatrix(new ArrayList<>(instrumentIdToPosition.values()));
        this.positionReturnCovarianceMatrix = buildPositionCovarianceMatrix();
        this.positionReturnCorrelationMatrix = buildPositionCorrelationMatrix();
        this.returnDistribution = createMultivariateNormalDistribution(positionReturnMeans.toArray(), positionReturnCovarianceMatrix.getData());
    }

    private double calculatePortfolioValue() {
        return instrumentIdToPosition.values().stream()
                .mapToDouble(position -> calculatePositionValue(position.getVolume(), position.getInstrumentId()))
                .sum();
    }

    private RealVector buildPositionWeightVector() {
        final double[] weights = instrumentIdToPosition.values().stream()
                .mapToDouble(position -> calculatePositionWeight(position.getInstrumentId()))
                .toArray();
        return new ArrayRealVector(weights);
    }

    private RealVector buildPositionMeanVector() {
        final double[] means = instrumentIdToPosition.values().stream()
                .mapToDouble(position -> getPositionMean(position.getInstrumentId()))
                .toArray();
        return new ArrayRealVector(means);
    }

    private RealVector buildPositionVarianceVector() {
        final double[] variance = instrumentIdToPosition.values().stream()
                .mapToDouble(position -> getPositionReturnVariance(position.getInstrumentId()))
                .toArray();
        return new ArrayRealVector(variance);
    }

    private RealMatrix buildRelativeReturnMatrix(List<Position> positions) {
        int numberOfReturns = getPositionRelativeReturns(positions.get(0).getInstrumentId()).size();
        RealMatrix matrix = new Array2DRowRealMatrix(numberOfReturns, numberOfPositions);
        for (int column = 0; column < numberOfPositions; column++) {
            List<Double> relativeReturns = getPositionRelativeReturns(positions.get(column).getInstrumentId());
            for (int row = 0; row < relativeReturns.size(); row++) {
                matrix.addToEntry(row, column, relativeReturns.get(row));
            }
        }
        return matrix;
    }

    private RealMatrix buildPositionCovarianceMatrix() {
        RealMatrix matrix = new Array2DRowRealMatrix(numberOfPositions, numberOfPositions);
        for (int column = 0; column < numberOfPositions; column++) {
            RealVector relativeReturnsFirstPosition = relativeReturnsMatrix.getColumnVector(column);
            matrix.addToEntry(column, column, positionReturnVariance.getEntry(column));
            for (int row = column + 1; row < numberOfPositions; row++) {
                RealVector relativeReturnsSecondPosition = relativeReturnsMatrix.getColumnVector(row);
                double covariance = calculateCovariance(relativeReturnsFirstPosition, relativeReturnsSecondPosition,
                        positionReturnMeans.getEntry(column), positionReturnMeans.getEntry(row));
                matrix.addToEntry(row, column, covariance);
                matrix.addToEntry(column, row, covariance);
            }
        }
        return matrix;
    }

    private RealMatrix buildPositionCorrelationMatrix() {
        RealMatrix matrix = new Array2DRowRealMatrix(numberOfPositions, numberOfPositions);
        for (int column = 0; column < numberOfPositions; column++) {
            double correlation = calculateCorrelation(positionReturnCovarianceMatrix.getEntry(column, column),
                    positionReturnVariance.getEntry(column), positionReturnVariance.getEntry(column));
            matrix.addToEntry(column, column, correlation);
            for (int row = column + 1; row < numberOfPositions; row++) {
                correlation = calculateCorrelation(positionReturnCovarianceMatrix.getEntry(row, column),
                        positionReturnVariance.getEntry(column), positionReturnVariance.getEntry(row));
                matrix.addToEntry(row, column, correlation);
                matrix.addToEntry(column, row, correlation);
            }
        }
        return matrix;
    }

    public Participant getParticipant() {
        return participant;
    }

    public Collection<Position> getPositions() {
        return instrumentIdToPosition.values();
    }

    public Collection<String> getPortfolioInstrumentIds() {
        return instrumentIdToPosition.keySet();
    }

    public List<Double> getRelativeDailyReturns(final String instrumentId) {
        return instrumentIdToPriceCollectionItem.get(instrumentId).historicalPrice().getDailyRelativeReturns();
    }

    public double calculatePositionWeight(final String instrumentId) {
        return (calculatePositionValue(instrumentIdToPosition.get(instrumentId).getVolume(), instrumentId)) / portfolioValue;
    }

    private double calculatePositionValue(final double volume, final String instrumentId) {
        return volume * instrumentIdToPriceCollectionItem.get(instrumentId).marginPrice().price();
    }

    private List<Double> getPositionRelativeReturns(final String instrumentId) {
        return instrumentIdToPriceCollectionItem.get(instrumentId).historicalPrice().getDailyRelativeReturns();
    }

    public double getPositionMean(final String instrumentId) {
        return instrumentIdToPriceCollectionItem.get(instrumentId).historicalPrice().getRelativeReturnMean();
    }

    public double getPositionReturnVariance(final String instrumentId) {
        return instrumentIdToPriceCollectionItem.get(instrumentId).historicalPrice().getRelativeReturnVariance();
    }

    public double getPortfolioValue() {
        return portfolioValue;
    }

    public RealMatrix getRelativeReturnsMatrix() {
        return relativeReturnsMatrix;
    }

    public RealVector getPositionWeights() {
        return positionWeights;
    }

    public int getNumberOfPositions() {
        return numberOfPositions;
    }

    public RealVector getPositionReturnMeans() {
        return positionReturnMeans;
    }

    public RealVector getPositionReturnVariances() {
        return positionReturnVariance;
    }

    public RealMatrix getPositionReturnCovarianceMatrix() {
        return positionReturnCovarianceMatrix;
    }

    public MultivariateNormalDistribution getReturnDistribution() {
        return returnDistribution;
    }

    public RealVector getPositionReturnStd() {
        return new ArrayRealVector(returnDistribution.getStandardDeviations());
    }

    public int getNumberOfSamples() {
        return relativeReturnsMatrix.getRowDimension();
    }

    public RealMatrix getPositionReturnCorrelationMatrix() {
        return positionReturnCorrelationMatrix;
    }
}