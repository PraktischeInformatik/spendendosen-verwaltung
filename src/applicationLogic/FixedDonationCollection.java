package applicationLogic;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * @author Oliver Koch
 *
 */
public class FixedDonationCollection extends DonationCollection {

	private Address fixedAddress;
	private ContactPerson contactPerson;

	//TODO Fehler: Wenn DonationCollection erstellt wird, ist i.d.R. nicht bekannt, wann sie enden!
	

	
	public FixedDonationCollection(int collectionId, ArrayList<DonationBox> donationBoxes, float sum, String comment, Boolean isCompleted, OrganisationPerson organizationPerson, String beginPeriode, String endPeriode, Address fixedAddress, ContactPerson contactPerson) throws ParseException  
	{
		super(collectionId, donationBoxes, organizationPerson, sum, comment, isCompleted, beginPeriode, endPeriode);
		setFixedAddress(fixedAddress);
		setContactPerson(contactPerson);
	}

	public Address getFixedAddress() {
		return fixedAddress;
	}

	public void setFixedAddress(Address fixedAddress) {
		this.fixedAddress = fixedAddress;
	}

	public ContactPerson getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(ContactPerson contactperson) {
		this.contactPerson = contactperson;
	}

	public void addContactPerson(ContactPerson contactPerson) {
		setContactPerson(contactPerson);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "FixedDonationCollection:" + super.toString()
				+ ",ContactPerson=" + contactPerson + ",LocationAddress="
				+ fixedAddress;
	}
}
