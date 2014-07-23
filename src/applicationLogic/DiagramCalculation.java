/**
 * @author KevinSchneider
 * this class includes all functions to show or export any possible diagram
 */

package applicationLogic;

import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import applicationLogicAccess.CollectionAccess;
import dataAccess.DataAccess;

public class DiagramCalculation {
	// static DonationCollectionManager donColManager;

	/**
	 * Returns a JPanel of a diagram that shows the collected sum of the
	 * collection identified by id
	 * 
	 * @author KevinSchneider
	 * @param id the id of the collection you want to print in the diagram
	 * @return JPanel the Panel of the diagram
	 */
	public static JPanel printCollectionDiagram(int id) {
		System.out.println("OneCollectionDiagram");
		// TODO Fehler suchen
		float sum = 0;
		DonationCollection donCol = CollectionAccess
				.searchDonationCollectionById(id);
		ArrayList<ClearingDonationBox> listClearBox = null;
		try {
			listClearBox = DataAccess.getInstance()
					.getClearingDonationBoxesbyCollectionId(id);
			Collections.sort(listClearBox, new ComparatorClearingBoxDate());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Es ist ein Datenbankfehler aufgetreten!", "Fehler",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(null,
					"Es ist ein Datenbankfehler aufgetreten!", "Fehler",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

		TimeSeries timeseries = new TimeSeries("ID: "
				+ Integer.toString(donCol.getCollectionId()));

		System.out.println(donCol.getBeginPeriode().getTime());
		// timeseries.addOrUpdate(new Day(donCol.getBeginPeriode().getTime()),
		// 0f);

		for (ClearingDonationBox clear : listClearBox) {
			sum = sum + clear.getSum();
			timeseries
					.addOrUpdate(new Day(clear.getClearDate().getTime()), sum);
			System.out.println(clear.getClearDate().getTime());
		}
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		dataset.addSeries(timeseries);

		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		DateAxis xax = new DateAxis("Datum");
		xax.setDateFormatOverride(new SimpleDateFormat("dd.MMM.yyyy"));
		NumberAxis yax = new NumberAxis("Spendenbetrag in Euro");
		XYPlot plot = new XYPlot(dataset, xax, yax, renderer);
		JFreeChart chart = new JFreeChart(plot);
		ChartPanel chartPanel = new ChartPanel(chart);
		// frame.setContentPane(chartPanel);
		// frame.pack();
		// frame.setVisible(true);
		return chartPanel;
	}

	/**
	 * Returns a JPanel of a diagram that shows the status of all collections
	 * 
	 * @author KevinSchneider
	 * @return JPanel the Panel of the diagram
	 */
	public static JPanel printOverallDiagram() {
		System.out.println("OverallDiagram");
		// TODO prüfen, welche Daten aus den ArrayListen und welche aus der
		// Datenbank geholt werden
		ArrayList<DonationCollection> listDonCol = null;
		ArrayList<FixedDonationCollection> listFixDonCol = null;
		ArrayList<StreetDonationCollection> listStreetDonCol = null;

		TimeSeries timeseriesAll = new TimeSeries("alle Spendensammlungen");
		TimeSeries timeseriesFixed = new TimeSeries(
				"ortsgebundene Spendensammlungen");
		TimeSeries timeseriesStreet = new TimeSeries("Straßensammlungen");

		float sumAll = 0;
		float sumFix = 0;
		float sumStreet = 0;
		Date dateAll;
		Date dateFix;
		Date dateStreet;
		Day dayAll;
		Day dayFix;
		Day dayStreet;

		try {
			listDonCol = CollectionAccess.getAllDonationCollections();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Es ist ein Datenbankfehler aufgetreten!", "Fehler",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(null,
					"Es ist ein Datenbankfehler aufgetreten!", "Fehler",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		listFixDonCol = CollectionAccess.getFixedDonationCollections();
		listStreetDonCol = CollectionAccess.getAllStreetDonationCollection();

		// ALL
		for (DonationCollection it : listDonCol) {

			try {
				ArrayList<ClearingDonationBox> listClearBox = DataAccess
						.getInstance().getClearingDonationBoxesbyCollectionId(
								it.getCollectionId());
				Collections.sort(listClearBox, new ComparatorClearingBoxDate());
				for (ClearingDonationBox itClear : listClearBox) {
					System.out.println("Leerung: ID: "
							+ itClear.getClearingId() + ", Betrag: "
							+ itClear.getSum() + " Datum: "
							+ itClear.getClearDate().getTime().toString());
					sumAll += itClear.getSum();
					dateAll = itClear.getClearDate().getTime();
					dayAll = new Day(dateAll);
					timeseriesAll.addOrUpdate(dayAll, sumAll);
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null,
						"Es ist ein Datenbankfehler aufgetreten!", "Fehler",
						JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			} catch (ParseException e) {
				JOptionPane.showMessageDialog(null,
						"Es ist ein Datenbankfehler aufgetreten!", "Fehler",
						JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}

		}

		// FIXED
		for (FixedDonationCollection it : listFixDonCol) {

			try {
				ArrayList<ClearingDonationBox> listClearBox = DataAccess
						.getInstance().getClearingDonationBoxesbyCollectionId(
								it.getCollectionId());
				Collections.sort(listClearBox, new ComparatorClearingBoxDate());
				for (ClearingDonationBox itClear : listClearBox) {
					sumFix += itClear.getSum();
					dateFix = itClear.getClearDate().getTime();
					dayFix = new Day(dateFix);
					timeseriesFixed.addOrUpdate(dayFix, sumFix);
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null,
						"Es ist ein Datenbankfehler aufgetreten!", "Fehler",
						JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			} catch (ParseException e) {
				JOptionPane.showMessageDialog(null,
						"Es ist ein Datenbankfehler aufgetreten!", "Fehler",
						JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}

		}

		// STREET
		for (StreetDonationCollection it : listStreetDonCol) {

			try {
				ArrayList<ClearingDonationBox> listClearBox = DataAccess
						.getInstance().getClearingDonationBoxesbyCollectionId(
								it.getCollectionId());
				Collections.sort(listClearBox, new ComparatorClearingBoxDate());
				for (ClearingDonationBox itClear : listClearBox) {
					sumStreet += itClear.getSum();
					dateStreet = itClear.getClearDate().getTime();
					dayStreet = new Day(dateStreet);
					timeseriesStreet.addOrUpdate(dayStreet, sumStreet);
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null,
						"Es ist ein Datenbankfehler aufgetreten!", "Fehler",
						JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			} catch (ParseException e) {
				JOptionPane.showMessageDialog(null,
						"Es ist ein Datenbankfehler aufgetreten!", "Fehler",
						JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}

		}

		TimeSeriesCollection dataset = new TimeSeriesCollection();
		dataset.addSeries(timeseriesAll);
		dataset.addSeries(timeseriesFixed);
		dataset.addSeries(timeseriesStreet);

		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		// dot.setDotHeight(5);
		// dot.setDotWidth(5);
		DateAxis xax = new DateAxis("Datum");
		NumberAxis yax = new NumberAxis("Spendenbetrag in Euro");
		XYPlot plot = new XYPlot(dataset, xax, yax, renderer);
		DateAxis axis = (DateAxis) plot.getDomainAxis();
		axis.setDateFormatOverride(new SimpleDateFormat("dd.MMM.yyyy"));

		JFreeChart chart = new JFreeChart("Übersicht", plot);

		// Erstellen eines Ausgabefensters
		ChartPanel chartPanel = new ChartPanel(chart);
		return chartPanel;
	}

	/**
	 * Returns a JPanel of a diagram that shows a comparison of the collections
	 * that are selected by id in the ArrayList
	 * 
	 * @author KevinSchneider
	 * @param idList an ArrayList containing the ids of the collections you want to print in the diagram
	 * @return JPanel the Panel of the diagram
	 */
	public static JPanel printCollectionCompareDiagram(ArrayList<Integer> idList) {
		System.out.println("CollectionCompareDiagram");

		ArrayList<DonationCollection> allDonCol = null;
		try {
			allDonCol = CollectionAccess.getAllDonationCollections();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Es ist ein Datenbankfehler aufgetreten!", "Fehler",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(null,
					"Es ist ein Datenbankfehler aufgetreten!", "Fehler",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		ArrayList<DonationCollection> listDonCol = new ArrayList<DonationCollection>();

		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		for (int i : idList) {
			for (DonationCollection d : allDonCol) {
				if (i == d.getCollectionId()) {
					listDonCol.add(d);
				}
			}

		}

		for (DonationCollection d : listDonCol) {
			dataset.setValue(d.getSum(), "Betrag in Euro",
					Integer.toString(d.getCollectionId()));
		}
		JFreeChart chart = ChartFactory.createBarChart(
				"Vergleich ausgewählter Spendensammlungen",
				"Spendensammlung (ID)", "Betrag in Euro", dataset);
		ChartPanel chartPanel = new ChartPanel(chart);
		return chartPanel;
	}
	
	/**
	 * Exports a diagram that shows a comparison of the collections
	 * that are selected by id in the ArrayList
	 * 
	 * @author KevinSchneider
	 * @param idList an ArrayList containing the ids of the collections you want to print in the diagram
	 * @param path the path of the location you want to save the diagram
	 * @throws IOException
	 */
	public static void exportCollectionCompareDiagram(
			ArrayList<Integer> idList, String path) throws IOException {
		// building diagram
		ArrayList<DonationCollection> allDonCol = null;
		try {
			allDonCol = CollectionAccess.getAllDonationCollections();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Es ist ein Datenbankfehler aufgetreten!", "Fehler",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(null,
					"Es ist ein Datenbankfehler aufgetreten!", "Fehler",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		ArrayList<DonationCollection> listDonCol = new ArrayList<DonationCollection>();

		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		for (int i : idList) {
			for (DonationCollection d : allDonCol) {
				if (i == d.getCollectionId()) {
					listDonCol.add(d);
				}
			}

		}

		for (DonationCollection d : listDonCol) {
			dataset.setValue(d.getSum(), "Betrag in Euro",
					Integer.toString(d.getCollectionId()));
		}
		JFreeChart chart = ChartFactory.createBarChart(
				"Vergleich ausgewählter Spendensammlungen",
				"Spendensammlung (ID)", "Betrag in Euro", dataset);
		// output
		OutputStream out = new FileOutputStream(path);
		BufferedImage chartImage = chart.createBufferedImage(750, 500, null);
		ImageIO.write(chartImage, "png", out);
		out.close();

	}

	/**
	 * Exports a diagram that shows the collected sum of the
	 * collection identified by id
	 * 
	 * @author KevinSchneider
	 * @param collectionId the id of the collection you want to print in the diagram
	 * @param path the path of the location you want to save the diagram
	 * @throws IOException
	 */
	public static void exportCollectionDiagram(int collectionId, String path)
			throws IOException {
		// building diagram
		float sum = 0;
		DonationCollection donCol = CollectionAccess
				.searchDonationCollectionById(collectionId);
		ArrayList<ClearingDonationBox> listClearBox = null;
		try {
			listClearBox = DataAccess.getInstance()
					.getClearingDonationBoxesbyCollectionId(collectionId);
			Collections.sort(listClearBox, new ComparatorClearingBoxDate());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Es ist ein Datenbankfehler aufgetreten!", "Fehler",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(null,
					"Es ist ein Datenbankfehler aufgetreten!", "Fehler",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

		TimeSeries timeseries = new TimeSeries("ID: "
				+ Integer.toString(donCol.getCollectionId()));

		System.out.println(donCol.getBeginPeriode().getTime());
		// timeseries.addOrUpdate(new Day(donCol.getBeginPeriode().getTime()),
		// 0f);

		for (ClearingDonationBox clear : listClearBox) {
			sum = sum + clear.getSum();
			timeseries
					.addOrUpdate(new Day(clear.getClearDate().getTime()), sum);
			System.out.println(clear.getClearDate().getTime());
		}
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		dataset.addSeries(timeseries);

		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		DateAxis xax = new DateAxis("Datum");
		xax.setDateFormatOverride(new SimpleDateFormat("dd.MMM.yyyy"));
		NumberAxis yax = new NumberAxis("Spendenbetrag in Euro");
		XYPlot plot = new XYPlot(dataset, xax, yax, renderer);
		JFreeChart chart = new JFreeChart(plot);
		// output
		OutputStream out = new FileOutputStream(path);
		BufferedImage chartImage = chart.createBufferedImage(750, 500, null);
		ImageIO.write(chartImage, "png", out);
		out.close();
	}

	/**
	 * Exports a diagram that shows the status of all collections
	 * 
	 * @author KevinSchneider
	 * @param path the path of the location you want to save the diagram
	 * @throws IOException
	 */
	public static void exportOverallDiagram(String path) throws IOException {
		// building diagram
		ArrayList<DonationCollection> listDonCol = null;
		ArrayList<FixedDonationCollection> listFixDonCol = null;
		ArrayList<StreetDonationCollection> listStreetDonCol = null;

		TimeSeries timeseriesAll = new TimeSeries("alle Spendensammlungen");
		TimeSeries timeseriesFixed = new TimeSeries(
				"ortsgebundene Spendensammlungen");
		TimeSeries timeseriesStreet = new TimeSeries("Straßensammlungen");

		float sumAll = 0;
		float sumFix = 0;
		float sumStreet = 0;
		Date dateAll;
		Date dateFix;
		Date dateStreet;
		Day dayAll;
		Day dayFix;
		Day dayStreet;

		try {
			listDonCol = CollectionAccess.getAllDonationCollections();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Es ist ein Datenbankfehler aufgetreten!", "Fehler",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(null,
					"Es ist ein Datenbankfehler aufgetreten!", "Fehler",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		listFixDonCol = CollectionAccess.getFixedDonationCollections();
		listStreetDonCol = CollectionAccess.getAllStreetDonationCollection();

		// ALL
		for (DonationCollection it : listDonCol) {

			try {
				ArrayList<ClearingDonationBox> listClearBox = DataAccess
						.getInstance().getClearingDonationBoxesbyCollectionId(
								it.getCollectionId());
				Collections.sort(listClearBox, new ComparatorClearingBoxDate());
				for (ClearingDonationBox itClear : listClearBox) {
					sumAll += itClear.getSum();
					dateAll = itClear.getClearDate().getTime();
					dayAll = new Day(dateAll);
					timeseriesAll.addOrUpdate(dayAll, sumAll);
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null,
						"Es ist ein Datenbankfehler aufgetreten!", "Fehler",
						JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			} catch (ParseException e) {
				JOptionPane.showMessageDialog(null,
						"Es ist ein Datenbankfehler aufgetreten!", "Fehler",
						JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}

		}

		// FIXED
		for (FixedDonationCollection it : listFixDonCol) {

			try {
				ArrayList<ClearingDonationBox> listClearBox = DataAccess
						.getInstance().getClearingDonationBoxesbyCollectionId(
								it.getCollectionId());
				Collections.sort(listClearBox, new ComparatorClearingBoxDate());
				for (ClearingDonationBox itClear : listClearBox) {
					sumFix += itClear.getSum();
					dateFix = itClear.getClearDate().getTime();
					dayFix = new Day(dateFix);
					timeseriesFixed.addOrUpdate(dayFix, sumFix);
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null,
						"Es ist ein Datenbankfehler aufgetreten!", "Fehler",
						JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			} catch (ParseException e) {
				JOptionPane.showMessageDialog(null,
						"Es ist ein Datenbankfehler aufgetreten!", "Fehler",
						JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}

		}

		// STREET
		for (StreetDonationCollection it : listStreetDonCol) {

			try {
				ArrayList<ClearingDonationBox> listClearBox = DataAccess
						.getInstance().getClearingDonationBoxesbyCollectionId(
								it.getCollectionId());
				Collections.sort(listClearBox, new ComparatorClearingBoxDate());
				for (ClearingDonationBox itClear : listClearBox) {
					sumStreet += itClear.getSum();
					dateStreet = itClear.getClearDate().getTime();
					dayStreet = new Day(dateStreet);
					timeseriesStreet.addOrUpdate(dayStreet, sumStreet);
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null,
						"Es ist ein Datenbankfehler aufgetreten!", "Fehler",
						JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			} catch (ParseException e) {
				JOptionPane.showMessageDialog(null,
						"Es ist ein Datenbankfehler aufgetreten!", "Fehler",
						JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}

		}

		TimeSeriesCollection dataset = new TimeSeriesCollection();
		dataset.addSeries(timeseriesAll);
		dataset.addSeries(timeseriesFixed);
		dataset.addSeries(timeseriesStreet);

		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		// dot.setDotHeight(5);
		// dot.setDotWidth(5);
		DateAxis xax = new DateAxis("Datum");
		NumberAxis yax = new NumberAxis("Spendenbetrag in Euro");
		XYPlot plot = new XYPlot(dataset, xax, yax, renderer);
		DateAxis axis = (DateAxis) plot.getDomainAxis();
		axis.setDateFormatOverride(new SimpleDateFormat("dd.MMM.yyyy"));
		JFreeChart chart = new JFreeChart("Übersicht", plot);
		// output
		OutputStream out = new FileOutputStream(path);
		BufferedImage chartImage = chart.createBufferedImage(750, 500, null);
		ImageIO.write(chartImage, "png", out);
		out.close();
	}

}
