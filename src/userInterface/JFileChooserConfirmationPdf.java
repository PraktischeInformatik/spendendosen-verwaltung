/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package userInterface;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Timo
 * 
 * This class extends the functionality of JFileChooser with an overwrite protection for pdf files
 * 
 */
public class JFileChooserConfirmationPdf extends JFileChooser {
    @Override
    public void approveSelection(){
        File file = getSelectedFile();
        if (!file.getAbsolutePath().endsWith(".pdf")){
            file = new File(file.getAbsolutePath() + ".pdf");
        }
        if(file.exists() && getDialogType() == SAVE_DIALOG){
            int result = JOptionPane.showConfirmDialog(this,
                    "Der gewählte Dateiname ist bereits "
                    + "vergeben, möchten sie die "
                    + "Vorhandene Datei überschreiben?",
                    "Dateiname bereits vorhanden",
                    JOptionPane.YES_NO_CANCEL_OPTION);
            switch(result){
                case JOptionPane.YES_OPTION:
                    super.approveSelection();
                    return;
                case JOptionPane.NO_OPTION:
                    return;
                case JOptionPane.CLOSED_OPTION:
                    return;
                case JOptionPane.CANCEL_OPTION:
                    cancelSelection();
                    return;
            }
        }
        super.approveSelection();
        
    } 
}
