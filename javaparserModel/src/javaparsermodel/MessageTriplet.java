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
public class MessageTriplet {
    private String method;
    private String IdMessage;
    private String CoveredId;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getIdMessage() {
        return IdMessage;
    }

    public void setIdMessage(String IdMessage) {
        if(IdMessage!=null){
          this.IdMessage = IdMessage;   
        }
        else {
            this.IdMessage = "start Point/end point";
        }
    }

    public String getCoveredId() {
        return CoveredId;
    }

    public void setCoveredId(String CoveredId) {
        if(CoveredId!=null){
          this.CoveredId = CoveredId;    
        }
        else{
            this.CoveredId = "start Point/end point";
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("[").append(method).append(", ");
        stringBuilder.append(IdMessage).append(", ").append(CoveredId);
        stringBuilder.append("]");

        return stringBuilder.toString();
    }
    
}
