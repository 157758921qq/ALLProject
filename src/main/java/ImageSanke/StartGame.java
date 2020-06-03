package ImageSanke;

import javax.swing.*;
import java.awt.*;

public class StartGame {
    //这是一个main方法，是程序的入口：
    public static void main(String[] args) {
        //1.创建一个窗体：
        JFrame jf = new JFrame();
        jf.setTitle("贪吃蛇小游戏");
            /*
       setBounds方法有四个参数：
        第一个参数，第二个参数：分别为距离屏幕左上角的x,y轴的距离
        第三个参数，第四个参数：分别为窗体的宽高
         */
        //显示器的宽度和高度：
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;

        jf.setBounds((width - 800) / 2, (height - 800) / 2, 800, 800);
        //设置窗口大小不可变：
        jf.setResizable(false);
        //点击窗口的右上角的×的时候，窗口关闭的同时要让游戏停止：
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //创建一个面板：
        GamePanel gp = new GamePanel();
        //将面板添加到窗口中：
        jf.add(gp);
        //窗体是有了，但是必须得设置让它显现出来
        //注意：这句话尽量放在最后一行
        jf.setVisible(true);
    }

}
