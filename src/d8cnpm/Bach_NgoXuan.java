package d8cnpm;

/**
 * Created by NgocKute on 27/05/2016.
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Ham chinh cua chuong trinh
 * Nhap vao kich thuoc mong muon va xay dung cua so game
 *
 * @author thanbaiks
 */
public class Bach_NgoXuan {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        // Bien luu kich thuoc ban co
        int size = -1;
        while (true) {
            String result = JOptionPane.showInputDialog("Nhap kich thuoc ban co", "18");
            if (result != null)
                try {
                    size = Integer.parseInt(result);
                    break;
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Ban nhap sai dinh dang. Vui long nhap lai");
                }
        }
        GameWindow game = GameWindow.create(size);
        if (game == null) {
            // That bai
            System.out.println("Khong the bat dau game. Moi truong CLI");
            System.exit(-1);
        } else {
            // Thanh cong
            game.setVisible(true);
        }
    }

}

/**
 * Lop Nguoi choi
 *
 * @author thanbaiks
 */
class Player {

    /**
     * Ten nguoi choi
     */
    private String name;

    /**
     * Ki tu nguoi choi
     */
    private char c;

    private int score;
    private JLabel label;

    public Player(String name, char c) {
        this.name = name;
        this.c = c;
        score = 0;
        label = new JLabel(name, SwingConstants.CENTER);
        label.setFont(new Font("SansSerif", Font.BOLD, 32));
        label.setPreferredSize(new Dimension(0, 100));
    }

    /**
     * Lay ten nguoi choi
     * @return Ten nguoi choi
     */
    public String getName() {
        return name;
    }

    /**
     * Lay ki tu choi cua nguoi choi
     *
     * @return Ki tu choi
     */
    public char getChar() {
        return c;
    }

    /**
     * Tra ve diem so cua nguoi choi
     * So lan thang
     *
     * @return diem so
     */
    private int getScore() {
        return score;
    }

    public JComponent getUI () {
        return label;
    }

    public void won() {
        score++;
        label.setText(name + " - " + score);
    }

    public void gotTurn() {
        label.setBorder(BorderFactory.createLineBorder(Color.red, 5));
    }

    public void lostTurn() {
        label.setBorder(null);
    }
}

/**
 * Lop Ke thua JButton
 * @author thanbaiks
 */
class GameButton extends JButton {
    /**
     * Nguoi choi cua Button nay
     */
    private Player p;

    /**
     * Toa do cua button tren ban co
     */
    int x,y;

    /**
     * Ham khoi tao
     * @param listener lang nghe su kien click
     * @param x toa do x
     * @param y toa do y
     */
    public GameButton(ActionListener listener, int x, int y) {
        super();
        this.x = x;
        this.y = y;
        setSize(100,100);
        setFont(new Font("Monospaced", Font.BOLD, 18));
        addActionListener(listener);
    }

    /**
     * Thuc hien thao tac tren o co nay
     *
     * @param p Nguoi choi thuc hien
     */
    public void play(Player p) {
        this.p = p;
        setText(String.valueOf(p.getChar()));
        if (p.getChar() == 'O')
            setForeground(Color.red);
        else
            setForeground(Color.BLACK);
    }

    /**
     * Kiem tra xem Button nay da co nguoi danh vao chua
     * @return boolean
     */
    public boolean isPlayable() {
        return p == null;
    }

    /**
     * Dua o co ve trang thai ban dau
     * Trang thai trong
     */
    public void reset() {
        setText("");
        setForeground(Color.black);
        p = null;
    }

    /**
     * Lay toa do X cua Button (ô cờ)
     *
     * @return toa do X
     */
    public int getPosX() {
        return x;
    }

    /**
     * Lay toa do Y cua Button (ô cờ)
     *
     * @return toa do Y
     */
    public int getPosY() {
        return y;
    }

    /**
     * Tra ve nguoi choi thuc hien thao tac o o co nay
     * return player hoac null neu o co trong
     */
    public Player getPlayer() {
        return p;
    }
}

/**
 * Lop ban co
 * @author thanbaiks
 */
class GameWindow extends JFrame {
    /**
     * Game logic
     */
    private static final int[][] DIRECTIONS = {{0,1},{1,1},{1,0},{-1,1}};

    /**
     * Bien luu kich thuoc cua ban co
     */
    private int size;

    /**
     * 2 Nguoi choi game caro
     */
    private Player player1 = new Player("Phan Thị Ngọc", 'X');
    private Player player2 = new Player("Nguyễn Thị Thư", 'O');

    private Player mCurrentPlayer = null;

    /**
     * Ma tran button (ô cờ)
     */
    private GameButton[][] buttons;
    private int countEmpty;

    /**
     * Constructor
     *
     * @param size Kich thuoc ban co
     * @throws HeadlessException
     */
    private GameWindow(int size) throws HeadlessException {
        super("Game Window - Phan Thị Ngọc");
        this.size = size;
    }

    /**
     * Factory pattern
     *
     * @param size kich thuoc ban co
     * @return GameWindow obj neu thanh cong hoac null neu day la moi truong headless (CLI)
     */
    public static GameWindow create(int size){
        try {
            GameWindow game = new GameWindow(size);
            game.init();
            return game;
        } catch (HeadlessException e) {
            return null;
        }
    }

    /**
     * Ham xay dung ban co
     */
    private void init() {
        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(size,size));
        gamePanel.setPreferredSize(new Dimension(800, 800));

        // Xay dung lop lang nghe su kien click vao buttons
        // Callback: onPlayerPlayed
        ActionListener listener = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameButton btn = (GameButton)e.getSource();
                if (btn.isPlayable()){
                    btn.play(currentPlayer());
                    if (!onPlayerPlayed(currentPlayer() ,btn.getPosX(), btn.getPosY())) {
                        // Khong chien thang
                        // Kiem tra hoa
                        countEmpty--;
                        if (countEmpty <= 0) {
                            // DRAW!
                            gameEnded(null);
                        }else {
                            // Luot choi ke tiep
                            nextPlayer();
                        }
                    }
                } else {
                    Toolkit.getDefaultToolkit().beep();
                }
            }
        };

        // Xay dung ma tran o co
        buttons = new GameButton[size][size];
        for (int i=0; i<size; i++) {
            for (int j=0;j<size;j++){
                buttons[i][j] = new GameButton(listener, i, j);
                gamePanel.add(buttons[i][j]);
            }
        }

        // Bang diem va thong tin nguoi choi
        JButton resignButton = new JButton("Đầu hàng");
        resignButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameEnded(nextPlayer());
            }
        });

        resignButton.setFont(new Font("SansSerif", Font.BOLD, 32));
        resignButton.setForeground(Color.BLUE);
        JPanel scoreBoard = new JPanel(new GridLayout(1, 3));
        scoreBoard.add(player1.getUI());
        scoreBoard.add(resignButton);
        scoreBoard.add(player2.getUI());

        // Day vao form
        JPanel container = new JPanel();
        container.setLayout(new BorderLayout());
        container.add(gamePanel, BorderLayout.CENTER);
        container.add(scoreBoard, BorderLayout.SOUTH);
        getContentPane().add(container);
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Tao game moi
        newGame();
    }

    /**
     * Dua game ve trang thai ban dau
     * Bat dau mot game moi
     */
    private void newGame() {
        for (GameButton[] gbs : buttons)
            for (GameButton gb : gbs)
                gb.reset();
        countEmpty = size*size;
        mCurrentPlayer = player1;
        notifyTurnChanged();
    }

    /**
     * Ham chuyen nguoi choi
     * @return tra ve nguoi choi tiep theo
     */
    private Player nextPlayer() {
        mCurrentPlayer =  mCurrentPlayer == player1 ? player2 : player1;
        notifyTurnChanged();
        return mCurrentPlayer;
    }

    /**
     * Ham tra ve nguoi choi hien tai
     * @return
     */
    private Player currentPlayer() {
        return mCurrentPlayer;
    }


    /**
     * Xu ly su kien nguoi choi thuc hien mot luot choi
     * @param player
     * @param x
     * @param y
     * @return true neu nguoi choi chien thang
     */
    private boolean onPlayerPlayed(Player player, int x, int y) {
        int i = 0;
        for (int[] dir : DIRECTIONS) {
            i =  Math.max(calc(x, y, player, dir[0], dir[1]) + calc(x, y, player, -dir[0], -dir[1]) - 1, i);
        }
        System.out.println(player.getName() + " : " + i);
        if (i >= 5) {
            player.won();
            gameEnded(player);
            return true;
        }
        return false;
    }

    /**
     * Tinh toan so o co lien tuc cua nguoi choi
     */
    private int calc(int x, int y, Player who, int ax, int ay) {
        if (x < 0 || y < 0 || x >= size || y >= size || buttons[x][y].getPlayer() != who) return 0; // Nope.
        else return 1 + calc(x+ax, y+ay, who, ax, ay);
    }

    /**
     * Su kien doi luot choi
     */
    private void notifyTurnChanged() {
        Player now = currentPlayer();
        Player next = now == player1 ? player2 : player1;
        next.lostTurn();
        now.gotTurn();
    }

    /**
     * Ket thuc game
     * @param player nguoi choi chien thang, hoac null neu hoa
     */
    private void gameEnded(Player player) {
        String message = player == null? "Hòa!" : player.getName() + " chiến thắng!";
        JOptionPane.showMessageDialog(this, message, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        newGame();
    }
}