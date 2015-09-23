package game.logicHandler;
/* [SO] Imports */

/* [EO] Imports */

import game.Config;
import game.gui.Game;
import game.gui.helpers.ComboButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static game.Config.getCurrentPlayer;

/**
 * Bulk of the program, calculates the points for each combination
 */
public class Calculator {

    /* [SO] Global variables */
    /**
     * Value of dice arrangement
     */
    static int dieValue;
    /**
     * Value that is to be returned to button
     */
    static int returnValue;
    /**
     * Number of dice that have the same value
     */
    static int sameValueDiceCount;
    /**
     * Yahtzee Count
     */
    static int yahtzeeCount = 0;
    /**
     * List to old the values of the die arrangement
     */
    static List<Integer> diceList = new ArrayList<Integer>();
    /* [EO] Global variables */

    public static int calculate(ComboButton.Value value) {
        //Reset values
        int currentKind = 0;
        dieValue = 0;
        returnValue = 0;
        sameValueDiceCount = 0;
        diceList.clear(); //Clear list of dice

        for (int i = 0; i < 5; i++) { //Die value
            diceList.add(Game.dice.get(i).diceValue); //add dice to list
        }
        Collections.sort(diceList); //sort list in acending order

        switch (value) { //Switch case
            case Ones:
                return Collections.frequency(diceList, 0) * 1; //Return ones
            case Twos:
                return Collections.frequency(diceList, 1) * 2; //Return twos
            case Threes:
                return Collections.frequency(diceList, 2) * 3; //Threes
            case Fours:
                return Collections.frequency(diceList, 3) * 4; //Fours
            case Fives:
                return Collections.frequency(diceList, 4) * 5; //Fives
            case Sixes:
                return Collections.frequency(diceList, 5) * 6; //Sixes
            case Sum:
                //if (calculate(ComboButton.Value.Ones) + calculate(ComboButton.Value.Twos) + calculate(ComboButton.Value.Threes))
                int sumScore = 0;
                for (int x = 0; x < Config.getCurrentPlayer().buttons.size(); x++) {
                    if (!Config.getCurrentPlayer().buttons.get(x).isEnabled()) { //Find all the ones where the score has been added
                        if (Config.getCurrentPlayer().buttons.get(x).value.equals(ComboButton.Value.Ones) ||
                                Config.getCurrentPlayer().buttons.get(x).value.equals(ComboButton.Value.Twos) ||
                                Config.getCurrentPlayer().buttons.get(x).value.equals(ComboButton.Value.Threes) ||
                                Config.getCurrentPlayer().buttons.get(x).value.equals(ComboButton.Value.Fours) ||
                                Config.getCurrentPlayer().buttons.get(x).value.equals(ComboButton.Value.Fives) ||
                                Config.getCurrentPlayer().buttons.get(x).value.equals(ComboButton.Value.Sixes)) { //Checks to see if its the singles
                            String current = Config.getCurrentPlayer().buttons.get(x).getText().replaceAll(" ", ""); //remove all the spaces
                            sumScore += Integer.parseInt(current); //parse string into int and add to score
                        }
                    }
                }
                return sumScore; //return final
            case Bonus:
                if (calculate(ComboButton.Value.Sum) >= 63) //Check if sum of singles is greater than 63..
                    return 35; //return 35 points if true
                break;
            case ThreeOfAKind:
                for (int i = 5; i >= 0; i--) { //Loop through die staring from the highest
                    if (Collections.frequency(diceList, i) >= 3) { //Check the count of the highest value and compare it with the frequency required
                        int chanceScore = 0;
                        for (int x = 0; x < 5; x++) {
                            chanceScore += Game.dice.get(x).diceValue + 1;
                        }
                        return chanceScore;
                    }
                }
                break;
            case FourOfAKind:
                for (int i = 5; i >= 0; i--) { //Loop through die staring from the highest
                    if (Collections.frequency(diceList, i) >= 4) { //Check the count of the highest value and compare it with the frequency required
                        int chanceScore = 0;
                        for (int x = 0; x < 5; x++) {
                            chanceScore += Game.dice.get(x).diceValue + 1;
                        }
                        return chanceScore;
                    }
                }
                break;
            case FullHouse:
                for (int i = 5; i >= 0; i--) { //Start from the highest value of dice
                    for (int j = 0; j < 5; j++) { //
                        if (Game.dice.get(j).diceValue == i) {
                            sameValueDiceCount++; //get the first die and set that as the default value handle
                            if (sameValueDiceCount == 3) { //if there are three of them
                                dieValue = Game.dice.get(j).diceValue; //set that die to not be tested the next time
                                sameValueDiceCount = 0; //rest die count
                                for (int x = 5; x >= 0; x--) { //same as above but looking for two die but not of the same value of the above combination
                                    if (x != dieValue) {
                                        for (int y = 0; y < 5; y++) { //Die value
                                            if (Game.dice.get(y).diceValue == x) {
                                                sameValueDiceCount++;
                                                if (sameValueDiceCount == 2) {
                                                    return 25;//return the score of 25
                                                }
                                            }
                                        }
                                    }
                                    sameValueDiceCount = 0; //reset die similarity count
                                }
                            }
                        }
                    }
                    sameValueDiceCount = 0; //reset die similarity count
                }
                break;
            case SmallStraight:
                //Todo fix this shit
                //Check if any of the three combinations exists
                if ((diceList.contains(0) && diceList.contains(1) && diceList.contains(2) && diceList.contains(3)) || (diceList.contains(1) && diceList.contains(2) && diceList.contains(3) && diceList.contains(4)) || (diceList.contains(2) && diceList.contains(3) && diceList.contains(4) && diceList.contains(5)))
                    return 30; //return a value if they do
                break;
            case LargeStraight:
                //Check if any of the two combinations exist
                if ((diceList.contains(0) && diceList.contains(1) && diceList.contains(2) && diceList.contains(3) && diceList.contains(4)) || (diceList.contains(1) && diceList.contains(2) && diceList.contains(3) && diceList.contains(4) && diceList.contains(5)))
                    return 40;
                break;
            case Yahtzee:
                currentKind = 5;
                break;
            case Chance: //Just add up all the values
                int chanceScore = 0;
                for (int x = 0; x < 5; x++) {
                    chanceScore += Game.dice.get(x).diceValue + 1;
                }
                return chanceScore;
        }
        //If its not one of the more complex calculations do the simple ones
        for (int i = 5; i >= 0; i--) { //Loop through die staring from the highest
            if (Collections.frequency(diceList, i) >= currentKind) { //Check the count of the highest value and compare it with the frequency required
                if (currentKind == 5) { //if the type we're looking for is yahtzee
                    //YAHTZEE BONUS
                    for (int x = 0; x < getCurrentPlayer().buttons.size(); x++) {
                        if (yahtzeeCount == 1 && getCurrentPlayer().buttons.get(x).value.equals(ComboButton.Value.Yahtzee) && !getCurrentPlayer().buttons.get(x).isEnabled()) {
                            System.out.println("Yahtzee two");
                            yahtzeeCount = 2;
                            return 100;
                        }
                    }
                    // return ; //return 50 rathar than a multiplier
                    yahtzeeCount = 1;
                    return 50;
                }
                return (i + 1) * currentKind;
            }
        }
        return 0; //Default return if the type does not exist
    }
}
