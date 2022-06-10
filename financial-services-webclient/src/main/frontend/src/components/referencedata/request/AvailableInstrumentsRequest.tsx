import axios from "axios";
import React, { useEffect } from 'react';
import { REFERENCE_DATA_END_POINTS } from "./ReferenceDataEndpoints";


const AvailableInstrumentsRequest = async () => {
    try {
        const response = await axios.get(REFERENCE_DATA_END_POINTS.availableInstruments);
        console.log(response.data);
        return response.data;

    } catch (error) {
        return error;
    }
}

export default AvailableInstrumentsRequest;