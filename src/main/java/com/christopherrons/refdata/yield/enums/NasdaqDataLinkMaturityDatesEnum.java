package com.christopherrons.refdata.yield.enums;

import java.time.LocalDate;
import java.util.function.Function;
import java.util.function.UnaryOperator;

// https://data.nasdaq.com/data/USTREASURY/YIELD-treasury-yield-curve-rates
public enum NasdaqDataLinkMaturityDatesEnum {
    ONE_MONTH("1 MO", (LocalDate startDate) -> startDate.plusMonths(1)),
    TWO_MONTH("2 MO", (LocalDate startDate) -> startDate.plusMonths(2)),
    THREE_MONTH("3 MO", (LocalDate startDate) -> startDate.plusMonths(3)),
    SIX_MONTH("6 MO", (LocalDate startDate) -> startDate.plusMonths(6)),
    ONE_YEAR("1 YR", (LocalDate startDate) -> startDate.plusYears(1)),
    TWO_YEAR("2 YR", (LocalDate startDate) -> startDate.plusYears(2)),
    THREE_YEAR("3 YR", (LocalDate startDate) -> startDate.plusYears(3)),
    FIVE_YEAR("5 YR", (LocalDate startDate) -> startDate.plusYears(5)),
    SEVEN_YEAR("7 YR", (LocalDate startDate) -> startDate.plusYears(7)),
    TEN_YEAR("10 YR", (LocalDate startDate) -> startDate.plusYears(10)),
    TWENTY_YEAR("20 YR", (LocalDate startDate) -> startDate.plusYears(20)),
    THIRTY_YEAR("30 YR", (LocalDate startDate) -> startDate.plusYears(30));

    private final String maturityName;
    private final UnaryOperator<LocalDate> maturityDateBuilder;

    NasdaqDataLinkMaturityDatesEnum(String maturityName, UnaryOperator<LocalDate> maturityDateBuilder) {
        this.maturityName = maturityName;
        this.maturityDateBuilder = maturityDateBuilder;
    }

    public String getMaturityName() {
        return maturityName;
    }

    public Function<LocalDate, LocalDate> getMaturityDateBuilder() {
        return maturityDateBuilder;
    }

    public static LocalDate createMaturityDate(final LocalDate startDate, final String maturityString) {
        for (NasdaqDataLinkMaturityDatesEnum maturityDatesEnum : NasdaqDataLinkMaturityDatesEnum.values()) {
            if (maturityString.equals(maturityDatesEnum.getMaturityName())) {
                return maturityDatesEnum.getMaturityDateBuilder().apply(startDate);
            }
        }
        return startDate;
    }
}
