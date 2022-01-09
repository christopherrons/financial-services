package com.christopherrons.shadoworderbook.helper;

import java.util.concurrent.ThreadFactory;

public class ThreadWrapper implements ThreadFactory {
    private final String name;

    public ThreadWrapper(String name) {
        this.name = name;
    }

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r, name);
    }
}

