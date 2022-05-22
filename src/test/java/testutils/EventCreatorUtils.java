package testutils;

import com.christopherrons.marketdata.api.MarketDataOrder;
import com.christopherrons.marketdata.common.enums.event.*;
import com.christopherrons.marketdata.common.enums.subscription.ChannelEnum;
import com.christopherrons.marketdata.common.enums.subscription.TradingPairEnum;
import com.christopherrons.marketdata.common.model.Order;
import com.christopherrons.refdata.instrument.api.Instrument;
import com.christopherrons.refdata.instrument.enums.InstrumentTypeEnum;
import com.christopherrons.refdata.participant.model.Member;
import com.christopherrons.refdata.participant.model.Participant;

import static com.christopherrons.refdata.participant.utils.UserGeneratorUtils.generateUser;

public class EventCreatorUtils {

    public static MarketDataOrder buildMarketDataOrderCreate(MarketDataFeedEnum marketDataFeedEnum,
                                                             long timeStampInMs,
                                                             double price,
                                                             double volume,
                                                             OrderTypeEnum orderTypeEnum,
                                                             long orderId) {
        return buildMarketDataOrder(marketDataFeedEnum, OrderOperationEnum.CREATE, EventDescriptionEnum.ORDER_CREATED,
                timeStampInMs, price, volume, orderTypeEnum, orderId);
    }

    public static MarketDataOrder buildMarketDataOrderRemove(MarketDataFeedEnum marketDataFeedEnum,
                                                             long timeStampInMs,
                                                             double price,
                                                             double volume,
                                                             OrderTypeEnum orderTypeEnum,
                                                             long orderId) {
        return buildMarketDataOrder(marketDataFeedEnum, OrderOperationEnum.DELETE, EventDescriptionEnum.ORDER_DELETED,
                timeStampInMs, price, volume, orderTypeEnum, orderId);
    }

    public static MarketDataOrder buildMarketDataOrder(MarketDataFeedEnum marketDataFeedEnum,
                                                       OrderOperationEnum orderOperationEnum,
                                                       EventDescriptionEnum eventDescriptionEnum,
                                                       long timeStampInMs,
                                                       double price,
                                                       double volume,
                                                       OrderTypeEnum orderTypeEnum,
                                                       long orderId) {
        return new Order(marketDataFeedEnum,
                orderOperationEnum,
                new Participant(new Member(marketDataFeedEnum.getName()), generateUser()),
                orderId,
                orderTypeEnum.getValue(),
                volume,
                volume,
                price,
                timeStampInMs,
                Instrument.createInstrument(InstrumentTypeEnum.STOCK, TradingPairEnum.XRP_USD),
                eventDescriptionEnum,
                ChannelEnum.LIVE_ORDERS,
                EventTypeEnum.ORDER);
    }
}
