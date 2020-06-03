package com.yz.callable;

import java.util.concurrent.Callable;

/**
 * 类似Runnable接口
 * 任务是可以放在线程池里运行的
 *
 */
public class MyTask implements Callable<Long> {
    @Override
    public Long call() throws Exception {
        long r = 0L;
        for(long i=0L; i<10L; i++){
            r += i;
            Thread.sleep(500);
            System.out.println(i+ "added");
        }
        return r;
    }
}
