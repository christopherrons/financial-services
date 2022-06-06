package com.christopherrons.common.math.probability;

public enum ConfidenceLevelEnum {

    NINTY_FIVE(0.95, 1.96);

    private final double confidenceLevel;
    private final double zscore;

    ConfidenceLevelEnum(double confidenceLevel, double zscore) {
        this.confidenceLevel = confidenceLevel;
        this.zscore = zscore;
    }

    public double getConfidenceLevel() {
        return confidenceLevel;
    }

    public double getZscore() {
        return zscore;
    }
}
