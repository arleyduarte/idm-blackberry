package net.excelsys.mobile.bb.observer;



import net.rim.device.api.i18n.ResourceBundle;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Screen;
import net.rim.device.api.ui.Ui;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.UiEngine;
import net.rim.device.api.ui.component.Dialog;

public class AlertCenter implements ICommunicationObserver {
	private static AlertCenter _instance = null;
	private boolean isUnautorhorized = false;
	private boolean isServiceUnavailable = false;
	private boolean isShowingMainNotification = false;
	private boolean isLoginScreen = false;



	private AlertCenter() {

		NotificationCenter _noCenter = NotificationCenter.getInstance();
		_noCenter.suscribeToNotification(NotificationsTypeConstants.HTTP_CLIENT_ERROR, this);
		_noCenter.suscribeToNotification(NotificationsTypeConstants.HTTP_SERVER_ERROR, this);
		_noCenter.suscribeToNotification(NotificationsTypeConstants.HTTP_UNAUTHORIZED, this);
		_noCenter.suscribeToNotification(NotificationsTypeConstants.NETWORK_NOT_AVAILABLE, this);
		_noCenter.suscribeToNotification(NotificationsTypeConstants.CLIENT_IN_BLACK_LIST, this);
		_noCenter.suscribeToNotification(NotificationsTypeConstants.SEVICE_UNAVAILABLE, this);
	}

	public static AlertCenter getInstance() {
		if (_instance == null) {
			_instance = new AlertCenter();
		}
		return _instance;
	}

	public void alertHTTPUnauthorized() {
		isUnautorhorized = true;
		//showNotification(resources.getString(MSG_UNLINKED_DEVICE));
	}

	public void alertClientInBlackList() {
		isUnautorhorized = true;
		//showNotification(resources.getString(MSG_CLIENT_IN_BLACK_LIST));
	}

	public void alertHTTPClientError() {
		//showNotification(resources.getString(THE_BANK_SERVICES_ARE_NOT_AVAILABLE));
	}

	public void alertHTTPServerError() {
		showNotification("No se pudo conectar con el servidor. Por favor valide que disponga de una conexión de red");

	}

	public void alertNetworkNotAvailable() {
		showNotification("No se pudo conectar con el servidor. Por favor valide que disponga de una conexión de red");
	}

	public void alertShowMessage(String message) {
		showNotification(message);
	}

	public void showAlertDialog(String message) {
		if (isShowingMainNotification) {
			isShowingMainNotification = false;
		} else {
			pushAlert(message);
		}
	}

	public void showAlertDialog(String message, String buttonTitle) {

		if (isShowingMainNotification) {
			isShowingMainNotification = false;
		} else {
			pushAlert(message, buttonTitle);
		}

	}

	private void pushAlert(String message) {
		pushAlert(message, null);
	}

	private void pushAlert(String message, String buttonTitle) {

		UiEngine ui = Ui.getUiEngine();
		Screen screen = null;
		if (buttonTitle == null || buttonTitle.length() == 0) {
			buttonTitle = "Aceptar";
		}
		screen = new Dialog(message, new String[] { buttonTitle }, new int[] { 1 }, 1, Bitmap.getPredefinedBitmap(Bitmap.EXCLAMATION), Dialog.D_OK);
		ui.pushGlobalScreen(screen, 1, UiEngine.GLOBAL_QUEUE);
	}

	private final void showNotification(String message) {
		isShowingMainNotification = true;

		synchronized (UiApplication.getApplication().getEventLock()) {

			pushAlert(message);


		}
	}

	public void alertServiceUnavailable() {
		isServiceUnavailable = true;
		//showNotification(resources.getString(MSG_SERVICE_UNAVAILABLE));
	}

	public boolean isLoginScreen() {
		return isLoginScreen;
	}

	public void setLoginScreen(boolean isLoginScreen) {
		this.isLoginScreen = isLoginScreen;
	}

}
