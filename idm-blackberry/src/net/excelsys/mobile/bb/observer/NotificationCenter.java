package net.excelsys.mobile.bb.observer;

import java.util.Hashtable;



import org.json.me.JSONObject;


public class NotificationCenter {

	private static NotificationCenter _notificationCenter = null;
	private Hashtable _notifications;
	
	private NotificationCenter(){
		_notifications = new Hashtable();
	}
	
	public static NotificationCenter getInstance(){
		if(_notificationCenter == null){
			_notificationCenter = new NotificationCenter();
		}
		return _notificationCenter;
	}
	
	public void suscribeToNotification(String notificationName, ICommunicationObserver observer){
		if(notificationName ==null){
			return;
		}
		else if(observer == null){
			return;
		}
		
		if(_notifications.containsKey(notificationName)){
			Observers observers = (Observers) _notifications.get(notificationName);
			observers.addObserver(observer);
		} else {
			Observers observers = new Observers();
			observers.addObserver(observer);
			_notifications.put(notificationName, observers);
		}
	}
	
	public void executeNotification(String name){
		if(name == null){
			return;
		}
		if(_notifications.containsKey(name)){
			Observers _observers = (Observers) _notifications.get(name);
			_observers.notifyObservers(name);
		}
	}
	
	public void executeNotification(JSONObject jsonNotification){
		if(jsonNotification == null){
			return;
		}
		

		
	}

	
}
