package net.excelsys.mobile.bb.observer;

import java.util.Enumeration;
import java.util.Vector;

public class Observers {

	private Vector _observers;

	public Observers() {
		_observers = new Vector();
	}

	public void addObserver(ICommunicationObserver observer) {
		if (observer == null) {
			return;
		}
		_observers.addElement(observer);
	}

	public void notifyObservers(String errorName) {
		if (_observers.size() < 1) {
			return;
		}
		for (Enumeration e = _observers.elements(); e.hasMoreElements();) {
			ICommunicationObserver element = (ICommunicationObserver) e.nextElement();
			if(errorName == NotificationsTypeConstants.HTTP_CLIENT_ERROR){
				element.alertHTTPClientError();
			} else if(errorName == NotificationsTypeConstants.HTTP_UNAUTHORIZED){
				element.alertHTTPUnauthorized();
			} else if(errorName == NotificationsTypeConstants.HTTP_SERVER_ERROR){
				element.alertHTTPServerError();
			} else if(errorName == NotificationsTypeConstants.NETWORK_NOT_AVAILABLE){
				element.alertNetworkNotAvailable();
			}
			
			 else if(errorName == NotificationsTypeConstants.CLIENT_IN_BLACK_LIST){
					element.alertClientInBlackList();
				}	
			 else if(errorName == NotificationsTypeConstants.SEVICE_UNAVAILABLE){
					element.alertServiceUnavailable();
				}	
			else{
				element.alertShowMessage(errorName);
			}
		}
	}

}
