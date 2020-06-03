package com.yz.snake.v4;

import java.awt.*;

/**
 * Snake的一节身体
 *
 * 注意：这个节点用的双向链表
 *      行数：第一行开始，非第0行
 *      列数：第一列开始，非第0列
 */

public class Node {
    int w = Yard.BLOCK_SIZE;
    int h = Yard.BLOCK_SIZE;
    int row, col;
    Dir dir = Dir.L;
    Node next = null;
    Node prev = null;


    Node(int row, int col, Dir dir) {
        this.row = row;
        this.col = col;
        this.dir = dir;
    }

    public void draw(Graphics g) {
//        Color c = g.getColor();
//        g.setColor(Color.BLACK);
//        //比如第 1行，第1列的这个方块：位置（0,0）
//        //第25行，25列的这个方块：位置（col * BLOCK_SIZE,  row * BLOCK_SIZE）
//        //只要告诉了第几行，第几列，就能求出这个方块的左上角点的坐标（col * BLOCK_SIZE,  row * BLOCK_SIZE）
//        //不要忘了起始坐标相对于（50， 50）
//        g.fillRect(50 + Yard.BLOCK_SIZE*col, 50 + Yard.BLOCK_SIZE *row, w, h);
//        g.setColor(c);


        //画图片版的时候，需要注意，如果是蛇头，根据蛇头的方向来画
        //如果画的身体，没问题直接画
        if(this.prev == null){
            //头结点
           switch(dir){
               case L:
                   g.drawImage(ResourceMgr.headLeft,50 + Yard.BLOCK_SIZE*col, 50 + Yard.BLOCK_SIZE *row, null);
                   break;
               case U:
                   g.drawImage(ResourceMgr.headUp,50 + Yard.BLOCK_SIZE*col, 50 + Yard.BLOCK_SIZE *row, null);
                   break;
               case R:
                   g.drawImage(ResourceMgr.headRight,50 + Yard.BLOCK_SIZE*col, 50 + Yard.BLOCK_SIZE *row, null);
                   break;
               case D:
                   g.drawImage(ResourceMgr.headDown,50 + Yard.BLOCK_SIZE*col, 50 + Yard.BLOCK_SIZE *row, null);
                   break;

           }
        } else
             g.drawImage(ResourceMgr.body, 50 + Yard.BLOCK_SIZE*col, 50 + Yard.BLOCK_SIZE *row, null);

    }
}
