package MockWordle;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author nafea8846
 */
public class MockWordleInstructionsPage implements Runnable, ActionListener {

    // Class Variables
    JFrame instructionsFrame;
    JPanel instructionsPanel;
    JPanel instructionsGridPanel;
    JButton[] instructionsButtons;
    JLabel instructionsTitleLabel;
    JLabel[] instructionsLabel;

    Font Title = new Font("arial", Font.BOLD, 70);
    Font Large = new Font("arial", Font.BOLD, 48);
    // Method to assemble our GUI

    @Override
    public void run() {
        // Creats a JFrame that is 800 pixels by 600 pixels, and closes when you click on the X
        instructionsFrame = new JFrame("Instructions");
        // makes the windows 800 pixel wide by 600 pixels tall
        instructionsFrame.setSize(940, 400);
        // shows the window
        instructionsFrame.setVisible(true);

        instructionsPanel = new JPanel();
        instructionsPanel.setLayout(null);

        instructionsGridPanel = new JPanel();
        instructionsGridPanel.setBounds(270, 250, 400, 80);
        instructionsGridPanel.setLayout(new GridLayout(1, 5));

        instructionsTitleLabel = new JLabel("How To Play");
        instructionsTitleLabel.setFont(Title);
        instructionsTitleLabel.setBounds(260, 0, 420, 80);

        instructionsLabel = new JLabel[5];
        for (int i = 0; i < 5; i++) {
            instructionsLabel[i] = new JLabel();
            switch (i) {
                case 0:
                    instructionsLabel[0].setText("The goal of the game is to guess the winning FIVE letter word using clues from your guesses,");
                    instructionsLabel[0].setBounds(205, 100, 530, 20);
                    break;
                case 1:
                    instructionsLabel[i].setText("You have SIX attempts per winning word.");
                    instructionsLabel[i].setBounds(350, 120, 240, 20);
                    break;
                case 2:
                    instructionsLabel[i].setText("If the background is GREY, the letter in that box IS NOT in the winning word, B and N are not in the winning word");
                    instructionsLabel[i].setBounds(20, 160, 800, 20);
                    break;
                case 3:
                    instructionsLabel[i].setText("If the background is YELLOW, the letter in that box IS IN the winning word but IS NOT in the corret spot, A and E are in the winning word, just not in those spots");
                    instructionsLabel[i].setBounds(20, 180, 900, 20);
                    break;
                case 4:
                    instructionsLabel[i].setText("If the background is GREEN, the letter in that box IS IN the winning word & IS in the correct spot, S is in the winning word & in that spot");
                    instructionsLabel[i].setBounds(20, 200, 800, 20);
                    break;

                default:
                    break;
            }

            instructionsPanel.add(instructionsLabel[i]);
        }

        instructionsButtons = new JButton[5];
        for (int i = 0; i < 5; i++) {
            instructionsButtons[i] = new JButton();
            instructionsButtons[i].setFocusable(false);
            instructionsButtons[i].setForeground(Color.BLACK);
            instructionsButtons[i].setFont(Large);
            switch (i) {
                case 0:
                    instructionsButtons[i].setBackground(Color.DARK_GRAY);
                    instructionsButtons[i].setText("B");
                    break;
                case 1:
                    instructionsButtons[i].setBackground(Color.YELLOW);
                    instructionsButtons[i].setText("E");
                    break;
                case 2:
                    instructionsButtons[i].setBackground(Color.YELLOW);
                    instructionsButtons[i].setText("A");
                    break;
                case 3:
                    instructionsButtons[i].setBackground(Color.DARK_GRAY);
                    instructionsButtons[i].setText("N");
                    break;
                case 4:
                    instructionsButtons[i].setBackground(Color.GREEN);
                    instructionsButtons[i].setText("S");
                    break;
                default:
                    break;

            }

            instructionsGridPanel.add(instructionsButtons[i]);

        }
        instructionsPanel.add(instructionsTitleLabel);
        instructionsPanel.add(instructionsGridPanel);
        instructionsFrame.add(instructionsPanel);

    }

    // method called when a button is pressed
    public void actionPerformed(ActionEvent e) {
        // get the command from the action
        String command = e.getActionCommand();

    }

    // Main method to start our program
    public static void main(String[] args) {
        // Creates an instance of our program
        MockWordleInstructionsPage gui = new MockWordleInstructionsPage();
        // Lets the computer know to start it in the event thread
        SwingUtilities.invokeLater(gui);
    }
}
