package javaparsermodel.process;

import org.xml.sax.*;
import org.xml.sax.helpers.*;
import java.io.*;
import java.util.*;
import javaparsermodel.InisiasiCall;
import javaparsermodel.InisiatorCall;
import javaparsermodel.Method;
import javaparsermodel.MethodPublic;
import javaparsermodel.NamaClass;
import javaparsermodel.PemanggilanMethod;
import data.*;
import javaparsermodel.Indent;
import javax.swing.JOptionPane;

public class CodeParserStack extends DefaultHandler {
    
   private Stack tagStack = new Stack();
   // Local list of Method call...
   private ArrayList<Method> methodCode= new ArrayList<Method>();
   private ArrayList<InisiasiCall> initCode = new ArrayList<InisiasiCall>();
   private ArrayList<InisiatorCall>inisiasiCode = new ArrayList<InisiatorCall>();
   private ArrayList<InisiasiCall> initCodePublik = new ArrayList<InisiasiCall>();
   private ArrayList<InisiatorCall>inisiasiCodePublik = new ArrayList<InisiatorCall>();
   private ArrayList<NamaClass> namaClassCode = new ArrayList<NamaClass>();
   private ArrayList<MethodPublic> methodPublicCode = new ArrayList<MethodPublic>();
   private ArrayList<PemanggilanMethod> panggilMethod = new ArrayList<PemanggilanMethod>();
   private ArrayList<String> isiPemanggilanKode = new ArrayList<String>();
   private ArrayList<Indent> isiIndent = new ArrayList<Indent>();
   private ArrayList<Indent> isiIndentDua = new ArrayList<Indent>();
   
   //Declare Variable untuk NamaClass Data Model
   private String isiNamaClass;
   private String isiModifierClass;
   private String isiTipeClass;
   
   //Declare Variabel untuk Method Data Model
   //untuk func yang return
   String typePublicTemp;
   String methodPublicTemp;//modifier
   String namaMethodValue; //untuk yang sifat nya function return
    //untuk yang sifatnya function void
   
   //untuk void
   String namaMethodVoid;
   String tipeVoid;
   //String methodVoidTemp; //apabila modifier nya public void
   String modifierVoid="";//untuk modifier void
   
   //String untuk pemanggilan method dalam class lain 
   String namaMethodCall;
   
   //pemanggilan method nya
   String isiReturn;
   String inisiatorClass;
   
   //pemanggilan pada fungsi return value
   String inisiatoClassPublik;
   String namaMethodCallPublik;
   String isiReturnPublik1;
   String isiReturnPublik2;
   
   //untuk konstruk kelas di dalama konstrukter
   String variable;
   String konstruktor;
   //untuk pemanggilan kode sumber 
   String variablePemanggil;
   String methodDipanggil;
   
   //private Method methodCodedata;
   private InisiasiCall initCodedata;
   //private InisiatorCall inisiasiCodedata = new InisiatorCall();;
   private InisiatorCall inisiasiCodedata;
   private NamaClass namaClassData;
   private Indent identData;
   private MethodPublic methodPublicCodeData;
   private PemanggilanMethod panggilMethodData;
   private CharArrayWriter contents = new CharArrayWriter(); //untuk mengambil char dari xml
   private Proyek proyek = new Proyek();
   private Kelas kelasData = new Kelas();
   private Metode metodeData ; //untuk menyimpan list of kode sumber
   private BarisKodeSumber barisKodeData; //untuk mendeklare struktur data baris kode sumber
   //private Metode methodData = new Metode();
   private Metode methodData;
   private Kelas kelas = new Kelas();
     
   
   public void startElement( String namespaceURI,
               String localName,
              String qName,
              Attributes attr ) throws SAXException {
       contents.reset();
      tagStack.push( localName );
      
    
   }
   public void endElement( String namespaceURI,
               String localName,
              String qName ) throws SAXException {
       
      System.out.println(getTagPath());
      if ( getTagPath().equals( "/unit/CLASS/IDENT" ) ) {
         isiNamaClass = contents.toString().trim();
         String tipeClass = "class";
         kelas.setKelas(isiNamaClass, isiModifierClass, tipeClass);
         proyek.addKelas(kelas);
      }
      
      
      
      if(getTagPath().equals("/unit/CLASS/MODIFIER_LIST/PUBLIC")){
          isiModifierClass = contents.toString().trim();
      }
      //untuk mengambil deklarasi konstruk kelas di dalam kelas konstrukter 
      if(getTagPath().equals("/unit/CLASS/CLASS_TOP_LEVEL_SCOPE/CONSTRUCTOR_DECL/BLOCK_SCOPE/EXPR/ASSIGN/IDENT")){
          variable = contents.toString().trim();
      }
      else if(getTagPath().equals("/unit/CLASS/CLASS_TOP_LEVEL_SCOPE/CONSTRUCTOR_DECL/BLOCK_SCOPE/EXPR/ASSIGN/CLASS_CONSTRUCTOR_CALL/QUALIFIED_TYPE_IDENT/IDENT")){
          konstruktor = contents.toString().trim();
          initCodedata = new InisiasiCall();
          initCodedata.setNamaClassInisiator(variable);
          initCodedata.setNamaMethodCaller(konstruktor);
          initCode.add(initCodedata);
      }
      
      //FUNCTION THAT HAS RETURN VALUE
      if(getTagPath().equals("/unit/CLASS/CLASS_TOP_LEVEL_SCOPE/FUNCTION_METHOD_DECL/MODIFIER_LIST/PUBLIC")){
          methodPublicTemp = contents.toString().trim();
      }
      else if(getTagPath().equals("/unit/CLASS/CLASS_TOP_LEVEL_SCOPE/FUNCTION_METHOD_DECL/TYPE/QUALIFIED_TYPE_IDENT/IDENT")){
          typePublicTemp = contents.toString().trim();
      }
      else if(getTagPath().equals("/unit/CLASS/CLASS_TOP_LEVEL_SCOPE/FUNCTION_METHOD_DECL/IDENT")){
          namaMethodValue = contents.toString().trim();
      } 
      else if(getTagPath().equals("/unit/CLASS/CLASS_TOP_LEVEL_SCOPE/FUNCTION_METHOD_DECL/FORMAL_PARAM_LIST/IDENT")){
         
      }
      //untuk pemanggilan kode sumber(Method call) untuk fungsi dengan return value
      else if(getTagPath().equals("/unit/CLASS/CLASS_TOP_LEVEL_SCOPE/FUNCTION_METHOD_DECL/BLOCK_SCOPE/VAR_DECLARATION/TYPE/QUALIFIED_TYPE_IDENT/IDENT")){
          //untuk mengambil nama funsi yang diambil metodenya 
          inisiatoClassPublik = contents.toString().trim();    
      }
      else if(getTagPath().equals("/unit/CLASS/CLASS_TOP_LEVEL_SCOPE/FUNCTION_METHOD_DECL/BLOCK_SCOPE/VAR_DECLARATION/VAR_DECLARATOR_LIST/VAR_DECLARATOR/IDENT")){
          //untuk mengambil nama variable yang di deklarasikan 
          namaMethodCallPublik = contents.toString().trim();
          //harus di set objek baru 
          initCodedata = new InisiasiCall();
          initCodedata.setNamaMethodCaller(namaMethodCallPublik);
          initCodedata.setNamaClassInisiator(inisiatoClassPublik);
          initCode.add(initCodedata);
          
      }
//      else if(getTagPath().equals("/unit/CLASS/CLASS_TOP_LEVEL_SCOPE/FUNCTION_METHOD_DECL/BLOCK_SCOPE/EXPR/METHOD_CALL/DOT")){
//          //untuk mengambil pemanggilan dengan dot nya
//          inisiasiCodedata = new InisiatorCall();
//          isiReturnPublik1 = contents.toString().trim();
//          inisiasiCodedata.setNamaInisiasi(isiReturnPublik1);
//          inisiasiCodedata.setNamaClassCaller(namaMethodCallPublik);
//          inisiasiCodedata.setPemilikKode(namaMethodValue);
//          inisiasiCode.add(inisiasiCodedata);    
//      }
      if(getTagPath().equals("/unit/CLASS/CLASS_TOP_LEVEL_SCOPE/FUNCTION_METHOD_DECL/BLOCK_SCOPE/EXPR/METHOD_CALL/DOT/IDENT")){
          //untuk mengambil pemanggilan dengan dot nya
          identData = new Indent();
          String isiMethodCall = contents.toString().trim();
          identData.setIndent(isiMethodCall);
          identData.setPemilikIndent(namaMethodValue);
          isiIndent.add(identData); 
      }
      
      else if(getTagPath().equals("/unit/CLASS/CLASS_TOP_LEVEL_SCOPE/FUNCTION_METHOD_DECL/BLOCK_SCOPE/RETURN")){
          //untuk return value nya
          //Metode methodData = new Metode(namaMethodValue,typePublicTemp,methodPublicTemp);
          methodData = new Metode();
          methodData.setMetode(namaMethodValue, typePublicTemp, methodPublicTemp);
          methodData.setPemilikMetode(isiNamaClass);
          kelasData.insertMetode(methodData);
          //kelasData.checkIsiMetode();
          //Set value ke data model
      }
      //FUNGSI VOID 
      if(getTagPath().equals("/unit/CLASS/CLASS_TOP_LEVEL_SCOPE/VOID_METHOD_DECL/MODIFIER_LIST/PUBLIC")){
          modifierVoid = contents.toString().trim();
      }
        
      else if(getTagPath().equals("/unit/CLASS/CLASS_TOP_LEVEL_SCOPE/VOID_METHOD_DECL/IDENT")){
          tipeVoid ="void";
          namaMethodVoid = contents.toString().trim();   
          if(!modifierVoid.isEmpty()){
              methodData = new Metode();
              methodData.setMetode(namaMethodVoid, tipeVoid, modifierVoid);
              methodData.setPemilikMetode(isiNamaClass);
              kelasData.insertMetode(methodData);
          }
          else{
              methodData = new Metode();
              methodData.setMetode(namaMethodVoid, tipeVoid, modifierVoid);
              methodData.setPemilikMetode(isiNamaClass);
              kelasData.insertMetode(methodData);
          }
      }
     
     //VOID-PEMANGGILAN CLASS LAIN DALAM VOID
      if(getTagPath().equals("/unit/CLASS/CLASS_TOP_LEVEL_SCOPE/VOID_METHOD_DECL/BLOCK_SCOPE/VAR_DECLARATION/TYPE/QUALIFIED_TYPE_IDENT/IDENT")){
         initCodedata = new InisiasiCall();
         inisiasiCodedata = new InisiatorCall();
         inisiatorClass = contents.toString().trim();
         System.out.println(inisiatorClass);
      }
      
      if(getTagPath().equals("/unit/CLASS/CLASS_TOP_LEVEL_SCOPE/VOID_METHOD_DECL/BLOCK_SCOPE/VAR_DECLARATION/VAR_DECLARATOR_LIST/VAR_DECLARATOR/IDENT")){
        namaMethodCall = contents.toString().trim();
        System.out.println(namaMethodCall+"ini variable");
//        initCodedata.setNamaMethodCaller(namaMethodCall);
//        initCodedata.setNamaClassInisiator(inisiatorClass);
        initCodedata.setNamaMethodCaller(inisiatorClass);
        initCodedata.setNamaClassInisiator(namaMethodCall);
        initCode.add(initCodedata);
      }
      
//      if ( getTagPath().equals( "/unit/CLASS/CLASS_TOP_LEVEL_SCOPE/VOID_METHOD_DECL/BLOCK_SCOPE/EXPR/METHOD_CALL/DOT" ) ) {
//         inisiasiCodedata = new InisiatorCall();
//         isiReturn =contents.toString().trim();
//         inisiasiCodedata.setNamaClassCaller(namaMethodCall);
//         inisiasiCodedata.setNamaInisiasi(isiReturn);
//         inisiasiCodedata.setPemilikKode(namaMethodVoid);
//         //inisiasiCode.add(inisiasiCodedata);
//      }
      if(getTagPath().equals("/unit/CLASS/CLASS_TOP_LEVEL_SCOPE/VOID_METHOD_DECL/BLOCK_SCOPE/IF/BLOCK_SCOPE/EXPR/METHOD_CALL/DOT/IDENT")){
          identData = new Indent();
          String pemanggilanVoidIF = contents.toString().trim();
          identData.setIndent(pemanggilanVoidIF);
          identData.setPemilikIndent(namaMethodVoid);
          isiIndent.add(identData);
      }
      if(getTagPath().equals("/unit/CLASS/CLASS_TOP_LEVEL_SCOPE/VOID_METHOD_DECL/BLOCK_SCOPE/EXPR/METHOD_CALL/DOT/IDENT")){
          identData = new Indent();
          methodDipanggil = contents.toString().trim();
          identData.setIndent(methodDipanggil);
          identData.setPemilikIndent(namaMethodVoid);
          isiIndent.add(identData);
          //System.out.println(methodDipanggil);
      }
      // clean up the stack...
      tagStack.pop();
   }
   public void characters( char[] ch, int start, int length )
                  throws SAXException {
      contents.write( ch, start, length );
   }

   
   private String getTagPath( ){
      String buffer = "";
      Enumeration e = tagStack.elements();
      while( e.hasMoreElements()){
               buffer  = buffer + "/" + (String) e.nextElement();
                             
      }
      return buffer;
   }

   public void getClassMethodCaller(){
       //panggilMethodData = new PemanggilanMethod();
       metodeData = new Metode();     
       int sizeInitCode = initCode.size();
       int sizeInisiasiCode = inisiasiCode.size();
       //membuat fungsi pemanggilan yang baru
       //dicari setiap pemanggilan kode
       System.out.println(sizeInitCode+" "+sizeInisiasiCode);
       for(int x=0;x<sizeInisiasiCode;x++){
           String namaClassTemp = inisiasiCode.get(x).getNamaClassCaller();
           String namaInisiasiTemp = inisiasiCode.get(x).getNamaInisiasi();
           String namaPemilik = inisiasiCode.get(x).getNamaPemilik();
           System.out.println("isi dari inisasiCode"+namaClassTemp +" "+namaInisiasiTemp);
           for(int y=0;y<sizeInitCode;y++){
               String namaClassInisiator = initCode.get(y).getNamaClassInisiator();
               String namaMethodCaller = initCode.get(y).getNamaMethodCaller();
               System.out.println(namaClassInisiator+" "+namaMethodCaller);
               if(namaClassTemp.equalsIgnoreCase(namaClassInisiator)){
                   String simpanNamaClass = namaMethodCaller;
                   String simpanNamaMetodeClass = namaInisiasiTemp;
                   barisKodeData = new BarisKodeSumber();
                   barisKodeData.setBarisKodeSumber(simpanNamaClass, simpanNamaMetodeClass);
                   barisKodeData.setPemilik(namaPemilik);
                   metodeData.insertBarisKode(barisKodeData);
               }
           }
       }
   }
   
    public void buildPemanggilan() {
        //membentuk pemanggilan kode sumer 
//        System.out.println(isiIndent.size());
//        for(int x=0;x<isiIndent.size();x++){
//            System.out.println(isiIndent.get(x).getIndent());
//        }
   
//        for(int x=0;x<isiIndent.size();x+=2){
//              //System.out.println(isiIndent.get(x).toString());
//              String isiSatu = isiIndent.get(x).getIndent();
//              String isiDua = isiIndent.get(x+1).getIndent();
//              String isiPemilik = isiIndent.get(x).getPemilik();
//              inisiasiCodedata = new InisiatorCall();
//              inisiasiCodedata.setNamaClassCaller(isiSatu);
//              inisiasiCodedata.setNamaInisiasi(isiDua);
//              inisiasiCodedata.setPemilikKode(isiPemilik);
//              inisiasiCode.add(inisiasiCodedata);
//          }
        try{
              for(int x=0;x<isiIndent.size();x+=2){
              String isiSatu = isiIndent.get(x).getIndent();
              String isiDua = isiIndent.get(x+1).getIndent();
              System.out.println("tes"+isiSatu+" "+isiDua);
              String isiPemilik = isiIndent.get(x).getPemilik();
              inisiasiCodedata = new InisiatorCall();
              inisiasiCodedata.setNamaClassCaller(isiSatu);
              inisiasiCodedata.setNamaInisiasi(isiDua);
              inisiasiCodedata.setPemilikKode(isiPemilik);
              inisiasiCode.add(inisiasiCodedata);}   
        }catch(Exception e){
            System.out.println("error ditangkap");
            JOptionPane.showMessageDialog(null, "Cek kembali XML source code, pastikan tidak ada pemanggilan yang terlewatkan", "Method Call error", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    
   public void readList(){
//        for(int x=0;x<initCode.size();x++){
//            System.out.println(initCode.get(x).getNamaClassInisiator()+" "+initCode.get(x).getNamaMethodCaller());
//        }
//            //untuk melakukan tes pemanggilan kode yang dibentuk dari perulangan diatas  
//        System.out.println("tes");
//        for(int x=0;x<inisiasiCode.size();x++){
//            
//            System.out.println(inisiasiCode.get(x).getNamaClassCaller()+" "+inisiasiCode.get(x).getNamaInisiasi());
//        }     
//       

          //isi pemanggilan kode
//          for(int x=0;x<isiIndent.size();x++){
//              System.out.println(isiIndent.get(x).toString());
//          }

        //dibawah ini untuk mengetes hasil yang dari kelas,metode,dan baris koe

       System.out.println();
       ArrayList<Kelas> isiKelas = new ArrayList<Kelas>();
       isiKelas = proyek.getKelas();
       for(int x=0;x<isiKelas.size();x++){
           System.out.println(isiKelas.get(x).toString());
       }
       ArrayList<Metode> isiMetode = new ArrayList<Metode>();
       isiMetode = kelasData.getIsiMetode();
       System.out.println(isiMetode.size());
       
       for(int x=0;x<isiMetode.size();x++){
           System.out.println(isiMetode.get(x).toString() +" "+isiMetode.get(x).getPemilik());
       }
    ArrayList<BarisKodeSumber> isiKodeSumber = new ArrayList<BarisKodeSumber>();
    isiKodeSumber = metodeData.getIsiBarisKode();
    System.out.println(isiKodeSumber.size());
    for(int x=0;x<isiKodeSumber.size();x++){
        System.out.println(isiKodeSumber.get(x).toString()+" "+isiKodeSumber.get(x).getPemilik());
    }
   }
    public void setProject(Proyek proyek) {
       this.proyek = proyek;
       
   }
   public Proyek getProyek() {
       return proyek;
   }
   public Kelas getKelasData(){
       return kelasData;
   }   
   public Metode getMetodeData(){
       return this.methodData;
   }
   public Metode getBarisKode(){
       return this.metodeData;
   }
   
   public static void main( String[] argv ){
      try {
          //masih salah dalam pengambilan variable dari pemanggilan kode. coba di cek ke dalam xml
         XMLReader xr = XMLReaderFactory.createXMLReader();
         Matching mp = new Matching();
         CodeParserStack ex1 = new CodeParserStack();
         xr.setContentHandler( ex1 );
         // Parse the file...
         //xr.parse( new InputSource(new FileReader( "src/xml/Transaction.xml" )) );
         xr.parse("src/xml/filechooser.xml");
        //ex1.getClassMethodCaller();
        ex1.buildPemanggilan();
        ex1.getClassMethodCaller();
        ex1.readList();
      }catch ( Exception e )  {
         e.printStackTrace();
      }
   }

   
}