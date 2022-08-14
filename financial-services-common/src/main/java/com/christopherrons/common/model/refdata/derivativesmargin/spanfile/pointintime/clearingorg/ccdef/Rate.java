package com.christopherrons.common.model.refdata.derivativesmargin.spanfile.pointintime.clearingorg.ccdef;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Rate {


    @JacksonXmlProperty
    private int r;

    @JacksonXmlProperty
    private double val;

    @JacksonXmlProperty
    private String ePe;
}
