package com.christopherrons.refdataservice.margin;

import com.christopherrons.common.model.refdata.DerivatesMarginRefData;
import com.christopherrons.refdataservice.margin.model.SpanFile;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class DerivativesMarginFileParser {

    private final XmlMapper xmlMapper = new XmlMapper();

    public DerivatesMarginRefData parseSpanFile(final String spanFileString) throws JsonProcessingException {
        SpanFile spanFile = xmlMapper.readValue(spanFileString, SpanFile.class);
        return null;
    }


}
