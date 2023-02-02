package com.cs3343.Hangman;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class AutoCorrect {
    public static List<String> wordList = new ArrayList<>();
    public static int closest = Integer.MAX_VALUE;
    String acWord = "";
    Node root;

    public static void listMaker(List<String> wordList) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("words_alpha.txt"));
        while (scanner.hasNext()) {
            wordList.add(scanner.nextLine());
        }
    }

    public static int calculateDistance(String x, String y) {
        int[][] dp = new int[x.length() + 1][y.length() + 1];

        for (int i = 0; i <= x.length(); i++) {
            for (int j = 0; j <= y.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                }
                else if (j == 0) {
                    dp[i][j] = i;
                }
                else {
                    dp[i][j] = min(dp[i - 1][j - 1] + costOfSubstitution(x.charAt(i - 1), y.charAt(j - 1)), dp[i - 1][j] + 1, dp[i][j - 1] + 1);
                }
            }
        }

        return dp[x.length()][y.length()];
    }

    public static int costOfSubstitution(char a, char b) {
        return a == b ? 0 : 1;
    }

    public static int min(int... numbers) {
        return Arrays.stream(numbers).min().orElse(Integer.MAX_VALUE);
    }

    public static String bruteForceAC(String userWord, List<String> wordList) {

        String index = "";

        if (wordList.contains(userWord)) {
            System.out.println("Using Brute Force Auto correct, it says that you have typed the correct word.");
            System.out.println("Word is valid.");
            return userWord;
        } else {
            System.out.println("You have entered an invalid word. Brute Force will now Auto Correct your word.");
            for (String current : wordList) {
                int distance = calculateDistance(current, userWord);
                if (distance < closest) {
                    closest = distance;
                    index = current;
                }
                if (current.equalsIgnoreCase(userWord)) {
                    index = current;
                    userWord = index;
                }
            }
            System.out.println("Autocorrect: "+ index);
            System.out.println("Word is now valid.");
        }
        return index;
    }

    public static String decreaseByConstantAC(int lo, int hi, String userWord ) throws FileNotFoundException {
        listMaker(wordList);
        int mid = 0; String newWord = "";
        while(lo <= hi){
            mid = (lo+hi)/2;
            int closestWord = wordList.get(mid).compareTo(userWord);
            if( closestWord < 0){
                lo = mid +1;
            }else if(closestWord > 0){
                hi = mid-1;
            }else if(closestWord == 0) {
                System.out.println("Using Decrease-by-Constant Factor 2 Auto correct, it says that you have typed the correct word.");
                System.out.println("Word is valid.");
                return wordList.get(mid);
            }
        }
        System.out.println("You have entered an invalid word. Decrease-By-Constant Factor will now Auto Correct your word.");
        newWord = wordList.get(mid-1);
        System.out.println("Autocorrect: " + newWord);
        System.out.println("Word is now valid.");
        return newWord;
    }

    public Node buildBST(List<String> wordList, int lo, int hi) {

        int mid = (lo+hi)/2;
        if(lo>hi){
            return null;
        }
        Node node = new Node(wordList.get(mid));
        node.left = buildBST(wordList, lo, mid-1);
        node.right = buildBST(wordList, mid+1, hi);


        return node;
    }

    public void buildBST(List<String> wordList){
        root = buildBST(wordList, 0,  wordList.size()-1);
    }

    public String binarySearch(String userWord){
        return binarySearchq(root, userWord);
    }

    private String binarySearchq(Node node, String closetWord) {
        if (node == null) {
            System.out.println("You have entered an invalid word. Binary Search Tree will now Auto Correct your word.");
            System.out.println("Autocorrect: " + acWord);
            System.out.println("Word is now valid.");
            return acWord;
        }
        if (closetWord.compareTo(node.data) == 0) {
            System.out.println("Using Binary Search Tree Auto correct, it says that you have typed the correct word.");
            System.out.println("Word is valid.");
            return node.data;
        }
        acWord = node.data;
        if (closetWord.compareTo(node.data) > 0) {
            return binarySearchq(node.right, closetWord);
        } else {
            return binarySearchq(node.left, closetWord);
        }

    }


}







