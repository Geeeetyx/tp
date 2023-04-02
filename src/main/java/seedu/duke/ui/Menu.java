//@@author JeraldChen

package seedu.duke.ui;
import seedu.duke.Duke;
import seedu.duke.diagnosis.Diagnosis;
import seedu.duke.diagnosis.IllnessMatch;
import seedu.duke.diagnosis.symptoms.Symptom;
import seedu.duke.patient.Patient;
import seedu.duke.save.Storage;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

//@@author JeraldChen
public class Menu {
    public static ArrayList<Symptom> symptoms = new ArrayList<>();

    /**
     * Shows the welcome menu.
     */
    //@@Geeeetyx
    public static void showWelcomeMenu() {
        System.out.println("---------------------------------------------------");
        System.out.println("What would you like to do? Please enter the number:");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Exit");
        System.out.println("---------------------------------------------------");
    }

    //@@author JeraldChen
    public static void register() {
        String name = "";
        Scanner scanner = new Scanner(System.in);
        while (true) {
            if (name.equals("")) {
                System.out.println("---------------------------------------------------");
                System.out.println("Please enter your name: ");
                name = scanner.nextLine();
                if (name.equals("")) {
                    System.out.println("---------------------------------------------------");
                    System.out.println("Registration failed! Name cannot be empty.");
                    continue;
                }
            }
            System.out.println("---------------------------------------------------");
            System.out.println("Please enter your password: ");
            String password = scanner.nextLine();

            password = password.replaceAll("\\s", "");
            int hash = Information.hashPassword(password);

            if (password.equals("")) {
                System.out.println("---------------------------------------------------");
                System.out.println("Registration failed! Password cannot be empty.");
            } else if (Information.checkHash(hash)) {
                System.out.println("--------------------------------------------------------");
                System.out.println("Password is already used. Please enter another password.");
            } else {
                System.out.println("---------------------------------------------------");
                System.out.println("Please re-enter your password: ");
                String password2 = new Scanner(System.in).nextLine();
                if (password.equals(password2)) {
                    System.out.println("---------------------------------------------------");
                    System.out.println("Registration successful!");
                    ArrayList<String> diagnosisHistory = new ArrayList<>();
                    Hashtable<String, ArrayList<String>> medicineHistory = new Hashtable<>();
                    Information.storePatientInfo(hash, new Patient(name, hash, diagnosisHistory, medicineHistory));
                    break;
                } else {
                    //@@author Geeeetyx
                    System.out.println("---------------------------------------------------");
                    System.out.println("Registration failed! Passwords do not match.");
                }
            }
        }
    }

    //@@author JeraldChen
    public static void login() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("---------------------------------------------------");
        System.out.println("Please enter your name: ");
        String name = new Scanner(System.in).nextLine();
        System.out.println("---------------------------------------------------");
        System.out.println("Please enter your password: ");
        String password = scanner.nextLine();
        password = password.replaceAll("\\s", "");
        int hash = Information.hashPassword(password);
        if (Information.checkHash(hash) && Information.getPatientInfo(hash).getName().equals(name)) {
            Duke.setPassword(hash);

            //@@author Geeeetyx

            System.out.println("---------------------------------------------------");
            System.out.println("Login successful!");
            System.out.println("Welcome " + name + "!");

            Information.addToQueue(password);
        } else {
            System.out.println("----------------------------------------------------------------------");
            System.out.println("Login failed! Please register first or key in the correct information.");
        }
    }

    //@@author Geeeetyx
    public static void exit() {
        System.out.println("---------------------------------------------------");
        System.out.println("Thank you for using");
        String logo = " ____         ____        _        \n"
                + "|  _ \\  ___  |  _ \\ _  _ | | _____ \n"
                + "| | | |/ _ \\ | | | | | | | |/ / _ \\\n"
                + "| |_| | |    | |_| | |_| |   <  __/\n"
                + "|____/|_|    |____/ \\__,_|_|\\_\\___|\n";
        System.out.print(logo);
        System.out.println("---------------------------------------------------");
        Storage.saveData();
        Storage.saveQueue();
        System.exit(0);
    }
    //@@author

    //@@author Thunderdragon221
    public static void showAccountMenu() {
        System.out.println("---------------------------------------------------");
        System.out.println("What would you like to do? Please enter the number:");
        System.out.println("1.  Report symptoms");
        System.out.println("2.  View diagnosis history");
        System.out.println("3.  Reset diagnosis history");
        System.out.println("4.  View symptoms history");
        System.out.println("5.  Delete symptom choice");
        System.out.println("6.  Reset symptoms");
        System.out.println("7.  View Medicine history");
        System.out.println("8.  List available medicines");
        System.out.println("9.  Find available medicine");
        System.out.println("10. Display Queue Number");
        System.out.println("0. Exit");
        System.out.println("---------------------------------------------------");
    }

    //@@author Thunderdragon221
    /**
     * Reads in a list of symptoms the user experiences.
     *
     * @return ArrayList of symptoms the user experiences.
     */
    public static ArrayList<Symptom> getUserSymptoms() {
        Scanner scanner = new Scanner(System.in);
        displaySymptomList();
        addSymptomToSymptomList(scanner, symptoms);
        while (true) {
            System.out.println("Do you have any other symptoms? [Y/N]");
            String decision = scanner.nextLine();
            if (decision.equals("Y") || decision.equals("y")) {
                displaySymptomList();
                addSymptomToSymptomList(scanner, symptoms);
            } else if (decision.equals("N") || decision.equals("n")) {
                System.out.println("Your entered symptoms are: ");
                System.out.println(symptoms);
                break;
            } else {
                System.out.println("Invalid command! Please indicate Y/N.");
            }
        }
        return symptoms;
    }

    //@@author Jeraldchen
    /**
     * Checks if symptom is valid, and adds it to the list of symptoms.
     *
     * @param scanner  takes in user input
     * @param symptoms list of symptoms
     */
    private static void addSymptomToSymptomList(Scanner scanner, ArrayList<Symptom> symptoms) {
        String symptomChoiceAlphabets;
        symptomChoiceAlphabets = scanner.nextLine();
        symptomChoiceAlphabets = symptomChoiceAlphabets.toUpperCase();
        symptomChoiceAlphabets = symptomChoiceAlphabets.trim();
        String[] symptomChoices = symptomChoiceAlphabets.split("(?!^)");
        parseSymptomInput(symptoms, symptomChoices);
    }

    /**
     * Parses user's input to a Symptom enumerator.
     *
     * @param symptoms       an ArrayList of symptoms.
     * @param symptomChoices an array of strings containing the user's input.
     */
    //@@author Jeraldchen
    private static void parseSymptomInput(ArrayList<Symptom> symptoms, String[] symptomChoices) {
        //@@author tanyizhe
        for (String symptomChoice : symptomChoices) {
            switch (symptomChoice) {
            case "A":
                try {
                    addSymptoms(Symptom.FEVER, symptoms);
                } catch (Exception e) {
                    System.out.println("Invalid command! Please enter a valid symptom.");
                }
                break;
            case "B":
                try {
                    addSymptoms(Symptom.DRY_COUGH, symptoms);
                } catch (Exception e) {
                    System.out.println("Invalid command! Please enter a valid symptom.");
                }
                break;
            case "C":
                try {
                    addSymptoms(Symptom.COUGH_WITH_PHLEGM, symptoms);
                } catch (Exception e) {
                    System.out.println("Invalid command! Please enter a valid symptom.");
                }
                break;
            case "D":
                try {
                    addSymptoms(Symptom.THROAT_IRRITATION, symptoms);
                } catch (Exception e) {
                    System.out.println("Invalid command! Please enter a valid symptom.");
                }
                break;
            case "E":
                try {
                    addSymptoms(Symptom.LOSS_OF_TASTE_OR_SMELL, symptoms);
                } catch (Exception e) {
                    System.out.println("Invalid command! Please enter a valid symptom.");
                }
                break;
            case "F":
                try {
                    addSymptoms(Symptom.RUNNY_NOSE, symptoms);
                } catch (Exception e) {
                    System.out.println("Invalid command! Please enter a valid symptom.");
                }
                break;
            case "G":
                try {
                    addSymptoms(Symptom.HEAD_ACHE, symptoms);
                } catch (Exception e) {
                    System.out.println("Invalid command! Please enter a valid symptom.");
                }
                break;
            //@@author JeraldChen
            case "H":
                try {
                    addSymptoms(Symptom.CHILLS, symptoms);
                } catch (Exception e) {
                    System.out.println("Invalid command! Please enter a valid symptom.");
                }
                break;
            case "I":
                try {
                    addSymptoms(Symptom.FATIGUE, symptoms);
                } catch (Exception e) {
                    System.out.println("Invalid command! Please enter a valid symptom.");
                }
                break;
            case "J":
                try {
                    addSymptoms(Symptom.SNEEZING, symptoms);
                } catch (Exception e) {
                    System.out.println("Invalid command! Please enter a valid symptom.");
                }
                break;
            case "K":
                try {
                    addSymptoms(Symptom.BLOCKED_NOSE, symptoms);
                } catch (Exception e) {
                    System.out.println("Invalid command! Please enter a valid symptom.");
                }
                break;
            case "L":
                try {
                    addSymptoms(Symptom.ITCHY_EYE, symptoms);
                } catch (Exception e) {
                    System.out.println("Invalid command! Please enter a valid symptom.");
                }
                break;
            case "M":
                try {
                    addSymptoms(Symptom.RED_EYES, symptoms);
                } catch (Exception e) {
                    System.out.println("Invalid command! Please enter a valid symptom.");
                }
                break;
            case "N":
                try {
                    addSymptoms(Symptom.DIARRHOEA, symptoms);
                } catch (Exception e) {
                    System.out.println("Invalid command! Please enter a valid symptom.");
                }
                break;
            case "O":
                try {
                    addSymptoms(Symptom.STOMACH_ACHE, symptoms);
                } catch (Exception e) {
                    System.out.println("Invalid command! Please enter a valid symptom.");
                }
                break;
            case "P":
                try {
                    addSymptoms(Symptom.WET_STOOL, symptoms);
                } catch (Exception e) {
                    System.out.println("Invalid command! Please enter a valid symptom.");
                }
                break;
            case "Q":
                try {
                    addSymptoms(Symptom.HARD_LUMPY_STOOL, symptoms);
                } catch (Exception e) {
                    System.out.println("Invalid command! Please enter a valid symptom.");
                }
                break;
            case "R":
                try {
                    addSymptoms(Symptom.NAUSEA, symptoms);
                } catch (Exception e) {
                    System.out.println("Invalid command! Please enter a valid symptom.");
                }
                break;
            case "S":
                try {
                    addSymptoms(Symptom.VOMITING, symptoms);
                } catch (Exception e) {
                    System.out.println("Invalid command! Please enter a valid symptom.");
                }
                break;
            case "T":
                try {
                    addSymptoms(Symptom.SLEEPLESSNESS, symptoms);
                } catch (Exception e) {
                    System.out.println("Invalid command! Please enter a valid symptom.");
                }
                break;
            case "U":
                try {
                    addSymptoms(Symptom.BLURRED_VISION, symptoms);
                } catch (Exception e) {
                    System.out.println("Invalid command! Please enter a valid symptom.");
                }
                break;
            case "V":
                try {
                    addSymptoms(Symptom.SENSITIVITY_TO_LIGHT_AND_SOUND, symptoms);
                } catch (Exception e) {
                    System.out.println("Invalid command! Please enter a valid symptom.");
                }
                break;
            case "W":
                try {
                    addSymptoms(Symptom.MUSCLE_ACHE, symptoms);
                } catch (Exception e) {
                    System.out.println("Invalid command! Please enter a valid symptom.");
                }
                break;
            case "X":
                try {
                    addSymptoms(Symptom.BACK_ACHE, symptoms);
                } catch (Exception e) {
                    System.out.println("Invalid command! Please enter a valid symptom.");
                }
                break;
            //@@Geeeetyx
            case " ":
                break;
            //@@Thunderdragon221
            default:
                System.out.println("Invalid command! Please enter a valid symptom.");
            }
        }
    }

    //@@author Brennanzuz
    /**
     * Adds the users' inputted symptom into the symptoms array.
     *
     * @param symptom  The symptom indicated by the user. Defined and passed from the case statement.
     * @param symptoms List of symptoms
     */
    private static void addSymptoms(Symptom symptom, ArrayList<Symptom> symptoms) {
        if (!symptoms.contains(symptom)) {
            assert symptom != null : symptom + " should not be null";
            symptoms.add(symptom);
        } else {
            System.out.println("You have already entered " + symptom + "!");
        }
    }

    /**
     * Displays a list of symptoms for users to pick out their symptoms from.
     */
    private static void displaySymptomList() {
        //Put all these in a dictionary with the symptom's display name hashed to the actual Symptom.
        System.out.println("---------------------------------------------------");
        System.out.println("Here is the list of possible symptoms:");
        System.out.println("a. Fever");
        System.out.println("b. Dry Cough");
        System.out.println("c. Cough with Phlegm");
        System.out.println("d. Throat Irritation");
        System.out.println("e. Loss of Taste or Smell");
        System.out.println("f. Runny nose");
        System.out.println("g. Headache");
        System.out.println("h. Chills");
        System.out.println("i. Fatigue");
        System.out.println("j. Sneezing");
        System.out.println("k. Blocked Nose");
        System.out.println("l. Itchy eyes");
        System.out.println("m. Red eyes");
        System.out.println("n. Diarrhoea");
        System.out.println("o. Stomachache");
        System.out.println("p. Wet Stools");
        System.out.println("q. Hard or Lumpy Stools");
        System.out.println("r. Nausea");
        System.out.println("s. Vomiting");
        System.out.println("t. Sleeplessness");
        System.out.println("u. Blurred Vision");
        System.out.println("v. Sensitivity to Light and Sound");
        System.out.println("w. Muscle ache");
        System.out.println("x. Backache");
        System.out.println("\nPlease enter a symptom.");
        System.out.println("---------------------------------------------------");
    }

    //@@author tanyizhe
    /**
     * Displays the possible illnesses that the user may have based on the symptoms he/she has entered.
     *
     * @author tanyizhe, Jeraldchen, Geeeetyx
     * @param symptoms ArrayList of symptoms the user has entered.
     */
    public static void displayPossibleIllness(ArrayList<Symptom> symptoms) {
        ArrayList<IllnessMatch> possibleIllnesses = Diagnosis.getPossibleIllnesses(symptoms);

        if (!possibleIllnesses.isEmpty()) {
            System.out.println("---------------------------------------------------");
            System.out.println("You may have: ");
            for (IllnessMatch illnessMatch : possibleIllnesses) {
                System.out.println(illnessMatch.getIllness().getIllnessName() + "    Match: "
                        + illnessMatch.getSimilarityPercentage() * 100 + "%");
            }
            System.out.println("---------------------------------------------------");
        } else {
            //@@author JeraldChen
            System.out.println("------------------------------------------------------------");
            System.out.println("Unable to diagnose illness. Please consult a Doctor instead.");
            System.out.println("------------------------------------------------------------");
        }
    }
    //@@author tanyizhe
    /**
     * Displays prompt for user to input keyword when finding medicines.
     */
    public static void displayFindMedicinePrompt() {
        System.out.println("---------------------------------------------------");
        System.out.println("Please enter a keyword:");
        System.out.println("---------------------------------------------------");
    }
    /**
     * Displays message of results found when using find medicine features.
     */
    public static void displayFindMedicineMessage(String phrase) {
        System.out.println("---------------------------------------------------");
        System.out.println("Results for \"" + phrase + "\":");
    }


}
