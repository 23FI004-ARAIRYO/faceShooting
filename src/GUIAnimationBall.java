
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

class GUIAnimationBall {

    GUIAnimatinFaceLook facelook;

    String cstr = "";
    Random rdn;

    String emotion = "normal";

    int w = 777;
    int h = 777;

    int x;
    int y;
    int radius; // 半径
    int radiusOfBullet;
    int vXOfBullet;
    int vYOfBullet;
    int centerX;
    int centerY;
    int score;
    String name = "";
    Color basicColor = Color.gray;
    Color initColor = Color.gray;
    public boolean damagedFlag = false;

    double xDir = -1; // 1:+方向 -1: -方向
    double yDir = 1;
    private int strCounter;

    private String basicLabelMessage = "(空白:未受信)";
    //HP
    private int maxHP = 50;
    private int currentHP = 50;
    private boolean isDead = false;

    GUIAnimationBall(int w, int h) {

        rdn = new Random();
        xDir = rdn.nextDouble() * 10 - 5;
        yDir = rdn.nextDouble() * 10 - 5;
        this.w = w;
        this.h = h;
        this.radius = 20;
        this.radiusOfBullet = 2;
        this.vXOfBullet = 10;
        this.vYOfBullet = 10;
        this.score = 0;

        setPosition(rdn.nextInt(w - 100) + 50, rdn.nextInt(h - 100) + 50);
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

    public int getRadius() {
        return radius;
    }

    void setRadiusOfBullet(int r) {
        this.radiusOfBullet = r;
        if (r > 10) {
            r = 10;
        }
    }

    public int getRadiusOfBullet() {
        return radiusOfBullet;
    }

    public void setVXOfBullet(int vx) {
        this.vXOfBullet = vx;
        if (vXOfBullet > 30) {
            vXOfBullet = 30;
        }
    }

    public int getVXOfBullet() {
        return vXOfBullet;
    }

    public void setVYOfBullet(int vy) {
        this.vYOfBullet = vy;
        if (vYOfBullet > 30) {
            vYOfBullet = 30;
        }
    }

    public int getVYOfBullet() {
        return vYOfBullet;
    }

    public void damage() {
        if (isDead) {
            return; // 死亡済みなら何もしない
        }
        currentHP--;
        if (currentHP <= 0) {
            currentHP = 0;
            score = 0;
            isDead = true;
            setEmotion("dead"); // 表情を「死」に変える
            setBasicColor(Color.darkGray); // 色も変える（任意）
        } else {
            setBasicColor(Color.red);
            damagedFlag = true;
        }
    }

    public void revive() {
        currentHP = 50;
        isDead = false;
    }

    public boolean isDamaged() {
        return damagedFlag;
    }

    public boolean isDead() {
        return isDead;
    }

    public void damageReset() {
        basicColor = initColor;
    }

    public boolean collision(int targetX, int targetY, int targetRadius) {
        // 弾が当たっていてかつ自身が生きている
        if (Math.sqrt((targetX - centerX) * (targetX - centerX)
                + (targetY - centerY) * (targetY - centerY)) <= radius + targetRadius && !isDead) {
            return true;
        }
        return false;
    }

    public double getDistance(double x, double y) {
        return Math.sqrt((x - centerX) * (x - centerX) + (y - centerY) * (y - centerY));
    }

    void draw(Graphics g) {

        //円の処理
        g.setColor(basicColor);
        g.fillOval(x, y, 2 * radius, 2 * radius); // rは半径なので2倍にする
        g.drawString(name, x - 5, y - 20); // 座標表示
        g.drawString("Score: " + String.valueOf(score), x - 5, y - 10); // スコア表示

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
        // HPバー（赤背景＋緑残量）
        g.setColor(Color.red);
        g.fillRect(x, y + 2 * radius + 5, 2 * radius, 5); // 赤背景バー
        g.setColor(Color.green);
        int hpWidth = (int) ((double) currentHP / maxHP * 2 * radius);
        g.fillRect(x, y + 2 * radius + 5, hpWidth, 5); // 緑の残HP部分

        // 死亡時は強制的に "dead" を渡す（誤って他の感情が残っていても上書き）
        if (isDead) {
            facelook.makeFace(g, "dead");
        } else {
            facelook.makeFace(g, emotion);
        }
    }

    public void resetHP() {
        this.currentHP = this.maxHP;
        this.isDead = false;
        this.setEmotion("normal");
        this.setBasicColor(initColor); // 元の色に戻す
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void setName(String name) {
        this.name = name;
    }

}// ball rim end
