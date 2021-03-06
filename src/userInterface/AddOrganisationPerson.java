/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package userInterface;

import java.awt.Color;
import java.awt.Image;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import applicationLogic.Address;
import applicationLogic.OrganisationPerson;
import applicationLogicAccess.Access;

/**
 * 
 * @author Timo
 */
public class AddOrganisationPerson extends javax.swing.JDialog {
	StringBuilder errorMessage = new StringBuilder();

	/**
	 * Initializes the Window
	 * 
	 * @param parent
	 *            the parent
	 * @param modal
	 *            determines if the window should be modal or not
	 */
	public AddOrganisationPerson(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		initComponents();
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
		
		btnAddPerson.setIcon(imgPlus);
		btnCancel.setIcon(imgKreuz);
		this.setLocationRelativeTo(null);
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	// <editor-fold defaultstate="collapsed" desc="Generated Code"> desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		jLabel5 = new javax.swing.JLabel();
		jLabel11 = new javax.swing.JLabel();
		txtLastname = new javax.swing.JTextField();
		jLabel9 = new javax.swing.JLabel();
		txtStreet = new javax.swing.JTextField();
		txtHousenumber = new javax.swing.JTextField();
		jLabel7 = new javax.swing.JLabel();
		txtCity = new javax.swing.JTextField();
		btnAddPerson = new javax.swing.JButton();
		jLabel6 = new javax.swing.JLabel();
		btnCancel = new javax.swing.JButton();
		txtForename = new javax.swing.JTextField();
		jLabel10 = new javax.swing.JLabel();
		jLabel8 = new javax.swing.JLabel();
		txtEmail = new javax.swing.JTextField();
		jLabel2 = new javax.swing.JLabel();
		txtZip = new javax.swing.JTextField();
		jLabel3 = new javax.swing.JLabel();
		txtMobile = new javax.swing.JTextField();
		txtPhone = new javax.swing.JTextField();
		jLabel4 = new javax.swing.JLabel();
		jLabel1 = new javax.swing.JLabel();
		jLabel12 = new javax.swing.JLabel();
		txtComment = new javax.swing.JTextField();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

		jLabel5.setText("Postleitzahl");

		jLabel11.setText("* Pflichtfelder");

		jLabel9.setText("Mobil");

		jLabel7.setText("Email");

		btnAddPerson.setText("Person hinzufügen");
		btnAddPerson.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnAddPersonActionPerformed(evt);
			}
		});

		jLabel6.setText("Ort");

		btnCancel.setText("Abbrechen");
		btnCancel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnCancelActionPerformed(evt);
			}
		});

		jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		jLabel10.setText("Organisationsmitglied hinzufügen");

		jLabel8.setText("Festnetz*");

		jLabel2.setText("Vorname*");

		jLabel3.setText("Straße");

		jLabel4.setText("Hausnummer");

		jLabel1.setText("Nachname*");

		jLabel12.setText("Kommentar");

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGap(53, 53, 53)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		jLabel12)
																.addContainerGap(
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		Short.MAX_VALUE))
												.addGroup(
														layout.createSequentialGroup()
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.TRAILING)
																				.addGroup(
																						layout.createSequentialGroup()
																								.addGap(0,
																										0,
																										Short.MAX_VALUE)
																								.addGroup(
																										layout.createParallelGroup(
																												javax.swing.GroupLayout.Alignment.LEADING)
																												.addGroup(
																														javax.swing.GroupLayout.Alignment.TRAILING,
																														layout.createSequentialGroup()
																																.addComponent(
																																		jLabel1)
																																.addGap(43,
																																		43,
																																		43))
																												.addGroup(
																														layout.createSequentialGroup()
																																.addComponent(
																																		jLabel2)
																																.addGap(51,
																																		51,
																																		51)))
																								.addGroup(
																										layout.createParallelGroup(
																												javax.swing.GroupLayout.Alignment.LEADING,
																												false)
																												.addComponent(
																														txtLastname,
																														javax.swing.GroupLayout.DEFAULT_SIZE,
																														191,
																														Short.MAX_VALUE)
																												.addComponent(
																														txtForename)))
																				.addGroup(
																						javax.swing.GroupLayout.Alignment.LEADING,
																						layout.createSequentialGroup()
																								.addGroup(
																										layout.createParallelGroup(
																												javax.swing.GroupLayout.Alignment.LEADING)
																												.addComponent(
																														jLabel4)
																												.addComponent(
																														jLabel3)
																												.addComponent(
																														jLabel5)
																												.addComponent(
																														jLabel6)
																												.addComponent(
																														jLabel7)
																												.addComponent(
																														jLabel8)
																												.addComponent(
																														jLabel9))
																								.addGap(37,
																										37,
																										37)
																								.addGroup(
																										layout.createParallelGroup(
																												javax.swing.GroupLayout.Alignment.LEADING)
																												.addGroup(
																														layout.createSequentialGroup()
																																.addComponent(
																																		jLabel11)
																																.addGap(0,
																																		0,
																																		Short.MAX_VALUE))
																												.addComponent(
																														txtPhone,
																														javax.swing.GroupLayout.Alignment.TRAILING)
																												.addComponent(
																														txtEmail,
																														javax.swing.GroupLayout.Alignment.TRAILING)
																												.addComponent(
																														txtCity,
																														javax.swing.GroupLayout.Alignment.TRAILING)
																												.addComponent(
																														txtZip,
																														javax.swing.GroupLayout.Alignment.TRAILING)
																												.addComponent(
																														txtHousenumber,
																														javax.swing.GroupLayout.Alignment.TRAILING)
																												.addComponent(
																														txtStreet)
																												.addComponent(
																														txtMobile)
																												.addComponent(
																														txtComment))))
																.addGap(102,
																		102,
																		102))))
				.addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup()
								.addContainerGap(
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addComponent(jLabel10,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										244,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(91, 91, 91))
				.addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup()
								.addGap(65, 65, 65)
								.addComponent(btnAddPerson,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										125,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(47, 47, 47)
								.addComponent(btnCancel,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										126,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(0, 0, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addComponent(jLabel10)
								.addGap(37, 37, 37)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(
														txtLastname,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(jLabel1))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(
														txtForename,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(jLabel2))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(
														txtStreet,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(jLabel3))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(
														txtHousenumber,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(jLabel4))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(
														txtZip,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(jLabel5))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(
														txtCity,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(jLabel6))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(
														txtEmail,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(jLabel7))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(
														txtPhone,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(jLabel8))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(
														txtMobile,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(jLabel9))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(jLabel12)
												.addComponent(
														txtComment,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(18, 18, 18)
								.addComponent(jLabel11)
								.addGap(18, 18, 18)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(btnAddPerson)
												.addComponent(btnCancel))
								.addContainerGap(27, Short.MAX_VALUE)));

		pack();
	}//</editor-fold>//GEN-END:initComponents

	private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
		dispose();
	}//GEN-LAST:event_btnCancelActionPerformed

	/**
	 * @author Marcel
	 */

	private void btnAddPersonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddPersonActionPerformed
		Address Adr = new Address();

		if (txtLastname.getText().length() < 1) {
			txtLastname.setBackground(Color.RED);
			errorMessage.append("Bitte geben Sie einen Nachnamen ein.\n");
		} else {
			if (txtLastname.getText().matches("[a-zA-Z]*")) {
				txtLastname.setBackground(Color.WHITE);
			} else {
				txtLastname.setBackground(Color.RED);
				errorMessage
						.append("Ein Nachname kann nur aus Buchstaben bestehen.\n");
			}
		}

		if (txtForename.getText().length() < 1) {
			txtForename.setBackground(Color.RED);
			errorMessage.append("Bitte geben Sie einen Vornamen ein.\n");
		} else {
			if (txtForename.getText().matches("[a-zA-Z]*")) {
				txtForename.setBackground(Color.WHITE);
			} else {
				txtForename.setBackground(Color.RED);
				errorMessage
						.append("Ein Vorname kann nur aus Buchstaben bestehen.\n");
			}
		}

		if (txtZip.getText().length() > 0) {
			if (txtZip.getText().length() == 5
					&& txtZip.getText().matches("[1-9]+[0-9]*")) {
				try {
					Adr.setZip(Integer.parseInt(txtZip.getText()));
					txtZip.setBackground(Color.white);
				} catch (NumberFormatException e) {
					txtZip.setBackground(Color.RED);
					errorMessage
							.append("Die PLZ muss 5 Stellig sein und muss aus Zahlen bestehen.\n");
				}
			} else {
				txtZip.setBackground(Color.RED);
				errorMessage
						.append("Die PLZ muss 5 Stellig sein und muss aus Zahlen bestehen.\n");
			}
		} else {
			txtZip.setBackground(Color.white);
		}

		if (txtPhone.getText().length() >= 1) {
			try {
				Long.parseLong(txtPhone.getText());
				txtPhone.setBackground(Color.white);
			} catch (NumberFormatException e) {
				txtPhone.setBackground(Color.RED);
				errorMessage
						.append("Eine Telefonnummer besteht nur aus Zahlen.\n");
			}
		} else {
			txtPhone.setBackground(Color.RED);
			errorMessage.append("Eine Telefonnummer ist notwendig.\n");
		}

		if (txtHousenumber.getText().length() > 0) {
			if (txtHousenumber.getText().matches("[1-9]+[0-9]*[a-zA-Z]?")) {
				txtHousenumber.setBackground(Color.white);
				Adr.setHauseNumber(txtHousenumber.getText());
			} else {
				txtHousenumber.setBackground(Color.RED);
				errorMessage
						.append("Eine Hausnummer hat entweder das Format 10 oder 10a.\n");
			}
		} else {
			txtHousenumber.setBackground(Color.white);
		}

		if (txtStreet.getText().length() > 0) {
			if (txtStreet.getText().matches("[a-zA-Z]*")) {
				Adr.setStreetName(txtStreet.getText());
				txtStreet.setBackground(Color.white);
			} else {
				txtStreet.setBackground(Color.RED);
				errorMessage
						.append("Ein Straßenname kann nur aus Buchstaben bestehen.\n");
			}
		} else {
			txtStreet.setBackground(Color.white);
		}

		if (txtCity.getText().length() > 0) {
			if (txtCity.getText().matches("[a-zA-Z]*")) {
				Adr.setLocationName(txtCity.getText());
				txtCity.setBackground(Color.WHITE);
			} else {
				txtCity.setBackground(Color.RED);
				errorMessage
						.append("Ein Stadtname kann nur aus Buchstaben bestehen.\n");
			}
		} else {
			txtCity.setBackground(Color.WHITE);
		}

		if (validMailAddress(txtEmail.getText())
				|| txtEmail.getText().length() < 1) {
			txtEmail.setBackground(Color.white);
		} else {
			txtEmail.setBackground(Color.RED);
			errorMessage.append("Die Mail ist falsch.\n");
		}

		if (txtComment.getText().length() <= 70) {
			txtComment.setBackground(Color.white);
		} else {
			txtComment.setBackground(Color.RED);
			errorMessage
					.append("Ein Kommentar darf höchstens 70 Zeichen lang sein.\n");
		}

		System.out.println(errorMessage.toString());
		if (errorMessage.length() < 1) {
			try {
				Access.saveNewOrganisationPerson(new OrganisationPerson(
						txtForename.getText(), txtLastname.getText(), Adr,
						txtEmail.getText(), txtPhone.getText(), txtMobile
								.getText(), txtComment.getText(), true));

				JOptionPane.showMessageDialog(null,
						"Das Mitglied wurde hinzugefügt");
				dispose();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null,
						"Fehler, Bitte versuchen sie es erneut.", "Fehler",
						JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
		} else {
			;
			JOptionPane.showMessageDialog(null, errorMessage.toString());
			errorMessage.setLength(0);
		}

		// if(jTextField1.getText().equals("")) {
		// System.out.println("Nachname");
		// }

	}//GEN-LAST:event_btnAddPersonActionPerformed

	/**
	 * @author Marcel
	 */

	static boolean validMailAddress(String mailAddress) {
		/**
		 * @author Kevin Schneider
		 */

		if (mailAddress.matches("[\\w|-]+@\\w[\\w|-]*\\.[a-z]{2,3}")) {
			return true;
		} else
			return false;
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
					AddOrganisationPerson.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(
					AddOrganisationPerson.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(
					AddOrganisationPerson.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(
					AddOrganisationPerson.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		}
		//</editor-fold>

		/* Create and display the dialog */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				AddOrganisationPerson dialog = new AddOrganisationPerson(
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
	private javax.swing.JButton btnAddPerson;
	private javax.swing.JButton btnCancel;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel10;
	private javax.swing.JLabel jLabel11;
	private javax.swing.JLabel jLabel12;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JLabel jLabel6;
	private javax.swing.JLabel jLabel7;
	private javax.swing.JLabel jLabel8;
	private javax.swing.JLabel jLabel9;
	private javax.swing.JTextField txtCity;
	private javax.swing.JTextField txtComment;
	private javax.swing.JTextField txtEmail;
	private javax.swing.JTextField txtForename;
	private javax.swing.JTextField txtHousenumber;
	private javax.swing.JTextField txtLastname;
	private javax.swing.JTextField txtMobile;
	private javax.swing.JTextField txtPhone;
	private javax.swing.JTextField txtStreet;
	private javax.swing.JTextField txtZip;
	// End of variables declaration//GEN-END:variables
}
