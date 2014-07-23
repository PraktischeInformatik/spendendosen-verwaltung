package applicationLogicAccess;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import applicationLogic.ContactPersonHistory;
import applicationLogic.OrgaBoxHistory;
import applicationLogic.OrgaCollectionHistory;
import applicationLogic.PersonHistory;
import applicationLogic.PersonHistoryManager;

public class PersonHistoryAccess {
	
	static SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
	
	// Getters & Setters
	
	public static int getNextGlobalId()
	{
		return PersonHistoryManager.getInstance().getNextGlobalId();
	}
	
	public static int getGlobalId()
	{
		return PersonHistoryManager.getInstance().getGlobalId();
	}
	
	public static void setGlobalId(int globalId)
	{
		PersonHistoryManager.getInstance().setGlobalId(globalId);
	}	
	
	public static ArrayList<PersonHistory> getPersonHistories() throws SQLException{
		return PersonHistoryManager.getInstance().getPersonHistories();
	}
	
	// inserts
	
	/**
	 * adds ContactPersonHistory to ArrayList
	 * @param toAdd
	 */
	public static void insertContactPersonHistory(ContactPersonHistory toAdd)
	{
		PersonHistoryManager.getInstance().addContactPersonHistory(toAdd);
	}
	
	/**
	 * adds OrgaCollectionHistory to ArrayList
	 * @param toAdd
	 */
	public static void insertOrgaCollectionHistory(OrgaCollectionHistory toAdd)
	{
		PersonHistoryManager.getInstance().addOrgaCollectionHistory(toAdd);
	}
	
	/**
	 * adds OrgaBoxHistory to ArrayList
	 * @param toAdd
	 */
	public static void insertOrgaBoxHistory(OrgaBoxHistory toAdd)
	{
		PersonHistoryManager.getInstance().addOrgaBoxHistory(toAdd);
	}
	
	
	
	/**
	 * creates new ContactPersonHistory Object
	 * @param contactPersonId
	 * @param collectionId
	 * @param beginDate
	 * @param endDate
	 */
	public static void insertContactPersonHistory(int contactPersonId, int collectionId, String beginDate, String endDate)
	{
		ContactPersonHistory toAdd = new ContactPersonHistory(getNextGlobalId(), contactPersonId, collectionId, beginDate, endDate);
		insertContactPersonHistory(toAdd);
	}
	
	/**
	 * creates new OrgaCollectionHistory Object
	 * @param organisationPersonId
	 * @param collectionId
	 * @param beginDate
	 * @param endDate
	 */
	public static void insertOrgaCollectionHistory(int organisationPersonId, int collectionId, String beginDate, String endDate)
	{
		OrgaCollectionHistory toAdd = new OrgaCollectionHistory(getNextGlobalId(), organisationPersonId, collectionId, beginDate, endDate);
		insertOrgaCollectionHistory(toAdd);
	}
	
	
	/**
	 * creates new OrgaBoxHistory Object
	 * @param organisationPersonId
	 * @param boxId
	 * @param beginDate
	 * @param endDate
	 */
	public static void insertOrgaBoxHistory(int organisationPersonId, int boxId, String beginDate, String endDate)
	{
		OrgaBoxHistory toAdd = new OrgaBoxHistory(getNextGlobalId(), organisationPersonId, boxId, beginDate, endDate);
		insertOrgaBoxHistory(toAdd);
	}
	
	
	
	/**
	 * creates new ContactPersonHistory Object
	 * @param contactPersonId
	 * @param collectionId
	 * @param beginDate
	 * @param endDate
	 * @throws SQLException
	 * @throws ParseException
	 */
	public static void insertContactPersonHistory(int contactPersonId, int collectionId, Date beginDate, Date endDate) throws SQLException, ParseException
	{
		try
		{
			ContactPersonHistory toAdd = new ContactPersonHistory(getNextGlobalId(), contactPersonId, collectionId, sdf.format(beginDate), sdf.format(endDate));
			savenewContactPersonHistory(toAdd);			
		}
		catch(NullPointerException x)
		{
			setGlobalId(getGlobalId()-1);
			ContactPersonHistory toAdd = new ContactPersonHistory(getNextGlobalId(), contactPersonId, collectionId, sdf.format(beginDate), null); 
			savenewContactPersonHistory(toAdd);
		}
	}
		
	/**
	 * creates new OrgaCollectionHistory Object
	 * @param organisationPersonId
	 * @param collectionId
	 * @param beginDate
	 * @param endDate
	 * @throws SQLException
	 * @throws ParseException
	 */
	public static void insertOrgaCollectionHistory(int organisationPersonId, int collectionId, Date beginDate, Date endDate) throws SQLException, ParseException
	{
		try
		{
			OrgaCollectionHistory toAdd = new OrgaCollectionHistory(getNextGlobalId(), organisationPersonId, collectionId, sdf.format(beginDate), sdf.format(endDate));
			savenewOrgaCollectionHistory(toAdd);			
		}
		catch(NullPointerException x) 
		{
			setGlobalId(getGlobalId()-1);
			OrgaCollectionHistory toAdd = new OrgaCollectionHistory(getNextGlobalId(), organisationPersonId, collectionId, sdf.format(beginDate), null); 
			savenewOrgaCollectionHistory(toAdd);
		}
	}	
		
	/**
	 * creates new OrgaBoxHistory Object
	 * @param organisationPersonId
	 * @param boxId
	 * @param beginDate
	 * @param endDate
	 * @throws SQLException
	 * @throws ParseException
	 */
	public static void insertOrgaBoxHistory(int organisationPersonId, int boxId, Date beginDate, Date endDate) throws SQLException, ParseException
	{
		try
		{
			OrgaBoxHistory toAdd = new OrgaBoxHistory(getNextGlobalId(), organisationPersonId, boxId, sdf.format(beginDate), sdf.format(endDate));
			savenewOrgaBoxHistory(toAdd);			
		}
		catch(NullPointerException x) 
		{
			setGlobalId(getGlobalId()-1);
			OrgaBoxHistory toAdd = new OrgaBoxHistory(getNextGlobalId(), organisationPersonId, boxId, sdf.format(beginDate), null); 
			savenewOrgaBoxHistory(toAdd);
		}
	}
	
	// Saves & Loads	
	
	/**
	 * saves new ContactPersonHistory Object in DB
	 * @param history
	 * @throws SQLException
	 * @throws ParseException
	 */
	public static void savenewContactPersonHistory (ContactPersonHistory history) throws SQLException, ParseException{
		PersonHistoryManager.getInstance().savenewContactPersonHistory(history);
	}
	
	/**
	 * saves already existing ContactPersonHistory in DB by overwriting old one
	 * @param history
	 * @throws SQLException
	 */
	public static void editContactPersonHistory(ContactPersonHistory history) throws SQLException
	{
		PersonHistoryManager.getInstance().editContactPersonHistory(history);
	}
	
	/**
	 * saves new OrgaCollectionHistory Object in DB
	 * @param history
	 * @throws SQLException
	 * @throws ParseException
	 */
	public static void savenewOrgaCollectionHistory (OrgaCollectionHistory history) throws SQLException, ParseException{
		PersonHistoryManager.getInstance().savenewOrgaCollectionHistory(history);
	}
	
	/**
	 * saves already existing OrgaCollectionHistory in DB by overwriting old one
	 * @param history
	 * @throws SQLException
	 */
	public static void editOrgaCollectionHistory(OrgaCollectionHistory history) throws SQLException
	{
		PersonHistoryManager.getInstance().editOrgaCollectionHistory(history);
	}
	
	/**
	 * saves new OrgaBoxHistory Object in DB
	 * @param history
	 * @throws SQLException
	 * @throws ParseException
	 */
	public static void savenewOrgaBoxHistory (OrgaBoxHistory history) throws SQLException, ParseException{
		PersonHistoryManager.getInstance().savenewOrgaBoxHistory(history);
	}
	
	/**
	 * saves already existing OrgaBoxHistory in DB by overwriting old one
	 * @param history
	 * @throws SQLException
	 */
	public static void editOrgaBoxHistory(OrgaBoxHistory history) throws SQLException
	{
		PersonHistoryManager.getInstance().editOrgaBoxHistory(history);
	}
	
	/**
	 * loads all Histories from DB and sets them as attribute(personHistories) in PersonHistoryManager
	 * @throws SQLException
	 */
	public static void loadAllHistoriesHistoriesFromDB() throws SQLException
	{
		PersonHistoryManager.getInstance().loadAllHistoriesFromDB();
	}
	
	// Methods
	
	/**
	 * returns all ContactPersonHistories filtered by collectionId
	 * @param collectionId
	 * @return
	 */
	public static ArrayList<PersonHistory> searchContactHistoriesByCollectionId(int collectionId)
	{
		return PersonHistoryManager.getInstance().searchContactHistoriesByCollectionId(collectionId);
	}
	
	/**
	 * returns all OrgaCollectionHistories filtered by collectionId
	 * @param collectionId
	 * @return
	 */
	public static ArrayList<PersonHistory> searchOrgaCollectionHistoriesByCollectionId(int collectionId)
	{
		return PersonHistoryManager.getInstance().searchOrganisationHistoriesByCollectionId(collectionId);
	}
	
	/**
	 * returns all OrgaBoxHistories filtered by collectionId
	 * @param boxId
	 * @return
	 */
	public static ArrayList<PersonHistory> searchOrgaBoxHistoriesByCollectionId(int boxId)
	{
		return PersonHistoryManager.getInstance().searchOrganisationHistoriesByBoxId(boxId);
	}

}
