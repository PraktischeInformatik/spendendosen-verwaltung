package applicationLogic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Etibar Hasanov
 *
 */
public class BoxHistory {

	
	
	private static int nextBoxHistoryId = 0;
	private int boxHistoryId;
	private int collectionId;
	private int boxId;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
	private Calendar beginDate;
	private Calendar endDate;

	//get und set
	public static int getNextBoxHistoryId() {
		return nextBoxHistoryId;
	}

	public static void setNextBoxHistoryId(int nextBoxHistoryId) {
		BoxHistory.nextBoxHistoryId = nextBoxHistoryId;
	}

	public int getBoxHistoryId() {
		return boxHistoryId;
	}

	public void setBoxHistoryId(int boxHistoryId) {
		this.boxHistoryId = boxHistoryId;
	}

	public int getCollectionId() {
		return collectionId;
	}

	public void setCollectionId(int collectionId) {
		this.collectionId = collectionId;
	}

	public int getBoxId() {
		return boxId;
	}

	public void setBoxId(int boxId) {
		this.boxId = boxId;
	}

	public Calendar getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Calendar beginDate) {
		this.beginDate = beginDate;
	}

	public Calendar getEndDate() {
		return endDate;
	}

	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}

	public BoxHistory() {
		this.boxHistoryId = this.getNextBoxHistoryId();
		this.nextBoxHistoryId++;
	}

	/**
	 * @author Etibar Hasanov
	 * @param collectionId
	 * @param boxId
	 * @param beginDate
	 * @param endDate
	 */
	public BoxHistory(int collectionId, int boxId, Calendar beginDate,
			Calendar endDate) {
		this();
		this.collectionId = collectionId;
		this.boxId = boxId;
		this.beginDate = beginDate;
		this.endDate = endDate;
	}

	// added by sz
	/**
	 * @param boxHistoryId
	 * @param beginDate
	 * @param endDate
	 * @param collectionId
	 * @param boxId
	 */
	public BoxHistory(int boxHistoryId, String beginDate, String endDate,
			int collectionId, int boxId) throws ParseException {
		Calendar beginCal = Calendar.getInstance();
		Calendar endCal = Calendar.getInstance();
		try {
			beginCal.setTime(sdf.parse(beginDate));
			setBeginDate(beginCal);
		} catch (Exception e) {
			setBeginDate(null);
		}

		try {
			endCal.setTime(sdf.parse(endDate));
			setEndDate(endCal);
		} catch (Exception e) {
			setEndDate(null);
		}
		this.boxHistoryId = boxHistoryId;
		// this.beginDate = beginCal;
		// this.endDate = endCal;
		this.collectionId = collectionId;
		this.boxId = boxId;

	}

	// added by sz
	@Override
	public String toString() {
		
		String bla = "-";
		try
		{
			bla = sdf.format(endDate.getTime());
		}
		catch(Exception x)
		{
			bla = null;
		}
		return "BoxHistory [boxHistoryId=" + boxHistoryId + ", collectionId="
				+ collectionId + ", boxId=" + boxId + ", beginDate="
				+ sdf.format(beginDate.getTime()) + ", endDate="
				+ bla + "]";
		
		// sdf.format(endDate.getTime())
	}
	
	/**
	 * @param Date
	 */	
	public static Calendar dateToCalendar(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}
	
	

}