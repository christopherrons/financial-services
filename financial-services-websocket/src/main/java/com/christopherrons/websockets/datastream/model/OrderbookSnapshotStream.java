package com.christopherrons.websockets.datastream.model;

import com.christopherrons.common.model.trading.PriceLevelData;
import com.christopherrons.websockets.api.DataStream;

import java.util.List;

public record OrderbookSnapshotStream(List<PriceLevelData> bidPriceLevelsData,
                                      List<PriceLevelData> askPriceLevelsData,
                                      double bestAsk,
                                      double bestBid) implements DataStream {
    @Override
    public boolean isEmpty() {
        return bidPriceLevelsData.isEmpty() && askPriceLevelsData.isEmpty();
    }
}
