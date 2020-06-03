package com.yz.tank;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

/**
 * 敌人坦克
 */
public class Tank extends AbstractGameObject{
    private int x, y;
    private Dir dir;
    private Group group;
    private boolean live = true;
    private boolean moving = true;
    private boolean bL, bU, bR, bD;
    private static final int SPEED = 4;
    private int w = ResourceMgr.badTankD.getWidth();
    private int h = ResourceMgr.badTankD.getHeight();
    //碰撞检测
    private Rectangle rect;
    //边界检测，回到之前位置
    private int oldX, oldY;

    public Tank(int x, int y, Dir dir, Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        this.oldX = x;
        this.oldX = y;
        this.rect = new Rectangle(x, y, w, h);
    }

    public Rectangle getRect() {
        return rect;
    }

    public void setRect(Rectangle rect) {
        this.rect = rect;
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

    public Dir getDir() {
        return dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    //敌方坦克
    // 自己画自己的方法
    public void paint(Graphics g) {
//      g.fillRect(x, y, 50, 50);
//        BufferedImage mainTank = null;
//        try {
//            mainTank = ImageIO.read(Tank.class.getClassLoader().getResourceAsStream("images/GoodTank1.png"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        g.drawImage(mainTank,x, y, null );

        if(!this.isLive()) return;
        //根据方向画坦克图片
        switch(dir){
            case L:
                g.drawImage(ResourceMgr.badTankL, x, y, null);
                break;
            case U:
                g.drawImage(ResourceMgr.badTankU, x, y, null);
                break;
            case R:
                g.drawImage(ResourceMgr.badTankR, x, y, null);
                break;
            case D:
                g.drawImage(ResourceMgr.badTankD, x, y, null);
                break;
        }
        //注意paint()方法，25毫秒画出自己
        //如果坦克是有方向，则移动，再窗口刷新时不断paint()自己的位置
        move();

        //更新Rectangle中x, y
        // update rect
        rect.x = x;
        rect.y = y;
    }



    private void move() {
        if(!moving) return;
        //移动前保存x, y位置
        oldX = x;
        oldY = y;

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
        //方向改变，检测是否出界
        boundsCheck();

        //地方坦克移动完后，给随机方向
        randomDir();

        //控制开火频率
        if(r.nextInt(100) > 90 ) {
            fire();
        }

    }


    private void boundsCheck() {
        if(x < 2  || y< 32 || x > TankFrame.GAME_WIDTH- ResourceMgr.badTankU.getWidth()-3 || y > TankFrame.GAME_HEIGHT - ResourceMgr.badTankU.getHeight()-3){
            this.back();
        }
    }

    public void back() {
        this.x = oldX;
        this.y = oldY;
    }


    private Random random = new Random();
    private Random r = new Random();
    private void randomDir() {
        if(r.nextInt(100) > 95) {
            this.dir = Dir.values()[random.nextInt(Dir.values().length)];
        }
    }

    //按住control键发出一系列子弹
    private void fire() {
        //子弹发射的位置,应该在坦克中间
        int bX = x +ResourceMgr.goodTankD.getWidth()/2 -ResourceMgr.bulletU.getWidth()/2;
        int bY = y + ResourceMgr.goodTankD.getHeight()/2 - ResourceMgr.bulletU.getHeight()/2;
        TankFrame.INSTANCE.getGm().add(new Bullet(bX, bY, this.dir, this.group));
    }

    public void die() {
        this.setLive(false);
    }
}
