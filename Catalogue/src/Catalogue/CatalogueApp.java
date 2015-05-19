/**
 * @file CatalogueApp.java
 * @date 19/05/2014
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
import java.util.ArrayList;


/**
 * @author Christopher Candy
 * @see CatalogueApp
 * @version 1.0
 * This class creates the CatalogueApp GUI frame. This class is also used to store 
 * the catalogue objects which are created by the user.
 * NOTE: If no Catalogue objects are present the program will not load. This is
 * only an issue if the save.dat file is deleted.
 */    
class CatalogueApp extends JFrame
{
    static DefaultListModel catalogueList;
    JList catNameList;
    JLabel catNameListLab, catNameLab;
    JTextField catNameText; 

    ArrayList<Catalogue> catalogue = new ArrayList();

    /**
     * @author Christopher Candy
     * @see catalogueCreateButtonListener
     * @version 1.0
     * This class is used to create a catalogue and then add it to the catalogue
     * name list. Entries with the same name are not accepted.
     */        
    class catalogueCreateButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e) 
        { 
            // Create catalogue only when there are no other catalogues with the same name
            Catalogue cat = new Catalogue();
            
            boolean multiCatalogue = false;
            
            for(Catalogue element : catalogue)
            {
                if(element.getCatalogueName().equals(catNameText.getText()))
                {
                    JOptionPane.showMessageDialog(CatalogueApp.this, 
                        "Error! Cannot have duplicate Catalogue names");    
                    multiCatalogue = true;
                }
            }
              
            if(multiCatalogue == false)
            {    
                if(catNameText.getText().equals(""))
                    JOptionPane.showMessageDialog(CatalogueApp.this, 
                        "Error! Enter a Catalogue Name");

                else
                {
                    cat.setCatalogueName(catNameText.getText());
                    catalogue.add(cat);
                    catalogueList.addElement(cat.getCatalogueName());
                }  
            }
        }
    } 

   /**
    * @author Christopher Candy
    * @see catalogueEditButtonListener
    * @version 1.0
    * This class is used to edit a catalogue. This allows the user to add entries
    * to the listed catalogue.
    */
    class catalogueEditButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e) 
        {
            // Edit the entries of a particular catalogue, selected from the drop down menu
            String select = "";
                    
            try
            {
                select = catNameList.getSelectedValue().toString();
            }
            catch(Exception ex)
            {
                JOptionPane.showMessageDialog(CatalogueApp.this, 
                    "Error! Null entry selected");                
            }
            
            saveCatalogueFile();
            
            for(Catalogue element : catalogue)
            {
                if(element.getCatalogueName().equals(select))
                {
                    element.entryApp = new EntryApp("Entry");
                    element.entryApp.setVisible(true);
                    element.entryApp.setSize(720,480);
                    CatalogueApp.this.setVisible(false);
                    break;
                }
            }
        } 
    }
    
   /**
    * @author Christopher Candy
    * @see catalogueExitButtonListener
    * @version 1.0
    * This class is used to exit the CatalogueApp GUI and the program.
    */       
    class catalogueExitButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e) 
        {
            System.exit(0);         
        }      
    }
    
    public CatalogueApp(String frameTitle)
    {
        super(frameTitle);
 
        // Create catalogue panels
        JPanel westPanel = new JPanel();
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
        
        
        catNameListLab = new JLabel("Catalogue List: ");
        westPanel.add(catNameListLab, left);

        catalogueList = new DefaultListModel();
        westPanel.add(catNameList = new JList(catalogueList));
        catNameList.setVisibleRowCount(5);      // Only 5 rows visible
        catNameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  // Prevent multiple selections
        JScrollPane catNameScroll = new JScrollPane(catNameList);
        westPanel.add(catNameScroll, right);
    
        catNameLab = new JLabel("Catalogue Name: ");
        westPanel.add(catNameLab, left);
        
        catNameText = new JTextField();
        westPanel.add(catNameText, right);
        
        add(westPanel, BorderLayout.WEST);
        add(southPanel, BorderLayout.SOUTH);
        
        westPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));        
        
        JButton addEntry = new JButton("Create Catalogue");
        southPanel.add(addEntry, BorderLayout.SOUTH);
        
        JButton editEntry = new JButton("Edit Catalogue");
        southPanel.add(editEntry, BorderLayout.SOUTH);
        
        JButton exitEntry = new JButton("Exit Catalogue");
        southPanel.add(exitEntry, BorderLayout.SOUTH);        

        addEntry.addActionListener(new catalogueCreateButtonListener());
        editEntry.addActionListener(new catalogueEditButtonListener());
        exitEntry.addActionListener(new catalogueExitButtonListener());

        loadCatalogueFile();
               
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
    * @see CatalogueApp
    * This method saves the catalogues to the save.dat file when the user presses
    * the Edit button on the GUI.
    */    
    public void saveCatalogueFile()
    {
        try
        {
            FileOutputStream fos = new FileOutputStream("save.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(catalogue);

            oos.close();
            fos.close();           
        }

        catch(IOException ex)
        {
            System.out.println("The output file cannot be accessed.\nClosing Program.");
            System.exit(1);
        } 
        
    }
 
   /**
    * @param fis File input stream
    * @param ois Object input stream
    * @param cat An ArrayList created from the object input stream
    * @return void
    * @exception Exception on input access error
    * @see CatalogueApp
    * This method loads the catalogues from the save.dat file when the user 
    * enters the GUI(either via startup or the Back button pressed from the
    * EntryApp GUI).
    */
    public void loadCatalogueFile()
    {
        ArrayList<Catalogue> cat = null;
        try
        {
            FileInputStream fis = new FileInputStream("save.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);

            cat = (ArrayList<Catalogue>)ois.readObject();
            catalogue = (ArrayList<Catalogue>)cat.clone();
            
            ois.close();
            fis.close();  
            
            
            for(Catalogue element : catalogue)
            {
                catalogueList.addElement(element.getCatalogueName());
            }
        }

        catch(Exception ex)
        {
            System.out.println("The Catalogue file cannot be accessed.\nClosing Program.");
            System.exit(1);
        }
    }
}
