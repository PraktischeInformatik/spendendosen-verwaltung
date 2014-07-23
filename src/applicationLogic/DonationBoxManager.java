	package applicationLogic;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import applicationLogicAccess.Access;
import applicationLogicAccess.PersonHistoryAccess;

import com.itextpdf.text.DocumentException;

import dataAccess.DataAccess;

/**
 * @author Etibar Hasanov
 *
 */
public class DonationBoxManager {
	private ArrayList<DonationBox> allBoxes;
	private ArrayList<DonationBox> tempDonationBoxList;
	private static DonationBoxManager instance = null;
	CurrentSearchValuesDonationBox currSearchBox;

	public void setCurrSearchBox(CurrentSearchValuesDonationBox currSearchBox) {
		this.currSearchBox = currSearchBox;
	}

	public CurrentSearchValuesDonationBox getCurrSearchBox() {
		return currSearchBox;
	}

	DonationBoxManager() {
		allBoxes = new ArrayList<DonationBox>();
		currSearchBox = new CurrentSearchValuesDonationBox();
	}

	public static DonationBoxManager getInstance() {
		if (instance == null)
			instance = new DonationBoxManager();
		return instance;
	}

	public ArrayList<DonationBox> getAllBoxes() {
		return allBoxes;
	}

	public void setAllBoxes(ArrayList<DonationBox> allDonationBoxes) {
		this.allBoxes = allDonationBoxes;
	}

	public void addDonationBox(DonationBox donationBox) {
		this.getAllBoxes().add(donationBox);
	}

	/**
	 * @author Etibar Hasanov
	 * @param bool
	 * @return
	 */
	public ArrayList<DonationBox> searchDonationBoxByInUse(boolean bool) {
		ArrayList<DonationBox> temporaryBoxes = new ArrayList<DonationBox>();
		Iterator<DonationBox> it = allBoxes.iterator();
		while (it.hasNext()) {
			DonationBox db = it.next();
			if (db.isInUse())
				temporaryBoxes.add(db);
		}
		return temporaryBoxes;
	}

	
	public ArrayList<DonationBox> getTempDonationBoxList() {
		return tempDonationBoxList;
	}

	public void setTempDonationBoxList(
			ArrayList<DonationBox> tempDonationBoxList) {
		this.tempDonationBoxList = tempDonationBoxList;
	}

	/**
	 * @author Etibar Hasanov
	 * @param boxId
	 * @param donationBoxes
	 * @return
	 */
	public ArrayList<DonationBox> searchDonationBoxWithBoxId(int boxId,
			ArrayList<DonationBox> donationBoxes) {
		ArrayList<DonationBox> result = new ArrayList<DonationBox>();
		Iterator<DonationBox> itResult = donationBoxes.iterator();
		while (itResult.hasNext()) {
			DonationBox tempBox = itResult.next();
			if (tempBox.getBoxId() == boxId)
				result.add(tempBox);
		}
		return result;

	}

	/**
	 * @author Etibar Hasanov
	 * @param responsiblePersonId
	 * @param donationBoxes
	 * @return
	 */
	public ArrayList<DonationBox> searchDonationBoxWithResponsiblePerson(
			int responsiblePersonId, ArrayList<DonationBox> donationBoxes) {

		ArrayList<DonationBox> result = new ArrayList<DonationBox>();
		Iterator<DonationBox> itResult = donationBoxes.iterator();
		while (itResult.hasNext()) {
			DonationBox tempBox = itResult.next();
			if (tempBox.getCurrentResponsiblePerson().getPersonId() == responsiblePersonId)
				result.add(tempBox);
		}
		return result;

	}

	/**
	 * @author Etibar Hasanov
	 * @param comment
	 * @param donationBoxes
	 * @return
	 */
	public ArrayList<DonationBox> searchDonationBoxWithComment(String comment,
			ArrayList<DonationBox> donationBoxes) {

		ArrayList<DonationBox> result = new ArrayList<DonationBox>();
		Iterator<DonationBox> itResult = donationBoxes.iterator();
		while (itResult.hasNext()) {
			DonationBox tempBox = itResult.next();
			if (tempBox.getComment().equals(comment))
				result.add(tempBox);
		}
		return result;

	}

	/**
	 * @author Etibar Hasanov
	 * @param collectionId
	 * @param donationBoxes
	 * @return
	 */
	public ArrayList<DonationBox> searchDonationBoxWithCollectionId(
			int collectionId, ArrayList<DonationBox> donationBoxes) {

		ArrayList<DonationBox> result = new ArrayList<DonationBox>();
		Iterator<DonationBox> itResult = donationBoxes.iterator();
		while (itResult.hasNext()) {
			DonationBox tempBox = itResult.next();
			if (tempBox.getCollectionId() == collectionId)
				result.add(tempBox);
		}
		return result;

	}

	/**
	 * @author Etibar Hasanov
	 * @param date
	 * @param donationBoxes
	 * @return
	 */
	public ArrayList<DonationBox> searchDonationBoxWithClearingDate(Date date,
			ArrayList<DonationBox> donationBoxes) {

		// Calendar cal=DonationBoxManager.getInstance().dateToCalendar(date);
		Calendar cal = DonationBoxManager.dateToCalendar(date);
		ArrayList<DonationBox> result = new ArrayList<DonationBox>();
		Iterator<DonationBox> itResult = donationBoxes.iterator();
		while (itResult.hasNext()) {
			DonationBox tempBox = itResult.next();
			Iterator<ClearingDonationBox> itForClearingBoxes = tempBox
					.getClearingDonationBoxes().iterator();
			while (itForClearingBoxes.hasNext()) {
				ClearingDonationBox tempClearingBox = itForClearingBoxes.next();
				if (DonationBoxManager.areEqual(tempClearingBox.getClearDate(),
						cal)) {
					result.add(tempBox);
					break;
				}
			}
		}
		return result;

	}

	/**
	 * @author Etibar Hasanov
	 * @param inUseYes
	 * @param inUseNo
	 * @param donationBoxes
	 * @return
	 */
	public ArrayList<DonationBox> searchDonationBoxWithInUse(boolean inUseYes,
			boolean inUseNo, ArrayList<DonationBox> donationBoxes) {

		ArrayList<DonationBox> result = new ArrayList<DonationBox>();

		if (inUseYes == true) {
			Iterator<DonationBox> itResult = donationBoxes.iterator();
			while (itResult.hasNext()) {
				DonationBox tempBox = itResult.next();
				if (tempBox.isInUse() == true)
					result.add(tempBox);
			}
		}

		if (inUseNo == true) {
			Iterator<DonationBox> itResult = donationBoxes.iterator();
			while (itResult.hasNext()) {
				DonationBox tempBox = itResult.next();
				if (tempBox.isInUse() == false)
					result.add(tempBox);
			}
		}
		return result;
	}

	public void exportDonationBoxListAsPdf(String file)
			throws FileNotFoundException, DocumentException, SQLException,
			ParseException {

		/**
		 * @author KevinSchneider
		 */
		ExportToPdf.exportDonationBoxesToPdf(tempDonationBoxList, file);
	}

	public void exportDonationBoxListAsCsv(String file) throws IOException,
			SQLException, ParseException {
		/**
		 * @author KevinSchneider
		 */
		ExportToCsv.exportDonationBoxes(file, tempDonationBoxList);
	}

	/**
	 * @author Etibar Hasanov
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void loadAllDonationBoxes() throws SQLException, ParseException {
		DonationBoxManager.getInstance().setAllBoxes(
				DataAccess.getInstance().getDonationBoxesFromDB());
		DonationBox.setNextBoxId(DataAccess.getInstance().getNextBoxIdFromDB());
		ClearingDonationBox.setNextClearingId(DataAccess.getInstance()
				.getNextClearingIdFromDB());
		ArrayList<DonationBox> tempAllDonationBoxes = DonationBoxManager
				.getInstance().getAllBoxes();
		Iterator<DonationBox> it = tempAllDonationBoxes.iterator();
		while (it.hasNext()) {
			DonationBox tempDb = it.next();
			tempDb.setClearingDonationBoxes(DataAccess.getInstance()
					.getClearingDonationBoxesByBoxIdInCollectionId(
							tempDb.getBoxId()));
		}

	}

	/**
	 * @author Etibar Hasanov
	 * @param orgPerson
	 * @param collection
	 * @param donationBox
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void savenewDonationBox(OrganisationPerson orgPerson,
			DonationCollection collection, DonationBox donationBox)
			throws SQLException, ParseException {
		DataAccess.getInstance().storeDonationBoxIntoDB(donationBox);
		DataAccess.getInstance().setPersonIdReferenceInDonationBox(
				orgPerson.getPersonId(), donationBox.getBoxId());
		DataAccess.getInstance().setCollectionIdReferenceInDonationBox(
				collection.getCollectionId(), donationBox.getBoxId());

		// ArrayList<ClearingDonationBox> tempClearingBoxes = donationBox
		// .getClearingDonationBoxes();
		// Iterator<ClearingDonationBox> it = tempClearingBoxes.iterator();
		// int globalClearingBoxId = DataAccess.getInstance()
		// .getNextClearingIdFromDB();
		//
		// while (it.hasNext()) {
		// ClearingDonationBox tempClearingBox = it.next();
		// if (tempClearingBox.getClearingId() >= globalClearingBoxId) {
		// DataAccess.getInstance().storeClearingDonationBoxIntoDatabase(
		// tempClearingBox);
		// DataAccess.getInstance()
		// .setCollectionIdReferenceInClearingDonationBox(
		// collection.getCollectionId(),
		// tempClearingBox.getClearingId());
		// DataAccess.getInstance()
		// .setBoxIdReferenceInClearingDonationBox(
		// donationBox.getBoxId(),
		// tempClearingBox.getClearingId());
		// }
		// }
		DataAccess.getInstance().storeNextClearingIdIntoDB(
				ClearingDonationBox.getNextClearingId());
		DataAccess.getInstance().storeNextBoxIdIntoDB(
				DonationBox.getNextBoxId());
		DonationBoxManager.getInstance().loadAllDonationBoxes();
	}
	
	// von Oliver - benötigt für Auslagerung der Logik aus Gui
	/**
	 * @author Etibar Hasanov
	 * @param boxId
	 * @param personId
	 * @param comment
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void editDonationBox(int boxId, int personId, String comment) throws SQLException, ParseException
	{
		DonationBox box = Access.getDonationBoxById(boxId);
		
		
		box.setComment(comment);
		
		OrganisationPerson person = (OrganisationPerson) Access.getPersonById(personId);
        if (person.getPersonId() == box.getCurrentResponsiblePerson().getPersonId())
        {
        	 box.setCurrentResponsiblePerson(person);
        }
        else
        {  	       
        	if(person.getPersonId() == 1000000)
        	{
        		ArrayList<PersonHistory> list = PersonHistoryAccess.searchOrgaBoxHistoriesByCollectionId(box.getBoxId());
    			OrgaBoxHistory history = (OrgaBoxHistory) list.get(list.size()-1);
    			history.setEndDate(Calendar.getInstance());
    			PersonHistoryAccess.editOrgaBoxHistory(history);
        	}
        	else 
        	{
        		if(box.getCurrentResponsiblePerson().getPersonId() == 1000000)
        		{
        			Date date = new Date();
            		box.setCurrentResponsiblePerson(person);
            		PersonHistoryAccess.insertOrgaBoxHistory(box.getCurrentResponsiblePerson().getPersonId(), box.getBoxId(), date, null);
        		}
        		else
        		{
        			ArrayList<PersonHistory> list = PersonHistoryAccess.searchOrgaBoxHistoriesByCollectionId(box.getBoxId());
        			OrgaBoxHistory history = (OrgaBoxHistory) list.get(list.size()-1);
        			history.setEndDate(Calendar.getInstance());
        			PersonHistoryAccess.editOrgaBoxHistory(history);
        			
        			Date date = new Date();
        			box.setCurrentResponsiblePerson(person);
        			PersonHistoryAccess.insertOrgaBoxHistory(box.getCurrentResponsiblePerson().getPersonId(), box.getBoxId(), date, null);
        		}
        	}
        	box.setCurrentResponsiblePerson(person);
        }
        
        Access.editDonationBox(box);
        
	}

	/**
	 * @author Etibar Hasanov
	 * @param orgPerson
	 * @param collection
	 * @param donationBox
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void editDonationBox(OrganisationPerson orgPerson,
			DonationCollection collection, DonationBox donationBox)
			throws SQLException, ParseException {
		DataAccess.getInstance().editDonationBoxInDB(donationBox);
		DataAccess.getInstance().setPersonIdReferenceInDonationBox(
				orgPerson.getPersonId(), donationBox.getBoxId());
		DataAccess.getInstance().setCollectionIdReferenceInDonationBox(
				collection.getCollectionId(), donationBox.getBoxId());

		// ArrayList<ClearingDonationBox> tempClearingBoxes = donationBox
		// .getClearingDonationBoxes();
		// Iterator<ClearingDonationBox> it = tempClearingBoxes.iterator();
		// int globalClearingBoxId = DataAccess.getInstance()
		// .getNextClearingIdFromDB();
		//
		// while (it.hasNext()) {
		// ClearingDonationBox tempClearingBox = it.next();
		// if (tempClearingBox.getClearingId() >= globalClearingBoxId) {
		// DataAccess.getInstance().storeClearingDonationBoxIntoDatabase(
		// tempClearingBox);
		// DataAccess.getInstance()
		// .setCollectionIdReferenceInClearingDonationBox(
		// collection.getCollectionId(),
		// tempClearingBox.getClearingId());
		// DataAccess.getInstance()
		// .setBoxIdReferenceInClearingDonationBox(
		// donationBox.getBoxId(),
		// tempClearingBox.getClearingId());
		// }
		// }
		DataAccess.getInstance().storeNextClearingIdIntoDB(
				ClearingDonationBox.getNextClearingId());
		DataAccess.getInstance().storeNextBoxIdIntoDB(
				DonationBox.getNextBoxId());
		DonationBoxManager.getInstance().loadAllDonationBoxes();

	}

	/**
	 * @author Etibar Hasanov
	 * @param donationBox
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void savenewDonationBox(DonationBox donationBox)
			throws SQLException, ParseException {
		
		PersonHistoryAccess.insertOrgaBoxHistory(donationBox.getCurrentResponsiblePerson().getPersonId(), donationBox.getBoxId(), new Date(), null);
		
		DataAccess.getInstance().storeDonationBoxIntoDB(donationBox);
		DataAccess.getInstance().setPersonIdReferenceInDonationBox(
				donationBox.getCurrentResponsiblePerson().getPersonId(),
				donationBox.getBoxId());
		DataAccess.getInstance().setCollectionIdReferenceInDonationBox(
				donationBox.getCollectionId(), donationBox.getBoxId());

		// ArrayList<ClearingDonationBox> tempClearingBoxes = donationBox
		// .getClearingDonationBoxes();
		// Iterator<ClearingDonationBox> it = tempClearingBoxes.iterator();
		// int globalClearingBoxId = DataAccess.getInstance()
		// .getNextClearingIdFromDB();
		//
		// while (it.hasNext()) {
		// ClearingDonationBox tempClearingBox = it.next();
		// if (tempClearingBox.getClearingId() >= globalClearingBoxId) {
		// DataAccess.getInstance().storeClearingDonationBoxIntoDatabase(
		// tempClearingBox);
		// DataAccess.getInstance()
		// .setCollectionIdReferenceInClearingDonationBox(
		// donationBox.getCollectionId(),
		// tempClearingBox.getClearingId());
		// DataAccess.getInstance()
		// .setBoxIdReferenceInClearingDonationBox(
		// donationBox.getBoxId(),
		// tempClearingBox.getClearingId());
		// }
		// }
		DataAccess.getInstance().storeNextClearingIdIntoDB(
				ClearingDonationBox.getNextClearingId());
		DataAccess.getInstance().storeNextBoxIdIntoDB(
				DonationBox.getNextBoxId());
		DonationBoxManager.getInstance().loadAllDonationBoxes();
	}

	/**
	 * @author Etibar Hasanov
	 * @param donationBox
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void editDonationBox(DonationBox donationBox) throws SQLException,
			ParseException {
		DataAccess.getInstance().editDonationBoxInDB(donationBox);
		DataAccess.getInstance().setPersonIdReferenceInDonationBox(
				donationBox.getCurrentResponsiblePerson().getPersonId(),
				donationBox.getBoxId());
		DataAccess.getInstance().setCollectionIdReferenceInDonationBox(
				donationBox.getCollectionId(), donationBox.getBoxId());

		// ArrayList<ClearingDonationBox> tempClearingBoxes = donationBox
		// .getClearingDonationBoxes();
		// Iterator<ClearingDonationBox> it = tempClearingBoxes.iterator();
		// int globalClearingBoxId = DataAccess.getInstance()
		// .getNextClearingIdFromDB();
		//
		// while (it.hasNext()) {
		// ClearingDonationBox tempClearingBox = it.next();
		// if (tempClearingBox.getClearingId() >= globalClearingBoxId)
		// DataAccess.getInstance().storeClearingDonationBoxIntoDatabase(
		// tempClearingBox);
		// DataAccess.getInstance()
		// .setCollectionIdReferenceInClearingDonationBox(
		// donationBox.getCollectionId(),
		// tempClearingBox.getClearingId());
		// DataAccess.getInstance().setBoxIdReferenceInClearingDonationBox(
		// donationBox.getBoxId(), tempClearingBox.getClearingId());
		// }
		DataAccess.getInstance().storeNextClearingIdIntoDB(
				ClearingDonationBox.getNextClearingId());
		DataAccess.getInstance().storeNextBoxIdIntoDB(
				DonationBox.getNextBoxId());
		DonationBoxManager.getInstance().loadAllDonationBoxes();

	}

	/**
	 * @author Etibar Hasanov
	 * @param donationBoxId
	 * @return
	 */
	public DonationBox getDonationBoxById(int donationBoxId) {
		Iterator<DonationBox> findIterator = allBoxes.iterator();
		while (findIterator.hasNext()) {
			DonationBox tempDonationBox = findIterator.next();
			if (tempDonationBox.getBoxId() == donationBoxId) {
				return tempDonationBox;
			}
		}
		return null;

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
	public void changeDonationBox(int boxId, float sum, boolean inUse,
			String comment, OrganisationPerson currentResponsiblePerson,
			int collectionId) throws IdNotFoundException {
		DonationBox resultDonationBox = DonationBoxManager.getInstance()
				.getDonationBoxById(boxId);
		resultDonationBox.setSum(sum);
		resultDonationBox.setInUse(inUse);
		resultDonationBox.setComment(comment);
		resultDonationBox.setCurrentResponsiblePerson(currentResponsiblePerson);
		resultDonationBox.setCollectionId(collectionId);
		return;
	}

	/**
	 * @author Etibar Hasanov
	 * @param date
	 * @return
	 */
	public static Calendar dateToCalendar(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

	/**
	 * @author Etibar Hasanov
	 * @param c1
	 * @param c2
	 * @return
	 */
	private static boolean areEqual(Calendar c1, Calendar c2) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		return (sdf.format(c1.getTime()).equals(sdf.format(c2.getTime())));
	}

	/**
	 * @author Etibar Hasanov
	 * @return
	 */
	public ArrayList<DonationBox> getAvailableDonationBoxes() {
		ArrayList<DonationBox> availableBoxes = new ArrayList<DonationBox>();
		Iterator<DonationBox> findIterator = allBoxes.iterator();
		while (findIterator.hasNext()) {
			DonationBox tempDonationBox = findIterator.next();
			if (tempDonationBox.getIsDeleted() == false
					&& tempDonationBox.isInUse() == false) {
				availableBoxes.add(tempDonationBox);
			}
		}

		return availableBoxes;

	}

	// returns the box sum //added sz
	/**
	 * @author Sebastian
	 * @param boxId
	 * @return
	 * @throws SQLException
	 * @throws ParseException
	 */
	public float calculateSumByBoxId(int boxId) throws SQLException,
			ParseException {

		float result = 0;
		ArrayList<ClearingDonationBox> cdbList = new ArrayList<ClearingDonationBox>();
		cdbList = DataAccess.getInstance().getClearingDonationBoxesByBoxId(
				boxId);

		for (ClearingDonationBox current : cdbList) {
			result = result + current.getSum();
		}

		return result;

	}

	/**
	 * @author Etibar Hasanov
	 * @param boxId
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void deleteDonationBox(int boxId) throws SQLException,
			ParseException {
		DonationBox tempDonBox=DonationBoxManager.getInstance().getDonationBoxById(boxId);
		if (tempDonBox.getCollectionId()!=0) {
			//BoxHistoryManager.getInstance().setEndDateNull(tempDonBox.getBoxId(),tempDonBox.getCollectionId());
			//DonationCollectionManager.getInstance().deleteBoxFromDonationCollection(boxId);
		}
		tempDonBox.setIsDeleted(true);
		DataAccess.getInstance().deleteDonationBoxById(boxId);
		DonationBoxManager.getInstance().loadAllDonationBoxes();
	}

	// added
	public void saveClearingDonationBox(ClearingDonationBox cdb,
			int collectionId, int boxId) throws SQLException, ParseException {
		DataAccess.getInstance().storeClearingDonationBoxIntoDatabase(cdb);
		DataAccess.getInstance().setCollectionIdReferenceInClearingDonationBox(
				collectionId, cdb.getClearingId());
		DataAccess.getInstance().setBoxIdReferenceInClearingDonationBox(boxId,
				cdb.getClearingId());
		/**
		 * @author Timo
		 * 
		 *         we need to update the sum
		 */

		// CALCULATE NEW SUM
		float sum = 0.0f;
		sum = Access.calculateSumByBoxId(boxId);
		// THIS SUM NEEDS TO BE PUT INTO THE COLLECTION WITH 'collectionId'

	}
	
	
	/**
	 * @author Etibar Hasanov
	 * @param id
	 * @return
	 */
	public boolean idInUse (int id)
	{  
		boolean result=false;
		ArrayList<DonationBox> tempDonBoxes=DonationBoxManager.getInstance().getAllBoxes();
		Iterator<DonationBox> tempIt=tempDonBoxes.iterator();
		while (tempIt.hasNext()) {
			DonationBox tempDon=tempIt.next();
			if (tempDon.getBoxId()==id) result=true ;
		}
		
		return result;
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
	public DonationBox createDonationBoxWithId (int boxId,float sum, boolean inUse, String comment,
			OrganisationPerson currentResponsiblePerson, int collectionId) throws  IdInUseException, SQLException   {
		DonationBox result;
		if (DonationBoxManager.getInstance().idInUse(boxId)==true) 
			{
			throw new IdInUseException() ;
			}
		else {
			if (boxId>DonationBox.getNextBoxId())  {
				int temp=boxId+1;
				DonationBox.setNextBoxId(temp);
				DataAccess.getInstance().storeNextBoxIdIntoDB(
						DonationBox.getNextBoxId());
			}
			result=new DonationBox(boxId,sum, inUse,comment,
					 currentResponsiblePerson, collectionId) ;
		}
		return result ;
	}
	
	/**
	 * @author Etibar Hasanov
	 * @param boxId
	 * @return
	 * @throws IdInUseException
	 * @throws SQLException
	 */
	public DonationBox createDonationBoxWithId (int boxId) throws  IdInUseException, SQLException   {
		DonationBox result;
		if (DonationBoxManager.getInstance().idInUse(boxId)==true) 
			{
			throw new IdInUseException() ;
			}
		else {
			if (boxId>DonationBox.getNextBoxId()){
				int temp=boxId+1;
				DonationBox.setNextBoxId(temp);
				DataAccess.getInstance().storeNextBoxIdIntoDB(
						DonationBox.getNextBoxId());
			}
			result=new DonationBox(boxId) ;
		}
		return result ;
	}
	

	/**
	 * @author Etibar Hasanov
	 * @return
	 */
	public int findLeastFreeId()  {
		int i=1;
		while (true)
		{
			if (DonationBoxManager.getInstance().idInUse(i))
			{i=i+1;}
			else return i;
		}
		
		}
	// creation of DonationBox objekt , its not in manager or in db1
	/**
	 * @author Etibar Hasanov
	 * @return
	 */
	public DonationBox createDonationBoxWithLeastFreeId ()   {
		DonationBox result=new DonationBox(DonationBoxManager.getInstance().findLeastFreeId());
		return result;
	}
	
	/**
	 * @author Etibar Hasanov
	 * @param id
	 * @param donBoxes
	 * @return
	 */
	public boolean idInUseINArrayList (int id,ArrayList<DonationBox> donBoxes)
	{  
		boolean result=false;
		Iterator<DonationBox> tempIt=donBoxes.iterator();
		while (tempIt.hasNext()) {
			DonationBox tempDon=tempIt.next();
			if (tempDon.getBoxId()==id) result=true ;
		}
		
		return result;
	}
	
	/**
	 * @author Etibar Hasanov
	 * @param donBoxes1
	 * @param donBoxes2
	 * @return
	 */
	public ArrayList<DonationBox> subtractionOfArrayListOfDonationBoxes
	                             (ArrayList<DonationBox> donBoxes1, ArrayList<DonationBox> donBoxes2)
	 { 
		ArrayList<DonationBox> result=new ArrayList<DonationBox>();
		ArrayList<DonationBox> temp=new ArrayList<DonationBox> ();
		Iterator<DonationBox> it1=donBoxes1.iterator();
		Iterator<DonationBox> it2=donBoxes2.iterator();
		while (it1.hasNext()) temp.add(it1.next());
		while (it2.hasNext()) temp.add(it2.next());
		
		Iterator<DonationBox> it3=temp.iterator();
		
		while (it3.hasNext()){
		    DonationBox temp1=it3.next();
		    if (DonationBoxManager.getInstance().idInUseINArrayList(temp1.getBoxId(), donBoxes1)==true||
		    		DonationBoxManager.getInstance().idInUseINArrayList(temp1.getBoxId(), donBoxes2)==false)
		    	result.add(temp1);
		}
		return result;
	                             }
	
	
}
