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
public class OwnedAttributes {
    private String xmiIdAttr;
    private String idTypePackageElement;
    
    public OwnedAttributes(){
        
    }
    public void setOwnedAttributes(String xmiIdAttr,String idTypePackageElement){
        this.xmiIdAttr = xmiIdAttr;
        this.idTypePackageElement = idTypePackageElement;
    }
    public String getIdOwnedAttributes(){
        return this.xmiIdAttr;
    }
    public String getIdTypePackageElement(){
        return this.idTypePackageElement;
    }
    
    @Override
     public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("id untuk id owned Attribute: ").append(xmiIdAttr).append("id untuk type Package: ").append(idTypePackageElement);
        return stringBuilder.toString();
    }
}
