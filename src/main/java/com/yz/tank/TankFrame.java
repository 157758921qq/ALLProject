package com.yz.tank;

import com.yz.tank.chainofresponsibility.BulletTankCollider;
import com.yz.tank.chainofresponsibility.BulletWallCollider;
import com.yz.tank.chainofresponsibility.Collider;
import com.yz.tank.chainofresponsibility.ColliderChain;

import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TankFrame extends Frame {


    public static final TankFrame INSTANCE = new TankFrame();
    private int x = 100, y = 100;
    private static final int SPEED = 5;
    public static final int GAME_WIDTH = 800;
    public static final int GAME_HEIGHT = 600;
    private GameModel gm = new GameModel();



    //生成主站坦克

//    private  Tank enemyTank;
//    private  Bullet bullet;
//    private List<Bullet> bullets;
//    private List<Tank> tanks;
//    private List<Explode> explodes;


//    private List<Collider> colliders


    private TankFrame(){
        this.setTitle("Tank War");
        this.setLocation(400,100);
        this.setSize(GAME_WIDTH, GAME_HEIGHT);


        //初始化敌方坦克
//        enemyTank = new Tank(300, 200, Dir.R, Group.BAD);
//        tanks = new ArrayList<>();

//        bullet = new Bullet(x, y, Dir.R, Group.GOOD);
//        bullets = new ArrayList<>();
//        explodes = new ArrayList<>();
        //增加键盘监听
        this.addKeyListener(new MyKeyListener());
        //窗口关闭
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

    }








    //定义一个碰撞器：成员变量
//    Collider collider = new BulletTankCollider();
//    Collider collider1 = new BulletWallCollider();
//    private List<Collider> colliders = Arrays.asList(new BulletTankCollider(), new BulletWallCollider());



    @Override
    public void paint(Graphics g) {
        gm.paint(g);


//        enemyTank.paint(g);
//        bullet.paint(g);
//        for(int i=0; i< tanks.size(); i++){
//           if(!tanks.get(i).isLive()){
//               tanks.remove(i);
//           } else {
//               tanks.get(i).paint(g);
//            }
//        }
//
//        for(int i=0; i<bullets.size(); i++){
//            //先碰 ，死了就不画
//            for(int j=0; j<tanks.size(); j++){
//                bullets.get(i).collidesWithTank(tanks.get(j));
//            }
////            bullets.get(i).collidesWithTank(enemyTank);
//            if(!bullets.get(i).isLive())
//                bullets.remove(i);
//            else{
//                bullets.get(i).paint(g);
//            }
//
//        }
//
//
//        for(int i=0; i< explodes.size(); i++){
//            if(!explodes.get(i).isLive()){
//                explodes.remove(i);
//            } else {
//                explodes.get(i).paint(g);
//            }
//        }



    }


    //键盘监听
    private class MyKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            gm.getMyTank().keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            gm.getMyTank().keyReleased(e);
        }
    }



    //消除闪烁
    Image offScreenImage = null;

    @Override
    public void update(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
        }
        Graphics gOffScreen = offScreenImage.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.BLACK);
        gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        gOffScreen.setColor(c);
        paint(gOffScreen);
        g.drawImage(offScreenImage, 0, 0, null);
    }


    //这个GameModel也可以写成单例模式
//    public void add(AbstractGameObject object){
//        this.gm.add(object);
//    }

    public GameModel getGm(){
        return this.gm;
    }

}
