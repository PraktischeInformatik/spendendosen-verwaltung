package applicationLogic;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * @author Oliver Koch
 *
 */
public class StreetDonationCollection extends DonationCollection{
	
	public StreetDonationCollection(int collectionId, ArrayList<DonationBox> donationBoxes, float sum, String comment, OrganisationPerson organizationPerson, Boolean isCompleted, String beginPeriode, String endPeriode) throws ParseException
	{
		super(collectionId, donationBoxes, organizationPerson, sum, comment, isCompleted, beginPeriode, endPeriode);
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "StreetDonationCollection"+ super.toString();
	}
}
