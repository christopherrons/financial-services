package com.christopherrons.common.model.refdata.derivativesmargin.spanfile.pointintime.clearingorg;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class DeltaPointDef {

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
