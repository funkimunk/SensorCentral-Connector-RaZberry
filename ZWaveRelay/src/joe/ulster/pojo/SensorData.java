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
public class SensorData {
    
    
    /*
    Example from LoRa WAN GPS tracker
    
    {"blobJson":"{\"params\": {\"dev_eui\": \"3331383277356705\", \"rx_time\": 1520944776.445303, \"encrypted_payload\": \"AAAAAAAAAAAAAAg==\"}, \"jsonrpc\": \"2.0\", \"id\": \"1e4efb09f686\", 
    \"method\": \"uplink\"}",
    "deviceMfg":100,"eventCode":101,
    "sensorClass":100,"sensorUUID":"3331383277356705",
    "timeStamp":1.52097718393799987E18,"uID":"3331383277356705_100_100"}*/

    
    public String blobJson  ="";
    public int eventCode = -1;
    public int sensorClass = -1;
    public int deviceMfg = -1;
    public String sensorUUID = "";
    public double timeStamp = 0;
    public String uID = "";
    
    
    
    
}
