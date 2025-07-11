package taskmanager;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class TaskManager {
    private static final String FILE_NAME = "tasks.txt";
    private ArrayList<Task> tasks = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    public void run() {
        loadTasksFromFile();
        while (true) {
            printMenu();
            int choice = getUserChoice();

            switch (choice) {
                case 1 -> addTask();
                case 2 -> showTasks();
                case 3 -> markTaskAsDone();
                case 4 -> {
                    saveTasksToFile();
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice");
            }
        }
    }

    private void printMenu() {
        System.out.println("\n=== Task Manager Menu ===");
        System.out.printf("%-25s%s\n", "1. Add task", "2. Show tasks");
        System.out.printf("%-25s%s\n", "3. Mark task as done", "4. Exit");
        System.out.print("Choose an option: ");
    }

    private int getUserChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input, please enter a number");
            return getUserChoice();
        }
    }

    public void addTask() {
        System.out.print("\nEnter task name: ");
        String name = scanner.nextLine();
        if (name.trim().isEmpty()) {
            System.out.println("Task name cannot be empty");
            return;
        }
        tasks.add(new Task(name.trim()));
        saveTasksToFile();
        System.out.println("Task added successfully!");
    }

    public void showTasks() {
        if (tasks.isEmpty()) {
            System.out.println("\n=== No tasks found ===\n");
            return;
        }
        System.out.println("\n┌────┬──────────────────────┬────────────┐");
        System.out.printf("│ %-2s │ %-20s │ %-10s │\n", "#", "Task", "Status");
        System.out.println("├────┼──────────────────────┼────────────┤");

        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            String status = task.isDone() ? "done" : "not done";
            String truncatedName = task.getName().length() > 20 ?
                    task.getName().substring(0, 17) + "..." :
                    task.getName();
            System.out.printf("│ %-2d │ %-20s │ %-10s │\n", i + 1, truncatedName, status);
        }
        System.out.println("└────┴──────────────────────┴────────────┘\n");
    }

    public void markTaskAsDone() {
        if (tasks.isEmpty()) {
            System.out.println("\n=== No tasks found ===\n");
            return;
        }

        showTasks();
        System.out.print("Enter task number to mark as done: ");
        try {
            int taskNumber = Integer.parseInt(scanner.nextLine());

            if (taskNumber > 0 && taskNumber <= tasks.size()) {
                Task task = tasks.get(taskNumber - 1);
                if (task.isDone()) {
                    System.out.println("Task is already marked as done");
                } else {
                    task.markAsDone();
                    saveTasksToFile();
                    System.out.println("Task marked as done successfully!");
                }
            } else {
                System.out.println("Invalid task number");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input, please enter a valid number");
        }
    }

    private void saveTasksToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Task task : tasks) {
                writer.println(task.getName() + ";" + task.isDone());
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    private void loadTasksFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 2) {
                    Task task = new Task(parts[0]);
                    if (Boolean.parseBoolean(parts[1])) {
                        task.markAsDone();
                    }
                    tasks.add(task);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }
    }
}