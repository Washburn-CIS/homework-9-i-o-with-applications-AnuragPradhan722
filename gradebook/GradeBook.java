import java.io.*;
import java.util.*;

class Student {
    private String firstName;
    private String lastName;
    private double grade;

    // Constructor
    public Student(String firstName, String lastName, double grade) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.grade = grade;
    }

    // Getters and Setters
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }
}

public class GradeBook {
    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);
        List<Student> students = new ArrayList<>();

        // Load students from file (if available)
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
            System.out.println("\nGradeBook Options:");
            System.out.println("1. View Grades");
            System.out.println("2. Update Grade");
            System.out.println("3. Save Grades");
            System.out.println("4. Add Student");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            String choice = input.nextLine();

            switch (choice) {
                case "1": // View Grades
                    if (students.isEmpty()) {
                        System.out.println("No students available.");
                    } else {
                        for (Student student : students) {
                            System.out.printf("%s, %s: %.2f%n", 
                                    student.getLastName(), 
                                    student.getFirstName(), 
                                    student.getGrade());
                        }
                    }
                    break;

                case "2": // Update Grade
                    System.out.println("Enter First Name: ");
                    String fname = input.nextLine();
                    System.out.println("Enter Last Name: ");
                    String lname = input.nextLine();

                    boolean found = false;
                    for (Student student : students) {
                        if (student.getFirstName().equalsIgnoreCase(fname) &&
                                student.getLastName().equalsIgnoreCase(lname)) {
                            System.out.println("Enter New Grade: ");
                            double newGrade = Double.parseDouble(input.nextLine());
                            student.setGrade(newGrade);
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
                            bw.write(student.getFirstName() + "," +
                                    student.getLastName() + "," +
                                    student.getGrade());
                            bw.newLine();
                        }
                        System.out.println("Grades saved to grades.txt.");
                    } catch (IOException e) {
                        System.out.println("Error saving grades.");
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

                case "5": // Exit
                    System.out.println("Goodbye!");
                    return;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
