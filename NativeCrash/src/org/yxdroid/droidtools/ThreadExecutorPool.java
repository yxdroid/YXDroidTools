package org.yxdroid.droidtools;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author yaxiong.fang
 * @Date 2017/5/23
 * @Time 16:33
 */
public class ThreadExecutorPool {

    private static ThreadExecutorPool instance;

    private ExecutorService mSingleThreadExecutor;

    public static synchronized ThreadExecutorPool getInstance() {
        if (instance == null) {
            synchronized (ThreadExecutorPool.class) {
                instance = new ThreadExecutorPool();
            }
        }
        return instance;
    }

    public ThreadExecutorPool() {
        mSingleThreadExecutor = Executors.newSingleThreadScheduledExecutor();
    }

    public void execute(Runnable runnable) {
        if (runnable == null) {
            return;
        }
        mSingleThreadExecutor.execute(runnable);
    }

    public <T> Future<T> submit(Callable<T> task) {
        if (task == null) {
            return null;
        }

        return mSingleThreadExecutor.submit(task);
    }

    public void destory() {
        mSingleThreadExecutor.shutdown();
        instance = null;
    }
}
