package com.christopherrons.refdataservice.margin.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class PointInTime {

    @JacksonXmlProperty
    private String date;

    @JacksonXmlProperty
    private int isSetl;

    @JacksonXmlProperty
    private String setlQualifier;

    @JacksonXmlProperty
    private ClearingOrg clearingOrg;
}
