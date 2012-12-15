package com.amdp.bb.basic.handler;


import net.rim.blackberry.api.browser.URLEncodedPostData;

import org.json.me.JSONObject;

import com.amdp.bb.api.ResourceHandler;
import com.amdp.bb.basic.entity.Status;
import com.amdp.bb.basic.entity.User;
import com.amdp.bb.basic.entity.UserBLL;
import com.amdp.bb.net.NetworkConstants;

public class UserValidationResourceHandler extends ResourceHandler{
	private String login = "";
	private String password = "";

	public UserValidationResourceHandler(String login, String password) {
		this.login = login;
		this.password = password;
	}
	public void handlerAPIResponse(JSONObject json) {
		Status status = new Status(json);

		if (status.isSuccess()) {
			User currentUser = new User();
			currentUser.setUserId(status.getCode());
			UserBLL.getInstanceBLL().setCurrentUser(currentUser);
			this.getResponseActionDelegate().didSuccessfully(status.getDescription());
		} else {
			this.getResponseActionDelegate().didNotSuccessfully(status.getCode());
		}
		
	}
	public URLEncodedPostData getValueParams() {
		URLEncodedPostData postData = new URLEncodedPostData("UTF-8", false);
		postData.append("username", login);
		postData.append("password", password);
		postData.append("deviceId", "BlackBerry");
		return postData;
	}
	public String getServiceURL() {
		return NetworkConstants.BASE_URL+"/user/validate/";
	}

}
