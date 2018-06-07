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
public class PackagedElement {
    private String xmiType;
    private String idPackageElement;
    private String namePackage;
    public PackagedElement(){
        
    }
    public void setPackagedElement(String xmiType,String idPackageElement,String namePackage){
        this.xmiType = xmiType;
        this.idPackageElement = idPackageElement;
        this.namePackage = namePackage;
    }
    public String getId(){
        return this.idPackageElement;
    }
    public String getName(){
        return this.namePackage;
    }
    public String getType(){
        return this.xmiType;
    }
    @Override
     public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(xmiType).append(".").append(idPackageElement).append(".").append(namePackage);
        return stringBuilder.toString();
    }
}
