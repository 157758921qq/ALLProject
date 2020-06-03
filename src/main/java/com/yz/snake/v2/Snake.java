package com.yz.snake.v2;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * 注意：snake是有蛇的身体一节一节组成
 * 并且每个节点需要保存：前驱节点 和  后继节点的指针信息
 * 保存蛇的长度
 *
 * 有个关键点是：Snake的方向，保存在蛇头节点里：因为这个节点有Dir
 * 也就是说蛇的方向就是指蛇头的方向
 *
 */
public class Snake {
    private Node head ;
    private Node tail;
    private int size = 0;
    private boolean bL, bU, bR, bD;
    //Begin Game ，init a snake, location(row = 10, col= 10)
    private Node n = new Node(10,10, Dir.L);

    public Snake(){
        head = n;//赋值
        tail = n;
        size = 1;
    }


    public void addToTail() {
        Node node = null;
        //根据尾巴节点的方向，增加新节点
        //注意：新增节点的位置和方向
        switch(tail.dir) {
            case L :
                node = new Node(tail.row, tail.col + 1, tail.dir);
                break;
            case U :
                node = new Node(tail.row + 1, tail.col, tail.dir);
                break;
            case R :
                node = new Node(tail.row, tail.col - 1, tail.dir);
                break;
            case D :
                node = new Node(tail.row - 1, tail.col, tail.dir);
                break;
        }
        //重点：
        //new出新节点后，需要移动前驱节点指针
        tail.next = node;//将new出来的node节点赋值给tail的next指针
        node.prev = tail;
        tail = node;
        size ++;
    }

    public void addToHead() {
        Node node = null;
        switch(head.dir) {
            case L :
                node = new Node(head.row, head.col - 1, head.dir);
                break;
            case U :
                node = new Node(head.row - 1, head.col, head.dir);
                break;
            case R :
                node = new Node(head.row, head.col + 1, head.dir);
                break;
            case D :
                node = new Node(head.row + 1, head.col, head.dir);
                break;
        }
        node.next = head;
        head.prev = node;
        head = node;
        size ++;
    }

    public void draw(Graphics g) {
        if(size <= 0) return;
        move();
        for(Node n = head; n!=null; n=n.next){
            n.draw(g);
        }

    }



    private void move() {
        addToHead();
        deleteFromTail();
    }

    private void deleteFromTail() {
        //注意逻辑：
        //1、将tail的前一个节点，设为tail
        //2、将tail的节点的prev，设为null
        if(size == 0) return;
        tail = tail.prev;
        tail.next = null;

    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        System.out.println("key:"+key);
        switch(key){
            case KeyEvent.VK_LEFT:
                bL = true;
                break;
            case KeyEvent.VK_UP:
                bU = true;
                break;
            case KeyEvent.VK_RIGHT:
                bR = true;
                break;
            case KeyEvent.VK_DOWN:
                bD = true;
                break;
        }
        setMainDir();
    }



    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        System.out.println("key:"+key);
        switch(key){
            case KeyEvent.VK_LEFT:
                bL = false;
                break;
            case KeyEvent.VK_UP:
                bU = false;
                break;
            case KeyEvent.VK_RIGHT:
                bR = false;
                break;
            case KeyEvent.VK_DOWN:
                bD = false;
                break;
        }
        setMainDir();
    }


    private void setMainDir() {
        if(bL && !bU && !bR && !bD && head.dir != Dir.R){
            head.dir = Dir.L;
        }
        if(!bL && bU && !bR && !bD && head.dir != Dir.D){
            head.dir = Dir.U;
        }
        if(!bL && !bU && bR && !bD && head.dir != Dir.L){
            head.dir = Dir.R;
        }
        if(!bL && !bU && !bR && bD && head.dir != Dir.U){
            head.dir = Dir.D;
        }
    }
}
