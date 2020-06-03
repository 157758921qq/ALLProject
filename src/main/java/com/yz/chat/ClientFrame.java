package com.yz.chat;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ClientFrame extends Frame {
    public static final ClientFrame INSTANCE = new ClientFrame();

    private TextArea ta = new TextArea();
    private TextField tf = new TextField();
    //一般定义成  成员变量，可以被使用
    private Client c = null;


    private  ClientFrame() {
        this.setTitle("聊天. Version 1");
        this.setSize(300, 400);
        this.setLocation(400, 20);
        this.add(ta, BorderLayout.CENTER);
        this.add(tf, BorderLayout.SOUTH);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //挥手关闭
                c.closeConnection();
                System.exit(0);
            }
        });

        tf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //拿到字符串后发送到server
                c.send(tf.getText());
                //ta.setText(ta.getText() + tf.getText() +"\r\n");
                tf.setText("");
            }
        });

    }

    private void connectToServer(){
        c = new Client();
        c.connect();

    }

    public static void main(String[] args) {
        ClientFrame f = ClientFrame.INSTANCE;
        f.setVisible(true);
        f.connectToServer();
    }

    public void updateText(String str) {
        ta.setText(ta.getText() +str + "\r\n");
    }
}
