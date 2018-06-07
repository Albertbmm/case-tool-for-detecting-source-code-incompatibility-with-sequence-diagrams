/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaparsermodel;

import ResourceManager.ResourceManager;
import java.io.File;
import javaparsermodel.process.MainPage;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author User
 */
public class FileChooser {
    
    public void chooseDirectory(String pathdiagramUrutan,String pathKode,String hasilDiagramUrutan,String hasilKodeSumber,String hasilDeteksi){
        JFileChooser jfSave = new JFileChooser();
        FileNameExtensionFilter jfSaveFilter = new FileNameExtensionFilter("File Project Pendeteksian Diagram Urutan","pkdu");
        ResourceManager rc = new ResourceManager();
        jfSave.setFileFilter(jfSaveFilter);
        jfSave.setDialogTitle("Save Project");
        int value = jfSave.showSaveDialog(null);
        if(value == JFileChooser.APPROVE_OPTION){
            SaveData data = new SaveData();
            File fi = jfSave.getSelectedFile();
            data.setHasilDeteksi(hasilDeteksi);
            data.setHasilEkstraksi(hasilDiagramUrutan, hasilKodeSumber);
            data.setFilePath(pathdiagramUrutan, pathKode);
            try{
                rc.save(data, fi.getPath()+".pkdu");
            }
            catch(Exception e){
                System.out.println("tidak dapat di save : "+e.getMessage());
            }
        }
        
    }
}
