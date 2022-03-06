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
        switch (instrumentTypeName) {
            case "stock":
                return InstrumentTypeEnum.STOCK;
            case "derivative":
                return InstrumentTypeEnum.DERIVATIVE;
            case "future":
                return InstrumentTypeEnum.FUTURE;
            case "option":
                return InstrumentTypeEnum.OPTION;
            default:
                return InstrumentTypeEnum.INVALID_INSTRUMENT;
        }
    }

    public static List<InstrumentTypeEnum> getInstrumentType() {
        return Arrays.stream(InstrumentTypeEnum.values())
                .filter(channelEnum -> !channelEnum.equals(InstrumentTypeEnum.INVALID_INSTRUMENT))
                .collect(Collectors.toList());
    }

    public static List<String> getInstrumentTypeNames() {
        return Arrays.stream(InstrumentTypeEnum.values())
                .filter(channelEnum -> !channelEnum.equals(InstrumentTypeEnum.INVALID_INSTRUMENT))
                .map(InstrumentTypeEnum::getName)
                .collect(Collectors.toList());
    }

    public String getName() {
        return name;
    }
}
