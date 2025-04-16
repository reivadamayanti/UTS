import java.util.ArrayList; // Mengimpor class ArrayList untuk menyimpan daftar pasien
import java.util.Scanner;   // Mengimpor Scanner untuk membaca input dari pengguna

public class HospitalQueueSystem25 {
    private static ArrayList<Patient> patientQueue = new ArrayList<>(); // List untuk menyimpan antrian pasien
    private static Scanner scanner = new Scanner(System.in);            // Scanner untuk menerima input dari pengguna
    private static int patientNumber = 1;                                // Nomor pasien yang akan terus bertambah otomatis

    public static void main(String[] args) {
        boolean running = true; // Menandakan program masih berjalan

        System.out.println("Welcome to the Hospital Queue Management System"); // Menyambut pengguna

        while (running) { // Selama program berjalan
            displayMenu(); // Tampilkan menu ke pengguna
            int choice = getValidIntInput("Enter your choice: "); // Ambil input pilihan dari pengguna

            switch (choice) { // Menentukan aksi berdasarkan pilihan pengguna
                case 1:
                    addPatient(); // Tambah pasien baru ke antrian
                    break;
                case 2:
                    serveNextPatient(); // Layani pasien berikutnya
                    break;
                case 3:
                    displayQueue(); // Tampilkan semua pasien yang sedang mengantri
                    break;
                case 4:
                    updatePriority(); // Ubah prioritas pasien
                    break;
                case 5:
                    searchPatient(); // Cari pasien berdasarkan nama
                    break;
                case 6:
                    System.out.println("Thank you for using the system. Goodbye!"); // Tampilkan pesan keluar
                    running = false; // Set program berhenti
                    break;
                default:
                    System.out.println("Invalid choice. Please try again."); // Jika input tidak valid
            }
        }

        scanner.close(); // Tutup scanner saat program selesai
    }

    private static void displayMenu() {
        System.out.println("\n===== HOSPITAL QUEUE SYSTEM ====="); // Judul menu
        System.out.println("1. Add a new patient to the queue"); // Opsi 1
        System.out.println("2. Serve next patient");              // Opsi 2
        System.out.println("3. Display current queue");           // Opsi 3
        System.out.println("4. Update patient priority");         // Opsi 4
        System.out.println("5. Search for a patient");            // Opsi 5
        System.out.println("6. Exit");                            // Opsi 6
        System.out.println("================================="); // Penutup menu
    }

    private static void addPatient() {
        System.out.println("\n----- Add New Patient -----"); // Judul bagian tambah pasien

        String id = "P" + String.format("%03d", patientNumber++); // Buat ID pasien otomatis seperti P001
        String name = getValidStringInput("Enter patient's name: "); // Ambil input nama pasien
        int age = getValidIntInput("Enter patient's age: ");         // Ambil input umur
        String complaint = getValidStringInput("Enter patient's chief complaint: "); // Input keluhan
        int priority = getValidIntInRange("Enter priority (1=Critical to 5=Non-urgent): ", 1, 5); // Input prioritas

        Patient newPatient = new Patient(id, name, age, complaint, priority); // Buat objek pasien baru
        patientQueue.add(newPatient); // Tambahkan pasien ke dalam antrian

        System.out.println("Patient added successfully with ID: " + id); // Konfirmasi pasien berhasil ditambahkan
    }

    private static void serveNextPatient() {
        if (patientQueue.isEmpty()) { // Jika tidak ada pasien
            System.out.println("No patients in the queue."); // Tampilkan pesan
            return; // Keluar dari fungsi
        }

        Patient nextPatient = patientQueue.get(0); // Ambil pasien pertama sebagai awal pembanding
        for (Patient p : patientQueue) { // Cek semua pasien
            if (p.getPriority() < nextPatient.getPriority()) { // Jika ada yang prioritasnya lebih tinggi
                nextPatient = p; // Ganti dengan pasien tersebut
            }
        }

        patientQueue.remove(nextPatient); // Hapus pasien yang dilayani dari antrian

        System.out.println("\n----- Serving Patient -----"); // Tampilkan info pelayanan
        System.out.println("Now serving: " + nextPatient.getName() + " (ID: " + nextPatient.getId() + ")"); // Nama dan ID
        System.out.println("Age: " + nextPatient.getAge()); // Umur
        System.out.println("Complaint: " + nextPatient.getCondition()); // Keluhan
        System.out.println("Priority: " + getPriorityText(nextPatient.getPriority())); // Prioritas
    }

    private static void displayQueue() {
        if (patientQueue.isEmpty()) { // Jika antrian kosong
            System.out.println(" No patients in the queue."); // Tampilkan pesan
            return;
        }

        System.out.println("\n----- Patient Queue -----"); // Judul tampilan antrian
        for (Patient p : patientQueue) { // Tampilkan info setiap pasien
            System.out.println("ID: " + p.getId() +
                    ", Name: " + p.getName() +
                    ", Age: " + p.getAge() +
                    ", Complaint: " + p.getCondition() +
                    ", Priority: " + getPriorityText(p.getPriority()));
        }
    }

    private static void updatePriority() {
        String id = getValidStringInput("Enter the ID of the patient to update: "); // Minta ID pasien
        boolean found = false; // Untuk menandai apakah pasien ditemukan

        for (Patient p : patientQueue) { // Cari pasien berdasarkan ID
            if (p.getId().equalsIgnoreCase(id)) {
                int newPriority = getValidIntInRange("Enter new priority (1=Critical to 5=Non-urgent): ", 1, 5); // Input prioritas baru
                p.setPriority(newPriority); // Set prioritas baru
                System.out.println("Priority for " + p.getName() + " has been updated to " + getPriorityText(newPriority)); // Konfirmasi
                found = true; // Tandai bahwa pasien ditemukan
                break;
            }
        }

        if (!found) {
            System.out.println("Patient with ID " + id + " not found."); // Jika tidak ditemukan
        }
    }

    private static void searchPatient() {
        String name = getValidStringInput("Enter patient's name to search: "); // Minta nama
        boolean found = false; // Tandai apakah ditemukan

        for (Patient p : patientQueue) { // Cari nama pasien
            if (p.getName().equalsIgnoreCase(name)) {
                System.out.println("Found: ID: " + p.getId() +
                        ", Age: " + p.getAge() +
                        ", Complaint: " + p.getCondition() +
                        ", Priority: " + getPriorityText(p.getPriority()));
                found = true;
            }
        }

        if (!found) {
            System.out.println("No patient with the name '" + name + "' was found."); // Tidak ditemukan
        }
    }

    private static String getPriorityText(int priority) {
        switch (priority) { // Ubah angka prioritas ke teks yang lebih jelas
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
                return "Unknown"; // Jika tidak sesuai
        }
    }

    private static int getValidIntInput(String prompt) {
        int value;
        while (true) {
            System.out.print(prompt); // Tampilkan prompt
            try {
                value = Integer.parseInt(scanner.nextLine().trim()); // Ambil input dan ubah jadi int
                break; // Jika berhasil, keluar dari loop
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number."); // Jika gagal parsing
            }
        }
        return value; // Kembalikan nilai
    }

    private static int getValidIntInRange(String prompt, int min, int max) {
        int value;
        while (true) {
            value = getValidIntInput(prompt); // Ambil angka
            if (value >= min && value <= max) { // Cek apakah dalam rentang
                break;
            }
            System.out.println("Please enter a value between " + min + " and " + max + "."); // Jika tidak valid
        }
        return value; // Kembalikan nilai yang valid
    }

    private static String getValidStringInput(String prompt) {
        String value;
        while (true) {
            System.out.print(prompt); // Tampilkan prompt
            value = scanner.nextLine().trim(); // Ambil input
            if (!value.isEmpty()) { // Pastikan tidak kosong
                break;
            }
            System.out.println("Input cannot be empty. Please try again."); // Jika kosong, minta ulang
        }
        return value; // Kembalikan input yang valid
    }
}
