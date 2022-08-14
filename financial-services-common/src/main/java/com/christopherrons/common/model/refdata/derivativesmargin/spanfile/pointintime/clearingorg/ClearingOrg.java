package com.christopherrons.common.model.refdata.derivativesmargin.spanfile.pointintime.clearingorg;

import com.christopherrons.common.model.refdata.derivativesmargin.spanfile.pointintime.clearingorg.ccdef.CcDef;
import com.christopherrons.common.model.refdata.derivativesmargin.spanfile.pointintime.clearingorg.exchange.Exchange;
import com.christopherrons.common.model.refdata.derivativesmargin.spanfile.pointintime.clearingorg.interspreads.InterSpreads;
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

    @JacksonXmlProperty
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<CcDef> ccDef;

    @JacksonXmlProperty
    private InterSpreads interSpreads;
}
