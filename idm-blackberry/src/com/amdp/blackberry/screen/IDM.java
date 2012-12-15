package com.amdp.blackberry.screen;


import net.rim.device.api.ui.UiApplication;

/**
 * This class extends the UiApplication class, providing a
 * graphical user interface.
 */
public class IDM extends UiApplication
{
    /**
     * Entry point for application
     * @param args Command line arguments (not used)
     */ 
    public static void main(String[] args)
    {
        // Create a new instance of the application and make the currently
        // running thread the application's event dispatch thread.
        IDM theApp = new IDM();       
        theApp.enterEventDispatcher();
    }
    

    /**
     * Creates a new IDM object
     */
    public IDM()
    {        
        // Push a screen onto the UI stack for rendering.
    	//GPScreen demo = new GPScreen();
    	
        pushScreen(new LoginScreen());
    }    
}
