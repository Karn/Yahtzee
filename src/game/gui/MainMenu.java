package game.gui;
/* [SO] Imports */

/* [EO] Imports */

import game.Config;
import game.Internal;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

/**
 * Yeahhhhhhhh main menuuu jpanel i dont even know why i have to explain this.,.
 */
public class MainMenu extends JPanel implements ActionListener {

    /* [SO] Global variables */
    /**
     * Play button object
     */
    private final JButton playButton;
    /**
     * Exit button object
     */
    private final JButton exitButton;
    /**
     * Layer Handler
     */
    JLayeredPane layeredPane = new JLayeredPane(); //Creates a layer handler
    /**
     * BG Image declaration
     */
    private BufferedImage backgroundImage;
    /* [EO] Global variables */

    public MainMenu() {
        super();
        Internal.log("Entered menu.");
        //Reset layout managers
        setLayout(null);
        layeredPane.setLayout(null);
        //set bounds
        layeredPane.setBounds(0, 0, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);

        backgroundImage = Internal.imageLoader("mainMenu"); //Init BG image

        //Play Button
        playButton = new JButton(); //Button object
        playButton.setIcon(new ImageIcon("res/mainMenuPlayButton.png")); //Button image
        playButton.setRolloverIcon(new ImageIcon("res/mainMenuPlayButtonHover.png")); //Hover
        playButton.setPressedIcon(new ImageIcon("res/mainMenuPlayButtonHover.png")); //Clicked
        playButton.setMargin(new Insets(-2, -2, -2, -2)); //Margins
        playButton.setBounds(273, 266, 256, 63); //manually set layout
        playButton.setBorder(BorderFactory.createEmptyBorder()); //Remove border
        playButton.setContentAreaFilled(false); //remove bg
        playButton.setFocusPainted(false); //remove dotted lines
        playButton.addActionListener(this); //New click handler
        playButton.setActionCommand("playClick"); //..with this identifier
        playButton.setVisible(true); //Show it

        //Quit Button as above with different handler identifier and images
        exitButton = new JButton();
        exitButton.setIcon(new ImageIcon("res/mainMenuExitButton.png"));
        exitButton.setRolloverIcon(new ImageIcon("res/mainMenuExitButtonHover.png"));
        exitButton.setPressedIcon(new ImageIcon("res/mainMenuExitButtonHover.png"));
        exitButton.setMargin(new Insets(-2, -2, -2, -2));
        exitButton.setBounds(273, 332, 256, 63);
        exitButton.setBorder(BorderFactory.createEmptyBorder());
        exitButton.setContentAreaFilled(false);
        exitButton.setFocusPainted(false);
        //exitButton.getModel().addChangeListener(this); //Add rollover
        exitButton.addActionListener(this);
        exitButton.setActionCommand("exitClick");
        exitButton.setVisible(true);

        //Add all components
        layeredPane.add(playButton, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(exitButton, JLayeredPane.PALETTE_LAYER);
        layeredPane.setVisible(true);

        /*
         * Validate and repaint new components
         */
        Config.mainFrame.getContentPane().removeAll(); //Clear Components
        //Add components
        Config.mainFrame.getContentPane().add(layeredPane);
        //Complete
        Config.mainFrame.getContentPane().validate();
        Config.mainFrame.getContentPane().repaint();
    }

    /**
     * Override graphics handler
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, null); //redraw images
    }

    /**
     * Invoked when an action occurs.
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("playClick")) {
            Config.mainFrame.getContentPane().removeAll();
            Config.mainFrame.getContentPane().add(new Game()); //Enter new state
            Config.mainFrame.setVisible(true);
        } else if (e.getActionCommand().equals("exitClick")) {
            System.exit(0); //or exit the game
        }
    }
}
