package CODSOFT;
import java.io.*;
import java.util.*;

// 1. Student Class
class Student implements Serializable {
    private String name;
    private int rollNumber;
    private String grade;

    public Student(String name, int rollNumber, String grade) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.grade = grade;
    }

    public int getRollNumber() { return rollNumber; }
    public String getName() { return name; }
    
    public void setName(String name) {
        this.name = name;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Roll No: " + rollNumber + " | Name: " + name + " | Grade: " + grade;
    }
}

// 2. Management System Class
class ManagementSystem {
    private List<Student> students = new ArrayList<>();
    private final String FILE_NAME = "students.dat";

    public void addStudent(Student s) { students.add(s); }

    public boolean removeStudent(int rollNo) {
        return students.removeIf(s -> s.getRollNumber() == rollNo);
    }

    public Student searchStudent(int rollNo) {
        for (Student s : students) {
            if (s.getRollNumber() == rollNo) return s;
        }
        return null;
    }

    public void displayAll() {
        if (students.isEmpty()) System.out.println("No students found.");
        else students.forEach(System.out::println);
    }

    // --- File I/O: to Save Data
    public void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(students);
        } catch (IOException e) {
            System.out.println("Error saving: " + e.getMessage());
        }
    }

    // --- File I/O: to Load Data
    @SuppressWarnings("unchecked")
    public void loadData() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            students = (List<Student>) ois.readObject();
        } catch (Exception e) {
            System.out.println("Error loading: " + e.getMessage());
        }
    }

    public boolean editStudent(int rollNo, int choice, String newValue) {
        for (Student s : students) {
            if (s.getRollNumber() == rollNo) {
                if (choice == 1) s.setName(newValue);
                else if (choice == 2) s.setGrade(newValue);
                return true;
            }
        }
        return false;
    }
}

// 3. User Interface (Console)
public class StudentManagementApp {
    private static Scanner sc = new Scanner(System.in);

    private static int getValidInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input! Please enter a numeric roll number.");
            }
        }
    }

    private static String getValidInput(String prompt, boolean isGrade) {
    while (true) {
        System.out.print(prompt);
        String in = sc.nextLine().trim();
        if (isGrade ? (in.length() > 0 && in.length() <= 2) : (!in.isEmpty() && in.matches("[a-zA-Z\\s]+"))) return in;
        System.out.println(isGrade ? "❌ Grade must be 1-2 characters." : "❌ Name must contain only letters.");
    }
}
    public static void main(String[] args) {
        ManagementSystem sms = new ManagementSystem();
        sms.loadData(); // Load existing students

        while (true) {
            System.out.println("\n--- STUDENT MANAGEMENT SYSTEM ---");
            System.out.println("1. Add Student record\n2. Remove Student record\n3. Search for Student\n4. Display All\n5. Edit existing record\n6. Exit");
            System.out.print("Select an option: ");
            
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    String name = getValidInput("Enter Name: ",false);
                    
                    int roll= getValidInt("Enter Roll Number: ");
                    
                    String grade=getValidInput("Enter Grade: ",true);
                                        
                    if(name.isEmpty() || grade.isEmpty()) {
                        System.out.println("Fields cannot be empty!");
                    } else {
                        sms.addStudent(new Student(name, roll, grade));
                        System.out.println("Student added successfully!");
                    }
                    break;

                case "2":
                    int rRem= getValidInt("Enter Roll Number to remove: ");
                    
                    if (sms.removeStudent(rRem)) System.out.println("Removed.");
                    else System.out.println("Not found.");
                    break;

                case "3":
                    System.out.print("Enter Roll Number to search: ");
                    int rSer = Integer.parseInt(sc.nextLine());
                    Student s = sms.searchStudent(rSer);
                    System.out.println(s != null ? s : "Student not found.");
                    break;

                case "4":
                    sms.displayAll();
                    break;

                case "5": // The Edit Case
                    int rollToEdit= getValidInt("Enter Roll Number of the student to edit: ");
                    
                    // Check if student exists first
                    Student existing = sms.searchStudent(rollToEdit);
                    if (existing != null) {
                        System.out.println("Current Record: " + existing);
                        System.out.println("1. Edit Name\n2. Edit Grade\n3. Edit Both");
                        System.out.print("What would you like to change? ");
                        String editChoice = sc.nextLine().trim();

                        if (editChoice.equals("1") || editChoice.equals("3")) {
                            String newName = getValidInput("Enter New Name: ", false);
                            sms.editStudent(rollToEdit, 1, newName);
                        }
                        
                        if (editChoice.equals("2") || editChoice.equals("3")) {
                            String newGrade = getValidInput("Enter New Grade: ", true);
                            sms.editStudent(rollToEdit, 2, newGrade);
                        }

                        System.out.println(" Update successful!");
                    } else {
                        System.out.println("Student with Roll Number " + rollToEdit + " not found.");
                    }
                    break;

                case "6":
                    sms.saveData(); // Save before exiting
                    System.out.println("Data saved. Goodbye!");
                    System.exit(0);

                default:
                    System.out.println("Invalid option.");
            }
        }
    }
}