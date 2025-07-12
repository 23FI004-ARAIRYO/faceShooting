import java.awt.Color;
import java.awt.Graphics;

public class Bullet {
    private int x;
    private int y;
    private int vx;
    private int vy;
    private int radius;
    private Color color = Color.red;

    int w = 500;
    int h = 500;

    public Bullet(int x, int y, int vx, int vy, int radius){
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.radius = radius;
    }

    public void move(){
        x += vx;
        y += vy;
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
}
