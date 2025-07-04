
import java.awt.*;
import java.awt.event.*;

public class FacesAWTMain {

    // メインメソッド：プログラムの開始点
    public static void main(String[] args) {
        new FacesAWTMain(); // インスタンスを生成
    }

    // コンストラクタ：ウィンドウを作成して表示
    FacesAWTMain() {
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

        private int w;       // 顔の幅
        private int h;       // 顔の高さ
        private int xStart;  // 顔の左上x座標
        private int yStart;  // 顔の左上y座標
        private FaceObj fobj1; // 顔オブジェクト

        // コンストラクタ：FaceObjインスタンスを生成
        FaceFrame() {
            fobj1 = new FaceObj();
        }

        // 描画処理
        public void paint(Graphics g) {
            w = 200;
            h = 200;
            xStart = 50;
            yStart = 50;

            // 顔の各パーツを描画
            drawRim(g);          // 顔の輪郭
            drawBrow(g, 30);     // まゆげ
            drawEye(g, 35);      // 目
            drawNose(g, 40);     // 鼻
            drawMouth(g, 100);   // 口
        }

        // 顔の枠線を描く
        public void drawRim(Graphics g) {
            g.drawLine(xStart, yStart, xStart + w, yStart);
            g.drawLine(xStart, yStart, xStart, yStart + h);
            g.drawLine(xStart, yStart + h, xStart + w, yStart + h);
            g.drawLine(xStart + w, yStart, xStart + w, yStart + h);
        }

        // まゆげを描く（未実装）
        public void drawBrow(Graphics g, int bx) {
            g.drawLine(xStart + 40, yStart + 50, xStart + 40 + bx, yStart + 50);
            g.drawLine(xStart + 120, yStart + 50, xStart + 120 + bx, yStart + 50);
        }

        // 鼻を描く（未実装）
        public void drawNose(Graphics g, int nx) {
            int xMiddle = xStart + w / 2;
            int yMiddle = yStart + h / 2;
            g.drawLine(xMiddle, yMiddle - nx / 2, xMiddle, yMiddle + nx / 2);
        }

        // 両目を描く
        public void drawEye(Graphics g, int r) {
            g.drawOval(xStart + 40, yStart + 70, r, r);
            g.drawOval(xStart + 120, yStart + 70, r, r);
        }

        // 口を描く
        public void drawMouth(Graphics g, int mx) {
            int xMiddle = xStart + w / 2;
            int yMouth = yStart + h - 30;
            g.drawLine(xMiddle - mx / 2, yMouth, xMiddle + mx / 2, yMouth);
        }
    }

    // 顔に関するロジックを後で追加する予定のクラス
    private class FaceObj {
        // 今後、顔のスタイル・パーツ管理などをここに記述
    }
} // FacesAWTMain end
