package dataAccess;

import java.sql.SQLException;

import applicationLogic.ContactPersonHistory;
import applicationLogic.OrgaBoxHistory;
import applicationLogic.OrgaCollectionHistory;


/**
 * testclass for personHistory
 * @author Sebastian
 *
 */
public class PersonHistoryTest {

	public static void main(String[] args) throws SQLException {

		DataAccess.getInstance().initDataAccessConnection();
		DataAccess.getInstance().createBasicTables();

		DataAccess.getInstance().storeOrganisationPersonHistoryInBoxIntoDB(
				new OrgaBoxHistory(1, 1, 1, "06.07.2014",
						"07.07.2014"));
		DataAccess.getInstance().storeOrganisationPersonHistoryInBoxIntoDB(
				new OrgaBoxHistory(2, 1, 1, "06.07.2014",
						"07.07.2014"));
		DataAccess.getInstance().storeOrganisationPersonHistoryInBoxIntoDB(
				new OrgaBoxHistory(7, 1, 2, "06.07.2014",
						"07.07.2014"));
		DataAccess.getInstance().storeOrganisationPersonHistoryInBoxIntoDB(
				new OrgaBoxHistory(8, 1, 3, "06.07.2014",
						"07.07.2014"));
		DataAccess.getInstance()
		.storeOrganisationPersonHistoryInCollectionIntoDB(
				new OrgaCollectionHistory(3, 1, 1, "06.07.2014",
						"07.07.2014"));
		DataAccess.getInstance()
		.storeOrganisationPersonHistoryInCollectionIntoDB(
				new OrgaCollectionHistory(4, 1, 1, "06.07.2014",
						"07.07.2014"));
		DataAccess.getInstance()
		.storeOrganisationPersonHistoryInCollectionIntoDB(
				new OrgaCollectionHistory(9, 2, 1, "06.07.2014",
						"07.07.2014"));
		DataAccess.getInstance()
		.storeOrganisationPersonHistoryInCollectionIntoDB(
				new OrgaCollectionHistory(10, 3, 1, "06.07.2014",
						"07.07.2014"));
		DataAccess.getInstance().storeContactPersonHistoryInCollectionIntoDB(
				new ContactPersonHistory(5, 4, 1, "06.07.2014", "07.07.2014"));
		DataAccess.getInstance().storeContactPersonHistoryInCollectionIntoDB(
				new ContactPersonHistory(6, 4, 1, "06.07.2014", "07.07.2014"));

		System.out.println(DataAccess.getInstance()
				.getOrganisationPersonHistoryInBoxByBoxId(1));


		System.out.println(DataAccess.getInstance()
				.getOrganisationPersonHistoryInCollectionByCollectionId(1));

		
		System.out.println(DataAccess.getInstance().getContactPersonHistoryInCollectionByCollectionId(1));
		
		System.out.println(DataAccess.getInstance()
				.getOrganisationPersonHistoryInCollectionByCollectionId(1));	
		DataAccess.getInstance().editOrganisationPersonHistoryInCollectionInDB(new OrgaCollectionHistory(3, 3, 1, "06.07.2014",
								"08.07.2014"));
		System.out.println(DataAccess.getInstance()
				.getOrganisationPersonHistoryInCollectionByCollectionId(1));
		
		
		System.out.println(DataAccess.getInstance().getContactPersonHistoryInCollectionByCollectionId(1));
		DataAccess.getInstance().editContactPersonHistoryInCollectionInDB(new ContactPersonHistory(5, 2, 1, "06.07.2014", "08.07.2014"));
		System.out.println(DataAccess.getInstance().getContactPersonHistoryInCollectionByCollectionId(1));
		
		System.out.println(DataAccess.getInstance()
				.getOrganisationPersonHistoryInBoxByBoxId(1));
		DataAccess.getInstance().editOrganisationPersonHistoryInBoxInDB(new OrgaBoxHistory(1, 5, 1, "06.07.2014",
				"07.08.2014"));
		System.out.println(DataAccess.getInstance()
				.getOrganisationPersonHistoryInBoxByBoxId(1));
		
		System.out.println(DataAccess.getInstance().getAllOrganisationPersonHistoryInBox());
		System.out.println(DataAccess.getInstance().getAllOrganisationPersonHistoryInCollection());
		
		System.out.println(DataAccess.getInstance().getAllPersonHistoriesFromDB());
	}

}
