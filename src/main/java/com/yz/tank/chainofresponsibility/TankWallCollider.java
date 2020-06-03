package com.yz.tank.chainofresponsibility;

import com.yz.tank.AbstractGameObject;
import com.yz.tank.Bullet;
import com.yz.tank.Tank;
import com.yz.tank.Wall;

public class TankWallCollider implements Collider {
    @Override
    public boolean collide(AbstractGameObject go1, AbstractGameObject go2) {
        if (go1 instanceof Tank && go2 instanceof Wall) {
            Tank t = (Tank) go1;
            Wall w = (Wall) go2;
            if(t.isLive()){
                if(t.getRect().intersects(w.getRect())){
                   t.back();
                   return true;
                }
            }
        }
        if (go1 instanceof Wall && go2 instanceof Tank) {
            return collide(go2, go1);
        }
        return true;
    }
}
