package com.test.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Blocking
 */
public class Server {
    public static void main(String[] args) throws Exception {

        ServerSocket ss = new ServerSocket();
        //端口：0 -  65535
        ss.bind(new InetSocketAddress("localhost", 8888));
        //accept()是阻塞式的方法，等待客户端连接上来。
        Socket s = ss.accept();
        BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
        String str = reader.readLine();
        System.out.println("hello");

    }
}
