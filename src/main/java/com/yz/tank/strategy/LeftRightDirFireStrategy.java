package com.yz.tank.strategy;

import com.yz.tank.*;

public class LeftRightDirFireStrategy implements FireStrategy {
    @Override
    public void fire(Player player) {
        //子弹发射的位置,应该在坦克中间
        int bX = player.getX() + ResourceMgr.goodTankD.getWidth()/2 -ResourceMgr.bulletU.getWidth()/2;
        int bY = player.getY() + ResourceMgr.goodTankD.getHeight()/2 - ResourceMgr.bulletU.getHeight()/2;
        TankFrame.INSTANCE.getGm().add(new Bullet(bX, bY, Dir.R, player.getGroup()));
        TankFrame.INSTANCE.getGm().add(new Bullet(bX, bY, Dir.L, player.getGroup()));
    }

}
