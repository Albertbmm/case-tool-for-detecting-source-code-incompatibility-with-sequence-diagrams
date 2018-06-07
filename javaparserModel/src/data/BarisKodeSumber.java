package data;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author User
 */
public class BarisKodeSumber {
    private Kelas kelas;
    private Metode metode;
    private String pemanggil;
    private String dipanggil;
    private String pemilikKodeSumber;
    //untuk membikin tipe data KodeSumber
    public BarisKodeSumber(){
        
    }
//    public BarisKodeSumber(String pemanggil,String dipanggil){
//        this.pemanggil = pemanggil;
//        this.dipanggil = dipanggil;
//    }
    public void setBarisKodeSumber(String pemanggil,String dipanggil){
       this.pemanggil = pemanggil;
       this.dipanggil = dipanggil;
    }
    public void setPemilik(String Pemilik){
        this.pemilikKodeSumber = Pemilik;
    }
    public String getPemilik(){
        return this.pemilikKodeSumber;
    }
    public String getPemanggil(){
        return this.pemanggil;
    }
    public String getDipanggil(){
        return this.dipanggil;
    }
    public void setKelas(Kelas kelas) {
        this.kelas = kelas;
    }
    public void setMetode(Metode metode) {
       this.metode = metode;   
    }
    
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(pemanggil).append(".").append(dipanggil);
        return stringBuilder.toString();
    }
}

