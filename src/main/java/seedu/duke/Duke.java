package seedu.duke;

import seedu.duke.diagnosis.Diagnosis;
import seedu.duke.diagnosis.IllnessMatch;
import seedu.duke.diagnosis.symptoms.Symptom;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import seedu.duke.save.CorruptedDataException;
import seedu.duke.ui.Menu;
import seedu.duke.ui.Parser;
import seedu.duke.save.Storage;

public class Duke {
    /**
     * Main entry-point for the java.duke.Duke application.
     */
    //@@author Jeraldchen
    public static void main(String[] args) throws FileNotFoundException, CorruptedDataException {
        String logo = " ____         ____        _        \n"
                + "|  _ \\  ___  |  _ \\ _  _ | | _____ \n"
                + "| | | |/ _ \\ | | | | | | | |/ / _ \\\n"
                + "| |_| | |    | |_| | |_| |   <  __/\n"
                + "|____/|_|    |____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);
        Storage.readFile();
        Menu.showMenu();
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();
        Parser.parseWelcome(input);



        //For testing of Diagnosis
//        for (IllnessMatch illnessMatch : Diagnosis.getPossibleIllnesses(new ArrayList<Symptom>(List.of(Symptom.BLOCKED_NOSE,
//                Symptom.RUNNY_NOSE,
//                Symptom.SNEEZING,
//                Symptom.CHILLS,
//                Symptom.FATIGUE,
//                Symptom.THROAT_IRRITATION)))) {
//            System.out.println(illnessMatch.getIllness().getIllnessName());
//            System.out.println(illnessMatch.getSimilarityPercentage());
//        }
    }
}
