import java.util.ArrayList;
import java.util.Scanner;

public class HospitalQueueSystem2 {
    private static ArrayList<Patient> patientQueue = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static int patientNumber = 1;

    public static void main(String[] args) {
        boolean running = true;

        System.out.println("Welcome to the Hospital Queue Management System");

        while (running) {
            displayMenu();
            int choice = getValidIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    addPatient();
                    break;
                case 2:
                    serveNextPatient();
                    break;
                case 3:
                    displayQueue();
                    break;
                case 4:
                    updatePriority();
                    break;
                case 5:
                    searchPatient();
                    break;
                case 6:
                    System.out.println("Thank you for using the system. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("\n===== HOSPITAL QUEUE SYSTEM =====");
        System.out.println("1. Add a new patient to the queue");
        System.out.println("2. Serve next patient");
        System.out.println("3. Display current queue");
        System.out.println("4. Update patient priority");
        System.out.println("5. Search for a patient");
        System.out.println("6. Exit");
        System.out.println("=================================");
    }

    private static void addPatient() {
        System.out.println("\n----- Add New Patient -----");

        String id = "P" + String.format("%03d", patientNumber++);
        String name = getValidStringInput("Enter patient's name: ");
        int age = getValidIntInput("Enter patient's age: ");
        String complaint = getValidStringInput("Enter patient's chief complaint: ");
        int priority = getValidIntInRange("Enter priority (1=Critical to 5=Non-urgent): ", 1, 5);

        Patient newPatient = new Patient(id, name, age, complaint, priority);
        patientQueue.add(newPatient);

        System.out.println("Patient added successfully with ID: " + id);
    }

    private static void serveNextPatient() {
        if (patientQueue.isEmpty()) {
            System.out.println("No patients in the queue.");
            return;
        }

        Patient nextPatient = patientQueue.get(0);
        for (Patient p : patientQueue) {
            if (p.getPriority() < nextPatient.getPriority()) {
                nextPatient = p;
            }
        }

        patientQueue.remove(nextPatient);

        System.out.println("\n----- Serving Patient -----");
        System.out.println("Now serving: " + nextPatient.getName() + " (ID: " + nextPatient.getId() + ")");
        System.out.println("Age: " + nextPatient.getAge());
        System.out.println("Complaint: " + nextPatient.getCondition());
        System.out.println("Priority: " + getPriorityText(nextPatient.getPriority()));
    }

    private static void displayQueue() {
        if (patientQueue.isEmpty()) {
            System.out.println(" No patients in the queue.");
            return;
        }

        System.out.println("\n----- Patient Queue -----");
        for (Patient p : patientQueue) {
            System.out.println("ID: " + p.getId() +
                    ", Name: " + p.getName() +
                    ", Age: " + p.getAge() +
                    ", Complaint: " + p.getCondition() +
                    ", Priority: " + getPriorityText(p.getPriority()));
        }
    }

    private static void updatePriority() {
        String id = getValidStringInput("Enter the ID of the patient to update: ");
        boolean found = false;

        for (Patient p : patientQueue) {
            if (p.getId().equalsIgnoreCase(id)) {
                int newPriority = getValidIntInRange("Enter new priority (1=Critical to 5=Non-urgent): ");
                p.setPriority(newPriority);
                System.out.println("Priority for " + p.getName() + " has been updated to " + getPriorityText(newPriority));
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Patient with ID " + id + " not found.");
        }
    }

    private static void searchPatient() {
        String name = getValidStringInput("Enter patient's name to search: ");
        boolean found = false;

        for (Patient p : patientQueue) {
            if (p.getName().equalsIgnoreCase(name)) {
                System.out.println("Found: ID: " + p.getId() +
                        ", Age: " + p.getAge() +
                        ", Complaint: " + p.getCondition() +
                        ", Priority: " + getPriorityText(p.getPriority()));
                found = true;
            }
        }

        if (!found) {
            System.out.println("No patient with the name '" + name + "' was found.");
        }
    }

    private static String getPriorityText(int priority) {
        switch (priority) {
            case 1:
                return "1 - Critical";
            case 2:
                return "2 - Urgent";
            case 3:
                return "3 - High";
            case 4:
                return "4 - Medium";
            case 5:
                return "5 - Non-urgent";
            default:
                return "Unknown";
        }
    }
    private static int getValidIntInput(String prompt) {
        int value;
        while (true) {
            System.out.print(prompt);
            try {
                value = Integer.parseInt(scanner.nextLine().trim());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
        return value;
    }

    private static int getValidIntInRange(String prompt, int min, int max) {
        int value;
        while (true) {
            value = getValidIntInput(prompt);
            if (value >= min && value <= max) {
                break;
            }
            System.out.println("Please enter a value between " + min + " and " + max + ".");
        }
        return value;
    }

    private static String getValidStringInput(String prompt) {
        String value;
        while (true) {
            System.out.print(prompt);
            value = scanner.nextLine().trim();
            if (!value.isEmpty()) {
                break;
            }
            System.out.println("Input cannot be empty. Please try again.");
        }
        return value;
    }
}

// Patient class
class Patient {
    private String id;
    private String name;
    private int age;
    private String condition;
    private int priority;

    public Patient(String id, String name, int age, String condition, int priority) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.condition = condition;
        this.priority = priority;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getCondition() { return condition; }
    public int getPriority() { return priority; }
    public void setPriority(int priority) { this.priority = priority; }
}
class Patient {
    private String id;
    private String name;
    private int age;
    private String condition;
    private int priority;

    public Patient(String id, String name, int age, String condition, int priority) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.condition = condition;
        this.priority = priority;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getCondition() {
        return condition;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}

