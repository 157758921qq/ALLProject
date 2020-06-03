package com.yz.tank.chainofresponsibility;

import com.yz.tank.AbstractGameObject;

/**
 * 碰撞器
 * 负责两个物体的相撞
 */
public interface Collider {
    /**
     * 关键：
     * 每个collide返回boolean类型
     * 这个boolean告诉你，每个碰撞后，要不要终止
     * true     继续
     * false    终止
     *
     */
    public boolean collide(AbstractGameObject go1, AbstractGameObject go2);

}
