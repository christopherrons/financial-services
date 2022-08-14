package com.christopherrons.refdataservice.margin.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Fut {

    @JacksonXmlProperty
    private String cId;

    @JacksonXmlProperty
    private String pe;

    @JacksonXmlProperty
    private double p;

    @JacksonXmlProperty
    private double d;

    @JacksonXmlProperty
    private double v;

    @JacksonXmlProperty
    private double cvf;

    @JacksonXmlProperty
    private double val;

    @JacksonXmlProperty
    private double sc;

    @JacksonXmlProperty
    private String setlDate;

    @JacksonXmlProperty
    private double t;

    @JacksonXmlProperty
    private String fDeliv;

    @JacksonXmlProperty
    private String lDeliv;

    @JacksonXmlProperty
    private UndC undC;

    @JacksonXmlProperty
    private ScanRate scanRate;

    @JacksonXmlProperty
    private RiskArray ra;
}
