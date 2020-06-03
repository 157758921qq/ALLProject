package com.yz.tank;

import java.awt.*;

public class Wall extends AbstractGameObject {
    private int x, y, w, h;
    //注意这个rect是静止的
    private Rectangle rect;

    public Wall(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.rect = new Rectangle(x, y, w, h);
    }

    public void paint(Graphics g){
        Color c = g.getColor();
        g.setColor(Color.GRAY);
        g.fillRect(x, y, w, h);
        g.setColor(c);
    }

    @Override
    public boolean isLive() {
        return true;
    }

    public Rectangle getRect() {
        //这样写是有问题的，每次调用都new一个对象
        //千万不能这样写
//        return new Rectangle(x, y, w, h);
        return rect;
    }
}
