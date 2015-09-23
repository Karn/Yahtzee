package game.gui;
/* [SO] Imports */

/* [EO] Imports */

import game.Config;
import game.Internal;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by Karn on 20/03/14.
 * Project name: Yahtzee.
 * Description:
 * Version:
 */
public class Splash extends JPanel {

    /* [SO] Global variables */
    /**
     * Layer handler
     */
    JLayeredPane layeredPane = new JLayeredPane(); //Creates a layer handler
    /* [EO] Global variables */

    public Splash() {
        super();
        Internal.log("Entered menu.");
        //Reset layout managers
        setLayout(null);
        layeredPane.setLayout(null);
        //set bounds
        layeredPane.setBounds(0, 0, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);

        Internal.log("Splash loaded.");
        JLabel bg = new JLabel(new ImageIcon("res/splashScreen.png")); //Draw bg
        bg.setBounds(0, 0, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT); //set bounds
        JLabel spinner = new JLabel(new ImageIcon("res/spinner.GIF")); //new loading animation
        spinner.setBounds(292, 444, 220, 20);

        //Add all components
        layeredPane.add(bg, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(spinner, JLayeredPane.MODAL_LAYER);
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

        try {
            //WORKER THREADS OMGOGMGOMGOMGOMGOM basically async tasks
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    //Initialize custom font
                    try {
                        //create the font to use. Specify the size!
                        Config.customFont = Font.createFont(Font.TRUETYPE_FONT, new File("res\\GothamLight.ttf")).deriveFont(20f);
                        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                        //register the font
                        ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("res\\GothamLight.ttf")));
                        Config.mainFrame.setFont(Config.customFont);
                        Internal.log("Custom font loaded.");
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (FontFormatException e) {
                        e.printStackTrace();
                    }
                }
            });
            Thread.sleep(2000); //delay a minimum of 2 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /*
         * Validate and repaint new components
         */
        Config.mainFrame.getContentPane().removeAll(); //Clear Components
        //Add components
        MainMenu mainMenu = new MainMenu();
        Config.mainFrame.getContentPane().add(mainMenu);
        //Complete
        Config.mainFrame.getContentPane().validate();
        Config.mainFrame.getContentPane().repaint();
    }

    /**
     * Override graphics handler
     * @param g
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(Internal.imageLoader("splashScreen"), 0, 0, null); //raw bg image
    }
}
