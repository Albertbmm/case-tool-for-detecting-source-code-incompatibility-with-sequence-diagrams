package data;


import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author User
 */

public class Kelas {
    Metode mtd = new Metode();
    ArrayList<Metode> listmetode;
    String nama="";
    String modifier="";
    String tipe ="";
    
    public Kelas() {
        listmetode = new ArrayList<Metode>();
    }    
//    public Kelas(String nama, String modifier,String tipe) {
//        //kelas harus constructor nya dibuat constructor kosong, lalu diberi getter setter
//        this.nama = nama;
//        this.modifier = modifier;
//        this.tipe = tipe;
//    }
   public void setKelas(String nama,String modifier,String tipe){
       this.nama = nama;
       this.modifier = modifier;
       this.tipe = tipe;
   }
   public Metode getMetode(){
       return this.mtd;
   }
    public void insertMetode(Metode metode) {
        listmetode.add(metode);    
    }
    
    public ArrayList<Metode> getIsiMetode(){
        return this.listmetode;
    }
   
     public void checkIsiMetode(){
        for(int x=0;x<listmetode.size();x++){
        
            System.out.println(listmetode.get(x).toString());
        }
//        if(!listmetode.isEmpty()){
//        System.out.println("ada isinya");
//        System.out.println();
//        }
//        
    }
     
     
    public boolean isEqual(String nama) {
        //mengecek metode ini sama atau tidak , dengan metode yang lain
        return this.nama.matches(nama);
    }

    public String getName() {
        return this.nama;
    }
    public Metode getMetode(String namametode) {
        //untuk mengambil nama metode yang kita cari
        //mengambil nama metode dengan iterasi satu"
        int size = listmetode.size();
        String simpanMetode = "";
        for(int x=0;x<size;x++){
            if(listmetode.get(x).namaMetode.toString().equals(namametode)){
                simpanMetode = listmetode.get(x).namaMetode.toString();
                System.out.println(simpanMetode);
            }
        }
        return null;
    }
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(modifier).append(" ").append(tipe).append(" ").append(nama);
        return stringBuilder.toString();
    }
}
