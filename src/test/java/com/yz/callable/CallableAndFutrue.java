package com.yz.callable;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableAndFutrue {
    public static void main(String[] args) throws Exception {
        //定义线程池
        ExecutorService service = Executors.newCachedThreadPool();
        //通过service的submit方法提交给线程池
        //返回结果用future来接收
        Future<Long> future = service.submit(new MyTask());

        //阻塞的
        long l = future.get(); //wait until there`s a result
        System.out.println(l);

        System.out.println("go on!");
    }
}
