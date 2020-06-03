package com.yz.tank;

import com.yz.tank.chainofresponsibility.ColliderChain;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 这个model装的是各种游戏模型比如Tank、Bullet、Explode等，
 * view在这个项目里：TankFrame
 * 通过这个改造，将model 和 view分离开
 *
 * Facade模式
 * 调停者模式
 *
 */
public class GameModel {

    private Player myTank;
    private List<AbstractGameObject> objects;
    private ColliderChain chain = new ColliderChain();

    public GameModel() {
        initGameObjects();
    }

    public void add(AbstractGameObject go) {
        objects.add(go);
    }

    private void initGameObjects() {
        //初始化的时候生成主站坦克
        myTank = new Player(100, 100, Dir.D, Group.GOOD);
        objects = new ArrayList<>();

        for (int i = 0; i < Integer.parseInt(PropertyMgr.get("initTankCount")); i++) {
            objects.add(new Tank(80 + 85 * i, 500, Dir.U, Group.BAD));
        }

        this.add(new Wall(300, 200, 50, 100));
    }

    public void paint(Graphics g) {

        Color c = g.getColor();
        g.setColor(Color.yellow);
        g.drawString("Objects的数量："+objects.size(), 10 ,50);
        g.setColor(c);

        myTank.paint(g);

        //这里的碰撞检测怎么写？
        //条件：自己和自己不发生碰撞,自己和自己发生的子弹不发生碰撞
        //第一层从list中取每一个
        //第二层，从list中取每一个
        //让第一层的每一个 与 第二层的每一个进行碰撞检测

        for (int i = 0; i < objects.size(); i++) {
            if (!objects.get(i).isLive()) {
                objects.remove(i);
                break;
            }
            AbstractGameObject go1 = objects.get(i);
            for (int j = 0; j < objects.size(); j++) {
                AbstractGameObject go2 = objects.get(j);
                //设计碰撞器，两两物体相撞，逻辑不确定，抽象出来
                //两个物体相撞的规则
                //责任链模式
//                collider.collide(go1, go2);
//                collider1.collide(go1, go2);
                chain.collide(go1, go2);
            }
            objects.get(i).paint(g);
        }
    }

    public Player getMyTank(){
        return myTank;
    }



}
