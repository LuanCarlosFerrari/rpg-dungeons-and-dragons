//Source: http://www.cs.cf.ac.uk/Dave/HCI/HCI_Handout_CALLER/node61.html

package rpcharactermanager;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

/**
 *
 * @author minahilIkram
 */
public class GUI {
    private static JFrame frame = new JFrame("Roleplaying Character Manager");
    private static JTabbedPane tabbedPane = new JTabbedPane();
    public static JPanel create;
    public static JPanel edit;
    public static JPanel delete;
    public static JPanel simulator;
    public static String firstName;
    public static String lastName;
    public static String age;
    public static String speed;
    public static String level;
    public static String cClass;
    
   public JMenuBar createMenuBar() {
        JMenuBar menuBar;
        JMenu menu;
        JMenuItem menuItem;
        
        menuBar = new JMenuBar();
        menu = new JMenu("File");
        menuBar.add(menu);
        
        menuItem = new JMenuItem("Create", KeyEvent.VK_T);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        menuItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event) {
                tabbedPane.setSelectedIndex(0);
            }
        });
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Edit", KeyEvent.VK_D);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
        menuItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event) {
                tabbedPane.setSelectedIndex(1);
            }
        });
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Delete", KeyEvent.VK_T);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK));
        menuItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event) {
                tabbedPane.setSelectedIndex(2);
            }
        });
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Character Simulator", KeyEvent.VK_T);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        menuItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event) {
                tabbedPane.setSelectedIndex(3);
            }
        });
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Save & Exit", KeyEvent.VK_V);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        menuItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event) {
                RPCharacterManager.quit();
                System.exit(0);
            }
        });
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Exit", KeyEvent.VK_X);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.CTRL_MASK));
        menuItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });
        menu.add(menuItem);

        return menuBar;
    }
   
    public static void editTab() {
        edit = new JPanel();
        edit.setLayout( null );

        JLabel label1 = new JLabel("First name: ");
        label1.setBounds( 10, 15, 150, 20 );
        edit.add( label1 );

        final JTextField field1 = new JTextField();
        field1.setBounds( 10, 35, 150, 20 );
        edit.add( field1 );

        JLabel label2 = new JLabel("Last Name: ");
        label2.setBounds( 10, 60, 150, 20 );
        edit.add( label2 );

        final JTextField field2 = new JTextField();
        field2.setBounds( 10, 80, 150, 20 );
        edit.add( field2 );
        
        JButton submit = new JButton("Submit");
        submit.setBounds(10, 105, 150, 20);
        edit.add(submit);
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                firstName = field1.getText();
                lastName = field2.getText();
                RPCharacterManager.edit(firstName, lastName);
                
                field1.setText("");
                field2.setText("");
            }
        });
        firstName = null;
        lastName = null;
    }
   
    public static void deleteTab() {
        delete = new JPanel();
       delete.setLayout( null );

        JLabel label1 = new JLabel("First name: ");
        label1.setBounds( 10, 15, 150, 20 );
        delete.add( label1 );

        final JTextField field1 = new JTextField();
        field1.setBounds( 10, 35, 150, 20 );
        delete.add( field1 );

        JLabel label2 = new JLabel("Last Name: ");
        label2.setBounds( 10, 60, 150, 20 );
        delete.add( label2 );

        final JTextField field2 = new JTextField();
        field2.setBounds( 10, 80, 150, 20 );
        delete.add( field2 );
        
        JButton submit = new JButton("Submit");
        submit.setBounds(10, 105, 150, 20);
        delete.add(submit);
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                firstName = field1.getText();
                lastName = field2.getText();
                RPCharacterManager.delete(firstName, lastName);
                
                field1.setText("");
                field2.setText("");
            }
        });
        firstName = null;
        lastName = null;
    }
    public static void simulatorTab() {
        simulator = new JPanel();
        simulator.setLayout( null );
        
        JButton submit = new JButton("Simulate");
        submit.setBounds(10, 15, 150, 20);
        simulator.add(submit);
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                RPCharacterManager.simulation();
            }
        });
    }
    
    public static void createTab() {
        create = new JPanel();
        create.setLayout( null );
        
        JLabel label1 = new JLabel("First name: ");
        label1.setBounds( 10, 15, 150, 20 );
        create.add( label1 );

        final JTextField field1 = new JTextField();
        field1.setBounds( 10, 35, 150, 20 );
        create.add(field1);

        JLabel label2 = new JLabel("Last Name: ");
        label2.setBounds( 10, 60, 150, 20 );
        create.add( label2 );

        final JTextField field2 = new JTextField();
        field2.setBounds( 10, 80, 150, 20 );
        create.add( field2 );
        
        JLabel label3 = new JLabel("Age: ");
        label3.setBounds( 10, 105, 150, 20 );
        create.add( label3 );

        final JTextField field3 = new JTextField();
        field3.setBounds( 10, 125, 150, 20 );
        create.add( field3 );

        JLabel label4 = new JLabel("Level: ");
        label4.setBounds( 10, 150, 150, 20 );
        create.add( label4 );

        final JTextField field4 = new JTextField();
        field4.setBounds( 10, 170, 150, 20 );
        create.add( field4 );
        
        JLabel label5 = new JLabel("Speed: ");
        label5.setBounds( 10, 195, 150, 20 );
        create.add( label5 );

        final JTextField field5 = new JTextField();
        field5.setBounds( 10, 215, 150, 20 );
        create.add( field5 );
        
        JLabel label6 = new JLabel("Class: ");
        label6.setBounds( 10, 240, 150, 20 );
        create.add( label6 );

        final JTextField field6 = new JTextField();
        field6.setBounds( 10, 260, 150, 20 );
        create.add( field6 );
        
        JButton submit = new JButton("Submit");
        submit.setBounds(10, 285, 150, 20);
        create.add(submit);
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                firstName = field1.getText();
                lastName = field2.getText();
                age = field3.getText();
                level = field4.getText();
                speed = field5.getText();
                cClass = field6.getText();
                RPCharacterManager.create(cClass, firstName, lastName, age, level, speed);
                
                field1.setText("");
                field2.setText("");
                field3.setText("");
                field4.setText("");
                field5.setText("");
                field6.setText("");
            }
        });
        firstName = null;
        lastName = null;
    }
    
    
     public static void main(String[] args) {
        RPCharacterManager.main(args);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        GUI starter = new GUI();
        frame.setJMenuBar(starter.createMenuBar());
        
        JPanel topPanel = new JPanel();
        topPanel.setLayout( new BorderLayout() );
        frame.getContentPane().add( topPanel );
        editTab();
        createTab();
        deleteTab();
        simulatorTab();
        
        tabbedPane.addTab( "Create", create );
        tabbedPane.addTab( "Edit", edit );
        tabbedPane.addTab( "Delete", delete );
        tabbedPane.addTab( "Simulator", simulator );
        topPanel.add( tabbedPane, BorderLayout.CENTER );
        frame.add(topPanel);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(screenSize.width-1000, screenSize.height-250);
        frame.setVisible(true);
    }
}