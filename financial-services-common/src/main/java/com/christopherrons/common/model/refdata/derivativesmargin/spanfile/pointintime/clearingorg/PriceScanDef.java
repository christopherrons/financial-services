package com.christopherrons.common.model.refdata.derivativesmargin.spanfile.pointintime.clearingorg;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class PriceScanDef {

    @JacksonXmlProperty
    private double mult;

    @JacksonXmlProperty
    private double numerator;

    @JacksonXmlProperty
    private double denominator;
}
