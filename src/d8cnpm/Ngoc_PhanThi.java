package d8cnpm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

/**
 * Created by NgocKute on 27/05/2016.
 */
public class Ngoc_PhanThi extends JFrame{
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        // Bien luu kich thuoc ban co
        int size = -1;
        while (true) {
            String result = JOptionPane.showInputDialog(null, "Nhập kích thước cho bàn cờ", "Form", JOptionPane.CANCEL_OPTION);
            if (result != null) {
                try {
                    if (Integer.parseInt(result) > 4&&Integer.parseInt(result)<20) {
                        size = Integer.parseInt(result);
                        System.out.println(size);
                        break;
                    } else {
                        JOptionPane.showMessageDialog(null, "Bạn phải nhập 1 số lớn hơn 4 và nhỏ hơn 20");
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Bạn nhập sai định dạng:bạn không được để ô trống hoặc phải nhập 1 số nguyên dương");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Bạn thực sự muốn đóng");
                System.exit(0);
            }
        }
        Game game = Game.create(size);

    }


    static class Game extends JFrame  implements ActionListener {
        String turn = "O";    //value is either "O" or "X"
        JPanel board;//khai bao Panel
        Panel board2;
        TicTacToeButton button[][];
        JLabel playerTurn;
        int size;
        ButtonHandler bH = new ButtonHandler();
        int usedCells = 0;    //number of cells in use
        JButton newGame=new JButton("New game");
        JButton exit=new JButton("Exit");

        private Game(int size) throws HeadlessException {
            super("Game-Phan Thị Ngọc");
            this.size = size;
        }


        /**
         * Factory pattern
         *
         * @param size kich thuoc ban co
         * @return GameWindow obj neu thanh cong hoac null neu day la moi truong headless (CLI)
         */
        public static Game create(int size) {
            try {
                Game game = new Game(size);
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
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setSize(400,400);
            this.setPreferredSize(new Dimension(500, 500));
           // this.setLayout( new GridLayout(3,1));
            Panel board2=new Panel();
            board = new JPanel();
            add(board2,BorderLayout.SOUTH);
            board.setBorder(BorderFactory.createLineBorder(Color.black, 0));
            board2.add(newGame);
            board2.add(exit);
            newGame.addActionListener(this);
            exit.addActionListener(this);


           //Panel board2=new Panel();
            button = new TicTacToeButton[size][size];//khoi tao 1 ma tran 3 hang 3 cot
            playerTurn = new JLabel("Sẵn sàng mời người chơi");
            board.setLayout(new GridLayout(size,size));

            //khoi tao o
            for (int i = 0; i < size; i++) {
                //button[i] = new TicTacToeButton[3];
                for (int j = 0; j < size; j++) {
                    button[i][j] = new TicTacToeButton();//khoi tao gia tri button[i][j] la rong
            /* inialize button values "" */
                    button[i][j].value = "";
                    board.add(button[i][j]);//them gia trị vao panel
            /* add listeners to the buttons */
                    button[i][j].addActionListener(bH);//kiem tra su kien click neu click thi no se goi ham actionPerformed
                    button[i][j].setFont(new Font("Monospaced", Font.BOLD, 14));
                }
            }

            add(board);
           // board.setBorder(BorderFactory.createLineBorder(Color.red, 5));
            add(playerTurn,BorderLayout.NORTH);
            playerTurn.setForeground(Color.blue);
            playerTurn.setFont(new Font("Monospaced", Font.BOLD, 18));

            /* add listeners for closing the frame */
            addWindowListener( new WindowAdapter() {
                public void windowClosing( WindowEvent event ) {
                    System.exit( 0 );
                }
            } );
      /* display */
            pack();
            setVisible(true);
        }
    //kiểm tra sự kiện click
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton nguon=(JButton) e.getSource();
            if(nguon==newGame)
            {
               board.removeAll();
                Game game = Game.create(size);
            }
            if(nguon==exit)
            {
                //JOptionPane.showMessageDialog(null, "Bạn thực sự muốn thoát khỏi chương trình");
                int reply = JOptionPane.showConfirmDialog(null,
                        "Bạn thực sự muốn thoát khỏi chương trình ?", "Exit", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION)
                    System.exit(0);
            }

        }


        class ButtonHandler implements ActionListener {
            void changeButtonLabel(TicTacToeButton b) {
                b.setLabel(turn);//gan b=turn
                b.value = turn;
                b.setForeground(Color.red);
                /* remove listener */
                b.removeActionListener(bH);
                usedCells++;
            }
            public void actionPerformed(ActionEvent actionEvent) {
                boolean win=false;
                boolean draw=false;
                int player=0;
                TicTacToeButton b= (TicTacToeButton)actionEvent.getSource();
                //thay doi nguoi choi
                changeButtonLabel(b);
                //kiểm tra người chơi thắng
                //kiểm tra theo hàng
                System.out.println("-----------sự kiện click tiếp theo");
                for (int i = 0; i < size; i++) {
                    int d=0;
                    for(int j=0;j<size-4;j++)
                    {
                        System.out.println("button size/2:"+button[i][size/2].value+"-------");
                        if(((button[i][size/2].value).equals("")))break;
                        if(!(button[i][j].value).equals(""))
                            if((button[i][j].value).equals(button[i][j+1].value) &&
                                    (button[i][j].value).equals(button[i][j+2].value)&&(button[i][j].value).equals(button[i][j+3].value)&&(button[i][j].value).equals(button[i][j+4].value))
                            {
                                win=true;break;
                            }
                    }
                    if(win==true)break;

                }
                //kiem tra theo cột
                for (int j = 0; j < size; j++) {
                    for(int i=0;i<size-4;i++)

                    {

                        if(((button[size/2][j].value).equals("")))break;
                        if(!(button[i][j].value).equals(""))
                        if((button[i][j].value).equals(button[i+1][j].value) &&
                                (button[i][j].value).equals(button[i+2][j].value)&&(button[i][j].value).equals(button[i+3][j].value)&&(button[i][j].value).equals(button[i+4][j].value))
                        {
                            win=true;break;
                        }
                    }
                    if(win==true)break;
                }
                //kiem tra duong cheo
                if(size==5) {
                    if(!(button[0][0].value).equals(""))
                        if ((button[0][0].value).equals(button[1][1].value) &&
                                (button[0][0].value).equals(button[2][2].value)&&(button[0][0].value).equals(button[3][3].value)&&(button[0][0].value).equals(button[4][4].value)) {
                            win = true;

                        }
                    if(!(button[0][4].value).equals(""))
                        if ((button[0][4].value).equals(button[1][3].value) &&
                                (button[0][4].value).equals(button[2][2].value)&&(button[0][4].value).equals(button[3][1].value)&&(button[0][4].value).equals(button[4][0].value)) {
                            win = true;

                        }
                }
                else
                {
                    for(int i=0;i<size-4;i++){
                        for(int j=0;j<size-4;j++){
                            if(!(button[i][j].value).equals(""))
                            if ((button[i][j].value).equals(button[i+1][j+1].value) &&
                                    (button[i][j].value).equals(button[i+2][j+2].value)&&(button[i][j].value).equals(button[i+3][j+3].value)&&(button[i][j].value).equals(button[i+4][j+4].value)) {
                                win = true;
                                break;
                            }
                        }
                        if(win==true)break;
                    }
                    for(int i=0;i<size-4;i++){
                        for (int j=size-1;j>=4;j--){
                            if(!(button[i][j].value).equals(""))
                            if ((button[i][j].value).equals(button[i+1][j-1].value) &&
                                    (button[i][j].value).equals(button[i+2][j-2].value)&&(button[i][j].value).equals(button[i+3][j-3].value)&&(button[i][j].value).equals(button[i+4][j-4].value)) {
                                win = true;
                                break;
                            }
                        }
                        if(win==true)break;
                    }
                }

                //check for a draw
                if(!win&& usedCells==(size*size))
                {
                    draw=true;
                }
                //thong bao
                String message="";

                if(win)
                {
                    if(turn.equals("O"))
                    {
                        player=1;

                    }
                    else
                    {
                        b.setForeground(Color.black);
                        player=2;
                    }
                    message="Người chơi thứ "+player+" thắng";

                    //newGame();
                    /* remove all listeners */
                    for (int i = 0; i <size; i++) {
                        for  (int j = 0; j <size; j++) {
                            button[i][j].removeActionListener(bH);//hủy ActionListener tức là hủy lắng nghe do win=true
                        }
                    }
                    JOptionPane.showMessageDialog(null, message, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    playerTurn.setText("Kết thúc ván chơi");
                    playerTurn.setForeground(Color.red);
                }
                else if (draw) {
                    message += "Hòa!";
                    for (int i = 0; i < size; i++) {
                        for  (int j = 0; j < size; j++) {
                            button[i][j].removeActionListener(bH);
                        }
                    }
                    JOptionPane.showMessageDialog(null, message, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    playerTurn.setText("Kết thúc ván chơi");
                    playerTurn.setForeground(Color.red);
                } else {
            /* change turn */
                    if (turn.equals("O")) {

                        turn = new String("X");
                        player = 2;
                        message +="Người chơi thứ 1:Phan Thị Ngọc";
                    } else {
                        b.setForeground(Color.black);
                        turn = new String("O");
                        player = 1;
                        message += "Người chơi thứ 2:Nguyễn Thị Thư";
                    }
                    playerTurn.setText(message);
                }


            }
        }

        class TicTacToeButton extends Button {
            String value;

            public void reset() {

            }
        }
    }
}

