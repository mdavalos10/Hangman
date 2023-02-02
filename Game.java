package com.cs3343.Hangman;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Game {

    static String word;
    public static List<String> wordList = new ArrayList<>();
    static String userWord;
    static int wrongCount;
    public static List<String> letters;
    public static List<String> usedLetter = new ArrayList<>();
    public static boolean gameWon = false;
    static Game game = new Game();
    private static Node root;



    public static void singlePlayer() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("words_alpha.txt"));

        while (scanner.hasNext()) {
            wordList.add(scanner.nextLine());
        }

        Random rand = new Random();
        word = wordList.get(rand.nextInt(wordList.size()));

        play1(word);
    }

    public static void multiPlayer() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("words_alpha.txt"));
        wordList = new ArrayList<>();

        while (scanner.hasNext()) {
            wordList.add(scanner.nextLine());
        }

        Scanner x = new Scanner(System.in);
        System.out.println("Player 1, please enter your word:");
        userWord = x.nextLine();
        Scanner h = new Scanner(System.in);

        System.out.println("Which autocorrect function would you like to use");
        System.out.println("    Type [1] for Brute Force");
        System.out.println("    Type [2] for Decrease-by-Constant Factor 2");
        System.out.println("    Type [3] for Binary Search Tree");
        int autoCorrectType = h.nextInt();
        AutoCorrect autoCorrect = new AutoCorrect();
        String newWord = "";
        switch (autoCorrectType){
            case 1:
                System.out.println("------------Brute Force-------------");
                newWord = autoCorrect.bruteForceAC(userWord, wordList);
                break;
            case 2:
                System.out.println("----Decrease-by-Constant Factor-----");
                newWord = autoCorrect.decreaseByConstantAC(0, wordList.size()-1, userWord);
                break;
            case 3:
                System.out.println("---------Binary-Search Tree---------");
                autoCorrect.buildBST(wordList);
                newWord = autoCorrect.binarySearch(userWord);
                break;
        }

        play2(newWord);

    }

    public static void printHangman(int mistakes){
        System.out.println();
        switch (mistakes) {
            case 1:
                System.out.println(" --------|");
                System.out.println("     |   O");
                System.out.println("     |");
                System.out.println("     |");
                System.out.println("_____|_____");
                System.out.println("One wrong");
                break;
            case 2:
                System.out.println(" --------|");
                System.out.println("     |   O");
                System.out.println("     |  /");
                System.out.println("     |");
                System.out.println("_____|_____");
                System.out.println("Two wrong");
                break;
            case 3:

                System.out.println(" --------|");
                System.out.println("     |   O");
                System.out.println("     |  /|");
                System.out.println("     |      ");
                System.out.println("_____|_____");
                System.out.println("Three wrong");
                break;
            case 4:
                System.out.println(" --------|");
                System.out.println("     |   O");
                System.out.println("     |  /|\\");
                System.out.println("     |      ");
                System.out.println("_____|_____");
                System.out.println("Four wrong");
                break;
            case 5:
                System.out.println(" --------|");
                System.out.println("     |   O");
                System.out.println("     |  /|\\");
                System.out.println("     |  /   ");
                System.out.println("_____|_____");
                System.out.println("Five wrong");
                System.out.println("This is your last chance");
                break;
            case 6:
                System.out.println(" --------|");
                System.out.println("     |   O");
                System.out.println("     |  /|\\");
                System.out.println("     |  / \\");
                System.out.println("_____|_____");
                System.out.println("Six wrong");
                break;
            default:
                System.out.println(" --------|");
                System.out.println("     |");
                System.out.println("     |");
                System.out.println("     |");
                System.out.println("_____|_____");
                System.out.println("Zero wrong");
                break;
        }
        System.out.println();
        System.out.println("------------------------------------------------------------------");
    }

    public static void play1(String word){
//        System.out.println(word);
        System.out.println("Word has been chosen");
        System.out.println("Here are the list of letters you have");
        characterList();
        String[] wordLetters = new String[word.length()];
        for(int i = 0; i < wordLetters.length; i++){
            String addLetter = "" + word.charAt(i);
            wordLetters[i] = addLetter;
        }
        String[] lettersGuessedArray = new String[word.length()];
        for(int i = 0; i < lettersGuessedArray.length; i++){
            lettersGuessedArray[i] = "False";
        }

        while(wrongCount < 6 && gameWon != true){
            System.out.println();
            Scanner scan = new Scanner(System.in);
            System.out.print("Enter guess: ");
            String Userguess = scan.nextLine();
            letters.remove(Userguess);

            usedLetter.add(Userguess);

            if(Userguess.equalsIgnoreCase("quit")){
                System.out.println("Looks like you have quit the game. See you next time.");
                System.exit(0);
            }
            boolean isGuessInWord = checkUserGuess(wordLetters, lettersGuessedArray, Userguess);
            if(isGuessInWord == false){
                wrongCount++;
            }

            game.printHangman(wrongCount);
            System.out.println("Letters available: " + letters);
            checkWord(wordLetters, lettersGuessedArray);
            chosenLetter(Userguess);
            System.out.println();
            gameWon = checkWise(lettersGuessedArray);
            if(gameWon != true ){
                System.out.println("Do you think you know the word. If so, would you like to guess Y|N");
                String fullwordGuess = scan.nextLine();
                if(fullwordGuess.equalsIgnoreCase("quit")){
                    System.out.println("Looks like you have quit the game. See you next time.");
                    System.exit(0);
                }
                if(fullwordGuess.equalsIgnoreCase("Y")){
                    System.out.println("Enter your guess for the word: ");
                    String wildGuess = scan.nextLine();
                    if(wildGuess.equalsIgnoreCase(word)){
                        System.out.println("You guessed CORRECTLY!");
                        gameWon = true;
                        break;
                    }else{
                        System.out.println("That is not the word");
                    }
                }else if(fullwordGuess.equalsIgnoreCase("N")){
                    continue;
                }
            }
        }
        System.out.println();
        if(gameWon == true && wrongCount<6){
            System.out.println("You won the game! :)");
        }if(gameWon == false && wrongCount >= 6){
            System.out.println("Sorry, you lost the game. :(");
            System.out.println("The word was " + word);
        }
    }

    public static void play2(String userWord){
//        System.out.println(userWord);
//        System.out.println("Word is valid.");

        System.out.println("Here are the list of letters you have");
        characterList();
        String[] wordLetters = new String[userWord.length()];
        for(int i = 0; i < wordLetters.length; i++){
            String addLetter = "" + userWord.charAt(i);
            wordLetters[i] = addLetter;
        }
        String[] lettersGuessedArray = new String[userWord.length()];
        for(int i = 0; i < lettersGuessedArray.length; i++){
            lettersGuessedArray[i] = "False";
        }

        while(wrongCount < 6 && gameWon != true){
            System.out.println();
            Scanner scan = new Scanner(System.in);
            System.out.print("Enter guess: ");
            String Userguess = scan.nextLine();
            letters.remove(Userguess);
            usedLetter.add(Userguess);

            if(Userguess.equalsIgnoreCase("quit")){
                System.out.println("Looks like you have quit the game. See you next time.");
                System.exit(0);
            }
            boolean isGuessInWord = checkUserGuess(wordLetters, lettersGuessedArray, Userguess);
            if(isGuessInWord == false){
                wrongCount++;
            }

            printHangman(wrongCount);
            System.out.println("Letters available " + letters);
            checkWord(wordLetters, lettersGuessedArray);
            chosenLetter(Userguess);
            System.out.println();
            gameWon = checkWise(lettersGuessedArray);
            if(gameWon != true ){
                System.out.println("Do you think you know the word. If so, would you like to guess Y|N");
                String makeGuess = scan.nextLine();
                if(makeGuess.equalsIgnoreCase("quit")){
                    System.out.println("Looks like you have quit the game. See you next time.");
                    System.exit(0);
                }
                if(makeGuess.equalsIgnoreCase("Y")){
                    System.out.println("Enter your guess for the word: ");
                    String wildGuess = scan.nextLine();
                    if(wildGuess.equalsIgnoreCase(userWord)){
                        System.out.println("You guessed CORRECTLY!");
                        gameWon = true;
                        break;
                    }else{
                        System.out.println("That is not the word");
                    }
                }else if(makeGuess.equalsIgnoreCase("N")){
                    continue;
                }
            }
        }

        System.out.println();
        if(gameWon == true && wrongCount<6){
            System.out.println("You won the game! :)");
            gameWon = false;
        }if(gameWon == false && wrongCount >= 6){
            System.out.println("Sorry, You lost the game :( ");
        }
    }


    public static void restart() throws FileNotFoundException, InterruptedException {
        wrongCount = 0;
        gameWon = false;
        usedLetter.removeAll(usedLetter);
        Scanner scan = new Scanner(System.in);
        System.out.println("Would you like to play again");
        System.out.println("    Type [1] for SINGLE PLAYER");
        System.out.println("    Type [2] for MULTIPLAYER");
        System.out.println("    Type [3] for RULES");
        System.out.println("    Type [4] for QUIT");
        String x = scan.nextLine();
        switch (x) {
            case "1":
                System.out.println("You have chosen to play against the Computer. The Computer will now choose a word");
                singlePlayer();
                restart();
                break;
            case "2":
                System.out.println("You have chosen to play against a friend.");
                multiPlayer();
                restart();
                break;
            case "3":
                rules();
                restart();
                break;
            case "4":
                System.out.println("See you next time");
                aboutAuthor();
                System.exit(0);

        }
    }

    public static void rules() {
        System.out.println("Here are the Rules");
        System.out.println("1. Hangman is a word game where the goal is simply try to find the missing word or words.");
        System.out.println("2. You will be presented with a number of blank spaces representing the missing letters you need to find.");
        System.out.println("3. Use the keyboard to guess a letter (I recommend starting with vowels).");
        System.out.println("4. If your chosen letter exists in the answer, then all places in the answer where that letter appear will be revealed.");
        System.out.println("5. After you've revealed several letters, you may be able to guess what the answer is and fill in the remaining letters.");
        System.out.println("6. Be warned, every time you guess a letter wrong you loose a life and the hangman begins to appear, piece by piece.");
        System.out.println("7. You will have 6 chances to get the full word and save the man getting hanged!!!!!");
        System.out.println("8. Solve the puzzle before the hangman dies.");
        System.out.println("");
    }

    public static void aboutAuthor(){
        System.out.println("Creator: Mateo Davalos");
        System.out.println("Date Created: 12-19-2022");
        System.out.println("Assigned: Professor Rusu");
    }

    public static void checkWord(String [] wordLetter, String[] boolArray){
        for(int i = 0; i < wordLetter.length; i++){
            if(boolArray[i].equals("False")){
                System.out.print("_");
            }else{
                System.out.print(wordLetter[i]);
            }
        }
    }

    public static void characterList() {
        String[] letterStrings = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
                "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        letters = new ArrayList<>();
        for(int i = 0; i < letterStrings.length; i++){
            letters.add(letterStrings[i]);
        }
        System.out.println(letters);
    }

    public static boolean checkUserGuess(String[] wordLetters, String[] boolArray, String guess){
        boolean guessCorrect = false;
        for(int i = 0; i < wordLetters.length; i++){
            if(wordLetters[i].equalsIgnoreCase(guess)){
                boolArray[i] = "True";
                guessCorrect = true;
            }
        }
        return guessCorrect;
    }

    public static boolean checkWise(String[] boolArray){
        boolean wonGame = false;
        int lettersGuessedTotal = 0;
        int correctTotal = boolArray.length;

        for(int i = 0; i < boolArray.length; i++){
            if(boolArray[i].equals("True")){
                lettersGuessedTotal += 1;
            }
        }
        if(lettersGuessedTotal == correctTotal){
            wonGame = true;
        }
        return wonGame;
    }



    public static void chosenLetter(String Userguess){
        Collections.sort(usedLetter);
        for (int i = 0; i < usedLetter.size()-1; i++) {
            if(usedLetter.get(i+1)==null){
                continue;
            }
            if (usedLetter.get(i).equalsIgnoreCase(Userguess) && usedLetter.get(i+1).equalsIgnoreCase(Userguess)){
                System.out.println("    You chose "  + Userguess + " already.");
                break;
            }
        }
    }


}
