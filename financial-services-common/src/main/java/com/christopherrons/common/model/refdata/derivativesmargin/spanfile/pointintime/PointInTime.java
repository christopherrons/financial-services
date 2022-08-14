package com.christopherrons.common.model.refdata.derivativesmargin.spanfile.pointintime;

import com.christopherrons.common.model.refdata.derivativesmargin.spanfile.pointintime.clearingorg.ClearingOrg;
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

    public ClearingOrg getClearingOrg() {
        return clearingOrg;
    }
}
