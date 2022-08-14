package com.christopherrons.common.model.refdata.derivativesmargin.spanfile.pointintime.clearingorg.exchange;

import com.christopherrons.common.model.refdata.derivativesmargin.spanfile.pointintime.clearingorg.ccdef.IntrRate;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

public class Series {

    @JacksonXmlProperty
    private UndC undC;

    @JacksonXmlProperty
    private ScanRate scanRate;

    @JacksonXmlProperty
    private IntrRate intrRate;

    @JacksonXmlProperty
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Opt> opt;

    @JacksonXmlProperty
    private String pe;

    @JacksonXmlProperty
    private double v;

    @JacksonXmlProperty
    private String volSrc;

    @JacksonXmlProperty
    private String setlDate;

    @JacksonXmlProperty
    private double t;

    @JacksonXmlProperty
    private double cvf;

    @JacksonXmlProperty
    private double svf;

    @JacksonXmlProperty
    private double sc;

    public List<Opt> getOpt() {
        return opt;
    }
}
