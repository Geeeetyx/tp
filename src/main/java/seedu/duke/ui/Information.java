//@@author JeraldChen
package seedu.duke.ui;

import seedu.duke.diagnosis.symptoms.Symptom;
import seedu.duke.patient.Patient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import static seedu.duke.save.Storage.saveData;

/**
 * This class stores information on all patients registered by DoctorDuke.
 */

//@@author JeraldChen
public class Information {
    //@@author Geeeetyx
    private static final ArrayList<Integer> queueList = new ArrayList<>();
    //@@author

    private static final HashMap<Integer, Patient> patientsList = new HashMap<>();

    private static final Logger logger = Logger.getLogger(Information.class.getName());

    public static void storePatientInfo(int hash, Patient patient) {
        logger.setLevel(Level.SEVERE);
        logger.log(Level.INFO, "Storing patient information");
        patientsList.put(hash, patient);
    }

    public static Patient getPatientInfo(int hash) {
        logger.log(Level.INFO, "Retrieving patient information");
        return patientsList.get(hash);
    }

    //@@author Geeeetyx

    /**
     * Prints the diagnosis history of the Patient.
     * @param hash The key to access the patient's details.
     */
    public static void printDiagnosisHistory(int hash) {
        logger.log(Level.INFO, "Printing diagnosis history");

        Patient patient = patientsList.get(hash);

        System.out.println("---------------------------------------------------");
        if (patient.getPatientDiagnosisHistory().isEmpty()) {
            System.out.println("You have no past diagnoses");
        } else {
            System.out.println("Your diagnosis history is: ");
            System.out.println("---------------------------------------------------");
            for (int i = 0; i < patient.getPatientDiagnosisHistory().size(); i++) {
                System.out.println(patient.getPatientDiagnosisHistory().get(i));
            }
        }
    }

    //@@author JeraldChen

    /**
     * Resets the diagnosis history of the patient.
     *
     * @param hash The hashed password of the patient.
     */
    public static void resetDiagnosisHistory(int hash) {
        logger.log(Level.INFO, "Resetting diagnosis history");

        Patient patient = patientsList.get(hash);
        patient.getPatientDiagnosisHistory().clear();
        assert patient.getPatientDiagnosisHistory().size() == 0 : "Diagnosis history should be empty";
        System.out.println("---------------------------------------------------");
        System.out.println("Your diagnosis history has been reset.");
        saveData();
    }

    //@@author Thunderdragon221

    /**
     * Checks the existence of a password in Dr Duke.
     *
     * @param hash hashedPassword to check.
     * @return true if hashedPassword exists in Dr Duke, and false otherwise.
     */
    public static boolean checkHash(int hash) {
        logger.log(Level.INFO, "Checking hash");
        return patientsList.containsKey(hash);
    }

    /**
     * Returns all patient data currently stored in Dr Duke.
     *
     * @return Hashmap of all patient data.
     */
    public static HashMap<Integer, Patient> getAllPatientData() {
        logger.log(Level.INFO, "Retrieving all patient data");
        return patientsList;
    }

    //@@author Thunderdragon221

    /**
     * Hashes the password keyed in by the user.
     * @param password password to hash.
     * @return hash of password.
     */
    public static int hashPassword(String password) {
        return password.hashCode();
    }

    //@@author Jeraldchen

    /**
     * Resets the symptom choice of the patient.
     * @param symptoms The symptom choice of the patient.
     */
    public static void resetSymptomChoice(ArrayList<Symptom> symptoms) {
        if (symptoms.size() != 0) {
            symptoms.clear();
            //@@Geeeetyx
            System.out.println("---------------------------------------------------");
            //@@JeraldChen
            System.out.println("Your symptom choice has been reset.");
        } else {
            //@@Geeeetyx
            System.out.println("---------------------------------------------------");
            //@@JeraldChen
            System.out.println("You have not entered any symptoms. No symptoms to reset.");
        }
    }
    //@@author JeraldChen

    /**
     * Prints the symptom history of the patient.
     * @param symptoms The patient's array of symptoms to print.
     */
    public static void viewSymptomHistory(ArrayList<Symptom> symptoms) {
        if (symptoms.size() == 0) {
            System.out.println("You have not entered any symptoms.");
        } else {
            for (int i = 0; i < symptoms.size(); i++) {
                System.out.println(symptoms.get(i));
            }
        }
    }

    //@@author Geeeetyx

    /**
     * Deletes a set of selected symptoms from an array containing symptoms
     * previously selected by the patient.
     *
     * @param symptoms The array of symptoms to delete the chosen symptoms from.
     */
    //@@author JeraldChen
    public static void deleteSymptom(ArrayList<Symptom> symptoms) {
        if (symptoms.size() == 0) {
            System.out.println("You have not entered any symptoms.");
            return;
        }
        System.out.println("---------------------------------------------------");
        System.out.println("Here is the list of your symptoms:");
        for (int i = 0; i < symptoms.size(); i++) {
            System.out.println((i + 1) + ". " + symptoms.get(i));
        }
        System.out.println("Please enter the number of the symptom you want to delete.");
        System.out.println("---------------------------------------------------");
        Scanner scanner = new Scanner(System.in);
        try {
            int index = Integer.parseInt(scanner.nextLine());
            if (index > 0 && index <= symptoms.size()) {
                symptoms.remove(index - 1);
                System.out.println("Successfully deleted symptom!");
                System.out.println("Here is the updated list of your symptoms:");
                for (int i = 0; i < symptoms.size(); i++) {
                    System.out.println((i + 1) + ". " + symptoms.get(i));
                }
            } else {
                System.out.println("Invalid command! Please enter a valid symptom.");
            }
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            System.out.println("Invalid number! Please enter a valid symptom number.");
        }
    }

    //@@author Geeeetyx
    public static ArrayList<Integer> getQueueList() {
        return queueList;
    }

    public static void addToQueue(String password) {
        int hash = Information.hashPassword(password);
        queueList.add(hash);
    }

    public static void printPatientQueueNumber() {
        int queueNumber = queueList.size();
        System.out.println("---------------------------------------------------");
        System.out.println("This is your queue number");
        System.out.println("---------------------------------------------------");
        System.out.println(queueNumber);
    }
    //@@author
}
