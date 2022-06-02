package MockWordle;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.JFrame;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import javax.swing.Timer;

/**
 *
 * @author nafea8846
 */
public class MockWordleStatsPage extends JComponent implements ActionListener {

    // Height and Width of our game
    static final int WIDTH = 400;
    static final int HEIGHT = 400;

    //Title of the window
    String title = "Your Stats";

    // sets the framerate and delay for our game
    // this calculates the number of milliseconds per frame
    // you just need to select an approproate framerate
    int desiredFPS = 60;
    int desiredTime = Math.round((1000 / desiredFPS));
    
    // timer used to run the game loop
    // this is what keeps our time running smoothly :)
    Timer gameTimer;

    // YOUR GAME VARIABLES WOULD GO HERE
    Font titleFont = new Font("arial", Font.BOLD, 70);
    Font mediumFont = new Font("arial", Font.BOLD, 30);
    
    // GAME VARIABLES END HERE    
    int gamesPlayed = 0;
    int winStreak = 0;
    int winCounterInt = 0;
    int[] turnCounterInt = new int[]{0,0,0,0,0,0};
    double winCounterDouble = 0;
    double[] turnCounterDouble = new double[]{0,0,0,0,0,0};
    
    // creates a windows to show my game
        JFrame frame = new JFrame(title);
        
    // Constructor to create the Frame and place the panel in
    // You will learn more about this in Grade 12 :)
    public MockWordleStatsPage(){
        

        // sets the size of my game
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        // adds the game to the window
        frame.add(this);

        // sets some options and size of the window automatically
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.pack();
        // shows the window to the user
        frame.setVisible(false);

        // add listeners for keyboard and mouse
        frame.addKeyListener(new Keyboard());
        Mouse m = new Mouse();
        this.addMouseMotionListener(m);
        this.addMouseWheelListener(m);
        this.addMouseListener(m);
        
        // Set things up for the game at startup
        setup();

       // Start the game loop
        gameTimer = new Timer(desiredTime,this);
        gameTimer.setRepeats(true);
        gameTimer.start();
    }

    // drawing of the game happens in here
    // we use the Graphics object, g, to perform the drawing
    // NOTE: This is already double buffered!(helps with framerate/speed)
    @Override
    public void paintComponent(Graphics g) {
        // always clear the screen first!
        g.clearRect(0, 0, WIDTH, HEIGHT);

        // GAME DRAWING GOES HERE
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(10, 10, WIDTH - 20, HEIGHT - 20);
        g.setColor(Color.BLACK);
        g.setFont(titleFont);
        g.drawString("STATS", 75, 75);
        g.setFont(mediumFont);
        g.drawString(String.valueOf(gamesPlayed), 45, 125);
        g.drawString("Plays",30, 155);
        g.drawString(String.valueOf(winCounterInt), 150, 125);
        g.drawString("Win%", 135 ,155);
        g.drawString(String.valueOf(winStreak), 285, 125);
        g.drawString("Win Streak", 230, 155);
        g.drawString("1", 20, 205);
        g.fillRect(40, 180, turnCounterInt[0], 25);
        g.drawString("2", 20, 240);
        g.fillRect(40, 215,turnCounterInt[1], 25);
        g.drawString("3", 20, 275);
        g.fillRect(40, 250,turnCounterInt[2], 25);
        g.drawString("4", 20, 310);
        g.fillRect(40, 285,turnCounterInt[3], 25);
        g.drawString("5", 20, 345);
        g.fillRect(40, 320,turnCounterInt[4], 25);
        g.drawString("6", 20, 380);
        g.fillRect(40, 355, turnCounterInt[5], 25);
        // GAME DRAWING ENDS HERE
    }
    public void boxLength(int[] statsCounter){
        gamesPlayed = statsCounter[0];
        winCounterDouble = statsCounter[1];
        winStreak = statsCounter[2];
        //for each trun get what percentage of the wins it has and make that the percentage of the rectange that is filled
        for (int i = 0; i < turnCounterDouble.length; i++) {
            turnCounterDouble[i] = statsCounter[i+3];
            turnCounterDouble[i] = turnCounterDouble[i]/winCounterDouble*340;
            turnCounterInt[i] = (int)turnCounterDouble[i];
        }
        //calculate win percentage
        winCounterDouble = winCounterDouble/gamesPlayed*100;
        winCounterInt = (int)winCounterDouble;
    }

    // This method is used to do any pre-setup you might need to do
    // This is run before the game loop begins!
    public void setup() {
        // Any of your pre setup before the loop starts should go here
    }

    // The main game loop
    // In here is where all the logic for my game will go
    public void loop() {
        
    }

    // Used to implement any of the Mouse Actions
    private class Mouse extends MouseAdapter {

        // if a mouse button has been pressed down
        @Override
        public void mousePressed(MouseEvent e) {

        }

        // if a mouse button has been released
        @Override
        public void mouseReleased(MouseEvent e) {

        }

        // if the scroll wheel has been moved
        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {

        }

        // if the mouse has moved positions
        @Override
        public void mouseMoved(MouseEvent e) {

        }
    }

    // Used to implements any of the Keyboard Actions
    private class Keyboard extends KeyAdapter {

        // if a key has been pressed down
        @Override
        public void keyPressed(KeyEvent e) {

        }

        // if a key has been released
        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        loop();
        repaint();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // creates an instance of my game
        MockWordleStatsPage game = new MockWordleStatsPage();
        
    }
}