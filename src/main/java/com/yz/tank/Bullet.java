package com.yz.tank;

import java.awt.*;
import java.util.BitSet;

public class Bullet extends AbstractGameObject {
    private int x, y;
    private static final int SPEED = 10;
    private Dir dir;
    private boolean live = true;
    private Group group;
    private int w = ResourceMgr.bulletD.getWidth();
    private int h = ResourceMgr.bulletD.getHeight();
    private Rectangle rect;

    public Bullet(int x, int y, Dir dir, Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        this.rect = new Rectangle(x, y, w, h);
    }

    public Rectangle getRect() {
        return rect;
    }

    public void setRect(Rectangle rect) {
        this.rect = rect;
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

    public Dir getDir() {
        return dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public void paint(Graphics g){
        //子弹 沿着 给定方向运动
        switch(dir){
            case L:
                g.drawImage(ResourceMgr.bulletL, x, y, null);
                break;
            case U:
                g.drawImage(ResourceMgr.bulletU, x, y, null);
                break;
            case R:
                g.drawImage(ResourceMgr.bulletR, x, y, null);
                break;
            case D:
                g.drawImage(ResourceMgr.bulletD, x, y, null);
                break;
        }

        move();
        rect.x = x;
        rect.y = y;

//        Color c = g.getColor();
//        g.setColor(Color.yellow);
//        g.drawRect(rect.x, rect.y, rect.width, rect.height);
//        g.setColor(c);
    }

    private void move() {
        switch(dir){
            case L:
                x -= SPEED;
                break;
            case U:
                y -= SPEED;
                break;
            case R:
                x += SPEED;
                break;
            case D:
                y += SPEED;
                break;
        }
        //子弹的碰撞检测


        //子弹move完后，做边界检查
       boundsCheck();


    }

    private void boundsCheck() {
        if(x < 0 || x > TankFrame.INSTANCE.GAME_WIDTH || y < 30 || y > TankFrame.INSTANCE.GAME_WIDTH){
            live = false;
        }
    }





    public void die() {
        this.setLive(false);
        TankFrame.INSTANCE.getGm().add(new Explode(x, y));
    }





}
