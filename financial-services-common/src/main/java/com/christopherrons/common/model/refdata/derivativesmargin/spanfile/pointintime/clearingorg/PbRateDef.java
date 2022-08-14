package com.christopherrons.common.model.refdata.derivativesmargin.spanfile.pointintime.clearingorg;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class PbRateDef {

    @JacksonXmlProperty
    private int r;

    @JacksonXmlProperty
    private int isCust;

    @JacksonXmlProperty
    private int isM;

    @JacksonXmlProperty
    private String pbc;

    @JacksonXmlProperty
    private String acctType;
}
