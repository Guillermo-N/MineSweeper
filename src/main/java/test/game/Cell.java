package test.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * Customized JButton used for populating the mine field.
 * Denominated as Cell
 */
public class Cell extends JButton{

    /**
     * Icon of the flag
     */
    private static final ImageIcon flag_icon = new ImageIcon(Cell.class.getClassLoader().getResource("flag_30px.png"));
    /**
     * Icon of the mine
     */
    private static final ImageIcon mine_icon = new ImageIcon(Cell.class.getClassLoader().getResource("mine_30px.png"));
    /**
     * Font used for cell text
     */
    private static final Font contactFont = new Font("Arial", Font.BOLD, 16);
    /**
     * Boolean to designate if the cell hides a mine
     */
    private boolean mine;
    /**
     * Coordinates where the cell sits on the mine field
     */
    private Point coordinates;

    /**
     * Initialize a (JButton) with custom properties
     * @param mine will this cell hide a mine
     * @param coordinates coordinates where the cell sits
     */
    public Cell(boolean mine, Point coordinates){
        super();
        this.mine = mine;
        this.coordinates = coordinates;

        this.setPreferredSize(new Dimension(32,32));
        this.setMargin(new Insets(0,0,0,0));
        this.setBackground(Color.WHITE);
        this.setFont(contactFont);
        this.setFocusable(false);
    }

    /**
     * Transform look and feel of the cell base on status
     * @param status Status of the cell
     */
    public void setSelected(int status){
        setIcon(null);
        setBackground(Color.WHITE);
        // Case cell is surounded
        if(status > 0){
            switch (status) {
                case 1:
                    setBackground(new Color(143, 237, 123, 175));
                    break;
                case 2:
                    setBackground(new Color(233, 237, 123, 175));
                    break;
                case 3:
                    setBackground(new Color(237, 176, 123, 175));
                    break;
                default:
                    setBackground(new Color(237, 123, 137, 175));
                    break;
            }
            setText(String.valueOf(status));
            setEnabled(false);
        // Case cell contains a mine
        }else if (status == -1) {
            setBackground(new Color(181, 63, 63));
            setIcon(mine_icon);
            setEnabled(false);
        // Case vanilla cell
        } else if (status == 0){
            setBackground(Color.LIGHT_GRAY);
            setEnabled(false);
        }
    }

    /**
     * Getter for mine
     * @return Contains a mine
     */
    public boolean getMine(){
        return this.mine;
    }

    /**
     * Getter for coordinates of the cell
     * @return Point coordinates of the cell on the board
     */
    public Point getPoint(){
        return this.coordinates;
    }
    
    /**
     * Resets the cell look and fell and reasigns mine
     * @param mine Will this cell hide a mine
     */
    public void reset(boolean mine){
        this.mine = mine;
        this.setText("");
        this.setBackground(Color.WHITE);
        this.setIcon(null);
        this.setEnabled(true);
    }

    /**
     * Toggle between flagged and not flagged cell
     */
    public void toggleFlagged(){
        if(this.getIcon() == flag_icon)
            this.setIcon(null);
        else
            this.setIcon(flag_icon);
        
    }

    /**
     * Is this cell flagged
     * @return Flagged state
     */
    public boolean isFlagged(){
        return this.getIcon() == flag_icon;
    }
}
