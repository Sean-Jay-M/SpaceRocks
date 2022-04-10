package com.spacerocks;

import java.io.*;
import java.util.Scanner;

public class HighScoreHandler {
    public void writeToScoreBoard() throws IOException {
        int newScore = 1000000;
        String scores = "./resources/score.txt";
        StringBuilder buffer = new StringBuilder();
        Scanner sc = new Scanner(scores);
        while (sc.hasNextLine()) {
            int currentScore = Integer.parseInt(sc.nextLine());
            if (newScore > currentScore) {
                buffer.append(currentScore).append(System.lineSeparator());
            }
            buffer.append(sc.nextLine()).append(System.lineSeparator());
        }
        String newScores = buffer.toString();
        System.out.println(newScores);
        FileWriter writer = new FileWriter(scores);
        writer.append(newScores);
    }
}
