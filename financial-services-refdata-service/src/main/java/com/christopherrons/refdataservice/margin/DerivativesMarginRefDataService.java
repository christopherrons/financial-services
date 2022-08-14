package com.christopherrons.refdataservice.margin;

import com.christopherrons.common.model.refdata.DerivatesMarginRefData;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Scanner;

@Service
public class DerivativesMarginRefDataService {

    private DerivatesMarginRefData derivatesMarginRefData = null;
    private final DerivativesMarginFileParser derivativesMarginFileParser = new DerivativesMarginFileParser();
    public DerivatesMarginRefData getDerviativesMarginRefData() throws IOException {
        if (derivatesMarginRefData == null) {
            String spanFile = loadSpanFile();
            derivatesMarginRefData = derivativesMarginFileParser.parseSpanFile(spanFile);
        }

        return derivatesMarginRefData;
    }

    private String loadSpanFile() throws IOException {
        StringBuilder spanFile = new StringBuilder();
        Resource resource = new ClassPathResource("ECC_SPANFILE_20220812.xml");
        Scanner in = new Scanner(resource.getInputStream());
        while (in.hasNext()) {
            spanFile.append(in.next());
        }
        in.close();
        return spanFile.toString();
    }

    public static void main(String[] args) throws IOException {
        var a = new DerivativesMarginRefDataService();
        a.getDerviativesMarginRefData();
    }

}

