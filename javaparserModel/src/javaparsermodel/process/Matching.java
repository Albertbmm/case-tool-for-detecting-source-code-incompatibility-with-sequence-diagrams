/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaparsermodel.process;

import ResourceManager.Similiarity;
import data.BarisKodeSumber;
import data.Kelas;
import data.Metode;
import data.Proyek;
import data.SequenceDiagram;
import data.Triplet;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaparserModel.process.FileParser;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 *
 * @author User
 */

public class Matching {
    //melakukan matching string matching kode sumber terhadap sequence diagram
    //Membuat metode dan fungsi matching nya disini
     private FileParser fps;
     private SequenceDiagram sq;
     private Triplet tpl;
     private Kelas kelasData = new Kelas();
     private Metode mtd = new Metode();
     private String simpan;
     private Matching mp;
     private CodeParserStack cp = new CodeParserStack();
     private Proyek project;
     private String direktory;
     private ArrayList<Triplet> isiTriplet = new ArrayList<Triplet>();
     private ArrayList<String> isiFile = new ArrayList<String>();
     private ArrayList<Metode> isiMetode = new ArrayList<Metode>();
     private ArrayList<Kelas> isiKelasBaru = new ArrayList<Kelas>();
     private ArrayList<BarisKodeSumber> isiKodeSumber = new ArrayList<BarisKodeSumber>();
     private ArrayList<String> isiActor = new ArrayList<String>();
     private ArrayList<String> hasilMatchingnya = new ArrayList<String>();
     private InputSource inputSource = new InputSource();
     //untuk melakukan compare similiarity di fungsi match obyek 
     private ArrayList<String> hasilString1 = new ArrayList<String>();
     private ArrayList<String> hasilString2 = new ArrayList<String>();
     //untuk melakukan compare similarity di fungsi match predikat
     private ArrayList<String> stringPredikat = new ArrayList<String>();
     private ArrayList<String> stringCariKelas = new ArrayList<String>();
     //untuk metode matching 
     private String subyekBerhasil;
     private String obyekBerhasil;
     private String predikatBerhasil;
     
     //untuk triplet 
     private String subyek;
     private String obyek;
     private String predikat;
     private String predikatSebelum =null;
     private String obyekSebelum = null;
     
     //penanda Aktor
     private boolean setActor = false;
 
     public void matching(String sequenceFile,String codeFile) throws IOException, SAXException, ParserConfigurationException{
                
        fps = new FileParser();
        //fps.parseFile("src/XML/contoh1.xmi");
        fps.parseFile(sequenceFile);
        fps.creatinTriplet();
        fps.getActor();
        //fps.readList();
        sq = fps.getSequence();
        isiTriplet = sq.getIsiTriplet();
        File isi = new File(codeFile);
        MainPage mpg = new MainPage();
        mpg.listFilesForFolder(isi);
        isiKelasBaru = mpg.getKelasList();
        isiMetode = mpg.getMetodeList();
        isiKodeSumber = mpg.getBarisKode();
        isiActor = fps.getListActor();
        isiActor.removeAll(Collections.singleton(null));
        hasilMatching();
    }
    
    public static void main(String args[]){
         try {
             Matching mp = new Matching();
             mp.matching("src/XML/contohKasusReal.xmi","E:\\Kasus Data\\Contoh Kasus visualParadigm\\xmlContohKasusDiagram");
         } catch (IOException ex) {
             Logger.getLogger(Matching.class.getName()).log(Level.SEVERE, null, ex);
         } catch (SAXException ex) {
             Logger.getLogger(Matching.class.getName()).log(Level.SEVERE, null, ex);
         } catch (ParserConfigurationException ex) {
             Logger.getLogger(Matching.class.getName()).log(Level.SEVERE, null, ex);
         }
    }

    private String matchingSubyek(String subyek, String predikat1, String obyek1) {
     String subyekMantap = null;
     if(predikatSebelum==null){
         subyekMantap = checkSubyekNotPredikat(subyek, predikat1, obyek1);
     }
     else{
         if(subyek.equalsIgnoreCase(predikatSebelum)){    
             if(!isiMetode.isEmpty()){
                 subyekMantap = checkSubyekYesPredikat(subyek, predikat1, obyek1);
             }}
         
         else{
          subyekMantap = checkSubyekNotPredikat(subyek, predikat1, obyek1);
          } 
     }
        return subyekMantap;
     }
    
    public void hasilMatching(){
        for(int x=0;x<isiTriplet.size();x++){   
            checkActor(isiTriplet.get(x).getSubyek());
            if(setActor == true){
                subyek = isiTriplet.get(x).getSubyek() + " (V) ";
                predikat = matchingPredikat(isiTriplet.get(x).getPredikat());
                checkActor(isiTriplet.get(x).getPredikat());
                 if(setActor == true){
                     predikat = isiTriplet.get(x).getPredikat()+" (X) ";
                     obyek = isiTriplet.get(x).getObyek()+" (X) ";
                 }
                 else{
                      predikat = matchingPredikat(isiTriplet.get(x).getPredikat());
                      obyek = matchingObyek(isiTriplet.get(x).getObyek(),isiTriplet.get(x).getPredikat());
                 }
            }
            else {
            subyek = matchingSubyek(isiTriplet.get(x).getSubyek().toString(),isiTriplet.get(x).getPredikat(),isiTriplet.get(x).getObyek());
            checkActor(isiTriplet.get(x).getPredikat());
                if(setActor == true){
                predikat = isiTriplet.get(x).getPredikat()+" (X) ";
                obyek = isiTriplet.get(x).getObyek()+" (X) ";
                }
                else {
              predikat = matchingPredikat(isiTriplet.get(x).getPredikat());
              obyek = matchingObyek(isiTriplet.get(x).getObyek(),isiTriplet.get(x).getPredikat());
               }
            }
            String hasilAkhir = subyek+" "+obyek+" "+predikat;
            hasilMatchingnya.add(hasilAkhir);
            System.out.println(subyek+" "+obyek+" "+predikat);
      }
    }
     
    private String checkSubyekNotPredikat(String subyek,String predikat1,String obyek1){
        String subyekMantap=null;
        tripletLoop:
        for(int y=0;y<isiKelasBaru.size();y++){
//                   System.out.println("Berhasil masuk");

                   String namaKelas = isiKelasBaru.get(y).getName();
                   if(subyek.equalsIgnoreCase(namaKelas))
                   {    
                       if(!isiMetode.isEmpty()){
                           for(int k=0;k<isiMetode.size();k++){
                               String pemilikMetode = isiMetode.get(k).getPemilik();
                               if(namaKelas.equals(pemilikMetode)){
                                   String metodeSimpan  = isiMetode.get(k).toString();
                                   String namaMetode = isiMetode.get(k).getNamaMetode();
                                   for(int l=0;l<isiKodeSumber.size();l++){
                                       
                                       String pemilikKodeSumber = isiKodeSumber.get(l).getPemilik();
                                       String pemanggilKode = isiKodeSumber.get(l).getPemanggil();
                                       String dipanggilKode = isiKodeSumber.get(l).getDipanggil();
                                       
                                       if(namaMetode.equals(pemilikKodeSumber)){
                                           String simpanKodeSumber = isiKodeSumber.get(l).toString();
                                              if(pemanggilKode.equalsIgnoreCase(predikat1) && dipanggilKode.equalsIgnoreCase(obyek1)){
                                                 subyekMantap = subyek+" (V) ";
                                                 predikatSebelum = predikat1;
                                                 obyekSebelum = obyek1;
                                                 break tripletLoop;
                                              }
                                       }   
                                   }
                               }
                                 
                           }  
                         
                       }
                       
                   }
                   else
                   {
                       subyekMantap = subyek+" (X) ";
                   }
               }
        return subyekMantap;
    }
    
    private String checkSubyekYesPredikat(String subyek,String predikat1,String obyek1){
        String subyekMantap=null;
        tripletLoop:
           for(int k=0;k<isiMetode.size();k++){
             String namaMetode = isiMetode.get(k).getNamaMetode();
             String pemilikMetode = isiMetode.get(k).getPemilik();
                      if(subyek.equalsIgnoreCase(pemilikMetode)){
                for(int l=0;l<isiKodeSumber.size();l++){
                   String pemilikKodeSumber = isiKodeSumber.get(l).getPemilik();
                   String pemanggilKode = isiKodeSumber.get(l).getPemanggil();
                   String dipanggilKode = isiKodeSumber.get(l).getDipanggil();
                     if(obyekSebelum.equals(pemilikKodeSumber)){
                       System.out.println(namaMetode+" "+pemilikKodeSumber);
                       String simpanKodeSumber = isiKodeSumber.get(l).toString();
                         if(pemanggilKode.equalsIgnoreCase(predikat1) && dipanggilKode.equalsIgnoreCase(obyek1)){
                            subyekMantap = subyek+" (V) ";
                            predikatSebelum = predikat1;
                            obyekSebelum = obyek1;
                            break tripletLoop;
                           }
                        }
                      else{
                       subyekMantap = subyek+" (X) ";
                        }
                }
              }
           }
        return subyekMantap;
    }
   
    private String matchingPredikat(String predikat) {
       Similiarity sml = new Similiarity();
       String predikatMantap=null;
       //mengambil nama kelas lagi
       //cek nama kelas 
        for(int a=0;a<isiKelasBaru.size();a++){
                String namaKelasCekObyek = isiKelasBaru.get(a).getName();
                    if(predikat.equals(namaKelasCekObyek)){
                       predikatMantap = predikat+" (V) ";
                       break;
                    }
                    else{
                        //melakukan deteksi similarity disini
                        //melakukan loop lagi untuk mengambil nama kelas yang baru
                        
                        stringPredikat = sml.splitCamelCaseString(predikat);
                        String kalimatSatu = sml.combineText(stringPredikat);
                        String[] words1 = kalimatSatu.split(" ");
                        //loop dibawah untuk mengcompare semua isi dari kelas-kelas dan kalo ada yang melebihi treshold berhasil
                        for(int x=0;x<isiKelasBaru.size();x++){
                          stringCariKelas = sml.splitCamelCaseString(isiKelasBaru.get(x).getName());
                          String kalimatDua = sml.combineText(stringCariKelas);
                          String[] words2 = kalimatDua.split(" ");
                          double hasil = sml.compute(words1, words2);
                          if(hasil > 0.45){
                              predikatMantap = predikat+" (V) ";
                              break;
                          }
                          else{
                              predikatMantap = predikat+" (X) ";
                          }
                        }
                        //predikatMantap = predikat+" (X) ";
                    }
        }
     return predikatMantap;
    }

    private String matchingObyek(String obyek3, String predikat1) {
        //untuk setiap mencari metodenya di for lagi 
        System.out.println("INI UNTUK YANG NYARI PERSAMAANNYA"+obyek3+" "+predikat1);
        ArrayList<Metode> simpanMetodePredikat = new ArrayList<Metode>();
        Similiarity sml = new Similiarity();
        String obyekMantap=null;
        //masukkan dulu ke array penyimpanan
        for(int x=0;x<isiMetode.size();x++){
            if(isiMetode.get(x).getPemilik().equalsIgnoreCase(predikat1)){
                //disini disimpan daftar metode yang didalam predikatnya 
                System.out.println("Simpan");
                simpanMetodePredikat.add(isiMetode.get(x));
            }
        }
        //ini untuk mengetest daftar simpanan array yang diatas
//        System.out.println(predikat1);
//        for(int x=0;x<simpanMetodePredikat.size();x++){
//            System.out.println(simpanMetodePredikat.get(x).getNamaMetode());
//        }
        //melakukan deteksi tiap index, lalu di compute
        
        //dari daftar metode predikat diatas diolah disini
        outerloop:
        for(int x=0;x<isiMetode.size();x++){
                String metodenyaPemilikPredikat = isiMetode.get(x).getPemilik();
                String metodenyaPredikat = isiMetode.get(x).getNamaMetode();
                System.out.println(metodenyaPemilikPredikat+" BBBB "+metodenyaPredikat);
                //seleksi index array
                if(isiMetode.get(x).getPemilik().equalsIgnoreCase(predikat1)){
                    //melakukan penyamaan index yang sudah diseleksi diatas, disamakan nama metodenya
                    //ini melakukan pengecekkan array sekarang  
                    if(metodenyaPredikat.equalsIgnoreCase(obyek3)){ 
                          System.out.println("Masuk if pertama");
                          obyekMantap = obyek3+" (V) ";  
                          System.out.println(metodenyaPredikat+" ISI DARI METODE setiap predikat");
                          break;
                    }
                    else{
                          //melakukan similarity obyek dengan list metode didalam array metode yang disimpan itu
                          double resultCompute;
                          System.out.println(metodenyaPredikat+"isi predikat");
                          System.out.println("MASUK YANG SIMILARITY");    
                          //kalimat satu itu yang sama , dengan yang diiterasi setiap array
                          hasilString1 = sml.splitCamelCaseString(obyek3);
                          String kalimatSatu = sml.combineText(hasilString1);
                          String[] words1 = kalimatSatu.split(" ");
                          //hasilString2 = sml.splitCamelCaseString(metodenyaPredikat);  
                          //disini mencari compare di setiap iterasi for untuk mendeteksi setiap isi metode didalam obyek
                          
                          for(int y=0;x<simpanMetodePredikat.size();x++){
                              //melakukan compute disini setiap iterasi simpan metode dengan obyek3  
                              //lalu apabila diiterasinya ditemukan yang sesuai / yang melebihi tresshold langsung disimpan dan break;
                              String namaMetodePredikat = simpanMetodePredikat.get(x).getNamaMetode();
                              hasilString2 = sml.splitCamelCaseString(namaMetodePredikat);
                              String kalimatDua = sml.combineText(hasilString2);
                              String[] words2 = kalimatDua.split(" ");
                              //Melakukan compute untuk mengetahui hasil similarity
                              double hasil = sml.compute(words1,words2);
                              System.out.println(hasil+"HASILNYAAAAAAAAAAA");
                               //melakukan if jika hasilnya lebih baik daripada tresshold maka langsung obyekmantap V dan break
                              if(hasil>0.35){
                                  //membenarkan obyekmantap lalu di break
                                  //karena tidak ada break maka yang setname dkk tidak di centang
                                  //coba disimpan didalam array hasilnya lalu dicari dibawah
                                  obyekMantap = obyek3+" (V) ";
                                  break outerloop;
                              }
                          }
                      }
                    }
                  else{
                    
                      System.out.println("Masuk disini OBYEK YANG SALAH SEKALI");
                           obyekMantap = obyek3+" (X) ";
                  }
             }
        return obyekMantap;
    }

    private void checkActor(String subyek) {
            for(int x=0;x<isiActor.size();x++){
                if(subyek.equalsIgnoreCase(isiActor.get(x))){
                    setActor = true;
                }
                else{
                    setActor = false;
                }
            }
    }
    
    public ArrayList<String> getHasil(){
        return this.hasilMatchingnya;
    }
}
