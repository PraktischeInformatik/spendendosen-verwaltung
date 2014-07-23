package applicationLogicAccess;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import applicationLogic.Address;
import applicationLogic.BoxHistory;
import applicationLogic.BoxHistoryManager;
import applicationLogic.ClearingDonationBox;
import applicationLogic.ContactPerson;
import applicationLogic.DonationBox;
import applicationLogic.DonationBoxManager;
import applicationLogic.DonationCollection;
import applicationLogic.DonationCollectionManager;
import applicationLogic.IdInUseException;
import applicationLogic.IdNotFoundException;
import applicationLogic.Login;
import applicationLogic.LoginInUseException;
import applicationLogic.LoginManager;
import applicationLogic.OrganisationPerson;
import applicationLogic.Person;
import applicationLogic.PersonManager;
import com.itextpdf.text.DocumentException;

/**
 * @author Etibar
 *
 */

public class Access {
	private static String actuellLogin;


	public static String getActuellLogin() {
		return actuellLogin;
	}

	
	/**
	 * @author Etibar Hasanov
	 * @param actuellLogin
	 */
	public static void setActuellLogin(String actuellLogin) {
		if (LoginManager.getInstance().loginInUse(actuellLogin)==true)
			{
		Access.actuellLogin = actuellLogin;
	}
	else 
	{
          Access.actuellLogin="Gast" ;
}
	}

	/**
	 * @author Etibar Hasanov
	 * @param toAdd
	 */
	public static void addPerson(Person toAdd) {
		PersonManager.getInstance().addPerson(toAdd);
	}

	/*
	 * private static void checkAdressStandard(String streetName, int
	 * hauseNumber, int zip, String locationName) throws
	 * InvalidArgumentsException { if (streetName.length() > 45) throw new
	 * InvalidArgumentsException("streetName " + streetName + " too long!"); if
	 * (locationName.length() > 45) throw new
	 * InvalidArgumentsException("locationName " + locationName + " to long!");
	 * 
	 * }
	 */

	/**
	 * @author Etibar Hasanov
	 * @param forename
	 * @param lastname
	 * @param address
	 * @param email
	 * @param telephoneNumber
	 * @param mobilNumber
	 * @param comment
	 * @throws InvalidArgumentsException
	 */
	private static void checkStandard(String forename, String lastname,
			Address address, String email, String telephoneNumber,
			String mobilNumber, String comment)
			throws InvalidArgumentsException {
		if (forename.length() < 3)
			throw new InvalidArgumentsException("forename " + forename
					+ " too short!");
		if (forename.length() > 45)
			throw new InvalidArgumentsException("forename " + forename
					+ " too long!");
		if (lastname.length() < 3)
			throw new InvalidArgumentsException("lastname " + lastname
					+ " too short!");
		if (lastname.length() > 45)
			throw new InvalidArgumentsException("lastname " + lastname
					+ " too long!");
		if (telephoneNumber.length() < 4)
			throw new InvalidArgumentsException("telephoneNumber "
					+ telephoneNumber + " too short!");

		;
	}

	/*
	 * public static void addPersonWithCheck(String forename, String lastname,
	 * Address address, String email, String telephoneNumber, String
	 * mobilNumber, String comment, boolean isActiveMember) throws
	 * InvalidArgumentsException { checkStandard(forename, lastname, address,
	 * email, telephoneNumber, mobilNumber, comment); OrganisationPerson toAdd =
	 * new OrganisationPerson(forename, lastname, address, email,
	 * telephoneNumber, mobilNumber, comment, isActiveMember); addPerson(toAdd);
	 * }
	 */

	// add to arraylist in PersonManager
	/*
	 * public static void addOrganisationPerson(String forename, String
	 * lastname, Address address, String email, String telephoneNumber, String
	 * mobilNumber, String comment, boolean isActiveMember) throws
	 * InvalidArgumentsException { addPersonWithCheck(forename, lastname,
	 * address, email, telephoneNumber, mobilNumber, comment, isActiveMember); }
	 */

	// add to arraylist in PersonManager
	/*
	 * public static void addContactPerson(String forename, String lastname,
	 * Address address, String email, String telephoneNumber, String
	 * mobilNumber, String comment, boolean isActiveMember) throws
	 * InvalidArgumentsException { addPersonWithCheck(forename, lastname,
	 * address, email, telephoneNumber, mobilNumber, comment, isActiveMember); }
	 * 
	 * public static Person searchByPerson(int id) { return
	 * PersonManager.getInstance().searchByPersonId(id); }
	 */

	/**
	 * @author Etibar Hasanov
	 * @param forename
	 * @return
	 */
	public static ArrayList<Person> searchByForeName(String forename) {
		return PersonManager.getInstance().searchPersonsByForename(forename);
	}

	/**
	 * @author Etibar Hasanov
	 * @param lastname
	 * @return
	 */
	public static ArrayList<Person> searchByLastName(String lastname) {
		return PersonManager.getInstance().searchPersonsByLastname(lastname);
	}

	/**
	 * @author Etibar Hasanov
	 * @param forename
	 * @param lastname
	 * @return
	 */
	public static ArrayList<Person> searchByName(String forename,
			String lastname) {
		return PersonManager.getInstance().searchPersonsByName(forename,
				lastname);
	}

	/**
	 * @author Etibar Hasanov
	 * @param personId
	 * @param forename
	 * @param lastname
	 * @param address
	 * @param email
	 * @param telephoneNumber
	 * @param mobilNumber
	 * @param comment
	 * @param isActiveMember
	 * @throws InvalidArgumentsException
	 */
	public static void changePerson(int personId, String forename,
			String lastname, Address address, String email,
			String telephoneNumber, String mobilNumber, String comment,
			boolean isActiveMember) throws InvalidArgumentsException {
		checkStandard(forename, lastname, address, email, telephoneNumber,
				mobilNumber, comment);
		PersonManager.getInstance().changePerson(personId, forename, lastname,
				address, email, telephoneNumber, mobilNumber, comment,
				isActiveMember);
		return;
	}

	/**
	 * @author Etibar Hasanov
	 * @param personId
	 * @param forename
	 * @param lastname
	 * @param address
	 * @param email
	 * @param telephoneNumber
	 * @param mobilNumber
	 * @param comment
	 * @param isActiveMember
	 * @throws InvalidArgumentsException
	 * @throws SQLException
	 * @throws ParseException
	 * @throws OrganisationPersonInUseException
	 */
	public static void changeOrganisationPerson(int personId, String forename,
			String lastname, Address address, String email,
			String telephoneNumber, String mobilNumber, String comment,
			boolean isActiveMember) throws InvalidArgumentsException,
			 SQLException, ParseException,
			OrganisationPersonInUseException {
		checkStandard(forename, lastname, address, email, telephoneNumber,
				mobilNumber, comment);
		PersonManager.getInstance().changeOrganisationPerson(personId,
				forename, lastname, address, email, telephoneNumber,
				mobilNumber, comment, isActiveMember);
		return;
	}

	/**
	 * @author Etibar Hasanov
	 * @param personId
	 * @param forename
	 * @param lastname
	 * @param address
	 * @param email
	 * @param telephoneNumber
	 * @param mobilNumber
	 * @param comment
	 * @param isActiveMember
	 * @throws InvalidArgumentsException
	 */
	public static void changeContactPerson(int personId, String forename,
			String lastname, Address address, String email,
			String telephoneNumber, String mobilNumber, String comment,
			boolean isActiveMember) throws InvalidArgumentsException {
		checkStandard(forename, lastname, address, email, telephoneNumber,
				mobilNumber, comment);
		PersonManager.getInstance().changeContactPerson(personId, forename,
				lastname, address, email, telephoneNumber, mobilNumber,
				comment, isActiveMember);
		return;
	}

	public static ArrayList<OrganisationPerson> getAllOrganisationPersons() {
		return PersonManager.getInstance().getAllOrganisationPersons();
	}

	public static ArrayList<ContactPerson> getAllContactPersons() {
		return PersonManager.getInstance().getAllContactPersons();
	}

	public static ArrayList<Person> getAllPersons() {
		return PersonManager.getInstance().getAllPersons();
	}

	public static ArrayList<DonationBox> getAllBoxes() {
		return DonationBoxManager.getInstance().getAllBoxes();
	}

	/*
	 * public static void saveSystem() throws IOException {
	 * PersonManager.getInstance().SaveSystem(); }
	 * 
	 * public static void loadSystem() throws IOException {
	 * PersonManager.getInstance().LoadSystem(); }
	 */

	// addTo arraylist in DonationBoxManager

	/**
	 * @author Etibar Hasanov
	 * @param isInUse
	 * @return
	 */
	public static ArrayList<DonationBox> searchbyInUse(boolean isInUse) {
		return DonationBoxManager.getInstance().searchDonationBoxByInUse(
				isInUse);
	}

	public static void exportOrganisationPersonsAsPdf(String file)
			throws FileNotFoundException, DocumentException {
		/**
		 * @author KevinSchneider
		 */

		/*
		 * FileNotFoundException wird geschmissen, wenn kein Zugriff auf Datei
		 * oder Speicherordner gestattet ist. z.B. wenn Datei geöffnet ist.
		 * 
		 * DocumentException wird geschmissen, wenn Fehler beim Schreiben der
		 * PDF auftritt.
		 */

		PersonManager.getInstance().exportOrganisationPersonListAsPdf(file);
	}

	public static void exportContactPersonsAsPdf(String file)
			throws FileNotFoundException, DocumentException {
		/**
		 * @author KevinSchneider
		 */
		PersonManager.getInstance().exportContactPersonListAsPdf(file);
	}

	public static void exportDonationBoxesAsPdf(String file)
			throws FileNotFoundException, DocumentException {
		/**
		 * @author KevinSchneider
		 */
		try {
			DonationBoxManager.getInstance().exportDonationBoxListAsPdf(file);
		} catch (SQLException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void exportDonationCollectionsAsPdf(String file)
			throws FileNotFoundException, DocumentException {
		/**
		 * @author KevinSchneider
		 */
		try {
			DonationCollectionManager.getInstance()
					.exportDonationCollectionListAsPDF(file);
		} catch (SQLException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void exportOrganisationPersonsAsCsv(String file)
			throws IOException {
		/**
		 * @author KevinSchneider
		 */

		/*
		 * Schmeisst Exception, wenn Fehler auftritt! Sehr wahrscheinlich wenn
		 * Dokument geöffnet ist. Muss aber nicht sein!!F
		 */
		PersonManager.getInstance().exportOrganisationPersonListAsCsv(file);
	}

	public static void exportContactPersonsAsCsv(String file)
			throws IOException {
		/**
		 * @author KevinSchneider
		 */
		PersonManager.getInstance().exportContactPersonListAsCsv(file);
	}

	public static void exportDonationCollectionsAsCsv(String file)
			throws IOException {
		/**
		 * @author KevinSchneider
		 */
		DonationCollectionManager.getInstance()
				.exportDonationCollectionListAsCsv(file);
	}

	public static void exportDonationBoxesAsCsv(String file) throws IOException {
		/**
		 * @author KevinSchneider
		 */
		try {
			DonationBoxManager.getInstance().exportDonationBoxListAsCsv(file);
		} catch (SQLException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @author Etibar Hasanov
	 * @param boxId
	 * @param responsiblePersonId
	 * @param comment
	 * @param collectionId
	 * @param date
	 * @param inUseYes
	 * @param inUseNo
	 * @return
	 */
	public static ArrayList<DonationBox> searchDonationBox(int boxId,
			int responsiblePersonId, String comment, int collectionId,
			Date date, boolean inUseYes, boolean inUseNo) {
		DonationBoxManager
				.getInstance()
				.getCurrSearchBox()
				.setValues(boxId, responsiblePersonId, comment, collectionId,
						date, inUseYes, inUseNo);

		ArrayList<DonationBox> empty = new ArrayList<DonationBox>();
		ArrayList<DonationBox> result = DonationBoxManager.getInstance()
				.getAllBoxes();
		if (boxId != -1)
			result = DonationBoxManager.getInstance()
					.searchDonationBoxWithBoxId(boxId, result);

		if (responsiblePersonId != -1)
			result = DonationBoxManager.getInstance()
					.searchDonationBoxWithResponsiblePerson(
							responsiblePersonId, result);

		if (comment != null)
			result = DonationBoxManager.getInstance()
					.searchDonationBoxWithComment(comment, result);

		if (collectionId != -1)
			result = DonationBoxManager.getInstance()
					.searchDonationBoxWithCollectionId(collectionId, result);

		if (date != null)
			result = DonationBoxManager.getInstance()
					.searchDonationBoxWithClearingDate(date, result);

		if (inUseYes == false && inUseNo == false) {
			return empty;
		} else {
			result = DonationBoxManager.getInstance()
					.searchDonationBoxWithInUse(inUseYes, inUseNo, result);
		}

		return result;
	}

	/**
	 * @author Etibar Hasanov
	 * @param person
	 * @return
	 */
	public static int getIdByPerson(Person person) {
		return PersonManager.getInstance().getIdByPerson(person);
	}

	/**@author Etibar Hasanov
	 * @param id
	 * @return
	 */
	public static Person getPersonById(int id) {
		return PersonManager.getInstance().getPersonById(id);
	}

	/**
	 * @author Etibar Hasanov
	 * @throws SQLException
	 */
	public static void loadAllPersons() throws SQLException {
		PersonManager.getInstance().loadAllPersonsFromDb();
	}

	/**
	 * @author Etibar Hasanov
	 * @param person
	 * @throws SQLException
	 * @throws ParseException
	 * @throws OrganisationPersonInUseException
	 */
	public static void editOrganisationPerson(OrganisationPerson person)
			throws SQLException, ParseException,
			OrganisationPersonInUseException {
		PersonManager.getInstance().saveoldOrganisationPerson(person);
	}

	/**
	 * @author Etibar Hasanov
	 * @param person
	 * @throws SQLException
	 */
	public static void saveNewOrganisationPerson(OrganisationPerson person)
			throws SQLException {
		PersonManager.getInstance().savenewOrganisationPerson(person);
	}

	/**@author Etibar Hasanov
	 * @param person
	 * @throws SQLException
	 */
	public static void editContactPerson(ContactPerson person)
			throws SQLException {
		PersonManager.getInstance().saveoldContactPerson(person);

	}

	/**@author Etibar Hasanov
	 * @param person
	 * @throws SQLException
	 */
	public static void saveNewContactPerson(ContactPerson person)
			throws SQLException {
		PersonManager.getInstance().savenewContactPerson(person);
	}

	/**@author Etibar Hasanov
	 * @throws SQLException
	 * @throws ParseException
	 */
	public static void loadAllDonationBoxes() throws SQLException,
			ParseException {
		DonationBoxManager.getInstance().loadAllDonationBoxes();
	}

	/**
	 * @author Etibar Hasanov
	 * @param orgPerson
	 * @param collection
	 * @param donationBox
	 * @throws SQLException
	 * @throws ParseException
	 */
	public static void addNewDonationBox(OrganisationPerson orgPerson,
			DonationCollection collection, DonationBox donationBox)
			throws SQLException, ParseException {
		DonationBoxManager.getInstance().savenewDonationBox(orgPerson,
				collection, donationBox);
	}

	/**
	 * @author Etibar Hasanov
	 * @param orgPerson
	 * @param collection
	 * @param donationBox
	 * @throws SQLException
	 * @throws ParseException
	 */
	public static void editDonationBox(OrganisationPerson orgPerson,
			DonationCollection collection, DonationBox donationBox)
			throws SQLException, ParseException {
		DonationBoxManager.getInstance().editDonationBox(orgPerson, collection,
				donationBox);
	}

	/**
	 * @author Etibar Hasanov
	 * @param donationBox
	 * @throws SQLException
	 * @throws ParseException
	 */
	public static void addNewDonationBox(DonationBox donationBox)
			throws SQLException, ParseException {
		DonationBoxManager.getInstance().savenewDonationBox(donationBox);
	}

	/**
	 * @author Etibar Hasanov
	 * @param donationBox
	 * @throws SQLException
	 * @throws ParseException
	 */
	public static void editDonationBox(DonationBox donationBox)
			throws SQLException, ParseException {
		DonationBoxManager.getInstance().editDonationBox(donationBox);
	}

	/**
	 * @author Etibar Hasanov
	 * @param boxId
	 * @param sum
	 * @param inUse
	 * @param comment
	 * @param currentResponsiblePerson
	 * @param collectionId
	 * @return
	 * @throws IdInUseException
	 * @throws SQLException
	 */
	public static DonationBox createDonationBoxWithId(int boxId, float sum,
			boolean inUse, String comment,
			OrganisationPerson currentResponsiblePerson, int collectionId)
			throws IdInUseException, SQLException {
		return DonationBoxManager.getInstance().createDonationBoxWithId(boxId,
				sum, inUse, comment, currentResponsiblePerson, collectionId);
	}

	/**
	 * returns the current Temporary Donation Box List after filtering it with
	 * the latest search criteria
	 * 
	 * @return
	 */
	public static ArrayList<DonationBox> getTempDonationBoxes() {
		int boxId = DonationBoxManager.getInstance().getCurrSearchBox()
				.getBoxId();
		int responsiblePersonId = DonationBoxManager.getInstance()
				.getCurrSearchBox().getResponsiblePersonId();
		String comment = DonationBoxManager.getInstance().getCurrSearchBox()
				.getComment();
		int collectionId = DonationBoxManager.getInstance().getCurrSearchBox()
				.getCollectionId();
		Date date = DonationBoxManager.getInstance().getCurrSearchBox()
				.getDate();
		boolean inUseYes = DonationBoxManager.getInstance().getCurrSearchBox()
				.isInUseYes();
		boolean inUseNo = DonationBoxManager.getInstance().getCurrSearchBox()
				.isInUseNo();

		DonationBoxManager.getInstance().setTempDonationBoxList(
				searchDonationBox(boxId, responsiblePersonId, comment,
						collectionId, date, inUseYes, inUseNo));

		return DonationBoxManager.getInstance().getTempDonationBoxList();
	}

	/**
	 * @author Etibar Hasanov
	 * @param donationBoxId
	 * @return
	 */
	public static DonationBox getDonationBoxById(int donationBoxId) {
		return DonationBoxManager.getInstance().getDonationBoxById(
				donationBoxId);
	}

	/**
	 * @author Etibar Hasanov
	 * @param boxId
	 * @param sum
	 * @param inUse
	 * @param comment
	 * @param currentResponsiblePerson
	 * @param collectionId
	 * @throws IdNotFoundException
	 */
	public static void changeDonationBox(int boxId, float sum, boolean inUse,
			String comment, OrganisationPerson currentResponsiblePerson,
			int collectionId) throws IdNotFoundException {
		DonationBoxManager.getInstance().changeDonationBox(boxId, sum, inUse,
				comment, currentResponsiblePerson, collectionId);
	}

	
	public static void setTempDonationBoxes(ArrayList<DonationBox> collection) {
		DonationBoxManager.getInstance().setTempDonationBoxList(collection);
	}

	/**
	 * returns the current Temporary Organisation Person List after filtering it
	 * with the latest search criteria
	 * 
	 * @return
	 */
	public static ArrayList<OrganisationPerson> getTempOrganisationPersons() {
		int orgPersId = PersonManager.getInstance().getCurrSearchOrgPers()
				.getOrgPersId();
		String lastname = PersonManager.getInstance().getCurrSearchOrgPers()
				.getLastname();
		String forename = PersonManager.getInstance().getCurrSearchOrgPers()
				.getForename();

		PersonManager.getInstance().setTempOrganisationPersonList(
				searchOrganisationPersons(orgPersId, lastname, forename));

		return PersonManager.getInstance().getTempOrganisationPersonList();
	}

	
	public static void setTempOrganisationPersons(
			ArrayList<OrganisationPerson> collection) {
		PersonManager.getInstance().setTempOrganisationPersonList(collection);
	}

	/**
	 * returns the current Temporary Contact Person List after filtering it with
	 * the latest search criteria
	 * 
	 * @return
	 */
	public static ArrayList<ContactPerson> getTempContactPersons() {
		int conPersId = PersonManager.getInstance().getCurrSearchConPers()
				.getConPersId();
		String lastname = PersonManager.getInstance().getCurrSearchConPers()
				.getLastname();
		String forename = PersonManager.getInstance().getCurrSearchConPers()
				.getForename();

		PersonManager.getInstance().setTempContactPersonList(
				searchContactPersons(conPersId, lastname, forename));

		return PersonManager.getInstance().getTempContactPersonList();
	}

	public static void setTempContactPersons(ArrayList<ContactPerson> collection) {
		PersonManager.getInstance().setTempContactPersonList(collection);
	}

	// search für OrganisationPerson
	public static ArrayList<OrganisationPerson> searchOrganisationPersons(
			int orgPersId, String lastname, String forename) {
		PersonManager.getInstance().getCurrSearchOrgPers()
				.setValues(orgPersId, lastname, forename);
		return PersonManager.getInstance().searchOrganisationPersons(orgPersId,
				lastname, forename);
	}

	// search für ContactPerson
	public static ArrayList<ContactPerson> searchContactPersons(int orgPersId,
			String lastname, String forename) {
		PersonManager.getInstance().getCurrSearchConPers()
				.setValues(orgPersId, lastname, forename);
		return PersonManager.getInstance().searchContactPersons(orgPersId,
				lastname, forename);
	}

	// löschen DonationBox
	public static void deleteDonationBox(int boxId) throws SQLException,
			ParseException, DonationBoxCurrentlyAssignedException {
		// Ich habe setIsDeleted auf true geändert, denn wir wollen mit dem
		// löschen Knopf ja löschen (@author Timo)
		
		if (DonationBoxManager.getInstance().getDonationBoxById(boxId).getCollectionId()!=0)
		{
			throw new DonationBoxCurrentlyAssignedException
			                        (DonationBoxManager.getInstance().
			                        		                getDonationBoxById(boxId).getCollectionId());
		}
		else
		{
		DonationBoxManager.getInstance().deleteDonationBox(boxId);
		}
	}

	// neue ClearingBox erzeugen , mit gegebenen date
	public static void clearDonationBox(int boxId, Date clearingDate, float sum)
			throws SQLException, ParseException {
		Calendar cal = Access.dateToCalendar(clearingDate);
		DonationBoxManager.getInstance().getDonationBoxById(boxId)
				.clearBox(cal, sum);
		DonationBoxManager.getInstance().editDonationBox(
				DonationBoxManager.getInstance().getDonationBoxById(boxId));
	}

	// wie viel Geld ist zurzeit drin in DonationBox

	public static float getEndSumFromDonationBox(int boxId) throws SQLException {
		return DonationBoxManager.getInstance().getDonationBoxById(boxId)
				.getEndSum();
	}

	public static ArrayList<DonationBox> getAvailableDonationBoxes() {
		return DonationBoxManager.getInstance().getAvailableDonationBoxes();

	}

	/**
	 * This method sets the Current Search Criteria for Donation Boxes to the
	 * default values.
	 */
	public static void setDonationBoxSearchNull() {
		DonationBoxManager.getInstance().getCurrSearchBox().resetValues();
	}

	/**
	 * This method sets the Current Search Criteria for Organisation Persons to
	 * the default values.
	 */
	public static void setOrganisationPersonSearchNull() {
		PersonManager.getInstance().getCurrSearchOrgPers().resetValues();
	}

	/**
	 * This method sets the Current Search Criteria for Organisation Persons to
	 * the default values.
	 */
	public static void setContactPersonSearchNull() {
		PersonManager.getInstance().getCurrSearchConPers().resetValues();
	}

	public static Calendar dateToCalendar(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

	// added sz
	public static float calculateSumByBoxId(int boxId) throws SQLException,
			ParseException {
		float sum = 0;
		sum = DonationBoxManager.getInstance().calculateSumByBoxId(boxId);
		return sum;
	}

	/**
	 * @author Etibar Hasanov
	 * @param cdb
	 * @param collectionId
	 * @param boxId
	 * @throws SQLException
	 * @throws ParseException
	 */
	public static void saveClearingDonationBox(ClearingDonationBox cdb,
			int collectionId, int boxId) throws SQLException, ParseException {
		DonationBoxManager.getInstance().saveClearingDonationBox(cdb,
				collectionId, boxId);
	}

	/**
	 * @author Etibar Hasanov
	 * @param boxId
	 * @return
	 * @throws IdInUseException
	 * @throws SQLException
	 */
	public static DonationBox createDonationBoxWithId(int boxId)
			throws IdInUseException, SQLException {
		return DonationBoxManager.getInstance().createDonationBoxWithId(boxId);
	}

	/**
	 * @author Etibar Hasanov
	 * @return
	 */
	public DonationBox createDonationBoxWithLeastFreeId() {
		return DonationBoxManager.getInstance()
				.createDonationBoxWithLeastFreeId();
	}

	// die Methoden für loginManager und boxHistoryManager

	
	/**
	 * @author Etibar Hasanov
	 * @param loginName
	 * @param passwort
	 * @throws SQLException
	 */
	public static void changePasswortOfLogin(String loginName, String passwort)
			throws SQLException {
		LoginManager.getInstance().changePasswortOfLogin(loginName, passwort);
	}

	
	/**
	 * @author Etibar Hasanov
	 * @param loginName
	 * @throws SQLException
	 * @throws LoginInUseException
	 * @throws SelfDeleteException
	 */
	public static void deleleLogin(String loginName) throws SQLException,
			LoginInUseException, SelfDeleteException {
		if (loginName.equals(Access.getActuellLogin())) throw new SelfDeleteException();
		LoginManager.getInstance().deleleLogin(loginName);
	}

	//added by sz
	/**
	 * deletes the login by name from db
	 * @param loginName the loginname
	 * @throws SQLException
	 * @throws LoginInUseException
	 */
	
	
	public static void deleteLoginByLoginnameFromDB(String loginName) throws SQLException,
	LoginInUseException, SelfDeleteException{
		if (loginName.equals(Access.getActuellLogin()))
			{throw new SelfDeleteException();
			}
		else {
		LoginManager.getInstance().deleteLoginFromDB(loginName);
		}
       
	}
	
	/**
	 * @author Etibar Hasanov
	 * @throws SQLException
	 * @throws LoginInUseException
	 */
	public static void loadAllLogins() throws SQLException, LoginInUseException {
		LoginManager.getInstance().loadAllLogins();
	}

	/**
	 * @author Etibar Hasanov
	 * @param login
	 * @throws SQLException
	 * @throws LoginInUseException
	 */
	public static void saveLogin(Login login) throws SQLException,
			LoginInUseException {
		LoginManager.getInstance().saveLogin(login);
	}

	/**
	 * @author Etibar Hasanov
	 * @param loginName
	 * @param accessLevel
	 * @throws SQLException
	 * @throws LoginInUseException
	 */
	public static void changeAccessLevel(String loginName, int accessLevel)
			throws SQLException, LoginInUseException {
		LoginManager.getInstance().changeAccessLevel(loginName, accessLevel);
	}

	/**
	 * @author Etibar Hasanov
	 * @param loginName
	 * @return
	 */
	public static Login getLoginWithName(String loginName) {
		return LoginManager.getInstance().getLoginWithName(loginName);
	}

	/**
	 * @author Etibar Hasanov
	 * @return
	 */
	public static int getAccessLevelOfProgramm() {
		return LoginManager.getAccessLevelOfProgramm();
	}

	/**
	 * @author Etibar Hasanov
	 * @param accessLevelOfProgramm
	 */
	public static void setAccessLevelOfProgramm(int accessLevelOfProgramm) {
		LoginManager.setAccessLevelOfProgramm(accessLevelOfProgramm);
	}

	/**
	 * @author Etibar Hasanov
	 * @param id
	 * @return
	 */
	public static boolean idInUse(int id) {
		return DonationBoxManager.getInstance().idInUse(id);
	}

	/**
	 * @author Etibar Hasanov
	 * @param loginName
	 * @param passwort
	 * @return
	 * @throws UserNameDoesNotExistException
	 * @throws WrongPasswortException
	 */
	public static int accessLevelofLogin(String loginName, String passwort) throws UserNameDoesNotExistException, WrongPasswortException {
		int result = 0;
		if (LoginManager.getInstance().loginInUse(loginName) == false)
		{
			throw new UserNameDoesNotExistException();
		}
		else 
		{
			Login tempLogin = LoginManager.getInstance().getLoginWithName(
					loginName);
			if (!tempLogin.getPasswort().equals(passwort))
			{
				throw new WrongPasswortException();
			}
			else 
			if (tempLogin.getPasswort().equals(passwort)) {
				result = tempLogin.getAccessLevel();
			}
		}
	   
		return result;
	}

	// erzeugt neue Login objekte , um zu speichern , muss man noch save methode
	// anrufen
	/**
	 * @author Etibar Hasanov
	 * @param loginName
	 * @param passwort
	 * @param accessLevel
	 * @return
	 * @throws LoginInUseException
	 */
	public static Login createLogin(String loginName, String passwort,
			int accessLevel) throws LoginInUseException {
		return LoginManager.getInstance().createLogin(loginName, passwort,
				accessLevel);
	}

	// public static boolean loginInUse (String loginName)
	// {
	// return LoginManager.getInstance().loginInUse(loginName);
	// }

	/**
	 * @author Etibar Hasanov
	 * @param boxHistoryId
	 * @return
	 */
	public static BoxHistory getBoxHistoryWithId(int boxHistoryId) {
		return BoxHistoryManager.getInstance()
				.getBoxHistoryWithId(boxHistoryId);
	}

	/**
	 * @author Etibar Hasanov
	 * @param donationBoxId
	 * @return
	 */
	public static ArrayList<BoxHistory> getBoxHistoriesWithDonationBoxId(
			int donationBoxId) {
		return BoxHistoryManager.getInstance()
				.getBoxHistoriesWithDonationBoxId(donationBoxId);
	}

	/**
	 * @author Etibar Hasanov
	 * @param collectionId
	 * @return
	 */
	public static ArrayList<BoxHistory> getBoxHistoriesWithCollectionId(
			int collectionId) {
		return BoxHistoryManager.getInstance().getBoxHistoriesWithCollectionId(
				collectionId);
	}

	/**
	 * @author Etibar Hasanov
	 * @throws SQLException
	 * @throws ParseException
	 */
	public static void loadBoxHistories() throws SQLException, ParseException {
		BoxHistoryManager.getInstance().loadBoxHistories();
	}

	/**
	 * @author Etibar Hasanov
	 * @param boxHistory
	 * @throws SQLException
	 * @throws ParseException
	 */
	public static void saveBoxHistories(BoxHistory boxHistory)
			throws SQLException, ParseException {
		BoxHistoryManager.getInstance().saveBoxHistories(boxHistory);
	}

	/**
	 * @author Etibar Hasanov
	 * @param boxId
	 * @param collectionId
	 * @throws SQLException
	 * @throws ParseException
	 */
	public static void create(int boxId, int collectionId) throws SQLException,
			ParseException {
		BoxHistoryManager.getInstance().create(boxId, collectionId);
	}

	/**
	 * @author Etibar Hasanov
	 * @param boxId
	 * @param collectionId
	 * @throws SQLException
	 * @throws ParseException
	 */
	public static void setEndDateNull(int boxId, int collectionId)
			throws SQLException, ParseException {
		BoxHistoryManager.getInstance().setEndDateNull(boxId, collectionId);
	}

	/**
	 * @author Etibar Hasanov
	 * @param boxId
	 * @param personId
	 * @param comment
	 * @throws SQLException
	 * @throws ParseException
	 */
	public static void editDonationBox(int boxId, int personId, String comment)
			throws SQLException, ParseException {
		DonationBoxManager.getInstance().editDonationBox(boxId, personId,
				comment);
	}

}