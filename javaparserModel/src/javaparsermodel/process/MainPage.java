/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaparsermodel.process;

import ResourceManager.PDFwriter;
import ResourceManager.ResourceManager;
import com.itextpdf.text.DocumentException;
import data.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaparserModel.process.FileParser;
import javaparsermodel.FileChooser;
import javaparsermodel.SaveData;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 *
 * @author User
 */
public class MainPage extends javax.swing.JFrame {

    private Proyek project;
    private Kelas kls;
    private Metode mtd ;
    private Matching matching;
    private CodeParserStack cps;
    private FileParser fps;
    private SequenceDiagram sqData = new SequenceDiagram();
    //private ArrayList<Triplet> sqTriplet = new ArrayList<Triplet>();
    private ArrayList<Kelas> simpanKelas = new ArrayList<Kelas>();
    private ArrayList<Metode> simpanMetode = new ArrayList<Metode>();
    private ArrayList<BarisKodeSumber> simpanBarisKode = new ArrayList<BarisKodeSumber>();
    private ArrayList<Triplet> simpanTriplet = new ArrayList<Triplet>();
    private ArrayList<String> hasilSimpan = new ArrayList<String>();
    private String isiTextArea = new String();
    
    /**
     * Creates new form main
     */
    //bikin get value data dari arrayList simpanKelas, simpanMetode, simpanBarisKode
    
    public void sequenceCall(String xmlfile){
        try {
            int bil = 1;
            fps = new FileParser();
            fps.parseFile(xmlfile);
            fps.creatinTriplet();
            //fps.readList();
            sqData = fps.getSequence();
            //sqData.checkIsiTriplet();
            ArrayList<Triplet> sqTriplet = new ArrayList<Triplet>();
            sqTriplet = sqData.getIsiTriplet();
            System.out.println(sqTriplet.size());
            for(int x=0;x<sqTriplet.size();x++){
                simpanTriplet.add(sqTriplet.get(x));
                jTextArea2.append(bil+"."+" "+sqTriplet.get(x).toString()+"\n");
                bil++;
            }
            System.out.println(isiTextArea);
        } catch (IOException ex) {
            Logger.getLogger(MainPage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(MainPage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(MainPage.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
    public void listFilesForFolder(File folder){
    String filePath = null; 
    for(File fileEntry :  folder.listFiles()){
         System.out.println(fileEntry.getAbsolutePath());
         filePath = fileEntry.getAbsolutePath();
         codeCall(filePath);
         System.out.println();
      }   
    }
    
    public void codeCall(String xmlfile){
        cps = new CodeParserStack(); 
         try {
            XMLReader xr = XMLReaderFactory.createXMLReader();
            xr.setContentHandler(cps);
             xr.parse( new InputSource(new FileReader(xmlfile)));
         } catch(Exception e) {
             e.printStackTrace();
         }
        cps.buildPemanggilan();
        cps.getClassMethodCaller();       
        project = cps.getProyek();
        ArrayList<Kelas> isiKelas = project.getKelas();
        jTextArea3.append("Nama Kelas : ");
        for(int x=0;x<isiKelas.size();x++){
            simpanKelas.add(isiKelas.get(x));
            
            jTextArea3.append(isiKelas.get(x).toString()+" "+"\n");
        }
        kls = cps.getKelasData();
        ArrayList<Metode> isiMetode = kls.getIsiMetode();
        jTextArea3.append("List Metode : \n");
        int banyakMetode = 1;
        int nomer=1;
        for(int x=0;x<isiMetode.size();x++){
             kls = new Kelas();
             kls.insertMetode(isiMetode.get(x));
        
             simpanMetode.add(isiMetode.get(x));
            
            jTextArea3.append(banyakMetode+". "+isiMetode.get(x).toString()+" "+"\n");
            banyakMetode++;
        }
        mtd = cps.getBarisKode();
        //ArrayList<BarisKodeSumber> isiBarisKode = mtd.getIsiBarisKode();
        ArrayList<BarisKodeSumber> isiBarisKode = mtd.getIsiBarisKode();
        System.out.println(isiBarisKode);
        if(isiBarisKode.isEmpty()){
            jTextArea3.append("\n");
        }
        else{
          jTextArea3.append("Isi Baris Kode : "+"\n");
        for(int x=0;x<isiBarisKode.size();x++){
            mtd = new Metode();
            mtd.insertBarisKode(isiBarisKode.get(x));
            simpanBarisKode.add(isiBarisKode.get(x));
            jTextArea3.append(nomer+"."+isiBarisKode.get(x).toString()+" "+"didalam"+" "+isiBarisKode.get(x).getPemilik()+"\n");   
            nomer++;
        }   
        jTextArea3.append("\n");
        }
    }
    public ArrayList<Kelas> getKelasList(){
        return this.simpanKelas;
    }
    public ArrayList<Metode> getMetodeList(){
        return this.simpanMetode;
    }
    public ArrayList<BarisKodeSumber> getBarisKode(){
        return this.simpanBarisKode;
    }
    
    public MainPage() {
        initComponents();
        jPanel1.setVisible(false);
        jPanel3.setVisible(false);
        jScrollPane1.setVisible(false);
        jButton1.setVisible(false);
        jButton3.setVisible(false);
        jButton4.setVisible(false);
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        ButtonDiagramUrutuan = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        jTextField2 = new javax.swing.JTextField();
        ButtonKodeSumber = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea3 = new javax.swing.JTextArea();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton3 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Diagram Urutuan"));
        jPanel1.setToolTipText("");

        jTextField1.setBackground(new java.awt.Color(204, 204, 204));
        jTextField1.setText("Diagram urutan .....");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        ButtonDiagramUrutuan.setText("Pilih");
        ButtonDiagramUrutuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonDiagramUrutuanActionPerformed(evt);
            }
        });

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jTextField1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ButtonDiagramUrutuan))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ButtonDiagramUrutuan))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
                .addGap(16, 16, 16))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Kode Sumber"));
        jPanel3.setToolTipText("");

        jTextField2.setBackground(new java.awt.Color(204, 204, 204));
        jTextField2.setText("Berkas Kode Sumber ...");

        ButtonKodeSumber.setText("Pilih");
        ButtonKodeSumber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonKodeSumberActionPerformed(evt);
            }
        });

        jTextArea3.setColumns(20);
        jTextArea3.setRows(5);
        jScrollPane3.setViewportView(jTextArea3);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ButtonKodeSumber))
                    .addComponent(jScrollPane3))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ButtonKodeSumber))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
                .addGap(16, 16, 16))
        );

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jButton3.setText("Proses");
        jButton3.setMaximumSize(new java.awt.Dimension(73, 23));
        jButton3.setMinimumSize(new java.awt.Dimension(73, 23));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton1.setLabel("Save");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton4.setText("export PDF ...");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jMenu1.setText("Project");

        jMenuItem1.setText("Create Project");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Open");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem3.setText("Close Project");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(61, 61, 61)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton4)
                .addGap(26, 26, 26))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4)
                .addGap(6, 6, 6))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed
    //UNTUK SEQUENCE DIAGRAM
    private void ButtonDiagramUrutuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonDiagramUrutuanActionPerformed
       try{
       JFileChooser chooser = new JFileChooser();
       FileNameExtensionFilter diagramUrutanFilter = new FileNameExtensionFilter("File XMI diagram Urutan","xmi","xml");
       chooser.setDialogTitle("Open sequence diagram ");
       chooser.setFileFilter(diagramUrutanFilter);
       int value = chooser.showOpenDialog(null);
       File f = chooser.getSelectedFile();
       String directory = f.getAbsolutePath();
       if(value == JFileChooser.APPROVE_OPTION){
           jTextField1.setText(directory);
           sequenceCall(directory);
           jTextArea2.setEditable(false);
       }}
       catch(NullPointerException e){
           JOptionPane.showMessageDialog(null, "pilih file", "FileNotchoosed", JOptionPane.INFORMATION_MESSAGE);
       }
    }//GEN-LAST:event_ButtonDiagramUrutuanActionPerformed
    //UNTUK KODE SUMBER
    private void ButtonKodeSumberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonKodeSumberActionPerformed
        try{
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int value = chooser.showOpenDialog(null);
        File f = chooser.getSelectedFile();
        String directory = f.getAbsolutePath();
        if(value == JFileChooser.APPROVE_OPTION){
             jTextField2.setText(directory);
             //disini parse untuk kode sumber
             jTextArea3.setEditable(false);
             listFilesForFolder(f);
        }}
        catch(NullPointerException e){
            JOptionPane.showMessageDialog(null, "pilih file", "FileNotchoosed", JOptionPane.INFORMATION_MESSAGE);
        }
        
    }//GEN-LAST:event_ButtonKodeSumberActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        String isiCodeFile = jTextField2.getText();
        String isiSequenceFile = jTextField1.getText();
        matching = new Matching();
        try {
            matching.matching(isiSequenceFile, isiCodeFile);
            hasilSimpan = matching.getHasil();
            System.out.println(hasilSimpan.size());
            for(int x=0;x<hasilSimpan.size();x++){
                jTextArea1.append(hasilSimpan.get(x)+"\n");
            }
            jTextArea1.setEditable(false);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Harap pilih xmi diagram urutan, dan xml kode sumber", "File tidak ada", JOptionPane.INFORMATION_MESSAGE);
        } catch (SAXException ex) {
            JOptionPane.showMessageDialog(null, "terdapat error pada SAX", "program error", JOptionPane.INFORMATION_MESSAGE);
            Logger.getLogger(MainPage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            JOptionPane.showMessageDialog(null, "terdapat error configuration", "program error", JOptionPane.INFORMATION_MESSAGE);
            Logger.getLogger(MainPage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        //untuk save function
        FileChooser fc = new FileChooser();
        String pathDiagramUrutan = jTextField1.getText();
        String pathKodeSumber = jTextField2.getText();
        String hasilDiagramUrutan = jTextArea2.getText();
        String hasilKodeSumber = jTextArea3.getText();
        String hasilDeteksi = jTextArea1.getText();
        fc.chooseDirectory(pathDiagramUrutan,pathKodeSumber ,hasilDiagramUrutan , hasilKodeSumber,hasilDeteksi );        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        //untuk melakukan export ke pdf 
        PDFwriter pw = new PDFwriter();
        JFileChooser jfPDF = new JFileChooser();
        FileNameExtensionFilter jfOpenFilter = new FileNameExtensionFilter("PDF Files","pdf");
        jfPDF.setDialogTitle("Export Detection to PDF");
        jfPDF.setFileFilter(jfOpenFilter);
        int value = jfPDF.showSaveDialog(null);
        
        if(value == JFileChooser.APPROVE_OPTION){
            try {
                //masukkan fungsi PDF
                String hasilDiagramUrutan = jTextArea2.getText();
                String hasilKodeSumber = jTextArea3.getText();
                String hasilDeteksi = jTextArea1.getText();
                //System.out.println(hasilDeteksi);
                File pdfPath = jfPDF.getSelectedFile();
                pw.writePdf(pdfPath.getPath()+".pdf", hasilDiagramUrutan, hasilKodeSumber, hasilDeteksi);
                
            } catch (DocumentException ex) {
                Logger.getLogger(MainPage.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
          newProyek();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        //melakukan JFileChooser open seperti tombol open. lalu tombol open dihilangkan
        //saat melakukan ini semua di set visible true, lalu memasukkan load lalu menset value ke text area dan textfield
        //dikasi fungsi untuk melakukan hal kyk gini
        //nama metodenya open 
        JFileChooser jfOpen = new JFileChooser();
        FileNameExtensionFilter jfOpenFilter = new FileNameExtensionFilter("File Project Pendeteksian Diagram Urutan","pkdu");
        jfOpen.setFileFilter(jfOpenFilter);
        jfOpen.setDialogTitle("Open Project");
        int value = jfOpen.showOpenDialog(null);
        if(value == JFileChooser.APPROVE_OPTION){
            File fi = jfOpen.getSelectedFile();
            try{
                SaveData data = (SaveData) ResourceManager.load(fi.getAbsolutePath());
                jPanel1.setVisible(true);
                jPanel3.setVisible(true);
                jScrollPane1.setVisible(true);
                jButton1.setVisible(true);
                //jButton2.setVisible(true);
                jButton3.setVisible(true);
                jButton4.setVisible(true);
                jTextField1.setText(data.getFilePath());
                jTextField2.setText(data.getFolderPath());
                jTextArea2.setEditable(false);
                jTextArea3.setEditable(false);
                jTextArea1.setEditable(false);
                jTextArea2.setText(data.getHasilEkstraksiDiagramUrutan());
                jTextArea3.setText(data.getHasilEkstraksiKodeSumber());
                jTextArea1.setText(data.getHasilDeteksi());
            }
            catch(Exception e){
                System.out.println("tidak bisa di buka: "+e.getMessage());
            }
            
        }
        
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        jPanel1.setVisible(false);
        jPanel3.setVisible(false);
        jScrollPane1.setVisible(false);
        jButton1.setVisible(false);
        //jButton2.setVisible(false);
        jButton3.setVisible(false);
        jButton4.setVisible(false);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws IOException, SAXException {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainPage().setVisible(true);
                
            }
        });
        
        
        //debug
      
     //MainPage mfc = new MainPage("src/xml/MenuUI.xml");
     //MainPage mfc = new MainPage();
     //mfc.sequenceCall("src/xml/contoh1.xmi");
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ButtonDiagramUrutuan;
    private javax.swing.JButton ButtonKodeSumber;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextArea jTextArea3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables

    public void newProyek() {
        jPanel1.setVisible(true);
        jPanel3.setVisible(true);
        jScrollPane1.setVisible(true);
        jButton1.setVisible(true);
        //jButton2.setVisible(true);
        jButton3.setVisible(true);
        jButton4.setVisible(true);
        jTextField1.setText("Diagram urutan .....");
        jTextField2.setText("Berkas Kode Sumber ...");
        jTextArea1.setText(" ");
        jTextArea2.setText(" ");
        jTextArea3.setText(" ");
    }
}
