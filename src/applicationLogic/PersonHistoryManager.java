package applicationLogic;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import applicationLogicAccess.PersonHistoryAccess;
import dataAccess.DataAccess;

public class PersonHistoryManager {
	
	private static int globalId = 0;
	
	private ArrayList<PersonHistory> personHistories = new ArrayList<PersonHistory>();
	private static PersonHistoryManager instance = null;
	
	// Getters & Setters
	
	public static int getGlobalId() {
		return globalId;
	}
	
	public static void setGlobalId(int globalId) {
		PersonHistoryManager.globalId = globalId;
	}
	
	public ArrayList<PersonHistory> getPersonHistories() throws SQLException {
		loadAllHistoriesFromDB();
		return personHistories;
	}
	
	public void setPersonHistories(ArrayList<PersonHistory> personHistories) {
		this.personHistories = personHistories;
	}
	
	/**
	 * creates new object of PersonHistoryManager if its not already existing. Object is needed so you use all methods, always type ".getInstance" before using any function
	 * @return
	 */
	public static PersonHistoryManager getInstance() {
		if (instance == null) 
		{
			instance = new PersonHistoryManager();
		}
		return instance;
	}
	
	
	/**
	 * increments globalId, then returns it
	 * @return
	 */
	public int getNextGlobalId() {
		globalId++;
		return globalId;
	}
	
	// Constructor
	
	public PersonHistoryManager()
	{
		PersonHistoryManager.setGlobalId(0);
	}
	
	// Inserts
	
	/**
	 * adds ContactPersonHistory to ArrayList
	 * @param toAdd
	 */
	public void addContactPersonHistory(ContactPersonHistory toAdd) {
		personHistories.add(toAdd);
	}
	
	/**
	 * adds OrgaCollectionHistory to ArrayList
	 * @param toAdd
	 */
	public void addOrgaCollectionHistory(OrgaCollectionHistory toAdd)
	{
		personHistories.add(toAdd);
	}
	
	/**
	 * adds OrgaBoxHistory to ArrayList
	 * @param toAdd
	 */
	public void addOrgaBoxHistory(OrgaBoxHistory toAdd)
	{
		personHistories.add(toAdd);
	}
	
	// Saves & Loads
	
	/**
	 * saves new ContactPersonHistory Object in DB
	 * @param history
	 * @throws SQLException
	 */
	public void savenewContactPersonHistory(ContactPersonHistory history) throws SQLException 
	{
		DataAccess.getInstance().storeContactPersonHistoryInCollectionIntoDB(history);
		DataAccess.getInstance().storeNextPersonHistoryIdIntoDB(PersonHistoryAccess.getGlobalId());
		PersonHistoryAccess.loadAllHistoriesHistoriesFromDB();
	}
	
	/**
	 * saves already existing ContactPersonHistory in DB by overwriting old one
	 * @param history
	 * @throws SQLException
	 */
	public void editContactPersonHistory(ContactPersonHistory history) throws SQLException 
	{
		DataAccess.getInstance().editContactPersonHistoryInCollectionInDB(history);
		PersonHistoryAccess.loadAllHistoriesHistoriesFromDB();
	}
	
	/**
	 * saves new OrgCollectionHistory Object in DB
	 * @param history
	 * @throws SQLException
	 */
	public void savenewOrgaCollectionHistory(OrgaCollectionHistory history) throws SQLException 
	{
		DataAccess.getInstance().storeOrganisationPersonHistoryInCollectionIntoDB(history);	
		DataAccess.getInstance().storeNextPersonHistoryIdIntoDB(PersonHistoryAccess.getGlobalId());
		PersonHistoryAccess.loadAllHistoriesHistoriesFromDB();
	}
	
	/**
	 * saves already existing OrgaCollectionHistory in DB by overwriting old one
	 * @param history
	 * @throws SQLException
	 */
	public void editOrgaCollectionHistory(OrgaCollectionHistory history) throws SQLException 
	{
		DataAccess.getInstance().editOrganisationPersonHistoryInCollectionInDB(history);
		PersonHistoryAccess.loadAllHistoriesHistoriesFromDB();
	}
	
	/**
	 * saves new OrgaBoxHistory Object in DB
	 * @param history
	 * @throws SQLException
	 */
	public void savenewOrgaBoxHistory(OrgaBoxHistory history) throws SQLException 
	{
		DataAccess.getInstance().storeOrganisationPersonHistoryInBoxIntoDB(history);
		DataAccess.getInstance().storeNextPersonHistoryIdIntoDB(PersonHistoryAccess.getGlobalId());
		PersonHistoryAccess.loadAllHistoriesHistoriesFromDB();
	}
	
	/**
	 * saves already existing OrgaBoxHistory in DB by overwriting old one
	 * @param history
	 * @throws SQLException
	 */
	public void editOrgaBoxHistory(OrgaBoxHistory history) throws SQLException 
	{
		DataAccess.getInstance().editOrganisationPersonHistoryInBoxInDB(history);
		PersonHistoryAccess.loadAllHistoriesHistoriesFromDB();
	}
	
	/**
	 * loads all Histories from DB and sets them as attribute(personHistories)
	 * @throws SQLException
	 */
	public void loadAllHistoriesFromDB() throws SQLException 
	{
		PersonHistoryManager.getInstance().setPersonHistories(DataAccess.getInstance().getAllPersonHistoriesFromDB());						
		PersonHistoryManager.setGlobalId(DataAccess.getInstance().getNextPersonHistoryIdFromDB());
	}
	
	// Methods
	
	/**
	 * returns all ContactPersonHistories 
	 * @return
	 */
	public ArrayList<PersonHistory> searchContactHistories()
	{
		ArrayList<PersonHistory> result = new ArrayList<PersonHistory>();
		for (PersonHistory tempresult : personHistories) {
			if(tempresult.getClass().getSimpleName().equals("ContactPersonHistory"))
			{
				result.add((ContactPersonHistory) tempresult);
			}
		}
		return result;		
	}
	
	/**
	 * returns all OrgaCollectionHistories
	 * @return
	 */
	public ArrayList<PersonHistory> searchOrgaCollectionHistories()
	{
		ArrayList<PersonHistory> result = new ArrayList<PersonHistory>();
		for (PersonHistory tempresult : personHistories) {
			if(tempresult.getClass().getSimpleName().equals("OrgaCollectionHistory"))
			{
				result.add((OrgaCollectionHistory) tempresult);
			}
		}
		return result;		
	}
	
	/**
	 * returns all OrgaBoxHistories 
	 * @return
	 */
	public ArrayList<PersonHistory> searchOrgaBoxHistories()
	{
		ArrayList<PersonHistory> result = new ArrayList<PersonHistory>();
		for (PersonHistory tempresult : personHistories) {
			if(tempresult.getClass().getSimpleName().equals("OrgaBoxHistory"))
			{
				result.add((OrgaBoxHistory) tempresult);
			}
		}
		return result;		
	}
	
	
	/**
	 * returns all ContactPersonHistories filtered by collectionId
	 * @param collectionId
	 * @return
	 */
	public ArrayList<PersonHistory> searchContactHistoriesByCollectionId(int collectionId) {

		ArrayList<PersonHistory> histories = searchContactHistories();
		ArrayList<PersonHistory> result = new ArrayList<PersonHistory>();
		Iterator<PersonHistory> iterator = histories.iterator();
		while (iterator.hasNext()) {
			PersonHistory tempresult = iterator.next();
			ContactPersonHistory history = (ContactPersonHistory) tempresult;
			if (history.getCollectionId() == collectionId) {
				
				result.add(history);
			}
		}
		return result;		
	}
	
	/**
	 * returns all OrgaCollectionHistories filtered by collectionId
	 * @param collectionId
	 * @return
	 */
	public ArrayList<PersonHistory> searchOrganisationHistoriesByCollectionId(int collectionId) {

		ArrayList<PersonHistory> histories = searchOrgaCollectionHistories();
		ArrayList<PersonHistory> result = new ArrayList<PersonHistory>();
		Iterator<PersonHistory> iterator = histories.iterator();
		while (iterator.hasNext()) {
			PersonHistory tempresult = iterator.next();
			OrgaCollectionHistory history = (OrgaCollectionHistory) tempresult;
			if (history.getCollectionId() == collectionId) {
				result.add(history);
			}
		}
		return result;
	}
	
	
	/**
	 * returns all OrgaBoxHistories filtered by collectionId
	 * @param boxId
	 * @return
	 */
	public ArrayList<PersonHistory> searchOrganisationHistoriesByBoxId(int boxId) {

		ArrayList<PersonHistory> histories = searchOrgaBoxHistories();
		ArrayList<PersonHistory> result = new ArrayList<PersonHistory>();
		Iterator<PersonHistory> iterator = histories.iterator();
		while (iterator.hasNext()) {
			PersonHistory tempresult = iterator.next();
			OrgaBoxHistory history = (OrgaBoxHistory) tempresult;
			if (history.getBoxId() == boxId) {
				result.add(history);
			}
		}
		return result;
	}
	
}
