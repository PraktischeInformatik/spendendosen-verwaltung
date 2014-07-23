package applicationLogic;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import applicationLogicAccess.Access;
import applicationLogicAccess.CollectionAccess;
import applicationLogicAccess.OrganisationPersonInUseException;
import applicationLogicAccess.PersonHistoryAccess;

import com.itextpdf.text.DocumentException;

import dataAccess.DataAccess;

public class PersonManager implements Serializable {
	/**
	 * @author Etibar Hasanov
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Person> allPersons;
	private ArrayList<OrganisationPerson> tempOrganisationPersonList;
	private ArrayList<ContactPerson> tempContactPersonList;
	private static PersonManager instance = null;
	CurrentSearchValuesOrganisationPerson currSearchOrgPers;
	CurrentSearchValuesContactPerson currSearchConPers;

	public void setCurrSearchOrgPers(
			CurrentSearchValuesOrganisationPerson currSearchOrgPers) {
		this.currSearchOrgPers = currSearchOrgPers;
	}

	public void setCurrSearchConPers(
			CurrentSearchValuesContactPerson currSearchConPers) {
		this.currSearchConPers = currSearchConPers;
	}

	public CurrentSearchValuesOrganisationPerson getCurrSearchOrgPers() {
		return currSearchOrgPers;
	}

	public CurrentSearchValuesContactPerson getCurrSearchConPers() {
		return currSearchConPers;
	}

	/*
	 * private int globalID; public int getGlobalID() { return globalID; }
	 */

	PersonManager() {
		allPersons = new ArrayList<Person>();
		currSearchOrgPers = new CurrentSearchValuesOrganisationPerson();
		currSearchConPers = new CurrentSearchValuesContactPerson();
	}

	public static PersonManager getInstance() {
		if (instance == null)
			instance = new PersonManager();
		return instance;
	}

	public ArrayList<Person> getAllPersons() {
		return allPersons;
	}

	public void setAllPersons(ArrayList<Person> allPersons) {
		this.allPersons = allPersons;
	}

	/*
	 * public void addContactPerson(ContactPerson contactPerson){
	 * this.getAllPersons().add(contactPerson); }
	 * 
	 * public void addOrganisationtPerson(OrganisationPerson
	 * organisationPerson){ this.getAllPersons().add(organisationPerson); }
	 */

	public void addPerson(Person person) {
		this.getAllPersons().add(person);
	}

	public Person searchByPersonId(int personId) {
		Iterator<Person> findIterator = allPersons.iterator();
		while (findIterator.hasNext()) {
			Person tempPerson = findIterator.next();
			if (tempPerson.getPersonId() == personId) {
				return tempPerson;
			}
		}
		return null;

	}

	public ArrayList<Person> searchPersonsByForename(String forename) {
		ArrayList<Person> result = new ArrayList<Person>();
		Iterator<Person> findIterator = allPersons.iterator();
		while (findIterator.hasNext()) {
			Person tempPerson = findIterator.next();
			if (tempPerson.getForename().equals(forename)) {
				result.add(tempPerson);
			}
		}
		return result;
	}

	public ArrayList<Person> searchPersonsByLastname(String lastname) {
		ArrayList<Person> result = new ArrayList<Person>();
		Iterator<Person> findIterator = allPersons.iterator();
		while (findIterator.hasNext()) {
			Person tempPerson = findIterator.next();
			if (tempPerson.getLastName().equals(lastname)) {
				result.add(tempPerson);
			}

		}
		return result;
	}

	public ArrayList<Person> searchPersonsByName(String lastname,
			String forename) {
		ArrayList<Person> result = new ArrayList<Person>();
		Iterator<Person> findIterator = allPersons.iterator();
		while (findIterator.hasNext()) {
			Person tempPerson = findIterator.next();
			if (tempPerson.getLastName().equals(lastname)
					&& tempPerson.getForename().equals(forename)) {
				result.add(tempPerson);
			}

		}
		return result;
	}

	public ArrayList<OrganisationPerson> getAllOrganisationPersons() {
		ArrayList<OrganisationPerson> result = new ArrayList<OrganisationPerson>();
		Iterator<Person> findIterator = allPersons.iterator();
		while (findIterator.hasNext()) {
			Person tempPerson = findIterator.next();
			if (tempPerson.getClass().getSimpleName()
					.equals("OrganisationPerson")) {
				if (tempPerson.getPersonId() != 1000000)
					result.add((OrganisationPerson) tempPerson);
			}

		}
		return result;
	}

	public ArrayList<ContactPerson> getAllContactPersons() {
		ArrayList<ContactPerson> result = new ArrayList<ContactPerson>();
		Iterator<Person> findIterator = allPersons.iterator();
		while (findIterator.hasNext()) {
			Person tempPerson = findIterator.next();
			if (tempPerson.getClass().getSimpleName().equals("ContactPerson")) {
				result.add((ContactPerson) tempPerson);
			}

		}
		return result;
	}

	/*
	 * public void SaveSystem() throws IOException { globalID =
	 * Person.getNextPersonId(); DataAccess1.save(this); }
	 * 
	 * public void LoadSystem() throws IOException { PersonManager loaded =
	 * null; loaded = DataAccess1.load(); instance = loaded;
	 * Person.setNextPersonId(instance.globalID);
	 * 
	 * }
	 */

	public void changePerson(int personId, String forename, String lastName,
			Address address, String email, String telephoneNumber,
			String mobilNumber, String comment, boolean isActiveMember)
			 {
		Person resultPerson;
		resultPerson = this.getPersonById(personId);
		resultPerson.setForename(forename);
		resultPerson.setLastName(lastName);
		resultPerson.setAddress(address);
		resultPerson.setEmail(email);
		resultPerson.setTelephoneNumber(telephoneNumber);
		resultPerson.setMobilNumber(mobilNumber);
		resultPerson.setComment(comment);
		resultPerson.setIsActiveMember(isActiveMember);
		return;
	}

	public void changeOrganisationPerson(int personId, String forename,
			String lastName, Address address, String email,
			String telephoneNumber, String mobilNumber, String comment,
			boolean isActiveMember) throws SQLException, ParseException, OrganisationPersonInUseException {
		
		System.out.println(isActiveMember);
		
		if(isActiveMember == false)
		{
			
			ArrayList<Integer> bugs = new ArrayList<Integer>();
			ArrayList<FixedDonationCollection> FixedCollections = CollectionAccess.getFixedDonationCollections();
			ArrayList<StreetDonationCollection> StreetCollections = CollectionAccess.getAllStreetDonationCollection();
			ArrayList<DonationBox> boxList = Access.getAllBoxes();
			ArrayList<PersonHistory> list = new ArrayList<PersonHistory>();
			
			for (FixedDonationCollection current : FixedCollections) {
				if(current.getOrganizationPerson().getPersonId() == personId && current.getIsCompleted() == false)
				{
					
					bugs.add(current.getCollectionId());
				}
			}
			
			for (StreetDonationCollection temp : StreetCollections)
			{
				if(temp.getOrganizationPerson().getPersonId() == personId && temp.getIsCompleted() == false)
				{
					bugs.add(temp.getCollectionId());
				}
			}
			
			if(bugs.isEmpty())
			{							
				for (DonationBox box : boxList)
				{
					if(box.getCurrentResponsiblePerson().getPersonId() == personId)
					{
						
						
						list = PersonHistoryAccess.searchOrgaBoxHistoriesByCollectionId(box.getBoxId());
						OrgaBoxHistory historyBox = (OrgaBoxHistory) list.get(list.size()-1);
						historyBox.setEndDate(Calendar.getInstance());
						PersonHistoryAccess.editOrgaBoxHistory(historyBox);
						
						box.setCurrentResponsiblePerson((OrganisationPerson) Access.getPersonById(1000000));
						Access.editDonationBox(box);
					}
				}
			}
			else
			{
				throw new OrganisationPersonInUseException(bugs);
			}
		}
		
		changePerson(personId, forename, lastName, address, email,
				telephoneNumber, mobilNumber, comment, isActiveMember);
	}

	public void changeContactPerson(int personId, String forename,
			String lastName, Address address, String email,
			String telephoneNumber, String mobilNumber, String comment,
			boolean isActiveMember)  {
		changePerson(personId, forename, lastName, address, email,
				telephoneNumber, mobilNumber, comment, isActiveMember);
	}

	public void exportOrganisationPersonListAsPdf(String file)
			throws FileNotFoundException, DocumentException {
		/**
		 * @author KevinSchneider
		 */
		ExportToPdf.exportOrganisationPersonstoPdf(tempOrganisationPersonList,
				file);
	}

	public void exportContactPersonListAsPdf(String file)
			throws FileNotFoundException, DocumentException {
		/**
		 * @author KevinSchneider
		 */
		ExportToPdf.exportContactPersonsToPdf(tempContactPersonList, file);
	}

	public void exportOrganisationPersonListAsCsv(String file)
			throws IOException {
		/**
		 * @author KevinSchneider
		 */
		ExportToCsv.exportOrganisationPersons(file, tempOrganisationPersonList);
	}

	public void exportContactPersonListAsCsv(String file) throws IOException {
		/**
		 * @author KevinSchneider
		 */
		ExportToCsv.exportContactPersons(file, tempContactPersonList);
	}

	public void loadAllPersonsFromDb() throws SQLException {
		PersonManager.getInstance().setAllPersons(
				DataAccess.getInstance().getPersonsFromDB());
		Person.setNextPersonId(DataAccess.getInstance().getNextPersonIdFromDB());
	}

	public void savenewOrganisationPerson(OrganisationPerson person)
			throws SQLException {
		DataAccess.getInstance().storeOrganisationPersonIntoDB(person);
		DataAccess.getInstance().storeNextPersonIdIntoDB(
				Person.getNextPersonId());
		PersonManager.getInstance().loadAllPersonsFromDb();
	}

	public void saveoldOrganisationPerson(OrganisationPerson person)
			throws SQLException, ParseException, OrganisationPersonInUseException {
		
		

				
		
		
		
		DataAccess.getInstance().editPersonInDB(person);
		PersonManager.getInstance().loadAllPersonsFromDb();
	}

	public void savenewContactPerson(ContactPerson person) throws SQLException {
		DataAccess.getInstance().storeContactPersonIntoDB(person);
		DataAccess.getInstance().storeNextPersonIdIntoDB(
				Person.getNextPersonId());
		PersonManager.getInstance().loadAllPersonsFromDb();
	}

	public void saveoldContactPerson(ContactPerson person) throws SQLException {
		DataAccess.getInstance().editPersonInDB(person);
		PersonManager.getInstance().loadAllPersonsFromDb();
	}

	public static int getIdByPerson(Person person) {
		return person.getPersonId();
	}

	public static Person getPersonById(int id) // what?
	{
		Person result = null;
		ArrayList<Person> allPersons = PersonManager.getInstance()
				.getAllPersons();
		Iterator<Person> it = allPersons.iterator();
		while (it.hasNext()) {
			Person tempPerson = it.next();
			if (tempPerson.getPersonId() == id)
				result = tempPerson;
		}
		return result;
	}

	public ArrayList<OrganisationPerson> searchOrganisationPersonsById(
			int OrgPersId, ArrayList<OrganisationPerson> orgPersons) {
		ArrayList<OrganisationPerson> result = new ArrayList<OrganisationPerson>();
		Iterator<OrganisationPerson> it = orgPersons.iterator();
		while (it.hasNext()) {
			OrganisationPerson tempOrgPers = it.next();
			if (tempOrgPers.getPersonId() == OrgPersId) {
				result.add(tempOrgPers);
			}
		}
		return result;
	}

	public ArrayList<OrganisationPerson> searchOrganisationPersonByLastname(
			String lastname, ArrayList<OrganisationPerson> orgPersons) {
		ArrayList<OrganisationPerson> result = new ArrayList<OrganisationPerson>();
		Iterator<OrganisationPerson> it = orgPersons.iterator();
		while (it.hasNext()) {
			OrganisationPerson tempOrgPers = it.next();
			if (tempOrgPers.getLastName().equals(lastname)) {
				result.add(tempOrgPers);
			}
		}
		return result;
	}

	public ArrayList<OrganisationPerson> searchOrganisationPersonByForename(
			String forename, ArrayList<OrganisationPerson> orgPersons) {
		ArrayList<OrganisationPerson> result = new ArrayList<OrganisationPerson>();
		Iterator<OrganisationPerson> it = orgPersons.iterator();
		while (it.hasNext()) {
			OrganisationPerson tempOrgPers = it.next();
			if (tempOrgPers.getForename().equals(forename)) {
				result.add(tempOrgPers);
			}
		}
		return result;
	}

	public ArrayList<OrganisationPerson> searchOrganisationPersons(
			int orgPersId, String lastname, String forename) {
		ArrayList<OrganisationPerson> result = this.getAllOrganisationPersons();
		// Iterator<OrganisationPerson>
		// itOrgPerson=PersonManager.getInstance().getAllOrganisati

		if (orgPersId != -1) {
			result = searchOrganisationPersonsById(orgPersId, result);
		}
		if (lastname != null) {
			result = searchOrganisationPersonByLastname(lastname, result);
		}
		if (forename != null) {
			result = searchOrganisationPersonByForename(forename, result);
		}

		return result;
	}

	public ArrayList<ContactPerson> searchContactPersonsById(int conPersId,
			ArrayList<ContactPerson> conPersons) {
		ArrayList<ContactPerson> result = new ArrayList<ContactPerson>();
		Iterator<ContactPerson> it = conPersons.iterator();
		while (it.hasNext()) {
			ContactPerson tempConPers = it.next();
			if (tempConPers.getPersonId() == conPersId) {
				result.add(tempConPers);
			}
		}
		return result;
	}

	public ArrayList<ContactPerson> searchContactPersonByLastname(
			String lastname, ArrayList<ContactPerson> conPersons) {
		ArrayList<ContactPerson> result = new ArrayList<ContactPerson>();
		Iterator<ContactPerson> it = conPersons.iterator();
		while (it.hasNext()) {
			ContactPerson tempConPers = it.next();
			if (tempConPers.getLastName().equals(lastname)) {
				result.add(tempConPers);
			}
		}
		return result;
	}

	public ArrayList<ContactPerson> searchContactPersonByForename(
			String forename, ArrayList<ContactPerson> conPersons) {
		ArrayList<ContactPerson> result = new ArrayList<ContactPerson>();
		Iterator<ContactPerson> it = conPersons.iterator();
		while (it.hasNext()) {
			ContactPerson tempConPers = it.next();
			if (tempConPers.getForename().equals(forename)) {
				result.add(tempConPers);
			}
		}
		return result;
	}

	public ArrayList<ContactPerson> searchContactPersons(int conPersId,
			String lastname, String forename) {
		ArrayList<ContactPerson> result = this.getAllContactPersons();
		// Iterator<OrganisationPerson>
		// itOrgPerson=PersonManager.getInstance().getAllOrganisati

		if (conPersId != -1) {
			result = searchContactPersonsById(conPersId, result);
		}
		if (lastname != null) {
			result = searchContactPersonByLastname(lastname, result);
		}
		if (forename != null) {
			result = searchContactPersonByForename(forename, result);
		}

		return result;
	}

	public int getNumberOfPersonsBeforeLastLoad() throws SQLException {
		return DataAccess.getInstance().getNextPersonIdFromDB();
	}

	public ArrayList<OrganisationPerson> getTempOrganisationPersonList() {
		return tempOrganisationPersonList;
	}

	public void setTempOrganisationPersonList(
			ArrayList<OrganisationPerson> tempOrganisationPersonList) {
		this.tempOrganisationPersonList = tempOrganisationPersonList;
	}

	public ArrayList<ContactPerson> getTempContactPersonList() {
		return tempContactPersonList;
	}

	public void setTempContactPersonList(
			ArrayList<ContactPerson> tempContactPersonList) {
		this.tempContactPersonList = tempContactPersonList;
	}
}
