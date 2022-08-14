package com.christopherrons.common.model.refdata.derivativesmargin.spanfile.pointintime.clearingorg.exchange;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class ScanRate {

    @JacksonXmlProperty
    private int r;

    @JacksonXmlProperty
    private double priceScan;

    @JacksonXmlProperty
    private double volScan;
}
