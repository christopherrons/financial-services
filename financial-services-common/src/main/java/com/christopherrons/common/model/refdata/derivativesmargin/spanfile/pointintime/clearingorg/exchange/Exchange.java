package com.christopherrons.common.model.refdata.derivativesmargin.spanfile.pointintime.clearingorg.exchange;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.Collections;
import java.util.List;

public class Exchange {

    @JacksonXmlProperty
    private String exch;

    @JacksonXmlProperty
    private String name;

    @JacksonXmlProperty
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<PhyPf> phyPf;

    @JacksonXmlProperty
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<FutPf> futPf;

    @JacksonXmlProperty
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<OofPf> oofPf;

    public List<PhyPf> getPhyPf() {
        return phyPf != null ? phyPf : Collections.emptyList();
    }

    public List<FutPf> getFutPf() {
        return futPf != null ? futPf : Collections.emptyList();
    }

    public List<OofPf> getOofPf() {
        return oofPf != null ? oofPf : Collections.emptyList();
    }
}
