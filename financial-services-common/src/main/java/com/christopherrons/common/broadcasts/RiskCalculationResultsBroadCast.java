package com.christopherrons.common.broadcasts;

import com.christopherrons.common.model.risk.RiskCalculationResult;
import org.springframework.context.ApplicationEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RiskCalculationResultsBroadCast extends ApplicationEvent {

    private final transient Collection<RiskCalculationResult> riskCalculationResults;

    public RiskCalculationResultsBroadCast(Object source, Collection<RiskCalculationResult> riskCalculationResults) {
        super(source);
        this.riskCalculationResults = riskCalculationResults;
    }

    public List<RiskCalculationResult> getRiskCalculationResults() {
        return new ArrayList<>(riskCalculationResults);
    }
}