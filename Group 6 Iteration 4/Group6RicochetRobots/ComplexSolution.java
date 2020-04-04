import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Write a description of class Solution here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class ComplexSolution
{
    // instance variables - replace the example below with your own
    private int movesSoFar;
    private int playerNum;
    //hint
  
    ///
    
    ///
    
    /**
     * Constructor for objects of class Solution
     */
    public ComplexSolution(String currentPlayer)
    {
        movesSoFar = 0;
        playerNum = Integer.parseInt(currentPlayer.substring(6,7));
        ///System.out.println(playerNum);
    }

    /**
     * Increases the counter of moves made so far for the current solution 
     * by 1
     */
    public void addToMoves()
    {
        movesSoFar += 1;
        checkStatus();
    }
    
    private void checkStatus()
    {
        if (movesSoFar <= ComplexMap.getBidValue())
        {
            if ((ComplexMap.getBoardSquare()[ComplexMap.getCurrentRobot().getrLocx()][ComplexMap.getCurrentRobot().getrLocy()].getTargetSpace()) != null)
            {
                if ((ComplexMap.getBoardSquare()[ComplexMap.getCurrentRobot().getrLocx()][ComplexMap.getCurrentRobot().getrLocy()].getTargetSpace())
                    .equals(ComplexMap.getCurrentTargetString()))  
                {
                    ///For correct robot colour and correct target colour
                    if ((ComplexMap.getBoardSquare()[ComplexMap.getCurrentRobot().getrLocx()][ComplexMap.getCurrentRobot().getrLocy()].getSquareColour())
                    .equals(ComplexMap.getCurrentRobot().getColour()))
                    {
                        if(checkIfValid() == true)
                        {
                            handleSuccessfulSolution();
                        }
                        else
                        {
                            handleUnsuccessfulSolution();
                        }
                    }
                    
                    ///To cater for multivortex
                    if ((ComplexMap.getBoardSquare()[ComplexMap.getCurrentRobot().getrLocx()][ComplexMap.getCurrentRobot().getrLocy()].getSquareColour())
                    .equals("multi"))
                    {
                        if(checkIfValid() == true)
                        {
                            handleSuccessfulSolution();
                        }
                        else
                        {
                            handleUnsuccessfulSolution();
                        }
                    }
                }
            }
        } else {
            handleUnsuccessfulSolution();
        }
    }
    
    private void handleSuccessfulSolution()
    {
        ///System.out.println("Success");
        updateScore();
        ComplexMap.setCurrentSolved(true);
        ComplexMap.updateCurrentTarget();
        ComplexMap.resetEastLayout();
    }
    
    private void handleUnsuccessfulSolution()
    {
        ///System.out.println("Exceeded moves");
        movesSoFar = 0;
        ComplexMap.setStillCurrentTarget(true);
        ComplexMap.setCurrentSolved(false);
        ComplexMap.resetEastLayout();
    }
    
    private boolean checkIfValid()
    {
        //Check if robot ricocheted at least once
        if(movesSoFar > 1)
        {
            //Check if the actual number of moves made is less than or 
            //equal to the bid entered
            if(ComplexMap.getBidValue() < movesSoFar)
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        else
        {
            return false;
        }
    }
    
    private void updateScore()
    {
        JLabel[] tempPScore = ComplexMap.getPlayerScoresLabel();
        int[] tempScore = ComplexMap.getPlayerScores();
        
        int previousScore = 0;
        previousScore = tempScore[playerNum - 1];
        
        tempScore[playerNum - 1] = previousScore + 10; //Using an incrementer of 10 for every successfull demonstration

        //Update corresponding label
        tempPScore[playerNum - 1].setText("Player " + playerNum + ": " + tempScore[playerNum - 1]);
        
        ////To announce winner after a player has reached a score of 50
        int num = 0;
        for (int i = 0; i < 10; i = i + 3)
        {
            if ((Boolean)StartFrame.getPlayerData().get(i))
            {
                if (tempScore[num] >= 10)
                {
                    ComplexMap.endGame.doClick();
                }
                num++;
            }
        }
    }
}
