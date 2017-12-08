/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contest.performance.analyzer;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Image;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Write all team info in a PDF File
 * 
 * @author Shahriar
 * @version 1.0
 */
public class TeamPublisher {
    
    List<Team> teamList;
    
    /**
     * receive Team List, File Destination,
     * Then save it.
     * 
     * @param list list of all Team
     * @see Team
     */
    TeamPublisher(List<Team> list){
        teamList = list;
    }
    
    /**
     * It will filter the Path,
     * And remove the unnecessary points
     * 
     * @param path a String contains the path to be filtered
     * @return a String, which contains filtered path
     */
    private String filterPath(String path){
        String ret = new String();
        for( int i = 0; i<path.length(); i++ ){
            if( path.charAt(i) == '.' )
                break;
            ret += path.charAt(i);
        }
        return ret;
    }
    
    /**
     * Check whether a String is null or not
     * @param str String to be check
     * @return true if String is not NULL, otherwise false
     */
    boolean isValid(String str){
       for( int i = 0; i<str.length(); i++ )
           if( str.charAt(i) != ' ' )
               return true;
       return false;
    }
    
    /**
     * Starts team publishing process
     */
    void start(){
        
        Stage pdfWindow = new Stage();
        pdfWindow.setTitle("PDF Window");
        
        Label errorMsg = new Label("");
        errorMsg.setFont(javafx.scene.text.Font.font("Lucida Console", 16));
        errorMsg.setTextFill(Color.web("#DC0000"));
        
        Label headLine = new Label("PDF Head Line:");
        headLine.setFont(javafx.scene.text.Font.font("Georgia", 16));
        TextField headLineField = new TextField();
        headLineField.setMinWidth(350);
        headLineField.setMaxWidth(350);
        
        Label msg = new Label("Announcement:");
        msg.setFont(javafx.scene.text.Font.font("Georgia", 16));
        TextArea msgContainer = new TextArea();
        msgContainer.setMinWidth(350);
        msgContainer.setMaxWidth(350);
        
        VBox pdfInfoSection = new VBox(10);
        pdfInfoSection.setAlignment(Pos.CENTER_LEFT);
        
        Button publishBtn = new Button("Publish");
        publishBtn.setMinHeight(20);
        publishBtn.setMinWidth(50);
        
        publishBtn.setOnAction((ActionEvent e)->{
            String headLineText = headLineField.getText();
            String announcement = msgContainer.getText();
            if( isValid(headLineText) == false || isValid(announcement) == false ){
                errorMsg.setText("All Field must be Filled");
            }
            else{
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save File");
                File file = fileChooser.showSaveDialog(new Stage());
                if (file != null) {
                    String path = file.getAbsolutePath();
                    publish(path, headLineText, announcement);
                    pdfWindow.close();
                }
            }
        });
        
        // Margin of Each element
        int leftMargin = 25;
        VBox.setMargin(errorMsg, new Insets(0,0,0,leftMargin));
        VBox.setMargin(headLine, new Insets(0,0,0,leftMargin));
        VBox.setMargin(headLineField, new Insets(0,0,0,leftMargin));
        VBox.setMargin(msg, new Insets(20,0,0,leftMargin));
        VBox.setMargin(msgContainer, new Insets(0,0,0,leftMargin));
        VBox.setMargin(publishBtn, new Insets(0,0,0,leftMargin));
        
        pdfInfoSection.getChildren().addAll(errorMsg, headLine, headLineField, msg, msgContainer, publishBtn);
        
        Scene scene = new Scene(pdfInfoSection, 400, 370);
        
        pdfWindow.setScene(scene);
        pdfWindow.show();
        
    }
    
    /**
     * 
     * Write the Teams info in a PDF file
     * 
     * @param filePath location where file will be saved
     * @param headLine Head Line of the PDF File
     * @param announcement Announcement attached in the PDF file
     * @see Team
     */
    void publish(String filePath, String headLine, String announcement){
        
//        System.out.println("Writing Started");

        filePath = filterPath(filePath) + ".pdf";
        
//        PageSize.
        
        Document document = new Document(PageSize.A4, 100, 100, 100, 300);
        
        try{
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
        }catch(Exception e){}
        
        document.open();
        
        Paragraph blankLine = new Paragraph("\n");
        
        Paragraph head = new Paragraph(headLine, FontFactory.getFont(FontFactory.TIMES_BOLD, 16));
        head.setAlignment(Element.ALIGN_CENTER);
        
        try{
            document.add(head);
        }catch(Exception e){}
        
        try{
            document.add(blankLine);
        }catch(Exception e){}
        try{
            document.add(blankLine);
        }catch(Exception e){}
        
        for( int i = 0; i<teamList.size(); i++ ){
            Paragraph teamName = new Paragraph("Team " + (i+1), FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD));
            try{
                document.add(teamName);
            }catch(Exception e){}
            Team team = teamList.get(i);
            for( int j = 0; j<team.teamMember.size(); j++ ){
                Paragraph teamMember = new Paragraph("     Team Member " + (j+1) + ": " + team.teamMember.get(j).name + " (" + team.teamMember.get(j).regNo + ")", 
                            FontFactory.getFont(FontFactory.HELVETICA, 11));
                try{
                    document.add(teamMember);
                }catch(Exception e){}
            }
            try{
                document.add(blankLine);
            }catch(Exception e){}
        }
        
        Paragraph sectionHead = new Paragraph("Message from the Coach: ", FontFactory.getFont(FontFactory.TIMES_BOLD, 14));
        sectionHead.setSpacingBefore(7);
        
        try{
            document.add(sectionHead);
        }catch(Exception e){}
        
        Paragraph announcementSection = new Paragraph(announcement, FontFactory.getFont(FontFactory.TIMES_ROMAN, 11));
        announcementSection.setIndentationLeft(20);
        
        try{
            document.add(announcementSection);
        }catch(Exception e){}
        
        Paragraph bottomPara1 = new Paragraph("PDF Generated by Contest Performance Analyzer", FontFactory.getFont(FontFactory.COURIER_BOLD, 14));
        bottomPara1.setAlignment(Element.ALIGN_CENTER);
        bottomPara1.setSpacingBefore(20);
        
        Paragraph bottomPara2 = new Paragraph("Developed by Moudud Khan Shahriar", FontFactory.getFont(FontFactory.COURIER_BOLD, 14));
        bottomPara2.setAlignment(Element.ALIGN_CENTER);
        
        Paragraph bottomPara3 = new Paragraph("SUST CSE 2013", FontFactory.getFont(FontFactory.COURIER_BOLD, 14));
        bottomPara3.setAlignment(Element.ALIGN_CENTER);
        
        try{
            document.add(bottomPara1);
            document.add(bottomPara2);
            document.add(bottomPara3);
        }catch(Exception e){}
        
        document.close();
        
//        System.out.println("Writing End");
    }
    
}
