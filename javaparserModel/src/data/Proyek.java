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
public class Proyek {
    ArrayList<Kelas> listkelas = new ArrayList<Kelas>();
    
    
    public Proyek() {
        //this.listkelas = new ArrayList<Kelas>();
    }
    public ArrayList<Kelas> getKelas(){
        return this.listkelas;
    }
    
    public void addKelas(Kelas kelas) {
        this.listkelas.add(kelas);
        //System.out.println(kelas.getName());
    }
    public void checkNamaClass(){
        //Untuk menampilkan nama class tinggal membuat return list
       for(int x=0;x<listkelas.size();x++){
           System.out.println(listkelas.get(x).toString());
       }    
    }
    
    
}
