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
package joe.ulster;

import joe.ulster.conf.AppConfig;
import joe.ulster.conf.ConfigurationManager;
import joe.ulster.pojo.ConfigFile;
import joe.ulster.processors.ZWaveConnector;

/**
 *
 * @author joera
 */
public class ZWaveRelay {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        
        System.out.println("------------------------------");
        System.out.println(" ZWave -> SensorCentral relay ");
        System.out.println("------------------------------");
        System.out.println("Developed by Ulster University");
        System.out.println("------------------------------");
        
        
        ConfigFile configFile;
        
        //Try to load config file
        ConfigurationManager configurationManager = new ConfigurationManager();
        
        
        //Read file
        configFile = configurationManager.readConfFile();
        
        //Check validity
        if(!configurationManager.testConfig(configFile)){
            System.exit(1);
        }
        
        //
        configurationManager.applyConfig(configFile);
        
        
        
        System.out.println("InstanceID " + AppConfig.instanceID);
        System.out.println("SC instance " + AppConfig.scAddr);
        
        
        ZWaveConnector zConn = new ZWaveConnector();
        zConn.negotiateLogin();
        
         while(true){
            //Loop this
            String deviceJson = zConn.getDeviceData();
            zConn.processDeviceDataJson(deviceJson);
         
            
            try{
               Thread.sleep(500);
            }catch(Exception ex){
            }
             
         
        }
        
        
        
    }
    
}
