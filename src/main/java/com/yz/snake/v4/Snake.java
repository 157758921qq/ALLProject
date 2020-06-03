package com.yz.snake.v4;

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

    //Begin Game，init a snake, location(row = 10, col= 10)
    private Node n = new Node(10,10, Dir.L);
    private Rectangle rectangle;


    public Snake(){
        head = n;//赋值
        tail = n;
        size = 1;
        System.out.println("prev:" + n.prev);
        System.out.println("tail:" + n.next);
        this.rectangle = new Rectangle(50+ n.col * Yard.BLOCK_SIZE, 50 + n.row * Yard.BLOCK_SIZE, Yard.BLOCK_SIZE, Yard.BLOCK_SIZE);
    }

    public Rectangle getRect() {
        return rectangle;
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
        node.next = head;   //指针：node next 指向 head
        head.prev = node;   //指针：head prev 指向 node
        head = node;        //head 指向  node
        size ++;            //长度加 1
    }

    public void draw(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.YELLOW);
        g.drawRect(50+ head.col * Yard.BLOCK_SIZE, 50 + head.row * Yard.BLOCK_SIZE, Yard.BLOCK_SIZE, Yard.BLOCK_SIZE);
        g.setColor(c);

        if(size <= 0) return;
        //键盘控制的更加灵敏
        move();
        for(Node n = head; n!=null; n=n.next){
            n.draw(g);
        }
    }



    private void move() {

        rectangle.x = 50 + head.col * Yard.BLOCK_SIZE;
        rectangle.y = 50 + head.row * Yard.BLOCK_SIZE;
//        System.out.println("rect.x:"+ rectangle.x);
//        System.out.println("rect.y:"+ rectangle.y);

        addToHead();
        deleteFromTail();

        //移动后，需要检测蛇是否出界
        checkDead();

    }

    private void checkPause() {
        Main.INSTANCE.pt.pause();
    }

    private void checkDead() {
        //这个边界1、不能出边界
        if(head.row < 0 || head.col < 0 || head.row > Yard.ROWS -1 || head.col > Yard.COLS -1)  {
            Main.INSTANCE.pt.gameOver();
        }
        //蛇头不能碰到自己身体
        for(Node n = head.next; n != null; n = n.next) {
            if(head.row == n.row && head.col == n.col) {
                Main.INSTANCE.pt.gameOver();
            }
        }
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
            case KeyEvent.VK_SPACE:
                Main.INSTANCE.pt.pause();
                break;
        }
        setMainDir();
    }



    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
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



    public void eat(Egg e) {
        if(this.getRect().intersects(e.getRect())) {
            System.out.println("snake  collidesWith egg!");
            e.reAppear();
            this.addToHead();
        }
    }

}
