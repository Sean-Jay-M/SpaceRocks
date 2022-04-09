package com.spacerocks;

import javafx.scene.text.Text;

import java.io.*;
import java.util.Scanner;

// https://www.tutorialspoint.com/how-to-overwrite-a-line-in-a-txt-file-using-java

public class ScoreBoardHandler {
    private static final File scoreBoardEntries = new File("./resources/score.txt");

    public void refreshScoreBoard(String newScore) throws IOException {
        System.out.println("Refreshing scoreboard");
        StringBuilder buffer = getRefreshedScoreBoard(newScore);
        writeRefreshedScoreBoardToFile(buffer);
    }

    public void setHigHScoreFromText(Text[] textEntries) throws FileNotFoundException {
        Scanner scanner = new Scanner(scoreBoardEntries);
        int entryCounter = 0;

        while (scanner.hasNextLine()) {
            textEntries[entryCounter].setText(scanner.nextLine());
            entryCounter++;
        }
    }

    private void writeRefreshedScoreBoardToFile(StringBuilder buffer) throws IOException {
        String newScores = buffer.toString();
        FileWriter writer = new FileWriter(scoreBoardEntries);
        writer.write(newScores);
        writer.flush();
    }

    private StringBuilder getRefreshedScoreBoard(String newScore) throws FileNotFoundException {
        StringBuilder buffer = new StringBuilder();
        Scanner scanner = new Scanner(scoreBoardEntries);

        for (int i = 0; i < 5; i++) {
            String currentScore = scanner.nextLine();
            System.out.println("The current score is " + currentScore);
            if (Integer.parseInt(newScore) > Integer.parseInt(currentScore)) {
                System.out.println("Adding new score " + currentScore);
                buffer.append(newScore).append(System.lineSeparator());
            }
            addExistingScore(buffer, scanner, currentScore);
        }
        return buffer;
    }

    private void addExistingScore(StringBuilder buffer, Scanner scanner, String currentScore) {
        if (scanner.hasNextLine()) {
            System.out.println("Adding existing line " + currentScore);
            buffer.append(currentScore).append(System.lineSeparator());
        }
    }
}
