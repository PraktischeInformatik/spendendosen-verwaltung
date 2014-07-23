package applicationLogic;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import dataAccess.DataAccess;

/**
 * @author Etibar Hasanov
 *
 */

public class DonationBox {
	
	
	static private int nextBoxId = 0;
	private int boxId;
	//current sum in donationbox
	private float sum=0;

	private boolean inUse;
	private String comment;
	private OrganisationPerson currentResponsiblePerson;
	private int collectionId;
	private boolean isDeleted;

	private ArrayList<ClearingDonationBox> clearingDonationBoxes = new ArrayList<ClearingDonationBox>();
	private ArrayList<BoxHistory> allBoxHistories=new ArrayList<BoxHistory>() ;

	public ArrayList<BoxHistory> getAllBoxHistories() {
		return allBoxHistories;
	}

	public void setAllBoxHistories(ArrayList<BoxHistory> allBoxHistories) {
		this.allBoxHistories = allBoxHistories;
	}

	public float getSum() {
		return sum;
	}

	public void setSum(float sum) {
		this.sum = sum;
	}

	public boolean isInUse() {
		return inUse;
	}

	public void setInUse(boolean inUse) {
		this.inUse = inUse;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public static int getNextBoxId() {
		return nextBoxId;
	}

	public static void setNextBoxId(int nextBoxId) {
		DonationBox.nextBoxId = nextBoxId;
	}

	public int getBoxId() {
		return boxId;
	}

	public void setBoxId(int boxId) {
		this.boxId = boxId;
	}

	public String getInUseForTable() {
		if (inUse) {
			return "Aktiv";
		} else {
			return "Inaktiv";
		}
	}

	
	public ArrayList<ClearingDonationBox> getClearingDonationBoxes() {
		return clearingDonationBoxes;
	}

	public void setClearingDonationBoxes(
			ArrayList<ClearingDonationBox> clearingDonationBoxes) {
		this.clearingDonationBoxes = clearingDonationBoxes;
	}

	public OrganisationPerson getCurrentResponsiblePerson() {
		return currentResponsiblePerson;
	}

	public void setCurrentResponsiblePerson(
			OrganisationPerson currentResponsiblePerson) {
		this.currentResponsiblePerson = currentResponsiblePerson;
	}

	public int getCollectionId() {
		return collectionId;
	}

	public void setCollectionId(int collectionId) {
		this.collectionId = collectionId;
	}

	public boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public DonationBox() {
		boxId = nextBoxId;
		nextBoxId++;
		this.setIsDeleted(false);
	}

	public DonationBox(int boxId) {
		this.setBoxId(boxId);
		this.setIsDeleted(false);
	}
	public DonationBox(boolean inUse,float sum, String comment,
			OrganisationPerson currentResponsiblePerson, int collectionId) {
		this();
		this.setInUse(inUse);
		this.setComment(comment);
		this.setCurrentResponsiblePerson(currentResponsiblePerson);
		this.setSum(sum);
	}

	// Sebastian
	public DonationBox(int boxId,float sum, boolean inUse, String comment,
			OrganisationPerson currentResponsiblePerson, int collectionId) {
		this.setBoxId(boxId);
		this.setInUse(inUse);
		this.setComment(comment);
		this.setCurrentResponsiblePerson(currentResponsiblePerson);
		this.setCollectionId(collectionId);
		this.setIsDeleted(false);
		this.setSum(sum);
	}

	public DonationBox (int boxId,float sum, boolean inUse, String comment,
			OrganisationPerson currentResponsiblePerson,
			ArrayList<ClearingDonationBox> clearingDonationBoxes,
			int collectionId, boolean isDeleted) {
		this.setBoxId(boxId);
		this.setInUse(inUse);
		this.setComment(comment);
		this.setCurrentResponsiblePerson(currentResponsiblePerson);
		this.setClearingDonationBoxes(clearingDonationBoxes);
		this.setCollectionId(collectionId);
		this.setIsDeleted(isDeleted);
		this.setSum(sum);
	}

	/**
	 * @author Etibar Hasanov
	 * @param clearingDate
	 * @param sum
	 */
	public void clearBox(Calendar clearingDate , float sum) {
		ClearingDonationBox tempClearedDonationBox = new ClearingDonationBox(
				clearingDate, sum);
		clearingDonationBoxes.add(tempClearedDonationBox);
	}

	/**
	 * @author Etibar Hasanov
	 * @return
	 * @throws SQLException
	 */
	public float getEndSum() throws SQLException {
		float tempResult = 0;

		Iterator<ClearingDonationBox> tempIterator = clearingDonationBoxes
				.iterator();
		int tempCollectionId = DataAccess.getInstance().getCollectionIdByBoxId(
				this.getBoxId());
		while (tempIterator.hasNext()) {
			ClearingDonationBox tempCdb = tempIterator.next();
			if (DataAccess.getInstance().getCollectionIdByClearingBoxId(
					tempCdb.getClearingId()) == tempCollectionId)
				tempResult = tempResult + tempCdb.getSum();
		}
		return tempResult;
	}

	/**
	 * @author Etibar Hasanov
	 * @param donationBoxId
	 * @return
	 * @throws SQLException
	 */
	public int getCollectionIdByDonationBoxId(int donationBoxId)
			throws SQLException {
		int result = DataAccess.getInstance().getCollectionIdByBoxId(
				donationBoxId);
		return result;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "boxId=" + boxId + ",sum=" + sum + ",inUse=" + inUse
				+ ",comment=" + comment + ",responsiblePerson="
				+ currentResponsiblePerson + ",collectionId="+ collectionId +",clearingdDonationBoxes="
				+ clearingDonationBoxes + ",isDeleted=" + isDeleted;
	}
	
	
}
