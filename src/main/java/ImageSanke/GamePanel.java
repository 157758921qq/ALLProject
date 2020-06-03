package ImageSanke;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends JPanel {
    //小蛇的长度：
    int length;
    //定义一个数组，专门存储小蛇的头和身体对应的x轴坐标
    int[] snakeX = new int[500];//500小蛇长度
    //定义一个数组，专门存储小蛇的头和身体对应的y轴坐标
    int[] snakeY = new int[500];//500小蛇长度

    //定义一个变量来判断小蛇的方向：
    String direction = "R";//默认情况下小蛇是向右的
    //上U下D左L右R

    //定义一个变量判断游戏的状态：是开始的还是结束：
    boolean isStart = false;//默认情况下游戏没有开始

    //加入一个定时器：
    Timer timer;

    //定义食物的坐标：
    int foodX;
    int foodY;

    //定义一个积分：
    int score;

    //定义一个变量 判断小蛇是否死亡：
    boolean isDie = false;//默认情况下小蛇是活着

    //初始化信息：
    public void init() {
        length = 3;
        //初始化蛇头的信息：
        snakeX[0] = 200;
        snakeY[0] = 375;
        //第一节身子的信息：
        snakeX[1] = 175;
        snakeY[1] = 375;
        //第二节身子的信息：
        snakeX[2] = 150;
        snakeY[2] = 375;

        //初始化食物信息：
        foodX = 325;
        foodY = 125;

        //设置定时器，让定时器每100ms执行一次操作：
        timer = new Timer(100, new ActionListener() {
            //相当于每100ms执行一下actionPerformed中的动作：
            @Override
            public void actionPerformed(ActionEvent e) {
                //让小蛇动一下：
                //如果游戏是开始的情况下，小蛇才会动：
                //再加入一个判断：在游戏开始的状态下，小蛇是活着的
                if (isStart == true && isDie == false) {
                    //注意：后一节跟着前一节动：
                    //先动身子
                    for (int i = length - 1; i > 0; i--) {
                        snakeX[i] = snakeX[i - 1];
                        snakeY[i] = snakeY[i - 1];
                    }
                    //最后动头：
                    switch (direction) {
                        case "R":
                            snakeX[0] = snakeX[0] + 25;
                            break;
                        case "L":
                            snakeX[0] = snakeX[0] - 25;
                            break;
                        case "U":
                            snakeY[0] = snakeY[0] - 25;
                            break;
                        case "D":
                            snakeY[0] = snakeY[0] + 25;
                            break;
                    }
                    //防止小蛇出界：(上下左右都要进行限制)
                    if (snakeX[0] > 750) {
                        snakeX[0] = 25;
                    }
                    if (snakeX[0] < 25) {
                        snakeX[0] = 750;
                    }

                    if (snakeY[0] < 100) {
                        snakeY[0] = 725;
                    }
                    if (snakeY[0] > 725) {
                        snakeY[0] = 100;
                    }

                    //当蛇头的坐标和食物的坐标一样的时候，就相当于碰撞了
                    if (snakeX[0] == foodX && snakeY[0] == foodY) {
                        //蛇长度加一：
                        length++;
                        //食物的坐标换位置：
                        foodX = 25 + 25 * (new Random().nextInt(28));//25-725
                        foodY = 100 + 25 * (new Random().nextInt(25));
                        ;    //100-725

                        //每碰撞一次 积分要加10：
                        score = (length - 3) * 10;
                    }

                    //小蛇死亡判定：
                    for (int i = 1; i < length; i++) {
                        if (snakeX[0] == snakeX[i] && snakeY[0] == snakeY[i]) {
                            isDie = true;
                        }
                    }
                    repaint();
                }
            }
        });

        //启动定时器
        timer.start();
    }

    public GamePanel() {
        init();
        //让焦点在面板上：
        this.setFocusable(true);//获取焦点
        //给面板添加一个监听：
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                int keyCode = e.getKeyCode();
                System.out.println(keyCode);
                if (keyCode == KeyEvent.VK_SPACE) {//证明你按下的是空格键：
                    if (isDie) {//小蛇死了：
                        //之前的数据全部恢复到初始化状态：
                        init();
                        isDie = false;
                    } else {//小蛇没死的情况下
                        isStart = !isStart;
                        repaint();//重新绘制界面-->底层会去调用：paintComponent方法
                    }
                }
                if (keyCode == KeyEvent.VK_UP) {
                    direction = "U";
                }
                if (keyCode == KeyEvent.VK_DOWN) {
                    direction = "D";
                }
                if (keyCode == KeyEvent.VK_LEFT) {
                    direction = "L";
                }
                if (keyCode == KeyEvent.VK_RIGHT) {
                    direction = "R";
                }


            }
        });
    }

    //paintComponent:方法不需要我们自己去调用，系统线程会去调用，
    //这个方法作用：所有画的动作都在这个方法中执行：
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //给面板设置一个背景色：
        this.setBackground(new Color(235, 194, 168));
        //画头部图片：
        Images.headerImg.paintIcon(this, g, 10, 10);

        //画一个矩形：
        //换一下画笔的颜色：
        g.setColor(new Color(213, 238, 232));
        g.fillRect(10, 70, 770, 685);

        //根据小蛇的方向来画蛇头：
        switch (direction) {
            case "L":
                Images.leftImg.paintIcon(this, g, snakeX[0], snakeY[0]);
                break;
            case "R":
                Images.rightImg.paintIcon(this, g, snakeX[0], snakeY[0]);
                break;
            case "U":
                Images.upImg.paintIcon(this, g, snakeX[0], snakeY[0]);
                break;
            case "D":
                Images.downImg.paintIcon(this, g, snakeX[0], snakeY[0]);
                break;
        }

              /*//画第一节身子：
        Images.bodyImg.paintIcon(this,g,snakeX[1],snakeY[1]);
        //画第二节身子：
        Images.bodyImg.paintIcon(this,g,snakeX[2],snakeY[2]);*/
        //对蛇身子的画法进行优化，加入循环：
        for (int i = 1; i < length; i++) {
            Images.bodyImg.paintIcon(this, g, snakeX[i], snakeY[i]);
        }

        //画食物：
        Images.foodImg.paintIcon(this, g, foodX, foodY);


        //当游戏暂停的时候，在面板中画文字：
        if (isStart == false) {//游戏是暂停的时候，才绘制文字：
            g.setColor(new Color(255, 186, 90));
            //设置：字体，加粗，字号
            g.setFont(new Font("微软雅黑", Font.BOLD, 40));
            g.drawString("点击空格游戏开始", 250, 330);
        }


        //画积分：
        g.setColor(new Color(255, 255, 255));
        //设置：字体，加粗，字号
        g.setFont(new Font("微软雅黑", Font.BOLD, 20));
        g.drawString("积分:" + score, 620, 40);

        //如果小蛇死亡，在页面中画入提示语：
        if (isDie) {
            g.setColor(new Color(255, 56, 49));
            //设置：字体，加粗，字号
            g.setFont(new Font("微软雅黑", Font.BOLD, 30));
            g.drawString("小蛇撞到自己死亡了,游戏停止,按下空格重新开始游戏", 40, 330);
        }

    }

}
