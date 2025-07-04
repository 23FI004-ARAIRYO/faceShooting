
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

public class MovingBallFacesAWTex {

    public static void main(String[] args) {
        new MovingBallFacesAWTex();
    }

    MovingBallFacesAWTex() {

        MovingInnerFFrame f = new MovingInnerFFrame();
        Thread th = new Thread(f);
        th.start();

        f.setSize(500, 500);
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        f.show();

        int MAXTime = 20;
        for (int i = 0; i < 20; i++) {
            try {

                Thread.sleep(1000);
            } catch (InterruptedException e1) {
                // TODO �����������ꂽ catch �u���b�N
                e1.printStackTrace();
            }
            System.out.println("�c��" + (MAXTime - i) + "�b�ł�");
        }
        System.out.println("�I�����܂�");
        f.setTh_enable(false);
        System.out.println("�I�����܂���");

        //System.exit(0);
    }

    class MovingInnerFFrame extends Frame implements Runnable {

        int ballnum = 5;
        BallRim[] myBalls = new BallRim[ballnum];

        private boolean th_enable = true;

        public boolean isTh_enable() {
            return th_enable;
        }

        public void setTh_enable(boolean th_enable) {
            this.th_enable = th_enable;
        }

        private int th_counter = 0;
        //Color[] colors={Color.RED,Color.BLUE,Color.BLACK,Color.yellow};

        int w = 500;
        int h = 500;

        public void run() {

            for (int i = 0; i < myBalls.length; i++) {
                myBalls[i] = new BallRim();
            }

            int timer = 0;
            while (th_enable) {

                try {

                    Thread.sleep(100);
                    th_counter++;
                    /*
					if(th_counter%10==0){
						timer++;
						System.out.println("�c��"+(20-timer));
					}*/
                    if (th_counter >= 500) {
                        th_enable = false;
                    }
                } catch (InterruptedException e) {
                }

                for (int i = 0; i < myBalls.length; i++) {
                    myBalls[i].move();
                }

                repaint(); // paint()���\�b�h���Ăяo�����
            }
        }

        public void paint(Graphics g) {

            for (int i = 0; i < myBalls.length; i++) {
                myBalls[i].draw(g);;
            }

        }

    }// FFrameend

    // BallRim
    class BallRim {

        FaceObj fobj;
        String str = "";

        Random rdn;
        int w = 500;
        int h = 500;

        int x;
        int y;
        int radius; //ボールの半径
        Color color = Color.RED;

        double xDir = -1;
        double yDir = 1;

        BallRim() {

            rdn = new Random();
            xDir = rdn.nextDouble() * 2 - 1;
            yDir = rdn.nextDouble() * 2 - 1;

            setPosition(rdn.nextInt(w), rdn.nextInt(h));
            setRadius(rdn.nextInt(30) + 30);//30-60�̃T�C�Y�̊�̗֊s

            Color c = new Color(rdn.nextInt(255), rdn.nextInt(255), rdn.nextInt(255));
            setColor(c);

            fobj = new FaceObj();

        }

        void setColor(Color c) {
            this.color = c;
        }

        public void setText(String str) {
            this.str = str;

        }

        void move() {

            if ((xDir > 0) && (x + this.radius * 2 >= w)) {
                xDir = -1 * xDir;
            }
            if ((xDir < 0) && (x <= 0)) {
                xDir = -1 * xDir;
            }

            if (xDir > 0) {
                x = x + 10;
            } else {
                x = x - 10;
            }

            if ((yDir > 0) && (y + this.radius * 2 >= h)) {
                yDir = -1 * yDir;
            }
            if ((yDir < 0) && (y <= 25)) {
                yDir = -1 * yDir;
            }

            if (yDir > 0) {
                y = y + 10;
            } else {
                y = y - 10;
            }

        }

        void setPosition(int x, int y) {
            this.x = x;
            this.y = y;
        }

        void setRadius(int r) {
            this.radius = r;
        }

        void draw(Graphics g) {
            g.setColor(color);
            g.fillOval(x, y, 2 * radius, 2 * radius);
            fobj.setXY(x, y);
            fobj.setSize(2 * radius, 2 * radius);
            fobj.makeFace(g);
        }

    }// ball end

    class FaceObj {

        int w = 100;
        int h = 100;
        int xStart = 0;
        int yStart = 0;
        Color hairColor;

        public FaceObj() {
            Random r = new Random();
            hairColor = new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256));
        }

        public void setXY(int x, int y) {
            this.xStart = x;
            this.yStart = y;
        }

        public void setSize(int h, int w) {
            this.h = h;
            this.w = w;
        }

        public void makeFace(Graphics g) {
            drawFaceShape(g);
            drawHair(g);
            drawEyes(g);
            drawNose(g);
            drawMouth(g);
        }

        private void drawFaceShape(Graphics g) {
            g.setColor(Color.WHITE);
            g.fillOval(xStart, yStart, w, h);

            g.setColor(Color.BLACK);
            g.drawOval(xStart, yStart, w, h);
        }

        private void drawHair(Graphics g) {
            int curls = 10;
            int radius = Math.min(w, h) / 4;
            double centerX = xStart + w / 2.0;
            double centerY = yStart + h / 2.0;

            g.setColor(hairColor);
            for (int i = 0; i < curls; i++) {
                double angle = Math.PI * (i / (double) (curls - 1));
                double offsetX = Math.cos(angle) * (w / 2.0 + 5);
                double offsetY = Math.sin(angle) * (h / 2.0 + 5);

                int cx = (int) (centerX + offsetX - radius / 2);
                int cy = (int) (centerY - offsetY - radius / 2);

                g.fillOval(cx, cy, radius, radius);
            }
        }

        private void drawEyes(Graphics g) {
            int eyeW = w / 6;
            int eyeH = h / 6;
            int offsetY = h / 4;

            // 赤メイク部分
            g.setColor(new Color(255, 126, 126));
            g.fillOval(xStart + w / 5, yStart + offsetY, eyeW, eyeH * 2);
            g.fillOval(xStart + 3 * w / 5, yStart + offsetY, eyeW, eyeH * 2);

            // 白目
            g.setColor(Color.WHITE);
            g.fillOval(xStart + w / 5 + 4, yStart + offsetY + eyeH / 2, eyeW / 2, eyeH / 2);
            g.fillOval(xStart + 3 * w / 5 + 4, yStart + offsetY + eyeH / 2, eyeW / 2, eyeH / 2);

            // 黒目
            g.setColor(Color.BLACK);
            g.fillOval(xStart + w / 5 + 6, yStart + offsetY + eyeH / 2 + 2, eyeW / 3, eyeH / 3);
            g.fillOval(xStart + 3 * w / 5 + 6, yStart + offsetY + eyeH / 2 + 2, eyeW / 3, eyeH / 3);
        }

        private void drawNose(Graphics g) {
            g.setColor(Color.RED);
            g.fillOval(xStart + w / 2 - w / 10, yStart + h / 2 - 5, w / 5, h / 10);
        }

        private void drawMouth(Graphics g) {
            int mouthY = yStart + (int) (h * 0.75);
            int mouthX1 = xStart + w / 4;
            int mouthX2 = xStart + 3 * w / 4;
            int mouthMidX = (mouthX1 + mouthX2) / 2;
            int mouthMidY = mouthY + h / 15;

            g.setColor(Color.RED);
            g.drawLine(mouthX1, mouthY, mouthMidX, mouthMidY);
            g.drawLine(mouthMidX, mouthMidY, mouthX2, mouthY);
        }
    }
    // Ball Rim end

}
