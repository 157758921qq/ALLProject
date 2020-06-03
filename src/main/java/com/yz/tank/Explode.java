package com.yz.tank;

import java.awt.*;

public class Explode extends AbstractGameObject{
    private int x, y;
    private int step = 0;
    private boolean live = true;
    private int width, height;

    public Explode(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = ResourceMgr.explodes[0].getWidth();
        this.height = ResourceMgr.explodes[0].getHeight();
        new Thread(()->{
            new Audio("audio/explode.wav").play();
        }).start();
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void paint(Graphics g){

        g.drawImage(ResourceMgr.explodes[step], x, y, null);
        step++;
        if(step >= ResourceMgr.explodes.length ) {
            this.die();
        }
    }

    private void die() {
        this.setLive(false);
    }
}
