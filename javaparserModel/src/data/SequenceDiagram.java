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
public class SequenceDiagram {
    //ArrayList<Triplet> tripletSequence=new ArrayList<Triplet>();
    ArrayList<Triplet> tripletSequence;
    public SequenceDiagram(){
        tripletSequence = new ArrayList<Triplet>();
    }
    public void insertTriplet(Triplet triplet){
        
        tripletSequence.add(triplet);
    }
//    public ArrayList<Triplet> getListTriplet(){
//        return tripletSequence;
//    }
    
    public void checkIsiTriplet(){
          for(int x=0;x<tripletSequence.size();x++){
           System.out.println(tripletSequence.get(x).toString());
       }    
    }
    public ArrayList<Triplet> getIsiTriplet(){
       return this.tripletSequence;
    }
}
