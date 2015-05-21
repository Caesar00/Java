/**
 * @file Client.java
 * @date 17/04/2015
 * @author Christopher Candy
 */
package Catalogue;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Christopher Candy
 * @see Client
 * @version 1.1
 * This class creates a catalogue GUI.
 */       
public class Client
{
    public static void main(String[] args )
    {
        ArrayList<Entry> entry = new ArrayList();
          
        CatalogueApp catApp = new CatalogueApp("Catalogue");
        catApp.pack();
        catApp.setVisible(true);
    }  
}
