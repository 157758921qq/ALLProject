package com.yz.snake.v2;

public class Main {
    public static void main(String[] args) {
       Yard yard =  new Yard();
       yard.setVisible(true);

        new Thread(() -> {
            for(;;){
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                yard.repaint();
            }
        } ).start();
    }
}
