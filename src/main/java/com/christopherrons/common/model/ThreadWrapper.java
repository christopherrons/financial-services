package com.christopherrons.common.model;

import java.util.concurrent.ThreadFactory;

public class ThreadWrapper implements ThreadFactory {
    private final String name;

    public ThreadWrapper(String name) {
        this.name = name;
    }

    @Override
    public Thread newThread(Runnable runnable) {
        return new Thread(runnable, name);
    }
}

