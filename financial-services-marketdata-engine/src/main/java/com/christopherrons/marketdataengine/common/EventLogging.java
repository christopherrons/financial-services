package com.christopherrons.marketdataengine.common;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

public class EventLogging {
    private static final Logger LOGGER = Logger.getLogger(EventLogging.class.getName());
    private static final int MESSAGE_UPDATE_INTERVAL = 1000;
    private final AtomicLong totalNrOfEvents = new AtomicLong();
    private final Instant startTime = Instant.now();
    private Instant lastLogUpdateTime = Instant.now();
    private AtomicLong lastUpdateTimeNrOfEvents = new AtomicLong();


    public void logEvent() {
        if (totalNrOfEvents.incrementAndGet() % MESSAGE_UPDATE_INTERVAL == 0) {
            Instant currentTime = Instant.now();
            LOGGER.info(String.format("Events received: %s. Current event rate %s/s, average event rate %s/s",
                    totalNrOfEvents.get(), getCurrentEventsPerSecond(currentTime), getAverageEventsPerSecond(currentTime)));
            lastLogUpdateTime = currentTime;
            lastUpdateTimeNrOfEvents = new AtomicLong(totalNrOfEvents.get());
        }
    }

    private long getCurrentEventsPerSecond(final Instant currentTime) {
        return (totalNrOfEvents.get() - lastUpdateTimeNrOfEvents.get()) / currentTime.minusMillis((lastLogUpdateTime.toEpochMilli())).getEpochSecond();
    }

    private long getAverageEventsPerSecond(final Instant currentTime) {
        return totalNrOfEvents.get() / currentTime.minusMillis((startTime.toEpochMilli())).getEpochSecond();
    }
}
