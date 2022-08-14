package com.christopherrons.common.model.refdata.derivativesmargin.spanfile.pointintime.clearingorg.ccdef;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

public class DSpread {

    @JacksonXmlProperty
    private int spread;

    @JacksonXmlProperty
    private String chargeMeth;

    @JacksonXmlProperty
    private Rate rate;

    @JacksonXmlProperty
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<TLeg> tLeg;

    @JacksonXmlProperty
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<PLeg> pLeg;
}
