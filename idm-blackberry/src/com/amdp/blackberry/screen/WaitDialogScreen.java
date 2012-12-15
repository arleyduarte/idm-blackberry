package com.amdp.blackberry.screen;

import net.rim.device.api.system.Display;
import net.rim.device.api.system.GIFEncodedImage;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.XYEdges;
import net.rim.device.api.ui.XYRect;
import net.rim.device.api.ui.container.PopupScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;
import net.rim.device.api.ui.decor.BackgroundFactory;
import net.rim.device.api.ui.decor.Border;
import net.rim.device.api.ui.decor.BorderFactory;

public class WaitDialogScreen extends PopupScreen {

	// This is de size of de the aniamte gif
	private int GIF_HEIGHT = 10;

	//private final static int _ALPHA = 0xBF; // 75% alpha
	private final static int _ALPHA = 0xBB;
	private AnimatedGIFField _ourAnimation = null;

	private boolean _cancelled = false;

	public WaitDialogScreen(final Runnable runThis) {
		super(new VerticalFieldManager(VerticalFieldManager.USE_ALL_HEIGHT
				| VerticalFieldManager.USE_ALL_WIDTH));

		Border border = BorderFactory.createSimpleBorder(
		        new XYEdges(), Border.STYLE_TRANSPARENT);
	
		

		this.setBorder(border);
		GIFEncodedImage ourAnimation = (GIFEncodedImage) GIFEncodedImage
				.getEncodedImageResource("loading10.gif");

		_ourAnimation = new AnimatedGIFField(ourAnimation, Field.FIELD_HCENTER);
		_ourAnimation.setMargin(new XYEdges((Display.getHeight() / 2)
				- (GIF_HEIGHT / 2), 0, 0, 0));

		this.add(_ourAnimation);

		setBackground(BackgroundFactory.createSolidTransparentBackground(
				0x000000, 0));

	}

	public static int showScreenAndWait(final Runnable runThis) {
		final WaitDialogScreen thisScreen = new WaitDialogScreen(runThis);
		Thread threadToRun = new Thread() {
			public void run() {

				try {
					runThis.run();
				} catch (Throwable t) {
					t.printStackTrace();
					//throw new RuntimeException(
					//		"Exception detected while waiting: " + t.toString());
				} finally {
					// Now dismiss this screen
					UiApplication.getUiApplication().invokeLater(
							new Runnable() {
								public void run() {
									UiApplication.getUiApplication().popScreen(
											thisScreen);
								}
							});
				}
			}
		};
		threadToRun.start();
		UiApplication.getUiApplication().pushModalScreen(thisScreen);
		if (thisScreen._cancelled) {
			return -1;
		} else {
			return 0;
		}
	}

	protected void sublayout(int width, int height) {
		setExtent(Display.getWidth(), Display.getHeight());
		setPosition(0,0);
		layoutDelegate(Display.getWidth(), Display.getHeight());
	}

	protected void paintBackground(Graphics g) {
		XYRect myExtent = getExtent();
		int color = g.getColor();
		int alpha = g.getGlobalAlpha();
		g.setGlobalAlpha(_ALPHA);
		g.setColor(0xE3E3E3);
		g.fillRect(0, 0, myExtent.width, myExtent.height+1000);
		g.setColor(color);
		g.setGlobalAlpha(alpha);
	}

}
