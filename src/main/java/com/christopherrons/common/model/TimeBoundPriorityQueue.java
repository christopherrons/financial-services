package com.christopherrons.common.model;

import com.christopherrons.common.api.HasTimeStamp;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class TimeBoundPriorityQueue<T extends HasTimeStamp> extends PriorityQueue<T> {

    private final int timeInMs;
    private final transient PriorityQueue<T> timeboundQueue;

    public TimeBoundPriorityQueue(int timeInMs) {
        this(timeInMs, Comparator.comparing(HasTimeStamp::getTimeStampMs));
    }

    public TimeBoundPriorityQueue(int timeInMs, Comparator<T> comparator) {
        this(timeInMs, new PriorityQueue<>(comparator));
    }

    public TimeBoundPriorityQueue(int timeInMs, PriorityQueue<T> queue) {
        this.timeInMs = timeInMs;
        this.timeboundQueue = queue;
    }

    public List<T> addItemThenPurge(final T item) {
        timeboundQueue.add(item);
        return purgeItems(item);
    }

    private List<T> purgeItems(final T item) {
        List<T> timeExceedingItems = new ArrayList<>();
        while (timeboundQueue.peek() != null && isTimeExceeded(item, timeboundQueue.peek())) {
            timeExceedingItems.add(timeboundQueue.poll());
        }
        return timeExceedingItems;
    }

    private boolean isTimeExceeded(final T currentItem, final T item) {
        return currentItem.getTimeStampMs() - item.getTimeStampMs() > timeInMs;
    }
}
