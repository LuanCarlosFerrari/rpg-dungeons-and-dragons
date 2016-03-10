package rpcharactermanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;
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
     * <p>user input first name of character.</p>
     */
    private static String firstName;
    
    /**
     * <p>user input last name of character.</p>
     */
    private static String lastName;
    
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
        String command;
        
        FileIO fileIO = new FileIO(DATA_FILE_PATH);

        System.out.println("Welcome to the Roleplaying Character Manager!\n\n"
                + "Loading characters from file " + DATA_FILE_PATH + " ...");
        if ((characterList = fileIO.readCharactersFromFile()) == null) {
            System.out.println("No characters were added to the list. " 
                    + "No character save file found.");
            characterList = new ArrayList<>();
        }
        do {
            System.out.println("\nPlease choose from one of the following options:" 
                    + "\n[C]reate a new character" 
                    + "\n[E]dit an existing character" 
                    + "\n[P]rint all the characters" 
                    + "\n[S]arch for a specific character" 
                    + "\n[Sim]mulate for fun" 
                    + "\n[Q]uit\n");
            command = keyboard.nextLine();  /* saved input into command variable */

            if (command.equalsIgnoreCase("c")
                || command.equalsIgnoreCase("create")) {
                    create();
            }
            
            else if (command.equalsIgnoreCase("e") 
                || command.equalsIgnoreCase("edit")) {
                    edit();
            }
            
            else if (command.equalsIgnoreCase("p") 
                || command.equalsIgnoreCase("print")) {
                    print();
            }
            
            else if (command.equalsIgnoreCase("s") 
                || command.equalsIgnoreCase("search")) {
                    search();
            }
            
            else if (command.equalsIgnoreCase("sim") 
                || command.equalsIgnoreCase("simulation")) {
                    simulation();
            }
            else if (command.equalsIgnoreCase("q") 
                || command.equalsIgnoreCase("quit")) {
                    quit();
                    break;
            }
            else {
                System.out.println("Sorry, wrong input. Please try again.");
            }
        } while (!(command.equalsIgnoreCase("quit")
                || command.equalsIgnoreCase("q")));
    }
    
    /**
     * <p>Create method is used for creating a new character with the provided name, and age.
     * Ability scores will not be added here.</p>
     */
    public static void create() {
        int iSpeed = 0, iLevel = 0, iAge;
        String characterClass;
        
        do {
            System.out.println("\nWhat class of character would you like? (Fighter, Wizard or Bard):");
            characterClass = keyboard.nextLine();
        } while (!validateClass(characterClass));
        
        do {
            do {
                System.out.println("Enter first name:");
                firstName = keyboard.nextLine();
            } while(!validateName(firstName));

            do {
                System.out.println("Enter last name:");
                lastName = keyboard.nextLine();
            } while(!validateName(lastName));
        } while (!(isNameUnique((firstName + " " + lastName))));
        
        do {
            System.out.println("Enter age (at least 5 years):");
            age = keyboard.nextLine();
        } while(!validateAge(age));
        iAge = Integer.parseInt(age);
        
        do {
            do {
                System.out.println("Enter character level (between 0 and 18): ");
                level = keyboard.nextLine();
            } while (!numbersOnly(level));
            if (!(level.isEmpty())) {
                iLevel = Integer.parseInt(level);
                if(iLevel < 1 || iLevel > 18)
                    System.out.println("Level must be between 0 and 18");
            }
            else
                iLevel = 1;
        } while (!(iLevel < 1 || iLevel > 18));
        
        do {
            System.out.println("Enter base speed: ");
            speed = keyboard.nextLine();
        } while (!numbersOnly(speed));
        if (!speed.isEmpty())
            iSpeed = Integer.parseInt(speed);
        
        if (characterClass.equalsIgnoreCase("fighter")) {
            characterList.add(new RPFighter(firstName, lastName, iAge, iLevel, iSpeed));
            System.out.println("Character was sucessfully created!");
        }
        else if (characterClass.equalsIgnoreCase("wizard")) {
            characterList.add(new RPWizard(firstName, lastName, iAge, iLevel, iSpeed));
            System.out.println("Character was sucessfully created!");
        }
        else if (characterClass.equalsIgnoreCase("bard")) {
            characterList.add(new RPBard(firstName, lastName, iAge, iLevel, iSpeed));
            System.out.println("Character was sucessfully created!");
        }
        else
            System.out.println("Character was not sucessfully created.");
    }
    
    /**
     * <p>Edit method is used for editing an existing character with the provided name.</p>
     */
    public static void edit () {
        int iSpeed, iLevel = 0, iAge = 0, matchFound = 0;
        String roll;
        
        if(characterList.isEmpty()) {
            System.out.println("[Error] Please create a character before editing.");
            return;
        }

        do {
            System.out.println("Enter first name: ");
            firstName = keyboard.nextLine();
        } while(!validateName(firstName));

        do {
            System.out.println("Enter last name: ");
            lastName = keyboard.nextLine();
        } while(!validateName(lastName));
        
        for(int i=0;i<characterList.size();i++) {
            if ((firstName +" " +lastName).equalsIgnoreCase(characterList.get(i).getFullName())) {
                matchFound++;
                do {
                    do {
                        System.out.println("Enter age (at least 5 years): ");
                        age = keyboard.nextLine();
                    } while(!numbersOnly(age));
                    if (!(age.isEmpty())) {
                        iAge = Integer.parseInt(age);
                        if (iAge < 5)
                            System.out.println("Age must be atleast 5 years.");
                        else
                            characterList.get(i).setAge(iAge);
                    }
                    else if (age.isEmpty())
                        break;
                } while (iAge < 5);
                
                do {
                    do {
                        System.out.println("Enter character level (between 0 and 18): ");
                        level = keyboard.nextLine();
                    } while (!numbersOnly(level));
                    if (!(level.isEmpty())) {
                        iLevel = Integer.parseInt(level);
                        if(iLevel < 1 || iLevel > 18)
                            System.out.println("Level must be between 0 and 18");
                        else
                            characterList.get(i).setLevel(iLevel);
                    }
                    else if (level.isEmpty())
                        break;
                } while (iLevel < 1 || iLevel > 18);
                
                do {
                    System.out.println("Enter base speed: ");
                    speed = keyboard.nextLine();
                } while(!numbersOnly(speed));
                if (!(speed.isEmpty())) {
                    iSpeed = Integer.parseInt(speed);
                    characterList.get(i).setSpeed(iSpeed);
                }
                
                do {
                    System.out.println("Strength score: " 
                            + characterList.get(i).getStrength() 
                            + "\n [R]e-roll \n");
                    roll = keyboard.nextLine();
                    if (roll.equalsIgnoreCase("r") || roll.equalsIgnoreCase("re-roll")) {
                        characterList.get(i).setStrength(DieRoller.generateAbilityScoreStandard());
                    }
                } while (!roll.isEmpty());
                
                do {
                    System.out.println("Dexterity score: " 
                            + characterList.get(i).getDexterity() 
                            + "\n [R]e-roll \n");
                    roll = keyboard.nextLine();
                    if (roll.equalsIgnoreCase("r") || roll.equalsIgnoreCase("re-roll")) {
                        characterList.get(i).setDexterity(DieRoller.generateAbilityScoreStandard());
                    }
                } while (!roll.isEmpty());
                
                do {
                    System.out.println("Constitution score: " 
                                + characterList.get(i).getConstitution() 
                                + "\n [R]e-roll \n");
                    roll = keyboard.nextLine();
                    if (roll.equalsIgnoreCase("r") || roll.equalsIgnoreCase("re-roll"))
                        characterList.get(i).setConstitution(DieRoller.generateAbilityScoreStandard());
                } while (!roll.isEmpty());
                
                do {
                    System.out.println("Intelligence score: " 
                                + characterList.get(i).getIntelligence() 
                                + "\n [R]e-roll \n");
                    roll = keyboard.nextLine();
                    if (roll.equalsIgnoreCase("r") || roll.equalsIgnoreCase("re-roll"))
                        characterList.get(i).setIntelligence(DieRoller.generateAbilityScoreStandard());
                } while (!roll.isEmpty());
                
                do {
                    System.out.println("Wisdom score: " 
                                + characterList.get(i).getWisdom() 
                                + "\n [R]e-roll \n");
                    roll = keyboard.nextLine();
                    if (roll.equalsIgnoreCase("r") || roll.equalsIgnoreCase("re-roll"))
                        characterList.get(i).setWisdom(DieRoller.generateAbilityScoreStandard());
                } while (!roll.isEmpty());
                
                do {
                    System.out.println("Charisma score: " 
                                + characterList.get(i).getCharisma() 
                                + "\n [R]e-roll \n");
                    roll = keyboard.nextLine();
                    if (roll.equalsIgnoreCase("r") || roll.equalsIgnoreCase("re-roll"))
                        characterList.get(i).setCharisma(DieRoller.generateAbilityScoreStandard());
                } while (!roll.isEmpty());
            }
        }
        if (matchFound == 0)
            System.out.println("There are no characters with the name "
                +"\"" +firstName +" " +lastName +"\"");
        
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
            System.out.println(A.walk(new Random(System.nanoTime()).nextInt(7)));
        
        for (int i=0; i<(new Random(System.nanoTime()).nextInt()); i++)
            System.out.println(B.run(new Random(System.nanoTime()).nextInt(7)));
        
        if (A.getCharacterClass().equalsIgnoreCase("FIGHTER")) {
            F = (RPFighter)A;
            System.out.println(F.attack(B));
        }
        if (A.getCharacterClass().equalsIgnoreCase("BARD")) {
            D = (RPBard)A;
            System.out.println(D.attack(B));
        }
        if (B.getCharacterClass().equalsIgnoreCase("FIGHTER")) {
            F = (RPFighter)B;
            System.out.println(F.attack(A));
        }
        if (B.getCharacterClass().equalsIgnoreCase("BARD")) {
            D = (RPBard)B;
            System.out.println(D.attack(A));
        }
        if (A.getCharacterClass().equalsIgnoreCase("FIGHTER")) {
            F = (RPFighter)A;
            System.out.println(F.useSkill(B));
        }
        if (A.getCharacterClass().equalsIgnoreCase("BARD")) {
            D = (RPBard)A;
            System.out.println(D.useSkill(B));
        }      
       if (B.getCharacterClass().equalsIgnoreCase("FIGHTER")) {
            F = (RPFighter)B;
            System.out.println(F.useSkill(A));
        }
       if (B.getCharacterClass().equalsIgnoreCase("BARD")) {
            D = (RPBard)B;
            System.out.println(D.useSkill(A));
        }
       if (A.getCharacterClass().equalsIgnoreCase("WIZARD")) {
            W = (RPWizard)A;
            System.out.println(W.castSpell(B));
        }
       if (A.getCharacterClass().equalsIgnoreCase("BARD")) {
            D = (RPBard)A;
            System.out.println(D.castSpell(B));
        }
       if (B.getCharacterClass().equalsIgnoreCase("WIZARD")) {
            W = (RPWizard)B;
            System.out.println(W.castSpell(A));
        }
       if (B.getCharacterClass().equalsIgnoreCase("BARD")) {
            D = (RPBard)B;
            System.out.println(D.castSpell(A));
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
        
        System.out.println("[Shutdown] Saving all characters to file " + DATA_FILE_PATH + " ...");        
        if (!characterList.isEmpty())
            fileIO.writeDataToFile(mappedList, DATA_FILE_PATH);
        
        System.out.println("Goodbye!");
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
                System.out.println("Character with the name " + "\"" + fullName + "\"" 
                        + " already exists." +"\nPlease try again with a different name.");
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
            System.out.println("Name is a required field.");
            return false;
        }
        for (int i=0; i<check.length(); i++) {
            int x = check.charAt(i);
            if (!(x >=65 && x <= 90) && !(x >=97 && x <= 122) && !(x == 39) && !(x == 45)) {
                System.out.println("\"" +check +"\""
                    +" contains characters that are unnacceptable.");
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
            System.out.println("Class is a required field.");
            return false;
        }
        
        else if (validate.equalsIgnoreCase("fighter") ||
                (validate.equalsIgnoreCase("wizard")) ||
                (validate.equalsIgnoreCase("bard")))
            return true;
        else {
            System.out.println("\"" +validate +"\""
                    +" is not an acceptable class.");
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
            System.out.println("Age is a required field.");
            return false;
        }
        else {
            intAge = Integer.parseInt(inputAge);
            if(intAge < 5) {
                System.out.println("Character must be atleast 5 years in age."
                    +"\nPlease try again.");
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
                    System.out.println("Only numbers are accepted in this category");
                    return false;
                }
            }
        return true;
    }
}