package applicationLogic;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;

import applicationLogicAccess.Access;
import applicationLogicAccess.CollectionAccess;
import applicationLogicAccess.PersonHistoryAccess;

import com.itextpdf.text.DocumentException;

import dataAccess.DataAccess;

/**
 * @author Oliver koch
 * 
 */
public class DonationCollectionManager {

	private static int globalID = 0;
	private ArrayList<DonationCollection> tempDonationCollections = new ArrayList<DonationCollection>();
	private ArrayList<DonationCollection> DonationCollections = new ArrayList<DonationCollection>();
	private static DonationCollectionManager instance = null;
	CurrentSearchValuesDonationCollection currSearchCollection;

	/**
	 * used for first Programmstart to set the GlobalId
	 */
	public DonationCollectionManager() {
		DonationCollectionManager.setGlobalID(0);
		currSearchCollection = new CurrentSearchValuesDonationCollection();
	}

	public void setCurrSearchCollection(
			CurrentSearchValuesDonationCollection currSearchCollection) {
		this.currSearchCollection = currSearchCollection;
	}

	public CurrentSearchValuesDonationCollection getCurrSearchCollection() {
		return currSearchCollection;
	}

	/**
	 * Filters Collections and returns only FixedDonationCollections
	 * 
	 * @return ArrayList of FixedDonationCollection
	 */
	public ArrayList<FixedDonationCollection> getAllFixedDonationCollection() {
		Iterator<DonationCollection> iterator = DonationCollections.iterator();
		ArrayList<FixedDonationCollection> result = new ArrayList<FixedDonationCollection>();
		while (iterator.hasNext()) {
			DonationCollection tempresult = iterator.next();
			if (tempresult.getClass().getSimpleName()
					.equals("FixedDonationCollection")) {
				result.add((FixedDonationCollection) tempresult);
			}
		}
		return result;
	}

	/**
	 * Filters Collections and returns only StreetDonationCollections
	 * 
	 * @return ArrayList of StreetDonationCollection
	 */
	public ArrayList<StreetDonationCollection> getAllStreetDonationCollection() {
		Iterator<DonationCollection> iterator = DonationCollections.iterator();
		ArrayList<StreetDonationCollection> result = new ArrayList<StreetDonationCollection>();
		while (iterator.hasNext()) {
			DonationCollection tempresult = iterator.next();
			if (tempresult.getClass().getSimpleName()
					.equals("StreetDonationCollection")) {
				result.add((StreetDonationCollection) tempresult);
			}
		}
		return result;
	}

	/**
	 * returns all Collections
	 * 
	 * @return ArrayList of DonationCollection
	 * @throws ParseException
	 * @throws SQLException
	 */
	public ArrayList<DonationCollection> getAllDonationCollection()
			throws SQLException, ParseException {
		loadAllCollectionsFromDB();
		return DonationCollections;
	}

	/**
	 * returns TempDonationCollections, which are used in the user interface
	 * 
	 * @author
	 * @return ArrayList of DonationCollection
	 */
	public ArrayList<DonationCollection> getTempDonationCollections() {
		return tempDonationCollections;
	}

	/**
	 * sets TempDonationCollection, which are used in the user interface
	 * 
	 * @author
	 * @param tempDonationCollections
	 */
	public void setTempDonationCollections(
			ArrayList<DonationCollection> tempDonationCollections) {
		this.tempDonationCollections = tempDonationCollections;
	}

	/**
	 * sets global ID
	 * 
	 * @param globalID
	 */
	public static void setGlobalID(int globalID) {
		DonationCollectionManager.globalID = globalID;
	}

	/**
	 * return actual globalID
	 * 
	 * @return int globalID
	 */
	public static int getActualGlobalID() {
		return globalID;
	}

	/**
	 * increments globalId and returns it, only used for generating collections
	 * 
	 * @return int globalID
	 */
	public int getGlobalID() {
		globalID++;
		return globalID;
	}

	/**
	 * @return Object of Manager
	 */
	public static DonationCollectionManager getInstance() {
		if (instance == null) {

			instance = new DonationCollectionManager();
		}
		return instance;
	}

	/**
	 * adding new DonationCollections to ArrayList
	 * 
	 * @param toAdd
	 */
	public void addDonationCollection(DonationCollection toAdd) {
		DonationCollections.add(toAdd);
	}

	/**
	 * deletes Collection from Database
	 * 
	 * @param collectionId
	 * @throws IdNotFoundException
	 * @throws SQLException
	 * @throws ParseException
	 */
	// public void deleteCollectionById(int collectionId) throws
	// IdNotFoundException, SQLException, ParseException
	// {
	// DataAccess.getInstance().deleteDonationCollectionByIdFromDB(collectionId);
	// CollectionAccess.loadAllCollectionsFromDB();
	// }

	/**
	 * filters given ArrayList of DonationCollection By collectionId
	 * 
	 * @param collectionId
	 * @return Object of DonationCollection
	 */
	public DonationCollection searchDonationCollectionById(int collectionId) {

		Iterator<DonationCollection> iterator = DonationCollections.iterator();
		while (iterator.hasNext()) {
			DonationCollection tempresult = iterator.next();
			if (tempresult.getCollectionId() == collectionId) {
				return tempresult;
			}
		}
		return null;
	}

	/**
	 * filters given ArrayList of DonationCollection By id
	 * 
	 * @param collectionId
	 * @param collections
	 * @return ArrayList of DonationCollection (should be 1 collection)
	 */
	public ArrayList<DonationCollection> searchDonationCollectionListById(
			int collectionId, ArrayList<DonationCollection> collections) {

		ArrayList<DonationCollection> result = new ArrayList<DonationCollection>();
		Iterator<DonationCollection> iterator = collections.iterator();
		while (iterator.hasNext()) {
			DonationCollection tempresult = iterator.next();
			if (tempresult.getCollectionId() == collectionId) {
				result.add(tempresult);
				return result;
			}
		}
		return null;
	}

	/**
	 * filters given ArrayList of DonationCollection By Boolean isCompleted
	 * 
	 * @param bool
	 * @param collections
	 * @return ArrayList of DonationCollection
	 */
	public ArrayList<DonationCollection> searchDonationCollectionByIsCompleted(
			Boolean bool, ArrayList<DonationCollection> collections) {
		Iterator<DonationCollection> iterator = collections.iterator();
		ArrayList<DonationCollection> result = new ArrayList<DonationCollection>();
		if (bool == true) {
			while (iterator.hasNext()) {
				DonationCollection tempresult = iterator.next();
				if (tempresult.getIsCompleted() == true) {
					result.add(tempresult);
				}
			}
		} else {
			while (iterator.hasNext()) {
				DonationCollection tempresult = iterator.next();
				if (tempresult.getIsCompleted() == false) {
					result.add(tempresult);
				}
			}

		}
		return result;
	}

	/**
	 * filters given ArrayList of DonationCollection By OrganisationPerson (Id)
	 * 
	 * @param orgaId
	 * @param collections
	 * @return ArrayList of DonationCollection
	 */
	public ArrayList<DonationCollection> searchDonationCollectionByOrganisationPersonId(
			int orgaId, ArrayList<DonationCollection> collections) {
		Iterator<DonationCollection> iterator = collections.iterator();
		ArrayList<DonationCollection> result = new ArrayList<DonationCollection>();
		while (iterator.hasNext()) {
			DonationCollection tempresult = iterator.next();
			if (tempresult.getOrganizationPerson().getPersonId() == orgaId) {
				result.add(tempresult);
			}
		}
		return result;
	}

	/**
	 * filters given ArrayList of DonationCollection By ContactPerson (id)
	 * 
	 * @param contactId
	 * @param collections
	 * @return ArrayList of DonationCollection
	 */
	public ArrayList<DonationCollection> searchDonationCollectionByContactPersonId(
			int contactId, ArrayList<DonationCollection> collections) {
		Iterator<DonationCollection> iterator = collections.iterator();
		ArrayList<DonationCollection> result = new ArrayList<DonationCollection>();

		while (iterator.hasNext()) {
			DonationCollection tempresult = iterator.next();
			if (tempresult.getClass().getSimpleName()
					.equals("FixedDonationCollection")) {
				FixedDonationCollection toAdd = (FixedDonationCollection) tempresult;
				if (toAdd.getContactPerson().getPersonId() == contactId) {
					result.add(toAdd);
				}
			}
		}

		return result;
	}

	/**
	 * filters given ArrayList of DonationCollection By Comment
	 * 
	 * @param comment
	 * @param collections
	 * @return ArrayList of DonationCollection
	 */
	public ArrayList<DonationCollection> searchDonationCollectionByComment(
			String comment, ArrayList<DonationCollection> collections) {
		Iterator<DonationCollection> iterator = collections.iterator();
		ArrayList<DonationCollection> result = new ArrayList<DonationCollection>();
		while (iterator.hasNext()) {
			DonationCollection tempresult = iterator.next();
			if (tempresult.getComment().equals(comment)) {
				result.add(tempresult);
			}
		}
		return result;
	}

	/**
	 * filters given ArrayList of DonationCollection By City
	 * 
	 * @param city
	 * @param collections
	 * @return ArrayList of DonationCollection
	 */
	public ArrayList<DonationCollection> searchDonationCollectionByCity(
			String city, ArrayList<DonationCollection> collections) {
		Iterator<DonationCollection> iterator = collections.iterator();
		ArrayList<DonationCollection> result = new ArrayList<DonationCollection>();
		while (iterator.hasNext()) {
			DonationCollection tempresult = iterator.next();
			if (tempresult.getClass().getSimpleName()
					.equals("FixedDonationCollection")) {
				FixedDonationCollection col = (FixedDonationCollection) tempresult;
				if (col.getFixedAddress().getLocationName().equals(city)) {
					result.add(col);
				}
			}
		}
		return result;
	}

	/**
	 * filters given ArrayList of DonationCollection By ZipCode
	 * 
	 * @param zip
	 * @param collections
	 * @return ArrayList of DonationCollection
	 */
	public ArrayList<DonationCollection> searchDonationCollectionByZip(int zip,
			ArrayList<DonationCollection> collections) {
		Iterator<DonationCollection> iterator = collections.iterator();
		ArrayList<DonationCollection> result = new ArrayList<DonationCollection>();
		while (iterator.hasNext()) {
			DonationCollection tempresult = iterator.next();
			if (tempresult.getClass().getSimpleName()
					.equals("FixedDonationCollection")) {
				FixedDonationCollection col = (FixedDonationCollection) tempresult;
				System.out.println("getzip " + col.getFixedAddress().getZip()
						+ " == " + zip + " ??");
				if (col.getFixedAddress().getZip() == zip) {
					System.out.println("jup");
					result.add(col);
				} else {
					System.out.println("nope");
				}
			}
		}
		return result;
	}

	/**
	 * filters given ArrayList of DonationCollection By beginStart, returns
	 * every Collection where BeginPeriode after beginStart
	 * 
	 * @param beginStart
	 * @param collections
	 * @return ArrayList of DonationCollection
	 */
	public ArrayList<DonationCollection> searchDonationCollectionByBeginStart(
			Date beginStart, ArrayList<DonationCollection> collections) {
		
		Iterator<DonationCollection> iterator = collections.iterator();
		ArrayList<DonationCollection> result = new ArrayList<DonationCollection>();
		Calendar cal = new GregorianCalendar();
		cal.setTime(beginStart);

		while (iterator.hasNext()) {
			DonationCollection tempresult = iterator.next();
			if (tempresult.getBeginPeriode().after(cal)) {
				result.add(tempresult);
			}
		}
		return result;
	}

	/**
	 * filters given ArrayList of DonationCollection by beginEnd, removes every
	 * Collection where BeginPeriode after beginEnd
	 * 
	 * @param beginEnd
	 * @param collections
	 * @return ArrayList of DonationCollection
	 */
	public ArrayList<DonationCollection> filterDonationCollectionByBeginEnd(
			Date beginEnd, ArrayList<DonationCollection> collections) {
		
		Iterator<DonationCollection> iterator = collections.iterator();
		Calendar cal = new GregorianCalendar();
		try
		{
			cal.setTime(beginEnd);
		}
		catch(Exception k) {cal.setTime(null);}
		

		while (iterator.hasNext()) {
			DonationCollection tempresult = iterator.next();
			if (tempresult.getBeginPeriode().after(cal)) {
				iterator.remove();
			}
		}
		return collections;
	}

	/**
	 * filters given ArrayList of DonationCollection By endStart, returns every
	 * Collection where EndPeriode after endStart
	 * 
	 * @param endStart
	 * @param collections
	 * @return ArrayList of DonationCollection
	 */
	public ArrayList<DonationCollection> searchDonationCollectionByEndStart(
			Date endStart, ArrayList<DonationCollection> collections) {
		Iterator<DonationCollection> iterator = collections.iterator();
		ArrayList<DonationCollection> result = new ArrayList<DonationCollection>();
		Calendar cal = new GregorianCalendar();
		cal.setTime(endStart);

		while (iterator.hasNext()) {
			DonationCollection tempresult = iterator.next();
			if (tempresult.getEndPeriode() != null) {
				if (tempresult.getEndPeriode().after(cal)) {
					result.add(tempresult);
				}
			}
		}
		return result;
	}

	/**
	 * filters given ArrayList of DonationCollection by endEnd, removes every
	 * Collection where EndPeriode after endEnd
	 * 
	 * @param endEnd
	 * @param collections
	 * @return ArrayList of DonationCollection
	 */
	public ArrayList<DonationCollection> filterDonationCollectionByEndEnd(
			Date endEnd, ArrayList<DonationCollection> collections) {
		Iterator<DonationCollection> iterator = collections.iterator();
		Calendar cal = new GregorianCalendar();
		try 
		{
		cal.setTime(endEnd);}
		catch (Exception x){cal.setTime(null);}

		while (iterator.hasNext()) {
			DonationCollection tempresult = iterator.next();
			if (tempresult.getEndPeriode().after(cal)) {
				iterator.remove();
			}
		}
		return collections;
	}

	/**
	 * filters given ArrayList of DonationCollection By
	 * 
	 * @param boxidfilters
	 * @param collections
	 * @return
	 */
	public ArrayList<DonationCollection> searchDonationCollectionByBoxId(
			int boxid, ArrayList<DonationCollection> collections) {
		ArrayList<DonationCollection> result = new ArrayList<DonationCollection>();

		for (DonationCollection curCollection : collections) {
			for (DonationBox curBox : curCollection.getDonationboxes()) {
				if (curBox.getBoxId() == boxid) {
					result.add(curCollection);
				}
			}
		}
		return result;
	}

	/**
	 * filters given ArrayList of DonationCollection by FixedDonationCollection
	 * 
	 * @param collections
	 * @return ArrayList of Donation Collection with only
	 *         FixedDonationCollections
	 */
	public ArrayList<DonationCollection> castFixedCollection(
			ArrayList<DonationCollection> collections) {
		Iterator<DonationCollection> iterator = collections.iterator();
		ArrayList<DonationCollection> result = new ArrayList<DonationCollection>();
		while (iterator.hasNext()) {
			DonationCollection tempresult = iterator.next();
			if (tempresult.getClass().getSimpleName()
					.equals("FixedDonationCollection")) {
				result.add((FixedDonationCollection) tempresult);
			}
		}
		return result;
	}

	/**
	 * filters given ArrayList of DonationCollection by StreetDonationCollection
	 * 
	 * @param collections
	 * @return ArrayList of Donation Collection with only
	 *         StreetDonationCollections
	 */
	public ArrayList<DonationCollection> castStreetCollection(
			ArrayList<DonationCollection> collections) {
		Iterator<DonationCollection> iterator = collections.iterator();
		ArrayList<DonationCollection> result = new ArrayList<DonationCollection>();
		while (iterator.hasNext()) {
			DonationCollection tempresult = iterator.next();
			if (tempresult.getClass().getSimpleName()
					.equals("StreetDonationCollection")) {
				result.add((StreetDonationCollection) tempresult);
			}
		}
		return result;
	}

	/**
	 * casts ArrayList of DonationCollection into Fixed- and
	 * StreetDonationCollections
	 * 
	 * @param collections
	 * @return ArrayList of Donation Collection
	 */
	public ArrayList<DonationCollection> castBothCollection(
			ArrayList<DonationCollection> collections) {
		Iterator<DonationCollection> iterator = collections.iterator();
		ArrayList<DonationCollection> result = new ArrayList<DonationCollection>();
		while (iterator.hasNext()) {
			DonationCollection tempresult = iterator.next();
			if (tempresult.getClass().getSimpleName()
					.equals("FixedDonationCollection")) {
				result.add((FixedDonationCollection) tempresult);
			} else {
				result.add((StreetDonationCollection) tempresult);
			}
		}
		return result;
	}

	/**
	 * filters given variables, -1 or null means wont be filtered
	 * 
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
	 * @return filtered ArrayList of DonationCollection
	 * @throws SQLException
	 * @throws ParseException
	 */
	public ArrayList<DonationCollection> searchDonationCollections(int collId,
			int orgaId, int contactId, String comment, int boxId, int zip,
			String city, Date beginStart, Date beginEnd, Date endStart,
			Date endEnd, Boolean Fixed, Boolean Street) throws SQLException,
			ParseException {

		ArrayList<DonationCollection> result = getAllDonationCollection();

		if (collId != -1) {
			result = searchDonationCollectionListById(collId, result);
		}

		if (orgaId != -1) {
			result = searchDonationCollectionByOrganisationPersonId(orgaId,
					result);
		}

		if (contactId != -1)
			result = searchDonationCollectionByContactPersonId(contactId,
					result);

		if (comment != null)
			result = searchDonationCollectionByComment(comment, result);

		if (boxId != -1)
			result = searchDonationCollectionByBoxId(boxId, result);

		if (zip != -1)
			result = searchDonationCollectionByZip(zip, result);

		if (city != null)
			result = searchDonationCollectionByCity(city, result);

		if (beginStart != null)
			result = searchDonationCollectionByBeginStart(beginStart, result);

		if (beginEnd != null)
			result = filterDonationCollectionByBeginEnd(beginEnd, result);

		if (endStart != null)
			result = searchDonationCollectionByEndStart(endStart, result);

		if (endEnd != null)
			result = filterDonationCollectionByEndEnd(endEnd, result);

		if (Street == true && Fixed == true)
			return castBothCollection(result);

		if (Street == true)
			return castStreetCollection(result);

		if (Fixed == true)
			return castFixedCollection(result);

		return null;

	}

	/**
	 * exports given ArrayList of DonationCollection as PDF
	 * 
	 * @param list
	 * @param file
	 * @throws FileNotFoundException
	 * @throws DocumentException
	 * @throws ParseException
	 * @throws SQLException
	 */
	public void exportDonationCollectionListAsPDF(String file)
			throws FileNotFoundException, DocumentException, SQLException,
			ParseException {
		/**
		 * @author KevinSchneider
		 */
		ExportToPdf.exportDonationCollectionsToPdf(tempDonationCollections,
				file);
	}

	/**
	 * exports given ArrayList of DonationCollection as .csv
	 * 
	 * @param list
	 * @param file
	 * @throws IOException
	 */
	public void exportDonationCollectionListAsCsv(String file)
			throws IOException {
		/**
		 * @author KevinSchneider
		 */
		ExportToCsv.exportDonationCollections(file, tempDonationCollections);
	}

	/**
	 * sets DonationCollections, mostly used to load Objects from DB
	 * @param allCollections
	 */
	public void setAllDonationCollections(
			ArrayList<DonationCollection> allCollections) {
		this.DonationCollections = allCollections;
	}

	/**
	 * loads all collections from database and sets it as attribute
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void loadAllCollectionsFromDB() throws SQLException, ParseException {
		DonationCollectionManager.getInstance().setAllDonationCollections(
				DataAccess.getInstance().getDonationCollectionsFromDB());
		DonationCollectionManager.setGlobalID(DataAccess.getInstance()
				.getNextDonationCollectionIdFromDB());
	}

	/**
	 * saves a new made FixedDonationCollection into database
	 * @param collection
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void savenewFixedDonationCollection(
			FixedDonationCollection collection) throws SQLException,
			ParseException {	
		
		PersonHistoryAccess.insertOrgaCollectionHistory(collection.getOrganizationPerson().getPersonId(), collection.getCollectionId(), new Date(), null);
		PersonHistoryAccess.insertContactPersonHistory(collection.getContactPerson().getPersonId(), collection.getCollectionId(), new Date(), null);
		
		DataAccess.getInstance().storeFixedDonationCollectionIntoDB(collection);
		DataAccess.getInstance().storeNextDonationCollectionIdIntoDB(
				DonationCollectionManager.getActualGlobalID());
		DataAccess.getInstance()
				.setOrganisationPersonReferenceInDonationCollection(
						collection.getOrganizationPerson().getPersonId(),
						collection.getCollectionId());
		try {
			DataAccess.getInstance()
					.setContactPersonReferenceInDonationCollection(
							collection.getContactPerson().getPersonId(),
							collection.getCollectionId());
		} catch (NullPointerException x) {
			CollectionAccess.loadAllCollectionsFromDB();
			;
		}
		//added by sz (leere liste mit hinzugefügter liste verglichen)
		boxHistoryUpdate(new ArrayList<DonationBox>(), collection.getDonationboxes(), collection.getCollectionId());
		
		CollectionAccess.loadAllCollectionsFromDB();
		Access.loadAllDonationBoxes(); // added by ml
	}

	/**
	 * saves an already existing FixedDonationCollectiono in DB by overwriting it
	 * @param collection
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void saveoldFixedDonationCollection(
			FixedDonationCollection collection) throws SQLException,
			ParseException {
		DataAccess.getInstance().editFixedDonationCollectionInDB(collection);
		DataAccess.getInstance()
				.setOrganisationPersonReferenceInDonationCollection(
						collection.getOrganizationPerson().getPersonId(),
						collection.getCollectionId());
		DataAccess.getInstance().setContactPersonReferenceInDonationCollection(
				collection.getContactPerson().getPersonId(),
				collection.getCollectionId());
		Iterator<DonationBox> iterator = collection.getDonationboxes()
				.iterator();
		while (iterator.hasNext()) {
			DonationBox box = iterator.next();
			DataAccess.getInstance().setCollectionIdReferenceInDonationBox(
					collection.getCollectionId(), box.getBoxId());
			Access.editDonationBox(box);
			// DataAccess.getInstance().setInUseInDonationBox(true,
			// box.getBoxId()); // added by ml
		}
		CollectionAccess.loadAllCollectionsFromDB();
		Access.loadAllDonationBoxes(); // added by ml
	}
	
	/**
	 * Used to change attributes of a FixedDonationCollection. Also creates new Histories if Datas were changed.
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
	public void editFixedDonationCollection(int collectionId, ArrayList<DonationBox> donationBoxes, String comment, OrganisationPerson organisationPerson, Date beginPeriode, Date endPeriode, Address fixedAddress, ContactPerson contactPerson) throws SQLException, ParseException
	{
		FixedDonationCollection collection = (FixedDonationCollection) CollectionAccess.searchDonationCollectionById(collectionId);
		
		boxHistoryUpdate(collection.getDonationboxes(), donationBoxes, collectionId);
		
		Calendar calAnfang = new GregorianCalendar();
		calAnfang.setTime(beginPeriode);
		Calendar calEnde = new GregorianCalendar();
		try {
			calEnde.setTime(endPeriode);
			collection.setEndPeriode(calEnde);
		} catch (Exception x) {
			collection.setEndPeriode(null);
		}
		
		for(DonationBox current : donationBoxes)
		{
			current.setInUse(true);
			current.setCollectionId(collectionId);
			Access.editDonationBox(current);
		}
				
		collection.setDonationboxes(donationBoxes);
		collection.setComment(comment);	
		collection.setBeginPeriode(calAnfang);
		collection.setFixedAddress(fixedAddress);
		
		if (organisationPerson.getPersonId() == collection.getOrganizationPerson().getPersonId())
		{
			collection.setOrganizationPerson(organisationPerson); 
		}
		else
		{
			ArrayList<PersonHistory> list = PersonHistoryAccess.searchOrgaCollectionHistoriesByCollectionId(collection.getCollectionId());
			OrgaCollectionHistory history = (OrgaCollectionHistory) list.get(list.size()-1);
			history.setEndDate(Calendar.getInstance());
			PersonHistoryAccess.editOrgaCollectionHistory(history);
			
			Date date = new Date();
			collection.setOrganizationPerson(organisationPerson); 
			PersonHistoryAccess.insertOrgaCollectionHistory(organisationPerson.getPersonId(), collection.getCollectionId(), date, null);
		}
		
		if (contactPerson.getPersonId() == collection.getContactPerson().getPersonId())
		{
			collection.setContactPerson(contactPerson);
		}
		else
		{
			ArrayList<PersonHistory> list = PersonHistoryAccess.searchContactHistoriesByCollectionId(collection.getCollectionId());
			ContactPersonHistory history = (ContactPersonHistory) list.get(list.size()-1);
			history.setEndDate(Calendar.getInstance());
			PersonHistoryAccess.editContactPersonHistory(history);
			
			Date date = new Date();
			collection.setContactPerson(contactPerson);
			PersonHistoryAccess.insertContactPersonHistory(contactPerson.getPersonId(), collection.getCollectionId(), date, null);
		}
		
		CollectionAccess.saveoldFixedDonationCollection(collection);
		
	}

	/**
	 * saves a new made StreetDonationCollection into database
	 * @param collection
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void savenewStreetDonationCollection(
			StreetDonationCollection collection) throws SQLException,
			ParseException {
		//BoxHistoryManager.getInstance().createForNewDonationBoxesBoxHistoryWithSetDateNull(collection.getDonationboxes());
		
		PersonHistoryAccess.insertOrgaCollectionHistory(collection.getOrganizationPerson().getPersonId(), collection.getCollectionId(), new Date(), null);
		
		DataAccess.getInstance()
				.storeStreetDonationCollectionIntoDB(collection);
		DataAccess.getInstance().storeNextDonationCollectionIdIntoDB(
				DonationCollectionManager.getActualGlobalID());
		DataAccess.getInstance()
				.setOrganisationPersonReferenceInDonationCollection(
						collection.getOrganizationPerson().getPersonId(),
						collection.getCollectionId());
		
		//added by sz (leere liste mit hinzugefügter liste verglichen)
		boxHistoryUpdate(new ArrayList<DonationBox>(), collection.getDonationboxes(), collection.getCollectionId());
		
		CollectionAccess.loadAllCollectionsFromDB();
		Access.loadAllDonationBoxes(); // addes by ml

	}
	
	/**
	 * Used to change attributes of a StreetDonationCollection. Also creates new Histories if Datas were changed.
	 * @param collectionId
	 * @param donationBoxes
	 * @param comment
	 * @param organisationPerson
	 * @param beginPeriode
	 * @param endPeriode
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void editStreetDonationCollection(int collectionId, ArrayList<DonationBox> donationBoxes, String comment, OrganisationPerson organisationPerson, Date beginPeriode, Date endPeriode) throws SQLException, ParseException
	{
		StreetDonationCollection collection = (StreetDonationCollection) CollectionAccess.searchDonationCollectionById(collectionId);
		
		boxHistoryUpdate(collection.getDonationboxes(), donationBoxes, collectionId);
		
		Calendar calAnfang = new GregorianCalendar();
		calAnfang.setTime(beginPeriode);
		Calendar calEnde = new GregorianCalendar();
		try {
			calEnde.setTime(endPeriode);
			collection.setEndPeriode(calEnde);
		} catch (Exception x) {
			collection.setEndPeriode(null);
		}
		
		for(DonationBox current : donationBoxes)
		{
			current.setInUse(true);
			current.setCollectionId(collectionId);
			Access.editDonationBox(current);
		}
				
		collection.setDonationboxes(donationBoxes);
		collection.setComment(comment);	
		collection.setBeginPeriode(calAnfang);

		if (organisationPerson.getPersonId() == collection.getOrganizationPerson().getPersonId())
			{
				collection.setOrganizationPerson(organisationPerson); 
			}
			else
			{
				ArrayList<PersonHistory> list = PersonHistoryAccess.searchOrgaCollectionHistoriesByCollectionId(collection.getCollectionId());
				OrgaCollectionHistory history = (OrgaCollectionHistory) list.get(list.size()-1);
				history.setEndDate(Calendar.getInstance());
				PersonHistoryAccess.editOrgaCollectionHistory(history);
				
				Date date = new Date();
				collection.setOrganizationPerson(organisationPerson); 
				PersonHistoryAccess.insertOrgaCollectionHistory(organisationPerson.getPersonId(), collection.getCollectionId(), date, null);
			}
		
		CollectionAccess.saveoldStreetDonationCollection(collection);
	}

	/**
	 * saves an already existing StreetDonationCollectiono in DB by overwriting it
	 * @param collection
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void saveoldStreetDonationCollection(
			StreetDonationCollection collection) throws SQLException,
			ParseException {	
		
		DataAccess.getInstance().editStreetDonationCollectionInDB(collection);
		DataAccess.getInstance()
				.setOrganisationPersonReferenceInDonationCollection(
						collection.getOrganizationPerson().getPersonId(),
						collection.getCollectionId());
		Iterator<DonationBox> iterator = collection.getDonationboxes()
				.iterator();
		while (iterator.hasNext()) {
			DonationBox box = iterator.next();
			DataAccess.getInstance().setCollectionIdReferenceInDonationBox(
					collection.getCollectionId(), box.getBoxId());
			Access.editDonationBox(box);
			// DataAccess.getInstance().setInUseInDonationBox(true,
			// box.getBoxId()); // added by ml
			Access.loadAllDonationBoxes(); // added by ml
		}
		CollectionAccess.loadAllCollectionsFromDB();
	}

	/**
	 * Deletes a Box and its reference from a Collection, its getting "free" again so other collections can use it
	 * @param boxId
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void deleteBoxFromDonationCollection(int boxId) throws SQLException,
			ParseException {



			DonationBox box = DonationBoxManager.getInstance().getDonationBoxById(boxId);
			try
			{

			BoxHistory history = BoxHistoryManager.getInstance().getBoxHistoriesWithBoxIdandCollectionIdandEndDateNull(boxId, box.getCollectionId());
			history.setEndDate(Calendar.getInstance());
			BoxHistoryManager.getInstance().editBoxHistories(history);
			}
			catch (NullPointerException x)
			{
				System.out.println("Nullpointer bei BoxHistory entfernen");
			}

		DataAccess.getInstance().deleteDonationBoxByIdFromCollection(boxId);
		CollectionAccess.loadAllCollectionsFromDB();
		Access.loadAllDonationBoxes(); // added by ml
	}

	/**
	 * adds all clearingDonationCollections from a collection and sets it as Sum.
	 * @param collectionId
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void calculateSumByCollectionId(int collectionId)
			throws SQLException, ParseException {

		DonationCollection collection = CollectionAccess.searchDonationCollectionById(collectionId);
		float result = 0;
		ArrayList<ClearingDonationBox> cdbList = new ArrayList<ClearingDonationBox>();
		cdbList = DataAccess.getInstance()
				.getClearingDonationBoxesbyCollectionId(collectionId);

		for (ClearingDonationBox current : cdbList) {
			result = result + current.getSum();
		}
		
		collection.setSum(result);
		
		if (collection.getClass().getSimpleName()
				.equals("FixedDonationCollection")) {
			CollectionAccess
					.saveoldFixedDonationCollection((FixedDonationCollection) collection);
		} else {
			CollectionAccess
					.saveoldStreetDonationCollection((StreetDonationCollection) collection);
		}
		
	}

	/**
	 * Deletes all Boxes from this collection, changes are not possible anymore after doing this. Only do this if you're sure the collection is finished.
	 * @param collectionId
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void finishCollection(int collectionId) throws SQLException,
			ParseException {
		Calendar cal = Calendar.getInstance();

		DonationCollection temp = CollectionAccess.searchDonationCollectionById(collectionId);
		float result = 0;
		Iterator<DonationBox> iterator = temp.getDonationboxes().iterator();
		ArrayList<ClearingDonationBox> boxArray = new ArrayList<ClearingDonationBox>();
		boxArray = DataAccess.getInstance()
				.getClearingDonationBoxesbyCollectionId(collectionId);

		for (ClearingDonationBox current : boxArray) {
			result = result + current.getSum();
		}
		
		BoxHistoryManager.getInstance().setEndDateNullForDonationBoxesBoxHistoryWithSetDateNull(temp.getDonationboxes());

		temp.setSum(result);
		
		System.out.println(temp.getSum());

		while (iterator.hasNext()) {
			DonationBox calc = iterator.next();
			calc.setInUse(false);
			calc.setCollectionId(0);
			Access.editDonationBox(calc);
		}

		temp.setIsCompleted(true);
		
		ArrayList<PersonHistory> list = PersonHistoryAccess.searchOrgaCollectionHistoriesByCollectionId(temp.getCollectionId());
		OrgaCollectionHistory history = (OrgaCollectionHistory) list.get(list.size()-1);
		if(temp.getEndPeriode() == null)
		{
			temp.setEndPeriode(cal);
			history.setEndDate(Calendar.getInstance());
		}
		else
		{
			history.setEndDate(temp.getEndPeriode());			
		}
		PersonHistoryAccess.editOrgaCollectionHistory(history);
		
		if (temp.getClass().getSimpleName().equals("FixedDonationCollection")) {
			ArrayList<PersonHistory> listContact = PersonHistoryAccess.searchContactHistoriesByCollectionId(temp.getCollectionId());
			ContactPersonHistory contactHistory = (ContactPersonHistory) listContact.get(listContact.size()-1);
			contactHistory.setEndDate(temp.getEndPeriode());
			PersonHistoryAccess.editContactPersonHistory(contactHistory);
		}
		
		

		if (temp.getClass().getSimpleName().equals("FixedDonationCollection")) {
			CollectionAccess
					.saveoldFixedDonationCollection((FixedDonationCollection) temp);
		} else {
			CollectionAccess
					.saveoldStreetDonationCollection((StreetDonationCollection) temp);
		}
		
		

		CollectionAccess.loadAllCollectionsFromDB();
		Access.loadAllDonationBoxes(); // added by ml

	} 

	/**
	 * Checks if a Collection is already completed (finished)
	 * @param collectionId
	 * @return
	 */
	public Boolean checkIsCompleted(int collectionId) {
		DonationCollection temp = CollectionAccess
				.searchDonationCollectionById(collectionId);
		if (temp.getIsCompleted() == true) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Used whenever a collection gets changed, it creates History Objects if new Boxes were added
	 * @param oldList
	 * @param newList
	 * @param collectionId
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void boxHistoryUpdate (ArrayList<DonationBox> oldList, ArrayList<DonationBox> newList, int collectionId) throws SQLException, ParseException
	{
		System.out.println("NEEEEEEUUUUUU");
		System.out.println(oldList);
		System.out.println("\n");
		System.out.println(newList);
		BoxHistoryManager.getInstance().loadBoxHistories();
		int k = 0;
		
		// Überprüfung, ob neue Box hinzugefügt wurde - anschließend Erstellung der History
		for(DonationBox newTemp : newList)
		{
			k = 0;
			
			for(DonationBox oldTemp : oldList)
			{
				if(oldTemp.getBoxId() == newTemp.getBoxId())
				{
					k = 1;
				}
			}
			if(k == 0)
			{
				BoxHistoryManager.getInstance().create(newTemp.getBoxId(), collectionId);
			}
		}

		
	}

}
