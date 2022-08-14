package com.christopherrons.common.model.refdata.derivativesmargin.spanfile.pointintime.clearingorg.ccdef;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

public class Tier {

    @JacksonXmlProperty
    private int tn;

    @JacksonXmlProperty
    private String sPe;

    @JacksonXmlProperty
    private String ePe;

    @JacksonXmlProperty
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Rate> rate;
}
