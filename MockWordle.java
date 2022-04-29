
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
public class MockWordle implements Runnable, ActionListener {

    // Class Variables
    ArrayList<String> dictionary;
    int wordLength = 6;
    JPanel mainPanel;
    JPanel buttonPanel;
    JButton[] grid;
    JButton submitButton;
    JTextField inputTextField;
    JLabel titleLabel;
    String randomWord;
    Font Title = new Font("arial", Font.BOLD, 70);
    Font Large = new Font("arial", Font.BOLD, 50);
    Font medium = new Font("arial", Font.BOLD, 30);
    
    // Method to assemble our GUI
    @Override
    public void run() {
        // Creats a JFrame that is 800 pixels by 600 pixels, and closes when you click on the X
        JFrame frame = new JFrame("Mock Wordle");
        // Makes the X button close the program
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // makes the windows 800 pixel wide by 600 pixels tall
        frame.setSize(800, 400);
        // shows the window
        frame.setVisible(true);
        
        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        
        buttonPanel = new JPanel();
        buttonPanel.setBounds(0, 205, 800, 160);
        buttonPanel.setLayout(new GridLayout(1,5));
        
        
        titleLabel = new JLabel("WORDLE");
        titleLabel.setFont(Title);
        titleLabel.setBounds(225,0, 350, 70);
        
        
        submitButton = new JButton("Submit");
        submitButton.setFont(medium);
        submitButton.setBounds(455,100, 150, 52);
        submitButton.addActionListener(this);
        submitButton.setActionCommand("Submit");
        
        inputTextField = new JTextField();
        inputTextField.setFont(Large);
        inputTextField.setBounds(195, 100, 250,52);
        
        grid = new JButton[wordLength];
        for (int i = 0; i < grid.length; i++) {
            grid[i] = new JButton();
            grid[i].setBackground(Color.LIGHT_GRAY);
            grid[i].setForeground(Color.BLACK);
            grid[i].setFont(Large);
            buttonPanel.add(grid[i]);
        }
        mainPanel.add(titleLabel);
        mainPanel.add(submitButton);
        mainPanel.add(inputTextField);
        mainPanel.add(buttonPanel);
        frame.add(mainPanel);
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
    }
    //Method checks word entered
    public void wordChecker (String word){
        //checks if word is in dictionary
        if (dictionary.contains(word)){
            //check if guessed word is the same as the random word
            if(word.equals(randomWord)){
                JOptionPane.showMessageDialog(mainPanel,"You Win!");
                resetGame();
            }else{
                //check if and where letters in gussed word are in random word
                for (int i = 0; i < wordLength; i++) {
                    char letterChar = word.charAt(i);
                    String letterString = String.valueOf(letterChar);
                    if(randomWord.contains(letterString)){
                        int location = randomWord.indexOf(letterChar);
                        if(i == location){
                            colorChanger(letterString, i, 1);
                        }else{
                            colorChanger(letterString, i, 2);
                        }
                    }else{
                        colorChanger(letterString, i, 0);
                    }
                }
            }
        }else{
            JOptionPane.showMessageDialog(mainPanel,"This word is not in the dictonary, try another word");
            inputTextField.setText("");
            inputTextField.requestFocusInWindow();
        }
    }
    //method changes color of buttons
    public void colorChanger(String letter, int location, int color){
        switch(color){
            case 1:
                grid[location].setText(letter);
                grid[location].setBackground(Color.GREEN);
                break;
            
            case 2:
                grid[location].setText(letter);
                grid[location].setBackground(Color.YELLOW);
                break;
            
            default:
                grid[location].setText(letter);
                grid[location].setBackground(Color.LIGHT_GRAY);
                break;
        }
    }
    //method resets game
    public void resetGame(){
        for (int i = 0; i < wordLength; i++) {
            grid[i].setBackground(Color.LIGHT_GRAY);
            grid[i].setText("");
        }
        inputTextField.setText("");
        randomWordGenerator();
    }
    // method called when a button is pressed
    @Override
    public void actionPerformed(ActionEvent e) {
        // get the command from the action
        String command = e.getActionCommand();
        if (command.equals("Submit")){
            String guessedWord = inputTextField.getText();
            guessedWord = guessedWord.toLowerCase();
            wordChecker(guessedWord);
            inputTextField.setText("");
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
