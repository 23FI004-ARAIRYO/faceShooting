import java.awt.Color;
import java.awt.Graphics;

public class Item {
    public static final int BIGGER_BULLET = 0;
    public static final int SPEED_UP = 1;
    public static final int ITEM_VARIATION = 2;

    private int x;
    private int y;
    private int vx;
    private int vy;
    private int radius;
    private int itemType;
    private int remainTime;
    private Color color = Color.BLUE;

    int w = 777;
    int h = 777;

    public Item(int x, int y, int radius, int itemType){
        this.x = x;
        this.y = y;
        this.vx = 0;
        this.vy = 0;
        this.radius = radius;
        this.remainTime = 1000;
        this.itemType = itemType;
        switch (itemType) {
            case BIGGER_BULLET:
                setColor(Color.BLUE);
                break;
            case SPEED_UP:
                setColor(Color.RED);
                break;
            default:
                break;
        }
    }

    public void move(){
        x += vx;
        y += vy;
        remainTime--;
    }

    public void setColor(Color color){
        this.color = color;
    }

    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g){
        g.setColor(color);
        g.fillOval(x, y, radius * 2, radius * 2);
    }

    public boolean isOutOfWindow(){
        if(x < 0 || w < x || y < 0 || h < y){
            return true;
        }
        return false;
    }

    public boolean isTimeOut(){
        if(remainTime < 0) return true;
        return false;
    }

    public void setX(int x){
        this.x = x;
    }
    public int getX(){
        return x;
    }
    public void setY(int y){
        this.y = y;
    }
    public int getY(){
        return y;
    }
    public void setVx(int vx){
        this.vx = vx;
    }
    public int getVx(){
        return vx;
    }
    public void setVy(int vy){
        this.vy = vy;
    }
    public int getVy(){
        return vy;
    }
    public void setRadis(int radius){
        this.radius = radius;
    }
    public int getRadius(){
        return radius;
    }
    public int getItemType(){
        return itemType;
    }
}// Item end
