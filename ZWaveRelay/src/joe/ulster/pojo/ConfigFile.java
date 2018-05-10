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

package joe.ulster.pojo;

/**
 *
 * @author Joe Rafferty - Ulster University
 */
public class ConfigFile {
    
    public String instanceID = "";
    public String piAddr = "";
    public String piUsn = "";
    public String piPw = "";

    public String scAddr = "";
    public String scApi = "";
    
    
    public ConfigFile(){
        
        instanceID = "A unique Id for this instance, such as: X8jZrsOuSaDs";
        piAddr = "A zwave server endpoint such as: http://127.0.0.1:8083";
        piUsn = "A username USERNAME";
        piPw = "A passsword PASSWORD";

        scAddr = "A SC REST Endpoint such as: https://HOSTSERVER.EXAMPLE/SensorCentral/REST/";
        scApi = "A Sensor Central  API KEY";
        
    }
    
}
