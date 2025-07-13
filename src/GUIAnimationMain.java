
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GUIAnimationMain extends JPanel implements ActionListener {

    private Ellipse2D.Float ellipse = new Ellipse2D.Float();

    final int FRAME_SPEED = 50;
    final private double MAX_Counter = 5000;

    private boolean isResetProcess = true;
    private double counter;
    private Timer timer;
    private int rotateAngle = 0;

    private int INIT_BALLNUM = 3;
    private GUIAnimationBall[] myBallRims = new GUIAnimationBall[INIT_BALLNUM];
    // private Bullet[] bullets = new Bullet[INIT_BULLETNUM];
    private static ArrayList<Bullet> bullets = new ArrayList<>();

    public static void main(String[] args) {// main 関数
        /* Frame関係調整：開始 */
        System.out.println("GUIAnimationFaceObjMain started");
        GUIAnimationMain animation = new GUIAnimationMain();

        JFrame frame = new JFrame("GUIAnimationFaceObjMain");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(animation);
        frame.setSize(550, 550);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        /* Frame関係調整終了：終了 */

        /**
         * ********************
         */
        // TCPChatサーバをスタート!!!!!
        /**
         * ********************
         */
        new GUIAniMultiTCPServer2(animation);

    }// main end

    ActionListener updateProBar;

    // コンストラクタ
    public GUIAnimationMain() {
        timer = new Timer(FRAME_SPEED, this);
        timer.setInitialDelay(500);
        timer.start();
    }

    public int getCurrentFaceCount() {
        return myBallRims.length;
    }

    public void addNewFace() {
        GUIAnimationBall[] newBalls = new GUIAnimationBall[myBallRims.length + 1];
        System.arraycopy(myBallRims, 0, newBalls, 0, myBallRims.length);
        newBalls[myBallRims.length] = new GUIAnimationBall(500, 500); // 仮にサイズ固定
        myBallRims = newBalls;
    }

    public synchronized void removeFace(int index) {
        if (index < 0 || index >= myBallRims.length) {
            return;
        }

        GUIAnimationBall[] newBalls = new GUIAnimationBall[myBallRims.length - 1];
        for (int i = 0, j = 0; i < myBallRims.length; i++) {
            if (i != index) {
                newBalls[j++] = myBallRims[i];
            }
        }
        myBallRims = newBalls;
        System.out.println("顔オブジェクト削除: index=" + index);
    }

    // i番目の顔の位置を変更する関数。
    public void setFacePlace(int i, int x, int y, String message) {
        System.out.println("setFacePlace() :" + message);
        myBallRims[i].setPosition(x, y);
        myBallRims[i].setMessage(message);
    }

    // i番目の感情を変更する関数。
    public void setFaceEmotion(int which, String emotion) {
        System.out.println("setFaceEmotion() :" + emotion);
        myBallRims[which].setEmotion(emotion);
    }

    // i番目の顔の色を変更する関数。
    public void setFaceColor(int which, Color c) {
        System.out.println("setFaceColor()");

        myBallRims[which].setBasicColor(c);
        myBallRims[which].setMessage(c.toString());

    }

    /*
        弾の生成
        複数の弾の座標を引数や文字列でやりとりすると煩雑になるため、
        ここでは弾のタイプと基準位置を引数とし、
        基準位置をもとに複数の弾を生成する
     */
    public void generateBullet(int type, int x, int y) {
        Random rand = new Random();
        switch (type) {
            case 0:
                bullets.add(new Bullet(x + 25, y, 10, 0, 3));
                bullets.add(new Bullet(x - 25, y, -10, 0, 3));
                bullets.add(new Bullet(x, y + 25, 0, 10, 3));
                bullets.add(new Bullet(x, y - 25, 0, -10, 3));
                break;
            case 1:
                bullets.add(new Bullet(x + 5, y + 25, 0, 20, 3));
                bullets.add(new Bullet(x - 5, y + 25, 0, 20, 3));
                bullets.add(new Bullet(x + 5, y + 25, 0, -20, 3));
                bullets.add(new Bullet(x - 5, y + 25, 0, -20, 3));
                break;
            case 2:
                double angle = Math.toRadians(rotateAngle);
                double angle2 = angle + Math.PI / 3 * 2;
                double angle3 = angle + Math.PI / 3 * 4;
                rotateAngle += 10;
                System.out.println(rotateAngle);
                if (rotateAngle >= 360) {
                    rotateAngle = 0;
                }
                bullets.add(new Bullet(x + (int) (Math.sin(angle) * 20), y + (int) (Math.cos(angle) * 20),
                        (int) (Math.sin(angle) * 20), (int) (Math.cos(angle) * 20), 3));
                bullets.add(new Bullet(x + (int) (Math.sin(angle2) * 20), y + (int) (Math.cos(angle2) * 20),
                        (int) (Math.sin(angle2) * 20), (int) (Math.cos(angle2) * 20), 3));
                bullets.add(new Bullet(x + (int) (Math.sin(angle3) * 20), y + (int) (Math.cos(angle3) * 20),
                        (int) (Math.sin(angle3) * 20), (int) (Math.cos(angle3) * 20), 3));
                break;
            default:
                break;
        }
    }

    // 初期化処理
    void initProcess(int w, int h) {
        for (int i = 0; i < myBallRims.length; i++) {
            myBallRims[i] = new GUIAnimationBall(w, h);
        }
        setEllipseSize(1, w, h);
    }

    public void paint(Graphics g) {
        /* おまじない：開始 */

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        rh.put(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        g2.setRenderingHints(rh);
        Dimension windowSize = getSize();

        if (isResetProcess) {
            initProcess(windowSize.width, windowSize.height);
            isResetProcess = false;
        }

        this.doNextStep(windowSize.width, windowSize.height);
        paintProcess(windowSize.width, windowSize.height, g2);
        /* おまじない：終了 */

    }// paint end

    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    public void setEllipseSize(double size, int w, int h) {
        counter = size;
        ellipse.setFrame(10, 10, size, size);
    }

    void doNextStep(int w, int h) {
        counter++;

        if (counter > MAX_Counter) {
            initProcess(w, h);

        } else {
            // ボールの移動
            for (int i = 0; i < myBallRims.length; i++) {
                myBallRims[i].move();
                if (myBallRims[i].isDamaged()) {
                    myBallRims[i].damageReset();
                }
            }
            // 弾の移動
            for (int i = 0; i < bullets.size(); i++) {
                bullets.get(i).move();
                // 弾の衝突判定
                for (int j = 0; j < myBallRims.length; j++) {
                    if (myBallRims[j].collision(bullets.get(i).getX(), bullets.get(i).getY())) {
                        // ダメージ判定
                        myBallRims[j].damage();
                    }
                }
                // 画面外に出たら削除
                if (bullets.get(i).isOutOfWindow()) {
                    bullets.remove(i);
                }
            }
            ellipse.setFrame(ellipse.getX(), ellipse.getY(), counter, counter);
        }
    }// doNextStep() end

    void paintProcess(int w, int h, Graphics2D g2) {

        // g2.setColor(Color.BLUE);
        // g2.draw(ellipse);
        // g2.drawString(counter + "Step経過", 10, 10);
        for (int i = 0; i < myBallRims.length; i++) {
            myBallRims[i].draw(g2);
        }
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).draw(g2);
        }
    }// paintProcess end

    // 顔の輪郭
}// GUI Animation End
