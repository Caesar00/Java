/**
 * @file Entry.java
 * @date 17/04/2015
 * @author Christopher Candy
 */
package Catalogue;

import java.util.Date;
import java.util.Scanner;
import java.io.Serializable;

/**
 * @author Christopher Candy
 * @see Entry
 * @version 1.0
 * This class represents an Entry. The data here is accessed via
 * the EntryApp class.
 */
public class Entry implements Serializable
{
    private String name;
    private int idNumber;
    private Date entryDate;
    private String picName;
    private String description;
    
    Entry()
    {
        name = "";
        idNumber = -1;
        entryDate = new Date();
        entryDate.setDate(entryDate.getDay());
        entryDate.setMonth(entryDate.getMonth());
        entryDate.setYear(entryDate.getYear());
        picName = "";
        description = "";
    }

   /**
    * @param name private String variable
    * @return void
    * @see Entry
    * This method sets the name of entry.
    */
    public void setName() 
    {
        System.out.print("Enter entry's name: ");
        Scanner keyboard = new Scanner (System.in);         
        
        name = keyboard.next();
    }
    
   /**
    * @param id int to store temporary ID
    * @param idNumber private int variable
    * @return boolean
    * @exception Exception on number not entered
    * @see Entry
    * This method sets the ID number of entry.
    */
    public boolean setIdNumber() 
    {
        System.out.print("Enter entry's ID number: ");
        Scanner keyboard = new Scanner (System.in); 
        
        int id = 0;
        
        try
        {
            id = keyboard.nextInt();
        }
        catch(Exception e)
        {
            System.out.println("Entry ID must be a number!");
            return false;
        } 
        
        idNumber = id;
        return true;
    }

   /**
    * @param day int to store temporary day
    * @param month int to store temporary month
    * @param year int to store temporary year
    * @param entryDate private date variable
    * @return boolean
    * @exception Exception on number not entered
    * @see Entry
    * This method sets the date of the entry.
    */
    public boolean setEntryDate() 
    {
        Scanner keyboard = new Scanner (System.in);   
        System.out.println ("Enter entry's Date: ");

        int day = 0;
        int month = 0;
        int year;
        
        while(day < 1 || day > 31)
        {
            try
            {
                System.out.print("Day(eg. 21): ");
                day = keyboard.nextInt();
            }
            catch(Exception e)
            {
                System.out.println("Day must be a number!");
                return false;
            }
        }
        
        while(month < 1 || month > 12)
        {
            try
            {
                System.out.print("Month(eg. 8): ");
                month = keyboard.nextInt();
            }
            catch(Exception e)
            {
                System.out.println("Month must be a number!");
                return false;
            }
        }
        
        try
        {
            System.out.print("Year(eg. 2014): ");
            year = keyboard.nextInt();
        }
        catch(Exception e)
        {
            System.out.println("Year must be a number!");
            return false;
        }
        
        entryDate.setDate(day);
        entryDate.setMonth(month - 1);
        entryDate.setYear(year - 1900);
        
        System.out.println(entryDate);
        
        return true;
        
    }
    
   /**
    * @param picName private String variable
    * @return void
    * @see Entry
    * This method sets the picture of entry.
    */
    public void setPicName() 
    {
        System.out.print("Enter entry's picture name: ");
        Scanner keyboard = new Scanner (System.in);         
        
        picName = keyboard.next();
    }    

   /**
    * @param description private String variable
    * @return void
    * @see Entry
    * This method sets the description of entry.
    */
    public void setDescription() 
    {
        System.out.print("Enter entry's description: ");
        Scanner keyboard = new Scanner (System.in);         
        
        description = keyboard.next();
    }

   /**
    * @return String name
    * @see Entry
    * This method returns the entry name
    */    
    public String getName() 
    {
        return name;
    }

   /**
    * @return int idNumber
    * @see Entry
    * This method returns the entry ID number
    */      
    public int getIdNumber() 
    {
        return idNumber;
    }

   /**
    * @return Date entryDate
    * @see Entry
    * This method returns the entry date
    */  
    public Date getEntryDate() 
    {
        return entryDate;
    }

   /**
    * @return String getPicName
    * @see Entry
    * This method returns the entry picture name
    */      
    public String getPicName()
    {
        return picName;
    }

   /**
    * @return String description
    * @see Entry
    * This method returns the entry description
    */      
    public String getDescription() 
    {
        return description;
    }
 
   /**
    * @param n String
    * @return void
    * @see Entry
    * This method sets the name of the entry. This method is used by the
    * EntryApp GUI class.
    */    
    public void setEntryName(String n)
    {
        name = n;       
    }

   /**
    * @param id int
    * @return void
    * @see Entry
    * This method sets the ID number of the entry. This method is used by the
    * EntryApp GUI class.
    */        
    public void setEntryIdNumber(int id)
    {
        idNumber = id;      
    }
    
   /**
    * @param d int
    * @return void
    * @see Entry
    * This method sets the day of the entry. This method is used by the
    * EntryApp GUI class.
    */            
    public void setEntryDay(int d) 
    {
        entryDate.setDate(d); 
    }
    
   /**
    * @param m int
    * @return void
    * @see Entry
    * This method sets the month of the entry. This method is used by the
    * EntryApp GUI class.
    */            
    public void setEntryMonth(int m)
    {
        entryDate.setMonth(m - 1);        
    }   
       
   /**
    * @param y int
    * @return void
    * @see Entry
    * This method sets the year number of the entry. This method is used by the
    * EntryApp GUI class.
    */            
    public void setEntryYear(int y)
    {
        entryDate.setYear(y - 1900);        
    }
    
   /**
    * @param desc String
    * @return void
    * @see Entry
    * This method sets the description of the entry. This method is used by the
    * EntryApp GUI class.
    */        
    public void setEntryDescription(String desc)
    {
        description = desc;
    }
    
   /**
    * @param pic String
    * @return void
    * @see Entry
    * This method sets the picture name of the entry. This method is used by the
    * EntryApp GUI class.
    */        
    public void setEntryPicName(String pic)
    {
        picName = pic;
    }
}