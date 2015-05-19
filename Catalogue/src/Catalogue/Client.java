/**
 * @file Client.java
 * @date 19/05/2014
 * @author Christopher Candy
 */
package ICT306Assignment2;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Christopher Candy
 * @see Client
 * @version 1.0
 * This class was initially used for the creation of entry objects. After the GUI
 * was created this class is no longer used for creating, adding, or deleting
 * entry objects in the GUI(These options are performed in the GUI itself). 
 * This class is currently used to start the program by running the CatalogueApp
 * class.
 */       
public class Client
{
    public static void main(String[] args )
    {
        displayStudentDetails();
        
        ArrayList<Entry> entry = new ArrayList();
          
        // Starts the GUI process by creating the catalogue
        CatalogueApp catApp = new CatalogueApp("Catalogue");

        catApp.setSize(400,320);
        catApp.setVisible(true);
        
        while(true)
        {
            // Create Menu
            printMenu();
            int option = selectOption();
            menuOption(option, entry);
        }
    }

    public static void printMenu()
    {
        System.out.println(); 
        
        System.out.print("Select an option\n1. Add Entry\n" 
                + "2. Edit Entry\n3. Delete Entry\n");  
    }
    
    public static int selectOption()
    {
        // A number between 1 and 3 must be chosen, which refers to the menu. An
        // exception is thrown if numbers are not entered.
        Scanner keyboard = new Scanner (System.in);
        
        int option = 0;
        
        try
        {
            option = keyboard.nextInt();
            
            if(option < 1 || option > 3)
                throw new Exception();
        }
        catch(Exception e)
        {
            System.out.println("Error! Enter a number between 1-3");
        }    
        
        return option;   
    }
    
    public static void menuOption(int option, ArrayList<Entry> entry)
    {
        switch (option)
        {
            case 1:addEntry(entry);
            break;
            case 2:editEntry(entry);
            break;
            case 3:deleteEntry(entry);
            break;
            default:System.out.println();
            break;
        }  

    }
    
    public static void addEntry(ArrayList<Entry> entry)
    {
        Scanner keyboard = new Scanner (System.in);

        Entry catEntry = new Entry();
        
        // Set entry details and add to ArrayList
        catEntry.setName();
        while(!catEntry.setIdNumber()){}
        while(!catEntry.setEntryDate()){}
        catEntry.setDescription();
        catEntry.setPicName();
        
        entry.add(catEntry);
    }
    
    public static void editEntry(ArrayList<Entry> entry)
    {
        // Check to see if entered entry is actually an entry before editing.
        // Entries are displayed before selecting.

        System.out.println("Enter entry's name: ");
        for(Entry element : entry)
        {
            System.out.println(element.getName());
        }
        Scanner keyboard = new Scanner (System.in);
        
        String editor = keyboard.nextLine();
        
        for(Entry element : entry)
        {
            if(element.getName().equals(editor))
            {
                System.out.println("Editing entry " + element.getName() + ": "); 
                element.setName();
                while(!element.setIdNumber()){}
                while(!element.setEntryDate()){}
                element.setDescription();
                element.setPicName();                
            }
        }
    }

    public static void deleteEntry(ArrayList<Entry> entry)
    {
        // Check to see if entered entry is actually an entry before deleting.
        // Entries are displayed before selecting.
        
        System.out.println("Enter entry's name: ");
        for(Entry element : entry)
        {
            System.out.println(element.getName());
        }
        Scanner keyboard = new Scanner (System.in);
        
        String remover = keyboard.nextLine();
        
        for(Entry element : entry)
        {
            if(element.getName().equals(remover))
            {
                entry.remove(element);
                break;
            }
        }
    }
    
    public static void displayStudentDetails()
    {
        System.out.println("Christopher Candy, 31704593\nMode - Internal\nTutor - Mark Abernethy\nLab - Tuesday, 3:30pm\n");
    }
}