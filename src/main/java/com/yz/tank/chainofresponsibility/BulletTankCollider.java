package com.yz.tank.chainofresponsibility;

import com.yz.tank.AbstractGameObject;
import com.yz.tank.Bullet;
import com.yz.tank.Tank;

/**
 * 子弹和坦克的碰撞
 *
 * 注意，return false就不继续了
 */
public class BulletTankCollider implements Collider {
    @Override
    public boolean collide(AbstractGameObject go1, AbstractGameObject go2) {
        if (go1 instanceof Bullet && go2 instanceof Tank) {
            Bullet b = (Bullet) go1;
            Tank t = (Tank) go2;
            if(!b.isLive() || !t.isLive()) return false;
            if(b.getGroup() == t.getGroup()) return true;
            //撞击的是地方坦克
            if(b.getRect().intersects(t.getRect())){
                b.die();
                t.die();
                return false;
            }
        }
        if (go1 instanceof Tank && go2 instanceof Bullet) {
           return collide(go2, go1);
        }
        return true;
    }
}
