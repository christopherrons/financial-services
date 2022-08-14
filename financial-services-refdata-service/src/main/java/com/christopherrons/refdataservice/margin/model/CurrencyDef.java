package com.christopherrons.refdataservice.margin.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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
