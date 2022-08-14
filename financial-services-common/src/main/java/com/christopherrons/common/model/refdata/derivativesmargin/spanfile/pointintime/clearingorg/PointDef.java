package com.christopherrons.common.model.refdata.derivativesmargin.spanfile.pointintime.clearingorg;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

public class PointDef {

    @JacksonXmlProperty
    private int r;

    @JacksonXmlProperty
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<DeltaPointDef> deltaPointDef;

    @JacksonXmlProperty
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<ScanPointDef> scanPointDef;

    @JacksonXmlProperty
    private String oopDeltaMeth;

    @JacksonXmlProperty
    private int capAnov;
}
