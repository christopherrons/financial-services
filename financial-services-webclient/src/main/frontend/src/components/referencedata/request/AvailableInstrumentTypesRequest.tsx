import axios from "axios";
import React, { useEffect } from 'react';
import { REFERENCE_DATA_END_POINTS } from "./ReferenceDataEndpoints";

async function availableInstrumentTypesRequest() {
    try {
        const response = await axios.get(REFERENCE_DATA_END_POINTS.availableInstrumentTypes);
        return response.data["instrumentTypeEnums"];

    } catch (error) {
        return error;
    }
}

export default availableInstrumentTypesRequest;