package utils;

import java.util.Random;

/**
 * <p>Helper class for simulating various dice-rolling scenarios.</p> 
 * 
 * @author mikram
 */
public class DieRoller {
 
    /*
     * ------------------------
     * DICE-ROLLING OPERATIONS
     * ------------------------
     */
    
    /**
     * <p>Perform a roll according to the current values for the number of dice 
     * and the number of sides of each die.</p>
     * 
     * @return Array of all the rolled values
     */
    private static int[] roll (Die die, int n) {
        int[] rolls = new int[n];
        for (int i = 0; i < n; i++) {            
            rolls[i] = 1 + new Random(System.nanoTime()).nextInt(die.getMaxFaces()); /* Shift by 1 to adjust for the range */
            //System.out.printf("\tLOG (DieRoller): Roll %d = %d\n", i+1, rolls[i]);                        
        }
        return rolls;
    }
    
    /**
     * <p>Perform a standard roll according to current parameters 
     * and summate the rolled values.</p>
     * 
     * @return Value of the summation of all the rolls
     */
    public static int rollNormal (Die die, int n) {        
        int total = 0;
        int[] rolls = roll(die, n);
        for (int i = 0; i < rolls.length; i++) {                        
            total = total + rolls[i];            
        }
        return total;
    }
     
    /**
     * <p>Generate a value for use in an ability score using the Standard
     * approach, as stated in the Pathfinder Core Rulebook.</p>
     * @return Value of the rolled ability score
     */
    public static int generateAbilityScoreStandard () {                        
        int total = 0;
        int[] abilityScore;
        int i;
        int min = 0;
        Die die = new Die();
        int n;
        
        //Sets number of sides to 6 & number of dice to 4
        n = 4;
        
        //Calls roll function to return values
        abilityScore = roll(die, n);
        
        //Looks for the lowest value
        for (i=0; i<n; i++) {
            if(abilityScore[min] > abilityScore[i]) {
                min = i;
            }
        }
        
        //Adds the top 3 values rolled
        for (i=0; i<n; i++) {
            if (i != min) {
                total = total + abilityScore[i]; 
            }
        }
        
        return total;
    }
}