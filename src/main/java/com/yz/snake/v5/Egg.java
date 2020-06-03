package com.yz.snake.v5;

import java.awt.*;
import java.util.Random;

public class Egg {
    private int row, col;
    private int w = Yard.BLOCK_SIZE;
    private int h = Yard.BLOCK_SIZE;
    private static Random r = new Random();
    private Color color = Color.GREEN;

    //得到food图片的宽和高
    private int width = ResourceMgr.food.getWidth();
    private int height = ResourceMgr.food.getHeight();

    public Egg(int row, int col){
        this.row = row;
        this.col = col;
        System.out.println("widht:" + width);
        System.out.println("height:" + height);
    }

    //初始化食物的位置： 都在控制在第几个格子中的
    public Egg(){
        //r.nextInt()   ----[0,1)
        //r.nextInt(20)   ----[0, 20)  = [0, 19]
        //行： r.nextInt(20)+2    ----[3, 22]
        //行： [3, 22]
        this(r.nextInt(Yard.ROWS -5)+3, r.nextInt(Yard.COLS -5)+ 3);
    }

    public void reAppear(){
        this.row = r.nextInt(Yard.ROWS -5)+3;
        this.col = r.nextInt(Yard.COLS -5)+ 3;
    }

    //是静止的物体得到Rectangle直接返回
    public Rectangle getRect(){
        return new Rectangle(Yard.BLOCK_SIZE * col, Yard.BLOCK_SIZE * row, w, h);
    }

    public void draw(Graphics g){
//
//        Color c = g.getColor();
//        g.setColor(color);
//        g.fillOval(Yard.BLOCK_SIZE * col, Yard.BLOCK_SIZE * row, w, h);
//        g.setColor(c);
//        if(color == Color.GREEN) color = Color.RED;
//        else color = Color.GREEN;
        //将食物画出来
        g.drawImage(ResourceMgr.food, Yard.BLOCK_SIZE * col, Yard.BLOCK_SIZE * row, null);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }


}
