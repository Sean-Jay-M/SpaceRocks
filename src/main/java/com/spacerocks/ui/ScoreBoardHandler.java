// Class is part of UI package
package com.spacerocks.ui;

// Importing JavaFX elements
import javafx.scene.control.Label;

// Importing relevant Java elements
import java.io.*;
import java.util.Scanner;

// This class is responsible for adding lines to the save file. The following tutorial
// was used to review text manipulation.
// Source: https://www.tutorialspoint.com/how-to-overwrite-a-line-in-a-txt-file-using-java
public class ScoreBoardHandler {

    // The file containing the high score remains static throughout and is located in the local directory.
    private static final File scoreBoardEntries = new File("./resources/score.txt");

    // This is a public method that will add the new score (if there is a new score)
    // to the static file
    public void refreshScoreBoard(String newScore) throws IOException {
        System.out.println("Refreshing scoreboard");
        if (newScore.equals("0")) return;
        StringBuilder buffer = getRefreshedScoreBoard(newScore);
        writeRefreshedScoreBoardToFile(buffer);
    }

    // This is a public method that will loop through the lines and set a high score if
    // acceptable.
    public void setHighScoreFromText(Label[] textEntries) throws FileNotFoundException {
        Scanner scanner = new Scanner(scoreBoardEntries);
        int entryCounter = 0;

        while (scanner.hasNextLine()) {
            textEntries[entryCounter].setText(scanner.nextLine());
            entryCounter++;
        }
    }

    // This method writes the new set of text to the file.
    private void writeRefreshedScoreBoardToFile(StringBuilder buffer) throws IOException {
        String newScores = buffer.toString();
        FileWriter writer = new FileWriter(scoreBoardEntries);
        writer.write(newScores);
        writer.flush();
    }

    // This method will check for through every line to see if a new high score needs to be inserted.
    private StringBuilder getRefreshedScoreBoard(String newScore) throws FileNotFoundException {
        StringBuilder buffer = new StringBuilder();
        Scanner scanner = new Scanner(scoreBoardEntries);

        boolean newScoreInserted = false;

        for (int i = 0; i < 5; i++) {
            String currentScore = scanner.nextLine();
            System.out.println("The current score is " + currentScore);
            if (Integer.parseInt(newScore) > Integer.parseInt(currentScore) && !newScoreInserted) {
                System.out.println("Adding new score " + currentScore);
                buffer.append(newScore).append(System.lineSeparator());
                newScoreInserted = true;
            }
            addExistingScore(buffer, scanner, currentScore);
        }
        return buffer;
    }

    // This method will "skip a line" when running through the file (i.e. the score does not need to be placed
    // in this spot.
    private void addExistingScore(StringBuilder buffer, Scanner scanner, String currentScore) {
        if (scanner.hasNextLine()) {
            System.out.println("Adding existing line " + currentScore);
            buffer.append(currentScore).append(System.lineSeparator());
        }
    }
}
