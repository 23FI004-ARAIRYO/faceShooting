
import java.awt.Color;
import java.awt.Graphics;

public class Item {

    public static final int BIGGER_BULLET = 0;
    public static final int SPEED_UP = 1;
    public static final int HEAL_HP = 2;
    public static final int BOMB = 3;
    public static final int ITEM_VARIATION = 4;

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

    public Item(int x, int y, int radius, int itemType) {
        this.x = x;
        this.y = y;
        this.vx = 0;
        this.vy = 0;
        this.radius = radius;
        this.remainTime = 1000;
        this.itemType = itemType;
        switch (itemType) {
            case BIGGER_BULLET:
                setColor(Color.RED);
                break;
            case SPEED_UP:
                setColor(Color.BLUE);
                break;
            case HEAL_HP:
                setColor(Color.GREEN);
                break;
            case BOMB:
                setColor(Color.BLACK);
                break;
            default:
                break;
        }
    }

    public void move() {
        x += vx;
        y += vy;
        remainTime--;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, radius * 2, radius * 2);
        g.setColor(Color.WHITE);
        switch (itemType) {
            case BIGGER_BULLET:
                g.drawString("+", x + radius - 3, y + radius + 4); // 中心にマーク描画
                break;
            case SPEED_UP:
                g.drawString("⇒", x + radius - 6, y + radius + 4);
                break;
            case HEAL_HP:
                g.drawString("💗", x + radius - 6, y + radius + 4);
                break;
            case BOMB:
                g.drawString("💀", x + radius - 6, y + radius + 4);
                break;
            default:
                break;
        }
    }

    public boolean isOutOfWindow() {
        if (x < 0 || w < x || y < 0 || h < y) {
            return true;
        }
        return false;
    }

    public boolean isTimeOut() {
        if (remainTime < 0) {
            return true;
        }
        return false;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public void setVx(int vx) {
        this.vx = vx;
    }

    public int getVx() {
        return vx;
    }

    public void setVy(int vy) {
        this.vy = vy;
    }

    public int getVy() {
        return vy;
    }

    public void setRadis(int radius) {
        this.radius = radius;
    }

    public int getRadius() {
        return radius;
    }

    public int getItemType() {
        return itemType;
    }
}// Item end
