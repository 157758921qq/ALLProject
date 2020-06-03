package com.yz.snake.v5;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Snake运动的院子
 * 计量单位：格子
 *          行：40
 *          列：40
 *          每格的宽和高15
 * 所以：定义成静态变量
 *      Yard的宽度是GAME_WIDTH ：COLS * BLOCK_SIZE
 *      Yard的宽度是GAME_HEIGHT：ROWS * BLOCK_SIZE
 */
public class Yard extends Frame {
    public static final Yard INSTANCE = new Yard();
    public static final int ROWS = 25;
    public static final int COLS = 25;
    public static final int BLOCK_SIZE = 25;
    //40 X 15 = 600 宽
    private static final int GAME_WIDTH = COLS * BLOCK_SIZE;
    // 40 X 15 = 600 高
    private static final int GAME_HEIGHT = ROWS * BLOCK_SIZE;


    Snake s = new Snake();
    Egg e = new Egg();

    private Yard() {
        this.setTitle("Snake begin！");
        this.setLocation(350, 10);
        this.setSize(700, 700);
//        this.setLayout(null);
        //增加键盘监听
        this.addKeyListener(new KeyMonitor());

        //对Frame的关闭
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    //重写了父类的paint()方法：画格子
    @Override
    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.GRAY);
        //注意：这个游戏界面距离左上角点的距离x=50， y=50
        g.fillRect(50, 50, COLS * BLOCK_SIZE, ROWS * BLOCK_SIZE);
        g.setColor(Color.DARK_GRAY);

        //画出横线
        for (int i=0; i<=ROWS; i++) {
            //第一条线的起始坐标(50，50) ----终点坐标（675，50）
            g.drawLine(50, 50+BLOCK_SIZE * i, 50 + COLS * BLOCK_SIZE, 50 + BLOCK_SIZE * i);
        }
        for(int i=1; i<=ROWS; i++){
            g.drawString(i+"行", 15, 40 + BLOCK_SIZE * i);
        }

        for (int i=0; i<=COLS; i++) {
            g.drawLine(50 + BLOCK_SIZE * i, 50, 50+ BLOCK_SIZE * i, 50+ BLOCK_SIZE * ROWS);
        }
        for(int i=1;i<=COLS;i++){
            g.drawString(i+"列", 25+BLOCK_SIZE * i, 45);
        }
        g.setColor(c);

        s.draw(g);
        e.draw(g);
        //画的时候，不断检测Snake 与 egg的碰撞
        s.eat(e);


    }


    //监听键盘的按键，控制Snake的运动
    //面向对象的角度：该运动的蛇的运动
    private class KeyMonitor extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
          s.keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            s.keyReleased(e);
        }
    }




    //消除闪烁
    Image offScreenImage = null;

    @Override
    public void update(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = this.createImage(50+GAME_WIDTH, 50+GAME_HEIGHT);
        }
        Graphics gOffScreen = offScreenImage.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.RED);
        gOffScreen.fillRect(50, 50, GAME_WIDTH, GAME_HEIGHT);
        gOffScreen.setColor(c);
        paint(gOffScreen);
        g.drawImage(offScreenImage, 0, 0, null);
    }



}
