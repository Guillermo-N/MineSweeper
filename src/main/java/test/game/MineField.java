package test.game;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.*;


/**
 * Core component of the game that contais the cell grid.
 */
public class MineField extends JPanel {

    /**
     * Matrix of cells that represents the minesweeper board
     */
    private Cell[][] cellGrid;
    /**
     * Number of mines in the match
     */
    private int mineCount;
    /**
     * Number of flags located on the board
     */
    private int flaggedCount;

    /**
     * String that indicates which cells will be designated as mines
     */
    private boolean[] minePlacement;
    /**
     * Number of cells in the board
     */
    private int cellCount; 
    /**
     * One side board cell count, board dimensions = (dimensions x dimensions)
     */
    private int dimensions;
    /**
     * Boolean to indicate if a mine was triggered and the match ended
     */
    private boolean gameOver = false;


    /**
     * Cell adjacent cells directions
     */
    private static final int[][] DIRECTIONS = {
        {0, 1}, {1, 1}, {1, 0}, {1, -1},
        {0, -1}, {-1, -1}, {-1, 0}, {-1, 1}
    };
    /**
     * Mine return code
     */
    private static final int MINE = -1;

    /**
     * Create a new instance of the minesweeper board
     * 
     * @param dimensions One sided number of cells
     * @param mines Number of mines to put in the board
     */
    public MineField(int dimensions, int mines){
        this.setLayout(new GridLayout(dimensions, dimensions));
        this.cellCount = dimensions * dimensions;
        this.mineCount = mines;
        this.flaggedCount = 0;
        this.dimensions = dimensions;
        this.cellGrid = new Cell[dimensions][dimensions];
        this.minePlacement = new boolean[this.cellCount];

        // Setting up mine placement.
        Random rand = new Random();
        for(int i = 0; i < this.mineCount; i++){
            boolean old_val;
            do{
                int place = rand.nextInt(this.cellCount);
                old_val = minePlacement[place];
                minePlacement[place] = true;
            }while(old_val);
        }

        // Creating cell buttons
        for (int i = 0; i < dimensions; i++){
            for (int j = 0; j < dimensions; j++){
                this.cellGrid[i][j] = new Cell(
                    minePlacement[i*dimensions+j],
                    new Point(i,j)
                );
                this.add(this.cellGrid[i][j]);
                this.cellGrid[i][j].addActionListener(new CellSelection());
                this.cellGrid[i][j].addMouseListener(new CellFlagged());
            }
        }
    }

    /**
     * Action to occur when a cell is selecte (clicked)
     */
    private class CellSelection implements ActionListener{
        
        @Override
        public void actionPerformed(ActionEvent arg0) {
            // Button selected from grid
            Cell b_grid = (Cell) arg0.getSource();
            if (!b_grid.isFlagged()){
                int result = cellSelection(b_grid.getPoint());
                //b_grid.setSelected(result);
                if (result == -1)
                    gameOverBanner();
            }
            
        }
    }

    /**
     * Action to occur when a cell is left clicked
     */
    private class CellFlagged extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e){
            if (SwingUtilities.isRightMouseButton(e)) {
                Cell b_grid = (Cell) e.getSource();
                b_grid.toggleFlagged();
                if (b_grid.isFlagged())
                    flaggedCount++;
                else
                    flaggedCount--;
            }
        }
        
    }

    /**
     * Resets board to for a new game, and reasign of mine cells
     */
    public void reset(){
        this.minePlacement = new boolean[this.cellCount];
        this.flaggedCount = 0;

        // Setting up mine placement.
        Random rand = new Random();
        for(int i = 0; i < this.mineCount; i++){
            boolean old_val;
            do{
                int place = rand.nextInt(this.cellCount);
                old_val = minePlacement[place];
                minePlacement[place] = true;
            }while(old_val);
        }

        for (int i = 0; i < dimensions; i++){
            for (int j = 0; j < dimensions; j++){
                this.cellGrid[i][j].reset(minePlacement[i*dimensions+j]);
            }
        }
    }

    /**
     * Recursive process of scanning adjacent mines when a cell is clicked
     * @param p Designates the cordinates of the cell clicked in the board
     * @return Number of adjacent mines, -1 if the cell contains a mine
     */
    private int cellSelection(Point p){
        // Cell cordinates
        int x = (int) p.getX();
        int y = (int) p.getY();

        int limit = this.cellGrid[0].length;
        int peripheryMineCount = 0;

        // If cell contains mine (base case)
        if(this.cellGrid[x][y].getMine()){
            this.cellGrid[x][y].setSelected(MINE);
            return MINE;
        }

        // Counting cell value
        for (int[] direction : DIRECTIONS) {
            int new_x = x + direction[0];
            int new_y = y + direction[1];
        
            if (new_x >= 0 && new_x < limit && new_y >= 0 && new_y < limit) {
                if(this.cellGrid[new_x][new_y].getMine())
                    peripheryMineCount++;
            }
        }
        
        // Hadeling cell action
        this.cellGrid[x][y].setSelected(peripheryMineCount);

        // Recursing calling to neighbouring cells
        if (peripheryMineCount == 0 ){
            for (int[] direction : DIRECTIONS) {
                int new_x = x + direction[0];
                int new_y = y + direction[1];
            
                if (new_x >= 0 && new_x < limit && new_y >= 0 && new_y < limit) {
                    if (this.cellGrid[new_x][new_y].isEnabled())
                        cellSelection(new Point(new_x, new_y));
                }
            }
        }
        

        return peripheryMineCount;
    }

    /**
     * Method to handle the game over
     */
    private void gameOverBanner(){
        for (int i = 0; i < this.dimensions; i++){
            for (int j = 0; j < this.dimensions; j++){
                if(this.cellGrid[i][j].getMine()){
                    this.cellGrid[i][j].setSelected(MineField.MINE);
                }
            }
        }
        int decision = JOptionPane.showConfirmDialog(this, "Restart Game?" ,"GameOver", JOptionPane.OK_CANCEL_OPTION);
        if(decision == 0)
            reset();
            MineSweeper.getInstance().setEnlapsedSeconds(0);
    }

    /**
     * Getter for flaggedCount
     * @return flaggedCount
     */
    public int getFlaggedCount() {
        return flaggedCount;
    }

}
