/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResourceManager;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USER
 */
public class PDFwriter {
    
    public static void writePdf(String filename,String hasilEkstraksiDiagram,String hasilEkstraksiKode,String hasilDeteksi) throws DocumentException{
        Document document = new Document();
        try {
            System.out.println("DARI PDFWRITER"+hasilDeteksi);
            PdfWriter.getInstance(document, new FileOutputStream(new File(filename)));
            //mulai dengan membuka PDF
            document.open();
            Font f = new Font();
            f.setSize(16);
            f.setStyle(Font.BOLD);
            document.add(new Paragraph("Hasil Ekstraksi diagram urutan",f));
            //isi dari hasil ekstraksi dimasukkan ke paragraph selanjutnya
            Paragraph p2 = new Paragraph();
            p2.add(hasilEkstraksiDiagram);
            document.add(p2);
            
             document.add(new Paragraph("Hasil Ekstraksi Kode Sumber",f));
            //isi dari hasil ekstraksi dimasukkan ke paragraph selanjutnya
            Paragraph p3 = new Paragraph();
            p3.add(hasilEkstraksiKode);
            document.add(p3);
                    
             document.add(new Paragraph("Hasil Deteksi: ",f));
             Paragraph p4 = new Paragraph();
             p4.toString();
             p4.add(hasilDeteksi);
             document.add(p4);
             
             
             document.close();
             
             System.out.println("Berhasil convert PDF");
             
        } catch (FileNotFoundException ex) {
            System.out.println("error: "+ex.getMessage());
        }
    }
    
}
