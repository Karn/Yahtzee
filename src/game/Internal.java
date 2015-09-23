package game;/* [SO] Imports */

/* [EO] Imports */

import game.gui.Splash;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Karn on 19/03/14.
 * Project name: Yahtzee.
 * Description:
 * Version:
 */
public class Internal implements Runnable {

    /* [SO] Global variables */
    /**
     * Main thread definition
     */
    public static Thread mainThread; //Main thread definition
    /* [EO] Global variables */

    public static void main(String[] args) {
        mainThread = new Thread(new Internal()); //Create new thread
        mainThread.setName("Main Thread"); //Thread identifier
        mainThread.start(); //Start thread
    }

    /**
     * Global system output in the form of debug
     * @param message user entered message
     */
    public static void log(Object message) {
        if (Config.DEBUG_ENABLED) //Check if debug is enabled
            System.out.println("[DEBUG]: " + message);
    }

    /**
     * Global image loader
     * @param imageName user entered image name that needs to match the file name in the res/img/ directory
     * @return returns image
     */
    public static BufferedImage imageLoader(String imageName) {
        BufferedImage image = null; //Null pointer
        try {
            image = ImageIO.read(new File("res/" + imageName + ".png")); //Init image
        } catch (IOException ex) {
            log(imageName + "failed to load.");
        }
        return image; //return new image
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p/>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     * @see Thread#run()
     */
    @Override
    public void run() {
        log("Initialized GUI thread.");
        Config.mainFrame = new JFrame(Config.TITLE); //this will be the actual window
        Config.mainFrame.setSize(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT); //Reset size
        Config.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Default close operation
        Config.mainFrame.setLocationRelativeTo(null); //Centers frame on screen
        try {
            // Set System Look and feel of the window
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Config.mainFrame.setResizable(false); //NO RESIZING
        Config.mainFrame.setVisible(true); //Set visible
        Internal.log("Initialized main GUI frame."); //Output to debug

        //Add new components to frame
        Splash splashScreen = new Splash(); //Initialize new splashscreen object
        splashScreen.setVisible(true); //Set it to visible
        //Add all components
        Config.mainFrame.add(splashScreen); //Add to main frame

        Config.mainFrame.setVisible(true); //enable mainframe
        Config.mainFrame.validate(); //finalizes changes
        Config.mainFrame.repaint(); //Re draw
    }
}
