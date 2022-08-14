package com.christopherrons.refdataservice.derivativesmargin.model;


import com.christopherrons.common.model.refdata.derivativesmargin.spanfile.definitions.Definitions;
import com.christopherrons.common.model.refdata.derivativesmargin.spanfile.pointintime.PointInTime;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class SpanFile {

    @JacksonXmlProperty
    private String fileFormat;

    @JacksonXmlProperty
    private String created;

    @JacksonXmlProperty
    private Definitions definitions;

    @JacksonXmlProperty
    private PointInTime pointInTime;

    public PointInTime getPointInTime() {
        return pointInTime;
    }
}
