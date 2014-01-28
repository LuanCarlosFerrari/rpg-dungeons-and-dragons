package rpcharacter;

import java.util.Map;
import utils.*;

/**
 *
 * @author minahilIkram
 */
public class RPFighter extends RPCharacter {
    
    
    /*
     * ------------
     * CONSTRUCTORS
     * ------------
     */
    
   /**
     * <p>Default constructor creates an object that represents one journal
     * that only contains organization.</p>
     */
    public RPFighter (String first, String last, int age, int lvl, float spd) {
        super(first, last, age, "FIGHTER", 10, lvl, spd);
    }
    
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
        return (getFirstName() +" " +getLastName()+" has used a skill on "
        +target.getFirstName() +" " +target.getLastName());
    }
    
    /**
    * Creates a new character object based on the given map of data.
    * @param map A Map representation of the character
    * @return Character object
    */
    @Override
    public RPFighter mapToCharacter (Map <String, String> map) {
        RPFighter character = new RPFighter("mona", "is", 12, 12, 12);
        
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