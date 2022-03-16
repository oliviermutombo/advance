package com.advance.io.fcd.utils;

import com.advance.io.fcd.models.RoundResult;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * <h3>Console Util</h3>
 *
 * Houses utilities to be used in console version ONLY.
 */
@Service
public class ConsoleUtil {

    ConsoleUtil() {}

    private String readInput() throws IOException {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));
        return reader.readLine();
    }
    public void info(final String promptString) {
        System.out.println(promptString);
    }
    public String answerToPrompt(String question) throws IOException {
        info(question);
        return readInput();
    }
    public boolean agreedToPrompt(String question) throws IOException {
        info(question);
        String userInput = readInput();
        if (userInput.equalsIgnoreCase("Y") || userInput.equalsIgnoreCase("Yes")) {
            return true;
        } else {
            return false;
        }
    }
    public void play(RoundResult roundResult) {
        info(roundResult.getHand());
        info(roundResult.getHandStrength());
    }
    public void exitGame(int status) {
        if (status == 0) {
            info("Thanks for playing :-)");
            System.exit(status);
        } else {
            info("An error occurred!");
            System.exit(status);
        }
    }
}
