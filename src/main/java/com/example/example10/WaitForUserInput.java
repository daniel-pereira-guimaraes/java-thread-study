package com.example.example10;

import java.util.Scanner;

public class WaitForUserInput implements Runnable {
    private final String expectedInput;

    public WaitForUserInput(char expectedInput) {
        this.expectedInput = String.valueOf(expectedInput);
    }

    @Override
    public void run() {
        try (var scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("Press q + ENTER to exit");
                var input = scanner.nextLine();
                if (expectedInput.equals(input)) {
                    return;
                }
            }
        }
    }
}
