package com.jfbian.core.threadpool;


import com.jfbian.util.ApplicationContextUtil;

import java.util.concurrent.ThreadPoolExecutor;


/**
 * @author 麻木神
 * @version 1.0
 * @title ThreadPoolManager.java
 * @description 线程池执行类  单例
 * @time 2019年12月4日 下午5:04:26
 */
public class ThreadPoolManager {

    private ThreadPoolManager() {
    }


    private static ThreadPoolManager me = new ThreadPoolManager();

    //返回单例
    public static ThreadPoolManager me() {
        return me;
    }

    //线程池
    private ThreadPoolExecutor threadPoolExecutor = ApplicationContextUtil.getContext().getBean(ThreadPoolExecutor.class);

    //交给线程池执行
    public void execute(Runnable runnable) {
        threadPoolExecutor.execute(runnable);
    }

    /**
     * @return void
     * @description 关闭线程池，不再接受新的任务，之前提交的任务等执行结束再关闭线程池
     * @author 麻木神
     * @time 2019年12月4日 下午5:28:08
     */
    public void shutdown() {
        if (!threadPoolExecutor.isShutdown()) {
            threadPoolExecutor.shutdown();
        }
    }
}
