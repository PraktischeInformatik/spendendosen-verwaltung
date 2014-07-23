package applicationLogic;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;

import applicationLogicAccess.CollectionAccess;
import dataAccess.DataAccess;



/**
 * @author Oliver Koch
 *
 */
public abstract class DonationCollection {
	
	private int collectionId;
	private ArrayList<DonationBox> donationboxes;
	private OrganisationPerson organizationPerson;
	private float sum;
	private String comment;
	private Boolean isCompleted;
	private Calendar beginPeriode;
	private Calendar endPeriode;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

	/**
	 * @param collectionId
	 * @param donationboxes
	 * @param organizationPerson
	 * @param sum
	 * @param comment
	 * @param isCompleted
	 * @param beginPeriode
	 * @param endPeriode
	 * @throws ParseException
	 */
	
	public DonationCollection(int collectionId, ArrayList<DonationBox> donationboxes, OrganisationPerson organizationPerson, float sum, String comment, Boolean isCompleted, String beginPeriode, String endPeriode) throws ParseException {
		
		Calendar calStart = Calendar.getInstance();
		Calendar calEnd = Calendar.getInstance();
		setCollectionId(collectionId);
		setDonationboxes(donationboxes);
		setOrganizationPerson(organizationPerson);
		setSum(sum);
		setComment(comment);
		setIsCompleted(isCompleted);
		calStart.setTime(sdf.parse(beginPeriode));
		setBeginPeriode(calStart);		
		try {
			calEnd.setTime(sdf.parse(endPeriode));
			setEndPeriode(calEnd);
		}
		catch(Exception x) {
			setEndPeriode(null);
		}
		
	}
	
	// methods


	/**
	 * @param organisationPerson
	 */
	
	public void addOrganisationperson(OrganisationPerson organisationPerson) {
		setOrganizationPerson(organisationPerson);
	}

	// Getters & Setters

	public int getCollectionId() {
		return collectionId;
	}

	public void setCollectionId(int collectionId) {
		this.collectionId = collectionId;
	}

	public ArrayList<DonationBox> getDonationboxes() {
		return donationboxes;
	}

	public void setDonationboxes(ArrayList<DonationBox> donationboxes) {
		this.donationboxes = donationboxes;
	}

	public OrganisationPerson getOrganizationPerson() {
		return organizationPerson;
	}

	public void setOrganizationPerson(OrganisationPerson orgaPerson) {
		this.organizationPerson = orgaPerson;
	}

	public float getSum() {
		return sum;
	}

	public void setSum(float sum) {
		this.sum = sum;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Boolean getIsCompleted() {
		return isCompleted;
	}

	public void setIsCompleted(Boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	public Calendar getBeginPeriode() {
		return beginPeriode;
	}

	public void setBeginPeriode(Calendar beginPeriode) {
		this.beginPeriode = beginPeriode;
	}

	public Calendar getEndPeriode() {
		return endPeriode;
	}

	public void setEndPeriode(Calendar endPeriode) {
		this.endPeriode = endPeriode;
	}
	
	

	@Override
	public String toString() {
		
		String endPeriodeString;
		if(endPeriode == null){
			endPeriodeString = null;
		} else {
			endPeriodeString = sdf.format(endPeriode.getTime());
		}
		
		return "collectionId=" + collectionId + ",sum=" + sum + ",comment="
				+ comment + ",isCompleted=" + isCompleted + ",beginPeriode="
				+ sdf.format(beginPeriode.getTime()) + ",endPeriode=" + endPeriodeString
				+ ",donationBoxes=" + donationboxes + ",organisationPerson="
				+ organizationPerson;
	}
}
