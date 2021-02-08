package TicTackToe;

import javax.swing.*;
import java.util.ArrayList;

public class TTTAI {



    public static byte minimax2 (JButton[][] gameState, String player) {
        if (checkWinner(gameState,TicTacToeGUI.computersChar)) {
            return 1;
        }
        if (checkWinner(gameState, TicTacToeGUI.humansChar)) {
            return -1;
        }
        ArrayList<String> moves = new ArrayList<>();
        ArrayList<Byte> scores = new ArrayList<>();
        for (int i = 0; i < gameState.length; i++) {
            for (int j = 0; j < gameState[i].length; j++) {
                if (gameState[i][j].getText().isEmpty()) {
                    moves.add(i + " " + j);
                }
            }
        }
        if (moves.isEmpty()) {
            return 0;
        }
        int bestMove;
        if (player.equalsIgnoreCase("hu")) {
            bestMove = Integer.MAX_VALUE;
        } else {
            bestMove = Integer.MIN_VALUE;
        }
        JButton[][] tempGameState = new JButton[3][3];
        for (int i = 0; i < gameState.length; i++) {
            for (int j = 0; j < gameState[i].length; j++) {
                tempGameState[i][j] = new JButton();
                if (!gameState[i][j].getText().isEmpty()) {
                    tempGameState[i][j].setText(gameState[i][j].getText());
                }
            }
        }
        for (String move: moves) {
            if (player.equalsIgnoreCase("hu")) {
                tempGameState[Integer.parseInt(move.split(" ")[0])][Integer.parseInt(move.split(" ")[1])].setText(TicTacToeGUI.humansChar);
                scores.add(minimax2(tempGameState, "pc"));
                tempGameState[Integer.parseInt(move.split(" ")[0])][Integer.parseInt(move.split(" ")[1])].setText("");
            } else {
                tempGameState[Integer.parseInt(move.split(" ")[0])][Integer.parseInt(move.split(" ")[1])].setText(TicTacToeGUI.computersChar);
                scores.add(minimax2(tempGameState, "hu"));
                tempGameState[Integer.parseInt(move.split(" ")[0])][Integer.parseInt(move.split(" ")[1])].setText("");
            }
        }
        for (int score: scores) {
            if (player.equalsIgnoreCase("hu")) {
                if (score < bestMove) {
                    bestMove = score;
                }
            } else {
                if (score > bestMove) {
                    bestMove = score;
                }
            }
        }
        return (byte) bestMove;
    }


    public static boolean checkWinner(JButton[][] gameFields, String XorO){
        int countDiagonal1 = 1;
        int countDiagonal2 = 1;
        for (int i = 0; i < gameFields.length; i++) {
            if (i < 2) {
                if (gameFields[i][i].getText().equals(gameFields[i + 1][i + 1].getText()) && gameFields[i][i].getText().equalsIgnoreCase(XorO)) {
                    countDiagonal1++;
                }
                if (gameFields[i][2 - i].getText().equals(gameFields[i + 1][1 - i].getText()) && gameFields[i][2 - i].getText().equalsIgnoreCase(XorO)) {
                    countDiagonal2++;
                }
            }
            int countHorizantal = 1;
            int countVertical = 1;
            for (int j = 0; j < gameFields[i].length - 1; j++) {
                if (gameFields[i][j].getText().equals(gameFields[i][j + 1].getText()) && gameFields[i][j].getText().equalsIgnoreCase(XorO)) {
                    countHorizantal++;
                }
                if (gameFields[j][i].getText().equals(gameFields[j + 1][i].getText()) && gameFields[j][i].getText().equalsIgnoreCase(XorO)) {
                    countVertical++;
                }
            }
            if (countHorizantal == 3 || countVertical == 3) {
                return true;
            }
        }
        return countDiagonal1 == 3 || countDiagonal2 == 3;
    }

}
