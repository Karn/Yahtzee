package game.gui;
/* [SO] Imports */

import game.Config;
import game.Internal;
import game.gui.helpers.ComboButton;
import game.gui.helpers.Dice;
import game.gui.helpers.PlayerTransition;
import game.logicHandler.Calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static game.Config.customFont;
import static game.Config.getCurrentPlayer;
import static game.Config.players.PLAYER_ONE;
import static game.Config.players.PLAYER_TWO;
/* [EO] Imports */

/**
 * Game screen as a jpanel calls and implement a few helper classes
 */
public class Game extends JPanel implements ActionListener {

    /* [SO] Global variables */
    /**
     * The default Y location of the dice
     */
    private static final int DIE_Y_LOCATION = 115;
    /**
     * New Jbutton that controls the roll of the dice
     */
    public static JButton rollButton;
    /**
     * Layer handler of the planel, controls what layer appear where
     */
    public static JLayeredPane layeredPane = new JLayeredPane(); //Creates a layer handler
    /**
     * List containing the dice that are available to the player during their roll
     */
    public static ArrayList<Dice> dice = new ArrayList<Dice>();
    /**
     * JLabel to show the current player and their score
     */
    private static JLabel playerLabel;
    /**
     * Another label to show the other players and their score
     */
    private static JLabel otherPlayerLabel;
    /**
     * Image declaration for the background
     */
    private BufferedImage backgroundImage;
    /**
     * Image declaration for the current player banner
     */
    private BufferedImage currentPlayerBG;
    /* [EO] Global variables */

    public Game() {
        super();
        Internal.log("Entered game screen.");
        //Reset layout managers
        setLayout(null);
        layeredPane.setLayout(null); //Remove preexisting layout manager
        layeredPane.setBounds(0, 0, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT); //set bounds
        backgroundImage = Internal.imageLoader("gameScreen"); //Init background image
        currentPlayerBG = Internal.imageLoader("gameScreenCurrentPlayer"); //Init banner image

        resetRoll(); //Generate and draw the randomly generated dice

        //Roll Button
        rollButton = new JButton(); //Jutton object
        rollButton.setIcon(new ImageIcon("res/gameScreenDiceRollLabel.png")); //Default icon
        rollButton.setRolloverIcon(new ImageIcon("res/gameScreenDiceRollLabel.png")); //Hover
        rollButton.setPressedIcon(new ImageIcon("res/gameScreenDiceRollLabel.png"));//Click
        rollButton.setMargin(new Insets(10, 10, 10, 10)); //Margins
        rollButton.setBounds(600, 360, 134, 78); //Manual layout manager
        rollButton.setBorder(BorderFactory.createEmptyBorder()); //remove ugly background
        rollButton.setContentAreaFilled(false); //Remove bg
        rollButton.setFocusPainted(false); //Remove dotted lines
        rollButton.addActionListener(this); //New click handler
        rollButton.setActionCommand("rollClick"); //...with this identifier
        rollButton.setVisible(true); //Display it

        playerLabel = new JLabel(); //New jlabel object
        playerLabel.setBounds(30, 10, 200, 50); //Manual bounds
        playerLabel.setForeground(new Color(224, 35, 45)); //Text color
        playerLabel.setFont(customFont.deriveFont(20f)); //Set font and size
        playerLabel.setText("<html><b>Player 1</b><br>Score: " + Config.getCurrentPlayer().getScore() + "</html>"); //Set text using html roperties -> didnt know this was possible. very cool

        //Same as above but for other player
        otherPlayerLabel = new JLabel();
        otherPlayerLabel.setBounds(30, 60, 200, 50);
        otherPlayerLabel.setForeground(new Color(255, 255, 255));
        otherPlayerLabel.setFont(customFont.deriveFont(16f));
        otherPlayerLabel.setText("<html><b>Player 2</b><br>Score: " + Config.getOtherPlayer().getScore() + "</html>");

        /*
         * Validate and repaint new components
         */
        Config.mainFrame.getContentPane().removeAll(); //Clear Components
        //Add components
        layeredPane.add(rollButton, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(playerLabel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(otherPlayerLabel, JLayeredPane.DEFAULT_LAYER);
        Config.mainFrame.getContentPane().add(layeredPane); //Add layer handler to main frame
        //Complete
        Config.mainFrame.getContentPane().validate();
        Config.mainFrame.getContentPane().repaint();
    }

    /**
     * Method that is called to reset and reroll dice.
     */
    public static void resetRoll() {
        //todo move this to initializer as it only needs to be done once
        if (dice.size() == 0) { //Checks if dice are available to be drawn
            for (int i = 0; i < 5; i++) { //umber of dice
                dice.add(new Dice(115 + (125 * i), DIE_Y_LOCATION)); //Add new die object with horizontal offset
            }
        } else {
            for (int x = 0; x < 5; x++) { //Only reset and roll dice if they aren't held
                if (!dice.get(x).isHeld) {
                    dice.get(x).setVisible(false); //remove visiblity
                    dice.set(x, new Dice(115 + (125 * x), DIE_Y_LOCATION)); //redraw
                }
            }
        }

        Component diceComponents[] = layeredPane.getComponentsInLayer(JLayeredPane.PALETTE_LAYER); //remove all currently drawn components on die layer
        for (int x = 0; x < diceComponents.length; x++) {
            layeredPane.remove(diceComponents[x]); //Remove property of dicelist
        }
        //Add components
        for (JButton button : dice) { //Readd new uttons
            layeredPane.add(button, JLayeredPane.PALETTE_LAYER);
            button.setVisible(true);
        }

        drawButtons(); //Redraw available types of moves for current player
        //Complete

        /*
         * Validate and repaint new components
         */
        //Complete
        layeredPane.setVisible(true);
        layeredPane.validate();
        layeredPane.repaint();

        Config.mainFrame.getContentPane().validate();
        Config.mainFrame.getContentPane().repaint();
    }

    /**
     * Same as above but only used to reroll dice
     */
    public static void rerollDice() {
        dice.clear(); //Clear current dice
        for (int i = 0; i < 5; i++) { //redraw dice
            dice.add(new Dice(115 + (125 * i), DIE_Y_LOCATION));
        }
        //Remove all previously drawn dice
        Component diceComponents[] = layeredPane.getComponentsInLayer(JLayeredPane.PALETTE_LAYER);
        for (int x = 0; x < diceComponents.length; x++) {
            layeredPane.remove(diceComponents[x]);
        }
        //Add components
        for (JButton button : dice) {
            layeredPane.add(button, JLayeredPane.PALETTE_LAYER);
            button.setVisible(true);
        }

        /*
         * Validate and repaint new components
         */
        //Complete
        layeredPane.setVisible(true);
        layeredPane.validate();
        layeredPane.repaint();

        Config.mainFrame.getContentPane().validate();
        Config.mainFrame.getContentPane().repaint();
    }

    /**
     * Method used to draw buttons that represent the avilible options for plays for the current player
     */
    public static void drawButtons() {
        //Draw individual buttons if none exist, manual placement
        if (getCurrentPlayer().buttons.size() == 0) {
            getCurrentPlayer().buttons.add(new ComboButton(30, 352, ComboButton.Value.Ones));
            getCurrentPlayer().buttons.add(new ComboButton(30, 386, ComboButton.Value.Twos));
            getCurrentPlayer().buttons.add(new ComboButton(30, 420, ComboButton.Value.Threes));
            getCurrentPlayer().buttons.add(new ComboButton(30, 454, ComboButton.Value.Fours));
            getCurrentPlayer().buttons.add(new ComboButton(30, 488, ComboButton.Value.Fives));

            getCurrentPlayer().buttons.add(new ComboButton(185, 352, ComboButton.Value.Sixes));
            getCurrentPlayer().buttons.add(new ComboButton(185, 386, ComboButton.Value.Sum));
            getCurrentPlayer().buttons.add(new ComboButton(185, 420, ComboButton.Value.Bonus));
            getCurrentPlayer().buttons.add(new ComboButton(185, 454, ComboButton.Value.ThreeOfAKind));
            getCurrentPlayer().buttons.add(new ComboButton(185, 488, ComboButton.Value.FourOfAKind));

            getCurrentPlayer().buttons.add(new ComboButton(340, 352, ComboButton.Value.FullHouse));
            getCurrentPlayer().buttons.add(new ComboButton(340, 386, ComboButton.Value.SmallStraight));
            getCurrentPlayer().buttons.add(new ComboButton(340, 420, ComboButton.Value.LargeStraight));
            getCurrentPlayer().buttons.add(new ComboButton(340, 454, ComboButton.Value.Yahtzee));
            getCurrentPlayer().buttons.add(new ComboButton(340, 488, ComboButton.Value.Chance));
        } else {
            for (int x = 0; x < getCurrentPlayer().buttons.size(); x++) { //Loop through buttons and check if theyre enabled for clicks
                if (getCurrentPlayer().buttons.get(x).isEnabled()) {
                    getCurrentPlayer().buttons.get(x).setVisible(false);
                    getCurrentPlayer().buttons.get(x).updateText(); //Recalc the points availible from this option
                    getCurrentPlayer().buttons.get(x).validate();
                    getCurrentPlayer().buttons.get(x).repaint();
                } else {
                    //TCheck for double yahtzee
                    if (Calculator.calculate(ComboButton.Value.Yahtzee) >= 50 && getCurrentPlayer().buttons.get(x).value.equals(ComboButton.Value.Yahtzee) && !getCurrentPlayer().buttons.get(x).isEnabled() && getCurrentPlayer().yahtzeeBonus == false) {
                        getCurrentPlayer().buttons.get(x).setVisible(false);
                        getCurrentPlayer().buttons.get(x).setText("                              " + "100");
                        getCurrentPlayer().buttons.get(x).validate();
                        getCurrentPlayer().buttons.get(x).repaint();
                        Config.getCurrentPlayer().setScore(Config.getCurrentPlayer().getScore() + 50); //Add score
                        Config.mainFrame.getContentPane().validate();
                        Config.mainFrame.getContentPane().repaint();
                        getCurrentPlayer().yahtzeeBonus = true;
                        SwingUtilities.invokeLater(new Runnable() { //Swing worker thread
                            public void run() { //init
                                Game.switchPlayer(); //Call visualizer
                            }
                        });
                    }
                }
            }
        }
        //Clear all current buttons for repaint
        Component buttonComponents[] = layeredPane.getComponentsInLayer(JLayeredPane.MODAL_LAYER);
        for (int x = 0; x < buttonComponents.length; x++) {
            layeredPane.remove(buttonComponents[x]);
        }
        //Add components
        for (JButton button : getCurrentPlayer().buttons) {
            layeredPane.add(button, JLayeredPane.MODAL_LAYER);
            button.setVisible(true);
        }
    }

    /**
     * Method that delegates player switches
     */
    public static void switchPlayer() {
        switch (Config.currentPlayer) { //Switch case for convenience
            case PLAYER_ONE:
                Config.currentPlayer = PLAYER_TWO;
                //Redraw labels
                playerLabel.setText("<html><b>Player 2</b><br>Score: " + Config.getCurrentPlayer().getScore() + "</html>");
                otherPlayerLabel.setText("<html><b>Player 1</b><br>Score: " + Config.getOtherPlayer().getScore() + "</html>");
                getCurrentPlayer().setRollCount(3); //Reset roll count
                Internal.log("Switched to player 2.");
                new PlayerTransition(); //Draw animation for switch
                break;
            case PLAYER_TWO: //As above
                Config.currentPlayer = PLAYER_ONE;
                playerLabel.setText("<html><b>Player 1</b><br>Score: " + Config.getCurrentPlayer().getScore() + "</html>");
                otherPlayerLabel.setText("<html><b>Player 2</b><br>Score: " + Config.getOtherPlayer().getScore() + "</html>");
                getCurrentPlayer().setRollCount(3);
                Internal.log("Switched to player 1.");
                new PlayerTransition();
                break;
        }
    }

    /**
     * Override graphics handler
     * @param g
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, null); //Draw bg
        g.drawImage(currentPlayerBG, 0, 10, null); //Draw current player
    }

    /**
     * Invoked when an action occurs.
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("rollClick")) {
            getCurrentPlayer().setRollCount(getCurrentPlayer().getRollCount() - 1); //Reduce number of availible rolls for current player
            if (getCurrentPlayer().getRollCount() <= 0) { //If no rolls are left
                drawButtons(); //draw buttons
                //rollButton.setEnabled(false); //Disable roll button
            }
            resetRoll(); //Draw new dice
        }
    }
}
