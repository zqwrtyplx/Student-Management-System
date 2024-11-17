import java.io.*;
import java.util.Scanner;

public class Main {
    static final int MAX_STUDENTS = 100;
    static Student[] students = new Student[MAX_STUDENTS]; // Array to hold students
    static int studentCount = 0; // Counter for current number of students
    static Scanner scanner = new Scanner(System.in); // Scanner for user input

    public static void main(String[] args) {
        // Main program loop
        while (true) {
            // Display menu options
            System.out.println("Menu:");
            System.out.println("1. Check available seats");
            System.out.println("2. Register student (with ID)");
            System.out.println("3. Delete student");
            System.out.println("4. Find student (with student ID)");
            System.out.println("5. Store student details into a file");
            System.out.println("6. Load student details from the file to the system");
            System.out.println("7. View the list of students based on their names");
            System.out.println("8. Additional options");
            System.out.println("9. Exit");
            System.out.print("Select an option: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine()); // Read user input choice
                switch (choice) {
                    case 1:
                        checkAvailableSeats();
                        break;
                    case 2:
                        registerStudent();
                        break;
                    case 3:
                        deleteStudent();
                        break;
                    case 4:
                        findStudent();
                        break;
                    case 5:
                        storeStudentDetails();
                        break;
                    case 6:
                        loadStudentDetails();
                        break;
                    case 7:
                        viewStudentsSortedByName();
                        break;
                    case 8:
                        additionalOptions();
                        break;
                    case 9:
                        System.out.println("Exiting...");
                        return; // Exit the program
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 9.");
            }
        }
    }

    // Method to display available seats
    public static void checkAvailableSeats() {
        System.out.println("Available seats: " + (MAX_STUDENTS - studentCount));
    }

    // Method to register a new student
    public static void registerStudent() {
        if (studentCount >= MAX_STUDENTS) {
            System.out.println("No available seats.");
            return;
        }
        System.out.print("Enter student ID: ");
        String id = scanner.nextLine().trim(); // Read and trim student ID input
        if (id.isEmpty()) {
            System.out.println("Student ID cannot be empty.");
            return;
        }

        // Check if the ID is unique
        for (int i = 0; i < studentCount; i++) {
            if (students[i].getId().equals(id)) {
                System.out.println("Student ID already exists. Please use a unique ID.");
                return;
            }
        }

        students[studentCount++] = new Student(id); // Create new student object and increment count
        System.out.println("Student registered.");
    }

    // Method to delete a student by ID
    public static void deleteStudent() {
        System.out.print("Enter student ID to delete: ");
        String id = scanner.nextLine().trim();
        for (int i = 0; i < studentCount; i++) {
            if (students[i].getId().equals(id)) {
                students[i] = students[--studentCount]; // Replace student with the last student and decrement count
                System.out.println("Student deleted.");
                return;
            }
        }
        System.out.println("Student not found.");
    }

    // Method to find and display a student by ID
    public static void findStudent() {
        System.out.print("Enter student ID to find: ");
        String id = scanner.nextLine().trim();
        for (int i = 0; i < studentCount; i++) {
            if (students[i].getId().equals(id)) {
                System.out.println("Student found: " + students[i]);
                return;
            }
        }
        System.out.println("Student not found.");
    }

    // Method to store student details into a file
    public static void storeStudentDetails() {
        if (studentCount == 0) {
            System.out.println("No student data found.");
            return;
        }

        try (PrintWriter writer = new PrintWriter(new File("students.txt"))) {
            for (int i = 0; i < studentCount; i++) {
                writer.println(students[i].toCSV()); // Write student details to file in CSV format
            }
            System.out.println("Student details stored into file.");
        } catch (IOException e) {
            System.out.println("Error storing student details: " + e.getMessage());
        }
    }

    // Method to load student details from a file into the system
    public static void loadStudentDetails() {
        File file = new File("students.txt");
        if (!file.exists()) {
            System.out.println("No student data found.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader("students.txt"))) {
            String line;
            studentCount = 0;
            while ((line = reader.readLine()) != null) {
                students[studentCount++] = Student.fromCSV(line); // Read and create students from CSV lines
            }
            if (studentCount == 0) {
                System.out.println("No student data found.");
            } else {
                System.out.println("Student details loaded from file.");
                // Display loaded students
                for (int i = 0; i < studentCount; i++) {
                    System.out.println(students[i]); // Print details of each student
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error loading student details: " + e.getMessage());
        }
    }

    // Method to view students sorted by name
    public static void viewStudentsSortedByName() {
        bubbleSortByName(); // Sort students by name
        for (int i = 0; i < studentCount; i++) {
            System.out.println(students[i]); // Print each student's details
        }
    }


    // Method to sort students by name using bubble sort algorithm
    public static void bubbleSortByName() {
        for (int i = 0; i < studentCount - 1; i++) {
            for (int j = 0; j < studentCount - i - 1; j++) {
                if (students[j].getName().compareTo(students[j + 1].getName()) > 0) {
                    Student temp = students[j];
                    students[j] = students[j + 1];
                    students[j + 1] = temp;
                }
            }
        }
    }


    // Method to handle additional options sub-menu
    public static void additionalOptions() {
        while (true) {
            // Display additional options menu
            System.out.println("Additional options:");
            System.out.println("a. Add student name");
            System.out.println("b. Add module marks");
            System.out.println("c. Generate a summary of the system");
            System.out.println("d. Generate a complete report");
            System.out.println("e. Back to main menu");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine().trim();
            // Handle user's choice
            switch (choice) {
                case "a" -> addStudentName();
                case "b" -> addModuleMarks();
                case "c" -> generateSummary();
                case "d" -> generateCompleteReport();
                case "e" -> {
                    return; // Exit the sub-menu and return to main menu
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // Method to add or update a student's name
    public static void addStudentName() {
        System.out.print("Enter student ID: ");
        String id = scanner.nextLine().trim();
        for (int i = 0; i < studentCount; i++) {
            if (students[i].getId().equals(id)) {
                System.out.print("Enter student name: ");
                String name = scanner.nextLine().trim();
                if (name.isEmpty()) {
                    System.out.println("Student name cannot be empty.");
                    return;
                }
                students[i].setName(name);
                System.out.println("Student name added.");
                return;
            }
        }
        System.out.println("Student not found.");
    }

    // Method to add or update a student's module marks
    public static void addModuleMarks() {
        System.out.print("Enter student ID: ");
        String id = scanner.nextLine().trim();
        for (int i = 0; i < studentCount; i++) {
            if (students[i].getId().equals(id)) {
                try {
                    // Get marks for each module
                    System.out.print("Enter Module 1 mark: ");
                    int mark1 = Integer.parseInt(scanner.nextLine().trim());
                    System.out.print("Enter Module 2 mark: ");
                    int mark2 = Integer.parseInt(scanner.nextLine().trim());
                    System.out.print("Enter Module 3 mark: ");
                    int mark3 = Integer.parseInt(scanner.nextLine().trim());
                    students[i].setModuleMarks(mark1, mark2, mark3);
                    System.out.println("Module marks added.");
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Marks should be numbers.");
                }
                return;
            }
        }
        System.out.println("Student not found.");
    }

    // Method to generate a summary of the system
    public static void generateSummary() {
        int totalRegistered = studentCount;
        int passCountModule1 = 0;
        int passCountModule2 = 0;
        int passCountModule3 = 0;

        // Count students who passed each module
        for (int i = 0; i < studentCount; i++) {
            if (students[i].getModuleMark(0) > 40) {
                passCountModule1++;
            }
            if (students[i].getModuleMark(1) > 40) {
                passCountModule2++;
            }
            if (students[i].getModuleMark(2) > 40) {
                passCountModule3++;
            }
        }

        // Display summary
        System.out.println("Total student registrations: " + totalRegistered);
        System.out.println("Total students who scored more than 40 marks in 1 module: " + passCountModule1);
        System.out.println("Total students who scored more than 40 marks in 2 module: " + passCountModule2);
        System.out.println("Total students who scored more than 40 marks in 3 module: " + passCountModule3);
    }

    // Method to generate a complete report of all students
    public static void generateCompleteReport() {
        bubbleSortByAverage(); // Sort students by average mark
        for (int i = 0; i < studentCount; i++) {
            System.out.println(students[i].completeReport());
        }
    }

    // Method to sort students by average mark using bubble sort
    public static void bubbleSortByAverage() {
        for (int i = 0; i < studentCount - 1; i++) {
            for (int j = 0; j < studentCount - i - 1; j++) {
                if (students[j].calculateAverage() < students[j + 1].calculateAverage()) {
                    // Swap students if they are in the wrong order
                    Student temp = students[j];
                    students[j] = students[j + 1];
                    students[j + 1] = temp;
                }
            }
        }
    }
}