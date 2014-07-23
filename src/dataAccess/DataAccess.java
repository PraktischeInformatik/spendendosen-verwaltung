package dataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import applicationLogic.Address;
import applicationLogic.BoxHistory;
import applicationLogic.ClearingDonationBox;
import applicationLogic.ContactPerson;
import applicationLogic.ContactPersonHistory;
import applicationLogic.DonationBox;
import applicationLogic.DonationCollection;
import applicationLogic.FixedDonationCollection;
import applicationLogic.Login;
import applicationLogic.LoginInUseException;
import applicationLogic.OrgaBoxHistory;
import applicationLogic.OrgaCollectionHistory;
import applicationLogic.OrganisationPerson;
import applicationLogic.Person;
import applicationLogic.PersonHistory;
import applicationLogic.StreetDonationCollection;

/**
 * this class manage the database-connection, sql-statemants and creates objects from database
 * 
 * @author Sebastian
 * 
 */

public class DataAccess {

	private static final DataAccess dataAccessController = new DataAccess();
	private static Connection connection;
	private static final String DB_PATH = System.getProperty("user.home") + "/"
			+ "S-Kollekt.db";

	private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

	
	// prüfen ob jdbc treiber richtig geladen wird
	static {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.err.println("Fehler beim Laden des JDBC-Treibers");
			e.printStackTrace();
		}
	}

	/**
	 * constructor of DataAccess-instance
	 * @author Sebastian
	 */
	// constructor
	private DataAccess() {

	}

	/**
	 * returns the final DataAccess-Instance
	 * @return the DataAccess-Instance
	 * @author Sebastian
	 */
	public static DataAccess getInstance() {
		return dataAccessController;
	}

	/**
	 * creates connection to the database-file and if the application closed, the connection
	 * to the database-file will be closed too. 
	 */
	public void initDataAccessConnection() {
		try {
			if (connection != null)
				return;
			System.out.println("Creating Connection to Database...");
			connection = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH);
			if (!connection.isClosed())
				System.out.println("...Connection established");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		// wenn application geschlossen wird, wird datenbank-connection auch
		// geschlossen
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				try {
					if (!connection.isClosed() && connection != null) {
						connection.close();
						if (connection.isClosed())
							System.out.println("Connection to Database closed");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * create basic tables into the database if they don't exist
	 * 
	 * @author Sebastian
	 */
	public void createBasicTables() throws SQLException {
		Statement statement = connection.createStatement();
		// Table Login
		statement.executeUpdate("CREATE TABLE IF NOT EXISTS Login ("
				+ "login_name VARCHAR(45) NOT NULL, "
				+ "password VARCHAR(45) NULL, "
				+ "accesslevel INTEGER UNSIGNED NULL, "
				+ "is_deleted BOOL NULL, " + "PRIMARY KEY(login_name));");
		// Table Global_Ids
		statement.executeUpdate("CREATE TABLE IF NOT EXISTS Global_Ids ("
				+ "id INTEGER UNSIGNED NOT NULL, "
				+ "next_person_id INTEGER UNSIGNED NULL, "
				+ "next_donation_collection_id INTEGER UNSIGNED NULL, "
				+ "next_box_id INTEGER UNSIGNED NULL, "
				+ "next_clearing_id INTEGER UNSIGNED NULL, "
				+ "next_person_history_id INTEGER UNSIGNED NULL, "
				+ "next_box_history_id INTEGER UNSIGNED NULL, "
				+ "PRIMARY KEY(id));");
		// Table Person
		statement
				.executeUpdate("CREATE TABLE IF NOT EXISTS Person ("
						+ "person_id INTEGER UNSIGNED NOT NULL, "
						+ "forename VARCHAR(45) NULL, "
						+ "lastname VARCHAR(45) NULL, "
						+ "email VARCHAR(255) NULL, "
						+ "phone_nr VARCHAR(20) NULL, "
						+ "mobile_nr VARCHAR(20) NULL, "
						+ "is_active BOOL NULL, "
						+ "person_comment TEXT NULL, "
						+ "person_type VARCHAR(20) NULL, "
						+ "PRIMARY KEY(person_id));");
		// Table DonationCollection
		statement
				.executeUpdate("CREATE TABLE IF NOT EXISTS DonationCollection ("
						+ "collection_id INTEGER UNSIGNED NOT NULL, "
						+ "collection_sum FLOAT NULL, "
						+ "begin_periode VARCHAR(20) NULL, "
						+ "end_periode VARCHAR(20) NULL, "
						+ "collection_comment TEXT NULL, "
						+ "collection_type VARCHAR(20) NULL, "
						+ "is_completed BOOL NULL, "
						// + "is_deleted BOOL NULL, " // TODO muss noch
						// aktuallisiert werden

						+ "contact_person_id INTEGER UNSIGNED NULL, "
						+ "organisation_person_id INTEGER UNSIGNED NULL, "
						+ "PRIMARY KEY(collection_id), "
						+ "FOREIGN KEY(contact_person_id) REFERENCES Persons(person_id), "
						+ "FOREIGN KEY(organisation_person_id) REFERENCES Persons(person_id));");
		// Table PersonAddress
		statement.executeUpdate("CREATE TABLE IF NOT EXISTS PersonAddress ("
				+ "street_name VARCHAR(45) NULL, "
				+ "house_nr VARCHAR(20) NULL, " + "zip INTEGER UNSIGNED NULL, "
				+ "location_name VARCHAR(45) NULL, "
				+ "person_id INTEGER UNSIGNED NOT NULL, "
				+ "FOREIGN KEY(person_id) REFERENCES Persons(person_id));");
		// Table LocationAddress
		statement
				.executeUpdate("CREATE TABLE IF NOT EXISTS LocationAddress ("
						+ "street_name VARCHAR(45) NULL, "
						+ "house_nr VARCHAR(20) NULL, "
						+ "zip INTEGER UNSIGNED NULL, "
						+ "location_name VARCHAR(45) NULL, "
						+ "collection_id INTEGER UNSIGNED NOT NULL, "
						+ "FOREIGN KEY(collection_id) REFERENCES DonationCollection(collection_id));");
		// Table DonationBox
		statement
				.executeUpdate("CREATE TABLE IF NOT EXISTS DonationBox ("
						+ "box_id INTEGER UNSIGNED NOT NULL, "
						+ "box_sum FLOAT NULL, "
						+ "in_use BOOL NULL, "
						+ "box_comment TEXT NULL, "
						+ "is_deleted BOOL NULL, "
						+ "collection_id INTEGER UNSIGNED NULL, "
						+ "person_id INTEGER UNSIGNED NULL, "
						+ "PRIMARY KEY(box_id), "
						+ "FOREIGN KEY(collection_id) REFERENCES DonationCollection(collection_id),"
						+ "FOREIGN KEY(person_id) REFERENCES Person(person_id));");
		// Table ClearingDonationBox
		statement
				.executeUpdate("CREATE TABLE IF NOT EXISTS ClearingDonationBox ("
						+ "clearing_id INTEGER UNSIGNED NOT NULL, "
						+ "clearing_sum FLOAT NOT NULL, "
						+ "clearing_date VARCHAR(20) NULL, "
						+ "collection_id INTEGER UNSIGNED NULL, "
						+ "box_id INTEGER UNSIGNED NULL, "
						+ "PRIMARY KEY(clearing_id), "
						+ "FOREIGN KEY(collection_id) REFERENCES DonationCollection(collection_id), "
						+ "FOREIGN KEY(box_id) REFERENCES DonationBox(box_id));");

		// Table ContactPersonHistoryInCollection
		statement
				.executeUpdate("CREATE TABLE IF NOT EXISTS ContactPersonHistoryInCollection ("
						+ "coll_contact_history_id INTEGER UNSIGNED NOT NULL, "
						+ "collection_id INTEGER UNSIGNED NULL, "
						+ "person_id INTEGER UNSIGNED NULL, "
						+ "begin_date VARCHAR(20) NULL, "
						+ "end_date VARCHAR(20) NULL, "
						+ "PRIMARY KEY(coll_contact_history_id), "
						+ "FOREIGN KEY(collection_id) REFERENCES DonationCollection(collection_id), "
						+ "FOREIGN KEY(person_id) REFERENCES Person(person_id)"
						+ ");");
		// Table OrganisationPersonHistoryInCollection
		statement
				.executeUpdate("CREATE TABLE IF NOT EXISTS OrganisationPersonHistoryInCollection ("
						+ "coll_orga_history_id INTEGER UNSIGNED NOT NULL, "
						+ "collection_id INTEGER UNSIGNED NULL, "
						+ "person_id INTEGER UNSIGNED NULL, "
						+ "begin_date VARCHAR(20) NULL, "
						+ "end_date VARCHAR(20) NULL, "
						+ "PRIMARY KEY(coll_orga_history_id), "
						+ "FOREIGN KEY(collection_id) REFERENCES DonationCollection(collection_id), "
						+ "FOREIGN KEY(person_id) REFERENCES Person(person_id)"
						+ ");");
		// Table OrganisationPersonHistoryInBox
		statement
				.executeUpdate("CREATE TABLE IF NOT EXISTS OrganisationPersonHistoryInBox ("
						+ "box_orga_history_id INTEGER UNSIGNED NOT NULL, "
						+ "box_id INTEGER UNSIGNED NULL, "
						+ "person_id INTEGER UNSIGNED NULL, "
						+ "begin_date VARCHAR(20) NULL, "
						+ "end_date VARCHAR(20) NULL, "
						+ "PRIMARY KEY(box_orga_history_id), "
						+ "FOREIGN KEY(box_id) REFERENCES DonationBox(box_id), "
						+ "FOREIGN KEY(person_id) REFERENCES Person(person_id)"
						+ ");");
		// Table BoxHistory
		statement
				.executeUpdate("CREATE TABLE IF NOT EXISTS BoxHistory ("
						+ "boxhistory_id INTEGER UNSIGNED NOT NULL, "
						+ "begin_date VARCHAR(20) NULL, "
						+ "end_date VARCHAR(20) NULL, "
						+ "collection_id INTEGER UNSIGNED NULL, "
						+ "box_id INTEGER UNSIGNED NULL, "
						+ "PRIMARY KEY(boxhistory_id), "
						+ "FOREIGN KEY(collection_id) REFERENCES DonationCollection(collection_id), "
						+ "FOREIGN KEY(box_id) REFERENCES DonationBox(box_id)"
						+ ");");

		// sets basic entry into Table Global_Ids
		// if no entry exists in table Global_Ids add basic entry
		if (statement.executeQuery("SELECT count(id) FROM Global_Ids;").getInt(
				"count(id)") == 0) {
			statement
					.executeUpdate("INSERT INTO Global_Ids "
							+ "(id, next_person_id, next_donation_collection_id, next_box_id, next_clearing_id, next_person_history_id, next_box_history_id) "
							+ "VALUES (1, 1, 0, 1, 1, 0, 1);");
		}

		// added Person 1000000 for the representation of the noPerson
		if (statement.executeQuery("SELECT count(person_id) FROM Person;")
				.getInt("count(person_id)") == 0) {

			statement
					.executeUpdate("INSERT INTO Person "
							+ "(person_id, forename, lastname, email, phone_nr, mobile_nr, is_active,"
							+ " person_comment, person_type) "
							+ "VALUES (1000000, '', '', '', '', '', 0, '', 'OrganisationPerson');");

			statement.executeUpdate("INSERT INTO PersonAddress "
					+ "(street_name, house_nr, zip, location_name, person_id)"
					+ "VALUES ('', '', '', '', 1000000);");
		}

		// added basic logins into database 1=admin 2=member 3=gast
		if (statement.executeQuery("SELECT count(login_name) FROM Login;")
				.getInt("count(login_name)") == 0) {
			statement
					.executeUpdate("INSERT INTO Login "
							+ "(login_name, password, accesslevel, is_deleted) VALUES ('admin','admin',1,0);");
//			statement
//					.executeUpdate("INSERT INTO Login "
//							+ "(login_name, password, accesslevel, is_deleted) VALUES ('member','member',2,0);");
			statement
					.executeUpdate("INSERT INTO Login "
							+ "(login_name, password, accesslevel, is_deleted) VALUES ('gast','gast',3,0);");

		}

	}

	/**
	 * stores the global id of the next Person into the database
	 * 
	 * @param nextPersonId
	 *            the global id
	 * @throws SQLException
	 * @author Sebastian
	 */
	public void storeNextPersonIdIntoDB(int nextPersonId) throws SQLException {
		Statement statement = connection.createStatement();
		statement.executeUpdate("UPDATE Global_Ids " + "SET next_person_id = "
				+ nextPersonId + " " + "WHERE id = 1;");
	}

	/**
	 * stores the global id of the next DonationCollection into the database
	 * 
	 * @param nextDonationCollectionId
	 *            the global id
	 * @throws SQLException
	 * @author Sebastian
	 */
	public void storeNextDonationCollectionIdIntoDB(int nextDonationCollectionId)
			throws SQLException {
		Statement statement = connection.createStatement();
		statement.executeUpdate("UPDATE Global_Ids "
				+ "SET next_donation_collection_id = "
				+ nextDonationCollectionId + " " + "WHERE id = 1;");
	}

	/**
	 * stores the global id of the next DonationBox into the database
	 * 
	 * @param nextBoxId
	 *            the global id
	 * @throws SQLException
	 * @author Sebastian
	 */
	public void storeNextBoxIdIntoDB(int nextBoxId) throws SQLException {
		Statement statement = connection.createStatement();
		statement.executeUpdate("UPDATE Global_Ids " + "SET next_box_id ="
				+ nextBoxId + " " + "WHERE id = 1;");
	}

	/**
	 * stores the global id of the next ClearingDonationBox into database
	 * 
	 * @param nextClearingId
	 *            the global id
	 * @throws SQLException
	 * @author Sebastian
	 */
	public void storeNextClearingIdIntoDB(int nextClearingId)
			throws SQLException {
		Statement statement = connection.createStatement();
		statement.executeUpdate("UPDATE Global_Ids " + "SET next_clearing_id ="
				+ nextClearingId + " " + "WHERE id = 1;");
	}

	/**
	 * stores the global id of the next PersonHistory into database
	 * 
	 * @param nextPersonHistoryId
	 *            the global personHistory id
	 * @throws SQLException
	 * @author Sebastian
	 */
	public void storeNextPersonHistoryIdIntoDB(int nextPersonHistoryId)
			throws SQLException {
		Statement statement = connection.createStatement();
		statement.executeUpdate("UPDATE Global_Ids "
				+ "SET next_person_history_id =" + nextPersonHistoryId + " "
				+ "WHERE id = 1;");
	}

	/**
	 * stores the global id of the next BoxHistory into database
	 * 
	 * @param nextBoxHistoryId
	 *            the global boxHistory id
	 * @throws SQLException
	 * @author Sebastian
	 */
	public void storeNextBoxHistoryIdIntoDB(int nextBoxHistoryId)
			throws SQLException {
		Statement statement = connection.createStatement();
		statement.executeUpdate("UPDATE Global_Ids "
				+ "SET next_box_history_id =" + nextBoxHistoryId + " "
				+ "WHERE id = 1;");
	}

	/**
	 * returns the global id of Person who is stored in database
	 * 
	 * @return global id of Person
	 * @throws SQLException
	 * @author Sebastian
	 */
	public int getNextPersonIdFromDB() throws SQLException {
		int nextPersonId = 0;
		Statement statment = connection.createStatement();
		ResultSet resultSet = statment.executeQuery("SELECT next_person_id "
				+ "FROM Global_Ids " + "WHERE id = 1;");

		while (resultSet.next()) {
			nextPersonId = resultSet.getInt("next_person_id");

		}

		return nextPersonId;
	}

	/**
	 * returns the global id of DonationCollection, who is stored in database
	 * 
	 * @return global id of DonationCollection
	 * @throws SQLException
	 * @author Sebastian
	 */
	public int getNextDonationCollectionIdFromDB() throws SQLException {
		int nextDonationCollectionId = 0;
		Statement statment = connection.createStatement();
		ResultSet resultSet = statment
				.executeQuery("SELECT next_donation_collection_id "
						+ "FROM Global_Ids " + "WHERE id = 1;");

		while (resultSet.next()) {
			nextDonationCollectionId = resultSet
					.getInt("next_donation_collection_id");
		}

		return nextDonationCollectionId;
	}

	/**
	 * returns the global id of DonationBox, who is stored in database
	 * 
	 * @return global id of DonationBox
	 * @throws SQLException
	 * @author Sebastian
	 */
	public int getNextBoxIdFromDB() throws SQLException {
		int nextBoxId = 0;
		Statement statment = connection.createStatement();
		ResultSet resultSet = statment.executeQuery("SELECT next_box_id "
				+ "FROM Global_Ids " + "WHERE id = 1;");

		while (resultSet.next()) {
			nextBoxId = resultSet.getInt("next_box_id");
		}
		// resultSet.close();

		return nextBoxId;
	}

	/**
	 * returns the global id of ClearingDonationBox, who is stored in database
	 * 
	 * @return global id of Clearing DonationBox
	 * @throws SQLException
	 * @author Sebastian
	 */
	public int getNextClearingIdFromDB() throws SQLException {
		int nextClearingId = 0;
		Statement statment = connection.createStatement();
		ResultSet resultSet = statment.executeQuery("SELECT next_clearing_id "
				+ "FROM Global_Ids " + "WHERE id = 1;");

		while (resultSet.next()) {
			nextClearingId = resultSet.getInt("next_clearing_id");
		}

		return nextClearingId;
	}

	/**
	 * returns the global id of PersonHistory, who is stored in database
	 * 
	 * @return global id of PersonHistory
	 * @throws SQLException
	 * @author Sebastian
	 */
	public int getNextPersonHistoryIdFromDB() throws SQLException {
		int nextPersonHistoryId = 0;
		Statement statment = connection.createStatement();
		ResultSet resultSet = statment
				.executeQuery("SELECT next_person_history_id "
						+ "FROM Global_Ids " + "WHERE id = 1;");

		while (resultSet.next()) {
			nextPersonHistoryId = resultSet.getInt("next_person_history_id");
		}

		return nextPersonHistoryId;
	}

	/**
	 * returns the global id of BoxHistory, who is stored in database
	 * 
	 * @return global id of BoxHistory
	 * @throws SQLException
	 * @author Sebastian
	 */
	public int getNextBoxHistoryIdFromDB() throws SQLException {
		int nextBoxHistoryId = 0;
		Statement statment = connection.createStatement();
		ResultSet resultSet = statment
				.executeQuery("SELECT next_box_history_id "
						+ "FROM Global_Ids " + "WHERE id = 1;");

		while (resultSet.next()) {
			nextBoxHistoryId = resultSet.getInt("next_box_history_id");
		}

		
		return nextBoxHistoryId;
		
	}

	/**
	 * returns an ArrayList of all DonationBox-objects, who are stored in
	 * database
	 * 
	 * @return ArrayList<DonationBox> of all DonationBox-Objects
	 * @throws SQLException
	 * @author Sebastian
	 * @throws ParseException
	 */
	public ArrayList<DonationBox> getDonationBoxesFromDB() throws SQLException,
			ParseException {

		ArrayList<DonationBox> allDonationBoxes = new ArrayList<DonationBox>();
		Statement statment = connection.createStatement();
		ResultSet resultSet = statment
				.executeQuery("SELECT box_id, in_use, box_sum, box_comment, collection_id, is_deleted "
						+ "FROM DonationBox;");

		while (resultSet.next()) {
			DonationBox db;
			OrganisationPerson op = null;
			ArrayList<ClearingDonationBox> allClearingDonationBoxesByBoxId = new ArrayList<ClearingDonationBox>();
			int boxId = resultSet.getInt("box_id");
			boolean inUse = resultSet.getBoolean("in_use");
			float boxSum = resultSet.getFloat("box_sum");
			int collectionId = resultSet.getInt("collection_id");
			String boxComment = resultSet.getString("box_comment");
			boolean isDeleted = resultSet.getBoolean("is_deleted");

			// for the clearingDonationBoxes in DonationBox
			Statement statmentB = connection.createStatement(); // added
			ResultSet resultSetB = statmentB
					.executeQuery("SELECT clearing_id, clearing_sum, clearing_date "
							+ "FROM ClearingDonationBox "
							+ "WHERE box_id = "
							+ boxId + ";");

			while (resultSetB.next()) {

				int clearingId = resultSetB.getInt("clearing_id");
				Float clearingSum = resultSetB.getFloat("clearing_sum");
				String clearingDate = resultSetB.getString("clearing_date");

				// Calendar clearingDate = Calendar.getInstance();
				// clearingDate.setTime(resultSetB.getDate("clearing_date"));
				// Date clearingDate = resultSetB.getDate("clearing_date");
				ClearingDonationBox cdb = new ClearingDonationBox(clearingId,
						clearingDate, clearingSum);
				allClearingDonationBoxesByBoxId.add(cdb);
			}

			// for the OrganisationPerson in DonationBox
			Statement statmentC = connection.createStatement(); // added
			ResultSet resultSetC = statmentC
					.executeQuery("SELECT person_id, forename, lastname, email, phone_nr, "
							+ "mobile_nr, is_active, person_comment, person_type, street_name, house_nr, zip, location_name "
							+ "FROM DonationBox NATURAL JOIN Person NATURAL JOIN PersonAddress "
							+ "WHERE box_id = " + boxId + ";");
			while (resultSetC.next()) {
				int personId = resultSetC.getInt("person_id");
				String forename = resultSetC.getString("forename");
				String lastname = resultSetC.getString("lastname");
				String email = resultSetC.getString("email");
				String phoneNr = resultSetC.getString("phone_nr");
				String mobileNr = resultSetC.getString("mobile_nr");
				boolean isActive = resultSetC.getBoolean("is_active");
				String personComment = resultSetC.getString("person_comment");
				// String personTyp = resultSetC.getString("person_type");
				String streetName = resultSetC.getString("street_name");
				String houseNr = resultSetC.getString("house_nr");
				int zip = resultSetC.getInt("zip");
				String locationName = resultSetC.getString("location_name");

				op = new OrganisationPerson(personId, forename, lastname,
						new Address(streetName, houseNr, zip, locationName),
						email, phoneNr, mobileNr, personComment, isActive);

			}

			db = new DonationBox(boxId, boxSum, inUse, boxComment, op,
					allClearingDonationBoxesByBoxId, collectionId, isDeleted);
			allDonationBoxes.add(db);
		}

		return allDonationBoxes;
	}

	/**
	 * stores the DonationBox-Object into database, but without the connections
	 * to responsible person and ClearingDonationBoxes
	 * 
	 * @param db
	 *            the DonationBox-Object
	 * @throws SQLException
	 * @author Sebastian
	 */
	public void storeDonationBoxIntoDB(DonationBox db) throws SQLException {

		String inUseByString = null;

		if (db.isInUse() == true) {
			inUseByString = "1";
		} else if (db.isInUse() == false) {
			inUseByString = "0";
		}

		String isDeletedbyString = null;

		if (db.getIsDeleted() == true) {
			isDeletedbyString = "1";
		} else if (db.getIsDeleted() == false) {
			isDeletedbyString = "0";
		}

		Statement statment = connection.createStatement();
		statment.execute("INSERT INTO DonationBox "
				+ "(box_id, box_sum, in_use, box_comment, collection_id, is_deleted) "
				+ "VALUES (" + db.getBoxId() + ", " + db.getSum() + ", "
				+ inUseByString + ", '" + db.getComment() + "', "
				+ db.getCollectionId() + ", '" + isDeletedbyString + "');");
	}

	/** to save all DonationBoxes */

	/*
	 * public void storeDonationBoxesIntoDB(ArrayList<DonationBox>
	 * allDonationBoxes) throws SQLException {
	 * 
	 * // Number of DonationBoxes before we Start our Programm . int
	 * temp1=DataAccess.getInstance().getNextBoxIdFromDB();
	 * 
	 * 
	 * // We have no delete funktion , thats why Number of Boxes always increase
	 * , // so it can be equal or greater from the previous Value. for (int
	 * i=0;i<allDonationBoxes.size();i++) { if (i<temp1)
	 * {DataAccess.getInstance().editDonationBoxInDB(allDonationBoxes.get(i));}
	 * else
	 * {DataAccess.getInstance().storeDonationBoxIntoDB(allDonationBoxes.get
	 * (i));} }
	 * 
	 * }
	 */

	/**
	 * returns the Person-object with the searchPersonId from database
	 * 
	 * @param searchPersonId
	 *            the Person-Id you are searching for
	 * @return Person object
	 * @throws SQLException
	 * @author Sebastian
	 */
	public Person getPersonByIdFromDB(int searchPersonId) throws SQLException {
		Person p = null;

		Statement statment = connection.createStatement();
		ResultSet resultSet = statment
				.executeQuery("SELECT person_id, forename, lastname, email, phone_nr, "
						+ "mobile_nr, is_active, person_comment, person_type, street_name, house_nr, zip, location_name "
						+ "FROM Person NATURAL JOIN PersonAddress "
						+ "WHERE person_id = " + searchPersonId + ";");

		while (resultSet.next()) {
			int personId = resultSet.getInt("person_id");
			String forename = resultSet.getString("forename");
			String lastname = resultSet.getString("lastname");
			String email = resultSet.getString("email");
			String phoneNr = resultSet.getString("phone_nr");
			String mobileNr = resultSet.getString("mobile_nr");
			boolean isActive = resultSet.getBoolean("is_active");
			String personComment = resultSet.getString("person_comment");
			String personTyp = resultSet.getString("person_type");
			String streetName = resultSet.getString("street_name");
			String houseNr = resultSet.getString("house_nr");
			int zip = resultSet.getInt("zip");
			String locationName = resultSet.getString("location_name");

			if (personTyp.equals("OrganisationPerson")) {
				p = new OrganisationPerson(personId, forename, lastname,
						new Address(streetName, houseNr, zip, locationName),
						email, phoneNr, mobileNr, personComment, isActive);

			} else if (personTyp.equals("ContactPerson")) {

				p = new ContactPerson(personId, forename, lastname,
						new Address(streetName, houseNr, zip, locationName),
						email, phoneNr, mobileNr, personComment, isActive);

			}
		}

		return p;
	}

	/**
	 * returns an ArrayList of all Person-Objects, who are stored in database
	 * 
	 * @return ArrayList<Person> of all Person-Objects
	 * @throws SQLException
	 * @author Sebastian
	 */
	public ArrayList<Person> getPersonsFromDB() throws SQLException {
		ArrayList<Person> allPersons = new ArrayList<Person>();

		Statement statment = connection.createStatement();
		ResultSet resultSet = statment
				.executeQuery("SELECT person_id, forename, lastname, email, phone_nr, "
						+ "mobile_nr, is_active, person_comment, person_type, street_name, house_nr, zip, location_name "
						+ "FROM Person NATURAL JOIN PersonAddress" + ";");

		while (resultSet.next()) {
			int personId = resultSet.getInt("person_id");
			String forename = resultSet.getString("forename");
			String lastname = resultSet.getString("lastname");
			String email = resultSet.getString("email");
			String phoneNr = resultSet.getString("phone_nr");
			String mobileNr = resultSet.getString("mobile_nr");
			boolean isActive = resultSet.getBoolean("is_active");
			String personComment = resultSet.getString("person_comment");
			String personTyp = resultSet.getString("person_type");
			String streetName = resultSet.getString("street_name");
			String houseNr = resultSet.getString("house_nr");
			int zip = resultSet.getInt("zip");
			String locationName = resultSet.getString("location_name");

			if (personTyp.equals("OrganisationPerson")) {
				OrganisationPerson op = new OrganisationPerson(personId,
						forename, lastname, new Address(streetName, houseNr,
								zip, locationName), email, phoneNr, mobileNr,
						personComment, isActive);
				allPersons.add(op);
			} else if (personTyp.equals("ContactPerson")) {

				ContactPerson cp = new ContactPerson(personId, forename,
						lastname, new Address(streetName, houseNr, zip,
								locationName), email, phoneNr, mobileNr,
						personComment, isActive);
				allPersons.add(cp);
			}

		}

		return allPersons;
	}

	public ArrayList<ClearingDonationBox> getClearingDonationBoxesFromDB()
			throws SQLException, ParseException {
		ArrayList<ClearingDonationBox> allClearingDonationBoxes = new ArrayList<ClearingDonationBox>();

		Statement statment = connection.createStatement();
		ResultSet resultSet = statment
				.executeQuery("SELECT clearing_id, clearing_sum, clearing_date "
						+ "FROM ClearingDonationBox;");

		while (resultSet.next()) {
			int clearingId = resultSet.getInt("clearing_id");
			float clearingSum = resultSet.getFloat("clearing_sum");
			String clearingDate = resultSet.getString("clearing_date");

			// Date clearingDate = resultSet.getDate("clearing_date");
			ClearingDonationBox cdb = new ClearingDonationBox(clearingId,
					clearingDate, clearingSum);
			allClearingDonationBoxes.add(cdb);
		}

		return allClearingDonationBoxes;
	}

	/**
	 * stores the OrganisationPerson-Object into database
	 * 
	 * @param op
	 *            the OrganisationPerson-Object
	 * @throws SQLException
	 * @author Sebastian
	 */

	public void storeOrganisationPersonIntoDB(OrganisationPerson op)
			throws SQLException {
		String isActiveByString = null;
		if (op.getIsActiveMember() == true) {
			isActiveByString = "1";
		} else if (op.getIsActiveMember() == false) {
			isActiveByString = "0";
		}

		Statement statment = connection.createStatement();
		statment.execute("INSERT INTO Person "
				+ "(person_id, forename, lastname, email, phone_nr, mobile_nr, is_active, person_comment, person_type) "
				+ "VALUES (" + op.getPersonId() + ", '" + op.getForename()
				+ "', '" + op.getLastName() + "', '" + op.getEmail() + "', '"
				+ op.getTelephoneNumber() + "', '" + op.getMobilNumber()
				+ "', " + isActiveByString + ", '" + op.getComment() + "', "
				+ "'OrganisationPerson'" + ");");

		Statement statmentB = connection.createStatement();
		statmentB.execute("INSERT INTO PersonAddress "
				+ "(street_name, house_nr, zip, location_name, person_id) "
				+ "VALUES ('" + op.getAddress().getStreetName() + "', '"
				+ op.getAddress().getHauseNumber() + "', "
				+ op.getAddress().getZip() + ", '"
				+ op.getAddress().getLocationName() + "', " + op.getPersonId()
				+ ");");
	}

	/**
	 * stores the ContactPerson-Object into database
	 * 
	 * @param cp
	 *            the ContactPerson-Object
	 * @throws SQLException
	 * @author Sebastian
	 */

	public void storeContactPersonIntoDB(ContactPerson cp) throws SQLException {

		String isActiveByString = null;
		if (cp.getIsActiveMember() == true) {
			isActiveByString = "1";
		} else if (cp.getIsActiveMember() == false) {
			isActiveByString = "0";
		}

		Statement statment = connection.createStatement();
		statment.execute("INSERT INTO Person "
				+ "(person_id, forename, lastname, email, phone_nr, mobile_nr, is_active, person_comment, person_type) "
				+ "VALUES (" + cp.getPersonId() + ", '" + cp.getForename()
				+ "', '" + cp.getLastName() + "', '" + cp.getEmail() + "', '"
				+ cp.getTelephoneNumber() + "', '" + cp.getMobilNumber()
				+ "', " + isActiveByString + ", '" + cp.getComment() + "', "
				+ "'ContactPerson'" + ");");

		Statement statmentB = connection.createStatement();
		statmentB.execute("INSERT INTO PersonAddress "
				+ "(street_name, house_nr, zip, location_name, person_id) "
				+ "VALUES ('" + cp.getAddress().getStreetName() + "', '"
				+ cp.getAddress().getHauseNumber() + "', "
				+ cp.getAddress().getZip() + ", '"
				+ cp.getAddress().getLocationName() + "', " + cp.getPersonId()
				+ ");");

	}

	/*
	 * public void storePersonsIntoDB(ArrayList<Person> allPersons) throws
	 * SQLException { // Number of Persons before last Store int
	 * temp1=DataAccess.getInstance().getNextPersonIdFromDB();
	 * 
	 * //Number of Persons can only increase , because we have no delete //
	 * Thats why allPersons.size() could be only equal or greater than temp1
	 * 
	 * for (int i=0;i<allPersons.size();i++) { if (i<temp1)
	 * {DataAccess.getInstance().editPersonInDB(allPersons.get(i));} else { if
	 * (allPersons
	 * .get(i).getClass().getSimpleName().equals("OrganisationPerson") )
	 * DataAccess
	 * .getInstance().storeOrganisationPersonIntoDB((OrganisationPerson
	 * )allPersons.get(i)) ;
	 * 
	 * if (allPersons.get(i).getClass().getSimpleName().equals("ContactPerson")
	 * )
	 * DataAccess.getInstance().storeContactPersonIntoDB((ContactPerson)allPersons
	 * .get(i)) ; } }
	 * 
	 * }
	 */
	/**
	 * return ArrayList of all DonationCollections, who stored in database
	 * 
	 * @return ArrayList<DonationCollection> the arrayList of all
	 *         DonationCollections
	 * @throws SQLException
	 * @author Sebastian
	 * @throws ParseException
	 */
	public ArrayList<DonationCollection> getDonationCollectionsFromDB()
			throws SQLException, ParseException {

		ArrayList<DonationCollection> allDonationCollections = new ArrayList<DonationCollection>();

		Statement statment = connection.createStatement();
		ResultSet resultSet = statment
				.executeQuery("SELECT collection_id, collection_sum, begin_periode, end_periode,"
						+ "collection_comment, collection_type, is_completed, contact_person_id, organisation_person_id "
						+ "FROM DonationCollection;");

		while (resultSet.next()) {

			OrganisationPerson op = null;
			ContactPerson cp = null;
			ArrayList<DonationBox> donationBoxesInCollection = new ArrayList<>();

			int collectionId = resultSet.getInt("collection_id");
			float collectionSum = resultSet.getFloat("collection_sum");

			String beginPeriode = resultSet.getString("begin_periode");

			String endPeriode = resultSet.getString("end_periode");

			String collectionComment = resultSet
					.getString("collection_comment");
			String collectionType = resultSet.getString("collection_type");
			boolean isCompleted = resultSet.getBoolean("is_completed");
			int contactPersonId = resultSet.getInt("contact_person_id");
			int organisationPersonId = resultSet
					.getInt("organisation_person_id");

			// for the organisationPersons in DonationCollection
			Person orgaPerson = getPersonByIdFromDB(organisationPersonId);
			op = (OrganisationPerson) orgaPerson;

			// for the contactPersons in DonationCollection
			Person contactPerson = getPersonByIdFromDB(contactPersonId);
			cp = (ContactPerson) contactPerson;

			// for DonationBoxes in DonationCollection
			Statement statmentB = connection.createStatement(); // added
			ResultSet donationBoxResultSet = statmentB
					.executeQuery("SELECT box_id, box_sum, in_use, box_comment, is_deleted FROM "
							+ "DonationBox NATURAL JOIN DonationCollection "
							+ "WHERE collection_id = " + collectionId + ";");

			while (donationBoxResultSet.next()) {
				DonationBox db = null;
				OrganisationPerson opIndb = null;
				ArrayList<ClearingDonationBox> allClearingDonationBoxesByBoxId = new ArrayList<ClearingDonationBox>();
				int boxId = donationBoxResultSet.getInt("box_id");
				boolean inUse = donationBoxResultSet.getBoolean("in_use");
				float boxSum = donationBoxResultSet.getFloat("box_sum");
				String boxComment = donationBoxResultSet
						.getString("box_comment");
				boolean isDeleted = donationBoxResultSet
						.getBoolean("is_deleted");

				allClearingDonationBoxesByBoxId = getClearingDonationBoxesByBoxIdInCollectionId(
						boxId, collectionId);

				// for the OrganisationPerson in DonationBox
				Statement statmentC = connection.createStatement(); // added
				ResultSet resultSetC = statmentC
						.executeQuery("SELECT person_id , forename, lastname, email, phone_nr, "
								+ "mobile_nr, is_active, person_comment, person_type, street_name, house_nr, zip, location_name "
								+ "FROM DonationBox NATURAL JOIN Person NATURAL JOIN PersonAddress "
								+ "WHERE box_id = " + boxId + ";");
				while (resultSetC.next()) {
					int personId = resultSetC.getInt("person_id");
					String forename = resultSetC.getString("forename");
					String lastname = resultSetC.getString("lastname");
					String email = resultSetC.getString("email");
					String phoneNr = resultSetC.getString("phone_nr");
					String mobileNr = resultSetC.getString("mobile_nr");
					boolean isActive = resultSetC.getBoolean("is_active");
					String personComment = resultSetC
							.getString("person_comment");
					// String personTyp = resultSet.getString("person_type");
					String streetName = resultSetC.getString("street_name");
					String houseNr = resultSetC.getString("house_nr");
					int zip = resultSetC.getInt("zip");
					String locationName = resultSetC.getString("location_name");

					opIndb = new OrganisationPerson(personId, forename,
							lastname, new Address(streetName, houseNr, zip,
									locationName), email, phoneNr, mobileNr,
							personComment, isActive);

				}

				db = new DonationBox(boxId, boxSum, inUse, boxComment, opIndb,
						allClearingDonationBoxesByBoxId, collectionId,
						isDeleted);
				donationBoxesInCollection.add(db);

			}

			Address fixedAddress = new Address();

			Statement statmentD = connection.createStatement();
			ResultSet locationAddressResultSet = statmentD
					.executeQuery("SELECT street_name, house_nr, zip, location_name "
							+ "FROM DonationCollection NATURAL JOIN LocationAddress "
							+ "WHERE collection_id = " + collectionId + ";");

			while (locationAddressResultSet.next()) {
				String streetName = locationAddressResultSet
						.getString("street_name");
				String houseNr = locationAddressResultSet.getString("house_nr");
				int zip = locationAddressResultSet.getInt("zip");
				String locationName = locationAddressResultSet
						.getString("location_name");

				fixedAddress = new Address(streetName, houseNr, zip,
						locationName);
			}

			// creating collection-Objects and add into List

			if (collectionType.equals("fixedCollection")) {
				FixedDonationCollection fdc = new FixedDonationCollection(
						collectionId, donationBoxesInCollection, collectionSum,
						collectionComment, isCompleted, op, beginPeriode,
						endPeriode, fixedAddress, cp);
				allDonationCollections.add(fdc);
			} else if (collectionType.equals("streetCollection")) {

				StreetDonationCollection sdc = new StreetDonationCollection(
						collectionId, donationBoxesInCollection, collectionSum,
						collectionComment, op, isCompleted, beginPeriode,
						endPeriode);
				allDonationCollections.add(sdc);
			}

		}

		return allDonationCollections;

	}

	/**
	 * stores basic StreetDonationCollection into database, without references
	 * to persons and boxes
	 * 
	 * @param sdc
	 *            the StreetDonationCollection-Object
	 * @throws SQLException
	 * @author Sebastian
	 */
	public void storeStreetDonationCollectionIntoDB(StreetDonationCollection sdc)
			throws SQLException {

		String isCompletedByString = null;

		if (sdc.getIsCompleted() == true) {
			isCompletedByString = "1";
		} else if (sdc.getIsCompleted() == false) {
			isCompletedByString = "0";
		}

		String endPeriode;
		if (sdc.getEndPeriode() == null) {
			endPeriode = null;
		} else {
			endPeriode = sdf.format(sdc.getEndPeriode().getTime());
		}

		Statement statement = connection.createStatement();
		statement
				.execute("INSERT INTO DonationCollection "
						+ "(collection_id, collection_sum, begin_periode, end_periode, collection_comment, collection_type, is_completed) "
						+ "VALUES (" + sdc.getCollectionId() + ", "
						+ sdc.getSum() + ", '"
						+ sdf.format(sdc.getBeginPeriode().getTime()) + "', '"
						+ endPeriode + "', '" + sdc.getComment() + "', '"
						+ "streetCollection" + "', " + isCompletedByString
						+ " );");

	}

	/**
	 * stores basic FixedDonationCollection into database, without references to
	 * persons and boxes and history
	 * 
	 * @param fdc
	 *            the FixedDonationCollection-Object
	 * @throws SQLException
	 * @author Sebastian
	 */
	public void storeFixedDonationCollectionIntoDB(FixedDonationCollection fdc)
			throws SQLException {

		String isCompletedByString = null;

		if (fdc.getIsCompleted() == true) {
			isCompletedByString = "1";
		} else if (fdc.getIsCompleted() == false) {
			isCompletedByString = "0";
		}

		String endPeriode;
		if (fdc.getEndPeriode() == null) {
			endPeriode = null;
		} else {
			endPeriode = sdf.format(fdc.getEndPeriode().getTime());
		}

		Statement statement = connection.createStatement();
		statement
				.execute("INSERT INTO DonationCollection "
						+ "(collection_id, collection_sum, begin_periode, end_periode, collection_comment, collection_type, is_completed) "
						+ "VALUES (" + fdc.getCollectionId() + ", "
						+ fdc.getSum() + ", '"
						+ sdf.format(fdc.getBeginPeriode().getTime()) + "', '"
						+ endPeriode + "', '" + fdc.getComment() + "', '"
						+ "fixedCollection" + "', " + isCompletedByString
						+ " );");

		Statement statementB = connection.createStatement();
		statementB.execute("INSERT INTO LocationAddress "
				+ "(street_name, house_nr, zip, location_name, collection_id) "
				+ "VALUES ('" + fdc.getFixedAddress().getStreetName() + "', '"
				+ fdc.getFixedAddress().getHauseNumber() + "', "
				+ fdc.getFixedAddress().getZip() + ", '"
				+ fdc.getFixedAddress().getLocationName() + "', "
				+ fdc.getCollectionId() + ");");
	}

	/**
	 * stores basic ClearingDonationCollection-Object into database, without
	 * connections to DonationCollection an Donation Box
	 * 
	 * @param cdc
	 *            the ClearingDonationCollection-Object
	 * @throws SQLException
	 * @author Sebastian
	 */
	public void storeClearingDonationBoxIntoDatabase(ClearingDonationBox cdb)
			throws SQLException {

		Statement statement = connection.createStatement();
		statement.execute("INSERT INTO ClearingDonationBox "
				+ "(clearing_id, clearing_sum, clearing_date) " + "VALUES ("
				+ cdb.getClearingId() + ", " + cdb.getSum() + ", '"
				+ sdf.format(cdb.getClearDate().getTime()) + "');");
	}

	/**
	 * update the edit person-object in database
	 * 
	 * @param person
	 *            the edit person-object
	 * @throws SQLException
	 * @author Sebastian
	 */
	public void editPersonInDB(Person person) throws SQLException {

		String isActiveByString = null;

		if (person.getIsActiveMember() == true) {
			isActiveByString = "1";
		} else if (person.getIsActiveMember() == false) {
			isActiveByString = "0";
		}

		Statement statement = connection.createStatement();
		statement.executeUpdate("UPDATE Person " + "SET " + "forename = '"
				+ person.getForename() + "', " + "lastname = '"
				+ person.getLastName() + "', " + "email = '"
				+ person.getEmail() + "', " + "phone_nr = '"
				+ person.getTelephoneNumber() + "', " + "mobile_nr = '"
				+ person.getMobilNumber() + "', " + "is_active = "
				+ isActiveByString + ", " + "person_comment = '"
				+ person.getComment() + "' "
				// + "person_type = " + +
				+ "WHERE person_id = " + person.getPersonId() + ";");

		Statement statementB = connection.createStatement();
		statementB.executeUpdate("UPDATE PersonAddress "
				+ "SET street_name = '" + person.getAddress().getStreetName()
				+ "', " + "house_nr = '" + person.getAddress().getHauseNumber()
				+ "', " + "zip = " + person.getAddress().getZip() + " ,"
				+ "location_name = '" + person.getAddress().getLocationName()
				+ "' " + "WHERE person_id = " + person.getPersonId() + ";");
	}

	/**
	 * edit the basic attributes of the DonationBox-Object in database, without
	 * connections to persons and collections
	 * 
	 * @param db
	 *            the edit DonationBox-Object
	 * @throws SQLException
	 * @author Sebastian
	 */
	public void editDonationBoxInDB(DonationBox db) throws SQLException {

		String inUseByString = null;

		if (db.isInUse() == true) {
			inUseByString = "1";
		} else if (db.isInUse() == false) {
			inUseByString = "0";
		}

		String collctionIdbyString = null;

		if (db.getCollectionId() == 0) {
			collctionIdbyString = "NULL";
		} else {
			collctionIdbyString = String.valueOf(db.getCollectionId());
		}

		Statement statement = connection.createStatement();
		statement.executeUpdate("UPDATE DonationBox " + "SET box_sum = "
				+ db.getSum() + ", " + "in_use = " + inUseByString + ", "
				+ "box_comment = '" + db.getComment() + "', "
				+ "collection_id = " + collctionIdbyString + " "
				+ "WHERE box_id = " + db.getBoxId() + ";");
	}

	/**
	 * edit the basic attributes of the StreetDonationCollection-Object in
	 * database, without connections to person and boxes
	 * 
	 * @param sdc
	 * @throws SQLException
	 * @author Sebastian
	 */
	public void editStreetDonationCollectionInDB(StreetDonationCollection sdc)
			throws SQLException {

		String isCompletedByString = null;

		if (sdc.getIsCompleted() == true) {
			isCompletedByString = "1";
		} else if (sdc.getIsCompleted() == false) {
			isCompletedByString = "0";
		}

		String endPeriode;
		if (sdc.getEndPeriode() == null) {
			endPeriode = null;
		} else {
			endPeriode = sdf.format(sdc.getEndPeriode().getTime());
		}

		Statement statement = connection.createStatement();
		statement.executeUpdate("UPDATE DonationCollection "
				+ "SET collection_sum = " + sdc.getSum()
				+ ", begin_periode = '"
				+ sdf.format(sdc.getBeginPeriode().getTime())
				+ "', end_periode = '" + endPeriode
				+ "', collection_comment = '" + sdc.getComment()
				+ "', is_completed = " + isCompletedByString + " "
				+ "WHERE collection_id = " + sdc.getCollectionId() + ";");

	}

	/**
	 * edit the basic attributes of the FixedDonationCollection-Object in
	 * database, without connections to persons and boxes
	 * 
	 * @param fdc
	 * @throws SQLException
	 * @author Sebastian
	 */
	public void editFixedDonationCollectionInDB(FixedDonationCollection fdc)
			throws SQLException {

		String isCompletedByString = null;

		if (fdc.getIsCompleted() == true) {
			isCompletedByString = "1";
		} else if (fdc.getIsCompleted() == false) {
			isCompletedByString = "0";
		}

		String endPeriode;
		if (fdc.getEndPeriode() == null) {
			endPeriode = null;
		} else {
			endPeriode = sdf.format(fdc.getEndPeriode().getTime());
		}

		Statement statement = connection.createStatement();
		statement
				.executeUpdate("UPDATE DonationCollection SET collection_sum = "
						+ fdc.getSum()
						+ ", begin_periode = '"
						+ sdf.format(fdc.getBeginPeriode().getTime())
						+ "', end_periode = '"
						+ endPeriode
						+ "', collection_comment = '"
						+ fdc.getComment()
						+ "', is_completed = "
						+ isCompletedByString
						+ " "
						+ "WHERE collection_id = "
						+ fdc.getCollectionId()
						+ ";");

		Statement statementB = connection.createStatement();
		statementB.executeUpdate("UPDATE LocationAddress SET street_name = '"
				+ fdc.getFixedAddress().getStreetName() + "', house_nr = '"
				+ fdc.getFixedAddress().getHauseNumber() + "', zip = "
				+ fdc.getFixedAddress().getZip() + ", location_name = '"
				+ fdc.getFixedAddress().getLocationName() + "' "
				+ "WHERE collection_id = " + fdc.getCollectionId() + ";");
	}

	/**
	 * deletes the ClearingDonationCollection Object by clearingId in database
	 * 
	 * @param clearingId
	 *            the clearingId
	 * @throws SQLException
	 * @author Sebastian
	 */
	public void deleteClearingDonationCollectionByIdFromDB(int clearingId)
			throws SQLException {
		Statement statement = connection.createStatement();
		statement.execute("DELETE FROM ClearingDonationCollection "
				+ "WHERE clearing_id = " + clearingId + ";");
	}

	/**
	 * sets the reference between DonationBox and DonationCollection in database
	 * 
	 * @param collectionId
	 *            the collectionId
	 * @param boxId
	 *            the boxId
	 * @throws SQLException
	 * @author Sebastian
	 */
	public void setCollectionIdReferenceInDonationBox(int collectionId,
			int boxId) throws SQLException {
		Statement statement = connection.createStatement();
		statement.executeUpdate("UPDATE DonationBox " + "SET collection_id = "
				+ collectionId + " " + "WHERE box_id = " + boxId + ";");
	}

	/**
	 * sets the reference between Person and DonationBox in database
	 * 
	 * @param personId
	 *            the personId
	 * @param boxId
	 *            the boxId
	 * @throws SQLException
	 * @author Sebastian
	 */
	public void setPersonIdReferenceInDonationBox(int personId, int boxId)
			throws SQLException {
		Statement statement = connection.createStatement();
		statement.executeUpdate("UPDATE DonationBox " + "SET person_id = "
				+ personId + " " + "WHERE box_id = " + boxId + ";");
	}

	/**
	 * sets the reference between DonationCollection and ClearingDonationBox in
	 * database
	 * 
	 * @param collectionId
	 *            the collectionId
	 * @param clearingId
	 *            the clearingId
	 * @throws SQLException
	 * @author Sebastian
	 */
	public void setCollectionIdReferenceInClearingDonationBox(int collectionId,
			int clearingId) throws SQLException {
		Statement statement = connection.createStatement();
		statement.executeUpdate("UPDATE ClearingDonationBox "
				+ "SET collection_id = " + collectionId + " "
				+ "WHERE clearing_id = " + clearingId + ";");
	}

	/**
	 * sets the reference between DonationBox and ClearingDonationBox in
	 * database
	 * 
	 * @param boxId
	 *            the boxId
	 * @param clearingId
	 *            the clearingId
	 * @throws SQLException
	 * @author Sebastian
	 */
	public void setBoxIdReferenceInClearingDonationBox(int boxId, int clearingId)
			throws SQLException {
		Statement statement = connection.createStatement();
		statement.executeUpdate("UPDATE ClearingDonationBox " + "SET box_id = "
				+ boxId + " " + "WHERE clearing_id = " + clearingId + ";");
	}

	/**
	 * sets the isActive Attribute in Person by personId in database
	 * 
	 * @param isActive
	 *            the isActive
	 * @param personId
	 *            the personId
	 * @throws SQLException
	 * @author Sebastian
	 */
	public void setIsActiveInPerson(boolean isActive, int personId)
			throws SQLException {

		String isActiveByString = null;
		if (isActive == true) {
			isActiveByString = "1";
		} else if (isActive == false) {
			isActiveByString = "0";
		}

		Statement statement = connection.createStatement();
		statement.executeUpdate("UPDATE Person " + "SET is_active = "
				+ isActiveByString + " " + "WHERE person_id = " + personId);
	}

	/**
	 * sets the isCompleted Attribute in DonationCollection by collectionId in
	 * database
	 * 
	 * @param isCompleted
	 *            the isCompleted
	 * @param collectionId
	 *            the collectionId
	 * @throws SQLException
	 * @author Sebastian
	 */
	public void setIsCompletedInDonationCollection(boolean isCompleted,
			int collectionId) throws SQLException {
		String isCompletedByString = null;
		if (isCompleted == true) {
			isCompletedByString = "1";
		} else if (isCompleted == false) {
			isCompletedByString = "0";
		}

		Statement statement = connection.createStatement();
		statement.executeUpdate("UPDATE DonationCollection "
				+ "SET is_completed = " + isCompletedByString + " "
				+ "WHERE collection_id = " + collectionId + ";");
	}

	/**
	 * sets the inUse Attribute in DonationBox by boxId in database
	 * 
	 * @param inUse
	 *            the inUse
	 * @param boxId
	 *            the boxId
	 * @throws SQLException
	 * @author Sebastian
	 */
	public void setInUseInDonationBox(boolean inUse, int boxId)
			throws SQLException {
		String inUseByString = null;
		if (inUse == true) {
			inUseByString = "1";
		} else if (inUse == false) {
			inUseByString = "0";
		}

		Statement statement = connection.createStatement();
		statement.executeUpdate("UPDATE DonationBox " + "SET in_use = "
				+ inUseByString + " " + "WHERE box_id = " + boxId + ";");
	}

	/**
	 * sets the contact-personId reference in DonationCollection
	 * 
	 * @param personId
	 *            the personId
	 * @param collectionId
	 *            the collectionId
	 * @throws SQLException
	 * @author Sebastian
	 */
	public void setContactPersonReferenceInDonationCollection(int personId,
			int collectionId) throws SQLException {
		Statement statement = connection.createStatement();
		statement.executeUpdate("UPDATE DonationCollection "
				+ "SET contact_person_id = " + personId + " "
				+ "WHERE collection_id = " + collectionId + ";");
	}

	/**
	 * sets the organisation-personId reference in DonationCollection
	 * 
	 * @param personId
	 *            the personId
	 * @param collectionId
	 *            the collectionId
	 * @throws SQLException
	 * @author Sebastian
	 */
	public void setOrganisationPersonReferenceInDonationCollection(
			int personId, int collectionId) throws SQLException {
		Statement statement = connection.createStatement();
		statement.executeUpdate("UPDATE DonationCollection "
				+ "SET organisation_person_id = " + personId + " "
				+ "WHERE collection_id = " + collectionId + ";");
	}

	/**
	 * returns ArraList of ClearingDonationBoxes by boxId and collectionId
	 * 
	 * @param boxId
	 *            the boxId
	 * @param collectionId
	 *            the collectionId
	 * @return ArrayList of ClearingDonationBox-Objects by boxId and
	 *         collectionId
	 * @throws SQLException
	 * @throws ParseException
	 * @author Sebastian
	 */
	public ArrayList<ClearingDonationBox> getClearingDonationBoxesByBoxIdInCollectionId(
			int boxId, int collectionId) throws SQLException, ParseException {

		ArrayList<ClearingDonationBox> allClearingDonationBoxesByBoxIdInCollectionId = new ArrayList<ClearingDonationBox>();

		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * "
				+ "FROM ClearingDonationBox " + "WHERE box_id = " + boxId + " "
				+ "AND collection_id = " + collectionId + ";");

		while (resultSet.next()) {
			int clearingId = resultSet.getInt("clearing_id");
			Float clearingSum = resultSet.getFloat("clearing_sum");
			String clearingDate = resultSet.getString("clearing_date");

			ClearingDonationBox cdb = new ClearingDonationBox(clearingId,
					clearingDate, clearingSum);
			allClearingDonationBoxesByBoxIdInCollectionId.add(cdb);
		}

		return allClearingDonationBoxesByBoxIdInCollectionId;
	}

	/**
	 * returns ArraList of ClearingDonationBoxes by boxId
	 * 
	 * @param boxId
	 * @return ArrayList of ClearingDonationBox-Objects by boxId
	 * @throws SQLException
	 * @throws ParseException
	 * @author Sebastian
	 */
	public ArrayList<ClearingDonationBox> getClearingDonationBoxesByBoxId(
			int boxId) throws SQLException, ParseException {
		ArrayList<ClearingDonationBox> allClearingDonationBoxesByBoxId = new ArrayList<ClearingDonationBox>();

		Statement statement = connection.createStatement();
		ResultSet resultSet = statement
				.executeQuery("SELECT * " + "FROM ClearingDonationBox "
						+ "WHERE box_id = " + boxId + ";");

		while (resultSet.next()) {
			int clearingId = resultSet.getInt("clearing_id");
			Float clearingSum = resultSet.getFloat("clearing_sum");
			String clearingDate = resultSet.getString("clearing_date");

			ClearingDonationBox cdb = new ClearingDonationBox(clearingId,
					clearingDate, clearingSum);
			allClearingDonationBoxesByBoxId.add(cdb);
		}

		return allClearingDonationBoxesByBoxId;
	}

	/**
	 * returns all ClearingDonationBoxes By boxId
	 * 
	 * @param boxId
	 * @return ArrayList of ClearingDonationBox
	 * @throws SQLException
	 * @throws ParseException
	 * @author Sebastian
	 */
	public ArrayList<ClearingDonationBox> getClearingDonationBoxesByBoxIdInCollectionId(
			int boxId) throws SQLException, ParseException {

		ArrayList<ClearingDonationBox> allClearingDonationBoxesByBoxIdInCollectionId = new ArrayList<ClearingDonationBox>();

		Statement statement = connection.createStatement();
		ResultSet resultSet = statement
				.executeQuery("SELECT * " + "FROM ClearingDonationBox "
						+ "WHERE box_id = " + boxId + ";");

		while (resultSet.next()) {
			int clearingId = resultSet.getInt("clearing_id");
			Float clearingSum = resultSet.getFloat("clearing_sum");

			String clearingDate = resultSet.getString("clearing_date");

			ClearingDonationBox cdb = new ClearingDonationBox(clearingId,
					clearingDate, clearingSum);
			allClearingDonationBoxesByBoxIdInCollectionId.add(cdb);
		}

		return allClearingDonationBoxesByBoxIdInCollectionId;
	}

	/**
	 * returns the collectionId by boxId
	 * 
	 * @param boxId
	 * @return int collectionId
	 * @throws SQLException
	 * @author Sebastian
	 */
	public int getCollectionIdByBoxId(int boxId) throws SQLException {
		int collectionId = -1;
		ResultSet result;
		Statement statement = connection.createStatement();
		result = statement.executeQuery("SELECT collection_id "
				+ "FROM DonationBox " + "WHERE box_id = " + boxId + ";");
		while (result.next()) {
			collectionId = result.getInt("collection_id");
		}
		return collectionId;
	}

	/**
	 * returns the collectionId by the clearingBoxId
	 * 
	 * @param clearingId
	 *            the clearingBoxId
	 * @return int collectionId
	 * @throws SQLException
	 * @author Sebastian
	 */
	public int getCollectionIdByClearingBoxId(int clearingId)
			throws SQLException {
		int collectionId = -1;
		ResultSet result;
		Statement statement = connection.createStatement();
		result = statement.executeQuery("SELECT collection_id "
				+ "FROM ClearingDonationBox " + "WHERE clearing_id = "
				+ clearingId + ";");
		while (result.next()) {
			collectionId = result.getInt("collection_id");
		}
		return collectionId;
	}

	// /**
	// * deletes the DonationCollection by id from database
	// * @param collectionId the collectionId
	// * @throws SQLException
	// * @author Sebastian
	// */
	// public void deleteDonationCollectionByIdFromDB(int collectionId) throws
	// SQLException{
	// Statement statement = connection.createStatement();
	// statement.executeUpdate("DELETE FROM DonationCollection "
	// + "WHERE collection_id = " + collectionId + ";");
	// }

	/**
	 * sets the attribut isDeletetd to true and remove box from collection by id
	 * in database
	 * 
	 * @param boxId
	 *            the box id
	 * @throws SQLException
	 * @author Sebastian
	 */
	public void deleteDonationBoxById(int boxId) throws SQLException {
		Statement statement = connection.createStatement();
		statement.executeUpdate("UPDATE DonationBox "
				+ "SET is_deleted = '1', " + "collection_id = NULL "
				+ "WHERE box_id = " + boxId + ";");

	}

	/**
	 * sets the attribut inUse to false and collectionId to null
	 * 
	 * @param boxId
	 *            the box id
	 * @throws SQLException
	 * @author Sebastian
	 */
	public void deleteDonationBoxByIdFromCollection(int boxId)
			throws SQLException {
		Statement statement = connection.createStatement();
		statement.executeUpdate("UPDATE DonationBox " + "SET in_use = '0', "
				+ "collection_id = NULL " + "WHERE box_id = " + boxId + ";");
	}

	/**
	 * returns ArrayList of ClearingDonationBoxes by collectionId
	 * 
	 * @param collectionId
	 *            the collectionId
	 * @return ArrayList of ClearingDonationBoxes by collectionId
	 * @throws SQLException
	 * @throws ParseException
	 * @author Sebastian
	 */
	public ArrayList<ClearingDonationBox> getClearingDonationBoxesbyCollectionId(
			int collectionId) throws SQLException, ParseException {

		ArrayList<ClearingDonationBox> clearingDonationBoxes = new ArrayList<ClearingDonationBox>();

		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * "
				+ "FROM ClearingDonationBox " + "WHERE collection_id = "
				+ collectionId + ";");

		while (resultSet.next()) {
			int clearingId = resultSet.getInt("clearing_id");
			Float clearingSum = resultSet.getFloat("clearing_sum");
			String clearingDate = resultSet.getString("clearing_date");

			ClearingDonationBox cdb = new ClearingDonationBox(clearingId,
					clearingDate, clearingSum);
			clearingDonationBoxes.add(cdb);
		}

		return clearingDonationBoxes;
	}

	// /////////////////////////////////////////////////////////////////////////////

	/**
	 * stores a login-object into database
	 * 
	 * @param login
	 *            the login-object
	 * @throws SQLException
	 * @author Sebastian
	 */
	public void storeLoginIntoDB(Login login) throws SQLException {

		String isDeletedByString = null;
		if (login.isDeleted() == true) {
			isDeletedByString = "1";
		} else if (login.isDeleted() == false) {
			isDeletedByString = "0";
		}

		Statement statment = connection.createStatement();
		statment.execute("INSERT INTO Login "
				+ "(login_name, password, accesslevel, is_deleted) "
				+ "VALUES ('" + login.getLoginName() + "', '"
				+ login.getPasswort() + "', " + login.getAccessLevel() + ", "
				+ isDeletedByString + ");");
	}

	/**
	 * returns the login-object by loginname from database
	 * 
	 * @param loginname
	 * @return the login-object
	 * @throws SQLException
	 * @throws LoginInUseException
	 * @author Sebastian
	 */
	public Login getLoginByLoginname(String loginname) throws SQLException,
			LoginInUseException {

		Login currLogin = null;
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM Login "
				+ "WHERE login_name = '" + loginname + "';");

		while (resultSet.next()) {
			String loginName = resultSet.getString("login_name");
			String password = resultSet.getString("password");
			int accesslevel = resultSet.getInt("accesslevel");
			boolean isDeleted = resultSet.getBoolean("is_deleted");

			currLogin = new Login(loginName, password, accesslevel, isDeleted);
		}

		return currLogin;
	}

	/**
	 * changes the password in login-object by loginame in database
	 * 
	 * @param loginname
	 * @param password
	 * @throws SQLException
	 * @author Sebastian
	 */
	public void changePasswordByLoginname(String loginname, String password)
			throws SQLException {
		Statement statement = connection.createStatement();
		statement.executeUpdate("UPDATE Login SET " + "password = '" + password
				+ "' " + "WHERE login_name = '" + loginname + "';");
	}

	/**
	 * sets is_deleted to true in database
	 * 
	 * @param loginname
	 *            the loginname
	 * @throws SQLException
	 * @author Sebastian
	 */
	public void deleteLoginByLoginname(String loginname) throws SQLException {
		Statement statement = connection.createStatement();
		statement.executeUpdate("UPDATE Login SET " + "is_deleted = 1 "
				+ "WHERE login_name = '" + loginname + "';");
	}
	
	/**
	 * deletes the login-object by loginname from database
	 * @param loginname the loginname
	 * @throws SQLException
	 * @author Sebastian
	 */
	public void deleteLoginByLoginnameFromDB(String loginname) throws SQLException{
		Statement statement = connection.createStatement();
		statement.executeUpdate("DELETE FROM Login WHERE login_name = '" + loginname + "';");
	}

	/**
	 * sets the accesslevel in login-object by loginname in database
	 * 
	 * @param loginname
	 *            the loginname
	 * @param accesslevel
	 *            the accesslevel
	 * @throws SQLException
	 * @author Sebastian
	 */
	public void setAccesslevelInLoginByLoginname(String loginname,
			int accesslevel) throws SQLException {
		Statement statement = connection.createStatement();
		statement
				.executeUpdate("UPDATE Login SET " + "accesslevel = "
						+ accesslevel + " " + "WHERE login_name = '"
						+ loginname + "';");
	}

	/**
	 * returns an ArrayList of all Login-Objects, who are stored in database
	 * 
	 * @return ArrayList of all Login-Objects
	 * @throws SQLException
	 * @throws LoginInUseException
	 * @author Sebastian
	 */
	public ArrayList<Login> getLoginsFromDB() throws SQLException,
			LoginInUseException {
		ArrayList<Login> allLogins = new ArrayList<Login>();

		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM Login;");

		while (resultSet.next()) {
			String loginName = resultSet.getString("login_name");
			String password = resultSet.getString("password");
			int accesslevel = resultSet.getInt("accesslevel");
			boolean isDeleted = resultSet.getBoolean("is_deleted");

			Login currLogin = new Login(loginName, password, accesslevel,
					isDeleted);
			allLogins.add(currLogin);
		}

		return allLogins;
	}

	// ////////////////////////////////////////////////////////////////////////////

	/**
	 * stores an BoxHistory-Object into database
	 * 
	 * @param bh
	 *            the BoxHistory object
	 * @throws SQLException
	 * @author Sebastian
	 */
	public void storeBoxHistoryIntoDB(BoxHistory bh) throws SQLException {

		String endDate;
		// if (bh.getEndDate().getTime() == null) {
		// endDate = null;
		// } else {
		// endDate = sdf.format(bh.getEndDate().getTime());
		// }

		try {
			endDate = sdf.format(bh.getEndDate().getTime());
		} catch (Exception x) {
			endDate = null;
		}

		Statement statment = connection.createStatement();
		statment.execute("INSERT INTO BoxHistory "
				+ "(boxhistory_id, begin_date, end_date, collection_id, box_id) "
				+ "VALUES (" + bh.getBoxHistoryId() + ", '"
				+ sdf.format(bh.getBeginDate().getTime()) + "', '" + endDate
				+ "', " + bh.getCollectionId() + "," + bh.getBoxId() + ");");
	}

	/**
	 * update the BoxHistory-Object in database
	 * @param bh the BoxHistory-Object 
	 * @throws SQLException
	 */
	public void editBoxHistoryInDB(BoxHistory bh) throws SQLException {

		String endDate;
		try {
			endDate = sdf.format(bh.getEndDate().getTime());
		} catch (Exception x) {
			endDate = null;
		}

		Statement statment = connection.createStatement();
		statment.executeUpdate("UPDATE BoxHistory SET " + "begin_date = '"
				+ sdf.format(bh.getBeginDate().getTime()) + "', "
				+ "end_date = '" + endDate + "', " + "collection_id = "
				+ bh.getCollectionId() + ", " + "box_id = " + bh.getBoxId()
				+ " WHERE boxhistory_id = " + bh.getBoxHistoryId() +  ";");

	}

	/**
	 * returns an ArrayList of BoxHistory-Objects by collectionId, who are
	 * stored in database
	 * 
	 * @param collectionId
	 *            the collectionId
	 * @return ArrayList of BoxHistory-Objects
	 * @throws SQLException
	 * @throws ParseException
	 * @author Sebastian
	 */
	public ArrayList<BoxHistory> getBoxHistoriesByCollectionId(int collectionId)
			throws SQLException, ParseException {

		ArrayList<BoxHistory> boxHistoryList = new ArrayList<BoxHistory>();

		Statement statement = connection.createStatement();
		ResultSet resultSet = statement
				.executeQuery("SELECT * FROM BoxHistory "
						+ "WHERE collection_id = " + collectionId + ";");

		while (resultSet.next()) {
			int boxHistoryId = resultSet.getInt("boxhistory_id");
			String beginDate = resultSet.getString("begin_date");
			String endDate = resultSet.getString("end_date");
			int collId = resultSet.getInt("collection_id");
			int boxId = resultSet.getInt("box_id");

			BoxHistory currBoxHistory = new BoxHistory(boxHistoryId, beginDate,
					endDate, collId, boxId);
			boxHistoryList.add(currBoxHistory);
		}

		return boxHistoryList;
	}

	/**
	 * returns an ArrayList of BoxHistory-Objects by boxId, who are stored in
	 * database
	 * 
	 * @param boxId
	 *            the boxId
	 * @return ArrayList of BoxHistory-Objects
	 * @throws SQLException
	 * @throws ParseException
	 */
	public ArrayList<BoxHistory> getBoxHistoriesByBoxId(int boxId)
			throws SQLException, ParseException {

		ArrayList<BoxHistory> boxHistoryList = new ArrayList<BoxHistory>();

		Statement statement = connection.createStatement();
		ResultSet resultSet = statement
				.executeQuery("SELECT * FROM BoxHistory " + "WHERE box_id = "
						+ boxId + ";");

		while (resultSet.next()) {
			int boxHistoryId = resultSet.getInt("boxhistory_id");
			String beginDate = resultSet.getString("begin_date");
			String endDate = resultSet.getString("end_date");
			int collId = resultSet.getInt("collection_id");
			int currboxId = resultSet.getInt("box_id");

			BoxHistory currBoxHistory = new BoxHistory(boxHistoryId, beginDate,
					endDate, collId, currboxId);
			boxHistoryList.add(currBoxHistory);
		}

		return boxHistoryList;
	}

	/**
	 * returns an ArrayList of all BoxHistory-Objects, who are stored in
	 * database
	 * 
	 * @return ArrayList of BoxHistory-Objects
	 * @throws SQLException
	 * @throws ParseException
	 * @author Sebastian
	 */
	public ArrayList<BoxHistory> getBoxHistoriesFromDB() throws SQLException,
			ParseException {

		ArrayList<BoxHistory> boxHistoryList = new ArrayList<BoxHistory>();

		Statement statement = connection.createStatement();
		ResultSet resultSet = statement
				.executeQuery("SELECT * FROM BoxHistory;");

		while (resultSet.next()) {
			int boxHistoryId = resultSet.getInt("boxhistory_id");
			String beginDate = resultSet.getString("begin_date");
			String endDate = resultSet.getString("end_date");
			int collId = resultSet.getInt("collection_id");
			int currboxId = resultSet.getInt("box_id");

			BoxHistory currBoxHistory = new BoxHistory(boxHistoryId, beginDate,
					endDate, collId, currboxId);
			boxHistoryList.add(currBoxHistory);
		}

		return boxHistoryList;
	}

	/**
	 * stores an OrgaBoxHistory-Object into database in table
	 * OrganisationPersonHistoryInBox
	 * 
	 * @param orgaBoxHistory
	 *            the OrgaBoxHistory-Object in box
	 * @throws SQLException
	 * @author Sebastian
	 */
	public void storeOrganisationPersonHistoryInBoxIntoDB(
			OrgaBoxHistory orgaBoxHistory) throws SQLException {

		String endDate;
		// if (orgaBoxHistory.getEndDate().getTime() == null) {
		// endDate = null;
		// } else {
		// endDate = sdf.format(orgaBoxHistory.getEndDate().getTime());
		// }

		try {
			endDate = sdf.format(orgaBoxHistory.getEndDate().getTime());
		} catch (Exception x) {
			endDate = null;
		}

		Statement statment = connection.createStatement();
		statment.execute("INSERT INTO OrganisationPersonHistoryInBox "
				+ "(box_orga_history_id, box_id, person_id, begin_date, end_date) "
				+ "VALUES (" + orgaBoxHistory.getHistoryId() + ", "
				+ orgaBoxHistory.getBoxId() + ", "
				+ orgaBoxHistory.getOrganisationPersonId() + ", '"
				+ sdf.format(orgaBoxHistory.getBeginDate().getTime()) + "', '"
				+ endDate + "');");
	}

	/**
	 * update the OrgaBoxHistory-Object in box in database
	 * 
	 * @param orgaBoxHistory
	 *            the OrgaBoxHistory-Object
	 * @throws SQLException
	 * @author Sebastian
	 */
	public void editOrganisationPersonHistoryInBoxInDB(
			OrgaBoxHistory orgaBoxHistory) throws SQLException {

		String endDate;
		if (orgaBoxHistory.getEndDate().getTime() == null) {
			endDate = null;
		} else {
			endDate = sdf.format(orgaBoxHistory.getEndDate().getTime());
		}

		Statement statment = connection.createStatement();
		statment.executeUpdate("UPDATE OrganisationPersonHistoryInBox SET "
				+ "person_id = " + orgaBoxHistory.getOrganisationPersonId()
				+ ", " + "begin_date = '"
				+ sdf.format(orgaBoxHistory.getBeginDate().getTime()) + "', "
				+ "end_date = '" + endDate + "' "
				+ "WHERE box_orga_history_id = "
				+ orgaBoxHistory.getHistoryId() + ";");
	}

	/**
	 * returns an ArrayList of OrgaBoxHistory-Objects by boxId from database
	 * 
	 * @param boxId
	 *            the boxId
	 * @return an ArrayList of OrgaBoxHistory-Objects
	 * @throws SQLException
	 */
	public ArrayList<OrgaBoxHistory> getOrganisationPersonHistoryInBoxByBoxId(
			int boxId) throws SQLException {

		ArrayList<OrgaBoxHistory> orgaHistoryList = new ArrayList<OrgaBoxHistory>();

		Statement statement = connection.createStatement();
		ResultSet resultSet = statement
				.executeQuery("SELECT * FROM OrganisationPersonHistoryInBox "
						+ "WHERE box_id = " + boxId + ";");

		while (resultSet.next()) {
			int organisationPersonHistoryId = resultSet
					.getInt("box_orga_history_id");
			int personId = resultSet.getInt("person_id");
			int boxID = resultSet.getInt("box_id");
			String beginDate = resultSet.getString("begin_date");
			String endDate = resultSet.getString("end_date");

			OrgaBoxHistory currOrgaBoxHistory = new OrgaBoxHistory(
					organisationPersonHistoryId, personId, boxID, beginDate,
					endDate);
			orgaHistoryList.add(currOrgaBoxHistory);
		}

		return orgaHistoryList;

	}

	/**
	 * returns an ArrayList of all OrgaBoxHistory-Objects in boxes from database
	 * 
	 * @return ArrayList of all OrgaBoxHistory-Objects in boxes
	 * @throws SQLException
	 * @author Sebastian
	 */
	public ArrayList<OrgaBoxHistory> getAllOrganisationPersonHistoryInBox()
			throws SQLException {
		ArrayList<OrgaBoxHistory> orgaBoxHistoryList = new ArrayList<OrgaBoxHistory>();

		Statement statement = connection.createStatement();
		ResultSet resultSet = statement
				.executeQuery("SELECT * FROM OrganisationPersonHistoryInBox;");

		while (resultSet.next()) {
			int organisationPersonHistoryId = resultSet
					.getInt("box_orga_history_id");
			int personId = resultSet.getInt("person_id");
			int boxID = resultSet.getInt("box_id");
			String beginDate = resultSet.getString("begin_date");
			String endDate = resultSet.getString("end_date");

			OrgaBoxHistory currOrgaBoxHistory = new OrgaBoxHistory(
					organisationPersonHistoryId, personId, boxID, beginDate,
					endDate);
			orgaBoxHistoryList.add(currOrgaBoxHistory);
		}

		return orgaBoxHistoryList;
	}

	/**
	 * stores an OrgaCollectionHistory-Object into database in table
	 * OrganisationPersonHistoryInCollection
	 * 
	 * @param orgaCollectionHistory
	 *            the OrgaCollectionHistory-Object in collection
	 * @throws SQLException
	 * @author Sebastian
	 */
	public void storeOrganisationPersonHistoryInCollectionIntoDB(
			OrgaCollectionHistory orgaCollectionHistory) throws SQLException {

		String endDate;

		try {
			endDate = sdf.format(orgaCollectionHistory.getEndDate().getTime());
		} catch (Exception x) {
			endDate = null;
		}

		// if (orgaCollectionHistory.getEndDate().getTime() == null) {
		// endDate = null;
		// } else {
		// endDate = sdf.format(orgaCollectionHistory.getEndDate().getTime());
		// }

		Statement statment = connection.createStatement();
		statment.execute("INSERT INTO OrganisationPersonHistoryInCollection "
				+ "(coll_orga_history_id, collection_id, person_id, begin_date, end_date) "
				+ "VALUES (" + orgaCollectionHistory.getHistoryId() + ", "
				+ orgaCollectionHistory.getCollectionId() + ", "
				+ orgaCollectionHistory.getOrganisationPersonId() + ", '"
				+ sdf.format(orgaCollectionHistory.getBeginDate().getTime())
				+ "', '" + endDate + "');");
	}

	/**
	 * update the OrgaCollectionHistory-Object in collection in database
	 * 
	 * @param orgaCollectionHistory
	 *            the OrgaCollectionHistory-Object
	 * @throws SQLException
	 * @author Sebastian
	 */
	public void editOrganisationPersonHistoryInCollectionInDB(
			OrgaCollectionHistory orgaCollectionHistory) throws SQLException {

		String endDate;
		if (orgaCollectionHistory.getEndDate().getTime() == null) {
			endDate = null;
		} else {
			endDate = sdf.format(orgaCollectionHistory.getEndDate().getTime());
		}

		Statement statment = connection.createStatement();
		statment.executeUpdate("UPDATE OrganisationPersonHistoryInCollection SET "
				+ "collection_id = "
				+ orgaCollectionHistory.getCollectionId()
				+ ", "
				+ "person_id = "
				+ orgaCollectionHistory.getOrganisationPersonId()
				+ ", "
				+ "begin_date = '"
				+ sdf.format(orgaCollectionHistory.getBeginDate().getTime())
				+ "', "
				+ "end_date = '"
				+ endDate
				+ "' "
				+ "WHERE coll_orga_history_id = "
				+ orgaCollectionHistory.getHistoryId() + ";");

	}

	/**
	 * returns an ArrayList of OrganisationPersonHistory-Objects by collectionId
	 * from database
	 * 
	 * @param collectionId
	 *            the collectionId
	 * @return an ArrayList of OrganisationPersonHistory-Objects
	 * @throws SQLException
	 */
	public ArrayList<OrgaCollectionHistory> getOrganisationPersonHistoryInCollectionByCollectionId(
			int collectionId) throws SQLException {

		ArrayList<OrgaCollectionHistory> orgaCollectionHistoryList = new ArrayList<OrgaCollectionHistory>();

		Statement statement = connection.createStatement();
		ResultSet resultSet = statement
				.executeQuery("SELECT * FROM OrganisationPersonHistoryInCollection "
						+ "WHERE collection_id = " + collectionId + ";");

		while (resultSet.next()) {
			int organisationPersonHistoryId = resultSet
					.getInt("coll_orga_history_id");
			int collectionID = resultSet.getInt("collection_id");
			int personId = resultSet.getInt("person_id");
			String beginDate = resultSet.getString("begin_date");
			String endDate = resultSet.getString("end_date");

			OrgaCollectionHistory currCollectionOrgaHistory = new OrgaCollectionHistory(
					organisationPersonHistoryId, personId, collectionID,
					beginDate, endDate);
			orgaCollectionHistoryList.add(currCollectionOrgaHistory);
		}

		return orgaCollectionHistoryList;

	}

	/**
	 * returns an ArrayList of all OrgaCollectionHistory-Objects in collections
	 * from database
	 * 
	 * @return ArrayList of all OrgaCollectionHistory-Objects in collections
	 * @throws SQLException
	 * @author Sebastian
	 */
	public ArrayList<OrgaCollectionHistory> getAllOrganisationPersonHistoryInCollection()
			throws SQLException {
		ArrayList<OrgaCollectionHistory> orgaCollectionHistoryList = new ArrayList<OrgaCollectionHistory>();

		Statement statement = connection.createStatement();
		ResultSet resultSet = statement
				.executeQuery("SELECT * FROM OrganisationPersonHistoryInCollection;");

		while (resultSet.next()) {
			int organisationPersonHistoryId = resultSet
					.getInt("coll_orga_history_id");
			int collectionID = resultSet.getInt("collection_id");
			int personId = resultSet.getInt("person_id");
			String beginDate = resultSet.getString("begin_date");
			String endDate = resultSet.getString("end_date");

			OrgaCollectionHistory currOrgaCollectionHistory = new OrgaCollectionHistory(
					organisationPersonHistoryId, personId, collectionID,
					beginDate, endDate);
			orgaCollectionHistoryList.add(currOrgaCollectionHistory);
		}

		return orgaCollectionHistoryList;
	}

	/**
	 * stores an ContactPersonHistory-Object into database in table
	 * ContactPersonHistoryInCollection
	 * 
	 * @param contactHistory
	 *            the ContactPersonHistory-Object in collection
	 * @throws SQLException
	 * @author Sebastian
	 */
	public void storeContactPersonHistoryInCollectionIntoDB(
			ContactPersonHistory contactHistory) throws SQLException {

		String endDate;
		// if (contactHistory.getEndDate().getTime() == null) {
		// endDate = null;
		// } else {
		// endDate = sdf.format(contactHistory.getEndDate().getTime());
		// }

		try {
			endDate = sdf.format(contactHistory.getEndDate().getTime());
		} catch (Exception x) {
			endDate = null;
		}

		Statement statment = connection.createStatement();
		statment.execute("INSERT INTO ContactPersonHistoryInCollection "
				+ "(coll_contact_history_id, collection_id, person_id, begin_date, end_date) "
				+ "VALUES (" + contactHistory.getHistoryId() + ", "
				+ contactHistory.getCollectionId() + ", "
				+ contactHistory.getContactPersonId() + ", '"
				+ sdf.format(contactHistory.getBeginDate().getTime()) + "', '"
				+ endDate + "');");
	}

	/**
	 * update the ContactPersonHistory-Object in collection in database
	 * 
	 * @param contactHistory
	 *            the ContactPersonHistory-Object
	 * @throws SQLException
	 * @author Sebastian
	 */
	public void editContactPersonHistoryInCollectionInDB(
			ContactPersonHistory contactHistory) throws SQLException {

		String endDate;
		if (contactHistory.getEndDate().getTime() == null) {
			endDate = null;
		} else {
			endDate = sdf.format(contactHistory.getEndDate().getTime());
		}

		Statement statment = connection.createStatement();
		statment.executeUpdate("UPDATE ContactPersonHistoryInCollection SET "
				+ "collection_id = " + contactHistory.getCollectionId() + ", "
				+ "person_id = " + contactHistory.getContactPersonId() + ", "
				+ "begin_date = '"
				+ sdf.format(contactHistory.getBeginDate().getTime()) + "', "
				+ "end_date = '" + endDate + "' "
				+ "WHERE coll_contact_history_id = "
				+ contactHistory.getHistoryId() + ";");

	}

	/**
	 * returns an ArrayList of ContactPersonHistory-Objects by collectionId from
	 * database
	 * 
	 * @param collectionId
	 *            the collectionId
	 * @return an ArrayList of ContactPersonHistory-Objects
	 * @throws SQLException
	 * @author Sebastian
	 */
	public ArrayList<ContactPersonHistory> getContactPersonHistoryInCollectionByCollectionId(
			int collectionId) throws SQLException {

		ArrayList<ContactPersonHistory> contactHistoryList = new ArrayList<ContactPersonHistory>();

		Statement statement = connection.createStatement();
		ResultSet resultSet = statement
				.executeQuery("SELECT * FROM ContactPersonHistoryInCollection "
						+ "WHERE collection_id = " + collectionId + ";");

		while (resultSet.next()) {
			int contactPersonHistoryId = resultSet
					.getInt("coll_contact_history_id");
			int collectionID = resultSet.getInt("collection_id");
			int personId = resultSet.getInt("person_id");
			String beginDate = resultSet.getString("begin_date");
			String endDate = resultSet.getString("end_date");

			ContactPersonHistory currContactHistory = new ContactPersonHistory(
					contactPersonHistoryId, personId, collectionID, beginDate,
					endDate);
			contactHistoryList.add(currContactHistory);
		}

		return contactHistoryList;
	}

	/**
	 * returns an ArrayList of all ContactPersonHistory-Objects in collections
	 * from database
	 * 
	 * @return ArrayList of all ContactPersonHistory-Objects in collections
	 * @throws SQLException
	 */
	public ArrayList<ContactPersonHistory> getAllContactPersonHistoryInCollection()
			throws SQLException {
		ArrayList<ContactPersonHistory> contactHistoryList = new ArrayList<ContactPersonHistory>();

		Statement statement = connection.createStatement();
		ResultSet resultSet = statement
				.executeQuery("SELECT * FROM ContactPersonHistoryInCollection;");

		while (resultSet.next()) {
			int contactPersonHistoryId = resultSet
					.getInt("coll_contact_history_id");
			int collectionID = resultSet.getInt("collection_id");
			int personId = resultSet.getInt("person_id");
			String beginDate = resultSet.getString("begin_date");
			String endDate = resultSet.getString("end_date");

			ContactPersonHistory currContactHistory = new ContactPersonHistory(
					contactPersonHistoryId, personId, collectionID, beginDate,
					endDate);
			contactHistoryList.add(currContactHistory);
		}

		return contactHistoryList;
	}

	/**
	 * returns an ArrayList of all PersonHistory-Objects from database
	 * 
	 * @return ArrayList of all PersonHistory-Objects
	 * @throws SQLException
	 * @author Sebastian
	 */
	public ArrayList<PersonHistory> getAllPersonHistoriesFromDB()
			throws SQLException {
		ArrayList<PersonHistory> allPersonHistoryList = new ArrayList<PersonHistory>();

		ArrayList<ContactPersonHistory> contactList = getAllContactPersonHistoryInCollection();
		for (ContactPersonHistory currContactPersonHistory : contactList) {
			allPersonHistoryList.add(currContactPersonHistory);
		}

		ArrayList<OrgaBoxHistory> orgaBoxList = getAllOrganisationPersonHistoryInBox();
		for (OrgaBoxHistory currOrgaBoxHistory : orgaBoxList) {
			allPersonHistoryList.add(currOrgaBoxHistory);
		}

		ArrayList<OrgaCollectionHistory> orgaCollectionList = getAllOrganisationPersonHistoryInCollection();
		for (OrgaCollectionHistory currOrgaCollectionHistory : orgaCollectionList) {
			allPersonHistoryList.add(currOrgaCollectionHistory);
		}

		return allPersonHistoryList;
	}

}
