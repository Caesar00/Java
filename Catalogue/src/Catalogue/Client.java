/**
 * @file Client.java
 * @date 19/05/2014
 * @author Christopher Candy
 */
package Catalogue;

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
        ArrayList<Entry> entry = new ArrayList();
          
        CatalogueApp catApp = new CatalogueApp("Catalogue");

        catApp.setSize(400,320);
        catApp.setVisible(true);
    }  
}
