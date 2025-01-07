/**
 *  @assignment LAB EXCERCISE 02
 * 
 *  @author      STUDENT'S NAME: MOHMMED R H HIREZ
 *               STUDENT'S NO:   1 2022 0519
 * 
 *  @instructor  Dr. Eyad El-Masri
 *
 *  @course      BSAI2121 Data Structures and Algorithm Analysis - Lab

 *  @date        05 Jan 2025
 */

import java.time.*;
import java.time.format.*;
import java.time.temporal.*;
import java.util.*;


public class LabExercise02{
    public static void main(String[] args) {
        AssignmentsApp app = new AssignmentsApp();
        app.addAssignment("Math Homework", "Complete exercises 1 to 10", 2025, 2, 25, 23, 59);
        app.addAssignment("English Essay", "Write an essay on Shakespeare", 2025, 1, 26, 12);
        app.addAssignment("Science Project", "Build a model volcano", 2025, 1, 5);
        app.addAssignment("Software Engineering Project", "Design and implement a user login system", 2025, 1, 20, 17, 30);
        app.addAssignment("Database Systems Assignment", "Create ER diagrams and normalize the schema", 2025, 1, 25, 23, 59);
        app.addAssignment("Operating Systems Lab", "Implement a basic CPU scheduler in C", 2024, 12, 30, 11, 0);
        app.addAssignment("Computer Networks Quiz", "Prepare for the quiz on TCP/IP protocols", 2025, 2, 5, 9, 0);
        app.addAssignment("Data Structures Homework", "Solve problems on binary trees and heaps", 2025, 2, 15, 18, 0);
        
        // SHOW
        app.showAssignments();
        app.showEarliestAssignment();

        // REMOVE
        app.removeEarliestAssignment();
        app.removeAssignment("English Essay");
        app.removeAssignments("Science Project","Operating Systems Lab");

        // COMPLETE 
        app.completeAssignment("Data Structures Homework");

        // SHOW THE CHANGES
        app.showAssignments();
        
        
        
        
    }
}




class AssignmentsApp {
    private Comparator<Assignment> Assignments_Comparator = 
        (assignment1, assignment2) -> assignment1.dueDate.compareTo(assignment2.dueDate);

        private  PriorityQueue<Assignment> assignments_PriorityQueue = new PriorityQueue<>(Assignments_Comparator);

     private class Assignment{
        String title = "", details = "";
        LocalDateTime dueDate;

        Assignment(String title, String details, int year,int month, int day){
            this(title, details, year, month,day, 0);
        }

        Assignment(String title, String details, int year,int month, int day, int hour){
            this(title, details, year, month,day, hour,0);
        }

        Assignment(String title, String details,int year,int month, int day, int hour, int minute){
            this.title = title;
            this.details = details;
            this.dueDate = LocalDateTime.of(year, month , day, hour, minute);
        }
}

    public void addAssignment(String title, String details, int year,int month, int day){
        assignments_PriorityQueue.add(new Assignment(title, details, year, month, day)); 
    }

    public void addAssignment(String title, String details, int year,int month, int day, int hour){
        assignments_PriorityQueue.add(new Assignment(title, details, year, month, day, hour)); 
    }

    public void addAssignment(String title, String details, int year,int month, int day, int hour, int minute){
        assignments_PriorityQueue.add(new Assignment(title, details, year, month, day, hour, minute)); 
    }


    public void removeAssignment(String title) {
        assignments_PriorityQueue.removeIf(a -> a.title.equalsIgnoreCase(title));
    }

    public void removeAssignments(String ... titles) {
        for(String title : titles)
            assignments_PriorityQueue.removeIf(a -> a.title.equalsIgnoreCase(title));
    }
    

    public void completeAssignment(String title){
        removeAssignment(title);
    }

    public void showAssignments(){

        List<Assignment> assignments_list = new ArrayList<>(assignments_PriorityQueue);
        if (assignments_list.isEmpty()){
            System.out.println("THER IS NO ASSIGNMENTS YEAT");
            System.out.println("\n");
            return;
        }
        System.out.println("------ ASSIGNMENTS ------\n");

        assignments_list.sort(Assignments_Comparator);

        for(Assignment assignment : assignments_list){
            System.out.print("Title: " + assignment.title);

            if(assignment.dueDate.isBefore(LocalDateTime.now())){

                long totalMinutes = ChronoUnit.MINUTES.between(assignment.dueDate, LocalDateTime.now());
                if (totalMinutes < 0) totalMinutes = -totalMinutes;
        
                System.out.printf(
                    " [OVERDUE BY %d DAYS %d HOURS AND %d MINUTES]%n",
                    totalMinutes / (24 * 60), 
                    (totalMinutes % (24 * 60)) / 60, 
                    totalMinutes % 60
                );
            }


            System.out.println("\n\tDetails: " + assignment.details);
            System.out.println(
                "\tDue Date: " + 
            assignment.dueDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")
            ));

            
            System.out.println("\n");

        }
        System.out.println("\n");
        
    }

    
    public void showEarliestAssignment() {
        if (assignments_PriorityQueue.isEmpty()) {
            System.out.println("THER IS NO ASSIGNMENTS YEAT");
            System.out.println("\n");
            return;
        }
        System.out.println("------ EARLIEST ASSIGNMENT ------\n");
        Assignment assignment = assignments_PriorityQueue.peek();
        System.out.print("Title: " + assignment.title);
        if(assignment.dueDate.isBefore(LocalDateTime.now())){

            long totalMinutes = ChronoUnit.MINUTES.between(assignment.dueDate, LocalDateTime.now());
            if (totalMinutes < 0) totalMinutes = -totalMinutes;
    
            System.out.printf(
                " [OVERDUE BY %d DAYS %d HOURS AND %d MINUTES]%n",
                totalMinutes / (24 * 60), 
                (totalMinutes % (24 * 60)) / 60, 
                totalMinutes % 60
            );
        }
        System.out.println("\n\tDetails: " + assignment.details);
            System.out.print(
                "\tDue Date: " + 
            assignment.dueDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")
            ));
        System.out.println("\n");
    }
    

    public void removeEarliestAssignment(){
        assignments_PriorityQueue.remove();
    }
}
