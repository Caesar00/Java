/**
 * @file CatalogueApp.java
 * @date 17/04/2015
 * @author Christopher Candy
 */
package Catalogue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
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
            
            for(Catalogue element : catalogue)
            {
                if(element.getCatalogueName().equals(catNameText.getText()))
                {
                    JOptionPane.showMessageDialog(CatalogueApp.this, 
                        "Error! Cannot have duplicate Catalogue names"); 
                    return;
                }
            }

            if(catNameText.getText().equals(""))
            {
                JOptionPane.showMessageDialog(CatalogueApp.this, 
                    "Error! Enter a Catalogue Name");
                return;  
            }
            else
            {
                cat.setCatalogueName(catNameText.getText());
                catalogue.add(cat);
                catalogueList.addElement(cat.getCatalogueName());
            }  
            
            saveCatalogueFile();
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
                return;
            }
            
            for(Catalogue element : catalogue)
            {
                if(element.getCatalogueName().equals(select))
                {
                    element.entryApp = new EntryApp("Entry", element.getCatalogueName());
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
    class catalogueDeleteButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
             // Delete the selected entry from the entry ArrayList
            String selectedEntry = "";
            
            try
            {
                selectedEntry = catNameList.getSelectedValue().toString();
            }
            catch(Exception ex)
            {
                JOptionPane.showMessageDialog(CatalogueApp.this, 
                    "Error! Null entry selected");                
            }
        
            for(Catalogue element : catalogue)
            {
                if(element.getCatalogueName().equals(selectedEntry))
                {
                    catalogueList.removeElement(element.getCatalogueName());
                    catNameText.setText("");
                    catalogue.remove(element);
                    saveCatalogueFile();
                    
                    File f = new File(element.getCatalogueName() + "EntrySave.dat");
                    if(f.exists() && !f.isDirectory())
                    f.delete();
                    break;
                }
            }        
        } 
    }
    
   /**
    * @author Christopher Candy
    * @see catalogueExitButtonListener
    * @version 1.1
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
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(4,4,1,4);
        c.fill = GridBagConstraints.HORIZONTAL;
        
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new GridBagLayout());
        this.add(northPanel, c);
        
        catNameListLab = new JLabel("Catalogue List: ");
        c.weightx = 0;
        c.gridx = 0;
        c.gridy = 0;
        northPanel.add(catNameListLab, c);

        catalogueList = new DefaultListModel();
        northPanel.add(catNameList = new JList(catalogueList));
        catNameList.setVisibleRowCount(5);      // Only 5 rows visible
        catNameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  // Prevent multiple selections
        JScrollPane catNameScroll = new JScrollPane(catNameList);
        c.weightx = 1;
        c.gridx = 1;
        c.gridy = 0;
        northPanel.add(catNameScroll, c);        
        
        catNameLab = new JLabel("Catalogue Name: ");
        c.weightx = 0;
        c.gridx = 0;
        c.gridy = 1;
        c.insets = new Insets(1,4,30,4);
        northPanel.add(catNameLab, c);
        
        catNameText = new JTextField();
        c.weightx = 1;
        c.gridx = 1;
        c.gridy = 1;
        northPanel.add(catNameText, c);        

      
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new GridBagLayout());
        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(2,4,2,4);
        this.add(southPanel, c);
        
        JButton addCatalogue = new JButton("Create Catalogue");
        c.gridx = 0;
        c.gridy = 0;
        southPanel.add(addCatalogue, c);
                
        JButton editCatalogue = new JButton("Edit Catalogue");
        c.gridx = 1;
        c.gridy = 0;
        southPanel.add(editCatalogue, c);
        
        JButton deleteCatalogue = new JButton("Delete Catalogue");
        c.gridx = 0;
        c.gridy = 1;
        southPanel.add(deleteCatalogue, c);
        
        JButton exitCatalogue = new JButton("Exit Catalogue");
        c.gridx = 1;
        c.gridy = 1;
        southPanel.add(exitCatalogue, c);
        
       
        addCatalogue.addActionListener(new catalogueCreateButtonListener());
        editCatalogue.addActionListener(new catalogueEditButtonListener());
        deleteCatalogue.addActionListener(new catalogueDeleteButtonListener());
        exitCatalogue.addActionListener(new catalogueExitButtonListener());

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
    * This method saves the catalogues to the catSave.dat file when the user presses
    * the Edit button on the GUI.
    */    
    public void saveCatalogueFile()
    {
        try
        {
            /*ObjectOutputStream oos = new
                ObjectOutputStream(new
                    FileOutputStream("catSave.dat"));*/
            FileOutputStream fos = new FileOutputStream("catSave.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(catalogue);

            oos.close();
            //fos.close();           
        }

        catch(IOException ex)
        {
            System.out.println("Error saving catalogue to file.\nClosing Program.");
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
    * This method loads the catalogues from the catSave.dat file when the user 
    * enters the GUI(either via startup or the Back button pressed from the
    * EntryApp GUI).
    */
    public void loadCatalogueFile()
    {
        ArrayList<Catalogue> cat = null;
        try
        {
            FileInputStream fis = new FileInputStream("catSave.dat");
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
            System.out.println("Error loading the Catalogue file.\nClosing Program.");
            System.exit(1);
        }
    }
}
