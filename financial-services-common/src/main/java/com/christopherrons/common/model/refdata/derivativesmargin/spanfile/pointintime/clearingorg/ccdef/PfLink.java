package com.christopherrons.common.model.refdata.derivativesmargin.spanfile.pointintime.clearingorg.ccdef;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class PfLink {

    @JacksonXmlProperty
    private String exch;

    @JacksonXmlProperty
    private int pfId;

    @JacksonXmlProperty
    private String pfCode;

    @JacksonXmlProperty
    private String pfType;

    @JacksonXmlProperty
    private double sc;

    @JacksonXmlProperty
    private String cmbMeth;

    @JacksonXmlProperty
    private int applyBasisRisk;

    @JacksonXmlProperty
    private int arrayPrecision;
    }
