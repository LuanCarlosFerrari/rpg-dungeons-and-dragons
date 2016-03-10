package rpcharacter;

import java.util.Map;

/**
 *
 * @author mikram
 */
public class RPWizard extends RPCharacter {
    
    public RPWizard (String first, String last, int age, int lvl, float spd) {
        super(first, last, age, "WIZARD", 6, lvl, spd);
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
    public RPWizard mapToCharacter (Map <String, String> map) {
        RPWizard character = new RPWizard("", "", 0, 0, 0);
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