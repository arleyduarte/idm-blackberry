package com.amdp.blackberry.screen;

import javax.microedition.lcdui.Font;

import com.amdp.bb.api.ResponseActionDelegate;
import com.amdp.bb.basic.handler.UserValidationResourceHandler;

import net.excelsys.mobile.bb.observer.AlertCenter;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.TransitionContext;
import net.rim.device.api.ui.Ui;
import net.rim.device.api.ui.UiEngineInstance;


import net.rim.device.api.ui.XYEdges;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.EditField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.PasswordEditField;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;
import net.rim.device.api.ui.decor.Background;
import net.rim.device.api.ui.decor.BackgroundFactory;
import net.rim.device.api.ui.decor.Border;
import net.rim.device.api.ui.decor.BorderFactory;


public class LoginScreen extends MainScreen implements ResponseActionDelegate {



	private LoginScreen responseActionDelegate = null;
	private int nextScreen = 0;
	private int DIALOG_MESSAGE_SCREEN = 1;
	private int SUCCESSFUL_SCREEN = 2;
	private String userMessage = "";
	private String userFullName = "";
	private String buttonTitle = "";
	private Thread threadToRun;
	private int fontHeight = (int) Math.floor(Font.getDefaultFont().getHeight() * 0.9);

	public LoginScreen() {
		super(MainScreen.VERTICAL_SCROLL | MainScreen.VERTICAL_SCROLLBAR);
		responseActionDelegate = this;
	
		setBackground(BackgroundFactory.createSolidBackground(AppStyle.APP_AUTH_PROCESS_BACKGROUND_COLOR)); 

		add(new HeaderBB());
		// field styles
		Border roundedBorder = BorderFactory.createRoundedBorder(new XYEdges(2, 2, 2, 2), Color.BLACK, Border.STYLE_SOLID);
		Background fieldBackground = BackgroundFactory.createLinearGradientBackground(Color.LIGHTGRAY, Color.LIGHTGRAY, Color.WHITE, Color.WHITE);
		XYEdges fieldPadding = new XYEdges(5, 5, 5, 5);
		XYEdges fieldMargin = new XYEdges(0, 0, 10, 0);

		// set the login form layout
		VerticalFieldManager loginFormManager = new VerticalFieldManager();
		loginFormManager.setMargin(new XYEdges(0, 0, 5, 0));
		loginFormManager.setPadding(new XYEdges(10, 20, 10, 20));
		loginFormManager.setBackground(BackgroundFactory.createSolidBackground(AppStyle.APP_AUTH_PROCESS_BACKGROUND_COLOR));
		add(loginFormManager);

		// username field
		LabelField usernameLabelField = new LabelField("Nombre de usuario");
		usernameLabelField.setFont(usernameLabelField.getFont().derive(Font.STYLE_BOLD, fontHeight));
		loginFormManager.add(usernameLabelField);
		final EditField usernameEditField = new EditField();
		usernameEditField.setBackground(fieldBackground);
		usernameEditField.setBorder(roundedBorder);
		usernameEditField.setPadding(fieldPadding);
		usernameEditField.setMargin(fieldMargin);
		loginFormManager.add(usernameEditField);

		// password field
		LabelField passwordLabelField = new LabelField("Contraseña");
		passwordLabelField.setFont(passwordLabelField.getFont().derive(Font.STYLE_BOLD, fontHeight));
		loginFormManager.add(passwordLabelField);
		final PasswordEditField passwordEditField = new PasswordEditField();
		passwordEditField.setBackground(fieldBackground);
		passwordEditField.setBorder(roundedBorder);
		passwordEditField.setPadding(fieldPadding);
		passwordEditField.setMargin(fieldMargin);
		loginFormManager.add(passwordEditField);

		ButtonField continueButton = new ButtonField("Continuar", ButtonField.CONSUME_CLICK | ButtonField.FIELD_HCENTER);
		loginFormManager.add(continueButton);
		continueButton.setChangeListener(new FieldChangeListener() {
			public void fieldChanged(Field arg0, int arg1) {

				String username = usernameEditField.getText();
				String password = passwordEditField.getText();
				if (null != username && username.compareTo("") != 0 && null != password && password.compareTo("") != 0) {

					validateUsernameAndPassword(username, password);
				} else {

					Dialog dialog = new Dialog("Debe ingresar el nombre de usuario y la contraseña ", new String[] { "Aceptar" }, new int[] { 1 }, 1, Bitmap
							.getPredefinedBitmap(Bitmap.EXCLAMATION), Dialog.D_OK);
					dialog.show();
				}
			}
		});

		VerticalFieldManager footerManager = new VerticalFieldManager();
		add(footerManager);



	}

	private void goToNextScreen() {
		

//		AlertCenter.getInstance().setLoginScreen(false);
		if (nextScreen == DIALOG_MESSAGE_SCREEN) {
			
			AlertCenter.getInstance().showAlertDialog("El nombre de usuario o contraseña no es valido. Por favor intente nuevamente");
		} else if (nextScreen == SUCCESSFUL_SCREEN) {
			GPScreen gpsScreen = new GPScreen();
			

		
			//GPScreen demo = new GPScreen();
		}

	}

	public void close() {
		System.exit(0);
	}

	protected boolean onSavePrompt() {
		System.exit(0);
		return false;
	}

	private void validateUsernameAndPassword(final String username, final String password) {

		threadToRun = new Thread() {
			public void run() {
				UserValidationResourceHandler rsourceHandler = new UserValidationResourceHandler(username,password );
				rsourceHandler.sendAPIMessage(responseActionDelegate);
			}
		};

		WaitDialogScreen.showScreenAndWait(threadToRun);
		goToNextScreen();
	}




	public void didSuccessfully(String status) {
		nextScreen = SUCCESSFUL_SCREEN;
		
	}

	public void didNotSuccessfully(String status) {
		nextScreen = DIALOG_MESSAGE_SCREEN;
		
	}
}
