package taskmanager;

import java.util.ArrayList;
import java.util.Scanner;

public class TaskManager {
    private ArrayList<Task> tasks = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    public void run() {
        while (true) {
            System.out.println("\n=== Task Manager Menu ===");
            System.out.printf("%-25s%s\n", "1. Add task", "2. Show tasks");
            System.out.printf("%-25s%s\n", "3. Mark task as done", "4. Exit");
            System.out.print("Choose an option: ");

            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Invalid input, please enter a number.");
                scanner.nextLine();
                continue;
            }

            switch (choice) {
                case 1:
                    addTask();
                    break;
                case 2:
                    showTasks();
                    break;
                case 3:
                    markTaskAsDone();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    public void addTask() {
        System.out.print("\nEnter task name: ");
        String name = scanner.nextLine();
        tasks.add(new Task(name));
        System.out.println("Task added");
    }

    public void showTasks() {
        if (tasks.isEmpty()) {
            System.out.println("\n=== No tasks found ===\n");
            return;
        }
        System.out.println("\n┌────┬──────────────┬────────────┐");
        System.out.printf("│ %-2s │ %-12s │ %-10s │\n", "#", "Task", "Status");
        System.out.println("├────┼──────────────┼────────────┤");

        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            String status = task.isDone() ? "done" : "not done";
            System.out.printf("│ %-2d │ %-12s │ %-10s │\n", i + 1, task.getName(), status);
        }
        System.out.println("└────┴──────────────┴────────────┘\n");
    }

    public void markTaskAsDone() {
        showTasks();
        System.out.print("Enter task number to mark as done: ");
        int taskNumber = scanner.nextInt();
        scanner.nextLine();

        if (taskNumber > 0 && taskNumber <= tasks.size()) {
            tasks.get(taskNumber - 1).markAsDone();
            System.out.println("Marked as done");
        } else {
            System.out.println("Invalid task number");
        }
    }
}
