package com.christopherrons.restapi.common.errorhandling.model;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public record ApiException(String message,
                           Throwable throwable,
                           HttpStatus httpStatus,
                           ZonedDateTime timeStamp) {
}
