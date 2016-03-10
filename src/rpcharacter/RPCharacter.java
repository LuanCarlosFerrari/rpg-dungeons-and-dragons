package rpcharacter;

import java.util.HashMap;
import java.util.Map;
import utils.Die;
import utils.DieRoller;

/**
 * <p>Representation of a character that can be used in a role-playing game.</p>
 * 
 * @author mikram
 */
public class RPCharacter {

    /*
     * -------------------------
     * INSTANCE/MEMBER VARIABLES
     * -------------------------
     */

    /**
     * <p>First name of the character.</p>
     */
    private String firstName;
    /**
     * <p>Last name of the character.</p>
     */
    private String lastName;
    /**
     * <p>Age of the character (must be at least 5 years old).</p>
     */
    private int age;
    
    // Credits - The following ability descriptions were taken straight from the Pathfinder Core Rulebook
    /**
     * <p>Character's muscle and physical power.</p>
     */
    private int strength;
   
    /**
     * <p>Character's agility, reflexes and balance.</p>
     */
    private int dexterity;
    
    /**
     * <p>Character's health and stamina.</p>
     */
    private int constitution;
    
    /**
     * <p>Character's learning and reasoning.</p>
     */
    private int intelligence;
    
    /**
     * <p>Character's willpower, common sense, awareness, and intuition.</p>
     */
    private int wisdom;
    
    /**
     * <p>Character's personality, personal magnetism, ability to lead, and appearance..</p>
     */
    private int charisma;
    
    /**
     * <p>The general level of power and skill of the character
     * (between 1 and 18, but upper bound need not be enforced).</p>
     */
    private int level;
    
    /**
     * <p>Base speed ; how much distance (feet) the character covers in one
     * move / minute (again , units are irrelevant).</p>
     */
    private float speed;
    
    /**
     * <p>Represents how hard it is to hit the character upon being attacked.</p>
     */
    private int armorClass;
    
    private final String characterClass;
    
    private final Die hitDie;
    
    HashMap abilities = new HashMap();
    
    /*
     * ------------
     * CONSTRUCTORS
     * ------------
     */
    
    /**
     * <p>Default constructor creates an object that represents one character
     * with name John Smith of age 5, sets ability scores to 0.</p>
     */
    public RPCharacter () {
        this("John", "Smith", 5, "", 0, 0, 0);
    }
    
    /**
     * <p>Standard constructor creates an object according to the specified 
     * name, and age. Sets Ability scores to 0.</p>
     * 
     * @param first First name of character
     * @param last Last name of character
     * @param age Age of character
     */
    public RPCharacter (String first, String last, int age, String cClass, int d, int lvl, float spd) {
        this.firstName = first;
        this.lastName = last;
        this.age = age;
        this.abilities.put ("STR", DieRoller.generateAbilityScoreStandard());
        this.abilities.put ("DEX", DieRoller.generateAbilityScoreStandard());
        this.abilities.put ("CON", DieRoller.generateAbilityScoreStandard());
        this.abilities.put ("INT", DieRoller.generateAbilityScoreStandard());
        this.abilities.put ("WIS", DieRoller.generateAbilityScoreStandard());
        this.abilities.put ("CHA", DieRoller.generateAbilityScoreStandard());
        this.level = lvl;
        this.speed = spd;
        this.characterClass = cClass;
        this.hitDie = new Die(4);
    }
    
    
    /*
     * --------
     * MUTATORS
     * --------
     */
    
    /**
     * <p>Sets the name of character after validation.</p>
     * 
     * @param first First name of character
     * @return <code>true</code> if the name was added; <code>false</code> if the assignment failed
     */  
    public boolean setFirstName (String first) {
        this.firstName = first;
        return true;
        }
    
    /**
     * <p>Sets the name of character after validation.</p>
     * 
     * @param last Last name of character
     * @return <code>true</code> if the name was added; <code>false</code> if the assignment failed
     */ 
    public boolean setLastName (String last) {
            this.lastName = last;
        return true;
    }
    
    /**
     * <p>Sets the age of character.</p>
     * 
     * @param age Age of character
     * @return <code>true</code> if the age was added; <code>false</code> if the assignment failed
     */ 
    public boolean setAge (int age) {
        if (age >= 5) {
            this.age = age;
            return true;
        }
        return false;
    }
    
    /**
     * <p>Sets the Strength ability score for the character.</p>
     * 
     * @param str Value for the character's Strength
     * @return <code>true</code> if the new value was assigned; <code>false</code> if the assignment failed
     */
    public boolean setStrength (int str) {
        this.abilities.put("STR", str);
        return true;
    }
     
    /**
     * <p>Sets the Dexterity ability score for the character.</p>
     * 
     * @param dex Value for the character's dexterity
     * @return <code>true</code> if the new value was assigned; <code>false</code> if the assignment failed
     */
    public boolean setDexterity (int dex) {
        this.abilities.put("DEX", dex);
        this.setArmorClass(10+(((int)this.abilities.get("DEX"))/4));
        return true;
    }
    
     /**
     * <p>Sets the Constitution ability score for the character.</p>
     * 
     * @param con Value for the character's Constitution
     * @return <code>true</code> if the new value was assigned; <code>false</code> if the assignment failed
     */
    public boolean setConstitution (int con) {
        this.abilities.put("CON", con);
        return true;
    }
    
     /**
     * <p>Sets the Intelligence ability score for the character.</p>
     * 
     * @param intel Value for the character's Intelligence
     * @return <code>true</code> if the new value was assigned; <code>false</code> if the assignment failed
     */
    public boolean setIntelligence (int intel) {
        this.abilities.put("INT", intel);
        return true;
    }
    
    /**
     * <p>Sets the Wisdom ability score for the character.</p>
     * 
     * @param wis Value for the character's Wisdom
     * @return <code>true</code> if the new value was assigned; <code>false</code> if the assignment failed
     */
    public boolean setWisdom (int wis) {
        this.abilities.put("WIS", wis);
        return true;
    }
    
    /**
     * <p>Sets the Charisma ability score for the character.</p>
     * 
     * @param chr Value for the character's Charisma
     * @return <code>true</code> if the new value was assigned; <code>false</code> if the assignment failed
     */
    public boolean setCharisma (int chr) {
        this.abilities.put("CHA", chr);
        return true;
    }
    
    /**
     * <p>Sets the level for the character.</p>
     * 
     * @param lev Value for the character's Level
     * @return <code>true</code> if the new value was assigned; <code>false</code> if the assignment failed
     */
    public boolean setLevel (int lev) {
        this.level = lev;
        return true;
    }
    
    /**
     * <p>Sets the speed for the character.</p>
     * 
     * @param lev Value for the character's Speed
     * @return <code>true</code> if the new value was assigned; <code>false</code> if the assignment failed
     */
    public boolean setSpeed (float speed) {
        this.speed = speed;
        return true;
    }
    
    /**
     * <p>Retrieves the armor class of the character.</p>
     * @return Armor Class of the character
     */
    public boolean setArmorClass (int AC) {
        this.armorClass = AC;
        return true;
    }
    

    /*
     * ---------
     * ACCESSORS
     * ---------
     */
    
    /**
     * <p>Retrieves the first name of the character.</p>
     * @return First name of the character
     */
    public String getFirstName () {
        return this.firstName;
    }
    
    /**
     * <p>Retrieves the last name of the character.</p>
     * @return Last name of the character
     */
    public String getLastName () {
        return this.lastName;
    }
    
    /**
     * <p>Retrieves the age of the character.</p>
     * @return Age of the character
     */
   public int getAge () {
        return this.age;
    }
    
    /**
     * <p>Retrieves the strength of the character.</p>
     * @return Strength of the character
     */
    public int getStrength () {
        return (int)this.abilities.get("STR");
    }
    
    /**
     * <p>Retrieves the dexterity of the character.</p>
     * @return Dexterity of the character
     */
    public int getDexterity () {
        return (int)this.abilities.get("DEX");
    }
    
    /**
     * <p>Retrieves the constitution of the character.</p>
     * @return Constitution of the character
     */
    public int getConstitution () {
        return (int)this.abilities.get("CON");
    }
    
     
    /**
     * <p>Retrieves the intelligence of the character.</p>
     * @return Intelligence of the character
     */
    public int getIntelligence () {
        return (int)this.abilities.get("INT");
    }
    
    /**
     * <p>Retrieves the wisdom of the character.</p>
     * @return Wisdom of the character
     */
    public int getWisdom () {
        return (int)this.abilities.get("WIS");
    }
    
    /**
     * <p>Retrieves the charisma of the character.</p>
     * @return Charisma of the character
     */
    public int getCharisma () {
        return (int)this.abilities.get("CHA");
    }
    
    /**
     * <p>Retrieves the level of the character.</p>
     * @return level of the character
     */
    public int getLevel () {
        return this.level;
    }
    
    /**
     * <p>Retrieves the speed of the character.</p>
     * @return Speed of the character
     */
    public float getSpeed () {
        return this.speed;
    }
    
    /**
     * <p>Retrieves the armor class of the character.</p>
     * @return Armor Class of the character
     */
    public int getArmorClass () {
        return this.armorClass;
    }
    
    public Die getHitDie () {
        return this.hitDie;
    }
    
    public String getCharacterClass () {
        return this.characterClass;
    }
    
    public String getFullName () {
        return (this.firstName +" " +this.lastName);
    }
    
    
    /*
     * -------------
     * OTHER METHODS
     * -------------
     */
    
    // Walk in the specified direction
    public String walk (int direction) {
        String dir;
        dir = this.convertDirection(direction);
        return (this.getFullName() +" has walked " +this.speed +"' " +dir +".");
    }
    
    // Run in the specified direction
    public String run (int direction) {
        String dir;
        dir = this.convertDirection(direction);
        return (this.getFullName() +" has walked " +this.speed*3 +"' " +dir +".");
    }
    
    public String convertDirection (int direction) {
        switch(direction) {
            case 0:
                return "N";
            case 1:
                return "NE";
            case 2:
                return "E";
            case 3:
                return "SE";
            case 4:
                return "S";
            case 5:
                return "SW";
            case 6:
                return "W";
            case 7:
                return "NW";       
        }
        return "no direction";
    }
    
     /**
     * <p>Creates a string for each character.</p>
     * @return the string created
     */
    @Override
    public String toString() {
        
        String character = "[character]"
                +"\nclass=" +this.getCharacterClass()
                +"\nfirstName=" +this.getFirstName()
                +"\nlastName=" +this.getLastName()
                +"\nage=" +this.getAge()
                +"\nSTR=" +this.getStrength()
                +"\nDEX=" +this.getDexterity()
                +"\nCON=" +this.getConstitution()
                +"\nINT=" +this.getIntelligence()
                +"\nWIS=" +this.getWisdom()
                +"\nCHA=" +this.getCharisma()
                +"\nlevel=" +this.getLevel()
                +"\nspeed=" +this.getSpeed();
        
        return character;
        
    }
    
    
    /**
    * Wraps the character object around a map.
    * @return A Map representation of the object
    */
    public HashMap<String, String> toMap () {
        HashMap<String, String> map = new HashMap();
        
        map.put("class", this.getCharacterClass());
        map.put("firstName", this.firstName);
        map.put("lastName", this.lastName);
        map.put("age", Integer.toString(this.age));
        map.put("strength", Integer.toString(this.strength));
        map.put("dexterity", Integer.toString(this.dexterity));
        map.put("constitution", Integer.toString(this.constitution));
        map.put("intelligence", Integer.toString(this.intelligence));
        map.put("charisma", Integer.toString(this.charisma));
        map.put("wisdom", Integer.toString(this.wisdom));
        map.put("level", Integer.toString(this.level));
        map.put("speed", Float.toString(this.speed));
        return map;
    }
    
    /**
    * Creates a new character object based on the given map of data.
    * @param map A Map representation of the character
    * @return Character object
    */
    public RPCharacter mapToCharacter (Map <String, String> map) {
        RPCharacter character = new RPCharacter();
        while (!map.isEmpty()) {
            character.firstName = map.get("firstName");
            character.lastName = map.get("lastName");
            character.age = Integer.parseInt(map.get("age"));
            character.strength = Integer.parseInt(map.get("strength"));
            character.dexterity = Integer.parseInt(map.get("dexterity"));
            character.constitution = Integer.parseInt(map.get("constitution"));
            character.charisma = Integer.parseInt(map.get("charisma"));
            character.wisdom = Integer.parseInt(map.get("wisdom"));
            character.intelligence = Integer.parseInt(map.get("intelligence"));
            character.level = Integer.parseInt(map.get("level"));
            character.speed = Float.parseFloat(map.get("speed"));
            return character;
        }
        return character;
    }
}