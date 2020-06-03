package com.yz.tank;

import com.yz.tank.strategy.DefaultFireStrategy;
import com.yz.tank.strategy.FireStrategy;
import com.yz.tank.strategy.FourDirFireStrategy;
import com.yz.tank.strategy.LeftRightDirFireStrategy;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * 主战坦克
 */
public class Player extends AbstractGameObject{
    private int x, y;
    private Dir dir;
    private Group group;
    private boolean moving = false;
    private boolean bL, bU, bR, bD;
    private static final int SPEED = 5;




    public Player(int x, int y, Dir dir, Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        this.initFireStrategy();
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

    //坦克自己的画自己的方法
    public void paint(Graphics g) {
//      g.fillRect(x, y, 50, 50);
//        BufferedImage mainTank = null;
//        try {
//            mainTank = ImageIO.read(Tank.class.getClassLoader().getResourceAsStream("images/GoodTank1.png"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        g.drawImage(mainTank,x, y, null );

        switch(dir){
        case L:
            g.drawImage(ResourceMgr.goodTankL, x, y, null);
            break;
        case U:
            g.drawImage(ResourceMgr.goodTankU, x, y, null);
            break;
        case R:
            g.drawImage(ResourceMgr.goodTankR, x, y, null);
            break;
        case D:
            g.drawImage(ResourceMgr.goodTankD, x, y, null);
            break;
    }
    move();
}

    @Override
    public boolean isLive() {
        return false;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        //根据键盘的按键，设置坦克的移动（X）
        //改成根据键盘的按键，得到坦克的方向
        //根据坦克方向，移动坦克
        switch(key){
            case KeyEvent.VK_LEFT:
                bL = true;
                break;
            case KeyEvent.VK_UP:
                bU = true;
                break;
            case KeyEvent.VK_RIGHT:
                bR = true;
                break;
            case KeyEvent.VK_DOWN:
                bD = true;
                break;
        }
        //根据bL, bU, bR, bD的值来得到坦克的方向
        setMainDir();

    }

    private void setMainDir() {
        if(!bL && !bU && !bR && !bD){//四个方向都没有被按下，说明坦克是静止的
            moving = false;
        } else {
            moving = true;
            if (bL && !bU && !bR && !bD) {
                dir = Dir.L;
            }
            if (!bL && bU && !bR && !bD) {
                dir = Dir.U;
            }
            if (!bL && !bU && bR && !bD) {
                dir = Dir.R;
            }
            if (!bL && !bU && !bR && bD) {
                dir = Dir.D;
            }
        }
    }

    private void move() {
        if(!moving) return;
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
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch(key){
            case KeyEvent.VK_LEFT:
                bL = false;
                break;
            case KeyEvent.VK_UP:
                bU = false;
                break;
            case KeyEvent.VK_RIGHT:
                bR = false;
                break;
            case KeyEvent.VK_DOWN:
                bD = false;
                break;
            case KeyEvent.VK_CONTROL:
                fire();
                break;
        }
        //根据方向，移动坦克
        setMainDir();
    }

    //提高成  成员变量，Player的构造方法中完成初始化
    private FireStrategy strategy = null;
    private void initFireStrategy(){
        ClassLoader loader = Player.class.getClassLoader();
        String className = PropertyMgr.get("tankFireStrategy");
        try {
            Class<?> clazz = loader.loadClass("com.yz.tank.strategy."+ className);
//            Class<?> clazz = Class.forName("com.yz.tank.strategy." + className);
            strategy  =(FireStrategy) clazz.getDeclaredConstructor().newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    //按住control键发出一系列子弹
    private void fire() {
//        FireStrategy strategy = new DefaultFireStrategy();
        strategy.fire(this);
    }



}
