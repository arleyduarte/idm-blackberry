package com.amdp.blackberry.screen;



import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.Display;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.XYEdges;
import net.rim.device.api.ui.component.BitmapField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.VerticalFieldManager;
import net.rim.device.api.ui.decor.BackgroundFactory;
import net.rim.device.api.ui.decor.Border;
import net.rim.device.api.ui.decor.BorderFactory;

public class HeaderBB extends HorizontalFieldManager {

	private int fontHeight = (int) Math.floor(Font.getDefault().getHeight() * 1.3);
	public HeaderBB() {

		HorizontalFieldManager headerManager = new HorizontalFieldManager(USE_ALL_WIDTH | Field.FIELD_VCENTER);

		LabelField title = new LabelField("Instituto de Desarrollo del Meta", LabelField.HCENTER) {
			public void paint(Graphics graphics) {
				graphics.setColor(Color.WHITE);
				super.paint(graphics);
			}
		};

		title.setFont(title.getFont().derive(Font.BOLD, fontHeight));
		


		headerManager.setBackground(BackgroundFactory.createSolidBackground(AppStyle.APP_CORPORATE_COLOR));

		headerManager.add(title);

		
		add(headerManager);
	}

}
