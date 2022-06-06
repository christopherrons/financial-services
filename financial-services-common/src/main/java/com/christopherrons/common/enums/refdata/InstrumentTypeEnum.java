package com.christopherrons.common.enums.refdata;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.stream;
import static java.util.function.UnaryOperator.identity;
import static java.util.stream.Collectors.toMap;

public enum InstrumentTypeEnum {
    INVALID_INSTRUMENT("invalid_instrument_type"),
    STOCK("stock"),
    DERIVATIVE("derivative"),
    FUTURE("future"),
    OPTION("option");

    private final String name;

    private static final Map<String, InstrumentTypeEnum> VALUES_BY_IDENTIFIER =
            stream(InstrumentTypeEnum.values()).collect(toMap(InstrumentTypeEnum::getName, identity()));

    InstrumentTypeEnum(String name) {
        this.name = name;
    }

    public static InstrumentTypeEnum fromName(final String instrumentTypeName) {
        return VALUES_BY_IDENTIFIER.getOrDefault(instrumentTypeName, InstrumentTypeEnum.INVALID_INSTRUMENT);
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
