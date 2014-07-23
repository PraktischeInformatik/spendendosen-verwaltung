/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package applicationLogic;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author Timo
 */
public abstract class PersonHistory {
	
	private int historyId;
	private Calendar beginDate;
	private Calendar endDate;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
	
	// Gettters & Setters
	
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
	
	public int getHistoryId() {
		return historyId;
	}
	
	public void setHistoryId(int historyId) {
		this.historyId = historyId;
	}
	
	// Constructor
	
	public PersonHistory(int historyId, String beginDate, String endDate)
	{
		this.historyId = historyId;
		Calendar calBegin = Calendar.getInstance();
		Calendar calEnd = Calendar.getInstance();		
		
		try {
			calBegin.setTime(sdf.parse(beginDate));
			setBeginDate(calBegin);
		}
		catch(Exception x) {
			setBeginDate(null);
		}
		
		try {
			calEnd.setTime(sdf.parse(endDate));
			setEndDate(calEnd);
		} catch (Exception e) {
			setEndDate(null);
		}
	}

	@Override
	public String toString() {
		
		String endD;
		try {
			endD = sdf.format(endDate.getTime());
		}
		catch (Exception x)
		{
			endD = null;
		}
		
		return "historyId=" + historyId + ", beginDate="
				+ sdf.format(beginDate.getTime()) + ", endDate=" + endD;
	}
    
	
	
}
