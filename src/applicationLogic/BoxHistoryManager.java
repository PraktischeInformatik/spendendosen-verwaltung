package applicationLogic;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import dataAccess.DataAccess;

/**
 * @author Etibar Hasanov
 *
 */
/**
 * @author Etibar
 *
 */
/**
 * @author Etibar
 *
 */
public class BoxHistoryManager {

	private ArrayList<BoxHistory> allBoxHistories;
	private static BoxHistoryManager instance=null;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
	
	public ArrayList<BoxHistory> getAllBoxHistories() {
		return allBoxHistories;
	}
	public void setAllBoxHistories(ArrayList<BoxHistory> allBoxHistories) {
		this.allBoxHistories = allBoxHistories;
	}
	public BoxHistoryManager() {
		allBoxHistories = new ArrayList<BoxHistory>();
	}
	
	public static BoxHistoryManager getInstance() {
		if (instance == null)
			instance = new BoxHistoryManager();
		return instance;
	}
	
	/**
	 * @author Etibar Hasanov
	 * @param boxHistoryId
	 * @return
	 */
	public BoxHistory getBoxHistoryWithId (int boxHistoryId)
	{
		BoxHistory result=null;
		Iterator<BoxHistory> itBoxHistory=BoxHistoryManager.getInstance().getAllBoxHistories().iterator();
		while (itBoxHistory.hasNext())
		{
			BoxHistory tempBoxHistory=itBoxHistory.next();
			if (tempBoxHistory.getBoxHistoryId()==boxHistoryId) result=tempBoxHistory;
		}
		return result;
		}
  
	/**
	 * @author Etibar Hasanov
	 * @param donationBoxId
	 * @return ArrayList<BoxHistory>
	 */
	public ArrayList<BoxHistory> getBoxHistoriesWithDonationBoxId (int donationBoxId)
	{
		ArrayList<BoxHistory> result=new ArrayList<BoxHistory> ();
		ArrayList<BoxHistory> tempBoxHistories=BoxHistoryManager.getInstance().getAllBoxHistories();
		Iterator<BoxHistory>  tempIt=tempBoxHistories.iterator();
		while (tempIt.hasNext()) 
		{
			BoxHistory tempBoxHistory=tempIt.next();
			if (tempBoxHistory.getBoxId()==donationBoxId)
			    result.add(tempBoxHistory);
			
		}
		return result;
	}
	
	/**
	 * @author Etibar Hasanov
	 * @param collectionId
	 * @return ArrayList<BoxHistory>
	 */
	public ArrayList<BoxHistory> getBoxHistoriesWithCollectionId (int collectionId)
	{
		ArrayList<BoxHistory> result=new ArrayList<BoxHistory> ();
		ArrayList<BoxHistory> tempBoxHistories=BoxHistoryManager.getInstance().getAllBoxHistories();
		Iterator<BoxHistory>  tempIt=tempBoxHistories.iterator();
		while (tempIt.hasNext()) 
		{
			BoxHistory tempBoxHistory=tempIt.next();
			if (tempBoxHistory.getCollectionId()==collectionId)
			    result.add(tempBoxHistory);
			
		}
		return result;
	}
	

	/**
	 * @author Etibar Hasanov
	 * @param boxId
	 * @param collectionId
	 * @return
	 */

	public BoxHistory getBoxHistoriesWithBoxIdandCollectionIdandEndDateNull (int boxId,int collectionId)
	{
		BoxHistory result=null;
		ArrayList<BoxHistory> tempBoxHistories = BoxHistoryManager.getInstance().getAllBoxHistories();
		Iterator<BoxHistory>  tempIt = tempBoxHistories.iterator();
		while (tempIt.hasNext()) 
		{
			BoxHistory tempBoxHistory=tempIt.next();
			if (tempBoxHistory.getBoxId()==boxId &&	tempBoxHistory.getCollectionId()==collectionId && tempBoxHistory.getEndDate()==null)
			    result=tempBoxHistory;
			
		}
		return result;
	}
	

	/**
	 * @author Etibar Hasanov
	 * @throws SQLException
	 * @throws ParseException
	 */

	public  void  loadBoxHistories() throws SQLException, ParseException 
	{
		ArrayList<BoxHistory> tempBoxHistories=DataAccess.getInstance().getBoxHistoriesFromDB();
		BoxHistory.setNextBoxHistoryId(DataAccess.getInstance().getNextBoxHistoryIdFromDB());
		BoxHistoryManager.getInstance().setAllBoxHistories(tempBoxHistories);
	}


	/**
	 * @author Etibar Hasanov
	 * @param boxHistory
	 * @throws SQLException
	 * @throws ParseException
	 */

	public  void  saveBoxHistories(BoxHistory boxHistory
			) throws SQLException, ParseException
	{
		System.out.println(boxHistory.getBoxHistoryId());
		DataAccess.getInstance().storeBoxHistoryIntoDB(boxHistory);
		DataAccess.getInstance().storeNextBoxHistoryIdIntoDB(BoxHistory.getNextBoxHistoryId());
		BoxHistoryManager.getInstance().loadBoxHistories();
	}
	

	/**
	 * @author Etibar Hasanov
	 * @param boxHistory
	 * @throws SQLException
	 * @throws ParseException
	 */

	public  void  editBoxHistories(BoxHistory boxHistory
			) throws SQLException, ParseException
	{
		DataAccess.getInstance().editBoxHistoryInDB(boxHistory);
		BoxHistoryManager.getInstance().loadBoxHistories();
	}
	
	/*public  void  editEndDateInBoxHistories(BoxHistory boxHistory
			) throws SQLException, ParseException
	{
		DataAccess.getInstance().editBoxHistoryInDB(boxHistory);
		BoxHistoryManager.getInstance().loadBoxHistories();
	}**/
	
	

	/**
	 * @author Etibar Hasanov
	 * @param boxId
	 * @param collectionId
	 * @throws SQLException
	 * @throws ParseException
	 */

	public void create(int boxId, int collectionId) throws SQLException, ParseException{
		if (BoxHistoryManager.getInstance().getBoxHistoriesWithBoxIdandCollectionIdandEndDateNull(boxId, collectionId)==null)
		{
		BoxHistory boxHistory=new BoxHistory(collectionId,boxId,Calendar.getInstance(),null) ;
	    BoxHistoryManager.getInstance().saveBoxHistories(boxHistory);
		}
	}
	

	/**
	 * @author Etibar Hasanov
	 * @param boxId
	 * @param collectionId
	 * @throws SQLException
	 * @throws ParseException
	 */

	public void setEndDateNull(int boxId,int collectionId) throws SQLException, ParseException{
		   BoxHistory temp=BoxHistoryManager.getInstance().
		                                       getBoxHistoriesWithBoxIdandCollectionIdandEndDateNull(boxId, collectionId);
		   temp.setEndDate(Calendar.getInstance());
		   BoxHistoryManager.getInstance().editBoxHistories(temp);
	}
	
	

	/**
	 * @author Etibar Hasanov
	 * @param allBoxes
	 * @throws SQLException
	 * @throws ParseException
	 */

	public void createForDonationBoxesBoxHistoryWithSetDateNull(ArrayList<DonationBox> allBoxes) throws SQLException, ParseException
	{
		Iterator<DonationBox> itAllBoxes=allBoxes.iterator();
		while (itAllBoxes.hasNext()) {
			DonationBox tempBox=itAllBoxes.next();

		if (BoxHistoryManager.getInstance().getBoxHistoriesWithBoxIdandCollectionIdandEndDateNull(tempBox.getBoxId(), tempBox.getCollectionId())==null)

			BoxHistoryManager.getInstance().create(tempBox.getBoxId(),tempBox.getCollectionId());
		}
	}
	

	/**
	 * @author Etibar Hasanov
	 * @param allBoxes
	 * @throws SQLException
	 * @throws ParseException
	 */

	public void setEndDateNullForDonationBoxesBoxHistoryWithSetDateNull(ArrayList<DonationBox> allBoxes) throws SQLException, ParseException
	{
		Iterator<DonationBox> itAllBoxes=allBoxes.iterator();
		while (itAllBoxes.hasNext()) {
			DonationBox tempBox=itAllBoxes.next();
			BoxHistoryManager.getInstance().setEndDateNull(tempBox.getBoxId(), tempBox.getCollectionId());
		}
	}
	
	
	
	}
