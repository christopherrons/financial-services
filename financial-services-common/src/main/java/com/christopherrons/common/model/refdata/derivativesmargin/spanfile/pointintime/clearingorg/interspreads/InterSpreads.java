package com.christopherrons.common.model.refdata.derivativesmargin.spanfile.pointintime.clearingorg.interspreads;

import com.christopherrons.common.model.refdata.derivativesmargin.spanfile.pointintime.clearingorg.ccdef.DSpread;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

public class InterSpreads {

    @JacksonXmlProperty
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<DSpread> dSpread;
}
