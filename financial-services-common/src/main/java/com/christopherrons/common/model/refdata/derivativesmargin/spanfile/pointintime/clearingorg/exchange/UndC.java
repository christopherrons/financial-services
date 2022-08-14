package com.christopherrons.common.model.refdata.derivativesmargin.spanfile.pointintime.clearingorg.exchange;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class UndC {

    @JacksonXmlProperty
    private String exch;

    @JacksonXmlProperty
    private int pfId;

    @JacksonXmlProperty
    private int cId;

    @JacksonXmlProperty
    private String s;

    @JacksonXmlProperty
    private double i;
}
