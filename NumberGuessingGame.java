package com.jsp.oibsip;

import java.util.Random;
import java.util.Scanner;

public class NumberGuessingGame {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        int lowerRange = 1;
        int upperRange = 100;
        int generatedNumber = random.nextInt(upperRange - lowerRange + 1) + lowerRange;
        int maxAttempts = 5;
        int currentRound = 1;
        int score = 0;

        System.out.println("Welcome to Guess the Number!");

        while (currentRound <= 3) { // You can adjust the number of rounds as needed
            System.out.println("\nRound " + currentRound);
            System.out.println("Guess a number between " + lowerRange + " and " + upperRange + ": ");

            for (int attempts = 1; attempts <= maxAttempts; attempts++) {
                int userGuess = scanner.nextInt();

                if (userGuess == generatedNumber) {
                    System.out.println("Congratulations! You guessed the correct number!");
                    score += 10 - attempts; // Score based on attempts
                    break;
                } else if (userGuess < generatedNumber) {
                    System.out.println("Too low! Try again.");
                } else {
                    System.out.println("Too high! Try again.");
                }

                if (attempts == maxAttempts) {
                    System.out.println("Sorry, you've run out of attempts. The correct number was: " + generatedNumber);
                }
            }

            currentRound++;
            generatedNumber = random.nextInt(upperRange - lowerRange + 1) + lowerRange;
        }

        System.out.println("\nGame Over! Your total score is: " + score);

        scanner.close();
    }
}
