package com.christopherrons.refdataservice.yield.enums;

import java.time.LocalDate;
import java.util.Map;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toMap;

// https://data.nasdaq.com/data/USTREASURY/YIELD-treasury-yield-curve-rates
public enum NasdaqDataLinkMaturityDatesEnum {
    ONE_MONTH("1MO", (LocalDate startDate) -> startDate.plusMonths(1)),
    TWO_MONTH("2MO", (LocalDate startDate) -> startDate.plusMonths(2)),
    THREE_MONTH("3MO", (LocalDate startDate) -> startDate.plusMonths(3)),
    SIX_MONTH("6MO", (LocalDate startDate) -> startDate.plusMonths(6)),
    ONE_YEAR("1YR", (LocalDate startDate) -> startDate.plusYears(1)),
    TWO_YEAR("2YR", (LocalDate startDate) -> startDate.plusYears(2)),
    THREE_YEAR("3YR", (LocalDate startDate) -> startDate.plusYears(3)),
    FIVE_YEAR("5YR", (LocalDate startDate) -> startDate.plusYears(5)),
    SEVEN_YEAR("7YR", (LocalDate startDate) -> startDate.plusYears(7)),
    TEN_YEAR("10YR", (LocalDate startDate) -> startDate.plusYears(10)),
    TWENTY_YEAR("20YR", (LocalDate startDate) -> startDate.plusYears(20)),
    THIRTY_YEAR("30YR", (LocalDate startDate) -> startDate.plusYears(30));

    private final String maturityName;
    private final UnaryOperator<LocalDate> maturityDateBuilder;
    private static final Map<String, Function<LocalDate, LocalDate>> VALUES_BY_IDENTIFIER =
            stream(NasdaqDataLinkMaturityDatesEnum.values())
                    .collect(toMap(NasdaqDataLinkMaturityDatesEnum::getMaturityName, NasdaqDataLinkMaturityDatesEnum::getMaturityDateBuilder));

    NasdaqDataLinkMaturityDatesEnum(String maturityName, UnaryOperator<LocalDate> maturityDateBuilder) {
        this.maturityName = maturityName;
        this.maturityDateBuilder = maturityDateBuilder;
    }

    public static LocalDate createMaturityDate(final LocalDate startDate, final String maturityString) {
        return VALUES_BY_IDENTIFIER.containsKey(maturityString) ? VALUES_BY_IDENTIFIER.get(maturityString).apply(startDate) : startDate;
    }

    public String getMaturityName() {
        return maturityName;
    }

    public Function<LocalDate, LocalDate> getMaturityDateBuilder() {
        return maturityDateBuilder;
    }
}
