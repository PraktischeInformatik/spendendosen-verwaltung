/**
 * @author KevinSchneider
 * this class includes all functions to export information as a PDF-file
 */

package applicationLogic;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;

import applicationLogicAccess.Access;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class ExportToPdf {

	/*
	public static void main(String[] args) {
		DonationCollection don = null;
		DonationBox donB = new DonationBox();
		ArrayList<DonationBox> li = new ArrayList<DonationBox>();
		li.add(donB);
		Address ad = new Address("Straße", "Hausnummer", 57280, "Siegen");
		OrganisationPerson orgP = new OrganisationPerson("Vorname", "Nachname",
				ad, "a@b.de", "02732", "", "", true);
		ContactPerson conP = new ContactPerson("V", "N", ad, "a@b.e",
				"2343254", "098982374", "", true);

		try {
			don = new FixedDonationCollection(101, li, 100f, "", true, orgP,
					"2000.01.01", "2000.01.01", ad, conP);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			exportFinishCollection(don, System.getProperty("user.home")
					+ "/finish.pdf");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
*/	


	// TODO Datum bei abgeschlossener Sammlung

	// TODO TempListen aktuell halten und richtige Listen an Export übergeben!
	// (getTemp...)

	/**
	 * Exports a PDF-file that includes a list of Organisation Persons
	 * 
	 * @author KevinSchneider
	 * @param list a list of the Organisation Persons you want to export
	 * @param file the path of the location you want to save the PDF-file
	 * @throws DocumentException
	 * @throws FileNotFoundException
	 */
	public static void exportOrganisationPersonstoPdf(
			ArrayList<OrganisationPerson> list, String file)
			throws FileNotFoundException, DocumentException {

		Document document = new Document(PageSize.A4.rotate());

		PdfWriter.getInstance(document, new FileOutputStream(file));

		document.open();

		// add a headline to the document
		Paragraph headline = new Paragraph();
		headline.add(new Paragraph("S-Kollekt", new Font(
				Font.FontFamily.UNDEFINED, 18, Font.UNDERLINE)));
		headline.setAlignment(Element.ALIGN_CENTER);
		headline.add(Chunk.NEWLINE);
		headline.add(Chunk.NEWLINE);

		// add date and time to the document
		GregorianCalendar now = new GregorianCalendar();
		DateFormat date = DateFormat.getDateInstance(DateFormat.LONG);
		DateFormat time = DateFormat.getTimeInstance(DateFormat.SHORT);
		headline.add(new Phrase("Dieses Dokument wurde erstellt am: "
				+ date.format(now.getTime()) + " um "
				+ time.format(now.getTime()) + " Uhr."));
		headline.add(Chunk.NEWLINE);
		headline.add(Chunk.NEWLINE);

		document.add(headline);

		// add type of the list to the document
		document.add(new Paragraph("Liste der Organisationsmitglieder", new Font(Font.FontFamily.UNDEFINED, 12, Font.BOLD)));

		if (PersonManager.getInstance().getCurrSearchOrgPers().getOrgPersId() == -1
				&& PersonManager.getInstance().getCurrSearchOrgPers()
						.getForename() == null
				&& PersonManager.getInstance().getCurrSearchOrgPers()
						.getLastname() == null) {
			document.add(new Paragraph(new Chunk(
					"In dieser Liste werden alle Mitglieder angezeigt.")));
		} else {
			document.add(new Paragraph(new Chunk(
					"Diese Mitgliederliste wurde gefiltert nach:")));

			if (PersonManager.getInstance().getCurrSearchOrgPers()
					.getOrgPersId() != -1) {
				document.add(new Paragraph(new Chunk("ID: "
						+ Integer.toString(PersonManager.getInstance()
								.getCurrSearchOrgPers().getOrgPersId()))));
			}
			if (PersonManager.getInstance().getCurrSearchOrgPers()
					.getForename() != null) {
				document.add(new Paragraph(new Chunk("Vorname: "
						+ PersonManager.getInstance().getCurrSearchOrgPers()
								.getForename())));
			}
			if (PersonManager.getInstance().getCurrSearchOrgPers()
					.getLastname() != null) {
				document.add(new Paragraph(new Chunk("Nachname: "
						+ PersonManager.getInstance().getCurrSearchOrgPers()
								.getLastname())));
			}
		}

		// creating empty table
		PdfPTable table = new PdfPTable(8); // TODO check number of columns
		table.setWidthPercentage(100);

		table.setWidths(new int[] { 1, 2, 2, 3, 2, 2, 2, 3 });

		table.setSpacingBefore(10f);
		table.setSpacingAfter(10f);
		PdfPCell cell1 = new PdfPCell(new Paragraph("ID"));
		PdfPCell cell2 = new PdfPCell(new Paragraph("Vorname"));
		PdfPCell cell3 = new PdfPCell(new Paragraph("Nachname"));
		PdfPCell cell4 = new PdfPCell(new Paragraph("Adresse"));
		PdfPCell cell5 = new PdfPCell(new Paragraph("eMail"));
		PdfPCell cell6 = new PdfPCell(new Paragraph("Telefonnummer"));
		PdfPCell cell7 = new PdfPCell(new Paragraph("Handynummer"));
		PdfPCell cell8 = new PdfPCell(new Paragraph("Kommentar"));
		table.addCell(cell1);
		table.addCell(cell2);
		table.addCell(cell3);
		table.addCell(cell4);
		table.addCell(cell5);
		table.addCell(cell6);
		table.addCell(cell7);
		table.addCell(cell8);

		// filling the table with the list
		Iterator<OrganisationPerson> it = list.iterator();
		while (it.hasNext()) {
			Person pers = (Person) it.next();
			PdfPCell cellP1 = new PdfPCell(new Paragraph(Integer.toString(pers
					.getPersonId())));
			PdfPCell cellP2 = new PdfPCell(new Paragraph(pers.getForename()));
			PdfPCell cellP3 = new PdfPCell(new Paragraph(pers.getLastName()));
			PdfPCell cellP4;
			if (pers.getAddress().getHauseNumber().equals("")
					&& pers.getAddress().getLocationName().equals("")
					&& pers.getAddress().getStreetName().equals("")
					&& pers.getAddress().getZip() == 0) {
				cellP4 = new PdfPCell(new Paragraph("-"));
			} else {
				cellP4 = new PdfPCell(new Paragraph(pers.getAddress()
						.toStringPdf()));
			}
			PdfPCell cellP5 = new PdfPCell(new Paragraph(pers.getEmail()));
			PdfPCell cellP6 = new PdfPCell(new Paragraph(
					pers.getTelephoneNumber()));
			PdfPCell cellP7 = new PdfPCell(new Paragraph(pers.getMobilNumber()));
			PdfPCell cellP8 = new PdfPCell(new Paragraph(pers.getComment()));

			table.addCell(cellP1);
			table.addCell(cellP2);
			table.addCell(cellP3);
			table.addCell(cellP4);
			table.addCell(cellP5);
			table.addCell(cellP6);
			table.addCell(cellP7);
			table.addCell(cellP8);
		}

		document.add(table);

		document.close();
	}

	/**
	 * Exports a PDF-file that includes a list of Contact Persons
	 * 
	 * @author KevinSchneider
	 * @param list a list of the Contact Persons you want to export
	 * @param file the path of the location you want to save the PDF-file
	 * @throws DocumentException
	 * @throws FileNotFoundException
	 */
	public static void exportContactPersonsToPdf(ArrayList<ContactPerson> list,
			String file) throws FileNotFoundException, DocumentException {

		Document document = new Document(PageSize.A4.rotate());
		PdfWriter.getInstance(document, new FileOutputStream(file));

		document.open();

		// add a headline to the document
		Paragraph headline = new Paragraph();
		headline.add(new Paragraph("S-Kollekt", new Font(
				Font.FontFamily.UNDEFINED, 18, Font.UNDERLINE)));
		headline.setAlignment(Element.ALIGN_CENTER);
		headline.add(Chunk.NEWLINE);
		headline.add(Chunk.NEWLINE);

		// add date and time to the document
		GregorianCalendar now = new GregorianCalendar();
		DateFormat date = DateFormat.getDateInstance(DateFormat.LONG);
		DateFormat time = DateFormat.getTimeInstance(DateFormat.SHORT);
		headline.add(new Phrase("Dieses Dokument wurde erstellt am: "
				+ date.format(now.getTime()) + " um "
				+ time.format(now.getTime()) + " Uhr."));
		headline.add(Chunk.NEWLINE);
		headline.add(Chunk.NEWLINE);

		document.add(headline);

		// add type of the list to the document
		document.add(new Paragraph("Liste der Kontaktpersonen", new Font(Font.FontFamily.UNDEFINED, 12, Font.BOLD)));

		if (PersonManager.getInstance().getCurrSearchConPers().getConPersId() == -1
				&& PersonManager.getInstance().getCurrSearchConPers()
						.getForename() == null
				&& PersonManager.getInstance().getCurrSearchConPers()
						.getLastname() == null) {
			document.add(new Paragraph(new Chunk(
					"In dieser Liste werden alle Ansprechpartner angezeigt.")));
		} else {
			document.add(new Paragraph(new Chunk(
					"Diese Liste von Ansprechpartnern wurde gefiltert nach:")));

			if (PersonManager.getInstance().getCurrSearchConPers()
					.getConPersId() != -1) {
				document.add(new Paragraph(new Chunk("ID: "
						+ Integer.toString(PersonManager.getInstance()
								.getCurrSearchConPers().getConPersId()))));
			}
			if (PersonManager.getInstance().getCurrSearchConPers()
					.getForename() != null) {
				document.add(new Paragraph(new Chunk("Vorname: "
						+ PersonManager.getInstance().getCurrSearchConPers()
								.getForename())));
			}
			if (PersonManager.getInstance().getCurrSearchConPers()
					.getLastname() != null) {
				document.add(new Paragraph(new Chunk("Nachname: "
						+ PersonManager.getInstance().getCurrSearchConPers()
								.getLastname())));
			}
		}

		// creating empty table
		PdfPTable table = new PdfPTable(8); // TODO check number of columns
		table.setWidthPercentage(100);

		table.setWidths(new int[] { 1, 2, 2, 3, 2, 2, 2, 3 });

		table.setSpacingBefore(10f);
		table.setSpacingAfter(10f);
		PdfPCell cell1 = new PdfPCell(new Paragraph("ID"));
		PdfPCell cell2 = new PdfPCell(new Paragraph("Vorname"));
		PdfPCell cell3 = new PdfPCell(new Paragraph("Nachname"));
		PdfPCell cell4 = new PdfPCell(new Paragraph("Adresse"));
		PdfPCell cell5 = new PdfPCell(new Paragraph("eMail"));
		PdfPCell cell6 = new PdfPCell(new Paragraph("Telefonnummer"));
		PdfPCell cell7 = new PdfPCell(new Paragraph("Handynummer"));
		PdfPCell cell8 = new PdfPCell(new Paragraph("Kommentar"));
		table.addCell(cell1);
		table.addCell(cell2);
		table.addCell(cell3);
		table.addCell(cell4);
		table.addCell(cell5);
		table.addCell(cell6);
		table.addCell(cell7);
		table.addCell(cell8);

		// filling the table with the list
		Iterator<ContactPerson> it = list.iterator();
		while (it.hasNext()) {
			Person pers = (Person) it.next();
			PdfPCell cellP1 = new PdfPCell(new Paragraph(Integer.toString(pers
					.getPersonId())));
			PdfPCell cellP2 = new PdfPCell(new Paragraph(pers.getForename()));
			PdfPCell cellP3 = new PdfPCell(new Paragraph(pers.getLastName()));
			PdfPCell cellP4;
			if (pers.getAddress().getHauseNumber().equals("")
					&& pers.getAddress().getLocationName().equals("")
					&& pers.getAddress().getStreetName().equals("")
					&& pers.getAddress().getZip() == 0) {
				cellP4 = new PdfPCell(new Paragraph("-"));
			} else {
				cellP4 = new PdfPCell(new Paragraph(pers.getAddress()
						.toStringPdf()));
			}
			PdfPCell cellP5 = new PdfPCell(new Paragraph(pers.getEmail()));
			PdfPCell cellP6 = new PdfPCell(new Paragraph(
					pers.getTelephoneNumber()));
			PdfPCell cellP7 = new PdfPCell(new Paragraph(pers.getMobilNumber()));
			PdfPCell cellP8 = new PdfPCell(new Paragraph(pers.getComment()));

			table.addCell(cellP1);
			table.addCell(cellP2);
			table.addCell(cellP3);
			table.addCell(cellP4);
			table.addCell(cellP5);
			table.addCell(cellP6);
			table.addCell(cellP7);
			table.addCell(cellP8);
		}

		document.add(table);

		document.close();
	}

	/**
	 * Exports a PDF-file that includes a list of Donation Collections
	 * 
	 * @author KevinSchneider
	 * @param list a list of the Donation Collections you want to export
	 * @param file the path of the location you want to save the PDF-file
	 * @throws DocumentException
	 * @throws FileNotFoundException
	 */
	public static void exportDonationCollectionsToPdf(
			ArrayList<DonationCollection> list, String file)
			throws DocumentException, FileNotFoundException, SQLException,
			ParseException {

		Document document = new Document(PageSize.A4.rotate());

		PdfWriter.getInstance(document, new FileOutputStream(file));

		document.open();

		// add a headline to the document
		Paragraph headline = new Paragraph();
		headline.add(new Paragraph("S-Kollekt", new Font(
				Font.FontFamily.UNDEFINED, 18, Font.UNDERLINE)));
		headline.setAlignment(Element.ALIGN_CENTER);
		headline.add(Chunk.NEWLINE);
		headline.add(Chunk.NEWLINE);

		// add date and time to the document
		GregorianCalendar now = new GregorianCalendar();
		DateFormat date = DateFormat.getDateInstance(DateFormat.LONG);
		DateFormat time = DateFormat.getTimeInstance(DateFormat.SHORT);
		headline.add(new Phrase("Dieses Dokument wurde erstellt am: "
				+ date.format(now.getTime()) + " um "
				+ time.format(now.getTime()) + " Uhr."));
		headline.add(Chunk.NEWLINE);
		headline.add(Chunk.NEWLINE);

		document.add(headline);

		// add type of the list to the document
		document.add(new Paragraph("Liste der Spendensammlungen", new Font(Font.FontFamily.UNDEFINED, 12, Font.BOLD)));

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
			document.add(new Paragraph(new Chunk(
					"In dieser Liste werden alle Spendensammlungen angezeigt.")));
		} else {
			document.add(new Paragraph(
					new Chunk(
							"Diese Liste von Spendensammlungen wurde nach folgenden Kriterien gefiltert:")));

			if (DonationCollectionManager.getInstance()
					.getCurrSearchCollection().getShowStreet() == false) {
				document.add(new Paragraph(new Chunk(
						"Es werden keine Straßensammlungen angezeigt.")));
			}
			if (DonationCollectionManager.getInstance()
					.getCurrSearchCollection().getShowFixed() == false) {
				document.add(new Paragraph(new Chunk(
						"Es werden keine festen Sammlungen angezeigt.")));
			}
			if (DonationCollectionManager.getInstance()
					.getCurrSearchCollection().getCollId() != -1) {
				document.add(new Paragraph(new Chunk("ID: "
						+ Integer.toString(DonationCollectionManager
								.getInstance().getCurrSearchCollection()
								.getCollId()))));
			}
			if (DonationCollectionManager.getInstance()
					.getCurrSearchCollection().getOrgaId() != -1) {
				document.add(new Paragraph(
						new Chunk("Verantwortliches Mitglied (ID): "
								+ Integer.toString(DonationCollectionManager
										.getInstance()
										.getCurrSearchCollection().getOrgaId()))));
			}
			if (DonationCollectionManager.getInstance()
					.getCurrSearchCollection().getContactId() != -1) {
				document.add(new Paragraph(new Chunk("Kontaktperson (ID): "
						+ Integer.toString(DonationCollectionManager
								.getInstance().getCurrSearchCollection()
								.getContactId()))));
			}
			if (DonationCollectionManager.getInstance()
					.getCurrSearchCollection().getComment() != null) {
				document.add(new Paragraph(new Chunk("Kommentar: "
						+ DonationCollectionManager.getInstance()
								.getCurrSearchCollection().getComment())));
			}
			if (DonationCollectionManager.getInstance()
					.getCurrSearchCollection().getBoxId() != -1) {
				document.add(new Paragraph(new Chunk(
						"In der Sammlung enthaltene Dose (ID): "
								+ Integer.toString(DonationCollectionManager
										.getInstance()
										.getCurrSearchCollection().getBoxId()))));
			}
			if (DonationCollectionManager.getInstance()
					.getCurrSearchCollection().getZip() != -1) {
				document.add(new Paragraph(new Chunk("Postleitzahl: "
						+ Integer.toString(DonationCollectionManager
								.getInstance().getCurrSearchCollection()
								.getZip()))));
			}
			if (DonationCollectionManager.getInstance()
					.getCurrSearchCollection().getCity() != null) {
				document.add(new Paragraph(new Chunk("Stadt: "
						+ DonationCollectionManager.getInstance()
								.getCurrSearchCollection().getCity())));
			}
			if (DonationCollectionManager.getInstance()
					.getCurrSearchCollection().getBeginStart() != null
					|| DonationCollectionManager.getInstance()
							.getCurrSearchCollection().getBeginEnd() != null) {
				if (DonationCollectionManager.getInstance()
						.getCurrSearchCollection().getBeginStart() != null
						&& DonationCollectionManager.getInstance()
								.getCurrSearchCollection().getBeginEnd() != null) {
					document.add(new Paragraph(new Chunk(
							"Startzeitraum der Sammlung: Von "
									+ date.format(DonationCollectionManager
											.getInstance()
											.getCurrSearchCollection()
											.getBeginStart())
									+ " bis "
									+ date.format(DonationCollectionManager
											.getInstance()
											.getCurrSearchCollection()
											.getBeginEnd()))));
				} else if (DonationCollectionManager.getInstance()
						.getCurrSearchCollection().getBeginStart() != null) {
					document.add(new Paragraph(new Chunk(
							"Es werden nur Sammlungen angezeigt, die ab dem "
									+ date.format(DonationCollectionManager
											.getInstance()
											.getCurrSearchCollection()
											.getBeginStart())
									+ " begonnen haben.")));
				} else if (DonationCollectionManager.getInstance()
						.getCurrSearchCollection().getBeginEnd() != null) {
					document.add(new Paragraph(new Chunk(
							"Es werden nur Sammlungen angezeigt, die bis zum "
									+ date.format(DonationCollectionManager
											.getInstance()
											.getCurrSearchCollection()
											.getBeginEnd())
									+ " begonnen haben.")));
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
					document.add(new Paragraph(new Chunk(
							"Endzeitraum der Sammlung: Von "
									+ date.format(DonationCollectionManager
											.getInstance()
											.getCurrSearchCollection()
											.getEndStart())
									+ " bis "
									+ date.format(DonationCollectionManager
											.getInstance()
											.getCurrSearchCollection()
											.getEndEnd()))));
				} else if (DonationCollectionManager.getInstance()
						.getCurrSearchCollection().getEndStart() != null) {
					document.add(new Paragraph(
							new Chunk(
									"Es werden nur Sammlungen angezeigt, die ab dem "
											+ date.format(DonationCollectionManager
													.getInstance()
													.getCurrSearchCollection()
													.getEndStart())
											+ " geendet haben.")));
				} else if (DonationCollectionManager.getInstance()
						.getCurrSearchCollection().getEndEnd() != null) {
					document.add(new Paragraph(new Chunk(
							"Es werden nur Sammlungen angezeigt, die bis zum "
									+ date.format(DonationCollectionManager
											.getInstance()
											.getCurrSearchCollection()
											.getEndEnd()) + " geendet haben.")));
				}

			}
		}

		// creating empty table
		PdfPTable table = new PdfPTable(8); // TODO check number of columns
		table.setWidthPercentage(100);

		table.setWidths(new int[] { 1, 2, 3, 2, 1, 3, 3, 3 });

		table.setSpacingBefore(10f);
		table.setSpacingAfter(10f);
		PdfPCell cell1 = new PdfPCell(new Paragraph("ID"));
		PdfPCell cell2 = new PdfPCell(new Paragraph("zugehörige Boxen (ID)"));
		PdfPCell cell3 = new PdfPCell(new Paragraph("verantwortliche Person"));
		PdfPCell cell4 = new PdfPCell(new Paragraph("Summe"));
		PdfPCell cell5 = new PdfPCell(new Paragraph("beendet?"));
		PdfPCell cell6 = new PdfPCell(new Paragraph("Zeitraum"));
		PdfPCell cell7 = new PdfPCell(new Paragraph("Adresse"));
		PdfPCell cell8 = new PdfPCell(new Paragraph("Kontaktperson"));
		table.addCell(cell1);
		table.addCell(cell2);
		table.addCell(cell3);
		table.addCell(cell4);
		table.addCell(cell5);
		table.addCell(cell6);
		table.addCell(cell7);
		table.addCell(cell8);

		// filling the table with the list
		Iterator<DonationCollection> it = list.iterator();
		while (it.hasNext()) {
			DonationCollection donation = (DonationCollection) it.next();
			PdfPCell cellP1 = new PdfPCell(new Paragraph(
					Integer.toString(donation.getCollectionId())));
			ArrayList<Integer> donBoxIds = new ArrayList<Integer>();
			Iterator<DonationBox> dIt = donation.getDonationboxes().iterator();
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
			PdfPCell cellP2 = new PdfPCell(new Paragraph(donBoxIdsString));
			PdfPCell cellP3 = new PdfPCell(new Paragraph(donation
					.getOrganizationPerson().toStringPdf()));
			PdfPCell cellP4 = new PdfPCell(new Paragraph(
					Float.toString(donation.getSum()))); // edit by
															// sz
			String isCompleted = null;
			if (donation.getIsCompleted()) {
				isCompleted = "Ja";
			} else
				isCompleted = "Nein";
			PdfPCell cellP5 = new PdfPCell(new Paragraph(isCompleted));
			date = DateFormat.getDateInstance(DateFormat.SHORT);
			PdfPCell cellP6;
			if (donation.getEndPeriode() != null) {
				cellP6 = new PdfPCell(new Paragraph(date.format(donation
						.getBeginPeriode().getTime())
						+ " - "
						+ date.format(donation.getEndPeriode().getTime())));
			} else {
				cellP6 = new PdfPCell(new Paragraph(date.format(donation
						.getBeginPeriode().getTime())));
			}

			PdfPCell cellP7;
			PdfPCell cellP8;
			if (donation.getClass().getName()
					.equals("applicationLogic.FixedDonationCollection")) {
				FixedDonationCollection fDon = (FixedDonationCollection) donation;
				cellP7 = new PdfPCell(new Paragraph(fDon.getFixedAddress()
						.toStringPdf()));
				cellP8 = new PdfPCell(new Paragraph(fDon.getContactPerson()
						.toStringPdf()));
			} else {
				cellP7 = new PdfPCell(new Paragraph("-"));
				cellP8 = new PdfPCell(new Paragraph("-"));
			}

			table.addCell(cellP1);
			table.addCell(cellP2);
			table.addCell(cellP3);
			table.addCell(cellP4);
			table.addCell(cellP5);
			table.addCell(cellP6);
			table.addCell(cellP7);
			table.addCell(cellP8);
		}

		document.add(table);

		document.close();
	}

	/**
	 * Exports a PDF-file that includes a list of Donation Boxes
	 * 
	 * @author KevinSchneider
	 * @param list a list of the Donation Boxes you want to export
	 * @param file the path of the location you want to save the PDF-file
	 * @throws DocumentException
	 * @throws FileNotFoundException
	 * @throws ParseException
	 * @throws SQLException
	 */
	public static void exportDonationBoxesToPdf(ArrayList<DonationBox> list,
			String file) throws DocumentException, FileNotFoundException,
			SQLException, ParseException {

		Document document = new Document(PageSize.A4.rotate());

		PdfWriter.getInstance(document, new FileOutputStream(file));

		document.open();

		// add a headline to the document
		Paragraph headline = new Paragraph();
		headline.add(new Paragraph("S-Kollekt", new Font(
				Font.FontFamily.UNDEFINED, 18, Font.UNDERLINE)));
		headline.setAlignment(Element.ALIGN_CENTER);
		headline.add(Chunk.NEWLINE);
		headline.add(Chunk.NEWLINE);

		// add date and time to the document
		GregorianCalendar now = new GregorianCalendar();
		DateFormat date = DateFormat.getDateInstance(DateFormat.LONG);
		DateFormat time = DateFormat.getTimeInstance(DateFormat.SHORT);
		headline.add(new Phrase("Dieses Dokument wurde erstellt am: "
				+ date.format(now.getTime()) + " um "
				+ time.format(now.getTime()) + " Uhr."));
		headline.add(Chunk.NEWLINE);
		headline.add(Chunk.NEWLINE);

		document.add(headline);

		// add type of the list to the document
		document.add(new Paragraph("Liste der Spendendosen", new Font(Font.FontFamily.UNDEFINED, 12, Font.BOLD)));

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
			document.add(new Paragraph(new Chunk(
					"Es werden alle Spendendosen angezeigt.")));
		} else {
			document.add(new Paragraph(
					new Chunk(
							"Die Liste von Spendendosen wurde nach folgenden Kriterien gefiltert:")));

			if (DonationBoxManager.getInstance().getCurrSearchBox()
					.isInUseYes() == false) {
				document.add(new Paragraph(
						new Chunk(
								"Spendendosen, die in Benutzung sind werden nicht angezeigt.")));
			}
			if (DonationBoxManager.getInstance().getCurrSearchBox().isInUseNo() == false) {
				document.add(new Paragraph(
						new Chunk(
								"Spendendosen, die nicht in Benutzung sind werden nicht angezeigt.")));
			}
			if (DonationBoxManager.getInstance().getCurrSearchBox().getBoxId() != -1) {
				document.add(new Paragraph(new Chunk("ID: "
						+ Integer.toString(DonationBoxManager.getInstance()
								.getCurrSearchBox().getBoxId()))));
			}
			if (DonationBoxManager.getInstance().getCurrSearchBox()
					.getResponsiblePersonId() != -1) {
				document.add(new Paragraph(new Chunk(
						"verantwortliches Mitglied (ID): "
								+ Integer.toString(DonationBoxManager
										.getInstance().getCurrSearchBox()
										.getResponsiblePersonId()))));
			}
			if (DonationBoxManager.getInstance().getCurrSearchBox()
					.getComment() != null) {
				document.add(new Paragraph(new Chunk("Kommentar: "
						+ DonationBoxManager.getInstance().getCurrSearchBox()
								.getComment())));
			}
			if (DonationBoxManager.getInstance().getCurrSearchBox()
					.getCollectionId() != -1) {
				document.add(new Paragraph(new Chunk(
						"zugehörige Sammlung (ID): "
								+ Integer.toString(DonationBoxManager
										.getInstance().getCurrSearchBox()
										.getCollectionId()))));
			}
			if (DonationBoxManager.getInstance().getCurrSearchBox().getDate() != null) {
				document.add(new Paragraph(new Chunk("Leerungsdatum: "
						+ date.format(DonationBoxManager.getInstance()
								.getCurrSearchBox().getDate()))));
			}
		}

		// creating empty table
		PdfPTable table = new PdfPTable(5); // TODO check number of columns
		table.setWidthPercentage(100);

		table.setWidths(new int[] { 1, 2, 2, 4, 3 });

		table.setSpacingBefore(10f);
		table.setSpacingAfter(10f);
		PdfPCell cell1 = new PdfPCell(new Paragraph("ID"));
		PdfPCell cell2 = new PdfPCell(new Paragraph("Summe"));
		PdfPCell cell3 = new PdfPCell(new Paragraph("Status"));
		PdfPCell cell4 = new PdfPCell(new Paragraph("Kommentar"));
		PdfPCell cell5 = new PdfPCell(new Paragraph("verantwortliche Person"));
		table.addCell(cell1);
		table.addCell(cell2);
		table.addCell(cell3);
		table.addCell(cell4);
		table.addCell(cell5);

		// filling the table with the list
		Iterator<DonationBox> it = list.iterator();
		while (it.hasNext()) {
			DonationBox donBox = (DonationBox) it.next();
			PdfPCell cellP1 = new PdfPCell(new Paragraph(
					Integer.toString(donBox.getBoxId())));
			PdfPCell cellP2 = new PdfPCell(new Paragraph(Float.toString(Access
					.calculateSumByBoxId(donBox.getBoxId()))));
			String status;
			if (donBox.getIsDeleted() == true) {
				status = "gelöscht";
			} else {
				if (donBox.isInUse()) {
					status = "in Benutzung";
				} else
					status = "nicht in Benutzung";
			}
			PdfPCell cellP3 = new PdfPCell(new Paragraph(status));
			PdfPCell cellP4 = new PdfPCell(new Paragraph(donBox.getComment()));
			PdfPCell cellP5;
			if (donBox.getCurrentResponsiblePerson() == null) {
				cellP5 = new PdfPCell(new Paragraph("-"));
			} else {
				cellP5 = new PdfPCell(new Paragraph(donBox
						.getCurrentResponsiblePerson().toStringPdf()));
			}

			table.addCell(cellP1);
			table.addCell(cellP2);
			table.addCell(cellP3);
			table.addCell(cellP4);
			table.addCell(cellP5);
		}

		document.add(table);

		document.close();
	}

	/**
	 * Exports a PDF-file that includes information about a finished Donation Collection
	 * 
	 * @author KevinSchneider
	 * @param donCol the finished Donation Collection
	 * @param file the path of the location you want to save the PDF-file
	 * @throws DocumentException
	 * @throws FileNotFoundException
	 */
	public static void exportFinishCollection(DonationCollection donCol,
			String file) throws FileNotFoundException, DocumentException {
		Document document = new Document(PageSize.A4.rotate());

		PdfWriter.getInstance(document, new FileOutputStream(file));

		document.open();

		// add a headline to the document
		Paragraph headline = new Paragraph();
		Paragraph paraId = new Paragraph();
		headline.add(new Paragraph("S-Kollekt",
				new Font(Font.FontFamily.UNDEFINED, 18, Font.UNDERLINE)));
		paraId.add(new Paragraph("Sammlung: "
				+ Integer.toString(donCol.getCollectionId()), new Font(
				Font.FontFamily.UNDEFINED, 17, Font.BOLD)));
		headline.setAlignment(Element.ALIGN_CENTER);
		// headline.add(Chunk.NEWLINE);
		// headline.add(Chunk.NEWLINE);

		// add date and time to the document
		GregorianCalendar now = new GregorianCalendar();
		DateFormat date = DateFormat.getDateInstance(DateFormat.LONG);
		DateFormat time = DateFormat.getTimeInstance(DateFormat.SHORT);
		headline.add(new Phrase("Dieses Dokument wurde erstellt am: "
				+ date.format(now.getTime()) + " um "
				+ time.format(now.getTime()) + " Uhr."));
		headline.add(Chunk.NEWLINE);
		headline.add(Chunk.NEWLINE);

		document.add(paraId);
		document.add(headline);

		document.add(Chunk.NEWLINE);
		document.add(Chunk.NEWLINE);

		// add information to document
		document.add(new Paragraph("Protokoll über den Abschluss der Sammlung", new Font(Font.FontFamily.UNDEFINED, 12, Font.BOLD)));
		document.add(new Paragraph("Die folgende Sammlung wurde am "
				+ date.format(donCol.getEndPeriode().getTime())
				+ " abgeschlossen:", new Font(Font.FontFamily.UNDEFINED, 12,
				Font.UNDERLINE)));
		document.add(new Paragraph("ID: "
				+ Integer.toString(donCol.getCollectionId())));
		if (donCol instanceof StreetDonationCollection) {
			document.add(new Paragraph("Typ: Straßensammlung"));
		} else {
			document.add(new Paragraph("Typ: ortsgebundene Sammlung"));
		}
		document.add(new Paragraph("Summe: " + Float.toString(donCol.getSum())
				+ "€"));
		document.add(new Paragraph("Anfangsdatum: "
				+ date.format(donCol.getBeginPeriode().getTime())));
		document.add(new Paragraph("Enddatum: "
				+ date.format(donCol.getEndPeriode().getTime())));
		document.add(new Paragraph("verantwortlicher Mitarbeiter: "
				+ donCol.getOrganizationPerson().getForename()
				+ " "
				+ donCol.getOrganizationPerson().getLastName()
				+ " (ID: "
				+ Integer
						.toString(donCol.getOrganizationPerson().getPersonId())
				+ ")"));
		if (donCol instanceof FixedDonationCollection) {
			FixedDonationCollection fDonCol = (FixedDonationCollection) donCol;
			document.add(new Paragraph("Ansprechpartner: "
					+ fDonCol.getContactPerson().getForename()
					+ " "
					+ fDonCol.getContactPerson().getLastName()
					+ " (ID: "
					+ Integer
							.toString(fDonCol.getContactPerson().getPersonId())
					+ ")"));
			document.add(new Paragraph("Adresse der Sammlung: "
					+ fDonCol.getFixedAddress().getStreetName() + " "
					+ fDonCol.getFixedAddress().getHauseNumber() + ", "
					+ Integer.toString(fDonCol.getFixedAddress().getZip())
					+ " " + fDonCol.getFixedAddress().getLocationName()));
		}
		document.add(new Paragraph("Kommentar: " + donCol.getComment()));
		document.close();

	}

}
