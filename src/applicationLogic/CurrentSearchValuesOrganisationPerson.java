package applicationLogic;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Timo
 * 
 * This simple class stores the values for searches in the Organisation Person Database.
 * The values get set during everytime the search function gets called.
 * 
 */
public class CurrentSearchValuesOrganisationPerson {
    int orgPersId = -1;
    String lastname = null;
    String forename = null;
    
    
    
    public int getOrgPersId() {
		return orgPersId;
	}



	public void setOrgPersId(int orgPersId) {
		this.orgPersId = orgPersId;
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



	public void setValues(int orgPersId, String lastname, String forename){
            this.orgPersId = orgPersId;
            this.lastname = lastname;
            this.forename = forename;
        }
        public void resetValues() {
            this.orgPersId = -1;
            this.lastname = null;
            this.forename = null;
        }
    }
