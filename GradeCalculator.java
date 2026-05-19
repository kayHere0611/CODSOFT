package CODSOFT;
// Task2 CodSoft

import java.util.Scanner;

public class GradeCalculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("====== STUDENT GRADE CALCULATOR ======");
        
        // Input 1: Number of subjects
        System.out.print("Enter the number of subjects: ");
        int numSubjects = scanner.nextInt();
        
        int[] marks = new int[numSubjects];
        int totalMarks = 0;

        // Input 2: Marks for each subject
        for (int i = 0; i < numSubjects; i++) {
            System.out.print("Enter marks obtained in subject " + (i + 1) + " (out of 100): ");
            int input = scanner.nextInt();
            
            // Basic validation for marks
            if (input < 0 || input > 100) {
                System.out.println("Invalid marks! Please enter a value between 0 and 100.");
                i--; // Decrement index to get valid marks
                continue;
            }
            
            marks[i] = input;
            totalMarks += marks[i];
        }

        // Calc Average Percentage
        double averagePercentage = (double) totalMarks / numSubjects;

        // Grade Calc
        String grade;
        if (averagePercentage >= 90) {
            grade = "A+ (Excellent)";
        } else if (averagePercentage >= 80) {
            grade = "A (Very Good)";
        } else if (averagePercentage >= 70) {
            grade = "B (Good)";
        } else if (averagePercentage >= 60) {
            grade = "C (Satisfactory)";
        } else if (averagePercentage >= 50) {
            grade = "D (Pass)";
        } else {
            grade = "F (Fail)";
        }

        // Display 
        System.out.println("\n--- RESULTS ---");
        System.out.println("Total Marks: " + totalMarks + " / " + (numSubjects * 100));
        System.out.printf("Average Percentage: %.2f%%\n", averagePercentage);
        System.out.println("Assigned Grade: " + grade);
        System.out.println("----------------");

        scanner.close();
    }
}