
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

import javax.swing.JFrame;

public class GUIAniMultiTCPClient2 extends JFrame implements KeyListener {

    private int faceX = 100;
    private int faceY = 100;
    private final int MOVE = 10; // 移動量
    private int radius = 20;

    private String hostname = "localhost";

    public static void main(String[] args) {
        new GUIAniMultiTCPClient2();
    }

    public GUIAniMultiTCPClient2() {
        super("Arrow Key Client");

        this.setSize(300, 200);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.addKeyListener(this);
        this.setVisible(true);

        // 初期位置を送信
        doClientAccess("face,place,0," + faceX + "," + faceY);
    }

    // ←→↑↓キーが押された時
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        switch (keyCode) {
            case KeyEvent.VK_LEFT:
                faceX -= MOVE;
                break;
            case KeyEvent.VK_RIGHT:
                faceX += MOVE;
                break;
            case KeyEvent.VK_UP:
                faceY -= MOVE;
                break;
            case KeyEvent.VK_DOWN:
                faceY += MOVE;
                break;
            case KeyEvent.VK_Z:
                doClientAccess("bullet,2," + (faceX + radius) + "," + (faceY + radius));
                break;
            default:
                return;
        }

        // 顔の位置を送信
        String message = "face,place,0," + faceX + "," + faceY;
        doClientAccess(message);
    }

    // 送信処理
    public void doClientAccess(String msg) {
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(hostname, 6000), 10000);

            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader rd = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            System.out.println("Send  : " + msg);
            writer.println(msg);

            String response = rd.readLine();
            System.out.println("Server: " + response);

            rd.close();
            writer.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 不要なメソッドも必要（空でOK）
    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
