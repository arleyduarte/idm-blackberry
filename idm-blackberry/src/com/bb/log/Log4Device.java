package com.bb.log;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Vector;

import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;

import net.rim.device.api.i18n.SimpleDateFormat;
import net.rim.device.api.system.DeviceInfo;
import net.rim.device.api.system.EventLogger;
import net.rim.device.api.util.StringUtilities;


public class Log4Device {

    private static long GUID = 0L;
    private static boolean inDebugMode = true;
    private static  Vector infoVector = new Vector();
        
    private static final String APPNAME = "4sqOauth[3party]";
        
    static {
        String classname = Log4Device.class.getName();
        String guidstr   = classname + ".GUID";
        GUID             = StringUtilities.stringHashToLong(guidstr);
                
        // register event logger
        EventLogger.register(GUID, APPNAME, EventLogger.ALWAYS_LOG);
    }
        
    private Log4Device() { /* make class singleton */ }

    public static void log(String message) {
        byte[] data = message.getBytes();
        if (DeviceInfo.isSimulator()) {
            try {
                System.out.println(new String(data, "UTF-8"));
            }
            catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        else {
           // EventLogger.logEvent(GUID, data, EventLogger.ALWAYS_LOG);
        }
        
        if(inDebugMode){
        	addInfoString(message);
        }
    }
    
    public static void inspectMemory(){
    	 infoVector.addElement("Inspect Memory free memory:" + Runtime.getRuntime().freeMemory()+" Total memory: "+Runtime.getRuntime().totalMemory());
    }
    
    
    
    
    public static void log(Object classMother, String message) {
    	log(classMother.getClass()+" "+message);
    }

	public static void setInDebugMode(boolean _inDebugMode) {
		inDebugMode = _inDebugMode;
	}


	
    public static  void addInfoString(String info){
        try {
            infoVector.addElement(info);
        } catch (Exception e) {
            System.out.println("error " + e);
        }
    }
    
    public static Vector getInfoVector(){
    	return infoVector;
    }

	public static void createFileReport() {
		
		

		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String fileName = "LogEFX_"+timeStamp;
		
 	   try {
 		  FileConnection fc = (FileConnection)Connector.open("file:///store/home/user/"+fileName+".txt");
  	     if (!fc.exists()){
  	    	 fc.create();
  	     }
  	     
  	     OutputStream outStream = fc.openOutputStream(); 
  	     String aux = "";
  	     
  	     for(int i=0; i<infoVector.size(); i++){
  	    	 aux += infoVector.elementAt(i).toString()+"\n";
  	     }
  	     
  	      outStream.write(aux.getBytes());
  	      outStream.close();
  	      fc.close();
  	   } 
  	   catch (Exception ex){
  		   System.out.println(""+ex);
  	   } 
  	
		
	}
}
