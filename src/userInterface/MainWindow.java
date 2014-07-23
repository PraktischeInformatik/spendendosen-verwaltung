/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package userInterface;

import applicationLogic.ClearingDonationBox;
import applicationLogic.ContactPerson;
import applicationLogic.DiagramCalculation;
import applicationLogic.DonationBox;
import applicationLogic.DonationCollection;
import applicationLogic.FixedDonationCollection;
import applicationLogic.Login;
import applicationLogic.LoginInUseException;
import applicationLogic.LoginManager;
import applicationLogic.OrganisationPerson;
import applicationLogicAccess.Access;
import applicationLogicAccess.CollectionAccess;
import applicationLogicAccess.DonationBoxCurrentlyAssignedException;
import applicationLogicAccess.PersonHistoryAccess;
import applicationLogicAccess.SelfDeleteException;
import applicationLogicAccess.UserNameDoesNotExistException;
import applicationLogicAccess.WrongPasswortException;

import com.itextpdf.text.DocumentException;

import dataAccess.DataAccess;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/**
 * 
 * @author Timo, Marcel
 */

public class MainWindow extends javax.swing.JFrame {

	DefaultTableModel boxModel;
	DefaultTableModel collectionModel;
	DefaultTableModel orgPersModel;
	DefaultTableModel conPersModel;
	DefaultTableModel userModel;
	String newComment;

	public String getNewComment() {
		return newComment;
	}

	public void setNewComment(String newComment) {
		this.newComment = newComment;
	}

	/**
	 * 
	 * @author Marcel
	 */

	public int loginUser() {
		int accessLevelofLogin = Access.getAccessLevelOfProgramm();
		JTextField username = new JTextField();
		//username.setText("admin"); //TODO später rausnehmen!     
		JTextField password = new JPasswordField();
		//password.setText("admin"); //TODO später rausnehmen!
		Object[] message = { "Benutzername:", username, "Passwort:", password };
		int option = JOptionPane.showConfirmDialog(null, message,
				"Benutzer wechseln", JOptionPane.OK_CANCEL_OPTION);

		if (option == JOptionPane.OK_OPTION) {
			if (username.getText().toString().isEmpty()) {
				try {
					accessLevelofLogin = Access.accessLevelofLogin("gast",
							"gast");
					Access.setActuellLogin("gast");
					Access.setAccessLevelOfProgramm(accessLevelofLogin);
					setAccessLevel(accessLevelofLogin);
				} catch (UserNameDoesNotExistException | WrongPasswortException ex) {
					// this should never happen, because gast//gast is a hardcoded account
					Logger.getLogger(MainWindow.class.getName()).log(
							Level.SEVERE, null, ex);
				}
			} else {
				try {
					accessLevelofLogin = Access.accessLevelofLogin(
							username.getText(), password.getText());
					Access.setActuellLogin(username.getText());
					Access.setAccessLevelOfProgramm(accessLevelofLogin);
					setAccessLevel(accessLevelofLogin);
				} catch (UserNameDoesNotExistException ex) {
					JOptionPane
							.showMessageDialog(
									null,
									"Sie haben einen falschen Benutzernamen eingegeben!",
									"Fehler!", JOptionPane.ERROR_MESSAGE);
				} catch (WrongPasswortException ex) {
					JOptionPane.showMessageDialog(null,
							"Sie haben ein falsches Passwort eingegeben!",
							"Fehler!", JOptionPane.ERROR_MESSAGE);
				}
			}
		} else {
			accessLevelofLogin = Access.getAccessLevelOfProgramm();
		}

		return accessLevelofLogin;

	}

	/**
	 * Initializes the Window
	 * 
	 * @param parent
	 *            the parent
	 * @param modal
	 *            determines if the window should be modal or not
	 */
	public MainWindow() {
		/**
		 * @author Marcel
		 * 
		 *         opens popup for login data todo: Access.checkLogin()
		 * @param evt
		 */
		//

		Access.setTempDonationBoxes(Access.getAllBoxes());
		try {
			CollectionAccess.setTempDonationCollections(CollectionAccess
					.getAllDonationCollections());
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Datenbankfehler!", "Fehler!",
					JOptionPane.ERROR_MESSAGE);
		} catch (ParseException ex) {
			JOptionPane.showMessageDialog(null, "Datenbankfehler!", "Fehler!",
					JOptionPane.ERROR_MESSAGE);
		}
		Access.setTempOrganisationPersons(Access.getAllOrganisationPersons());
		Access.setTempContactPersons(Access.getAllContactPersons());

		initComponents();
		boxTable.getTableHeader().setReorderingAllowed(false);
		collectionTable.getTableHeader().setReorderingAllowed(false);
		orgPersTable.getTableHeader().setReorderingAllowed(false);
		conPersTable.getTableHeader().setReorderingAllowed(false);

		// Define icons
		ImageIcon imgLupe = new ImageIcon("images/imgLupe.png");
		ImageIcon imgAuge = new ImageIcon("images/imgAuge.png");
		ImageIcon imgPerson = new ImageIcon("images/imgPerson.png");
		ImageIcon imgPlus = new ImageIcon("images/imgPlus.png");
		ImageIcon imgBearbeiten = new ImageIcon("images/imgBearbeiten.png");
		ImageIcon imgPfeil = new ImageIcon("images/imgPfeil.png");
		ImageIcon imgKreuz = new ImageIcon("images/imgKreuz.png");
		ImageIcon imgDocument = new ImageIcon("images/imgDocument.png");
		ImageIcon imgGeld = new ImageIcon("images/imgGeld.png");
		ImageIcon imgDiagramm = new ImageIcon("images/imgDiag.png");
		//added by sz
		Image imgIcon = new ImageIcon("images/icon.png").getImage();
		setIconImage(imgIcon);
		//
		btnAddDonationBox.setIcon(imgPlus);
		btnSearchBox.setIcon(imgLupe);
		btnDeleteDonationBox.setIcon(imgKreuz);
		btnEditDonationBox.setIcon(imgBearbeiten);
		btnShowBoxDetails.setIcon(imgAuge);
		btnExportDonationBoxToPdf.setIcon(imgDocument);
		btnExportDonationBoxToCsv.setIcon(imgDocument);
		btnShowAllBoxes.setIcon(imgAuge);
		btnClearDonationBox.setIcon(imgGeld);
		btnAddCollection.setIcon(imgPlus);
		btnSearchCollection.setIcon(imgLupe);
		btnManageCollection.setIcon(imgBearbeiten);
		btnExportCollectionToPdf.setIcon(imgDocument);
		btnExportCollectionToCSV.setIcon(imgDocument);
		btnShowAllCollections.setIcon(imgAuge);
		btnAddOrganisationPerson.setIcon(imgPlus);
		btnEditOrganisationPerson.setIcon(imgBearbeiten);
		btnExportOrganizationPersonToPdf.setIcon(imgDocument);
		btnSearchOrganisationPerson.setIcon(imgLupe);
		btnExportOrganisationPersonToCsv.setIcon(imgDocument);
		btnAddContactPerson.setIcon(imgPlus);
		btnShowAllOrganisationPersons.setIcon(imgAuge);
		btnShowCollectionsOfOrganisationtPerson.setIcon(imgAuge);
		btnShowOrgPersDetails.setIcon(imgAuge);
		btnEditContactPerson.setIcon(imgBearbeiten);
		btnSearchContactPerson.setIcon(imgLupe);
		btnExportContactPersonToPdf.setIcon(imgDocument);
		btnExportContactPersonToCsv.setIcon(imgDocument);
		btnShowAllContactPersons.setIcon(imgAuge);
		btnShowCollectionsOfContactPerson.setIcon(imgAuge);
		btnShowConPersDetails.setIcon(imgAuge);
		btnShowAllDiagram.setIcon(imgDiagramm);
		btnShowCompareDiagram.setIcon(imgDiagramm);
		btnChangePw.setIcon(imgBearbeiten);
		btnChangeUserPw.setIcon(imgBearbeiten);
		btnLogout.setIcon(imgPfeil);
		btnLogin.setIcon(imgPerson);
		btnDeleteUser.setIcon(imgKreuz);
		btnAddUser.setIcon(imgPlus);
		setAccessLevel(3);

		this.setLocationRelativeTo(null);

		boxModel = (DefaultTableModel) boxTable.getModel();
		fillBoxTable(Access.getTempDonationBoxes());

		collectionModel = (DefaultTableModel) collectionTable.getModel();
		try {
			fillCollectionTable(CollectionAccess.getTempDonationCollections());
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Datenbankfehler!", "Fehler!",
					JOptionPane.ERROR_MESSAGE);
		} catch (ParseException ex) {
			JOptionPane.showMessageDialog(null, "Datenbankfehler!", "Fehler!",
					JOptionPane.ERROR_MESSAGE);
		}

		orgPersModel = (DefaultTableModel) orgPersTable.getModel();
		fillOrganisationPersonTable(Access.getTempOrganisationPersons());

		conPersModel = (DefaultTableModel) conPersTable.getModel();
		fillContactPersonTable(Access.getTempContactPersons());

		userModel = (DefaultTableModel) userTable.getModel();
		fillUserTable();

	}

	/**
	 * @author Timo, Marcel
	 * 
	 *         Clears and reloads the content of all 4 main Tables with
	 *         up-to-date data. Current filters are still being applied
	 */
	public void refreshTables() {
		boxModel.setRowCount(0);
		collectionModel.setRowCount(0);
		orgPersModel.setRowCount(0);
		conPersModel.setRowCount(0);

		fillBoxTable(Access.getTempDonationBoxes());
		try {
			fillCollectionTable(CollectionAccess.getTempDonationCollections());
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Datenbankfehler!", "Fehler!",
					JOptionPane.ERROR_MESSAGE);
		} catch (ParseException ex) {
			JOptionPane.showMessageDialog(null, "Datenbankfehler!", "Fehler!",
					JOptionPane.ERROR_MESSAGE);
		}
		fillOrganisationPersonTable(Access.getTempOrganisationPersons());
		fillContactPersonTable(Access.getTempContactPersons());

	}

	/**
	 * Grants or takes access to different Buttons and the adminPane, depending
	 * on the accessLevel
	 * 
	 * @param accessLevel
	 *            the accessLevel. 1 for Admin; 2 for Member; 3 for Guest
	 */
	public void setAccessLevel(int accessLevel) {
		String level = "";
		lblLogin.setText("Eingeloggt als: " + Access.getActuellLogin());
		if (accessLevel == 1) { // admin
			level = " (Admin)";
			boolean state = true;
			btnClearDonationBox.setEnabled(state);
			btnEditContactPerson.setEnabled(state);
			btnEditDonationBox.setEnabled(state);
			btnEditOrganisationPerson.setEnabled(state);
			btnAddContactPerson.setEnabled(state);
			btnAddCollection.setEnabled(state);
			btnAddDonationBox.setEnabled(state);
			btnAddOrganisationPerson.setEnabled(state);
			btnClearDonationBox.setEnabled(state);
			btnDeleteDonationBox.setEnabled(state);
			paneMain.add("Admin - Benutzerverwaltung", adminPane);
			btnChangePw.setEnabled(state);
			btnLogout.setEnabled(state);
		}
		if (accessLevel == 2) { // member
			level = " (Mitglied)";
			boolean state = true;
			btnClearDonationBox.setEnabled(state);
			btnEditContactPerson.setEnabled(state);
			btnEditDonationBox.setEnabled(state);
			btnEditOrganisationPerson.setEnabled(state);
			btnAddContactPerson.setEnabled(state);
			btnAddCollection.setEnabled(state);
			btnAddDonationBox.setEnabled(state);
			btnAddOrganisationPerson.setEnabled(state);
			btnClearDonationBox.setEnabled(state);
			btnDeleteDonationBox.setEnabled(state);
			paneMain.remove(adminPane);
			btnChangePw.setEnabled(state);
			btnLogout.setEnabled(state);

		}
		if (accessLevel == 3) { // guest
			level = " (Gast)";
			boolean state = false;
			btnClearDonationBox.setEnabled(state);
			btnEditContactPerson.setEnabled(state);
			btnEditDonationBox.setEnabled(state);
			btnEditOrganisationPerson.setEnabled(state);
			btnAddContactPerson.setEnabled(state);
			btnAddCollection.setEnabled(state);
			btnAddDonationBox.setEnabled(state);
			btnAddOrganisationPerson.setEnabled(state);
			btnClearDonationBox.setEnabled(state);
			btnDeleteDonationBox.setEnabled(state);
			paneMain.remove(adminPane);
			btnChangePw.setEnabled(state);
			btnLogout.setEnabled(state);
		}
		lblLogin.setText("Eingeloggt als: " + Access.getActuellLogin() + level);
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		paneMain = new javax.swing.JTabbedPane();
		jPanel2 = new javax.swing.JPanel();
		jScrollPane2 = new javax.swing.JScrollPane();
		collectionTable = new javax.swing.JTable();
		btnAddCollection = new javax.swing.JButton();
		btnSearchCollection = new javax.swing.JButton();
		btnManageCollection = new javax.swing.JButton();
		btnExportCollectionToPdf = new javax.swing.JButton();
		btnExportCollectionToCSV = new javax.swing.JButton();
		btnShowAllCollections = new javax.swing.JButton();
		btnShowAllDiagram = new javax.swing.JButton();
		btnShowCompareDiagram = new javax.swing.JButton();
		jPanel1 = new javax.swing.JPanel();
		jScrollPane1 = new javax.swing.JScrollPane();
		boxTable = new javax.swing.JTable();
		btnAddDonationBox = new javax.swing.JButton();
		btnSearchBox = new javax.swing.JButton();
		btnDeleteDonationBox = new javax.swing.JButton();
		btnEditDonationBox = new javax.swing.JButton();
		btnShowBoxDetails = new javax.swing.JButton();
		btnExportDonationBoxToPdf = new javax.swing.JButton();
		btnExportDonationBoxToCsv = new javax.swing.JButton();
		btnShowAllBoxes = new javax.swing.JButton();
		btnClearDonationBox = new javax.swing.JButton();
		jPanel3 = new javax.swing.JPanel();
		jScrollPane3 = new javax.swing.JScrollPane();
		orgPersTable = new javax.swing.JTable();
		btnAddOrganisationPerson = new javax.swing.JButton();
		btnEditOrganisationPerson = new javax.swing.JButton();
		btnSearchOrganisationPerson = new javax.swing.JButton();
		btnExportOrganizationPersonToPdf = new javax.swing.JButton();
		btnExportOrganisationPersonToCsv = new javax.swing.JButton();
		btnShowAllOrganisationPersons = new javax.swing.JButton();
		btnShowCollectionsOfOrganisationtPerson = new javax.swing.JButton();
		btnShowOrgPersDetails = new javax.swing.JButton();
		jPanel4 = new javax.swing.JPanel();
		jScrollPane4 = new javax.swing.JScrollPane();
		conPersTable = new javax.swing.JTable();
		btnAddContactPerson = new javax.swing.JButton();
		btnEditContactPerson = new javax.swing.JButton();
		btnSearchContactPerson = new javax.swing.JButton();
		btnExportContactPersonToPdf = new javax.swing.JButton();
		btnExportContactPersonToCsv = new javax.swing.JButton();
		btnShowAllContactPersons = new javax.swing.JButton();
		btnShowCollectionsOfContactPerson = new javax.swing.JButton();
		btnShowConPersDetails = new javax.swing.JButton();
		adminPane = new javax.swing.JPanel();
		jScrollPane5 = new javax.swing.JScrollPane();
		userTable = new javax.swing.JTable();
		btnAddUser = new javax.swing.JButton();
		btnDeleteUser = new javax.swing.JButton();
		btnChangeUserPw = new javax.swing.JButton();
		lblLogin = new javax.swing.JLabel();
		btnLogin = new javax.swing.JButton();
		btnChangePw = new javax.swing.JButton();
		btnLogout = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("S-Kollekt");
		setMinimumSize(new java.awt.Dimension(1391, 700));
		setResizable(false);

		collectionTable.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] {

				}, new String[] { "ID", "Abgeschlossen", "Verantwortlicher",
						"Standort", "Beginn", "Ende", "Summe", "Kommentar" }) {
			boolean[] canEdit = new boolean[] { false, false, false, false,
					false, false, false, false };

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		jScrollPane2.setViewportView(collectionTable);
		if (collectionTable.getColumnModel().getColumnCount() > 0) {
			collectionTable.getColumnModel().getColumn(0).setMinWidth(50);
			collectionTable.getColumnModel().getColumn(0).setPreferredWidth(50);
			collectionTable.getColumnModel().getColumn(0).setMaxWidth(50);
			collectionTable.getColumnModel().getColumn(1).setMinWidth(100);
			collectionTable.getColumnModel().getColumn(1)
					.setPreferredWidth(100);
			collectionTable.getColumnModel().getColumn(1).setMaxWidth(100);
			collectionTable.getColumnModel().getColumn(2).setMinWidth(200);
			collectionTable.getColumnModel().getColumn(2)
					.setPreferredWidth(200);
			collectionTable.getColumnModel().getColumn(2).setMaxWidth(200);
			collectionTable.getColumnModel().getColumn(3).setMinWidth(190);
			collectionTable.getColumnModel().getColumn(3)
					.setPreferredWidth(190);
			collectionTable.getColumnModel().getColumn(3).setMaxWidth(190);
			collectionTable.getColumnModel().getColumn(4).setMinWidth(80);
			collectionTable.getColumnModel().getColumn(4).setPreferredWidth(80);
			collectionTable.getColumnModel().getColumn(4).setMaxWidth(80);
			collectionTable.getColumnModel().getColumn(5).setMinWidth(80);
			collectionTable.getColumnModel().getColumn(5).setPreferredWidth(80);
			collectionTable.getColumnModel().getColumn(5).setMaxWidth(80);
			collectionTable.getColumnModel().getColumn(6).setMinWidth(60);
			collectionTable.getColumnModel().getColumn(6).setPreferredWidth(60);
			collectionTable.getColumnModel().getColumn(6).setMaxWidth(60);
		}

		btnAddCollection.setText("Sammlung erstellen");
		btnAddCollection.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnAddCollectionActionPerformed(evt);
			}
		});

		btnSearchCollection.setText("Sammlung(en) suchen");
		btnSearchCollection
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						btnSearchCollectionActionPerformed(evt);
					}
				});

		btnManageCollection.setText("Sammlung verwalten");
		btnManageCollection
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						btnManageCollectionActionPerformed(evt);
					}
				});

		btnExportCollectionToPdf.setText("Daten als PDF exportieren");
		btnExportCollectionToPdf
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						btnExportCollectionToPdfActionPerformed(evt);
					}
				});

		btnExportCollectionToCSV.setText("Daten als CSV exportieren");
		btnExportCollectionToCSV
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						btnExportCollectionToCSVActionPerformed(evt);
					}
				});

		btnShowAllCollections.setText("Zeige Alle Sammlungen");
		btnShowAllCollections
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						btnShowAllCollectionsActionPerformed(evt);
					}
				});

		btnShowAllDiagram.setText("Gesamtauswertung");
		btnShowAllDiagram
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						btnShowAllDiagramActionPerformed(evt);
					}
				});

		btnShowCompareDiagram.setText("Sammlungen vergleichen");
		btnShowCompareDiagram
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						btnShowCompareDiagramActionPerformed(evt);
					}
				});

		javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(
				jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout
				.setHorizontalGroup(jPanel2Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel2Layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jPanel2Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																jScrollPane2)
														.addGroup(
																jPanel2Layout
																		.createSequentialGroup()
																		.addComponent(
																				btnShowAllCollections)
																		.addGap(18,
																				18,
																				18)
																		.addComponent(
																				btnSearchCollection)
																		.addGap(18,
																				18,
																				18)
																		.addComponent(
																				btnAddCollection)
																		.addGap(18,
																				18,
																				18)
																		.addComponent(
																				btnManageCollection)
																		.addGap(18,
																				18,
																				18)
																		.addComponent(
																				btnShowAllDiagram)
																		.addGap(18,
																				18,
																				18)
																		.addComponent(
																				btnShowCompareDiagram)
																		.addGap(0,
																				0,
																				Short.MAX_VALUE)))
										.addContainerGap())
						.addGroup(
								jPanel2Layout
										.createSequentialGroup()
										.addGap(406, 406, 406)
										.addComponent(btnExportCollectionToPdf)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED,
												236, Short.MAX_VALUE)
										.addComponent(btnExportCollectionToCSV)
										.addGap(406, 406, 406)));
		jPanel2Layout
				.setVerticalGroup(jPanel2Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel2Layout
										.createSequentialGroup()
										.addGap(20, 20, 20)
										.addGroup(
												jPanel2Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																btnShowAllCollections)
														.addComponent(
																btnSearchCollection)
														.addComponent(
																btnAddCollection)
														.addComponent(
																btnManageCollection)
														.addComponent(
																btnShowAllDiagram)
														.addComponent(
																btnShowCompareDiagram))
										.addGap(18, 18, 18)
										.addComponent(
												jScrollPane2,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												509,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(18, 18, 18)
										.addGroup(
												jPanel2Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																btnExportCollectionToPdf)
														.addComponent(
																btnExportCollectionToCSV))
										.addGap(41, 41, 41)));

		paneMain.addTab("Sammlungen", jPanel2);

		boxTable.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] {

				}, new String[] { "ID", "Status", "Bisher gesammeltes Geld",
						"Verantwortliche Person", "Kommentar" }) {
			Class[] types = new Class[] { java.lang.Integer.class,
					java.lang.String.class, java.lang.String.class,
					java.lang.String.class, java.lang.String.class };
			boolean[] canEdit = new boolean[] { false, false, false, false,
					false };

			public Class getColumnClass(int columnIndex) {
				return types[columnIndex];
			}

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		jScrollPane1.setViewportView(boxTable);
		if (boxTable.getColumnModel().getColumnCount() > 0) {
			boxTable.getColumnModel().getColumn(0).setMinWidth(50);
			boxTable.getColumnModel().getColumn(0).setPreferredWidth(50);
			boxTable.getColumnModel().getColumn(0).setMaxWidth(50);
			boxTable.getColumnModel().getColumn(1).setMinWidth(70);
			boxTable.getColumnModel().getColumn(1).setPreferredWidth(70);
			boxTable.getColumnModel().getColumn(1).setMaxWidth(70);
			boxTable.getColumnModel().getColumn(2).setMinWidth(160);
			boxTable.getColumnModel().getColumn(2).setPreferredWidth(160);
			boxTable.getColumnModel().getColumn(2).setMaxWidth(160);
			boxTable.getColumnModel().getColumn(3).setMinWidth(150);
			boxTable.getColumnModel().getColumn(3).setPreferredWidth(150);
			boxTable.getColumnModel().getColumn(3).setMaxWidth(150);
			boxTable.getColumnModel().getColumn(4).setPreferredWidth(400);
		}

		btnAddDonationBox.setText("Dose Erstellen");
		btnAddDonationBox
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						btnAddDonationBoxActionPerformed(evt);
					}
				});

		btnSearchBox.setText("Dosen suchen");
		btnSearchBox.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnSearchBoxActionPerformed(evt);
			}
		});

		btnDeleteDonationBox.setText("Dose löschen");
		btnDeleteDonationBox
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						btnDeleteDonationBoxActionPerformed(evt);
					}
				});

		btnEditDonationBox.setText("Dose bearbeiten");
		btnEditDonationBox
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						btnEditDonationBoxActionPerformed(evt);
					}
				});

		btnShowBoxDetails.setText("Details anzeigen");
		btnShowBoxDetails
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						btnShowBoxDetailsActionPerformed(evt);
					}
				});

		btnExportDonationBoxToPdf.setText("Daten als PDF exportieren");
		btnExportDonationBoxToPdf
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						btnExportDonationBoxToPdfActionPerformed(evt);
					}
				});

		btnExportDonationBoxToCsv.setText("Daten als CSV exportieren");
		btnExportDonationBoxToCsv
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						btnExportDonationBoxToCsvActionPerformed(evt);
					}
				});

		btnShowAllBoxes.setText("Alle Dosen anzeigen");
		btnShowAllBoxes.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnShowAllBoxesActionPerformed(evt);
			}
		});

		btnClearDonationBox.setText("Dose leeren");
		btnClearDonationBox
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						btnClearDonationBoxActionPerformed(evt);
					}
				});

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(
				jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout
				.setHorizontalGroup(jPanel1Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel1Layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jPanel1Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																jScrollPane1)
														.addGroup(
																jPanel1Layout
																		.createSequentialGroup()
																		.addComponent(
																				btnShowAllBoxes)
																		.addGap(18,
																				18,
																				18)
																		.addComponent(
																				btnSearchBox)
																		.addGap(18,
																				18,
																				18)
																		.addComponent(
																				btnAddDonationBox)
																		.addGap(18,
																				18,
																				18)
																		.addComponent(
																				btnEditDonationBox)
																		.addGap(18,
																				18,
																				18)
																		.addComponent(
																				btnShowBoxDetails)
																		.addGap(18,
																				18,
																				18)
																		.addComponent(
																				btnClearDonationBox)
																		.addGap(18,
																				18,
																				18)
																		.addComponent(
																				btnDeleteDonationBox)
																		.addGap(0,
																				0,
																				Short.MAX_VALUE)))
										.addContainerGap())
						.addGroup(
								jPanel1Layout
										.createSequentialGroup()
										.addGap(406, 406, 406)
										.addComponent(btnExportDonationBoxToPdf)
										.addGap(120, 120, 120)
										.addComponent(btnExportDonationBoxToCsv)
										.addGap(420, 420, 420)));
		jPanel1Layout
				.setVerticalGroup(jPanel1Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								jPanel1Layout
										.createSequentialGroup()
										.addGap(20, 20, 20)
										.addGroup(
												jPanel1Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																btnShowAllBoxes)
														.addComponent(
																btnSearchBox)
														.addComponent(
																btnAddDonationBox)
														.addComponent(
																btnShowBoxDetails)
														.addComponent(
																btnDeleteDonationBox)
														.addComponent(
																btnEditDonationBox)
														.addComponent(
																btnClearDonationBox))
										.addGap(18, 18, 18)
										.addComponent(
												jScrollPane1,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(18, 18, 18)
										.addGroup(
												jPanel1Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																btnExportDonationBoxToPdf)
														.addComponent(
																btnExportDonationBoxToCsv))
										.addContainerGap(132, Short.MAX_VALUE)));

		paneMain.addTab("Dosen", jPanel1);

		orgPersTable
				.setModel(new javax.swing.table.DefaultTableModel(
						new Object[][] {

						}, new String[] { "ID", "Name, Vorname", "Adresse",
								"E-Mail", "Festnetz", "Mobil",
								"Aktives Mitglied?", "Kommentar" }) {
					boolean[] canEdit = new boolean[] { false, false, false,
							false, false, false, false, true };

					public boolean isCellEditable(int rowIndex, int columnIndex) {
						return canEdit[columnIndex];
					}
				});
		orgPersTable.getTableHeader().setReorderingAllowed(false);
		jScrollPane3.setViewportView(orgPersTable);
		if (orgPersTable.getColumnModel().getColumnCount() > 0) {
			orgPersTable.getColumnModel().getColumn(0).setMinWidth(50);
			orgPersTable.getColumnModel().getColumn(0).setPreferredWidth(50);
			orgPersTable.getColumnModel().getColumn(0).setMaxWidth(50);
			orgPersTable.getColumnModel().getColumn(2).setMinWidth(190);
			orgPersTable.getColumnModel().getColumn(2).setPreferredWidth(190);
			orgPersTable.getColumnModel().getColumn(2).setMaxWidth(190);
		}

		btnAddOrganisationPerson.setText("Mitglied hinzufügen");
		btnAddOrganisationPerson
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						btnAddOrganisationPersonActionPerformed(evt);
					}
				});

		btnEditOrganisationPerson.setText("Mitgliedsdaten bearbeiten");
		btnEditOrganisationPerson
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						btnEditOrganisationPersonActionPerformed(evt);
					}
				});

		btnSearchOrganisationPerson.setText("Mitglied suchen");
		btnSearchOrganisationPerson
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						btnSearchOrganisationPersonActionPerformed(evt);
					}
				});

		btnExportOrganizationPersonToPdf.setText("Daten als PDF exportieren");
		btnExportOrganizationPersonToPdf
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						btnExportOrganizationPersonToPdfActionPerformed(evt);
					}
				});

		btnExportOrganisationPersonToCsv.setText("Daten als CSV exportieren");
		btnExportOrganisationPersonToCsv
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						btnExportOrganisationPersonToCsvActionPerformed(evt);
					}
				});

		btnShowAllOrganisationPersons
				.setText("Alle Organisationsmitglieder anzeigen");
		btnShowAllOrganisationPersons
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						btnShowAllOrganisationPersonsActionPerformed(evt);
					}
				});

		btnShowCollectionsOfOrganisationtPerson
				.setText("Zugehörige Sammlungen anzeigen");
		btnShowCollectionsOfOrganisationtPerson
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						btnShowCollectionsOfOrganisationtPersonActionPerformed(evt);
					}
				});

		btnShowOrgPersDetails.setText("Details anzeigen");
		btnShowOrgPersDetails
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						btnShowOrgPersDetailsActionPerformed(evt);
					}
				});

		javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(
				jPanel3);
		jPanel3.setLayout(jPanel3Layout);
		jPanel3Layout
				.setHorizontalGroup(jPanel3Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel3Layout
										.createSequentialGroup()
										.addGroup(
												jPanel3Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																jPanel3Layout
																		.createSequentialGroup()
																		.addContainerGap()
																		.addComponent(
																				jScrollPane3))
														.addGroup(
																jPanel3Layout
																		.createSequentialGroup()
																		.addGap(406,
																				406,
																				406)
																		.addComponent(
																				btnExportOrganizationPersonToPdf)
																		.addGap(120,
																				120,
																				120)
																		.addComponent(
																				btnExportOrganisationPersonToCsv)
																		.addGap(0,
																				512,
																				Short.MAX_VALUE)))
										.addContainerGap())
						.addGroup(
								jPanel3Layout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(
												btnShowAllOrganisationPersons)
										.addGap(18, 18, 18)
										.addComponent(
												btnSearchOrganisationPerson)
										.addGap(18, 18, 18)
										.addComponent(btnAddOrganisationPerson)
										.addGap(18, 18, 18)
										.addComponent(btnEditOrganisationPerson)
										.addGap(18, 18, 18)
										.addComponent(
												btnShowCollectionsOfOrganisationtPerson)
										.addGap(18, 18, 18)
										.addComponent(btnShowOrgPersDetails)
										.addContainerGap(
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));
		jPanel3Layout
				.setVerticalGroup(jPanel3Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								jPanel3Layout
										.createSequentialGroup()
										.addGap(20, 20, 20)
										.addGroup(
												jPanel3Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																btnAddOrganisationPerson)
														.addComponent(
																btnSearchOrganisationPerson)
														.addComponent(
																btnShowAllOrganisationPersons)
														.addComponent(
																btnEditOrganisationPerson)
														.addComponent(
																btnShowCollectionsOfOrganisationtPerson)
														.addComponent(
																btnShowOrgPersDetails))
										.addGap(18, 18, 18)
										.addComponent(
												jScrollPane3,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(18, 18, 18)
										.addGroup(
												jPanel3Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																btnExportOrganizationPersonToPdf)
														.addComponent(
																btnExportOrganisationPersonToCsv))
										.addGap(15, 15, 15)));

		paneMain.addTab("Organisationsmitglieder", jPanel3);

		conPersTable.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] {

				}, new String[] { "ID", "Name, Vorname", "Adresse", "E-Mail",
						"Festnetz", "Mobil", "Kommentar" }) {
			boolean[] canEdit = new boolean[] { false, false, false, false,
					true, false, true };

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		jScrollPane4.setViewportView(conPersTable);
		if (conPersTable.getColumnModel().getColumnCount() > 0) {
			conPersTable.getColumnModel().getColumn(0).setMinWidth(50);
			conPersTable.getColumnModel().getColumn(0).setPreferredWidth(50);
			conPersTable.getColumnModel().getColumn(0).setMaxWidth(50);
			conPersTable.getColumnModel().getColumn(4).setMinWidth(100);
			conPersTable.getColumnModel().getColumn(4).setPreferredWidth(100);
			conPersTable.getColumnModel().getColumn(4).setMaxWidth(100);
			conPersTable.getColumnModel().getColumn(5).setMinWidth(100);
			conPersTable.getColumnModel().getColumn(5).setPreferredWidth(100);
			conPersTable.getColumnModel().getColumn(5).setMaxWidth(100);
		}

		btnAddContactPerson.setText("Ansprechpartner hinzufügen");
		btnAddContactPerson
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						btnAddContactPersonActionPerformed(evt);
					}
				});

		btnEditContactPerson.setText("Ansprechpartner bearbeiten");
		btnEditContactPerson
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						btnEditContactPersonActionPerformed(evt);
					}
				});

		btnSearchContactPerson.setText("Ansprechpartner suchen");
		btnSearchContactPerson
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						btnSearchContactPersonActionPerformed(evt);
					}
				});

		btnExportContactPersonToPdf.setText("Daten als PDF exportieren");
		btnExportContactPersonToPdf
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						btnExportContactPersonToPdfActionPerformed(evt);
					}
				});

		btnExportContactPersonToCsv.setText("Daten als CSV exportieren");
		btnExportContactPersonToCsv
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						btnExportContactPersonToCsvActionPerformed(evt);
					}
				});

		btnShowAllContactPersons.setText("Alle Kontaktpersonen Anzeigen");
		btnShowAllContactPersons
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						btnShowAllContactPersonsActionPerformed(evt);
					}
				});

		btnShowCollectionsOfContactPerson
				.setText("Zugehörige Sammlungen anzeigen");
		btnShowCollectionsOfContactPerson
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						btnShowCollectionsOfContactPersonActionPerformed(evt);
					}
				});

		btnShowConPersDetails.setText("Details anzeigen");
		btnShowConPersDetails
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						btnShowConPersDetailsActionPerformed(evt);
					}
				});

		javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(
				jPanel4);
		jPanel4.setLayout(jPanel4Layout);
		jPanel4Layout
				.setHorizontalGroup(jPanel4Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel4Layout
										.createSequentialGroup()
										.addGroup(
												jPanel4Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																jPanel4Layout
																		.createSequentialGroup()
																		.addContainerGap()
																		.addComponent(
																				jScrollPane4))
														.addGroup(
																jPanel4Layout
																		.createSequentialGroup()
																		.addGap(406,
																				406,
																				406)
																		.addComponent(
																				btnExportContactPersonToPdf)
																		.addGap(120,
																				120,
																				120)
																		.addComponent(
																				btnExportContactPersonToCsv)
																		.addGap(0,
																				0,
																				Short.MAX_VALUE)))
										.addContainerGap())
						.addGroup(
								jPanel4Layout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(btnShowAllContactPersons)
										.addGap(18, 18, 18)
										.addComponent(btnSearchContactPerson)
										.addGap(18, 18, 18)
										.addComponent(btnAddContactPerson)
										.addGap(18, 18, 18)
										.addComponent(btnEditContactPerson)
										.addGap(18, 18, 18)
										.addComponent(
												btnShowCollectionsOfContactPerson)
										.addGap(18, 18, 18)
										.addComponent(btnShowConPersDetails)
										.addContainerGap(
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));
		jPanel4Layout
				.setVerticalGroup(jPanel4Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								jPanel4Layout
										.createSequentialGroup()
										.addGap(20, 20, 20)
										.addGroup(
												jPanel4Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																btnAddContactPerson)
														.addComponent(
																btnSearchContactPerson)
														.addComponent(
																btnShowAllContactPersons)
														.addComponent(
																btnEditContactPerson)
														.addComponent(
																btnShowCollectionsOfContactPerson)
														.addComponent(
																btnShowConPersDetails))
										.addGap(18, 18, 18)
										.addComponent(
												jScrollPane4,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(18, 18, 18)
										.addGroup(
												jPanel4Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																btnExportContactPersonToPdf)
														.addComponent(
																btnExportContactPersonToCsv))
										.addGap(10, 10, 10)));

		paneMain.addTab("Ansprechpartner", jPanel4);

		userTable.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] {

				}, new String[] { "Name", "Zugriffslevel" }) {
			boolean[] canEdit = new boolean[] { false, false };

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		userTable.getTableHeader().setReorderingAllowed(false);
		jScrollPane5.setViewportView(userTable);

		btnAddUser.setText("Benutzer anlegen");
		btnAddUser.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnAddUserActionPerformed(evt);
			}
		});

		btnDeleteUser.setText("Benutzer löschen");
		btnDeleteUser.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnDeleteUserActionPerformed(evt);
			}
		});

		btnChangeUserPw.setText("Benutzerpasswort ändern");
		btnChangeUserPw.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnChangeUserPwActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout adminPaneLayout = new javax.swing.GroupLayout(
				adminPane);
		adminPane.setLayout(adminPaneLayout);
		adminPaneLayout
				.setHorizontalGroup(adminPaneLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								adminPaneLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												adminPaneLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																jScrollPane5,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addGroup(
																adminPaneLayout
																		.createSequentialGroup()
																		.addComponent(
																				btnAddUser)
																		.addGap(18,
																				18,
																				18)
																		.addComponent(
																				btnDeleteUser)
																		.addGap(18,
																				18,
																				18)
																		.addComponent(
																				btnChangeUserPw)))
										.addContainerGap(904, Short.MAX_VALUE)));
		adminPaneLayout
				.setVerticalGroup(adminPaneLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								adminPaneLayout
										.createSequentialGroup()
										.addGap(20, 20, 20)
										.addGroup(
												adminPaneLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																btnAddUser)
														.addComponent(
																btnDeleteUser)
														.addComponent(
																btnChangeUserPw))
										.addGap(18, 18, 18)
										.addComponent(
												jScrollPane5,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addContainerGap(173, Short.MAX_VALUE)));

		paneMain.addTab("Admin - Benutzerverwaltung", adminPane);

		lblLogin.setText("Eingeloggt als: _____________");

		btnLogin.setText("Benutzer wechseln");
		btnLogin.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnLoginActionPerformed(evt);
			}
		});

		btnChangePw.setText("Passwort ändern");
		btnChangePw.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnChangePwActionPerformed(evt);
			}
		});

		btnLogout.setText("Ausloggen");
		btnLogout.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnLogoutActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(paneMain)
												.addGroup(
														layout.createSequentialGroup()
																.addGap(0,
																		0,
																		Short.MAX_VALUE)
																.addComponent(
																		lblLogin)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																.addComponent(
																		btnLogin)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																.addComponent(
																		btnChangePw)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																.addComponent(
																		btnLogout)))
								.addContainerGap()));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup()
								.addGap(10, 10, 10)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(lblLogin)
												.addComponent(btnLogin)
												.addComponent(btnChangePw)
												.addComponent(btnLogout))
								.addGap(1, 1, 1).addComponent(paneMain)
								.addContainerGap()));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	/**
	 * Opens a new ManageCollection Window
	 * 
	 * @param evt
	 *            the event (unused)
	 */
	private void btnManageCollectionActionPerformed(
			java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnManageCollectionActionPerformed
		int n = collectionTable.getSelectedRow();
		if (n == -1) {
			JOptionPane
					.showMessageDialog(
							null,
							"Keine Sammlung ausgewählt!\n\nBitte wählen sie vor Betätigen des Knopfes die zu betrachtende Sammlung aus.",
							"Fehler!", JOptionPane.ERROR_MESSAGE);
		} else {
			int toDetail = (int) collectionTable.getValueAt(n, 0);
			ManageCollection mainFrame = new ManageCollection(null, true,
					toDetail);
			mainFrame.setVisible(true);
		}
		refreshTables();
	}//GEN-LAST:event_btnManageCollectionActionPerformed

	/**
	 * Opens a new DisplayDiagram Window
	 * 
	 * @param evt
	 *            the event (unused)
	 */
	private void btnShowCompareDiagramActionPerformed(
			java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowCompareDiagramActionPerformed
		if (collectionTable.getSelectedRowCount() < 1
				|| collectionTable.getSelectedRowCount() > 10) {
			JOptionPane
					.showMessageDialog(
							null,
							"Bitte wählen sie mindestens 1 und maximal 10 Sammlungen aus die sie vergleichen möchten. Halten sie die STRG-Taste gedrückt um mehrere Sammlungen auszuwählen.",
							"Fehler!", JOptionPane.ERROR_MESSAGE);
		} else {
			int[] rows = collectionTable.getSelectedRows();
			ArrayList<Integer> collIds = new ArrayList<>();

			for (int curr : rows) {
				collIds.add((int) collectionTable.getValueAt(curr, 0));
			}
			JPanel diagram = DiagramCalculation
					.printCollectionCompareDiagram(collIds);
			DisplayDiagram mainFrame = new DisplayDiagram(null, true, diagram,
					DisplayDiagram.COLLECTION_COMPARE_DIAGRAM, collIds);
			mainFrame.setVisible(true);
		}

	}//GEN-LAST:event_btnShowCompareDiagramActionPerformed

	/**
	 * Opens a new ManageCollection Window
	 * 
	 * @param evt
	 *            the event (unused)
	 */
	private void btnShowAllDiagramActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowAllDiagramActionPerformed
		JPanel diagram = DiagramCalculation.printOverallDiagram();
		DisplayDiagram mainFrame = new DisplayDiagram(null, true, diagram,
				DisplayDiagram.OVERALL_DIAGRAM);
		mainFrame.setVisible(true);
	}//GEN-LAST:event_btnShowAllDiagramActionPerformed

	/**
	 * Calls the loginUser() function and sets the accessLevel of the whole
	 * program accordingly
	 * 
	 * @param evt
	 *            the event (unused)
	 */
	private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
		// TODO add your handling code here:
		int accessLevelofLogin = loginUser();

		if (accessLevelofLogin == 1) { // admin
			setAccessLevel(1);
			Access.setAccessLevelOfProgramm(1);
		}

		if (accessLevelofLogin == 2) { // member
			setAccessLevel(2);
			Access.setAccessLevelOfProgramm(2);
		}
		if (accessLevelofLogin == 3 || accessLevelofLogin == 0) { // guest
			setAccessLevel(3);
			Access.setAccessLevelOfProgramm(3);

		}
	}//GEN-LAST:event_btnLoginActionPerformed

	/**
	 * Displays a JOptionPane for changing a users password
	 * 
	 * @param evt
	 *            the event (unused)
	 */
	private void btnChangePwActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChangePwActionPerformed
		JTextField username = new JTextField();
		username.setText(Access.getActuellLogin());
		username.setEditable(false);

		JTextField password = new JTextField();

		Object[] message = { "Username:", username, "New Password:", password };
		int option = JOptionPane.showConfirmDialog(null, message,
				"Passwort ändern - Neues Passwort",
				JOptionPane.OK_CANCEL_OPTION);
                
                if ( option == JOptionPane.OK_OPTION) {
                    if (password.getText().toString().isEmpty()) {
			JOptionPane.showMessageDialog(null,
					"Bitte geben Sie ein Passwort ein");
                    } else {

			try {
				Access.changePasswortOfLogin(username.getText().toString(),
						password.getText().toString());
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(
                                        null,
                                        "Fehler, bitte versuchen sie es erneut!",
                                        "Fehler!", JOptionPane.ERROR_MESSAGE);
			}
                    }
                }
                

	}//GEN-LAST:event_btnChangePwActionPerformed

	private void btnAddUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddUserActionPerformed
		JTextField username = new JTextField();
		JCheckBox btnLevel = new JCheckBox("Admin");

		JTextField password = new JTextField();

		Object[] message = { "Loginame:", username, "Passwort:", password,
				"AdminStatus?:", btnLevel };
		int option = JOptionPane.showConfirmDialog(null, message,
				"Nutzer anlegen", JOptionPane.OK_CANCEL_OPTION);

		if (option == JOptionPane.OK_OPTION) {
			if (username.getText().isEmpty() || password.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null,
						"Bitte füllen sie alle Felder aus", "Fehler",
						JOptionPane.ERROR_MESSAGE);
			} else {
				String loginName = username.getText();
				String pw = password.getText();
				int loginLevel;
				if (btnLevel.isSelected()) {
					loginLevel = 1;
				} else {
					loginLevel = 2;
				}
				try {
					Access.saveLogin(Access.createLogin(loginName, pw,
							loginLevel));
					JOptionPane.showMessageDialog(null,
							"Ein Benutzer mit dem Namen " + loginName
									+ " wurde erfolgreich angelegt.", "Fehler",
							JOptionPane.INFORMATION_MESSAGE);
				} catch (LoginInUseException e) {
					JOptionPane.showMessageDialog(null, "Der Loginname "
							+ loginName + " ist bereits vergeben!", "Fehler",
							JOptionPane.ERROR_MESSAGE);
				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(null,
							"Fehler, bitte versuchen sie es erneut!", "Fehler",
							JOptionPane.ERROR_MESSAGE);
				}

			}
			fillUserTable();
		}

	}//GEN-LAST:event_btnAddUserActionPerformed

	private void btnDeleteUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteUserActionPerformed
		ArrayList<Login> logins = LoginManager.getInstance().getAllLogins();
		ArrayList<String> names = new ArrayList<>(logins.size() - 1);
		for (Login curr : logins) {
			if (curr.getAccessLevel() != 3) {
				names.add(curr.getLoginName());
			}
		}
		Object[] namesArray = names.toArray();
		JComboBox comboBox = new JComboBox(namesArray);
		JLabel label = new JLabel(
				"Bitte wählen sie den zu löschenden Benutzer aus:");
		Object[] message = { label, " ", comboBox, };
		int option = JOptionPane.showConfirmDialog(null, message,
				"Nutzer löschen", JOptionPane.OK_CANCEL_OPTION);

		if (option == JOptionPane.OK_OPTION) {
			String loginName = (String) comboBox.getSelectedItem();
			try {
				Access.deleteLoginByLoginnameFromDB(loginName);
				JOptionPane.showMessageDialog(null,
						"Der Benutzer mit dem Namen " + loginName
								+ " wurde erfolgreich gelöscht.", "Fehler",
						JOptionPane.INFORMATION_MESSAGE);
			} catch (SQLException ex) {
				JOptionPane.showMessageDialog(null,
						"Fehler, bitte versuchen sie es erneut!", "Fehler",
						JOptionPane.ERROR_MESSAGE);
			} catch (LoginInUseException ex) {
				// TODO wtf?
			} catch (SelfDeleteException ex) {
				JOptionPane.showMessageDialog(null,
						"Sie können nicht ihren eigenen Account löschen!",
						"Fehler", JOptionPane.ERROR_MESSAGE);
			}
			fillUserTable();
		}
	}//GEN-LAST:event_btnDeleteUserActionPerformed

	private void btnChangeUserPwActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChangeUserPwActionPerformed
		ArrayList<Login> logins = LoginManager.getInstance().getAllLogins();
		ArrayList<String> names = new ArrayList<>(logins.size());
		for (Login curr : logins) {
			if (curr.getAccessLevel() != 3) {
				names.add(curr.getLoginName());
			}
		}
		Object[] namesArray = names.toArray();
		JComboBox comboBox = new JComboBox(namesArray);

		JTextField pw1 = new JTextField();
		JTextField pw2 = new JTextField();
		Object[] message = { "Wählen sie den Benutzer aus:", comboBox,
				"Neus Passwort:", pw1, "Passwort wiederholen:", pw2 };
		int option = JOptionPane.showConfirmDialog(null, message,
				"Passwort ändern", JOptionPane.OK_CANCEL_OPTION);
		if (option == JOptionPane.OK_OPTION) {
			if (!pw1.getText().equals(pw2.getText())) {
				JOptionPane.showMessageDialog(null,
						"Die angegebenen Passwörter stimmen nicht überein!",
						"Fehler", JOptionPane.ERROR_MESSAGE);
			} else {
				String loginName = (String) comboBox.getSelectedItem();
				String newPw = pw1.getText();
				try {
					Access.changePasswortOfLogin(loginName, newPw);
					JOptionPane.showMessageDialog(null,
							"Das Passwort des Benutzers " + loginName
									+ " wurde erfolgreich geändert.", "Fehler",
							JOptionPane.INFORMATION_MESSAGE);
				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(null,
							"Fehler, bitte versuchen sie es erneut!", "Fehler",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}//GEN-LAST:event_btnChangeUserPwActionPerformed

	private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
		try {
			int level = Access.accessLevelofLogin("gast", "gast");
			Access.setActuellLogin("gast");
			Access.setAccessLevelOfProgramm(level);
			setAccessLevel(level);
		} catch (UserNameDoesNotExistException | WrongPasswortException ex) {
			// this should never happen, because gast//gast is a hardcoded account
			Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE,
					null, ex);
		}
	}//GEN-LAST:event_btnLogoutActionPerformed

	boolean saveAs(String pfad, String source) {

		JFileChooser chooser;
		if (pfad == null)
			pfad = System.getProperty("user.home");
		File file = new File(pfad.trim());

		chooser = new JFileChooser(pfad);
		chooser.setDialogType(JFileChooser.SAVE_DIALOG);
		FileNameExtensionFilter plainFilter = new FileNameExtensionFilter(
				"PDF", "pdf");
		FileNameExtensionFilter plainFilter2 = new FileNameExtensionFilter(
				"CSV", "csv");

		chooser.removeChoosableFileFilter(chooser.getAcceptAllFileFilter());
		if (pfad == "pdf") {
			chooser.setFileFilter(plainFilter);
		}
		if (pfad == "csv") {
			chooser.setFileFilter(plainFilter2);
		}
		chooser.setCurrentDirectory(file);
		chooser.setDialogTitle("Speichern unter...");

		chooser.setVisible(true);

		int result = chooser.showSaveDialog(this);
		String endung = pfad;
		if (result == JFileChooser.APPROVE_OPTION) {

			pfad = chooser.getSelectedFile().toString();
			file = new File(pfad);
			if (plainFilter.accept(file) || plainFilter2.accept(file)) {
				try {

					if (source == "Donationbox" && endung == "pdf") {
						Access.exportDonationBoxesAsPdf(pfad);
					}
					if (source == "OrganisationPerson" && endung == "pdf") {
						Access.exportOrganisationPersonsAsPdf(pfad);
					}
					if (source == "ContactPerson" && endung == "pdf") {
						Access.exportContactPersonsAsPdf(pfad);
					}

					if (source == "Collection" && endung == "pdf") {
						Access.exportDonationCollectionsAsPdf(pfad);
					}

					if (source == "Donationbox" && endung == "csv") {
						try {
							Access.exportDonationBoxesAsCsv(pfad);
						} catch (IOException e) {
							JOptionPane.showMessageDialog(null,
									"Fehler, Bitte versuchen sie es erneut.",
									"Fehler", JOptionPane.ERROR_MESSAGE);
							e.printStackTrace();
						}  
					 
					}
					if (source == "OrganisationPerson" && endung == "csv") {
						try {
							Access.exportOrganisationPersonsAsCsv(pfad);
						} catch (IOException e) {
							JOptionPane.showMessageDialog(null,
									"Fehler, Bitte versuchen sie es erneut.",
									"Fehler", JOptionPane.ERROR_MESSAGE);
							e.printStackTrace();
						}
					}
					if (source == "ContactPerson" && endung == "csv") {
						try {
							Access.exportContactPersonsAsCsv(pfad);
						} catch (IOException e) {
							JOptionPane.showMessageDialog(null,
									"Fehler, Bitte versuchen sie es erneut.",
									"Fehler", JOptionPane.ERROR_MESSAGE);
							e.printStackTrace();
						}
					}

					if (source == "Collection" && endung == "csv") {
						try {
							Access.exportDonationCollectionsAsCsv(pfad);
						} catch (IOException e) {
							JOptionPane.showMessageDialog(null,
									"Fehler, Bitte versuchen sie es erneut.",
									"Fehler", JOptionPane.ERROR_MESSAGE);
							e.printStackTrace();
						}
					}

				} catch (FileNotFoundException ex) {
					Logger.getLogger(MainWindow.class.getName()).log(
							Level.SEVERE, null, ex);
					JOptionPane.showMessageDialog(null,
							"Es ist ein Speicherfehler aufgetreten!", "Fehler",
							JOptionPane.ERROR_MESSAGE);
					
				} catch (DocumentException ex) {
					Logger.getLogger(MainWindow.class.getName()).log(
							Level.SEVERE, null, ex);
					JOptionPane.showMessageDialog(null,
							"Es ist ein Speicherfehler aufgetreten!", "Fehler",
							JOptionPane.ERROR_MESSAGE);
				}
			} else if (endung == "pdf") {
				pfad += ".pdf";
			}
			if (endung == "csv") {
				pfad += ".csv";
			}

			file = new File(pfad);
			if (plainFilter.accept(file) || plainFilter2.accept(file)) {
				try {

					if (source == "Donationbox" && endung == "pdf") {
						Access.exportDonationBoxesAsPdf(pfad);
					}
					if (source == "OrganisationPerson" && endung == "pdf") {
						Access.exportOrganisationPersonsAsPdf(pfad);
					}
					if (source == "ContactPerson" && endung == "pdf") {
						Access.exportContactPersonsAsPdf(pfad);
					}

					if (source == "Collection" && endung == "pdf") {
						Access.exportDonationCollectionsAsPdf(pfad);
					}

					if (source == "Donationbox" && endung == "csv") {
						try {
							Access.exportDonationBoxesAsCsv(pfad);
						} catch (IOException e) {
							JOptionPane.showMessageDialog(null,
									"Fehler, Bitte versuchen sie es erneut.",
									"Fehler", JOptionPane.ERROR_MESSAGE);
							e.printStackTrace();
						}
					}
					if (source == "OrganisationPerson" && endung == "csv") {
						try {
							Access.exportOrganisationPersonsAsCsv(pfad);
						} catch (IOException e) {
							JOptionPane.showMessageDialog(null,
									"Fehler, Bitte versuchen sie es erneut.",
									"Fehler", JOptionPane.ERROR_MESSAGE);
							e.printStackTrace();
						}
					}
					if (source == "ContactPerson" && endung == "csv") {
						try {
							Access.exportContactPersonsAsCsv(pfad);
						} catch (IOException e) {
							JOptionPane.showMessageDialog(null,
									"Fehler, Bitte versuchen sie es erneut.",
									"Fehler", JOptionPane.ERROR_MESSAGE);
							e.printStackTrace();
						}
					}

					if (source == "Collection" && endung == "csv") {
						try {
							Access.exportDonationCollectionsAsCsv(pfad);
						} catch (IOException e) {
							JOptionPane.showMessageDialog(null,
									"Fehler, Bitte versuchen sie es erneut.",
									"Fehler", JOptionPane.ERROR_MESSAGE);
							e.printStackTrace();
						}
					}

				}

				catch (FileNotFoundException ex) {
					Logger.getLogger(MainWindow.class.getName()).log(
							Level.SEVERE, null, ex);
					JOptionPane.showMessageDialog(null,
							"Es ist ein Speicherfehler aufgetreten!", "Fehler",
							JOptionPane.ERROR_MESSAGE);
				} catch (DocumentException ex) {
					Logger.getLogger(MainWindow.class.getName()).log(
							Level.SEVERE, null, ex);
					JOptionPane.showMessageDialog(null,
							"Es ist ein Speicherfehler aufgetreten!", "Fehler",
							JOptionPane.ERROR_MESSAGE);
				}
			}

			chooser.setVisible(false);
			return true;
		}
		chooser.setVisible(false);
		return false;
	}

	/**
	 * @author Kevin Schneider
	 * 
	 * @param evt
	 */
	private void btnExportDonationBoxToPdfActionPerformed(
			java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportDonationBoxToPdfActionPerformed

		saveAs("pdf", "Donationbox");

	}//GEN-LAST:event_btnExportDonationBoxToPdfActionPerformed

	/**
	 * @author Kevin Schneider
	 * 
	 * @param evt
	 */
	private void btnExportDonationBoxToCsvActionPerformed(
			java.awt.event.ActionEvent evt) {

		saveAs("csv", "Donationbox");

	}

	/**
	 * @author Kevin Schneider
	 * 
	 * @param evt
	 */
	private void btnExportCollectionToPdfActionPerformed(
			java.awt.event.ActionEvent evt) {

		saveAs("pdf", "Collection");

		//GEN-FIRST:event_btnExportCollectionToPdfActionPerformed
		/*
		 * //PDF---------------
		 * 
		 * //PDF---------------
		 * 
		 * //DIAGRAM---------------- ArrayList<Integer> listi = new
		 * ArrayList<Integer>(); listi.add(1); listi.add(2); listi.add(3);
		 * listi.add(4); listi.add(5); listi.add(6); listi.add(7); listi.add(8);
		 * listi.add(9);
		 * 
		 * try { DiagramCalculation.exportCollectionCompareDiagram(listi,
		 * System.getProperty("user.home") + "/hallo.png"); } catch (IOException
		 * e1) { // TODO Auto-generated catch block e1.printStackTrace(); }
		 * DiagramCalculation.printCollectionDiagram(3);
		 */

	}//GEN-LAST:event_btnExportCollectionToPdfActionPerformed

	/**
	 * 
	 * @author Kevin Schneider
	 * 
	 * @param evt
	 */
	private void btnExportCollectionToCSVActionPerformed(
			java.awt.event.ActionEvent evt) {
		saveAs("csv", "Collection");
	}

	/**
	 * @author Kevin Schneider
	 * 
	 * @param evt
	 */
	private void btnExportOrganizationPersonToPdfActionPerformed(
			java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportOrganizationPersonToPdfActionPerformed

		saveAs("pdf", "OrganisationPerson");
	}//GEN-LAST:event_btnExportOrganizationPersonToPdfActionPerformed

	/**
	 * @author Kevin Schneider
	 * 
	 * @param evt
	 */
	private void btnExportOrganisationPersonToCsvActionPerformed(
			java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportOrganisationPersonToCsvActionPerformed
		saveAs("csv", "OrganisationPerson");

	}//GEN-LAST:event_btnExportOrganisationPersonToCsvActionPerformed

	/**
	 * @author Timo
	 * 
	 * @param evt
	 */
	private void btnAddOrganisationPersonActionPerformed(
			java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddOrganisationPersonActionPerformed
		AddOrganisationPerson mainFrame = new AddOrganisationPerson(null, true);

		mainFrame.setVisible(true);
		orgPersModel.setRowCount(0);
		fillOrganisationPersonTable(Access.getTempOrganisationPersons());
	}//GEN-LAST:event_btnAddOrganisationPersonActionPerformed

	/**
	 * @author Kevin Schneider
	 * 
	 * @param evt
	 */
	private void btnExportContactPersonToCsvActionPerformed(
			java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportContactPersonToCsvActionPerformed
		saveAs("csv", "ContactPerson");
	}//GEN-LAST:event_btnExportContactPersonToCsvActionPerformed

	/**
	 * @author Kevin Schneider
	 * 
	 * @param evt
	 */
	private void btnExportContactPersonToPdfActionPerformed(
			java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportContactPersonToPdfActionPerformed
		saveAs("pdf", "ContactPerson");
	}//GEN-LAST:event_btnExportContactPersonToPdfActionPerformed

	private void btnAddContactPersonActionPerformed(
			java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddContactPersonActionPerformed
		AddContactPerson mainFrame = new AddContactPerson(null, true);
		mainFrame.setVisible(true);
		refreshTables();
	}//GEN-LAST:event_btnAddContactPersonActionPerformed

	private void btnSearchCollectionActionPerformed(
			java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchCollectionActionPerformed
		SearchCollection mainFrame = new SearchCollection(null, true);
		mainFrame.setVisible(true);
		collectionModel.setRowCount(0);
		try {
			fillCollectionTable(CollectionAccess.getTempDonationCollections());
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Datenbankfehler!", "Fehler!",
					JOptionPane.ERROR_MESSAGE);
		} catch (ParseException ex) {
			JOptionPane.showMessageDialog(null, "Datenbankfehler!", "Fehler!",
					JOptionPane.ERROR_MESSAGE);
		}

	}//GEN-LAST:event_btnSearchCollectionActionPerformed

	private void btnAddDonationBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddDonationBoxActionPerformed
		AddDonationBox mainFrame = new AddDonationBox(null, true);
		mainFrame.setVisible(true);
		refreshTables();
	}//GEN-LAST:event_btnAddDonationBoxActionPerformed

	/**
	 * @author Timo
	 * 
	 * @param evt
	 */
	private void btnSearchBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchBoxActionPerformed
		SearchDonationBoxes mainFrame = new SearchDonationBoxes(null, true);
		mainFrame.setVisible(true);
		boxModel.setRowCount(0);
		fillBoxTable(Access.getTempDonationBoxes());
	}//GEN-LAST:event_btnSearchBoxActionPerformed

	/**
	 * @author Timo, Marcel
	 * 
	 * @param evt
	 */
	private void btnEditDonationBoxActionPerformed(
			java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditDonationBoxActionPerformed
		try {
			int n = boxTable.getSelectedRow();
			int boxId = (int) boxTable.getValueAt(n, 0);

			if (n == -1) {
				JOptionPane
						.showMessageDialog(
								null,
								"Keine Spendendose ausgewÃ¤hlt!\n\nBitte wählen sie vor Betätigen des Knopfes die zu kommentierende Dose aus.",
								"Fehler!", JOptionPane.ERROR_MESSAGE);
			}

			if (Access.getDonationBoxById(boxId).getIsDeleted()) {
				JOptionPane.showMessageDialog(null,
						"Gelöschte Dosen können nicht mehr bearbeitet werden");
			} else {
				// this.setNewComment((String) boxTable.getValueAt(n, 4));

				EditDonationBox mainFrame = new EditDonationBox(null, true,
						boxId);
				mainFrame.setVisible(true);
				refreshTables();
			}
		} catch (ArrayIndexOutOfBoundsException x) {

			JOptionPane
					.showMessageDialog(
							null,
							"Keine Spendendose ausgewÃ¤hlt!\n\nBitte wählen sie vor Betätigen des Knopfes die zu kommentierende Dose aus.",
							"Fehler!", JOptionPane.ERROR_MESSAGE);

		}
	}//GEN-LAST:event_btnEditDonationBoxActionPerformed

	/**
	 * @author Timo
	 * 
	 * @param evt
	 */
	private void btnShowBoxDetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowBoxDetailsActionPerformed
		int n = boxTable.getSelectedRow();
		if (n == -1) {
			JOptionPane
					.showMessageDialog(
							null,
							"Keine Spendendose ausgewählt!\n\nBitte wählen sie vor Betätigen des Knopfes die zu betrachtende Dose aus.",
							"Fehler!", JOptionPane.ERROR_MESSAGE);
		} else {
			int toDetail = (int) boxTable.getValueAt(n, 0);
			DetailsDonationBox MainFrame = new DetailsDonationBox(null, true,
					toDetail);
			MainFrame.setVisible(true);

		}
	}//GEN-LAST:event_btnShowBoxDetailsActionPerformed

	/**
	 * @author Marcel
	 * 
	 * @param evt
	 */
	private void btnEditOrganisationPersonActionPerformed(
			java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditOrganisationPersonActionPerformed
		int currentRowSelected = orgPersTable.getSelectedRow();
		if (currentRowSelected == -1) {
			JOptionPane.showMessageDialog(null, "Kein Eintrag ausgewählt");
		} else {
			int persId = (int) orgPersTable.getValueAt(currentRowSelected, 0);
			EditOrganizationPerson mainFrame = new EditOrganizationPerson(null,
					true, persId);
			mainFrame.setVisible(true);
			refreshTables();

		}

	}//GEN-LAST:event_btnEditOrganisationPersonActionPerformed

	/**
	 * @author Marcel
	 * 
	 * @param evt
	 */
	private void btnEditContactPersonActionPerformed(
			java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditContactPersonActionPerformed
		int currentRowSelected = conPersTable.getSelectedRow();
		if (currentRowSelected == -1) {
			JOptionPane.showMessageDialog(null, "Kein Eintrag ausgewählt");
		} else {
			int persId = (int) conPersTable.getValueAt(currentRowSelected, 0);
			EditContactPerson mainFrame = new EditContactPerson(null, true, persId);
			mainFrame.setVisible(true);
			refreshTables();
		}
	}//GEN-LAST:event_btnEditContactPersonActionPerformed

	private void btnSearchContactPersonActionPerformed(
			java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchContactPersonActionPerformed
		SearchContactPerson mainFrame = new SearchContactPerson(null, true);
		mainFrame.setVisible(true);
		conPersModel.setRowCount(0);
		fillContactPersonTable(Access.getTempContactPersons());
	}//GEN-LAST:event_btnSearchContactPersonActionPerformed

	private void btnShowCollectionsOfContactPersonActionPerformed(
			java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowCollectionsOfContactPersonActionPerformed
		int n = conPersTable.getSelectedRow();
		if (n == -1) {
			JOptionPane
					.showMessageDialog(
							null,
							"Keine Kontaktperson ausgewählt!\n\nBitte wählen sie vor Betätigen des Knopfes eine Person aus.",
							"Fehler!", JOptionPane.ERROR_MESSAGE);
		} else {
			try {
				int contactId = (int) conPersTable.getValueAt(n, 0);
				collectionModel.setRowCount(0);
				CollectionAccess.setTempDonationCollections(CollectionAccess
						.searchDonationCollections(-1, -1, contactId, null, -1,
								-1, null, null, null, null, null, true, true));
				fillCollectionTable(CollectionAccess
						.getTempDonationCollections());
				paneMain.setSelectedIndex(0);
			} catch (SQLException ex) {
				JOptionPane.showMessageDialog(null, "Datenbankfehler!",
						"Fehler!", JOptionPane.ERROR_MESSAGE);
			} catch (ParseException ex) {
				JOptionPane.showMessageDialog(null, "Datenbankfehler!",
						"Fehler!", JOptionPane.ERROR_MESSAGE);
			}
		}

	}//GEN-LAST:event_btnShowCollectionsOfContactPersonActionPerformed

	private void btnClearDonationBoxActionPerformed(
			java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearDonationBoxActionPerformed
		try {
			int n = boxTable.getSelectedRow();

			int selected = (int) boxTable.getValueAt(boxTable.getSelectedRow(),
					0);

			if (n == -1) {
				JOptionPane
						.showMessageDialog(
								null,
								"Keine Spendenbox ausgewählt!\n\nBitte wählen sie vor Betätigen des Knopfes eine Spendenbox aus.",
								"Fehler!", JOptionPane.ERROR_MESSAGE);
			}

			else if (!Access.getDonationBoxById(selected).isInUse()) {

				JOptionPane.showMessageDialog(null,
						"Spendendose nicht in Benutzung!", "Fehler!",
						JOptionPane.ERROR_MESSAGE);
			}

			else if (Access.getDonationBoxById(selected).getIsDeleted()) {
				JOptionPane.showMessageDialog(null,
						"Spendendose bereits gelöscht!", "Fehler!",
						JOptionPane.ERROR_MESSAGE);

			} else {
				int boxId = (int) boxTable.getValueAt(n, 0);
				ClearDonationBox mainFrame = new ClearDonationBox(null, true,
						boxId);
				// int collcetionId =
				// Access.getDonationBoxById(boxId).getCollectionId(); //added
				// sz
				// Access.saveClearingDonationBox(mainFrame, collcetionId ,
				// boxId); //added sz
				mainFrame.setVisible(true);
			}

		} catch (ArrayIndexOutOfBoundsException x) {

			JOptionPane
					.showMessageDialog(
							null,
							"Keine Spendenbox ausgewählt!\n\nBitte wählen sie vor Betätigen des Knopfes eine Spendendose aus.",
							"Fehler!", JOptionPane.ERROR_MESSAGE);

		}

		refreshTables();
	}//GEN-LAST:event_btnClearDonationBoxActionPerformed

	private void btnShowAllBoxesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowAllBoxesActionPerformed
		boxModel.setRowCount(0);
		Access.setDonationBoxSearchNull();
		fillBoxTable(Access.getTempDonationBoxes());
	}//GEN-LAST:event_btnShowAllBoxesActionPerformed

	private void btnShowAllCollectionsActionPerformed(
			java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowAllCollectionsActionPerformed
		collectionModel.setRowCount(0);
		CollectionAccess.setDonationCollectionSearchNull();
		try {
			fillCollectionTable(CollectionAccess.getTempDonationCollections());
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Datenbankfehler!", "Fehler!",
					JOptionPane.ERROR_MESSAGE);
		} catch (ParseException ex) {
			JOptionPane.showMessageDialog(null, "Datenbankfehler!", "Fehler!",
					JOptionPane.ERROR_MESSAGE);
		}
	}//GEN-LAST:event_btnShowAllCollectionsActionPerformed

	private void btnShowAllOrganisationPersonsActionPerformed(
			java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowAllOrganisationPersonsActionPerformed
		orgPersModel.setRowCount(0);
		Access.setOrganisationPersonSearchNull();
		fillOrganisationPersonTable(Access.getTempOrganisationPersons());
	}//GEN-LAST:event_btnShowAllOrganisationPersonsActionPerformed

	private void btnShowAllContactPersonsActionPerformed(
			java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowAllContactPersonsActionPerformed
		conPersModel.setRowCount(0);
		Access.setContactPersonSearchNull();
		fillContactPersonTable(Access.getTempContactPersons());
	}//GEN-LAST:event_btnShowAllContactPersonsActionPerformed

	private void btnShowCollectionDetailsActionPerformed(
			java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowCollectionDetailsActionPerformed
		int n = collectionTable.getSelectedRow();
		if (n == -1) {
			JOptionPane
					.showMessageDialog(
							null,
							"Keine Sammlung ausgewählt!\n\nBitte wählen sie vor Betätigen des Knopfes die zu betrachtende Sammlung aus.",
							"Fehler!", JOptionPane.ERROR_MESSAGE);
		} else {
			int toDetail = (int) collectionTable.getValueAt(n, 0);
			ManageCollection mainFrame = new ManageCollection(null, true,
					toDetail);
			mainFrame.setVisible(true);
		}
	}//GEN-LAST:event_btnShowCollectionDetailsActionPerformed

	private void btnShowCollectionsOfOrganisationtPersonActionPerformed(
			java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowCollectionsOfOrganisationtPersonActionPerformed
		int n = orgPersTable.getSelectedRow();
		if (n == -1) {
			JOptionPane
					.showMessageDialog(
							null,
							"Kein Organisationsmitglied ausgewählt!\n\nBitte wählen sie vor Betätigen des Knopfes eine Person aus.",
							"Fehler!", JOptionPane.ERROR_MESSAGE);
		} else {
			try {
				int orgaId = (int) orgPersTable.getValueAt(n, 0);
				collectionModel.setRowCount(0);
				CollectionAccess.setTempDonationCollections(CollectionAccess
						.searchDonationCollections(-1, orgaId, -1, null, -1,
								-1, null, null, null, null, null, true, true));
				fillCollectionTable(CollectionAccess
						.getTempDonationCollections());
				paneMain.setSelectedIndex(0);
			} catch (SQLException ex) {
				JOptionPane.showMessageDialog(null, "Datenbankfehler!",
						"Fehler!", JOptionPane.ERROR_MESSAGE);
			} catch (ParseException ex) {
				JOptionPane.showMessageDialog(null, "Datenbankfehler!",
						"Fehler!", JOptionPane.ERROR_MESSAGE);
			}
		}
	}//GEN-LAST:event_btnShowCollectionsOfOrganisationtPersonActionPerformed

	private void btnShowOrgPersDetailsActionPerformed(
			java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowOrgPersDetailsActionPerformed
		int n = orgPersTable.getSelectedRow();
		if (n == -1) {
			JOptionPane
					.showMessageDialog(
							null,
							"Kein Organisationsmitglied ausgewählt!\n\nBitte wählen sie vor Betätigen des Knopfes eine Person aus.",
							"Fehler!", JOptionPane.ERROR_MESSAGE);
		} else {
			int toDetail = (int) orgPersTable.getValueAt(n, 0);
			DetailsOrganisationPerson MainFrame = new DetailsOrganisationPerson(
					null, true, toDetail);
			MainFrame.setVisible(true);
		}
	}//GEN-LAST:event_btnShowOrgPersDetailsActionPerformed

	private void btnDeleteDonationBoxActionPerformed(
			java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteDonationBoxActionPerformed
		int n[] = boxTable.getSelectedRows();

		try {
			int selected = (int) boxTable.getValueAt(boxTable.getSelectedRow(),
					0);

			if (n.length == 0) {
				JOptionPane
						.showMessageDialog(
								null,
								"Keine Spendendose zum löschen ausgewahlt!\n\nBitte wahlen sie vor Betatigen des Löschen Knopfes die zu löschende Dose aus.",
								"Fehler!", JOptionPane.ERROR_MESSAGE);
			}

			if (Access.getDonationBoxById(selected).getIsDeleted()) {
				JOptionPane.showMessageDialog(null,
						"Spendendose bereits gelöscht!", "Fehler!",
						JOptionPane.ERROR_MESSAGE);

			}

			else {
				int choice = JOptionPane.showConfirmDialog(null,
						"Wollen Sie die ausgewählten Dosen wirklich löschen?",
						"Löschen bestätigen", JOptionPane.YES_NO_OPTION);
				if (choice == 0) {
					for (int currInd : n) {
						int toDel = (int) boxTable.getValueAt(currInd, 0);
						try {
							Access.deleteDonationBox(toDel);
						} catch (SQLException ex) {
							JOptionPane.showMessageDialog(null,
									"Datenbankfehler!", "Fehler!",
									JOptionPane.ERROR_MESSAGE);
						} catch (ParseException ex) {
							JOptionPane.showMessageDialog(null, "Fehler!",
									"Fehler!", JOptionPane.ERROR_MESSAGE);
						} catch (DonationBoxCurrentlyAssignedException ex) {
							JOptionPane
									.showMessageDialog(
											null,
											"Die Spendendose ist momentan der Sammlung mit der ID "
													+ ex.getCollectionId()
													+ " zugeordnet\nund kann daher nicht gelöscht werden.",
											"Fehler!",
											JOptionPane.ERROR_MESSAGE);
						}

					}
					boxModel.setRowCount(0);
					refreshTables();
					JOptionPane.showMessageDialog(null, "Löschen erfolgreich.",
							"Löschen erfolgreich!",
							JOptionPane.INFORMATION_MESSAGE);

				}

			}
		} catch (ArrayIndexOutOfBoundsException x) {

			JOptionPane
					.showMessageDialog(
							null,
							"Keine Spendendose zum löschen ausgewahlt!\n\nBitte wahlen sie vor Betatigen des Löschen Knopfes die zu löschende Dose aus.",
							"Fehler!", JOptionPane.ERROR_MESSAGE);
		}

	}//GEN-LAST:event_btnDeleteDonationBoxActionPerformed

	private void btnFinishCollectionActionPerformed(
			java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFinishCollectionActionPerformed
		try {
			int currentRowSelected = collectionTable.getSelectedRow();
			int collId = (int) collectionTable
					.getValueAt(currentRowSelected, 0);

			if (currentRowSelected == -1) {
				JOptionPane.showMessageDialog(null, "Kein Eintrag ausgewählt");
			}

			else if (CollectionAccess.checkIfIsCompleted(collId)) {
				JOptionPane
						.showMessageDialog(null,
								"Abgeschlossene Sammlungen können nicht mehr abgeschlossen werden");
			}

			else {

				int n = JOptionPane
						.showConfirmDialog(
								null,
								"Sind sie sicher, dass sie die Sammlung mit der ID "
										+ collId
										+ " abschließen"
										+ " möchten?\n\nDurch das abschließen der Sammlung werden die zugeordneten"
										+ " Dosen für andere Sammlungen freigegeben.\n\n\nStellen Sie sicher, dass "
										+ "alle Dosen vor dem abschließen der Leerung mit Hilfe des \"Dose Leeren\" "
										+ "Buttons geleert wurden!",
								"Sammlung abschließen",
								JOptionPane.OK_CANCEL_OPTION);
				if (n == 0) {
					try {
						CollectionAccess.finishCollection(collId);
						refreshTables();
					} catch (SQLException ex) {

						JOptionPane.showMessageDialog(null, "Datenbankfehler!",
								"Fehler!", JOptionPane.ERROR_MESSAGE);
					} catch (ParseException ex) {
						JOptionPane.showMessageDialog(null, "Datenbankfehler!",
								"Fehler!", JOptionPane.ERROR_MESSAGE);
						;
					}
				}

			}
		} catch (ArrayIndexOutOfBoundsException c) {

			JOptionPane.showMessageDialog(null,
					"Bitte treffen Sie eine Auswahl");

		}
	}//GEN-LAST:event_btnFinishCollectionActionPerformed

	private void btnShowConPersDetailsActionPerformed(
			java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowConPersDetailsActionPerformed
		int n = conPersTable.getSelectedRow();
		if (n == -1) {
			JOptionPane
					.showMessageDialog(
							null,
							"Kein Organisationsmitglied ausgewählt!\n\nBitte wählen sie vor Betätigen des Knopfes eine Person aus.",
							"Fehler!", JOptionPane.ERROR_MESSAGE);
		} else {
			int toDetail = (int) conPersTable.getValueAt(n, 0);
			DetailsContactPerson MainFrame = new DetailsContactPerson(null,
					true, toDetail);
			MainFrame.setVisible(true);
			refreshTables();
		}
	}//GEN-LAST:event_btnShowConPersDetailsActionPerformed

	/**
	 * @author Timo
	 * 
	 * @param evt
	 */
	private void btnSearchOrganisationPersonActionPerformed(
			java.awt.event.ActionEvent evt) {
		SearchOrganisationPerson mainFrame = new SearchOrganisationPerson(null,
				true);
		mainFrame.setVisible(true);
		orgPersModel.setRowCount(0);
		fillOrganisationPersonTable(Access.getTempOrganisationPersons());
	}

	private void btnAddCollectionActionPerformed(java.awt.event.ActionEvent evt) {
		AddCollection MainFrame = new AddCollection(null, true);
		MainFrame.setModal(true);
		MainFrame.setVisible(true);
		refreshTables();
	}

	/**
	 * @author Timo
	 * 
	 *         This function fills the conPersTable with the elements of the
	 *         given ArrayList<ContactPerson> It does not clear the jTable
	 *         before adding the elements, so make sure to clear it in advance
	 *         if needed!
	 * 
	 * @param list
	 *            The list of ContactPersons to be displayed
	 */
	private void fillContactPersonTable(ArrayList<ContactPerson> list) {
		for (ContactPerson currPers : list) {

			String Addresse = "";

			if (currPers.getAddress() != null) {
				if (currPers.getAddress().getZip() != 0) {
					Addresse = String.valueOf(currPers.getAddress().getZip());
				}
				if (!currPers.getAddress().getLocationName().equals("")) {
					Addresse = Addresse + " "
							+ currPers.getAddress().getLocationName();
				}
				if (!currPers.getAddress().getStreetName().equals("")) {
					Addresse = Addresse + ", "
							+ currPers.getAddress().getStreetName();
				}
				if (!currPers.getAddress().getHauseNumber().equals("")) {
					Addresse = Addresse + " "
							+ currPers.getAddress().getHauseNumber();
				}
			}

			conPersModel.insertRow(
					conPersModel.getRowCount(),
					new Object[] {
							currPers.getPersonId(),
							"" + currPers.getLastName() + ", "
									+ currPers.getForename(), Addresse,
							currPers.getEmail(), currPers.getTelephoneNumber(),
							currPers.getMobilNumber(), currPers.getComment() });
		}

	}

	/**
	 * @author Timo
	 * 
	 *         This function fills the conPersTable with the elements of the
	 *         given ArrayList<OrganisationPerson> It does not clear the jTable
	 *         before adding the elements, so make sure to clear it in advance
	 *         if needed!
	 * 
	 * @param list
	 *            The list of OrganisationPersons to be displayed
	 */
	private void fillOrganisationPersonTable(ArrayList<OrganisationPerson> list) {
		for (OrganisationPerson currPers : list) {

			String Addresse = "";

			if (currPers.getAddress() != null) {
				if (currPers.getAddress().getZip() != 0) {
					Addresse = String.valueOf(currPers.getAddress().getZip());
				}
				if (!currPers.getAddress().getLocationName().equals("")) {
					Addresse = Addresse + " "
							+ currPers.getAddress().getLocationName();
				}
				if (!currPers.getAddress().getStreetName().equals("")) {
					Addresse = Addresse + ", "
							+ currPers.getAddress().getStreetName();
				}
				if (!currPers.getAddress().getHauseNumber().equals("")) {
					Addresse = Addresse + " "
							+ currPers.getAddress().getHauseNumber();
				}
			}
			orgPersModel.insertRow(
					orgPersModel.getRowCount(),
					new Object[] {
							currPers.getPersonId(),
							"" + currPers.getLastName() + ", "
									+ currPers.getForename(), Addresse,
							currPers.getEmail(), currPers.getTelephoneNumber(),
							currPers.getMobilNumber(),
							currPers.getIsActiveMemberForTable(),
							currPers.getComment() });
		}

	}

	/**
	 * @author Timo
	 * 
	 *         This function fills the BoxTable with the elements of the given
	 *         ArrayList<DonationBox> It does not clear the jTable before adding
	 *         the elements, so make sure to clear it in advance if needed!
	 * 
	 * @param list
	 *            The list of DonationBoxes to be displayed
	 */
	private void fillBoxTable(ArrayList<DonationBox> list) {
		for (DonationBox currBox : list) {
			String status;
			if (currBox.getIsDeleted()) {
				status = "gelöscht";
			} else {
				status = currBox.getInUseForTable();
			}
			float sum = 0.00f;
			for (ClearingDonationBox currClear : currBox
					.getClearingDonationBoxes()) {
				sum += currClear.getSum();
			}
			String name;
			if (currBox.getCurrentResponsiblePerson().getLastName() == null
					|| currBox.getCurrentResponsiblePerson().getLastName()
							.equals("")) {
				if (currBox.getCurrentResponsiblePerson().getForename() == null
						|| currBox.getCurrentResponsiblePerson().getForename()
								.equals("")) {
					name = "-";
				} else {
					name = currBox.getCurrentResponsiblePerson().getForename();
				}
			} else {
				if (currBox.getCurrentResponsiblePerson().getForename() == null
						|| currBox.getCurrentResponsiblePerson().getForename()
								.equals("")) {
					name = currBox.getCurrentResponsiblePerson().getLastName();
				} else {
					name = currBox.getCurrentResponsiblePerson().getLastName()
							+ ", "
							+ currBox.getCurrentResponsiblePerson()
									.getForename();
				}
			}

			boxModel.insertRow(boxModel.getRowCount(),
					new Object[] { currBox.getBoxId(), status, sum, name,
							currBox.getComment() });
		}

	}

	/**
	 * { if(currBox.getCurrentResponsiblePerson().getLastName() != null ||
	 * !currBox.getCurrentResponsiblePerson().getLastName().equals("")) { name =
	 * currBox.getCurrentResponsiblePerson().getLastName();
	 * if(currBox.getCurrentResponsiblePerson().getForename() != null ||
	 * !currBox.getCurrentResponsiblePerson().getForename().equals("")) { name =
	 * name + ", " + currBox.getCurrentResponsiblePerson().getForename(); } }
	 * else { name = currBox.getCurrentResponsiblePerson().getForename(); } }
	 * else name = currBox.getCurrentResponsiblePerson().getForename();
	 * 
	 * @author Timo
	 * 
	 *         This function fills the CollectionTable with the elements of the
	 *         given ArrayList<DonationCollection> It does not clear the jTable
	 *         before adding the elements, so make sure to clear it in advance
	 *         if needed!
	 * 
	 * @param list
	 *            The list of DonationCollections to be displayed
	 */
	private void fillCollectionTable(ArrayList<DonationCollection> list) {
		for (DonationCollection currCollection : list) {
			String begin;
			String end;
			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
			String adr;
			String status;
			if (currCollection.getBeginPeriode() != null) {
				begin = sdf.format(currCollection.getBeginPeriode().getTime());
			} else {
				begin = "-";
			}

			if (currCollection.getEndPeriode() != null) {
				end = sdf.format(currCollection.getEndPeriode().getTime());
			} else {
				end = "-";
			}

			if (currCollection.getClass().getSimpleName()
					.equals("StreetDonationCollection")) {
				adr = "-";
			} else {
				FixedDonationCollection fixed = (FixedDonationCollection) currCollection;
				adr = fixed.getFixedAddress().toStringTable();
			}

			if (currCollection.getIsCompleted()) {
				status = "Ja";
			} else {
				status = "Nein";
			}

			collectionModel.insertRow(collectionModel.getRowCount(),
					new Object[] {
							currCollection.getCollectionId(),
							status,
							currCollection.getOrganizationPerson()
									.getLastName()
									+ ", "
									+ currCollection.getOrganizationPerson()
											.getForename(), adr, begin, end,
							currCollection.getSum(), // This
														// should
														// just
														// be
														// currCollection.getSum()
														// !
							currCollection.getComment() });

		}

	}

	private void fillUserTable() {
		userModel.setRowCount(0);
		ArrayList<Login> list = LoginManager.getInstance().getAllLogins();
		String level;
		for (Login curr : list) {
			if (curr.getAccessLevel() == 1) {
				level = "1 (Admin)";
				userModel.insertRow(userModel.getRowCount(), new Object[] {
						curr.getLoginName(), level });
			} else if (curr.getAccessLevel() == 2) {
				level = "2 (Mitglied)";
				userModel.insertRow(userModel.getRowCount(), new Object[] {
						curr.getLoginName(), level });
			} else {
				// Gäste sollen nicht angezeigt werden
			}
		}
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		/* Set the Nimbus look and feel */
		// <editor-fold defaultstate="collapsed" desc="Generated Code">
		// desc=" Look and feel setting code (optional) ">
		/*
		 * If Nimbus (introduced in Java SE 6) is not available, stay with the
		 * default look and feel. For details see
		 * http://download.oracle.com/javase
		 * /tutorial/uiswing/lookandfeel/plaf.html
		 */

		DataAccess.getInstance().initDataAccessConnection();

		try {
			DataAccess.getInstance().createBasicTables();
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null,
					"Fehler, Bitte versuchen sie es erneut.", "Fehler",
					JOptionPane.ERROR_MESSAGE);
			e1.printStackTrace();
		}

		try {
			Access.loadAllDonationBoxes();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Fehler, Bitte versuchen sie es erneut.", "Fehler",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(null,
					"Fehler, Bitte versuchen sie es erneut.", "Fehler",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		try {
			Access.loadAllPersons();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Fehler, Bitte versuchen sie es erneut.", "Fehler",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

		try {
			CollectionAccess.loadAllCollectionsFromDB();
			;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Fehler, Bitte versuchen sie es erneut.", "Fehler",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(null,
					"Fehler, Bitte versuchen sie es erneut.", "Fehler",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

		try {
			Access.loadBoxHistories();
		} catch (SQLException ex) {
			Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE,
					null, ex);
		} catch (ParseException ex) {
			Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE,
					null, ex);
		}

		try {
			PersonHistoryAccess.loadAllHistoriesHistoriesFromDB();
		} catch (SQLException ex) {
			Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE,
					null, ex);
		}

		try {
			Access.loadAllLogins();
		} catch (SQLException ex) {
			Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE,
					null, ex);
		} catch (LoginInUseException ex) {
			Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE,
					null, ex);
		}

		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
					.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		}

		// Login as Guest
		int level;
		try {
			level = Access.accessLevelofLogin("gast", "gast");
			Access.setActuellLogin("gast");
			Access.setAccessLevelOfProgramm(level);
			DataAccess.getInstance().initDataAccessConnection();
		} catch (UserNameDoesNotExistException | WrongPasswortException e) {
			// this can not happen, because gast//gast is hardcoded into the system
			e.printStackTrace();
		}

		//</editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new MainWindow().setVisible(true);
			}
		});
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JPanel adminPane;
	private javax.swing.JTable boxTable;
	private javax.swing.JButton btnAddCollection;
	private javax.swing.JButton btnAddContactPerson;
	private javax.swing.JButton btnAddDonationBox;
	private javax.swing.JButton btnAddOrganisationPerson;
	private javax.swing.JButton btnAddUser;
	private javax.swing.JButton btnChangePw;
	private javax.swing.JButton btnChangeUserPw;
	private javax.swing.JButton btnClearDonationBox;
	private javax.swing.JButton btnDeleteDonationBox;
	private javax.swing.JButton btnDeleteUser;
	private javax.swing.JButton btnEditContactPerson;
	private javax.swing.JButton btnEditDonationBox;
	private javax.swing.JButton btnEditOrganisationPerson;
	private javax.swing.JButton btnExportCollectionToCSV;
	private javax.swing.JButton btnExportCollectionToPdf;
	private javax.swing.JButton btnExportContactPersonToCsv;
	private javax.swing.JButton btnExportContactPersonToPdf;
	private javax.swing.JButton btnExportDonationBoxToCsv;
	private javax.swing.JButton btnExportDonationBoxToPdf;
	private javax.swing.JButton btnExportOrganisationPersonToCsv;
	private javax.swing.JButton btnExportOrganizationPersonToPdf;
	private javax.swing.JButton btnLogin;
	private javax.swing.JButton btnLogout;
	private javax.swing.JButton btnManageCollection;
	private javax.swing.JButton btnSearchBox;
	private javax.swing.JButton btnSearchCollection;
	private javax.swing.JButton btnSearchContactPerson;
	private javax.swing.JButton btnSearchOrganisationPerson;
	private javax.swing.JButton btnShowAllBoxes;
	private javax.swing.JButton btnShowAllCollections;
	private javax.swing.JButton btnShowAllContactPersons;
	private javax.swing.JButton btnShowAllDiagram;
	private javax.swing.JButton btnShowAllOrganisationPersons;
	private javax.swing.JButton btnShowBoxDetails;
	private javax.swing.JButton btnShowCollectionsOfContactPerson;
	private javax.swing.JButton btnShowCollectionsOfOrganisationtPerson;
	private javax.swing.JButton btnShowCompareDiagram;
	private javax.swing.JButton btnShowConPersDetails;
	private javax.swing.JButton btnShowOrgPersDetails;
	private javax.swing.JTable collectionTable;
	private javax.swing.JTable conPersTable;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JPanel jPanel3;
	private javax.swing.JPanel jPanel4;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JScrollPane jScrollPane3;
	private javax.swing.JScrollPane jScrollPane4;
	private javax.swing.JScrollPane jScrollPane5;
	private javax.swing.JLabel lblLogin;
	private javax.swing.JTable orgPersTable;
	private javax.swing.JTabbedPane paneMain;
	private javax.swing.JTable userTable;
	// End of variables declaration//GEN-END:variables
}
