package com.yz.tank.strategy;

import com.yz.tank.Player;
import com.yz.tank.Tank;


/**
 * 策略模式
 */
public interface FireStrategy {
    public void fire(Player player);
}
