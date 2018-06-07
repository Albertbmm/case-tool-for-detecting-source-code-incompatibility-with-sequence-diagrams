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
public class Indent {
    private String isiIndent;
    private String pemilikIndent;

    public Indent(String isiIndent) {
        this.isiIndent = isiIndent;
    }

    public Indent() {
        this.isiIndent = isiIndent;
    }
    public String getIndent(){
        return isiIndent;
    }
    
    public void setIndent(String isiIndent){
        this.isiIndent = isiIndent;
    }
    public void setPemilikIndent(String pemilik){
        this.pemilikIndent = pemilik;
    }
    public String getPemilik(){
        return this.pemilikIndent;
    }
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("[");
        stringBuilder.append(isiIndent);
        stringBuilder.append(" ");
        stringBuilder.append(pemilikIndent);
        stringBuilder.append("]");

        return stringBuilder.toString();
    }
}
