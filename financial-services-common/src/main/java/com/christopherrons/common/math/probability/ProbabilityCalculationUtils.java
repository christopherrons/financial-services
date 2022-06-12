package com.christopherrons.common.math.probability;

import org.apache.commons.math3.distribution.MultivariateNormalDistribution;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.stat.correlation.Covariance;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;

import java.util.List;

public class ProbabilityCalculationUtils {

    private ProbabilityCalculationUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static double calculateStandardDeviation(final List<Double> values) {
        return Math.sqrt(calculateVariance(values, calculateMean(values)));
    }

    public static double calculateStandardDeviation(final List<Double> values, final double mean) {
        return Math.sqrt(calculateVariance(values, mean));
    }

    public static double calculateVariance(final List<Double> values) {
        return calculateVariance(values, calculateMean(values));
    }

    public static double calculateVariance(final List<Double> values, final double mean) {
        return values.stream().mapToDouble(value -> Math.pow(value - mean, 2)).sum() / (values.size() - 1);
    }

    public static double calculateCovariance(final List<Double> firstValues,
                                             final List<Double> secondValues) {
        return calculateCovariance(firstValues, secondValues, calculateMean(firstValues), calculateMean(secondValues));
    }

    public static double calculateCovariance(final List<Double> firstValues,
                                             final List<Double> secondValues,
                                             final double meanFirstValues,
                                             final double meanSecondValues) {
        double covariance = 0;
        for (int i = 0; i < firstValues.size(); i++) {
            covariance = covariance + (firstValues.get(i) - meanFirstValues) * (secondValues.get(i) - meanSecondValues);
        }
        return covariance / firstValues.size();
    }

    public static double calculateCovariance(final RealVector firstValues,
                                             final RealVector secondValues,
                                             final double meanFirstValues,
                                             final double meanSecondValues) {
        return firstValues.mapSubtract(meanFirstValues).dotProduct(secondValues.mapSubtract(meanSecondValues)) / (firstValues.getDimension() - 1);
    }

    public static double calculateCorrelation(final double covariance,
                                              final double firstVariance,
                                              final double secondVariance) {
        return covariance / Math.sqrt(firstVariance * secondVariance);
    }

    public static double calculateMean(final List<Double> values) {
        return values.stream().mapToDouble(value -> value).sum() / values.size();
    }


    public static MultivariateNormalDistribution createMultivariateNormalDistribution(final double[] means, double[][] covariance) {
        return new MultivariateNormalDistribution(means, covariance);
    }

    public static RealMatrix createCovarianceMatrix(final RealMatrix data) {
        return new Covariance(data).getCovarianceMatrix();
    }

    public static RealMatrix createCorrelationMatrix(final RealMatrix data) {
        return new PearsonsCorrelation(data).getCorrelationMatrix();
    }
}
