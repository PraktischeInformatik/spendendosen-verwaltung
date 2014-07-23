/**
 * @author KevinSchneider
 * this class includes all functions to export information as a CSV-file
 */

package applicationLogic;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;

import com.itextpdf.text.DocumentException;

import applicationLogicAccess.Access;

public class ExportToCsv {
	/*
	 * public static void main(String[] args) { ExportToCsv exp = new
	 * ExportToCsv();
	 * 
	 * Address adre = new Address(); adre.setHauseNumber("12");
	 * adre.setLocationName("Siegen"); adre.setStreetName("Teststra");
	 * adre.setZip(57074); Person kevin = new OrganisationPerson("Kevin",
	 * "Schneider", adre, "kevin@schneider.de", "027327888", "0175145554",
	 * "Kommentard", true); ArrayList<OrganisationPerson> orgList = new
	 * ArrayList<OrganisationPerson>(); orgList.add((OrganisationPerson) kevin);
	 * orgList.add((OrganisationPerson) kevin); orgList.add((OrganisationPerson)
	 * kevin); orgList.add((OrganisationPerson) kevin);
	 * orgList.add((OrganisationPerson) kevin);
	 * 
	 * try { exp.exportOrganisationPersons(System.getProperty("user.home") +
	 * "/Mitglieder.csv", orgList); } catch (IOException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); }
	 * 
	 * Person kevin2 = new ContactPerson("Max", "Mustermann", adre,
	 * "kevin@schder.de", "027327888", "0175144444", "Kommentar....", true);
	 * 
	 * OrganisationPerson kevinOrg = (OrganisationPerson) kevin; ContactPerson
	 * kevinCont = (ContactPerson) kevin2; Calendar da = new
	 * GregorianCalendar();
	 * 
	 * ArrayList listFixDonCol = new ArrayList(); ArrayList streetFixDonCol =
	 * new ArrayList(); DonationBox donBox = new DonationBox();
	 * ArrayList<DonationBox> listDonBox = new ArrayList<DonationBox>();
	 * listDonBox.add(donBox); FixedDonationCollection fix = new
	 * FixedDonationCollection(25, listDonBox, kevinOrg, 12345.12f, "", true,
	 * da, da, adre, kevinCont); StreetDonationCollection street = new
	 * StreetDonationCollection(17, listDonBox, kevinOrg, 10f, "test", false,
	 * da, da); listFixDonCol.add(fix); listFixDonCol.add(fix);
	 * listFixDonCol.add(street); listFixDonCol.add(fix);
	 * 
	 * try { exp.exportDonationCollections(System.getProperty("user.home") +
	 * "/Spendensammlungen2.csv", listFixDonCol); } catch (IOException e) { //
	 * TODO Auto-generated catch block e.printStackTrace(); } }
	 */

	/**
	 * Exports a CSV-file that includes a list of Organisation Persons
	 * 
	 * @author KevinSchneider
	 * @param list
	 *            a list of the Organisation Persons you want to export
	 * @param file
	 *            the path of the location you want to save the PDF-file
	 * @throws IOException
	 */
	public static void exportOrganisationPersons(String file,
			ArrayList<OrganisationPerson> list) throws IOException {

		FileWriter out = new FileWriter(file);

		Iterator<OrganisationPerson> it = list.iterator();

		// add date and time to the document
		GregorianCalendar now = new GregorianCalendar();
		DateFormat date = DateFormat.getDateInstance(DateFormat.LONG);
		DateFormat time = DateFormat.getTimeInstance(DateFormat.SHORT);
		out.append("Dieses Dokument wurde erstellt am:" + ";"
				+ date.format(now.getTime()) + ";" + "um" + ";"
				+ time.format(now.getTime()) + " Uhr.");
		out.append(System.getProperty("line.separator"));

		// add type of the list to the document
		if (PersonManager.getInstance().getCurrSearchOrgPers().getOrgPersId() == -1
				&& PersonManager.getInstance().getCurrSearchOrgPers()
						.getForename() == null
				&& PersonManager.getInstance().getCurrSearchOrgPers()
						.getLastname() == null) {
			out.append("In dieser Liste werden alle Mitglieder angezeigt."
					+ System.getProperty("line.separator"));
		} else {
			out.append("Diese Mitgliederliste wurde gefiltert nach:"
					+ System.getProperty("line.separator"));

			if (PersonManager.getInstance().getCurrSearchOrgPers()
					.getOrgPersId() != -1) {
				out.append("ID:"
						+ ';'
						+ Integer.toString(PersonManager.getInstance()
								.getCurrSearchOrgPers().getOrgPersId()));
				out.append(System.getProperty("line.separator"));
			}
			if (PersonManager.getInstance().getCurrSearchOrgPers()
					.getForename() != null) {
				out.append("Vorname: "
						+ ';'
						+ PersonManager.getInstance().getCurrSearchOrgPers()
								.getForename());
				out.append(System.getProperty("line.separator"));
			}
			if (PersonManager.getInstance().getCurrSearchOrgPers()
					.getLastname() != null) {
				out.append("Nachname: "
						+ ';'
						+ PersonManager.getInstance().getCurrSearchOrgPers()
								.getLastname());
				out.append(System.getProperty("line.separator"));
			}
		}

		out.append("ID" + ';');
		out.append("Vorname" + ';');
		out.append("Nachname" + ';');
		out.append("Adresse" + ';');
		out.append("eMail" + ';');
		out.append("Telefonnummer" + ';');
		out.append("Handynummer" + ';');
		out.append("Kommentar");
		out.append(System.getProperty("line.separator"));

		while (it.hasNext()) {
			OrganisationPerson pers = it.next();
			out.append(Integer.toString(pers.getPersonId()) + ';');
			out.append(pers.getForename() + ';');
			out.append(pers.getLastName() + ';');
			if (pers.getAddress().getHauseNumber().equals("")
					&& pers.getAddress().getLocationName().equals("")
					&& pers.getAddress().getStreetName().equals("")
					&& pers.getAddress().getZip() == 0) {
				out.append(';');
			} else {
				out.append(pers.getAddress().toStringPdf() + ';');
			}
			out.append(pers.getEmail() + ';');
			out.append(pers.getTelephoneNumber() + ';');
			out.append(pers.getMobilNumber() + ';');
			out.append(pers.getComment());
			out.append(System.getProperty("line.separator"));
		}

		out.flush();
		out.close();
	}

	/**
	 * Exports a CSV-file that includes a list of Contact Persons
	 * 
	 * @author KevinSchneider
	 * @param list
	 *            a list of the Contact Persons you want to export
	 * @param file
	 *            the path of the location you want to save the PDF-file
	 * @throws IOException
	 */
	public static void exportContactPersons(String file,
			ArrayList<ContactPerson> list) throws IOException {

		FileWriter out = new FileWriter(file);

		Iterator<ContactPerson> it = list.iterator();

		// add date and time to the document
		GregorianCalendar now = new GregorianCalendar();
		DateFormat date = DateFormat.getDateInstance(DateFormat.LONG);
		DateFormat time = DateFormat.getTimeInstance(DateFormat.SHORT);
		out.append("Dieses Dokument wurde erstellt am:" + ";"
				+ date.format(now.getTime()) + ";" + "um" + ";"
				+ time.format(now.getTime()) + " Uhr.");
		out.append(System.getProperty("line.separator"));

		// add type of the list to the document
		if (PersonManager.getInstance().getCurrSearchConPers().getConPersId() == -1
				&& PersonManager.getInstance().getCurrSearchConPers()
						.getForename() == null
				&& PersonManager.getInstance().getCurrSearchConPers()
						.getLastname() == null) {
			out.append("In dieser Liste werden alle Ansprechpartner angezeigt.");
			out.append(System.getProperty("line.separator"));
		} else {
			out.append("Diese Liste von Ansprechpartnern wurde gefiltert nach:");
			out.append(System.getProperty("line.separator"));

			if (PersonManager.getInstance().getCurrSearchConPers()
					.getConPersId() != -1) {
				out.append("ID:"
						+ ';'
						+ Integer.toString(PersonManager.getInstance()
								.getCurrSearchConPers().getConPersId()));
				out.append(System.getProperty("line.separator"));
			}
			if (PersonManager.getInstance().getCurrSearchConPers()
					.getForename() != null) {
				out.append("Vorname:"
						+ ';'
						+ PersonManager.getInstance().getCurrSearchConPers()
								.getForename());
				out.append(System.getProperty("line.separator"));
			}
			if (PersonManager.getInstance().getCurrSearchConPers()
					.getLastname() != null) {
				out.append("Nachname:"
						+ ';'
						+ PersonManager.getInstance().getCurrSearchConPers()
								.getLastname());
				out.append(System.getProperty("line.separator"));
			}
		}

		out.append("ID" + ';');
		out.append("Vorname" + ';');
		out.append("Nachname" + ';');
		out.append("Adresse" + ';');
		out.append("eMail" + ';');
		out.append("Telefonnummer" + ';');
		out.append("Handynummer" + ';');
		out.append("Kommentar");
		out.append(System.getProperty("line.separator"));

		while (it.hasNext()) {
			ContactPerson pers = it.next();
			out.append(Integer.toString(pers.getPersonId()) + ';');
			out.append(pers.getForename() + ';');
			out.append(pers.getLastName() + ';');
			if (pers.getAddress().getHauseNumber().equals("")
					&& pers.getAddress().getLocationName().equals("")
					&& pers.getAddress().getStreetName().equals("")
					&& pers.getAddress().getZip() == 0) {
				out.append(';');
			} else {
				out.append(pers.getAddress().toStringPdf() + ';');
			}
			out.append(pers.getEmail() + ';');
			out.append(pers.getTelephoneNumber() + ';');
			out.append(pers.getMobilNumber() + ';');
			out.append(pers.getComment());
			out.append(System.getProperty("line.separator"));
		}

		out.flush();
		out.close();
	}

	/**
	 * Exports a CSV-file that includes a list of Donation Collections
	 * 
	 * @author KevinSchneider
	 * @param list
	 *            a list of the Donation Collections you want to export
	 * @param file
	 *            the path of the location you want to save the PDF-file
	 * @throws IOException
	 */
	public static void exportDonationCollections(String file,
			ArrayList<DonationCollection> list) throws IOException {

		FileWriter out = new FileWriter(file);

		Iterator<DonationCollection> it = list.iterator();
		DateFormat date = DateFormat.getDateInstance(DateFormat.SHORT);

		// add date and time to the document
		GregorianCalendar now = new GregorianCalendar();
		DateFormat date2 = DateFormat.getDateInstance(DateFormat.LONG);
		DateFormat time2 = DateFormat.getTimeInstance(DateFormat.SHORT);
		out.append("Dieses Dokument wurde erstellt am:" + ";"
				+ date2.format(now.getTime()) + ";" + "um" + ";"
				+ time2.format(now.getTime()) + " Uhr.");
		out.append(System.getProperty("line.separator"));

		// add type of the list to the document
		if (DonationCollectionManager.getInstance().getCurrSearchCollection()
				.getCollId() == -1
				&& DonationCollectionManager.getInstance()
						.getCurrSearchCollection().getOrgaId() == -1
				&& DonationCollectionManager.getInstance()
						.getCurrSearchCollection().getContactId() == -1
				&& DonationCollectionManager.getInstance()
						.getCurrSearchCollection().getComment() == null
				&& DonationCollectionManager.getInstance()
						.getCurrSearchCollection().getBoxId() == -1
				&& DonationCollectionManager.getInstance()
						.getCurrSearchCollection().getZip() == -1
				&& DonationCollectionManager.getInstance()
						.getCurrSearchCollection().getCity() == null
				&& DonationCollectionManager.getInstance()
						.getCurrSearchCollection().getBeginStart() == null
				&& DonationCollectionManager.getInstance()
						.getCurrSearchCollection().getBeginEnd() == null
				&& DonationCollectionManager.getInstance()
						.getCurrSearchCollection().getEndStart() == null
				&& DonationCollectionManager.getInstance()
						.getCurrSearchCollection().getEndEnd() == null
				&& DonationCollectionManager.getInstance()
						.getCurrSearchCollection().getShowStreet() == true
				&& DonationCollectionManager.getInstance()
						.getCurrSearchCollection().getShowFixed() == true) {
			out.append("In dieser Liste werden alle Spendensammlungen angezeigt.");
			out.append(System.getProperty("line.separator"));
		} else {
			out.append("Diese Liste von Spendensammlungen wurde nach folgenden Kriterien gefiltert:");
			out.append(System.getProperty("line.separator"));

			if (DonationCollectionManager.getInstance()
					.getCurrSearchCollection().getShowStreet() == false) {
				out.append("Es werden keine Straßensammlungen angezeigt.");
				out.append(System.getProperty("line.separator"));
			}
			if (DonationCollectionManager.getInstance()
					.getCurrSearchCollection().getShowFixed() == false) {
				out.append("Es werden keine festen Sammlungen angezeigt.");
				out.append(System.getProperty("line.separator"));
			}
			if (DonationCollectionManager.getInstance()
					.getCurrSearchCollection().getCollId() != -1) {
				out.append("ID:"
						+ ';'
						+ Integer.toString(DonationCollectionManager
								.getInstance().getCurrSearchCollection()
								.getCollId()));
				out.append(System.getProperty("line.separator"));
			}
			if (DonationCollectionManager.getInstance()
					.getCurrSearchCollection().getOrgaId() != -1) {
				out.append("Verantwortliches Mitglied (ID):"
						+ ';'
						+ Integer.toString(DonationCollectionManager
								.getInstance().getCurrSearchCollection()
								.getOrgaId()));
				out.append(System.getProperty("line.separator"));
			}
			if (DonationCollectionManager.getInstance()
					.getCurrSearchCollection().getContactId() != -1) {
				out.append("Kontaktperson (ID): "
						+ ';'
						+ Integer.toString(DonationCollectionManager
								.getInstance().getCurrSearchCollection()
								.getContactId()));
				out.append(System.getProperty("line.separator"));
			}
			if (DonationCollectionManager.getInstance()
					.getCurrSearchCollection().getComment() != null) {
				out.append("Kommentar: "
						+ ';'
						+ DonationCollectionManager.getInstance()
								.getCurrSearchCollection().getComment());
				out.append(System.getProperty("line.separator"));
			}
			if (DonationCollectionManager.getInstance()
					.getCurrSearchCollection().getBoxId() != -1) {
				out.append("In der Sammlung enthaltene Dose (ID): "
						+ ';'
						+ Integer.toString(DonationCollectionManager
								.getInstance().getCurrSearchCollection()
								.getBoxId()));
				out.append(System.getProperty("line.separator"));
			}
			if (DonationCollectionManager.getInstance()
					.getCurrSearchCollection().getZip() != -1) {
				out.append("Postleitzahl: "
						+ ';'
						+ Integer.toString(DonationCollectionManager
								.getInstance().getCurrSearchCollection()
								.getZip()));
				out.append(System.getProperty("line.separator"));
			}
			if (DonationCollectionManager.getInstance()
					.getCurrSearchCollection().getCity() != null) {
				out.append("Stadt: "
						+ ';'
						+ DonationCollectionManager.getInstance()
								.getCurrSearchCollection().getCity());
				out.append(System.getProperty("line.separator"));
			}
			if (DonationCollectionManager.getInstance()
					.getCurrSearchCollection().getBeginStart() != null
					|| DonationCollectionManager.getInstance()
							.getCurrSearchCollection().getBeginEnd() != null) {
				if (DonationCollectionManager.getInstance()
						.getCurrSearchCollection().getBeginStart() != null
						&& DonationCollectionManager.getInstance()
								.getCurrSearchCollection().getBeginEnd() != null) {
					out.append("Startzeitraum der Sammlung:"
							+ ';'
							+ "Von"
							+ ';'
							+ date.format(DonationCollectionManager
									.getInstance().getCurrSearchCollection()
									.getBeginStart())
							+ ';'
							+ "bis"
							+ ';'
							+ date.format(DonationCollectionManager
									.getInstance().getCurrSearchCollection()
									.getEndStart()));
					out.append(System.getProperty("line.separator"));
				} else if (DonationCollectionManager.getInstance()
						.getCurrSearchCollection().getBeginStart() != null) {
					out.append("Es werden nur Sammlungen angezeigt, die ab dem"
							+ ';'
							+ date.format(DonationCollectionManager
									.getInstance().getCurrSearchCollection()
									.getBeginStart()) + ';' + "begonnen haben.");
					out.append(System.getProperty("line.separator"));
				} else if (DonationCollectionManager.getInstance()
						.getCurrSearchCollection().getBeginEnd() != null) {
					out.append("Es werden nur Sammlungen angezeigt, die bis zum"
							+ ';'
							+ date.format(DonationCollectionManager
									.getInstance().getCurrSearchCollection()
									.getBeginEnd()) + ';' + "begonnen haben.");
					out.append(System.getProperty("line.separator"));
				}

			}

			if (DonationCollectionManager.getInstance()
					.getCurrSearchCollection().getEndStart() != null
					|| DonationCollectionManager.getInstance()
							.getCurrSearchCollection().getEndEnd() != null) {
				if (DonationCollectionManager.getInstance()
						.getCurrSearchCollection().getEndStart() != null
						&& DonationCollectionManager.getInstance()
								.getCurrSearchCollection().getEndEnd() != null) {
					out.append("Endzeitraum der Sammlung:"
							+ ';'
							+ "Von"
							+ ';'
							+ date.format(DonationCollectionManager
									.getInstance().getCurrSearchCollection()
									.getEndStart())
							+ ';'
							+ "bis"
							+ ';'
							+ date.format(DonationCollectionManager
									.getInstance().getCurrSearchCollection()
									.getEndEnd()));
					out.append(System.getProperty("line.separator"));
				} else if (DonationCollectionManager.getInstance()
						.getCurrSearchCollection().getEndStart() != null) {
					out.append("Es werden nur Sammlungen angezeigt, die ab dem"
							+ ';'
							+ date.format(DonationCollectionManager
									.getInstance().getCurrSearchCollection()
									.getEndStart()) + ';' + "geendet haben.");
					out.append(System.getProperty("line.separator"));
				} else if (DonationCollectionManager.getInstance()
						.getCurrSearchCollection().getEndEnd() != null) {
					out.append("Es werden nur Sammlungen angezeigt, die bis zum"
							+ ';'
							+ date.format(DonationCollectionManager
									.getInstance().getCurrSearchCollection()
									.getEndEnd()) + ';' + "geendet haben.");
					out.append(System.getProperty("line.separator"));
				}

			}
		}

		out.append("ID" + ';');
		out.append("zugehörige Boxen (ID)" + ';');
		out.append("verantwortliche Person" + ';');
		out.append("Summe" + ';');
		out.append("beendet?" + ';');
		out.append("Zeitraum" + ';');
		out.append("Adresse" + ';');
		out.append("Kontaktperson");
		out.append(System.getProperty("line.separator"));

		while (it.hasNext()) {
			DonationCollection don = it.next();
			out.append(Integer.toString(don.getCollectionId()) + ';');
			ArrayList<Integer> donBoxIds = new ArrayList<Integer>();
			Iterator<DonationBox> dIt = don.getDonationboxes().iterator();
			while (dIt.hasNext()) {
				donBoxIds.add(dIt.next().getBoxId());
			}
			Iterator<Integer> iIt = donBoxIds.iterator();
			String donBoxIdsString = "";
			while (iIt.hasNext()) {
				donBoxIdsString += Integer.toString(iIt.next());
				donBoxIdsString += ", ";
			}
			if (donBoxIdsString.length() >= 2) {
				donBoxIdsString = donBoxIdsString.substring(0,
						donBoxIdsString.length() - 2);
			}
			out.append(donBoxIdsString + ';');
			out.append(don.getOrganizationPerson().toStringPdf() + ';');
			out.append(Float.toString(don.getSum()) + ';');
			String isCompleted = null;
			if (don.getIsCompleted()) {
				isCompleted = "Ja";
			} else
				isCompleted = "Nein";
			out.append(isCompleted + ';');
			if (don.getEndPeriode() != null) {
				out.append(date.format(don.getBeginPeriode().getTime()) + " - "
						+ date.format(don.getEndPeriode().getTime()) + ';');
			} else {
				out.append(date.format(don.getBeginPeriode().getTime()) + ';');
			}

			if (don.getClass().getName()
					.equals("applicationLogic.FixedDonationCollection")) {
				FixedDonationCollection fDon = (FixedDonationCollection) don;
				out.append(fDon.getFixedAddress().toStringPdf() + ';');
				out.append(fDon.getContactPerson().toStringPdf() + ';');
			} else {
				out.append("-" + ';');
				out.append("-" + ';');
			}
			out.append(System.getProperty("line.separator"));
		}

		out.flush();
		out.close();
	}

	/**
	 * Exports a CSV-file that includes a list of Donation Boxes
	 * 
	 * @author KevinSchneider
	 * @param list
	 *            a list of the Donation Boxes you want to export
	 * @param file
	 *            the path of the location you want to save the PDF-file
	 * @throws IOException
	 * @throws ParseException
	 * @throws SQLException
	 */
	public static void exportDonationBoxes(String file,
			ArrayList<DonationBox> list) throws IOException, SQLException,
			ParseException {

		FileWriter out = new FileWriter(file);

		Iterator<DonationBox> it = list.iterator();

		// add date and time to the document
		GregorianCalendar now = new GregorianCalendar();
		DateFormat date = DateFormat.getDateInstance(DateFormat.LONG);
		DateFormat time = DateFormat.getTimeInstance(DateFormat.SHORT);
		out.append("Dieses Dokument wurde erstellt am:" + ";"
				+ date.format(now.getTime()) + ";" + "um" + ";"
				+ time.format(now.getTime()) + " Uhr.");
		out.append(System.getProperty("line.separator"));

		// add type of the list to the document
		if (DonationBoxManager.getInstance().getCurrSearchBox().getBoxId() == -1
				&& DonationBoxManager.getInstance().getCurrSearchBox()
						.getResponsiblePersonId() == -1
				&& DonationBoxManager.getInstance().getCurrSearchBox()
						.getComment() == null
				&& DonationBoxManager.getInstance().getCurrSearchBox()
						.getCollectionId() == -1
				&& DonationBoxManager.getInstance().getCurrSearchBox()
						.getDate() == null
				&& DonationBoxManager.getInstance().getCurrSearchBox()
						.isInUseYes() == true
				&& DonationBoxManager.getInstance().getCurrSearchBox()
						.isInUseNo() == true) {
			out.append("Es werden alle Spendendosen angezeigt.");
			out.append(System.getProperty("line.separator"));
		} else {
			out.append("Die Liste von Spendendosen wurde nach folgenden Kriterien gefiltert:");
			out.append(System.getProperty("line.separator"));

			if (DonationBoxManager.getInstance().getCurrSearchBox()
					.isInUseYes() == false) {
				out.append("Spendendosen, die in Benutzung sind werden nicht angezeigt.");
				out.append(System.getProperty("line.separator"));
			}
			if (DonationBoxManager.getInstance().getCurrSearchBox().isInUseNo() == false) {
				out.append("Spendendosen, die nicht in Benutzung sind werden nicht angezeigt.");
				out.append(System.getProperty("line.separator"));
			}
			if (DonationBoxManager.getInstance().getCurrSearchBox().getBoxId() != -1) {
				out.append("ID: "
						+ ';'
						+ Integer.toString(DonationBoxManager.getInstance()
								.getCurrSearchBox().getBoxId()));
				out.append(System.getProperty("line.separator"));
			}
			if (DonationBoxManager.getInstance().getCurrSearchBox()
					.getResponsiblePersonId() != -1) {
				out.append("verantwortliches Mitglied (ID): "
						+ ';'
						+ Integer.toString(DonationBoxManager.getInstance()
								.getCurrSearchBox().getResponsiblePersonId()));
				out.append(System.getProperty("line.separator"));
			}
			if (DonationBoxManager.getInstance().getCurrSearchBox()
					.getComment() != null) {
				out.append("Kommentar: "
						+ ';'
						+ DonationBoxManager.getInstance().getCurrSearchBox()
								.getComment());
				out.append(System.getProperty("line.separator"));
			}
			if (DonationBoxManager.getInstance().getCurrSearchBox()
					.getCollectionId() != -1) {
				out.append("zugehörige Sammlung (ID): "
						+ ';'
						+ Integer.toString(DonationBoxManager.getInstance()
								.getCurrSearchBox().getCollectionId()));
				out.append(System.getProperty("line.separator"));
			}
			if (DonationBoxManager.getInstance().getCurrSearchBox().getDate() != null) {
				out.append("Leerungsdatum: "
						+ ';'
						+ date.format(DonationBoxManager.getInstance()
								.getCurrSearchBox().getDate()));
				out.append(System.getProperty("line.separator"));
			}
		}

		out.append("ID" + ';');
		out.append("Summe" + ';');
		out.append("Status" + ';');
		out.append("Kommentar" + ';');
		out.append("verantwortliche Person" + ';');
		out.append(System.getProperty("line.separator"));

		while (it.hasNext()) {
			DonationBox donBox = it.next();
			out.append(Integer.toString(donBox.getBoxId()) + ';');
			out.append(Float.toString(Access.calculateSumByBoxId(donBox
					.getBoxId())) + ';');
			String status;
			if (donBox.getIsDeleted() == true) {
				status = "gelöscht";
			} else {
				if (donBox.isInUse()) {
					status = "in Benutzung";
				} else
					status = "nicht in Benutzung";
			}
			out.append(status + ';');
			out.append(donBox.getComment() + ';');
			if (donBox.getCurrentResponsiblePerson() == null) {
				out.append("-" + ';');
			} else {
				out.append(donBox.getCurrentResponsiblePerson().toStringPdf() + ';');
			}
			out.append(System.getProperty("line.separator"));
		}

		out.flush();
		out.close();
	}
}
