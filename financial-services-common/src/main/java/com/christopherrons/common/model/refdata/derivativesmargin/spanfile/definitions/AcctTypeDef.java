package com.christopherrons.common.model.refdata.derivativesmargin.spanfile.definitions;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class AcctTypeDef implements Serializable {

    @JsonProperty
    private int isCust;

    @JsonProperty
    private String acctType;

    @JsonProperty
    private String name;

    @JsonProperty
    private int isNetMargin;

    @JsonProperty
    private int priority;
}
