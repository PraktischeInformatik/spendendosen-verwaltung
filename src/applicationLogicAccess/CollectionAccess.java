package applicationLogicAccess;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import applicationLogic.Address;
import applicationLogic.ContactPerson;
import applicationLogic.DonationBox;
import applicationLogic.DonationCollection;
import applicationLogic.DonationCollectionManager;
import applicationLogic.FixedDonationCollection;
import applicationLogic.OrganisationPerson;
import applicationLogic.StreetDonationCollection;

public class CollectionAccess {
	
	static SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
	
        /**
         * This method retrieves the latest search values and uses them to filter the list before returning it
         * 
         * @return 
         * @throws ParseException 
         * @throws SQLException 
         */
	public static ArrayList<DonationCollection> getTempDonationCollections() throws SQLException, ParseException
	{
            int collId = DonationCollectionManager.getInstance().getCurrSearchCollection().getCollId();
            int orgaId = DonationCollectionManager.getInstance().getCurrSearchCollection().getOrgaId();
            int contactId = DonationCollectionManager.getInstance().getCurrSearchCollection().getContactId();
            String comment = DonationCollectionManager.getInstance().getCurrSearchCollection().getComment();
            int boxId = DonationCollectionManager.getInstance().getCurrSearchCollection().getBoxId();
            int zip = DonationCollectionManager.getInstance().getCurrSearchCollection().getZip();
            String city = DonationCollectionManager.getInstance().getCurrSearchCollection().getCity();
            Date beginStart = DonationCollectionManager.getInstance().getCurrSearchCollection().getBeginStart();
            Date beginEnd = DonationCollectionManager.getInstance().getCurrSearchCollection().getBeginEnd();
            Date endStart = DonationCollectionManager.getInstance().getCurrSearchCollection().getEndStart();
            Date endEnd = DonationCollectionManager.getInstance().getCurrSearchCollection().getEndEnd();
            Boolean Fixed = DonationCollectionManager.getInstance().getCurrSearchCollection().getShowFixed();
            Boolean Street = DonationCollectionManager.getInstance().getCurrSearchCollection().getShowStreet();            
            
            DonationCollectionManager.getInstance().setTempDonationCollections(searchDonationCollections(collId, orgaId, contactId, comment, boxId, zip, city, beginStart, beginEnd, endStart, endEnd, Fixed, Street));
            
            return DonationCollectionManager.getInstance().getTempDonationCollections();
	}
	
	public static void setTempDonationCollections(ArrayList<DonationCollection> collection)
	{
		DonationCollectionManager.getInstance().setTempDonationCollections(collection);
	}
	
	/**
	 * returns all Collections
	 * @return ArrayList <DonationCollection>
	 * @throws SQLException
	 * @throws ParseException
	 */
	public static ArrayList<DonationCollection> getAllDonationCollections() throws SQLException, ParseException
	{
		return DonationCollectionManager.getInstance().getAllDonationCollection();
	}
	
	/**
	 * returns all StreetDonationCollections
	 * @return 
	 */
	public static ArrayList <StreetDonationCollection> getAllStreetDonationCollection(){
		return DonationCollectionManager.getInstance().getAllStreetDonationCollection();
	}
	
	/**
	 * inserts DonationCollection in DB
	 * @param toAdd
	 */
	public static void insertDonationCollection(DonationCollection toAdd)
	{
		DonationCollectionManager.getInstance().addDonationCollection(toAdd);
	}
	
	/**
	 * sets global ID
	 * @param globalID
	 */
	public static void setGlobalId(int globalID)
	{
		DonationCollectionManager.getInstance().setGlobalID(globalID);
	}
	
	/**
	 * returns Global ID
	 * @return
	 */
	public static int getActualGlobalId()
	{
		return DonationCollectionManager.getInstance().getActualGlobalID();
	}
	
	/**
	 * increments global id and then returns it
	 * @return
	 */
	public static int getGlobalId()
	{
		return DonationCollectionManager.getInstance().getGlobalID();
	}

	/**
	 * creates new FixedDonationCollection Object
	 * @param donationBoxes
	 * @param organizationPerson
	 * @param comment
	 * @param beginPeriode
	 * @param endPeriode
	 * @param fixedAddress
	 * @param contactPerson
	 * @throws InvalidArgumentsException
	 * @throws ParseException
	 * @throws SQLException
	 */
	public static void insertFixedDonationCollection(ArrayList<DonationBox> donationBoxes, OrganisationPerson organizationPerson, String comment, String beginPeriode, String endPeriode, Address fixedAddress, ContactPerson contactPerson) throws InvalidArgumentsException, ParseException, SQLException
	{	
		float sum = 0;
		Boolean isCompleted = false;
		FixedDonationCollection toAdd = new FixedDonationCollection(getGlobalId(), donationBoxes, sum, comment, isCompleted, organizationPerson, beginPeriode, endPeriode, fixedAddress, contactPerson);		
		insertDonationCollection(toAdd);
	}

    /**
     * searches DB for Collections, -1 means attribut wont be filtered
     * @param collId
     * @param orgaId
     * @param contactId
     * @param comment
     * @param boxId
     * @param zip
     * @param city
     * @param beginStart
     * @param beginEnd
     * @param endStart
     * @param endEnd
     * @param Fixed
     * @param Street
     * @return
     * @throws SQLException
     * @throws ParseException
     */
    public static ArrayList<DonationCollection> searchDonationCollections(int collId, int orgaId, int contactId, String comment, int boxId, int zip, String city, Date beginStart, Date beginEnd, Date endStart, Date endEnd, Boolean Fixed, Boolean Street) throws SQLException, ParseException {
        DonationCollectionManager.getInstance().getCurrSearchCollection().setValues(collId, orgaId, contactId, comment, boxId, zip, city, beginStart, beginEnd, endStart, endEnd, Fixed, Street);
    	return DonationCollectionManager.getInstance().searchDonationCollections(collId, orgaId, contactId, comment, boxId, zip, city, beginStart, beginEnd, endStart, endEnd, Fixed, Street);    	       
    }

    /**
     * This method sets the Current Search Criteria for Donation Collections to the default values.
     */
    public static void setDonationCollectionSearchNull() {
        DonationCollectionManager.getInstance().getCurrSearchCollection().resetValues();
    }
	
	/**
	 * returns all FixedDonationCollections
	 * @return
	 */
	public static ArrayList<FixedDonationCollection> getFixedDonationCollections()
	{
		return DonationCollectionManager.getInstance().getAllFixedDonationCollection();
	}
	
	/**
	 * Creates new FixedDonationCollection Object
	 * @param donationBoxes
	 * @param organizationPerson
	 * @param comment
	 * @param beginPeriode
	 * @param endPeriode
	 * @param fixedAddress
	 * @param contactPerson
	 * @throws InvalidArgumentsException
	 * @throws ParseException
	 * @throws SQLException
	 */
	public static void insertFixedDonationCollection(ArrayList<DonationBox> donationBoxes, OrganisationPerson organizationPerson, String comment, Date beginPeriode, Date endPeriode, Address fixedAddress, ContactPerson contactPerson) throws InvalidArgumentsException, ParseException, SQLException
	{
		float sum = 0;
		Boolean isCompleted = false;
		try
		{
			FixedDonationCollection toAdd = new FixedDonationCollection(getGlobalId(), donationBoxes, sum, comment, isCompleted, organizationPerson, sdf.format(beginPeriode), sdf.format(endPeriode), fixedAddress, contactPerson);
			savenewFixedDonationCollection(toAdd);			
		}
		catch(NullPointerException x)
		{
			setGlobalId(getActualGlobalId()-1);
			FixedDonationCollection toAdd = new FixedDonationCollection(getGlobalId(), donationBoxes, sum, comment, isCompleted, organizationPerson, sdf.format(beginPeriode), null, fixedAddress, contactPerson);
			savenewFixedDonationCollection(toAdd);
		}
	}
	
	/**
	 * Creates new StreetDonationCollection Object
	 * @param donationBoxes
	 * @param organizationPerson
	 * @param comment
	 * @param beginPeriode
	 * @param endPeriode
	 * @throws InvalidArgumentsException
	 * @throws ParseException
	 * @throws SQLException
	 */
	public static void insertStreetDonationCollection (ArrayList<DonationBox> donationBoxes, OrganisationPerson organizationPerson, String comment, String beginPeriode, String endPeriode) throws InvalidArgumentsException, ParseException, SQLException
	{
		float sum = 0;
		Boolean isCompleted = false;
		StreetDonationCollection toAdd = new StreetDonationCollection(getGlobalId(), donationBoxes, sum, comment, organizationPerson, isCompleted, beginPeriode, endPeriode);
		savenewStreetDonationCollection(toAdd);
	}
	
	/**
	 * Creates new StreetDonationCollection Object
	 * @param donationBoxes
	 * @param organizationPerson
	 * @param comment
	 * @param beginPeriode
	 * @param endPeriode
	 * @throws InvalidArgumentsException
	 * @throws ParseException
	 * @throws SQLException
	 */
	public static void insertStreetDonationCollection (ArrayList<DonationBox> donationBoxes, OrganisationPerson organizationPerson, String comment, Date beginPeriode, Date endPeriode) throws InvalidArgumentsException, ParseException, SQLException
	{
		float sum = 0;
		Boolean isCompleted = false;
		try
		{
			StreetDonationCollection toAdd = new StreetDonationCollection(getGlobalId(), donationBoxes, sum, comment, organizationPerson, isCompleted, sdf.format(beginPeriode), sdf.format(endPeriode));
			savenewStreetDonationCollection(toAdd);			
		}
		catch(NullPointerException x)
		{
			setGlobalId(getActualGlobalId()-1);
			StreetDonationCollection toAdd = new StreetDonationCollection(getGlobalId(), donationBoxes, sum, comment, organizationPerson, isCompleted, sdf.format(beginPeriode), null);
			savenewStreetDonationCollection(toAdd);
		}
	}
	
	/**
	 * filters DonationCollections by ID
	 * @param collectionId
	 * @return
	 */
	public static DonationCollection searchDonationCollectionById(int collectionId)
	{
		return DonationCollectionManager.getInstance().searchDonationCollectionById(collectionId);
	}
	
	/**
	 * filters DonationCollections by isCompleted
	 * @param bool
	 * @param collections
	 * @return
	 */
	public ArrayList<DonationCollection> searchDonationCollectionByIsCompleted(Boolean bool, ArrayList<DonationCollection> collections) 
	{
		return DonationCollectionManager.getInstance().searchDonationCollectionByIsCompleted(bool, collections);
	}
	
	/**
	 * filters given list of DonationCollection by OrganisationPerson
	 * @param orgaId
	 * @param collections
	 * @return
	 */
	public ArrayList<DonationCollection> searchDonationCollectionByOrganisationPerson (int orgaId, ArrayList<DonationCollection> collections) 
	{
		return DonationCollectionManager.getInstance().searchDonationCollectionByOrganisationPersonId(orgaId, collections);
	}
		
	/**
	 * filters given list of DonationCollection by ContactPerson
	 * @param contactId
	 * @param collections
	 * @return
	 */
	public ArrayList<DonationCollection> searchDonationCollectionByContactPerson (int contactId, ArrayList<DonationCollection> collections)
	{
		return DonationCollectionManager.getInstance().searchDonationCollectionByContactPersonId(contactId, collections);
	}
	
	/**
	 * filters given list of DonationCollection by ID
	 * @param collectionId
	 * @param collections
	 * @return
	 */
	public ArrayList<DonationCollection> searchDonationCollectionListById(int collectionId, ArrayList<DonationCollection> collections)
	{
		return DonationCollectionManager.getInstance().searchDonationCollectionListById(collectionId, collections);
	}
	
	/**
	 * loads all DonationCollection from DB
	 * @throws SQLException
	 * @throws ParseException
	 */
	public static void loadAllCollectionsFromDB() throws SQLException, ParseException
	{
		DonationCollectionManager.getInstance().loadAllCollectionsFromDB();
	}
	
	/**
	 * saves already existing FixedDonationCollection in DB by overwriting old one
	 * @param collection
	 * @throws SQLException
	 * @throws ParseException
	 */
	public static void saveoldFixedDonationCollection (FixedDonationCollection collection) throws SQLException, ParseException{
		DonationCollectionManager.getInstance().saveoldFixedDonationCollection(collection);
	}
	
	/**
	 * edits an already existing FixedDonationCollection
	 * @param collectionId
	 * @param donationBoxes
	 * @param comment
	 * @param organisationPerson
	 * @param beginPeriode
	 * @param endPeriode
	 * @param fixedAddress
	 * @param contactPerson
	 * @throws SQLException
	 * @throws ParseException
	 */
	public static void editFixedDonationCollection (int collectionId, ArrayList<DonationBox> donationBoxes, String comment, OrganisationPerson organisationPerson, Date beginPeriode, Date endPeriode, Address fixedAddress, ContactPerson contactPerson) throws SQLException, ParseException{
		DonationCollectionManager.getInstance().editFixedDonationCollection(collectionId, donationBoxes, comment, organisationPerson, beginPeriode, endPeriode, fixedAddress, contactPerson);
	}
	
	/**
	 * inserts new FixedDonationCollection in DB
	 * @param collection
	 * @throws SQLException
	 * @throws ParseException
	 */
	public static void savenewFixedDonationCollection (FixedDonationCollection collection) throws SQLException, ParseException{
		DonationCollectionManager.getInstance().savenewFixedDonationCollection(collection);
	}
	
	/**
	 * saves already existing StreetDonationCollection in DB by overwriting old one
	 * @param collection
	 * @throws SQLException
	 * @throws ParseException
	 */
	public static void saveoldStreetDonationCollection (StreetDonationCollection collection) throws SQLException, ParseException{
		DonationCollectionManager.getInstance().saveoldStreetDonationCollection(collection);
	}
	
	/**
	 * edits an already existing StreetDonationCollection
	 * @param collectionId
	 * @param donationBoxes
	 * @param comment
	 * @param organisationPerson
	 * @param beginPeriode
	 * @param endPeriode
	 * @throws SQLException
	 * @throws ParseException
	 */
	public static void editStreetDonationCollection (int collectionId, ArrayList<DonationBox> donationBoxes, String comment, OrganisationPerson organisationPerson, Date beginPeriode, Date endPeriode) throws SQLException, ParseException{
		DonationCollectionManager.getInstance().editStreetDonationCollection(collectionId, donationBoxes, comment, organisationPerson, beginPeriode, endPeriode);
	}

	/**
	 * saves new StreetDonationCollection Object in DB
	 * @param collection
	 * @throws SQLException
	 * @throws ParseException
	 */
	public static void savenewStreetDonationCollection (StreetDonationCollection collection) throws SQLException, ParseException{
		DonationCollectionManager.getInstance().savenewStreetDonationCollection(collection);
	}
	
	/**
	 * Deletes Box and its reference from a collection
	 * @param boxId
	 * @throws SQLException
	 * @throws ParseException
	 */
	public static void deleteBoxFromDonationCollection(int boxId) throws SQLException, ParseException
	{
		DonationCollectionManager.getInstance().deleteBoxFromDonationCollection(boxId);
	}
	
	/**
	 * finishes collection, after that there is now way of changing it
	 * @param collectionId
	 * @throws SQLException
	 * @throws ParseException
	 */
	public static void finishCollection(int collectionId) throws SQLException, ParseException
	{
		DonationCollectionManager.getInstance().finishCollection(collectionId);
	}
	
	/**
	 * checks if collection is finished
	 * @param collectionId
	 * @return
	 */
	public static Boolean checkIfIsCompleted(int collectionId)
	{
		return DonationCollectionManager.getInstance().checkIsCompleted(collectionId);
	}
	
	
	//added sz
	/**
	 * calculates sum of collection and puts the result in sum 
	 * @param collectionId
	 * @throws SQLException
	 * @throws ParseException
	 */
	public static void calculateSumByCollectionId (int collectionId) throws SQLException, ParseException{
		
		DonationCollectionManager.getInstance().calculateSumByCollectionId(collectionId);
		
	}
}
