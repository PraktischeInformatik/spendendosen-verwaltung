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
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import applicationLogic.ContactPerson;
import applicationLogic.DonationBox;
import applicationLogic.OrganisationPerson;
import applicationLogicAccess.Access;
import applicationLogicAccess.CollectionAccess;

/**
 * 
 * @author Timo
 */
public class SearchCollection extends javax.swing.JDialog {
	ArrayList<OrganisationPerson> orgPersList;
	ArrayList<ContactPerson> conPersList;
	ArrayList<DonationBox> boxList;
	String[] orgPersArray;
	String[] conPersArray;
	String[] boxArray;

	/**
	 * Initializes the Window
	 * 
	 * @param parent
	 *            the parent
	 * @param modal
	 *            determines if the window should be modal or not
	 */
	public SearchCollection(java.awt.Frame parent, boolean modal) {
		super(parent, modal);

		orgPersList = Access.getAllOrganisationPersons();
		int sizePersList = orgPersList.size();
		orgPersArray = new String[sizePersList];
		int i = 0;
		for (OrganisationPerson currPers : orgPersList) {
			String currString = "" + currPers.getPersonId() + " "
					+ currPers.getLastName() + ", " + currPers.getForename();
			orgPersArray[i] = currString;
			i++;
		}

		conPersList = Access.getAllContactPersons();
		int sizeConList = conPersList.size();
		conPersArray = new String[sizeConList];
		i = 0;
		for (ContactPerson currPers : conPersList) {
			String currString = "" + currPers.getPersonId() + " "
					+ currPers.getLastName() + ", " + currPers.getForename();
			conPersArray[i] = currString;
			i++;
		}

		boxList = Access.getAllBoxes();
		int sizeBoxList = boxList.size();
		boxArray = new String[sizeBoxList];
		i = 0;
		for (DonationBox currBox : boxList) {
			int currId = currBox.getBoxId();
			boxArray[i] = "" + currId;
			i++;
		}

		initComponents();

		ImageIcon imgLupe = new ImageIcon("images/imgLupe.png");
		ImageIcon imgAuge = new ImageIcon("images/imgAuge.png");
		ImageIcon imgPerson = new ImageIcon("images/imgPerson.png");
		ImageIcon imgPlus = new ImageIcon("images/imgPlus.png");
		ImageIcon imgBearbeiten = new ImageIcon("images/imgBearbeiten.png");
		ImageIcon imgPfeil = new ImageIcon("images/imgPfeil.png");
		ImageIcon imgKreuz = new ImageIcon("images/imgKreuz.png");
		ImageIcon imgDocument = new ImageIcon("images/imgDocument.png");
		ImageIcon imgGeld = new ImageIcon("images/imgGeld.png");
		//added by sz
		Image imgIcon = new ImageIcon("images/icon.png").getImage();
		setIconImage(imgIcon);
		//
		btnCancel.setIcon(imgKreuz);
		btnSearch.setIcon(imgLupe);
		this.setLocationRelativeTo(null);
		cbOrgPers.setModel(new javax.swing.DefaultComboBoxModel(orgPersArray));
		cbConPers.setModel(new javax.swing.DefaultComboBoxModel(conPersArray));
		cbBox.setModel(new javax.swing.DefaultComboBoxModel(boxArray));
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated Code"> desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		jLabel10 = new javax.swing.JLabel();
		jLabel8 = new javax.swing.JLabel();
		txtId = new javax.swing.JTextField();
		txtComment = new javax.swing.JTextField();
		txtCity = new javax.swing.JTextField();
		dtBeginStart = new org.jdesktop.swingx.JXDatePicker();
		dtEndStart = new org.jdesktop.swingx.JXDatePicker();
		dtBeginEnd = new org.jdesktop.swingx.JXDatePicker();
		jLabel9 = new javax.swing.JLabel();
		jLabel11 = new javax.swing.JLabel();
		dtEndEnd = new org.jdesktop.swingx.JXDatePicker();
		btnSearch = new javax.swing.JButton();
		btnCancel = new javax.swing.JButton();
		showStreetCollectionsCheckBox = new javax.swing.JCheckBox();
		showFixedCollectionsCheckBox = new javax.swing.JCheckBox();
		chkBoxId = new javax.swing.JCheckBox();
		chkBoxOrganisation = new javax.swing.JCheckBox();
		chkBoxComment = new javax.swing.JCheckBox();
		chkBoxBox = new javax.swing.JCheckBox();
		chkBoxCity = new javax.swing.JCheckBox();
		chkBoxBegin = new javax.swing.JCheckBox();
		chkBoxEnd = new javax.swing.JCheckBox();
		txtZip = new javax.swing.JTextField();
		chkBoxZip = new javax.swing.JCheckBox();
		chkBoxContact = new javax.swing.JCheckBox();
		cbOrgPers = new javax.swing.JComboBox();
		cbConPers = new javax.swing.JComboBox();
		cbBox = new javax.swing.JComboBox();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Suchen");
		setResizable(false);

		jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		jLabel10.setText("Sammlung(en) suchen");

		jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
		jLabel8.setText("Suche nach:");

		jLabel9.setText("und");

		jLabel11.setText("und");

		btnSearch.setText("Suchen");
		btnSearch.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnSearchActionPerformed(evt);
			}
		});

		btnCancel.setText("Abbrechen");
		btnCancel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnCancelActionPerformed(evt);
			}
		});

		showStreetCollectionsCheckBox.setSelected(true);
		showStreetCollectionsCheckBox.setText("Zeige Strassensammlungen");

		showFixedCollectionsCheckBox.setSelected(true);
		showFixedCollectionsCheckBox.setText("Zeige Ortsgebundene Sammlungen");

		chkBoxId.setText("ID");

		chkBoxOrganisation.setText("Verantwortliche Person");

		chkBoxComment.setText("Kommentar");

		chkBoxBox.setText("Zugeordnete Dose");

		chkBoxCity.setText("Ort");

		chkBoxBegin.setText("Beginn der Sammlung zwischen");

		chkBoxEnd.setText("Ende der Sammlung zwischen");

		chkBoxZip.setText("Postleitzahl");

		chkBoxContact.setText("Ansprechpartner");

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGap(42, 42, 42)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		jLabel8)
																.addContainerGap())
												.addGroup(
														layout.createSequentialGroup()
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING)
																				.addGroup(
																						layout.createSequentialGroup()
																								.addGap(9,
																										9,
																										9)
																								.addComponent(
																										showStreetCollectionsCheckBox))
																				.addComponent(
																						chkBoxId)
																				.addComponent(
																						chkBoxOrganisation)
																				.addComponent(
																						chkBoxContact)
																				.addComponent(
																						chkBoxComment)
																				.addComponent(
																						chkBoxBox)
																				.addComponent(
																						chkBoxZip)
																				.addComponent(
																						chkBoxCity)
																				.addComponent(
																						chkBoxBegin)
																				.addComponent(
																						chkBoxEnd)
																				.addGroup(
																						layout.createSequentialGroup()
																								.addGap(73,
																										73,
																										73)
																								.addComponent(
																										btnSearch,
																										javax.swing.GroupLayout.PREFERRED_SIZE,
																										110,
																										javax.swing.GroupLayout.PREFERRED_SIZE)))
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING)
																				.addGroup(
																						layout.createSequentialGroup()
																								.addGap(44,
																										44,
																										44)
																								.addComponent(
																										showFixedCollectionsCheckBox)
																								.addContainerGap(
																										javax.swing.GroupLayout.DEFAULT_SIZE,
																										Short.MAX_VALUE))
																				.addGroup(
																						javax.swing.GroupLayout.Alignment.TRAILING,
																						layout.createSequentialGroup()
																								.addPreferredGap(
																										javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																										javax.swing.GroupLayout.DEFAULT_SIZE,
																										Short.MAX_VALUE)
																								.addGroup(
																										layout.createParallelGroup(
																												javax.swing.GroupLayout.Alignment.LEADING)
																												.addComponent(
																														txtComment,
																														javax.swing.GroupLayout.PREFERRED_SIZE,
																														285,
																														javax.swing.GroupLayout.PREFERRED_SIZE)
																												.addComponent(
																														txtCity,
																														javax.swing.GroupLayout.PREFERRED_SIZE,
																														285,
																														javax.swing.GroupLayout.PREFERRED_SIZE)
																												.addGroup(
																														layout.createSequentialGroup()
																																.addGroup(
																																		layout.createParallelGroup(
																																				javax.swing.GroupLayout.Alignment.TRAILING,
																																				false)
																																				.addComponent(
																																						dtEndStart,
																																						javax.swing.GroupLayout.Alignment.LEADING,
																																						javax.swing.GroupLayout.DEFAULT_SIZE,
																																						javax.swing.GroupLayout.DEFAULT_SIZE,
																																						Short.MAX_VALUE)
																																				.addComponent(
																																						dtBeginStart,
																																						javax.swing.GroupLayout.Alignment.LEADING,
																																						javax.swing.GroupLayout.PREFERRED_SIZE,
																																						javax.swing.GroupLayout.DEFAULT_SIZE,
																																						javax.swing.GroupLayout.PREFERRED_SIZE))
																																.addGroup(
																																		layout.createParallelGroup(
																																				javax.swing.GroupLayout.Alignment.LEADING)
																																				.addGroup(
																																						layout.createSequentialGroup()
																																								.addPreferredGap(
																																										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																																								.addComponent(
																																										jLabel11)
																																								.addGap(5,
																																										5,
																																										5)
																																								.addComponent(
																																										dtEndEnd,
																																										javax.swing.GroupLayout.PREFERRED_SIZE,
																																										javax.swing.GroupLayout.DEFAULT_SIZE,
																																										javax.swing.GroupLayout.PREFERRED_SIZE))
																																				.addGroup(
																																						layout.createSequentialGroup()
																																								.addGap(5,
																																										5,
																																										5)
																																								.addComponent(
																																										jLabel9)
																																								.addGap(5,
																																										5,
																																										5)
																																								.addComponent(
																																										dtBeginEnd,
																																										javax.swing.GroupLayout.PREFERRED_SIZE,
																																										javax.swing.GroupLayout.DEFAULT_SIZE,
																																										javax.swing.GroupLayout.PREFERRED_SIZE))))
																												.addComponent(
																														txtZip,
																														javax.swing.GroupLayout.PREFERRED_SIZE,
																														285,
																														javax.swing.GroupLayout.PREFERRED_SIZE)
																												.addComponent(
																														txtId,
																														javax.swing.GroupLayout.PREFERRED_SIZE,
																														285,
																														javax.swing.GroupLayout.PREFERRED_SIZE)
																												.addComponent(
																														cbBox,
																														javax.swing.GroupLayout.PREFERRED_SIZE,
																														130,
																														javax.swing.GroupLayout.PREFERRED_SIZE)
																												.addGroup(
																														layout.createParallelGroup(
																																javax.swing.GroupLayout.Alignment.TRAILING,
																																false)
																																.addComponent(
																																		cbConPers,
																																		javax.swing.GroupLayout.Alignment.LEADING,
																																		0,
																																		130,
																																		Short.MAX_VALUE)
																																.addComponent(
																																		cbOrgPers,
																																		javax.swing.GroupLayout.Alignment.LEADING,
																																		0,
																																		javax.swing.GroupLayout.DEFAULT_SIZE,
																																		Short.MAX_VALUE))
																												.addGroup(
																														layout.createSequentialGroup()
																																.addGap(64,
																																		64,
																																		64)
																																.addComponent(
																																		btnCancel,
																																		javax.swing.GroupLayout.PREFERRED_SIZE,
																																		110,
																																		javax.swing.GroupLayout.PREFERRED_SIZE)))
																								.addGap(42,
																										42,
																										42))))))
				.addGroup(
						layout.createSequentialGroup()
								.addGap(169, 169, 169)
								.addComponent(jLabel10)
								.addContainerGap(
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGap(9, 9, 9)
								.addComponent(jLabel10)
								.addGap(18, 18, 18)
								.addComponent(jLabel8)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(chkBoxId)
												.addComponent(
														txtId,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(18, 18, 18)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(
														chkBoxOrganisation)
												.addComponent(
														cbOrgPers,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(18, 18, 18)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(chkBoxContact)
												.addComponent(
														cbConPers,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(20, 20, 20)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(chkBoxComment)
												.addComponent(
														txtComment,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(18, 18, 18)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(chkBoxBox)
												.addComponent(
														cbBox,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(18, 18, 18)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(chkBoxZip)
												.addComponent(
														txtZip,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(18, 18, 18)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(chkBoxCity)
												.addComponent(
														txtCity,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(18, 18, 18)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(chkBoxBegin)
												.addComponent(
														dtBeginStart,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(jLabel9)
												.addComponent(
														dtBeginEnd,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(18, 18, 18)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(chkBoxEnd)
												.addComponent(
														dtEndStart,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(
														dtEndEnd,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(jLabel11))
								.addGap(18, 18, 18)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(
														showStreetCollectionsCheckBox)
												.addComponent(
														showFixedCollectionsCheckBox))
								.addGap(19, 19, 19)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(btnCancel)
												.addComponent(btnSearch))
								.addGap(36, 36, 36)));

		pack();
	}//</editor-fold>//GEN-END:initComponents

	private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
		dispose();
	}//GEN-LAST:event_btnCancelActionPerformed

	private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
		try {
			int collId = -1;
			int orgaId = -1;
			int contactId = -1;
			String comment = null;
			int boxId = -1;
			int zip = -1;
			String city = null;
			Date beginStart = null;
			Date beginEnd = null;
			Date endStart = null;
			Date endEnd = null;
			boolean showStreet = showStreetCollectionsCheckBox.isSelected();
			boolean showFixed = showFixedCollectionsCheckBox.isSelected();

			if (chkBoxId.isSelected()) {
				try {
					collId = Integer.parseInt(txtId.getText());
					txtId.setBackground(Color.WHITE);
				} catch (Exception e) {
					JOptionPane
							.showMessageDialog(
									null,
									"Bitte geben sie eine gültige Zahl im Suchfeld für die zu suchende Sammlungs ID ein.",
									"Fehler", JOptionPane.ERROR_MESSAGE);
					txtId.setBackground(Color.red);
				}
			}

			if (chkBoxOrganisation.isSelected() && cbOrgPers.getItemCount() > 0) {
				String currSelectedPerson = (String) cbOrgPers
						.getSelectedItem();
				String[] splitted = currSelectedPerson.split(" ");
				orgaId = Integer.parseInt(splitted[0]);
			}

			if (chkBoxContact.isSelected() && cbConPers.getItemCount() > 0) {
				String currSelectedPerson = (String) cbConPers
						.getSelectedItem();
				String[] splitted = currSelectedPerson.split(" ");
				contactId = Integer.parseInt(splitted[0]);
			}

			if (chkBoxComment.isSelected()) {
				comment = txtComment.getText();
			}

			if (chkBoxBox.isSelected() && cbBox.getItemCount() > 0) {
				boxId = Integer.parseInt((String) cbBox.getSelectedItem());
			}

			if (chkBoxZip.isSelected()) {
				try {
					zip = Integer.parseInt(txtZip.getText());
					txtZip.setBackground(Color.WHITE);
				} catch (Exception e) {
					JOptionPane
							.showMessageDialog(
									null,
									"Bitte geben sie eine gültige Zahl im Suchfeld für die zugehörige Postleitzahl ein",
									"Fehler", JOptionPane.ERROR_MESSAGE);
					txtZip.setBackground(Color.red);
				}
			}

			if (chkBoxCity.isSelected()) {
				city = txtCity.getText();
			}

			if (chkBoxBegin.isSelected()) {
				beginStart = dtBeginStart.getDate();
				beginEnd = dtBeginEnd.getDate();
			}

			if (chkBoxEnd.isSelected()) {
				endStart = dtEndStart.getDate();
				endEnd = dtEndEnd.getDate();
			}
			System.out.println(CollectionAccess.searchDonationCollections(
					collId, orgaId, contactId, comment, boxId, zip, city,
					beginStart, beginEnd, endStart, endEnd, showFixed,
					showStreet));

			CollectionAccess.setTempDonationCollections(CollectionAccess
					.searchDonationCollections(collId, orgaId, contactId,
							comment, boxId, zip, city, beginStart, beginEnd,
							endStart, endEnd, showFixed, showStreet));
			dispose();
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Datenbankfehler!", "Fehler!",
					JOptionPane.ERROR_MESSAGE);
		} catch (ParseException ex) {
			JOptionPane.showMessageDialog(null, "Datenbankfehler!", "Fehler!",
					JOptionPane.ERROR_MESSAGE);
		}
	}//GEN-LAST:event_btnSearchActionPerformed

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		/* Set the Nimbus look and feel */
		//<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
		/*
		 * If Nimbus (introduced in Java SE 6) is not available, stay with the
		 * default look and feel. For details see
		 * http://download.oracle.com/javase
		 * /tutorial/uiswing/lookandfeel/plaf.html
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
					.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger
					.getLogger(SearchCollection.class.getName()).log(
							java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger
					.getLogger(SearchCollection.class.getName()).log(
							java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger
					.getLogger(SearchCollection.class.getName()).log(
							java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger
					.getLogger(SearchCollection.class.getName()).log(
							java.util.logging.Level.SEVERE, null, ex);
		}
		//</editor-fold>

		/* Create and display the dialog */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				SearchCollection dialog = new SearchCollection(
						new javax.swing.JFrame(), true);
				dialog.addWindowListener(new java.awt.event.WindowAdapter() {
					@Override
					public void windowClosing(java.awt.event.WindowEvent e) {
						System.exit(0);
					}
				});
				dialog.setVisible(true);
			}
		});
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton btnCancel;
	private javax.swing.JButton btnSearch;
	private javax.swing.JComboBox cbBox;
	private javax.swing.JComboBox cbConPers;
	private javax.swing.JComboBox cbOrgPers;
	private javax.swing.JCheckBox chkBoxBegin;
	private javax.swing.JCheckBox chkBoxBox;
	private javax.swing.JCheckBox chkBoxCity;
	private javax.swing.JCheckBox chkBoxComment;
	private javax.swing.JCheckBox chkBoxContact;
	private javax.swing.JCheckBox chkBoxEnd;
	private javax.swing.JCheckBox chkBoxId;
	private javax.swing.JCheckBox chkBoxOrganisation;
	private javax.swing.JCheckBox chkBoxZip;
	private org.jdesktop.swingx.JXDatePicker dtBeginEnd;
	private org.jdesktop.swingx.JXDatePicker dtBeginStart;
	private org.jdesktop.swingx.JXDatePicker dtEndEnd;
	private org.jdesktop.swingx.JXDatePicker dtEndStart;
	private javax.swing.JLabel jLabel10;
	private javax.swing.JLabel jLabel11;
	private javax.swing.JLabel jLabel8;
	private javax.swing.JLabel jLabel9;
	private javax.swing.JCheckBox showFixedCollectionsCheckBox;
	private javax.swing.JCheckBox showStreetCollectionsCheckBox;
	private javax.swing.JTextField txtCity;
	private javax.swing.JTextField txtComment;
	private javax.swing.JTextField txtId;
	private javax.swing.JTextField txtZip;
	// End of variables declaration//GEN-END:variables
}
