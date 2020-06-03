package com.yz.codec;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ServerFrame extends Frame {

    public static final ServerFrame INSTANCE = new ServerFrame();

    TextArea taServer = new TextArea();
    TextArea taClient = new TextArea();

    private Server server = new Server();

    private  ServerFrame(){
        this.setSize(800, 600);
        this.setLocation(300,50);
        Panel p = new Panel(new GridLayout(1, 2));
        p.add(taServer);
        p.add(taClient);
        this.add(p);

        taServer.setFont(new Font("consolas", Font.PLAIN, 25));
        taClient.setFont(new Font("consolas", Font.PLAIN, 25));

        this.updateServerMsg("Server:");
        this.updateClientMsg("Client:");

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });


    }

    public void updateServerMsg(String str){
        //跨平台，换行符：System.getProperty("line.separator")
        this.taServer.setText(taServer.getText() + str + System.getProperty("line.separator"));
    }

    public void updateClientMsg(String str){
        this.taClient.setText(taClient.getText() + str + System.getProperty("line.separator"));
    }

    public static void main(String[] args) {
        ServerFrame.INSTANCE.setVisible(true);
        ServerFrame.INSTANCE. server.serverStart();
    }

}
