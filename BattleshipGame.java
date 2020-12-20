package com.company;
import java.util.Arrays;
import java.util.Scanner;

public class BattleshipGame {
    private static final char[] FLEET = {'A', 'B', 'D', 'S', 'P'};
    private final static char[][] battleShipBoard = new char[10][10];
    private final static char[][] battleShipBoardForDisplay = new char[10][10];
    private static int ACSunk = 0, BASHSunk = 0, DESunk = 0, SUSunk = 0, PBSunk = 0;


    public static void main(String[] args) throws InterruptedException {
        String[] gameOpening = {"        ~~~Battleship~~~  ", "Inspired by Shmuel Toporowitch's and Dovid Rosenberg's videos on slack.", "There are five boats hiding in the sea, your job is to locate them and attack.", "The sea is marked with coordinates, you have to guess a row and then a column to spot and attack ships.", "If you hit a boat, the coordinate of the attacked boat will be marked with the ships initial.", "If you miss it will be marked with \"*\".", "Boats are: AircraftCarrier of 5 coordinates length, Battleship of 4 length, Destroyer of 3, Submarine of 3, PatrolBoat of 2.", "Find all the Boats to Win!! Good Luck!!!!"};
        for (String s: gameOpening
             ) {
            char[] chars = s.toCharArray();
            for (char c: chars
                 ) {
                System.out.print(c);
                Thread.sleep(100);
            }
            System.out.println();
            Thread.sleep(500);
        }
        fillBattleShipBoard(FLEET);
        //printBoard(battleShipBoard);
        initializeBattleShipBoardForDisplay();
        printBoard(battleShipBoardForDisplay);
        while (true) {
            int areAllShipsSunk = 0;
            Scanner scanner = new Scanner(System.in);
            int x, y;
            try {
                System.out.println("Enter the row and column of position you want to attack:");
                char[] input = scanner.nextLine().toCharArray();
                x = input[0] < 97 ? input[0] - 'A' : input[0] - (32 + 'A');
                y = input[1] - '0';
                if (x < 0 || x > 9 || y < 0 || y > 9 || input.length > 2){
                    throw new Exception();
                }
                setBattleShipBoardForDisplay(x, y);
            } catch (Exception e) {
                System.out.println("Something was wrong with your entry, Enter a letter followed by a number between 0 - 9: ");
            }
            for (char[] chars: battleShipBoardForDisplay
                 ) {
                for (char c: chars
                     ) {
                    if (c != '-' && c != '*') {
                        areAllShipsSunk++;
                }
            }
            }
            if (areAllShipsSunk == 17) {
                System.out.println("\r\rHurray!! YOU WON!!!!\r\nPress Enter to exit");
                scanner.nextLine();
                return;
            }
            printBoard(battleShipBoardForDisplay);
        }
    }


    private static void fillBattleShipBoard(char[] ships) {
        for (char[] chars : battleShipBoard) {
            Arrays.fill(chars, '-');
        }
        for (char c : ships
        ) {
            int ship = switch (c) {
                case 'A' -> 5;
                case 'B' -> 4;
                case 'D', 'S' -> 3;
                case 'P' -> 2;
                default -> throw new IllegalStateException("Unexpected value: " + c);
            };
            int horizontalCoord = (int) (Math.random() * 10);
            int verticalCoord = (int) (Math.random() * 10);
            int alignHorizontalOrVertical = (int) (Math.random() * 2);
            //checks if the boat will go out of bounds, and providing a fix.
            boolean forward = true;
            if (alignHorizontalOrVertical == 0) {
                if (verticalCoord + ship >= 10) {
                    forward = false;
                }
            } else {
                if (horizontalCoord + ship >= 10) {
                    forward = false;
                }
            }
            //checks if the boat will overlap with another one, and providing a fix.
            boolean overlapping = true;
            while (overlapping) {
                overlapping = false;
                int j = 0;
                int i = 0;
                while (i < ship) {
                    if (alignHorizontalOrVertical == 0) {
                        if (battleShipBoard[horizontalCoord][verticalCoord + j] != '-') {
                            overlapping = true;
                            horizontalCoord = (int) (Math.random() * 10);
                            break;
                        }
                    } else {
                        if (battleShipBoard[horizontalCoord + j][verticalCoord] != '-') {
                            overlapping = true;
                            verticalCoord = (int) (Math.random() * 10);
                            break;
                        }
                    }
                    if (forward) {
                        j++;
                    } else {
                        j--;
                    }
                    i++;
                }
            }
            //actually placing the boat element by element
            int i = 0, j= 0;
            while (i < ship) {
                if (alignHorizontalOrVertical == 0) {
                    battleShipBoard[horizontalCoord][verticalCoord + j] = c;
                } else {
                    battleShipBoard[horizontalCoord + j][verticalCoord] = c;
                }
                if (forward) {
                    j++;
                } else {
                    j--;
                }
                i++;
            }
        }
    }


    private static void initializeBattleShipBoardForDisplay(){
        for (char[] chars: battleShipBoardForDisplay
             ) {
            Arrays.fill(chars, '-');
        }
    }


    private static void setBattleShipBoardForDisplay(int x, int y) throws InterruptedException {
        if (battleShipBoard[x][y] > '-' && battleShipBoard[x][y] != battleShipBoardForDisplay[x][y]) {
            battleShipBoardForDisplay[x][y] = battleShipBoard[x][y];
            System.out.println("You hit!!");
            Thread.sleep(500);
            printSunkMessageIfSunk(x, y);
        } else if (battleShipBoard[x][y] == '-'){
            battleShipBoardForDisplay[x][y] = '*';
            battleShipBoard[x][y] = '*';
            System.out.println("Missed!");
        } else {
            System.out.println("You already hit there!");
        }
        Thread.sleep(1000);
    }


    private static void printBoard(char[][] board){
        System.out.print("  ");
        for (int i = 0; i < board.length; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i = 0; i < board.length; i++) {
            System.out.print((char)('A' + i) + " ");
            for (int j = 0; j < board[i].length; j++) {
                char c = board[i][j];
                System.out.print(c + " ");
            }
            System.out.println();
        }
    }


    private static void printSunkMessageIfSunk(int x, int y) throws InterruptedException {
        switch (battleShipBoard[x][y]) {
            case 'A' -> {
                ACSunk++;
                if (ACSunk == 5) {
                    printMesaage("AircraftCarrier sunk!!");
                }
            }
            case 'B' -> {
                BASHSunk++;
                if (BASHSunk == 4) {
                    printMesaage("Battleship sunk!!");
                }
            }
            case 'D' -> {
                DESunk++;
                if (DESunk == 3) {
                    printMesaage("Destroyer sunk!!");
                }
            }
            case 'S' -> {
                SUSunk++;
                if (SUSunk == 3) {
                    printMesaage("Submarine sunk!!");
                }
            }
            case 'P' -> {
                PBSunk++;
                if (PBSunk == 2) {
                    printMesaage("PatrolBoat sunk!!");
                }
            }
        }
   }


   private static void printMesaage(String s) throws InterruptedException {
       for (char c: s.toCharArray()
                ) {
                    System.out.print(c);
                    Thread.sleep(200);
                }
                System.out.println();
   }
}

