/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package userInterface;

import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import applicationLogic.OrganisationPerson;
import applicationLogicAccess.Access;

/**
 * 
 * @author Timo
 */
public class SearchOrganisationPerson extends javax.swing.JDialog {

	ArrayList<OrganisationPerson> orgPersList;
	String[] orgPersArray;

	/**
	 * Initializes the Window
	 * 
	 * @param parent
	 *            the parent
	 * @param modal
	 *            determines if the window should be modal or not
	 */
	public SearchOrganisationPerson(java.awt.Frame parent, boolean modal) {
		super(parent, modal);

		orgPersList = Access.getAllOrganisationPersons();
		int sizePersList = orgPersList.size();
		orgPersArray = new String[sizePersList];
		int i = 0;
		for (OrganisationPerson currPers : orgPersList) {
			String currString = "" + currPers.getPersonId();
			orgPersArray[i] = currString;
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
		cbOrgPersId
				.setModel(new javax.swing.DefaultComboBoxModel(orgPersArray));
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated Code"> desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		txtLastname = new javax.swing.JTextField();
		btnSearch = new javax.swing.JButton();
		btnCancel = new javax.swing.JButton();
		txtForename = new javax.swing.JTextField();
		jLabel10 = new javax.swing.JLabel();
		chkLastname = new javax.swing.JCheckBox();
		chkForename = new javax.swing.JCheckBox();
		chkOrgPersId = new javax.swing.JCheckBox();
		cbOrgPersId = new javax.swing.JComboBox();
		jLabel9 = new javax.swing.JLabel();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Suchen");
		setResizable(false);

		btnSearch.setText("Person suchen");
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

		jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		jLabel10.setText("Organisationsmitglieder suchen");

		chkLastname.setText("Nachname");

		chkForename.setText("Vorname");

		chkOrgPersId.setText("ID");

		jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
		jLabel9.setText("Suche nach:");

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(
														layout.createSequentialGroup()
																.addGap(50, 50,
																		50)
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING)
																				.addComponent(
																						jLabel9)
																				.addGroup(
																						layout.createSequentialGroup()
																								.addGroup(
																										layout.createParallelGroup(
																												javax.swing.GroupLayout.Alignment.TRAILING)
																												.addComponent(
																														chkLastname)
																												.addComponent(
																														chkOrgPersId,
																														javax.swing.GroupLayout.Alignment.LEADING)
																												.addComponent(
																														chkForename,
																														javax.swing.GroupLayout.Alignment.LEADING))
																								.addGap(46,
																										46,
																										46)
																								.addGroup(
																										layout.createParallelGroup(
																												javax.swing.GroupLayout.Alignment.LEADING,
																												false)
																												.addComponent(
																														cbOrgPersId,
																														javax.swing.GroupLayout.PREFERRED_SIZE,
																														javax.swing.GroupLayout.DEFAULT_SIZE,
																														javax.swing.GroupLayout.PREFERRED_SIZE)
																												.addComponent(
																														txtForename,
																														javax.swing.GroupLayout.DEFAULT_SIZE,
																														191,
																														Short.MAX_VALUE)
																												.addComponent(
																														txtLastname)))))
												.addGroup(
														layout.createSequentialGroup()
																.addGap(72, 72,
																		72)
																.addComponent(
																		btnSearch,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		145,
																		javax.swing.GroupLayout.PREFERRED_SIZE)
																.addGap(30, 30,
																		30)
																.addComponent(
																		btnCancel,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		126,
																		javax.swing.GroupLayout.PREFERRED_SIZE)))
								.addContainerGap(50, Short.MAX_VALUE))
				.addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup()
								.addGap(0, 0, Short.MAX_VALUE)
								.addComponent(jLabel10,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										248,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(64, 64, 64)));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGap(21, 21, 21)
								.addComponent(jLabel10)
								.addGap(18, 18, 18)
								.addComponent(jLabel9)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(
														cbOrgPersId,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(chkOrgPersId))
								.addGap(18, 18, 18)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(chkLastname)
												.addComponent(
														txtLastname,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(18, 18, 18)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(chkForename)
												.addComponent(
														txtForename,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(33, 33, 33)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(btnSearch)
												.addComponent(btnCancel))
								.addContainerGap(46, Short.MAX_VALUE)));

		pack();
	}//</editor-fold>//GEN-END:initComponents

	/**
	 * @param evt
	 *            the event (unused)
	 */
	private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
		dispose();
	}//GEN-LAST:event_btnCancelActionPerformed

	/**
	 * @param evt
	 *            the event (unused)
	 */
	private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
		int orgPersId = -1;
		String lastname = null;
		String forename = null;

		if (chkOrgPersId.isSelected() && cbOrgPersId.getItemCount() > 0) {
			orgPersId = Integer
					.parseInt((String) cbOrgPersId.getSelectedItem());
		}

		if (chkLastname.isSelected()) {
			lastname = txtLastname.getText();
			System.out.println("lastname " + lastname);
		}

		if (chkForename.isSelected()) {
			forename = txtForename.getText();
			System.out.println("forename " + forename);
		}
		Access.setTempOrganisationPersons(Access.searchOrganisationPersons(
				orgPersId, lastname, forename));
		dispose();
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
			java.util.logging.Logger.getLogger(
					SearchOrganisationPerson.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(
					SearchOrganisationPerson.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(
					SearchOrganisationPerson.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(
					SearchOrganisationPerson.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		}
		//</editor-fold>

		/* Create and display the dialog */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				SearchOrganisationPerson dialog = new SearchOrganisationPerson(
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
	private javax.swing.JComboBox cbOrgPersId;
	private javax.swing.JCheckBox chkForename;
	private javax.swing.JCheckBox chkLastname;
	private javax.swing.JCheckBox chkOrgPersId;
	private javax.swing.JLabel jLabel10;
	private javax.swing.JLabel jLabel9;
	private javax.swing.JTextField txtForename;
	private javax.swing.JTextField txtLastname;
	// End of variables declaration//GEN-END:variables
}
