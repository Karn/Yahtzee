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
 * Basically stole this screen from the mainMeu... but added the text label
 */
public class GameOver extends JPanel implements ActionListener {

    /* [SO] Global variables */
    /**
     * Replay button
     */
    private final JButton playButton;
    /**
     * Exit button
     */
    private final JButton exitButton;
    /**
     * Winner text
     */
    JLabel winner;
    /**
     * Layer manager
     */
    JLayeredPane layeredPane = new JLayeredPane(); //Creates a layer handler
    /**
     * Background image declaration
     */
    private BufferedImage backgroundImage;
    /* [EO] Global variables */

    public GameOver() {
        super();
        Internal.log("Entered menu.");
        //Reset layout managers
        setLayout(null);
        layeredPane.setLayout(null);
        //set bounds
        layeredPane.setBounds(0, 0, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);

        backgroundImage = Internal.imageLoader("gameOver"); //Init bg image

        winner = new JLabel(); //New lable object
        winner.setBounds(Config.SCREEN_WIDTH / 2 - 100, Config.SCREEN_HEIGHT / 2, 200, 50); //manually set layout
        winner.setForeground(new Color(255, 255, 255)); //Set font color
        winner.setFont(Config.customFont.deriveFont(20f)); //Set font
        winner.setText(highScore()); //Calculate winner and output text

        //The same buttons from the menu with different images.. c&p really
        playButton = new JButton();
        playButton.setIcon(new ImageIcon("res/gameOverReplayButton.png"));
        playButton.setRolloverIcon(new ImageIcon("res/gameOverReplayButtonHover.png"));
        playButton.setPressedIcon(new ImageIcon("res/gameOverReplayButtonHover.png"));
        playButton.setMargin(new Insets(-2, -2, -2, -2));
        playButton.setBounds(273, 329, 256, 63);
        playButton.setBorder(BorderFactory.createEmptyBorder());
        playButton.setContentAreaFilled(false);
        playButton.setFocusPainted(false);
        playButton.addActionListener(this);
        playButton.setActionCommand("playClick");
        playButton.setVisible(true);

        //Quit Button
        exitButton = new JButton();
        exitButton.setIcon(new ImageIcon("res/mainMenuExitButton.png"));
        exitButton.setRolloverIcon(new ImageIcon("res/mainMenuExitButtonHover.png"));
        exitButton.setPressedIcon(new ImageIcon("res/mainMenuExitButtonHover.png"));
        exitButton.setMargin(new Insets(-2, -2, -2, -2));
        exitButton.setBounds(273, 395, 256, 63);
        exitButton.setBorder(BorderFactory.createEmptyBorder());
        exitButton.setContentAreaFilled(false);
        exitButton.setFocusPainted(false);
        //exitButton.getModel().addChangeListener(this); //Add rollover
        exitButton.addActionListener(this);
        exitButton.setActionCommand("exitClick");
        exitButton.setVisible(true);

        //Add all components
        layeredPane.add(playButton, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(exitButton, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(winner, JLayeredPane.DEFAULT_LAYER);
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
     * Override panel graphics
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, null); //Draw image
    }

    /**
     * Calculate winner and...
     * @return winner as text for Jlabel
     */
    public String highScore() {
        if (Config.playerOne.getScore() > Config.playerTwo.getScore()) { // 1 is more than 2
            return "<html>Winner: Player 1<br>Score: " + Config.getCurrentPlayer().getScore() + "</html>";
        } else if (Config.playerOne.getScore() < Config.playerTwo.getScore()) { // 2 is more than 1
            return "<html>Winner: Player 2<br>Score: " + Config.getCurrentPlayer().getScore() + "</html>";
        } else {
            return "Tie game!";
        }
    }

    /**
     * Invoked when an action occurs.
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("playClick")) {
            Config.currentPlayer = Config.players.PLAYER_ONE; //reset player
            //Reset player 1
            Config.playerOne.setScore(0);
            Config.playerOne.setRollCount(3);
            Config.playerOne.setAvailableButtonCount(8);
            Config.playerOne.buttons.clear();
            //Reset player 2
            Config.playerTwo.setScore(0);
            Config.playerTwo.setRollCount(3);
            Config.playerTwo.setAvailableButtonCount(8);
            Config.playerTwo.buttons.clear();

            Config.mainFrame.getContentPane().removeAll(); //remove all panels
            Config.mainFrame.getContentPane().add(new Game()); //new game panel
            Config.mainFrame.setVisible(true);
        } else if (e.getActionCommand().equals("exitClick")) {
            System.exit(0); //Exit the game
        }
    }
}
