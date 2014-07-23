package dataAccess;

import java.sql.SQLException;
import java.text.ParseException;

import applicationLogic.BoxHistory;

/**
 * testclass for boxhistories
 * @author Sebastian
 *
 */
public class BoxHistoryTest {

	public static void main(String[] args) throws SQLException, ParseException {
		// TODO Auto-generated method stub
		DataAccess.getInstance().initDataAccessConnection();
		DataAccess.getInstance().createBasicTables();
		
		DataAccess.getInstance().storeBoxHistoryIntoDB(new BoxHistory(1, "11.01.2010", "11.02.2010", 1, 2));
		DataAccess.getInstance().storeBoxHistoryIntoDB(new BoxHistory(2, "11.01.2010", "11.02.2010", 1, 2));
		DataAccess.getInstance().storeBoxHistoryIntoDB(new BoxHistory(3, "11.01.2010", "11.02.2010", 2, 1));
		DataAccess.getInstance().storeBoxHistoryIntoDB(new BoxHistory(4, "11.01.2010", "11.02.2010", 2, 1));
		
		System.out.println(DataAccess.getInstance().getBoxHistoriesByBoxId(1));
		System.out.println(DataAccess.getInstance().getBoxHistoriesByCollectionId(1));
		System.out.println(DataAccess.getInstance().getBoxHistoriesFromDB());
	}

}
