package com.company;


import java.util.Scanner;

public class Bingo {

    private static int[][][] MULTICARDS;

    public static void main(String[] args) {
        System.out.println("Bingo!");
        playBingo(3, createCard(), createCard(), createCard());
    }

    private static void playBingo(int amount, int[][]... cards) {
        MULTICARDS = new int[amount][][];
        for (int i = 0; i < amount; i++) {
            MULTICARDS[i] = cards[i];
        }
        printCards(MULTICARDS);
        System.out.println("Hit any key + enter to start.");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        boolean gameOver;
        while (true) {
            int randomGuessNumber = (int) (Math.random() * 75) + 1;
            for (int i = 0; i < MULTICARDS.length; i++) {
                for (int j = 0; j < MULTICARDS[i].length; j++) {
                    for (int k = 0; k < MULTICARDS[i][j].length; k++) {
                        if (MULTICARDS[i][j][k] == randomGuessNumber) {
                            MULTICARDS[i][j][k] = 0;
                        }
                    }
                }
            }
            System.out.println("Random Number: " + randomGuessNumber);
            printCards(MULTICARDS);
            gameOver = checkIfWinner(MULTICARDS);
            if (gameOver) {
                System.out.println("We have a Winner!!!!");
                return;
            }
            System.out.println("Hit enter for next round.");
            scanner.nextLine();
        }
    }

    private static int[][] createCard() {
        int[] numbersOnTheCard = new int[25];
        for (int i = 0; i < numbersOnTheCard.length; i++) {
            boolean isAlreadyThere;
            do {
                isAlreadyThere = false;
                numbersOnTheCard[i] = (int) (Math.random() * 75) + 1;
                for (int j = 0; j < i; j++) {
                    if (numbersOnTheCard[j] == numbersOnTheCard[i]) {
                        isAlreadyThere = true;
                    }
                }
            } while (isAlreadyThere);
        }
        int[][] card = new int[(int) Math.sqrt(numbersOnTheCard.length)][(int) Math.sqrt(numbersOnTheCard.length)];
        for (int i = 0, j = 0; i < card.length; i++, j += 4) {
            for (int k = 0; k < card[i].length; k++) {
                card[i][k] = numbersOnTheCard[i + j + k];
            }
        }
        card[2][2] = 0;
        return card;
    }

    private static void printCards(int[][][] multiPlayingCards) {
        for (int i = 0; i < multiPlayingCards.length; i++) {
            System.out.print(" B  I  N  G  O    ");
        }
        System.out.println();
        int card = 0;
        while (card < multiPlayingCards[0][0].length) {
            for (int i = 0; i < multiPlayingCards.length; i++) {
                for (int j = card; j < card + 1; j++) {
                    for (int k = 0; k < multiPlayingCards[i][j].length; k++) {
                        if (multiPlayingCards[i][j][k] > 9) System.out.print(multiPlayingCards[i][j][k] + " ");
                        else System.out.print(" " + multiPlayingCards[i][j][k] + " ");
                        if (k == 4) System.out.print("   ");
                    }
                }
            }
            System.out.println();
            card++;
        }
    }

    private static boolean checkIfWinner(int[][][] MULTICARDS) {
        for (int[][] card: MULTICARDS
        ) {
            int winRow = 0, winColumn = 0, winDiagonal1 = 0, winDiagonal2 = 0;
            for (int i = 0; i < card.length; i++) {
                for (int j = 0; j < card[i].length; j++) {
                    if (card[i][j] == 0) {
                        winRow++;
                        if (winRow == 5) return true;
                    }
                    if (card[j][i] == 0) {
                        winColumn++;
                        if (winColumn == 5) return true;
                    }
                    if (i == 0) {
                        if (card[j][j] == 0) {
                            winDiagonal1++;
                            if (winDiagonal1 == 5) return true;
                        }
                        if (card[j][card.length - (j + 1)] == 0) {
                            winDiagonal2++;
                            if (winDiagonal2 == 5) return true;
                        }
                    }
                }
                winRow = 0;
                winColumn = 0;
                winDiagonal1 = 0;
                winDiagonal2 = 0;
            }
        }
        return false;
    }
}
