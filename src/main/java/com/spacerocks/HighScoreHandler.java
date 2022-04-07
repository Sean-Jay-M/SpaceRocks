package com.spacerocks;

import java.io.*;

public class HighScoreHandler {
    public void readScore() throws IOException {
        FileReader reader = new FileReader("resources/score.txt");
        BufferedReader br = new BufferedReader(reader);
        String currentLine;
        while ((currentLine = br.readLine()) != null) {
            System.out.println(currentLine);
        }
    }
}
