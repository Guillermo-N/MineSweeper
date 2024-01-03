package test.game;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Entripoint of the app
 */
public class Main {

    private static JFrame frame;
    private static CardLayout card;
    private static JPanel cardPane;
    private static MineSweeper mineSweeper;

    /**
     * Main method (execution start point)
     * @param args
     */
    public static void main(String[] args) {
        //ineSweeper ms = MineSweeper.getInstance();
        StartMenu startMenu = new StartMenu();
        
        cardPane = new JPanel();
        card = new CardLayout();
        cardPane.setLayout(card);
        cardPane.add(startMenu, "Start Menu");
        //cardPane.add(ms, "Minesweeper");


        ActionListener exitGame = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                card.next(cardPane);
                cardPane.remove(mineSweeper);
            }
            
        };

        ActionListener gameSelection = new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent arg0) {
                JButton b = (JButton) arg0.getSource();
                int size = Integer.valueOf(b.getText().split(" ")[2]);
                mineSweeper = new MineSweeper(size);
                mineSweeper.setExitActionListener(exitGame);
                cardPane.add(mineSweeper, "Minesweeper");
                card.next(cardPane);
                frame.pack();
                centerFrameOnScreen(frame);

            }
            
        };
        startMenu.addActionListener(gameSelection);


        frame = new JFrame("Mine Sweeper");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.add(cardPane);
        frame.pack();
        centerFrameOnScreen(frame);

    }

    /**
     * Set the location of the JFrame to the middle of the screen
     * @param frame JFrame to be displayed
     */
    private static void centerFrameOnScreen(JFrame frame) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        int frameWidth = frame.getWidth();
        int frameHeight = frame.getHeight();

        int x = (screenWidth - frameWidth) / 2;
        int y = (screenHeight - frameHeight) / 2;

        frame.setLocation(x, y);
    }
}