package com.christopherrons.refdataservice.margin.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class ScanPointDef {

    @JacksonXmlProperty
    private int point;

    @JacksonXmlProperty
    private PriceScanDef priceScanDef;

    @JacksonXmlProperty
    private VolScanDef volScanDef;

    @JacksonXmlProperty
    private double weight;

    @JacksonXmlProperty
    private int pairedPoint;

}
