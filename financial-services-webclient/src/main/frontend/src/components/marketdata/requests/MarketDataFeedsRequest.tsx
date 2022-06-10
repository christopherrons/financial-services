import axios from "axios";
import React, { useEffect } from 'react';
import { MARKET_DATA_END_POINTS } from "./MarketDataEndPoints";


export const MarketDataFeedsRequest = () => {
    const fetchMarketDataFeeds = () => {
        axios.get(MARKET_DATA_END_POINTS.availableDataFeeds).then(response => {
            console.log(response);
        });
    }

    useEffect(() => {
        fetchMarketDataFeeds();
    }, [])

    return <h1></h1>;
}