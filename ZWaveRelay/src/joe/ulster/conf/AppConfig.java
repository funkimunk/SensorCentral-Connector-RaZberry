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

import java.util.Hashtable;

/**
 *
 * @author Joe Rafferty - Ulster University
 */
public class AppConfig {

    public static String instanceID = "";
    public static String piAddr = "http://10.69.69.2:8083";
    public static String piUsn = "admin";
    public static String piPw = "password";

    public static String scAddr = "";
    public static String scApi = "";
    
    public static String confFileName = "conf.json";
    
    public static String cookie = "";
    
    public static Hashtable<String, Integer> devices = new Hashtable<String, Integer>();
    
    
}
