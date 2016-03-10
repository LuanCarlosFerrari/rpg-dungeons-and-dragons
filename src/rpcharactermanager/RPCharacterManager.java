package rpcharactermanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;
import rpcharacter.*;
import utils.DieRoller;
import utils.FileIO;

/**
 * A character management utility for table-top role-playing games.
 * 
 * @author mikram
 */
public class RPCharacterManager {
    
    /*
     * -------------------------
     * INSTANCE/MEMBER VARIABLES
     * -------------------------
     */
    
    /**
     * <p>List of characters managed by the application.</p>
     */
    public static List<RPCharacter> characterList = new ArrayList<>();
    
    /**
     * <p>Maximum number of keywords that can be searched,
     * used in the search method.</p>
     */
    private static int MAX_INPUT_KEYWORDS = 10;
    
    /**
     * <p>Path to the file containing saved characters.</p>
     */
    private static final String DATA_FILE_PATH = "build\\classes\\utils\\data\\characters.txt";
    
    /**
     * <p>Accepts user input throughout application.</p>
     */
    private static Scanner keyboard = new Scanner(System.in);
    
    /**
     * <p>user input age of character.</p>
     */
    private static String age;
    
    /**
     * <p>user input level of character.</p>
     */
    private static String level;
    
    /**
     * <p>user input speed of character.</p>
     */
    private static String speed;

    /**
     * <p>Main thread of execution. A main-menu consisting of four options: Create,
     * Edit, Print, Search, Simulate and Exit, is implemented in the form of a command-loop. 
     * <ol>
     *  <li><b>Create</b>: Creates a new character.</li>
     *  <li><b>Edit</b>: Edits an existing character.</li>
     *  <li><b>Print</b>: Displays all existing characters.</li>
     *  <li><b>Search</b>: Search for specific characters.</li>
     *  <li><b>Simulate</b>: Perform a fun simulation between randomly selected characters.</li>
     *  <li><b>Exit</b>: Exits the application.</li>
     * </ol>
     * </p>
     * 
     * @param args The command line arguments
     */
    public static void main(String[] args) {
        FileIO fileIO = new FileIO(DATA_FILE_PATH);
        
        if ((characterList = fileIO.readCharactersFromFile()) == null)
            characterList = new ArrayList<>();
    }
    
    /**
     * <p>Create method is used for creating a new character with the provided name, and age.
     * Ability scores will not be added here.</p>
     */
    public static void create(String characterClass, String first, String last, String age, String level, String speed) {
        int iSpeed = 0, iLevel = 0, iAge;
        
        while(!(validateClass(characterClass)))
            characterClass = JOptionPane.showInputDialog(null, "What class of character would you like? (Fighter, Wizard or Bard): ", "Character Class", 1);

        while(!(validateName(first)))
            first = JOptionPane.showInputDialog(null, "Enter first name: ", "First Name", 1);

        while(!(validateName(last)))
            last = JOptionPane.showInputDialog(null, "Enter last name: ", "Last Name", 1);

            if (!(isNameUnique((first + " " + last)))) {
                while(!(validateName(first)))
                first = JOptionPane.showInputDialog(null, "Enter first name: ", "First Name", 1);

                while(!(validateName(last)))
                last = JOptionPane.showInputDialog(null, "Enter last name: ", "Last Name", 1);
        }

        while(!validateAge(age))
            age = JOptionPane.showInputDialog(null, "Enter age (at least 5 years): ", "Age", 1);
        iAge = Integer.parseInt(age);

        while (iLevel < 1 || iLevel > 18) {
            while (!numbersOnly(level))
                level = JOptionPane.showInputDialog(null, "Enter character level (between 0 and 18): ", "Level", 1);
            if (!(level.isEmpty())) {
                iLevel = Integer.parseInt(level);
                if(iLevel < 1 || iLevel > 18)
                    JOptionPane.showMessageDialog(null, "Level must be between 0 and 18", "Error", 3);
            }
            else
                iLevel = 1;
        }

        while (!numbersOnly(speed))
            speed = JOptionPane.showInputDialog(null, "Enter base speed: ", "Speed", 1);
        if (!speed.isEmpty())
            iSpeed = Integer.parseInt(speed);

        if (characterClass.equalsIgnoreCase("fighter")) {
            characterList.add(new RPFighter(first, last, iAge, iLevel, iSpeed));
            JOptionPane.showMessageDialog(null, "Character successfully added!", "Success", 3);
        }
        else if (characterClass.equalsIgnoreCase("wizard")) {
            characterList.add(new RPWizard(first, last, iAge, iLevel, iSpeed));
            JOptionPane.showMessageDialog(null, "Character successfully added!", "Success", 3);
        }
        else if (characterClass.equalsIgnoreCase("bard")) {
            characterList.add(new RPBard(first, last, iAge, iLevel, iSpeed));
            JOptionPane.showMessageDialog(null, "Character successfully added!", "Success", 3);
        }
        else
            JOptionPane.showMessageDialog(null, "Character was not successfully added!", "Unsuccessful", 3);
    }
    
    /**
     * <p>Edit method is used for editing an existing character with the provided name.</p>
     */
    public static void edit (String first, String last) {
       int iSpeed, iLevel = 0, iAge = 0, matchFound = 0;
        int r;
        
        
        if(characterList.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please create a character before editing.", "Error", 3);
            return;
        }
        
        for(int i=0;i<characterList.size();i++) {
            if ((first + " " +last).equalsIgnoreCase(characterList.get(i).getFullName())) {
                matchFound++;
                do {
                    do {
                        age = JOptionPane.showInputDialog(null, "Enter age (at least 5 years): ", "Age", 1);
                        if (age == null)
                            age = "";
                    } while(!numbersOnly(age));
                    if (!(age.isEmpty())) {
                        iAge = Integer.parseInt(age);
                        if (iAge < 5)
                            JOptionPane.showMessageDialog(null, "Age must be atleast 5 years.", "Error", 3);
                        else
                            characterList.get(i).setAge(iAge);
                    }
                    else if (age.isEmpty())
                        break;
                } while (iAge < 5);
                
                do {
                    do {
                        level = JOptionPane.showInputDialog(null, "Enter character level (between 0 and 18): ", "Level", 1);
                        if (level == null)
                            level = "";
                    } while (!numbersOnly(level));

                    if (!(level.isEmpty())) {
                        iLevel = Integer.parseInt(level);
                        if(iLevel < 1 || iLevel > 18)
                            JOptionPane.showMessageDialog(null, "Level must be between 0 and 18", "Error", 3);
                        else
                            characterList.get(i).setLevel(iLevel);
                    }
                    else if (level.isEmpty())
                        break;
                } while (iLevel < 1 || iLevel > 18);
                
                do {
                    speed = JOptionPane.showInputDialog(null, "Enter base speed: ", "Speed", 1);
                    if (speed == null)
                            speed = "";
                } while (!numbersOnly(speed));
                if (!speed.isEmpty()) {
                    iSpeed = Integer.parseInt(speed);
                    characterList.get(i).setSpeed(iSpeed);
                }
                
                do {
                    r = JOptionPane.showConfirmDialog(null, "Strength score: " 
                            + characterList.get(i).getStrength() 
                            + "\n [R]e-roll \n", "Roll", 0);
                    if (r == JOptionPane.OK_OPTION) {
                        characterList.get(i).setStrength(DieRoller.generateAbilityScoreStandard());
                    }
                } while (!(r == JOptionPane.NO_OPTION));
                
                do {
                    r = JOptionPane.showConfirmDialog(null, "Dexterity score: " 
                            + characterList.get(i).getDexterity() 
                            + "\n [R]e-roll \n", "Roll", 0);
                    if (r == JOptionPane.OK_OPTION) {
                        characterList.get(i).setDexterity(DieRoller.generateAbilityScoreStandard());
                    }
                } while (!(r == JOptionPane.NO_OPTION));
                
                do {
                    r = JOptionPane.showConfirmDialog(null, "Constitution score: " 
                            + characterList.get(i).getConstitution() 
                            + "\n [R]e-roll \n", "Roll", 0);
                    if (r == JOptionPane.OK_OPTION) {
                        characterList.get(i).setConstitution(DieRoller.generateAbilityScoreStandard());
                    }
                } while (!(r == JOptionPane.NO_OPTION));
                
                do {
                    r = JOptionPane.showConfirmDialog(null, "Intelligence score: " 
                            + characterList.get(i).getIntelligence() 
                            + "\n [R]e-roll \n", "Roll", 0);
                    if (r == JOptionPane.OK_OPTION) {
                        characterList.get(i).setIntelligence(DieRoller.generateAbilityScoreStandard());
                    }
                } while (!(r == JOptionPane.NO_OPTION));
                
                do {
                    r = JOptionPane.showConfirmDialog(null, "Wisdom score: " 
                            + characterList.get(i).getWisdom() 
                            + "\n [R]e-roll \n", "Roll", 0);
                    if (r == JOptionPane.OK_OPTION) {
                        characterList.get(i).setWisdom(DieRoller.generateAbilityScoreStandard());
                    }
                } while (!(r == JOptionPane.NO_OPTION));
                
                do {
                    r = JOptionPane.showConfirmDialog(null, "Charisma score: " 
                            + characterList.get(i).getCharisma() 
                            + "\n [R]e-roll \n", "Roll", 0);
                    if (r == JOptionPane.OK_OPTION) {
                        characterList.get(i).setCharisma(DieRoller.generateAbilityScoreStandard());
                    }
                } while (!(r == JOptionPane.NO_OPTION));
            }
        }
        if (matchFound == 0)
            JOptionPane.showMessageDialog(null, "There are no characters with the name "
                +"\"" +first +" " +last +"\"", "Not Found", 3);
        
    }
    
    public static void delete(String first, String last) {
        int matchFound = 0;
        
        if(characterList.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please create a character before deleting.", "Error", 3);
            return;
        }
        
        for(int i=0;i<characterList.size();i++) {
            if ((first + " " +last).equalsIgnoreCase(characterList.get(i).getFullName())) {
                matchFound++;
                characterList.remove(i);
            }
        }
        if (matchFound == 0)
            JOptionPane.showMessageDialog(null, "There are no characters with the name "
                +"\"" +first +" " +last +"\"", "Not Found", 3);
    }
    
    /**
     * <p>Displays all the current characters to screen.</p>
     */
    public static void print() {
        //Cannot print if the list is empty.
        if(characterList.isEmpty()) {
            System.out.println("[Error] Please create a character before printing.");
            return;
        }
        
        //Uses toString method to print list.
        for(int i=0;i<characterList.size();i++) {
            System.out.println("\n-------------\n"
                    +characterList.get(i).toString());
        }
    }
    
    /**
     * <p>Searches through the list for the given character.
     * Displays all the found characters to screen.</p>
     */
    public static void search () {
        boolean matchFound = false;
        int tokenCount = 0, i, j;
        String searchString;
        StringTokenizer searchToken;
        
        String[] tempArray = new String[MAX_INPUT_KEYWORDS];
        
        if(characterList.isEmpty()) {
            System.out.println("[Error] Please create a character before editing.");
            return;
        }
        
        System.out.println("\nPlease enter some keywords seperated by spaces."
                + "\n\nExample: keyword1 keyword2 keyword3");
        keyboard = new Scanner(System.in);
        searchString = keyboard.nextLine();
        
        System.out.println("Searching for the keywords entered... \n");

        //tokenzies user input and saves into temp array
        searchToken = new StringTokenizer(searchString, " ");
        while (searchToken.hasMoreTokens()) {
            tempArray[tokenCount] = searchToken.nextToken();
            tokenCount++;
        }
        
        for (i=0; i<characterList.size(); i++) {
            for (j=0; j<tokenCount; j++) {
                if (tempArray[j] != null) {
                    if (characterList.get(i).getFirstName().equalsIgnoreCase(tempArray[j])) {
                        System.out.println("\nThe following matches were found:");
                        System.out.println("\n-------------\n"
                            +characterList.get(i).toString()
                            +"\n-------------");
                        matchFound = true;
                    }
                    if ((matchFound == false)
                        && (characterList.get(i).getLastName().equalsIgnoreCase(tempArray[j]))) {
                            System.out.println("\nThe following matches were found:");
                            System.out.println("\n-------------\n"
                                +characterList.get(i).toString()
                                +"\n-------------");
                    }
                }
            }
        }
    }
    
    /**
     * <p>Performs a simulation with two random characters.</p>
     */
    public static void simulation () {
        RPCharacter A = characterList.get(new Random(System.nanoTime()).nextInt(characterList.size()));
        RPCharacter B = characterList.get(new Random(System.nanoTime()).nextInt(characterList.size()));
        RPFighter F;
        RPWizard W;
        RPBard D;
        
        for (int i=0; i<(new Random(System.nanoTime()).nextInt()); i++)
            JOptionPane.showMessageDialog(null, A.walk(new Random(System.nanoTime()).nextInt(7)), "Walk", 3);
        
        for (int i=0; i<(new Random(System.nanoTime()).nextInt()); i++)
            JOptionPane.showMessageDialog(null, B.run(new Random(System.nanoTime()).nextInt(7)), "Run", 3);
        
        if (A.getCharacterClass().equalsIgnoreCase("FIGHTER")) {
            F = (RPFighter)A;
            JOptionPane.showMessageDialog(null, F.attack(B), "Attack!", 3);
        }
        if (A.getCharacterClass().equalsIgnoreCase("BARD")) {
            D = (RPBard)A;
            JOptionPane.showMessageDialog(null, D.attack(B), "Attack!", 3);
        }
        if (B.getCharacterClass().equalsIgnoreCase("FIGHTER")) {
            F = (RPFighter)B;
            JOptionPane.showMessageDialog(null, F.attack(A), "Attack!", 3);
        }
        if (B.getCharacterClass().equalsIgnoreCase("BARD")) {
            D = (RPBard)B;
            JOptionPane.showMessageDialog(null, D.attack(A), "Attack!", 3);
        }
        if (A.getCharacterClass().equalsIgnoreCase("FIGHTER")) {
            F = (RPFighter)A;
            JOptionPane.showMessageDialog(null, F.useSkill(B), "Skill!", 3);
        }
        if (A.getCharacterClass().equalsIgnoreCase("BARD")) {
            D = (RPBard)A;
            JOptionPane.showMessageDialog(null, D.useSkill(B), "Skill!", 3);
        }      
       if (B.getCharacterClass().equalsIgnoreCase("FIGHTER")) {
            F = (RPFighter)B;
            JOptionPane.showMessageDialog(null, F.useSkill(A), "Skill!", 3);
        }
       if (B.getCharacterClass().equalsIgnoreCase("BARD")) {
            D = (RPBard)B;
            JOptionPane.showMessageDialog(null, D.useSkill(A), "Skill!", 3);
        }
       if (A.getCharacterClass().equalsIgnoreCase("WIZARD")) {
            W = (RPWizard)A;
            JOptionPane.showMessageDialog(null, W.castSpell(B), "Spell!", 3);
        }
       if (A.getCharacterClass().equalsIgnoreCase("BARD")) {
            D = (RPBard)A;
            JOptionPane.showMessageDialog(null, D.castSpell(B), "Spell!", 3);
        }
       if (B.getCharacterClass().equalsIgnoreCase("WIZARD")) {
            W = (RPWizard)B;
            JOptionPane.showMessageDialog(null, W.castSpell(A), "Spell!", 3);
        }
       if (B.getCharacterClass().equalsIgnoreCase("BARD")) {
            D = (RPBard)B;
            JOptionPane.showMessageDialog(null, D.castSpell(A), "Spell!", 3);
        }
        
    }
    
    /**
     * <p>Saves all the data to file and quits the program.</p>
     */
    public static void quit() {
        ArrayList<HashMap<String,String>> mappedList = new ArrayList<>();
        FileIO fileIO = new FileIO(DATA_FILE_PATH);
        
        for (int i=0; i<characterList.size(); i++)
            mappedList.add(characterList.get(i).toMap());
              
        if (!characterList.isEmpty())
            fileIO.writeDataToFile(mappedList, DATA_FILE_PATH);
    }
    
    /**
     * <p>Checks if the name entered already exists in array.</p>
     * @param fullName accepts the fullName of the character entered
     * @return <code>true</code> if the list is empty or if name does not exist;
     * <code>false</code> if name exists
     */
    public static boolean isNameUnique(String fullName) {
        //If list is empty no need to check if name exists.
        if(characterList.isEmpty())
            return true;
        
        //Searches through the list for name combination.
        for(int i=0;i<characterList.size();i++) {
            if (fullName.equalsIgnoreCase(characterList.get(i).getFullName())) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * <p>Checks if the name entered contains letters, ''',  and '-' only.</p>
     * @param check accepts a string of the name to check
     * @return <code>true</code> if the name is acceptable;
     * <code>false</code> if name is not acceptable
     */
    public static boolean validateName(String check) {
        if (check == null || check.isEmpty()) {
            return false;
        }
        for (int i=0; i<check.length(); i++) {
            int x = check.charAt(i);
            if (!(x >=65 && x <= 90) && !(x >=97 && x <= 122) && !(x == 39) && !(x == 45)) {
                JOptionPane.showMessageDialog(null, "Name contains characters that are unacceptable", "Name", 3);
                return false;
            }
        }
        return true;
    }
    
    /**
     * <p>Checks if the class entered meets the three classes.</p>
     * @param validate accepts a string of the class to check
     * @return <code>true</code> if the class is acceptable;
     * <code>false</code> if class is not acceptable
     */
    public static boolean validateClass (String validate) {
        if (validate == null || validate.isEmpty()) {
            return false;
        }
        
        else if (validate.equalsIgnoreCase("fighter") ||
                (validate.equalsIgnoreCase("wizard")) ||
                (validate.equalsIgnoreCase("bard")))
            return true;
        else {
            JOptionPane.showMessageDialog(null, "Fields; Fighter, Wizard & Bard, are only acceptable", "Character Class", 3);
            return false;
        }
    }
    
    /**
     * <p>Checks if the age entered contains numbers, larger then 5 and is not empty.</p>
     * @param validate accepts a string of the age to check
     * @return <code>true</code> if the age is acceptable;
     * <code>false</code> if class is age acceptable
     */
    public static boolean validateAge(String inputAge) {
        int intAge;
        
        if ( inputAge == null || inputAge.isEmpty()) {
            return false;
        }
        else {
            intAge = Integer.parseInt(inputAge);
            if(intAge < 5) {
                JOptionPane.showMessageDialog(null, "Age must be greater than 5", "Age", 3);
                return false;
            }
        }
        return numbersOnly(inputAge);
    }
  
    /**
     * <p>Checks if the string entered only contains numbers.</p>
     * @param validate accepts a string to check
     * @return <code>true</code> if the string is acceptable;
     * <code>false</code> if class is string acceptable
     */
    public static boolean numbersOnly (String numberCheck) {
        if (numberCheck != null && !numberCheck.isEmpty()) {
            for (int i=0; i<numberCheck.length(); i++)
                if (numberCheck.charAt(i) < 48 || numberCheck.charAt(i) > 57) {
                    JOptionPane.showMessageDialog(null, "Fields; Age, Level & Speed require numbers only", "Numbers", 3);
                    return false;
                }
            }
        return true;
    }
}