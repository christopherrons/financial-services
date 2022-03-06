package com.christopherrons.refdata.yield.model;

import java.time.LocalDate;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

public class YieldRefData {

    public static final double DAYS_PER_YEAR = 365.0;
    private final LocalDate startDate;
    private final double[] maturities;
    private final List<LocalDate> maturityDates;
    private final double[] yields;

    public YieldRefData(LocalDate startDate, List<LocalDate> maturityDates, double[] yields) {
        this.startDate = startDate;
        this.maturityDates = maturityDates;
        this.yields = yields;
        this.maturities = createMaturitiesDecimal(maturityDates);
    }

    private double[] createMaturitiesDecimal(final List<LocalDate> maturityDates) {
        double[] localMaturities = new double[maturityDates.size()];
        for (int i = 0; i < maturityDates.size(); i++) {
            localMaturities[i] = DAYS.between(startDate, maturityDates.get(i)) / DAYS_PER_YEAR;
        }
        return localMaturities;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public double[] getMaturities() {
        return maturities;
    }

    public double[] getYields() {
        return yields;
    }

    public List<LocalDate> getMaturityDates() {
        return maturityDates;
    }
}
