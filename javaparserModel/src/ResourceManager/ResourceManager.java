/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResourceManager;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author USER
 */
public class ResourceManager {
    public static void save(Serializable data,String fileName)throws Exception{
        try(ObjectOutputStream os = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)))){
            os.writeObject(data);
        }
    }
    
    public static Object load(String fileName)throws Exception{
        try(ObjectInputStream is = new ObjectInputStream(Files.newInputStream(Paths.get(fileName)))){
            return is.readObject();
        }
    } 
}