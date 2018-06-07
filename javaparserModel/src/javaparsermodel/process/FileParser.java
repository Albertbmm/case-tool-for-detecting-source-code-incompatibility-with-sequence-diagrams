package javaparserModel.process;


import data.SequenceDiagram;
import data.Triplet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import javaparserModel.Account;
import javaparsermodel.FragmentTemp;
import javaparsermodel.Lifelinetemp;
import javaparsermodel.message;
import javaparsermodel.fragment;
import javaparsermodel.triplet;
import javaparsermodel.MessageTriplet;
import javaparsermodel.OwnedAttributes;
import javaparsermodel.PackagedElement;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * BUTUH DI EDIT
 * @author USER
 */
public class FileParser extends DefaultHandler{

       private Account acct;
       private message msgg;
       private String temp;
       private fragment fgmm;
       //private Lifelinetemp tempLifeline;
       private triplet triplet;
       private PackagedElement packageElement = new PackagedElement();
       private OwnedAttributes ownedAttr = new OwnedAttributes();
       FragmentTemp tempFragment = new FragmentTemp();
       Lifelinetemp tempLifeline = new Lifelinetemp(); 
       private MessageTriplet msgtriplet;
       private ArrayList<FragmentTemp> listFragmentTemp = new ArrayList<FragmentTemp>();
       private ArrayList<Lifelinetemp> listLifelineTemp = new ArrayList<Lifelinetemp>();
       private ArrayList<MessageTriplet> listmsgtriplet = new ArrayList<MessageTriplet>();
       private ArrayList<Account> accList = new ArrayList<Account>();
       private ArrayList<message> msg = new ArrayList<message>();
       private ArrayList<fragment> fgm = new ArrayList<fragment>();
       private ArrayList<triplet> trp = new ArrayList<triplet>();
       private ArrayList<PackagedElement> pkgElement = new ArrayList<PackagedElement>();
       private ArrayList<OwnedAttributes> owdElement = new ArrayList<OwnedAttributes>();
       private ArrayList<triplet> tripletku;
       //untuk menyimpan actor
       private ArrayList<String> simpanActor = new ArrayList<String>();
       private SequenceDiagram sequenceData = new SequenceDiagram();
       
       //private ArrayList<triplet> trpSC = new ArrayList<triplet>();
       private SAXParser sp;    

       
       
    public static void main(String[] args) throws IOException, SAXException,ParserConfigurationException, org.xml.sax.SAXException 
    {
              //Create an instance of this class; it defines all the handler methods
              FileParser fp = new FileParser();
              fp.parseFile("src/xml/Untitled.xmi");
              //fp.parseFile("src/xml/contoh1.xmi");
              fp.creatinTriplet();
              fp.getActor();
              fp.readList();
              
    }
    
    public FileParser() throws IOException, SAXException,ParserConfigurationException, org.xml.sax.SAXException  {
            SAXParserFactory spfac = SAXParserFactory.newInstance();
             sp = spfac.newSAXParser();
             tripletku = new ArrayList<triplet>();
    }
    
    public void parseFile(String filename) throws SAXException,IOException {
                      //Finally, tell the parser to parse the input and notify the handler        
        sp.parse(filename, this);               
    }
    
    
    public void characters(char[] buffer, int start, int length) {
              temp = new String(buffer, start, length);
    }
    
    public void startElement(String uri, String localName,String qName, Attributes attributes) throws SAXException {
              temp = "";
              if (qName.equalsIgnoreCase("lifeline")) {
                     acct = new Account();
                     String packageName = attributes.getValue("name");
                     String packageType = attributes.getValue("xmi:type");
                     String PackageID = attributes.getValue("xmi:id");
                     String representId = attributes.getValue("represents");
                     acct.setRepresent(representId);
                     acct.setType(packageType);
                     acct.setName(packageName);
                     acct.setId(PackageID);
                    
              }
              else if (qName.equalsIgnoreCase("message")) {
                     msgtriplet = new MessageTriplet();
                     String messageName = attributes.getValue("name");
                     String receiveEvent = attributes.getValue("receiveEvent");
                     String sendEvent = attributes.getValue("sendEvent");
                     msgtriplet.setMethod(messageName);
                     msgtriplet.setIdMessage(receiveEvent);
                     msgtriplet.setCoveredId(sendEvent);
                    
              }
               else if (qName.equalsIgnoreCase("fragment")) {
                    fgmm = new fragment();
                    String messageEvent = attributes.getValue("xmi:id");
                    String lifelineCovered = attributes.getValue("covered");
                    fgmm.setId(messageEvent);
                    fgmm.setCovered(lifelineCovered);
                    
              }
               else if(qName.equalsIgnoreCase("ownedAttribute")){
                   ownedAttr = new OwnedAttributes();
                   String xmIdOwned = attributes.getValue("xmi:id");
                   String idPackageOwned = attributes.getValue("type");
                   ownedAttr.setOwnedAttributes(xmIdOwned, idPackageOwned);
               }
               else if(qName.equalsIgnoreCase("packagedElement")){
                   //untuk mengambil daftar actor lalu masukkan ke fungsi get actor
                   packageElement = new PackagedElement();
                   String idFragment = attributes.getValue("xmi:id"); 
                   String tipeUML = attributes.getValue("xmi:type");
                   String namaPackage = attributes.getValue("name");
                   packageElement.setPackagedElement(tipeUML,idFragment, namaPackage);
               }
              
       }
     
    public void endElement(String uri, String localName, String qName)throws SAXException {

            if (qName.equalsIgnoreCase("lifeline")) {
                     // add it to the list
                     accList.add(acct);
              }
            else if (qName.equalsIgnoreCase("message")) {
                     // add it to the list
                     listmsgtriplet.add(msgtriplet);
                     msg.add(msgg);
                     
              }
             else if (qName.equalsIgnoreCase("fragment")) {
                     // add it to the list
                     fgm.add(fgmm);
              }
             else if(qName.equalsIgnoreCase("ownedAttribute")){
                 owdElement.add(ownedAttr);
             }
             else if(qName.equalsIgnoreCase("packagedElement")){
                 pkgElement.add(packageElement);
             }
      
               
       }
    
    public void creatinTriplet(){
       String Subyek;
       String predikat;
       String Obyek;
       int size = listmsgtriplet.size();
       System.out.println(size);
       for(int i= 0;i<size;i++)
       {
           String isiObyek = listmsgtriplet.get(i).getIdMessage().toString();
            //System.out.println(listmsgtriplet.get(i));
            triplet tpl = new triplet();  
            tripletku.add(tpl); 
            predikat = listmsgtriplet.get(i).getMethod().toString();
            Subyek = getSubyek(listmsgtriplet.get(i).getCoveredId().toString());
            Obyek = getObyek(isiObyek);
            //System.out.println(Subyek+" "+predikat+" "+Obyek);
          
            //masukkan ke arraylist triplet yang sudah ada di dalam kelas Triplet
            Triplet tripletData = new Triplet(Subyek,Obyek,predikat);
            
            sequenceData.insertTriplet(tripletData);
            
            tpl.setObyek(Obyek);
            tpl.setPredikat(predikat);
            tpl.setSubyek(Subyek);
            tripletku.add(tpl);
      }   
            
   }
    public void setSequenceData(SequenceDiagram sequenceData){
        this.sequenceData = sequenceData;
    }
    public SequenceDiagram getSequence(){
        return this.sequenceData;
    }
    public ArrayList<String> getListActor(){
        return this.simpanActor;
    }
    public void readList() throws IOException, SAXException, ParserConfigurationException {
//         ArrayList<Triplet> ada = new ArrayList<Triplet>();
//         sequenceData.checkIsiTriplet();
//         ada = sequenceData.getIsiTriplet(); 
//         System.out.println("ini banyak triplet "+ada.size());
//         System.out.println();
//           sequenceData.checkIsiTriplet();  
           simpanActor.removeAll(Collections.singleton(null));
           System.out.println(simpanActor.size());
           for(int x=0;x<simpanActor.size();x++){
               System.out.println(simpanActor.get(x));
           }
      // disini keluar 
//       ArrayList<Triplet> ada = new ArrayList<Triplet>();
//       ada = sequenceData.getListTriplet(); 
//       System.out.println(ada.size());
//       System.out.println();
//        for(int x=0;x<pkgElement.size();x++){
//            System.out.println(pkgElement.get(x).toString());
//        }
//        System.out.println();
//        for(int x=0;x<owdElement.size();x++){
//            System.out.println(owdElement.get(x).toString());
//        }
       
    }
     private  String getSubyek(String subyek) {
        int size = accList.size(); 
        int sizefragment = fgm.size();
        String idFragment;
        String coveredId;
        int sizeMessageTriplet = listmsgtriplet.size();
            for(int y=0;y<sizeMessageTriplet;y++){
               for(int x=0;x<sizefragment;x++){
                idFragment = fgm.get(x).getId().toString();
                coveredId = fgm.get(x).getCovered().toString();
                tempFragment.setIdFragment(idFragment);
                tempFragment.setIdCovered(coveredId);
                listFragmentTemp.add(tempFragment);
                    for(int i=0;i<size;i++){
                      String idLifeline = accList.get(i).getId().toString();
                      //String namaLifeline = accList.get(i).getName().toString();
                      String idRepresentLifeline = accList.get(i).getRepresent().toString();
                      tempLifeline.setId(idLifeline);
                      tempLifeline.setRepresent(idRepresentLifeline);
                      //tempLifeline.setName(namaLifeline);
                      listLifelineTemp.add(tempLifeline);
                         if(subyek.equals(listFragmentTemp.get(x).getIdFragment().toString()))
                           {
                              if(listFragmentTemp.get(x).getIdCovered().toString().equals(listLifelineTemp.get(i).getId().toString()))
                                 {
                                     //sudah dapat idCoveredFragment
                                     for(int j=0;j<owdElement.size();j++){
                                         String idOwnedAttribute = owdElement.get(j).getIdOwnedAttributes();
                                         if(idRepresentLifeline.equals(idOwnedAttribute)){
                                             String idTypeOwdElement = owdElement.get(j).getIdTypePackageElement();
                                             for(int h=0;h<pkgElement.size();h++){
                                                 String idPkgElement = pkgElement.get(h).getId();
                                                 if(idTypeOwdElement.equals(idPkgElement)){
                                                     subyek = pkgElement.get(h).getName();
                                                 }
                                             }
                                         }
                                     }
                                   
                                  }
                            }
                                         
                    }     
            }
        }
        return subyek;    
    }
    private String getObyek(String obyek) {
        int size = accList.size(); 
        int sizefragment = fgm.size();
        int sizeMessageTriplet = listmsgtriplet.size();
        String idFragment;
        String coveredId;
        
        for(int x=0;x<sizeMessageTriplet;x++)
        {
           for(int y=0;y<sizefragment;y++){
             idFragment = fgm.get(y).getId().toString();
             coveredId = fgm.get(y).getCovered().toString();
             tempFragment.setIdFragment(idFragment);
             tempFragment.setIdCovered(coveredId);
             listFragmentTemp.add(tempFragment);               
                for(int i=0;i<size;i++){
                    String idLifeline = accList.get(i).getId().toString();
                    //String namaLifeline = accList.get(i).getName().toString();
                    //String idRepresentLifeline = accList.get(i).getRepresent().toString();
                    tempLifeline.setId(idLifeline);
                    //tempLifeline.setName(namaLifeline);
                    //tempLifeline.setRepresent(idRepresentLifeline);
                    listLifelineTemp.add(tempLifeline);
                        if(obyek.equals(listFragmentTemp.get(y).getIdFragment().toString())){
                            if(listFragmentTemp.get(y).getIdCovered().toString().equals(listLifelineTemp.get(i).getId().toString())){
                                //represent udah dapat langsung loop untuk yang di dalam owned attribut 
                                String idRepresentLifeline = accList.get(i).getRepresent().toString();
                                for(int j=0;j<owdElement.size();j++){
                                   String idOwned = owdElement.get(j).getIdOwnedAttributes();
                                   if(idRepresentLifeline.equals(idOwned)){
                                       //mengambil id type
                                       String idTypeOwdElement = owdElement.get(j).getIdTypePackageElement();
                                        //cocokkan id type dengan yang ada di package elemen
                                        for(int h=0;h<pkgElement.size();h++){
                                            String idPackagedElement = pkgElement.get(h).getId();
                                            if(idTypeOwdElement.equals(idPackagedElement)){
                                                 obyek = pkgElement.get(h).getName();
                                            }
                                        }
                                   }
                                }
                                //obyek = listLifelineTemp.get(i).getName().toString();             
                            }
                          }
                }
          }
        }
        return obyek;    
    }

    public void getActor() {
        //salah logic harusnya yang di cek itu UMLtype sama dengan actor kalo ada baru cari 
        //fungsi untuk mencari aktor sudah selesai tinggal dimasukkan ke dalam arraylist, lalu fungsinya langsung di taruh disini saja 
        //tidak usah memakai pemanggilan buildListActor
        String actor="";
        //pkgElement = new ArrayList<PackagedElement>();
        for(int x=0;x<pkgElement.size();x++){
             String actorType = pkgElement.get(x).getType();
             if(actorType.equalsIgnoreCase("uml:Actor")){
                 actor = buildListActor(pkgElement.get(x).getId().toString());
                 simpanActor.add(actor);
             }
             else{
                 System.out.println("tidak ditemukan actor");
             }
             //simpanActor.add(actor);
             //System.out.println(actor);
        }
       
       
    }

    private String buildListActor(String actor) {
        String simpanIdOwned = null;
        String hasil = null;
        String hasilAkhir = null;
        //cari nama aktor yang ada melalui dari hasil string actor(id dari package) dicocokkan ke dalam owned attribute lalu ke lifeline yang sesuai id nya
        for(int x=0;x<owdElement.size();x++){
            simpanIdOwned = owdElement.get(x).getIdTypePackageElement();
            //owdElement memilikki typeid yang sama dengan package elemen jadi typeID = actor 
            if(actor.equals(simpanIdOwned)){
                simpanIdOwned = owdElement.get(x).getIdOwnedAttributes();
                 //masuk ke looping untuk fragment mencocokkan idOwned attribute ,idOwnedAttribute = idLifeline
                 for(int y=0;y<accList.size();y++)
                 {
                     hasil = accList.get(y).getRepresent();
                     //System.out.println(hasil);
                     //if nya harusnya bukan ke id dari lifeline tetapi represent dari lifeline
                     if(simpanIdOwned.equals(hasil)){
                         //handle untuk getname nya
                         //harusnnya  langsung mencari seperti di subyek dan obyek
                         for(int i=0;i<owdElement.size();i++){
                             String idOwdElement = owdElement.get(i).getIdOwnedAttributes();
                             if(hasil.equals(idOwdElement)){
                                 String idTypeOwd = owdElement.get(i).getIdTypePackageElement();
                                 for(int j=0;j<pkgElement.size();j++){
                                     String idPkgElement = pkgElement.get(j).getId();
                                     if(idTypeOwd.equals(idPkgElement)){
                                         hasilAkhir = pkgElement.get(j).getName();
                                         break;
                                     }
                                 }
                             }
                         }
                     }
                 }
            }
        }
        return hasilAkhir;
    }
       
}

