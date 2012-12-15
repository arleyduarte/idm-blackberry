package com.amdp.bb.basic.entity;
import org.json.me.JSONException;
import org.json.me.JSONObject;

public class Status {
	private boolean success = false;
	private String code="";
	private String description="";

	public Status(JSONObject jsonResponse) {
		fromJson(jsonResponse);
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public void setSuccess(String success) {
		if (success.equalsIgnoreCase("true")) {
			this.success = true;
		}

		else {
			this.success = false;
		}

	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void fromJson(JSONObject jsonObject) {
		if(jsonObject != null){
			try {

				JSONObject statusNode = jsonObject.getJSONObject("status");
				setSuccess(statusNode.getString("success"));

				setCode(statusNode.getString("code"));
				setDescription(statusNode.getString("description"));

			} catch (JSONException e) {
				e.printStackTrace();
			}		
		}
		

	}
}
