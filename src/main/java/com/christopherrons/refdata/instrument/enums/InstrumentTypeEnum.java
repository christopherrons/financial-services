package com.christopherrons.refdata.instrument.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum InstrumentTypeEnum {
    INVALID_INSTRUMENT("invalid_instrument_type"),
    STOCK("stock"),
    DERIVATIVE("derivative"),
    FUTURE("future"),
    OPTION("option");

    private final String name;

    InstrumentTypeEnum(String name) {
        this.name = name;
    }

    public static InstrumentTypeEnum fromName(final String instrumentTypeName) {
        return switch (instrumentTypeName) {
            case "stock" -> InstrumentTypeEnum.STOCK;
            case "derivative" -> InstrumentTypeEnum.DERIVATIVE;
            case "future" -> InstrumentTypeEnum.FUTURE;
            case "option" -> InstrumentTypeEnum.OPTION;
            default -> InstrumentTypeEnum.INVALID_INSTRUMENT;
        };
    }

    public static List<InstrumentTypeEnum> getInstrumentType() {
        return Arrays.stream(InstrumentTypeEnum.values())
                .filter(channelEnum -> !channelEnum.equals(InstrumentTypeEnum.INVALID_INSTRUMENT))
                .toList();
    }

    public static List<String> getInstrumentTypeNames() {
        return Arrays.stream(InstrumentTypeEnum.values())
                .filter(channelEnum -> !channelEnum.equals(InstrumentTypeEnum.INVALID_INSTRUMENT))
                .map(InstrumentTypeEnum::getName)
                .toList();
    }

    public String getName() {
        return name;
    }
}
