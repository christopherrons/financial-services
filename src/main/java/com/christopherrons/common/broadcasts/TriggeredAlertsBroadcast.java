package com.christopherrons.common.broadcasts;

import com.christopherrons.surveillanceengine.alert.model.Alert;
import org.springframework.context.ApplicationEvent;

import java.util.List;

public class TriggeredAlertsBroadcast extends ApplicationEvent {
    private final transient  List<Alert> alerts;

    public TriggeredAlertsBroadcast(Object source, List<Alert> alerts) {
        super(source);
        this.alerts = alerts;
    }

    public List<Alert> getAlerts() {
        return alerts;
    }
}
