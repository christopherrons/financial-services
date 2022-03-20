import EndPointUtil from "../../common/endpoints/EndPointUtil"


export default class MarketDataEndPoints {
    static MarketDataBaseEndPoint: string = EndPointUtil.baseEndpoint + "/marketData";
}

export const MARKET_DATA_END_POINTS = {
    availableDataFeeds: MarketDataEndPoints.MarketDataBaseEndPoint + "/availableDataFeeds"
}