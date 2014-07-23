/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package applicationLogic;

/**
 *
 * @author Timo
 * 
 * This simple class stores the values for searches in the Contact Person Database.
 * The values get set during everytime the search function gets called.
 * 
 */
public class CurrentSearchValuesContactPerson {
    int conPersId = -1;
    String lastname = null;
    String forename = null;
    
    
    
        public int getConPersId() {
		return conPersId;
	}



	public void setConPersId(int conPersId) {
		this.conPersId = conPersId;
	}



	public String getLastname() {
		return lastname;
	}



	public void setLastname(String lastname) {
		this.lastname = lastname;
	}



	public String getForename() {
		return forename;
	}



	public void setForename(String forename) {
		this.forename = forename;
	}



	public void setValues(int conPersId, String lastname, String forename){
    	/**
    	 * @author Kevin Schneider
    	 */
    	this.conPersId = conPersId;
    	this.lastname = lastname;
    	this.forename = forename;
        }
        
        public void resetValues() {
            this.conPersId = -1;
            this.lastname = null;
            this.forename = null;
        }
}
