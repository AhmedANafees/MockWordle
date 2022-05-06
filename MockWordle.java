package MockWordle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author nafea8846
 */
public final class MockWordle implements Runnable {

    // Class Variables
    ArrayList<String> dictionary;
    JPanel mainPanel;
    JPanel gridPanel;
    JButton[][] grid;
    JLabel titleLabel;
    boolean wrongWord = true;
    String randomWord;
    String wordGuessed = "";
    String letterString = "abcdefghijklmnopqrstuvwxyz";
    int counter = 0;
    int location = 0;
    int wordLength = 5;
    int lastInst = wordLength;
    int numOfGames = 1;
    int gameStreak = 0;
    int[] winCounter = {0,0,0,0,0,0,0};
    Font Title = new Font("arial", Font.BOLD, 70);
    Font Large = new Font("arial", Font.BOLD, 50);
    Font medium = new Font("arial", Font.BOLD, 30);
    MockWordleStatsPage game = new MockWordleStatsPage();

    // Method to assemble our GUI
    @Override
    public void run() {
        // Creats a JFrame that is 800 pixels by 600 pixels, and closes when you click on the X
        JFrame frame = new JFrame("Mock Wordle");
        // Makes the X button close the program
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // makes the windows 800 pixel wide by 600 pixels tall
        frame.setSize(440, 610);
        // shows the window
        frame.setVisible(true);
        frame.setFocusable(true);
        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == ('\b')) {
                    location = location - 1;
                    while (location < 0) {
                        location++;
                    }
                    letterRemover(counter, location);
                    wordGuessed = wordGuessed.substring(0, location);
                } else if (e.getKeyCode() == 10) {
                    if (wordGuessed.length() == wordLength) {
                        wordChecker(wordGuessed);
                        frame.setFocusable(true);
                        if(wrongWord){
                            location = 0;
                        }
                        else{
                            location = 5;
                            wrongWord = true;
                        }
                    } else {
                        if (wordGuessed.length() > wordLength) {
                            JOptionPane.showMessageDialog(mainPanel, "this is not a " + wordLength + " letter word");
                        }
                    }

                } else if (letterString.contains(String.valueOf(e.getKeyChar()))) {
                    String letterEntered = String.valueOf(e.getKeyChar());
                    wordGuessed = wordGuessed + letterEntered;
                    if (wordGuessed.length() > wordLength) {
                        wordGuessed = wordGuessed.substring(0, (wordLength));
                    } else {
                        System.out.println(wordGuessed);
                        letterPlacer(letterEntered, counter, location);
                        location++;
                    }

                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }

            @Override
            public void keyTyped(KeyEvent e) {
            }
        });

        mainPanel = new JPanel();
        mainPanel.setLayout(null);

        gridPanel = new JPanel();
        gridPanel.setBounds(10, 80, 400, 480);
        gridPanel.setLayout(new GridLayout(wordLength + 1, wordLength));

        titleLabel = new JLabel("WORDLE");
        titleLabel.setFont(Title);
        titleLabel.setBounds(60, 0, 320, 70);

        grid = new JButton[wordLength + 1][wordLength];
        for (int i = 0; i < wordLength + 1; i++) {
            for (int j = 0; j < wordLength; j++) {
                grid[i][j] = new JButton();
                grid[i][j].setFocusable(false);
                grid[i][j].setBackground(Color.LIGHT_GRAY);
                grid[i][j].setForeground(Color.BLACK);
                grid[i][j].setFont(Large);
                gridPanel.add(grid[i][j]);
            }
        }
        mainPanel.add(titleLabel);
        mainPanel.add(gridPanel);
        frame.add(mainPanel);
    }

    public void letterPlacer(String letter, int row, int location) {
        grid[row][location].setText(letter);
    }

    public void letterRemover(int row, int column) {
        grid[row][column].setText("");
    }

    //method loads dictionary of 5 letterChar words
    public MockWordle() throws MalformedURLException, IOException {
        URL dictionaryFile = new URL("https://github.com/dwyl/english-words/raw/master/words_alpha.txt");
        Scanner textInput = new Scanner(dictionaryFile.openStream());
        dictionary = new ArrayList<>();
        while (textInput.hasNext()) {
            String phrase = textInput.nextLine();
            if (phrase.length() == wordLength) {
                dictionary.add(phrase);
            }
        }
        randomWordGenerator();
    }

    //Method generates random word from loaded dictionary
    public void randomWordGenerator() {
        int lowest = 1;
        int highest = dictionary.size();
        int randomIndex = (int) (Math.random() * (highest - lowest + 1) + lowest);
        randomWord = dictionary.get(randomIndex);
        System.out.println(randomWord);
    }

    //Method checks word entered
    public void wordChecker(String word) {

        //checks if word is in dictionary
        if (dictionary.contains(word)) {
            //check if guessed word is the same as the random word
            if (word.equals(randomWord)) {
                JOptionPane.showMessageDialog(mainPanel, "You Win!");
                gameStreak++;
                winCounter[0]++;
                winCounter[counter+1]++;
                game.boxLength(winCounter, gameStreak, numOfGames);
                game.frame.setVisible(true);
                resetGame();
            } else {
                //check if and where letters in gussed word are in random word
                for (int i = 0; i < wordLength; i++) {
                    char letterChar = word.charAt(i);
                    String letterString = String.valueOf(letterChar);
                    if (randomWord.contains(letterString)) {
                        int location = randomWord.indexOf(letterChar);
                        if ((word.indexOf(letterString) != word.lastIndexOf(letterString) != (randomWord.indexOf(letterString) != randomWord.lastIndexOf(letterString)))) {
                            lastInst = word.lastIndexOf(letterString);
                        }
                        if (i != lastInst) {
                            if (i == location) {
                                colorChanger(letterString, i, 1);
                            } else {
                                colorChanger(letterString, i, 2);
                            }
                        }
                    } else {
                        colorChanger(letterString, i, 0);
                    }
                }
                if (counter < wordLength) {
                    counter++;
                } else if (counter == wordLength && !word.equals(randomWord)) {
                    JOptionPane.showMessageDialog(mainPanel, "You lose, the word was: " + randomWord);
                    gameStreak = 0;
                    game.boxLength(winCounter, gameStreak, numOfGames);
                    game.frame.setVisible(true);
                    resetGame();
                }
            }
            wordGuessed = "";
        } else {
            location = wordLength;
            JOptionPane.showMessageDialog(mainPanel, "This word is not in the dictonary, try another word");
            wrongWord = false;
        }

    }

    //method changes color of buttons
    public void colorChanger(String letter, int location, int color) {
        switch (color) {
            case 1:
                grid[counter][location].setText(letter);
                grid[counter][location].setBackground(Color.GREEN);
                break;

            case 2:
                grid[counter][location].setText(letter);
                grid[counter][location].setBackground(Color.YELLOW);
                break;

            default:
                grid[counter][location].setText(letter);
                grid[counter][location].setBackground(Color.LIGHT_GRAY);
                break;
        }
    }

    //method resets game
    public void resetGame() {
        for (int i = 0; i < wordLength + 1; i++) {
            for (int j = 0; j < wordLength; j++) {
                grid[i][j].setBackground(Color.LIGHT_GRAY);
                grid[i][j].setText("");
            }
        }
        numOfGames++;
        counter = 0;
        wordGuessed = "";
        randomWordGenerator();
    }

    // Main method to start our program
    public static void main(String[] args) throws IOException {
        // Creates an instance of our program
        MockWordle gui = new MockWordle();

        // Lets the computer know to start it in the event thread
        SwingUtilities.invokeLater(gui);
    }

}
