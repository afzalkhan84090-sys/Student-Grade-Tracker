import java.util.ArrayList;
import java.util.Scanner;

class Student {
    private String name;
    private ArrayList<Double> grades;

    public Student(String name) {
        this.name = name;
        this.grades = new ArrayList<>();
    }

    public void addGrade(double grade) {
        grades.add(grade);
    }

    public String getName() {
        return name;
    }

    public ArrayList<Double> getGrades() {
        return grades;
    }

    public double getAverage() {
        if (grades.isEmpty()) return 0;
        double total = 0;
        for (double grade : grades) total += grade;
        return total / grades.size();
    }

    public double getHighest() {
        double highest = grades.get(0);
        for (double grade : grades) {
            if (grade > highest) highest = grade;
        }
        return highest;
    }

    public double getLowest() {
        double lowest = grades.get(0);
        for (double grade : grades) {
            if (grade < lowest) lowest = grade;
        }
        return lowest;
    }
}

public class StudentGradeTracker {
    private static final Scanner sc = new Scanner(System.in);
    private static final ArrayList<Student> students = new ArrayList<>();

    private static double readGrade() {
        while (true) {
            System.out.print("Enter grade (0-100): ");
            try {
                double grade = Double.parseDouble(sc.nextLine());
                if (grade >= 0 && grade <= 100) return grade;
                System.out.println("Grade must be between 0 and 100.");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static Student findStudent(String name) {
        for (Student student : students) {
            if (student.getName().equalsIgnoreCase(name)) return student;
        }
        return null;
    }

    public static void main(String[] args) {
        while (true) {
            System.out.println("\\n===== STUDENT GRADE TRACKER =====");
            System.out.println("1. Add student and grades");
            System.out.println("2. Add grade to existing student");
            System.out.println("3. View student report");
            System.out.println("4. View class summary");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            String choice = sc.nextLine();
            switch (choice) {
                case "1":
                    System.out.print("Enter student name: ");
                    String name = sc.nextLine().trim();
                    if (name.isEmpty()) {
                        System.out.println("Name cannot be empty.");
                        break;
                    }
                    if (findStudent(name) != null) {
                        System.out.println("Student already exists.");
                        break;
                    }
                    Student student = new Student(name);
                    System.out.print("How many grades do you want to enter? ");
                    try {
                        int count = Integer.parseInt(sc.nextLine());
                        if (count <= 0) {
                            System.out.println("Enter at least one grade.");
                            break;
                        }
                        for (int i = 0; i < count; i++) student.addGrade(readGrade());
                        students.add(student);
                        System.out.println("Student added successfully.");
                    } catch (NumberFormatException e) {
                        System.out.println("Please enter a valid count.");
                    }
                    break;

                case "2":
                    System.out.print("Enter student name: ");
                    Student existing = findStudent(sc.nextLine().trim());
                    if (existing == null) {
                        System.out.println("Student not found.");
                    } else {
                        existing.addGrade(readGrade());
                        System.out.println("Grade added successfully.");
                    }
                    break;

                case "3":
                    System.out.print("Enter student name: ");
                    Student selected = findStudent(sc.nextLine().trim());
                    if (selected == null) {
                        System.out.println("Student not found.");
                    } else {
                        System.out.println("\\n--- Student Report ---");
                        System.out.println("Name: " + selected.getName());
                        System.out.println("Grades: " + selected.getGrades());
                        System.out.printf("Average: %.2f%n", selected.getAverage());
                        System.out.printf("Highest: %.2f%n", selected.getHighest());
                        System.out.printf("Lowest: %.2f%n", selected.getLowest());
                    }
                    break;

                case "4":
                    if (students.isEmpty()) {
                        System.out.println("No student records available.");
                        break;
                    }
                    double totalAverage = 0;
                    System.out.println("\\n--- Class Summary ---");
                    for (Student s : students) {
                        System.out.printf("%s | Average: %.2f | Highest: %.2f | Lowest: %.2f%n",
                                s.getName(), s.getAverage(), s.getHighest(), s.getLowest());
                        totalAverage += s.getAverage();
                    }
                    System.out.printf("Class average: %.2f%n", totalAverage / students.size());
                    break;

                case "5":
                    System.out.println("Thank you for using Student Grade Tracker.");
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
