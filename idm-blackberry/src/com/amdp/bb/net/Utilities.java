package com.amdp.bb.net;



import com.bb.log.Log4Device;


/**
 * 
 */
public class Utilities {

    /** Convert from byte array to String with Hex representation */
    public static String byteArrayToHexString(byte in[]) {
        byte ch = 0x00;
        int i = 0;
        if (in == null || in.length <= 0)
            return null;
        
        String pseudo[] =
        {"0", "1", "2", "3",
         "4", "5", "6", "7",
         "8", "9", "A", "B",
         "C", "D", "E", "F"};
         StringBuffer out = new StringBuffer(in.length * 2);
         
         while (i < in.length)
         {
             ch = (byte) (in[i] & 0xF0); // Strip off high nibble
             ch = (byte) (ch >>> 4);
             // shift the bits down
             ch = (byte) (ch & 0x0F);
             // must do this is high order bit is on!
             out.append(pseudo[ (int) ch]); // convert the nibble to a String Character
             ch = (byte) (in[i] & 0x0F); // Strip off low nibble
             out.append(pseudo[ (int) ch]); // convert the nibble to a String Character
             i++;
         }
         String rslt = out.toString();
         return rslt;
    }

    /**
     * Simple helper routine to convert to UTF8 String
     * @param stringToConvert - standard java String to convert
     * @return - byte array containing converted text
     */
    static public byte [] toUTF8(String stringToConvert) {
        byte [] returnBytes = null;
        try {
            returnBytes = stringToConvert.getBytes("UTF-8");
        } catch (Exception e) {
            Log4Device.log("Exception converting to UTF-8: " + e.toString());
        }
        return returnBytes;
    }

    /**
     * Simple helper routine to convert from UTF8 bytes
     * @param bytesToConvert - UTF8 encoded bytes
     * @return - String to use in Application
     */
    static public String fromUTF8(byte [] bytesToConvert) {
        String returnString = null;
        try {
            returnString = new String(bytesToConvert,"UTF-8");
        } catch (Exception e) {
            Log4Device.log("Exception converting from UTF-8: " + e.toString());
        }
        return returnString;
    }
} 