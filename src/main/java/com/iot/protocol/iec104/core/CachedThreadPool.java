package com.iot.protocol.iec104.core;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池
 * @author Mr.Qu
 * @since 2020/5/13
 */
public final class CachedThreadPool {

    private  static final CachedThreadPool cachedThreadPool  = new CachedThreadPool();
    private final ExecutorService executorService;


    private CachedThreadPool() {
        executorService = Executors.newCachedThreadPool();
    }

    public static CachedThreadPool getCachedThreadPool() {
        return  cachedThreadPool;
    }

    public void execute(Runnable runnable) {
        executorService.execute(runnable);
    }
}
