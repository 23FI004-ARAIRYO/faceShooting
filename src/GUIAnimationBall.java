
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

class GUIAnimationBall {

    GUIAnimatinFaceLook facelook;

    String cstr = "";
    Random rdn;

    String emotion = "normal";

    int w = 500;
    int h = 500;

    int x;
    int y;
    int radius; // 半径
    int centerX;
    int centerY;
    Color basicColor = Color.gray;
    Color initColor = Color.gray;
    public boolean damagedFlag = false;

    double xDir = -1; // 1:+方向 -1: -方向
    double yDir = 1;
    private int strCounter;

    private String basicLabelMessage = "(空白:未受信)";

    GUIAnimationBall(int w, int h) {

        rdn = new Random();
        xDir = rdn.nextDouble() * 10 - 5;
        yDir = rdn.nextDouble() * 10 - 5;
        this.w = w;
        this.h = h;
        this.radius = 20;

        setPosition(230, 230);
        setRadius(radius);// 30-60のサイズの顔の輪郭

        this.basicColor = new Color(rdn.nextInt(256), rdn.nextInt(256), rdn.nextInt(256));
        this.initColor = basicColor;

        facelook = new GUIAnimatinFaceLook();

    }

    /* 感情をセット */
    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }

    public void setMessage(String message) {
        this.basicLabelMessage = message;
    }

    void setBasicColor(Color bcolor) {
        this.basicColor = bcolor;
    }

    public void setCollisionText(String cstr, int strCounter) {
        this.cstr = cstr;
        this.strCounter = strCounter;
    }

    void move() {

        if ((xDir > 0) && (x + this.radius * 2 >= w)) {
            xDir = -1 * xDir;
            if (emotion.equals("angry")) {
                setCollisionText("右が痛いわぁ!!激おこ", 6);
            } else {
                setCollisionText("右が痛い。。。でも言えない", 3);
            }

        } else if ((xDir < 0) && (x <= 0)) {
            xDir = -1 * xDir;

            if (emotion.equals("angry")) {
                setCollisionText("左が痛いわぁ!!おこ", 6);
            } else {
                setCollisionText("左の頬が少し。。。", 4);
            }

        }

        if ((yDir > 0) && (y + this.radius * 2 >= h)) {
            yDir = -1 * yDir;
            if (emotion.equals("angry")) {
                setCollisionText("顎が痛いよ!!ぷんぷん", 7);
            } else {
                setCollisionText("AGO ITAI...Boku..", 4);
            }
        }
        if ((yDir < 0) && (y <= 0)) {
            yDir = -1 * yDir;
            if (emotion.equals("angry")) {
                setCollisionText("頭をぶつけたよ痛いよ!!まじ痛いよ", 8);
            } else {
                setCollisionText("こつん.", 4);
            }

        }

    }

    void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        centerX = x + radius;
        centerY = y + radius;
    }

    void setRadius(int r) {
        this.radius = r;
    }

    public int getRadius(){
        return radius;
    }

    public void damage(){
        setBasicColor(Color.red);
        damagedFlag = true;
    }

    public boolean isDamaged(){
        return damagedFlag;
    }

    public void damageReset(){
        basicColor = initColor;
    }

    public boolean collision(int targetX, int targetY){
        if(Math.sqrt((targetX - centerX) * (targetX - centerX) +
                    (targetY - centerY) * (targetY - centerY)) <= radius){
            return true;
        }
        return false;
    }

    public double getDistance(double x, double y){
        return Math.sqrt((x - centerX) * (x - centerX) + (y - centerY) * (y - centerY));
    }

    void draw(Graphics g) {

        //円の処理
        g.setColor(basicColor);
        g.fillOval(x, y, 2 * radius, 2 * radius); // rは半径なので2倍にする
        g.drawString(basicLabelMessage, x - 5, y - 10);

        if (strCounter > 0) {
            g.drawString(cstr, x, y);
            strCounter--;
        } else {
            cstr = "";
        }

        facelook.setXY(x, y);
        facelook.setSize(2 * radius, 2 * radius);

        //fobj.makeFace(g);
        facelook.makeFace(g, emotion);

        g.setColor(initColor);

    }

}// ball rim end
