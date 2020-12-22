package com.company;

public class RollDice {
    public static void main(String[] args) {

        int[] diceCounter = new int[13];
        int roll;
        for (int i = 0; i < 36000; i++) {
            roll = rollDice();
            diceCounter[roll]++;
        }
        int sum = 0;
        for (int i = 2; i < diceCounter.length; i++) {
            System.out.println("amount of times " + i + " was rolled: " + diceCounter[i]);
            sum += diceCounter[i];
        }
        System.out.println("amount of times dice were rolled : " + sum);

    }



    private static int rollDie(){
        return (int) (Math.random() * 6) + 1;
    }

    private static int rollDice(){
        return rollDie() + rollDie();
    }
}
