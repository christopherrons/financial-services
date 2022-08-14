package com.christopherrons.refdataservice.margin.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

public class ClearingOrg {

    @JacksonXmlProperty
    private String ec;

    @JacksonXmlProperty
    private String name;

    @JacksonXmlProperty
    private int isContractScale;

    @JacksonXmlProperty
    private int isNetMargin;

    @JacksonXmlProperty
    private String finalizeMeth;

    @JacksonXmlProperty
    private String oopDeltaMeth;

    @JacksonXmlProperty
    private int capAnov;

    @JacksonXmlProperty
    private double lookAheadYears;

    @JacksonXmlProperty
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<CurvConv> curConv;

    @JacksonXmlProperty
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<PbRateDef> pbRateDef;

    @JacksonXmlProperty
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<PointDef> pointDef;

    @JacksonXmlProperty
    private Exchange exchange;
}
