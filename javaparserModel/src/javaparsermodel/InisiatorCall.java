/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaparsermodel;

/**
 *
 * @author User
 */
public class InisiatorCall {
    private String namaClassCaller;
    private String namaInisiasi;
    private String namaPemilikKodeSumber;
    public String getNamaClassCaller(){
        return namaClassCaller;
    }
    public void setNamaClassCaller(String namaClassCaller){
        if(namaClassCaller!=null){
             this.namaClassCaller = namaClassCaller;   
           }   
           else{
               this.namaClassCaller = "kosong";
           }
        
    }
    public String getNamaPemilik(){
        return this.namaPemilikKodeSumber;
    }
    public String getNamaInisiasi(){
        return namaInisiasi;
    }
    public void setPemilikKode(String namaPemilikKodeSumber){
         if(namaPemilikKodeSumber!=null){
             this.namaPemilikKodeSumber = namaPemilikKodeSumber;    
           }   
           else{
               this.namaPemilikKodeSumber = "kosong";
           }
    }
    public void setNamaInisiasi(String namaInisiasi){
        if(namaInisiasi!=null){
             this.namaInisiasi = namaInisiasi;    
           }   
           else{
               this.namaInisiasi = "kosong";
           }
        //this.namaInisiasi = namaInisiasi;
    }
     @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(namaClassCaller).append(".").append(namaInisiasi).append(" ").append(namaPemilikKodeSumber);
        return stringBuilder.toString();
    }
}
