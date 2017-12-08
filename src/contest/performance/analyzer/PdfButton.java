/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contest.performance.analyzer;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Make a PDF Button
 * 
 * @author Shahriar
 * @version 1.0
 */
public class PdfButton {
    
    /**
     * make a View for PDF Button
     * 
     * @return PDF Button View
     */
    Button getPdfButton(){
        Image pdfIcon = new Image("/contest/performance/analyzer/resources/pdf.png", 70, 70, false, true);
        Button pdfBtn = new Button( "", new ImageView(pdfIcon) );
        pdfBtn.getStyleClass().add("pdf-btn");
        pdfBtn.setStyle("-fx-background-color: transparent;");
        return pdfBtn;
    }
}
