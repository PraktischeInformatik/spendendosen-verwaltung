/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package userInterface;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import applicationLogic.DiagramCalculation;

/**
 * 
 * @author Timo
 */
public class DisplayDiagram extends javax.swing.JDialog {

	public static final int COLLECTION_COMPARE_DIAGRAM = 1;
	public static final int COLLECTION_DIAGRAM = 2;
	public static final int OVERALL_DIAGRAM = 3;
	int type;
	ArrayList<Integer> collIds;
	int collId;

	/**
	 * Initializes the Window
	 * 
	 * @param parent
	 *            the parent
	 * @param modal
	 *            determines if the window should be modal or not
	 * @param diagram
	 *            the panel holding the diagram
	 * @param type
	 *            determines the type of diagram we want to display in the
	 *            window. 1 = Collection compare diagram; 2 = Collection
	 *            diagram; 3 = Overall diagram
	 */
	public DisplayDiagram(java.awt.Frame parent, boolean modal, JPanel diagram,
			int type) {
		super(parent, modal);
		this.diagram = diagram;
		this.type = type;
		initComponents();
		this.setLocationRelativeTo(null);
		//added by sz
		Image imgIcon = new ImageIcon("images/icon.png").getImage();
		setIconImage(imgIcon);
		//	
		
	}

	public DisplayDiagram(java.awt.Frame parent, boolean modal, JPanel diagram,
			int type, ArrayList<Integer> collIds) {
		super(parent, modal);
		this.diagram = diagram;
		this.type = type;
		initComponents();
		this.setLocationRelativeTo(null);
		this.collIds = collIds;
		//added by sz
		Image imgIcon = new ImageIcon("images/icon.png").getImage();
		setIconImage(imgIcon);
		//
	}

	public DisplayDiagram(java.awt.Frame parent, boolean modal, JPanel diagram,
			int type, int collId) {
		super(parent, modal);
		this.diagram = diagram;
		this.type = type;
		initComponents();
		this.setLocationRelativeTo(null);
		this.collId = collId;
		//added by sz
		Image imgIcon = new ImageIcon("images/icon.png").getImage();
		setIconImage(imgIcon);
		//
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fc = new javax.swing.JFileChooser();
        diagram = diagram; ;
        ImageIcon imgPfeil = new ImageIcon("images/imgPfeil.png");
        btnExport = new javax.swing.JButton();
        btnExport.setIcon(imgPfeil);
        ImageIcon imgKreuz = new ImageIcon("images/imgKreuz.png");
        btnClose = new javax.swing.JButton();
        btnClose.setIcon(imgKreuz);

        fc.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);
        fc.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        fc.setName(""); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        javax.swing.GroupLayout diagramLayout = new javax.swing.GroupLayout(diagram);
        diagram.setLayout(diagramLayout);
        diagramLayout.setHorizontalGroup(
            diagramLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 359, Short.MAX_VALUE)
        );
        diagramLayout.setVerticalGroup(
            diagramLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 170, Short.MAX_VALUE)
        );

        btnExport.setText("Export");
        btnExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportActionPerformed(evt);
            }
        });

        btnClose.setText("Schließen");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addComponent(diagram, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExport, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(diagram, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                .addComponent(btnExport)
                .addGap(18, 18, 18)
                .addComponent(btnClose)
                .addGap(13, 13, 13))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

	private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
		dispose();
	}//GEN-LAST:event_btnCloseActionPerformed

	private void btnExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportActionPerformed
		JFileChooserConfirmationPng fc = new JFileChooserConfirmationPng();
		FileNameExtensionFilter plainFilter = new FileNameExtensionFilter(
				"Bild Dateien (.png)", "png", "PNG", "Png");
		fc.setFileFilter(plainFilter);
		fc.removeChoosableFileFilter(fc.getAcceptAllFileFilter());
		String home = System.getProperty("user.home");
		home = home.trim();
		fc.setSelectedFile(new File(home + "/Diagramm"));
		int result = fc.showSaveDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			if (!file.getAbsolutePath().endsWith(".png")) {
				file = new File(file.getAbsolutePath() + ".png");
			}
			String path = file.getAbsolutePath();
			if (plainFilter.accept(file)) {
				if (type == COLLECTION_COMPARE_DIAGRAM) {
					try {
						DiagramCalculation.exportCollectionCompareDiagram(
								collIds, path);
						System.out.println(path);
					} catch (IOException ex) {
						JOptionPane.showMessageDialog(null,
								"Es ist ein Fehler aufgetreten!", "Fehler",
								JOptionPane.ERROR_MESSAGE);
						ex.printStackTrace();
					}
				} else if (type == COLLECTION_DIAGRAM) {
					try {
						DiagramCalculation
								.exportCollectionDiagram(collId, path);
					} catch (IOException ex) {
						JOptionPane.showMessageDialog(null,
								"Es ist ein Fehler aufgetreten!", "Fehler",
								JOptionPane.ERROR_MESSAGE);
						ex.printStackTrace();
					}
				} else if (type == OVERALL_DIAGRAM) {
					try {
						DiagramCalculation.exportOverallDiagram(path);
					} catch (IOException ex) {
						JOptionPane.showMessageDialog(null,
								"Es ist ein Fehler aufgetreten!", "Fehler",
								JOptionPane.ERROR_MESSAGE);
						ex.printStackTrace();
					}
				}
			}
		}
	}//GEN-LAST:event_btnExportActionPerformed

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
			java.util.logging.Logger.getLogger(DisplayDiagram.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(DisplayDiagram.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(DisplayDiagram.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(DisplayDiagram.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		}
		//</editor-fold>
		
 
	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnExport;
    private javax.swing.JPanel diagram;
    private javax.swing.JFileChooser fc;
    // End of variables declaration//GEN-END:variables
}
