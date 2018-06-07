/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResourceManager;

import java.util.ArrayList;
import java.util.LinkedList;
import edu.cmu.lti.lexical_db.ILexicalDatabase;
import edu.cmu.lti.lexical_db.NictWordNet;
import edu.cmu.lti.ws4j.RelatednessCalculator;
import edu.cmu.lti.ws4j.impl.HirstStOnge;
import edu.cmu.lti.ws4j.impl.JiangConrath;
import edu.cmu.lti.ws4j.impl.LeacockChodorow;
import edu.cmu.lti.ws4j.impl.Lesk;
import edu.cmu.lti.ws4j.impl.Lin;
import edu.cmu.lti.ws4j.impl.Path;
import edu.cmu.lti.ws4j.impl.Resnik;
import edu.cmu.lti.ws4j.impl.WuPalmer;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author User
 */
public class Similiarity {
    //pre process
     public ArrayList<String> splitCamelCaseString(String s){
    ArrayList<String> result = new ArrayList<String>();	
    for (String w : s.split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])")) {
    	result.add(w);
    }    
    return result;}
    
     public String combineText(ArrayList<String> s){
         StringBuilder sb = new StringBuilder();
         String hasil;
          for(int i=0;i<s.size();i++)
          {
              sb.append(s.get(i));
              sb.append(" ");
          }
          hasil = sb.toString();
         return hasil;
     }
     
     
     //Perhitungan Similiarity Wordnet Wu-Palmer
    private static ILexicalDatabase db = new NictWordNet();
    public double[][] getSimilarityMatrix( String[] words1, String[] words2, RelatednessCalculator rc ){
    double[][] result = new double[words1.length][words2.length];
    for ( int i=0; i<words1.length; i++ ){
        for ( int j=0; j<words2.length; j++ ) {
            double score = rc.calcRelatednessOfWords(words1[i], words2[j]);
            result[i][j] = score;
               if(result[i][j]>1.00){
                    result[i][j] = 1.00;
                }           
          }
        }
    return result;}
    
    //menghitung similiaritynya
   public double compute (String[] words1, String[] words2)
  {
    int penandaSatu=0;  
    int penandaDua=0;
    double hasil;
    double[] firstValue = new double[10];
    double[] secondValue = new double[10];
    List<Double> list = new ArrayList<Double>();  
    double value;
    double[] nums = new double[2];
    System.out.println("WuPalmer");
    RelatednessCalculator rc1 = new WuPalmer(db);
       {
        double[][] s1 = getSimilarityMatrix(words1, words2,rc1);     
        for(int i=0; i<words1.length; i++){
            for(int j=0; j< words2.length; j++){
                System.out.println(words1[i]+" "+words2[j]+" "+s1[i][j]);
                if(i%2==1){
                    //diisi ke dalam variable double A
                    double hasilSimpan = s1[i][j];
                    firstValue[penandaSatu] = hasilSimpan;
                    System.out.println("A "+s1[i][j]);
                    penandaSatu++;
                }
                else{
                    //diisi ke dalam variable double b
                    double hasilSimpanDua = s1[i][j];
                    secondValue[penandaDua] = hasilSimpanDua;
                    System.out.println("B "+s1[i][j]);
                    penandaDua++;
                }
        }
       }
        hasil = Averagearray(s1);
        //hasil = cosineSimilarity(firstValue,secondValue);
        System.out.println("ini hasilnya"+" "+hasil);
        if(hasil>0.45){
            System.out.println("benar implement");
        }
       }
       return hasil;
  }
    
    //menghitung rata" dari hasil wordnet
    public double Averagearray(double[][] array) {
        double total=0;
        int totallength=0;
                for(int i=0;i<array.length;i++) {
                for(int j=0;j<array[i].length;j++) {
                    total+=array[i][j];
                    totallength++;
                }
            }
        return total/(totallength);}
    
    public static double cosineSimilarity(double[] vectorA, double[] vectorB) {
    //melakukan perhitungan dari vector A yang didapat dari hasil [i] dan vector B yang didapat dari hasil [j]
        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;
                for (int i = 0; i < vectorA.length; i++) {
                dotProduct += vectorA[i] * vectorB[i];
                normA += Math.pow(vectorA[i], 2);
                normB += Math.pow(vectorB[i], 2);
                }
        double hasilAkhir = dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
}
}
