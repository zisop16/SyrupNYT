package Games;

import Games.Connections;
import Games.NYTGame;

import java.util.ArrayList;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static Scanner scanner;
    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        NYTGame crossword = new MiniCrossword();
        crossword.start();
    }

    public static int grabOption(String prompt, ArrayList<String> options) {
        System.out.print(prompt);
        System.out.print(" (");
        for (int i = 0; i < options.size(); i++) {
            String option = options.get(i);
            System.out.print(option);
            if (i != options.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.print("): ");
        String response = scanner.nextLine();
        response = response.toLowerCase();
        response = response.replace(" ", "");
        for (int i = 0; i < options.size(); i++) {
            String option = options.get(i).toLowerCase().replace(" ", "");
            if (response.contains(option)) {
                return i;
            }
        }
        System.out.println("Sorry, that was not a valid option. Please try again.");
        return grabOption(prompt, options);
    }
    public static int grabIntOption(String prompt) {
        System.out.print(prompt + ' ');
        String response = scanner.nextLine().replace(" ", "");
        int value;
        try {
            value = Integer.parseInt(response);
        } catch (NumberFormatException e) {
            System.out.println("That wasn't a number, try again.");
            return grabIntOption(prompt);
        }
        return value;
    }
}