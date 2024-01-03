package test.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;


/**
 * App class, it contains all the elements needed for the game.
 */
public class MineSweeper extends JPanel{

    /**
     * Private static instance of the class.
     * For singelton use
     */
    private static MineSweeper instance;

    /**
     * Component with minesweeper board/grid
     */
    private MineField mineField;
    /**
     * Button to reset the game
     */
    private JButton resetButton;
    /**
     * Button to pasuse the game
     */
    private JButton pauseButton;
    /**
     * Button to exit the game
     */
    private JButton exitButton;
    /**
     * Count of enlapsed seconds on the match
     */
    private long enlapsedSeconds;
    /**
     * Number of mines in the game
     */
    private int mineCount;
    /**
     * Maximum number of flags to be use in the game
     */
    private int flaggedCount;
    /*
     * Size for the board
     */
    private int fieldSize;

    /**
     * Constructs a new instance of the game.
     * 
     * @param size  Size of the minefield (size X size).
     */
    public MineSweeper(int size){
        //super("Mine Sweeper (" + size + "x" + size + ")");
        this.fieldSize = size;
        this.setBackground(Color.GRAY);
        setLayout(new BorderLayout());
        this.mineCount = 18;
        this.flaggedCount = 0;
        this.mineField = new MineField(this.fieldSize, this.mineCount);
        add(mineField, BorderLayout.WEST);
        this.enlapsedSeconds = 0;

        resetButton = new JButton("Reset");
        resetButton.setBackground(new Color(249, 178, 51, 234));
        resetButton.setFocusable(false);
        resetButton.setPreferredSize(new Dimension(60, 40));
        resetButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                mineField.reset();
                enlapsedSeconds = 0;
            }
            
        });
        add(resetButton, BorderLayout.SOUTH);
        

        
        JPanel sidePanel = new JPanel(new GridLayout(5, 1));
        // Side flags counter 
        ImageIcon flagIcon = new ImageIcon(Cell.class.getClassLoader().getResource("flag_30px.png"));
        JLabel flagLabel = new JLabel(flagIcon);
        flagLabel.setVerticalAlignment(SwingConstants.BOTTOM);
        JLabel usedFlags = new JLabel(this.flaggedCount + "/" + this.mineCount);
        usedFlags.setHorizontalAlignment(SwingConstants.CENTER);
        usedFlags.setVerticalAlignment(SwingConstants.NORTH);
        // Side chronometer
        ImageIcon chrIcon = new ImageIcon(Cell.class.getClassLoader().getResource("chrono_30px.png"));
        JLabel chronoLabel = new JLabel(chrIcon);
        chronoLabel.setVerticalAlignment(SwingConstants.BOTTOM);
        JLabel enlapsedTime = new JLabel(MineSweeper.secondsToTime(enlapsedSeconds));
        enlapsedTime.setHorizontalAlignment(SwingConstants.CENTER);
        enlapsedTime.setVerticalAlignment(SwingConstants.NORTH);
        // Side personal best
        ImageIcon podiumIcon = new ImageIcon(Cell.class.getClassLoader().getResource("podium_30px.png"));
        JLabel podiumLabel = new JLabel(podiumIcon);
        JLabel topThree = new JLabel("00:34:12");
        topThree.setHorizontalAlignment(SwingConstants.CENTER);
        topThree.setVerticalAlignment(SwingConstants.NORTH);
        
        // Side components placement
        sidePanel.setPreferredSize(new Dimension(80, 200));
        JPanel chronoPanel = new JPanel(new GridLayout(2,1));
        chronoPanel.add(chronoLabel);
        chronoPanel.add(enlapsedTime);
        sidePanel.add(chronoPanel);
        JPanel podiumPanel = new JPanel(new GridLayout(2,1));
        podiumPanel.add(podiumLabel);
        podiumPanel.add(topThree);
        sidePanel.add(podiumPanel);
        JPanel flagPanel = new JPanel(new GridLayout(2,1));
        flagPanel.add(flagLabel);
        flagPanel.add(usedFlags);
        sidePanel.add(flagPanel);
        

        // Game timer
        Timer timer = new Timer(1000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                enlapsedSeconds++;
                enlapsedTime.setText(secondsToTime(enlapsedSeconds));
                usedFlags.setText(mineField.getFlaggedCount() + "/" + mineCount);
            }
            
        });
        timer.start();

        // Pause button actions
        pauseButton = new JButton("Pause");
        pauseButton.setBackground(new Color(249, 179, 0, 234));
        pauseButton.setFocusable(false);
        pauseButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                timer.stop();
                JOptionPane.showMessageDialog(mineField, "Resume!!!", "Game Paused", JOptionPane.INFORMATION_MESSAGE);
                timer.start();
            }
            
        });

        // Exit button actions
        exitButton = new JButton("Exit");
        exitButton.setBackground(new Color(178, 20, 20, 234));
        exitButton.setFocusable(false);
        sidePanel.add(pauseButton);
        sidePanel.add(exitButton);

        add(sidePanel, BorderLayout.EAST);
    }

    /**
     * Singelton get instance method
     * @return Instance of this class
     */
    public static MineSweeper getInstance(){
        if(instance == null)
            instance = new MineSweeper(32);
        return instance;
    }

    /**
     * Transform seconds in to time friendly format
     * @param totalSeconds number of seconds
     * @return time in friendly format
     */
    private static String secondsToTime(long totalSeconds){
        // Create a Duration object
        Duration duration = Duration.ofSeconds(totalSeconds);

        // Extract hours, minutes, and seconds from the Duration
        long hours = duration.toHours();
        long minutes = duration.minusHours(hours).toMinutes();
        long seconds = duration.minusHours(hours).minusMinutes(minutes).getSeconds();

        // Format the result as a string
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    /**
     * Setter for enlapsedSeconds
     * @param s value to set enlapsedSeconds
     */
    public void setEnlapsedSeconds(int s){
        this.enlapsedSeconds = s;
    }
    
    public void setExitActionListener(ActionListener al){
        this.exitButton.addActionListener(al);
    }
}
