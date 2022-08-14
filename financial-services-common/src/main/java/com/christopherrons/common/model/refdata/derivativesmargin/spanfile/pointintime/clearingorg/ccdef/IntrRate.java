package com.christopherrons.common.model.refdata.derivativesmargin.spanfile.pointintime.clearingorg.ccdef;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class IntrRate {

    @JacksonXmlProperty
    private double val;

    @JacksonXmlProperty
    private int rl;

    @JacksonXmlProperty
    private int cpm;

    @JacksonXmlProperty
    private int exm;
}
