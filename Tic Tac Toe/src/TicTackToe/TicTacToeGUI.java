package TicTackToe;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


public class TicTacToeGUI extends MouseAdapter {
    private boolean playerStart = true;
    private JFrame gameFrame;
    private JButton[][] gameFields;
    private static int moveNumber;
    public static String computersChar;
    public static String humansChar;


    public TicTacToeGUI() {
        initGameFrame();
    }

    public void initGameFrame() {
        gameFrame = new JFrame("Tic Tac Toe");
        gameFields = new JButton[3][3];
        gameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        gameFrame.setSize(dimension.width / 2, (int) (dimension.height * .7));
        gameFrame.setResizable(true);
        gameFrame.setLocation(dimension.width / 2 - gameFrame.getWidth() / 2, dimension.height / 2 - gameFrame.getHeight() / 2);
        JPanel border = new JPanel();
        border.setLayout(new BorderLayout());
        border.setBorder(new EmptyBorder(10, 20, 10, 20));
        JPanel gameBoard = new JPanel();
        gameBoard.setLayout(new GridLayout(3, 3));

        for (int i = 0; i < gameFields.length; i++) {
            for (int j = 0; j < gameFields[i].length; j++) {
                JButton currentButton = new JButton();
                currentButton.setHorizontalAlignment(SwingConstants.CENTER);
                currentButton.setFont(currentButton.getFont().deriveFont(90.0F));
                currentButton.setBackground(Color.WHITE);
                currentButton.setBorder(new LineBorder(Color.BLACK, 2));
                currentButton.addMouseListener(this);
                gameFields[i][j] = currentButton;
                gameBoard.add(currentButton);
            }
        }
        border.add(gameBoard, SwingConstants.CENTER);
        gameFrame.add(border, BorderLayout.CENTER);
        JPanel newGameButtonPanel = new JPanel();
        JButton newGameButton = new JButton("New Game");
        newGameButton.addMouseListener(this);
        newGameButtonPanel.add(newGameButton);
        JButton computerFirst = new JButton("Computer First");
        computerFirst.addMouseListener(this);
        //whoIsX.setHorizontalAlignment(SwingConstants.CENTER);
        gameFrame.add(newGameButtonPanel, BorderLayout.NORTH);
        gameFrame.add(computerFirst, BorderLayout.SOUTH);
        gameFrame.setVisible(true);
    }

    public void playComputer() {
        int bestMove = Integer.MIN_VALUE;
        JButton[][] tempGameState = new JButton[3][3];
        ArrayList<String> moves = new ArrayList<>();
        ArrayList<Byte> scores = new ArrayList<>();
        for (int i = 0; i < gameFields.length; i++) {
            for (int j = 0; j <gameFields[i].length ; j++) {
                if (gameFields[i][j].getText().isEmpty()) {
                    moves.add(i + " " + j);
                }
            }
        }
        for (int i = 0; i < gameFields.length; i++) {
            for (int j = 0; j < gameFields[i].length; j++) {
                tempGameState[i][j] = new JButton("");
                if (!gameFields[i][j].getText().isEmpty()) {
                    tempGameState[i][j].setText(gameFields[i][j].getText());
                }
            }
        }
        for (String move: moves) {
            tempGameState[Integer.parseInt(move.split(" ")[0])][Integer.parseInt(move.split(" ")[1])].setText(computersChar);
            scores.add(TTTAI.minimax2(tempGameState, "hu"));
            tempGameState[Integer.parseInt(move.split(" ")[0])][Integer.parseInt(move.split(" ")[1])].setText("");
        }
        for (int score: scores) {
            if (score > bestMove) {
                bestMove = score;
            }
        }
        int index = 0;
        for (int i = 0; i < scores.size(); i++) {
            if (scores.get(i) == bestMove) {
                index = i;
            }
        }
        if (scores.size() > 0) {
            gameFields[Integer.parseInt(moves.get(index).split(" ")[0])][Integer.parseInt(moves.get(index).split(" ")[1])].setText(computersChar);
            if (TTTAI.checkWinner(gameFields, computersChar)) {
                JOptionPane.showMessageDialog(gameFrame, "Computer Won!!!");
            } else {
                int tie = 0;
                for (int i = 0; i < gameFields.length; i++) {
                    for (int j = 0; j < gameFields[i].length; j++) {
                        if (!gameFields[i][j].getText().isEmpty()) {
                            tie++;
                        }
                    }
                }
                if (tie == 9) {
                    JOptionPane.showMessageDialog(gameFrame, "Draw!!!");
                }
            }
        }

    }


    @Override
    public void mousePressed(MouseEvent e) {
        JButton button = (JButton)e.getSource();
        if (button.getText().isEmpty()) {
            if (moveNumber == 0) {
                humansChar = "X";
                computersChar = "O";
                moveNumber++;
            }
            if (playerStart) {
                button.setText("X");
            } else {
                button.setText("O");
            }
            if (TTTAI.checkWinner(gameFields, humansChar)) {
                JOptionPane.showMessageDialog(gameFrame, "You Won!!!");
            } else {
                int tie = 0;
                for (int i = 0; i < gameFields.length; i++) {
                    for (int j = 0; j < gameFields[i].length; j++) {
                        if (!gameFields[i][j].getText().isEmpty()) {
                            tie++;
                        }
                    }
                }
                if (tie == 9) {
                    JOptionPane.showMessageDialog(gameFrame, "Draw!!!");
                }
            }
            playComputer();
            return;
        }
        if (button.getText().equals("Computer First")) {
            if (moveNumber > 0) {
                JOptionPane.showMessageDialog(gameFrame, "You already started the game!!!!");
                return;
            }
            moveNumber++;
            computersChar = "X";
            humansChar = "O";
            playerStart = false;
            playComputer();
            return;
        }
        if (button.getText().equalsIgnoreCase("New Game")) {
            for (int i = 0; i < gameFields.length; i++) {
                for (int j = 0; j < gameFields[i].length; j++) {
                    gameFields[i][j].setText("");
                }
            }
            moveNumber = 0;
            playerStart = true;
        }
    }


}
