package com.christopherrons.common.model.refdata.derivativesmargin.spanfile.pointintime.clearingorg.exchange;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class RiskArray {

    @JacksonXmlProperty
    private int r;

    @JacksonXmlProperty
    @JacksonXmlElementWrapper(useWrapping = false)
    private double[] a;

    @JacksonXmlProperty
    private double d;

    public int getR() {
        return r;
    }

    public double[] getScenarioLosses() {
        return a;
    }

    public double getD() {
        return d;
    }
}
