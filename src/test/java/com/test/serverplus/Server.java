package com.test.serverplus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Blocking
 * 如果连到服务器上的client不多
 * 用这个模型效率极高，模型简单
 */
public class Server {
    public static void main(String[] args) throws Exception {

        ServerSocket ss = new ServerSocket();
        //端口：0 -  65535
        ss.bind(new InetSocketAddress("localhost", 8888));
        boolean started = true;

        while(started){
            Socket s = ss.accept();

            new Thread(()->{
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
                    String str = reader.readLine();
                    System.out.println("hello");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        ss.close();
    }
}
