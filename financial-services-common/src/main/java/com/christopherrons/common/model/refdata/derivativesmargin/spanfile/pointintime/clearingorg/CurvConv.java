package com.christopherrons.common.model.refdata.derivativesmargin.spanfile.pointintime.clearingorg;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class CurvConv {

    @JacksonXmlProperty
    private String fromCur;

    @JacksonXmlProperty
    private String toCur;

    @JacksonXmlProperty
    private double factor;

}
