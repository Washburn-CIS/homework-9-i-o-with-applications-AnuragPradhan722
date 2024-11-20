import java.io.*;
import java.util.*;

public class GradeBook {
    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);
        List<Student> students = new ArrayList<>();
        
        // Load students from file
        File file = new File("grades.txt");
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",");
                    students.add(new Student(parts[0], parts[1], Double.parseDouble(parts[2])));
                }
            }
        }

        while (true) {
            System.out.println("GradeBook Options:");
            System.out.println("1. View Grades");
            System.out.println("2. Update Grade");
            System.out.println("3. Save Grades");
            System.out.println("4. Add Student");
            System.out.println("5. Delete Student");
            System.out.println("6. View Average Grade");
            System.out.println("7. Exit");
            System.out.print("Enter choice: ");
            String choice = input.nextLine();

            switch (choice) {
                case "1": // View Grades
                    for (Student student : students) {
                        System.out.printf("%s, %s: %f%n", student.getLastName(),
                                student.getFirstName(),
                                student.getGrade());
                    }
                    break;

                case "2": // Update Grade
                    System.out.println("Enter First Name: ");
                    String fname = input.nextLine();
                    System.out.println("Enter Last Name: ");
                    String lname = input.nextLine();

                    boolean found = false;
                    for (Student student : students) {
                        if (student.getFirstName().equals(fname) &&
                                student.getLastName().equals(lname)) {
                            System.out.println("Enter Grade: ");
                            student.setGrade(Double.parseDouble(input.nextLine()));
                            System.out.println("Grade updated.");
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        System.out.println("Student not found.");
                    }
                    break;

                case "3": // Save Grades to File
                    try (BufferedWriter bw = new BufferedWriter(new FileWriter("grades.txt"))) {
                        for (Student student : students) {
                            bw.write(student.getLastName() + "," +
                                    student.getFirstName() + "," +
                                    student.getGrade());
                            bw.newLine();
                        }
                        System.out.println("Grades saved to grades.txt");
                    }
                    break;

                case "4": // Add Student
                    System.out.println("Enter First Name: ");
                    String newFName = input.nextLine();
                    System.out.println("Enter Last Name: ");
                    String newLName = input.nextLine();
                    System.out.println("Enter Grade: ");
                    double newGrade = Double.parseDouble(input.nextLine());
                    students.add(new Student(newFName, newLName, newGrade));
                    System.out.println("Student added.");
                    break;

                case "5": // Delete Student
                    System.out.println("Enter First Name: ");
                    String delFName = input.nextLine();
                    System.out.println("Enter Last Name: ");
                    String delLName = input.nextLine();

                    students.removeIf(student -> 
                        student.getFirstName().equals(delFName) && 
                        student.getLastName().equals(delLName)
                    );
                    System.out.println("Student deleted if found.");
                    break;

                case "6": // View Average Grade
                    if (students.isEmpty()) {
                        System.out.println("No students in gradebook.");
                    } else {
                        double total = 0;
                        for (Student student : students) {
                            total += student.getGrade();
                        }
                        double average = total / students.size();
                        System.out.printf("Average Grade: %.2f%n", average);
                    }
                    break;

                case "7": // Exit
                    System.out.println("Goodbye!");
                    return;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
