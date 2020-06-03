package com.yz.tank.chainofresponsibility;

import com.yz.tank.AbstractGameObject;
import com.yz.tank.PropertyMgr;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * 责任链模式：链条，装一个一个责任处理对象
 *
 */
public class ColliderChain implements Collider {

    //存放Collider的容器
    private List<Collider> colliders;

    public ColliderChain() {
        initColliders();
    }

    private void initColliders() {
        colliders = new ArrayList<>();
        String[] colliderNames = PropertyMgr.get("colliders").split(",");
        try {
            for (String name : colliderNames) {
                //拿到每个类的名字，load到内存
                Class clazz = Class.forName("com.yz.tank.chainofresponsibility." + name);
                //new到内存，找到构造方法，new出对象
                Collider c = (Collider) (clazz.getConstructor().newInstance());
                colliders.add(c);
            }
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

    //ColliderChain 链条上每个节点进行两物体碰撞
    @Override
    public boolean collide(AbstractGameObject go1, AbstractGameObject go2) {
        for (Collider collider : colliders) {
            //需要判断
            //如果    终止    了，就 break
            if (!collider.collide(go1, go2)) {
                break;
            }
        }
        return true;
    }
}
