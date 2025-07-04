
import java.awt.*;
import java.awt.event.*;

public class FacesAWTAraiRyo {

    // メインメソッド：プログラムの開始点
    public static void main(String[] args) {
        new FacesAWTAraiRyo(); // インスタンスを生成
    }

    // コンストラクタ：ウィンドウを作成して表示
    FacesAWTAraiRyo() {
        FaceFrame f = new FaceFrame(); // カスタムフレーム生成
        f.setSize(800, 800); // ウィンドウサイズ設定
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0); // ウィンドウを閉じたときに終了
            }
        });
        f.setVisible(true); // ウィンドウを表示
    }

    // 内部クラス：顔を描画するためのフレーム
    class FaceFrame extends Frame {

        private final int FACE_W = 180;
        private final int FACE_H = 180;

        public void paint(Graphics g) {
            int panelWidth = getWidth();
            int panelHeight = getHeight();

            int spacingX = (panelWidth - (FACE_W * 3)) / 4;
            int spacingY = (panelHeight - (FACE_H * 3)) / 4;

            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    int x = spacingX + col * (FACE_W + spacingX);
                    int y = spacingY + row * (FACE_H + spacingY);

                    // 髪の色をランダムに
                    int r = (int) (Math.random() * 256);
                    int gr = (int) (Math.random() * 256);
                    int b = (int) (Math.random() * 256);
                    Color hairColor = new Color(r, gr, b);
                    drawPierrotFace(g, x, y, row, col, hairColor);
                }
            }
        }

        // ピエロの顔を描画
        public void drawPierrotFace(Graphics g, int x, int y, int row, int col, Color hairColor) {
            drawFaceShape(g, x, y);
            drawHair(g, x, y, hairColor);
            drawNose(g, x, y);
            drawEyes(g, x, y, col);
            drawMouth(g, x, y, row);
        }

        // 顔の輪郭
        private void drawFaceShape(Graphics g, int x, int y) {
            g.setColor(Color.WHITE);
            g.fillOval(x, y, FACE_W, FACE_H);

            g.setColor(Color.BLACK);
            g.drawOval(x, y, FACE_W, FACE_H);
        }

        // 髪
        private void drawHair(Graphics g, int x, int y, Color hairColor) {
            int curls = 10; // パーマの丸の数
            int radius = 40; // 丸のサイズ
            double centerX = x + FACE_W / 2.0;
            double centerY = y + FACE_H / 2.0;
            g.setColor(hairColor);

            // パーマの髪の描画
            for (int i = 0; i < curls; i++) {
                double angle = Math.PI * (i / (double) (curls - 1)); // 0 〜 π の範囲
                double offsetX = Math.cos(angle) * (FACE_W / 2.0 + 10);
                double offsetY = Math.sin(angle) * (FACE_H / 2.0 + 10);

                int cx = (int) (centerX + offsetX - radius / 2);
                int cy = (int) (centerY - offsetY - radius / 2);

                g.fillOval(cx, cy, radius, radius);
            }
        }

        // 鼻
        private void drawNose(Graphics g, int x, int y) {
            g.setColor(Color.RED);
            g.fillOval(x + FACE_W / 2 - 10, y + 90, 20, 20);
        }

        // 目
        private void drawEyes(Graphics g, int x, int y, int col) {
            // 目の周りのメイク
            g.setColor(new Color(255, 126, 126));
            g.fillOval(x + 45, y + 50, 30, 60); // 左目元
            g.fillOval(x + 105, y + 50, 30, 60); // 右目元

            if (col != 0) { // 左目を開く
                // 白目
                g.setColor(Color.WHITE);
                g.fillOval(x + 50, y + 70, 20, 20);
                // 赤目
                g.setColor(Color.RED);
                g.fillOval(x + 57, y + 70, 5, 20);
                // 黒目
                g.setColor(Color.BLACK);
                g.fillOval(x + 50, y + 77, 20, 8);
            } else {
                // つむった左目（横線）
                g.setColor(Color.BLACK);
                g.drawLine(x + 50, y + 80, x + 70, y + 80);
            }

            if (col != 2) { // 右目を開く
                // 白目
                g.setColor(Color.WHITE);
                g.fillOval(x + 110, y + 70, 20, 20);
                // 赤目
                g.setColor(Color.RED);
                g.fillOval(x + 117, y + 70, 5, 20);
                // 黒目
                g.setColor(Color.BLACK);
                g.fillOval(x + 110, y + 77, 20, 8);
            } else {
                // つむった右目（横線）
                g.setColor(Color.BLACK);
                g.drawLine(x + 110, y + 80, x + 130, y + 80);
            }
        }

        // 口
        private void drawMouth(Graphics g, int x, int y, int row) {
            g.setColor(Color.RED);

            int mouthY = y + 135;
            int mouthX1 = x + 60;
            int mouthX2 = x + 120;
            int mouthMidX = (mouthX1 + mouthX2) / 2;
            int mouthMidY;

            switch (row) {
                case 0 -> { // 笑顔
                    mouthMidY = mouthY + 12;
                    g.drawLine(mouthX1, mouthY, mouthMidX, mouthMidY);
                    g.drawLine(mouthMidX, mouthMidY, mouthX2, mouthY);
                }
                case 1 -> { // 無表情
                    g.drawLine(mouthX1, mouthY, mouthX2, mouthY);
                }
                case 2 -> { // 怒り顔
                    mouthMidY = mouthY - 12;
                    g.drawLine(mouthX1, mouthY, mouthMidX, mouthMidY);
                    g.drawLine(mouthMidX, mouthMidY, mouthX2, mouthY);
                }
            }
        }
    }
}
