package com.christopherrons.restapi.refdata.controller;

import com.christopherrons.common.enums.refdata.InstrumentTypeEnum;
import com.christopherrons.restapi.refdata.dto.ApiAvailableInstrumentTypesDto;
import com.christopherrons.restapi.refdata.dto.ApiAvailableInstrumentsByTypeDto;
import com.christopherrons.restapi.refdata.dto.ApiAvailableInstrumentsDto;
import com.christopherrons.restapi.refdata.requests.ApiAvailableInstrumentsByTypeRequest;
import com.christopherrons.restapi.refdata.service.RefDataApiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@CrossOrigin("*")
@RequestMapping("/referenceData")
public class RefDataApiController {

    private static final Logger LOGGER = Logger.getLogger(RefDataApiController.class.getName());

    @Autowired
    private RefDataApiService refDataApiService;

    @GetMapping("/availableInstruments")
    @Operation(summary = "Request available instruments.",
            description = "This method returns all available instruments.")
    public ApiAvailableInstrumentsDto getAvailableInstrumentsRequest() {
        LOGGER.info("Get request available instruments received.");
        return refDataApiService.getAvailableInstruments();
    }

    @GetMapping("/availableInstrumentTypes")
    @Operation(summary = "Request available instruments types.",
            description = "This method returns all available instruments types.")
    public ApiAvailableInstrumentTypesDto getAvailableInstrumentTypesRequest() {
        LOGGER.info("Get request available instruments received.");
        return refDataApiService.getAvailableInstrumentTypes();
    }

    @PostMapping(value = "/availableInstrumentsByType")
    @Operation(summary = "Request available instruments by type.",
            description = "This methods returns all available instruments by typ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Bad Request"),
    })
    @Parameters({
            @Parameter(name = "instrumentType", description = "instrument type", required = true, in = ParameterIn.QUERY,
                    content = @Content(schema = @Schema(implementation = InstrumentTypeEnum.class, defaultValue = "STOCK"))),
    })
    public ApiAvailableInstrumentsByTypeDto subscriptionTradingPairsRequest(final ApiAvailableInstrumentsByTypeRequest availableInstrumentsByTypeRequest) {
        LOGGER.info(String.format("Post request available instruments by type received: %s.", availableInstrumentsByTypeRequest));
        return refDataApiService.getAvailableInstrumentsByType(availableInstrumentsByTypeRequest.getInstrumentType());
    }
}
