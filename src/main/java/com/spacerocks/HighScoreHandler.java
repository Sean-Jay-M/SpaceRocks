package com.spacerocks;

import java.io.*;
import java.util.Scanner;

// https://www.tutorialspoint.com/how-to-overwrite-a-line-in-a-txt-file-using-java

public class HighScoreHandler {
    public void writeScore() throws IOException {
        String newScore = "1000000";
        File scores = new File("./resources/score.txt");
        StringBuffer buffer = new StringBuffer();
        Scanner sc = new Scanner(scores);
        int loop = 1;
        boolean scoreInserted = false;
        while (sc.hasNextLine()) {
            String currentScore = sc.nextLine();
            if (Integer.parseInt(newScore) > Integer.parseInt(currentScore) && !scoreInserted) {
                System.out.println("Extra line, appending " + newScore);
                buffer.append(newScore + System.lineSeparator());
                scoreInserted = true;
            }
            if (sc.hasNextLine()) {
                buffer.append(currentScore + System.lineSeparator());
            }
        }
        String newScores = buffer.toString();
        FileWriter writer = new FileWriter(scores);
        writer.write(newScores);
        writer.flush();
    }
}
