/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package applicationLogic;

import java.util.Date;

/**
 *
 * @author Timo
 * 
 * This simple class stores the values for searches in the Donation Collection Database.
 * The values get set during everytime the search function gets called.
 * 
 */
public class CurrentSearchValuesDonationCollection {
    int collId = -1;
    int orgaId = -1;
    int contactId =-1;
    String comment = null;
    int boxId = -1;
    int zip = -1;
    String city = null;
    Date beginStart = null;
    Date beginEnd = null;
    Date endStart = null;
    Date endEnd = null;
    boolean showStreet = true;
    boolean showFixed = true;
    
    
    
    public int getCollId() {
		return collId;
	}



	public void setCollId(int collId) {
		this.collId = collId;
	}



	public int getOrgaId() {
		return orgaId;
	}



	public void setOrgaId(int orgaId) {
		this.orgaId = orgaId;
	}



	public int getContactId() {
		return contactId;
	}



	public void setContactId(int contactId) {
		this.contactId = contactId;
	}



	public String getComment() {
		return comment;
	}



	public void setComment(String comment) {
		this.comment = comment;
	}



	public int getBoxId() {
		return boxId;
	}



	public void setBoxId(int boxId) {
		this.boxId = boxId;
	}



	public int getZip() {
		return zip;
	}



	public void setZip(int zip) {
		this.zip = zip;
	}



	public String getCity() {
		return city;
	}



	public void setCity(String city) {
		this.city = city;
	}



	public Date getBeginStart() {
		return beginStart;
	}



	public void setBeginStart(Date beginStart) {
		this.beginStart = beginStart;
	}



	public Date getBeginEnd() {
		return beginEnd;
	}



	public void setBeginEnd(Date beginEnd) {
		this.beginEnd = beginEnd;
	}



	public Date getEndStart() {
		return endStart;
	}



	public void setEndStart(Date endStart) {
		this.endStart = endStart;
	}



	public Date getEndEnd() {
		return endEnd;
	}



	public void setEndEnd(Date endEnd) {
		this.endEnd = endEnd;
	}



	public boolean getShowStreet() {
		return showStreet;
	}



	public void setShowStreet(boolean showStreet) {
		this.showStreet = showStreet;
	}



	public boolean getShowFixed() {
		return showFixed;
	}



	public void setShowFixed(boolean showFixed) {
		this.showFixed = showFixed;
	}



	public void setValues(int collId, int orgaId, int contactId, String comment, int boxId, int zip, String city, Date beginStart,
    		Date beginEnd, Date endStart, Date endEnd, boolean showStreet, boolean showFixed){
            /**
             * @author Kevin Schneider
             */
            this.collId = collId;
            this.orgaId = orgaId;
            this.contactId = contactId;
            this.comment = comment;
            this.boxId = boxId;
            this.zip = zip;
            this.city = city;
            this.beginStart = beginStart;
            this.beginEnd = beginEnd;
            this.endStart = endStart;
            this.endEnd = endEnd;
            this.showStreet = showStreet;
            this.showFixed = showFixed;
        }
        
        public void resetValues() {
            this.collId = -1;
            this.orgaId = -1;
            this.contactId =-1;
            this.comment = null;
            this.boxId = -1;
            this.zip = -1;
            this.city = null;
            this.beginStart = null;
            this.beginEnd = null;
            this.endStart = null;
            this.endEnd = null;
            this.showStreet = true;
            this.showFixed = true;
        }
}
