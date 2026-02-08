package com.bloodbank;

import java.util.*;
import java.io.*;

public class Main {

    static Scanner sc = new Scanner(System.in);

    static ArrayList<Donor> donors = new ArrayList<>();
    static ArrayList<Patient> patients = new ArrayList<>();

    static final String DONOR_FILE = "donors.txt";
    static final String PATIENT_FILE = "patients.txt";
    static final String CREDENTIAL_FILE = "credentials.txt";

    public static void main(String[] args) {

        loadDonors();
        loadPatients();
        ensureCredentialsFile();

        System.out.println("=======================================");
        System.out.println("      BLOOD BANK MANAGEMENT SYSTEM");
        System.out.println("=======================================");

        while (true) {
            System.out.println("\n1. Admin Login");
            System.out.println("2. User Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = getInt();

            if (choice == 1) {
                adminLogin();
            } else if (choice == 2) {
                userLogin();
            } else if (choice == 3) {
                System.out.println("\nThank you! Program exited.");
                break;
            } else {
                System.out.println("\n❌ Invalid choice!");
            }
        }
    }

    // ================= ADMIN LOGIN =================
    static void adminLogin() {
        String[] adminCred = getAdminCredentials();

        System.out.print("\nEnter username: ");
        String username = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();

        if (username.equals(adminCred[0]) && password.equals(adminCred[1])) {
            System.out.println("\n✅ Login Successful!");
            adminPanel();
        } else {
            System.out.println("\n❌ Invalid Username or Password");
        }
    }

    // ================= USER LOGIN =================
    static void userLogin() {
        System.out.print("\nEnter username: ");
        String username = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();

        if (username.equals("user") && password.equals("user")) {
            System.out.println("\n✅ Login Successful!");
            userPanel();
        } else {
            System.out.println("\n❌ Invalid Username or Password");
        }
    }

    // ================= ADMIN PANEL =================
    static void adminPanel() {
        int adminChoice;
        do {
            System.out.println("\n================ ADMIN PANEL ================");
            System.out.println("1. Donor Management");
            System.out.println("2. Patient Management");
            System.out.println("3. Blood Inventory");
            System.out.println("4. Change Admin Password");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");

            adminChoice = getInt();

            switch (adminChoice) {
                case 1:
                    donorManagement();
                    break;
                case 2:
                    patientManagement();
                    break;
                case 3:
                    showInventory();
                    break;
                case 4:
                    changeAdminPassword();
                    break;
                case 5:
                    System.out.println("🔒 Logged out successfully.");
                    break;
                default:
                    System.out.println("❌ Invalid option, try again.");
            }
        } while (adminChoice != 5);
    }

    // ================= USER PANEL =================
    static void userPanel() {
        int choice;
        do {
            System.out.println("\n--- USER PANEL ---");
            System.out.println("1. View Blood Inventory");
            System.out.println("2. Request Blood");
            System.out.println("3. Logout");
            System.out.print("Enter your choice: ");

            choice = getInt();

            switch (choice) {
                case 1:
                    showInventory();
                    break;
                case 2:
                    requestBlood();
                    break;
                case 3:
                    System.out.println("🔒 Logged out successfully.");
                    break;
                default:
                    System.out.println("❌ Invalid option");
            }
        } while (choice != 3);
    }

    // ================= DONOR MANAGEMENT =================
    static void donorManagement() {
        int choice;
        do {
            System.out.println("\n--- DONOR MANAGEMENT ---");
            System.out.println("1. Add Donor");
            System.out.println("2. View Donors");
            System.out.println("3. Update Donor");
            System.out.println("4. Delete Donor");
            System.out.println("5. Back");
            System.out.print("Enter choice: ");
            choice = getInt();

            switch (choice) {
                case 1:
                    addDonor();
                    break;
                case 2:
                    viewDonors();
                    break;
                case 3:
                    updateDonor();
                    break;
                case 4:
                    deleteDonor();
                    break;
                case 5:
                    break;
                default:
                    System.out.println("❌ Invalid option");
            }
        } while (choice != 5);
    }

    static void addDonor() {
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Age: ");
        int age = getInt();
        System.out.print("Enter Blood Group: ");
        String bg = sc.nextLine();
        System.out.print("Enter Phone: ");
        String phone = sc.nextLine();

        donors.add(new Donor(name, age, bg, phone));
        saveDonors();
        System.out.println("✅ Donor Added");
    }

    static void viewDonors() {
        if (donors.isEmpty()) {
            System.out.println("No donors available");
            return;
        }
        for (Donor d : donors) d.display();
    }

    static void updateDonor() {
        viewDonors();
        if (donors.isEmpty()) return;

        System.out.print("Enter index of donor to update (starting 1): ");
        int idx = getInt() - 1;
        if (idx < 0 || idx >= donors.size()) {
            System.out.println("❌ Invalid index");
            return;
        }

        Donor d = donors.get(idx);
        System.out.print("Enter new Name (" + d.name + "): ");
        String name = sc.nextLine();
        System.out.print("Enter new Age (" + d.age + "): ");
        int age = getInt();
        System.out.print("Enter new Blood Group (" + d.bloodGroup + "): ");
        String bg = sc.nextLine();
        System.out.print("Enter new Phone (" + d.phone + "): ");
        String phone = sc.nextLine();

        d.name = name;
        d.age = age;
        d.bloodGroup = bg;
        d.phone = phone;

        saveDonors();
        System.out.println("✅ Donor Updated");
    }

    static void deleteDonor() {
        viewDonors();
        if (donors.isEmpty()) return;

        System.out.print("Enter index of donor to delete (starting 1): ");
        int idx = getInt() - 1;
        if (idx < 0 || idx >= donors.size()) {
            System.out.println("❌ Invalid index");
            return;
        }

        donors.remove(idx);
        saveDonors();
        System.out.println("✅ Donor Deleted");
    }

    // ================= PATIENT MANAGEMENT =================
    static void patientManagement() {
        int choice;
        do {
            System.out.println("\n--- PATIENT MANAGEMENT ---");
            System.out.println("1. Add Patient");
            System.out.println("2. View Patients");
            System.out.println("3. Update Patient");
            System.out.println("4. Delete Patient");
            System.out.println("5. Back");
            System.out.print("Enter choice: ");
            choice = getInt();

            switch (choice) {
                case 1:
                    addPatient();
                    break;
                case 2:
                    viewPatients();
                    break;
                case 3:
                    updatePatient();
                    break;
                case 4:
                    deletePatient();
                    break;
                case 5:
                    break;
                default:
                    System.out.println("❌ Invalid option");
            }
        } while (choice != 5);
    }

    static void addPatient() {
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Age: ");
        int age = getInt();
        System.out.print("Enter Blood Group: ");
        String bg = sc.nextLine();
        System.out.print("Enter Problem: ");
        String problem = sc.nextLine();
        System.out.print("Enter Phone: ");
        String phone = sc.nextLine();

        patients.add(new Patient(name, age, bg, problem, phone));
        savePatients();
        System.out.println("✅ Patient Added");
    }

    static void viewPatients() {
        if (patients.isEmpty()) {
            System.out.println("No patients available");
            return;
        }
        for (Patient p : patients) p.display();
    }

    static void updatePatient() {
        viewPatients();
        if (patients.isEmpty()) return;

        System.out.print("Enter index of patient to update (starting 1): ");
        int idx = getInt() - 1;
        if (idx < 0 || idx >= patients.size()) {
            System.out.println("❌ Invalid index");
            return;
        }

        Patient p = patients.get(idx);
        System.out.print("Enter new Name (" + p.name + "): ");
        String name = sc.nextLine();
        System.out.print("Enter new Age (" + p.age + "): ");
        int age = getInt();
        System.out.print("Enter new Blood Group (" + p.bloodGroup + "): ");
        String bg = sc.nextLine();
        System.out.print("Enter new Problem (" + p.problem + "): ");
        String problem = sc.nextLine();
        System.out.print("Enter new Phone (" + p.phone + "): ");
        String phone = sc.nextLine();

        p.name = name;
        p.age = age;
        p.bloodGroup = bg;
        p.problem = problem;
        p.phone = phone;

        savePatients();
        System.out.println("✅ Patient Updated");
    }

    static void deletePatient() {
        viewPatients();
        if (patients.isEmpty()) return;

        System.out.print("Enter index of patient to delete (starting 1): ");
        int idx = getInt() - 1;
        if (idx < 0 || idx >= patients.size()) {
            System.out.println("❌ Invalid index");
            return;
        }

        patients.remove(idx);
        savePatients();
        System.out.println("✅ Patient Deleted");
    }

    // ================= BLOOD INVENTORY =================
    static void showInventory() {
        Map<String, Integer> map = new HashMap<>();
        for (Donor d : donors)
            map.put(d.bloodGroup, map.getOrDefault(d.bloodGroup, 0) + 1);

        System.out.println("\n--- BLOOD INVENTORY ---");
        if (map.isEmpty()) {
            System.out.println("No blood available.");
            return;
        }
        for (String bg : map.keySet())
            System.out.println(bg + " : " + map.get(bg));
    }

    // ================= USER BLOOD REQUEST =================
    static void requestBlood() {
        System.out.print("Enter required Blood Group: ");
        String req = sc.nextLine();

        Map<String, Integer> map = new HashMap<>();
        for (Donor d : donors)
            map.put(d.bloodGroup, map.getOrDefault(d.bloodGroup, 0) + 1);

        if (map.getOrDefault(req, 0) > 0) {
            System.out.println("✅ Blood available. Request Confirmed!");
            // Reduce stock
            for (int i = 0; i < donors.size(); i++) {
                if (donors.get(i).bloodGroup.equalsIgnoreCase(req)) {
                    donors.remove(i);
                    saveDonors();
                    break;
                }
            }
        } else {
            System.out.println("❌ Sorry, blood not available.");
        }
    }

    // ================= ADMIN PASSWORD =================
    static void changeAdminPassword() {
        System.out.print("Enter new username: ");
        String u = sc.nextLine();
        System.out.print("Enter new password: ");
        String p = sc.nextLine();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CREDENTIAL_FILE))) {
            bw.write(u + "|" + p);
            bw.newLine();
        } catch (Exception e) {}

        System.out.println("✅ Admin credentials updated");
    }

    // ================= FILE HANDLING =================
    static void saveDonors() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(DONOR_FILE))) {
            for (Donor d : donors)
                bw.write(d.name + "|" + d.age + "|" + d.bloodGroup + "|" + d.phone + "\n");
        } catch (Exception e) {}
    }

    static void loadDonors() {
        try (BufferedReader br = new BufferedReader(new FileReader(DONOR_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split("\\|");
                donors.add(new Donor(p[0], Integer.parseInt(p[1]), p[2], p[3]));
            }
        } catch (Exception e) {}
    }

    static void savePatients() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PATIENT_FILE))) {
            for (Patient p : patients)
                bw.write(p.name + "|" + p.age + "|" + p.bloodGroup + "|" + p.problem + "|" + p.phone + "\n");
        } catch (Exception e) {}
    }

    static void loadPatients() {
        try (BufferedReader br = new BufferedReader(new FileReader(PATIENT_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split("\\|");
                patients.add(new Patient(p[0], Integer.parseInt(p[1]), p[2], p[3], p[4]));
            }
        } catch (Exception e) {}
    }

    static void ensureCredentialsFile() {
        File f = new File(CREDENTIAL_FILE);
        if (!f.exists()) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
                bw.write("admin|admin\n");
            } catch (Exception e) {}
        }
    }

    static String[] getAdminCredentials() {
        try (BufferedReader br = new BufferedReader(new FileReader(CREDENTIAL_FILE))) {
            return br.readLine().split("\\|");
        } catch (Exception e) {
            return new String[]{"admin", "admin"};
        }
    }

    static int getInt() {
        while (!sc.hasNextInt()) {
            sc.next();
            System.out.print("Enter number only: ");
        }
        int n = sc.nextInt();
        sc.nextLine();
        return n;
    }
}
