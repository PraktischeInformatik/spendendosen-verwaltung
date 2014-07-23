package applicationLogic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author Etibar Hasanov
 *
 */
public class ClearingDonationBox {

	private static int nextClearingId = 0;
	private int clearingId;
	private Calendar clearDate;
	private float sum;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

	public static int getNextClearingId() {
		return nextClearingId;
	}

	public static void setNextClearingId(int nextClearingId) {
		ClearingDonationBox.nextClearingId = nextClearingId;
	}

	public int getClearingId() {
		return clearingId;
	}

	public void setClearingId(int clearingId) {
		this.clearingId = clearingId;
	}

	public Calendar getClearDate() {
		return clearDate;
	}

	public float getSum() {
		return sum;
	}

	public void setSum(float sum) {
		this.sum = sum;
	}

	public ClearingDonationBox() {
		this.clearingId = this.nextClearingId;
		this.nextClearingId++;
	}

	public ClearingDonationBox(Calendar clearDate, float sum ) {
		this();
		this.clearDate = clearDate;
		this.sum = sum;
	}

	// Sebastian
	public ClearingDonationBox(int clearingId, Calendar clearDate, float sum) {
		this.clearingId = clearingId;
		this.clearDate = clearDate;
		this.sum = sum;
	}

	public ClearingDonationBox(int clearingId, String clearDate, float sum) throws ParseException {
		Calendar cal = Calendar.getInstance();
		this.clearingId = clearingId;
		cal.setTime(sdf.parse(clearDate));
		this.clearDate = cal;
		this.sum = sum;
	}
	
	public String toString() {
		return "ClearingDonationBox:"+"clearingId=" + clearingId + ",clearDate=" + sdf.format(clearDate.getTime())
				+ ", sum=" + sum ;
	}

}
