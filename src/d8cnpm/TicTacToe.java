package d8cnpm;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by NgocKute on 29/05/2016.
 */
class TicTacToe extends Frame {
    String turn = "O";	//value is either "O" or "X"
    Panel board;//khai bao Panel
    TicTacToeButton button[][];
    Label playerTurn;
    ButtonHandler bH = new ButtonHandler();
    int usedCells = 0;	//number of cells in use

    TicTacToe(String title) {
        super(title);//trieu hoi phương thuc khoi tao của lớp Frame
        board = new Panel();
        button = new TicTacToeButton[3][3];//khoi tao 1 ma tran 3 hang 3 cot
        playerTurn = new Label("Player One's Turn");
    }

    void launchGame() {
        board.setLayout(new GridLayout(3,3));
      /* initialize buttons */
        for (int i = 0, count = 0; i < 3; i++) {
            //button[i] = new TicTacToeButton[3];
            for (int j = 0; j < 3; j++, count++) {
                button[i][j] = new TicTacToeButton();//khoi tao gia tri button[i][j] la rong
            /* inialize button values to 0,1, 2,...,8 */
                button[i][j].value = (new Integer(count)).toString();
                board.add(button[i][j]);//them gia trị vao panel
            /* add listeners to the buttons */
                button[i][j].addActionListener(bH);//kiem tra su kien click neu click thi no se goi ham actionPerformed
            }
        }
        add(board);
        add(playerTurn, BorderLayout.SOUTH);
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

    class ButtonHandler implements ActionListener {
        void changeButtonLabel(TicTacToeButton b) {
            b.setLabel(turn);//gan b=turn
            b.value = turn;
         /* remove listener */
            b.removeActionListener(bH);
            usedCells++;
        }

        //continued on next page
        //ham actionPerformed không đươc thay đổi ten
        public void actionPerformed(ActionEvent actionEvent) {
            boolean win = false;
            boolean draw = false;
            int player = 0;
            TicTacToeButton b = (TicTacToeButton)actionEvent.getSource();
         /* change content of button to O or X */
            changeButtonLabel(b);
         /* check for a win */
         /* horizontal */
            //kiem tra theo hàng
            for (int i = 0; i < 3; i++) {
                System.out.println("button "+i+"="+button[i][0].value);
                System.out.println("button "+i+"="+button[i][1].value);
                System.out.println("button "+i+"="+button[i][2].value);
                System.out.println("```````````````````");
                if ((button[i][0].value).equals(button[i][1].value) &&
                        (button[i][0].value).equals(button[i][2].value)) {
                    win = true;
                }
            }
         /* vertical */
            //kiem tra theo cột:equals so sánh chuỗi
            for (int i = 0; i < 3; i++) {
                if ((button[0][i].value).equals(button[1][i].value) &&
                        (button[0][i].value).equals(button[2][i].value)) {
                    win = true;
                }
            }
         /* diagonal */
            //kiem tra theo hang cheo
            if ((button[0][0].value).equals(button[1][1].value) &&
                    (button[0][0].value).equals(button[2][2].value)) {
                win = true;
            } else if ((button[0][2].value).equals(button[1][1].value) &&
                    (button[0][2].value).equals(button[2][0].value)) {
                win = true;
            }
         /* check for a draw
         * Nếu !win tức là win=false mà usedCells=9 thì draw=true tức là hòa
         * */
            if (!win) {
                if (usedCells==9) {
                    draw = true;
                }
            }
         /* Change message */
            String message = "";

            //continued on next page
            if (win) {
                if (turn.equals("O")) {
                    player = 1;
                } else {
                    player = 2;
                }
                message += "Player " + player + " wins!";
            /* remove all listeners */
                for (int i = 0; i < 3; i++) {
                    for  (int j = 0; j < 3; j++) {
                        button[i][j].removeActionListener(bH);//hủy ActionListener tức là hủy lắng nghe do win=true
                    }
                }
            } else if (draw) {
                message += "It's a draw!";
                for (int i = 0; i < 3; i++) {
                    for  (int j = 0; j < 3; j++) {
                        button[i][j].removeActionListener(bH);
                    }
                }
            } else {
            /* change turn */
                if (turn.equals("O")) {
                    turn = new String("X");
                    player = 2;
                    message += "Player Two's Turn.";
                } else {
                    turn = new String("O");
                    player = 1;
                    message += "Player One's Turn.";
                }
            }
            playerTurn.setText(message);
        }
    }

    class TicTacToeButton extends Button {
        String value;
    }

    public static void main(String args[]) {
        TicTacToe game = new TicTacToe("Tic-Tac-Toe");
        game.launchGame();
    }
}
