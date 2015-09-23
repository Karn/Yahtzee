package game;/* [SO] Imports */

/* [EO] Imports */

import game.gui.helpers.Player;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Karn on 19/03/14.
 * Project name: Yahtzee.
 * Description:
 * Version:
 */
public class Config {

    /* [SO] Global variables */
    /**
     * Title of the main frame
     */
    public static final String TITLE = "Yahtzee";

    /**
     * Debug output
     */
    public static final boolean DEBUG_ENABLED = true;

    /**
     * Width of the frame
     */
    public static final int SCREEN_WIDTH = 800;

    /**
     * Height of the frame
     */
    public static final int SCREEN_HEIGHT = 500;

    /**
     * New object initialization of player one
     */
    public static final Player playerOne = new Player() {
    };
    /**
     * New object initialization of player two
     */
    public static final Player playerTwo = new Player() {
    };

    /**
     * Main Jframe component, everything gets rendered and updated here
     */
    public static JFrame mainFrame;

    /**
     * Define custom font
     */
    public static Font customFont;
    /**
     * Set default player turn
     */
    public static players currentPlayer = players.PLAYER_ONE;

    /**
     * Method to find out who is currently playing
     * basically to return the values associated with each player
     * @return
     */
    public static Player getCurrentPlayer() {
        switch (currentPlayer) { //basically to re
            case PLAYER_ONE:
                return playerOne;
            case PLAYER_TWO:
                return playerTwo;
        }
        return null; //Error handler
    }

    /**
     * Method to get the values associated with the player that is not currently paying
     * @return
     */
    public static Player getOtherPlayer() {
        switch (currentPlayer) {
            case PLAYER_ONE:
                return playerTwo;
            case PLAYER_TWO:
                return playerOne;
        }
        return null; //Error handler
    }

    /**
     * Enumerated states with type of players
     */
    public static enum players {
        PLAYER_ONE, PLAYER_TWO
    }
    /* [EO] Global variables */
}
