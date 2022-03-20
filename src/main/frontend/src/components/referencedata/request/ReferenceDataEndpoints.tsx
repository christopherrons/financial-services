import EndPointUtil from "../../common/endpoints/EndPointUtil"


export default class ReferenceDataEndpoints {
    static ReferenceDataBaseEndpoints: string = EndPointUtil.baseEndpoint + "/referenceData";
}

export const REFERENCE_DATA_END_POINTS = {
    availableInstruments: ReferenceDataEndpoints.ReferenceDataBaseEndpoints + "/availableInstruments",
    availableInstrumentTypes: ReferenceDataEndpoints.ReferenceDataBaseEndpoints + "/availableInstrumentTypes",
    availableInstrumentsByType: ReferenceDataEndpoints.ReferenceDataBaseEndpoints + "/availableInstrumentsByType"

}