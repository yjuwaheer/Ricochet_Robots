import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Color;

/**
 * Write a description of class Turn here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Turn
{
    private ArrayList<Integer> enteredNums = new ArrayList<>();
    //timer
    //list of solutions if there are multiple
    private Solution bestSolution;
    private static Solution currentSolution;
    private static String chosenTargetSpace;
    private static Icon chosenIcon;
    ///public String currentPlayer;
    

    /**
     * Constructor to start a new solution for the current turn
     */
    public Turn()
    {
        //lowestNumEntered = 0;
        //countdown = 0;
        ///currentSolution = new Solution();
        ///boolean robotOnSquare = false;
        
        chosenTargetSpace = getRandomTargetSpace();
        
        ////To fetch the correct targetSpaceMap
        HashMap<String, Icon> targetSpaceMap = SimpleMap.getTargetSpaceMap();
        chosenIcon = targetSpaceMap.get(chosenTargetSpace);
    }
    
    //Return the currentSolution to access Solution method
    public static Solution getCurrentSolution()
    {
        return currentSolution;
    }
    
    public static String getChosenTargetSpace()
    {
        return chosenTargetSpace;
    }
    
    /**
     * Starts a new solution for the current turn
     */
    
    public void startNewSolution(String currentPlayer)
    {
        currentSolution = new Solution(currentPlayer);
        ///boolean robotOnSquare = false;
    }
    
    
    public static Icon getRandomTarget()
    {
        return chosenIcon;
    }
    
    //Return current random target space
    public static String getCurrentRandTarget()
    {
        return chosenTargetSpace;
    }
    
    /**
     * Chooses and returns a random target space
     */
    public static String getRandomTargetSpace()
    {
        HashMap<Integer, String> indexMap = new HashMap<>();
        indexMap.put(0, "redCircle");
        indexMap.put(1, "redSquare");
        indexMap.put(2, "greenTriangle");
        indexMap.put(3, "blueTriangle");
        indexMap.put(4, "yellowStar");
        indexMap.put(5, "greenStar");
        indexMap.put(6, "blueSquare");
        indexMap.put(7, "yellowCircle");
        indexMap.put(8, "multivortex");
        indexMap.put(9, "blueCircle");
        indexMap.put(10, "yellowSquare");
        indexMap.put(11, "greenSquare");
        indexMap.put(12, "redTriangle");
        indexMap.put(13, "greenCircle");
        indexMap.put(14, "redStar");
        indexMap.put(15, "blueStar");
        indexMap.put(16, "yellowTriangle");
        
        HashMap<String, Boolean> chosenMap = new HashMap<>();
        chosenMap.put("redCircle", false);
        chosenMap.put("redSquare", false);
        chosenMap.put("greenTriangle", false);
        chosenMap.put("blueTriangle", false);
        chosenMap.put("yellowStar", false);
        chosenMap.put("greenStar", false);
        chosenMap.put("blueSquare", false);
        chosenMap.put("yellowCircle", false);
        chosenMap.put("multivortex", false);
        chosenMap.put("blueCircle", false);
        chosenMap.put("yellowSquare", false);
        chosenMap.put("greenSquare", false);
        chosenMap.put("redTriangle", false);
        chosenMap.put("greenCircle", false);
        chosenMap.put("redStar", false);
        chosenMap.put("blueStar", false);
        chosenMap.put("yellowTriangle", false);
        
        Boolean targetSpaceSelected = false;
        String randomTargetSpace = null;
        while(targetSpaceSelected == false) {
            Random rand = new Random();
            int index = rand.nextInt(17);
            randomTargetSpace = indexMap.get(index);
            
            Boolean chosenAlready = chosenMap.get(randomTargetSpace);
            if(chosenAlready == false) {
                targetSpaceSelected = true;
            }
        }
        
        return randomTargetSpace;
    }
}
