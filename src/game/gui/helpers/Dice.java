package game.gui.helpers;
/* [SO] Imports */
import game.gui.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
/* [EO] Imports */

/**
 * Helper class to draw instances of dice
 * extends JButton again
 * and uses a Click handler
 */
public class Dice extends JButton implements ActionListener {

    /* [SO] Global variables */
    /**
     * The value of the die 0-5 (1-6 visually).
     */
    public int diceValue;
    /**
     * Can this die be reset?
     */
    public boolean isHeld;
    /**
     * X position of die
     */
    int x;
    /**
     * Y position of die
     */
    int y;
    /* [EO] Global variables */

    public Dice(int x, int y) {
        this.x = x; //init pos values
        this.y = y;
        diceValue = new Random().nextInt(6); //Generate random value ot emulate roll

        ImageIcon buttonImage = new ImageIcon("res/gameScreenDie" + (diceValue + 1) + ".png"); //Draw image based on random value
        new JButton(); //New instance
        setIcon(buttonImage); //draw image bg
        setRolloverIcon(buttonImage); //hover image
        setPressedIcon(buttonImage); //click image
        setMargin(new Insets(-2, -2, -2, -2)); //margins
        setBounds(this.x, this.y, 70, 70); //manually force layout
        setBorder(BorderFactory.createEmptyBorder()); //Remove borders
        setContentAreaFilled(false); //Remove bg fill
        addActionListener(this); //new click handler
        setActionCommand("click"); //...with the name
        setVisible(true); //Let there be light
    }

    /**
     * Invoked when an action occurs.
     * Draw the new positions based on whether or not its held by the current player
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("click")) {
            if (!isHeld) { //Simple if else to change positions by 100
                setBounds(this.x, this.y + 100, 70, 70);
                isHeld = true; //set hed true
            } else {
                setBounds(this.x, this.y, 70, 70);
                isHeld = false;
            }
            //Game.drawButtons(); //Redraw the new button states
        }
    }
}
