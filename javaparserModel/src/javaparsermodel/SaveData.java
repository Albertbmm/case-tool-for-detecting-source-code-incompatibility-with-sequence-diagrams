/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaparsermodel;

/**
 *
 * @author USER
 */
public class SaveData implements java.io.Serializable{
    private static final long serialVersionUID = 1L;
    private String filePathDiagramUrutan;
    private String filePathFolderKodeSmber;
    private String hasilEkstrakDiagramUrutan;
    private String hasilEkstraksiKodeSumber;
    private String hasilDeteksi;
    public void setHasilEkstraksi(String ekstraksiDiagramUrutan,String ekstraksiKodeSumber){
        this.hasilEkstrakDiagramUrutan = ekstraksiDiagramUrutan;
        this.hasilEkstraksiKodeSumber = ekstraksiKodeSumber;
    }
    public void setFilePath(String filePathDiagram,String filePathKode){
            this.filePathDiagramUrutan = filePathDiagram;
            this.filePathFolderKodeSmber = filePathKode;
    }
    public void setHasilDeteksi(String hasil){
        this.hasilDeteksi = hasil;
    }
    public String getFilePath(){
        return this.filePathDiagramUrutan;
    }
    public String getFolderPath(){
        return this.filePathFolderKodeSmber;
    }
    public String getHasilEkstraksiDiagramUrutan(){
        return this.hasilEkstrakDiagramUrutan;
    }
    public String getHasilEkstraksiKodeSumber(){
        return this.hasilEkstraksiKodeSumber;
    }
    public String getHasilDeteksi(){
        return this.hasilDeteksi;
    }
}
