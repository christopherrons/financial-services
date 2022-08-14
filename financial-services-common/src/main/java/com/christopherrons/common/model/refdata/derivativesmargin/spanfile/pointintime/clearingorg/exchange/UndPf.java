package com.christopherrons.common.model.refdata.derivativesmargin.spanfile.pointintime.clearingorg.exchange;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class UndPf {

    @JacksonXmlProperty
    private String exch;

    @JacksonXmlProperty
    private int pfId;

    @JacksonXmlProperty
    private String pfCode;

    @JacksonXmlProperty
    private String pfType;

    @JacksonXmlProperty
    private String s;

    @JacksonXmlProperty
    private double i;
}
