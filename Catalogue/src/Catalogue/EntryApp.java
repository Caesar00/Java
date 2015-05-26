/**
 * @file EntryApp.java
 * @date 17/04/2015
 * @author Christopher Candy
 */
package Catalogue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.File;
import java.util.ArrayList;


/**
 * @author Christopher Candy
 * @see EntryApp
 * @version 1.0
 * This class creates the Entry GUI frame. This class is also used to store the
 * entry objects which are created by the user. 
 */
class EntryApp extends JFrame
{
    static DefaultListModel entryList;
    JList entryNameList;
    
    JLabel entNameListLab, entNameLab, entIDLab, entDayLab, entMonthLab, entYearLab, entDescLab, entPicLab, entPicture;
    JTextField entNameText, entIDText, entDayText, entMonthText, entYearText, entPicText; 
    JTextArea entDescText;
    
    ArrayList<Entry> entry = new ArrayList();
    
    /**
     * @author Christopher Candy
     * @see createEntryButtonListener
     * @version 1.1
     * This class is used to create an entry and then add it to the entry name list.
     * Entries with the same name are not accepted.
     */
    class createEntryButtonListener implements ActionListener
    {
        private String catalogueName;
        
        public createEntryButtonListener(String catName)
        {
            this.catalogueName = catName;
        }
        
        public void actionPerformed(ActionEvent e) 
        { 
            Entry ent = new Entry();

            for(Entry element : entry)
            {
                if(element.getName().equals(entNameText.getText()))
                {
                    JOptionPane.showMessageDialog(EntryApp.this, 
                        "Error! Cannot have duplicate Catalogue names");    
                    return;
                }
            }
        
            try
            {
                // Perform bounds checks on all fields before adding to the entry ArrayList
                if(entNameText.getText().equals(""))
                    JOptionPane.showMessageDialog(EntryApp.this, 
                        "Error! Enter an Entry Name");
                else
                    if(entIDText.getText().equals(""))
                        JOptionPane.showMessageDialog(EntryApp.this, 
                            "Error! Enter a Entry ID Number");
                    else
                        if(entDayText.getText().equals(""))
                            JOptionPane.showMessageDialog(EntryApp.this, 
                                "Error! Enter a Entry Day");
                        else
                            if(Integer.parseInt(entDayText.getText()) < 1 ||
                                    Integer.parseInt(entDayText.getText()) > 31)
                                JOptionPane.showMessageDialog(EntryApp.this, 
                                    "Error! Day must be between 1-31");  
                            else
                                if(entMonthText.getText().equals(""))
                                    JOptionPane.showMessageDialog(EntryApp.this, 
                                        "Error! Enter a Entry Month");
                                else
                                    if(Integer.parseInt(entMonthText.getText()) < 1 ||
                                            Integer.parseInt(entMonthText.getText()) > 12)
                                        JOptionPane.showMessageDialog(EntryApp.this, 
                                            "Error! Month must be between 1-12");                          
                                    else
                                        if(entYearText.getText().equals(""))
                                            JOptionPane.showMessageDialog(EntryApp.this, 
                                                "Error! Enter a Entry Year"); 
                                        else
                                            if(entDescText.getText().equals(""))
                                                JOptionPane.showMessageDialog(EntryApp.this, 
                                                    "Error! Enter a Entry Description");                    
                                            else
                                                if(entPicText.getText().equals(""))
                                                    JOptionPane.showMessageDialog(EntryApp.this, 
                                                        "Error! Enter a Picture");
                                                else
                                                {
                                                    ent.setEntryName(entNameText.getText());
                                                    ent.setEntryIdNumber(Integer.parseInt(entIDText.getText()));
                                                    ent.setEntryDay(Integer.parseInt(entDayText.getText()));
                                                    ent.setEntryMonth(Integer.parseInt(entMonthText.getText()));
                                                    ent.setEntryYear(Integer.parseInt(entYearText.getText()));
                                                    ent.setEntryDescription(entDescText.getText());                               
                                                    ent.setEntryPicName(entPicText.getText());                                

                                                    entry.add(ent);
                                                    entryList.addElement(ent.getName());

                                                    ImageIcon icon = new ImageIcon(entPicText.getText());

                                                    // Scale icon to fit onto GUI properly
                                                    Image image = icon.getImage();
                                                    image = image.getScaledInstance(300, 400, java.awt.Image.SCALE_SMOOTH);
                                                    icon = new ImageIcon(image);

                                                    entPicture.setIcon(icon);
                                                    saveEntryFile(catalogueName);
                                                }   
                }
                catch(NumberFormatException ex)
                {
                    JOptionPane.showMessageDialog(EntryApp.this, 
                        "Error! Entry ID, day, month and year must all contain numbers");                
                }     
        }
    } 
    
   /**
    * @author Christopher Candy
    * @see viewEntryButtonListener
    * @version 1.0
    * This class is used to view the details of a listed entry. If no entries are
    * selected from the list an error message is given.
    */
    class viewEntryButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e) 
        {   
            // Return selected entries details
            String selectedEntry = "";
            
            try
            {
                selectedEntry = entryNameList.getSelectedValue().toString();
            }
            catch(Exception ex)
            {
                JOptionPane.showMessageDialog(EntryApp.this, 
                    "Error! Null entry selected");                
            }
            
            for(Entry element : entry)
            {
                if(element.getName().equals(selectedEntry))
                {
                    entNameText.setText(element.getName());
                    entIDText.setText("" + element.getIdNumber());
                    entDayText.setText("" + element.getEntryDate().getDate());                    
                    entMonthText.setText("" + (element.getEntryDate().getMonth() + 1));                    
                    entYearText.setText("" + (element.getEntryDate().getYear() + 1900));                    
                    entPicText.setText(element.getPicName());                    
                    entDescText.setText(element.getDescription());
                    
                    ImageIcon icon = new ImageIcon(entPicText.getText());
                    
                    // Scale icon to fit onto GUI properly            
                    Image image = icon.getImage();
                    image = image.getScaledInstance(300, 400, java.awt.Image.SCALE_SMOOTH);
                    icon = new ImageIcon(image);

                    entPicture.setIcon(icon);
                }
            }
        }
    }

   /**
    * @author Christopher Candy
    * @see editEntryButtonListener
    * @version 1.1
    * This class is used to edit an entry that has already been created. The entry
    * to edit is chosen from a list. The name of the entry cannot be changed.
    */
    class editEntryButtonListener implements ActionListener
    {
        private String catalogueName;
        
        public editEntryButtonListener(String catName)
        {
            this.catalogueName = catName;
        }
        
        public void actionPerformed(ActionEvent e) 
        {   
            String selectedEntry = "";
            
            try
            {
                selectedEntry = entryNameList.getSelectedValue().toString();
            }
            catch(Exception ex)
            {
                JOptionPane.showMessageDialog(EntryApp.this, 
                    "Error! Null entry selected");                
            }

            for(Entry element : entry)
            {
                if(element.getName().equals(selectedEntry))
                {
                    // Perform bounds checking on edited entry
                    try
                    {
                        if(entIDText.getText().equals(""))
                            JOptionPane.showMessageDialog(EntryApp.this, 
                                "Error! Enter a Entry ID Number");
                        else
                            if(entDayText.getText().equals(""))
                                JOptionPane.showMessageDialog(EntryApp.this, 
                                    "Error! Enter a Entry Day");
                            else
                                if(Integer.parseInt(entDayText.getText()) < 1 ||
                                        Integer.parseInt(entDayText.getText()) > 31)
                                    JOptionPane.showMessageDialog(EntryApp.this, 
                                        "Error! Day must be between 1-31");  
                                else
                                    if(entMonthText.getText().equals(""))
                                        JOptionPane.showMessageDialog(EntryApp.this, 
                                            "Error! Enter a Entry Month");
                                    else
                                        if(Integer.parseInt(entMonthText.getText()) < 1 ||
                                                Integer.parseInt(entMonthText.getText()) > 12)
                                            JOptionPane.showMessageDialog(EntryApp.this, 
                                                "Error! Month must be between 1-12");                          
                                        else
                                            if(entYearText.getText().equals(""))
                                                JOptionPane.showMessageDialog(EntryApp.this, 
                                                    "Error! Enter a Entry Year"); 
                                            else
                                                if(entDescText.getText().equals(""))
                                                    JOptionPane.showMessageDialog(EntryApp.this, 
                                                        "Error! Enter a Entry Description");                    
                                                else
                                                    if(entPicText.getText().equals(""))
                                                        JOptionPane.showMessageDialog(EntryApp.this, 
                                                            "Error! Enter a Picture");
                                                    else
                                                    {
                                                        element.setEntryIdNumber(Integer.parseInt(entIDText.getText()));
                                                        element.setEntryDay(Integer.parseInt(entDayText.getText()));
                                                        element.setEntryMonth(Integer.parseInt(entMonthText.getText()));
                                                        element.setEntryYear(Integer.parseInt(entYearText.getText()));
                                                        element.setEntryDescription(entDescText.getText());                               
                                                        element.setEntryPicName(entPicText.getText());                                

                                                        ImageIcon icon = new ImageIcon(entPicText.getText());
                                                        
                                                        // Scale icon to fit onto GUI properly     
                                                        Image image = icon.getImage();
                                                        image = image.getScaledInstance(300, 400, java.awt.Image.SCALE_SMOOTH);
                                                        icon = new ImageIcon(image);

                                                        entPicture.setIcon(icon);
                                                        break;
                                                    }
                    }
                    catch(NumberFormatException ex)
                    {
                        JOptionPane.showMessageDialog(EntryApp.this, 
                            "Error! Entry ID, day, month and year must all contain numbers");                
                    }
                }            
            }
            saveEntryFile(catalogueName);
                        
        }
    }

   /**
    * @author Christopher Candy
    * @see deleteEntryButtonListener
    * @version 1.1
    * This class is used to delete an entry that has already been created. The entry
    * to delete is chosen from a list. If no entry is selected an error message is 
    * given.
    */
    class deleteEntryButtonListener implements ActionListener
    {
        private String catalogueName;
        
        public deleteEntryButtonListener(String catName)
        {
            this.catalogueName = catName;
        }
        
        public void actionPerformed(ActionEvent e) 
        {   
            // Delete the selected entry from the entry ArrayList
            String selectedEntry = "";
            
            try
            {
                selectedEntry = entryNameList.getSelectedValue().toString();
            }
            catch(Exception ex)
            {
                JOptionPane.showMessageDialog(EntryApp.this, 
                    "Error! Null entry selected");                
            }
        
            for(Entry element : entry)
            {
                if(element.getName().equals(selectedEntry))
                {
                    entryList.removeElement(element.getName());
                    entNameText.setText("");
                    entIDText.setText("");
                    entDayText.setText("");
                    entMonthText.setText("");
                    entYearText.setText("");
                    entPicText.setText("");
                    entDescText.setText("");                    
                    entry.remove(element);
                    saveEntryFile(catalogueName);
                    break;
                }
            }
        }
    }
              
   /**
    * @author Christopher Candy
    * @see clearButtonListener
    * @version 1.0
    * This class is used to clear the entry text fields.
    */    
    class clearButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e) 
        {   
            // Clear all entry text fields and picture
            entNameText.setText("");
            entIDText.setText("");
            entDayText.setText("");
            entMonthText.setText("");
            entYearText.setText("");
            entPicText.setText("");
            entDescText.setText("");
            
            ImageIcon icon = new ImageIcon("default.jpg");
            
            // Scale icon to fit onto GUI properly               
            Image image = icon.getImage();
            image = image.getScaledInstance(300, 400, java.awt.Image.SCALE_SMOOTH);
            icon = new ImageIcon(image);
            
            entPicture.setIcon(icon);
        }
    }
    
   /**
    * @author Christopher Candy
    * @see backButtonListener
    * @version 1.0
    * This class is used to go back to the CatalogueApp GUI. All entries are saved
    * to the file EntrySav.dat. 
    */        
    class backButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e) 
        {   
            CatalogueApp catApp = new CatalogueApp("Catalogue");

            catApp.pack();
            catApp.setVisible(true); 
            EntryApp.this.setVisible(false);
        }
    }    

   /**
    * @author Christopher Candy
    * @see addPictureListener
    * @version 1.0
    * This class displays the picture for the entry when pressing enter in the
    * picture text field. This class was mainly used for initial testing as the 
    * View button was later used, though it is still functional.
    */    
    class addPictureListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            ImageIcon icon = new ImageIcon(entPicText.getText());
            
            // Scale icon to fit onto GUI properly               
            Image image = icon.getImage();
            image = image.getScaledInstance(300, 400, java.awt.Image.SCALE_SMOOTH);
            icon = new ImageIcon(image);
            
            entPicture.setIcon(icon);
        }       
    }

    public EntryApp(String frameTitle, String catalogueName)
    {
        super(frameTitle);

        // Create panels and set layouts
        JPanel westPanel = new JPanel();
        JPanel eastPanel = new JPanel();
        JPanel southPanel = new JPanel(); 

        westPanel.setLayout(new GridBagLayout());
        getContentPane().add(westPanel);
        
        GridBagConstraints left = new GridBagConstraints();
        left.anchor = GridBagConstraints.WEST;
        left.gridx = 20;
        
        GridBagConstraints right = new GridBagConstraints();
        right.weightx = 2.0;
        right.fill = GridBagConstraints.HORIZONTAL;
        right.gridwidth = GridBagConstraints.REMAINDER;    
        
        GridBagConstraints south = new GridBagConstraints();
        south.anchor = GridBagConstraints.SOUTH;
        
        getContentPane().add(southPanel);
        southPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); 

        // Add variables to the panels
        entNameListLab = new JLabel("Entry List: ");
        westPanel.add(entNameListLab, left);

        entryList = new DefaultListModel();
        westPanel.add(entryNameList = new JList(entryList));
        entryNameList.setVisibleRowCount(5);      // Only 5 rows visible
        entryNameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  // Prevent multiple selections
        JScrollPane catNameScroll = new JScrollPane(entryNameList);
        westPanel.add(catNameScroll, right);
        
        entNameLab = new JLabel("Entry Name: ");
        westPanel.add(entNameLab, left);
        
        entNameText = new JTextField();
        westPanel.add(entNameText, right);
        
        entIDLab = new JLabel("Identification No: ");
        westPanel.add(entIDLab, left);
        
        entIDText = new JTextField();
        westPanel.add(entIDText, right);        
        
        entDayLab = new JLabel("Day: ");
        westPanel.add(entDayLab, left);
        
        entDayText = new JTextField();
        westPanel.add(entDayText, right);
        
        entMonthLab = new JLabel("Month: ");
        westPanel.add(entMonthLab, left);
        
        entMonthText = new JTextField();
        westPanel.add(entMonthText, right);
        
        entYearLab = new JLabel("Year: ");
        westPanel.add(entYearLab, left);
        
        entYearText = new JTextField();
        westPanel.add(entYearText, right);
        
        entDescLab = new JLabel("Description: ");
        westPanel.add(entDescLab, left);
        
        entDescText = new JTextArea(4, 25);
        entDescText.setLineWrap(true); 
        entDescText.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(entDescText);
        westPanel.add(scroll, right);
        
        entPicLab = new JLabel("Picture Name: ");
        westPanel.add(entPicLab, left);
 
        ImageIcon image = new ImageIcon("default.jpg");
        entPicture = new JLabel(image);
        eastPanel.add(entPicture, BorderLayout.EAST);

        entPicText = new JTextField();
        westPanel.add(entPicText, right);
                     
        JButton addEntry = new JButton("Create Entry");
        southPanel.add(addEntry, BorderLayout.SOUTH);
        
        JButton viewEntry = new JButton("View Entry");
        southPanel.add(viewEntry, BorderLayout.SOUTH);
        
        JButton editEntry = new JButton("Edit Entry");
        southPanel.add(editEntry, BorderLayout.SOUTH);        
        
        JButton deleteEntry = new JButton("Delete Entry");
        southPanel.add(deleteEntry, BorderLayout.SOUTH);
        
        JButton Clear = new JButton("Clear");
        southPanel.add(Clear, BorderLayout.SOUTH);  
        
        JButton Back = new JButton("Back");
        southPanel.add(Back, BorderLayout.SOUTH);

        add(eastPanel, BorderLayout.EAST);
        add(westPanel, BorderLayout.WEST);
        add(southPanel, BorderLayout.SOUTH);
                
        entPicText.addActionListener(new EntryApp.addPictureListener());
        
        addEntry.addActionListener(new EntryApp.createEntryButtonListener(catalogueName));
        viewEntry.addActionListener(new EntryApp.viewEntryButtonListener());
        editEntry.addActionListener(new EntryApp.editEntryButtonListener(catalogueName));        
        deleteEntry.addActionListener(new EntryApp.deleteEntryButtonListener(catalogueName)); 
        
        Clear.addActionListener(new EntryApp.clearButtonListener());
        Back.addActionListener(new EntryApp.backButtonListener());
        
        // Load the entry file whenever the EntryApp class is run.
        File f = new File(catalogueName + "EntrySave.dat");
        if(f.exists() && !f.isDirectory())
            loadEntryFile(catalogueName);
        
        addWindowListener(new WindowAdapter() 
        {
            public void windowClosing(WindowEvent e) 
            {
                System.exit(0);
            }
        });
    }

   /**
    * @param fos File output stream
    * @param oos Object output stream
    * @return void
    * @exception IOException on output access error
    * @see EntryApp
    * This method saves the entries to the EntrySave.dat file when the user presses
    * the Back button on the GUI.
    */
    public void saveEntryFile(String catName)
    {
        try
        {
            FileOutputStream fos = new FileOutputStream((catName + "EntrySave.dat"));
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(entry);

            oos.close();
            fos.close();           
        }

        catch(IOException ex)
        {
            System.out.println("Error saving entry to file.\nClosing Program.");
            System.exit(1);
        } 
        
    }

   /**
    * @param fis File input stream
    * @param ois Object input stream
    * @param ent An ArrayList created from the object input stream
    * @return void
    * @exception Exception on input access error
    * @see EntryApp
    * This method loads the entries from the EntrySave.dat file when the user 
    * enters the GUI.
    */
    public void loadEntryFile(String catName)
    {
        ArrayList<Entry> ent = null;
        try
        {
            FileInputStream fis = new FileInputStream((catName + "EntrySave.dat"));
            ObjectInputStream ois = new ObjectInputStream(fis);

            ent = (ArrayList<Entry>)ois.readObject();
            entry = (ArrayList<Entry>)ent.clone();
            
            ois.close();
            fis.close();  
                
            for(Entry element : entry)
            {
                entryList.addElement(element.getName());
            }
        }

        catch(Exception ex)
        {
            System.out.println("Error loading the Entry file.\nClosing Program.");
            System.exit(1);
        }
    }
}
