import axios from "axios";
import React, { useEffect } from 'react';
import { REFERENCE_DATA_END_POINTS } from "./ReferenceDataEndpoints";

function AvailableInstrumentsByTypeRequest(instrumentType) {
    try {
        console.log(instrumentType);
        const response = axios.get(REFERENCE_DATA_END_POINTS.availableInstrumentsByType + "? instrumentType=" + instrumentType);

        //return response.data["instruments"];

    } catch (error) {
        // return error;
    }

    return <h1>te</h1>;
}

export default AvailableInstrumentsByTypeRequest;