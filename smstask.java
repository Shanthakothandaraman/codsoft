import java.io.*;  
import java.util.ArrayList;  
import java.util.List;  
import java.util.Scanner;  

public class StudentManagementSystem {  
    private List<Student> students;  
    private final String storageFile = "students.txt";  

    public StudentManagementSystem() {  
        students = new ArrayList<>();  
        loadStudents();  
    }  

    public void addStudent(Student student) {  
        students.add(student);  
        saveStudents();  
    }  

    public void removeStudent(String rollNumber) {  
        students.removeIf(student -> student.getRollNumber().equals(rollNumber));  
        saveStudents();  
    }  

    public Student searchStudent(String rollNumber) {  
        for (Student student : students) {  
            if (student.getRollNumber().equals(rollNumber)) {  
                return student;  
            }  
        }  
        return null;  
    }  

    public void displayStudents() {  
        if (students.isEmpty()) {  
            System.out.println("No students found.");  
        } else {  
            for (Student student : students) {  
                System.out.println(student);  
            }  
        }  
    }  

    private void loadStudents() {  
        try (BufferedReader reader = new BufferedReader(new FileReader(storageFile))) {  
            String line;  
            while ((line = reader.readLine()) != null) {  
                String[] parts = line.split(",");  
                if (parts.length == 3) {  
                    students.add(new Student(parts[0], parts[1], parts[2]));  
                }  
            }  
        } catch (IOException e) {  
            System.out.println("Error loading students: " + e.getMessage());  
        }  
    }  

    private void saveStudents() {  
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(storageFile))) {  
            for (Student student : students) {  
                writer.write(student.getName() + "," + student.getRollNumber() + "," + student.getGrade());  
                writer.newLine();  
            }  
        } catch (IOException e) {  
            System.out.println("Error saving students: " + e.getMessage());  
        }  
    }  

    public static void main(String[] args) {  
        StudentManagementSystem sms = new StudentManagementSystem();  
        Scanner scanner = new Scanner(System.in);  
        String option;  

        do {  
            System.out.println("1. Add Student");  
            System.out.println("2. Remove Student");  
            System.out.println("3. Search Student");  
            System.out.println("4. Display All Students");  
            System.out.println("5. Exit");  
            System.out.print("Select an option: ");  
            option = scanner.nextLine();  

            switch (option) {  
                case "1":  
                    System.out.print("Enter name: ");  
                    String name = scanner.nextLine();  
                    System.out.print("Enter roll number: ");  
                    String rollNumber = scanner.nextLine();  
                    System.out.print("Enter grade: ");  
                    String grade = scanner.nextLine();  
                    if (!name.isEmpty() && !rollNumber.isEmpty() && !grade.isEmpty()) {  
                        sms.addStudent(new Student(name, rollNumber, grade));  
                    } else {  
                        System.out.println("Name, Roll Number, and Grade cannot be empty.");  
                    }  
                    break;  
                case "2":  
                    System.out.print("Enter roll number of the student to remove: ");  
                    String rollToRemove = scanner.nextLine();  
                    sms.removeStudent(rollToRemove);  
                    break;  
                case "3":  
                    System.out.print("Enter roll number to search: ");  
                    String rollToSearch = scanner.nextLine();  
                    Student foundStudent = sms.searchStudent(rollToSearch);  
                    if (foundStudent != null) {  
                        System.out.println("Found Student: " + foundStudent);  
                    } else {  
                        System.out.println("Student not found.");  
                    }  
                    break;  
                case "4":  
                    sms.displayStudents();  
                    break;  
                case "5":  
                    System.out.println("Exiting...");  
                    break;  
                default:  
                    System.out.println("Invalid option. Please try again.");  
            }  
        } while (!option.equals("5"));  

        scanner.close();  
    }  
}