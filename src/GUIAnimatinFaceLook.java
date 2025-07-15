
import java.awt.Color;
import java.awt.Graphics;

class GUIAnimatinFaceLook {// 顔のオブジェクト

    int h = 100;
    int w = 100;

    int xStart = 0;
    int yStart = 0;

    public void setXY(int x, int y) {
        this.xStart = x;
        this.yStart = y;
    }

    public void setSize(int h, int w) {
        this.h = h;
        this.w = h;
    }

    public GUIAnimatinFaceLook() {

    }

    void makeFace(Graphics g) {
        // makeRim(g);
        makeFace(g, "normal");  // ← 1行に簡略化可能
    }

    public void makeFace(Graphics g, String emotion) {
        // **ここにemotion毎の顔を用意する。*///
        makeEyebrows(g, emotion);
        makeEyes(g, w / 5, emotion);
        makeNose(g, h / 5);
        makeMouth(g, w / 2);

    }

    /*
     * public void makeRim(Graphics g) { g.drawLine(xStart, yStart, xStart + h,
     * yStart); g.drawLine(xStart, yStart, xStart, yStart + w);
     * g.drawLine(xStart, yStart + w, xStart + h, yStart + w); g.drawLine(xStart
     * + h, yStart, xStart + h, yStart + w); }
     */
    void makeEyebrows(Graphics g, String emotion) {
        g.setColor(Color.BLACK);
        int eyeY = yStart + (w * 1 / 3);
        int eyeX1 = xStart + (h * 2 / 7);
        int eyeX2 = xStart + (h * 4 / 7);
        int eyeSize = w / 5;

        if (emotion.equals("smile")) {
            // 眉をゆるやかに下げる（優しい表情）
            g.drawLine(eyeX1, eyeY - 10, eyeX1 + eyeSize, eyeY - 5);
            g.drawLine(eyeX2, eyeY - 10, eyeX2 + eyeSize, eyeY - 15);
        } else if (emotion.equals("angry")) {
            // 眉を鋭く上げる（怒った表情）
            g.drawLine(eyeX1, eyeY - 5, eyeX1 + eyeSize, eyeY - 10);
            g.drawLine(eyeX2, eyeY - 15, eyeX2 + eyeSize, eyeY - 10);
        } else if (emotion.equals("dead")) {
            // 死んだ表情（眉は変化なし）
            g.drawLine(eyeX1, eyeY - 10, eyeX1 + eyeSize, eyeY - 10);
            g.drawLine(eyeX2, eyeY - 10, eyeX2 + eyeSize, eyeY - 10);
        } else {
            // normal（水平）
            g.drawLine(eyeX1, eyeY - 10, eyeX1 + eyeSize, eyeY - 10);
            g.drawLine(eyeX2, eyeY - 10, eyeX2 + eyeSize, eyeY - 10);
        }
    }

    void makeEyes(Graphics g, int eyeSize, String emotion) {
        // setColor(Color.red);
        // g.fillRect(xStart + (h * 1 / 3) - 20, yStart + (w * 1 / 3) - 10,
        // 10, 10);

        g.setColor(Color.blue);

        // g.fillRoundRect()
        // g.fillOval()
        g.fillArc(xStart + (h * 2 / 7), yStart + (w * 1 / 3), eyeSize, eyeSize,
                0, 300);
        g.setColor(Color.black);

        // g.drawLine(xStart + (h * 1 / 3) - 20, yStart + (w * 1 / 3) - 10,
        // xStart + (h * 1 / 3) + 20, yStart + (w * 1 / 3) - 10);
        // g.drawLine(xStart + (h * 2 / 3) - 20, yStart + (w * 1 / 3) - 10,
        // xStart + (h * 2 / 3) + 20, yStart + (w * 1 / 3) - 10);
        g.drawOval(xStart + (h * 2 / 7), yStart + (w * 1 / 3), eyeSize, eyeSize);
        g.drawOval(xStart + (h * 4 / 7), yStart + (w * 1 / 3), eyeSize, eyeSize);
        if (emotion.equals("dead")) {
            g.setColor(Color.black);
            // 左目に「×」
            int lx = xStart + (h * 2 / 7);
            int ly = yStart + (w * 1 / 3);
            g.drawLine(lx, ly, lx + eyeSize, ly + eyeSize);
            g.drawLine(lx + eyeSize, ly, lx, ly + eyeSize);

            // 右目に「×」
            int rx = xStart + (h * 4 / 7);
            int ry = ly;
            g.drawLine(rx, ry, rx + eyeSize, ry + eyeSize);
            g.drawLine(rx + eyeSize, ry, rx, ry + eyeSize);
            return; // 「×目」を描いたら戻る
        }
    }// makeEyes()

    public void makeNose(Graphics g, int noseSize) {
        g.drawLine(xStart + (h * 1 / 2), yStart + (w * 2 / 4), xStart
                + (h * 1 / 2), yStart + (w * 2 / 4) + noseSize);
    }// makeNose() end

    public void makeMouth(Graphics g, int mouseSize) {
        int xMiddle = xStart + h / 2;
        int yMiddle = yStart + 3 * w / 4;
        g.drawLine(xMiddle - mouseSize / 2, yMiddle, xMiddle + mouseSize / 2,
                yMiddle);
    }
}// FaceObj End
