import java.util.Scanner;

public class StudentResultManagementSystem {

    static final int MAX_STUDENTS = 100;
    static final int SUBJECTS = 5;

    // Arrays to store student details
    static int[] studentIds = new int[MAX_STUDENTS];
    static String[] studentNames = new String[MAX_STUDENTS];
    static int[][] marks = new int[MAX_STUDENTS][SUBJECTS];

    static int studentCount = 0;

    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {

            System.out.println("\n======================================");
            System.out.println("   STUDENT RESULT MANAGEMENT SYSTEM");
            System.out.println("======================================");
            System.out.println("1. Add Students");
            System.out.println("2. Search Student");
            System.out.println("3. Display All Students");
            System.out.println("4. Display Class Topper");
            System.out.println("5. Display Passed Students");
            System.out.println("6. Display Failed Students");
            System.out.println("7. Exit");
            System.out.println("======================================");   

            int choice = readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    addStudent();
                    break;

                case 2:
                    searchStudent();
                    break;

                case 3:
                    displayAllStudents();
                    break;

                case 4:
                    displayTopper();
                    break;

                case 5:
                    displayPassedStudents();
                    break;

                case 6:
                    displayFailedStudents();
                    break;

                case 7:
                    System.out.println("Application closed.");
                    scan.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // =========================================================
    // ADD STUDENT
    // =========================================================

    static void addStudent() {

        if (studentCount >= MAX_STUDENTS) {
            System.out.println("Student limit reached.");
            return;
        }

        System.out.println("\n--- Add Student ---");

        // Read Student ID
        int id;

        while (true) {

            id = readInt("Enter Student ID: ");

            if (isDuplicateId(id)) {
                System.out.println("Student ID already exists. Enter another ID.");
            } else {
                break;
            }
        }

        // Read Student Name
        String name;

        while (true) {

            System.out.print("Enter Student Name: ");
            name = scan.nextLine().trim();

            if (name.isEmpty()) {
                System.out.println("Student name cannot be empty.");
            } else {
                break;
            }
        }

        // Read marks for 5 subjects
        System.out.println("\nEnter marks for 5 subjects:");

        for (int i = 0; i < SUBJECTS; i++) {

            while (true) {

                int mark = readInt("Subject " + (i + 1) + " marks: ");

                if (mark >= 0 && mark <= 100) {
                    marks[studentCount][i] = mark;
                    break;
                } else {
                    System.out.println(
                        "Invalid marks. Marks must be between 0 and 100."
                    );
                }
            }
        }

        // Store student details
        studentIds[studentCount] = id;
        studentNames[studentCount] = name;

        studentCount++;

        System.out.println("\nStudent added successfully!");
    }

    // =========================================================
    // SEARCH STUDENT
    // =========================================================

    static void searchStudent() {

        if (studentCount == 0) {
            System.out.println("No students available.");
            return;
        }

        System.out.println("\n--- Search Student ---");

        int id = readInt("Enter Student ID to search: ");

        int index = findStudent(id);

        if (index == -1) {
            System.out.println("Student not found.");
        } else {
            displayStudent(index);
        }
    }

    // =========================================================
    // FIND STUDENT
    // =========================================================

    static int findStudent(int id) {

        for (int i = 0; i < studentCount; i++) {

            if (studentIds[i] == id) {
                return i;
            }
        }

        return -1;
    }

    // =========================================================
    // CHECK DUPLICATE ID
    // =========================================================

    static boolean isDuplicateId(int id) {

        for (int i = 0; i < studentCount; i++) {

            if (studentIds[i] == id) {
                return true;
            }
        }

        return false;
    }

    // =========================================================
    // CALCULATE TOTAL
    // =========================================================

    static int calculateTotal(int index) {

        int total = 0;

        for (int i = 0; i < SUBJECTS; i++) {
            total += marks[index][i];
        }

        return total;
    }

    // =========================================================
    // CALCULATE AVERAGE
    // =========================================================

    static double calculateAverage(int index) {

        int total = calculateTotal(index);

        return total / (double) SUBJECTS;
    }

    // =========================================================
    // CALCULATE GRADE
    // =========================================================

    static char calculateGrade(int index) {

        double average = calculateAverage(index);

        if (average >= 90) {
            return 'A';
        } else if (average >= 80) {
            return 'B';
        } else if (average >= 70) {
            return 'C';
        } else if (average >= 60) {
            return 'D';
        } else if (average >= 50) {
            return 'E';
        } else {
            return 'F';
        }
    }

    // =========================================================
    // CHECK PASS / FAIL
    // =========================================================

    static boolean isPassed(int index) {

        for (int i = 0; i < SUBJECTS; i++) {

            if (marks[index][i] < 35) {
                return false;
            }
        }

        return true;
    }

    // =========================================================
    // DISPLAY ONE STUDENT
    // =========================================================

    static void displayStudent(int index) {

        int total = calculateTotal(index);
        double average = calculateAverage(index);
        char grade = calculateGrade(index);

        System.out.println("\n--------------------------------");
        System.out.println("Student ID : " + studentIds[index]);
        System.out.println("Name       : " + studentNames[index]);

        System.out.println("Marks:");

        for (int i = 0; i < SUBJECTS; i++) {
            System.out.println(
                "Subject " + (i + 1) + " : " + marks[index][i]
            );
        }

        System.out.println("Total      : " + total);
        System.out.printf("Average    : %.2f%n", average);
        System.out.println("Grade      : " + grade);

        if (isPassed(index)) {
            System.out.println("Result     : PASS");
        } else {
            System.out.println("Result     : FAIL");
        }

        System.out.println("--------------------------------");
    }

    // =========================================================
    // DISPLAY ALL STUDENTS
    // =========================================================

    static void displayAllStudents() {

        if (studentCount == 0) {
            System.out.println("No students available.");
            return;
        }

        System.out.println("\n========== ALL STUDENTS ==========");

        for (int i = 0; i < studentCount; i++) {
            displayStudent(i);
        }
    }

    // =========================================================
    // DISPLAY CLASS TOPPER
    // =========================================================

    static void displayTopper() {

        if (studentCount == 0) {
            System.out.println("No students available.");
            return;
        }

        int topperIndex = 0;

        for (int i = 1; i < studentCount; i++) {

            if (calculateAverage(i) > calculateAverage(topperIndex)) {
                topperIndex = i;
            }
        }

        System.out.println("\n========== CLASS TOPPER ==========");

        displayStudent(topperIndex);
    }

    // =========================================================
    // DISPLAY PASSED STUDENTS
    // =========================================================

    static void displayPassedStudents() {

        if (studentCount == 0) {
            System.out.println("No students available.");
            return;
        }

        boolean found = false;

        System.out.println("\n========== PASSED STUDENTS ==========");

        for (int i = 0; i < studentCount; i++) {

            if (isPassed(i)) {
                displayStudent(i);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No students have passed.");
        }
    }

    // =========================================================
    // DISPLAY FAILED STUDENTS
    // =========================================================

    static void displayFailedStudents() {

        if (studentCount == 0) {
            System.out.println("No students available.");
            return;
        }

        boolean found = false;

        System.out.println("\n========== FAILED STUDENTS ==========");

        for (int i = 0; i < studentCount; i++) {

            if (!isPassed(i)) {
                displayStudent(i);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No students have failed.");
        }
    }

    // =========================================================
    // SAFE INTEGER INPUT
    // =========================================================

    static int readInt(String message) {

        while (true) {

            System.out.print(message);

            String input = scan.nextLine();

            try {

                return Integer.parseInt(input);

            } catch (NumberFormatException e) {

                System.out.println(
                    "Invalid input. Please enter a valid number."
                );
            }
        }
    }
}