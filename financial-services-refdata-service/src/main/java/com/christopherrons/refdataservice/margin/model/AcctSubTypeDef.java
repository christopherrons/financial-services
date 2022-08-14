package com.christopherrons.refdataservice.margin.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class AcctSubTypeDef implements Serializable {

    @JsonProperty
    private String acctSubTypeCode;

    @JsonProperty
    private String dataType;

    @JsonProperty
    private String description;
}
