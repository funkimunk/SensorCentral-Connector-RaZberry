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
package joe.ulster.processors;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import joe.ulster.conf.AppConfig;
import joe.ulster.pojo.SensorData;
import joe.ulster.pojo.SensorMetadata;

/**
 *
 * @author Joe Rafferty - Ulster University
 */
public class ZWaveConnector {

    
    public void negotiateLogin(){
        

               try{

                //Register sensor
                Gson gson = new Gson();
                
                java.net.URL obj = new java.net.URL(AppConfig.piAddr + "/ZAutomation/api/v1/login");
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                //add reuqest header
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/json");
                
                 con.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                wr.writeBytes("{\"form\":true,\"login\":\""+AppConfig.piUsn+"\",\"password\":\""+AppConfig.piPw+"\",\"keepme\":false,\"default_ui\":1}");
                wr.flush();
                wr.close();        

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                    
                }
                
                AppConfig.cookie = con.getHeaderField("Set-Cookie");
                AppConfig.cookie = AppConfig.cookie.substring(0, AppConfig.cookie.indexOf(';'));
                //System.out.println(AppConfig.cookie );
                in.close();
                
        }catch(Exception e){
        }

        
    }
    
    
    public String getDeviceData(){
        
        String retStr = "";
        
        try{

            java.net.URL obj = new java.net.URL(AppConfig.piAddr + "/ZWaveAPI/Run/devices");
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            //add reuqest header
            con.setRequestMethod("GET");
            con.setRequestProperty("Cookie", AppConfig.cookie); 

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));

            String inputLine;

            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            //System.out.println(response.toString());
            retStr = response.toString();

            in.close();

        }catch(Exception e){
            System.out.println("Get data error "+ e);
        }

        return retStr;
    }
    
    
    public void processDeviceDataJson(String iStr){
        
        JsonParser parser = new JsonParser();
        JsonElement jsonTree = parser.parse(iStr);
        JsonObject rootObj = new JsonObject();
        
        if(jsonTree.isJsonObject()) {
            rootObj = jsonTree.getAsJsonObject();
        }
        
        
        int index = 0;
        
        Set<Entry<String, JsonElement>> entrySet = rootObj.entrySet();
        for(Map.Entry<String,JsonElement> entry : entrySet){
            
            JsonElement currentObj = rootObj.get(entry.getKey()).getAsJsonObject();
            
            try{

                SensorData sensorData = new SensorData();
                sensorData.eventCode = 101;
                
                String blobJson = "";
                String typeString = "";
                int updateTime = -1;      
                
                //boolean firstEnrol = false;
                boolean sendData = false;
                
                //Get binary sensor data
                try{
                    String genericType = currentObj.getAsJsonObject()
                        .getAsJsonObject("instances")
                        .getAsJsonObject("0")
                        .getAsJsonObject("commandClasses")
                        .getAsJsonObject("48")
                        .getAsJsonObject("data")
                        .getAsJsonObject("1")
                        .getAsJsonObject("level")
                        .get("value")
                        .toString();
                        
                    //System.out.println(genericType);
                    
                    //Process sensor data
                    if(genericType.equalsIgnoreCase("false")){
                        sensorData.eventCode = 0;
                    }else{
                        sensorData.eventCode = 1;
                    }
                    
                    blobJson +="\"state\":" + genericType +",";
                    
                }catch(Exception e){
                    
                }

                //Battery Info
                try{
                    String genericType= currentObj.getAsJsonObject()
                        .getAsJsonObject("instances")
                        .getAsJsonObject("0")
                        .getAsJsonObject("commandClasses")
                        .getAsJsonObject("128")
                        .getAsJsonObject("data")
                        .getAsJsonObject("last")
                        .get("value").toString();
                      
                    blobJson +="\"batteryLevel\":" + genericType +",";
                    
                    //System.out.println(genericType);

                }catch(Exception e){
                    
                }
                
                
                
                //Get ID
                try{
                    //JSON
                    String sensorID = currentObj
                            .getAsJsonObject()
                            .get("id").toString();
                
                    //System.out.println(sensorID);
                    sensorData.sensorUUID = sensorID;

                }catch(Exception e){
                     //Else Get INDEX + INCREMENT
                     sensorData.sensorUUID = (index + 1)+"";
                }
                
                
                //Get BasicType
                try{
                    //JSON
                    String sensorID = currentObj
                        .getAsJsonObject()
                        .getAsJsonObject("data")
                        .getAsJsonObject("basicType")
                        .get("value").toString();

                    typeString+= sensorID;
                    //System.out.println(sensorID);

                }catch(Exception e){
                }
                
                //Get genericType
                try{
                    //JSON
                    String sensorID = currentObj
                        .getAsJsonObject()
                        .getAsJsonObject("data")
                        .getAsJsonObject("genericType")
                        .get("value").toString();

                    typeString+= sensorID;
                    
                    //System.out.println(sensorID);

                }catch(Exception e){
                }

                 //Get "manufacturerProductType"
                try{
                    //JSON
                    String sensorID = currentObj
                        .getAsJsonObject()
                        .getAsJsonObject("data")
                        .getAsJsonObject("manufacturerProductType")
                        .get("value").toString();

                    typeString+= sensorID;
                    //System.out.println(sensorID);

                }catch(Exception e){
                }
                
                
                //Get Manufacturer ID
                try{
                    //JSON
                    String sensorID = currentObj
                        .getAsJsonObject()
                        .getAsJsonObject("data")
                        .getAsJsonObject("manufacturerId")
                        .get("value").toString();

                    typeString+= sensorID;

                }catch(Exception e){
                }
                
                //Get last update time
                try{
                    //JSON
                    String temp = currentObj
                        .getAsJsonObject()
                        .getAsJsonObject("data")
                        .getAsJsonObject("lastReceived")
                        .get("updateTime").toString();

                    
                    blobJson +="\"piTime\":" + temp +",";
                    try{
                        updateTime = Integer.parseInt(temp);
                    }catch(Exception e){
                        
                    }

                }catch(Exception e){
                }
                
                
                //System.out.println(typeString);
                
                //Process local typestring to SC compatible one
                switch(typeString){
                    
                    //Binary contact Sensor - Everspring
                    case("432296"):
                        
                        sensorData.deviceMfg = 16;
                        sensorData.sensorClass = 3;
                        
                        break;
                        
                    default:
                        sensorData.deviceMfg = 100;
                        sensorData.sensorClass = 100;
                        break;
                    
                }
                
            
                //Generate Sensor ID
                sensorData.sensorUUID = AppConfig.instanceID + sensorData.sensorUUID ;
                sensorData.uID = sensorData.sensorUUID +"_"+sensorData.sensorClass+"_"+sensorData.deviceMfg;
                
           
                try{
                    int prevTime = AppConfig.devices.get(sensorData.sensorUUID);
                
                    //Do comparison
                    if(updateTime > prevTime){
                        sendData = true;
                    }
                    
                    //System.out.println(prevTime);
                    
                }catch(Exception e){
                    
                    sendData = true;
                    
                    SensorMetadata sensorMetadata = new SensorMetadata();
                            
                    sensorMetadata.UUID = sensorData.uID;
                    sensorMetadata.sensorID = sensorData.sensorUUID;
                    sensorMetadata.associatedEnv = -1;
                    sensorMetadata.deviceMfg = sensorData.deviceMfg;
                    sensorMetadata.sensorClass = sensorData.sensorClass;
                    sensorMetadata.location = "New sensor";
                    sensorMetadata.label = "New sensor";
                            
                    //Enroll sensor
                    (new Thread() {
                        public void run() {

                            try{
                                //Register sensor
                                Gson gson = new Gson();

                                java.net.URL obj = new java.net.URL(AppConfig.scAddr + "SensorMetadata/?token=" + AppConfig.scApi);
                                HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                                //add reuqest header
                                con.setRequestMethod("POST");
                                con.setRequestProperty("Content-Type", "application/json");
                                String writeBody = gson.toJson(sensorMetadata);

                                // Send post request
                                con.setDoOutput(true);
                                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                                wr.writeBytes(writeBody);
                                wr.flush();
                                wr.close();        

                                BufferedReader in = new BufferedReader(
                                        new InputStreamReader(con.getInputStream()));
                                String inputLine;
                                StringBuffer response = new StringBuffer();

                                while ((inputLine = in.readLine()) != null) {
                                    response.append(inputLine);
                                }

                                in.close();

                            }catch(Exception e){
                                System.out.println("REST Upload error "+ e);
                            }
                        }
                    }).start();
                    
                }finally{
                   // System.out.println("Updating");
                    AppConfig.devices.put(sensorData.sensorUUID, updateTime);
                }
                                
                //Send Sensor Data
                if(sendData){
                    
                    //Trim blobJson
                    try{
                        blobJson = blobJson.substring(0, blobJson.length()-1);
                    }catch(Exception ex3){
                        blobJson = "";
                    }
                    sensorData.blobJson =  "{"+blobJson + "}";
                    
                    /*
                    //Test output
                    Gson gson = new Gson();
                    System.out.println(gson.toJson(sensorData));
                    */
                
                    (new Thread() {
                        public void run() {

                            try{


                                //Register sensor
                                Gson gson = new Gson();

                                java.net.URL obj = new java.net.URL(AppConfig.scAddr + "SensorData/?token=" + AppConfig.scApi);
                                HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                                //add reuqest header
                                con.setRequestMethod("POST");
                                con.setRequestProperty("Content-Type", "application/json");
                                String writeBody = gson.toJson(sensorData);

                                // Send post request
                                con.setDoOutput(true);
                                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                                wr.writeBytes(writeBody);
                                wr.flush();
                                wr.close();        

                                BufferedReader in = new BufferedReader(
                                        new InputStreamReader(con.getInputStream()));
                                String inputLine;
                                StringBuffer response = new StringBuffer();

                                while ((inputLine = in.readLine()) != null) {
                                    response.append(inputLine);
                                }

                                in.close();

                            }catch(Exception e){
                                System.out.println("REST Upload error "+ e);
                            }
                        }
                    }).start();
                }
                
                     
            }catch(Exception e){
                
            }
        
            index++;
        }
        
        
    }

    
}
