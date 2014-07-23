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
 * This simple class stores the values for searches in the Donation Box Database.
 * The values get set during everytime the search function gets called.
 * 
 */
public class CurrentSearchValuesDonationBox {
    int boxId = -1;
    int responsiblePersonId = -1;
    String comment = null;
    int collectionId = -1;
    Date date = null;
    boolean inUseYes = true;
    boolean inUseNo = true;
    
    
    
    public int getBoxId() {
		return boxId;
	}



	public void setBoxId(int boxId) {
		this.boxId = boxId;
	}



	public int getResponsiblePersonId() {
		return responsiblePersonId;
	}



	public void setResponsiblePersonId(int responsiblePersonId) {
		this.responsiblePersonId = responsiblePersonId;
	}



	public String getComment() {
		return comment;
	}



	public void setComment(String comment) {
		this.comment = comment;
	}



	public int getCollectionId() {
		return collectionId;
	}



	public void setCollectionId(int collectionId) {
		this.collectionId = collectionId;
	}



	public Date getDate() {
		return date;
	}



	public void setDate(Date date) {
		this.date = date;
	}







	public boolean isInUseYes() {
		return inUseYes;
	}



	public void setInUseYes(boolean inUseYes) {
		this.inUseYes = inUseYes;
	}



	public boolean isInUseNo() {
		return inUseNo;
	}



	public void setInUseNo(boolean inUseNo) {
		this.inUseNo = inUseNo;
	}



	public void setValues(int boxId, int responsiblePersonId, String comment, int collectionId, Date date, boolean inUseYes, boolean inUseNo) {
            /**
             * @author Kevin Schneider
             */
            this.boxId = boxId;
            this.responsiblePersonId = responsiblePersonId;
            this.comment = comment;
            this.collectionId = collectionId;
            this.date = date;
            this.inUseYes = inUseYes;
            this.inUseNo = inUseNo;
        }
        
        public void resetValues() {
            this.boxId = -1;
            this.responsiblePersonId = -1;
            this.comment = null;
            this.collectionId = -1;
            this.date = null;
            this.inUseYes = true;
            this.inUseNo = true;
        }
    }
