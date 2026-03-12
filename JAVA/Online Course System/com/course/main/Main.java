package com.course.main;

import com.course.exception.*;
import com.course.model.Course;
import com.course.model.Student;
import com.course.service.CourseService;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        CourseService service = new CourseService();

        service.addCourse(new Course(1, "Java Programming", 2));
        service.addCourse(new Course(2, "Database Systems", 3));

        while (true) {

            System.out.println("\n1. View Courses");
            System.out.println("2. Enroll Student");
            System.out.println("3. View Enrollments (File)");
            System.out.println("4. Exit");

            System.out.print("Choose option: ");
            int choice = sc.nextInt();

            try {

                switch (choice) {

                    case 1:
                        service.displayCourses();
                        break;

                    case 2:
                        System.out.print("Enter Course ID: ");
                        int cid = sc.nextInt();

                        System.out.print("Enter Student ID: ");
                        int sid = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Enter Student Name: ");
                        String name = sc.nextLine();

                        Student s = new Student(sid, name);

                        service.enrollStudent(cid, s);

                        System.out.println("Student enrolled successfully!");
                        break;

                    case 3:
                        service.viewCourses();
                        break;

                    case 4:
                        System.out.println("Exiting...");
                        return;

                    default:
                        System.out.println("Invalid choice!");
                }

            } catch (CourseFullException |
                     CourseNotFoundException |
                     DuplicateEnrollmentException e) {

                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}