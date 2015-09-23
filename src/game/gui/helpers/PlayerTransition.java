package game.gui.helpers;

import game.Config;
import game.Internal;
import game.gui.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Animation when player switch occurs
 * Just a panel overlaid on the game panel below and clicks
 */
public class PlayerTransition extends JPanel implements ActionListener {

    /* [SO] Global variables */
    JLayeredPane layeredPane = new JLayeredPane(); //Creates a layer handler
    JLabel overlay; //Gif
    JButton continueButton; //Button to go to next player
    /* [EO] Global variables */

    public PlayerTransition() {
        super();
        Internal.log("Drawn player transition.");
        //Reset layout managers
        setLayout(null);
        layeredPane.setLayout(null);
        //set bounds
        layeredPane.setBounds(0, 0, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
        layeredPane.setOpaque(true); //Remove white/cream background from JPanel

        //Continue Button
        continueButton = new JButton(); //New button
        continueButton.setIcon(new ImageIcon("res/gameScreenPlayerTransitionContinueButton.png")); //bg
        continueButton.setRolloverIcon(new ImageIcon("res/gameScreenPlayerTransitionContinueButtonHover.png")); //hover
        continueButton.setPressedIcon(new ImageIcon("res/gameScreenPlayerTransitionContinueButtonHover.png")); //click
        continueButton.setMargin(new Insets(-2, -2, -2, -2)); //Margins
        continueButton.setBounds(273, 266, 256, 63); //Manual layouts
        continueButton.setBorder(BorderFactory.createEmptyBorder()); //remove borders
        continueButton.setContentAreaFilled(false); //remove bg
        continueButton.setFocusPainted(false); //remove dotted lines
        continueButton.addActionListener(this); //new click handler
        continueButton.setActionCommand("click");
        continueButton.setVisible(true); //display it

        ImageIcon overlayIcon = new ImageIcon("res/nextPlayer.gif"); //amimation as single loop gif
        overlayIcon.getImage().flush(); //Reset image from cache
        overlay = new JLabel(overlayIcon); //draw it
        overlay.setBounds(0, 170, 800, 180); //manually config layout
        overlay.setVisible(true); //display it

        //Add all components to respective layer
        Game.layeredPane.add(overlay, JLayeredPane.POPUP_LAYER);
        Game.layeredPane.add(continueButton, JLayeredPane.DRAG_LAYER);
        Game.layeredPane.setVisible(true);
        //update and redraw
        Game.layeredPane.validate();
        Game.layeredPane.repaint();
        //same as above but for main
        Config.mainFrame.getContentPane().validate();
        Config.mainFrame.getContentPane().repaint();
    }

    /**
     * Invoked when an action occurs.
     * Remove the overlay and continue
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("click")) {
            Game.rerollDice(); //new dice
            Game.resetRoll(); //Reset roll
            //remove hovers
            Game.layeredPane.remove(overlay);
            Game.layeredPane.remove(continueButton);
            Game.rollButton.setEnabled(true); //reenable the roll button
            Game.layeredPane.setVisible(true); //Display overlay
            //Update and draw
            Game.layeredPane.validate();
            Game.layeredPane.repaint();
            //As above but for the mainframe
            Config.mainFrame.getContentPane().validate();
            Config.mainFrame.getContentPane().repaint();
        }
    }
}
