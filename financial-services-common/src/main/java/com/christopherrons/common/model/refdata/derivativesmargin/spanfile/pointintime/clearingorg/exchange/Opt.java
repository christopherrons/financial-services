package com.christopherrons.common.model.refdata.derivativesmargin.spanfile.pointintime.clearingorg.exchange;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Opt implements Product  {

    @JacksonXmlProperty
    private RiskArray ra;

    @JacksonXmlProperty
    private int cId;

    @JacksonXmlProperty
    private String o;

    @JacksonXmlProperty
    private double k;

    @JacksonXmlProperty
    private double p;

    @JacksonXmlProperty
    private double pq;

    @JacksonXmlProperty
    private double d;

    @JacksonXmlProperty
    private double v;

    @JacksonXmlProperty
    private double val;
}
