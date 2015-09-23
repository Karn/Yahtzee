package game.gui.helpers;

/* [SO] Imports */

import game.Config;
import game.gui.Game;
import game.gui.GameOver;
import game.logicHandler.Calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/* [EO] Imports */

/**
 * Helper Class to generate the appropriate Buttons for the eight types of combinations
 * Extends JButton for obvious reasons (its a button)
 * Action listener to handle button
 */
public class ComboButton extends JButton implements ActionListener {

    /* [SO] Global variables */
    /**
     * Default type of button (refer to 'value' enum)
     */
    public Value value = Value.None;
    /**
     * Value of the current die values that correspond to the function of the button
     */
    private int comboValue;
    /**
     * X position of the button
     */
    private int x;
    /**
     * Y position of the button
     */
    private int y;
    /* [EO] Global variables */

    public ComboButton(int x, int y, Value value) {
        this.x = x; //Offset values foe aesthetics
        this.y = y - 50;
        this.value = value; //Initialize type

        ImageIcon buttonImage = new ImageIcon("res/gameScreenButton_" + this.value.toString() + ".png"); //Draw button icon based on type
        new JButton(); //init JButton
        setIcon(buttonImage); //Set icon of button (bg)
        setFont(new Font("Dialog", 1, 14)); //Font button for values
        setForeground(new Color(179, 35, 37)); //Default color when actie
        updateText(); //Update the value based type
        setHorizontalTextPosition(JButton.CENTER); //Set positioning within text
        setVerticalTextPosition(JButton.CENTER);
        setRolloverIcon(buttonImage); //Hover icon
        setPressedIcon(buttonImage); //Click icon
        setMargin(new Insets(-2, -2, -2, -2)); //Inner margins
        setBounds(this.x, this.y, 151, 31); //Set manual layout
        setBorder(BorderFactory.createEmptyBorder()); //Remove ugly border
        setContentAreaFilled(false); //remove bg color
        setFocusPainted(false); //remove dotted lines
        addActionListener(this); //init new click handler...
        setActionCommand("click"); //..with this name
        setVisible(true); //LET THERE BE LIGHT
    }

    /**
     * This method is called to update the score based on the given die arrangement and type
     */
    public void updateText() {
        this.comboValue = Calculator.calculate(value); //Calculate the value
        setText("                              " + this.comboValue); //notice the huge space? its just to offset the value display area
    }

    /**
     * Invoked when an action occurs.
     * Used to disable button and move to next player turn and add score
     * @param e
     */
    @Override
    public void actionPerformed(final ActionEvent e) {
        if (e.getActionCommand().equals("click")) { //Click name
            if (value != Value.Sum) {
                if (isEnabled()) { //Check if button is enabled
                    setForeground(new Color(93, 93, 93)); //Change to disabled color
                    setEnabled(false); //disable it
                    Config.getCurrentPlayer().setScore(Config.getCurrentPlayer().getScore() + comboValue); //Update score for current player
                    Config.getCurrentPlayer().setAvailableButtonCount(Config.getCurrentPlayer().getAvailableButtonCount() - 1); //Minus 1 roll
                    Config.mainFrame.getContentPane().validate();
                    Config.mainFrame.getContentPane().repaint();
                }
                if (Config.playerOne.availableButtonCount == 0 && Config.playerTwo.availableButtonCount == 0) { //Check to see if all buttons are clicked by both players
                    Config.mainFrame.getContentPane().removeAll(); //Clear Components
                    //Add components
                    GameOver gameOver = new GameOver(); //Go to gameover screen
                    Config.mainFrame.getContentPane().add(gameOver);
                    //Complete and update main frame
                    Config.mainFrame.getContentPane().validate();
                    Config.mainFrame.getContentPane().repaint();
                }
                //While the above is happening run new thread that draws the animation of player switching
                SwingUtilities.invokeLater(new Runnable() { //Swing worker thread
                    public void run() { //init
                        Game.switchPlayer(); //Call visualizer
                    }
                });
            }
        }
    }

    /**
     * Available types of buttons
     */
    public enum Value {
        None, Ones, Twos, Threes, Fours, Fives, Sixes, Sum, Bonus, ThreeOfAKind, FourOfAKind, FullHouse, SmallStraight, LargeStraight, Yahtzee, Chance
    }
}
