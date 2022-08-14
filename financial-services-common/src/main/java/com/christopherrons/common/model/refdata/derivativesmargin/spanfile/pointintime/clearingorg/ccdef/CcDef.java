package com.christopherrons.common.model.refdata.derivativesmargin.spanfile.pointintime.clearingorg.ccdef;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

public class CcDef {

    @JacksonXmlProperty
    private String cc;

    @JacksonXmlProperty
    private String name;

    @JacksonXmlProperty
    private String currency;

    @JacksonXmlProperty
    private int riskExponent;

    @JacksonXmlProperty
    private int capAnov;

    @JacksonXmlProperty
    private String wfprMeth;

    @JacksonXmlProperty
    private String procMeth;

    @JacksonXmlProperty
    private String spotMeth;

    @JacksonXmlProperty
    private String somMeth;

    @JacksonXmlProperty
    private String cmbMeth;

    @JacksonXmlProperty
    private String marginMeth;

    @JacksonXmlProperty
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<PfLink> pfLink;

    @JacksonXmlProperty
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<ScanTier> scanTiers;

    @JacksonXmlProperty
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<InterTier> interTiers;

    @JacksonXmlProperty
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<IntraTier> intraTiers;

    @JacksonXmlProperty
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<SomTier> somTiers;

    @JacksonXmlProperty
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<DSpread> dSpread;

    public List<PfLink> getPfLink() {
        return pfLink;
    }

    public String getCombinedCommodityId() {
        return cc;
    }
}
