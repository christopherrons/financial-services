package com.christopherrons.refdataservice.margin.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.io.Serializable;
import java.util.List;

public class Definitions implements Serializable {

    @JacksonXmlProperty
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<CurrencyDef> currencyDef;

    @JacksonXmlProperty
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<AcctTypeDef> acctTypeDef;

    @JacksonXmlProperty
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<AcctSubTypeDef> acctSubTypeDef;
}
