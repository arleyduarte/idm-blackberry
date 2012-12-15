package com.amdp.bb.basic.handler;


import net.rim.blackberry.api.browser.URLEncodedPostData;

import org.json.me.JSONObject;

import com.amdp.bb.api.ResourceHandler;
import com.amdp.bb.basic.entity.Status;
import com.amdp.bb.basic.entity.UserBLL;
import com.amdp.bb.net.NetworkConstants;

public class AddCheckinResourceHandler extends ResourceHandler{
	private String longitude = "";
	private String latitude = "";


	public AddCheckinResourceHandler(double latitude, double longitude) {
		this.longitude = String.valueOf(longitude);
		this.latitude = String.valueOf(latitude);

	}
	public void handlerAPIResponse(JSONObject json) {
		Status status = new Status(json);

		if (status.isSuccess()) {
			this.getResponseActionDelegate().didSuccessfully(status.getDescription());
		} else {
			this.getResponseActionDelegate().didNotSuccessfully(status.getCode());
		}
		
	}
	public URLEncodedPostData getValueParams() {
		URLEncodedPostData postData = new URLEncodedPostData("UTF-8", false);
		postData.append("longitude", longitude);
		postData.append("latitude", latitude);
		postData.append("userId", UserBLL.getInstanceBLL().getCurrentUser().getUserId());
		return postData;
	}
	public String getServiceURL() {
		return NetworkConstants.BASE_URL+"/checkin/add/";
	}

}
