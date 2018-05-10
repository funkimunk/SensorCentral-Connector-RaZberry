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
public class SensorMetadata {

    public String UUID = "";
    public String sensorID = "";
    public String label = "";
    public String location = "";
    public String relatedMimeResources = "";
    
    public int associatedEnv = -1;
    public int sensorClass = -1;
    public int deviceMfg = -1;
}
