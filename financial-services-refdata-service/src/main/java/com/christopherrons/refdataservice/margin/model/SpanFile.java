package com.christopherrons.refdataservice.margin.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SpanFile {

    @JacksonXmlProperty
    private String fileFormat;

    @JacksonXmlProperty
    private String created;

    @JacksonXmlProperty
    private Definitions definitions;

    @JacksonXmlProperty
    private PointInTime pointInTime;
}
