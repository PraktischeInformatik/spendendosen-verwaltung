package dataAccess;

import java.sql.SQLException;
import java.text.ParseException;

import applicationLogic.Address;
import applicationLogic.ClearingDonationBox;
import applicationLogic.ContactPerson;
import applicationLogic.DonationBox;
import applicationLogic.FixedDonationCollection;
import applicationLogic.OrganisationPerson;
import applicationLogic.StreetDonationCollection;

/**
 * database stress test class
 * @author Sebastian
 *
 */
public class Dbtest {

	public static void main(String[] args) throws SQLException, ParseException {
		
		DataAccess.getInstance().initDataAccessConnection();
		DataAccess.getInstance().createBasicTables();
		
		//add organisationPerson
		for(int i=1; i<500; i++){
			DataAccess.getInstance().storeOrganisationPersonIntoDB(new OrganisationPerson(i, "Karl", "Heinz", new Address("spargel", "33", 34567, "pizzadorf"), "milchreis@cool.de", 
    			"073635272", "0393862352", "test2", true));
		}
		//add contackt person
		for(int i=500; i<1000; i++){
			DataAccess.getInstance().storeContactPersonIntoDB(new ContactPerson(i, "Hans", "Wurst", new Address("hallo", "1", 34567, "Gutentag"), "supertoll@pups.de", 
	    			"12345676", "019283437", "test",true));
		}
		
		//add donationBoxes
		for(int i=1; i<1000; i++){
			DataAccess.getInstance().storeDonationBoxIntoDB(new DonationBox(i, 0, false, "test", null, null, 0,false));
			DataAccess.getInstance().setPersonIdReferenceInDonationBox(1000000, i); //represents no person
		}
		
		//add streetcollection
		for(int i=1; i<500; i++){
			DataAccess.getInstance().storeStreetDonationCollectionIntoDB(new StreetDonationCollection(i, null, 0, "coll2", null, false, "01.01.2001", null));
			DataAccess.getInstance().setOrganisationPersonReferenceInDonationCollection(1, i);
		}
		
		
		//add fixedcollection
		for(int i=500; i<1000; i++){
			DataAccess.getInstance().storeFixedDonationCollectionIntoDB(new FixedDonationCollection(i, null, 0, "coll1", false, null, "01.01.2000", "31.12.2000", new Address("ahsdklas", "22", 23261, "dort"), null));
			DataAccess.getInstance().setOrganisationPersonReferenceInDonationCollection(1, i);
			DataAccess.getInstance().setContactPersonReferenceInDonationCollection(500, i);
		}
		
		
		//add cleareings		
		for(int i=1; i<1000; i++){
			DataAccess.getInstance().storeClearingDonationBoxIntoDatabase(new ClearingDonationBox(i, "02.04.2000", 50));
			DataAccess.getInstance().setBoxIdReferenceInClearingDonationBox(1, i);
			DataAccess.getInstance().setCollectionIdReferenceInClearingDonationBox(1, i);
		}
		
		DataAccess.getInstance().storeNextBoxIdIntoDB(1000);
		DataAccess.getInstance().storeNextClearingIdIntoDB(1000);
		DataAccess.getInstance().storeNextDonationCollectionIdIntoDB(999);
		DataAccess.getInstance().storeNextPersonIdIntoDB(1000);
		
		System.out.println("ready!");
	}

}
