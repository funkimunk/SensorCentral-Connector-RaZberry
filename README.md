# SensorCentral-Connector-RaZberry

### Introduction

This is a connector for SensorCentral enabling relay of sensor data from an instance of the RaZberry zwave server ([ https://zwave.me/products/razberry/]( https://zwave.me/products/razberry/)).
This software will automatically enumerate all sensors registered to a RaZberry zwave server and provide near realtime relay of data from these devices.
The initial release supports binary sensors only but by extension though a simple case statement it should seamlessly support other types.

### Development Environment

- The connector was developed in platform independent Java. 
- This software was developed using Netbeans. 
- Dependant libraries include Gson.

### Configuring the solution

On initial run, the software will generate example config files (conf.json). These need to be modified by an end user to provide a number environment specific parameters.
The text of this file is shown below, and the parameters are detailed in a subsequent table. 

#### conf.json

``` javascript
{
  "instanceID": "",
  "piAddr": "",
  "piUsn": "",
  "piPw": "",
  "scAddr": "",
  "scApi": ""
}
```

#### Parameters

Parameter  | Description
------------- | -------------
instanceID  | A unique ID for the RaZberry instance, this is alphanumeric and random such as Hs8dKSh69sd.
piAddr  | The address of the Pi hosting the server, such as : http://127.0.0.1:8083.
piUsn | A username for the RaZberry instance.
piPw | A password for the user accessing the RaZberry instance.
scAddr | The address of the SensorCentral instance, such as  https://HOSTSERVER.EXAMPLE/SensorCentral/REST/
scApi | A valid API key for the SensorCentral instance, such as: AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
