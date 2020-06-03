package ImageSanke;

import javax.swing.*;
import java.net.URL;

public class Images {
    //将图片load到内存
    public static URL headrURL = Images.class.getResource("/snakeImgs/header.png");
    public static ImageIcon headerImg = new ImageIcon(headrURL);

    public static URL bodyURL = Images.class.getResource("/snakeImgs/body.png");
    public static ImageIcon bodyImg = new ImageIcon(bodyURL);

    public static URL downURL = Images.class.getResource("/snakeImgs/down.png");
    public static ImageIcon downImg = new ImageIcon(downURL);

    public static URL foodURL = Images.class.getResource("/snakeImgs/food.png");
    public static ImageIcon foodImg = new ImageIcon(foodURL);

    public static URL leftURL = Images.class.getResource("/snakeImgs/left.png");
    public static ImageIcon leftImg = new ImageIcon(leftURL);

    public static URL rightURL = Images.class.getResource("/snakeImgs/right.png");
    public static ImageIcon rightImg = new ImageIcon(rightURL);

    public static URL upURL = Images.class.getResource("/snakeImgs/up.png");
    public static ImageIcon upImg = new ImageIcon(upURL);

}
