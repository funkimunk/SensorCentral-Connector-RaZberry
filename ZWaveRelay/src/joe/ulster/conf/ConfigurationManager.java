/*
 * Authored by Joe Raffery
 * Copyright 2018 (©) Joe Rafferty
 * 
 * This is dual licensed under the GNU GENERAL PUBLIC LICENSE Version 3 (GPL3) 
 * and an optional commercial license. 
 * 
 * Full text of GPL3 license is available under an accompanying license.txt file
 * and online at: https://www.gnu.org/licenses/gpl-3.0.en.html 
 * 
 */
package joe.ulster.conf;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import joe.ulster.pojo.ConfigFile;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;




/**
 *
 * @author joera
 */
public class ConfigurationManager {
    
    ConfigFile configFile;
    
    public ConfigurationManager(){
        //configFile = new ConfigFile();
    }
    
    //The main readConfFile routine
    public ConfigFile readConfFile()
    {
        configFile = new ConfigFile();
        
        Path source = Paths.get(AppConfig.confFileName);
        
        //Test if the current conf exists
        if(Files.exists(source)){
        
            String fileContent = "";
            Scanner scanner = null;

            try {
                
                scanner = new Scanner(
                    new BufferedReader(
                        new FileReader(AppConfig.confFileName)
                    ));
                
                while (scanner.hasNext()) {
                    fileContent = fileContent + scanner.nextLine();
                }
                
                //Parse the file content
                try{

                    Gson gson = new Gson();
                    configFile = gson.fromJson(fileContent, ConfigFile.class);
                    
                } catch(Exception ex ){
                    
                    System.err.println("Malformed configuration file: " + ex);
                    
                }
                
            } catch (final Exception e) {
                
                System.err.println("Configuration file read/access error: " + e);
                
            } finally {
            
                if (scanner != null) {
                    scanner.close();
                }
                
            }
                        
        }else{
            
            System.err.println("Missing configuration file, please modify the autogenerated file '"+AppConfig.confFileName+"'" );
          
            configFile = new ConfigFile();
            
            //Write out default files
            writeConfFile(configFile);            
        }
        
        
        return configFile;
                
    }
    
    
    //Write the a conf file 
    private void writeConfFile(ConfigFile iConfigFile) {
        
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String fileContent = gson.toJson(iConfigFile);        
        System.out.println(fileContent);
        
        writetextToFile(AppConfig.confFileName , fileContent);
        writetextToFile("SAMPLE_"+AppConfig.confFileName , fileContent);
        
    }
        
    public void writetextToFile(String iFileName, String iFileContent){
     
        BufferedWriter bufferedWriter = null;
        FileWriter fileWriter = null;

        try { //TCF 0

            fileWriter = new FileWriter(iFileName);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(iFileContent);

            System.out.println(iFileName+" - written");

        } catch (Exception e) {
            
            System.err.println("Error writing " + iFileName);
            System.err.println(e);

        } finally {

            try { //TCF 1

                if (bufferedWriter != null){
                    bufferedWriter.close();
                }

                if (fileWriter != null){
                    fileWriter.close();
                }

            } catch (Exception ex) {
                System.err.println("Error closing " + iFileName);
                ex.printStackTrace();

            } //END TCF1
            
        }//END TCF 0
        
    }
    
    //Test the config
    public boolean testConfig(ConfigFile iConfigFile){
    
        Boolean retBool = true;
        
        ConfigFile defaultConfig = new ConfigFile();
         
        if(!iConfigFile.instanceID.toString().trim().equals(defaultConfig.instanceID.toString().trim())){
           retBool = false;
        }else{
            System.err.println("You must change the instance ID - set this to a random value of B64 characters");
        }
        
  
        if(iConfigFile.instanceID.trim().length()>0){
           retBool = false;
        }else{
            System.err.println("You must have a random instance ID - set this to a random value of B64 characters");
        }
        
        defaultConfig = null;
        
        return retBool;
        
    }
    
    //Copy the params from the config file to the global config
    public void applyConfig(ConfigFile iConfigFile){
        
        //this could be casted, if you like potential side effects
        AppConfig.instanceID = iConfigFile.instanceID;
        AppConfig.piAddr = iConfigFile.piAddr;
        AppConfig.piUsn = iConfigFile.piUsn;
        AppConfig.piPw = iConfigFile.piPw;
        AppConfig.scAddr = iConfigFile.scAddr;
        AppConfig.scApi = iConfigFile.scApi;
    
    }


}