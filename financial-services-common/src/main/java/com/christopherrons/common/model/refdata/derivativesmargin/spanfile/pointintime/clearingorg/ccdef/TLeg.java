package com.christopherrons.common.model.refdata.derivativesmargin.spanfile.pointintime.clearingorg.ccdef;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class TLeg {

    @JacksonXmlProperty
    private String cc;

    @JacksonXmlProperty
    private int tn;

    @JacksonXmlProperty
    private String rs;

    @JacksonXmlProperty
    private String i;
}
