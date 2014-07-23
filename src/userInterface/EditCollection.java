/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package userInterface;

import java.awt.Color;
import java.awt.Image;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import applicationLogic.Address;
import applicationLogic.ContactPerson;
import applicationLogic.ContactPersonHistory;
import applicationLogic.DonationBox;
import applicationLogic.DonationCollection;
import applicationLogic.FixedDonationCollection;
import applicationLogic.OrgaCollectionHistory;
import applicationLogic.OrganisationPerson;
import applicationLogic.PersonHistory;
import applicationLogic.StreetDonationCollection;
import applicationLogicAccess.Access;
import applicationLogicAccess.CollectionAccess;
import applicationLogicAccess.InvalidArgumentsException;
import applicationLogicAccess.PersonHistoryAccess;

/**
 * 
 * @author Marcel
 */
public class EditCollection extends javax.swing.JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	StringBuilder errorMessage = new StringBuilder();
	ArrayList<DonationBox> selectBoxes = new ArrayList();
	DefaultTableModel boxModel;
        int collId;

        /**
         * Initializes the Window
         *
         * @param parent the parent
         * @param modal determines if the window should be modal or not
         * @param collId the ID of the collection we want to edit
         */
	public EditCollection(java.awt.Frame parent, boolean modal, int collId) {
            super(parent, modal);
            this.collId = collId;
            initComponents();
            jTable1.getTableHeader().setReorderingAllowed(false);
		
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
        ImageIcon imgMinus = new ImageIcon("images/imgMinus.png");
        //added by sz
      	Image imgIcon = new ImageIcon("images/icon.png").getImage();
      	setIconImage(imgIcon);
      	//
		btnSammlungErstellen.setIcon(imgPlus);
		btnAddBoxToList.setIcon(imgPlus);
		jButton5.setIcon(imgKreuz);
		jButton1.setIcon(imgPerson);
		jButton6.setIcon(imgPerson);
		jButton3.setIcon(imgMinus);
		
		
		this.setLocationRelativeTo(null);
		rdbStrassensammlung.setEnabled(false);
		rdbFesteSammlung.setEnabled(false);

		jTable1.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] {}, new String[] { "ID", "Kommentar" }));

		DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
		ArrayList<DonationBox> dbx = CollectionAccess
				.searchDonationCollectionById(collId)
				.getDonationboxes();
		ArrayList<DonationBox> currentBoxesArray = new ArrayList<DonationBox>();

		for (int i = 0; dbx.size() > i; i++) {
			int idtoAdd = (dbx.get(i).getBoxId());
			model.addRow(new Object[] { idtoAdd, dbx.get(i).getComment() });

			currentBoxesArray.add(dbx.get(i));
		}

		jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(
				new String[] { "0 Bitte auswählen" }));

		DonationCollection myCol = CollectionAccess
				.searchDonationCollectionById(collId);
		
		
			// Set input forms enabled/disabled for each instance of 
		if (myCol instanceof StreetDonationCollection) {
			boolean state = false;
			jTextField5.setEnabled(state);
			jTextField4.setEnabled(state);
			jTextField3.setEnabled(state);
			jTextField2.setEnabled(state);
			jLabel11.setEnabled(state);
			jLabel12.setEnabled(state);
			jLabel13.setEnabled(state);
			jLabel14.setEnabled(state);
			jLabel9.setEnabled(state);
			jComboBox1.setEnabled(state);
			jButton1.setEnabled(state);
			jLabel1.setEnabled(state);
			jLabel6.setEnabled(state);
			jComboBox1.setEnabled(state);
			jButton1.setEnabled(state);
			jLabel1.setEnabled(state);
		}

		if (myCol instanceof FixedDonationCollection) {
			rdbFesteSammlung.setSelected(true);
			jTextField1.setText(((FixedDonationCollection) myCol).getComment());
			jTextField2.setText(((FixedDonationCollection) myCol)
					.getFixedAddress().getStreetName());
			jTextField3.setText(String
					.valueOf(((FixedDonationCollection) myCol)
							.getFixedAddress().getHauseNumber()));

			jTextField4.setText(String
					.valueOf(((FixedDonationCollection) myCol)
							.getFixedAddress().getZip()));
			jTextField5.setText(((FixedDonationCollection) myCol)
					.getFixedAddress().getLocationName());

		}
		if (myCol instanceof StreetDonationCollection) {
			rdbStrassensammlung.setSelected(true);
			jTextField1.setText(((StreetDonationCollection) myCol).getComment());
		}

		jXDatePicker1.setDate(myCol.getBeginPeriode().getTime());

		try {
			jXDatePicker2.setDate(myCol.getEndPeriode().getTime());
		} catch (NullPointerException e) {
			 
		}
		ArrayList<DonationBox> donbox = Access.getAvailableDonationBoxes();
		// Filter donbox deleted, inbox

		for (int i = 0; i < donbox.size(); i++) {

			cbSelectBoxes.addItem(donbox.get(i).getBoxId() + " "
					+ donbox.get(i).getComment().toString());

		}


		String[] conPersArray = new String[Access.getAllOrganisationPersons()
				.size()];
		int s = 0;

		for (OrganisationPerson currPers : Access.getAllOrganisationPersons()) {
			if(Access.getPersonById(currPers.getPersonId()).getIsActiveMember()){
			String currString2 = "" + currPers.getPersonId() + " "
					+ currPers.getLastName() + ", " + currPers.getForename();
			jComboBox2.addItem(currString2);

			conPersArray[s] = currString2;
			s++;}
		}

		String[] orgPersArray = new String[Access.getAllContactPersons().size()];
		int i = 0;
		for (ContactPerson currPers : Access.getAllContactPersons()) {
			String currString = "" + currPers.getPersonId() + " "
					+ currPers.getLastName() + ", " + currPers.getForename();
			jComboBox1.addItem(currString);

			i++;
		}

		int persId = 0;
		int persIdb = 0;

		// TODO: check if fixed ans dets rdb
		if (myCol instanceof FixedDonationCollection) {
			persId = (((FixedDonationCollection) myCol).getOrganizationPerson()
					.getPersonId()); // get pers ID to compare

			persIdb = (((FixedDonationCollection) myCol).getContactPerson()
					.getPersonId()); // get pers ID to compare
		} else {

			persId = (((StreetDonationCollection) myCol)
					.getOrganizationPerson().getPersonId()); // get pers ID to
																// compare

			
			persIdb = 0;
		}

		String[] persId1 = new String[jComboBox1.getItemCount()]; // create
																	// array
		int thepos = 0;
		for (int ii = 0; ii < jComboBox1.getItemCount(); ii++) {
			String[] split = jComboBox1.getItemAt(ii).toString().split(" "); // get
																				// ID
																				// from
																				// selected
																				// each

			persId1[ii] = split[0]; // insert id from selected

			if (persIdb == Integer.parseInt(persId1[ii])) {

				thepos = ii;
			}
		}
		jComboBox1.setSelectedIndex(thepos);

		String[] persId2 = new String[jComboBox2.getItemCount()]; // create
																	// array
		int thepos1 = 0;
		for (int ii = 0; ii < jComboBox2.getItemCount(); ii++) {
			String[] split = jComboBox2.getItemAt(ii).toString().split(" "); // get
																				// ID
																				// from
																				// selected
																				// each

			persId2[ii] = split[0]; // insert id from selected

			if (persId == Integer.parseInt(persId2[ii])) {

				thepos1 = ii;
			}
		}
		jComboBox2.setSelectedIndex(thepos1);

	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings({ "unchecked", "static-access", "null" })
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {
		btnAddBoxToList = new javax.swing.JButton();
		CollectionTypeRadioGrp = new javax.swing.ButtonGroup();
		jLabel10 = new javax.swing.JLabel();
		jLabel1 = new javax.swing.JLabel();
		jComboBox1 = new javax.swing.JComboBox();
		jButton1 = new javax.swing.JButton();
		jXDatePicker1 = new org.jdesktop.swingx.JXDatePicker();
		jXDatePicker2 = new org.jdesktop.swingx.JXDatePicker();
		jLabel2 = new javax.swing.JLabel();
		jLabel3 = new javax.swing.JLabel();
		jLabel4 = new javax.swing.JLabel();
		jTextField1 = new javax.swing.JTextField();
		jLabel5 = new javax.swing.JLabel();
		jLabel6 = new javax.swing.JLabel();
		rdbStrassensammlung = new javax.swing.JRadioButton();
		rdbFesteSammlung = new javax.swing.JRadioButton();
		jScrollPane1 = new javax.swing.JScrollPane();
		jTable1 = new javax.swing.JTable();
		jLabel7 = new javax.swing.JLabel();
		jButton3 = new javax.swing.JButton();
		btnSammlungErstellen = new javax.swing.JButton();
		jButton5 = new javax.swing.JButton();
		jComboBox2 = new javax.swing.JComboBox();
		jLabel8 = new javax.swing.JLabel();
		jButton6 = new javax.swing.JButton();
		jLabel9 = new javax.swing.JLabel();
		jLabel11 = new javax.swing.JLabel();
		jTextField2 = new javax.swing.JTextField();
		jLabel12 = new javax.swing.JLabel();
		jTextField3 = new javax.swing.JTextField();
		jLabel13 = new javax.swing.JLabel();
		jTextField4 = new javax.swing.JTextField();
		jLabel14 = new javax.swing.JLabel();
		jTextField5 = new javax.swing.JTextField();
		cbSelectBoxes = new javax.swing.JComboBox();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Sammlung ändern");
		setResizable(false);

		jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		jLabel10.setText("Sammlung ändern");

		jLabel1.setText("Kontaktperson auswählen");

		jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(
				new String[] { "0 Bitte auswählen" }));

		jButton1.setText("Neue Person hinzufügen");
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton1ActionPerformed(evt);
			}
		});

		jXDatePicker1.setName(""); // NOI18N

		jLabel2.setText("Beginn der Sammlung *");

		jLabel3.setText("Ende der Sammlung (optional)");

		jLabel4.setText("Kommentar (optional)");

		jLabel5.setText("Zugehörige Dosen auswählen");

		jLabel6.setText("Art der Sammlung auswählen *");

		CollectionTypeRadioGrp.add(rdbStrassensammlung);
		rdbStrassensammlung.setText("Straßensammlung");
		rdbStrassensammlung
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						rdbStrassensammlungActionPerformed(evt);
					}
				});

		CollectionTypeRadioGrp.add(rdbFesteSammlung);
		rdbFesteSammlung.setText("Festinstallierte Sammlung");
		rdbFesteSammlung.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				rdbFesteSammlungActionPerformed(evt);
			}
		});

		jScrollPane1.setViewportView(jTable1);

		jLabel7.setText("Bisher ausgewählte Dosen");

		jButton3.setText("Markierte Dose aus der Auswahl entfernen");
		jButton3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					jButton3ActionPerformed(evt);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		btnSammlungErstellen.setText("Sammlung ändern");
		btnSammlungErstellen
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						try {
							btnSammlungErstellenActionPerformed(evt);
						} catch (InvalidArgumentsException e) {
							
							JOptionPane.showMessageDialog(null, e.getMessage(), "Fehler!",
									JOptionPane.ERROR_MESSAGE);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});

		jButton5.setText("Abbrechen");
		jButton5.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton5ActionPerformed(evt);
			}
		});

		jLabel8.setText("Verantwortliche Person auswählen *");

		jButton6.setText("Neue Person hinzufügen");
		jButton6.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton6ActionPerformed(evt);
			}
		});

		jLabel9.setText("Adresse der Straßensammlung *");

		jLabel11.setText("Straße");

		jTextField2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jTextField2ActionPerformed(evt);
			}
		});

		jLabel12.setText("Nr");

		jLabel13.setText("Plz");

		jLabel14.setText("Ort");

		cbSelectBoxes.setModel(new javax.swing.DefaultComboBoxModel(
				new String[] { "---" }));

		btnAddBoxToList.setText("Hinzufügen");
		btnAddBoxToList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnAddBoxToListActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGap(58, 58, 58)
								.addComponent(btnSammlungErstellen)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addComponent(jButton5,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										123,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(63, 63, 63))
				.addGroup(
						layout.createSequentialGroup()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(
														layout.createSequentialGroup()
																.addGap(212,
																		212,
																		212)
																.addComponent(
																		jLabel10,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		144,
																		javax.swing.GroupLayout.PREFERRED_SIZE))
												.addGroup(
														layout.createSequentialGroup()
																.addGap(39, 39,
																		39)
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING)
																				.addGroup(
																						layout.createSequentialGroup()
																								.addComponent(
																										jLabel6)
																								.addGap(60,
																										60,
																										60)
																								.addComponent(
																										rdbStrassensammlung)
																								.addGap(18,
																										18,
																										18)
																								.addComponent(
																										rdbFesteSammlung))
																				.addGroup(
																						layout.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING,
																								false)
																								.addGroup(
																										javax.swing.GroupLayout.Alignment.TRAILING,
																										layout.createSequentialGroup()
																												.addComponent(
																														jLabel1)
																												.addPreferredGap(
																														javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																														javax.swing.GroupLayout.DEFAULT_SIZE,
																														Short.MAX_VALUE)
																												.addComponent(
																														jComboBox1,
																														javax.swing.GroupLayout.PREFERRED_SIZE,
																														132,
																														javax.swing.GroupLayout.PREFERRED_SIZE)
																												.addGap(18,
																														18,
																														18)
																												.addComponent(
																														jButton1))
																								.addGroup(
																										layout.createSequentialGroup()
																												.addComponent(
																														jLabel8)
																												.addGap(31,
																														31,
																														31)
																												.addComponent(
																														jComboBox2,
																														javax.swing.GroupLayout.PREFERRED_SIZE,
																														132,
																														javax.swing.GroupLayout.PREFERRED_SIZE)
																												.addGap(18,
																														18,
																														18)
																												.addComponent(
																														jButton6))
																								.addGroup(
																										layout.createSequentialGroup()
																												.addGroup(
																														layout.createParallelGroup(
																																javax.swing.GroupLayout.Alignment.TRAILING)
																																.addComponent(
																																		jLabel11)
																																.addComponent(
																																		jLabel2)
																																.addComponent(
																																		jLabel13))
																												.addGap(28,
																														28,
																														28)
																												.addGroup(
																														layout.createParallelGroup(
																																javax.swing.GroupLayout.Alignment.LEADING)
																																.addGroup(
																																		layout.createSequentialGroup()
																																				.addComponent(
																																						jTextField2,
																																						javax.swing.GroupLayout.PREFERRED_SIZE,
																																						186,
																																						javax.swing.GroupLayout.PREFERRED_SIZE)
																																				.addGap(18,
																																						18,
																																						18)
																																				.addComponent(
																																						jLabel12))
																																.addGroup(
																																		layout.createSequentialGroup()
																																				.addComponent(
																																						jTextField4,
																																						javax.swing.GroupLayout.PREFERRED_SIZE,
																																						109,
																																						javax.swing.GroupLayout.PREFERRED_SIZE)
																																				.addGap(28,
																																						28,
																																						28)
																																				.addComponent(
																																						jLabel14)
																																				.addGap(18,
																																						18,
																																						18)
																																				.addComponent(
																																						jTextField5,
																																						javax.swing.GroupLayout.PREFERRED_SIZE,
																																						190,
																																						javax.swing.GroupLayout.PREFERRED_SIZE))))
																								.addGroup(
																										layout.createSequentialGroup()
																												.addGroup(
																														layout.createParallelGroup(
																																javax.swing.GroupLayout.Alignment.LEADING)
																																.addComponent(
																																		jLabel3)
																																.addComponent(
																																		jLabel4)
																																.addComponent(
																																		jLabel5)
																																.addComponent(
																																		jLabel7))
																												.addGroup(
																														layout.createParallelGroup(
																																javax.swing.GroupLayout.Alignment.TRAILING)
																																.addGroup(
																																		layout.createSequentialGroup()
																																				.addPreferredGap(
																																						javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																																				.addGroup(
																																						layout.createParallelGroup(
																																								javax.swing.GroupLayout.Alignment.LEADING)
																																								.addGroup(
																																										layout.createSequentialGroup()
																																												.addGroup(
																																														layout.createParallelGroup(
																																																javax.swing.GroupLayout.Alignment.TRAILING,
																																																false)
																																																.addComponent(
																																																		jXDatePicker1,
																																																		javax.swing.GroupLayout.Alignment.LEADING,
																																																		javax.swing.GroupLayout.DEFAULT_SIZE,
																																																		javax.swing.GroupLayout.DEFAULT_SIZE,
																																																		Short.MAX_VALUE)
																																																.addComponent(
																																																		jXDatePicker2,
																																																		javax.swing.GroupLayout.PREFERRED_SIZE,
																																																		132,
																																																		javax.swing.GroupLayout.PREFERRED_SIZE))
																																												.addPreferredGap(
																																														javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																																														javax.swing.GroupLayout.DEFAULT_SIZE,
																																														Short.MAX_VALUE)
																																												.addComponent(
																																														jTextField3,
																																														javax.swing.GroupLayout.PREFERRED_SIZE,
																																														140,
																																														javax.swing.GroupLayout.PREFERRED_SIZE))
																																								.addGroup(
																																										javax.swing.GroupLayout.Alignment.TRAILING,
																																										layout.createSequentialGroup()
																																												.addGap(0,
																																														0,
																																														Short.MAX_VALUE)
																																												.addComponent(
																																														jTextField1,
																																														javax.swing.GroupLayout.PREFERRED_SIZE,
																																														299,
																																														javax.swing.GroupLayout.PREFERRED_SIZE))))
																																.addGroup(
																																		javax.swing.GroupLayout.Alignment.LEADING,
																																		layout.createSequentialGroup()
																																				.addGap(36,
																																						36,
																																						36)
																																				.addComponent(
																																						cbSelectBoxes,
																																						javax.swing.GroupLayout.PREFERRED_SIZE,
																																						225,
																																						javax.swing.GroupLayout.PREFERRED_SIZE)
																																				.addPreferredGap(
																																						javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																																				.addComponent(
																																						btnAddBoxToList)
																																				.addGap(0,
																																						0,
																																						Short.MAX_VALUE)))))
																				.addComponent(
																						jLabel9)))
												.addGroup(
														layout.createSequentialGroup()
																.addGap(177,
																		177,
																		177)
																.addComponent(
																		jButton3))
												.addGroup(
														layout.createSequentialGroup()
																.addGap(49, 49,
																		49)
																.addComponent(
																		jScrollPane1,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		501,
																		javax.swing.GroupLayout.PREFERRED_SIZE)))
								.addContainerGap(54, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGap(25, 25, 25)
								.addComponent(jLabel10)
								.addGap(18, 18, 18)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(jLabel6)
												.addComponent(
														rdbStrassensammlung)
												.addComponent(rdbFesteSammlung))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(jLabel8)
												.addComponent(
														jComboBox2,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(jButton6))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(jLabel1)
												.addComponent(
														jComboBox1,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(jButton1))
								.addGap(18, 18, 18)
								.addComponent(jLabel9)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(jLabel11)
												.addComponent(
														jTextField2,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(jLabel12)
												.addComponent(
														jTextField3,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(jLabel13)
												.addGroup(
														layout.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
																.addComponent(
																		jTextField4,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.PREFERRED_SIZE)
																.addComponent(
																		jLabel14)
																.addComponent(
																		jTextField5,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.PREFERRED_SIZE)))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED,
										23, Short.MAX_VALUE)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(jLabel2)
												.addComponent(
														jXDatePicker1,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(jLabel3)
												.addComponent(
														jXDatePicker2,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(jLabel4)
												.addComponent(
														jTextField1,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(jLabel5)
												.addComponent(
														cbSelectBoxes,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(btnAddBoxToList))
								.addGap(18, 18, 18)
								.addComponent(jLabel7)
								.addGap(18, 18, 18)
								.addComponent(jScrollPane1,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										127,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(18, 18, 18)
								.addComponent(jButton3)
								.addGap(31, 31, 31)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(
														btnSammlungErstellen)
												.addComponent(jButton5))
								.addGap(25, 25, 25)));

		pack();
	}//</editor-fold>//GEN-END:initComponents

	/**
	 * 
	 * @author Marcel
	 */

	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
		AddContactPerson MainFrame = new AddContactPerson(null,
				true);
		MainFrame.setVisible(true);
		
		 
		for (ContactPerson currPers : Access.getAllContactPersons()) {
			String currString = "" + currPers.getPersonId() + " "
					+ currPers.getLastName() + ", " + currPers.getForename();
			jComboBox1.addItem(currString);

			 
		}
	}

	@SuppressWarnings("unchecked")
	private void jButton3ActionPerformed(java.awt.event.ActionEvent evt)
			throws SQLException, ParseException {//GEN-FIRST:event_jButton1ActionPerformed
		// TODO add your handling code here:
		int selectedRowIndex = jTable1.getSelectedRow();

		
		int idToDel = Integer.parseInt(jTable1.getValueAt(selectedRowIndex, 0)
				.toString());
		CollectionAccess.deleteBoxFromDonationCollection(idToDel);

		DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
		model.removeRow(selectedRowIndex);
		ArrayList<DonationBox> donbox = Access.getAvailableDonationBoxes();
		// Filter donbox deleted, inbox
		cbSelectBoxes.removeAllItems();

		for (int i = 0; i < donbox.size(); i++) {

			cbSelectBoxes.addItem(donbox.get(i).getBoxId() + " "
					+ donbox.get(i).getComment().toString());
			selectBoxes
					.add(Access.getDonationBoxById(donbox.get(i).getBoxId()));
		}

	}//GEN-LAST:event_jButton1ActionPerformed

	private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
		// TODO add your handling code here:
		dispose();
	}//GEN-LAST:event_jButton1ActionPerformed

	private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
		// TODO add your handling code here:
		AddOrganisationPerson MainFrame = new AddOrganisationPerson(null,
				true);
		MainFrame.setVisible(true);
		for (OrganisationPerson currPers : Access.getAllOrganisationPersons()) {
			String currString2 = "" + currPers.getPersonId() + " "
					+ currPers.getLastName() + ", " + currPers.getForename();
			jComboBox2.addItem(currString2);


			 
			 
		}
	}//GEN-LAST:event_jButton6ActionPerformed

	private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
		// TODO add your handling code here:
	}//GEN-LAST:event_jTextField2ActionPerformed

	private void rdbFesteSammlungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbFesteSammlungActionPerformed
		// TODO add your handling code here:
		boolean state = true;
		jTextField5.setEnabled(state);
		jTextField4.setEnabled(state);
		jTextField3.setEnabled(state);
		jTextField2.setEnabled(state);
		jLabel11.setEnabled(state);
		jLabel12.setEnabled(state);
		jLabel13.setEnabled(state);
		jLabel14.setEnabled(state);
		jLabel9.setEnabled(state);
		jComboBox1.setEnabled(state);
		jButton1.setEnabled(state);
		jLabel1.setEnabled(state);
	}//GEN-LAST:event_rdbFesteSammlungActionPerformed

	private void rdbStrassensammlungActionPerformed(
			java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbStrassensammlungActionPerformed
		// TODO add your handling code here:
		boolean state = false;
		jTextField5.setEnabled(state);
		jTextField4.setEnabled(state);
		jTextField3.setEnabled(state);
		jTextField2.setEnabled(state);
		jLabel11.setEnabled(state);
		jLabel12.setEnabled(state);
		jLabel13.setEnabled(state);
		jLabel14.setEnabled(state);
		jLabel9.setEnabled(state);
		jComboBox1.setEnabled(state);
		jButton1.setEnabled(state);
		jLabel1.setEnabled(state);

	}//GEN-LAST:event_rdbStrassensammlungActionPerformed

	private void btnAddBoxToListActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
		
		DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
		ArrayList<DonationBox> donbox = Access.getAvailableDonationBoxes();
		String[] idOfBox = cbSelectBoxes.getSelectedItem().toString()
				.split(" ");
		String[] split1 = cbSelectBoxes.getSelectedItem().toString().split(" "); // liste
		boolean duplicate = false;
		if(split1[0].equals("---")) {
			
			JOptionPane.showMessageDialog(null,
					"Bitte treffen Sie eine Auswahl");
			duplicate = true;
		}
		else {
		for (int i = 0; model.getRowCount() >= i + 1; i++) {

			if (Integer.parseInt(model.getValueAt(i, 0).toString()) == Integer
					.parseInt(split1[0])) {
				JOptionPane.showMessageDialog(null,
						"Die Box ist bereits in der Liste");
				duplicate = true;

			}
		}}

		if (duplicate == false) {

			model.addRow(new Object[] { split1[0], split1[1] });

			cbSelectBoxes.removeAllItems();

			for (int n = 0; n < donbox.size(); n++) {
				cbSelectBoxes.addItem(donbox.get(n).getBoxId() + " "
						+ donbox.get(n).getComment().toString());

			}

		}

	}

	private void btnSammlungErstellenActionPerformed(
			java.awt.event.ActionEvent evt) throws InvalidArgumentsException,
			ParseException, SQLException {

		if (jComboBox2.getSelectedItem().toString().equals("0 Bitte auswählen")) {
			errorMessage
					.append("Bitte eine verantwortliche Kontaktperson auswählen.\n");
			jComboBox2.setBackground(Color.red);
		}

		if (jTextField2.getText().matches("([a-zA-Z]*[^a-zA-Z][a-zA-Z]*)+")) {
			errorMessage.append("Fehlerhafte Eingabe der Straße.\n");
			jTextField2.setBackground(Color.red);
		} else {
			jTextField2.setBackground(Color.white);
		}

		if (!jTextField3.getText().matches("([0-9]*)+")
				|| jTextField3.getText().length() > 5) {
			errorMessage
					.append("Bitte geben Sie eine gültige Hausnummer in Form 123 ein.\n");
			jTextField3.setBackground(Color.red);
		} else
			jTextField3.setBackground(Color.white);

		if (!jTextField4.getText().matches("([0-9]*)+")
				|| jTextField4.getText().length() > 5) {
			errorMessage.append("Die PLZ muss aus 5 Zahlen bestehen.\n");
			jTextField4.setBackground(Color.red);
		} else
			jTextField4.setBackground(Color.white);

		if (jTextField5.getText().matches("([a-zA-Z]*[^a-zA-Z][a-zA-Z]*)+")) {
			errorMessage.append("Ein Ort besteht nur aus Buchstaben.\n");
			jTextField5.setBackground(Color.red);
		} else
			jTextField5.setBackground(Color.white);

		if (jTextField1.getText().length() > 100) {
			errorMessage.append("Ein Ort besteht nur aus Buchstaben.\n");
			jTextField1.setBackground(Color.red);
		} else
			jTextField1.setBackground(Color.white);
		
		try {
			if(jXDatePicker1.getDate().getTime()>jXDatePicker2.getDate().getTime()) {
				errorMessage.append("Das Enddatum kann nicht vor Anfang liegen\n");
			}
			
		
		
		} catch (Exception x) {
			
		}
		
		

		String rdbSelected = "none";
		if (rdbFesteSammlung.isSelected()) {
			rdbSelected = "FesteSammlung";
		}
		if (rdbStrassensammlung.isSelected()) {
			rdbSelected = "StrassenSammlung";
		}

		if (errorMessage.length() < 1 && rdbStrassensammlung.isSelected()) {
			// organisationpers combo 2 // contactpers combo 1
			
			

			String[] cbox2sel = jComboBox2.getSelectedItem().toString()
					.split(" ");


			DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

			ArrayList<DonationBox> boxesInList = new ArrayList<>();

			for (int i = 0; model.getRowCount() - 1 >= i; i++) {

				boxesInList.add(Access.getDonationBoxById(Integer
						.parseInt(model.getValueAt(i, 0).toString())));

			}
			


			
			CollectionAccess.editStreetDonationCollection(collId, boxesInList, jTextField1.getText(), (OrganisationPerson) Access.getPersonById(Integer.parseInt(cbox2sel[0])), jXDatePicker1.getDate(), jXDatePicker2.getDate());



			dispose();

		} else if (errorMessage.length() < 1 && rdbFesteSammlung.isSelected()) {
			
			


			DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

			ArrayList<DonationBox> boxesInList = new ArrayList();

			for (int i = 0; model.getRowCount() - 1 >= i; i++) {

				boxesInList.add(Access.getDonationBoxById(Integer
						.parseInt(model.getValueAt(i, 0).toString())));

			}

			String[] cbox2sel = jComboBox2.getSelectedItem().toString()
					.split(" ");
			String[] cbox1sel = jComboBox1.getSelectedItem().toString()
					.split(" ");

			int cboxid1 = Integer.parseInt(cbox1sel[0]);
			int cboxid2 = Integer.parseInt(cbox2sel[0]);

			
			CollectionAccess.editFixedDonationCollection(collId, boxesInList, jTextField1.getText(), (OrganisationPerson) Access.getPersonById(Integer.parseInt(cbox2sel[0])), jXDatePicker1.getDate(), jXDatePicker2.getDate(), new Address(jTextField2.getText(), jTextField3.getText(), Integer.parseInt(jTextField4.getText()), jTextField5.getText()), (ContactPerson) Access.getPersonById(Integer.parseInt(cbox1sel[0])));



			dispose();

			// Access.
		} else {
			JOptionPane.showMessageDialog(null, errorMessage);
			errorMessage.setLength(0);
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
		if(Access.getAccessLevelOfProgramm()==1) {
			
			
			
			
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
			java.util.logging.Logger.getLogger(EditCollection.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(EditCollection.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(EditCollection.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(EditCollection.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		}
		//</editor-fold>
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.ButtonGroup CollectionTypeRadioGrp;
	private javax.swing.JButton btnSammlungErstellen;
	private javax.swing.JComboBox cbSelectBoxes;
	private javax.swing.JButton jButton1;
	private javax.swing.JButton jButton3;
	private javax.swing.JButton jButton5;
	private javax.swing.JButton jButton6;
	private javax.swing.JComboBox jComboBox1;
	private javax.swing.JComboBox jComboBox2;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel10;
	private javax.swing.JLabel jLabel11;
	private javax.swing.JLabel jLabel12;
	private javax.swing.JLabel jLabel13;
	private javax.swing.JLabel jLabel14;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JLabel jLabel6;
	private javax.swing.JLabel jLabel7;
	private javax.swing.JLabel jLabel8;
	private javax.swing.JLabel jLabel9;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JTable jTable1;
	private javax.swing.JTextField jTextField1;
	private javax.swing.JTextField jTextField2;
	private javax.swing.JTextField jTextField3;
	private javax.swing.JTextField jTextField4;
	private javax.swing.JTextField jTextField5;
	private javax.swing.JButton btnAddBoxToList;
	private org.jdesktop.swingx.JXDatePicker jXDatePicker1;
	private org.jdesktop.swingx.JXDatePicker jXDatePicker2;
	private javax.swing.JRadioButton rdbFesteSammlung;
	private javax.swing.JRadioButton rdbStrassensammlung;
	// End of variables declaration//GEN-END:variables
}
