package dataAccess;

import java.sql.SQLException;
import java.text.ParseException;

import applicationLogic.Address;
import applicationLogic.BoxHistory;
import applicationLogic.ClearingDonationBox;
import applicationLogic.ContactPerson;
import applicationLogic.ContactPersonHistory;
import applicationLogic.DonationBox;
import applicationLogic.FixedDonationCollection;
import applicationLogic.OrgaBoxHistory;
import applicationLogic.OrgaCollectionHistory;
import applicationLogic.OrganisationPerson;
import applicationLogic.StreetDonationCollection;



/**
 * this class creates test datas into database
 * @author Sebastian
 *
 */

public class Test {

	public static void main(String[] args) throws SQLException, ParseException {
		
		DataAccess dac = DataAccess.getInstance();
	    dac.initDataAccessConnection();
	    
	    dac.createBasicTables();
    	
//    	dac.storeNextBoxIdIntoDB(99);
//    	dac.storeNextDonationCollectionIdIntoDB(44);
//    	dac.storeNextPersonIdIntoDB(33);
//    	dac.storeNextClearingIdIntoDB(6668);
    	
//    	dac.storeDonationBoxIntoDB(new DonationBox(false, "supertoll", new OrganisationPerson("Hans", "Wurst", new Address("hallo", 33, 34567, "toll"), "supertoll@pups.de", 
//    			"12345676", "019283437", "test",true)));
//    	
//    	dac.storeContactPersonIntoDB(new ContactPerson("Hans", "Wurst", new Address("hallo", 33, 34567, "toll"), "supertoll@pups.de", 
//    			"12345676", "019283437", "test",true));
//    	
//    	dac.storeOrganisationPersonIntoDB(new OrganisationPerson("Karl", "Heinz", new Address("hallo", 33, 34567, "toll"), "milchreis@cool.de", 
//    			"073635272", "0393862352", "test2", true));
    	
//    	System.out.println(dac.getPersonsFromDB().toString());
//    	
//    	System.out.println(dac.getNextPersonIdFromDB());
//    	System.out.println(dac.getNextDonationCollectionIdFromDB());
//    	System.out.println(dac.getNextBoxIdFromDB());
//    	System.out.println(dac.getNextClearingIdFromDB());
    	
//    	dac.editPersonInDB(new ContactPerson("Hans-Peter", "Wurst", new Address("hallo", 33, 34567, "toll"), "supertoll@toll.de", 
//    			"12345676", "019283437", "test",true));
    	
//    	dac.editPersonInDB(new ContactPerson("Hans-Peter", "Wurst", new Address(), "supertoll@toll.de", 
//    			"12345676", "019283437", "test"));
//    	dac.editPersonInDB(new ContactPerson("Hans-Peter", "Wurst", new Address(), "supertoll@toll.de", 
//    			"12345676", "019283437", "test"));
    	
// 
    	
//    	System.out.println(dac.getPersonsFromDB().toString());
    	
//    	dac.storeFixedDonationCollectionIntoDB(new FixedDonationCollection(2, null, null, 0, "test", false, null, null, null, null));
//    	
//    	System.out.println(dac.getDonationBoxesFromDB());
    	

    	dac.storeDonationBoxIntoDB(new DonationBox(1, 0, false, "test", null, null, 0,false));
    	dac.storeDonationBoxIntoDB(new DonationBox(2, 0, true, "super box", null, null, 0,false));
    	dac.storeDonationBoxIntoDB(new DonationBox(3, 0, false, "schwere box", null, null, 0,false));
    	dac.storeDonationBoxIntoDB(new DonationBox(4, 0, false, "leichte box", null, null, 0,false));
    	dac.storeDonationBoxIntoDB(new DonationBox(5, 0, false, "kleine box", null, null, 0, false));
    	dac.storeDonationBoxIntoDB(new DonationBox(6, 0, true, "bunte box", null, null, 0, false));
    	dac.storeDonationBoxIntoDB(new DonationBox(7, 0, false, "geile box", null, null, 0, false));
    	dac.storeDonationBoxIntoDB(new DonationBox(8, 0, true, "neue box", null, null, 0,false));
//    	dac.storeDonationBoxIntoDB(new DonationBox(9, 0, false, "neue box", null, null, 0,false));
    	
    	dac.storeContactPersonIntoDB(new ContactPerson(1, "Hans", "Wurst", new Address("hallo", "1", 34567, "Gutentag"), "supertoll@pups.de", 
    			"12345676", "019283437", "test",true));
    	dac.storeOrganisationPersonIntoDB(new OrganisationPerson(2, "Karl", "Heinz", new Address("spargel", "33", 34567, "pizzadorf"), "milchreis@cool.de", 
    			"073635272", "0393862352", "test2", true));
    	dac.storeOrganisationPersonIntoDB(new OrganisationPerson(3, "Kalglna", "asjdjsa", new Address("ususuusu", "32", 76255, "hafjks"), "hsnhez@39321.de", 
    			"62717111122", "22736353682", "test3", true));
    	dac.storeContactPersonIntoDB(new ContactPerson(4, "Alan", "Cool", new Address("gutermann", "38", 34567, "burgstadt"), "mrcool@pups.de", 
    			"12345676", "019283437", "test",true));
    	dac.storeContactPersonIntoDB(new ContactPerson(5, "Peter", "Lustig", new Address("LustigeStr", "300", 34567, "salztal"), "jshsnass@web.de", 
    			"12345676", "019283437", "test",true));
    	dac.storeOrganisationPersonIntoDB(new OrganisationPerson(6, "Uwe", "Baer", new Address("Hirschweg", "7", 34567, "toll"), "oma@sau.de", 
    			"073635272", "0393862352", "test2", true));
    
    	
    	
    	
//    	dac.storeFixedDonationCollectionIntoDB(new FixedDonationCollection(0, null, 299, "comment", false, null, null, null, new Address("ahsdklas", "22", 23261, "dort"), null));
    	dac.storeFixedDonationCollectionIntoDB(new FixedDonationCollection(1, null, 163.65f, "coll1", true, null, "01.01.2000", "31.12.2000", new Address("ahsdklas", "22", 23261, "dort"), null));
    	dac.storeStreetDonationCollectionIntoDB(new StreetDonationCollection(2, null, 210.43f, "coll2", null, false, "01.01.2001", null));
    	dac.storeFixedDonationCollectionIntoDB(new FixedDonationCollection(3, null, 850.33f, "coll3", true, null, "01.01.2002", "31.12.2002", new Address("ahsdklas", "22", 11111, "dort"), null));
    	dac.storeStreetDonationCollectionIntoDB(new StreetDonationCollection(4, null, 132, "coll4", null, true, "01.01.2003", "31.12.2003"));
    	dac.storeStreetDonationCollectionIntoDB(new StreetDonationCollection(5, null, 149.22f, "coll5", null, true, "01.01.2004", "31.12.2004"));
    	dac.storeFixedDonationCollectionIntoDB(new FixedDonationCollection(6, null, 155, "coll6", false, null, "01.01.2005", null, new Address("ahsdklas", "22", 22222, "dort"), null));
    	
    	dac.storeClearingDonationBoxIntoDatabase(new ClearingDonationBox(1, "02.04.2000", 50));
    	dac.storeClearingDonationBoxIntoDatabase(new ClearingDonationBox(2, "02.12.2000", 45.65f));
    	dac.storeClearingDonationBoxIntoDatabase(new ClearingDonationBox(3, "04.04.2001", 10));
    	dac.storeClearingDonationBoxIntoDatabase(new ClearingDonationBox(4, "05.08.2001", 200.43f));
    	dac.storeClearingDonationBoxIntoDatabase(new ClearingDonationBox(5, "02.04.2002", 66));
    	dac.storeClearingDonationBoxIntoDatabase(new ClearingDonationBox(6, "02.06.2002", 784.33f));
    	dac.storeClearingDonationBoxIntoDatabase(new ClearingDonationBox(7, "04.04.2003", 88));
    	dac.storeClearingDonationBoxIntoDatabase(new ClearingDonationBox(8, "02.09.2003", 44));
    	dac.storeClearingDonationBoxIntoDatabase(new ClearingDonationBox(9, "22.01.2004", 50.22f));
    	dac.storeClearingDonationBoxIntoDatabase(new ClearingDonationBox(10, "03.09.2004", 99));
    	dac.storeClearingDonationBoxIntoDatabase(new ClearingDonationBox(11, "01.02.2005", 11));
    	dac.storeClearingDonationBoxIntoDatabase(new ClearingDonationBox(12, "04.19.2005", 34));
    	dac.storeClearingDonationBoxIntoDatabase(new ClearingDonationBox(13, "03.14.2000", 34));
    	dac.storeClearingDonationBoxIntoDatabase(new ClearingDonationBox(14, "06.17.2000", 34));
    	dac.storeClearingDonationBoxIntoDatabase(new ClearingDonationBox(15, "14.06.2014", 99));
    	dac.storeClearingDonationBoxIntoDatabase(new ClearingDonationBox(16, "12.06.2014", 11));
    	//referenzen gesetzt
    	dac.setBoxIdReferenceInClearingDonationBox(1, 1);
    	dac.setBoxIdReferenceInClearingDonationBox(1, 2);
    	dac.setBoxIdReferenceInClearingDonationBox(2, 3);
    	dac.setBoxIdReferenceInClearingDonationBox(2, 4);
    	dac.setBoxIdReferenceInClearingDonationBox(3, 5);
    	dac.setBoxIdReferenceInClearingDonationBox(3, 6);
    	dac.setBoxIdReferenceInClearingDonationBox(4, 7);
    	dac.setBoxIdReferenceInClearingDonationBox(4, 8);
    	dac.setBoxIdReferenceInClearingDonationBox(5, 9);
    	dac.setBoxIdReferenceInClearingDonationBox(5, 10);
    	dac.setBoxIdReferenceInClearingDonationBox(6, 11);
    	dac.setBoxIdReferenceInClearingDonationBox(6, 12);
    	dac.setBoxIdReferenceInClearingDonationBox(7, 13);
    	dac.setBoxIdReferenceInClearingDonationBox(7, 14);
    	dac.setBoxIdReferenceInClearingDonationBox(8, 15);
    	dac.setBoxIdReferenceInClearingDonationBox(8, 16);
    	
    	
    	dac.setCollectionIdReferenceInClearingDonationBox(1, 1);
    	dac.setCollectionIdReferenceInClearingDonationBox(1, 2);
    	dac.setCollectionIdReferenceInClearingDonationBox(2, 3);
    	dac.setCollectionIdReferenceInClearingDonationBox(2, 4);
    	dac.setCollectionIdReferenceInClearingDonationBox(3, 5);
    	dac.setCollectionIdReferenceInClearingDonationBox(3, 6);
    	dac.setCollectionIdReferenceInClearingDonationBox(4, 7);
    	dac.setCollectionIdReferenceInClearingDonationBox(4, 8);
    	dac.setCollectionIdReferenceInClearingDonationBox(5, 9);
    	dac.setCollectionIdReferenceInClearingDonationBox(5, 10);
    	dac.setCollectionIdReferenceInClearingDonationBox(6, 11);
    	dac.setCollectionIdReferenceInClearingDonationBox(6, 12);
    	dac.setCollectionIdReferenceInClearingDonationBox(1, 13);
    	dac.setCollectionIdReferenceInClearingDonationBox(1, 14);
    	dac.setCollectionIdReferenceInClearingDonationBox(6, 15);
    	dac.setCollectionIdReferenceInClearingDonationBox(6, 16);
    	
    //	dac.setCollectionIdReferenceInDonationBox(1, 1);
    	dac.setCollectionIdReferenceInDonationBox(2, 2);
    //	dac.setCollectionIdReferenceInDonationBox(3, 3);
    //	dac.setCollectionIdReferenceInDonationBox(4, 4);
   // 	dac.setCollectionIdReferenceInDonationBox(5, 5);
    	dac.setCollectionIdReferenceInDonationBox(6, 6);
    //	dac.setCollectionIdReferenceInDonationBox(1, 7);
    	dac.setCollectionIdReferenceInDonationBox(6, 8);
    	
    	dac.setPersonIdReferenceInDonationBox(2, 1);
    	dac.setPersonIdReferenceInDonationBox(2, 2);
    	dac.setPersonIdReferenceInDonationBox(3, 3);
    	dac.setPersonIdReferenceInDonationBox(3, 4);
    	dac.setPersonIdReferenceInDonationBox(6, 5);
    	dac.setPersonIdReferenceInDonationBox(6, 6);
    	dac.setPersonIdReferenceInDonationBox(2, 7);
    	dac.setPersonIdReferenceInDonationBox(2, 8);
 //   	dac.setPersonIdReferenceInDonationBox(1000000, 9);
    	
	    dac.setOrganisationPersonReferenceInDonationCollection(2, 1);
	    dac.setOrganisationPersonReferenceInDonationCollection(2, 2);
	    dac.setOrganisationPersonReferenceInDonationCollection(3, 3);
	    dac.setOrganisationPersonReferenceInDonationCollection(3, 4);
	    dac.setOrganisationPersonReferenceInDonationCollection(6, 5);
	    dac.setOrganisationPersonReferenceInDonationCollection(6, 6);    
	   
    	dac.setContactPersonReferenceInDonationCollection(1, 1);
    	dac.setContactPersonReferenceInDonationCollection(4, 3);
    	dac.setContactPersonReferenceInDonationCollection(5, 6);
    	//store personHistoriesinCollection
    	dac.storeOrganisationPersonHistoryInCollectionIntoDB(new OrgaCollectionHistory(1, 2, 1, "01.01.2000", "31.12.2000"));
    	dac.storeOrganisationPersonHistoryInCollectionIntoDB(new OrgaCollectionHistory(2, 2, 2, "01.01.2001", null));
    	dac.storeOrganisationPersonHistoryInCollectionIntoDB(new OrgaCollectionHistory(3, 3, 3, "01.01.2002", "31.12.2002"));
    	dac.storeOrganisationPersonHistoryInCollectionIntoDB(new OrgaCollectionHistory(4, 3, 4, "01.01.2003", "31.12.2003"));
    	dac.storeOrganisationPersonHistoryInCollectionIntoDB(new OrgaCollectionHistory(5, 6, 5, "01.01.2004", "31.12.2004"));
    	dac.storeOrganisationPersonHistoryInCollectionIntoDB(new OrgaCollectionHistory(6, 6, 6, "01.01.2005", null));
    	dac.storeContactPersonHistoryInCollectionIntoDB(new ContactPersonHistory(7, 1, 1, "01.01.2000", "31.12.2000"));
    	dac.storeContactPersonHistoryInCollectionIntoDB(new ContactPersonHistory(8, 4, 3, "01.01.2002", "31.12.2002"));
    	dac.storeContactPersonHistoryInCollectionIntoDB(new ContactPersonHistory(9, 5, 6, "01.01.2005", null));
    	//store personhistory in box
    	dac.storeOrganisationPersonHistoryInBoxIntoDB(new OrgaBoxHistory(10, 2, 1, "01.01.2000", null));
    	dac.storeOrganisationPersonHistoryInBoxIntoDB(new OrgaBoxHistory(11, 2, 2, "01.01.2000", null));
    	dac.storeOrganisationPersonHistoryInBoxIntoDB(new OrgaBoxHistory(12, 3, 3, "01.01.2000", null));
    	dac.storeOrganisationPersonHistoryInBoxIntoDB(new OrgaBoxHistory(13, 3, 4, "01.01.2000", null));
    	dac.storeOrganisationPersonHistoryInBoxIntoDB(new OrgaBoxHistory(14, 6, 5, "01.01.2000", null));
    	dac.storeOrganisationPersonHistoryInBoxIntoDB(new OrgaBoxHistory(15, 6, 6, "01.01.2000", null));
    	dac.storeOrganisationPersonHistoryInBoxIntoDB(new OrgaBoxHistory(16, 2, 7, "01.01.2000", null));
    	dac.storeOrganisationPersonHistoryInBoxIntoDB(new OrgaBoxHistory(17, 2, 8, "01.01.2000", null));
    	//store boxhistories 
    	dac.storeBoxHistoryIntoDB(new BoxHistory(1, "01.01.2000", "31.12.2000", 1, 1));
    	dac.storeBoxHistoryIntoDB(new BoxHistory(2, "01.01.2001", null, 2, 2));
    	dac.storeBoxHistoryIntoDB(new BoxHistory(3, "01.01.2002", "31.12.2002", 3, 3));
    	dac.storeBoxHistoryIntoDB(new BoxHistory(4, "01.01.2003", "31.12.2003", 4, 4));
    	dac.storeBoxHistoryIntoDB(new BoxHistory(5, "01.01.2004", "31.12.2004", 5, 5));
    	dac.storeBoxHistoryIntoDB(new BoxHistory(6, "01.01.2005", null, 6, 6));
    	dac.storeBoxHistoryIntoDB(new BoxHistory(7, "01.01.2000", "31.12.2000", 1, 7));
    	dac.storeBoxHistoryIntoDB(new BoxHistory(8, "01.01.2005", null, 6, 8));
    	
    	//store global ids
    	dac.storeNextBoxIdIntoDB(10);
    	dac.storeNextClearingIdIntoDB(17);
    	dac.storeNextDonationCollectionIdIntoDB(6);
    	dac.storeNextPersonIdIntoDB(7);
    	dac.storeNextPersonHistoryIdIntoDB(17);
    	dac.storeNextBoxHistoryIdIntoDB(9);
    	
    	System.out.println(dac.getClearingDonationBoxesFromDB());
    	System.out.println(dac.getClearingDonationBoxesByBoxIdInCollectionId(1, 1));
    	
	    System.out.println(dac.getPersonsFromDB());
	    System.out.println(dac.getDonationBoxesFromDB());
	    
    	System.out.println(dac.getDonationCollectionsFromDB());
    	System.out.println(dac.getAllPersonHistoriesFromDB());
    	
//    	dac.editPersonInDB(new ContactPerson(1, "milch", "reis", new Address("a", "42", 2314115, "v"), "suppertoll", "00292891881", "231049175", "nfgolsbgrb", false));
//    	System.out.println(dac.getPersonsFromDB());
//    	dac.deleteDonationBoxByIdFromDB(1);
//    	System.out.println(dac.getDonationCollectionsFromDB());
//    	System.out.println(dac.getDonationBoxesFromDB());
    	
//    	dac.deleteDonationCollectionByIdFromDB(6);
    	
//    	dac.editFixedDonationCollectionInDB(new FixedDonationCollection(2, null, 200, "Milchreis", false, null, null, new Address("WWWWWWWWWWWWWW", "22", 23261, "dort"), null));												
    	
//    	dac.deleteDonationBoxByIdFromCollection(7);
//    	dac.deleteDonationBoxByIdFromCollection(6);
//    	dac.deleteDonationBoxByIdFromCollection(5);
//    	dac.deleteDonationBoxByIdFromCollection(4);
//    	dac.deleteDonationBoxByIdFromCollection(3);
//    	dac.deleteDonationBoxByIdFromCollection(2);
//    	dac.deleteDonationBoxByIdFromCollection(1);
    	
//    	System.out.println(dac.getDonationBoxesFromDB());
//    	System.out.println(dac.getDonationCollectionsFromDB());
    	
    	
//    	System.out.println(dac.getClearingDonationBoxesbyCollectionId(1));
//    	System.out.println(DonationCollectionManager.getInstance().calculateSumByCollectionId(1));	
    	
    	
    	
	}

}
