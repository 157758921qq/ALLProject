package com.yz.snake.v4;

public class Main {

    public static final Main INSTANCE = new Main();
    public PaintThread pt;
    public boolean gameOver = false;


    private Main() {
        pt = new PaintThread();
        new Thread(pt).start();
    }


    //线程内
    public static class PaintThread implements Runnable {
        private boolean running = true;
        private boolean pause = false;

        public void run() {
            while (running) {
                //如果pause为true，说明暂停了，
                if (pause) continue;
                else {
                    Yard.INSTANCE.repaint();
                    System.out.println("--不断刷新--");
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public void pause() {
            this.pause = true;
        }

//        public void reStart() {
//            this.pause = false;
//            Yard.INSTANCE.s = new Snake(Yard.INSTANCE);
//            Main.INSTANCE.gameOver = false;
//        }

        public void gameOver() {
            running = false;
        }

    }


    public static void main(String[] args) {
        Yard.INSTANCE.setVisible(true);
    }
}
