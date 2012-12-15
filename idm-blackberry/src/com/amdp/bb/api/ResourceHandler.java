package com.amdp.bb.api;

import java.io.IOException;
import net.rim.blackberry.api.browser.URLEncodedPostData;
import org.json.me.JSONObject;

import com.amdp.bb.net.NetworkConnectorOAuth;

public abstract class ResourceHandler {

	private ResponseActionDelegate responseActionDelegate;

	public void sendAPIMessage(ResponseActionDelegate responseActionDelegate) {
		this.responseActionDelegate = responseActionDelegate;
		NetworkConnectorOAuth nc = new NetworkConnectorOAuth(getServiceURL());
		nc.setRequestMethod(getHttpVerb());
		try {

			handlerAPIResponse(nc.connect(getValueParams(),isNeedAutorization()));
		} catch (IOException e) {

		}
	}
	
	public boolean isNeedAutorization() {
		return false;
	}
	public abstract void handlerAPIResponse(JSONObject json);

	public  URLEncodedPostData getValueParams(){
		return null;
	}

	public abstract String getServiceURL();

	public String getHttpVerb() {
		return NetworkConnectorOAuth.REQUEST_METHOD_POST;
	}

	public ResponseActionDelegate getResponseActionDelegate() {
		return responseActionDelegate;
	}


}