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
public final class MockWordle implements Runnable, ActionListener {

    // Class Variables
    ArrayList<String> dictionary;
    JPanel mainPanel;
    JPanel gridPanel;
    JButton[][] grid;
    JButton instructionsButton;
    JButton statsButton;
    JLabel titleLabel;
    boolean realWord = false;
    String randomWord;
    String wordGuessed = "";
    String alphabetString = "abcdefghijklmnopqrstuvwxyz";
    int turnCounter = 0;
    int location = 0;
    int wordLength = 5;
    int lastInst = wordLength;
    
    MockWordleStatsPage stats;
    int[] statsCounter = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
     
    //variables for instructions page
    JFrame instructionsFrame;
    JPanel instructionsPanel;
    JPanel instructionsGridPanel;
    JButton[] instructionsButtons;
    JLabel instructionsTitleLabel;
    JLabel[] instructionsLabel;

    //Fonts
    Font titleFont = new Font("arial", Font.BOLD, 70);
    Font largeFont = new Font("arial", Font.BOLD, 48);
    Font smallFont = new Font("arial", Font.BOLD, 10);

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
                int key = e.getKeyCode();

                //if Lackspace is pressed
                if (key == KeyEvent.VK_BACK_SPACE) {
                    location = location - 1;
                    //keeps the location of the letter between 0 and 5
                    if (location < 0) {
                        location++;
                    }
                    letterManipulator("removeLetter", turnCounter, location);
                    wordGuessed = wordGuessed.substring(0, location);
                    //if Enter is pressed
                } else if (key == KeyEvent.VK_ENTER) {
                    //if the word entered is 5 letters
                    if (wordGuessed.length() == wordLength) {
                        wordChecker(wordGuessed);
                        frame.setFocusable(true);
                        if (!realWord) {
                            location = 0;
                        } else {
                            location = 5;
                        }
                    }

                } //if a letter is entererd
                else if (alphabetString.contains(String.valueOf(e.getKeyChar()))) {
                    String letterEntered = String.valueOf(e.getKeyChar());
                    letterEntered.toUpperCase();
                    wordGuessed = wordGuessed + letterEntered;
                    //limits word entered length to 5 letters
                    if (wordGuessed.length() > wordLength) {
                        wordGuessed = wordGuessed.substring(0, (wordLength));
                    } else {
                        letterManipulator(letterEntered, turnCounter, location);
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

        //create mainPanel
        mainPanel = new JPanel();
        mainPanel.setLayout(null);

        //create gridPanel
        gridPanel = new JPanel();
        gridPanel.setBounds(10, 80, 400, 480);
        gridPanel.setLayout(new GridLayout(wordLength + 1, wordLength));

        //create titleLabel
        titleLabel = new JLabel("WORDLE");
        titleLabel.setFont(titleFont);
        titleLabel.setBounds(60, 0, 320, 70);

        //create instrucitonsButton
        instructionsButton = new JButton("?");
        instructionsButton.setFont(smallFont);
        instructionsButton.setActionCommand("openInstructions");
        instructionsButton.addActionListener(this);
        instructionsButton.setBackground(Color.LIGHT_GRAY);
        instructionsButton.setForeground(Color.BLACK);
        instructionsButton.setBounds(0, 0, 40, 40);

//create stats button
        statsButton = new JButton("*");
        statsButton.setFont(smallFont);
        statsButton.setActionCommand("openStats");
        statsButton.addActionListener(this);
        statsButton.setBackground(Color.LIGHT_GRAY);
        statsButton.setForeground(Color.BLACK);
        statsButton.setBounds(385, 0, 40, 40);

//create button grid, set color, font and add them to gridPanel
        grid = new JButton[wordLength + 1][wordLength];
        for (int i = 0; i < wordLength + 1; i++) {
            for (int j = 0; j < wordLength; j++) {
                grid[i][j] = new JButton();
                grid[i][j].setFocusable(false);
                grid[i][j].setBackground(Color.LIGHT_GRAY);
                grid[i][j].setForeground(Color.BLACK);
                grid[i][j].setFont(largeFont);
                gridPanel.add(grid[i][j]);
            }
        }
        //add elements to panel and frame
        mainPanel.add(instructionsButton);
        mainPanel.add(statsButton);
        mainPanel.add(titleLabel);
        mainPanel.add(gridPanel);
        frame.add(mainPanel);

//create Instrucitons page
        instructionsFrame = new JFrame("Instructions");
        // makes the windows 800 pixel wide by 600 pixels tall
        instructionsFrame.setSize(940, 400);
        // shows the window
        instructionsFrame.setVisible(true);
//create main panel
        instructionsPanel = new JPanel();
        instructionsPanel.setLayout(null);
//create grid panel for buttons
        instructionsGridPanel = new JPanel();
        instructionsGridPanel.setBounds(270, 250, 400, 80);
        instructionsGridPanel.setLayout(new GridLayout(1, 5));
        //create title label
        instructionsTitleLabel = new JLabel("How To Play");
        instructionsTitleLabel.setFont(titleFont);
        instructionsTitleLabel.setBounds(260, 0, 420, 80);

        instructionsLabel = new JLabel[5];
        //create and all instructionsLabels to panel
        for (int i = 0; i < 5; i++) {
            instructionsLabel[i] = new JLabel();
            instructionsPanel.add(instructionsLabel[i]);
        }
//set text and bounds for labels
        instructionsLabel[0].setText("The goal of the game is to guess the winning FIVE letter word using clues from your guesses,");
        instructionsLabel[0].setBounds(205, 100, 530, 20);
        instructionsLabel[1].setText("You have SIX attempts per winning word.");
        instructionsLabel[1].setBounds(350, 120, 240, 20);
        instructionsLabel[2].setText("If the background is GREY, the letter in that box IS NOT in the winning word, B and N are not in the winning word");
        instructionsLabel[2].setBounds(20, 160, 800, 20);
        instructionsLabel[3].setText("If the background is YELLOW, the letter in that box IS IN the winning word but IS NOT in the corret spot, A and E are in the winning word, just not in those spots");
        instructionsLabel[3].setBounds(20, 180, 900, 20);
        instructionsLabel[4].setText("If the background is GREEN, the letter in that box IS IN the winning word & IS in the correct spot, S is in the winning word & in that spot");
        instructionsLabel[4].setBounds(20, 200, 800, 20);

        instructionsButtons = new JButton[5];
        //create buttons, set font and add them to panel
        for (int i = 0; i < 5; i++) {
            instructionsButtons[i] = new JButton();
            instructionsButtons[i].setFocusable(false);
            instructionsButtons[i].setForeground(Color.BLACK);
            instructionsButtons[i].setFont(largeFont);
            instructionsGridPanel.add(instructionsButtons[i]);

        }

        //set text and color of buttons
        instructionsButtons[0].setBackground(Color.DARK_GRAY);
        instructionsButtons[0].setText("B");
        instructionsButtons[1].setBackground(Color.YELLOW);
        instructionsButtons[1].setText("E");
        instructionsButtons[2].setBackground(Color.YELLOW);
        instructionsButtons[2].setText("A");
        instructionsButtons[3].setBackground(Color.DARK_GRAY);
        instructionsButtons[3].setText("N");
        instructionsButtons[4].setBackground(Color.GREEN);
        instructionsButtons[4].setText("S");

        //add instructions page elements to panel and frame
        instructionsPanel.add(instructionsTitleLabel);
        instructionsPanel.add(instructionsGridPanel);
        instructionsFrame.add(instructionsPanel);
    }

    //method loads dictionary of 5 letterChar words and generates first winning word
    public MockWordle() throws MalformedURLException, IOException {
        //creating stats page
        stats = new MockWordleStatsPage();
        //Loading Dictionary
        URL dictionaryFile = new URL("https://github.com/dwyl/english-words/raw/master/words_alpha.txt");
        Scanner textInput = new Scanner(dictionaryFile.openStream());
        dictionary = new ArrayList<>();
        while (textInput.hasNext()) {
            String phrase = textInput.nextLine();
            if (phrase.length() == wordLength) {
                phrase.toUpperCase();
                dictionary.add(phrase);
            }
        }
        //generating a random word
        randomWordGenerator();
    }

    //Adds and removes letters from buttons when they are typed
    public void letterManipulator(String letter, int row, int location) {
        //if letter is being removed
        if (letter.equals("removeLetter")) {
            grid[row][location].setText("");
            //if letter is being added
        } else {
            letter = letter.toUpperCase();
            grid[row][location].setText(letter);
        }
    }

    //Method generates random word from loaded dictionary
    public void randomWordGenerator() {
        int lowest = 1;
        int highest = dictionary.size();
        int randomIndex = (int) (Math.random() * (highest - lowest + 1) + lowest);
        randomWord = dictionary.get(randomIndex);
        //System.out.println(randomWord);
    }

    //Method checks word entered
    public void wordChecker(String word) {

        //checks if word is in dictionary
        if (dictionary.contains(word)) {
            realWord = false;
            //if the guess is correct
            if (word.equals(randomWord)) {
                JOptionPane.showMessageDialog(mainPanel, "You Win!");
                statsCounter[1]++;
                statsCounter[2]++;
                statsCounter[turnCounter + 3]++;
                resetGame();
            } else {
                //goes through each letter in word and checks if and where letters in word are in the winning word
                for (int i = 0; i < wordLength; i++) {
                    char letterChar = word.charAt(i);
                    String letterString = String.valueOf(letterChar);
                    //if the letter is in the winning word
                    if (randomWord.contains(letterString)) {
                        int location = randomWord.indexOf(letterChar);
                        // check if the words have more than one of the same letter and where
                        if ((word.indexOf(letterString) != word.lastIndexOf(letterString) != (randomWord.indexOf(letterString) != randomWord.lastIndexOf(letterString)))) {
                            lastInst = word.lastIndexOf(letterString);
                        }
                        //if the letter is not a the last one in a word with two of that letter
                        if (i != lastInst) {
                            //if letter is in the winning word and in the correct spot
                            if (i == location) {
                                colorChanger(letterString, i, 1);
                                //letter is in the winning word, just not in the correct spot
                            } else {
                                colorChanger(letterString, i, 2);
                            }
                        } else {
                            if (letterString.equals(letterChar)) {
                                colorChanger(letterString, i, 1);
                                //letter is in the winning word, just not in the correct spot
                            } else {
                                colorChanger(letterString, i, 0);
                            }
                        }
                        //letter is not in word
                    } else {
                        colorChanger(letterString, i, 0);
                    }
                }
                //if there are still turns left
                if (turnCounter < wordLength) {
                    turnCounter++;
                    //if player uses all turns and still loses
                } else if (turnCounter == wordLength && !word.equals(randomWord)) {
                    JOptionPane.showMessageDialog(mainPanel, "You lose, the word was: " + randomWord);
                    statsCounter[2] = 0;
                    resetGame();
                }
            }
            wordGuessed = "";
            //if word is not in dictionary
        } else {
            location = wordLength;
            JOptionPane.showMessageDialog(mainPanel, "This word is not a word, try a real word");
            realWord = true;
        }

    }

    //method changes color of buttons
    public void colorChanger(String letter, int location, int color) {
        switch (color) {
            //color needs to be changed to green
            case 1:
                grid[turnCounter][location].setText(letter.toUpperCase());
                grid[turnCounter][location].setBackground(Color.GREEN);
                break;
            //color needs to be changed to yellow
            case 2:
                grid[turnCounter][location].setText(letter.toUpperCase());
                grid[turnCounter][location].setBackground(Color.YELLOW);
                break;
            // color needs to be changed to grey
            default:
                grid[turnCounter][location].setText(letter.toUpperCase());
                grid[turnCounter][location].setBackground(Color.DARK_GRAY);
                break;
        }
    }

    //method resets the game each round
    public void resetGame() {
        //clear all squares and reset their color
        for (int i = 0; i < wordLength + 1; i++) {
            for (int j = 0; j < wordLength; j++) {
                grid[i][j].setBackground(Color.LIGHT_GRAY);
                grid[i][j].setText("");
            }
        }
        statsCounter[0]++;
        wordGuessed = "";
        turnCounter = 0;
        stats.boxLength(statsCounter);
        randomWordGenerator();
        stats.frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String command = ae.getActionCommand();
        //if instructionsButton is pressed
        if (command.equals("openInstructions")) {
            instructionsFrame.setVisible(true);
            //if statsButton is pressed
        } else if (command.equals("openStats")) {
            stats.frame.setVisible(true);
        }
    }

    // Main method to start our program
    public static void main(String[] args) throws IOException {
        // Creates an instance of our program
        MockWordle gui = new MockWordle();

        // Lets the computer know to start it in the event thread
        SwingUtilities.invokeLater(gui);
    }

}
