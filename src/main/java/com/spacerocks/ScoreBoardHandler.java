package com.spacerocks;

import java.io.*;
import java.util.Scanner;

// https://www.tutorialspoint.com/how-to-overwrite-a-line-in-a-txt-file-using-java

public class ScoreBoardHandler {
    private static final File scoreBoardEntries = new File("./resources/score.txt");

    public void refreshScoreBoard(String newScore) throws IOException {
        StringBuilder buffer = getRefreshedScoreBoard(newScore);
        writeRefreshedScoreBoardToFile(buffer);
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
        boolean scoreInserted = false;

        while (scanner.hasNextLine()) {
            String currentScore = scanner.nextLine();
            if (Integer.parseInt(newScore) > Integer.parseInt(currentScore) && !scoreInserted) {
                buffer.append(newScore).append(System.lineSeparator());
                scoreInserted = true;
            }
            addExistingScore(buffer, scanner, currentScore);
        }
        return buffer;
    }

    private void addExistingScore(StringBuilder buffer, Scanner scanner, String currentScore) {
        if (scanner.hasNextLine()) {
            buffer.append(currentScore).append(System.lineSeparator());
        }
    }
}
