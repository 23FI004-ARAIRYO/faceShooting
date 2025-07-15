
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

import javax.swing.JFrame;

public class GUIAniMultiTCPClient2 extends JFrame implements KeyListener, WindowListener {

    private int faceX = 100;
    private int faceY = 100;
    private final int MOVE = 10; // 移動量
    final static int WINDOW_SIZE = 777;
    private int radius = 20;
    private int currentBulletType = 0;  // 現在の弾タイプ
    private final int MAX_BULLET_TYPE = 3; // タイプは0,1,2の3種類
    private int clientId = -1;  // クライアントIDを保持

    private String hostname = "localhost";

    public static void main(String[] args) {
        new GUIAniMultiTCPClient2();
    }

    public GUIAniMultiTCPClient2() {
        super("Arrow Key Client");

        this.setSize(300, 200);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.addKeyListener(this);
        this.addWindowListener(this); // ウィンドウの確認
        this.setVisible(true);

        // クライアントIDをサーバーに登録
        clientId = registerClient();
        System.out.println("取得したクライアントID: " + clientId);

        // 初期位置を送信
        doClientAccess("face,place," + clientId + "," + faceX + "," + faceY);
    }

    private int registerClient() {
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(hostname, 6000), 10000);

            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader rd = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            writer.println("register");

            String response = rd.readLine();
            rd.close();
            writer.close();
            socket.close();

            return Integer.parseInt(response); // サーバーからのID受信
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    private void notifyRemove() {
        if (clientId >= 0) {
            try {
                Socket socket = new Socket(hostname, 6000);
                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                writer.println("remove," + clientId);
                writer.close();
                socket.close();
                System.out.println("Remove通知を送信: clientId=" + clientId);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendPosition() {
        String message = "face,place," + clientId + "," + faceX + "," + faceY;
        doClientAccess(message);
    }

    // ←→↑↓キーが押された時
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        switch (keyCode) {
            case KeyEvent.VK_LEFT:
                faceX -= MOVE;
                if (faceX < 0) {
                    faceX = 0;
                }
                sendPosition();
                break;
            case KeyEvent.VK_RIGHT:
                faceX += MOVE;
                if (WINDOW_SIZE < faceX + (radius * 3)) {
                    faceX = WINDOW_SIZE - (radius * 3);
                }
                sendPosition();
                break;
            case KeyEvent.VK_UP:
                faceY -= MOVE;
                if (faceY < 0) {
                    faceY = 0;
                }
                sendPosition();
                break;
            case KeyEvent.VK_DOWN:
                faceY += MOVE;
                if (WINDOW_SIZE < faceY + (radius * 4)) {
                    faceY = WINDOW_SIZE - (radius * 4);
                }
                sendPosition();
                break;
            case KeyEvent.VK_Z:
                doClientAccess("bullet," + currentBulletType + "," + (faceX + radius) + "," + (faceY + radius) + "," + clientId);
                break;
            case KeyEvent.VK_X:
                currentBulletType = (currentBulletType + 1) % MAX_BULLET_TYPE;
                System.out.println("弾タイプ切替: " + currentBulletType);
                // 弾タイプに応じて表情切り替え
                String emotion = "normal"; // デフォルト
                switch (currentBulletType) {
                    case 0:
                        emotion = "normal";
                        break;
                    case 1:
                        emotion = "smile";
                        break;
                    case 2:
                        emotion = "angry";
                        break;
                }
                // サーバーに emotion を送信
                doClientAccess("face,emotion," + clientId + "," + emotion);
                break;
            case KeyEvent.VK_Q:
                doClientAccess("remove," + clientId);
                System.exit(0);
                break;
            default:
                return;
        }

        // 顔の位置を送信
        String message = "face,place," + clientId + "," + faceX + "," + faceY;
        doClientAccess(message);
    }

    // ---- WindowListener 実装 ----
    @Override
    public void windowClosing(WindowEvent e) {
        notifyRemove();
    }

    public void windowOpened(WindowEvent e) {
    }

    public void windowClosed(WindowEvent e) {
    }

    public void windowIconified(WindowEvent e) {
    }

    public void windowDeiconified(WindowEvent e) {
    }

    public void windowActivated(WindowEvent e) {
    }

    public void windowDeactivated(WindowEvent e) {
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
}// class MultiClient end
