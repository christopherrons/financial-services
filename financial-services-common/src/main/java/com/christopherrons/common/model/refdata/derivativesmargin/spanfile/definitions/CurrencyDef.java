package com.christopherrons.common.model.refdata.derivativesmargin.spanfile.definitions;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.io.Serializable;

public class CurrencyDef implements Serializable {

    @JacksonXmlProperty
    private String currency;

    @JacksonXmlProperty
    private String symbol;

    @JacksonXmlProperty
    private String name;

    @JacksonXmlProperty
    private int decimalPos;
}
