package game.gui.helpers;
/* [SO] Imports */

/* [EO] Imports */

import game.gui.helpers.ComboButton;

import java.util.ArrayList;

/**
 * Created by Karn on 22/03/14.
 * Project name: Yahtzee.
 * Description:
 * Version:
 */
public abstract class Player {

    /* [SO] Global variables */
    /**
     * Score of current player
     */
    public int score;
    /**
     * Number of rolls available to player
     */
    public int rollCount;
    /**
     * Number of button available to choose from
     */
    public int availableButtonCount;
    /**
     * Yahtzee bonus
     */
    public boolean yahtzeeBonus = false;
    /**
     * •One of a kind(0): Highest single die.
     * •Two of a kind(1): Highest pair.
     * •Three of a kind(2): Highest triplet.
     * •Four of a kind(3): Highest triplet.
     * •Full house(4): Three of a kind and a two of a kind.
     * •Small straight(5): 4 consecutive dice.
     * •Large straight(6): 5 consecutive dice.
     * •Yahtzee(7): Any 5 die quintet.
     */
    public boolean[] buttonSelected;
    /**
     * List of buttons as types of combiations
     */
    public ArrayList<ComboButton> buttons = new ArrayList<ComboButton>();
    /* [EO] Global variables */

    public Player() {
        //Define default values
        setScore(0);
        setRollCount(3);
        setAvailableButtonCount(15);
    }

    /* [SO] Getter/Setters */

    /**
     * Finds the current score
     * @return score
     */
    public int getScore() {
        return score;
    }

    /**
     * Modifies the score
     * @param score value to set score to
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Number of rolls available
     * @return count
     */
    public int getRollCount() {
        return rollCount;
    }

    /**
     * Set number of rolls
     * @param rollCount value to modify to
     */
    public void setRollCount(int rollCount) {
        this.rollCount = rollCount;
    }

    /**
     * Get number of buttons
     * @return count
     */
    public int getAvailableButtonCount() {
        return availableButtonCount;
    }

    /**
     * Set number of buttons
     * @param availableButtonCount value to set to
     */
    public void setAvailableButtonCount(int availableButtonCount) {
        this.availableButtonCount = availableButtonCount;
    }
    /* [EO] Getter/Setters */
}
