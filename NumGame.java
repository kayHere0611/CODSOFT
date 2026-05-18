package CODSOFT;

import java.util.Random;
import java.util.Scanner;

public class NumGame {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        
        // Game configuration
        int minRange = 1;
        int maxRange = 100;
        int maxAttempts = 7; // Limiting attempts per round
        
        int totalRounds = 0;
        int roundsWon = 0;
        int totalScore = 0;

        System.out.println("====== WELCOME TO THE NUMBER GAME ======");
        System.out.println("I will think of a number between " + minRange + " and " + maxRange + ".");
        System.out.println("You have " + maxAttempts + " attempts to guess it per round.\n");

        boolean playAgain = true;

        while (playAgain) {
            totalRounds++;
            int randomNumber = random.nextInt(maxRange - minRange + 1) + minRange;
            int attemptsTaken = 0;
            boolean isCorrect = false;

            System.out.println("--- Round " + totalRounds + " ---");
            
            while (attemptsTaken < maxAttempts) {
                System.out.print("Enter your guess: ");
                
                // Input validation to prevent crashes if user enters text
                while (!scanner.hasNextInt()) {
                    System.out.println("Please enter a valid integer.");
                    System.out.print("Enter your guess: ");
                    scanner.next();
                }
                
                int userGuess = scanner.nextInt();
                attemptsTaken++;

                if (userGuess == randomNumber) {
                    System.out.println("🎉(Emoji) Correct! You guessed the number in " + attemptsTaken + " attempts.");
                    isCorrect = true;
                    roundsWon++;
                    // Score calculation: higher score for fewer attempts
                    int roundScore = (maxAttempts - attemptsTaken + 1) * 10;
                    totalScore += roundScore;
                    break;
                } else if (userGuess < randomNumber) {
                    System.out.println("Too low! Attempts remaining: " + (maxAttempts - attemptsTaken));
                } else {
                    System.out.println("Too high! Attempts remaining: " + (maxAttempts - attemptsTaken));
                }
            }

            if (!isCorrect) {
                System.out.println("😢(Emoji) Game Over for this round! You've run out of attempts.");
                System.out.println("The correct number was: " + randomNumber);
            }

            System.out.println("\n--- Current Stats ---");
            System.out.println("Rounds Played: " + totalRounds + " | Rounds Won: " + roundsWon);
            System.out.println("Total Score: " + totalScore + " points\n");

            // if the user wants to play another round; y works
            System.out.print("Do you want to play another round? (yes/no): ");
            String response = scanner.next().trim().toLowerCase();
            playAgain = response.startsWith("y");
            System.out.println();
        }

        System.out.println("==========================================");
        System.out.println("Thank you for playing!");
        System.out.println("Final Results -> Rounds Won: " + roundsWon + "/" + totalRounds + " | Final Score: " + totalScore);
        System.out.println("==========================================");
        
        scanner.close();
    }
}