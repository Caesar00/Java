/**
 * @file Catalogue.java
 * @date 19/05/2014
 * @author Christopher Candy
 */
package Catalogue;

import java.io.Serializable;

/**
 * @author Christopher Candy
 * @see Catalogue
 * @version 1.0
 * This class represents a catalogue. The data here is accessed via
 * the CatalogueApp class.
 */
class Catalogue implements Serializable
{
    private String catalogueName;
    EntryApp entryApp;

    
   /**
    * @param catName String
    * @return void
    * @see Catalogue
    * This method sets the name of the catalogue. This method is used by the
    * CatalogueApp GUI class.
    */        
    public void setCatalogueName(String catName)
    {
        catalogueName = catName;
    }
    
    /**
    * @return String catalogueName
    * @see Catalogue
    * This method returns the catalogue name
    */   
    public String getCatalogueName()
    {
        return catalogueName;
    }
} 