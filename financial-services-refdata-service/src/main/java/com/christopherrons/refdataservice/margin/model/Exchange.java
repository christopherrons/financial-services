package com.christopherrons.refdataservice.margin.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

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
}
