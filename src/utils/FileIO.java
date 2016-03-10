package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import rpcharacter.*;;

/**
 * Handles the reading/writing of data from/to files.
 * 
 * @author mikram
 */
public class FileIO {
    
    /**
     * String containing the data file path
     */
    private static final String CHARACTER_FIELD_HEADER = "[character]";
    private static final String CHARACTER_FIELD_KEYVAL_SEP = "=";
    private static final String CHARACTER_FIELD_CLASS = "class";
    private static final String CHARACTER_FIELD_FNAME = "firstName";
    private static final String CHARACTER_FIELD_LNAME = "lastName";
    private static final String CHARACTER_FIELD_AGE = "age";
    private static final String CHARACTER_FIELD_STR = "STR";
    private static final String CHARACTER_FIELD_DEX = "DEX";
    private static final String CHARACTER_FIELD_CON = "CON";
    private static final String CHARACTER_FIELD_INT = "INT";
    private static final String CHARACTER_FIELD_WIS = "WIS";
    private static final String CHARACTER_FIELD_CHA = "CHA";
    private static final String CHARACTER_FIELD_LEVEL = "level";
    private static final String CHARACTER_FIELD_SPEED = "speed";
    
    /**
     * File path for the input file
     */
    private String inputFilePath;
    /**
     * File path for the output file
     */
    private String outputFilePath;
    
    /**
     * Used for reading from the input file
     */
    private BufferedReader reader;
    /**
     * Used for writing to the input file using buffers
     */
    private BufferedWriter writer;        
                
    /**
     * Default constructor: sets the default input and output stream buffers 
     * to the keyboard and the screen, respectively.
     */
    public FileIO () {
        reader = new BufferedReader(new InputStreamReader(System.in));
        writer = new BufferedWriter(new PrintWriter(System.out));
    }    
       
    /**
     * Creates a new object that performs reading and writing to the same file.
     * @param filePath Path to the data file to be used for reading and writing
     */
    public FileIO (String filePath) {
        this.inputFilePath = filePath;
        this.outputFilePath = filePath;         
    }        
    
    /**
     * Creates a new object that performs reading and writing to separate files.
     * @param inputFilePath Path to the input file for reading
     * @param outputFilePath Path to the output file for writing
     */
    public FileIO (String inputFilePath, String outputFilePath) {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;        
    }
    
    /**
     * Safely close both the reading and writing stream buffers. (This method 
     * may be redundant)    
     */
    public void closeStreams () {
        try {
            if (reader != null) {
                reader.close();
            }
            if (writer != null) {
                writer.close();
            }
        }
        catch (IOException exception) {
            System.err.println("(FileIO): " + exception);
            System.err.println("(FileIO): Error closing I/O streams");
        }
    }
    
    /**
     * Open the input file for reading and read all lines.
     * @return List of strings representing all the lines of the file
     */
    public List<String> readLinesFromFile () {
        // Open the input file for reading
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFilePath)));
        }
        catch (FileNotFoundException exception) {
            System.err.println("(FileIO): " + exception.toString());
            System.err.println("(FileIO): Error opening file " + inputFilePath);
            System.err.println("(FileIO): Defaulting to System.out");
            reader = reader = new BufferedReader(new InputStreamReader(System.in));
        }                
        
        // Read each line of the file
        List<String> lines = new ArrayList<>();        
        String line;
        try {
            while ((line = reader.readLine()) != null) {            
                lines.add(line);
            }
        }
        catch (IOException exception) {
            System.err.println("(FileIO): " + exception);
        }
        // Close the file when we have finished reading or if an error occurs
        finally {            
            try {
                reader.close();
            } catch (IOException exception) {
                System.err.println("(FileIO): " + exception);
            }
        }
        return lines;
    }
    
    /**
     * Open the output file for writing and write to it a list of lines.
     * @param lines List of strings representing lines to be written to the file
     */
    public void writeLinesToFile (List<String> lines) {
        // Open the output file for writing
        try {
            writer = new BufferedWriter(new PrintWriter(new File(outputFilePath)));
        }
        catch (FileNotFoundException exception) {
            System.err.println("(FileIO): " + exception.toString());
            System.err.println("(FileIO): Error opening file " + outputFilePath);
            System.err.println("(FileIO): Defaulting to System.out");
            writer = new BufferedWriter(new PrintWriter(System.out));
        }                       
        
        // Write each line to the file
        try {
            for (String line : lines) {                
                writer.write(line);
                writer.newLine(); // Platform-independent newline character (this is the most portable and safe approach
            }
        }
        catch (IOException exception) {
            System.err.println("(FileIO): " + exception);
        }
        // Close the file when we have finished writing or if an error occurs
        finally {            
            try {
                reader.close();
            } catch (IOException exception) {
                System.err.println("(FileIO): " + exception);
            }
        }
    }
    
    public ArrayList<HashMap<String, String>> readDataFromFile () throws IOException {
        // Open the input file for reading
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFilePath)));
        }
        catch (FileNotFoundException exception) {
            System.err.println("(FileIO): " + exception.toString());
            System.err.println("(FileIO): Error opening file " + inputFilePath);
            System.err.println("(FileIO): Defaulting to System.out");
            reader = reader = new BufferedReader(new InputStreamReader(System.in));
        }
        ArrayList <HashMap<String, String>> list = new ArrayList<>();
        HashMap<String, String> map;
        try {            
            // Read new lines until we reach a blank line or EOF            
            String line;            
            while ((line = this.reader.readLine()) != null) {                
                if (line.equals("[character]")) {
                    map = new HashMap<>();
                    RPCharacter character = readCharacterFromFile(this.reader);
                    if (character != null) {
                        map.put("class", character.getCharacterClass());
                        map.put("firstName", character.getFirstName());
                        map.put("lastName", character.getLastName());
                        map.put("age", Integer.toString(character.getAge()));
                        map.put("strength", Integer.toString(character.getStrength()));
                        map.put("dexterity", Integer.toString(character.getDexterity()));
                        map.put("constitution", Integer.toString(character.getConstitution()));
                        map.put("intelligence", Integer.toString(character.getIntelligence()));
                        map.put("charisma", Integer.toString(character.getCharisma()));
                        map.put("wisdom", Integer.toString(character.getWisdom()));
                        map.put("level", Integer.toString(character.getLevel()));
                        map.put("speed", Float.toString(character.getSpeed()));
                    }
                    list.add(map);
                }
            }            
        }
        catch (IOException exception) {
            System.err.println("(FileIO): " + exception);
        }
        // Close the file when we have finished reading or if an error occurs
        finally {            
            try {
                reader.close();
            } catch (IOException exception) {
                System.err.println("(FileIO): " + exception);
            }
        }
        return list;
    }
    
    void openFileForWriting () {        
        try {
            this.writer = new BufferedWriter(new PrintWriter(new File(this.outputFilePath)));
        }
        catch (FileNotFoundException exception) {
            System.err.println("(FileIO): " + exception.toString());
            System.err.println("(FileIO): Error opening file " + this.outputFilePath);
            System.err.println("(FileIO): Defaulting to System.out");
            this.writer = new BufferedWriter(new PrintWriter(System.out));
        }
    }
    
    
    public boolean writeCharactersToFile (List<RPCharacter> characterList) {                
        // Open the output file for wriitng
        this.openFileForWriting();        
        
        try {
            for (RPCharacter character : characterList) {
                String line = CHARACTER_FIELD_HEADER;
                this.writer.write(line);
                this.writer.newLine(); // Platform-independent newline   
                if (!this.writeCharacterToFile(this.writer, character)) {
                    throw new IOException();
                }
                this.writer.newLine(); 
                this.writer.flush(); // Do not forget this or nothing will be written to the file
            }
        }
        catch (IOException exception) {
            System.err.println("(FileIO): " + exception);
            System.err.println("(FileIO): Failed to save characters to file.");
            return false;
        }
        // Close the file when we have finished writing or if an error occurs
        finally {            
            try {
                writer.close();
            } catch (IOException exception) {
                System.err.println("(FileIO): " + exception);
            }
        }
        
        
        return true;
    }
    
    
    private boolean writeCharacterToFile (BufferedWriter bw, RPCharacter character) {        
        
        try {                                    
            bw.write(CHARACTER_FIELD_CLASS + CHARACTER_FIELD_KEYVAL_SEP + character.getCharacterClass());
            bw.newLine();
            bw.write(CHARACTER_FIELD_FNAME + CHARACTER_FIELD_KEYVAL_SEP + character.getFirstName());
            bw.newLine();
            bw.write(CHARACTER_FIELD_LNAME + CHARACTER_FIELD_KEYVAL_SEP + character.getLastName());
            bw.newLine();
            bw.write(CHARACTER_FIELD_AGE + CHARACTER_FIELD_KEYVAL_SEP + character.getAge());
            bw.newLine();
            bw.write(CHARACTER_FIELD_STR + CHARACTER_FIELD_KEYVAL_SEP + character.getStrength());
            bw.newLine();
            bw.write(CHARACTER_FIELD_DEX + CHARACTER_FIELD_KEYVAL_SEP + character.getDexterity());
            bw.newLine();
            bw.write(CHARACTER_FIELD_CON + CHARACTER_FIELD_KEYVAL_SEP + character.getConstitution());
            bw.newLine();
            bw.write(CHARACTER_FIELD_INT + CHARACTER_FIELD_KEYVAL_SEP + character.getIntelligence());
            bw.newLine();
            bw.write(CHARACTER_FIELD_WIS + CHARACTER_FIELD_KEYVAL_SEP + character.getWisdom());
            bw.newLine();
            bw.write(CHARACTER_FIELD_CHA + CHARACTER_FIELD_KEYVAL_SEP + character.getCharisma());
            bw.newLine();
            bw.write(CHARACTER_FIELD_LEVEL + CHARACTER_FIELD_KEYVAL_SEP + character.getLevel());
            bw.newLine();
            bw.write(CHARACTER_FIELD_SPEED + CHARACTER_FIELD_KEYVAL_SEP + character.getSpeed());
            bw.newLine();
        }                    
        catch (IOException exception) {
            System.err.println("(FileIO): " + exception);
            System.err.println("(FileIO): Failed to save character " +
                    character.getFirstName() + " " + character.getLastName() + " to file.");
            return false;
        }
        finally {
            return true;
        }
    }
    
    
    /**
     * Create a single RPCharacter object based from a file.
     * @param br The buffered reader pointing to the specified input stream/file
     * @return An RPCharacter object if the file reading was successful, else <code>null</code>
     */
    private RPCharacter readCharacterFromFile (BufferedReader br) {                        
        String characterClass = null, firstName = "PlaceHolderFirst", lastName = "PlaceHolderLast";
        int STR = 0, DEX = 0, CON = 0, INT = 0, WIS = 0, CHA = 0, age = 5, level = 1;
        float speed = 25f; 
        RPCharacter character = null;
        
        try {                        
            // Read new lines until we reach a blank line or EOF            
            String line;            
            while ((line = br.readLine()) != null) {                 
                if (line.length() == 0) {
                    break;
                }                
                String[] tokens = line.split(CHARACTER_FIELD_KEYVAL_SEP);
                if (tokens.length == 2) {
                    String key = tokens[0], value = tokens[1];
                    if (key.equals(CHARACTER_FIELD_CLASS)) {
                        characterClass = value;
                    }
                    else if (key.equals(CHARACTER_FIELD_FNAME)) {
                        firstName = value;
                    }
                    else if (key.equals(CHARACTER_FIELD_LNAME)) {
                        lastName = value;
                    }
                    else if (key.equals(CHARACTER_FIELD_AGE)) {
                        age = Integer.parseInt(value);
                    }
                    else if (key.equals(CHARACTER_FIELD_STR)) {
                        STR = Integer.parseInt(value);
                    }
                    else if (key.equals(CHARACTER_FIELD_DEX)) {
                        DEX = Integer.parseInt(value);
                    }
                    else if (key.equals(CHARACTER_FIELD_CON)) {
                        CON = Integer.parseInt(value);
                    }
                    else if (key.equals(CHARACTER_FIELD_INT)) {
                        INT = Integer.parseInt(value);
                    }
                    else if (key.equals(CHARACTER_FIELD_WIS)) {
                        WIS = Integer.parseInt(value);
                    }
                    else if (key.equals(CHARACTER_FIELD_CHA)) {
                        CHA = Integer.parseInt(value);
                    }
                    else if (key.equals(CHARACTER_FIELD_LEVEL)) {
                        level = Integer.parseInt(value);
                    }
                    else if (key.equals(CHARACTER_FIELD_SPEED)) {
                        speed = Float.parseFloat(value);
                    }
                }
            }
                        
            if (characterClass != null &&
                    firstName != null &&
                    lastName != null) { 
                if (characterClass.equalsIgnoreCase("Fighter")) {
                    character = new RPFighter(firstName, lastName, age, 0, 0);
                }
                else if (characterClass.equalsIgnoreCase("Wizard")) {
                    character = new RPWizard(firstName, lastName, age, 0, 0);
                }
                else {
                    character = new RPBard(firstName, lastName, age, 0, 0);
                }
            }        
        }                    
        catch (IOException exception) {
            character = null;
            System.err.println("(FileIO): " + exception);                        
        }

        return character;
    }
    
    public List<RPCharacter> readCharactersFromFile () {
        // Open the input file for reading
        this.openFileForReading();
        
        List<RPCharacter> characterList = null;
        try {            
            // Read new lines until we reach a blank line or EOF            
            String line;            
            while ((line = this.reader.readLine()) != null) {                
                if (line.equals("[character]")) {
                    RPCharacter character = readCharacterFromFile(this.reader);
                    if (character != null) {
                        if (characterList == null) {
                            characterList = new ArrayList<>();
                        }                        
                        characterList.add(character);
                    }
                }
            }            
        }
        catch (IOException exception) {
            characterList = null;
            System.err.println("(FileIO): " + exception);            
        }
        // Close the file when we have finished reading or if an error occurs
        finally {
            try {
                this.reader.close();                
            } catch (IOException exception) {
                System.err.println("(FileIO): " + exception);
            }
        }
                
        return characterList;
    }
    
    
    void openFileForReading () {         
        try {
            this.reader = new BufferedReader(new InputStreamReader(new FileInputStream(this.inputFilePath)));
        }
        catch (FileNotFoundException exception) {
            System.err.println("(FileIO): " + exception.toString());
            System.err.println("(FileIO): Error opening file " + this.inputFilePath);
            System.err.println("(FileIO): Defaulting to System.in");
            this.reader = new BufferedReader(new InputStreamReader(System.in));
        }
    }
    
    
    public void writeDataToFile (ArrayList<HashMap<String, String>> data, String file) {
        
        this.openFileForWriting();
        
        try {
            for (int i=0; i<data.size(); i++) {
                if (data.get(i).containsValue("WIZARD")) {
                    RPWizard W = new RPWizard("mona", "is", 12, 12, 12);
                    W = W.mapToCharacter(data.get(i));
                    String line = CHARACTER_FIELD_HEADER;
                    this.writer.write(line);
                    this.writer.newLine(); // Platform-independent newline   
                    if (!this.writeCharacterToFile(this.writer, W)) {
                        throw new IOException();
                    }
                    this.writer.newLine(); 
                    this.writer.flush(); // Do not forget this or nothing will be written to the file
                        
                }
                else if (data.get(i).containsValue("BARD")) {
                    RPBard B = new RPBard("mona", "is", 12, 12, 12);
                    B = B.mapToCharacter(data.get(i));
                    String line = CHARACTER_FIELD_HEADER;
                    this.writer.write(line);
                    this.writer.newLine(); // Platform-independent newline   
                    if (!this.writeCharacterToFile(this.writer, B)) {
                        throw new IOException();
                    }
                    this.writer.newLine(); 
                    this.writer.flush(); // Do not forget this or nothing will be written to the file    
                }
                else if (data.get(i).containsValue("FIGHTER")) {
                    RPFighter F = new RPFighter("mona", "is", 12, 12, 12);
                    F = F.mapToCharacter(data.get(i));
                    String line = CHARACTER_FIELD_HEADER;
                    this.writer.write(line);
                    this.writer.newLine(); // Platform-independent newline   
                    if (!this.writeCharacterToFile(this.writer, F)) {
                        throw new IOException();
                    }
                    this.writer.newLine(); 
                    this.writer.flush(); // Do not forget this or nothing will be written to the file    
                }
            }
        }
        catch (IOException exception) {
            System.err.println("(FileIO): " + exception);
            System.err.println("(FileIO): Failed to save characters to file.");
        }
        // Close the file when we have finished writing or if an error occurs
        finally {            
            try {
                writer.close();
            } catch (IOException exception) {
                System.err.println("(FileIO): " + exception);
            }
        }
    }
}