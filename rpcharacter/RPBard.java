package rpcharacter;

import java.util.Map;
import utils.*;

/**
 * <p>Representation of a bard character that can be used in a role-playing game.</p>
 * 
 * @author mikram
 */
public class RPBard extends RPCharacter {
    
     /*
     * ------------
     * CONSTRUCTORS
     * ------------
     */
    
    /**
     * <p>Constructor creates an object according to the specified 
     * name, and age, level and speed.</p>
     * 
     * @param first First name of character
     * @param last Last name of character
     * @param age Age of character
     * @param lvl Level of character
     * @param spd Speed of character
     */
    public RPBard (String first, String last, int age, int lvl, float spd) {
        super(first, last, age, "BARD", 8, lvl, spd);
    }
    
    /*
     * -------------
     * OTHER METHODS
     * -------------
     */
    
    /**
     * <p>Creates a string for attack result.</p>
     * @param target Target character to attack
     * @return the string created
     */
    public String attack (RPCharacter target) {
        int value;
        int attackValue;
        Die die = new Die(20);
        int d;
        int total;
        int damage;
        value = die.getFaceValue();
        attackValue = value +(getStrength()*4);
        
        if (attackValue >= target.getArmorClass()) {
            d = getLevel();
            total = DieRoller.rollNormal(getHitDie(), d);
            damage = total +((getStrength())/8);
            return (getFirstName() +" " +getLastName()
            +" has attempted an attack on " +target.getFirstName()
            +" "+target.getLastName() +" and has succeeded to cause a damage of " +damage +".");
        }
        else
            return (getFirstName() +" " +getLastName()
            +" has attempted an attack on " +target.getFirstName()
            +" "+target.getLastName() +" and has failed.");
    }
    
    public String useSkill (RPCharacter target) {
        return (getFirstName() +" " +getLastName() +" has used a skill on "
        +target.getFirstName() +" " +target.getLastName());
    }
    
    public String castSpell (RPCharacter target) {
        return (getFirstName() +" " +getLastName() +" has cast a spell on "
        +target.getFirstName() +" " +target.getLastName());
    }
    
    /**
    * Creates a new character object based on the given map of data.
    * @param map A Map representation of the character
    * @return Character object
    */
    @Override
    public RPBard mapToCharacter (Map <String, String> map) {
        RPBard character = new RPBard("mona", "is", 12, 12, 12);
            
            character.setFirstName(map.get("firstName"));
            character.setLastName(map.get("lastName"));
            character.setAge(Integer.parseInt(map.get("age")));
            character.setStrength(Integer.parseInt(map.get("strength")));
            character.setDexterity(Integer.parseInt(map.get("dexterity")));
            character.setConstitution(Integer.parseInt(map.get("constitution")));
            character.setCharisma(Integer.parseInt(map.get("constitution")));
            character.setWisdom(Integer.parseInt(map.get("wisdom")));
            character.setIntelligence(Integer.parseInt(map.get("intelligence")));
            character.setLevel(Integer.parseInt(map.get("level")));
            character.setSpeed(Float.parseFloat(map.get("speed")));
            
        return character;
    }
}
