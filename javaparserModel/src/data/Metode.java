package data;

import java.util.ArrayList;
import java.util.List;
import data.BarisKodeSumber;
import java.util.ListIterator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author User
 */
public class Metode {
    //baris code isinya method call
    BarisKodeSumber bks = new BarisKodeSumber();
    ArrayList<BarisKodeSumber> bariskode = new ArrayList<BarisKodeSumber>();
    //ListIterator<BarisKodeSumber> it = bariskode.listIterator();
    
    String namaMetode = "";
    String modifierMetode = "";
    String tipeReturnMetode = "";
    String pemilikMetode ="";
    //membuat  1  variabel index untuk menunjukkan index sementara
    int indexPenanda;
    //mengisi methode dengan nama methode , modifier , dan dll pindahkan semua dari yang arraylist ke sini
    
    public Metode() {
        
    }
    
//    public Metode(String namaMetode,String modifierMetode,String tipeReturnMetode) {
//        //Jadikan constructor yang kosong
//        //nanti dikosongkan.
//        this.namaMetode = namaMetode;
//        this.modifierMetode = modifierMetode;
//        this.tipeReturnMetode = tipeReturnMetode;
//    }
    public  void setMetode(String namaMetode,String modifierMetode,String tipeReturnMetode){
        this.namaMetode = namaMetode;
        this.modifierMetode = modifierMetode;
        this.tipeReturnMetode = tipeReturnMetode;
    }
    public void  setPemilikMetode(String pemilikMetode){
        this.pemilikMetode = pemilikMetode;
    }
    public String getNamaMetode(){
        return this.namaMetode;
    }
    public String getPemilik(){
        return this.pemilikMetode;
    }
    //sett getter setter untuk menyetting variable dan isinya
    public void setMetode(){
        bks.setMetode(this);
    }
    public Integer getIndex(){
        return indexPenanda;
    }
    public void setIndex(int indexPenanda){
         this.indexPenanda = indexPenanda;
    }
    
    public void insertBarisKode(BarisKodeSumber bks) {
        bariskode.add(bks);
    }
    public ArrayList<BarisKodeSumber> getIsiBarisKode(){
        return this.bariskode;
    }
    
    public void checkBarisKode(){
        for(int x=0;x<bariskode.size();x++){
            System.out.println(bariskode.get(x).toString());
        }
    }
    public BarisKodeSumber getFirst() {
       //metode baris perintah , untuk memanggil kode sumber
       //metode bisa disearch, bisa di search by keyword
       //di search dulu , baru melakukan getFirst getNext
       //mennunjukkan pointer sementara posisi baris perintah , untuk pemanggilan kode sumber
       BarisKodeSumber barisData = null;
       indexPenanda = 0;
       barisData = bariskode.get(indexPenanda);
       setIndex(indexPenanda);
       System.out.println(barisData.toString() + " :Ini data dari getFirst");
       //perlu membikin getter  setter index ?
       return barisData;
    }
    
    public BarisKodeSumber getNext() {
        //untuk mengecek metode selanjutnya.
        //refrensi
        //https://stackoverflow.com/questions/16244205/using-next-to-call-next-iteration-of-arraylist
        BarisKodeSumber barisData = null;
        //melakukan validasi agar tidak terjadi out of bonds
        int index = getIndex();
        System.out.println(index);
        index = index + 1;
        //System.out.println(index + " Isinya ");
        setIndex(index);        
        int indexSimpan = bariskode.indexOf(index);
        //System.out.println(indexSimpan);
        if(bariskode.size() <= index || getIndex() == null ){
            
            System.out.println("Habis");
        }
        else{
            barisData = bariskode.get(index);
            System.out.println(barisData.toString());
        }
       return barisData;
    }
    public boolean isExist(BarisKodeSumber isiBariskode) {
        //untuk mengecek apakah bariskode yang dimasukkan ada atau tidak
        int size = bariskode.size();
        for(int x=0;x<size;x++){
            if(bariskode.get(x).equals(isiBariskode)){
                getFirst();
                getNext();
//                getNext();
//                getNext();
//                
                return true;
            }
        }
      return false;
    }
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(tipeReturnMetode).append(" ").append(modifierMetode).append(" ").append(namaMetode);
        return stringBuilder.toString();
    }
}
