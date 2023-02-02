package com.cs3343.Hangman;

import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    static Game game = new Game();

    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        Scanner scan = new Scanner(System.in);
        System.out.println("Welcome to my Hangman Game. Let's get ready to play");
        System.out.println("Will you be playing alone or with a friend?");
        System.out.println("    Type [1] for SINGLE PLAYER");
        System.out.println("    Type [2] for MULTIPLAYER");
        System.out.println("    Type [3] for RULES");
        System.out.println("    Type [4] for QUIT");
        String x = scan.nextLine();

            switch (x) {
                case "1":
                    System.out.println("You have chosen to play against the Computer. The Computer will now choose a word");
                    game.singlePlayer();
                    game.restart();
                    break;
                case "2":
                    System.out.println("You have chosen to play against a friend.");
                    game.multiPlayer();
                    game.restart();
                    break;
                case "3":
                    game.rules();
                    game.restart();
                    break;
                case "4":
                    System.out.println("See you next time");
                    game.aboutAuthor();
                    System.exit(0);

            }
    }


}
