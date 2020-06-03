package com.test.net;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws Exception {
        //客户端连接到Server上
        Socket s = new Socket("localhost",8888);
        //这个socket连接建立后，从socket角度看是连接到Server端的
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
        bw.write("yangzhou");
        bw.newLine();
        bw.flush();
        bw.close();
    }
}
